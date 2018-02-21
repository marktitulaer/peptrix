package pharmaceuticals.nl.peptrix.gui.filechooser;

import java.awt.Checkbox;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import pharmaceuticals.nl.peptrix.Controller;

public class FileChooserExperiment {
    Controller cc;
    JPanel panelexperimentleft;
    GridBagLayout gridbagexperiment;
    GridBagConstraints constraintsexperiment;
    ButtonGroup btngroup;
    JLabel experimentidlabel;
    JLabel labelexperimentname;
    JLabel automaticlabel;

    public FileChooserExperiment(Controller cc) {
        this.cc = cc;
        panelexperimentleft = new JPanel();
        gridbagexperiment = new GridBagLayout();
        panelexperimentleft.setLayout(gridbagexperiment);
        constraintsexperiment = new GridBagConstraints();
        constraintsexperiment.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsexperiment.insets = new Insets(2, 2, 2, 2);
        cc.newradio = new JRadioButton("New");
        cc.newradio.addActionListener(cc);
        cc.newradio.setMnemonic(KeyEvent.VK_1);
        btngroup = new ButtonGroup();
        btngroup.add(cc.newradio);
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 0;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.newradio, constraintsexperiment);
        panelexperimentleft.add(cc.newradio);
        cc.existradio = new JRadioButton("Existing");
        cc.existradio.addActionListener(cc);
        cc.existradio.setSelected(true);
        cc.existradio.setMnemonic(KeyEvent.VK_0);
        btngroup.add(cc.existradio);
        constraintsexperiment.gridx = 2;
        constraintsexperiment.gridy = 0;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.existradio, constraintsexperiment);
        panelexperimentleft.add(cc.existradio);
        experimentidlabel = new JLabel(" ID");
        constraintsexperiment.gridx = 0;
        constraintsexperiment.gridy = 1;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(experimentidlabel, constraintsexperiment);
        panelexperimentleft.add(experimentidlabel);
        cc.inputexperimentid = new TextField(20);
        cc.inputexperimentid.addTextListener(cc.createMatrixAction);
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 1;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.inputexperimentid, constraintsexperiment);
        panelexperimentleft.add(cc.inputexperimentid);
        constraintsexperiment.gridx = 2;
        constraintsexperiment.gridy = 1;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        cc.search = new JButton("Search");
        cc.search.addActionListener(cc);
        gridbagexperiment.setConstraints(cc.search, constraintsexperiment);
        panelexperimentleft.add(cc.search);
        labelexperimentname = new JLabel("Name");
        constraintsexperiment.gridx = 0;
        constraintsexperiment.gridy = 2;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(labelexperimentname, constraintsexperiment);
        panelexperimentleft.add(labelexperimentname);
        cc.inputexperimentname = new TextField(30);
        cc.inputexperimentname.setEnabled(false);
        cc.inputexperimentname.setBackground(cc.colorgrey);
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 2;
        constraintsexperiment.gridwidth = 2;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.inputexperimentname, constraintsexperiment);
        panelexperimentleft.add(cc.inputexperimentname);
        cc.checkboxgeneratesamplecodes = new Checkbox();
        cc.checkboxgeneratesamplecodes.setState(true);
        constraintsexperiment.gridx = 0;
        constraintsexperiment.gridy = 3;
        constraintsexperiment.gridwidth = 1;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(cc.checkboxgeneratesamplecodes, constraintsexperiment);
        panelexperimentleft.add(cc.checkboxgeneratesamplecodes);
        automaticlabel = new JLabel("Automatically generate sample codes");
        automaticlabel.setBackground(cc.colorgrey);
        constraintsexperiment.gridx = 1;
        constraintsexperiment.gridy = 3;
        constraintsexperiment.gridwidth = 2;
        constraintsexperiment.gridheight = 1;
        gridbagexperiment.setConstraints(automaticlabel, constraintsexperiment);
        panelexperimentleft.add(automaticlabel);
    }

    public JPanel getpanelexperiment() {
        return panelexperimentleft;
    }
}
