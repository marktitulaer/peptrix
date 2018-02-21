package pharmaceuticals.nl.peptrix.service;

import java.awt.TextField;
import java.math.BigDecimal;

import pharmaceuticals.nl.peptrix.createpeaklist.MassSpectrometryFile;
import pharmaceuticals.nl.peptrix.experiment.Experiment;

public interface ResultService {

    public int getnumberofrecords();

    public int getrecordsnotallocated();

    public Object[][] getdata(int offset, int limit, String filtertext);

    public String getfiltertype();

    public Object[][] countallocatedrecords(TextField experimentid2);

    public Object[][] resulttype(TextField experimentid2);

    public Object[][] getnumberofnoisyspectra(String strexperimentid, String strquantilethreshold);

    public Object[][] getfiletypes(TextField experimentid);

    public String[] collectmatrixfiles(String experimentid);

    public String getresultid(int i);

    public String returnmatrixfilename(String resultid);

    public Object[][] collectmatrixfileswilcoxon(String experimentid);

    public Object[][] displaynewmatrix(String strexperimentid);

    public void update_Offset_LC_MS(double old_offset_LC_MS, double double_offset_LC_MS, String resultid);

    public int resetgroupid(String resultid);

    public int updategroupid(String newgroupid, String resultid);

    public Object[][] getresultrecords(String strexperimentid, String filtertype);

    public int update_group_id(String groupid, String resultid);

    public Object[][] get_sampleid_from_samplecode_in_experiment(String strSample_code, String strexperimentid);

    public int resetsampleid(String resultid);

    public int updatesampleid(String newsampleid, String resultid);

    public Object[][] selectresultrecords(String experimentid, String filtertype);

    public String[] selectheader();

    public Object[][] determinenumberofreplicates(String experimentid, String quantilethreshold);

    public int too_low_numbers_of_replicates(String experimentid, String required_number_of_replicates, String sampleid,
                                             String quantilethreshold);

    public Object[][] select_peak_lists(String experimentid, String quantilethreshold);

    public Object[][] getreducedresultrecords(String experimentid, String quantilethreshold);

    public Object[][] get_Offset_LC_MS(String experimentid, String quantilethreshold, String sampleid);

    public Object[][] get_noise_peaks(String experimentid, String quantilethreshold, String sampleid, String file);

    public Object[][] get_noise_peaks2(String experimentid, String quantilethreshold, String sampleid, String file);

    public Object[][] get_offset_lcms(String experimentid, String quantilethreshold, String sampleid);

    public Object[][] getallfiles(String filetype, String experimentnumber);

    public int insertreport(String strquantilethreshold, String reportname, String strexperimentid, BigDecimal tempBD);

    public int delete_report(String strquantilethreshold, String reportname, String strexperimentid);

    public String newexperimentid();

    public String[] collectgroupnumberswilcoxon(String strexperimentid);

    public Object[][] getodatagroupnumbers();

    public Object[][] gettransposedmatrixfiles(String transposed_file_name, String experimentid);

    public int deletetransposedfile(String strexperimentid, String strtype, String transposed_file_name);

    public int inserttransposedfile(BigDecimal tempBD, String strexperimentid, String strtype,
                                    String transposed_file_name);

    public Object[][] collectmatrixfiles2(String experimentid);

    public int deleteresultrecordwilcoxon(String strexperimentid, String strtype, String exportname);

    public int insertresultrecordwilcoxon(BigDecimal tempBD, String strexperimentid, String strtype, String exportname);

    public Object[][] collectmatrixfileswilcoxon2(String experimentid);

    public int deleteresultrecords(String experimentnumber, String strquantilethreshold);

    public int deleteresultrecord(String filenamematrix, String experimentnumber, String strquantilethreshold);

    public int insertresultrecord(String type, double filegrootte_kbytes, MassSpectrometryFile massspectrometryfile,
                                  String strtime, String experimentnumber, String strquantilethreshold, String exportname, String strdatum,
                                  String stryear);

    public int insertmatrixresultrecord(String filename, double filegrootte_kbytes, String strtime,
                                        String experimentnumber, String strquantilethreshold, String strdatum, String stryear);

    public int insertresultrecord(String type, double filegrootte_kbytes, String sampleid, String Group_id,
                                  Experiment experiment, String strdatum, String strtime, String exportnamexmlfraction, String strjaar,
                                  String retentiontime, String mzxmlfraction, int filenumber, double offset_lc_ms);

    public Object[][] getallresultrecords(String existingexperimentid);


}
