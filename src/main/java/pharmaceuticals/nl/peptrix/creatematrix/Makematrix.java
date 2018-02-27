package pharmaceuticals.nl.peptrix.creatematrix;

import java.io.*;
import java.math.BigDecimal;
import java.util.Vector;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.utils.SortMatrix;
import pharmaceuticals.nl.peptrix.createpeaklist.MassSpectrometryFile;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPTransferType;

public class Makematrix {
    boolean min_and_max_retentiontime_present;
    boolean extra_retention_time_and_ms2_sequencing_data;
    Controller cc;
    ExportFileToDisk exportmatrixheadertodisk;
    ExportFileToDisk exportmatrixcountstodisk;
    ExportFileToDisk exportmatrixintensitytodisk;
    ExportFileToDisk exportmatrixbinarytodisk;
    SortMatrix sortmatrix_ms2;
    BigDecimal tempBD;
    FileInputStream is;
    Vector<String[]> sequensing_results_vector;
    Object[][] odata;
    Object[][] odatanoisepeaks;
    Object[][] odatanoisepeaks2;
    Object[][] odata_lc_ms_offset;
    String[] arraymassesoffile;
    String[] arraytimesoffile;
    String[] arrayintensitiesoffile;
    String[] compare_sequensing_results;
    String[] sequensing_results;
    String[] noisemass;
    String[] listdir;
    String[] arraymintimesoffile;
    String[] arraymaxtimesoffile;
    String[] massline;
    StringBuffer bufferlisminretentiontimesoffile;
    StringBuffer stringbufferdetailinformation;
    StringBuffer bufferlismaxretentiontimesoffile;
    StringBuffer bufferlistintensitiesoffile;
    StringBuffer bufferlistimesoffile;
    StringBuffer bufferlistmassesoffile;
    StringBuffer newsamplecount;
    StringBuffer newsampleintensity;
    StringBuffer linebuffer;
    StringBuffer newsamplebinary;
    StringBuffer noiselinebuffer;
    String stringclusteringtechnique;
    String line;
    String temporary_noise_file;
    String temporary_noise_file3;
    String noiseline = "";
    String stringsampleintensity;
    String stringsamplebinary;
    String listmassesoffile;
    String listintensitiesoffile;
    String test;
    String listtimesoffile;
    String listmintimesoffile;
    String listmaxtimesoffile;
    String fileSeparator;
    String newsampleid;
    String strdeltatimecombine;
    String filenoisepeaks;
    String groupid_old;
    String sampleid_old;
    String stringsamplecount;
    String strpeakfindmethod;
    String matrixcountsfilename;
    String binarymatrixfilename;
    String matrixintensityfilename;
    double[][] doublearraycombinedpeaks;
    double[][] ms2_peaks;
    double sum_ms2_retentiontime;
    double deltamzcombinelocal2;
    double count_ms2;
    double sum_ms2_mass;
    double absolutedifference;
    double doublemass2;
    double doublemass3;
    double deltatimecombine;
    int timeclusteringtechnique;
    byte[] bytesnoise;
    byte[] data;
    byte[] bytesfile;
    boolean filenoisepeaks2present;
    boolean startcreatinglist = true;
    boolean ms2_sequence_already_present;
    boolean firstdata = true;
    boolean filepresent;
    boolean startcreatingnoiselist;
    Experiment experiment;
    CombineMS2peaks combinems2peaks;
    AddPeaksToMatrix addpeakstomatrix;
    CombineNoisePeaks combinenoisepeaks;
    CombineNoisePeaks2 combinenoisepeaks2;
    MassSpectrometryFile masspectrometryfile;
    Matrix matrix;
    int matrixlenght;
    ResultService resultService;

    public Makematrix(Controller cc, Experiment experiment) {
        this.cc = cc;
        resultService = new ResultServiceImpl(cc);
        this.experiment = experiment;
        sequensing_results_vector = null;
        exportmatrixheadertodisk = new ExportFileToDisk(cc);
        exportmatrixcountstodisk = new ExportFileToDisk(cc);
        exportmatrixintensitytodisk = new ExportFileToDisk(cc);
        exportmatrixbinarytodisk = new ExportFileToDisk(cc);
        combinems2peaks = new CombineMS2peaks(experiment);
        addpeakstomatrix = new AddPeaksToMatrix(experiment);
        combinenoisepeaks = new CombineNoisePeaks();
        combinenoisepeaks2 = new CombineNoisePeaks2(cc);
        masspectrometryfile = new MassSpectrometryFile();
    }

    public void makematrix(String str_aligned, String calibtext, Progress progress, FTPClient ftp) {
        min_and_max_retentiontime_present = false;
        extra_retention_time_and_ms2_sequencing_data = false;
        deltatimecombine = experiment.gettime_window_combining_peptide_masses();
        timeclusteringtechnique = experiment.gettime_clustering_absolute_or_percentage();
        if (experiment.getclusteringtechnique() == 1) {
            stringclusteringtechnique = "ppm";
        } else {
            stringclusteringtechnique = "Da";
        }
        strpeakfindmethod = "";
        if (experiment.getpeakfindmethod() == 1) {
            strpeakfindmethod = "SN";
        }
        if (experiment.getpeakfindmethod() == 2) {
            strpeakfindmethod = "q";
        }
        int inputfollowingnumbersample = Integer.parseInt(experiment.getNumberofreplicatessample());
        doublearraycombinedpeaks = experiment.getcombinedpeaks();
        matrixlenght = doublearraycombinedpeaks[0].length;
        matrix = new Matrix(matrixlenght);
        if (experiment.getperform_ms2_sequencing()) {
            if (sequensing_results_vector == null) {
                sequensing_results_vector = new Vector<String[]>();
            }
        }
        sampleid_old = "-1";
        groupid_old = "-1";
        firstdata = true;
        fileSeparator = System.getProperty("file.separator");
        strdeltatimecombine = "";
        if (deltatimecombine > 0) {
            strdeltatimecombine = "t" + String.valueOf(Math.round(deltatimecombine)) + str_aligned + "_";
        }
        matrixcountsfilename = experiment.getfiletype() + "_" + strpeakfindmethod
                + experiment.getquantilethreshold().trim() + "_E" + experiment.getExperimentid() + "_" + calibtext
                + "_sam" + String.valueOf(inputfollowingnumbersample).trim() + "_matrixcounts_" + strdeltatimecombine
                + experiment.getdelta_mz_combine().trim() + stringclusteringtechnique + ".txt";
        matrixintensityfilename = experiment.getfiletype() + "_" + strpeakfindmethod
                + experiment.getquantilethreshold().trim() + "_E" + experiment.getExperimentid() + "_" + calibtext
                + "_sam" + String.valueOf(inputfollowingnumbersample).trim() + "_matrixintensity_" + strdeltatimecombine
                + experiment.getdelta_mz_combine().trim() + stringclusteringtechnique + ".txt";
        binarymatrixfilename = experiment.getfiletype() + "_" + strpeakfindmethod
                + experiment.getquantilethreshold().trim() + "_E" + experiment.getExperimentid() + "_" + calibtext
                + "_sam" + String.valueOf(inputfollowingnumbersample).trim() + "_threshold"
                + experiment.getthresholdbinarymatrix() + "_binarymatrix_" + strdeltatimecombine
                + experiment.getdelta_mz_combine().trim() + stringclusteringtechnique + ".txt";
        experiment.setmatrixcountsfilename(matrixcountsfilename);
        experiment.setmatrixintensityfilename(matrixintensityfilename);
        experiment.setbinarymatrixfilename(binarymatrixfilename);
        try {
            exportmatrixheadertodisk.open_file(cc.userhome, "tmp2_header_" + matrixcountsfilename);
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            cc.jdbcconnection.createcon();
            odata = resultService.getreducedresultrecords(experiment.getExperimentid(),
                    experiment.getquantilethreshold());
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (odata != null) {
            try {
                stringsamplecount = "";
                data = stringsamplecount.getBytes();
                exportmatrixcountstodisk.open_file(cc.userhome, "tmp2_" + matrixcountsfilename);
                stringsampleintensity = "";
                data = stringsampleintensity.getBytes();
                exportmatrixintensitytodisk.open_file(cc.userhome, "tmp2_" + matrixintensityfilename);
                stringsamplebinary = "";
                data = stringsamplebinary.getBytes();
                exportmatrixbinarytodisk.open_file(cc.userhome, "tmp2_" + binarymatrixfilename);
            } catch (Exception e) {
                if (cc.debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            progress.setmaximum(odata.length);
            for (int i = 0; i < odata.length; i++) {
                progress.setnumberandtext(i + 1, "Combine peaks .." + odata[i][4].toString().trim());
                newsampleid = odata[i][0].toString().trim();
                if ((!odata[i][0].toString().trim().equalsIgnoreCase(sampleid_old))
                        || (!odata[i][2].toString().trim().equalsIgnoreCase(groupid_old))) {
                    if (!sampleid_old.equals("-1")) {
                        newsamplecount = new StringBuffer("");
                        newsampleintensity = new StringBuffer("");
                        newsamplebinary = new StringBuffer("");
                        for (int j = 0; j < matrix.getsamplecountlength(); j++) {
                            if ((matrix.getsamplecount(j) > 0) || (matrix.getsamplenoisecount(j) > 0)) {
                                tempBD = new BigDecimal(
                                        (matrix.getsampleintensity(j) + matrix.getsamplenoiseintensity(j))
                                                / (matrix.getsamplecount(j) + matrix.getsamplenoisecount(j)));
                            } else {
                                tempBD = new BigDecimal(
                                        matrix.getsampleintensity(j) + matrix.getsamplenoiseintensity(j));
                            }
                            newsamplecount.append("," + String.valueOf((int) matrix.getsamplecount(j)).trim());
                            if (matrix.getsamplecount(j) >= Double.parseDouble(experiment.getthresholdbinarymatrix())) {
                                newsamplebinary.append(",1");
                            } else {
                                newsamplebinary.append(",0");
                            }
                            newsampleintensity.append("," + String
                                    .valueOf(tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN).doubleValue()).trim());
                            matrix.setsamplecount(j, 0);
                            matrix.setsampleintensity(j, 0);
                            matrix.setsamplenoisecount(j, 0);
                            matrix.setsamplenoiseintensity(j, 0);
                        }
                        if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
                                || (experiment.getequipmentid() == 5)) {
                            if ((sampleid_old != null) && (!sampleid_old.trim().equalsIgnoreCase(""))) {
                                odata_lc_ms_offset = resultService.get_Offset_LC_MS(experiment.getExperimentid(),
                                        experiment.getquantilethreshold(), sampleid_old);
                            }
                            for (int p = 0; p < inputfollowingnumbersample; p++) {
                                test = "0";
                                if (odata_lc_ms_offset != null) {
                                    if (p < odata_lc_ms_offset.length) {
                                        test = odata_lc_ms_offset[p][0].toString();
                                    }
                                }
                                newsamplecount.append("," + test);
                                newsampleintensity.append("," + test);
                                newsamplebinary.append("," + test);
                            }
                        }
                        try {
                            exportmatrixcountstodisk.append_data_to_file(newsamplecount.toString().getBytes());
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        try {
                            exportmatrixintensitytodisk.append_data_to_file(newsampleintensity.toString().getBytes());
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        try {
                            exportmatrixbinarytodisk.append_data_to_file(newsamplebinary.toString().getBytes());
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    sampleid_old = odata[i][0].toString().trim();
                    groupid_old = odata[i][2].toString().trim();
                    if (firstdata) {
                        stringsamplecount = odata[i][0].toString().trim() + "," + odata[i][1].toString().trim() + ","
                                + odata[i][2].toString().trim() + "," + odata[i][3].toString().trim();
                        firstdata = false;
                    } else {
                        stringsamplecount = cc.linefeed + odata[i][0].toString().trim() + ","
                                + odata[i][1].toString().trim() + "," + odata[i][2].toString().trim() + ","
                                + odata[i][3].toString().trim();
                    }
                    try {
                        exportmatrixcountstodisk.append_data_to_file(stringsamplecount.getBytes());
                    } catch (Exception e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    try {
                        exportmatrixintensitytodisk.append_data_to_file(stringsamplecount.getBytes());
                    } catch (Exception e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    try {
                        exportmatrixbinarytodisk.append_data_to_file(stringsamplecount.getBytes());
                    } catch (Exception e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                try {
                    ftp.quit();
                } catch (Exception e) {
                }
                bytesfile = null;
                try {
                    ftp.setRemoteHost(cc.ftpremotehost);
                    ftp.connect();
                    ftp.login(cc.ftpuser, cc.ftppassword);
                    ftp.setConnectMode(FTPConnectMode.PASV);
                    ftp.setTimeout(cc.ftp_longtime);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
                            + experiment.getExperimentid());
                    bytesfile = ftp.get(odata[i][4].toString().trim());
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                filenoisepeaks = "noise_" + odata[i][4].toString().trim();
                odatanoisepeaks = null;
                bytesnoise = null;
                odatanoisepeaks = resultService.get_noise_peaks2(experiment.getExperimentid(),
                        experiment.getquantilethreshold(), odata[i][0].toString().trim(), filenoisepeaks);
                if (odatanoisepeaks != null) {
                    if (odatanoisepeaks.length > 0) {
                        listdir = null;
                        filepresent = false;
                        try {
                            listdir = ftp.dir();
                            for (int j = 0; j < listdir.length; j++) {
                                if (listdir[j].trim().equalsIgnoreCase(filenoisepeaks.trim())) {
                                    filepresent = true;
                                }
                            }
                        } catch (Exception e) {
                            filepresent = false;
                        }
                        if (filepresent) {
                            try {
                                ftp.quit();
                            } catch (Exception e) {
                            }
                            try {
                                ftp.setRemoteHost(cc.ftpremotehost);
                                ftp.connect();
                                ftp.login(cc.ftpuser, cc.ftppassword);
                                ftp.setConnectMode(FTPConnectMode.PASV);
                                ftp.setTimeout(cc.ftp_longtime);
                                ftp.setType(FTPTransferType.BINARY);
                                ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
                                        + experiment.getExperimentid());
                                bytesnoise = ftp.get(filenoisepeaks);
                            } catch (Exception e) {
                                ;
                            }
                        }
                    }
                }
                temporary_noise_file = "tmp_noise.txt";
                filenoisepeaks2present = false;
                filenoisepeaks = "noise2_" + odata[i][4].toString().trim();
                odatanoisepeaks2 = resultService.get_noise_peaks(experiment.getExperimentid(),
                        experiment.getquantilethreshold(), odata[i][0].toString().trim(), filenoisepeaks);
                if (odatanoisepeaks2 != null) {
                    if (odatanoisepeaks2.length > 0) {
                        listdir = null;
                        filenoisepeaks2present = false;
                        try {
                            listdir = ftp.dir();
                            for (int j = 0; j < listdir.length; j++) {
                                if (listdir[j].trim().equalsIgnoreCase(filenoisepeaks.trim())) {
                                    filenoisepeaks2present = true;
                                }
                            }
                        } catch (Exception e) {
                            filenoisepeaks2present = false;
                        }
                        if (filenoisepeaks2present) {
                            try {
                                ftp.quit();
                            } catch (Exception e) {
                            }
                            try {
                                ftp.connect();
                                ftp.login(cc.ftpuser, cc.ftppassword);
                                ftp.setConnectMode(FTPConnectMode.PASV);
                                ftp.setType(FTPTransferType.BINARY);
                                ftp.setTimeout(cc.ftp_longtime);
                                ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
                                        + experiment.getExperimentid());
                                ftp.get(cc.userhome + fileSeparator + temporary_noise_file, filenoisepeaks.trim());
                            } catch (Exception e) {
                                if (cc.debugmode) {
                                    e.printStackTrace();
                                } else {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            try {
                                ftp.quit();
                            } catch (Exception e) {
                            }
                        }
                    }
                }
                temporary_noise_file3 = "tmp_noise3.txt";
                if (bytesfile != null) {
                    line = "";
                    linebuffer = new StringBuffer(line);
                    listmassesoffile = "";
                    listintensitiesoffile = "";
                    listtimesoffile = "";
                    listmintimesoffile = "";
                    listmaxtimesoffile = "";
                    bufferlistmassesoffile = new StringBuffer(listmassesoffile);
                    bufferlistintensitiesoffile = new StringBuffer(listintensitiesoffile);
                    bufferlistimesoffile = new StringBuffer(listtimesoffile);
                    bufferlisminretentiontimesoffile = new StringBuffer(listmintimesoffile);
                    bufferlismaxretentiontimesoffile = new StringBuffer(listmaxtimesoffile);
                    startcreatinglist = true;
                    for (int j = 0; j < bytesfile.length; j++) {
                        if ((bytesfile[j] != 10) && (bytesfile[j] != 13)) {
                            linebuffer.append((char) bytesfile[j]);
                        }
                        if (bytesfile[j] == 10) {
                            line = linebuffer.toString();
                            massline = line.split(" ");
                            if (startcreatinglist == true) {
                                bufferlistmassesoffile.append(massline[0].trim());
                                bufferlistintensitiesoffile.append(massline[1].trim());
                                if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
                                        || (experiment.getequipmentid() == 5)) {
                                    bufferlistimesoffile.append(massline[2].trim());
                                    if ((experiment.getequipmentid() == 4) && (massline.length > 4)) {
                                        extra_retention_time_and_ms2_sequencing_data = true;
                                        if (massline[3].trim().equalsIgnoreCase("")) {
                                            bufferlisminretentiontimesoffile.append("-1");
                                        } else {
                                            bufferlisminretentiontimesoffile.append(massline[3].trim());
                                        }
                                        if (massline[4].trim().equalsIgnoreCase("")) {
                                            bufferlismaxretentiontimesoffile.append("-1");
                                        } else {
                                            bufferlismaxretentiontimesoffile.append(massline[4].trim());
                                        }
                                        if (experiment.getperform_ms2_sequencing()) {
                                            if (massline.length == 6) {
                                                sequensing_results = new String[6];
                                                sequensing_results[0] = massline[0].trim();
                                                sequensing_results[1] = massline[2].trim();
                                                if (massline[3].trim().equalsIgnoreCase("")) {
                                                    sequensing_results[2] = "-1";
                                                } else {
                                                    sequensing_results[2] = massline[3].trim();
                                                }
                                                if (massline[4].trim().equalsIgnoreCase("")) {
                                                    sequensing_results[3] = "-1";
                                                } else {
                                                    sequensing_results[3] = massline[4].trim();
                                                }
                                                sequensing_results[4] = massline[5].trim();
                                                sequensing_results[5] = "1";
                                                try {
                                                    count_ms2 = 1;
                                                    sum_ms2_mass = Double.parseDouble(massline[0].trim());
                                                    sum_ms2_retentiontime = Double.parseDouble(massline[2].trim());
                                                    ms2_sequence_already_present = false;
                                                    for (int v = 0; v < sequensing_results_vector.size(); v++) {
                                                        compare_sequensing_results = sequensing_results_vector
                                                                .elementAt(v);
                                                        if (sequensing_results[4].trim().equalsIgnoreCase(
                                                                compare_sequensing_results[4].trim())) {
                                                            count_ms2 = count_ms2
                                                                    + Double.parseDouble(compare_sequensing_results[5]);
                                                            sum_ms2_mass = sum_ms2_mass
                                                                    + (Double.parseDouble(compare_sequensing_results[5])
                                                                    * Double.parseDouble(
                                                                    compare_sequensing_results[0]));
                                                            sum_ms2_retentiontime = sum_ms2_retentiontime
                                                                    + (Double.parseDouble(compare_sequensing_results[5])
                                                                    * Double.parseDouble(
                                                                    compare_sequensing_results[1]));
                                                            if (Double
                                                                    .parseDouble(compare_sequensing_results[2]) < Double
                                                                    .parseDouble(sequensing_results[2])) {
                                                                sequensing_results[2] = compare_sequensing_results[2];
                                                            }
                                                            if (Double
                                                                    .parseDouble(compare_sequensing_results[3]) > Double
                                                                    .parseDouble(sequensing_results[3])) {
                                                                sequensing_results[3] = compare_sequensing_results[3];
                                                            }
                                                            ms2_sequence_already_present = true;
                                                            sequensing_results_vector.removeElementAt(v);
                                                        }
                                                    }
                                                    if (ms2_sequence_already_present) {
                                                        sequensing_results[0] = String
                                                                .valueOf((sum_ms2_mass / count_ms2));
                                                        sequensing_results[1] = String
                                                                .valueOf((sum_ms2_retentiontime / count_ms2));
                                                        sequensing_results[5] = String.valueOf(count_ms2);
                                                    }
                                                } catch (Exception exc_ms2) {
                                                }
                                                sequensing_results_vector.add(sequensing_results);
                                            }
                                        }
                                    }
                                }
                                startcreatinglist = false;
                            } else {
                                bufferlistmassesoffile.append("," + massline[0].trim());
                                bufferlistintensitiesoffile.append("," + massline[1].trim());
                                if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
                                        || (experiment.getequipmentid() == 5)) {
                                    bufferlistimesoffile.append("," + massline[2].trim());
                                    if ((experiment.getequipmentid() == 4) && (massline.length > 4)) {
                                        extra_retention_time_and_ms2_sequencing_data = true;
                                        if (massline[3].trim().equalsIgnoreCase("")) {
                                            bufferlisminretentiontimesoffile.append("," + "-1");
                                        } else {
                                            bufferlisminretentiontimesoffile.append("," + massline[3].trim());
                                        }
                                        if (massline[4].trim().equalsIgnoreCase("")) {
                                            bufferlismaxretentiontimesoffile.append("," + "-1");
                                        } else {
                                            bufferlismaxretentiontimesoffile.append("," + massline[4].trim());
                                        }
                                        if (experiment.getperform_ms2_sequencing()) {
                                            if (massline.length == 6) {
                                                sequensing_results = new String[6];
                                                sequensing_results[0] = massline[0].trim();
                                                sequensing_results[1] = massline[2].trim();
                                                if (massline[3].trim().equalsIgnoreCase("")) {
                                                    sequensing_results[2] = "-1";
                                                } else {
                                                    sequensing_results[2] = massline[3].trim();
                                                }
                                                if (massline[4].trim().equalsIgnoreCase("")) {
                                                    sequensing_results[3] = "-1";
                                                } else {
                                                    sequensing_results[3] = massline[4].trim();
                                                }
                                                sequensing_results[4] = massline[5].trim();
                                                sequensing_results[5] = "1";
                                                try {
                                                    count_ms2 = 1;
                                                    sum_ms2_mass = Double.parseDouble(massline[0].trim());
                                                    sum_ms2_retentiontime = Double.parseDouble(massline[2].trim());
                                                    ms2_sequence_already_present = false;
                                                    for (int v = 0; v < sequensing_results_vector.size(); v++) {
                                                        compare_sequensing_results = sequensing_results_vector
                                                                .elementAt(v);
                                                        if (sequensing_results[4].trim().equalsIgnoreCase(
                                                                compare_sequensing_results[4].trim())) {
                                                            doublemass2 = Double.parseDouble(sequensing_results[0]);
                                                            doublemass3 = Double
                                                                    .parseDouble(compare_sequensing_results[0]);
                                                            deltamzcombinelocal2 = Double
                                                                    .parseDouble(experiment.getdelta_mz_combine());
                                                            if (experiment.getclusteringtechnique() == 1) {
                                                                deltamzcombinelocal2 = Double
                                                                        .parseDouble(experiment.getdelta_mz_combine())
                                                                        * doublemass2 / 1000000;
                                                            }
                                                            absolutedifference = Math.abs(doublemass2 - doublemass3);
                                                            if (absolutedifference < deltamzcombinelocal2) {
                                                                count_ms2 = count_ms2 + Double
                                                                        .parseDouble(compare_sequensing_results[5]);
                                                                sum_ms2_mass = sum_ms2_mass + (Double
                                                                        .parseDouble(compare_sequensing_results[5])
                                                                        * Double.parseDouble(
                                                                        compare_sequensing_results[0]));
                                                                sum_ms2_retentiontime = sum_ms2_retentiontime + (Double
                                                                        .parseDouble(compare_sequensing_results[5])
                                                                        * Double.parseDouble(
                                                                        compare_sequensing_results[1]));
                                                                if (Double.parseDouble(
                                                                        compare_sequensing_results[2]) < Double
                                                                        .parseDouble(sequensing_results[2])) {
                                                                    sequensing_results[2] = compare_sequensing_results[2];
                                                                }
                                                                if (Double.parseDouble(
                                                                        compare_sequensing_results[3]) > Double
                                                                        .parseDouble(sequensing_results[3])) {
                                                                    sequensing_results[3] = compare_sequensing_results[3];
                                                                }
                                                                ms2_sequence_already_present = true;
                                                                sequensing_results_vector.removeElementAt(v);
                                                            }
                                                        }
                                                    }
                                                    if (ms2_sequence_already_present) {
                                                        sequensing_results[0] = String
                                                                .valueOf((sum_ms2_mass / count_ms2));
                                                        sequensing_results[1] = String
                                                                .valueOf((sum_ms2_retentiontime / count_ms2));
                                                        sequensing_results[5] = String.valueOf(count_ms2);
                                                    }
                                                } catch (Exception exc_ms2) {
                                                }
                                                sequensing_results_vector.add(sequensing_results);
                                            }
                                        }
                                    }
                                }
                            }
                            line = "";
                            linebuffer = new StringBuffer(line);
                        }
                    }
                    listmassesoffile = bufferlistmassesoffile.toString();
                    listintensitiesoffile = bufferlistintensitiesoffile.toString();
                    listtimesoffile = bufferlistimesoffile.toString();
                    arraymassesoffile = listmassesoffile.split(",");
                    arrayintensitiesoffile = listintensitiesoffile.split(",");
                    arraytimesoffile = listtimesoffile.split(",");
                    listmintimesoffile = bufferlisminretentiontimesoffile.toString();
                    listmaxtimesoffile = bufferlismaxretentiontimesoffile.toString();
                    arraymintimesoffile = listmintimesoffile.split(",");
                    arraymaxtimesoffile = listmaxtimesoffile.split(",");
                    for (int j = 0; j < doublearraycombinedpeaks[0].length; j++) {
                        matrix.setboolean_addnoisepeaks(j, 1);
                    }
                    masspectrometryfile.setarraymassesoffile(arraymassesoffile);
                    masspectrometryfile.setarrayintensitiesoffile(arrayintensitiesoffile);
                    masspectrometryfile.setarraymintimesoffile(arraymintimesoffile);
                    masspectrometryfile.setarraymaxtimesoffile(arraymaxtimesoffile);
                    masspectrometryfile.setarraytimesoffile(arraytimesoffile);
                    addpeakstomatrix.combinepeaks(doublearraycombinedpeaks,
                            extra_retention_time_and_ms2_sequencing_data, masspectrometryfile, matrix);
                    if (odatanoisepeaks != null) {
                        if (odatanoisepeaks.length > 0) {
                            if (bytesnoise != null) {
                                noiseline = "";
                                noiselinebuffer = new StringBuffer(noiseline);
                                listmassesoffile = "";
                                listintensitiesoffile = "";
                                listtimesoffile = "";
                                listmintimesoffile = "";
                                listmaxtimesoffile = "";
                                bufferlistmassesoffile = new StringBuffer(listmassesoffile);
                                bufferlistintensitiesoffile = new StringBuffer(listintensitiesoffile);
                                bufferlistimesoffile = new StringBuffer(listtimesoffile);
                                bufferlisminretentiontimesoffile = new StringBuffer(listmintimesoffile);
                                bufferlismaxretentiontimesoffile = new StringBuffer(listmaxtimesoffile);
                                startcreatingnoiselist = true;
                                for (int j = 0; j < bytesnoise.length; j++) {
                                    if ((bytesnoise[j] != 10) && (bytesnoise[j] != 13)) {
                                        noiselinebuffer.append((char) bytesnoise[j]);
                                    }
                                    if (bytesnoise[j] == 10) {
                                        noiseline = noiselinebuffer.toString();
                                        noisemass = noiseline.split(" ");
                                        if (startcreatingnoiselist == true) {
                                            bufferlistmassesoffile.append(noisemass[0].trim());
                                            bufferlistintensitiesoffile.append(noisemass[1].trim());
                                            if (experiment.getequipmentid() == 4) {
                                                bufferlistimesoffile.append(noisemass[2].trim());
                                                bufferlisminretentiontimesoffile.append(noisemass[3].trim());
                                                bufferlismaxretentiontimesoffile.append(noisemass[4].trim());
                                            }
                                            startcreatingnoiselist = false;
                                        } else {
                                            bufferlistmassesoffile.append("," + noisemass[0].trim());
                                            bufferlistintensitiesoffile.append("," + noisemass[1].trim());
                                            if (experiment.getequipmentid() == 4) {
                                                bufferlistimesoffile.append("," + noisemass[2].trim());
                                                bufferlisminretentiontimesoffile.append("," + noisemass[3].trim());
                                                bufferlismaxretentiontimesoffile.append("," + noisemass[4].trim());
                                            }
                                        }
                                        noiseline = "";
                                        noiselinebuffer = new StringBuffer(noiseline);
                                    }
                                }
                                listmassesoffile = bufferlistmassesoffile.toString();
                                listintensitiesoffile = bufferlistintensitiesoffile.toString();
                                arraymassesoffile = listmassesoffile.split(",");
                                arrayintensitiesoffile = listintensitiesoffile.split(",");
                                if (experiment.getequipmentid() == 4) {
                                    listtimesoffile = bufferlistimesoffile.toString();
                                    arraytimesoffile = listtimesoffile.split(",");
                                    listmintimesoffile = bufferlisminretentiontimesoffile.toString();
                                    arraymintimesoffile = listmintimesoffile.split(",");
                                    listmaxtimesoffile = bufferlismaxretentiontimesoffile.toString();
                                    arraymaxtimesoffile = listmaxtimesoffile.split(",");
                                }
                                if (noisemass.length == 5) {
                                    min_and_max_retentiontime_present = true;
                                } else {
                                    min_and_max_retentiontime_present = false;
                                }
                                masspectrometryfile.setarraymassesoffile(arraymassesoffile);
                                masspectrometryfile.setarrayintensitiesoffile(arrayintensitiesoffile);
                                masspectrometryfile.setarraymintimesoffile(arraymintimesoffile);
                                masspectrometryfile.setarraymaxtimesoffile(arraymaxtimesoffile);
                                masspectrometryfile.setarraytimesoffile(arraytimesoffile);
                                combinenoisepeaks.combinenoisepeaks(doublearraycombinedpeaks, experiment, matrix,
                                        masspectrometryfile, min_and_max_retentiontime_present);
                            }
                        }
                        if (odatanoisepeaks2 != null) {
                            if (odatanoisepeaks2.length > 0) {
                                if (filenoisepeaks2present) {
                                    combinenoisepeaks2.combinenoisepeaks2(temporary_noise_file, experiment,
                                            doublearraycombinedpeaks, matrix);
                                }
                            }
                        }
                    }
                }
            }
            if (odata.length > 0) {
                newsamplecount = new StringBuffer("");
                newsampleintensity = new StringBuffer("");
                newsamplebinary = new StringBuffer("");
                for (int j = 0; j < matrix.getsamplecountlength(); j++) {
                    newsamplecount.append("," + String.valueOf((int) matrix.getsamplecount(j)).trim());
                    if ((matrix.getsamplecount(j) > 0) || (matrix.getsamplenoisecount(j) > 0)) {
                        tempBD = new BigDecimal((matrix.getsampleintensity(j) + matrix.getsamplenoiseintensity(j))
                                / (matrix.getsamplecount(j) + matrix.getsamplenoisecount(j)));
                    } else {
                        tempBD = new BigDecimal(matrix.getsampleintensity(j) + matrix.getsamplenoiseintensity(j));
                    }
                    newsampleintensity.append(
                            "," + String.valueOf(tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN).doubleValue()).trim());
                    if (matrix.getsamplecount(j) >= Double.parseDouble(experiment.getthresholdbinarymatrix())) {
                        newsamplebinary.append(",1");
                    } else {
                        newsamplebinary.append(",0");
                    }
                    matrix.setsamplecount(j, 0);
                    matrix.setsampleintensity(j, 0);
                    matrix.setsamplenoisecount(j, 0);
                    matrix.setsamplenoiseintensity(j, 0);
                }
                if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
                        || (experiment.getequipmentid() == 5)) {
                    if ((newsampleid != null) && (!newsampleid.trim().equalsIgnoreCase(""))) {
                        odata_lc_ms_offset = null;
                        odata_lc_ms_offset = resultService.get_offset_lcms(experiment.getExperimentid(),
                                experiment.getquantilethreshold(), newsampleid);
                    }
                    for (int p = 0; p < inputfollowingnumbersample; p++) {
                        test = "0";
                        if (odata_lc_ms_offset != null) {
                            if (p < odata_lc_ms_offset.length) {
                                test = odata_lc_ms_offset[p][0].toString();
                            }
                        }
                        newsamplecount.append("," + test);
                        newsampleintensity.append("," + test);
                        newsamplebinary.append("," + test);
                    }
                }
                try {
                    exportmatrixcountstodisk.append_data_to_file(newsamplecount.toString().getBytes());
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    exportmatrixintensitytodisk.append_data_to_file(newsampleintensity.toString().getBytes());
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    exportmatrixbinarytodisk.append_data_to_file(newsamplebinary.toString().getBytes());
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (experiment.getperform_ms2_sequencing()) {
                    if (sequensing_results_vector != null) {
                        if (sequensing_results_vector.size() > 0) {
                            ms2_peaks = new double[5][sequensing_results_vector.size()];
                            for (int v = 0; v < sequensing_results_vector.size(); v++) {
                                sequensing_results = sequensing_results_vector.elementAt(v);
                                ms2_peaks[0][v] = Double.parseDouble(sequensing_results[0]);
                                ms2_peaks[1][v] = Double.parseDouble(sequensing_results[1]);
                                ms2_peaks[2][v] = Double.parseDouble(sequensing_results[2]);
                                ms2_peaks[3][v] = Double.parseDouble(sequensing_results[3]);
                                ms2_peaks[4][v] = v;
                            }
                            sortmatrix_ms2 = new SortMatrix(ms2_peaks, 0);
                            stringsamplecount = combinems2peaks.combine_ms2_peaks(doublearraycombinedpeaks, ms2_peaks,
                                    sequensing_results_vector);
                            if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
                                    || (experiment.getequipmentid() == 5)) {
                                for (int p = 0; p < inputfollowingnumbersample; p++) {
                                    stringsamplecount = stringsamplecount + ", ";
                                }
                            }
                            try {
                                exportmatrixcountstodisk.append_data_to_file(stringsamplecount.getBytes());
                            } catch (Exception e) {
                                if (cc.debugmode) {
                                    e.printStackTrace();
                                } else {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            try {
                                exportmatrixintensitytodisk.append_data_to_file(stringsamplecount.getBytes());
                            } catch (Exception e) {
                                if (cc.debugmode) {
                                    e.printStackTrace();
                                } else {
                                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }
                            try {
                                exportmatrixbinarytodisk.append_data_to_file(stringsamplecount.getBytes());
                            } catch (Exception e) {
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
                try {
                    exportmatrixheadertodisk.append_data_to_file("sampleid,samplecode,groupid,groupcode".getBytes());
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                for (int i = 0; i < doublearraycombinedpeaks[0].length; i++) {
                    if (matrix.gettotalcount(i) < 1) {
                        matrix.settotalcount(i, 1);
                    }
                    tempBD = new BigDecimal((matrix.getaccumulatemass(i) / matrix.gettotalcount(i)));
                    try {
                        exportmatrixheadertodisk.append_data_to_file((","
                                + String.valueOf(tempBD.setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue()).trim())
                                .getBytes());
                    } catch (Exception e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
                        || (experiment.getequipmentid() == 5)) {
                    for (int p = 0; p < inputfollowingnumbersample; p++) {
                        try {
                            exportmatrixheadertodisk.append_data_to_file((",0." + String.valueOf(p + 1)).getBytes());
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    try {
                        exportmatrixheadertodisk.append_data_to_file(cc.linefeed.getBytes());
                        exportmatrixheadertodisk.append_data_to_file(",,,time".getBytes());
                    } catch (Exception e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    for (int i = 0; i < doublearraycombinedpeaks[0].length; i++) {
                        tempBD = new BigDecimal(doublearraycombinedpeaks[1][i]);
                        try {
                            exportmatrixheadertodisk.append_data_to_file(
                                    ("," + String.valueOf(tempBD.setScale(4, BigDecimal.ROUND_HALF_EVEN).doubleValue())
                                            .trim()).getBytes());
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    for (int p = 0; p < inputfollowingnumbersample; p++) {
                        try {
                            exportmatrixheadertodisk.append_data_to_file((",0." + String.valueOf(p + 1)).getBytes());
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                try {
                    exportmatrixheadertodisk.append_data_to_file(cc.linefeed.getBytes());
                    exportmatrixheadertodisk.close_file();
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    ftp.quit();
                } catch (Exception e) {
                }
                try {
                    ftp.setRemoteHost(cc.ftpremotehost);
                    ftp.connect();
                    ftp.login(cc.ftpuser, cc.ftppassword);
                    ftp.setConnectMode(FTPConnectMode.PASV);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
                            + experiment.getExperimentid());
                } catch (Exception e) {
                }
                try {
                    exportmatrixcountstodisk.close_file();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    ftp.setTimeout(cc.ftp_longtime);
                    fileSeparator = System.getProperty("file.separator");
                    progress.settext("Migrate .." + matrixcountsfilename.trim());
                    is = new FileInputStream(cc.userhome + fileSeparator + "tmp2_header_" + matrixcountsfilename);
                    ftp.put(is, matrixcountsfilename, false);
                    is.close();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (FTPException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    is = new FileInputStream(cc.userhome + fileSeparator + "tmp2_" + matrixcountsfilename);
                    ftp.put(is, matrixcountsfilename, true);
                    ftp.put(cc.linefeed.getBytes(), matrixcountsfilename, true);
                    is.close();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (FTPException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    ftp.quit();
                } catch (Exception e) {
                }
                try {
                    ftp.setRemoteHost(cc.ftpremotehost);
                    ftp.connect();
                    ftp.login(cc.ftpuser, cc.ftppassword);
                    ftp.setConnectMode(FTPConnectMode.PASV);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
                            + experiment.getExperimentid());
                } catch (Exception e) {
                }
                try {
                    exportmatrixintensitytodisk.close_file();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    ftp.setTimeout(cc.ftp_longtime);
                    fileSeparator = System.getProperty("file.separator");
                    progress.settext("Migrate .." + matrixintensityfilename.trim());
                    is = new FileInputStream(cc.userhome + fileSeparator + "tmp2_header_" + matrixcountsfilename);
                    ftp.put(is, matrixintensityfilename, false);
                    is.close();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (FTPException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    is = new FileInputStream(cc.userhome + fileSeparator + "tmp2_" + matrixintensityfilename);
                    ftp.put(is, matrixintensityfilename, true);
                    ftp.put(cc.linefeed.getBytes(), matrixintensityfilename, true);
                    is.close();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (FTPException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    ftp.quit();
                } catch (Exception e) {
                }
                try {
                    ftp.setRemoteHost(cc.ftpremotehost);
                    ftp.connect();
                    ftp.login(cc.ftpuser, cc.ftppassword);
                    ftp.setConnectMode(FTPConnectMode.PASV);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
                            + experiment.getExperimentid());
                } catch (Exception e) {
                }
                try {
                    exportmatrixbinarytodisk.close_file();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    ftp.setTimeout(cc.ftp_longtime);
                    fileSeparator = System.getProperty("file.separator");
                    progress.settext("Migrate .." + binarymatrixfilename.trim());
                    is = new FileInputStream(cc.userhome + fileSeparator + "tmp2_header_" + matrixcountsfilename);
                    ftp.put(is, binarymatrixfilename, false);
                    is.close();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (FTPException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    is = new FileInputStream(cc.userhome + fileSeparator + "tmp2_" + binarymatrixfilename);
                    ftp.put(is, binarymatrixfilename, true);
                    ftp.put(cc.linefeed.getBytes(), binarymatrixfilename, true);
                    is.close();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (FTPException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                data = String.valueOf("").getBytes();
                try {
                    ftp.quit();
                } catch (Exception e) {
                }
                try {
                    ftp.setRemoteHost(cc.ftpremotehost);
                    ftp.connect();
                    ftp.login(cc.ftpuser, cc.ftppassword);
                    ftp.setConnectMode(FTPConnectMode.PASV);
                    ftp.setTimeout(cc.ftp_longtime);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
                            + experiment.getExperimentid());
                    ftp.put(data, matrixcountsfilename, false);
                    ftp.put(data, binarymatrixfilename, false);
                    ftp.put(data, matrixintensityfilename, false);
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }
}
