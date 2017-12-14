package pharmaceuticals.nl.peptrix.gui.createwilcoxon;

import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Northcreatewilcoxon {

	Controller cc;

	JPanel panelexperimentleft;

	JPanel panelexperimentright;

	JPanel createwilcoxonnorth;

	public Northcreatewilcoxon(Controller cc) {
		this.cc = cc;
		panelexperimentleft = new JPanel();
		panelexperimentright = new JPanel();
		createwilcoxonnorth = new JPanel();
		createwilcoxonnorth.setLayout(new BorderLayout());
		createwilcoxonnorth.add(panelexperimentleft, BorderLayout.WEST);
		createwilcoxonnorth.add(panelexperimentright, BorderLayout.CENTER);
	}

	public void fillexperimentleftpanel() {
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
		cc.experimentidwilcoxon = new TextField(20);
		cc.experimentidwilcoxon.addTextListener(cc);
		cc.experimentidwilcoxon.setEnabled(false);
		cc.experimentidwilcoxon.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.experimentidwilcoxon, constraintsexperiment);
		panelexperimentleft.add(cc.experimentidwilcoxon);
		constraintsexperiment.gridx = 3;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		cc.searchwilcoxon = new JButton("Search");
		cc.searchwilcoxon.addActionListener(cc);
		gridbagexperiment.setConstraints(cc.searchwilcoxon, constraintsexperiment);
		panelexperimentleft.add(cc.searchwilcoxon);
		JLabel labelexperimentname = new JLabel("Name");
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 2;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(labelexperimentname, constraintsexperiment);
		panelexperimentleft.add(labelexperimentname);
		cc.experimentnamewilcoxon = new TextField(30);
		cc.experimentnamewilcoxon.setEnabled(false);
		cc.experimentnamewilcoxon.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 2;
		constraintsexperiment.gridwidth = 2;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.experimentnamewilcoxon, constraintsexperiment);
		panelexperimentleft.add(cc.experimentnamewilcoxon);
		JLabel equipmentlabel = new JLabel("Equipment");
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 3;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(equipmentlabel, constraintsexperiment);
		panelexperimentleft.add(equipmentlabel);
		cc.equipmentnamewilcoxon = new TextField(50);
		cc.equipmentnamewilcoxon.setEnabled(false);
		cc.equipmentnamewilcoxon.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 3;
		constraintsexperiment.gridwidth = 2;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.equipmentnamewilcoxon, constraintsexperiment);
		panelexperimentleft.add(cc.equipmentnamewilcoxon);
		JLabel datelabel = new JLabel("Date");
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 4;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(datelabel, constraintsexperiment);
		panelexperimentleft.add(datelabel);
		cc.dateexperimentwilcoxon = new TextField(10);
		cc.dateexperimentwilcoxon.setEnabled(false);
		cc.dateexperimentwilcoxon.setBackground(cc.colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 4;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(cc.dateexperimentwilcoxon, constraintsexperiment);
		panelexperimentleft.add(cc.dateexperimentwilcoxon);
	}

	public JPanel getpanel() {
		return createwilcoxonnorth;
	}

}
