package pharmaceuticals.nl.peptrix.serviceimpl;

import java.awt.TextField;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.createpeaklist.MassSpectrometryFile;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.service.ResultService;

public class ResultServiceImpl implements ResultService {

    Object[][] odatafiletypes;

    int datalength;

    Object[][] odatafiletypes2;

    int intmaxnumberofrecords;

    int recordsnotallocated;

    String[][] odata;

    String filtertype;

    Controller cc;

    String[][] odatamatrixfiles;

    String[] matrixfiles;

    Object[][] odataselectedmatrix;

    boolean presenterrormessage;

    String strquery;

    Object[][] count_allocated_records;

    Object[][] numberofnoisypectra;

    Object[][] odatanewresultid2;

    String[] oheaders;

    Object[][] odatagroupnumbers;

    public ResultServiceImpl(Controller cc) {
        this.cc = cc;
    }

    public int getnumberofrecords() {
        return intmaxnumberofrecords;
    }

    public int getrecordsnotallocated() {
        return recordsnotallocated;
    }

    public Object[][] getdata(int offset, int limit, String filtertext) {
        String stroffset = String.valueOf(offset);
        String strlimit = String.valueOf(limit);
        filtertype = "''";
        Object[][] maxnumberofrecords;
        recordsnotallocated = 0;
        if (filtertext.equals(cc.textfilter.getDescription())) {
            filtertype = "'txt'";
        }
        if (filtertext.equals(cc.fidfilter.getDescription())) {
            filtertype = "'fid'";
        }
        if (filtertext.equals(cc.xmlfilter.getDescription())) {
            filtertype = "'xml'";
        }
        if (filtertext.equals(cc.mzxmlfilter.getDescription())) {
            filtertype = "'mzxml'";
        }
        if (filtertext.equals(cc.rawfilter.getDescription())) {
            filtertype = "'raw'";
        }
        if (cc.importsampleid != null && !cc.importsampleid.trim().equalsIgnoreCase("")) {
            strquery = "select count(*) from result rs " + "inner join sample sa on rs.sampleid = sa.sampleid "
                    + "left outer join Group_ gr on rs.Group_id = gr.Group_id " + "where rs.experimentid = "
                    + cc.experimentid.getText().trim() + " and rs.Type = " + filtertype + " and rs.sampleid = "
                    + cc.importsampleid.trim() + " order by rs.resultid ";
        } else {
            strquery = "select count(*) from result rs " + "left outer join sample sa on rs.sampleid = sa.sampleid "
                    + "left outer join Group_ gr on rs.Group_id = gr.Group_id " + "where rs.experimentid = "
                    + cc.experimentid.getText().trim() + " and rs.Type = " + filtertype + " order by rs.resultid ";
        }
        maxnumberofrecords = null;
        try {
            maxnumberofrecords = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (maxnumberofrecords != null) {
            intmaxnumberofrecords = Integer.parseInt(maxnumberofrecords[0][0].toString());
        }
        recordsnotallocated = 0;
        strquery = "select count(*) from result rs " + "where rs.experimentid = " + cc.experimentid.getText().trim()
                + " and (sampleid is null or Group_id is null) and rs.Type = " + filtertype + " order by rs.resultid ";
        maxnumberofrecords = null;
        try {
            maxnumberofrecords = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (maxnumberofrecords != null) {
            recordsnotallocated = Integer.parseInt(maxnumberofrecords[0][0].toString());
        }
        strquery = "select " + "rs.File, " + "sa.sampleid, "
                + "sa.Sample_code, " + "sa.name , "
                + "gr.Group_id, " + "gr.Group_code, "
                + "gr.Name, "
                + "resultid, " + "Offset_LC_MS "
                + "from result rs " + "left outer join sample sa on rs.sampleid = sa.sampleid "
                + "left outer join Group_ gr on rs.Group_id = gr.Group_id " + "where rs.experimentid = "
                + cc.experimentid.getText().trim();
        StringBuffer querybuffer = new StringBuffer(strquery);
        if (cc.importsampleid != null && !cc.importsampleid.trim().equalsIgnoreCase("")) {
            strquery = " and rs.sampleid = " + cc.importsampleid.trim();
            querybuffer.append(strquery);
        }
        strquery = " and rs.Type = " + filtertype + " order by rs.File " + " limit " + strlimit + " offset "
                + stroffset;
        querybuffer.append(strquery);
        strquery = querybuffer.toString();
        odata = null;
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public String getfiltertype() {
        return filtertype;
    }

    public Object[][] countallocatedrecords(TextField experimentid2) {
        strquery = " select count(distinct sampleid, group_id) from result where type in ('txt','raw','fid','xml') and experimentid = "
                + experimentid2.getText().trim();
        try {
            count_allocated_records = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return count_allocated_records;
    }

    public Object[][] resulttype(TextField experimentid2) {
        strquery = " select distinct(type) from result where experimentid = " + experimentid2.getText().trim();
        try {
            odatafiletypes2 = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatafiletypes2;
    }

    public Object[][] getnumberofnoisyspectra(String strexperimentid, String strquantilethreshold) {
        numberofnoisypectra = null;
        strquery = "select count(distinct(result.sampleid)) Samples_tolowcalibrated,"
                + " group_code, group_.name, group_.group_id " + " from result inner join group_ on "
                + " result.group_id = group_.group_id inner join sample on "
                + " sample.sampleid = result.sampleid where result.experimentid = " + strexperimentid + " and "
                + " result.quantilethreshold = '" + strquantilethreshold.trim()
                + "' and result.type like '%noisy%' group " + " by result.group_id ";
        try {
            numberofnoisypectra = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return numberofnoisypectra;
    }

    public Object[][] getfiletypes(TextField experimentid) {
        strquery = " select distinct(type) from result where experimentid = " + experimentid.getText().trim();
        try {
            odatafiletypes = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatafiletypes;
    }

    public String[] collectmatrixfiles(String experimentid) {
        try {
            if (!experimentid.trim().equals("")) {
                strquery = "select file, resultid from result where type = 'matrix' " + " and experimentid = "
                        + experimentid.trim() + " order by file desc";
                odatamatrixfiles = cc.jdbcconnection.returnData(strquery);
                if (odatamatrixfiles.length > 0) {
                    matrixfiles = new String[odatamatrixfiles.length];
                    for (int i = 0; i < odatamatrixfiles.length; i++) {
                        matrixfiles[i] = odatamatrixfiles[i][0];
                    }
                } else {
                    matrixfiles = new String[1];
                    matrixfiles[0] = "";
                }
            }
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return matrixfiles;
    }

    public String getresultid(int i) {
        return odatamatrixfiles[i][1];
    }

    public String returnmatrixfilename(String resultid) {
        String matrixfilename = "";
        if (!resultid.trim().equalsIgnoreCase("")) {
            odataselectedmatrix = null;
            strquery = "select file from result where type = 'matrix' " + " and resultid = " + resultid;
            try {
                odataselectedmatrix = cc.jdbcconnection.returnData(strquery);
            } catch (SQLException e) {
                presenterrormessage = true;
                try {
                    cc.jdbcconnection.con.close();
                    cc.jdbcconnection.createcon();
                    presenterrormessage = false;
                } catch (SQLException e2) {
                    ;
                }
                if (presenterrormessage) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            if (odataselectedmatrix != null) {
                if (odataselectedmatrix.length > 0) {
                    if (!odataselectedmatrix[0][0].toString().trim().equalsIgnoreCase("")) {
                        matrixfilename = odataselectedmatrix[0][0].toString();
                    }
                }
            }
        }
        return matrixfilename;
    }

    public Object[][] collectmatrixfileswilcoxon(String experimentid) {
        Object[][] odatamatrixfileswicoxon = null;
        try {
            if (!experimentid.trim().equals("")) {
                strquery = "select rs.file, rs.resultid, exp.year from result rs, experiment exp "
                        + " where rs.file not like '%binarymatrix%' " + " and exp.experimentid =  rs.experimentid "
                        + " and rs.type = 'matrix' " + " and rs.experimentid = " + experimentid.trim()
                        + " order by rs.file desc";
                odatamatrixfileswicoxon = cc.jdbcconnection.returnData(strquery);
                if (odatamatrixfileswicoxon.length > 0) {
                    cc.matrixfiles = new String[odatamatrixfileswicoxon.length];
                    for (int i = 0; i < odatamatrixfileswicoxon.length; i++) {
                        cc.matrixfiles[i] = (String) odatamatrixfileswicoxon[i][0];
                        cc.dataexperiment[4] = odatamatrixfileswicoxon[i][2].toString().trim();
                    }
                }
            }
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatamatrixfileswicoxon;
    }

    public Object[][] displaynewmatrix(String strexperimentid) {
        strquery = "select resultid from result where type = 'matrix' " + " and experimentid = " + strexperimentid
                + " order by resultid desc ";
        try {
            odatanewresultid2 = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatanewresultid2;
    }

    public void set_Offset_LC_MS(double double_offset_LC_MS, String resultid) {
        strquery = "update result set Offset_LC_MS = " + String.valueOf(0 + double_offset_LC_MS) + " where resultid = "
                + resultid.trim();
        try {
            cc.updategroup = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void update_Offset_LC_MS(double old_offset_LC_MS, double double_offset_LC_MS, String resultid) {
        if (old_offset_LC_MS != double_offset_LC_MS) {
            strquery = "update result set Offset_LC_MS = " + String.valueOf(0 + double_offset_LC_MS)
                    + " where resultid = " + resultid;
            try {
                cc.updategroup = cc.jdbcconnection.update_table(strquery);
            } catch (SQLException e) {
                if (cc.debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public int resetgroupid(String resultid) {
        strquery = "update result set group_id = null " + "where resultid = " + resultid.trim();
        int updategroup = 0;
        try {
            updategroup = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updategroup;
    }

    public int updategroupid(String newgroupid, String resultid) {
        int updategroup = 0;
        strquery = "update result set group_id = " + newgroupid.trim() + " where resultid = " + resultid.trim();
        try {
            updategroup = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updategroup;
    }

    public Object[][] getresultrecords(String strexperimentid, String filtertype) {
        Object[][] odata = null;
        strquery = "select res.resultid, res.Sampleid, res.Group_id " + "from result res "
                + "inner join sample sam on sam.Sampleid = res.Sampleid "
                + "left outer join group_ grp on grp.group_id = res.group_id " + "where res.experimentid = "
                + strexperimentid.trim() + " and res.Type = " + filtertype + " order by res.sampleid, res.File ";
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public int update_group_id(String groupid, String resultid) {
        int updategroup = -1;
        strquery = "update result set group_id = " + groupid.trim() + " where resultid = " + resultid.trim();
        try {
            updategroup = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updategroup;
    }

    public Object[][] get_sampleid_from_samplecode_in_experiment(String strSample_code, String strexperimentid) {
        Object[][] odata = null;
        strquery = " select rs.sampleid " + "from result rs, sample sa " + "where rs.sampleid = sa.sampleid "
                + "and sa.sample_code = '" + strSample_code.trim() + "' and rs.experimentid = "
                + strexperimentid.trim();
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public int resetsampleid(String resultid) {
        int updategroup = -1;
        strquery = "update result set sampleid = null " + "where resultid = " + resultid.trim();
        try {
            updategroup = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updategroup;
    }

    public int updatesampleid(String newsampleid, String resultid) {
        int updategroup = -1;
        strquery = "update result set sampleid = " + newsampleid.trim() + " where resultid = " + resultid.trim();
        try {
            updategroup = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updategroup;
    }

    public Object[][] selectresultrecords(String experimentid, String filtertype) {
        strquery = "select " + "rs.File, " + "sa.sampleid, "
                + "sa.Sample_code, " + "sa.name , "
                + "gr.Group_id, " + "gr.Group_code, "
                + "gr.Name, "
                + "resultid " + "from result rs " + "inner join sample sa on rs.sampleid = sa.sampleid "
                + "inner join Group_ gr on rs.Group_id = gr.Group_id " + "where rs.experimentid = "
                + experimentid.trim() + " and rs.Type = " + filtertype + " order by rs.File ";
        Object[][] odatadetails = null;
        try {
            odatadetails = cc.jdbcconnection.returnData(strquery);
            oheaders = cc.jdbcconnection.returnHeaders();
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatadetails;
    }

    public String[] selectheader() {
        return oheaders;
    }

    public Object[][] determinenumberofreplicates(String experimentid, String quantilethreshold) {
        Object[][] odata = null;
        strquery = "select count(*), sampleid from result where experimentid = " + experimentid
                + " and type = 'reduced' and quantilethreshold = " + quantilethreshold
                + " group by sampleid, group_id; ";
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public int too_low_numbers_of_replicates(String experimentid, String required_number_of_replicates, String sampleid,
                                             String quantilethreshold) {
        int recordsaffected = -1;
        String strquery = "update result set type = 'n<" + required_number_of_replicates.trim()
                + "' where experimentid = " + experimentid + " and type = 'reduced' and quantilethreshold = "
                + quantilethreshold + " and sampleid = " + sampleid;
        try {
            recordsaffected = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return recordsaffected;
    }

    public Object[][] select_peak_lists(String experimentid, String quantilethreshold) {
        Object[][] odata = null;
        strquery = "select rs.file from result rs where experimentid = " + experimentid
                + " and type = 'reduced' and quantilethreshold = " + quantilethreshold + " order by sampleid ";
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public Object[][] getreducedresultrecords(String experimentid, String quantilethreshold) {
        Object[][] odata = null;
        strquery = "select sam.Sampleid, sam.sample_code, grp.Group_id, grp.group_code, res.File " + "from result res "
                + "inner join sample sam on sam.Sampleid = res.Sampleid "
                + "inner join group_ grp on grp.group_id = res.group_id " + "where res.experimentid = " + experimentid
                + " and res.type = 'reduced' " + "and res.quantilethreshold = " + quantilethreshold
                + " order by grp.Group_id, sam.Sampleid, res.resultid ";
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public Object[][] get_Offset_LC_MS(String experimentid, String quantilethreshold, String sampleid) {
        Object[][] odata = null;
        strquery = "select res.Offset_LC_MS " + "from result res "
                + "inner join sample sam on sam.Sampleid = res.Sampleid "
                + "inner join group_ grp on grp.group_id = res.group_id " + "where res.experimentid = " + experimentid
                + " and res.type = 'reduced' " + "and res.quantilethreshold = " + quantilethreshold
                + " and sam.Sampleid = " + sampleid;
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return odata;
    }

    public Object[][] get_noise_peaks(String experimentid, String quantilethreshold, String sampleid, String file) {
        Object[][] odata = null;
        strquery = "select file from result res inner join sample sam on sam.Sampleid = res.Sampleid "
                + "inner join group_ grp on grp.group_id = res.group_id " + "where res.experimentid = " + experimentid
                + " and res.type = 'noisepeaks_2' " + "and res.quantilethreshold = " + quantilethreshold
                + " and res.file = '" + file + "' and sam.sampleid = " + sampleid;
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return odata;
    }

    public Object[][] get_noise_peaks2(String experimentid, String quantilethreshold, String sampleid, String file) {
        Object[][] odatanoisepeaks = null;
        try {
            strquery = "select file from result res inner join sample sam on sam.Sampleid = res.Sampleid "
                    + "inner join group_ grp on grp.group_id = res.group_id " + "where res.experimentid = "
                    + experimentid + " and res.type = 'noisepeaks' " + "and res.quantilethreshold = "
                    + quantilethreshold + " and res.file = '" + file + "' and sam.sampleid = " + sampleid;
            odatanoisepeaks = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odatanoisepeaks;
    }

    public Object[][] get_offset_lcms(String experimentid, String quantilethreshold, String sampleid) {
        Object[][] odata = null;
        strquery = "select res.Offset_LC_MS " + "from result res "
                + "inner join sample sam on sam.Sampleid = res.Sampleid "
                + "inner join group_ grp on grp.group_id = res.group_id " + "where res.experimentid = " + experimentid
                + " and res.type = 'reduced' " + "and res.quantilethreshold = " + quantilethreshold
                + " and sam.Sampleid = " + sampleid;
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return odata;
    }

    public Object[][] getallfiles(String filetype, String experimentnumber) {
        Object[][] odata = null;
        strquery = "select res.File, res.Sampleid, res.Group_id, sam.sample_code, grp.group_code, res.Offset_LC_MS  "
                + "from result res " + "inner join sample sam on sam.Sampleid = res.Sampleid "
                + "inner join group_ grp on grp.group_id = res.group_id " + "where Type = \"" + filetype
                + "\" and experimentid = " + experimentnumber + " order by res.sampleid, res.resultid ";
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return odata;

    }

    public int insertreport(String strquantilethreshold, String reportname, String strexperimentid, BigDecimal tempBD) {
        int recordsaffected = -1;
        strquery = "INSERT INTO Result(Experimentid,Sampleid,Group_id"
                + ",Date,Time,Size_KB,File,Type,Quantilethreshold,Year)" + " VALUES (" + strexperimentid.trim()
                + ",null,null,'" + cc.actualtime.getdatestring() + "','" + cc.actualtime.gettimestring() + "',"
                + tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN) + ",'" + reportname + "','report','"
                + strquantilethreshold + "','" + cc.actualtime.getyear() + "')";
        try {
            recordsaffected = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return recordsaffected;
    }

    public int delete_report(String strquantilethreshold, String reportname, String strexperimentid) {
        int recordsaffected = -1;
        strquery = "delete from result where Experimentid = " + strexperimentid.trim() + " and Quantilethreshold = '"
                + strquantilethreshold + "' and Type = \"report\" and file = '" + reportname + "'";
        try {
            recordsaffected = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return recordsaffected;
    }

    public String newexperimentid() {
        Object[][] odataexperiment = null;
        String newexperimentid = "";
        try {
            cc.strquery = "select max(experimentid) from Experiment";
            odataexperiment = cc.jdbcconnection.returnData(cc.strquery);
            if (odataexperiment.length > 0) {
                newexperimentid = (String) odataexperiment[0][0];
            } else {
                newexperimentid = "1";
            }
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return newexperimentid;
    }

    public String[] collectgroupnumberswilcoxon(String strexperimentid) {

        String[] groupnumbers = null;
        try {
            if (!strexperimentid.trim().equals("")) {
                cc.strquery = "select distinct gr.Group_code, rs.Group_id " + "from result rs inner join Group_ gr "
                        + "on rs.Group_id = gr.Group_id " + "where rs.experimentid = " + strexperimentid.trim()
                        + " and rs.Group_id is not null " + "and rs.sampleid is not null " + "order by gr.Group_code ";
                odatagroupnumbers = cc.jdbcconnection.returnData(cc.strquery);
                if (odatagroupnumbers.length > 0) {
                    groupnumbers = new String[odatagroupnumbers.length];
                    for (int i = 0; i < odatagroupnumbers.length; i++) {
                        groupnumbers[i] = (String) odatagroupnumbers[i][0];
                    }
                } else {
                    groupnumbers = new String[1];
                    groupnumbers[0] = "";
                }
            }
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return groupnumbers;
    }

    public Object[][] getodatagroupnumbers() {
        return odatagroupnumbers;
    }

    public Object[][] gettransposedmatrixfiles(String transposed_file_name, String experimentid) {
        Object[][] odata = null;
        strquery = "select rs.file, rs.resultid, exp.year from result rs, experiment exp " + " where rs.file = '"
                + transposed_file_name.trim() + "' " + " and exp.experimentid =  rs.experimentid "
                + " and rs.type = 'transposed' " + " and rs.experimentid = " + experimentid.trim();
        try {

            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public int deletetransposedfile(String strexperimentid, String strtype, String transposed_file_name) {
        int recordsaffected = -1;
        strquery = "delete from result where Experimentid = " + strexperimentid + " and Type = '" + strtype
                + "' and File = '" + transposed_file_name + "'";
        try {
            recordsaffected = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return recordsaffected;
    }

    public int inserttransposedfile(BigDecimal tempBD, String strexperimentid, String strtype,
                                    String transposed_file_name) {
        int recordsaffected = -1;
        strquery = "INSERT INTO Result(Experimentid, Date, Time," + "Size_KB, File, Type, Year) " + "VALUES ("
                + strexperimentid + ",'" + cc.actualtime.getdatestring() + "','" + cc.actualtime.gettimestring() + "',"
                + tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN) + ",'" + transposed_file_name + "','" + strtype + "'"
                + ",'" + cc.actualtime.getyear() + "')";
        try {
            recordsaffected = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return recordsaffected;
    }

    ;

    public Object[][] collectmatrixfiles2(String experimentid) {
        Object[][] odata2 = null;
        try {
            if (!experimentid.trim().equals("")) {
                strquery = "select rs.file, rs.resultid, exp.year from result rs, experiment exp "
                        + " where rs.file not like '%binarymatrix%' " + " and exp.experimentid =  rs.experimentid "
                        + " and rs.type = 'matrix' " + " and rs.experimentid = " + experimentid.trim()
                        + " order by rs.file desc";
                odata2 = cc.jdbcconnection.returnData(strquery);
            }
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata2;
    }

    public int deleteresultrecordwilcoxon(String strexperimentid, String strtype, String exportname) {
        int recordsaffected = -1;
        strquery = "delete from result where Experimentid = " + strexperimentid + " and Type = '" + strtype
                + "' and File = '" + exportname + "'";
        try {
            recordsaffected = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return recordsaffected;
    }

    public int insertresultrecordwilcoxon(BigDecimal tempBD, String strexperimentid, String strtype,
                                          String exportname) {
        int recordsaffected = -1;
        strquery = "INSERT INTO Result(Experimentid, Date, Time," + "Size_KB, File, Type, Year) " + "VALUES ("
                + strexperimentid + ",'" + cc.actualtime.getdatestring() + "','" + cc.actualtime.gettimestring() + "',"
                + tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN) + ",'" + exportname + "','" + strtype + "'" + ",'"
                + cc.actualtime.getyear() + "')";
        try {
            recordsaffected = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return recordsaffected;
    }

    public Object[][] collectmatrixfileswilcoxon2(String experimentid) {
        Object[][] odata = null;
        try {
            strquery = "select rs.file, rs.resultid, exp.year from result rs, experiment exp "
                    + " where rs.file not like '%binarymatrix%' " + " and exp.experimentid =  rs.experimentid "
                    + " and rs.type = 'matrix' " + " and rs.experimentid = " + experimentid.trim()
                    + " order by rs.file desc";
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }

    public int deleteresultrecords(String experimentnumber, String strquantilethreshold) {
        strquery = "delete from result where Experimentid = " + experimentnumber + " and Quantilethreshold = '"
                + strquantilethreshold + "' and Type <> \"matrix\"";
        int updatesample = -1;
        try {
            updatesample = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updatesample;
    }

    public int deleteresultrecord(String filenamematrix, String experimentnumber, String strquantilethreshold) {
        strquery = "delete from result where Experimentid = " + experimentnumber + " and Quantilethreshold = '"
                + strquantilethreshold + "' and Type = \"matrix\" and file = '" + filenamematrix + "'";
        int updatesample = -1;
        try {
            updatesample = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updatesample;
    }

    public int insertresultrecord(String type, double filegrootte_kbytes, MassSpectrometryFile massspectrometryfile,
                                  String strtime, String experimentnumber, String strquantilethreshold, String exportname, String strdatum,
                                  String stryear) {
        int updatesample = -1;

        BigDecimal tempBD = new BigDecimal(filegrootte_kbytes);
        if (massspectrometryfile.getSampleid().trim().equals("")) {
            massspectrometryfile.setSampleid("null");
        }
        if (massspectrometryfile.getGroupid().trim().equals("")) {
            massspectrometryfile.setGroupid("null");
        }
        strquery = "INSERT INTO Result(Experimentid,Sampleid,Group_id"
                + ",Date,Time,Size_KB,File,Type,Quantilethreshold,Year,Offset_LC_MS)" + " VALUES (" + experimentnumber
                + "," + massspectrometryfile.getSampleid() + "," + massspectrometryfile.getGroupid() + ",'" + strdatum
                + "','" + strtime + "'," + tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN) + ",'" + exportname + "','"
                + type + "','" + strquantilethreshold + "','" + stryear + "'," + massspectrometryfile.getOffset_lc_ms()
                + ")";

        try {
            updatesample = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updatesample;
    }

    public int insertmatrixresultrecord(String filename, double filegrootte_kbytes, String strtime,
                                        String experimentnumber, String strquantilethreshold, String strdatum, String stryear) {
        int updatesample = -1;
        BigDecimal tempBD = new BigDecimal(filegrootte_kbytes);
        strquery = "INSERT INTO Result(Experimentid, Sampleid,Group_id, Date, Time, Size_KB, File, Type, Quantilethreshold,Year) "
                + "VALUES (" + experimentnumber + ",null,null,'" + strdatum + "','" + strtime + "',"
                + tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN) + ",'" + filename + "','matrix'" + ",'"
                + strquantilethreshold + "','" + stryear + "')";

        try {
            updatesample = cc.jdbcconnection.update_table(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updatesample;
    }

    public int insertresultrecord(String type, double filegrootte_kbytes, String sampleid, String Group_id,
                                  Experiment experiment, String strdatum, String strtime, String exportnamexmlfraction, String strjaar,
                                  String retentiontime, String mzxmlfraction, int filenumber, double offset_lc_ms) {
        int updatesample = -1;


        BigDecimal tempBD = new BigDecimal(filegrootte_kbytes);
        if (sampleid.trim().equals("")) {
            sampleid = "null";
        }
        if (Group_id.trim().equals("")) {
            Group_id = "null";
        }
        strquery = "INSERT INTO Result(Experimentid,Sampleid,Group_id"
                + ",Date,Time,Size_KB,File,Type,Quantilethreshold,Year,retentiontime,lcfraction,filenumber,Offset_LC_MS)"
                + " VALUES (" + String.valueOf(experiment.getExperimentid()) + "," + sampleid + "," + Group_id + ",'"
                + strdatum + "','" + strtime + "'," + tempBD.setScale(3, BigDecimal.ROUND_HALF_EVEN) + ",'"
                + exportnamexmlfraction + "','" + type + "','" + experiment.getquantilethreshold().trim() + "','"
                + strjaar + "'," + retentiontime + "," + mzxmlfraction + "," + String.valueOf(filenumber) + ","
                + String.valueOf(offset_lc_ms) + ")";

        try {
            updatesample = cc.jdbcconnection.update_table(strquery);
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return updatesample;
    }

    public Object[][] getallresultrecords(String existingexperimentid) {
        Object[][] odata = null;
        strquery = "Select File from Result where experimentid = " + existingexperimentid;
        try {
            odata = cc.jdbcconnection.returnData(strquery);
        } catch (SQLException e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return odata;
    }


}
