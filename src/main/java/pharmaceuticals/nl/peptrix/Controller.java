package pharmaceuticals.nl.peptrix;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import pharmaceuticals.nl.peptrix.gui.Allocation;
import pharmaceuticals.nl.peptrix.gui.CreateWilcoxon;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.gui.ReportAllocation;
import pharmaceuticals.nl.peptrix.gui.SearchSelection;
import pharmaceuticals.nl.peptrix.gui.SlavePane;
import pharmaceuticals.nl.peptrix.gui.TableSorter;
import pharmaceuticals.nl.peptrix.gui.UpdateView;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.FidFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.RawFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.TextFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.XMLFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.mzXMLFilter;
import pharmaceuticals.nl.peptrix.service.ExperimentService;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.service.SystemCodeItemService;
import pharmaceuticals.nl.peptrix.serviceimpl.ExperimentServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.GroupServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.SampleServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.SystemCodeItemServiceImpl;
import pharmaceuticals.nl.peptrix.gui.application.menu.Buttonstoppanel;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.Cleanpvalues;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.DisplayPplot;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.DisplayPvalues;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.InsertresultrecordWilcoxon;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.StartWilcoxon;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.StorePplot;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.StoredetailsWilcoxon;
import pharmaceuticals.nl.peptrix.transpose.*;
import pharmaceuticals.nl.peptrix.utils.ActualTime;
import pharmaceuticals.nl.peptrix.utils.SortMatrix;
import pharmaceuticals.nl.peptrix.R.Rterm;
import pharmaceuticals.nl.peptrix.allocation.InsertGroups;
import pharmaceuticals.nl.peptrix.allocation.InsertSamples;
import pharmaceuticals.nl.peptrix.calibration.Getcalibrationstandards;
import pharmaceuticals.nl.peptrix.createpeaklist.Readdatafiles;
import pharmaceuticals.nl.peptrix.database.JDBC;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.experiment.Getexistingexperiment;
import pharmaceuticals.nl.peptrix.export.CreateexportstringWilcoxon;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.export.Storereport;
import pharmaceuticals.nl.peptrix.fileimport.*;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class Controller
        implements ActionListener, MouseListener, ItemListener, PropertyChangeListener, FocusListener {
    public CreateMatrixAction createMatrixAction;
    Object[][] odatamatrixfileswicoxon;
    int datalength2;
    SystemCodeItemService systemcodeservice;
    GroupServiceImpl groupserviceimpl;
    String newexperimentid;
    public JButton savereportallocation;
    public String[] dataexperiment;
    Object[][] odatagroup;
    StartWilcoxon startWilcoxon;
    public byte[] data_wilcoxon;
    String input_p_file;
    public String p_plot_prefix = "p_plot_";
    DisplayPvalues displayPvalues;
    DisplayPplot displayPplot;
    public StorePplot storepplot;
    Progress progress;
    JFrame filechooserframe;
    public Createnewexperiment createnewexperiment;
    Getexistingexperiment getexistingexperiment;
    public Experiment existingexperiment;
    ReportAllocation reportallocation;
    public SortMatrix sortmatrix;
    public Experiment experiment;
    TableSorter sorter;
    Readdatafiles readdatafiles;
    public JDBC jdbcconnection;
    SearchSelection searchselection;
    UpdateView updateview;
    SlavePane[] slavepane;
    Allocation allocation;
    public CreateWilcoxon createwilcoxon;
    TransposeMatrix transposematrix;
    GetFiles Getfiles;
    public Rterm rterm;
    public ActualTime actualtime;
    public TextFilter textfilter = new TextFilter();
    public FidFilter fidfilter = new FidFilter();
    public XMLFilter xmlfilter = new XMLFilter();
    public mzXMLFilter mzxmlfilter = new mzXMLFilter();
    public RawFilter rawfilter = new RawFilter();
    ResultServiceImpl queryresults;
    public ExportFileToDisk exportfiletodisk;
    public MenuItem Import = new MenuItem("Import Spectra");
    public MenuItem Code = new MenuItem("Code");
    DefaultTableModel defaulttablemodel;
    public FTPClient ftp;
    FlowLayout flowlayout;
    public JSlider slider;
    public JDialog searchtablerecorddialog;
    JDialog menudialog;
    public FileInputStream fis;
    public JFrame frame = new JFrame();
    JOptionPane optionsearch;
    JScrollPane browserpane;
    public JPanel pLeft;
    public JPanel pTop;
    public JPanel pBody;
    JPanel searchtablerecordpanel;
    JPanel searchtablerecordpanel2;
    JTable matrixtable;
    JTable allocationtable;
    public SortedListModel model_all_modifications;
    public SortedListModel model_variabel_mod;
    public SortedListModel model_fixed_mod;
    public SortedListModel selected_enzyme_list;
    public SortedListModel potential_enzyme_list;
    public JComboBox comborandomizationgroups;
    public JComboBox colorgroup1;
    public JComboBox colorgroup2;
    public JComboBox creatematrixmethodcombobox;
    public JComboBox combo_Taxonomy;
    public JComboBox combo_searchengine;
    public JComboBox combo_ITOLU;
    public JComboBox combo_TOLU;
    public JComboBox combomatrixtodisplay;
    public JComboBox calibrationstandard;
    public JComboBox equipmentcombo;
    public JComboBox filtercombobox2;
    public JComboBox filtercombobox;
    public JComboBox combogroupnumber1;
    public JComboBox combogroupnumber2;
    public JComboBox combomatrixtodisplaywilcoxon;
    public ButtonGroup btngroup;
    public ButtonGroup radiosetapodization;
    public ButtonGroup raw_to_mzXML_programms;
    public ButtonGroup centroidingmethods;
    public JRadioButton none;
    public JRadioButton weightedmean;
    public JRadioButton msconvert_programm_64;
    public JRadioButton msconvert_programm_32;
    String[] selected_enzymes;
    public JRadioButton ppm;
    public JRadioButton Da;
    public JRadioButton btn_Gaussian_Lorentzian;
    public JRadioButton btn_sine_bell;
    public JRadioButton btn_Squared_Sine_bell;
    public JRadioButton btn_Hanning;
    public JRadioButton btn_Hamming;
    public JRadioButton btn_Blackmann_Harris;
    public JRadioButton btn_no_apodization;
    public JRadioButton existradio;
    public JRadioButton newradio;
    public JButton[] cleargroup;
    public JButton[] clearsample;
    public JButton[] searchgroup;
    public JButton[] searchsample;
    public JButton search_experiment;
    public JButton startcreatingmatrix;
    public JButton startwilcoxon;
    public JButton search;
    public JButton allocation2;
    public JButton btnwilcoxon2;
    public JButton button_remove_fixed;
    public JButton button_remove_enzyme;
    public JButton button_add_enzyme;
    public JButton button_add_fixed;
    public JButton button_add_variabel;
    public JButton button_remove_variabel;
    public JButton btnGroup = new JButton("Group");
    JButton btnPatient = new JButton("Person");
    JButton btnOrgan = new JButton("Origin");
    JButton btnTissue = new JButton("Material");
    public JButton btnSample_ = new JButton("Sample");
    public JButton btnExperiment = new JButton("Experiment");
    public JButton btnEquipment = new JButton("Equipment");
    public JButton btnResult = new JButton("Result");
    public JButton btnallocation = new JButton("Allocation");
    public JButton btncreatematrix = new JButton("Create Matrix");
    public JButton btnfasta = new JButton("Fasta files");
    public JButton btnwilcoxon = new JButton("Wilcoxon");
    public JButton btntransposematrix = new JButton("Transpose matrix");
    public JButton btnrefresh;
    public JButton btnNext;
    public JButton btnFirst;
    public JButton btnPrevious;
    public JButton btnLast;
    public JButton buttonsavedetails;
    public JButton searchwilcoxon;
    public JButton selectinputsample;
    public JButton search_sample;
    public JButton btnsave;
    public JButton creatematrix2;
    public JButton btnreport;
    public JButton clearselectedsample;
    public JList list_fixed_mod;
    public JList list_selected_enzyme;
    public JList list_potential_enzyme;
    public JList list_variabel_mod;
    public JList list_all_modifications;
    public TextField[] Offset_LC_MS;
    public TextField[] Sampleid;
    public TextField[] Groupid;
    public TextField[] Sample_code;
    public TextField[] Sample_name;
    public TextField[] Group_code;
    public TextField[] Group_name;
    public TextField numberofrandomizations;
    public TextField inputnumberofreplicatessample;
    public TextField selectsampleid;
    public TextField experimentname;
    public TextField experimentid;
    public TextField equipmentname;
    public TextField inputminimummass;
    public TextField inputmaximummass;
    public TextField inputdeltamzcombine;
    public TextField inputdeltamzsearch;
    public TextField inputpointstimealignment;
    public TextField input_time_widow_lc_ms;
    public TextField input_tol;
    public TextField input_itol;
    public TextField inputLorentzfactorA;
    public TextField inputzerofillingfaktor;
    public TextField isotopicdistance;
    public TextField deviation_isotopicdistance;
    public TextField inputvarianceisotopicdistance;
    public TextField texfieldinputerror;
    public TextField textfieldmaxchargestate;
    public TextField inputexperimentname;
    public TextField experimentidwilcoxon;
    public TextField inputexperimentid;
    public TextField equipmentid;
    public TextField inputsingnaltonoiseinparts;
    public TextField inputGaussianfaktor;
    public TextField inputlimitcentroiding;
    public TextField dateexperimentwilcoxon;
    public TextField equipmentnamewilcoxon;
    public TextField experimentnamewilcoxon;
    public TextField inputthresholdbinarytable;
    public TextField inputquantilepeakfinding;
    public TextField missingcalibratiopeaksallowed;
    public TextField peaksnoisyspectra;
    public TextField selectsamplecode;
    public TextField experimentname2;
    public TextField equipmentname2;
    public TextField dateexperiment2;
    public TextField input_changeingradient1;
    public TextField input_changeingradient2;
    public TextField input_changeingradient3;
    public TextField inputplotheight;
    public TextField inputplotwidth;
    public TextField inputdeltamzsearchcalibrant;
    public TextField dateexperiment;
    public TextField inputminimumcountmasses;
    public TextField inputwindowcentroiding;
    public TextField updatefield;
    public JLabel apodization_method;
    public JLabel labelrecordsnotallocated;
    public JLabel Raw_to_mzXML;
    public JLabel labelLorentzianfactor;
    public JLabel labelGaussianfaktor;
    public JLabel labelquantilepeakfinding;
    public JLabel labeldeltamzsearchcalibrants;
    public JLabel labeldeltamzcombine;
    public JLabel labelpointstimealignment;
    public JLabel labeltimealignment;
    public JLabel labeltimewindow_lc_ms;
    public JLabel minimumcountmasses;
    public JLabel labelApodizationformula;
    public JLabel labelnoisyspectra;
    public JLabel labeldeltamzsearch;
    public JLabel labelwindowcentroiding;
    public JLabel labellimitcentroiding;
    public JLabel labelnumberofreplicatessample;
    public JLabel labelthresholdbinarytable;
    public JLabel labelmaximummass;
    public JLabel labelminimummass;
    public JLabel labelzero;
    public JLabel numberofrecords;
    public Checkbox[] checkbox;
    public Checkbox checkbox_ms2sequencing;
    public Checkbox only_ms2sequenced_masses;
    public Checkbox checkbox_internalcalibration;
    public Checkbox checkboxtimealignment;
    public Checkbox checkboxall;
    public Checkbox checkboxgeneratesamplecodes;
    public Checkbox checkdeisotoping;
    public Color menubackgroundcolor = new Color(12, 32, 116);
    public Color color_empty_frames = new Color(134, 210, 237);
    public Color colorgrey = new Color(204, 204, 204);
    public Color lightgrey = new Color(238, 238, 238);
    public Color topmenu = new Color(255, 255, 255);
    Double testinputerror;
    public BigDecimal tempBD;
    public Object[][] odatafiles;
    Object[][] numberofnoisypectra;
    Object[][] numberofsamplesinmatrix;
    Object[][] tolownumberofcalibratedsamples;
    Object[][] totalnumberofsamples;
    public Object[][] odata_allocation;
    Object[][] odataenzymes;
    Object[][] odatamodifications;
    Object[][] odatasample;
    SampleServiceImpl sampleserviceimpl;
    public Object[][] odataequipment;
    Object[][] count_allocated_records;
    Object[][] odatafiletypes2;
    Object[][] odatafiletypes;
    Object[][] odatanewresultid;
    Object[][] odataexperimentnamewilcoxon;
    String[][] tabledata;
    public String[] temp2;
    public String[] filtertypes = {fidfilter.getDescription(), textfilter.getDescription(), xmlfilter.getDescription(),
            mzxmlfilter.getDescription(), rawfilter.getDescription()};
    String[] filetypes = {fidfilter.getDescription(), textfilter.getDescription(), xmlfilter.getDescription(),
            mzxmlfilter.getDescription(), rawfilter.getDescription()};
    public String[] resultid;
    public String[] oldsampleid;
    public String[] newsampleid;
    public String[] oldsamplecode;
    public String[] oldgroupid;
    public String[] newgroupid;
    public String[] oldgroupcode;
    public String[] equipmentnames;
    public String[] ms2_proteins;
    String[] array_fixed_modifications;
    String[] array_variabel_modifications;
    String[] enzymes;
    String[] allpossiblemodifications;
    String[] temp;
    public String[] groupnumbers;
    String[] tableheader;
    public String[] matrixfiles;
    public String[] strcalibrationstandards;
    public StringBuffer exportbuffer;
    StringBuffer reportbuffer;
    StringBuffer linebuffer;
    String dialogtitle;
    String strexperimentid;
    String selected_enzyme;
    String report;
    String line;
    String matrixfilename;
    String str_threshold_noisy_spectra;
    String strinputnumberofreplicatessample;
    public String strexperimentid2;
    public String strtype;
    public String strquery;
    public String linefeed = "\n";
    String strgroupid = "";
    public String fileSeparator;
    public String userhome;
    public String databaseuser;
    public String databaseName;
    public String databasepassword;
    public String p_values_prefix = "p_values_";
    String javaHome;
    public String osName;
    String userDir_;
    public String inputexperimentidfilechooser;
    String TableName;
    String primarykey;
    String primarykeyvalue;
    public String displayedsamplecode;
    public String importsampleid;
    String strsampleid;
    public String ftpuser;
    public String updatefieldvalue;
    public String ftppassword;
    public String ftpremotehost;
    String prop;
    String filetype;
    String systemcodeitemid;
    String strthresholdbinarymatrix;
    String strquantilethreshold;
    String TOL;
    String TOLU;
    String ITOL;
    String ITOLU;
    public String exportname;
    String string_Taxonomy = "";
    String string_search_engine;
    public String details_prefix = "p_details_";
    public String groupcode1wilcoxon;
    public String groupid1wilcoxon;
    public String groupcode2wilcoxon;
    public String groupid2wilcoxon;
    public String exportstringwilcoxon;
    String wherestring;
    String reportname;
    String raw_to_mzXML;
    String string_apodization_method;
    String numbersamplestolowreplicates;
    String numbernoisysamples;
    String numbersampleinmatrix;
    String newgroupid2;
    public String P_plot_file;
    public double[][] doublePvalues;
    public double[][] cleanedPalues;
    double[] inputgradientchange = new double[3];
    public double[] old_offset_LC_MS;
    double deltatimecombine;
    double GaussfactorB;
    double LorentzfactorA;
    double inputerror = -1;
    double limitshiftcentroiding;
    double deltaxcentroiding;
    double percent_stdevisotopingdistance = -1;
    double percent_deviation_isotopic_distance = -1;
    double c13_c12 = -1;
    public double filegrootte_kbytes_wilcoxon;
    public double double_offset_LC_MS;
    double filegrootte_kbytes;
    int numberpointstimealignment;
    int matrixfileslength;
    int updatesample2;
    int max_charge_state = -1;
    int clusteringtechnique = 0;
    int numbersamplesnotcalibrated;
    int missingfractionsallowed = -1;
    int model_variabel_mod_Size;
    int model_fixed_mod_Size;
    public int updategroup;
    int peakfindmethod = 1;
    int minimumnumberoffractions = -1;
    int updatesampleid;
    int updategroupid;
    int offset = 0;
    int limit = 50;
    int zerofillingfactor;
    int numberofrows2;
    int numberofcolums;
    int duration_hour;
    int minute_per_hour = 60;
    public int second_per_minute = 60;
    int milliseconds = 1000;
    public int ftp_longtime;
    int numberofrows;
    public int updatesample;
    int centroidingmethod;
    int timeclusteringtechnique;
    int ndiv;
    int deletemasseswithcount = 0;
    public byte[] data;
    boolean performtimealgnment;
    boolean internalcalibration;
    public boolean errornotshownbefore = true;
    public boolean debugmode;
    boolean filepresent;
    boolean monoistopefinding;
    boolean perform_ms2_sequencing;
    boolean matrix_only_ms2sequenced_masses;
    public boolean filetransportedwilcoxon;
    public boolean isnewexperiment = true;
    boolean matrixtodisplay;
    StoredetailsWilcoxon storedetailsWilcoxon;
    CreateexportstringWilcoxon createexportstringWilcoxon;
    public InsertresultrecordWilcoxon insertresultrecordWilcoxon;
    ExperimentService getexperimentdata;
    InsertSamples insertsamples;
    Getcalibrationstandards getcalibrationstandards;
    Storereport storereport;
    ResultService collectmatrixfiles;
    ResultService resultservice;
    public Cleanpvalues cleanpvalues;
    InsertGroups insertGroups;
    public ResultService resultService;

    public Controller(JFrame frame, String databaseuser, String databasepassword, String databaseName,String  ftpuser ,
                      String ftppassword ,
                      String ftpremotehost) {


        this.ftpuser = ftpuser;
        this.ftppassword = ftppassword;
        this.ftpremotehost = ftpremotehost;




        this.databaseuser = databaseuser;
        this.databasepassword = databasepassword;
        this.databaseName = databaseName;
        fileSeparator = System.getProperty("file.separator");
        userhome = System.getProperty("user.home");
        javaHome = System.getProperty("java.home");
        osName = System.getProperty("os.name");

        duration_hour = 16;
        ftp_longtime = duration_hour * minute_per_hour * second_per_minute * milliseconds;
        createMatrixAction = new CreateMatrixAction(this);
        createnewexperiment = new Createnewexperiment(this);
        exportfiletodisk = new ExportFileToDisk(this);
        progress = new Progress();
        defaulttablemodel = new DefaultTableModel();
        tabledata = new String[5][20];
        tableheader = new String[20];
        actualtime = new ActualTime();
        this.frame = frame;
        pBody = new JPanel();
        pBody.setLayout(new BorderLayout());
        pBody.setBackground(color_empty_frames);
        Buttonstoppanel buttonstoppanel = new Buttonstoppanel(this);
        pTop = buttonstoppanel.getPanel();
        jdbcconnection = new JDBC(this);
        rterm = new Rterm(this);
        debugmode = true;
        getcalibrationstandards = new Getcalibrationstandards(this);
        strcalibrationstandards = getcalibrationstandards.collectcalibrationstandards();
        errornotshownbefore = true;
        allocation = new Allocation(this);
        searchselection = new SearchSelection(this, frame, pBody);
        reportallocation = new ReportAllocation(this);
        getexistingexperiment = new Getexistingexperiment(this);
        existingexperiment = new Experiment();
        Getfiles = new GetFiles(this);
        storepplot = new StorePplot(this);
        displayPplot = new DisplayPplot(this);
        displayPvalues = new DisplayPvalues(this);
        storedetailsWilcoxon = new StoredetailsWilcoxon(this);
        insertresultrecordWilcoxon = new InsertresultrecordWilcoxon(this);
        insertGroups = new InsertGroups(this);
        dataexperiment = new String[6];
        getexperimentdata = new ExperimentServiceImpl(this);
        insertsamples = new InsertSamples(this);
        storereport = new Storereport(this);
        collectmatrixfiles = new ResultServiceImpl(this);
        resultservice = new ResultServiceImpl(this);
        cleanpvalues = new Cleanpvalues();
        groupserviceimpl = new GroupServiceImpl(this);
        sampleserviceimpl = new SampleServiceImpl(this);
        systemcodeservice = new SystemCodeItemServiceImpl(this);
        resultService = new ResultServiceImpl(this);
        createMatrixAction.create_matrix_screen();
    }

    public void textValueChanged(TextEvent evt) {
    }

    public Color getcolor_empty_frames() {
        return color_empty_frames;
    }

    public ReportAllocation getReportallocation() {
        return reportallocation;
    }

    public void selectexperiment(String strinputexperimentid) {
        inputerror = -1;
        zerofillingfactor = -1;
        GaussfactorB = -1;
        LorentzfactorA = -1;
        inputgradientchange[0] = -1;
        inputgradientchange[1] = -1;
        inputgradientchange[2] = -1;
        createMatrixAction.creatematrix.displayframes();
        if (strinputexperimentid != null) {
            createMatrixAction.experimentid2.setText(strinputexperimentid);
        }
        frame.setVisible(true);
    }

    public void setslavepane(SlavePane[] slavepane) {
        this.slavepane = slavepane;
    }

    public void PerformActionObjectAllocation() {
        wherestring = "";
        updateview = new UpdateView(this);
        updateview.viewtablerecord(TableName, primarykey, primarykeyvalue);
    }

    public void setbackground(Color colorgrey) {
        createMatrixAction.creatematrix.setbackground(colorgrey);
        checkbox_internalcalibration.setBackground(colorgrey);
        checkdeisotoping.setBackground(colorgrey);
        checkboxtimealignment.setBackground(colorgrey);
        checkbox_ms2sequencing.setBackground(colorgrey);
        only_ms2sequenced_masses.setBackground(colorgrey);
    }

    public void storepvaluesdetails(String filenamematrix) {
        storedetailsWilcoxon.create_exportstring_details(filenamematrix, progress);
    }

    public void display_p_data(String filename, String groupid1, String groupid2) {
        createwilcoxon.removeAll_from_p_values_panel();
        strexperimentid2 = experimentidwilcoxon.getText().trim();
        progress.init("Retrieving data ......", 70);
        progress.setmaximum(100);
        progress.setnumberandtext(30, "");
        String p_values_name = p_values_prefix + groupid1 + "_" + groupid2 + "_" + filename;
        JTable pvaluestable = displayPvalues.displaypvalues(p_values_name, dataexperiment[4], strexperimentid2);
        if (pvaluestable != null) {
            createwilcoxon.displayPvalues(pvaluestable);
        }
        progress.setnumberandtext(60, "");
        input_p_file = p_plot_prefix + groupid1 + "_" + groupid2 + "_" + filename;
        displayPplot.displayPplot(input_p_file, createwilcoxon, dataexperiment[4], strexperimentid2);
        progress.setnumberandtext(100, "");
        progress.close();
        frame.setVisible(true);
    }

    public void textValueChangedZ(TextEvent evt) {
        textValueChanged3(odata_allocation, evt, odatasample, datalength2);
        clearallcheckboxes(datalength2);
    }

    public void clearallcheckboxes(int datalength) {
        for (int k = 0; k <= (datalength - 1); k++) {
            checkbox[k].setState(false);
        }
    }

    public void textValueChanged3(Object[][] odata, TextEvent evt, Object[][] odatasample, int datalength) {
        if (odata != null) {
            strsampleid = "";
            updatesampleid = -1;
            strgroupid = "";
            updategroupid = -1;
            for (int i = 0; i <= (datalength - 1); i++) {
                if (evt.getSource() == Sampleid[i]) {
                    Sampleid[i].removeTextListener(createMatrixAction);
                    updatesampleid = i;
                    strsampleid = Sampleid[i].getText().trim();
                }
                if (evt.getSource() == Groupid[i]) {
                    Groupid[i].removeTextListener(createMatrixAction);
                    updategroupid = i;
                    strgroupid = Groupid[i].getText().trim();
                }
            }
            if (updatesampleid != -1) {
                for (int k = 0; k <= (datalength - 1); k++) {
                    if (k != updatesampleid) {
                        if (checkbox[k].getState()) {
                            Sampleid[k].setText(strsampleid);
                            if (strsampleid.trim().equals("")) {
                                Sample_name[k].setText("");
                                Sample_code[k].setText("");
                            }
                        }
                    }
                }
                for (int k = 0; k <= (datalength - 1); k++) {
                    if (!(Sampleid[k].getText().trim().equals(""))) {
                        odatasample = sampleserviceimpl.getsampledata(Sampleid[k].getText().trim());
                        if (odatasample[0][0] != null) {
                            Sample_code[k].setText(odatasample[0][0].toString());
                        }
                        if (odatasample[0][1] != null) {
                            Sample_name[k].setText(odatasample[0][1].toString());
                        }
                    }
                }
            }
            if (updategroupid != -1) {
                for (int k = 0; k <= (datalength - 1); k++) {
                    if (k != updategroupid) {
                        if (checkbox[k].getState()) {
                            Groupid[k].setText(strgroupid);
                            if (strgroupid.trim().equals("")) {
                                Group_name[k].setText("");
                                Group_code[k].setText("");
                            }
                        }
                    }
                }
                for (int k = 0; k <= (datalength - 1); k++) {
                    if (!(Groupid[k].getText().trim().equals(""))) {
                        odatagroup = groupserviceimpl.getgroupdata(Groupid[k].getText().trim());
                        if (odatagroup[0][1] != null) {
                            Group_code[k].setText(odatagroup[0][1].toString());
                        }
                        if (odatagroup[0][2] != null) {
                            Group_name[k].setText(odatagroup[0][2].toString());
                        }
                    }
                }
            }
        }
    }

    public void actionPerformed(ActionEvent evt) {
        PerformAction(evt.getSource());
    }

    public void selectexperimentAllocation(String inputexperimentnumber) {
        pBody.removeAll();
        pBody.add(allocation.getpanelnorthallocation(), BorderLayout.PAGE_START);
        pBody.add(allocation.getpanelcenterallocation(), BorderLayout.CENTER);
        pBody.add(allocation.getpanelsouthallocation(), BorderLayout.PAGE_END);
        if (inputexperimentnumber != null) {
            experimentid.setText(inputexperimentnumber);
        }
        frame.setVisible(true);
    }

    public void allocatesamples() {
        labelrecordsnotallocated.setText("not allocated : ");
        if ((!(experimentid.getText().equalsIgnoreCase("")))
                && (!(filtercombobox.getSelectedItem().toString().equals("")))) {
            String filtertext = filtercombobox.getSelectedItem().toString();
            queryresults = new ResultServiceImpl(this);
            odata_allocation = queryresults.getdata(offset, limit, filtertext);
            datalength2 = 0;
            if (odata_allocation != null) {
                datalength2 = odata_allocation.length;
            } else {
                datalength2 = 0;
            }
            selectsamplecode.setText("");
            displayedsamplecode = "";
            allocation.fill_panels_with_fields();
            if (datalength2 == 0) {
                frame.setVisible(true);
                return;
            }
            allocation.fillbrowsepanel();
            if (!selectsampleid.getText().trim().equalsIgnoreCase("")) {
                selectsamplecode.setText(displayedsamplecode);
            } else {
                selectsamplecode.setText("");
            }
            labelrecordsnotallocated
                    .setText("not allocated : " + String.valueOf(queryresults.getrecordsnotallocated()));
            if (datalength2 != 0) {
                disablebrowsebuttons();
            }
            frame.setVisible(true);
        }
    }

    public void disablefieldsexperiment(boolean newexperiment) {
        if (newexperiment == true) {
            newexperimentid = getexperimentdata.getnewexperimentid();
            if (newexperimentid == null) {
                newexperimentid = "1";
            }
            inputexperimentid.setText(newexperimentid);
            inputexperimentname.setText("Experiment " + newexperimentid);
            search.setEnabled(false);
        } else {
            inputexperimentid.setText("");
            inputexperimentname.setBackground(colorgrey);
            inputexperimentname.setText("");
            search.setEnabled(true);
        }
        inputexperimentid.setEnabled(false);
        inputexperimentid.setBackground(colorgrey);
        equipmentid.setEnabled(false);
        equipmentid.setBackground(colorgrey);
    }

    public void savedata() {
        int datalength = datalength2;
        String filtertype = queryresults.getfiltertype();
        update_lc_ms_offset(datalength);
        String[] arraySampleids = new String[datalength];
        String[] arraySample_codes = new String[datalength];
        String[] arraySample_names = new String[datalength];
        String[] arraygroupids = new String[datalength];
        String[] arraygroupcodes = new String[datalength];
        String[] arraygroupnames = new String[datalength];
        for (int i = 0; i <= (datalength - 1); i++) {
            arraySampleids[i] = Sampleid[i].getText().trim();
            arraySample_codes[i] = Sample_code[i].getText().trim();
            arraySample_names[i] = Sample_name[i].getText().trim();
            arraygroupids[i] = Groupid[i].getText().trim();
            arraygroupcodes[i] = Group_code[i].getText().trim();
            arraygroupnames[i] = Group_name[i].getText().trim();
        }
        String strexperimentid = experimentid.getText();
        insertsamples.insertsamples(arraySampleids, arraySample_codes, arraySample_names, strexperimentid, newsampleid,
                oldsampleid, resultid, oldsamplecode);
        insertGroups.insertgroups(datalength, filtertype, arraygroupids, arraygroupcodes, arraygroupnames,
                strexperimentid, newgroupid, oldgroupid, resultid, oldgroupcode);
    }

    public void update_lc_ms_offset(int datalength) {
        for (int i = 0; i <= (datalength - 1); i++) {
            double_offset_LC_MS = 0;
            try {
                double_offset_LC_MS = Double.parseDouble(Offset_LC_MS[i].getText().trim());
            } catch (Exception e) {
                ;
            }
            resultservice.update_Offset_LC_MS(old_offset_LC_MS[i], double_offset_LC_MS, resultid[i]);
        }
    }

    public boolean generatesamplecodes() {
        return checkboxgeneratesamplecodes.getState();
    }

    public void mouseClicked(MouseEvent evt) {
        allocation.centerallocation.panelallocationcentertotal.Clickedmouse(evt, datalength2);
    }

    public void mouseEntered(MouseEvent evt) {
    }

    public void mouseExited(MouseEvent evt) {
    }

    public void mousePressed(MouseEvent evt) {
    }

    public void mouseReleased(MouseEvent evt) {
        allocation.resetscrollposition();
        offset = (slider.getValue() - (slider.getValue() % limit));
        allocatesamples();
    }

    public void disablebrowsebuttons() {
        int intmaxnumberofrecords = queryresults.getnumberofrecords();
        numberofrecords.setText(String.valueOf(intmaxnumberofrecords));
        if (offset == 0) {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
        }
        if ((offset + limit) >= intmaxnumberofrecords) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
        }
        if (intmaxnumberofrecords > limit) {
            slider.setMinimum(0);
            slider.setMaximum(intmaxnumberofrecords);
            slider.setValue(offset);
            slider.setMajorTickSpacing(intmaxnumberofrecords / 10);
            slider.setPaintTicks(true);
            slider.addMouseListener(this);
        } else {
            slider.setVisible(false);
            labelzero.setText("number of records : ");
        }
    }

    public void itemStateChanged(ItemEvent event) {
        allocation.centerallocation.panelallocationcentertotal.ChangeditemState(event, datalength2);
    }

    public boolean checkfields(int datalength) {
        boolean fields_ok = true;
        for (int i = 0; i <= (datalength - 1); i++) {
            if ((fields_ok) && (Sample_code[i].getText().trim().indexOf(",") > -1)) {
                JOptionPane.showMessageDialog(null, " \",\" in field not allowed ", "Error", JOptionPane.ERROR_MESSAGE);
                fields_ok = false;
            }
            if ((fields_ok) && (Group_code[i].getText().trim().indexOf(",") > -1)) {
                JOptionPane.showMessageDialog(null, " \",\" in field not allowed ", "Error", JOptionPane.ERROR_MESSAGE);
                fields_ok = false;
            }
            if ((fields_ok) && (Sample_name[i].getText().trim().indexOf(",") > -1)) {
                JOptionPane.showMessageDialog(null, " \",\" in field not allowed ", "Error", JOptionPane.ERROR_MESSAGE);
                fields_ok = false;
            }
            if ((fields_ok) && (Group_name[i].getText().trim().indexOf(",") > -1)) {
                JOptionPane.showMessageDialog(null, " \",\" in field not allowed ", "Error", JOptionPane.ERROR_MESSAGE);
                fields_ok = false;
            }
        }
        return fields_ok;
    }

    public void savedata2() {
        if (queryresults != null) {
            if (checkfields(datalength2)) {
                savedata();
                allocatesamples();
            }
        }
    }


    public void propertyChange(PropertyChangeEvent evt) {
        prop = evt.getPropertyName();
        if ((prop.equals(JOptionPane.VALUE_PROPERTY)) && (evt.getSource() == optionsearch)
                && (evt.getNewValue().toString().trim().equalsIgnoreCase("0"))) {
            savedata2();
        }
        if (searchtablerecorddialog != null) {
            if (searchtablerecorddialog.isVisible() && (evt.getSource() == optionsearch)
                    && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                if (evt.getOldValue().toString().trim().equalsIgnoreCase("0")) {
                    optionsearch.setVisible(false);
                } else if (evt.getOldValue().toString().trim().equalsIgnoreCase("1")) {
                    searchtablerecorddialog.setVisible(false);
                } else {
                    searchtablerecorddialog.setVisible(false);
                }
            }
        }
    }

    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
        if (e.getSource() == input_changeingradient1) {
            try {
                inputgradientchange[0] = Double.parseDouble(input_changeingradient1.getText());
            } catch (Exception exc) {
                inputgradientchange[0] = -1;
            }
        }
        if (e.getSource() == input_changeingradient2) {
            try {
                inputgradientchange[1] = Double.parseDouble(input_changeingradient2.getText());
            } catch (Exception exc) {
                inputgradientchange[1] = -1;
            }
        }
        if (e.getSource() == input_changeingradient3) {
            try {
                inputgradientchange[2] = Double.parseDouble(input_changeingradient3.getText());
            } catch (Exception exc) {
                inputgradientchange[2] = -1;
            }
        }
    }

    public void addSourceElements(Object newValue[]) {
        fillListModel(model_fixed_mod, newValue);
    }

    public void addSourceElements2(Object newValue[]) {
        fillListModel(model_variabel_mod, newValue);
    }

    public void addDestinationElements(Object newValue[]) {
        fillListModel(model_all_modifications, newValue);
    }

    public void addSelectedEnzymeElements(Object newValue[]) {
        fillListModel(selected_enzyme_list, newValue);
    }

    public void removeSelectedEnzymeElements(Object newValue[]) {
        fillListModel(potential_enzyme_list, newValue);
    }

    private void fillListModel(SortedListModel model, Object newValues[]) {
        model.addAll(newValues);
    }

    private void clearSourceSelected() {
        // Object selected[] = list_fixed_mod.getSelectedValuesList().toArray();
        //  for (int i = selected.length - 1; i >= 0; --i) {
        //     model_fixed_mod.removeElement(selected[i]);
        //   }
        //   list_fixed_mod.getSelectionModel().clearSelection();


        int[] selectedindices = list_fixed_mod.getSelectedIndices();
        for (int i = 0; i < selectedindices.length; i++) {
            Object selected = list_fixed_mod.getModel().getElementAt(selectedindices[i]);
            model_fixed_mod.removeElement(selected);
        }

        list_fixed_mod.getSelectionModel().clearSelection();


    }

    private void clearSourceSelected2() {
        //Object selected[] = list_variabel_mod.getSelectedValuesList().toArray();
        //for (int i = selected.length - 1; i >= 0; --i) {
        //    model_variabel_mod.removeElement(selected[i]);
        //}
        // list_variabel_mod.getSelectionModel().clearSelection();


        int[] selectedindices = list_variabel_mod.getSelectedIndices();
        for (int i = 0; i < selectedindices.length; i++) {
            Object selected = list_variabel_mod.getModel().getElementAt(selectedindices[i]);
            model_variabel_mod.removeElement(selected);
        }

        list_variabel_mod.getSelectionModel().clearSelection();


    }

    private void clearDestinationSelected() {
        // Object selected[] = list_all_modifications.getSelectedValuesList().toArray();
        //  for (int i = selected.length - 1; i >= 0; --i) {
        //       model_all_modifications.removeElement(selected[i]);
        //   }
        //   list_all_modifications.getSelectionModel().clearSelection();


        int[] selectedindices = list_all_modifications.getSelectedIndices();
        for (int i = 0; i < selectedindices.length; i++) {
            Object selected = list_all_modifications.getModel().getElementAt(selectedindices[i]);
            model_all_modifications.removeElement(selected);
        }

        list_all_modifications.getSelectionModel().clearSelection();


    }

    private void clearPotentialEnzymesSelected() {
        //Object selected[] = list_potential_enzyme.getSelectedValuesList().toArray();
        // for (int i = selected.length - 1; i >= 0; --i) {
        //     potential_enzyme_list.removeElement(selected[i]);
        // }
        //  list_potential_enzyme.getSelectionModel().clearSelection();


        int[] selectedindices = list_potential_enzyme.getSelectedIndices();
        for (int i = 0; i < selectedindices.length; i++) {
            Object selected = list_potential_enzyme.getModel().getElementAt(selectedindices[i]);
            potential_enzyme_list.removeElement(selected);
        }

        list_potential_enzyme.getSelectionModel().clearSelection();


    }

    private void clearSelectedEnzymesSelected() {
        //Object selected[] = list_selected_enzyme.getSelectedValuesList().toArray();
        //for (int i = selected.length - 1; i >= 0; --i) {
        //    selected_enzyme_list.removeElement(selected[i]);
        // }
        // list_selected_enzyme.getSelectionModel().clearSelection();


        int[] selectedindices = list_selected_enzyme.getSelectedIndices();
        for (int i = 0; i < selectedindices.length; i++) {
            Object selected = list_selected_enzyme.getModel().getElementAt(selectedindices[i]);
            selected_enzyme_list.removeElement(selected);
        }

        list_selected_enzyme.getSelectionModel().clearSelection();


    }

    public void setlabelsanddefaults() {
        if (inputgradientchange[0] > -1) {
            input_changeingradient1.setText(String.valueOf(inputgradientchange[0]));
        }
        if (inputgradientchange[1] > -1) {
            input_changeingradient2.setText(String.valueOf(inputgradientchange[1]));
        }
        if (inputgradientchange[2] > -1) {
            input_changeingradient3.setText(String.valueOf(inputgradientchange[2]));
        }
        string_apodization_method = "gaussian";
        btn_Gaussian_Lorentzian.setSelected(true);
        createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                .setVisible(true);
        createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                .setVisible(true);
        createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                .setVisible(true);
        if (centroidingmethod == 0) {
            none.setSelected(true);
        } else if (centroidingmethod == 1) {
            weightedmean.setSelected(true);
        }
        labeltimewindow_lc_ms.setText("Time Window LC MS");
        minimumcountmasses.setText("Mass counts in matrix    > ");
        labeltimealignment.setText("Time Alignment");
        labelpointstimealignment.setText("Minimum points timealignment");
        if (!createMatrixAction.experimentid2.getText().trim().equalsIgnoreCase("")) {
            count_allocated_records = resultservice.countallocatedrecords(createMatrixAction.experimentid2);
            if (count_allocated_records.length > 0) {
                if (Integer.parseInt(count_allocated_records[0][0].toString().trim()) >= 100) {
                    inputminimumcountmasses.setText(String.valueOf((int) Math
                            .round(0.05 * Integer.parseInt(count_allocated_records[0][0].toString().trim()))));
                } else if (Integer.parseInt(count_allocated_records[0][0].toString().trim()) >= 40) {
                    inputminimumcountmasses.setText("4");
                } else if (Integer.parseInt(count_allocated_records[0][0].toString().trim()) >= 30) {
                    inputminimumcountmasses.setText("3");
                } else if (Integer.parseInt(count_allocated_records[0][0].toString().trim()) >= 20) {
                    inputminimumcountmasses.setText("2");
                } else if (Integer.parseInt(count_allocated_records[0][0].toString().trim()) >= 10) {
                    inputminimumcountmasses.setText("1");
                } else {
                    inputminimumcountmasses.setText("0");
                }
            }
            odatafiletypes2 = resultservice.resulttype(createMatrixAction.experimentid2);
            if (odatafiletypes2.length > 0) {
                for (int i = 0; i < odatafiletypes2.length; i++) {
                    for (int j = 0; j < filetypes.length; j++) {
                        if (filetypes[j].trim().indexOf(odatafiletypes2[i][0].toString().trim()) > 0) {
                            filtercombobox2.addItem(filetypes[j]);
                        }
                    }
                }
            } else {
                for (int j = 0; j < filetypes.length; j++) {
                    filtercombobox2.addItem(filetypes[j]);
                }
            }
        }
        if (dataexperiment[3].trim().equalsIgnoreCase("1")) {
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(false);
            input_time_widow_lc_ms.setEditable(false);
            input_time_widow_lc_ms.setBackground(colorgrey);
            performtimealgnment = false;
            checkboxtimealignment.setState(performtimealgnment);
            checkboxtimealignment.setEnabled(false);
            inputpointstimealignment.setEditable(false);
            input_changeingradient1.setEditable(false);
            input_changeingradient2.setEditable(false);
            input_changeingradient3.setEditable(false);
            inputpointstimealignment.setBackground(colorgrey);
            input_changeingradient1.setBackground(colorgrey);
            input_changeingradient2.setBackground(colorgrey);
            input_changeingradient3.setBackground(colorgrey);
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(false);
            if (centroidingmethod < 0) {
                none.setSelected(true);
                centroidingmethod = 0;
            }
            if (clusteringtechnique < 1) {
                clusteringtechnique = 2;
            }
            if (clusteringtechnique == 1) {
                ppm.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (ppm)");
                labellimitcentroiding.setText("limit shift centroiding (ppm)");
                labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                inputdeltamzsearch.setText("250");
                labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                inputdeltamzcombine.setText("250");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                inputdeltamzsearchcalibrant.setText("250");
                inputwindowcentroiding.setText("200");
                inputlimitcentroiding.setText("50");
            } else if (clusteringtechnique == 2) {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.50");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.50");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.50");
                inputwindowcentroiding.setText("0.4");
                inputlimitcentroiding.setText("0.1");
            } else {
                ppm.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (ppm)");
                labellimitcentroiding.setText("limit shift centroiding (ppm)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.50");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.50");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.50");
                inputwindowcentroiding.setText("0.5");
                inputlimitcentroiding.setText("2");
            }
            labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            if (peakfindmethod == 1) {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
                peaksnoisyspectra.setText("1000");
            } else if (peakfindmethod == 2) {
                labelquantilepeakfinding.setText("Quantile threshold peak finding");
                inputquantilepeakfinding.setText("0.98");
                creatematrixmethodcombobox.setSelectedIndex(1);
                peaksnoisyspectra.setText("1000");
            } else {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
                peaksnoisyspectra.setText("1000");
            }
        } else if (dataexperiment[3].trim().equalsIgnoreCase("2")) {
            Raw_to_mzXML.setVisible(false);
            msconvert_programm_64.setVisible(false);
            msconvert_programm_32.setVisible(false);
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(false);
            textfieldmaxchargestate.setText("1");
            textfieldmaxchargestate.setEditable(false);
            textfieldmaxchargestate.setBackground(colorgrey);
            input_time_widow_lc_ms.setEditable(false);
            input_time_widow_lc_ms.setBackground(colorgrey);
            performtimealgnment = false;
            checkboxtimealignment.setState(performtimealgnment);
            checkboxtimealignment.setEnabled(false);
            inputpointstimealignment.setEditable(false);
            inputpointstimealignment.setBackground(colorgrey);
            input_changeingradient1.setEditable(false);
            input_changeingradient1.setBackground(colorgrey);
            input_changeingradient2.setEditable(false);
            input_changeingradient2.setBackground(colorgrey);
            input_changeingradient3.setEditable(false);
            input_changeingradient3.setBackground(colorgrey);
            if (percent_stdevisotopingdistance < 0) {
                percent_stdevisotopingdistance = 0.1;
            }
            inputvarianceisotopicdistance.setText(String.valueOf(percent_stdevisotopingdistance));
            percent_deviation_isotopic_distance = 1;
            deviation_isotopicdistance.setText(String.valueOf(percent_deviation_isotopic_distance));
            if (inputerror < 0) {
                inputerror = 0.15;
            }
            texfieldinputerror.setText(String.valueOf(inputerror));
            if (c13_c12 < 0) {
                c13_c12 = 1.0034;
            }
            isotopicdistance.setText(String.valueOf(c13_c12));
            monoistopefinding = true;
            checkdeisotoping.setState(monoistopefinding);
            if (zerofillingfactor < 0) {
                zerofillingfactor = 4;
            }
            inputzerofillingfaktor.setText(String.valueOf(zerofillingfactor));
            if (GaussfactorB < 0) {
                GaussfactorB = 3.18;
            }
            inputGaussianfaktor.setText(String.valueOf(GaussfactorB));
            if (LorentzfactorA < 0) {
                LorentzfactorA = -3.18;
            }
            inputLorentzfactorA.setText(String.valueOf(LorentzfactorA));
            if (clusteringtechnique < 1) {
                clusteringtechnique = 1;
            }
            if (centroidingmethod < 0) {
                none.setSelected(true);
                centroidingmethod = 0;
            }
            if (clusteringtechnique == 1) {
                ppm.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (ppm)");
                labellimitcentroiding.setText("limit shift centroiding (ppm)");
                labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                inputdeltamzsearch.setText("20");
                labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                inputdeltamzcombine.setText("3");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                inputdeltamzsearchcalibrant.setText("30");
                inputwindowcentroiding.setText("20");
                inputlimitcentroiding.setText("0.5");
            } else if (clusteringtechnique == 2) {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.040");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.006");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.060");
                inputwindowcentroiding.setText("0.020");
                inputlimitcentroiding.setText("0.002");
            } else {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.010");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.010");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.075");
                inputwindowcentroiding.setText("0.010");
                inputlimitcentroiding.setText("2");
            }
            labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            peaksnoisyspectra.setText("50000");
            if (peakfindmethod == 1) {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (peakfindmethod == 2) {
                labelquantilepeakfinding.setText("Quantile threshold peak finding");
                inputquantilepeakfinding.setText("0.92");
                creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            }
        } else if (dataexperiment[3].trim().equalsIgnoreCase("3")) {
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(false);
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                    .setVisible(false);
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(false);
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                    .setVisible(false);
            if (clusteringtechnique < 1) {
                clusteringtechnique = 2;
            }
            if (centroidingmethod < 0) {
                none.setSelected(true);
                centroidingmethod = 0;
            }
            input_time_widow_lc_ms.setText("120");
            performtimealgnment = true;
            checkboxtimealignment.setState(performtimealgnment);
            inputpointstimealignment.setText("100");
            if (clusteringtechnique == 1) {
                ppm.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (ppm)");
                labellimitcentroiding.setText("limit shift centroiding (ppm)");
                labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                inputdeltamzsearch.setText("3");
                labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                inputdeltamzcombine.setText("3");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                inputdeltamzsearchcalibrant.setText("30");
                inputwindowcentroiding.setText("0.5");
                inputlimitcentroiding.setText("2");
            } else if (clusteringtechnique == 2) {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.010");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.200");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.075");
                inputwindowcentroiding.setText("0.5");
                inputlimitcentroiding.setText("2");
            } else {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.010");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.010");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.075");
                inputwindowcentroiding.setText("0.5");
                inputlimitcentroiding.setText("2");
            }
            labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            peaksnoisyspectra.setText("30000");
            if (peakfindmethod == 1) {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (peakfindmethod == 2) {
                labelquantilepeakfinding.setText("Quantile threshold peak finding");
                inputquantilepeakfinding.setText("0.98");
                creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            }
        } else if ((dataexperiment[3].trim().equalsIgnoreCase("4"))
                || (dataexperiment[3].trim().equalsIgnoreCase("5"))) {
            msconvert_programm_32.setSelected(true);
            raw_to_mzXML = "msconvert32";
            if (filtercombobox2 != null) {
                if (filtercombobox2.getSelectedItem().toString().toLowerCase().indexOf(".txt") > -1) {
                    Raw_to_mzXML.setVisible(false);
                    msconvert_programm_64.setVisible(false);
                    msconvert_programm_32.setVisible(false);
                }
                if (filtercombobox2.getSelectedItem().toString().toLowerCase().indexOf(".mzxml") > -1) {
                    Raw_to_mzXML.setVisible(false);
                    msconvert_programm_64.setVisible(false);
                    msconvert_programm_32.setVisible(false);
                }
            }
            perform_ms2_sequencing = true;
            matrix_only_ms2sequenced_masses = false;
            checkbox_ms2sequencing.setState(perform_ms2_sequencing);
            only_ms2sequenced_masses.setState(matrix_only_ms2sequenced_masses);
            input_tol.setText("10");
            if (dataexperiment[3].trim().equalsIgnoreCase("5")) {
                input_tol.setText("200");
            }
            for (int i = 0; i < combo_TOLU.getItemCount(); i++) {
                if (combo_TOLU.getItemAt(i).toString().trim().equalsIgnoreCase("ppm")) {
                    combo_TOLU.setSelectedIndex(i);
                }
            }
            input_itol.setText("0.6");
            for (int i = 0; i < combo_ITOLU.getItemCount(); i++) {
                if (combo_ITOLU.getItemAt(i).toString().trim().equalsIgnoreCase("da")) {
                    combo_ITOLU.setSelectedIndex(i);
                }
            }
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(true);
            btn_Gaussian_Lorentzian.setVisible(false);
            btn_sine_bell.setVisible(false);
            btn_Squared_Sine_bell.setVisible(false);
            btn_Hanning.setVisible(false);
            btn_Hamming.setVisible(false);
            btn_Blackmann_Harris.setVisible(false);
            btn_no_apodization.setVisible(false);
            apodization_method.setVisible(false);
            if (dataexperiment[3].trim().equalsIgnoreCase("4")) {
                createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                        .setBorder(BorderFactory.createTitledBorder("Orbitrap"));
            }
            if (dataexperiment[3].trim().equalsIgnoreCase("5")) {
                createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                        .setBorder(BorderFactory.createTitledBorder("Bruker Daltonics Ion Trap"));
            }
            inputvarianceisotopicdistance.setText("");
            inputvarianceisotopicdistance.setEnabled(false);
            inputvarianceisotopicdistance.setBackground(colorgrey);
            if (minimumnumberoffractions < 0) {
                minimumnumberoffractions = 4;
            }
            inputLorentzfactorA.setText(String.valueOf(minimumnumberoffractions));
            if (missingfractionsallowed < 0) {
                missingfractionsallowed = 100;
            }
            inputGaussianfaktor.setText(String.valueOf(missingfractionsallowed));
            if (max_charge_state < 0) {
                max_charge_state = 3;
            }
            textfieldmaxchargestate.setText(String.valueOf(max_charge_state));
            if (dataexperiment[3].trim().equalsIgnoreCase("4")) {
                percent_deviation_isotopic_distance = 1;
            }
            if (dataexperiment[3].trim().equalsIgnoreCase("5")) {
                percent_deviation_isotopic_distance = 10;
            }
            deviation_isotopicdistance.setText(String.valueOf(percent_deviation_isotopic_distance));
            if (inputerror < 0) {
                inputerror = 0.40;
            }
            texfieldinputerror.setText(String.valueOf(inputerror));
            if (c13_c12 < 0) {
                c13_c12 = 1.0034;
            }
            isotopicdistance.setText(String.valueOf(c13_c12));
            monoistopefinding = true;
            checkdeisotoping.setState(monoistopefinding);
            checkdeisotoping.setEnabled(false);
            labelApodizationformula.setText("");
            labelLorentzianfactor.setText("Number of MS1 scans");
            labelGaussianfaktor.setText("Missing number MS1 scans allowed");
            inputzerofillingfaktor.setEnabled(false);
            inputzerofillingfaktor.setBackground(colorgrey);
            if (clusteringtechnique < 1) {
                clusteringtechnique = 1;
            }
            peakfindmethod = 2;
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                    .setVisible(false);
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(true);
            createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                    .setVisible(false);
            if (clusteringtechnique < 1) {
                clusteringtechnique = 2;
            }
            if (centroidingmethod < 0) {
                none.setSelected(true);
                centroidingmethod = 0;
            }
            input_time_widow_lc_ms.setText("300");
            performtimealgnment = true;
            checkboxtimealignment.setState(performtimealgnment);
            inputpointstimealignment.setText("30");
            if (clusteringtechnique == 1) {
                ppm.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (ppm)");
                labellimitcentroiding.setText("limit shift centroiding (ppm)");
                labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                if (dataexperiment[3].trim().equalsIgnoreCase("4")) {
                    inputdeltamzsearch.setText("50");
                    inputdeltamzcombine.setText("10");
                    inputdeltamzsearchcalibrant.setText("30");
                    inputwindowcentroiding.setText("0.5");
                    inputlimitcentroiding.setText("2");
                }
                if (dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    inputdeltamzsearch.setText("200");
                    inputdeltamzcombine.setText("200");
                    inputdeltamzsearchcalibrant.setText("500");
                    inputwindowcentroiding.setText("0.5");
                    inputlimitcentroiding.setText("2");
                }
            } else if (clusteringtechnique == 2) {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                if (dataexperiment[3].trim().equalsIgnoreCase("4")) {
                    inputdeltamzsearch.setText("0.1");
                    inputdeltamzcombine.setText("0.075");
                    inputdeltamzsearchcalibrant.setText("0.075");
                    inputwindowcentroiding.setText("0.5");
                    inputlimitcentroiding.setText("2");
                }
                if (dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    inputdeltamzsearch.setText("0.2");
                    inputdeltamzcombine.setText("0.2");
                    inputdeltamzsearchcalibrant.setText("0.5");
                    inputwindowcentroiding.setText("0.5");
                    inputlimitcentroiding.setText("2");
                }
            } else {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                if (dataexperiment[3].trim().equalsIgnoreCase("4")) {
                    inputdeltamzsearch.setText("0.1");
                    inputdeltamzcombine.setText("0.02");
                    inputdeltamzsearchcalibrant.setText("0.075");
                    inputwindowcentroiding.setText("0.5");
                    inputlimitcentroiding.setText("2");
                }
                if (dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    inputdeltamzsearch.setText("0.5");
                    inputdeltamzcombine.setText("0.5");
                    inputdeltamzsearchcalibrant.setText("0.5");
                    inputwindowcentroiding.setText("0.5");
                    inputlimitcentroiding.setText("2");
                }
            }
            labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            peaksnoisyspectra.setText("200000");
            if (peakfindmethod == 1) {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (peakfindmethod == 2) {
                labelquantilepeakfinding.setText("Quantile threshold peak finding");
                if (dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    inputquantilepeakfinding.setText("0.90");
                } else {
                    inputquantilepeakfinding.setText("0.75");
                }
                creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            }
            odatamodifications = null;
            try {
                jdbcconnection.con.close();
            } catch (Exception e) {
                ;
            }
            try {
                jdbcconnection.createcon();
            } catch (Exception e) {
                ;
            }
            odatamodifications = systemcodeservice.getsystemcodeitem();
            if (odatamodifications != null) {
                if (odatamodifications.length > 0) {
                    allpossiblemodifications = new String[odatamodifications.length];
                    for (int i = 0; i < odatamodifications.length; i++) {
                        allpossiblemodifications[i] = odatamodifications[i][0].toString();
                    }
                    addDestinationElements(allpossiblemodifications);
                }
            }
            for (int i = 0; i < model_all_modifications.getSize(); i++) {
                if (model_all_modifications.getElementAt(i).toString().toLowerCase().trim()
                        .indexOf("carbamidomethyl (c)") == 0) {
                    model_fixed_mod.add(model_all_modifications.getElementAt(i));
                    model_all_modifications.removeElement(model_all_modifications.getElementAt(i));
                }
                if (model_all_modifications.getElementAt(i).toString().toLowerCase().trim()
                        .indexOf("oxidation (m)") == 0) {
                    model_variabel_mod.add(model_all_modifications.getElementAt(i));
                    model_all_modifications.removeElement(model_all_modifications.getElementAt(i));
                }
            }
            try {
                jdbcconnection.con.close();
            } catch (Exception e) {
                ;
            }
            try {
                jdbcconnection.createcon();
            } catch (Exception e) {
                ;
            }
            odataenzymes = null;
            odataenzymes = systemcodeservice.selectenzymes();
            if (odataenzymes != null) {
                if (odataenzymes.length > 0) {
                    enzymes = new String[odataenzymes.length];
                    for (int i = 0; i < odataenzymes.length; i++) {
                        enzymes[i] = odataenzymes[i][0].toString();
                        if (enzymes[i].trim().equalsIgnoreCase("trypsin")) {
                            selected_enzyme_list.add(enzymes[i].trim());
                        } else {
                            potential_enzyme_list.add(enzymes[i].trim());
                        }
                    }
                }
            }
        } else {
            if (clusteringtechnique < 1) {
                clusteringtechnique = 2;
            }
            if (centroidingmethod < 0) {
                none.setSelected(true);
                centroidingmethod = 0;
            }
            if (clusteringtechnique == 1) {
                ppm.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (ppm)");
                labellimitcentroiding.setText("limit shift centroiding (ppm)");
                labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                inputdeltamzsearch.setText("3");
                labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                inputdeltamzcombine.setText("3");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                inputdeltamzsearchcalibrant.setText("30");
                inputwindowcentroiding.setText("0.5");
                inputlimitcentroiding.setText("2");
            } else if (clusteringtechnique == 2) {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.010");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.010");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.075");
                inputwindowcentroiding.setText("0.5");
                inputlimitcentroiding.setText("2");
            } else {
                Da.setSelected(true);
                labelwindowcentroiding.setText("Window centroiding (Da)");
                labellimitcentroiding.setText("limit shift centroiding (Da)");
                labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                inputdeltamzsearch.setText("0.010");
                labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                inputdeltamzcombine.setText("0.010");
                labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                inputdeltamzsearchcalibrant.setText("0.075");
                inputwindowcentroiding.setText("0.5");
                inputlimitcentroiding.setText("2");
            }
            labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            peaksnoisyspectra.setText("10000");
            if (peakfindmethod == 1) {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (peakfindmethod == 2) {
                labelquantilepeakfinding.setText("Quantile threshold peak finding");
                inputquantilepeakfinding.setText("0.98");
                creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                labelquantilepeakfinding.setText("Signal to noise peak finding");
                inputquantilepeakfinding.setText("4");
                creatematrixmethodcombobox.setSelectedIndex(0);
            }
        }
        creatematrixmethodcombobox.addActionListener(this);
        labelnumberofreplicatessample.setText("Maximum mass spectra per sample ");
        inputnumberofreplicatessample.setText("1");
        labelthresholdbinarytable.setText("Threshold binary table   0 <    1 >=");
        inputthresholdbinarytable.setText("2");
        labelminimummass.setText("Minimum mass (Da)");
        inputminimummass.setText("790");
        if (dataexperiment[3].trim().equalsIgnoreCase("4") || dataexperiment[3].trim().equalsIgnoreCase("5")) {
            inputminimummass.setText("0");
        }
        labelmaximummass.setText("Maximum mass (Da)");
        inputmaximummass.setText("4000");
        if (dataexperiment[3].trim().equalsIgnoreCase("4")) {
            inputmaximummass.setText("10000");
        }
    }

    public void createreport() {
        reportname = strquantilethreshold.trim() + "_" + strexperimentid.trim() + "_" + readdatafiles.calibtext
                + "_sample" + strinputnumberofreplicatessample.trim() + "_binary" + strthresholdbinarymatrix
                + "_quality.txt";
        String strinternalcalibration = "";
        if (internalcalibration) {
            strinternalcalibration = "yes " + linefeed + readdatafiles.calibtext;
        } else {
            strinternalcalibration = "no";
        }
        report = "*******************************************" + linefeed + "experimentid : " + strexperimentid
                + linefeed + "experimentname : " + experimentname2.getText().trim() + linefeed
                + "Find maxima within distance (Da) : " + experiment.getdelta_mz_searchmaximum() + linefeed
                + "Quantile threshold peak finding : " + strquantilethreshold + linefeed
                + "Combine peaks within distance (Da) : " + experiment.getdelta_mz_combine() + linefeed
                + "Noisy spectra contain peak numbers > : " + str_threshold_noisy_spectra + linefeed
                + "Maximum mass spectra per sample : " + strinputnumberofreplicatessample + linefeed
                + "threshold binary table   0 <    1 >= : " + strthresholdbinarymatrix + linefeed
                + "Minimum mass (Da) : " + experiment.getminimum_mass() + linefeed + "Maximum mass (Da) : "
                + experiment.getmaximum_mass() + linefeed + "Calibration : " + strinternalcalibration + linefeed
                + "*******************************************" + linefeed + "number of masses in matrix : "
                + String.valueOf(readdatafiles.numberofmasses).trim() + linefeed
                + "*******************************************";
        reportbuffer = new StringBuffer(report);
        totalnumberofsamples = null;
        totalnumberofsamples = sampleserviceimpl.determine_number_of_samples(strexperimentid);
        numberofsamplesinmatrix = null;
        numberofsamplesinmatrix = sampleserviceimpl.getnumberofsamplesinmatrix(strexperimentid, strquantilethreshold);
        tolownumberofcalibratedsamples = null;
        tolownumberofcalibratedsamples = sampleserviceimpl.getsampleswithtoolownumberofcalibrantmatches(strexperimentid,
                strquantilethreshold);
        numberofnoisypectra = resultservice.getnumberofnoisyspectra(strexperimentid, strquantilethreshold);
        for (int i = 0; i < totalnumberofsamples.length; i++) {
            reportbuffer.append(linefeed + "group : " + totalnumberofsamples[i][2].toString() + "("
                    + totalnumberofsamples[i][1].toString() + ")" + linefeed + "total number of samples : "
                    + totalnumberofsamples[i][0].toString() + linefeed);
            numbersampleinmatrix = "0";
            numbersamplestolowreplicates = "0";
            numbernoisysamples = "0";
            numbersamplesnotcalibrated = 0;
            for (int j = 0; j < numberofsamplesinmatrix.length; j++) {
                if (totalnumberofsamples[i][3].toString().trim()
                        .equalsIgnoreCase(numberofsamplesinmatrix[j][3].toString().trim())) {
                    numbersampleinmatrix = numberofsamplesinmatrix[j][0].toString();
                    reportbuffer.append("number of samples in matrix : " + numbersampleinmatrix + linefeed);
                }
            }
            for (int j = 0; j < tolownumberofcalibratedsamples.length; j++) {
                if (totalnumberofsamples[i][3].toString().trim()
                        .equalsIgnoreCase(tolownumberofcalibratedsamples[j][3].toString().trim())) {
                    numbersamplestolowreplicates = tolownumberofcalibratedsamples[j][0].toString();
                    reportbuffer.append("number of samples with to low number of replicates : "
                            + numbersamplestolowreplicates + linefeed);
                }
            }
            numbersamplesnotcalibrated = (Integer.valueOf(totalnumberofsamples[i][0].toString()).intValue()
                    - Integer.valueOf(numbersampleinmatrix).intValue()
                    - Integer.valueOf(numbersamplestolowreplicates).intValue());
            reportbuffer.append(
                    "number of samples not in matrix that because all spectra cannot not be calibrated or are noisy : "
                            + String.valueOf(numbersamplesnotcalibrated) + linefeed);
            for (int j = 0; j < numberofnoisypectra.length; j++) {
                if (totalnumberofsamples[i][3].toString().trim()
                        .equalsIgnoreCase(numberofnoisypectra[j][3].toString().trim())) {
                    numbernoisysamples = numberofnoisypectra[j][0].toString();
                    reportbuffer.append("(total number of " + numbernoisysamples
                            + " sample(s) with at least one noisy spectrum)" + linefeed);
                }
            }
            reportbuffer.append("*******************************************");
        }
        report = reportbuffer.toString();
        updatesample = storereport.storereport(report, strexperimentid, dataexperiment[4], reportname,
                strquantilethreshold);
    }

    public void displaymatrix(String resultid) {
        createMatrixAction.creatematrix.getcreatematrixcenterpanelsouth().removeAll();
        if (resultid != null) {
            matrixfilename = resultservice.returnmatrixfilename(resultid);
            filepresent = false;
            byte[] data = null;
            if (ftp != null) {
            } else {
                ftp = new FTPClient();
            }
            try {
                ftp.setRemoteHost(ftpremotehost);
                ftp.connect();
                ftp.login(ftpuser, ftppassword);
                ftp.setConnectMode(FTPConnectMode.PASV);
                ftp.setType(FTPTransferType.BINARY);
                ftp.chdir(File.separator + dataexperiment[4] + File.separator + createMatrixAction.experimentid2.getText().trim());
                numberofrows = 0;
            } catch (Exception e) {
                if (debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            filepresent = false;
            data = null;
            try {
                data = ftp.get(matrixfilename);
                filepresent = true;
            } catch (Exception e) {
                filepresent = false;
            }
            try {
                ftp.quit();
            } catch (Exception e) {
                ;
            }
            for (int j = 0; j < tableheader.length; j++) {
                tableheader[j] = "";
                for (int k = 0; k < tabledata.length; k++) {
                    tabledata[k][j] = "";
                }
            }
            if (filepresent) {
                line = "";
                linebuffer = new StringBuffer(line);
                numberofrows = 0;
                numberofrows2 = 0;
                numberofcolums = 0;
                for (int i = 0; i < data.length; i++) {
                    if ((data[i] != 10) && (data[i] != 13)) {
                        linebuffer.append((char) data[i]);
                    }
                    if (data[i] == 10) {
                        line = linebuffer.toString();
                        temp = null;
                        temp = line.split(",");
                        if (numberofrows == 0) {
                            numberofcolums = temp.length;
                            for (int j = 0; ((j < temp.length) && (j < tableheader.length)); j++) {
                                tableheader[j] = temp[j];
                            }
                        } else {
                            if (numberofrows <= tabledata.length) {
                                for (int j = 0; ((j < temp.length) && (j < tableheader.length)); j++) {
                                    tabledata[numberofrows - 1][j] = temp[j];
                                }
                            }
                        }
                        numberofrows++;
                        if (!temp[0].trim().equalsIgnoreCase("")) {
                            numberofrows2++;
                        }
                        line = "";
                        linebuffer = new StringBuffer(line);
                    }
                }
                if (temp != null) {
                    defaulttablemodel.setDataVector(tabledata, tableheader);
                    if (sorter == null) {
                        sorter = new TableSorter(defaulttablemodel);
                    } else {
                        sorter.setTableModel(defaulttablemodel);
                    }
                    if (matrixtable == null) {
                        matrixtable = new JTable(sorter);
                        matrixtable.setPreferredScrollableViewportSize(
                                createMatrixAction.creatematrix.creatematrix_center.creatematrixcenter.getSize());
                    } else {
                        matrixtable.setModel(sorter);
                    }
                    sorter.setTableHeader(matrixtable.getTableHeader());
                    browserpane = new JScrollPane(matrixtable);
                    createMatrixAction.creatematrix.creatematrix_center.centerpanelsouthcreatematrix.displaymatrix(temp, numberofrows2,
                            browserpane);
                }
            }
        }
    }

    public void PerformAction(Object choice) {
        if (slavepane != null) {
            for (int i = 0; i < slavepane.length; i++) {
                if (choice == slavepane[i]) {
                    updateview.viewtablerecord(slavepane[i].table, slavepane[i].primarykey,
                            slavepane[i].primarykeyvalue);
                }
            }
        }
        if (choice == savereportallocation) {
            reportallocation.saveallocation.save_allocation(reportallocation.resultsettablemodel, exportfiletodisk,
                    queryresults);
        }
        if (choice == searchselection) {
            updateview = new UpdateView(this);
            updateview.viewtablerecord(searchselection.TableName, searchselection.primarykey,
                    searchselection.primarykeyvalue);
        }
        if (choice == searchtablerecorddialog) {
            updatefield.setText(updatefieldvalue);
        }
        if (choice == inputexperimentid) {
            if ((!isnewexperiment) && (!(inputexperimentid.getText().trim().equals("")))) {
                existingexperiment = getexistingexperiment.getexperimentname(inputexperimentid.getText().trim());
                inputexperimentname.setText(existingexperiment.getstr_experimentname());
            }
        }
        if (choice == equipmentcombo) {
            if ((equipmentid != null) && (odataequipment != null)) {
                equipmentid.setText((String) odataequipment[equipmentcombo.getSelectedIndex()][0]);
            }
        }
        if (choice == existradio) {
            Getfiles.disableexperimentfields();
        }
        if (choice == newradio) {
            Getfiles.enableexperimentfields();
        }
        if (choice == search) {
            updatefield = inputexperimentid;
            dialogtitle = "Search Experiment";
            optionsearch = new JOptionPane(dialogtitle, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
            searchtablerecorddialog = optionsearch.createDialog(filechooserframe, dialogtitle);
            optionsearch.addPropertyChangeListener(this);
            searchtablerecordpanel = new JPanel();
            searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
            searchselection = new SearchSelection(this, searchtablerecorddialog, searchtablerecordpanel);
            wherestring = "";
            searchselection.selectbrowser("Experiment", wherestring);
            searchtablerecorddialog.setSize(900, 700);
            searchtablerecorddialog.setVisible(true);
        }
        if (choice == selectinputsample) {
            updatefield = selectsampleid;
            String dialogtitle = "Search sample";
            searchtablerecorddialog = new JDialog(frame, dialogtitle);
            JPanel searchtablerecordpanel = new JPanel();
            searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
            SearchSelection searchselection = new SearchSelection(this, searchtablerecorddialog,
                    searchtablerecordpanel);
            String wherestring = "";
            searchselection.selectbrowser("sample", wherestring);
            searchtablerecorddialog.setSize(900, 700);
            searchtablerecorddialog.setVisible(true);
        }
        if (choice == search_sample) {
            updatefield = experimentid;
            searchtablerecorddialog = new JDialog(frame, "Search experiment");
            searchtablerecordpanel2 = new JPanel();
            searchtablerecorddialog.getContentPane().add(searchtablerecordpanel2);
            searchselection = new SearchSelection(this, searchtablerecorddialog, searchtablerecordpanel2);
            wherestring = "";
            searchselection.selectbrowser("experiment", wherestring);
            searchtablerecorddialog.setSize(900, 700);
            searchtablerecorddialog.setVisible(true);
        }
        if (choice == buttonsavedetails) {
            storedetailsWilcoxon.save_detailslocal();
        }
        matrixfileslength = 0;
        if (matrixfiles != null) {
            matrixfileslength = matrixfiles.length;
        }
        if (choice == startwilcoxon) {
            if ((groupnumbers.length > 1) && (matrixfileslength > 0)) {
                try {
                    startwilcoxon.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    startWilcoxon = new StartWilcoxon(this);
                    startWilcoxon.startwilcoxon(progress);
                } finally {
                    startwilcoxon.setCursor(Cursor.getDefaultCursor());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Equal groups or no matrices", "Warning",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
        if (choice == searchwilcoxon) {
            updatefield = experimentidwilcoxon;
            dialogtitle = "Search experiment";
            searchtablerecorddialog = new JDialog(this.frame, dialogtitle);
            searchtablerecordpanel = new JPanel();
            searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
            searchselection = new SearchSelection(this, searchtablerecorddialog, searchtablerecordpanel);
            wherestring = "";
            searchselection.selectbrowser("experiment", wherestring);
            searchtablerecorddialog.setSize(900, 700);
            searchtablerecorddialog.setVisible(true);
        }
        if ((choice == combomatrixtodisplaywilcoxon) || (choice == combogroupnumber1)
                || (choice == combogroupnumber2)) {
            resultService.getodatagroupnumbers();
            if (resultService.getodatagroupnumbers() != null) {
                if (resultService.getodatagroupnumbers().length > 0) {
                    Object[][] odatagroupnumbers = resultService.getodatagroupnumbers();
                    display_p_data(combomatrixtodisplaywilcoxon.getSelectedItem().toString(),
                            odatagroupnumbers[combogroupnumber1.getSelectedIndex()][1].toString().trim(),
                            odatagroupnumbers[combogroupnumber2.getSelectedIndex()][1].toString().trim());
                }
            }
        }
        if (choice == allocation) {
            updateview = new UpdateView(this);
            updateview.viewtablerecord(TableName, primarykey, primarykeyvalue);
        }
        if (choice == Import) {
            Getfiles.getFiles();
        }
        if (choice == Code) {
            wherestring = "";
            searchselection.selectbrowser("Systemcode", wherestring);
        }
        if (choice == combomatrixtodisplay) {
            int test = combomatrixtodisplay.getSelectedIndex();
            if (test + 1 <= matrixfiles.length) {
                matrixtodisplay = true;
                if (matrixfiles.length == 1) {
                    if (matrixfiles[0].equalsIgnoreCase("")) {
                        matrixtodisplay = false;
                    }
                }
                if (matrixtodisplay) {
                    displaymatrix(collectmatrixfiles.getresultid(test));
                }
                frame.setVisible(true);
            }
        }
        if (choice == button_add_variabel) {
            //Object selected[] = list_all_modifications.getSelectedValuesList().toArray();

            int[] selectedIndices = list_all_modifications.getSelectedIndices();
            Object[] selected = new Object[selectedIndices.length];
            for (int i = 0; i < selectedIndices.length; i++) {
                selected[i] = list_all_modifications.getModel().getElementAt(selectedIndices[i]);
            }


            addSourceElements2(selected);
            clearDestinationSelected();
        }
        if (choice == button_remove_variabel) {
            //Object selected[] = list_variabel_mod.getSelectedValuesList().toArray();

            int[] selectedIndices = list_variabel_mod.getSelectedIndices();
            Object[] selected = new Object[selectedIndices.length];
            for (int i = 0; i < selectedIndices.length; i++) {
                selected[i] = list_variabel_mod.getModel().getElementAt(selectedIndices[i]);
            }


            addDestinationElements(selected);
            clearSourceSelected2();
        }
        if (choice == button_add_fixed) {
            // Object selected[] = list_all_modifications.getSelectedValuesList().toArray();

            int[] selectedIndices = list_all_modifications.getSelectedIndices();
            Object[] selected = new Object[selectedIndices.length];
            for (int i = 0; i < selectedIndices.length; i++) {
                selected[i] = list_all_modifications.getModel().getElementAt(selectedIndices[i]);
            }


            addSourceElements(selected);
            clearDestinationSelected();
        }
        if (choice == button_remove_enzyme) {
            //Object selected[] = list_selected_enzyme.getSelectedValuesList().toArray();

            int[] selectedIndices = list_selected_enzyme.getSelectedIndices();
            Object[] selected = new Object[selectedIndices.length];
            for (int i = 0; i < selectedIndices.length; i++) {
                selected[i] = list_selected_enzyme.getModel().getElementAt(selectedIndices[i]);
            }


            removeSelectedEnzymeElements(selected);
            clearSelectedEnzymesSelected();
        }
        if (choice == button_add_enzyme) {
            //Object selected[] = list_potential_enzyme.getSelectedValuesList().toArray();

            int[] selectedIndices = list_potential_enzyme.getSelectedIndices();
            Object[] selected = new Object[selectedIndices.length];
            for (int i = 0; i < selectedIndices.length; i++) {
                selected[i] = list_potential_enzyme.getModel().getElementAt(selectedIndices[i]);
            }


            addSelectedEnzymeElements(selected);
            clearPotentialEnzymesSelected();
        }
        if (choice == button_remove_fixed) {
            //Object selected[] = list_fixed_mod.getSelectedValuesList().toArray();

            int[] selectedIndices = list_fixed_mod.getSelectedIndices();
            Object[] selected = new Object[selectedIndices.length];
            for (int i = 0; i < selectedIndices.length; i++) {
                selected[i] = list_fixed_mod.getModel().getElementAt(selectedIndices[i]);
            }

            addDestinationElements(selected);
            clearSourceSelected();
        }
        if (choice == btnwilcoxon2) {
            if (!createMatrixAction.experimentid2.getText().trim().equalsIgnoreCase("")) {
                createwilcoxon = new CreateWilcoxon(this);
                createwilcoxon.selectexperiment(createMatrixAction.experimentid2.getText().trim());
            }
        }
        if (choice == allocation2) {
            if (!createMatrixAction.experimentid2.getText().trim().equalsIgnoreCase("")) {
                selectexperimentAllocation(createMatrixAction.experimentid2.getText().trim());
            }
        }
        if (choice == creatematrixmethodcombobox) {
            int test = creatematrixmethodcombobox.getSelectedIndex();
            peakfindmethod = test + 1;
            createMatrixAction.creatematrix.fill_panels_with_fields();
            setlabelsanddefaults();
            setbackground(colorgrey);
            frame.setVisible(true);
        }
        if (choice == btn_Gaussian_Lorentzian) {
            string_apodization_method = "gaussian";
        }
        if (choice == btn_sine_bell) {
            string_apodization_method = "sine";
        }
        if (choice == btn_Squared_Sine_bell) {
            string_apodization_method = "squared_sine";
        }
        if (choice == btn_Hanning) {
            string_apodization_method = "hanning";
        }
        if (choice == btn_Hamming) {
            string_apodization_method = "hamming";
        }
        if (choice == btn_Blackmann_Harris) {
            string_apodization_method = "blackman";
        }
        if (choice == btn_no_apodization) {
            string_apodization_method = "none";
        }
        if (choice == search_experiment) {
            updatefield = createMatrixAction.experimentid2;
            dialogtitle = "Search experiment";
            searchtablerecorddialog = new JDialog(frame, dialogtitle);
            searchtablerecordpanel = new JPanel();
            searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
            searchselection = new SearchSelection(this, searchtablerecorddialog, searchtablerecordpanel);
            wherestring = "";
            searchselection.selectbrowser("experiment", wherestring);
            searchtablerecorddialog.setSize(900, 700);
            searchtablerecorddialog.setVisible(true);
        }
        if (choice == Da) {
            clusteringtechnique = 2;
            createMatrixAction.creatematrix.fill_panels_with_fields();
            setlabelsanddefaults();
            setbackground(colorgrey);
            frame.setVisible(true);
        }
        if (choice == ppm) {
            clusteringtechnique = 1;
            createMatrixAction.creatematrix.fill_panels_with_fields();
            setlabelsanddefaults();
            setbackground(colorgrey);
            frame.setVisible(true);
        }
        if (choice == msconvert_programm_64) {
            raw_to_mzXML = "msconvert64";
        }
        if (choice == msconvert_programm_32) {
            raw_to_mzXML = "msconvert32";
        }
        if (choice == none) {
            centroidingmethod = 0;
        }
        if (choice == weightedmean) {
            centroidingmethod = 1;
        }
        if (choice == startcreatingmatrix) {
            performtimealgnment = checkboxtimealignment.getState();
            experiment = new Experiment();
            experiment.setraw_to_mzXML(raw_to_mzXML);
            strexperimentid = createMatrixAction.experimentid2.getText().trim();
            experiment.setExperimentid(createMatrixAction.experimentid2.getText().trim());
            internalcalibration = checkbox_internalcalibration.getState();
            strinputnumberofreplicatessample = inputnumberofreplicatessample.getText().trim();
            str_threshold_noisy_spectra = peaksnoisyspectra.getText().trim();
            experiment.setnumbermisfitsallowed(missingcalibratiopeaksallowed.getText().trim());
            systemcodeitemid = getcalibrationstandards.getcodeitemid(calibrationstandard.getSelectedIndex());
            strquantilethreshold = inputquantilepeakfinding.getText().trim();
            experiment.setquantilethreshold(strquantilethreshold);
            experiment.setdelta_mz_searchmaximum(inputdeltamzsearch.getText().trim());
            experiment.setdelta_mz_combine(inputdeltamzcombine.getText().trim());
            experiment.setminimum_mass(inputminimummass.getText().trim());
            experiment.setmaximum_mass(inputmaximummass.getText().trim());
            strthresholdbinarymatrix = inputthresholdbinarytable.getText().trim();
            experiment.setthresholdbinarymatrix(inputthresholdbinarytable.getText().trim());
            filetype = "";
            if (filtercombobox2.getSelectedItem().toString().equals(textfilter.getDescription())) {
                filetype = "txt";
                internalcalibration = false;
            }
            if (filtercombobox2.getSelectedItem().toString().equals(fidfilter.getDescription())) {
                filetype = "fid";
            }
            if (filtercombobox2.getSelectedItem().toString().equals(xmlfilter.getDescription())) {
                filetype = "xml";
                internalcalibration = false;
            }
            if (filtercombobox2.getSelectedItem().toString().equals(mzxmlfilter.getDescription())) {
                filetype = "mzxml";
                internalcalibration = false;
            }
            if (filtercombobox2.getSelectedItem().toString().equals(rawfilter.getDescription())) {
                filetype = "raw";
                internalcalibration = false;
            }
            experiment.setfiletype(filetype);
            deletemasseswithcount = 0;
            if (!inputminimumcountmasses.getText().trim().equalsIgnoreCase("")) {
                if (Integer.parseInt(inputminimumcountmasses.getText().trim()) > 0) {
                    deletemasseswithcount = Integer.parseInt(inputminimumcountmasses.getText().trim());
                }
            }
            experiment.setminimum_number_of_masses_that_should_be_present(deletemasseswithcount);
            if (!inputwindowcentroiding.getText().trim().equalsIgnoreCase("")) {
                deltaxcentroiding = Double.parseDouble(inputwindowcentroiding.getText().trim());
            }
            if (!inputlimitcentroiding.getText().trim().equalsIgnoreCase("")) {
                limitshiftcentroiding = Double.parseDouble(inputlimitcentroiding.getText().trim());
            }
            ndiv = 20;
            if (!inputsingnaltonoiseinparts.getText().trim().equalsIgnoreCase("")) {
                try {
                    ndiv = Integer.getInteger(inputsingnaltonoiseinparts.getText().trim()).intValue();
                } catch (Exception e) {
                }
            }
            LorentzfactorA = 0;
            GaussfactorB = 0;
            if (!inputGaussianfaktor.getText().trim().equalsIgnoreCase("")) {
                try {
                    GaussfactorB = Double.parseDouble(inputGaussianfaktor.getText().trim());
                } catch (Exception ex) {
                }
                try {
                    missingfractionsallowed = Integer.parseInt(inputGaussianfaktor.getText().trim());
                } catch (Exception ex) {
                }
            }
            if (!inputLorentzfactorA.getText().trim().equalsIgnoreCase("")) {
                try {
                    LorentzfactorA = Double.parseDouble(inputLorentzfactorA.getText().trim());
                } catch (Exception ex) {
                }
                try {
                    minimumnumberoffractions = Integer.parseInt(inputLorentzfactorA.getText().trim());
                } catch (Exception ex) {
                }
            }
            zerofillingfactor = 1;
            if (!inputzerofillingfaktor.getText().trim().equalsIgnoreCase("")) {
                try {
                    zerofillingfactor = Integer.parseInt(inputzerofillingfaktor.getText().trim());
                } catch (Exception ex) {
                }
            }
            if (dataexperiment[3].trim().equalsIgnoreCase("2")) {
                monoistopefinding = checkdeisotoping.getState();
            } else {
                monoistopefinding = false;
            }
            deltatimecombine = 0;
            if (dataexperiment[3].trim().equalsIgnoreCase("3") || dataexperiment[3].trim().equalsIgnoreCase("4")
                    || dataexperiment[3].trim().equalsIgnoreCase("5")) {
                if (!input_time_widow_lc_ms.getText().trim().equalsIgnoreCase("")) {
                    if (Double.parseDouble(input_time_widow_lc_ms.getText().trim()) > 0) {
                        deltatimecombine = Double.parseDouble(input_time_widow_lc_ms.getText().trim());
                    }
                }
            }
            if (!isotopicdistance.getText().trim().equalsIgnoreCase("")) {
                if (Double.parseDouble(isotopicdistance.getText().trim()) > 0) {
                    c13_c12 = Double.parseDouble(isotopicdistance.getText().trim());
                }
            }
            if (!deviation_isotopicdistance.getText().trim().equalsIgnoreCase("")) {
                if (Double.parseDouble(deviation_isotopicdistance.getText().trim()) > 0) {
                    percent_deviation_isotopic_distance = Double
                            .parseDouble(deviation_isotopicdistance.getText().trim());
                }
            }
            if (!inputvarianceisotopicdistance.getText().trim().equalsIgnoreCase("")) {
                if (Double.parseDouble(inputvarianceisotopicdistance.getText().trim()) > 0) {
                    percent_stdevisotopingdistance = Double.parseDouble(inputvarianceisotopicdistance.getText().trim());
                }
            }
            numberpointstimealignment = 100;
            if (!inputpointstimealignment.getText().trim().equalsIgnoreCase("")) {
                if (Integer.parseInt(inputpointstimealignment.getText().trim()) > 0) {
                    numberpointstimealignment = Integer.parseInt(inputpointstimealignment.getText().trim());
                }
            }
            timeclusteringtechnique = 2;
            if (!texfieldinputerror.getText().trim().equalsIgnoreCase("")) {
                testinputerror = Double.parseDouble(texfieldinputerror.getText().trim());
                inputerror = testinputerror.doubleValue();
            }
            if (!textfieldmaxchargestate.getText().trim().equalsIgnoreCase("")) {
                max_charge_state = Integer.parseInt(textfieldmaxchargestate.getText().trim());
            }
            selected_enzymes = null;
            if (selected_enzyme_list.getSize() > 0) {
                selected_enzymes = new String[selected_enzyme_list.getSize()];
                for (int i = 0; i < selected_enzyme_list.getSize(); i++) {
                    selected_enzymes[i] = selected_enzyme_list.getElementAt(i).toString().trim();
                }
            }
            model_fixed_mod_Size = 0;
            try {
                model_fixed_mod_Size = model_fixed_mod.getSize();
            } catch (Exception ex) {
            }
            array_fixed_modifications = null;
            if (model_fixed_mod_Size > 0) {
                array_fixed_modifications = new String[model_fixed_mod_Size];
                for (int i = 0; i < model_fixed_mod_Size; i++) {
                    array_fixed_modifications[i] = model_fixed_mod.getElementAt(i).toString().trim();
                }
            }
            model_variabel_mod_Size = 0;
            try {
                model_variabel_mod_Size = model_variabel_mod.getSize();
            } catch (Exception ex) {
            }
            array_variabel_modifications = null;
            if (model_variabel_mod_Size > 0) {
                array_variabel_modifications = new String[model_variabel_mod_Size];
                for (int i = 0; i < model_variabel_mod_Size; i++) {
                    array_variabel_modifications[i] = model_variabel_mod.getElementAt(i).toString().trim();
                }
            }
            TOL = "";
            try {
                TOL = input_tol.getText();
            } catch (Exception e) {
            }
            TOLU = "";
            try {
                TOLU = combo_TOLU.getSelectedItem().toString().trim();
            } catch (Exception e) {
            }
            ITOL = "";
            try {
                ITOL = input_itol.getText();
            } catch (Exception e) {
            }
            ITOLU = "";
            try {
                ITOLU = combo_ITOLU.getSelectedItem().toString().trim();
            } catch (Exception e) {
            }
            string_search_engine = "X!tandem";
            try {
                string_search_engine = combo_searchengine.getSelectedItem().toString().trim();
            } catch (Exception e) {
            }
            perform_ms2_sequencing = false;
            try {
                perform_ms2_sequencing = checkbox_ms2sequencing.getState();
            } catch (Exception e) {
            }
            matrix_only_ms2sequenced_masses = false;
            try {
                matrix_only_ms2sequenced_masses = only_ms2sequenced_masses.getState();
            } catch (Exception e) {
            }
            string_Taxonomy = "";
            try {
                string_Taxonomy = combo_Taxonomy.getSelectedItem().toString().trim();
            } catch (Exception e) {
            }
            experiment.setExperimentyear(dataexperiment[4]);
            experiment.setInternalcalibration(internalcalibration);
            experiment.setNumberofreplicatessample(inputnumberofreplicatessample.getText().trim());
            experiment.setThreshold_noisy_spectra(peaksnoisyspectra.getText().trim());
            experiment.setclusteringtechnique(clusteringtechnique);
            experiment.setequipmentid(Integer.parseInt(dataexperiment[3].trim()));
            experiment.setEquipmentcode(dataexperiment[5]);
            if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
                    || (experiment.getequipmentid() == 5)) {
                experiment.setHas_retentiontime(true);
            }
            experiment.setpeakfindmethod(peakfindmethod);
            experiment.setid_calibrationmasses(Integer.parseInt(systemcodeitemid));
            experiment.setdelta_mz_search_calibrants(inputdeltamzsearchcalibrant.getText().trim());
            experiment.setdivisions_in_determination_noise(ndiv);
            experiment.setisotopic_distance_c13_c12(c13_c12);
            experiment.settaxonomy_name(string_Taxonomy);
            experiment.setperform_ms2_sequencing(perform_ms2_sequencing);
            experiment.set_only_ms2_sequencedmasses(matrix_only_ms2sequenced_masses);
            experiment.setTOL(TOL);
            experiment.setTOLU(TOLU);
            experiment.setITOL(ITOL);
            experiment.setITOLU(ITOLU);
            experiment.setname_search_engine(string_search_engine);
            experiment.setGaussfactorB(GaussfactorB);
            experiment.setLorentzfactorA(LorentzfactorA);
            experiment.setminimum_points_required_for_time_alignment(numberpointstimealignment);
            experiment.setperform_time_alignment(performtimealgnment);
            experiment.setmissing_number_ms_scans_allowed(missingfractionsallowed);
            experiment.setpeptide_present_in_minimumnumberoffractions(minimumnumberoffractions);
            experiment.setzerofillingfactor(zerofillingfactor);
            experiment.setSelected_enzymes(selected_enzymes);
            experiment.setmax_charge_state_peptide(max_charge_state);
            experiment.setdeviation_from_expected_intensity_ratio(inputerror);
            experiment.setpercent_deviation_from_isotopic_distance(percent_deviation_isotopic_distance);
            experiment.setvariance_isotopic_distance(percent_stdevisotopingdistance);
            experiment.settime_window_combining_peptide_masses(deltatimecombine);
            experiment.setFT_ICR_apodization_method(string_apodization_method);
            experiment.setperform_deisotoping(monoistopefinding);
            experiment.setchange_in_gradient_after_time(inputgradientchange);
            experiment.setmethod_peak_centroiding(centroidingmethod);
            experiment.setvariabel_peptide_modifications(array_variabel_modifications);
            experiment.setfixed_peptide_modifications(array_fixed_modifications);
            experiment.settime_clustering_absolute_or_percentage(timeclusteringtechnique);
            experiment.setwindow_centroiding_ppm(deltaxcentroiding);
            experiment.setmaximal_shift_by_centroiding_ppm(limitshiftcentroiding);
            readdatafiles = new Readdatafiles(this, exportfiletodisk, experiment);
            readdatafiles.creatematrix();
            createreport();
            if (readdatafiles != null) {
                readdatafiles = null;
            }
            odatanewresultid = resultservice.displaynewmatrix(createMatrixAction.experimentid2.getText().trim());
            displaymatrix(odatanewresultid[0][0].toString().trim());
            createMatrixAction.experimentid2.setText(createMatrixAction.experimentid2.getText().trim());
        }
        if (choice == btnGroup) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("group_", wherestring);
        }
        if (choice == btnPatient) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("person", wherestring);
        }
        if (choice == btnOrgan) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("origin", wherestring);
        }
        if (choice == btnTissue) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("material", wherestring);
        }
        if (choice == btnSample_) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("Sample", wherestring);
        }
        if (choice == btnExperiment) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("experiment", wherestring);
        }
        if (choice == btnEquipment) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("equipment", wherestring);
        }
        if (choice == btnResult) {
            wherestring = "";
            searchselection = new SearchSelection(this, frame, pBody);
            searchselection.selectbrowser("result", wherestring);
        }
        if (choice == btnallocation) {
            selectexperimentAllocation("-1");
        }
        if (choice == btncreatematrix) {
            selectexperiment("-1");
        }
        if (choice == btnfasta) {
            selectexperiment("-1");
        }
        if (choice == btnwilcoxon) {
            createwilcoxon = new CreateWilcoxon(this);
            createwilcoxon.selectexperiment(null);
        }
        if (choice == btntransposematrix) {
            transposematrix = new TransposeMatrix(this);
            transposematrix.selectexperiment(null);
        }
        if (choice == btnrefresh) {
            allocatesamples();
        }
        if (choice == btnNext) {
            allocation.resetscrollposition();
            offset = offset + limit;
            allocatesamples();
        }
        if (choice == btnLast) {
            allocation.resetscrollposition();
            offset = queryresults.getnumberofrecords() - (queryresults.getnumberofrecords() % limit);
            allocatesamples();
        }
        if (choice == btnFirst) {
            allocation.resetscrollposition();
            offset = 0;
            allocatesamples();
        }
        if (choice == btnPrevious) {
            allocation.resetscrollposition();
            offset = offset - limit;
            if (offset < 0) {
                offset = 0;
            }
            allocatesamples();
        }
        if (choice == btnreport) {
            getReportallocation().reportallocation(queryresults.getfiltertype(), sorter, allocationtable,
                    allocation.centerallocation.panelallocationcentertotal.allocationcenterpanelsouth);
        }
        if (choice == creatematrix2) {
            if (!experimentid.getText().trim().equalsIgnoreCase("")) {
                selectexperiment(experimentid.getText().trim());
            }
        }
        if (choice == btnsave) {
            errornotshownbefore = true;
            dialogtitle = "Save allocation";
            optionsearch = new JOptionPane(dialogtitle, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
            searchtablerecorddialog = optionsearch.createDialog(this.frame, dialogtitle);
            optionsearch.addPropertyChangeListener(this);
            searchtablerecorddialog.setSize(250, 100);
            searchtablerecorddialog.setVisible(true);
        }
        if (choice == clearselectedsample) {
            importsampleid = "";
            offset = 0;
            selectsampleid.setText("");
            allocatesamples();
        }
        if (choice == filtercombobox) {
            if (filtercombobox.getSelectedItem() != null) {
                allocation.resetscrollposition();
                allocatesamples();
            }
        }
        if (odata_allocation != null) {
            for (int i = 0; i <= (datalength2 - 1); i++) {
                if (choice == searchsample[i]) {
                    updatefield = Sampleid[i];
                    Sampleid[i].addTextListener(createMatrixAction);
                    dialogtitle = "Search sample";
                    searchtablerecorddialog = new JDialog(this.frame, dialogtitle);
                    searchtablerecordpanel = new JPanel();
                    searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
                    searchselection = new SearchSelection(this, searchtablerecorddialog, searchtablerecordpanel);
                    wherestring = "";
                    searchselection.selectbrowser("sample", wherestring);
                    searchtablerecorddialog.setSize(900, 700);
                    searchtablerecorddialog.setVisible(true);
                }
                if (choice == searchgroup[i]) {
                    updatefield = Groupid[i];
                    Groupid[i].addTextListener(createMatrixAction);
                    dialogtitle = "Search group";
                    searchtablerecorddialog = new JDialog(this.frame, dialogtitle);
                    searchtablerecordpanel = new JPanel();
                    searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
                    searchselection = new SearchSelection(this, searchtablerecorddialog, searchtablerecordpanel);
                    wherestring = "";
                    searchselection.selectbrowser("group_", wherestring);
                    searchtablerecorddialog.setSize(900, 700);
                    searchtablerecorddialog.setVisible(true);
                }
                if (choice == clearsample[i]) {
                    Sampleid[i].addTextListener(createMatrixAction);
                    Sampleid[i].setText("");
                    Sample_name[i].setText("");
                    Sample_code[i].setText("");
                }
                if (choice == cleargroup[i]) {
                    Groupid[i].addTextListener(createMatrixAction);
                    Groupid[i].setText("");
                    Group_name[i].setText("");
                    Group_code[i].setText("");
                }
            }
        }
        if (choice == selectsampleid) {
            if (!selectsampleid.getText().trim().equalsIgnoreCase("")) {
                offset = 0;
                importsampleid = selectsampleid.getText().trim();
                allocatesamples();
            }
        }
        if (choice == experimentid) {
            offset = 0;
            selectsampleid.setText("");
            dataexperiment = getexperimentdata.getexperimentdata(experimentid.getText());
            if (experimentid.getText().trim().equalsIgnoreCase("-1")) {
                dataexperiment[0] = "";
                dataexperiment[1] = "";
                dataexperiment[2] = "";
                dataexperiment[3] = "";
                dataexperiment[4] = "";
                dataexperiment[5] = "";
            }
            experimentname.setText(dataexperiment[0]);
            equipmentname.setText(dataexperiment[1]);
            dateexperiment.setText(dataexperiment[2]);
            filtercombobox.removeActionListener(this);
            if (!experimentid.getText().trim().equalsIgnoreCase("")) {
                filtercombobox.removeAllItems();
                odatafiletypes = null;
                odatafiletypes = resultservice.getfiletypes(experimentid);
                if (odatafiletypes.length > 0) {
                    for (int i = 0; i < odatafiletypes.length; i++) {
                        for (int j = 0; j < filtertypes.length; j++) {
                            if (filtertypes[j].trim().indexOf(odatafiletypes[i][0].toString().trim()) > 0) {
                                filtercombobox.addItem(filtertypes[j]);
                            }
                        }
                    }
                } else {
                    for (int j = 0; j < filtertypes.length; j++) {
                        filtercombobox.addItem(filtertypes[j]);
                    }
                }
            }
            filtercombobox.addActionListener(this);
            allocatesamples();
        }
        if (choice == experimentidwilcoxon) {
            odatamatrixfileswicoxon = resultservice.collectmatrixfileswilcoxon(experimentidwilcoxon.getText());
            groupnumbers = resultService.collectgroupnumberswilcoxon(experimentidwilcoxon.getText());
            dataexperiment = getexperimentdata.getexperimentdata(experimentidwilcoxon.getText());
            experimentnamewilcoxon.setText(dataexperiment[0]);
            equipmentnamewilcoxon.setText(dataexperiment[1]);
            dateexperimentwilcoxon.setText(dataexperiment[2]);
            createwilcoxon.getwilcoxoninputpanel().removeAll();
            createwilcoxon.centerWilcoxon.panelpvalues.gettablepanel().removeAll();
            createwilcoxon.centerWilcoxon.panelpvalues.p_plot_panel.removeAll();
            if ((dataexperiment[3] != null) && (odatamatrixfileswicoxon.length > 0)) {
                createwilcoxon.centerWilcoxon.inputpanelwilcoxon.fillwilcoxoninputpanel();
                createwilcoxon.centerWilcoxon.setBackground(colorgrey);
                if (combomatrixtodisplaywilcoxon != null) {
                    if (combomatrixtodisplaywilcoxon.getComponentCount() > 0) {
                        combomatrixtodisplaywilcoxon.setSelectedIndex(0);
                    }
                }
            }
            frame.setVisible(true);
        }
    }

    public void keyPressed(KeyEvent evt) {
        PerformAction(evt.getSource());
    }
}
