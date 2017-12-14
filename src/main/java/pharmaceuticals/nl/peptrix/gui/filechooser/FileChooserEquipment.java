package pharmaceuticals.nl.peptrix.gui.filechooser;

import java.awt.BorderLayout;
import pharmaceuticals.nl.peptrix.Controller;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileChooserEquipment {

	JPanel panelequipment;

	JPanel panelequipmentleft;

	JPanel panelequipmentright;

	GridBagLayout gridbagequipment;

	GridBagConstraints constraintsequipment;

	JLabel equipmentidlabel;

	Controller cc;

	public FileChooserEquipment(Controller cc) {
		this.cc = cc;
		cc.equipmentcombo = new JComboBox(cc.equipmentnames);
		cc.equipmentcombo.setSelectedIndex(-1);
		cc.equipmentcombo.setEditable(false);
		cc.equipmentcombo.setLightWeightPopupEnabled(false);
		cc.equipmentcombo.addActionListener(cc);
		panelequipment = new JPanel();
		panelequipment.setLayout(new BorderLayout());
		panelequipment.setBorder(BorderFactory.createTitledBorder("Equipment"));
		panelequipmentleft = new JPanel();
		panelequipmentright = new JPanel();
		gridbagequipment = new GridBagLayout();
		panelequipmentleft.setLayout(gridbagequipment);
		constraintsequipment = new GridBagConstraints();
		constraintsequipment.anchor = GridBagConstraints.FIRST_LINE_START;
		constraintsequipment.insets = new Insets(2, 2, 2, 2);
		constraintsequipment.gridx = 0;
		constraintsequipment.gridy = 0;
		constraintsequipment.gridwidth = 2;
		constraintsequipment.gridheight = 1;
		gridbagequipment.setConstraints(cc.equipmentcombo, constraintsequipment);
		panelequipmentleft.add(cc.equipmentcombo);
		equipmentidlabel = new JLabel(" ID");
		constraintsequipment.gridx = 0;
		constraintsequipment.gridy = 1;
		constraintsequipment.gridwidth = 1;
		constraintsequipment.gridheight = 1;
		gridbagequipment.setConstraints(equipmentidlabel, constraintsequipment);
		panelequipmentleft.add(equipmentidlabel);
		cc.equipmentid = new TextField(20);
		constraintsequipment.gridx = 1;
		constraintsequipment.gridy = 1;
		constraintsequipment.gridwidth = 1;
		constraintsequipment.gridheight = 1;
		gridbagequipment.setConstraints(cc.equipmentid, constraintsequipment);
		panelequipmentleft.add(cc.equipmentid);
		panelequipment.add(panelequipmentleft, BorderLayout.WEST);
		panelequipment.add(panelequipmentright, BorderLayout.CENTER);
	}

	public JPanel getpanelequipment() {
		return panelequipment;
	}
}
