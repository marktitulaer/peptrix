package pharmaceuticals.nl.peptrix.createpeaklist;

public class MassSpectrometryFile {
    private String m_filename;
    private int m_filenumber;
    private String m_sampleid;
    private String m_previoussampleid;
    private String m_groupid;
    private String m_previousgroupid;
    private String m_tmp_exportname;
    private double m_offset_lc_ms;
    private String[] m_arraymassesoffile;
    private String[] m_arrayintensitiesoffile;
    private String[] m_arraymintimesoffile;
    private String[] m_arraymaxtimesoffile;
    private String[] m_arraytimesoffile;

    public MassSpectrometryFile() {
    }

    public void setFilename(String filename) {
        m_filename = filename;
    }

    public String getFilename() {
        return m_filename;
    }

    public void setFilenumber(int filenumber) {
        m_filenumber = filenumber;
    }

    public int getFilenumber() {
        return m_filenumber;
    }

    public void setSampleid(String sampleid) {
        m_sampleid = sampleid;
    }

    public String getSampleid() {
        return m_sampleid;
    }

    public void setPreviousSampleid(String previoussampleid) {
        m_previoussampleid = previoussampleid;
    }

    public String getPreviousSampleid() {
        return m_previoussampleid;
    }

    public void setPreviousGroupid(String previousgroupid) {
        m_previousgroupid = previousgroupid;
    }

    public String getPreviousGroupid() {
        return m_previousgroupid;
    }

    public void setGroupid(String groupid) {
        m_groupid = groupid;
    }

    public String getGroupid() {
        return m_groupid;
    }

    public void setTmp_exportname(String tmp_exportname) {
        m_tmp_exportname = tmp_exportname;
    }

    public String getTmp_exportname() {
        return m_tmp_exportname;
    }

    public void setOffset_lc_ms(double offset_lc_ms) {
        m_offset_lc_ms = offset_lc_ms;
    }

    public double getOffset_lc_ms() {
        return m_offset_lc_ms;
    }

    public void setarraymassesoffile(String[] arraymassesoffile) {
        m_arraymassesoffile = arraymassesoffile;
    }

    public int getlength() {
        return m_arraymassesoffile.length;
    }

    public String[] getarraymassesoffile() {
        return m_arraymassesoffile;
    }

    public void setarrayintensitiesoffile(String[] arrayintensitiesoffile) {
        m_arrayintensitiesoffile = arrayintensitiesoffile;
    }

    public String[] getarrayintensitiesoffile() {
        return m_arrayintensitiesoffile;
    }

    public void setarraymintimesoffile(String[] arraymintimesoffile) {
        m_arraymintimesoffile = arraymintimesoffile;
    }

    public String[] getarraymintimesoffile() {
        return m_arraymintimesoffile;
    }

    public void setarraymaxtimesoffile(String[] arraymaxtimesoffile) {
        m_arraymaxtimesoffile = arraymaxtimesoffile;
    }

    public String[] getarraymaxtimesoffile() {
        return m_arraymaxtimesoffile;
    }

    public void setarraytimesoffile(String[] arraytimesoffile) {
        m_arraytimesoffile = arraytimesoffile;
    }

    public String[] getarraytimesoffile() {
        return m_arraytimesoffile;
    }
}
