package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

class CreateMatrixStartPanel {
    JPanel startcreatematrixpanel;
    Controller cc;

    public CreateMatrixStartPanel(Controller cc) {
        startcreatematrixpanel = new JPanel();
        this.cc = cc;
    }

    public void fill_panels_with_fields() {
        String[] creatematrixmethods = {"Signal to Noise", "Simple Peak Find (Quantile)"};
        startcreatematrixpanel.removeAll();
        startcreatematrixpanel.setBorder(BorderFactory.createTitledBorder("Start create matrix"));
        GridBagLayout gridbagstartcreatematrix = new GridBagLayout();
        startcreatematrixpanel.setLayout(gridbagstartcreatematrix);
        GridBagConstraints constraintsstartcreatematrix = new GridBagConstraints();
        constraintsstartcreatematrix.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsstartcreatematrix.insets = new Insets(2, 2, 2, 2);
        JLabel labelcreatematrixmethod = new JLabel("Method");
        constraintsstartcreatematrix.gridx = 1;
        constraintsstartcreatematrix.gridy = 1;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(labelcreatematrixmethod, constraintsstartcreatematrix);
        startcreatematrixpanel.add(labelcreatematrixmethod);
        cc.creatematrixmethodcombobox = new JComboBox(creatematrixmethods);
        cc.creatematrixmethodcombobox.setLightWeightPopupEnabled(false);
        constraintsstartcreatematrix.gridx = 2;
        constraintsstartcreatematrix.gridy = 1;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(cc.creatematrixmethodcombobox, constraintsstartcreatematrix);
        startcreatematrixpanel.add(cc.creatematrixmethodcombobox);
        JLabel labelmatrixfiles = new JLabel("Display");
        constraintsstartcreatematrix.gridx = 1;
        constraintsstartcreatematrix.gridy = 2;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(labelmatrixfiles, constraintsstartcreatematrix);
        startcreatematrixpanel.add(labelmatrixfiles);
        cc.combomatrixtodisplay = new JComboBox(cc.matrixfiles);
        cc.combomatrixtodisplay.setLightWeightPopupEnabled(false);
        cc.combomatrixtodisplay.addActionListener(cc);
        constraintsstartcreatematrix.gridx = 2;
        constraintsstartcreatematrix.gridy = 2;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(cc.combomatrixtodisplay, constraintsstartcreatematrix);
        startcreatematrixpanel.add(cc.combomatrixtodisplay);
        JLabel labelfiletype = new JLabel("File type");
        constraintsstartcreatematrix.gridx = 1;
        constraintsstartcreatematrix.gridy = 3;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(labelfiletype, constraintsstartcreatematrix);
        startcreatematrixpanel.add(labelfiletype);
        cc.filtercombobox2 = new JComboBox();
        cc.filtercombobox2.setLightWeightPopupEnabled(false);
        constraintsstartcreatematrix.gridx = 2;
        constraintsstartcreatematrix.gridy = 3;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(cc.filtercombobox2, constraintsstartcreatematrix);
        startcreatematrixpanel.add(cc.filtercombobox2);
        cc.allocation2 = new JButton("Allocation");
        constraintsstartcreatematrix.gridx = 1;
        constraintsstartcreatematrix.gridy = 4;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(cc.allocation2, constraintsstartcreatematrix);
        startcreatematrixpanel.add(cc.allocation2);
        cc.allocation2.addActionListener(cc);
        cc.btnwilcoxon2 = new JButton("Wilcoxon");
        constraintsstartcreatematrix.gridx = 1;
        constraintsstartcreatematrix.gridy = 5;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(cc.btnwilcoxon2, constraintsstartcreatematrix);
        startcreatematrixpanel.add(cc.btnwilcoxon2);
        cc.btnwilcoxon2.addActionListener(cc);
        cc.startcreatingmatrix = new JButton("Start");
        cc.startcreatingmatrix.setBackground(Color.GREEN);
        constraintsstartcreatematrix.gridx = 1;
        constraintsstartcreatematrix.gridy = 6;
        constraintsstartcreatematrix.gridwidth = 1;
        constraintsstartcreatematrix.gridheight = 1;
        gridbagstartcreatematrix.setConstraints(cc.startcreatingmatrix, constraintsstartcreatematrix);
        startcreatematrixpanel.add(cc.startcreatingmatrix);
        cc.startcreatingmatrix.addActionListener(cc);
    }

    public JPanel getpanel() {
        return startcreatematrixpanel;
    }
}
