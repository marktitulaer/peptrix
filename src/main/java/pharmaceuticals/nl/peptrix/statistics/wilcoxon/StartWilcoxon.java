package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.utils.SortMatrix;
import com.enterprisedt.net.ftp.FTPClient;

public class StartWilcoxon {
    Controller cc;
    String experimentnumber_wilcoxon;
    String stringnumberrandomizations;
    int int_randomizationgroups;
    String color1;
    String color2;
    String strinputplotheight = "480";
    String strinputplotwidth = "480";
    StorePvalues storePvalues;

    public StartWilcoxon(Controller cc) {
        this.cc = cc;
        storePvalues = new StorePvalues(cc);
    }

    public void startwilcoxon(Progress progress) {
        String wilcoxonfilename = cc.combomatrixtodisplaywilcoxon.getSelectedItem().toString();
        experimentnumber_wilcoxon = cc.experimentidwilcoxon.getText().trim();
        cc.groupcode1wilcoxon = cc.combogroupnumber1.getSelectedItem().toString();
        Object[][] odatagroupnumbers = cc.resultService.getodatagroupnumbers();
        cc.groupid1wilcoxon = odatagroupnumbers[cc.combogroupnumber1.getSelectedIndex()][1].toString().trim();
        cc.groupcode2wilcoxon = cc.combogroupnumber2.getSelectedItem().toString();
        cc.groupid2wilcoxon = odatagroupnumbers[cc.combogroupnumber2.getSelectedIndex()][1].toString().trim();
        stringnumberrandomizations = cc.numberofrandomizations.getText().trim();
        if (stringnumberrandomizations.trim().equalsIgnoreCase("")) {
            stringnumberrandomizations = "1";
        }
        int_randomizationgroups = cc.comborandomizationgroups.getSelectedIndex();
        color1 = cc.colorgroup1.getSelectedItem().toString();
        color2 = cc.colorgroup2.getSelectedItem().toString();
        if (!cc.inputplotheight.getText().trim().equalsIgnoreCase("")) {
            strinputplotheight = cc.inputplotheight.getText().trim();
        }
        if (!cc.inputplotwidth.getText().trim().equalsIgnoreCase("")) {
            strinputplotwidth = cc.inputplotwidth.getText().trim();
        }
        if (!cc.groupid1wilcoxon.equalsIgnoreCase(cc.groupid2wilcoxon)) {
            progress.init("Perform Wilcoxon-Mann-Whitney test ......", 70);
            cc.doublePvalues = cc.createwilcoxon.wilcoxon.performtest(cc.dataexperiment[4], wilcoxonfilename,
                    experimentnumber_wilcoxon, cc.groupcode1wilcoxon, cc.groupid1wilcoxon, cc.groupcode2wilcoxon,
                    cc.groupid2wilcoxon, stringnumberrandomizations, int_randomizationgroups, color1, color2,
                    strinputplotheight, strinputplotwidth, cc.dataexperiment[3], progress);
            if (cc.doublePvalues != null) {
                cc.strexperimentid2 = cc.experimentidwilcoxon.getText().trim();
                cc.cleanedPalues = cc.cleanpvalues.clean_p_values(cc.doublePvalues);
                int sortrow = 1;
                cc.sortmatrix = new SortMatrix(cc.cleanedPalues, sortrow);
                if (cc.ftp != null) {
                } else {
                    try {
                        cc.ftp = new FTPClient();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                storePvalues.store_p_values(wilcoxonfilename);
                progress.init("Store Wilcoxon-Mann-Whitney detailed p-values list", 70);
                cc.storepvaluesdetails(wilcoxonfilename);
                cc.storepplot.store_p_plot(cc.p_plot_prefix, wilcoxonfilename, cc.groupid1wilcoxon, cc.groupid2wilcoxon,
                        cc.groupcode1wilcoxon, cc.groupcode2wilcoxon);
            }
            cc.display_p_data(wilcoxonfilename, cc.groupid1wilcoxon, cc.groupid2wilcoxon);
            progress.close();
        }
    }
}
