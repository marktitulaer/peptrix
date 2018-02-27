package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.createwilcoxon.CenterWilcoxon;
import pharmaceuticals.nl.peptrix.gui.createwilcoxon.Northcreatewilcoxon;
import pharmaceuticals.nl.peptrix.gui.createwilcoxon.SouthWilcoxon;
import pharmaceuticals.nl.peptrix.statistics.wilcoxon.Wilcoxon;

public class CreateWilcoxon {
    public Wilcoxon wilcoxon;
    Controller cc;
    Northcreatewilcoxon northcreatewilcoxon;
    public CenterWilcoxon centerWilcoxon;
    SouthWilcoxon southWilcoxon;

    public CreateWilcoxon(Controller cc) {
        this.cc = cc;
        northcreatewilcoxon = new Northcreatewilcoxon(cc);
        centerWilcoxon = new CenterWilcoxon(cc);
        southWilcoxon = new SouthWilcoxon(cc);
    }

    public void selectexperiment(String strinputexperimentid) {
        wilcoxon = new Wilcoxon(cc);
        fillexperimentleftpanel();
        cc.pBody.setLayout(new BorderLayout());
        cc.pBody.removeAll();
        cc.pBody.add(northcreatewilcoxon.getpanel(), BorderLayout.PAGE_START);
        cc.pBody.add(centerWilcoxon.returnScrollPane(), BorderLayout.CENTER);
        cc.pBody.add(southWilcoxon.getpanel(), BorderLayout.PAGE_END);
        centerWilcoxon.setBackground(cc.color_empty_frames);
        if (strinputexperimentid != null) {
            cc.experimentidwilcoxon.setText(strinputexperimentid);
        }
        cc.frame.setVisible(true);
    }

    private void fillexperimentleftpanel() {
        northcreatewilcoxon.fillexperimentleftpanel();
    }

    public void removeAll_from_p_plot_panel() {
        centerWilcoxon.removeAll_from_p_plot_panel();
    }

    public void p_plot_panel_add_image(JLabel icon_label) {
        centerWilcoxon.p_plot_panel_add_image(icon_label);
    }

    public void displayPvalues(JTable Pvaluestable) {
        centerWilcoxon.displayPvalues(Pvaluestable);
    }

    public JPanel getwilcoxoninputpanel() {
        return centerWilcoxon.getwilcoxoninputpanel();
    }

    public void removeAll_from_p_values_panel() {
        centerWilcoxon.removeAll_from_p_values_panel();
    }
}
