package pharmaceuticals.nl.peptrix.gui.createwilcoxon.center;

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

public class Inputpanelwilcoxon {

	Controller cc;

	JPanel wilcoxoninputpanel;

	public Inputpanelwilcoxon(Controller cc) {
		this.cc = cc;
		wilcoxoninputpanel = new JPanel();
	}

	public void fillwilcoxoninputpanel() {
		wilcoxoninputpanel.setBorder(BorderFactory.createTitledBorder("Wilcoxon"));
		GridBagLayout gridbagwilcoxoninoutpanel = new GridBagLayout();
		wilcoxoninputpanel.setLayout(gridbagwilcoxoninoutpanel);
		GridBagConstraints wilcoxoninputpanelconstraints = new GridBagConstraints();
		wilcoxoninputpanelconstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		wilcoxoninputpanelconstraints.insets = new Insets(2, 2, 2, 2);
		JLabel labelselectmatrix = new JLabel("Matrix");
		wilcoxoninputpanelconstraints.gridx = 1;
		wilcoxoninputpanelconstraints.gridy = 1;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(labelselectmatrix, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(labelselectmatrix);
		cc.combomatrixtodisplaywilcoxon = new JComboBox(cc.matrixfiles);
		cc.combomatrixtodisplaywilcoxon.addActionListener(cc);
		cc.combomatrixtodisplaywilcoxon.setLightWeightPopupEnabled(false);
		wilcoxoninputpanelconstraints.gridx = 2;
		wilcoxoninputpanelconstraints.gridy = 1;
		wilcoxoninputpanelconstraints.gridwidth = 2;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.combomatrixtodisplaywilcoxon, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.combomatrixtodisplaywilcoxon);
		JLabel group1 = new JLabel("Group 1");
		wilcoxoninputpanelconstraints.gridx = 1;
		wilcoxoninputpanelconstraints.gridy = 2;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(group1, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(group1);
		cc.combogroupnumber1 = new JComboBox(cc.groupnumbers);
		cc.combogroupnumber1.setSelectedIndex(0);
		cc.combogroupnumber1.addActionListener(cc);
		cc.combogroupnumber1.setLightWeightPopupEnabled(false);
		wilcoxoninputpanelconstraints.gridx = 2;
		wilcoxoninputpanelconstraints.gridy = 2;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.combogroupnumber1, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.combogroupnumber1);
		String[] colors = { "green", "blue", "grey", "black", "white" };
		cc.colorgroup1 = new JComboBox(colors);
		cc.colorgroup1.setSelectedIndex(0);
		cc.colorgroup1.setLightWeightPopupEnabled(false);
		wilcoxoninputpanelconstraints.gridx = 3;
		wilcoxoninputpanelconstraints.gridy = 2;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.colorgroup1, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.colorgroup1);
		JLabel group2 = new JLabel("Group 2");
		wilcoxoninputpanelconstraints.gridx = 1;
		wilcoxoninputpanelconstraints.gridy = 3;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(group2, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(group2);
		cc.combogroupnumber2 = new JComboBox(cc.groupnumbers);
		if (cc.groupnumbers.length > 1) {
			cc.combogroupnumber2.setSelectedIndex(1);
		}
		cc.combogroupnumber2.addActionListener(cc);
		cc.combogroupnumber2.setLightWeightPopupEnabled(false);
		wilcoxoninputpanelconstraints.gridx = 2;
		wilcoxoninputpanelconstraints.gridy = 3;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.combogroupnumber2, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.combogroupnumber2);
		cc.colorgroup2 = new JComboBox(colors);
		if (colors.length > 1) {
			cc.colorgroup2.setSelectedIndex(1);
		}
		cc.colorgroup2.setLightWeightPopupEnabled(false);
		wilcoxoninputpanelconstraints.gridx = 3;
		wilcoxoninputpanelconstraints.gridy = 3;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.colorgroup2, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.colorgroup2);
		JLabel labelnumberrandomizations = new JLabel("Number of randomizations");
		wilcoxoninputpanelconstraints.gridx = 1;
		wilcoxoninputpanelconstraints.gridy = 4;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(labelnumberrandomizations, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(labelnumberrandomizations);
		cc.numberofrandomizations = new TextField(10);
		cc.numberofrandomizations.setText("1");
		wilcoxoninputpanelconstraints.gridx = 2;
		wilcoxoninputpanelconstraints.gridy = 4;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.numberofrandomizations, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.numberofrandomizations);
		String[] randomizationgroups = { "Both groups", "All groups" };
		cc.comborandomizationgroups = new JComboBox(randomizationgroups);
		cc.comborandomizationgroups.setSelectedIndex(0);
		cc.comborandomizationgroups.setLightWeightPopupEnabled(false);
		wilcoxoninputpanelconstraints.gridx = 3;
		wilcoxoninputpanelconstraints.gridy = 4;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.comborandomizationgroups, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.comborandomizationgroups);
		JLabel plotwidth = new JLabel("Histogram plot width");
		wilcoxoninputpanelconstraints.gridx = 1;
		wilcoxoninputpanelconstraints.gridy = 5;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(plotwidth, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(plotwidth);
		cc.inputplotwidth = new TextField(10);
		cc.inputplotwidth.setText("480");
		wilcoxoninputpanelconstraints.gridx = 2;
		wilcoxoninputpanelconstraints.gridy = 5;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.inputplotwidth, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.inputplotwidth);
		JLabel plotheight = new JLabel("Histogram plot height");
		wilcoxoninputpanelconstraints.gridx = 1;
		wilcoxoninputpanelconstraints.gridy = 6;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(plotheight, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(plotheight);
		cc.inputplotheight = new TextField(10);
		cc.inputplotheight.setText("480");
		wilcoxoninputpanelconstraints.gridx = 2;
		wilcoxoninputpanelconstraints.gridy = 6;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.inputplotheight, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.inputplotheight);
		cc.startwilcoxon = new JButton("Start");
		cc.startwilcoxon.addActionListener(cc);
		cc.startwilcoxon.setBackground(Color.GREEN);
		wilcoxoninputpanelconstraints.gridx = 1;
		wilcoxoninputpanelconstraints.gridy = 7;
		wilcoxoninputpanelconstraints.gridwidth = 1;
		wilcoxoninputpanelconstraints.gridheight = 1;
		gridbagwilcoxoninoutpanel.setConstraints(cc.startwilcoxon, wilcoxoninputpanelconstraints);
		wilcoxoninputpanel.add(cc.startwilcoxon);
	}

	public void setBackground(Color color) {
		wilcoxoninputpanel.setBackground(color);
	}

	public JPanel getpanel() {
		return wilcoxoninputpanel;
	}
}
