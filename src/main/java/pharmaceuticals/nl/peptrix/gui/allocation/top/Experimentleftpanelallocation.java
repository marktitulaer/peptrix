package pharmaceuticals.nl.peptrix.gui.allocation.top;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Experimentleftpanelallocation {

	JPanel panelexperimentleft;

	GridBagLayout gridbagexperiment;

	GridBagConstraints constraintsexperiment;

	JLabel experimentidlabel;

	JLabel labelexperimentname;

	JLabel equipmentlabel;

	JLabel datelabel;

	JLabel labelfiletype;

	JLabel selectsamplelabel;

	public Experimentleftpanelallocation(Controller cc) {
		panelexperimentleft = new JPanel();
		panelexperimentleft.setBorder(BorderFactory.createTitledBorder("Experiment"));
		gridbagexperiment = new GridBagLayout();
		panelexperimentleft.setLayout(gridbagexperiment);
		constraintsexperiment = new GridBagConstraints();
		constraintsexperiment.anchor = GridBagConstraints.FIRST_LINE_START;
		constraintsexperiment.insets = new Insets(2, 2, 2, 2);
		experimentidlabel = new JLabel(" ID");
		constraintsexperiment.gridx = 0;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(experimentidlabel, constraintsexperiment);
		panelexperimentleft.add(experimentidlabel);
		cc.experimentid = new TextField(20);
		cc.experimentid.addTextListener(cc);
		cc.experimentid.setEnabled(false);
		cc.experimentid.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.experimentid, constraintsexperiment);
		panelexperimentleft.add(cc.experimentid);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 3;
		constraintsexperiment.gridheight = 1;
		cc.search_sample = new JButton("Search");
		cc.search_sample.addActionListener(cc);
		gridbagexperiment.setConstraints(cc.search_sample, constraintsexperiment);
		panelexperimentleft.add(cc.search_sample);
		labelexperimentname = new JLabel("Name");
		constraintsexperiment.gridx = 0;
		constraintsexperiment.gridy = 2;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(labelexperimentname, constraintsexperiment);
		panelexperimentleft.add(labelexperimentname);
		cc.experimentname = new TextField(30);
		cc.experimentname.setEnabled(false);
		cc.experimentname.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 2;
		constraintsexperiment.gridwidth = 4;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.experimentname, constraintsexperiment);
		panelexperimentleft.add(cc.experimentname);
		equipmentlabel = new JLabel("Equipment");
		constraintsexperiment.gridx = 0;
		constraintsexperiment.gridy = 3;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(equipmentlabel, constraintsexperiment);
		panelexperimentleft.add(equipmentlabel);
		cc.equipmentname = new TextField(50);
		cc.equipmentname.setEnabled(false);
		cc.equipmentname.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 3;
		constraintsexperiment.gridwidth = 4;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.equipmentname, constraintsexperiment);
		panelexperimentleft.add(cc.equipmentname);
		datelabel = new JLabel("Date");
		constraintsexperiment.gridx = 0;
		constraintsexperiment.gridy = 4;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(datelabel, constraintsexperiment);
		panelexperimentleft.add(datelabel);
		cc.dateexperiment = new TextField(10);
		cc.dateexperiment.setEnabled(false);
		cc.dateexperiment.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 4;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.dateexperiment, constraintsexperiment);
		panelexperimentleft.add(cc.dateexperiment);
		labelfiletype = new JLabel("File Type");
		constraintsexperiment.gridx = 0;
		constraintsexperiment.gridy = 5;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(labelfiletype, constraintsexperiment);
		panelexperimentleft.add(labelfiletype);
		cc.filtercombobox = new JComboBox(cc.filtertypes);
		cc.filtercombobox.setLightWeightPopupEnabled(false);
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 5;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.filtercombobox, constraintsexperiment);
		panelexperimentleft.add(cc.filtercombobox);
		cc.filtercombobox.addActionListener(cc);
		constraintsexperiment.gridx = 0;
		constraintsexperiment.gridy = 6;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		selectsamplelabel = new JLabel(" Sample");
		gridbagexperiment.setConstraints(selectsamplelabel, constraintsexperiment);
		panelexperimentleft.add(selectsamplelabel);
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 6;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		cc.selectsampleid = new TextField(20);
		cc.selectsampleid.addTextListener(cc);
		cc.selectsampleid.setEnabled(false);
		cc.selectsampleid.setBackground(cc.colorgrey);
		gridbagexperiment.setConstraints(cc.selectsampleid, constraintsexperiment);
		panelexperimentleft.add(cc.selectsampleid);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 6;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		cc.selectinputsample = new JButton("Search");
		cc.selectinputsample.addActionListener(cc);
		gridbagexperiment.setConstraints(cc.selectinputsample, constraintsexperiment);
		panelexperimentleft.add(cc.selectinputsample);
		constraintsexperiment.gridx = 3;
		constraintsexperiment.gridy = 6;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		cc.clearselectedsample = new JButton("Clear");
		cc.clearselectedsample.addActionListener(cc);
		gridbagexperiment.setConstraints(cc.clearselectedsample, constraintsexperiment);
		panelexperimentleft.add(cc.clearselectedsample);
		constraintsexperiment.gridx = 4;
		constraintsexperiment.gridy = 6;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		cc.selectsamplecode = new TextField(16);
		cc.selectsamplecode.setBackground(Color.YELLOW);
		cc.selectsamplecode.setEnabled(false);
		gridbagexperiment.setConstraints(cc.selectsamplecode, constraintsexperiment);
		panelexperimentleft.add(cc.selectsamplecode);
	}

	public JPanel getpanel() {
		return panelexperimentleft;
	}

}
