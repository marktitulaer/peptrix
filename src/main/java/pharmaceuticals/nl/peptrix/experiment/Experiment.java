package pharmaceuticals.nl.peptrix.experiment;

public class Experiment {

    private String str_m_equipmentcode;

    private String str_m_equipmentname;

    private String str_m_experimentname;

    private boolean m_has_retentiontime;

    private int m_divisions_in_detemination_noise;

    private String m_raw_to_mzXML;

    private String m_experimentid;

    private String m_experimentyear;

    private boolean m_internalcalibration;

    private String m_numberofreplicatessample;

    private String m_threshold_noisy_spectra;

    private String m_numbermisfitsallowed;

    private String m_quantilethreshold;

    private String m_delta_mz_searchmaximum;

    private String m_delta_mz_combine;

    private String m_minimum_mass;

    private String m_maximum_mass;

    private int m_clusteringtechnique;

    private String m_thresholdbinarymatrix;

    private int m_equipmentid = -1;

    private int m_peakfindmethod;

    private int m_id_calibrationmasses;

    private String m_filetype;

    private String m_delta_mz_search_calibrants;

    private int m_minimum_number_of_masses_that_should_be_present;

    private double m_isotopic_distance_c13_c12;

    private String m_taxonomy_name;


    private boolean m_perform_ms2_sequencing;

    private boolean m_only_ms2_sequencedmasses;

    private String m_TOL;

    private String m_TOLU;

    private String m_ITOL;

    private String m_ITOLU;

    private String m_name_search_engine;

    private double m_GaussfactorB;

    private double m_LorentzfactorA;

    private int m_minimum_points_required_for_time_alignment;

    private boolean m_should_time_alignment_be_performed;

    private int m_missing_number_ms_scans_allowed;

    private int m_peptide_present_in_minimumnumberoffractions;

    private int m_zerofillingfactor;

    private String[] m_selected_enzymes;

    private int m_max_charge_state_peptide;

    private double m_deviation_from_expected_intensity_ratio;

    private double m_percent_deviation_from_isotopic_distance;

    private double m_variance_isotopic_distance;

    private double m_time_window_combining_peptide_masses;

    private String m_FT_ICR_apodization_method;

    private boolean m_perform_deisotoping;

    private double[] m_change_in_gradient_after_time;

    private int m_method_peak_centroiding;

    private String[] m_fixed_peptide_modifications;

    private String[] m_variabel_peptide_modifications;

    private int m_time_clustering_absolute_or_percentage;

    private double m_window_centroiding_ppm;

    private double m_maximal_shift_by_centroiding_ppm;

    private String m_matrixcountsfilename;

    private String m_matrixintensityfilename;

    private String m_binarymatrixfilename;

    private int m_numberofmasses;

    private double[][] m_doublearraycombinedpeaks;

    public Experiment() {
        m_has_retentiontime = false;
        str_m_equipmentname = "";
        str_m_experimentname = "";
        m_experimentyear = "";
        m_equipmentid = -1;
        str_m_equipmentcode = "";
    }

    public String[] getSelected_enzymes() {
        return m_selected_enzymes;
    }

    public void setSelected_enzymes(String[] selected_enzymes) {
        m_selected_enzymes = selected_enzymes;
    }

    public void setequipmentid(int equipmentid) {
        m_equipmentid = equipmentid;
    }

    public int getequipmentid() {
        return m_equipmentid;
    }

    public void setstr_equipmentname(String str_equipmentname) {
        str_m_equipmentname = str_equipmentname;
    }

    public String getstr_equipmentname() {
        return str_m_equipmentname;
    }

    public void setstr_experimentname(String str_experimentname) {
        str_m_experimentname = str_experimentname;
    }

    public String getstr_experimentname() {
        return str_m_experimentname;
    }

    public void setExperimentyear(String experimentyear) {
        m_experimentyear = experimentyear;
    }

    public String getExperimentyear() {
        return m_experimentyear;
    }

    public void setHas_retentiontime(boolean has_retentiontime) {
        m_has_retentiontime = has_retentiontime;
    }

    public boolean getHas_retentiontime() {
        return m_has_retentiontime;
    }

    public void setExperimentid(String experimentid) {
        m_experimentid = experimentid;
    }

    public String getExperimentid() {
        return m_experimentid;
    }

    public void setInternalcalibration(boolean internalcalibration) {
        m_internalcalibration = internalcalibration;
    }

    public boolean getInternalcalibration() {
        return m_internalcalibration;
    }

    public void setNumberofreplicatessample(String numberofreplicatessample) {
        m_numberofreplicatessample = numberofreplicatessample;
    }

    public String getNumberofreplicatessample() {
        return m_numberofreplicatessample;
    }

    public void setThreshold_noisy_spectra(String threshold_noisy_spectra) {
        m_threshold_noisy_spectra = threshold_noisy_spectra;
    }

    public String getThreshold_noisy_spectra() {
        return m_threshold_noisy_spectra;
    }

    public void setnumbermisfitsallowed(String numbermisfitsallowed) {
        m_numbermisfitsallowed = numbermisfitsallowed;
    }

    public String getnumbermisfitsallowed() {
        return m_numbermisfitsallowed;
    }

    public void setquantilethreshold(String quantilethreshold) {
        m_quantilethreshold = quantilethreshold;
    }

    public String getquantilethreshold() {
        return m_quantilethreshold;
    }

    public void setdelta_mz_searchmaximum(String delta_mz_searchmaximum) {
        m_delta_mz_searchmaximum = delta_mz_searchmaximum;
    }

    public String getdelta_mz_searchmaximum() {
        return m_delta_mz_searchmaximum;
    }

    public void setdelta_mz_combine(String delta_mz_combine) {
        m_delta_mz_combine = delta_mz_combine;
    }

    public String getdelta_mz_combine() {
        return m_delta_mz_combine;
    }

    public void setminimum_mass(String minimum_mass) {
        m_minimum_mass = minimum_mass;
    }

    public String getminimum_mass() {
        return m_minimum_mass;
    }

    public void setmaximum_mass(String maximum_mass) {
        m_maximum_mass = maximum_mass;
    }

    public String getmaximum_mass() {
        return m_maximum_mass;
    }

    public void setclusteringtechnique(int clusteringtechnique) {
        m_clusteringtechnique = clusteringtechnique;
    }

    public int getclusteringtechnique() {
        return m_clusteringtechnique;
    }

    public void setthresholdbinarymatrix(String thresholdbinarymatrix) {
        m_thresholdbinarymatrix = thresholdbinarymatrix;
    }

    public String getthresholdbinarymatrix() {
        return m_thresholdbinarymatrix;
    }

    public void setpeakfindmethod(int peakfindmethod) {
        m_peakfindmethod = peakfindmethod;
    }

    public int getpeakfindmethod() {
        return m_peakfindmethod;
    }

    public void setid_calibrationmasses(int id_calibrationmasses) {
        m_id_calibrationmasses = id_calibrationmasses;
    }

    public int getid_calibrationmasses() {
        return m_id_calibrationmasses;
    }

    public void setfiletype(String filetype) {
        m_filetype = filetype;
    }

    public String getfiletype() {
        return m_filetype;
    }

    public void setdelta_mz_search_calibrants(String delta_mz_search_calibrants) {
        m_delta_mz_search_calibrants = delta_mz_search_calibrants;
    }

    public String getdelta_mz_search_calibrants() {
        return m_delta_mz_search_calibrants;
    }

    public void setminimum_number_of_masses_that_should_be_present(
            int minimum_number_of_masses_that_should_be_present) {
        m_minimum_number_of_masses_that_should_be_present = minimum_number_of_masses_that_should_be_present;
    }

    public int getminimum_number_of_masses_that_should_be_present() {
        return m_minimum_number_of_masses_that_should_be_present;
    }

    public void setdivisions_in_determination_noise(int divisions_in_detemination_noise) {
        m_divisions_in_detemination_noise = divisions_in_detemination_noise;
    }

    public int getdivisions_in_determination_noise() {
        return m_divisions_in_detemination_noise;
    }

    public void setisotopic_distance_c13_c12(double isotopic_distance_c13_c12) {
        m_isotopic_distance_c13_c12 = isotopic_distance_c13_c12;
    }

    public double getisotopic_distance_c13_c12() {
        return m_isotopic_distance_c13_c12;
    }

    public void settaxonomy_name(String taxonomy_name) {
        m_taxonomy_name = taxonomy_name;
    }

    public String gettaxonomy_name() {
        return m_taxonomy_name;
    }


    public void setperform_ms2_sequencing(boolean perform_ms2_sequencing) {
        m_perform_ms2_sequencing = perform_ms2_sequencing;
    }

    public boolean getperform_ms2_sequencing() {
        return m_perform_ms2_sequencing;
    }

    public void set_only_ms2_sequencedmasses(boolean only_ms2_sequencedmasses) {
        m_only_ms2_sequencedmasses = only_ms2_sequencedmasses;
    }

    public boolean getonly_ms2_sequencedmasses() {
        return m_only_ms2_sequencedmasses;
    }

    public void setTOL(String TOL) {
        m_TOL = TOL;
    }

    public String getTOL() {
        return m_TOL;
    }

    public void setTOLU(String TOLU) {
        m_TOLU = TOLU;
    }

    public String getTOLU() {
        return m_TOLU;
    }

    public void setITOL(String ITOL) {
        m_ITOL = ITOL;
    }

    public String getITOL() {
        return m_ITOL;
    }

    public void setITOLU(String ITOLU) {
        m_ITOLU = ITOLU;
    }

    public String getITOLU() {
        return m_ITOLU;
    }

    public void setname_search_engine(String name_search_engine) {
        m_name_search_engine = name_search_engine;
    }

    public String getname_search_engine() {
        return m_name_search_engine;
    }

    public void setGaussfactorB(double GaussfactorB) {
        m_GaussfactorB = GaussfactorB;
    }

    public double getGaussfactorB() {
        return m_GaussfactorB;
    }

    public void setLorentzfactorA(double LorentzfactorA) {
        m_LorentzfactorA = LorentzfactorA;
    }

    public double getLorentzfactorA() {
        return m_LorentzfactorA;
    }

    public void setminimum_points_required_for_time_alignment(int minimum_points_required_for_time_alignment) {
        m_minimum_points_required_for_time_alignment = minimum_points_required_for_time_alignment;
    }

    public int getminimum_points_required_for_time_alignment() {
        return m_minimum_points_required_for_time_alignment;
    }

    public void setperform_time_alignment(boolean should_time_alignment_be_performed) {
        m_should_time_alignment_be_performed = should_time_alignment_be_performed;
    }

    public boolean getperform_time_alignment() {
        return m_should_time_alignment_be_performed;
    }

    public void setmissing_number_ms_scans_allowed(int missing_number_ms_scans_allowed) {
        m_missing_number_ms_scans_allowed = missing_number_ms_scans_allowed;
    }

    public int getmissing_number_ms_scans_allowed() {
        return m_missing_number_ms_scans_allowed;
    }

    public void setpeptide_present_in_minimumnumberoffractions(int peptide_present_in_minimumnumberoffractions) {
        m_peptide_present_in_minimumnumberoffractions = peptide_present_in_minimumnumberoffractions;
    }

    public int getpeptide_present_in_minimumnumberoffractions() {
        return m_peptide_present_in_minimumnumberoffractions;
    }

    public void setzerofillingfactor(int zerofillingfactor) {
        m_zerofillingfactor = zerofillingfactor;
    }

    public int getzerofillingfactor() {
        return m_zerofillingfactor;
    }

    public void setmax_charge_state_peptide(int max_charge_state_peptide) {
        m_max_charge_state_peptide = max_charge_state_peptide;
    }

    public int getmax_charge_state_peptide() {
        return m_max_charge_state_peptide;
    }

    public void setdeviation_from_expected_intensity_ratio(double deviation_from_expected_intensity_ratio) {
        m_deviation_from_expected_intensity_ratio = deviation_from_expected_intensity_ratio;
    }

    public double getdeviation_from_expected_intensity_ratio() {
        return m_deviation_from_expected_intensity_ratio;
    }

    public void setpercent_deviation_from_isotopic_distance(double percent_deviation_from_isotopic_distance) {
        m_percent_deviation_from_isotopic_distance = percent_deviation_from_isotopic_distance;
    }

    public double getpercent_deviation_from_isotopic_distance() {
        return m_percent_deviation_from_isotopic_distance;
    }

    public void setvariance_isotopic_distance(double variance_isotopic_distance) {
        m_variance_isotopic_distance = variance_isotopic_distance;
    }

    public double getvariance_isotopic_distance() {
        return m_variance_isotopic_distance;
    }

    public void settime_window_combining_peptide_masses(double time_window_combining_peptide_masses) {
        m_time_window_combining_peptide_masses = time_window_combining_peptide_masses;
    }

    public double gettime_window_combining_peptide_masses() {
        return m_time_window_combining_peptide_masses;
    }

    public void setFT_ICR_apodization_method(String FT_ICR_apodization_method) {
        m_FT_ICR_apodization_method = FT_ICR_apodization_method;
    }

    public String getFT_ICR_apodization_method() {
        return m_FT_ICR_apodization_method;
    }

    public void setperform_deisotoping(boolean perform_deisotoping) {
        m_perform_deisotoping = perform_deisotoping;
    }

    public boolean getperform_deisotoping() {
        return m_perform_deisotoping;
    }

    public void setchange_in_gradient_after_time(double[] change_in_gradient_after_time) {
        m_change_in_gradient_after_time = change_in_gradient_after_time;
    }

    public double[] getchange_in_gradient_after_time() {
        return m_change_in_gradient_after_time;
    }

    public void setmethod_peak_centroiding(int method_peak_centroiding) {
        m_method_peak_centroiding = method_peak_centroiding;
    }

    public int getmethod_peak_centroiding() {
        return m_method_peak_centroiding;
    }

    public void setfixed_peptide_modifications(String[] fixed_peptide_modifications) {
        m_fixed_peptide_modifications = fixed_peptide_modifications;
    }

    public String[] getfixed_peptide_modifications() {
        return m_fixed_peptide_modifications;
    }

    public void setvariabel_peptide_modifications(String[] variabel_peptide_modifications) {
        m_variabel_peptide_modifications = variabel_peptide_modifications;
    }

    public String[] getvariabel_peptide_modifications() {
        return m_variabel_peptide_modifications;
    }

    public void settime_clustering_absolute_or_percentage(int time_clustering_absolute_or_percentage) {
        m_time_clustering_absolute_or_percentage = time_clustering_absolute_or_percentage;
    }

    public int gettime_clustering_absolute_or_percentage() {
        return m_time_clustering_absolute_or_percentage;
    }

    public void setwindow_centroiding_ppm(double window_centroiding_ppm) {
        m_window_centroiding_ppm = window_centroiding_ppm;
    }

    public double getwindow_centroiding_ppm() {
        return m_window_centroiding_ppm;
    }

    public void setmaximal_shift_by_centroiding_ppm(double maximal_shift_by_centroiding_ppm) {
        m_maximal_shift_by_centroiding_ppm = maximal_shift_by_centroiding_ppm;
    }

    public double getmaximal_shift_by_centroiding_ppm() {
        return m_maximal_shift_by_centroiding_ppm;
    }

    public void setmatrixcountsfilename(String matrixcountsfilename) {
        m_matrixcountsfilename = matrixcountsfilename;
    }

    public String getmatrixcountsfilename() {
        return m_matrixcountsfilename;
    }

    public void setmatrixintensityfilename(String matrixintensityfilename) {
        m_matrixintensityfilename = matrixintensityfilename;
    }

    public String getmatrixintensityfilename() {
        return m_matrixintensityfilename;
    }

    public void setbinarymatrixfilename(String binarymatrixfilename) {
        m_binarymatrixfilename = binarymatrixfilename;
    }

    public String getbinarymatrixfilename() {
        return m_binarymatrixfilename;
    }

    public void setnumberofmasses(int numberofmasses) {
        m_numberofmasses = numberofmasses;
    }

    public int getnumberofmasses() {
        return m_numberofmasses;
    }

    public void setcombinedpeaks(double[][] doublearraycombinedpeaks) {
        m_doublearraycombinedpeaks = doublearraycombinedpeaks;
    }

    public double[][] getcombinedpeaks() {
        return m_doublearraycombinedpeaks;
    }

    public void setraw_to_mzXML(String raw_to_mzXML) {
        m_raw_to_mzXML = raw_to_mzXML;
    }

    public String getraw_to_mzXML() {
        return m_raw_to_mzXML;
    }

    public String getEquipmentcode() {
        return str_m_equipmentcode;
    }

    public void setEquipmentcode(String equipmentcode) {
        this.str_m_equipmentcode = equipmentcode;
    }

}
