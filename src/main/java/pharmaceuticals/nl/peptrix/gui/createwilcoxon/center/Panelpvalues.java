package pharmaceuticals.nl.peptrix.gui.createwilcoxon.center;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import pharmaceuticals.nl.peptrix.Controller;

public class Panelpvalues {

    JPanel detailspanel;

    JScrollPane tablepane;

    JPanel pvaluespanel;

    Controller cc;

    GridBagLayout gridbagpvaluespanel;

    GridBagConstraints pvaluespanelconstraints;

    public JPanel p_plot_panel;

    JPanel tablepanel;

    public JPanel gettablepanel() {
        return tablepanel;
    }

    public Panelpvalues(Controller cc) {
        this.cc = cc;
        cc.buttonsavedetails = new JButton("save details");
        cc.buttonsavedetails.addActionListener(cc);
        pvaluespanel = new JPanel();
        detailspanel = new JPanel();
        detailspanel.setLayout(new BorderLayout());
        detailspanel.add(cc.buttonsavedetails, BorderLayout.WEST);
        tablepanel = new JPanel();
        tablepanel.setLayout(new BorderLayout());
        gridbagpvaluespanel = new GridBagLayout();
        pvaluespanelconstraints = new GridBagConstraints();
        pvaluespanelconstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        pvaluespanelconstraints.insets = new Insets(2, 2, 2, 2);
        pvaluespanel.setLayout(gridbagpvaluespanel);
        pvaluespanelconstraints.gridx = 1;
        pvaluespanelconstraints.gridy = 1;
        pvaluespanelconstraints.gridwidth = 1;
        pvaluespanelconstraints.gridheight = 1;
        gridbagpvaluespanel.setConstraints(tablepanel, pvaluespanelconstraints);
        pvaluespanel.add(tablepanel);
        p_plot_panel = new JPanel();
        pvaluespanelconstraints.gridx = 2;
        pvaluespanelconstraints.gridy = 1;
        pvaluespanelconstraints.gridwidth = 1;
        pvaluespanelconstraints.gridheight = 1;
        gridbagpvaluespanel.setConstraints(p_plot_panel, pvaluespanelconstraints);
        pvaluespanel.add(p_plot_panel);
    }

    public void setBackground(Color color) {
        tablepanel.setBackground(color);
        p_plot_panel.setBackground(color);
        pvaluespanel.setBackground(color);

    }

    public void displayPvalues(JTable Pvaluestable) {
        tablepane = new JScrollPane(Pvaluestable);
        tablepanel.add(detailspanel, BorderLayout.NORTH);
        tablepanel.add(tablepane, BorderLayout.CENTER);
    }

    public JPanel getpanel() {
        return pvaluespanel;
    }

    public void removeAll_from_p_plot_panel() {
        p_plot_panel.removeAll();
    }

    public void removeAll_from_p_values_panel() {
        tablepanel.removeAll();
    }
}
