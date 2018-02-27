package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.Checkbox;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Calibrationinputpanel {
    JPanel jpanel;

    public Calibrationinputpanel(Controller cc) {
        jpanel = new JPanel();
        GridBagLayout gridbagcalibration = new GridBagLayout();
        jpanel.setLayout(gridbagcalibration);
        jpanel.setBorder(BorderFactory.createTitledBorder("Calibration"));
        gridbagcalibration = new GridBagLayout();
        jpanel.setLayout(gridbagcalibration);
        GridBagConstraints constraintscalibration = new GridBagConstraints();
        constraintscalibration.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintscalibration.insets = new Insets(2, 2, 2, 2);
        JLabel internalcalibration_yes_no = new JLabel("Internal calibration ?");
        constraintscalibration.gridx = 1;
        constraintscalibration.gridy = 1;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(internalcalibration_yes_no, constraintscalibration);
        jpanel.add(internalcalibration_yes_no);
        cc.checkbox_internalcalibration = new Checkbox();
        cc.checkbox_internalcalibration.setState(true);
        constraintscalibration.gridx = 2;
        constraintscalibration.gridy = 1;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(cc.checkbox_internalcalibration, constraintscalibration);
        jpanel.add(cc.checkbox_internalcalibration);
        JLabel labelnumbermissingpeaksallowed = new JLabel("Number of missing calibration peaks allowed");
        constraintscalibration.gridx = 1;
        constraintscalibration.gridy = 2;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(labelnumbermissingpeaksallowed, constraintscalibration);
        jpanel.add(labelnumbermissingpeaksallowed);
        cc.missingcalibratiopeaksallowed = new TextField(4);
        cc.missingcalibratiopeaksallowed.setText("0");
        constraintscalibration.gridx = 2;
        constraintscalibration.gridy = 2;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(cc.missingcalibratiopeaksallowed, constraintscalibration);
        jpanel.add(cc.missingcalibratiopeaksallowed);
        JLabel labelcalibrationmasses = new JLabel("Calibration standard");
        constraintscalibration.gridx = 1;
        constraintscalibration.gridy = 3;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(labelcalibrationmasses, constraintscalibration);
        jpanel.add(labelcalibrationmasses);
        cc.calibrationstandard = new JComboBox(cc.strcalibrationstandards);
        cc.calibrationstandard.setLightWeightPopupEnabled(false);
        constraintscalibration.gridx = 2;
        constraintscalibration.gridy = 3;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(cc.calibrationstandard, constraintscalibration);
        jpanel.add(cc.calibrationstandard);
        cc.labeldeltamzsearchcalibrants = new JLabel();
        constraintscalibration.gridx = 1;
        constraintscalibration.gridy = 4;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(cc.labeldeltamzsearchcalibrants, constraintscalibration);
        jpanel.add(cc.labeldeltamzsearchcalibrants);
        cc.inputdeltamzsearchcalibrant = new TextField(4);
        constraintscalibration.gridx = 2;
        constraintscalibration.gridy = 4;
        constraintscalibration.gridwidth = 1;
        constraintscalibration.gridheight = 1;
        gridbagcalibration.setConstraints(cc.inputdeltamzsearchcalibrant, constraintscalibration);
        jpanel.add(cc.inputdeltamzsearchcalibrant);
    }

    public void setVisible(boolean visible) {
        jpanel.setVisible(visible);
    }

    public JPanel getPanel() {
        return jpanel;
    }
}
