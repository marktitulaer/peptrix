package pharmaceuticals.nl.peptrix.gui.creatematrix.top;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class ExperimentLeftPanelCreateMatrix {
    JPanel panelexperimentleft;

    public ExperimentLeftPanelCreateMatrix(Controller cc) {
        panelexperimentleft = new JPanel();
        panelexperimentleft.setBorder(BorderFactory.createTitledBorder("Experiment"));
        GridBagLayout gridbagexperiment = new GridBagLayout();
        panelexperimentleft.setLayout(gridbagexperiment);
        GridBagConstraints constraintsexperiment = new GridBagConstraints();
        constraintsexperiment.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsexperiment.insets = new Insets(2, 2, 2, 2);
        JLabel experimentidlabel = new JLabel(" ID");
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 1;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(experimentidlabel, constraintsexperiment);
        panelexperimentleft.add(experimentidlabel);
        cc.createMatrixAction.experimentid2 = new TextField(20);
        cc.createMatrixAction.experimentid2.addTextListener(cc.createMatrixAction);
        cc.createMatrixAction.experimentid2.setEnabled(false);
        cc.createMatrixAction.experimentid2.setBackground(cc.colorgrey);
        constraintsexperiment.gridx = 2;
        constraintsexperiment.gridy = 1;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.createMatrixAction.experimentid2, constraintsexperiment);
        panelexperimentleft.add(cc.createMatrixAction.experimentid2);
        constraintsexperiment.gridx = 3;
        constraintsexperiment.gridy = 1;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        cc.search_experiment = new JButton("Search");
        cc.search_experiment.addActionListener(cc);
        gridbagexperiment.setConstraints(cc.search_experiment, constraintsexperiment);
        panelexperimentleft.add(cc.search_experiment);
        JLabel labelexperimentname = new JLabel("Name");
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 2;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(labelexperimentname, constraintsexperiment);
        panelexperimentleft.add(labelexperimentname);
        cc.experimentname2 = new TextField(30);
        cc.experimentname2.setEnabled(false);
        cc.experimentname2.setBackground(cc.colorgrey);
        constraintsexperiment.gridx = 2;
        constraintsexperiment.gridy = 2;
        constraintsexperiment.gridwidth = 2;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.experimentname2, constraintsexperiment);
        panelexperimentleft.add(cc.experimentname2);
        JLabel equipmentlabel = new JLabel("Equipment");
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 3;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(equipmentlabel, constraintsexperiment);
        panelexperimentleft.add(equipmentlabel);
        cc.equipmentname2 = new TextField(50);
        cc.equipmentname2.setEnabled(false);
        cc.equipmentname2.setBackground(cc.colorgrey);
        constraintsexperiment.gridx = 2;
        constraintsexperiment.gridy = 3;
        constraintsexperiment.gridwidth = 2;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.equipmentname2, constraintsexperiment);
        panelexperimentleft.add(cc.equipmentname2);
        JLabel datelabel = new JLabel("Date");
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 4;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(datelabel, constraintsexperiment);
        panelexperimentleft.add(datelabel);
        cc.dateexperiment2 = new TextField(10);
        cc.dateexperiment2.setEnabled(false);
        cc.dateexperiment2.setBackground(cc.colorgrey);
        constraintsexperiment.gridx = 2;
        constraintsexperiment.gridy = 4;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.dateexperiment2, constraintsexperiment);
        panelexperimentleft.add(cc.dateexperiment2);
    }

    public JPanel getpanel() {
        return panelexperimentleft;
    }
}
