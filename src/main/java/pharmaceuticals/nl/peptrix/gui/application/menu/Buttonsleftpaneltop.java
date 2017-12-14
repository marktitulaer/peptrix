package pharmaceuticals.nl.peptrix.gui.application.menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Buttonsleftpaneltop {

	JPanel jpanel;

	public Buttonsleftpaneltop(Controller cc) {
		jpanel = new JPanel();
		jpanel.setBackground(cc.menubackgroundcolor);
		GridBagLayout gridbglayout = new GridBagLayout();
		GridBagConstraints gridbagconstraints = new GridBagConstraints();
		gridbagconstraints.anchor = GridBagConstraints.NORTHEAST;
		gridbagconstraints.insets = new Insets(2, 2, 2, 2);
		jpanel.setLayout(gridbglayout);
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 1;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		Dimension fill = new Dimension(20, 40);
		Box.Filler vFill = new Box.Filler(fill, fill, fill);
		gridbglayout.setConstraints(vFill, gridbagconstraints);
		jpanel.add(vFill);
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 2;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		gridbglayout.setConstraints(cc.btnallocation, gridbagconstraints);
		cc.btnallocation.addActionListener(cc);
		jpanel.add(cc.btnallocation);
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 3;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		gridbglayout.setConstraints(cc.btncreatematrix, gridbagconstraints);
		cc.btncreatematrix.addActionListener(cc);
		jpanel.add(cc.btncreatematrix);
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 4;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		gridbglayout.setConstraints(cc.btntransposematrix, gridbagconstraints);
		cc.btntransposematrix.addActionListener(cc);
		jpanel.add(cc.btntransposematrix);
		gridbagconstraints.gridx = 1;
		gridbagconstraints.gridy = 5;
		gridbagconstraints.gridwidth = 1;
		gridbagconstraints.gridheight = 1;
		gridbglayout.setConstraints(cc.btnwilcoxon, gridbagconstraints);
		cc.btnwilcoxon.addActionListener(cc);
		jpanel.add(cc.btnwilcoxon);
	}

	public JPanel getPanel() {
		return jpanel;
	}

}
