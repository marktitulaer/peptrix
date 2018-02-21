package pharmaceuticals.nl.peptrix.fileimport;

import java.io.File;
import java.sql.SQLException;
import java.util.Vector;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.SuffixAwareFilter;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.service.SampleService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.SampleServiceImpl;

public class ImportFiles {

    FileTransfer filetransfer;

    SuffixAwareFilter suffixAwarefilter;

    File[] files;

    File parent1;

    File parent2;

    File parent3;

    File parent4;

    Object[][] filesolreadyindatabase;

    String[] strfilesolreadyindatabase;

    StringBuffer stringbufferfilename;

    String type;

    String suffix;

    float filegrootte_kbytes;

    long lastmod;

    int progresscount;

    int teller;

    ResultService resultservice;

    ResultServiceImpl resultServiceImpl;

    boolean booleanfileolreadyindatabase;

    SampleService sampleService;

    SampleServiceImpl sampleServiceImpl;

    public void processfiles(Controller cc, Vector<File[]> filevector, String filtertype, String existingexperimentid,
                             boolean isnewexperiment, String latestexperimentid, String experimentyear, Progress progress1,
                             boolean generatesamplecodes) {
        sampleService = new SampleServiceImpl(cc);
        resultservice = new ResultServiceImpl(cc);
        progresscount = 0;
        for (int i = 0; i <= (filevector.size() - 1); i++) {
            File progress[];
            progress = filevector.get(i);
            for (int j = 0; j <= progress.length - 1; j++) {
                suffix = null;
                suffixAwarefilter = new SuffixAwareFilter();
                suffix = suffixAwarefilter.getSuffix(progress[j]);
                if (suffix != null) {
                    if ((suffix.equals("txt") && filtertype.equals("txt"))
                            || (suffix.equals("fid") && filtertype.equals("fid"))
                            || (suffix.equals("acqu") && filtertype.equals("fid"))
                            || (suffix.equals("xml") && filtertype.equals("xml"))
                            || (suffix.equals("mzxml") && filtertype.equals("mzxml"))
                            || (suffix.equals("raw") && filtertype.equals("raw"))
                            || (suffix.equals("acqus") && filtertype.equals("fid"))) {
                        progresscount++;
                    }
                }
            }
        }
        files = null;
        progress1.init("Preparing SQL statement ...", 70);
        progress1.setmaximum(progresscount);
        filesolreadyindatabase = resultservice.getallresultrecords(existingexperimentid);
        strfilesolreadyindatabase = new String[filesolreadyindatabase.length];
        for (int i = 0; i <= (filesolreadyindatabase.length - 1); i++) {
            strfilesolreadyindatabase[i] = (String) filesolreadyindatabase[i][0];
        }
        teller = 0;
        try {
            cc.jdbcconnection.init_batch();
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        for (int i = 0; i <= (filevector.size() - 1); i++) {
            files = filevector.get(i);
            for (int j = 0; j <= files.length - 1; j++) {
                lastmod = files[j].lastModified();
                cc.actualtime.setTimeInMillis(lastmod);
                filegrootte_kbytes = (float) (files[j].length() / 1024.000);
                suffix = null;
                suffixAwarefilter = new SuffixAwareFilter();
                suffix = suffixAwarefilter.getSuffix(files[j]);
                if ((suffix.equals("txt") && (filtertype.equals("txt")))
                        || (suffix.equals("fid") && (filtertype.equals("fid")))
                        || (suffix.equals("acqu") && (filtertype.equals("fid")))
                        || (suffix.equals("xml") && (filtertype.equals("xml")))
                        || (suffix.equals("mzxml") && filtertype.equals("mzxml"))
                        || (suffix.equals("raw") && (filtertype.equals("raw")))
                        || (suffix.equals("acqus") && (filtertype.equals("fid")))) {
                    teller++;
                    progress1.setnumberandtext(teller, "");
                    String filename = files[j].getName();
                    if (suffix.equals("fid") || suffix.equals("acqu") || suffix.equals("mzxml") || suffix.equals("xml")
                            || suffix.equals("raw") || suffix.equals("acqus")) {
                        parent1 = files[j].getParentFile();
                        parent2 = parent1.getParentFile();
                        parent3 = parent2.getParentFile();
                        parent4 = parent3.getParentFile();
                        stringbufferfilename = new StringBuffer();
                        if (parent4 != null && !parent4.getName().trim().equalsIgnoreCase("")) {
                            stringbufferfilename.append(parent4.getName().trim() + "_");
                        }
                        if (parent3 != null && !parent3.getName().trim().equalsIgnoreCase("")) {
                            stringbufferfilename.append(parent3.getName().trim() + "_");
                        } else {
                            stringbufferfilename = new StringBuffer();
                        }
                        if (parent2 != null && !parent2.getName().trim().equalsIgnoreCase("")) {
                            stringbufferfilename.append(parent2.getName().trim() + "_");
                        } else {
                            stringbufferfilename = new StringBuffer();
                        }
                        if (parent1 != null && !parent1.getName().trim().equalsIgnoreCase("")) {
                            stringbufferfilename.append(parent1.getName().trim() + "_");
                        } else {
                            stringbufferfilename = new StringBuffer();
                        }
                        stringbufferfilename.append(files[j].getName().trim());
                        filename = stringbufferfilename.toString();
                    } else if (suffix.equals("txt")) {
                        parent1 = files[j].getParentFile();
                        if (parent1.getName().trim().equalsIgnoreCase("")) {
                            filename = files[j].getName().trim();
                        } else {
                            filename = parent1.getName().trim() + "_" + files[j].getName().trim();
                        }
                    }
                    if (filename.length() > 100) {
                        filename = filename.substring(filename.length() - 100, filename.length());
                    }
                    booleanfileolreadyindatabase = false;
                    for (int k = 0; k <= (strfilesolreadyindatabase.length - 1); k++) {
                        if (filename.trim().equalsIgnoreCase(strfilesolreadyindatabase[k].trim())) {
                            booleanfileolreadyindatabase = true;
                            break;
                        }
                    }
                    if (!booleanfileolreadyindatabase) {
                        type = suffix.toString().trim();
                        if (type.equals("fid") && (filegrootte_kbytes < 5)) {
                            type = "zero(fid)";
                        }
                        if ((cc.equipmentid.getText().trim().equalsIgnoreCase("2"))
                                && (filename.indexOf("peaklist") > -1) && (type.trim().equalsIgnoreCase("xml"))) {
                            type = "zero(xml)";
                        }
                        if ((!(suffix.equals("acqu") || suffix.equals("acqus"))) && (generatesamplecodes)) {
                            sampleService.insertsample(filename);
                        }


                        cc.strquery = "INSERT INTO Result(Experimentid, Sampleid, Date, Time, Size_KB, File, Type,Year) "
                                + "VALUES (" + existingexperimentid + ",null,'" + cc.actualtime.getdatestring() + "','"
                                + cc.actualtime.gettimestring() + "'," + filegrootte_kbytes + ",'" + filename + "','"
                                + type + "','" + cc.actualtime.getyear() + "')";
                        try {
                            cc.jdbcconnection.add_update_batch(cc.strquery);
                        } catch (SQLException e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        if ((!(suffix.equals("acqu") || suffix.equals("acqus"))) && (generatesamplecodes)) {
                            cc.strquery = "update result res set res.sampleid = (select max(sampleid) from sample sa where sa.sample_code = '"
                                    + filename + "') where res.file = '" + filename + "'and res.experimentid = "
                                    + existingexperimentid + ";";
                            try {
                                cc.jdbcconnection.add_update_batch(cc.strquery);
                            } catch (SQLException e) {
                                if (cc.debugmode) {
                                    e.printStackTrace();
                                } else {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                    }
                }
            }
        }
        progress1.setmaximum(1);
        progress1.setnumberandtext(0, "Insert into table Result ...");
        try {
            cc.jdbcconnection.execute_batch();
        } catch (SQLException e) {
        }
        progress1.setnumberandtext(0, "Filetransfer ...");
        boolean transfer = true;
        filetransfer = new FileTransfer(cc);
        cc.actualtime.resettime();
        if (isnewexperiment) {
            filetransfer.putDataFiles(cc.actualtime.getyear(), latestexperimentid.trim(), filevector, transfer,
                    filtertype, progress1);
            cc.selectexperimentAllocation(latestexperimentid.trim());
        } else {
            if (experimentyear == null) {
                experimentyear = cc.actualtime.getyear();
            } else if (experimentyear.trim().equalsIgnoreCase("")) {
                experimentyear = cc.actualtime.getyear();
            }
            filetransfer.putDataFiles(experimentyear, cc.inputexperimentid.getText().trim(), filevector, transfer,
                    filtertype, progress1);
            cc.selectexperimentAllocation(cc.inputexperimentid.getText());
        }
    }
}
