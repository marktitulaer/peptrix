package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pharmaceuticals.nl.peptrix.Controller;

public class CenterpanelsouthCreatematrix {

	JPanel creatematrixcenterpanelsouth;

	GridBagLayout gridbglayoutcenterpanelsouth;

	GridBagConstraints gridbagconstraints;

	JLabel numberofsamples;

	JLabel numberofmasses;

	public CenterpanelsouthCreatematrix(Controller cc) {
		creatematrixcenterpanelsouth = new JPanel();
		gridbglayoutcenterpanelsouth = new GridBagLayout();
		gridbagconstraints = new GridBagConstraints();
		gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
		gridbagconstraints.insets = new Insets(2, 2, 2, 2);
		creatematrixcenterpanelsouth.setLayout(gridbglayoutcenterpanelsouth);
	}

	public JPanel getpanel() {
		return creatematrixcenterpanelsouth;
	}

	public void setbackground(Color color) {
		creatematrixcenterpanelsouth.setBackground(color);
	}

	public void removeAll() {
		creatematrixcenterpanelsouth.removeAll();
	}

	public JPanel getcreatematrixcenterpanelsouth() {
		return creatematrixcenterpanelsouth;
	}

	public void removeAll2() {
		creatematrixcenterpanelsouth.removeAll();
		creatematrixcenterpanelsouth.setVisible(false);
	}

	public void displaymatrix(String[] temp, int numberofrows2, JScrollPane browserpane) {
		numberofmasses = new JLabel("total number of masses  : " + String.valueOf(temp.length - 4));
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 1;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		gridbglayoutcenterpanelsouth.setConstraints(numberofmasses, gridbagconstraints);
		creatematrixcenterpanelsouth.add(numberofmasses);
		numberofsamples = new JLabel("total number of samples  : " + String.valueOf(numberofrows2 - 1));
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 2;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		gridbglayoutcenterpanelsouth.setConstraints(numberofsamples, gridbagconstraints);
		creatematrixcenterpanelsouth.add(numberofsamples);
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 3;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		gridbglayoutcenterpanelsouth.setConstraints(browserpane, gridbagconstraints);
		creatematrixcenterpanelsouth.add(browserpane);
		creatematrixcenterpanelsouth.repaint();
	}

	public void setvisible() {
		creatematrixcenterpanelsouth.setVisible(true);
	}

}
