package pharmaceuticals.nl.peptrix.R;

import java.io.*;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.SearchFile;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class Rterm {

    ExportFileToDisk exportfiletodisk;

    public String RsourceBasePath;

    String RtopPath;

    String RbasePath;

    String RbinBasePath;

    File fRscript;

    Process jproc;

    File fileRtopPath;

    String findstring;

    int rwvalue = 0;

    int rwvalueold = 0;

    File[] fileRtopPathsubdirs;

    String teststring;

    String fullRunRfilePath;

    String fullRoutputFilePath;

    String fullRsourcePath;

    String sRunQuote;

    String sInputQuote;

    String sOutputQuote;

    int idxSpaceInRunFilePath;

    int idxSpaceInRscriptInputPath;

    int idxSpaceInRscriptOutputPath;

    String Rcmd;

    FileDescriptor fd;

    String Rbatchfile;

    public String RinputBasePath;

    public String ReportsBasePath;

    String RoutBasePath;

    String message;

    boolean errormessageonce = false;

    int count_names;

    String r_executable = "r.exe";

    String found_r_executable_name;

    SearchFile searchfile;

    Controller cc;

    int follownumber;

    public Rterm(Controller cc) {
        follownumber = 0;
        this.cc = cc;
        exportfiletodisk = new ExportFileToDisk(cc);
        searchfile = new SearchFile(cc);
        makedirs();
        errormessageonce = false;
    }

    private void makedirs() {
        RinputBasePath = cc.userhome + cc.fileSeparator + "Rinput" + cc.fileSeparator;
        fRscript = null;
        try {
            fRscript = new File(RinputBasePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!fRscript.isDirectory()) {
            fRscript.mkdirs();
        }
        ReportsBasePath = cc.userhome + cc.fileSeparator + "Reports" + cc.fileSeparator;
        fRscript = null;
        try {
            fRscript = new File(ReportsBasePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!fRscript.isDirectory()) {
            fRscript.mkdirs();
        }
        RsourceBasePath = cc.userhome + cc.fileSeparator + "R" + cc.fileSeparator;

        fRscript = null;
        try {
            fRscript = new File(RsourceBasePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!fRscript.isDirectory()) {
            fRscript.mkdirs();
        }
        RoutBasePath = cc.userhome + cc.fileSeparator + "Out" + cc.fileSeparator;

        fRscript = null;
        try {
            fRscript = new File(RoutBasePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!fRscript.isDirectory()) {
            fRscript.mkdirs();
        }

    }

    public void createRbatchfile(String inputRscriptname) {
        String Rscriptname = inputRscriptname + ".R";
        if (RbinBasePath == null) {
            setupRbinPath();
        } else {
            if (RbinBasePath.trim().equalsIgnoreCase("")) {
                setupRbinPath();
            }
        }
        fullRunRfilePath = RbinBasePath + "R";
        fullRoutputFilePath = RoutBasePath + "output.txt";
        fullRsourcePath = RsourceBasePath + Rscriptname;
        String str_follownumber = "";
        try {
            str_follownumber = String.valueOf(++follownumber) + "_";
        } catch (Exception e) {
            ;
        }
        String str_experimentid = "";
        try {
            str_experimentid = cc.experiment.getExperimentid() + "_";
        } catch (Exception e) {
            ;
        }
        if (cc.osName.startsWith("Windows")) {
            Rbatchfile = RsourceBasePath + "R_" + str_experimentid + str_follownumber + inputRscriptname.trim()
                    + ".bat";
            idxSpaceInRunFilePath = fullRunRfilePath.indexOf(" ");
            idxSpaceInRscriptInputPath = fullRsourcePath.indexOf(" ");
            idxSpaceInRscriptOutputPath = fullRoutputFilePath.indexOf(" ");
            sRunQuote = (idxSpaceInRunFilePath == -1) ? "" : "\"";
            sInputQuote = (idxSpaceInRscriptInputPath == -1) ? "" : "\"";
            sOutputQuote = (idxSpaceInRscriptOutputPath == -1) ? "" : "\"";
            Rcmd = sRunQuote + fullRunRfilePath + sRunQuote + " --no-save < " + sInputQuote + fullRsourcePath
                    + sInputQuote + " > " + sOutputQuote + fullRoutputFilePath + sOutputQuote;
            exportfiletodisk.exportcompletefilename(Rbatchfile, Rcmd.getBytes());
        }
    }

    public int execRscript() {
        int procesexitvalue = -1;
        try {
            if (Rbatchfile.indexOf(" ") > 0) {
                Rbatchfile = "\"" + Rbatchfile + "\"";
            }
            jproc = Runtime.getRuntime().exec(Rbatchfile);
            try {
                jproc.waitFor();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            procesexitvalue = jproc.exitValue();
            if (procesexitvalue == 1) {
                if (errormessageonce == false) {
                    errormessageonce = true;
                    message = "R.exe (www.r-project.org) was not found on the computer \n"
                            + "It should be installed in a directory like C:/Program Files/R/rw2011/bin or\n"
                            + "C:/Program Files/R/R-2.2.1/bin \n" + "with 2011 or 2.2.1 the version number";
                    if (cc.debugmode) {
                        System.out.println(message);
                    } else {
                        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            jproc.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (procesexitvalue);
    }

    private void setupRbinPath() {
        found_r_executable_name = searchfile.return_file("R.exe");
        if ((found_r_executable_name != null) && (!found_r_executable_name.trim().equalsIgnoreCase(""))) {
            RbinBasePath = found_r_executable_name.substring(0, found_r_executable_name.toLowerCase().indexOf("r.exe"));
            if (found_r_executable_name.toLowerCase().indexOf("\\r\\") > -1) {
                RtopPath = found_r_executable_name.substring(0,
                        (found_r_executable_name.toLowerCase().indexOf("\\r\\") + 3));
            } else if (found_r_executable_name.toLowerCase().indexOf("/r/") > -1) {
                RtopPath = found_r_executable_name.substring(0,
                        (found_r_executable_name.toLowerCase().indexOf("/r/") + 3));
            }
        }
    }
}
