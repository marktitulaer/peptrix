package pharmaceuticals.nl.peptrix.createpeaklist;

public class Clusters {
    StringBuffer m_bufferoverviewofisotopes;
    StringBuffer m_listidsofpotentialmonoistopes;
    StringBuffer m_ids_todelete;
    double m_mean2;
    double m_sum_of_squares2;
    int m_chargestate;
    int m_numberoflines;
    int m_number_of_best_isotopic_clusters;

    public Clusters() {
    }

    public void setchargestate(int chargestate) {
        m_chargestate = chargestate;
    }

    public int getchargestate() {
        return m_chargestate;
    }

    public void setnumberoflines(int numberoflines) {
        m_numberoflines = numberoflines;
    }

    public int getnumberoflines() {
        return m_numberoflines;
    }

    public void setbufferoverviewofisotopes(StringBuffer bufferoverviewofisotopes) {
        m_bufferoverviewofisotopes = bufferoverviewofisotopes;
    }

    public StringBuffer getbufferoverviewofisotopes() {
        return m_bufferoverviewofisotopes;
    }

    public void setnumber_of_best_isotopic_clusters(int number_of_best_isotopic_clusters) {
        m_number_of_best_isotopic_clusters = number_of_best_isotopic_clusters;
    }

    public int getnumber_of_best_isotopic_clusters() {
        return m_number_of_best_isotopic_clusters;
    }

    public void setmean2(double mean2) {
        m_mean2 = mean2;
    }

    public double getmean2() {
        return m_mean2;
    }

    public void setsum_of_squares2(double sum_of_squares2) {
        m_sum_of_squares2 = sum_of_squares2;
    }

    public double getsum_of_squares2() {
        return m_sum_of_squares2;
    }

    public void init_listidsofpotentialmonoistopes() {
        m_listidsofpotentialmonoistopes = new StringBuffer("");
    }

    public void append_listidsofpotentialmonoistopes(String idsofpotentialmonoistopes) {
        m_listidsofpotentialmonoistopes.append(idsofpotentialmonoistopes);
    }

    public StringBuffer get_listidsofpotentialmonoistopes() {
        return m_listidsofpotentialmonoistopes;
    }

    public void init_ids_todelete() {
        m_ids_todelete = new StringBuffer("");
    }

    public void append_ids_todelete(String str_ids_todelete) {
        m_ids_todelete.append(str_ids_todelete);
    }

    public StringBuffer get_ids_todelete() {
        return m_ids_todelete;
    }
}
