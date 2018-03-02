package pharmaceuticals.nl.peptrix.createpeaklist;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import javax.swing.*;

import com.enterprisedt.net.ftp.*;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.utils.FastFourierTransform;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.utils.SortArray;
import pharmaceuticals.nl.peptrix.utils.SortMatrix;
import pharmaceuticals.nl.peptrix.calibration.Calibrationlinear;
import pharmaceuticals.nl.peptrix.calibration.Calibrationquadratic;
import pharmaceuticals.nl.peptrix.creatematrix.Combinepeaks;
import pharmaceuticals.nl.peptrix.service.ExperimentService;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.service.SystemCodeItemService;
import pharmaceuticals.nl.peptrix.serviceimpl.ExperimentServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.SystemCodeItemServiceImpl;

public class Readdatafiles extends DefaultHandler {
    int countftp;
    double referencemass2;
    int countdown;
    File parsefile;
    Monoisotoopfinding monoisotoopfinding;
    Thermo_Scientific thermoscientific;
    ReadmzXML readmzxml;
    SAXParserFactory spf;
    SAXParser sp;
    FastFourierTransform fft;
    Spectrum spectrum;
    File file;
    FileInputStream is;
    FTMSdata ftmsdata;
    IdentifyPeaks identifypeaks;
    SortMatrix sortmatrix;
    SortArray sortarray;
    Calibrationquadratic calibrationquadratic;
    Calibrationlinear calibrationlinear;
    Controller cc;
    FileOutputStream os;
    FileOutputStream timealignmentreferencemass;
    FileOutputStream timealignmentreferenceretentiontime;
    FileOutputStream timealignmentreferencesequence;
    FileInputStream inputalignmentreferencemass;
    FileInputStream inputalignmentreferenceretentiontime;
    FileInputStream inputalignmentreferencesequence;
    FileInputStream inputpeaklist;
    String nametimealignmentreferencemass;
    String nametimealignmentreferenceretentiontime;
    String nametimealignmentreferencesequence;
    FileDescriptor fd;
    Combinepeaks combinepeaks;
    FTPClient ftp = null;
    FTPMessageCollector listener;
    Simplepeakfind simplepeakfind;
    BigDecimal tempBD;
    GregorianCalendar gc;
    Object[][] odatacalmasses;
    Object[][] odatafiles;
    Object[][] odataequipmentid;
    Object[][] objectcalmasses;
    String[] linearray;
    String[] arrayalignmentmasses;
    String[] arrayretentiontimes;
    String[] arraysequences;
    String[] alignment_time;
    String[] arrayofremovemassesindices;
    String teststring_header = "abcdfghijklmnopqrstuvwxyzABCDFGHIJKLMNOPQRSTUVWXYZ~`!@#$%^&*()_=\\\"?/<>[]{}:;";
    String teststring_separated = "0123456789.E-+e";
    String line;
    String searchstring;
    String systemcodeitemid;
    String calmassesquery;
    String numberofmeasurements;
    String acquisname;
    String filenameexport;
    String exportname;
    String exportstring;
    String linefeed = "\n";
    String fid = "fid";
    String acqus = "acqus";
    String query;
    String experimentnumber;
    String strdatum;
    String stryear = "";
    public String calibtext;
    String strtime;
    String strquantilethreshold;
    String groupcode;
    String samplecode;
    String matrixcounts;
    String matrixcountsfilename;
    String matrixintensityfilename;
    String matrixintensitystring;
    String binarymatrixfilename;
    String binarymatrixstring;
    String inputtype;
    String experimentyear;
    String headerline;
    String tmp_filename;
    String str_aligned;
    String tmp_filenameexport;
    String localmzxmlfilename;
    String save_exportname;
    String strdeltamzsearchmaximum;
    String strinputminimummass;
    String strinputmaximummass;
    String strthresholdbinarymatrix;
    String strdeltamzsearchcalibrants;
    StringBuffer linebuffer;
    StringBuffer headerbuffer;
    StringBuffer timealignmentbuffermassnew;
    StringBuffer timealignmentbuffersequencenew;
    StringBuffer timealignmentbufferretentiontimenew;
    StringBuffer removemassesindices;
    StringBuffer removemassesindices2;
    double quantilethreshold;
    double deltamzsearchmaximum;
    double deltamzsearchcalibrants;
    double deltamzsearchcalibrantslocal;
    double c0;
    double c1;
    double c2;
    double delay;
    double timebase;
    double time;
    double C;
    double B;
    double A;
    double square_root_mass;
    double reciprocalmass;
    double mass;
    double dummy1;
    double dummy2;
    double sweep;
    double aquisitiontime;
    double frequencyresolution;
    double frequency;
    double centralfrequency;
    double lowmass;
    double highfrequency;
    double filegrootte_kbytes;
    double deltamzcombine;
    double minimummass;
    double maximummass;
    double thresholdbinarymatrix;
    double LorentzfactorA;
    double GaussfactorB;
    double signal_to_noise;
    double multiply;
    double progress;
    double c13_c12;
    double percent_stdevisotopingdistance;
    double deltaxcentroiding;
    double limitshiftcentroiding;
    double intensitypreviousisotope = 0;
    double mean2;
    double deltatimecombine;
    double referencemass;
    double referenceretentiontime;
    double deltamzlocal;
    double deltatimelocalcombine;
    double save_ms2_index;
    double save_mass;
    double save_retentiontime;
    double save_min_retentiontime;
    double save_max_retentiontime;
    double save_intensity;
    double sqr_difference_time_alignment;
    double mean_difference_time_alignment;
    double standard_deviation_time_alignment;
    double max_time_difference;
    double sum_distances;
    double inputerror;
    double distance;
    double sum_square_distances;
    double distance_count;
    double variance_distances;
    double standard_deviation_distance;
    double average_distances;
    int ndiv_timealignment;
    double sum_time;
    double sum_difference_time;
    double sum_data_points;
    double ms2_mass;
    int count_windows;
    double[] newcalibrationmasses2;
    double[] intensity;
    double[] calibrationchannel2;
    double[] reciprocalcalibrationchannel2;
    double[] frequencyraw;
    double[] frequencyraw2;
    double[] reciprocalcalibrationmasses2;
    double[] a;
    double[] calibrationmasses;
    double[] calibrationmassesintensity;
    double[] newcalibrationmassesraw;
    double[] calibrationchannelraw;
    double[] reciprocalfrequencyraw;
    double[] reciprocalfrequencyraw2;
    double[] calibrationtimeraw;
    double[] fftMag;
    double[] xim;
    double[] delta_time;
    double[] calibration_time;
    double[] average_time;
    double[] average_differene_time;
    double[] calibration_delta_time;
    double[] inputgradientchange;
    double[] low_time_alignment;
    double[] high_time_alignment;
    double[] a0_time_alignment;
    double[] a1_time_alignment;
    double[] a2_time_alignment;
    double[][] calibration_time_new;
    double[][] peaks;
    double[][] noisepeaks;
    double[][] peakstransposed;
    double[][] retentiontimes;
    double[][] doublearraycombinedpeaks;
    double[][] cleanmasses;
    double[] used_ms2_mass;
    int n_time_alignment;
    int timeclusteringtechnique;
    int intnumberofmeasurements;
    int intnumberofmeasurements_old = 0;
    int detect_mode;
    int equipmentid;
    int numbermisfitallowed;
    int numberoffits;
    int threshold_noisy_spectra;
    int teller = 0;
    int chanelintensity;
    int shiftby;
    int day;
    int month;
    int year;
    int hour;
    int min;
    int sec;
    int updatesample;
    int inputfollowingnumbersample;
    int count;
    public int numberofmasses;
    int zerofillingfactor;
    int peakfindmethod;
    int sortrow;
    int clusteringtechnique;
    int int_filegrootte;
    int ndiv;
    int centroidingmethod;
    int numberoflines;
    int numberpointstimealignment;
    int filenumber;
    int chargestate = 1;
    int ch;
    int ch2;
    int ch3;
    int max_charge_state;
    int minimumnumberoffractions;
    int missingfractionsallowed;
    int low_value;
    int intnumberofmeasurementsraw;
    int q;
    int countdown2;
    int peaksbuffer;
    boolean first;
    boolean calibrated = false;
    boolean filetransported = false;
    boolean internalcalibration = false;
    boolean matrixcountsfiletransported = false;
    boolean matrixintensityfiletransported = false;
    boolean matrixbinaryfiletransported = false;
    boolean set_separator;
    boolean add_to_peak_list = true;
    boolean performApodization = true;
    boolean filetype_canbe_processed;
    boolean header;
    boolean zerofile;
    boolean add_mass;
    boolean monoistopefinding;
    boolean performtimealgnment;
    boolean alignmentmassfound;
    boolean in_time_window;
    boolean in_time_window2;
    byte[] acquis;
    byte[] data;
    byte[] bytesfile;
    String string_apodization_method;
    double remove_double_mass_window;
    double retentiontime_distance_factor;
    double mass_distance_factor;
    int save_i;
    String inputnoisepeaks;
    String resultrecordtype;
    FileOutputStream fileexportbuffer;
    BufferedReader input_rejectedpeaks;
    String TOL;
    String TOLU;
    String ITOL;
    String ITOLU;
    boolean perform_ms2_sequencing;
    String string_search_engine;
    String string_Taxonomy;
    Vector<String[]> sequensing_results_vector;
    String[] sequensing_results;
    boolean ms2_sequencenced_masses_present;
    double double_ms2_retentiontime_min;
    double double_ms2_retentiontime_max;
    boolean ms2_within_time_window;
    String sequencing_results_string;
    boolean firsttimereferenceexport;
    String strnumbermisfitallowed;
    String strdeltamzcombine;
    Experiment experiment;
    Progress progressnew;
    MassSpectrometryFile massspectrometryfile;
    ExportFileToDisk exportfiletodisk;
    Filetypes filetypes;
    SystemCodeItemService systemCodeItemService;
    ExperimentService experimentService;
    ResultService resultservice;

    public Readdatafiles(Controller cc, ExportFileToDisk exportfiletodisk, Experiment experiment) {
        this.experiment = experiment;
        this.cc = cc;
        systemCodeItemService = new SystemCodeItemServiceImpl(cc);
        experimentService = new ExperimentServiceImpl(cc);
        resultservice = new ResultServiceImpl(cc);
        this.exportfiletodisk = exportfiletodisk;
        calibrationquadratic = new Calibrationquadratic(cc);
        calibrationlinear = new Calibrationlinear(cc);
        intnumberofmeasurements_old = 0;
        filetypes = new Filetypes();
    }

    public void creatematrix() {
        deltaxcentroiding = experiment.getwindow_centroiding_ppm();
        limitshiftcentroiding = experiment.getmaximal_shift_by_centroiding_ppm();
        timeclusteringtechnique = experiment.gettime_clustering_absolute_or_percentage();
        centroidingmethod = experiment.getmethod_peak_centroiding();
        inputgradientchange = experiment.getchange_in_gradient_after_time();
        monoistopefinding = experiment.getperform_deisotoping();
        string_apodization_method = experiment.getFT_ICR_apodization_method();
        deltatimecombine = experiment.gettime_window_combining_peptide_masses();
        percent_stdevisotopingdistance = experiment.getvariance_isotopic_distance();
        inputerror = experiment.getdeviation_from_expected_intensity_ratio();
        max_charge_state = experiment.getmax_charge_state_peptide();
        zerofillingfactor = experiment.getzerofillingfactor();
        minimumnumberoffractions = experiment.getpeptide_present_in_minimumnumberoffractions();
        missingfractionsallowed = experiment.getmissing_number_ms_scans_allowed();
        performtimealgnment = experiment.getperform_time_alignment();
        numberpointstimealignment = experiment.getminimum_points_required_for_time_alignment();
        GaussfactorB = experiment.getGaussfactorB();
        LorentzfactorA = experiment.getLorentzfactorA();
        string_search_engine = experiment.getname_search_engine();
        TOL = experiment.getTOL();
        TOLU = experiment.getTOLU();
        ITOL = experiment.getITOL();
        ITOLU = experiment.getITOLU();
        perform_ms2_sequencing = experiment.getperform_ms2_sequencing();
        string_Taxonomy = experiment.gettaxonomy_name();
        c13_c12 = experiment.getisotopic_distance_c13_c12();
        ndiv = experiment.getdivisions_in_determination_noise();
        strdeltamzsearchcalibrants = experiment.getdelta_mz_search_calibrants();
        String systemcodeitemid = String.valueOf(experiment.getid_calibrationmasses());
        String equipmentid2 = String.valueOf(experiment.getequipmentid());
        sortarray = new SortArray(this.inputgradientchange);
        filenumber = 0;
        str_aligned = "";
        timealignmentbuffermassnew = null;
        timealignmentbuffersequencenew = null;
        timealignmentbufferretentiontimenew = null;
        clusteringtechnique = experiment.getclusteringtechnique();
        peakfindmethod = experiment.getpeakfindmethod();
        experimentyear = experiment.getExperimentyear();
        experimentnumber = experiment.getExperimentid();
        internalcalibration = experiment.getInternalcalibration();
        thresholdbinarymatrix = 2;
        strthresholdbinarymatrix = experiment.getthresholdbinarymatrix();
        if (strthresholdbinarymatrix != null) {
            if (!strthresholdbinarymatrix.trim().equalsIgnoreCase("")) {
                this.thresholdbinarymatrix = Double.parseDouble(strthresholdbinarymatrix);
            }
        }
        this.inputfollowingnumbersample = 2;
        if (experiment.getNumberofreplicatessample() != null) {
            if (!experiment.getNumberofreplicatessample().trim().equalsIgnoreCase("")) {
                this.inputfollowingnumbersample = Integer.parseInt(experiment.getNumberofreplicatessample());
            }
        }
        this.threshold_noisy_spectra = 450;
        if (experiment.getThreshold_noisy_spectra() != null) {
            if (!experiment.getThreshold_noisy_spectra().trim().equalsIgnoreCase("")) {
                this.threshold_noisy_spectra = Integer.parseInt(experiment.getThreshold_noisy_spectra());
            }
        }
        this.numbermisfitallowed = 0;
        strnumbermisfitallowed = experiment.getnumbermisfitsallowed();
        if (strnumbermisfitallowed != null) {
            if (!strnumbermisfitallowed.trim().equalsIgnoreCase("")) {
                this.numbermisfitallowed = Integer.parseInt(strnumbermisfitallowed);
            }
        }
        this.systemcodeitemid = "1";
        if (!systemcodeitemid.trim().equalsIgnoreCase("")) {
            this.systemcodeitemid = systemcodeitemid.trim();
        }
        this.quantilethreshold = 0.98;
        strquantilethreshold = experiment.getquantilethreshold();
        if (strquantilethreshold != null) {
            if (!strquantilethreshold.trim().equalsIgnoreCase("")) {
                this.quantilethreshold = Double.parseDouble(strquantilethreshold);
            }
        }
        strdeltamzsearchmaximum = experiment.getdelta_mz_searchmaximum();
        this.deltamzsearchmaximum = 0.5;
        if (strdeltamzsearchmaximum != null) {
            if (!strdeltamzsearchmaximum.trim().equalsIgnoreCase("")) {
                this.deltamzsearchmaximum = Double.parseDouble(strdeltamzsearchmaximum);
            }
        }
        this.deltamzsearchcalibrants = 0.5;
        if (strdeltamzsearchcalibrants != null) {
            if (!strdeltamzsearchcalibrants.trim().equalsIgnoreCase("")) {
                this.deltamzsearchcalibrants = Double.parseDouble(strdeltamzsearchcalibrants);
            }
        }
        strdeltamzcombine = experiment.getdelta_mz_combine();
        this.deltamzcombine = 0.5;
        if (strdeltamzcombine != null) {
            if (!strdeltamzcombine.trim().equalsIgnoreCase("")) {
                this.deltamzcombine = Double.parseDouble(strdeltamzcombine);
            }
        }
        this.minimummass = 800;
        strinputminimummass = experiment.getminimum_mass();
        if (strinputminimummass != null) {
            if (!strinputminimummass.trim().equalsIgnoreCase("")) {
                this.minimummass = Double.parseDouble(strinputminimummass);
            }
        }
        this.maximummass = 4000;
        strinputmaximummass = experiment.getmaximum_mass();
        if (strinputmaximummass != null) {
            if (!strinputmaximummass.trim().equalsIgnoreCase("")) {
                this.maximummass = Double.parseDouble(strinputmaximummass);
            }
        }
        odatacalmasses = systemCodeItemService.getclibrationmasses(systemcodeitemid);
        calibrationmasses = new double[odatacalmasses.length];
        for (int i = 0; i <= (odatacalmasses.length - 1); i++) {
            calibrationmasses[i] = Double.parseDouble(odatacalmasses[i][0].toString());
        }
        calibtext = "";
        if ((internalcalibration) && (odatacalmasses.length > 0)) {
            calibtext = odatacalmasses[0][1].toString().trim() + "("
                    + String.valueOf(odatacalmasses.length - numbermisfitallowed) + ")";
            calibtext.replaceAll(" ", "");
        } else {
            calibtext = "nocalib";
        }
        progressnew = new Progress();
        Dimension dimension = new Dimension(300, 150);
        progressnew.setdialogsize(dimension);
        gc = new GregorianCalendar();
        strdatum = date_to_string(gc);
        stryear = get_year_date(gc);
        strtime = date_to_time(gc);
        equipmentid = experimentService.getequipmentid(experimentnumber);
        if (ftp == null) {
            try {
                ftp = new FTPClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        filetype_canbe_processed = filetypes.can_be_processed(experiment);
        if (filetype_canbe_processed) {
            if (equipmentid2.trim().equalsIgnoreCase("4") && experiment.getfiletype().trim().equalsIgnoreCase("raw")) {
                if (thermoscientific == null) {
                    thermoscientific = new Thermo_Scientific(cc, experiment, exportfiletodisk);
                }
            }
            if (equipmentid2.trim().equalsIgnoreCase("4")
                    && experiment.getfiletype().trim().equalsIgnoreCase("mzxml")) {
                if (readmzxml == null) {
                    readmzxml = new ReadmzXML(cc, experiment, exportfiletodisk);
                }
            }
            if (equipmentid2.trim().equalsIgnoreCase("5")
                    && experiment.getfiletype().trim().equalsIgnoreCase("mzxml")) {
                if (readmzxml == null) {
                    readmzxml = new ReadmzXML(cc, experiment, exportfiletodisk);
                }
            }
            datareduction_files();
        }
        emptymemory2();
        storematrixcountslocal();
        storematrixintensities();
        storebinarymatrix();
        emptymemory();
        progressnew.close();
        //System.out.println("=============================================================");
        //System.out.println("    einde create matrix  experiment.getExperimentid() " + experiment.getExperimentid());
        cc.createMatrixAction.generateCreateMtrixScreen(experiment.getExperimentid());
        // System.out.println("en nu ?");
        // System.out.println("=============================================================");
        if (filetype_canbe_processed == false) {
            String message = "Filetype " + experiment.getfiletype() + " can not be processed for equipmentid "
                    + equipmentid;
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void emptymemory() {
        if (combinepeaks != null) {
            combinepeaks = null;
        }
    }

    private void emptymemory2() {
        if (thermoscientific != null) {
            thermoscientific.setobjects_null();
            thermoscientific = null;
        }
    }

    private void storematrixcountslocal() {
        matrixcounts = "";
        if (inputfollowingnumbersample > 0) {
            combinepeaks = new Combinepeaks(cc, experiment);
            timealignmentbuffermassnew = null;
            timealignmentbuffersequencenew = null;
            timealignmentbufferretentiontimenew = null;
            arrayalignmentmasses = null;
            arrayretentiontimes = null;
            arraysequences = null;
            doublearraycombinedpeaks = null;
            combinepeaks.combinepeaks(calibtext, str_aligned, progressnew);
            matrixcountsfilename = experiment.getmatrixcountsfilename();
            numberofmasses = experiment.getnumberofmasses();
        }
        try {
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            ftp.setTimeout(cc.ftp_longtime);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            ftp.get(cc.userhome + cc.fileSeparator + matrixcountsfilename, matrixcountsfilename);
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
        file = new File(cc.userhome + cc.fileSeparator + matrixcountsfilename);
        try {
            is = new FileInputStream(file);
            int_filegrootte = is.available();
            filegrootte_kbytes = int_filegrootte / 1024.000;
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updatesample = resultservice.deleteresultrecord(matrixcountsfilename, experimentnumber, strquantilethreshold);
        gc = new GregorianCalendar();
        strdatum = date_to_string(gc);
        stryear = get_year_date(gc);
        strtime = date_to_time(gc);
        updatesample = resultservice.insertmatrixresultrecord(matrixcountsfilename, filegrootte_kbytes, strtime,
                experimentnumber, strquantilethreshold, strdatum, stryear);
    }

    private void storematrixintensities() {
        if (inputfollowingnumbersample > 0) {
            matrixintensityfilename = experiment.getmatrixintensityfilename();
        }
        try {
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            ftp.setTimeout(cc.ftp_longtime);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            ftp.get(cc.userhome + cc.fileSeparator + matrixintensityfilename, matrixintensityfilename);
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
        file = new File(cc.userhome + cc.fileSeparator + matrixintensityfilename);
        try {
            is = new FileInputStream(file);
            int_filegrootte = is.available();
            filegrootte_kbytes = int_filegrootte / 1024.000;
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updatesample = resultservice.deleteresultrecord(matrixintensityfilename, experimentnumber,
                strquantilethreshold);
        gc = new GregorianCalendar();
        strdatum = date_to_string(gc);
        stryear = get_year_date(gc);
        strtime = date_to_time(gc);
        updatesample = resultservice.insertmatrixresultrecord(matrixintensityfilename, filegrootte_kbytes, strtime,
                experimentnumber, strquantilethreshold, strdatum, stryear);
    }

    private void storebinarymatrix() {
        if (inputfollowingnumbersample > 0) {
            binarymatrixfilename = experiment.getbinarymatrixfilename();
        }
        try {
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            ftp.setTimeout(cc.ftp_longtime);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            ftp.get(cc.userhome + cc.fileSeparator + binarymatrixfilename, binarymatrixfilename);
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
        file = new File(cc.userhome + cc.fileSeparator + binarymatrixfilename);
        try {
            is = new FileInputStream(file);
            int_filegrootte = is.available();
            filegrootte_kbytes = int_filegrootte / 1024.000;
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        updatesample = resultservice.deleteresultrecord(binarymatrixfilename, experimentnumber, strquantilethreshold);
        gc = new GregorianCalendar();
        strdatum = date_to_string(gc);
        stryear = get_year_date(gc);
        strtime = date_to_time(gc);
        updatesample = resultservice.insertmatrixresultrecord(binarymatrixfilename, filegrootte_kbytes, strtime,
                experimentnumber, strquantilethreshold, strdatum, stryear);
    }

    private void datareduction_files() {
        odatafiles = resultservice.getallfiles(experiment.getfiletype().trim(), experimentnumber);
        updatesample = resultservice.deleteresultrecords(experimentnumber, strquantilethreshold);
        progressnew.init("Datareduction ....", 35);
        count = 1;
        progressnew.setmaximum(odatafiles.length);
        massspectrometryfile = new MassSpectrometryFile();
        massspectrometryfile.setPreviousSampleid("-1");
        massspectrometryfile.setPreviousGroupid("-1");
        massspectrometryfile.setOffset_lc_ms(0);
        for (int i = 0; i <= odatafiles.length - 1; i++) {
            massspectrometryfile.setFilename(odatafiles[i][0].toString().trim());
            if (odatafiles[i][1] == null) {
                massspectrometryfile.setSampleid("");
            } else {
                massspectrometryfile.setSampleid(odatafiles[i][1].toString().trim());
            }
            if (odatafiles[i][2] == null) {
                massspectrometryfile.setGroupid("");
            } else {
                massspectrometryfile.setGroupid(odatafiles[i][2].toString().trim());
            }
            if (odatafiles[i][3] == null) {
                samplecode = "";
            } else {
                samplecode = odatafiles[i][3].toString().trim();
            }
            if (odatafiles[i][4] == null) {
                groupcode = "";
            } else {
                groupcode = odatafiles[i][4].toString().trim();
            }
            massspectrometryfile.setOffset_lc_ms(0);
            try {
                massspectrometryfile.setOffset_lc_ms(Double.parseDouble(odatafiles[i][5].toString().trim()));
            } catch (Exception ex) {
            }
            progressnew.setnumberandtext(i, "Datareduction .... " + massspectrometryfile.getFilename());
            if ((!massspectrometryfile.getPreviousSampleid().equalsIgnoreCase(massspectrometryfile.getSampleid()))
                    || (!massspectrometryfile.getPreviousGroupid()
                    .equalsIgnoreCase(massspectrometryfile.getGroupid()))) {
                count = 1;
            }
            if (count <= inputfollowingnumbersample) {
                if (experiment.getfiletype().trim().equalsIgnoreCase("fid") && (massspectrometryfile.getFilename()
                        .substring((massspectrometryfile.getFilename().length() - fid.length()),
                                massspectrometryfile.getFilename().length())
                        .equalsIgnoreCase(fid))) {
                    acquisname = massspectrometryfile.getFilename().substring(0,
                            (massspectrometryfile.getFilename().length() - fid.length())) + acqus;
                }
                if (filetypes.can_be_processed(experiment)) {
                    datareductionfile();
                }
            }
            massspectrometryfile.setPreviousSampleid(massspectrometryfile.getSampleid());
            massspectrometryfile.setPreviousGroupid(massspectrometryfile.getGroupid());
        }
        progressnew.setnumberandtext(odatafiles.length,
                "Datareduction .... (finished) " + massspectrometryfile.getFilename());
    }

    private void fillpeaks(String createobject) {
        if (peakfindmethod == 1) {
            if (createobject.equalsIgnoreCase("createobject")) {
                identifypeaks = new IdentifyPeaks(cc, experiment);
            }
            peaks = identifypeaks.returnpeaks(ftmsdata, spectrum);
            noisepeaks = identifypeaks.returnnoisepeaks();
        } else if (peakfindmethod == 2) {
            if (createobject.equalsIgnoreCase("createobject")) {
                simplepeakfind = new Simplepeakfind();
            }
            if (equipmentid == 2) {
                peaks = simplepeakfind.findlocalmaximaFTMS(deltamzsearchmaximum, quantilethreshold, minimummass,
                        maximummass, clusteringtechnique, ftmsdata);
            } else {
                peaks = simplepeakfind.findlocalmaxima(deltamzsearchmaximum, quantilethreshold, minimummass,
                        maximummass, clusteringtechnique, spectrum);
            }
        } else {
            if (createobject.equalsIgnoreCase("createobject")) {
                simplepeakfind = new Simplepeakfind();
            }
            peaks = simplepeakfind.findlocalmaxima(deltamzsearchmaximum, quantilethreshold, minimummass, maximummass,
                    clusteringtechnique, spectrum);
        }
    }

    private void calibrateinternalmalditof() {
        newcalibrationmassesraw = new double[odatacalmasses.length];
        calibrationchannelraw = new double[odatacalmasses.length];
        numberoffits = 0;
        calibrationmassesintensity = new double[calibrationmasses.length];
        for (int i = 0; i <= calibrationmassesintensity.length - 1; i++) {
            calibrationmassesintensity[i] = -1;
        }
        deltamzsearchcalibrantslocal = deltamzsearchcalibrants;
        for (int i = 0; i <= peaks.length - 1; i++) {
            if (clusteringtechnique == 1) {
                deltamzsearchcalibrantslocal = peaks[i][0] * deltamzsearchcalibrants / 1000000;
            }
            for (int j = 0; j <= calibrationmasses.length - 1; j++) {
                if ((peaks[i][0] > (calibrationmasses[j] - deltamzsearchcalibrantslocal))
                        && (peaks[i][0] < (calibrationmasses[j] + deltamzsearchcalibrantslocal))) {
                    if (calibrationmassesintensity[j] == -1) {
                        calibrationmassesintensity[j] = peaks[i][1];
                        newcalibrationmassesraw[j] = calibrationmasses[j];
                        calibrationchannelraw[j] = peaks[i][2];
                    } else {
                        if (peaks[i][1] > calibrationmassesintensity[j]) {
                            calibrationmassesintensity[j] = peaks[i][1];
                            newcalibrationmassesraw[j] = calibrationmasses[j];
                            calibrationchannelraw[j] = peaks[i][2];
                        }
                    }
                }
            }
        }
        for (int i = 0; i <= newcalibrationmassesraw.length - 1; i++) {
            if (newcalibrationmassesraw[i] != 0) {
                numberoffits++;
            }
        }
        if (internalcalibration) {
            calibrated = false;
            if (numberoffits > 0) {
                if (numberoffits >= (calibrationmasses.length - numbermisfitallowed)) {
                    newcalibrationmasses2 = new double[numberoffits];
                    calibrationchannel2 = new double[numberoffits];
                    numberoffits = 0;
                    for (int i = 0; i <= newcalibrationmassesraw.length - 1; i++) {
                        if (newcalibrationmassesraw[i] != 0) {
                            newcalibrationmasses2[numberoffits] = newcalibrationmassesraw[i];
                            calibrationchannel2[numberoffits] = calibrationchannelraw[i];
                            numberoffits++;
                        }
                    }
                    a = calibrationquadratic.quadratic(newcalibrationmasses2, calibrationchannel2);
                    A = a[1];
                    B = a[2];
                    C = a[0];
                    for (int i = 0; i < intnumberofmeasurements; i++) {
                        spectrum.mass[i] = (A * i * i) + (B * i) + C;
                    }
                    calibrated = true;
                }
            }
        } else {
            calibrated = true;
        }
    }

    private void calibrateinternalftms() {
        newcalibrationmassesraw = new double[odatacalmasses.length];
        frequencyraw = new double[odatacalmasses.length];
        reciprocalfrequencyraw = new double[odatacalmasses.length];
        numberoffits = 0;
        calibrationmassesintensity = new double[calibrationmasses.length];
        for (int i = 0; i <= calibrationmassesintensity.length - 1; i++) {
            calibrationmassesintensity[i] = -1;
        }
        deltamzsearchcalibrantslocal = deltamzsearchcalibrants;
        for (int i = 0; i <= peaks.length - 1; i++) {
            if (clusteringtechnique == 1) {
                deltamzsearchcalibrantslocal = peaks[i][0] * deltamzsearchcalibrants / 1000000;
            }
            for (int j = 0; j <= calibrationmasses.length - 1; j++) {
                if ((peaks[i][0] > (calibrationmasses[j] - deltamzsearchcalibrantslocal))
                        && (peaks[i][0] < (calibrationmasses[j] + deltamzsearchcalibrantslocal))) {
                    if (calibrationmassesintensity[j] == -1) {
                        calibrationmassesintensity[j] = peaks[i][1];
                        newcalibrationmassesraw[j] = calibrationmasses[j];
                        frequencyraw[j] = masstofrequency(peaks[i][0]);
                        reciprocalfrequencyraw[j] = 1 / masstofrequency(peaks[i][0]);
                    } else {
                        if (peaks[i][1] > calibrationmassesintensity[j]) {
                            calibrationmassesintensity[j] = peaks[i][1];
                            newcalibrationmassesraw[j] = calibrationmasses[j];
                            frequencyraw[j] = masstofrequency(peaks[i][0]);
                            reciprocalfrequencyraw[j] = 1 / masstofrequency(peaks[i][0]);
                        }
                    }
                }
            }
        }
        for (int i = 0; i <= newcalibrationmassesraw.length - 1; i++) {
            if (newcalibrationmassesraw[i] != 0) {
                numberoffits++;
            }
        }
        if (internalcalibration) {
            calibrated = false;
            if ((numberoffits > 1) && (numberoffits >= (calibrationmasses.length - numbermisfitallowed))) {
                newcalibrationmasses2 = new double[numberoffits];
                reciprocalfrequencyraw2 = new double[numberoffits];
                frequencyraw2 = new double[numberoffits];
                reciprocalcalibrationmasses2 = new double[numberoffits];
                numberoffits = 0;
                for (int i = 0; i <= newcalibrationmassesraw.length - 1; i++) {
                    if (newcalibrationmassesraw[i] != 0) {
                        newcalibrationmasses2[numberoffits] = newcalibrationmassesraw[i];
                        frequencyraw2[numberoffits] = frequencyraw[i];
                        reciprocalfrequencyraw2[numberoffits] = reciprocalfrequencyraw[i];
                        numberoffits++;
                    }
                }
                for (int p = 0; p < newcalibrationmasses2.length; p++) {
                    reciprocalcalibrationmasses2[p] = 1 / newcalibrationmasses2[p];
                }
                a = calibrationlinear.linear(frequencyraw2, reciprocalcalibrationmasses2);
                A = a[1];
                B = a[0];
                for (int i = 0; i <= peaks.length - 1; i++) {
                    peaks[i][0] = A / (masstofrequency(peaks[i][0]) - B);
                }
                if (noisepeaks != null) {
                    for (int i = 0; i <= noisepeaks.length - 1; i++) {
                        noisepeaks[i][0] = A / (masstofrequency(noisepeaks[i][0]) - B);
                    }
                }
                calibrated = true;
            }
        } else {
            calibrated = true;
        }
    }

    private void removedoublemasses() {
        referencemass = -1;
        referenceretentiontime = -1;
        removemassesindices = new StringBuffer("");
        for (int i = 0; i <= peaks.length - 1; i++) {
            deltamzlocal = deltamzcombine;
            if (clusteringtechnique == 1) {
                deltamzlocal = deltamzcombine * peaks[i][0] / 1000000;
            }
            if (referencemass > 0) {
                if (Math.abs(referencemass - peaks[i][0]) < Math.abs(deltamzlocal)) {
                    if (removemassesindices.toString().equalsIgnoreCase("")) {
                        removemassesindices.append(String.valueOf(i - 1) + "," + String.valueOf(i));
                    } else {
                        removemassesindices.append("," + String.valueOf(i));
                    }
                } else {
                    if (removemassesindices.toString().split(",").length > 1) {
                        arrayofremovemassesindices = removemassesindices.toString().split(",");
                        save_mass = -1;
                        save_retentiontime = 0;
                        save_intensity = 0;
                        cleanmasses = new double[4][removemassesindices.toString().split(",").length];
                        for (int j = 0; j <= arrayofremovemassesindices.length - 1; j++) {
                            cleanmasses[0][j] = Double.parseDouble(arrayofremovemassesindices[j]);
                            cleanmasses[1][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][0];
                            cleanmasses[2][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][1];
                            cleanmasses[3][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][2];
                        }
                        sortrow = 3;
                        sortmatrix = new SortMatrix(cleanmasses, sortrow);
                        for (int j = 0; j <= cleanmasses[0].length - 1; j++) {
                            deltatimelocalcombine = deltatimecombine;
                            if (timeclusteringtechnique == 1) {
                                deltatimelocalcombine = deltatimecombine * cleanmasses[3][j] / 100;
                            }
                            if (cleanmasses[2][j] > save_intensity) {
                                save_mass = cleanmasses[1][j];
                                save_intensity = cleanmasses[2][j];
                                save_retentiontime = cleanmasses[3][j];
                            }
                            if ((j <= cleanmasses[0].length - 2)
                                    && (Math.abs(cleanmasses[3][j + 1] - cleanmasses[3][j]) < deltatimelocalcombine)) {
                                peaks[(int) cleanmasses[0][j]][0] = -1;
                            } else {
                                peaks[(int) cleanmasses[0][j]][0] = save_mass;
                                peaks[(int) cleanmasses[0][j]][1] = save_intensity;
                                peaks[(int) cleanmasses[0][j]][2] = save_retentiontime;
                                save_mass = -1;
                                save_intensity = 0;
                                save_retentiontime = 0;
                            }
                        }
                    }
                    removemassesindices = new StringBuffer("");
                }
            }
            referencemass = peaks[i][0];
            referenceretentiontime = peaks[i][2];
        }
    }

    private void removeoverlappingindices_raw() {
        mass_distance_factor = 2;
        referencemass = -1;
        removemassesindices = new StringBuffer("");
        for (int i = 0; i <= peaks.length - 1; i++) {
            deltamzlocal = mass_distance_factor * deltamzcombine;
            if (clusteringtechnique == 1) {
                deltamzlocal = mass_distance_factor * deltamzcombine * peaks[i][0] / 1000000;
            }
            if (referencemass > 0) {
                if (Math.abs(referencemass - peaks[i][0]) < Math.abs(deltamzlocal)) {
                    if (removemassesindices.toString().equalsIgnoreCase("")) {
                        removemassesindices.append(String.valueOf(save_i) + "," + String.valueOf(i));
                    } else {
                        removemassesindices.append("," + String.valueOf(i));
                    }
                } else {
                    save_i = i;
                    if (removemassesindices.toString().split(",").length > 1) {
                        arrayofremovemassesindices = removemassesindices.toString().split(",");
                        cleanmasses = new double[7][removemassesindices.toString().split(",").length];
                        for (int j = 0; j <= arrayofremovemassesindices.length - 1; j++) {
                            cleanmasses[0][j] = Double.parseDouble(arrayofremovemassesindices[j]);
                            cleanmasses[1][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][0];
                            cleanmasses[2][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][1];
                            cleanmasses[3][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][3];
                            cleanmasses[4][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][4];
                            cleanmasses[5][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][5];
                            cleanmasses[6][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][6];
                        }
                        sortrow = 3;
                        sortmatrix = new SortMatrix(cleanmasses, sortrow);
                        for (int j = 0; j <= cleanmasses[0].length - 1; j++) {
                            if (j > 0) {
                                if (cleanmasses[3][j] - cleanmasses[4][j] <= cleanmasses[3][j - 1]) {
                                    if (cleanmasses[2][j - 1] > cleanmasses[2][j]) {
                                        peaks[(int) cleanmasses[0][j]][0] = peaks[(int) cleanmasses[0][j - 1]][0];
                                        peaks[(int) cleanmasses[0][j]][1] = peaks[(int) cleanmasses[0][j - 1]][1];
                                        peaks[(int) cleanmasses[0][j]][2] = peaks[(int) cleanmasses[0][j - 1]][2];
                                        if (ms2_sequencenced_masses_present) {
                                            if (peaks[(int) cleanmasses[0][j - 1]][7] > -1) {
                                                peaks[(int) cleanmasses[0][j]][7] = peaks[(int) cleanmasses[0][j
                                                        - 1]][7];
                                            }
                                        }
                                    }
                                    if (cleanmasses[5][j - 1] < cleanmasses[5][j]) {
                                        peaks[(int) cleanmasses[0][j]][5] = peaks[(int) cleanmasses[0][j - 1]][5];
                                    }
                                    if (cleanmasses[6][j - 1] > cleanmasses[6][j]) {
                                        peaks[(int) cleanmasses[0][j]][6] = peaks[(int) cleanmasses[0][j - 1]][6];
                                    }
                                    peaks[(int) cleanmasses[0][j - 1]][0] = -1;
                                }
                            }
                        }
                    }
                    referencemass2 = peaks[save_i][0];
                    countdown = save_i;
                    deltamzlocal = mass_distance_factor * deltamzcombine;
                    if (clusteringtechnique == 1) {
                        deltamzlocal = mass_distance_factor * deltamzcombine * referencemass2 / 1000000;
                    }
                    removemassesindices2 = new StringBuffer("");
                    while (countdown > 0) {
                        countdown--;
                        if ((peaks[countdown][0] > 0) && (referencemass2 > 0)) {
                            if (Math.abs(referencemass2 - peaks[countdown][0]) < Math.abs(deltamzlocal)) {
                                if (removemassesindices2.toString().equalsIgnoreCase("")) {
                                    removemassesindices2
                                            .append(String.valueOf(save_i) + "," + String.valueOf(countdown));
                                } else {
                                    removemassesindices2.append("," + String.valueOf(countdown));
                                }
                            } else {
                                countdown = 0;
                                if (removemassesindices2.toString().split(",").length > 1) {
                                    arrayofremovemassesindices = removemassesindices2.toString().split(",");
                                    cleanmasses = new double[7][removemassesindices2.toString().split(",").length];
                                    for (int j = 0; j <= arrayofremovemassesindices.length - 1; j++) {
                                        cleanmasses[0][j] = Double.parseDouble(arrayofremovemassesindices[j]);
                                        cleanmasses[1][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][0];
                                        cleanmasses[2][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][1];
                                        cleanmasses[3][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][3];
                                        cleanmasses[4][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][4];
                                        cleanmasses[5][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][5];
                                        cleanmasses[6][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][6];
                                    }
                                    sortrow = 3;
                                    sortmatrix = new SortMatrix(cleanmasses, sortrow);
                                    for (int j = 0; j <= cleanmasses[0].length - 1; j++) {
                                        if (j > 0) {
                                            if (cleanmasses[3][j] - cleanmasses[4][j] <= cleanmasses[3][j - 1]) {
                                                if (cleanmasses[2][j - 1] > cleanmasses[2][j]) {
                                                    peaks[(int) cleanmasses[0][j]][0] = peaks[(int) cleanmasses[0][j
                                                            - 1]][0];
                                                    peaks[(int) cleanmasses[0][j]][1] = peaks[(int) cleanmasses[0][j
                                                            - 1]][1];
                                                    peaks[(int) cleanmasses[0][j]][2] = peaks[(int) cleanmasses[0][j
                                                            - 1]][2];
                                                    if (ms2_sequencenced_masses_present) {
                                                        if (peaks[(int) cleanmasses[0][j - 1]][7] > -1) {
                                                            peaks[(int) cleanmasses[0][j]][7] = peaks[(int) cleanmasses[0][j
                                                                    - 1]][7];
                                                        }
                                                    }
                                                }
                                                if (cleanmasses[5][j - 1] < cleanmasses[5][j]) {
                                                    peaks[(int) cleanmasses[0][j]][5] = peaks[(int) cleanmasses[0][j
                                                            - 1]][5];
                                                }
                                                if (cleanmasses[6][j - 1] > cleanmasses[6][j]) {
                                                    peaks[(int) cleanmasses[0][j]][6] = peaks[(int) cleanmasses[0][j
                                                            - 1]][6];
                                                }
                                                peaks[(int) cleanmasses[0][j - 1]][0] = -1;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    referencemass = peaks[save_i][0];
                    removemassesindices = new StringBuffer("");
                }
            } else {
                save_i = i;
                referencemass = peaks[save_i][0];
            }
        }
    }

    private void removedoublemasses_raw() {
        referencemass = -1;
        referenceretentiontime = -1;
        removemassesindices = new StringBuffer("");
        retentiontime_distance_factor = 2;
        mass_distance_factor = 2;
        for (int i = 0; i <= peaks.length - 1; i++) {
            deltamzlocal = mass_distance_factor * deltamzcombine;
            if (clusteringtechnique == 1) {
                deltamzlocal = mass_distance_factor * deltamzcombine * peaks[i][0] / 1000000;
            }
            if (referencemass > 0) {
                if (Math.abs(referencemass - peaks[i][0]) < Math.abs(deltamzlocal)) {
                    countdown2 = save_i;
                    while (countdown2 > 0) {
                        countdown2--;
                        if ((peaks[countdown2][0] > 0) && (peaks[i][0] > 0)) {
                            if (Math.abs(peaks[i][0] - peaks[countdown2][0]) < Math.abs(deltamzlocal)) {
                                in_time_window2 = false;
                                deltatimelocalcombine = retentiontime_distance_factor * deltatimecombine;
                                if (timeclusteringtechnique == 1) {
                                    deltatimelocalcombine = retentiontime_distance_factor * deltatimecombine
                                            * peaks[i][2] / 100;
                                }
                                if ((Math.abs(peaks[i][2] - peaks[countdown2][2]) < deltatimelocalcombine)) {
                                    in_time_window2 = true;
                                }
                                if ((peaks[i][5] < peaks[countdown2][5]) && (peaks[i][6] > peaks[countdown2][5])) {
                                    in_time_window2 = true;
                                }
                                if ((peaks[i][5] < peaks[countdown2][6]) && (peaks[i][6] > peaks[countdown2][6])) {
                                    in_time_window2 = true;
                                }
                                if ((peaks[i][5] > peaks[countdown2][5]) && (peaks[i][6] < peaks[countdown2][6])) {
                                    in_time_window2 = true;
                                }
                                if ((peaks[countdown2][5] > peaks[i][5]) && (peaks[countdown2][6] < peaks[i][6])) {
                                    in_time_window2 = true;
                                }
                                if (in_time_window2) {
                                    if (peaks[countdown2][1] > peaks[i][1]) {
                                        peaks[i][0] = -1;
                                        if (peaks[i][5] < peaks[countdown2][5]) {
                                            peaks[countdown2][5] = peaks[i][5];
                                        }
                                        if (peaks[i][6] > peaks[countdown2][6]) {
                                            peaks[countdown2][6] = peaks[i][6];
                                        }
                                        if (ms2_sequencenced_masses_present) {
                                            if (peaks[i][7] > -1) {
                                                peaks[countdown2][7] = peaks[i][7];
                                            }
                                        }
                                    } else {
                                        peaks[countdown2][0] = -1;
                                        if (peaks[countdown2][5] < peaks[i][5]) {
                                            peaks[i][5] = peaks[countdown2][5];
                                        }
                                        if (peaks[countdown2][6] > peaks[i][6]) {
                                            peaks[i][6] = peaks[countdown2][6];
                                        }
                                        if (ms2_sequencenced_masses_present) {
                                            if (peaks[countdown2][7] > -1) {
                                                peaks[i][7] = peaks[countdown2][7];
                                            }
                                        }
                                    }
                                    countdown2 = 0;
                                }
                            } else {
                                countdown2 = 0;
                            }
                        }
                    }
                    if (peaks[i][0] > -1) {
                        if (removemassesindices.toString().equalsIgnoreCase("")) {
                            removemassesindices.append(String.valueOf(save_i) + "," + String.valueOf(i));
                        } else {
                            removemassesindices.append("," + String.valueOf(i));
                        }
                    }
                } else {
                    referencemass = peaks[i][0];
                    referenceretentiontime = peaks[i][2];
                    save_i = i;
                    if (removemassesindices.toString().split(",").length > 1) {
                        arrayofremovemassesindices = removemassesindices.toString().split(",");
                        save_mass = -1;
                        save_retentiontime = 0;
                        save_intensity = 0;
                        save_min_retentiontime = 0;
                        save_max_retentiontime = 0;
                        save_ms2_index = -1;
                        if (ms2_sequencenced_masses_present) {
                            cleanmasses = new double[7][removemassesindices.toString().split(",").length];
                        } else {
                            cleanmasses = new double[6][removemassesindices.toString().split(",").length];
                        }
                        for (int j = 0; j <= arrayofremovemassesindices.length - 1; j++) {
                            cleanmasses[0][j] = Double.parseDouble(arrayofremovemassesindices[j]);
                            cleanmasses[1][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][0];
                            cleanmasses[2][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][1];
                            cleanmasses[3][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][2];
                            cleanmasses[4][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][5];
                            cleanmasses[5][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][6];
                            if (ms2_sequencenced_masses_present) {
                                cleanmasses[6][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][7];
                            }
                        }
                        sortrow = 3;
                        sortmatrix = new SortMatrix(cleanmasses, sortrow);
                        for (int j = 0; j <= cleanmasses[0].length - 1; j++) {
                            deltatimelocalcombine = retentiontime_distance_factor * deltatimecombine;
                            if (timeclusteringtechnique == 1) {
                                deltatimelocalcombine = retentiontime_distance_factor * deltatimecombine
                                        * cleanmasses[3][j] / 100;
                            }
                            if (save_min_retentiontime == 0) {
                                save_min_retentiontime = cleanmasses[4][j];
                            }
                            if (save_max_retentiontime == 0) {
                                save_max_retentiontime = cleanmasses[5][j];
                            }
                            if (cleanmasses[4][j] < save_min_retentiontime) {
                                save_min_retentiontime = cleanmasses[4][j];
                            }
                            if (cleanmasses[5][j] > save_max_retentiontime) {
                                save_max_retentiontime = cleanmasses[5][j];
                            }
                            if (cleanmasses[2][j] > save_intensity) {
                                save_mass = cleanmasses[1][j];
                                save_intensity = cleanmasses[2][j];
                                save_retentiontime = cleanmasses[3][j];
                                if (ms2_sequencenced_masses_present) {
                                    if (cleanmasses[6][j] > -1) {
                                        save_ms2_index = cleanmasses[6][j];
                                    }
                                }
                            }
                            in_time_window = false;
                            if (j <= cleanmasses[0].length - 2) {
                                if ((Math.abs(cleanmasses[3][j + 1] - cleanmasses[3][j]) < deltatimelocalcombine)) {
                                    in_time_window = true;
                                }
                                if ((save_min_retentiontime < cleanmasses[4][j + 1])
                                        && (save_max_retentiontime > cleanmasses[4][j + 1])) {
                                    in_time_window = true;
                                }
                                if ((save_min_retentiontime < cleanmasses[5][j + 1])
                                        && (save_max_retentiontime > cleanmasses[5][j + 1])) {
                                    in_time_window = true;
                                }
                                if ((save_min_retentiontime > cleanmasses[4][j + 1])
                                        && (save_max_retentiontime < cleanmasses[5][j + 1])) {
                                    in_time_window = true;
                                }
                                if ((cleanmasses[4][j + 1] > save_min_retentiontime)
                                        && (cleanmasses[5][j + 1] < save_max_retentiontime)) {
                                    in_time_window = true;
                                }
                            }
                            if (in_time_window) {
                                peaks[(int) cleanmasses[0][j]][0] = -1;
                            } else {
                                peaks[(int) cleanmasses[0][j]][0] = save_mass;
                                peaks[(int) cleanmasses[0][j]][1] = save_intensity;
                                peaks[(int) cleanmasses[0][j]][2] = save_retentiontime;
                                peaks[(int) cleanmasses[0][j]][5] = save_min_retentiontime;
                                peaks[(int) cleanmasses[0][j]][6] = save_max_retentiontime;
                                if (ms2_sequencenced_masses_present) {
                                    if (save_ms2_index > -1) {
                                        peaks[(int) cleanmasses[0][j]][7] = save_ms2_index;
                                    }
                                }
                                save_mass = -1;
                                save_intensity = 0;
                                save_retentiontime = 0;
                                save_min_retentiontime = 0;
                                save_max_retentiontime = 0;
                                save_ms2_index = -1;
                            }
                        }
                    }
                    referencemass2 = peaks[save_i][0];
                    countdown = save_i;
                    deltamzlocal = mass_distance_factor * deltamzcombine;
                    if (clusteringtechnique == 1) {
                        deltamzlocal = mass_distance_factor * deltamzcombine * referencemass2 / 1000000;
                    }
                    removemassesindices2 = new StringBuffer("");
                    while (countdown > 0) {
                        countdown--;
                        if ((peaks[countdown][0] > 0) && (referencemass2 > 0)) {
                            if (Math.abs(referencemass2 - peaks[countdown][0]) < Math.abs(deltamzlocal)) {
                                if (removemassesindices2.toString().equalsIgnoreCase("")) {
                                    removemassesindices2
                                            .append(String.valueOf(save_i) + "," + String.valueOf(countdown));
                                } else {
                                    removemassesindices2.append("," + String.valueOf(countdown));
                                }
                            } else {
                                countdown = 0;
                                if (removemassesindices2.toString().split(",").length > 1) {
                                    arrayofremovemassesindices = removemassesindices2.toString().split(",");
                                    save_mass = -1;
                                    save_retentiontime = 0;
                                    save_intensity = 0;
                                    save_min_retentiontime = 0;
                                    save_max_retentiontime = 0;
                                    save_ms2_index = -1;
                                    if (ms2_sequencenced_masses_present) {
                                        cleanmasses = new double[7][removemassesindices2.toString().split(",").length];
                                    } else {
                                        cleanmasses = new double[6][removemassesindices2.toString().split(",").length];
                                    }
                                    for (int j = 0; j <= arrayofremovemassesindices.length - 1; j++) {
                                        cleanmasses[0][j] = Double.parseDouble(arrayofremovemassesindices[j]);
                                        cleanmasses[1][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][0];
                                        cleanmasses[2][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][1];
                                        cleanmasses[3][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][2];
                                        cleanmasses[4][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][5];
                                        cleanmasses[5][j] = peaks[Integer.parseInt(arrayofremovemassesindices[j])][6];
                                        if (ms2_sequencenced_masses_present) {
                                            cleanmasses[6][j] = peaks[Integer
                                                    .parseInt(arrayofremovemassesindices[j])][7];
                                        }
                                    }
                                    sortrow = 3;
                                    sortmatrix = new SortMatrix(cleanmasses, sortrow);
                                    for (int j = 0; j <= cleanmasses[0].length - 1; j++) {
                                        deltatimelocalcombine = retentiontime_distance_factor * deltatimecombine;
                                        if (timeclusteringtechnique == 1) {
                                            deltatimelocalcombine = retentiontime_distance_factor * deltatimecombine
                                                    * cleanmasses[3][j] / 100;
                                        }
                                        if (save_min_retentiontime == 0) {
                                            save_min_retentiontime = cleanmasses[4][j];
                                        }
                                        if (save_max_retentiontime == 0) {
                                            save_max_retentiontime = cleanmasses[5][j];
                                        }
                                        if (cleanmasses[4][j] < save_min_retentiontime) {
                                            save_min_retentiontime = cleanmasses[4][j];
                                        }
                                        if (cleanmasses[5][j] > save_max_retentiontime) {
                                            save_max_retentiontime = cleanmasses[5][j];
                                        }
                                        if (cleanmasses[2][j] > save_intensity) {
                                            save_mass = cleanmasses[1][j];
                                            save_intensity = cleanmasses[2][j];
                                            save_retentiontime = cleanmasses[3][j];
                                            if (ms2_sequencenced_masses_present) {
                                                if (cleanmasses[6][j] > -1) {
                                                    save_ms2_index = cleanmasses[6][j];
                                                }
                                            }
                                        }
                                        in_time_window = false;
                                        if (j <= cleanmasses[0].length - 2) {
                                            if ((Math.abs(cleanmasses[3][j + 1]
                                                    - cleanmasses[3][j]) < deltatimelocalcombine)) {
                                                in_time_window = true;
                                            }
                                            if ((save_min_retentiontime < cleanmasses[4][j + 1])
                                                    && (save_max_retentiontime > cleanmasses[4][j + 1])) {
                                                in_time_window = true;
                                            }
                                            if ((save_min_retentiontime < cleanmasses[5][j + 1])
                                                    && (save_max_retentiontime > cleanmasses[5][j + 1])) {
                                                in_time_window = true;
                                            }
                                            if ((save_min_retentiontime > cleanmasses[4][j + 1])
                                                    && (save_max_retentiontime < cleanmasses[5][j + 1])) {
                                                in_time_window = true;
                                            }
                                            if ((cleanmasses[4][j + 1] > save_min_retentiontime)
                                                    && (cleanmasses[5][j + 1] < save_max_retentiontime)) {
                                                in_time_window = true;
                                            }
                                        }
                                        if (in_time_window) {
                                            peaks[(int) cleanmasses[0][j]][0] = -1;
                                        } else {
                                            peaks[(int) cleanmasses[0][j]][0] = save_mass;
                                            peaks[(int) cleanmasses[0][j]][1] = save_intensity;
                                            peaks[(int) cleanmasses[0][j]][2] = save_retentiontime;
                                            peaks[(int) cleanmasses[0][j]][5] = save_min_retentiontime;
                                            peaks[(int) cleanmasses[0][j]][6] = save_max_retentiontime;
                                            if (ms2_sequencenced_masses_present) {
                                                if (save_ms2_index > -1) {
                                                    peaks[(int) cleanmasses[0][j]][7] = save_ms2_index;
                                                }
                                            }
                                            save_mass = -1;
                                            save_intensity = 0;
                                            save_retentiontime = 0;
                                            save_min_retentiontime = 0;
                                            save_max_retentiontime = 0;
                                            save_ms2_index = -1;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    removemassesindices = new StringBuffer("");
                }
            } else {
                referencemass = peaks[i][0];
                referenceretentiontime = peaks[i][2];
                save_i = i;
            }
        }
    }

    private void exportpeakslist() {
        try {
            fileexportbuffer = new FileOutputStream(cc.userhome + cc.fileSeparator + exportname);
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        peakstransposed = new double[peaks[0].length][peaks.length];
        for (int i = 0; i < peaks.length; i++) {
            for (int j = 0; j < peaks[0].length; j++) {
                peakstransposed[j][i] = peaks[i][j];
            }
        }
        sortrow = 0;
        sortmatrix = new SortMatrix(peakstransposed, sortrow);
        for (int i = 0; i < peaks.length; i++) {
            for (int j = 0; j < peaks[0].length; j++) {
                peaks[i][j] = peakstransposed[j][i];
            }
        }
        if (monoistopefinding) {
            if (monoisotoopfinding == null) {
                monoisotoopfinding = new Monoisotoopfinding(newcalibrationmassesraw, experiment);
            }
            chargestate = 1;
            monoisotoopfinding.mono_isotoopfinding(peaks, chargestate, newcalibrationmassesraw);
        }
        if ((equipmentid == 4) || (equipmentid == 5)) {
            if (experiment.getfiletype().trim().equalsIgnoreCase("mzxml")
                    || experiment.getfiletype().trim().equalsIgnoreCase("raw")) {
                peakstransposed = new double[peaks[0].length][peaks.length];
                for (int i = 0; i < peaks.length; i++) {
                    for (int j = 0; j < peaks[0].length; j++) {
                        peakstransposed[j][i] = peaks[i][j];
                    }
                }
                sortrow = 0;
                sortmatrix = new SortMatrix(peakstransposed, sortrow);
                for (int i = 0; i < peaks.length; i++) {
                    for (int j = 0; j < peaks[0].length; j++) {
                        peaks[i][j] = peakstransposed[j][i];
                    }
                }
                removeoverlappingindices_raw();
                peakstransposed = new double[peaks[0].length][peaks.length];
                for (int i = 0; i < peaks.length; i++) {
                    for (int j = 0; j < peaks[0].length; j++) {
                        peakstransposed[j][i] = peaks[i][j];
                    }
                }
                sortrow = 0;
                sortmatrix = new SortMatrix(peakstransposed, sortrow);
                for (int i = 0; i < peaks.length; i++) {
                    for (int j = 0; j < peaks[0].length; j++) {
                        peaks[i][j] = peakstransposed[j][i];
                    }
                }
                removedoublemasses_raw();
            } else {
                removedoublemasses();
            }
        }
        if (equipmentid == 3) {
            removedoublemasses();
        }
        for (int i = 0; i <= peaks.length - 1; i++) {
            tempBD = new BigDecimal(peaks[i][0]);
            if (peaks[i][0] > 0) {
                try {
                    fileexportbuffer
                            .write((tempBD.setScale(6, BigDecimal.ROUND_HALF_EVEN) + " " + peaks[i][1]).getBytes());
                    fileexportbuffer.flush();
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if ((equipmentid == 3) || (equipmentid == 4) || (equipmentid == 5)) {
                    if ((massspectrometryfile.getOffset_lc_ms() != 0) && (!performtimealgnment)) {
                        peaks[i][2] = peaks[i][2] + massspectrometryfile.getOffset_lc_ms();
                    }
                    try {
                        fileexportbuffer.write((" " + peaks[i][2]).getBytes());
                        fileexportbuffer.flush();
                    } catch (Exception e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    if ((equipmentid == 4) && (experiment.getfiletype().trim().equalsIgnoreCase("raw")
                            || experiment.getfiletype().trim().equalsIgnoreCase("mzxml"))) {
                        try {
                            fileexportbuffer.write((" " + peaks[i][5] + " " + peaks[i][6]).getBytes());
                            fileexportbuffer.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (ms2_sequencenced_masses_present) {
                            if (peaks[i][7] > -1) {
                                int j = (int) peaks[i][7];
                                sequensing_results = sequensing_results_vector.elementAt(j);
                                sequencing_results_string = sequensing_results[0].trim() + "#"
                                        + sequensing_results[8].trim() + "#" + sequensing_results[3].trim();
                                sequencing_results_string = sequencing_results_string.replaceAll(" ", "_");
                                try {
                                    fileexportbuffer.write((" " + sequencing_results_string).getBytes());
                                    fileexportbuffer.flush();
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
                }
                try {
                    fileexportbuffer.write(linefeed.getBytes());
                    fileexportbuffer.flush();
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        filetransported = false;
        filegrootte_kbytes = 1.0;
        try {
            fileexportbuffer.close();
        } catch (IOException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            ftp.quit();
        } catch (Exception e) {
            ;
        }
        try {
            ftp.setRemoteHost(cc.ftpremotehost);
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setTimeout(cc.ftp_longtime);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            inputpeaklist = new FileInputStream(cc.userhome + cc.fileSeparator + exportname);
            BufferedReader in = new BufferedReader(new InputStreamReader(inputpeaklist));
            line = "";
            ftp.put(line.getBytes(), exportname, false);
            countftp = 0;
            while (in.ready()) {
                line = line + in.readLine() + linefeed;
                if (countftp > 1000) {
                    try {
                        ftp.put(line.getBytes(), exportname, true);
                        line = "";
                        countftp = 0;
                    } catch (Exception ex2) {
                        try {
                            ftp.quit();
                        } catch (Exception ex3) {
                            ;
                        }
                        try {
                            ftp.setRemoteHost(cc.ftpremotehost);
                            ftp.connect();
                            ftp.login(cc.ftpuser, cc.ftppassword);
                            ftp.setConnectMode(FTPConnectMode.PASV);
                            ftp.setTimeout(cc.ftp_longtime);
                            ftp.setType(FTPTransferType.BINARY);
                            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
                        } catch (Exception ex4) {
                            ;
                        }
                    }
                }
                countftp++;
            }
            try {
                ftp.quit();
            } catch (Exception ex3) {
                ;
            }
            try {
                ftp.setRemoteHost(cc.ftpremotehost);
                ftp.connect();
                ftp.login(cc.ftpuser, cc.ftppassword);
                ftp.setConnectMode(FTPConnectMode.PASV);
                ftp.setTimeout(cc.ftp_longtime);
                ftp.setType(FTPTransferType.BINARY);
                ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            } catch (Exception ex4) {
                ;
            }
            ftp.put(line.getBytes(), exportname, true);
            inputpeaklist.close();
            filetransported = true;
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
            ;
        }
        if (calibrated) {
            if ((filetransported) && (!massspectrometryfile.getSampleid().trim().equals(""))
                    && (!massspectrometryfile.getGroupid().trim().equals(""))) {
                if (peaks.length < threshold_noisy_spectra) {
                    resultservice.insertresultrecord("reduced", filegrootte_kbytes, massspectrometryfile, strtime,
                            experimentnumber, strquantilethreshold, exportname, strdatum, stryear);
                    count++;
                } else {
                    resultservice.insertresultrecord("noisy", filegrootte_kbytes, massspectrometryfile, strtime,
                            experimentnumber, strquantilethreshold, exportname, strdatum, stryear);
                }
            }
        } else {
            if ((filetransported) && (!massspectrometryfile.getSampleid().trim().equals(""))
                    && (!massspectrometryfile.getGroupid().trim().equals(""))) {
                inputtype = "c= " + String.valueOf(numberoffits) + " <" + " ("
                        + String.valueOf(calibrationmasses.length) + "-" + String.valueOf(numbermisfitallowed) + ")";
                resultservice.insertresultrecord(inputtype, filegrootte_kbytes, massspectrometryfile, strtime,
                        experimentnumber, strquantilethreshold, exportname, strdatum, stryear);
            }
        }
        if (noisepeaks != null) {
            exportname = "noise_" + exportname;
            try {
                fileexportbuffer = new FileOutputStream(cc.userhome + cc.fileSeparator + exportname);
            } catch (Exception e) {
                if (cc.debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            peakstransposed = new double[noisepeaks[0].length][noisepeaks.length];
            for (int i = 0; i < noisepeaks.length; i++) {
                for (int j = 0; j < noisepeaks[0].length; j++) {
                    peakstransposed[j][i] = noisepeaks[i][j];
                }
            }
            sortrow = 0;
            sortmatrix = new SortMatrix(peakstransposed, sortrow);
            for (int i = 0; i < noisepeaks.length; i++) {
                for (int j = 0; j < noisepeaks[0].length; j++) {
                    noisepeaks[i][j] = peakstransposed[j][i];
                }
            }
            for (int i = 0; i <= noisepeaks.length - 1; i++) {
                tempBD = new BigDecimal(noisepeaks[i][0]);
                if (noisepeaks[i][0] > 0) {
                    try {
                        fileexportbuffer.write(
                                (tempBD.setScale(6, BigDecimal.ROUND_HALF_EVEN) + " " + noisepeaks[i][1] + linefeed)
                                        .getBytes());
                        fileexportbuffer.flush();
                    } catch (Exception e) {
                        if (cc.debugmode) {
                            e.printStackTrace();
                        } else {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
            filegrootte_kbytes = 1;
            filetransported = false;
            try {
                fileexportbuffer.close();
            } catch (IOException e) {
                if (cc.debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            try {
                inputpeaklist = new FileInputStream(cc.userhome + cc.fileSeparator + exportname);
                ftp.setRemoteHost(cc.ftpremotehost);
                ftp.connect();
                ftp.login(cc.ftpuser, cc.ftppassword);
                ftp.setConnectMode(FTPConnectMode.PASV);
                ftp.setTimeout(cc.ftp_longtime);
                ftp.setType(FTPTransferType.BINARY);
                ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
                ftp.put(inputpeaklist, exportname, false);
                inputpeaklist.close();
                filetransported = true;
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
                ;
            }
            if ((filetransported) && (!massspectrometryfile.getSampleid().trim().equals(""))
                    && (!massspectrometryfile.getGroupid().trim().equals(""))) {
                resultservice.insertresultrecord("noisepeaks", filegrootte_kbytes, massspectrometryfile, strtime,
                        experimentnumber, strquantilethreshold, exportname, strdatum, stryear);
            }
        }
    }

    private void datareductionfile() {
        ms2_sequencenced_masses_present = false;
        sequensing_results_vector = null;
        sequensing_results = null;
        noisepeaks = null;
        filenameexport = massspectrometryfile.getFilename().toLowerCase().replaceAll(".txt", "_txt");
        tmp_filenameexport = filenameexport;
        filenameexport = tmp_filenameexport.toLowerCase().replaceAll(".raw", "_raw");
        tmp_filenameexport = filenameexport;
        filenameexport = tmp_filenameexport.toLowerCase().replaceAll(".mzxml", "_mzxml");
        exportname = String.valueOf(quantilethreshold) + "_" + filenameexport + ".txt";
        if (((equipmentid == 1) || (equipmentid == 2)) && (experiment.getfiletype().trim().equalsIgnoreCase("txt"))) {
            readnumberoflines();
            peaks = new double[intnumberofmeasurements][3];
            fillmassesintensitiespeaklist();
            removedouble_masses_within_search_window();
            calibrated = true;
            exportpeakslist();
        }
        if ((equipmentid == 2) && experiment.getfiletype().trim().equalsIgnoreCase("fid")) {
            if (fft == null) {
                fft = new FastFourierTransform();
            }
            readparameters();
            if (ftmsdata == null) {
                ftmsdata = new FTMSdata(intnumberofmeasurements * zerofillingfactor);
                xim = new double[ftmsdata.data.length];
                intnumberofmeasurements_old = intnumberofmeasurements * zerofillingfactor;
            } else {
                if (intnumberofmeasurements_old == (intnumberofmeasurements * zerofillingfactor)) {
                    for (int s = 0; s < ftmsdata.data.length; s++) {
                        ftmsdata.data[s] = 0;
                        xim[s] = 0;
                    }
                } else {
                    ftmsdata = new FTMSdata(intnumberofmeasurements * zerofillingfactor);
                    xim = new double[ftmsdata.data.length];
                }
            }
            filltimesignalFTMS();
            if (performApodization) {
                aquisitiontime = intnumberofmeasurements / (2.0 * sweep);
                Apodization();
            }
            if (detect_mode == 1) {
                fillmassesFTMS();
                fillpeaks("createobject");
                if (peaks != null) {
                    calibrateinternalftms();
                    exportpeakslist();
                }
            } else if (detect_mode == 2) {
            } else {
            }
        }
        if ((equipmentid == 1) && experiment.getfiletype().trim().equalsIgnoreCase("fid")) {
            readparameters();
            spectrum = new Spectrum(intnumberofmeasurements);
            filltimesignal();
            if (zerofile) {
                resultservice.insertresultrecord("zero(data)", filegrootte_kbytes, massspectrometryfile, strtime,
                        experimentnumber, strquantilethreshold, exportname, strdatum, stryear);
            } else {
                fillmassesMALDITOF();
                fillpeaks("createobject");
                if (peaks != null) {
                    if (internalcalibration) {
                        calibrateinternalmalditof();
                        fillpeaks("");
                    } else {
                        calibrated = true;
                    }
                    exportpeakslist();
                }
            }
        }
        if (((equipmentid == 3) || (equipmentid == 4) || (equipmentid == 5))
                && experiment.getfiletype().trim().equalsIgnoreCase("txt")) {
            readnumberoflines();
            peaks = new double[intnumberofmeasurements][3];
            fillmassesintensitiespeaklist();
            calibrated = true;
            if (performtimealgnment) {
                timealignment();
            }
            exportpeakslist();
        }
        if ((equipmentid == 4) && experiment.getfiletype().trim().equalsIgnoreCase("raw")) {
            exportname = "f" + String.valueOf(minimumnumberoffractions) + "_m" + String.valueOf(missingfractionsallowed)
                    + "_" + exportname;
            filenumber++;
            intnumberofmeasurements = 0;
            massspectrometryfile.setFilenumber(filenumber);
            massspectrometryfile.setTmp_exportname("tmp_" + exportname);
            intnumberofmeasurements = thermoscientific.createpeaklist(massspectrometryfile);
            intnumberofmeasurementsraw = intnumberofmeasurements;
            if (intnumberofmeasurements > 0) {
                if (perform_ms2_sequencing) {
                    if (thermoscientific.sequensing_results_vector != null) {
                        this.sequensing_results_vector = thermoscientific.sequensing_results_vector;
                        if (sequensing_results_vector.size() > 0) {
                            ms2_sequencenced_masses_present = true;
                        }
                    }
                }
                calibrated = true;
                if (ms2_sequencenced_masses_present) {
                    peaks = new double[(intnumberofmeasurements + sequensing_results_vector.size())][8];
                    used_ms2_mass = new double[sequensing_results_vector.size()];
                    for (int i = 0; i < used_ms2_mass.length; i++) {
                        used_ms2_mass[i] = 0;
                    }
                } else {
                    peaks = new double[intnumberofmeasurements][7];
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
                    ftp.setTimeout(cc.ftp_longtime);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + this.experimentyear + File.separator + this.experimentnumber);
                    bytesfile = ftp.get("tmp_" + exportname);
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
                intnumberofmeasurements = 0;
                if (bytesfile.length > 0) {
                    line = "";
                    linebuffer = new StringBuffer(line);
                    for (int i = 0; i < bytesfile.length; i++) {
                        if ((bytesfile[i] != 10) && (bytesfile[i] != 13)) {
                            linebuffer.append((char) bytesfile[i]);
                        } else {
                            if (bytesfile[i] == 10) {
                                line = linebuffer.toString();
                                linearray = line.split("#");
                                peaks[intnumberofmeasurements][0] = -1;
                                peaks[intnumberofmeasurements][1] = 0;
                                peaks[intnumberofmeasurements][2] = 0;
                                peaks[intnumberofmeasurements][3] = 0;
                                peaks[intnumberofmeasurements][4] = 0;
                                peaks[intnumberofmeasurements][5] = 0;
                                peaks[intnumberofmeasurements][6] = 0;
                                try {
                                    peaks[intnumberofmeasurements][0] = Double.parseDouble(linearray[0]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][1] = Double.parseDouble(linearray[1]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][2] = Double.parseDouble(linearray[2]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][3] = Double.parseDouble(linearray[3]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][4] = Double.parseDouble(linearray[4]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][5] = Double.parseDouble(linearray[5]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][6] = Double.parseDouble(linearray[6]);
                                } catch (Exception e) {
                                }
                                if (ms2_sequencenced_masses_present) {
                                    peaks[intnumberofmeasurements][7] = -1;
                                    for (int j = 0; j < sequensing_results_vector.size(); j++) {
                                        sequensing_results = sequensing_results_vector.elementAt(j);
                                        if (sequensing_results != null) {
                                            ms2_mass = -1;
                                            double_ms2_retentiontime_min = -1;
                                            double_ms2_retentiontime_max = -1;
                                            try {
                                                ms2_mass = Double.parseDouble(sequensing_results[1]);
                                            } catch (Exception ex) {
                                            }
                                            try {
                                                double_ms2_retentiontime_min = Double
                                                        .parseDouble(sequensing_results[6]);
                                            } catch (Exception ex) {
                                            }
                                            try {
                                                double_ms2_retentiontime_max = Double
                                                        .parseDouble(sequensing_results[7]);
                                            } catch (Exception ex) {
                                            }
                                            if ((ms2_mass > -1) && (peaks[intnumberofmeasurements][0] > -1)) {
                                                deltamzlocal = deltamzcombine;
                                                if (clusteringtechnique == 1) {
                                                    deltamzlocal = deltamzcombine * peaks[intnumberofmeasurements][0]
                                                            / 1000000;
                                                }
                                                if (Math.abs(ms2_mass - peaks[intnumberofmeasurements][0]) < Math
                                                        .abs(deltamzlocal)) {
                                                    if ((double_ms2_retentiontime_min > -1)
                                                            && (double_ms2_retentiontime_max > -1)) {
                                                        ms2_within_time_window = false;
                                                        deltatimelocalcombine = deltatimecombine;
                                                        if (timeclusteringtechnique == 1) {
                                                            deltatimelocalcombine = deltatimecombine
                                                                    * peaks[intnumberofmeasurements][2] / 100;
                                                        }
                                                        if (Math.abs(double_ms2_retentiontime_min
                                                                - peaks[intnumberofmeasurements][6]) < deltatimelocalcombine) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if (Math.abs(double_ms2_retentiontime_max
                                                                - peaks[intnumberofmeasurements][5]) < deltatimelocalcombine) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if ((double_ms2_retentiontime_min <= peaks[intnumberofmeasurements][5])
                                                                && (double_ms2_retentiontime_max >= peaks[intnumberofmeasurements][5])) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if ((double_ms2_retentiontime_max >= peaks[intnumberofmeasurements][6])
                                                                && (double_ms2_retentiontime_min <= peaks[intnumberofmeasurements][6])) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if ((double_ms2_retentiontime_min >= peaks[intnumberofmeasurements][5])
                                                                && (double_ms2_retentiontime_max <= peaks[intnumberofmeasurements][6])) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if (ms2_within_time_window) {
                                                            peaks[intnumberofmeasurements][7] = j;
                                                            used_ms2_mass[j] = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                line = "";
                                linebuffer = new StringBuffer(line);
                                intnumberofmeasurements++;
                            }
                        }
                    }
                }
                if (ms2_sequencenced_masses_present) {
                    for (int j = 0; j < used_ms2_mass.length; j++) {
                        if (used_ms2_mass[j] == 0) {
                            sequensing_results = sequensing_results_vector.elementAt(j);
                            try {
                                ms2_mass = Double.parseDouble(sequensing_results[1]);
                                peaks[intnumberofmeasurements][0] = ms2_mass;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][0] = -1;
                            }
                            peaks[intnumberofmeasurements][1] = 0;
                            try {
                                double_ms2_retentiontime_min = Double.parseDouble(sequensing_results[6]);
                                peaks[intnumberofmeasurements][2] = double_ms2_retentiontime_min;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][2] = 0;
                            }
                            peaks[intnumberofmeasurements][3] = 1;
                            peaks[intnumberofmeasurements][4] = 1;
                            try {
                                double_ms2_retentiontime_min = Double.parseDouble(sequensing_results[6]);
                                peaks[intnumberofmeasurements][5] = double_ms2_retentiontime_min;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][5] = 0;
                            }
                            try {
                                double_ms2_retentiontime_max = Double.parseDouble(sequensing_results[7]);
                                peaks[intnumberofmeasurements][6] = double_ms2_retentiontime_max;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][6] = 0;
                            }
                            peaks[intnumberofmeasurements][7] = j;
                            used_ms2_mass[j] = 1;
                            intnumberofmeasurements++;
                        }
                    }
                }
            }
            low_time_alignment = null;
            if (performtimealgnment) {
                timealignment();
            }
            exportpeakslist();
            inputnoisepeaks = "tmp_" + exportname;
            save_exportname = exportname;
            exportname = "noise_" + exportname;
            resultrecordtype = "noisepeaks";
            exportnoisepeaks();
            exportname = save_exportname;
            if (thermoscientific.number_of_noise_peaks > 0) {
                intnumberofmeasurementsraw = thermoscientific.number_of_noise_peaks;
                inputnoisepeaks = "noise_tmp_" + exportname;
                exportname = "noise2_" + exportname;
                resultrecordtype = "noisepeaks_2";
                exportnoisepeaks();
            }
            exportname = save_exportname;
        }
        if ((equipmentid == 3) && experiment.getfiletype().trim().equalsIgnoreCase("xml")) {
            peaks = null;
            intnumberofmeasurements = 0;
            datareductionfileXML();
            if (intnumberofmeasurements > 0) {
                peaks = new double[intnumberofmeasurements][3];
                intnumberofmeasurements = 0;
                datareductionfileXML();
                calibrated = true;
                if (performtimealgnment) {
                    timealignment();
                }
                exportpeakslist();
            }
        }
        if ((equipmentid == 2) && experiment.getfiletype().trim().equalsIgnoreCase("xml")) {
            peaks = null;
            intnumberofmeasurements = 0;
            datareductionfileXML();
            if (intnumberofmeasurements > 0) {
                peaks = new double[intnumberofmeasurements][2];
                intnumberofmeasurements = 0;
                datareductionfileXML();
                calibrated = true;
                exportpeakslist();
            }
        }
        if (((equipmentid == 5) || (equipmentid == 4)) && experiment.getfiletype().trim().equalsIgnoreCase("mzxml")) {
            exportname = "f" + String.valueOf(minimumnumberoffractions) + "_m" + String.valueOf(missingfractionsallowed)
                    + "_" + exportname;
            filenumber++;
            massspectrometryfile.setFilenumber(filenumber);
            intnumberofmeasurements = 0;
            localmzxmlfilename = cc.userhome + "/localmzxmlfile.mzXML";
            store_mzxml_file_local();
            intnumberofmeasurements = readmzxml.store_peak_lists(localmzxmlfilename, "1", "tmp_" + exportname,
                    massspectrometryfile);
            intnumberofmeasurementsraw = intnumberofmeasurements;
            ms2_sequencenced_masses_present = false;
            if (intnumberofmeasurements > 0) {
                if (perform_ms2_sequencing) {
                    if (readmzxml.sequensing_results_vector != null) {
                        this.sequensing_results_vector = readmzxml.sequensing_results_vector;
                        if (sequensing_results_vector.size() > 0) {
                            ms2_sequencenced_masses_present = true;
                        }
                    }
                }
                calibrated = true;
                if (ms2_sequencenced_masses_present) {
                    peaks = new double[(intnumberofmeasurements + sequensing_results_vector.size())][8];
                    used_ms2_mass = new double[sequensing_results_vector.size()];
                    for (int i = 0; i < used_ms2_mass.length; i++) {
                        used_ms2_mass[i] = 0;
                    }
                } else {
                    peaks = new double[intnumberofmeasurements][7];
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
                    ftp.setTimeout(cc.ftp_longtime);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + this.experimentyear + File.separator + this.experimentnumber);
                    bytesfile = ftp.get("tmp_" + exportname);
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
                intnumberofmeasurements = 0;
                if (bytesfile.length > 0) {
                    line = "";
                    linebuffer = new StringBuffer(line);
                    for (int i = 0; i < bytesfile.length; i++) {
                        if ((bytesfile[i] != 10) && (bytesfile[i] != 13)) {
                            linebuffer.append((char) bytesfile[i]);
                        } else {
                            if (bytesfile[i] == 10) {
                                line = linebuffer.toString();
                                linearray = line.split("#");
                                peaks[intnumberofmeasurements][0] = -1;
                                peaks[intnumberofmeasurements][1] = 0;
                                peaks[intnumberofmeasurements][2] = 0;
                                peaks[intnumberofmeasurements][3] = 0;
                                peaks[intnumberofmeasurements][4] = 0;
                                peaks[intnumberofmeasurements][5] = 0;
                                peaks[intnumberofmeasurements][6] = 0;
                                try {
                                    peaks[intnumberofmeasurements][0] = Double.parseDouble(linearray[0]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][1] = Double.parseDouble(linearray[1]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][2] = Double.parseDouble(linearray[2]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][3] = Double.parseDouble(linearray[3]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][4] = Double.parseDouble(linearray[4]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][5] = Double.parseDouble(linearray[5]);
                                } catch (Exception e) {
                                }
                                try {
                                    peaks[intnumberofmeasurements][6] = Double.parseDouble(linearray[6]);
                                } catch (Exception e) {
                                }
                                if (ms2_sequencenced_masses_present) {
                                    peaks[intnumberofmeasurements][7] = -1;
                                    for (int j = 0; j < sequensing_results_vector.size(); j++) {
                                        sequensing_results = sequensing_results_vector.elementAt(j);
                                        if (sequensing_results != null) {
                                            ms2_mass = -1;
                                            double_ms2_retentiontime_min = -1;
                                            double_ms2_retentiontime_max = -1;
                                            try {
                                                ms2_mass = Double.parseDouble(sequensing_results[1]);
                                            } catch (Exception ex) {
                                            }
                                            try {
                                                double_ms2_retentiontime_min = Double
                                                        .parseDouble(sequensing_results[6]);
                                            } catch (Exception ex) {
                                            }
                                            try {
                                                double_ms2_retentiontime_max = Double
                                                        .parseDouble(sequensing_results[7]);
                                            } catch (Exception ex) {
                                            }
                                            if ((ms2_mass > -1) && (peaks[intnumberofmeasurements][0] > -1)) {
                                                deltamzlocal = deltamzcombine;
                                                if (clusteringtechnique == 1) {
                                                    deltamzlocal = deltamzcombine * peaks[intnumberofmeasurements][0]
                                                            / 1000000;
                                                }
                                                if (Math.abs(ms2_mass - peaks[intnumberofmeasurements][0]) < Math
                                                        .abs(deltamzlocal)) {
                                                    if ((double_ms2_retentiontime_min > -1)
                                                            && (double_ms2_retentiontime_max > -1)) {
                                                        ms2_within_time_window = false;
                                                        deltatimelocalcombine = deltatimecombine;
                                                        if (timeclusteringtechnique == 1) {
                                                            deltatimelocalcombine = deltatimecombine
                                                                    * peaks[intnumberofmeasurements][2] / 100;
                                                        }
                                                        if (Math.abs(double_ms2_retentiontime_min
                                                                - peaks[intnumberofmeasurements][6]) < deltatimelocalcombine) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if (Math.abs(double_ms2_retentiontime_max
                                                                - peaks[intnumberofmeasurements][5]) < deltatimelocalcombine) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if ((double_ms2_retentiontime_min <= peaks[intnumberofmeasurements][5])
                                                                && (double_ms2_retentiontime_max >= peaks[intnumberofmeasurements][5])) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if ((double_ms2_retentiontime_max >= peaks[intnumberofmeasurements][6])
                                                                && (double_ms2_retentiontime_min <= peaks[intnumberofmeasurements][6])) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if ((double_ms2_retentiontime_min >= peaks[intnumberofmeasurements][5])
                                                                && (double_ms2_retentiontime_max <= peaks[intnumberofmeasurements][6])) {
                                                            ms2_within_time_window = true;
                                                        }
                                                        if (ms2_within_time_window) {
                                                            peaks[intnumberofmeasurements][7] = j;
                                                            used_ms2_mass[j] = 1;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                line = "";
                                linebuffer = new StringBuffer(line);
                                intnumberofmeasurements++;
                            }
                        }
                    }
                }
                if (ms2_sequencenced_masses_present) {
                    for (int j = 0; j < used_ms2_mass.length; j++) {
                        if (used_ms2_mass[j] == 0) {
                            sequensing_results = sequensing_results_vector.elementAt(j);
                            try {
                                ms2_mass = Double.parseDouble(sequensing_results[1]);
                                peaks[intnumberofmeasurements][0] = ms2_mass;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][0] = -1;
                            }
                            peaks[intnumberofmeasurements][1] = 0;
                            try {
                                double_ms2_retentiontime_min = Double.parseDouble(sequensing_results[6]);
                                peaks[intnumberofmeasurements][2] = double_ms2_retentiontime_min;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][2] = 0;
                            }
                            peaks[intnumberofmeasurements][3] = 1;
                            peaks[intnumberofmeasurements][4] = 1;
                            try {
                                double_ms2_retentiontime_min = Double.parseDouble(sequensing_results[6]);
                                peaks[intnumberofmeasurements][5] = double_ms2_retentiontime_min;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][5] = 0;
                            }
                            try {
                                double_ms2_retentiontime_max = Double.parseDouble(sequensing_results[7]);
                                peaks[intnumberofmeasurements][6] = double_ms2_retentiontime_max;
                            } catch (Exception exc) {
                                peaks[intnumberofmeasurements][6] = 0;
                            }
                            peaks[intnumberofmeasurements][7] = j;
                            used_ms2_mass[j] = 1;
                            intnumberofmeasurements++;
                        }
                    }
                }
            }
            low_time_alignment = null;
            if (performtimealgnment) {
                if ((peaks != null) && (peaks.length > 0)) {
                    timealignment();
                }
            }
            exportpeakslist();
            inputnoisepeaks = "tmp_" + exportname;
            save_exportname = exportname;
            exportname = "noise_" + exportname;
            resultrecordtype = "noisepeaks";
            exportnoisepeaks();
            exportname = save_exportname;
            if (readmzxml.return_number_of_noisemasses() > 0) {
                intnumberofmeasurementsraw = readmzxml.return_number_of_noisemasses();
                inputnoisepeaks = "noise_tmp_" + exportname;
                exportname = "noise2_" + exportname;
                resultrecordtype = "noisepeaks_2";
                exportnoisepeaks();
            }
            exportname = save_exportname;
        }
    }

    private void exportnoisepeaks() {
        try {
            fileexportbuffer = new FileOutputStream(cc.userhome + cc.fileSeparator + exportname);
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (intnumberofmeasurementsraw > 0) {
            peaks = new double[intnumberofmeasurementsraw][7];
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
                ftp.chdir(File.separator + this.experimentyear + File.separator + this.experimentnumber);
                bytesfile = ftp.get(inputnoisepeaks);
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
            intnumberofmeasurements = 0;
            if (bytesfile.length > 0) {
                line = "";
                linebuffer = new StringBuffer(line);
                for (int i = 0; i < bytesfile.length; i++) {
                    if ((bytesfile[i] != 10) && (bytesfile[i] != 13)) {
                        linebuffer.append((char) bytesfile[i]);
                    } else {
                        if (bytesfile[i] == 10) {
                            line = linebuffer.toString();
                            linearray = line.split("#");
                            peaks[intnumberofmeasurements][0] = -1;
                            peaks[intnumberofmeasurements][1] = 0;
                            peaks[intnumberofmeasurements][2] = 0;
                            peaks[intnumberofmeasurements][3] = 0;
                            peaks[intnumberofmeasurements][4] = 0;
                            peaks[intnumberofmeasurements][5] = 0;
                            peaks[intnumberofmeasurements][6] = 0;
                            try {
                                peaks[intnumberofmeasurements][0] = Double.parseDouble(linearray[0]);
                            } catch (Exception e) {
                            }
                            try {
                                peaks[intnumberofmeasurements][1] = Double.parseDouble(linearray[1]);
                            } catch (Exception e) {
                            }
                            try {
                                peaks[intnumberofmeasurements][2] = Double.parseDouble(linearray[2]);
                            } catch (Exception e) {
                            }
                            try {
                                peaks[intnumberofmeasurements][3] = Double.parseDouble(linearray[3]);
                            } catch (Exception e) {
                            }
                            try {
                                peaks[intnumberofmeasurements][4] = Double.parseDouble(linearray[4]);
                            } catch (Exception e) {
                            }
                            try {
                                peaks[intnumberofmeasurements][5] = Double.parseDouble(linearray[5]);
                            } catch (Exception e) {
                            }
                            try {
                                peaks[intnumberofmeasurements][6] = Double.parseDouble(linearray[6]);
                            } catch (Exception e) {
                            }
                            line = "";
                            linebuffer = new StringBuffer(line);
                            intnumberofmeasurements++;
                        }
                    }
                }
                for (int i = 0; i < peaks.length; i++) {
                    if ((performtimealgnment) && (low_time_alignment != null)) {
                        for (int d = 0; d < low_time_alignment.length; d++) {
                            if ((low_time_alignment[d] > -1) || (high_time_alignment[d] > -1)) {
                                if (high_time_alignment[d] == -1) {
                                    if (peaks[i][2] > low_time_alignment[d]) {
                                        peaks[i][2] = peaks[i][2] - ((a1_time_alignment[d] * peaks[i][2] * peaks[i][2])
                                                + (a2_time_alignment[d] * peaks[i][2]) + a0_time_alignment[d]);
                                        peaks[i][5] = peaks[i][5] - ((a1_time_alignment[d] * peaks[i][5] * peaks[i][5])
                                                + (a2_time_alignment[d] * peaks[i][5]) + a0_time_alignment[d]);
                                        peaks[i][6] = peaks[i][6] - ((a1_time_alignment[d] * peaks[i][6] * peaks[i][6])
                                                + (a2_time_alignment[d] * peaks[i][6]) + a0_time_alignment[d]);
                                    }
                                } else {
                                    if ((peaks[i][2] > low_time_alignment[d])
                                            && (peaks[i][2] <= high_time_alignment[d])) {
                                        peaks[i][2] = peaks[i][2] - ((a1_time_alignment[d] * peaks[i][2] * peaks[i][2])
                                                + (a2_time_alignment[d] * peaks[i][2]) + a0_time_alignment[d]);
                                        peaks[i][5] = peaks[i][5] - ((a1_time_alignment[d] * peaks[i][5] * peaks[i][5])
                                                + (a2_time_alignment[d] * peaks[i][5]) + a0_time_alignment[d]);
                                        peaks[i][6] = peaks[i][6] - ((a1_time_alignment[d] * peaks[i][6] * peaks[i][6])
                                                + (a2_time_alignment[d] * peaks[i][6]) + a0_time_alignment[d]);
                                    }
                                }
                            }
                        }
                    }
                }
                peakstransposed = new double[peaks[0].length][peaks.length];
                for (int i = 0; i < peaks.length; i++) {
                    for (int j = 0; j < peaks[0].length; j++) {
                        peakstransposed[j][i] = peaks[i][j];
                    }
                }
                sortrow = 0;
                sortmatrix = new SortMatrix(peakstransposed, sortrow);
                for (int i = 0; i < peaks.length; i++) {
                    for (int j = 0; j < peaks[0].length; j++) {
                        peaks[i][j] = peakstransposed[j][i];
                    }
                }
                for (int i = 0; i <= peaks.length - 1; i++) {
                    try {
                        tempBD = new BigDecimal(peaks[i][0]);
                    } catch (Exception e) {
                    }
                    if (peaks[i][0] > 0) {
                        try {
                            fileexportbuffer.write(
                                    (tempBD.setScale(6, BigDecimal.ROUND_HALF_EVEN) + " " + peaks[i][1]).getBytes());
                            fileexportbuffer.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if ((massspectrometryfile.getOffset_lc_ms() != 0) && (!performtimealgnment)) {
                            peaks[i][2] = peaks[i][2] + massspectrometryfile.getOffset_lc_ms();
                        }
                        try {
                            fileexportbuffer.write(
                                    (" " + peaks[i][2] + " " + peaks[i][5] + " " + peaks[i][6] + linefeed).getBytes());
                            fileexportbuffer.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                filegrootte_kbytes = 1;
                filetransported = false;
                try {
                    ftp.quit();
                } catch (Exception e) {
                    ;
                }
                try {
                    fileexportbuffer.close();
                } catch (IOException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                try {
                    ftp.setRemoteHost(cc.ftpremotehost);
                    ftp.connect();
                    ftp.login(cc.ftpuser, cc.ftppassword);
                    ftp.setConnectMode(FTPConnectMode.PASV);
                    ftp.setTimeout(cc.ftp_longtime);
                    ftp.setType(FTPTransferType.BINARY);
                    ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
                    inputpeaklist = new FileInputStream(cc.userhome + cc.fileSeparator + exportname);
                    BufferedReader in = new BufferedReader(new InputStreamReader(inputpeaklist));
                    line = "";
                    ftp.put(line.getBytes(), exportname, false);
                    countftp = 0;
                    while (in.ready()) {
                        line = line + in.readLine() + linefeed;
                        if (countftp > 1000) {
                            try {
                                ftp.put(line.getBytes(), exportname, true);
                                line = "";
                                countftp = 0;
                            } catch (Exception ex2) {
                                try {
                                    ftp.quit();
                                } catch (Exception ex3) {
                                    ;
                                }
                                try {
                                    ftp.setRemoteHost(cc.ftpremotehost);
                                    ftp.connect();
                                    ftp.login(cc.ftpuser, cc.ftppassword);
                                    ftp.setConnectMode(FTPConnectMode.PASV);
                                    ftp.setTimeout(cc.ftp_longtime);
                                    ftp.setType(FTPTransferType.BINARY);
                                    ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
                                } catch (Exception ex4) {
                                    ;
                                }
                            }
                        }
                        countftp++;
                    }
                    try {
                        ftp.quit();
                    } catch (Exception ex3) {
                        ;
                    }
                    try {
                        ftp.setRemoteHost(cc.ftpremotehost);
                        ftp.connect();
                        ftp.login(cc.ftpuser, cc.ftppassword);
                        ftp.setConnectMode(FTPConnectMode.PASV);
                        ftp.setTimeout(cc.ftp_longtime);
                        ftp.setType(FTPTransferType.BINARY);
                        ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
                    } catch (Exception ex4) {
                        ;
                    }
                    ftp.put(line.getBytes(), exportname, true);
                    inputpeaklist.close();
                    filetransported = true;
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
                    ;
                }
                if ((filetransported) && (!massspectrometryfile.getSampleid().trim().equals(""))
                        && (!massspectrometryfile.getGroupid().trim().equals(""))) {
                    resultservice.insertresultrecord(resultrecordtype, filegrootte_kbytes, massspectrometryfile,
                            strtime, experimentnumber, strquantilethreshold, exportname, strdatum, stryear);
                }
            }
        }
    }

    private void store_mzxml_file_local() {
        try {
            ftp.quit();
        } catch (Exception e) {
        }
        try {
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + experimentyear + File.separator + experimentnumber);
            ftp.get(localmzxmlfilename, massspectrometryfile.getFilename());
        } catch (Exception e) {
        }
        try {
            ftp.quit();
        } catch (Exception e) {
        }
    }

    private void timealignment() {
        str_aligned = "aligned";
        alignment_time = new String[peaks.length];
        delta_time = new double[peaks.length];
        deltamzlocal = deltamzcombine;
        if (timealignmentbuffermassnew == null) {
            timealignmentbuffermassnew = new StringBuffer("");
            timealignmentbufferretentiontimenew = new StringBuffer("");
            timealignmentbuffersequencenew = new StringBuffer("");
            if (timealignmentreferencemass != null) {
                try {
                    timealignmentreferencemass.close();
                } catch (IOException e) {
                }
            }
            if (timealignmentreferenceretentiontime != null) {
                try {
                    timealignmentreferenceretentiontime.close();
                } catch (IOException e) {
                }
            }
            if (timealignmentreferencesequence != null) {
                try {
                    timealignmentreferencesequence.close();
                } catch (IOException e) {
                }
            }
            nametimealignmentreferencemass = "E" + experimentnumber + "timealignmentreferencemass.txt";
            nametimealignmentreferenceretentiontime = "E" + experimentnumber
                    + "timealignmentreferenceretentiontime.txt";
            nametimealignmentreferencesequence = "E" + experimentnumber + "timealignmentreferencesequence.txt";
            try {
                timealignmentreferencemass = new FileOutputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferencemass);
                timealignmentreferenceretentiontime = new FileOutputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferenceretentiontime);
                timealignmentreferencesequence = new FileOutputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferencesequence);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            firsttimereferenceexport = true;
            for (int i = 0; i < peaks.length; i++) {
                if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                    if (peaks[i][7] > -1) {
                        int j = (int) peaks[i][7];
                        sequensing_results = sequensing_results_vector.elementAt(j);
                        sequencing_results_string = sequensing_results[0].trim().replaceAll(",", "");
                        if (!sequencing_results_string.trim().equalsIgnoreCase("")) {
                            alignmentmassfound = true;
                            if (clusteringtechnique == 1) {
                                deltamzlocal = deltamzcombine * peaks[i][0] / 1000000;
                            }
                            int k = i;
                            while (k > 0) {
                                k--;
                                if (Math.abs(peaks[i][0] - peaks[k][0]) < Math.abs(deltamzlocal)) {
                                    if (peaks[k][1] > peaks[i][1]) {
                                        if (peaks[k][7] > -1) {
                                            int t = (int) peaks[k][7];
                                            if (j == t) {
                                                alignmentmassfound = false;
                                            }
                                        }
                                    }
                                } else {
                                    k = 0;
                                }
                            }
                            k = i;
                            while (k < (peaks.length - 1)) {
                                k++;
                                if (Math.abs(peaks[i][0] - peaks[k][0]) < Math.abs(deltamzlocal)) {
                                    if (peaks[k][1] > peaks[i][1]) {
                                        if (peaks[k][7] > -1) {
                                            int t = (int) peaks[k][7];
                                            if (j == t) {
                                                alignmentmassfound = false;
                                            }
                                        }
                                    }
                                } else {
                                    k = peaks.length - 1;
                                }
                            }
                            if (alignmentmassfound) {
                                if (peaks[i][0] > 0) {
                                    if (firsttimereferenceexport) {
                                        firsttimereferenceexport = false;
                                        timealignmentbuffermassnew.append(String.valueOf(peaks[i][0]).trim());
                                        timealignmentbufferretentiontimenew.append(String.valueOf(peaks[i][2]).trim());
                                        timealignmentbuffersequencenew.append(sequencing_results_string);
                                    } else {
                                        timealignmentbuffermassnew.append("," + String.valueOf(peaks[i][0]).trim());
                                        timealignmentbufferretentiontimenew
                                                .append("," + String.valueOf(peaks[i][2]).trim());
                                        timealignmentbuffersequencenew.append("," + sequencing_results_string);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (i == 0) {
                        timealignmentbuffermassnew.append(String.valueOf(peaks[i][0]).trim());
                        timealignmentbufferretentiontimenew.append(String.valueOf(peaks[i][2]).trim());
                    } else {
                        timealignmentbuffermassnew.append("," + String.valueOf(peaks[i][0]).trim());
                        timealignmentbufferretentiontimenew.append("," + String.valueOf(peaks[i][2]).trim());
                    }
                }
            }
            data = (timealignmentbuffermassnew.toString() + "#").getBytes();
            if (timealignmentreferencemass != null) {
                try {
                    timealignmentreferencemass.write(data);
                    timealignmentreferencemass.flush();
                    timealignmentreferencemass.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            data = (timealignmentbufferretentiontimenew.toString() + "#").getBytes();
            if (timealignmentreferenceretentiontime != null) {
                try {
                    timealignmentreferenceretentiontime.write(data);
                    timealignmentreferenceretentiontime.flush();
                    timealignmentreferenceretentiontime.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                data = (timealignmentbuffersequencenew.toString() + "#").getBytes();
                if (timealignmentreferencesequence != null) {
                    try {
                        timealignmentreferencesequence.write(data);
                        timealignmentreferencesequence.flush();
                        timealignmentreferencesequence.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (timealignmentreferencesequence != null) {
                try {
                    timealignmentreferencesequence.flush();
                    timealignmentreferencesequence.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            sortrow = 0;
            max_time_difference = 0;
            if (inputalignmentreferencemass != null) {
                try {
                    inputalignmentreferencemass.close();
                } catch (IOException e) {
                }
                inputalignmentreferencemass = null;
            }
            if (inputalignmentreferenceretentiontime != null) {
                try {
                    inputalignmentreferenceretentiontime.close();
                } catch (IOException e) {
                }
                inputalignmentreferenceretentiontime = null;
            }
            if (inputalignmentreferencesequence != null) {
                try {
                    inputalignmentreferencesequence.close();
                } catch (IOException e) {
                }
                inputalignmentreferencesequence = null;
            }
            try {
                inputalignmentreferencemass = new FileInputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferencemass);
                inputalignmentreferenceretentiontime = new FileInputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferenceretentiontime);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                try {
                    inputalignmentreferencesequence = new FileInputStream(
                            cc.userhome + cc.fileSeparator + nametimealignmentreferencesequence);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            timealignmentbuffermassnew = new StringBuffer("");
            timealignmentbufferretentiontimenew = new StringBuffer("");
            timealignmentbuffersequencenew = new StringBuffer("");
            for (int i = 0; i < peaks.length; i++) {
                alignment_time[i] = "no";
                if (peaks[i][0] > 0) {
                } else {
                    peaks[i][0] = -1;
                }
            }
            peakstransposed = new double[peaks[0].length][peaks.length];
            for (int i = 0; i < peaks.length; i++) {
                for (int j = 0; j < peaks[0].length; j++) {
                    peakstransposed[j][i] = peaks[i][j];
                }
            }
            sortrow = 0;
            sortmatrix = new SortMatrix(peakstransposed, sortrow);
            for (int i = 0; i < peaks.length; i++) {
                for (int j = 0; j < peaks[0].length; j++) {
                    peaks[i][j] = peakstransposed[j][i];
                }
            }
            try {
                while ((ch = inputalignmentreferencemass.read()) != -1) {
                    timealignmentbuffermassnew.append((char) ch);
                    if (ch == 35) {
                        while ((ch2 = inputalignmentreferenceretentiontime.read()) != -1) {
                            timealignmentbufferretentiontimenew.append((char) ch2);
                            if (ch2 == 35) {
                                break;
                            }
                        }
                        if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                            while ((ch3 = inputalignmentreferencesequence.read()) != -1) {
                                timealignmentbuffersequencenew.append((char) ch3);
                                if (ch3 == 35) {
                                    break;
                                }
                            }
                            arraysequences = timealignmentbuffersequencenew.toString()
                                    .substring(0, timealignmentbuffersequencenew.toString().length() - 1).split(",");
                        }
                        arrayalignmentmasses = timealignmentbuffermassnew.toString()
                                .substring(0, timealignmentbuffermassnew.toString().length() - 1).split(",");
                        arrayretentiontimes = timealignmentbufferretentiontimenew.toString()
                                .substring(0, timealignmentbufferretentiontimenew.toString().length() - 1).split(",");
                        if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                            doublearraycombinedpeaks = new double[3][arrayalignmentmasses.length];
                        } else {
                            doublearraycombinedpeaks = new double[2][arrayalignmentmasses.length];
                        }
                        for (int i = 0; i < arrayalignmentmasses.length; i++) {
                            if (!arrayalignmentmasses[i].trim().equalsIgnoreCase("")) {
                                doublearraycombinedpeaks[0][i] = Double.valueOf(arrayalignmentmasses[i]).doubleValue();
                            } else {
                                doublearraycombinedpeaks[0][i] = -1;
                            }
                            if (!arrayretentiontimes[i].trim().equalsIgnoreCase("")) {
                                doublearraycombinedpeaks[1][i] = Double.valueOf(arrayretentiontimes[i]).doubleValue();
                            } else {
                                doublearraycombinedpeaks[1][i] = -1;
                            }
                            if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                                doublearraycombinedpeaks[2][i] = i;
                            }
                        }
                        sortmatrix = new SortMatrix(doublearraycombinedpeaks, sortrow);
                        for (int j = 0; j < peaks.length; j++) {
                            alignmentmassfound = false;
                            for (int i = 0; i < doublearraycombinedpeaks[0].length; i++) {
                                referencemass = doublearraycombinedpeaks[0][i];
                                referenceretentiontime = doublearraycombinedpeaks[1][i];
                                if (clusteringtechnique == 1) {
                                    deltamzlocal = deltamzcombine * referencemass / 1000000;
                                }
                                referencemass = doublearraycombinedpeaks[0][i];
                                referenceretentiontime = doublearraycombinedpeaks[1][i];
                                if (clusteringtechnique == 1) {
                                    deltamzlocal = deltamzcombine * referencemass / 1000000;
                                }
                                if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                                    if (Math.abs(referencemass - peaks[j][0]) < Math.abs(deltamzlocal)) {
                                        alignmentmassfound = true;
                                        if (alignmentmassfound) {
                                            if (peaks[j][7] > -1) {
                                                int o = (int) peaks[j][7];
                                                sequensing_results = sequensing_results_vector.elementAt(o);
                                                sequencing_results_string = sequensing_results[0].trim().replaceAll(",",
                                                        "");
                                                if (!sequencing_results_string.trim().equalsIgnoreCase("")) {
                                                    if (peaks[j][0] > 0) {
                                                        int testint = -1;
                                                        try {
                                                            testint = (int) doublearraycombinedpeaks[2][i];
                                                        } catch (Exception ex) {
                                                        }
                                                        if (testint > -1) {
                                                            if (sequencing_results_string.toLowerCase().trim()
                                                                    .equalsIgnoreCase(arraysequences[testint]
                                                                            .toLowerCase().trim())) {
                                                                int k = j;
                                                                while (k > 0) {
                                                                    k--;
                                                                    if (Math.abs(referencemass - peaks[k][0]) < Math
                                                                            .abs(deltamzlocal)) {
                                                                        if (peaks[k][7] > -1) {
                                                                            o = (int) peaks[k][7];
                                                                            sequensing_results = sequensing_results_vector
                                                                                    .elementAt(o);
                                                                            sequencing_results_string = sequensing_results[0]
                                                                                    .trim().replaceAll(",", "");
                                                                            if (sequencing_results_string.toLowerCase()
                                                                                    .trim().equalsIgnoreCase(
                                                                                            arraysequences[testint]
                                                                                                    .toLowerCase()
                                                                                                    .trim())) {
                                                                                if (peaks[k][1] > peaks[j][1]) {
                                                                                    alignmentmassfound = false;
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        k = 0;
                                                                    }
                                                                }
                                                                k = j;
                                                                while (k < (peaks.length - 1)) {
                                                                    k++;
                                                                    if (Math.abs(referencemass - peaks[k][0]) < Math
                                                                            .abs(deltamzlocal)) {
                                                                        if (peaks[k][7] > -1) {
                                                                            o = (int) peaks[k][7];
                                                                            sequensing_results = sequensing_results_vector
                                                                                    .elementAt(o);
                                                                            sequencing_results_string = sequensing_results[0]
                                                                                    .trim().replaceAll(",", "");
                                                                            if (sequencing_results_string.toLowerCase()
                                                                                    .trim().equalsIgnoreCase(
                                                                                            arraysequences[testint]
                                                                                                    .toLowerCase()
                                                                                                    .trim())) {
                                                                                if (peaks[k][1] > peaks[j][1]) {
                                                                                    alignmentmassfound = false;
                                                                                }
                                                                            }
                                                                        }
                                                                    } else {
                                                                        k = peaks.length - 1;
                                                                    }
                                                                }
                                                            } else {
                                                                alignmentmassfound = false;
                                                            }
                                                            if (alignmentmassfound) {
                                                                if (alignment_time[j].toString()
                                                                        .equalsIgnoreCase("yes")) {
                                                                    if (Math.abs(
                                                                            peaks[j][2] - referenceretentiontime) < Math
                                                                            .abs(delta_time[j])) {
                                                                        delta_time[j] = peaks[j][2]
                                                                                - referenceretentiontime;
                                                                    }
                                                                } else {
                                                                    alignment_time[j] = "yes";
                                                                    delta_time[j] = peaks[j][2]
                                                                            - referenceretentiontime;
                                                                }
                                                                if (Math.abs(delta_time[j]) > max_time_difference) {
                                                                    max_time_difference = Math.abs(delta_time[j]);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (Math.abs(referencemass - peaks[j][0]) < Math.abs(deltamzlocal)) {
                                        alignmentmassfound = true;
                                        int k = j;
                                        while (k > 0) {
                                            k--;
                                            if (Math.abs(referencemass - peaks[k][0]) < Math.abs(deltamzlocal)) {
                                                if (Math.abs(referenceretentiontime - peaks[k][2]) < Math
                                                        .abs(referenceretentiontime - peaks[j][2])) {
                                                    alignmentmassfound = false;
                                                }
                                            } else {
                                                k = 0;
                                            }
                                        }
                                        k = j;
                                        while (k < (peaks.length - 1)) {
                                            k++;
                                            if (Math.abs(referencemass - peaks[k][0]) < Math.abs(deltamzlocal)) {
                                                if (Math.abs(referenceretentiontime - peaks[k][2]) < Math
                                                        .abs(referenceretentiontime - peaks[j][2])) {
                                                    alignmentmassfound = false;
                                                }
                                            } else {
                                                k = peaks.length - 1;
                                            }
                                        }
                                    } else {
                                        alignmentmassfound = false;
                                    }
                                    if (alignmentmassfound) {
                                        int k = i;
                                        while (k > 0) {
                                            k--;
                                            if (Math.abs(doublearraycombinedpeaks[0][k] - peaks[j][0]) < Math
                                                    .abs(deltamzlocal)) {
                                                if (Math.abs(doublearraycombinedpeaks[1][k] - peaks[j][2]) < Math
                                                        .abs(referenceretentiontime - peaks[j][2])) {
                                                    alignmentmassfound = false;
                                                }
                                            } else {
                                                k = 0;
                                            }
                                        }
                                        k = i;
                                        while (k < (doublearraycombinedpeaks[0].length - 1)) {
                                            k++;
                                            if (Math.abs(doublearraycombinedpeaks[0][k] - peaks[j][0]) < Math
                                                    .abs(deltamzlocal)) {
                                                if (Math.abs(doublearraycombinedpeaks[1][k] - peaks[j][2]) < Math
                                                        .abs(referenceretentiontime - peaks[j][2])) {
                                                    alignmentmassfound = false;
                                                }
                                            } else {
                                                k = doublearraycombinedpeaks[0].length - 1;
                                            }
                                        }
                                        if (alignmentmassfound) {
                                            if (alignment_time[j].toString().equalsIgnoreCase("yes")) {
                                                if (Math.abs(peaks[j][2] - referenceretentiontime) < Math
                                                        .abs(delta_time[j])) {
                                                    delta_time[j] = peaks[j][2] - referenceretentiontime;
                                                }
                                            } else {
                                                alignment_time[j] = "yes";
                                                delta_time[j] = peaks[j][2] - referenceretentiontime;
                                            }
                                            if (Math.abs(delta_time[j]) > max_time_difference) {
                                                max_time_difference = Math.abs(delta_time[j]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        timealignmentbuffermassnew = new StringBuffer("");
                        timealignmentbufferretentiontimenew = new StringBuffer("");
                        timealignmentbuffersequencenew = new StringBuffer("");
                    }
                }
            } catch (IOException e1) {
            }
            n_time_alignment = 0;
            double low_time = -1;
            double high_time = -1;
            boolean valid_time_window;
            low_time_alignment = new double[inputgradientchange.length + 1];
            high_time_alignment = new double[inputgradientchange.length + 1];
            a0_time_alignment = new double[inputgradientchange.length + 1];
            a1_time_alignment = new double[inputgradientchange.length + 1];
            a2_time_alignment = new double[inputgradientchange.length + 1];
            for (int d = 0; d <= inputgradientchange.length; d++) {
                low_time_alignment[d] = -1;
                high_time_alignment[d] = -1;
                a0_time_alignment[d] = 0;
                a1_time_alignment[d] = 0;
                a2_time_alignment[d] = 0;
                valid_time_window = false;
                if (d == inputgradientchange.length) {
                    if (low_time == -1) {
                        valid_time_window = true;
                        low_time = 0;
                        high_time = -1;
                    }
                    if (inputgradientchange[d - 1] > 0) {
                        valid_time_window = true;
                        low_time = cc.second_per_minute * inputgradientchange[d - 1];
                        high_time = -1;
                    }
                } else if (inputgradientchange[d] > 0) {
                    if (low_time == -1) {
                        low_time = 0;
                    } else if (low_time == 0) {
                        low_time = cc.second_per_minute * inputgradientchange[d - 1];
                    } else {
                    }
                    high_time = cc.second_per_minute * inputgradientchange[d];
                    valid_time_window = true;
                    if (low_time == high_time) {
                        valid_time_window = false;
                    }
                }
                if (valid_time_window) {
                    for (int j = 0; j < peaks.length; j++) {
                        if (alignment_time[j].equalsIgnoreCase("yes")) {
                            if (peaks[j][2] >= low_time) {
                                if (high_time == -1) {
                                    n_time_alignment++;
                                } else {
                                    if (peaks[j][2] < high_time) {
                                        n_time_alignment++;
                                    }
                                }
                            }
                        }
                    }
                    if (n_time_alignment >= numberpointstimealignment) {
                        calibration_time = new double[n_time_alignment];
                        calibration_time_new = new double[2][n_time_alignment];
                        calibration_delta_time = new double[n_time_alignment];
                        n_time_alignment = 0;
                        for (int j = 0; j < peaks.length; j++) {
                            if (alignment_time[j].equalsIgnoreCase("yes")) {
                                if (peaks[j][2] >= low_time) {
                                    if (high_time == -1) {
                                        calibration_time[n_time_alignment] = peaks[j][2];
                                        calibration_time_new[0][n_time_alignment] = peaks[j][2];
                                        calibration_delta_time[n_time_alignment] = delta_time[j];
                                        calibration_time_new[1][n_time_alignment] = delta_time[j];
                                        n_time_alignment++;
                                    } else {
                                        if (peaks[j][2] < high_time) {
                                            calibration_time[n_time_alignment] = peaks[j][2];
                                            calibration_time_new[0][n_time_alignment] = peaks[j][2];
                                            calibration_delta_time[n_time_alignment] = delta_time[j];
                                            calibration_time_new[1][n_time_alignment] = delta_time[j];
                                            n_time_alignment++;
                                        }
                                    }
                                }
                            }
                        }
                        a = calibrationquadratic.quadratic(calibration_delta_time, calibration_time);
                        sum_distances = 0;
                        distance = 0;
                        sum_square_distances = 0;
                        distance_count = 0;
                        variance_distances = 0;
                        standard_deviation_distance = 0;
                        average_distances = 0;
                        for (int j = 0; j < peaks.length; j++) {
                            if (alignment_time[j].equalsIgnoreCase("yes")) {
                                if (high_time == -1) {
                                    distance_count++;
                                    distance = Math.abs(delta_time[j]
                                            - ((a[1] * peaks[j][2] * peaks[j][2]) + (a[2] * peaks[j][2]) + a[0]));
                                    sum_distances = sum_distances + distance;
                                    sum_square_distances = sum_square_distances + (distance * distance);
                                } else {
                                    if (peaks[j][2] < high_time) {
                                        distance_count++;
                                        distance = Math.abs(delta_time[j]
                                                - ((a[1] * peaks[j][2] * peaks[j][2]) + (a[2] * peaks[j][2]) + a[0]));
                                        sum_distances = sum_distances + distance;
                                        sum_square_distances = sum_square_distances + (distance * distance);
                                    }
                                }
                            }
                        }
                        average_distances = sum_distances / distance_count;
                        variance_distances = (sum_square_distances
                                - (distance_count * average_distances * average_distances)) / (distance_count - 1);
                        standard_deviation_distance = Math.sqrt(variance_distances);
                        n_time_alignment = 0;
                        for (int j = 0; j < peaks.length; j++) {
                            if (alignment_time[j].equalsIgnoreCase("yes")) {
                                if (peaks[j][2] >= low_time) {
                                    if (high_time == -1) {
                                        distance = Math.abs(delta_time[j]
                                                - ((a[1] * peaks[j][2] * peaks[j][2]) + (a[2] * peaks[j][2]) + a[0]));
                                        if (distance < (2 * standard_deviation_distance)) {
                                            n_time_alignment++;
                                        }
                                    } else {
                                        if (peaks[j][2] < high_time) {
                                            distance = Math.abs(delta_time[j] - ((a[1] * peaks[j][2] * peaks[j][2])
                                                    + (a[2] * peaks[j][2]) + a[0]));
                                            if (distance < (2 * standard_deviation_distance)) {
                                                n_time_alignment++;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (n_time_alignment >= numberpointstimealignment) {
                            calibration_time = new double[n_time_alignment];
                            calibration_delta_time = new double[n_time_alignment];
                            n_time_alignment = 0;
                            for (int j = 0; j < peaks.length; j++) {
                                if (alignment_time[j].equalsIgnoreCase("yes")) {
                                    if (peaks[j][2] >= low_time) {
                                        if (high_time == -1) {
                                            distance = Math.abs(delta_time[j] - ((a[1] * peaks[j][2] * peaks[j][2])
                                                    + (a[2] * peaks[j][2]) + a[0]));
                                            if (distance < (2 * standard_deviation_distance)) {
                                                calibration_time[n_time_alignment] = peaks[j][2];
                                                calibration_delta_time[n_time_alignment] = delta_time[j];
                                                n_time_alignment++;
                                            }
                                        } else {
                                            if (peaks[j][2] < high_time) {
                                                distance = Math.abs(delta_time[j] - ((a[1] * peaks[j][2] * peaks[j][2])
                                                        + (a[2] * peaks[j][2]) + a[0]));
                                                if (distance < (2 * standard_deviation_distance)) {
                                                    calibration_time[n_time_alignment] = peaks[j][2];
                                                    calibration_delta_time[n_time_alignment] = delta_time[j];
                                                    n_time_alignment++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            a = calibrationquadratic.quadratic(calibration_delta_time, calibration_time);
                        }
                        low_time_alignment[d] = low_time;
                        high_time_alignment[d] = high_time;
                        a0_time_alignment[d] = a[0];
                        a1_time_alignment[d] = a[1];
                        a2_time_alignment[d] = a[2];
                        for (int j = 0; j < peaks.length; j++) {
                            if (peaks[j][2] >= low_time) {
                                if (high_time == -1) {
                                    peaks[j][2] = peaks[j][2]
                                            - ((a[1] * peaks[j][2] * peaks[j][2]) + (a[2] * peaks[j][2]) + a[0]);
                                    if ((equipmentid == 4) && experiment.getfiletype().trim().equalsIgnoreCase("raw")) {
                                        peaks[j][5] = peaks[j][5]
                                                - ((a[1] * peaks[j][5] * peaks[j][5]) + (a[2] * peaks[j][5]) + a[0]);
                                        peaks[j][6] = peaks[j][6]
                                                - ((a[1] * peaks[j][6] * peaks[j][6]) + (a[2] * peaks[j][6]) + a[0]);
                                    }
                                } else {
                                    if (peaks[j][2] < high_time) {
                                        peaks[j][2] = peaks[j][2]
                                                - ((a[1] * peaks[j][2] * peaks[j][2]) + (a[2] * peaks[j][2]) + a[0]);
                                        if ((equipmentid == 4)
                                                && experiment.getfiletype().trim().equalsIgnoreCase("raw")) {
                                            peaks[j][5] = peaks[j][5] - ((a[1] * peaks[j][5] * peaks[j][5])
                                                    + (a[2] * peaks[j][5]) + a[0]);
                                            peaks[j][6] = peaks[j][6] - ((a[1] * peaks[j][6] * peaks[j][6])
                                                    + (a[2] * peaks[j][6]) + a[0]);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (inputalignmentreferencemass != null) {
                try {
                    inputalignmentreferencemass.close();
                } catch (IOException e) {
                }
                inputalignmentreferencemass = null;
            }
            if (inputalignmentreferenceretentiontime != null) {
                try {
                    inputalignmentreferenceretentiontime.close();
                } catch (IOException e) {
                }
                inputalignmentreferenceretentiontime = null;
            }
            try {
                timealignmentreferencemass = new FileOutputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferencemass, true);
            } catch (FileNotFoundException e) {
            }
            try {
                timealignmentreferenceretentiontime = new FileOutputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferenceretentiontime, true);
            } catch (FileNotFoundException e) {
            }
            try {
                timealignmentreferencesequence = new FileOutputStream(
                        cc.userhome + cc.fileSeparator + nametimealignmentreferencesequence, true);
            } catch (FileNotFoundException e) {
            }
            timealignmentbuffermassnew = new StringBuffer("");
            timealignmentbufferretentiontimenew = new StringBuffer("");
            timealignmentbuffersequencenew = new StringBuffer("");
            first = true;
            for (int j = 0; j < peaks.length; j++) {
                if (alignment_time[j].equalsIgnoreCase("no")) {
                    if (peaks[j][0] > 0) {
                        if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                            if (peaks[j][7] > -1) {
                                int q = (int) peaks[j][7];
                                sequensing_results = sequensing_results_vector.elementAt(q);
                                sequencing_results_string = sequensing_results[0].trim().replaceAll(",", "");
                                if (!sequencing_results_string.trim().equalsIgnoreCase("")) {
                                    alignmentmassfound = true;
                                    if (clusteringtechnique == 1) {
                                        deltamzlocal = deltamzcombine * peaks[j][0] / 1000000;
                                    }
                                    int k = j;
                                    while (k > 0) {
                                        k--;
                                        if (Math.abs(peaks[j][0] - peaks[k][0]) < Math.abs(deltamzlocal)) {
                                            if (peaks[k][1] > peaks[j][1]) {
                                                if (peaks[k][7] > -1) {
                                                    int t = (int) peaks[k][7];
                                                    if (q == t) {
                                                        alignmentmassfound = false;
                                                    }
                                                }
                                            }
                                        } else {
                                            k = 0;
                                        }
                                    }
                                    k = j;
                                    while (k < (peaks.length - 1)) {
                                        k++;
                                        if (Math.abs(peaks[j][0] - peaks[k][0]) < Math.abs(deltamzlocal)) {
                                            if (peaks[k][1] > peaks[j][1]) {
                                                if (peaks[k][7] > -1) {
                                                    int t = (int) peaks[k][7];
                                                    if (q == t) {
                                                        alignmentmassfound = false;
                                                    }
                                                }
                                            }
                                        } else {
                                            k = peaks.length - 1;
                                        }
                                    }
                                    if (alignmentmassfound) {
                                        if (first) {
                                            first = false;
                                            timealignmentbuffermassnew.append(String.valueOf(peaks[j][0]).trim());
                                            timealignmentbufferretentiontimenew
                                                    .append(String.valueOf(peaks[j][2]).trim());
                                            timealignmentbuffersequencenew.append(sequencing_results_string);
                                        } else {
                                            timealignmentbuffermassnew.append("," + String.valueOf(peaks[j][0]).trim());
                                            timealignmentbufferretentiontimenew
                                                    .append("," + String.valueOf(peaks[j][2]).trim());
                                            timealignmentbuffersequencenew.append("," + sequencing_results_string);
                                        }
                                    }
                                }
                            }
                        } else {
                            if (first) {
                                first = false;
                                timealignmentbuffermassnew.append(String.valueOf(peaks[j][0]).trim());
                                timealignmentbufferretentiontimenew.append(String.valueOf(peaks[j][2]).trim());
                            } else {
                                timealignmentbuffermassnew.append("," + String.valueOf(peaks[j][0]).trim());
                                timealignmentbufferretentiontimenew.append("," + String.valueOf(peaks[j][2]).trim());
                            }
                        }
                    }
                }
            }
            data = (timealignmentbuffermassnew.toString() + "#").getBytes();
            if (timealignmentreferencemass != null) {
                try {
                    timealignmentreferencemass.write(data);
                    timealignmentreferencemass.flush();
                    timealignmentreferencemass.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            data = (timealignmentbufferretentiontimenew.toString() + "#").getBytes();
            if (timealignmentreferenceretentiontime != null) {
                try {
                    timealignmentreferenceretentiontime.write(data);
                    timealignmentreferenceretentiontime.flush();
                    timealignmentreferenceretentiontime.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if ((perform_ms2_sequencing) && (ms2_sequencenced_masses_present)) {
                data = (timealignmentbuffersequencenew.toString() + "#").getBytes();
                if (timealignmentreferencesequence != null) {
                    try {
                        timealignmentreferencesequence.write(data);
                        timealignmentreferencesequence.flush();
                        timealignmentreferencesequence.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void datareductionfileXML() {
        spf = SAXParserFactory.newInstance();
        tmp_filename = "tmp_xml_file.xml";
        try {
            ftp.setRemoteHost(cc.ftpremotehost);
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setTimeout(cc.ftp_longtime);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            ftp.get(cc.userhome + cc.fileSeparator + tmp_filename, massspectrometryfile.getFilename());
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            sp = spf.newSAXParser();
            parsefile = new File(cc.userhome + cc.fileSeparator + tmp_filename);
            sp.parse(parsefile, this);
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
            ;
        }
    }

    @Override
    public void startElement(String uri, String localName, String rawName, Attributes attributes) {
        if (equipmentid == 3) {
            if (peaks == null) {
                if (rawName.trim().equalsIgnoreCase("Compound")) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        if ((attributes.getQName(i).trim().equalsIgnoreCase("mass"))
                                && (Double.parseDouble(attributes.getValue(i)) >= minimummass)
                                && (Double.parseDouble(attributes.getValue(i)) <= maximummass)) {
                            intnumberofmeasurements++;
                        }
                    }
                }
            } else {
                if (rawName.trim().equalsIgnoreCase("Compound")) {
                    add_mass = false;
                    for (int i = 0; i < attributes.getLength(); i++) {
                        if ((attributes.getQName(i).trim().equalsIgnoreCase("mass"))
                                && (Double.parseDouble(attributes.getValue(i)) >= minimummass)
                                && (Double.parseDouble(attributes.getValue(i)) <= maximummass)) {
                            intnumberofmeasurements++;
                            add_mass = true;
                        }
                    }
                    if (add_mass) {
                        for (int i = 0; i < attributes.getLength(); i++) {
                            if (attributes.getQName(i).trim().equalsIgnoreCase("mass")) {
                                peaks[intnumberofmeasurements - 1][0] = Double.parseDouble(attributes.getValue(i));
                            }
                            if (attributes.getQName(i).trim().equalsIgnoreCase("AbsoluteIntensity")) {
                                peaks[intnumberofmeasurements - 1][1] = Double.parseDouble(attributes.getValue(i));
                            }
                            if (attributes.getQName(i).trim().equalsIgnoreCase("RetentionTime")) {
                                peaks[intnumberofmeasurements - 1][2] = Double.parseDouble(attributes.getValue(i));
                            }
                        }
                    }
                }
            }
        }
        if (equipmentid == 2) {
            if (peaks == null) {
                if (rawName.trim().equalsIgnoreCase("pk")) {
                    for (int i = 0; i < attributes.getLength(); i++) {
                        if ((attributes.getQName(i).trim().equalsIgnoreCase("mz"))
                                && (Double.parseDouble(attributes.getValue(i)) >= minimummass)
                                && (Double.parseDouble(attributes.getValue(i)) <= maximummass)) {
                            intnumberofmeasurements++;
                        }
                    }
                }
            } else {
                if (rawName.trim().equalsIgnoreCase("pk")) {
                    add_mass = false;
                    for (int i = 0; i < attributes.getLength(); i++) {
                        if ((attributes.getQName(i).trim().equalsIgnoreCase("mz"))
                                && (Double.parseDouble(attributes.getValue(i)) >= minimummass)
                                && (Double.parseDouble(attributes.getValue(i)) <= maximummass)) {
                            intnumberofmeasurements++;
                            add_mass = true;
                        }
                    }
                    if (add_mass) {
                        for (int i = 0; i < attributes.getLength(); i++) {
                            if (attributes.getQName(i).trim().equalsIgnoreCase("mz")) {
                                peaks[intnumberofmeasurements - 1][0] = Double.parseDouble(attributes.getValue(i));
                            }
                            if (attributes.getQName(i).trim().equalsIgnoreCase("i")) {
                                peaks[intnumberofmeasurements - 1][1] = Double.parseDouble(attributes.getValue(i));
                            }
                        }
                    }
                }
            }
        }
    }

    public String date_to_string(GregorianCalendar gc) {
        day = gc.get(Calendar.DAY_OF_MONTH);
        month = (gc.get(Calendar.MONTH) + 1);
        year = gc.get(Calendar.YEAR);
        if (year < 1000) {
            year = (year + 1900);
        }
        hour = gc.get(Calendar.HOUR_OF_DAY);
        min = gc.get(Calendar.MINUTE);
        sec = gc.get(Calendar.SECOND);
        String datestring = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
        return datestring;
    }

    String date_to_time(GregorianCalendar gc) {
        hour = gc.get(Calendar.HOUR_OF_DAY);
        min = gc.get(Calendar.MINUTE);
        sec = gc.get(Calendar.SECOND);
        String datestring = String.valueOf(hour) + ":" + String.valueOf(min) + ":" + String.valueOf(sec);
        return datestring;
    }

    public String get_year_date(GregorianCalendar gc) {
        year = gc.get(Calendar.YEAR);
        if (year < 1000) {
            year = (year + 1900);
        }
        return String.valueOf(year).trim();
    }

    private void filltimesignal() {
        try {
            ftp.setRemoteHost(cc.ftpremotehost);
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setTimeout(cc.ftp_longtime);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            byte[] inputstream = null;
            inputstream = ftp.get(massspectrometryfile.getFilename());
            chanelintensity = 0;
            zerofile = true;
            for (int i = 0; i < (inputstream.length / 4); i++) {
                chanelintensity = 0;
                shiftby = 0;
                for (int j = 0; j < 4; j++) {
                    shiftby = j * 8;
                    chanelintensity = chanelintensity | ((inputstream[teller] & 0xff) << shiftby);
                    teller++;
                }
                if (chanelintensity > 0) {
                    zerofile = false;
                }
                spectrum.intensity[i] = chanelintensity;
            }
        } catch (Exception e) {
            ;
        }
        try {
            ftp.quit();
        } catch (Exception e) {
            ;
        }
    }

    private void filltimesignalFTMS() {
        try {
            ftp.setRemoteHost(cc.ftpremotehost);
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setTimeout(cc.ftp_longtime);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            byte[] inputstream = null;
            inputstream = ftp.get(massspectrometryfile.getFilename());
            chanelintensity = 0;
            for (int i = 0; i < (inputstream.length / 4); i++) {
                chanelintensity = 0;
                shiftby = 0;
                for (int j = 0; j < 4; j++) {
                    shiftby = j * 8;
                    chanelintensity = chanelintensity | ((inputstream[teller] & 0xff) << shiftby);
                    teller++;
                }
                ftmsdata.data[i] = chanelintensity;
            }
        } catch (Exception e) {
            ;
        }
        try {
            ftp.quit();
        } catch (Exception e) {
            ;
        }
    }

    private void fillmassesFTMS() {
        fft.fftMag(ftmsdata, xim);
        frequencyresolution = 1 / (aquisitiontime * zerofillingfactor);
        for (int i = 0; i <= (ftmsdata.data.length / 2) - 1; i++) {
            ftmsdata.data[(ftmsdata.data.length / 2) + i] = frequencytomass((i + 1) * frequencyresolution);
            ftmsdata.data[i] = ftmsdata.data[i] * intnumberofmeasurements * zerofillingfactor / 4;
        }
    }

    private void fillmassesMALDITOF() {
        for (int i = 0; i < intnumberofmeasurements; i++) {
            time = delay + (i * timebase);
            C = (c0 - time);
            B = Math.sqrt(Math.pow(10, 12) / c1);
            A = c2;
            if ((A == 0) || ((Math.pow(B, 2) - (4 * A * C)) < 0)) {
                dummy1 = time - c0;
                dummy2 = c1 * dummy1 * dummy1;
                mass = dummy2 / Math.pow(10, 12);
                spectrum.mass[i] = mass;
            } else {
                square_root_mass = (((Math.sqrt(Math.pow(B, 2) - (4 * A * C))) - B) / (2 * A));
                mass = square_root_mass * square_root_mass;
                spectrum.mass[i] = mass;
            }
        }
    }

    private void readnumberoflines() {
        intnumberofmeasurements = 1;
        try {
            try {
                ftp.setRemoteHost(cc.ftpremotehost);
                ftp.connect();
                ftp.login(cc.ftpuser, cc.ftppassword);
                ftp.setConnectMode(FTPConnectMode.PASV);
                ftp.setType(FTPTransferType.BINARY);
                ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            } catch (Exception e) {
                if (cc.debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            try {
                acquis = ftp.get(massspectrometryfile.getFilename());
            } catch (Exception e) {
                if (cc.debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            intnumberofmeasurements = 0;
            for (int i = 0; i < acquis.length; i++) {
                if (acquis[i] == 10) {
                    intnumberofmeasurements++;
                }
            }
            if (acquis[acquis.length - 1] != 10) {
                intnumberofmeasurements++;
            }
            try {
                ftp.quit();
            } catch (Exception e) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removedouble_masses_within_search_window() {
        peakstransposed = new double[peaks[0].length][peaks.length];
        for (int i = 0; i < peaks.length; i++) {
            for (int j = 0; j < peaks[0].length; j++) {
                peakstransposed[j][i] = peaks[i][j];
            }
        }
        sortrow = 0;
        sortmatrix = new SortMatrix(peakstransposed, sortrow);
        for (int i = 0; i < peaks.length; i++) {
            for (int j = 0; j < peaks[0].length; j++) {
                peaks[i][j] = peakstransposed[j][i];
            }
        }
        remove_double_mass_window = deltamzsearchmaximum;
        for (int i = 0; i < peaks.length; i++) {
            if (peaks[i][0] > 0) {
                if (clusteringtechnique == 1) {
                    remove_double_mass_window = deltamzsearchmaximum * peaks[i][0] / 1000000;
                }
                q = i + 1;
                while ((q < peaks.length) && (Math.abs(peaks[q][0] - peaks[i][0]) < remove_double_mass_window)) {
                    if (peaks[q][0] > 0) {
                        if (peaks[q][1] < peaks[i][1]) {
                            peaks[q][0] = -1;
                        } else {
                            peaks[i][0] = -1;
                        }
                    }
                    q++;
                }
            }
        }
    }

    private void fillmassesintensitiespeaklist() {
        try {
            intnumberofmeasurements = 0;
            header = false;
            line = "";
            headerline = "";
            linebuffer = new StringBuffer(line);
            headerbuffer = new StringBuffer(headerline);
            set_separator = false;
            for (int i = 0; i < acquis.length; i++) {
                headerbuffer.append((char) acquis[i]);
                if (teststring_header.indexOf((char) acquis[i]) > -1) {
                    header = true;
                }
                if (teststring_separated.indexOf((char) acquis[i]) > -1) {
                    linebuffer.append((char) acquis[i]);
                    set_separator = true;
                } else {
                    if (set_separator) {
                        linebuffer.append("#");
                        set_separator = false;
                    }
                }
                if (acquis[i] == 10) {
                    if (header == false) {
                        line = linebuffer.toString();
                        linearray = line.split("#");
                        if ((!linearray[0].trim().equalsIgnoreCase(""))
                                && (Double.parseDouble(linearray[0]) >= minimummass)
                                && (Double.parseDouble(linearray[0]) <= maximummass)) {
                            peaks[intnumberofmeasurements][0] = Double.parseDouble(linearray[0]);
                            peaks[intnumberofmeasurements][1] = Double.parseDouble(linearray[1]);
                            if (((equipmentid == 3) || (equipmentid == 4)) && (linearray.length > 2)) {
                                peaks[intnumberofmeasurements][2] = Double.parseDouble(linearray[2]);
                            }
                            intnumberofmeasurements++;
                            set_separator = false;
                        }
                    } else {
                        headerline = headerbuffer.toString();
                    }
                    line = "";
                    linebuffer = new StringBuffer(line);
                    header = false;
                }
            }
            if (acquis[acquis.length - 1] != 10) {
                if (header == false) {
                    line = linebuffer.toString();
                    linearray = line.split("#");
                    if ((!linearray[0].trim().equalsIgnoreCase("")) && (Double.parseDouble(linearray[0]) >= minimummass)
                            && (Double.parseDouble(linearray[0]) <= maximummass)) {
                        peaks[intnumberofmeasurements][0] = Double.parseDouble(linearray[0]);
                        peaks[intnumberofmeasurements][1] = Double.parseDouble(linearray[1]);
                        if (((equipmentid == 3) || (equipmentid == 4)) && (linearray.length > 2)) {
                            peaks[intnumberofmeasurements][2] = Double.parseDouble(linearray[2]);
                        }
                        intnumberofmeasurements++;
                        set_separator = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readparameters() {
        teller = 0;
        c0 = 0;
        c1 = 0;
        c2 = 0;
        sweep = 0;
        intnumberofmeasurements = 0;
        try {
            ftp.setRemoteHost(cc.ftpremotehost);
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + experimentyear + File.separator + this.experimentnumber);
            acquis = ftp.get(acquisname);
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        line = "";
        linebuffer = new StringBuffer(line);
        for (int i = 0; i < acquis.length; i++) {
            if ((acquis[i] != 10) && (acquis[i] != 13)) {
                linebuffer.append((char) acquis[i]);
            }
            if (acquis[i] == 10) {
                line = linebuffer.toString();
                readparametersline(line);
                line = "";
                linebuffer = new StringBuffer(line);
            }
        }
        try {
            ftp.quit();
        } catch (Exception e) {
            ;
        }
    }

    private void Apodization() {
        multiply = 0;
        progress = 0;
        if (string_apodization_method == null) {
        } else if (string_apodization_method.equalsIgnoreCase("gaussian")) {
            for (int i = 0; i < (intnumberofmeasurements - 1); i++) {
                progress = Double.parseDouble(String.valueOf(i))
                        / Double.parseDouble(String.valueOf(intnumberofmeasurements));
                multiply = (-Math.PI * LorentzfactorA * progress) + (-Math.PI * GaussfactorB * progress * progress);
                multiply = Math.exp(multiply);
                ftmsdata.data[i] = multiply * ftmsdata.data[i];
            }
        } else if (string_apodization_method.equalsIgnoreCase("sine")) {
            for (int i = 0; i < (intnumberofmeasurements - 1); i++) {
                progress = Double.parseDouble(String.valueOf(i))
                        / Double.parseDouble(String.valueOf(intnumberofmeasurements));
                multiply = Math.sin(Math.PI * progress);
                ftmsdata.data[i] = multiply * ftmsdata.data[i];
            }
        } else if (string_apodization_method.equalsIgnoreCase("squared_sine")) {
            for (int i = 0; i < (intnumberofmeasurements - 1); i++) {
                progress = Double.parseDouble(String.valueOf(i))
                        / Double.parseDouble(String.valueOf(intnumberofmeasurements));
                multiply = Math.pow(Math.sin(Math.PI * progress), 2);
                ftmsdata.data[i] = multiply * ftmsdata.data[i];
            }
        } else if (string_apodization_method.equalsIgnoreCase("hanning")) {
            for (int i = 0; i < (intnumberofmeasurements - 1); i++) {
                progress = Double.parseDouble(String.valueOf(i))
                        / Double.parseDouble(String.valueOf(intnumberofmeasurements));
                multiply = 0.5 * (1 - Math.cos(2 * Math.PI * progress));
                ftmsdata.data[i] = multiply * ftmsdata.data[i];
            }
        } else if (string_apodization_method.equalsIgnoreCase("hamming")) {
            for (int i = 0; i < (intnumberofmeasurements - 1); i++) {
                progress = Double.parseDouble(String.valueOf(i))
                        / Double.parseDouble(String.valueOf(intnumberofmeasurements));
                multiply = 0.54 - (0.46 * Math.cos(2 * Math.PI * progress));
                ftmsdata.data[i] = multiply * ftmsdata.data[i];
            }
        } else if (string_apodization_method.equalsIgnoreCase("blackman")) {
            for (int i = 0; i < (intnumberofmeasurements - 1); i++) {
                progress = Double.parseDouble(String.valueOf(i))
                        / Double.parseDouble(String.valueOf(intnumberofmeasurements));
                multiply = 0.42423 - 0.49755 * Math.cos(2 * Math.PI * progress)
                        + 0.07922 * Math.cos(4 * Math.PI * progress);
                ftmsdata.data[i] = multiply * ftmsdata.data[i];
            }
        } else if (string_apodization_method.equalsIgnoreCase("none")) {
        } else if (string_apodization_method.equalsIgnoreCase("")) {
        } else {
        }
    }

    double frequencytomass(double f) {
        return c1 / (f - c0);
    }

    double masstofrequency(double mass) {
        return (c1 / mass) + c0;
    }

    private void readparametersline(String line) {
        searchstring = "##$ML1=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                c1 = Double.parseDouble(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
        searchstring = "##$ML2=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                c0 = Double.parseDouble(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
        searchstring = "##$ML3=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                c2 = Double.parseDouble(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
        searchstring = "##$DW=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                timebase = Double
                        .parseDouble(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
        searchstring = "##$DELAY=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                delay = Double.parseDouble(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
        searchstring = "##$TD=";
        if (line.indexOf(searchstring) >= 0) {
            numberofmeasurements = line.substring(line.indexOf(searchstring) + searchstring.length()).trim();
            intnumberofmeasurements = Integer.parseInt(numberofmeasurements);
        }
        searchstring = "##$SW_h=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                sweep = Double.parseDouble(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
        searchstring = "##$MW_low=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                lowmass = Double.parseDouble(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
        searchstring = "##$DM=";
        if (line.indexOf(searchstring) >= 0) {
            if (!line.substring(line.indexOf(searchstring) + searchstring.length()).trim().equals("")) {
                detect_mode = Integer
                        .parseInt(line.substring(line.indexOf(searchstring) + searchstring.length()).trim());
            }
        }
    }
}
