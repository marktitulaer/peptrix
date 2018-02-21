package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import pharmaceuticals.nl.peptrix.Controller;

public class PanelPreprocessing {

    JPanel preprocessingpanel;

    GridBagLayout preprocessinglayout;

    GridBagConstraints gridbagcontraintspreprocessing;

    JLabel labelsingnaltonoiseinparts;

    JLabel labelcentroidingmethod;

    Controller cc;

    public PanelPreprocessing(Controller cc) {
        this.cc = cc;
        preprocessingpanel = new JPanel();
    }

    public void fill_panels_with_fields() {
        preprocessingpanel.removeAll();
        preprocessingpanel.setBorder(BorderFactory.createTitledBorder("Pre-processing"));
        preprocessinglayout = new GridBagLayout();
        preprocessingpanel.setLayout(preprocessinglayout);
        gridbagcontraintspreprocessing = new GridBagConstraints();
        gridbagcontraintspreprocessing.anchor = GridBagConstraints.FIRST_LINE_START;
        gridbagcontraintspreprocessing.insets = new Insets(2, 2, 2, 2);
        labelsingnaltonoiseinparts = new JLabel();
        labelsingnaltonoiseinparts.setText("Determine noise in parts of the spectrum");
        gridbagcontraintspreprocessing.gridx = 1;
        gridbagcontraintspreprocessing.gridy = 1;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(labelsingnaltonoiseinparts, gridbagcontraintspreprocessing);
        preprocessingpanel.add(labelsingnaltonoiseinparts);
        cc.inputsingnaltonoiseinparts = new TextField(6);
        gridbagcontraintspreprocessing.gridx = 2;
        gridbagcontraintspreprocessing.gridy = 1;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(cc.inputsingnaltonoiseinparts, gridbagcontraintspreprocessing);
        preprocessingpanel.add(cc.inputsingnaltonoiseinparts);
        cc.inputsingnaltonoiseinparts.setText("20");
        cc.labelwindowcentroiding = new JLabel();
        gridbagcontraintspreprocessing.gridx = 1;
        gridbagcontraintspreprocessing.gridy = 2;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(cc.labelwindowcentroiding, gridbagcontraintspreprocessing);
        preprocessingpanel.add(cc.labelwindowcentroiding);
        cc.inputwindowcentroiding = new TextField(6);
        gridbagcontraintspreprocessing.gridx = 2;
        gridbagcontraintspreprocessing.gridy = 2;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(cc.inputwindowcentroiding, gridbagcontraintspreprocessing);
        preprocessingpanel.add(cc.inputwindowcentroiding);
        cc.labellimitcentroiding = new JLabel();
        gridbagcontraintspreprocessing.gridx = 1;
        gridbagcontraintspreprocessing.gridy = 3;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(cc.labellimitcentroiding, gridbagcontraintspreprocessing);
        preprocessingpanel.add(cc.labellimitcentroiding);
        cc.inputlimitcentroiding = new TextField(6);
        gridbagcontraintspreprocessing.gridx = 2;
        gridbagcontraintspreprocessing.gridy = 3;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(cc.inputlimitcentroiding, gridbagcontraintspreprocessing);
        preprocessingpanel.add(cc.inputlimitcentroiding);
        labelcentroidingmethod = new JLabel();
        labelcentroidingmethod.setText("Centroiding method");
        gridbagcontraintspreprocessing.gridx = 1;
        gridbagcontraintspreprocessing.gridy = 4;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(labelcentroidingmethod, gridbagcontraintspreprocessing);
        preprocessingpanel.add(labelcentroidingmethod);
        cc.centroidingmethods = new ButtonGroup();
        cc.none = new JRadioButton("none");
        cc.none.setMnemonic(KeyEvent.VK_0);
        cc.centroidingmethods.add(cc.none);
        cc.none.addActionListener(cc);
        gridbagcontraintspreprocessing.gridx = 2;
        gridbagcontraintspreprocessing.gridy = 4;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(cc.none, gridbagcontraintspreprocessing);
        preprocessingpanel.add(cc.none);
        cc.weightedmean = new JRadioButton("Weighted mean");
        cc.weightedmean.setMnemonic(KeyEvent.VK_1);
        cc.centroidingmethods.add(cc.weightedmean);
        cc.weightedmean.addActionListener(cc);
        gridbagcontraintspreprocessing.gridx = 2;
        gridbagcontraintspreprocessing.gridy = 5;
        gridbagcontraintspreprocessing.gridwidth = 1;
        gridbagcontraintspreprocessing.gridheight = 1;
        preprocessinglayout.setConstraints(cc.weightedmean, gridbagcontraintspreprocessing);
        preprocessingpanel.add(cc.weightedmean);
    }

    public JPanel getpanel() {
        return preprocessingpanel;
    }

}
