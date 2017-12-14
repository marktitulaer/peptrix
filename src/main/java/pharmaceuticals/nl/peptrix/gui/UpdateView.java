package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.Creatematrix;

public class UpdateView implements ActionListener {

	String[] type;

	JFrame calendarframe = new JFrame();

	JTabbedPane tabbedPane;

	ScrollPane updatemasterview;

	JPanel p;

	JPanel updatemastertotal;

	JPanel updatemasternorth;

	JPanel primarytablevalues;

	JPanel browsepanel;

	JPanel updatepanel;

	JPanel panel_OK_Cancel;

	JPanel mastertablevalues;

	JPanel mastertablevaluesleft;

	JPanel mastertablevaluesright;

	JPanel dummypanel;

	JPanel updatemastersouth;

	JPanel[] slavepanels;

	JPanel primarytablevaluestotal;

	JButton btnNext;

	JButton btnFirst;

	JButton btnPrevious;

	JButton btnLast;

	JButton btnrefresh;

	JButton btnNew;

	JButton btnUpdate;

	JButton btnDelete;

	JButton btnOK;

	JButton btnCancel;

	JButton btnallocation;

	JButton btncreatematrix;

	JButton[] open;

	JButton[] zoek;

	JButton[] clear;

	JButton[] btncalendar;

	String[] zoektable;

	TextField[] mastertablefieldvalue;

	TextField[] tablefield;

	Label[] masterfieldname;

	Label[] fieldname;

	GridBagLayout gridbaglayout = new GridBagLayout();

	GridBagLayout gridbaglayoutmastertable = new GridBagLayout();

	GridBagConstraints gridbagcontraints = new GridBagConstraints();

	Controller cc;

	SlavePane[] slavepane;

	Object[][] odata;

	Object[][] odata2;

	Object[][] odata_min;

	Object[][] odata_max;

	Object[][] odata_new;

	Object[][] odata_delete;

	String message;

	String actualstatus;

	String query;

	String TableName;

	String primarykey;

	String primarykeyvalue;

	String oldTableName;

	String oldprimarykey;

	String oldprimarykeyvalue;

	String strfieldname;

	String wherestring = "";

	String dialogtitle;

	String[] primarykeymastertable;

	String[] primarykeynamemastertable;

	StringBuffer querybuffer;

	String minvalue;

	String maxvalue;

	String newvalue;

	String wherestringsearch;

	String fidname;

	String searchstring;

	boolean firstrecord = false;

	boolean lastrecord = false;

	boolean recordtoupdate = false;

	boolean debugmode = false;

	boolean disable_delete_button = true;

	static final String num = "0123456789";

	static final String[] status = { "DISPLAY", "NEW", "UPDATE", "DELETE" };

	int slavepanelcount;

	int top = 2;

	int left = 2;

	int bottom = 2;

	int right = 2;

	int indexof_fid;

	JPanel searchtablerecordpanel;

	SearchSelection searchselection;

	JCalendar calendar;

	int rows;

	public UpdateView(Controller cc) {
		this.cc = cc;
		this.actualstatus = status[0];// DISPLAY
	}

	public void viewtablerecord(String TableName, String primarykey, String primarykeyvalue) {
		this.TableName = TableName;
		this.primarykey = primarykey;
		this.primarykeyvalue = primarykeyvalue;
		if (this.actualstatus != status[1]) {
			oldTableName = TableName;
			oldprimarykey = primarykey;
			oldprimarykeyvalue = primarykeyvalue;
		}
		int p = 0;
		odata = null;
		odata2 = null;
		String query;
		tabbedPane = new JTabbedPane();
		try {
			query = "show tables";
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			if (debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		String tabs[] = new String[20];
		tabs[p] = TableName.trim();
		for (int i = 0; i <= (odata.length - 1); i++) {
			if (!odata[i][0].toString().trim().toLowerCase().equals(TableName.trim().toLowerCase())) {
				try {
					query = "describe " + odata[i][0].toString();
					odata2 = cc.jdbcconnection.returnData(query);
					for (int j = 0; j <= (odata2.length - 1); j++) {
						if (primarykey.trim().equals(odata2[j][0].toString().trim())) {
							p++;
							tabs[p] = odata[i][0].toString();
						}
					}
				} catch (SQLException e) {
					if (debugmode) {
						e.printStackTrace();
					} else {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
		slavepanels = new JPanel[p];
		slavepane = new SlavePane[p];
		slavepanelcount = 0;
		for (int i = 0; i <= p; i++) {
			if (i == 0) {
				createmasterPane(tabs[i], primarykey, primarykeyvalue);
			} else {
				createslavePanes(tabs[i], primarykey, primarykeyvalue);
			}
		}

		cc.setslavepane(slavepane);
		tabbedPane.setSelectedIndex(0);
		cc.pBody.removeAll();
		cc.pBody.add(tabbedPane);
		cc.frame.repaint();
		cc.frame.setVisible(true);
	}

	private void createmasterPane(String tab, String primarykey, String primarykeyvalue) {
		btnNew = new JButton("New");
		btnNew.addActionListener(this);
		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(this);
		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this);
		btnNext = new JButton(">");
		btnNext.addActionListener(this);
		btnFirst = new JButton("|<");
		btnFirst.addActionListener(this);
		btnrefresh = new JButton("[]");
		btnrefresh.addActionListener(this);
		btnPrevious = new JButton("<");
		btnPrevious.addActionListener(this);
		btnLast = new JButton(">|");
		btnLast.addActionListener(this);
		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);
		p = new JPanel();
		updatepanel = new JPanel();
		panel_OK_Cancel = new JPanel();
		browsepanel = new JPanel();
		browsepanel.add(btnFirst);
		browsepanel.add(btnPrevious);
		browsepanel.add(btnrefresh);
		browsepanel.add(btnNext);
		browsepanel.add(btnLast);
		updatepanel.add(btnNew);
		updatepanel.add(btnUpdate);
		if (disable_delete_button) {
			btnDelete.setEnabled(false);
		}
		updatepanel.add(btnDelete);
		panel_OK_Cancel.add(btnOK);
		panel_OK_Cancel.add(btnCancel);
		p.setLayout(new BorderLayout());
		updatemasterview = new ScrollPane();
		updatemasterview.setSize(new Dimension(800, 600));
		mastertablevalues = new JPanel();
		mastertablevaluesleft = new JPanel();
		mastertablevaluesright = new JPanel();
		dummypanel = new JPanel();
		updatemastersouth = new JPanel();
		updatemastersouth.setLayout(new BorderLayout());
		updatemastertotal = new JPanel();
		updatemasternorth = new JPanel();
		primarytablevalues = new JPanel();
		primarytablevalues.setBorder(BorderFactory.createEtchedBorder());
		primarytablevaluestotal = new JPanel();
		primarytablevaluestotal.setLayout(new BorderLayout());
		primarytablevaluestotal.add(dummypanel, BorderLayout.EAST);
		primarytablevaluestotal.add(primarytablevalues, BorderLayout.WEST);
		updatemastersouth.add(primarytablevaluestotal, BorderLayout.NORTH);
		mastertablevalues.setBorder(BorderFactory.createEtchedBorder());
		mastertablevalues.setLayout(new BorderLayout());
		mastertablevalues.add(mastertablevaluesleft, BorderLayout.WEST);
		mastertablevalues.add(mastertablevaluesright, BorderLayout.EAST);
		updatemastersouth.add(mastertablevalues, BorderLayout.CENTER);
		updatemastersouth.add(dummypanel, BorderLayout.SOUTH);
		updatemasternorth.setLayout(new BorderLayout());
		updatemastertotal.setLayout(new BorderLayout());
		updatemastertotal.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		primarytablevalues = selectfields(tab, primarykey, primarykeyvalue);

		if (this.actualstatus != status[1]) {
			fillfieldsmastertable(tab, primarykey, primarykeyvalue);
		}
		checkstatus(tab);
		updatemasternorth.add(browsepanel);
		updatemastertotal.add(updatemasternorth, BorderLayout.NORTH);
		updatemastertotal.add(updatemastersouth, BorderLayout.WEST);
		updatemasterview.add(updatemastertotal);
		p.add(updatemasterview, BorderLayout.NORTH);
		tabbedPane.addTab(tab, null, p);
		diablebrowsepanel(primarykeyvalue, tab, primarykey);
	}

	private void fillfieldsmastertable(String tab, String primarykey, String primarykeyvalue) {
		mastertablevaluesleft.removeAll();
		String[][] mastertablekey = new String[fieldname.length][2];
		String id = "id";
		String mastertable = null;
		Vector<String> fieldlabel = new Vector<String>();
		Vector<String> fieldvalue = new Vector<String>();
		int indexofid;
		for (int j = 0; j <= (fieldname.length - 1); j++) {
			strfieldname = fieldname[j].getText().trim();
			indexofid = strfieldname.indexOf(id);
			if ((!primarykey.trim().equalsIgnoreCase(strfieldname)) && (indexofid > -1)
					&& (id.length() + indexofid == strfieldname.length())) {
				mastertablekey[j][0] = strfieldname;
				mastertablekey[j][1] = tablefield[j].getText().trim();
			}
		}
		for (int j = 0; j <= (mastertablekey.length - 1); j++) {
			if (mastertablekey[j][0] != null) {
				query = "describe ";
				querybuffer = new StringBuffer(query);
				querybuffer.append(mastertablekey[j][0].substring(0, mastertablekey[j][0].indexOf(id)));
				int numberoffields = -1;
				odata = null;
				try {
					query = querybuffer.toString();
					odata = cc.jdbcconnection.returnData(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				numberoffields = odata.length;
				query = "select * from ";
				querybuffer = new StringBuffer(query);
				querybuffer.append(mastertablekey[j][0].substring(0, mastertablekey[j][0].indexOf(id)));
				querybuffer.append(" where ");
				querybuffer.append(mastertablekey[j][0]);
				querybuffer.append(" = ");
				querybuffer.append(mastertablekey[j][1]);
				query = querybuffer.toString();
				odata2 = null;
				try {
					odata2 = cc.jdbcconnection.returnData(query);
				} catch (SQLException e) {

				}
				for (int i = 0; i <= (numberoffields - 1); i++) {
					if (odata[i][0] != null) {
						strfieldname = odata[i][0].toString().trim();
						indexofid = strfieldname.indexOf(id);
						if ((indexofid > -1) && (id.length() + indexofid == strfieldname.length())
								&& (!strfieldname.trim().equalsIgnoreCase(mastertablekey[j][0].trim()))) {
						} else {
							fieldlabel.add(odata[i][0].toString());
							if ((odata2 != null) && (odata2.length > 0) && (odata2[0][i] != null)) {
								fieldvalue.add(odata2[0][i].toString());
							} else {
								fieldvalue.add("");
							}
						}
					}
				}
			}
		}
		masterfieldname = new Label[fieldlabel.size()];
		mastertablefieldvalue = new TextField[fieldlabel.size()];
		open = new JButton[fieldlabel.size()];
		mastertablevaluesleft.setLayout(gridbaglayoutmastertable);
		gridbagcontraints.insets = new Insets(top, left, bottom, right);
		int count = 0;
		primarykeymastertable = new String[fieldlabel.size()];
		primarykeynamemastertable = new String[fieldlabel.size()];
		for (int i = 0; i < fieldlabel.size(); i++) {
			strfieldname = fieldlabel.elementAt(i).trim();
			indexofid = strfieldname.indexOf(id);
			if ((!primarykey.trim().equalsIgnoreCase(strfieldname)) && (indexofid > -1)
					&& (id.length() + indexofid == strfieldname.length())) {
				mastertable = strfieldname.substring(0, strfieldname.indexOf(id));
				open[count] = new JButton(mastertable);
				open[count].addActionListener(this);
				primarykeymastertable[count] = fieldvalue.elementAt(i).trim();
				primarykeynamemastertable[count] = strfieldname;
				gridbagcontraints.gridx = 0;
				gridbagcontraints.gridy = count;
				gridbagcontraints.gridwidth = 1;
				gridbagcontraints.gridheight = 1;
				gridbagcontraints.anchor = GridBagConstraints.WEST;
				gridbaglayoutmastertable.setConstraints(open[count], gridbagcontraints);
				mastertablevaluesleft.add(open[count]);
			} else {
				masterfieldname[count] = new Label(fieldlabel.elementAt(i));
				gridbagcontraints.gridx = 1;
				gridbagcontraints.gridy = count;
				gridbagcontraints.gridwidth = 1;
				gridbagcontraints.gridheight = 1;
				gridbagcontraints.anchor = GridBagConstraints.WEST;
				gridbaglayoutmastertable.setConstraints(masterfieldname[count], gridbagcontraints);
				mastertablevaluesleft.add(masterfieldname[count]);
				mastertablefieldvalue[count] = new TextField(fieldvalue.elementAt(i).trim());
				mastertablefieldvalue[count].setEnabled(false);
				mastertablefieldvalue[count].setBackground(new Color(204, 204, 204));
				gridbagcontraints.gridx = 2;
				gridbagcontraints.gridy = count;
				gridbagcontraints.gridwidth = 1;
				gridbagcontraints.gridheight = 1;
				gridbagcontraints.anchor = GridBagConstraints.WEST;
				gridbaglayoutmastertable.setConstraints(mastertablefieldvalue[count], gridbagcontraints);
				mastertablevaluesleft.add(mastertablefieldvalue[count]);
				count++;
			}
		}
		if (tab.trim().equalsIgnoreCase("experiment")) {
			btnallocation = new JButton("Allocation");
			btnallocation.addActionListener(this);
			gridbagcontraints.gridx = 0;
			gridbagcontraints.gridy = count;
			gridbagcontraints.gridwidth = 1;
			gridbagcontraints.gridheight = 1;
			gridbagcontraints.anchor = GridBagConstraints.WEST;
			gridbaglayoutmastertable.setConstraints(btnallocation, gridbagcontraints);
			mastertablevaluesleft.add(btnallocation);
			count++;
			btncreatematrix = new JButton("Create matrix");
			btncreatematrix.addActionListener(this);
			gridbagcontraints.gridx = 0;
			gridbagcontraints.gridy = count;
			gridbagcontraints.gridwidth = 1;
			gridbagcontraints.gridheight = 1;
			gridbagcontraints.anchor = GridBagConstraints.WEST;
			gridbaglayoutmastertable.setConstraints(btncreatematrix, gridbagcontraints);
			mastertablevaluesleft.add(btncreatematrix);
		}
	}

	private void checkstatus(String tab) {
		if (actualstatus == status[0]) {// DISPLAY
			updatemasternorth.removeAll();
			updatemasternorth.add(updatepanel, BorderLayout.WEST);
			disablefields();
		} else if (actualstatus == status[1]) {// NEW
			updatemasternorth.removeAll();
			updatemasternorth.add(panel_OK_Cancel, BorderLayout.WEST);
			tabbedPane.setEnabled(false);
		} else if (actualstatus == status[2]) {// UPDATE
			updatemasternorth.removeAll();
			updatemasternorth.add(panel_OK_Cancel, BorderLayout.WEST);
			tabbedPane.setEnabled(false);
		} else if (actualstatus == status[3]) {// DELETE
			updatemasternorth.removeAll();
			updatemasternorth.add(panel_OK_Cancel, BorderLayout.WEST);
			tabbedPane.setEnabled(false);
			disablefields();
		} else {
			;
		}
	}

	private void diablebrowsepanel(String primarykeyvalue, String tab, String primarykey) {
		query = "select min(";
		querybuffer = new StringBuffer(query);
		querybuffer.append(primarykey);
		querybuffer.append(") from ");
		querybuffer.append(tab);
		query = querybuffer.toString();
		try {
			odata_min = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		minvalue = (String) odata_min[0][0];
		if (primarykeyvalue.trim().equals(minvalue.trim())) {
			firstrecord = true;
		} else {
			firstrecord = false;
		}
		query = "select max(";
		querybuffer = new StringBuffer(query);
		querybuffer.append(primarykey);
		querybuffer.append(") from ");
		querybuffer.append(tab);
		query = querybuffer.toString();
		try {
			odata_max = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		maxvalue = (String) odata_max[0][0];
		if (primarykeyvalue.trim().equals(maxvalue.trim())) {
			lastrecord = true;
		} else {
			lastrecord = false;
		}

		if (actualstatus == status[0]) {
			btnFirst.setEnabled(!firstrecord);
			btnPrevious.setEnabled(!firstrecord);
			btnNext.setEnabled(!lastrecord);
			btnLast.setEnabled(!lastrecord);
		} else {
			btnFirst.setEnabled(false);
			btnPrevious.setEnabled(false);
			btnNext.setEnabled(false);
			btnLast.setEnabled(false);
			btnrefresh.setEnabled(false);
			for (int i = 0; i < open.length; i++) {
				if (open[i] != null) {
					open[i].setEnabled(false);
				}
			}
		}
	}

	private void disablefields() {
		Object[] components = primarytablevalues.getComponents();
		Vector<TextField> databasefields = new Vector<TextField>();
		Vector<JButton> zoekbuttons = new Vector<JButton>();

		for (int i = 0; i < components.length; i++) {
			if (components[i] instanceof TextField) {
				databasefields.add((TextField) components[i]);
			}
			if (components[i] instanceof JButton) {
				zoekbuttons.add((JButton) components[i]);
			}
		}
		for (int j = 0; j < databasefields.size(); j++) {
			((Component) databasefields.elementAt(j)).setEnabled(false);
			((Component) databasefields.elementAt(j)).setBackground(new Color(204, 204, 204));
		}
		for (int j = 0; j < zoekbuttons.size(); j++) {
			((Component) zoekbuttons.elementAt(j)).setEnabled(false);
		}
	}

	private JPanel selectfields(String TableName, String primarykey, String primarykeyvalue) {
		String tablefieldvalue;
		query = "describe ";
		querybuffer = new StringBuffer(query);
		querybuffer.append(TableName);
		int numberoffields = 0;
		odata = null;
		try {
			query = querybuffer.toString();
			odata = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		fieldname = new Label[odata.length];
		numberoffields = odata.length;
		tablefield = new TextField[odata.length];
		type = new String[odata.length];
		String[] primary_foreign_key = new String[odata.length];
		zoek = new JButton[odata.length];
		clear = new JButton[odata.length];
		btncalendar = new JButton[odata.length];
		zoektable = new String[odata.length];
		for (int i = 0; i <= (numberoffields - 1); i++) {
			fieldname[i] = new Label((String) odata[i][0]);
			type[i] = (String) odata[i][1];
			primary_foreign_key[i] = (String) odata[i][3];
		}
		query = "select * from  ";
		querybuffer = new StringBuffer(query);
		querybuffer.append(TableName);
		querybuffer.append(" where ");
		querybuffer.append(primarykey);
		querybuffer.append(" = ");
		querybuffer.append(primarykeyvalue);
		query = querybuffer.toString();
		if (this.actualstatus == status[1]) {
			for (int i = 0; i <= (odata.length - 1); i++) {
				odata[i][0] = null;
			}
		} else {
			try {
				if (!primarykeyvalue.trim().equals("")) {
					odata = cc.jdbcconnection.returnData(query);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		for (int i = 0; i <= (odata.length - 1); i++) {
			for (int j = 0; j <= (numberoffields - 1); j++) {
				if (this.actualstatus == status[1]) {
					tablefieldvalue = "";
				} else {
					tablefieldvalue = (String) odata[i][j];
				}
				int fieldlength = 0;
				fieldlength = Integer.parseInt(findNum(type[j]));
				if (type[j].trim().equals("date")) {
					fieldlength = 10;
				}
				if (tablefieldvalue == null) {
					tablefieldvalue = "";
				}
				if (fieldlength > 60) {
					fieldlength = 60;
				}
				if (fieldlength > 0) {
					tablefield[j] = new TextField(tablefieldvalue.trim(), fieldlength);
				} else {
					tablefield[j] = new TextField(tablefieldvalue.trim());
				}
			}
		}
		primarytablevalues.removeAll();
		primarytablevalues.setLayout(gridbaglayout);
		gridbagcontraints.insets = new Insets(top, left, bottom, right);
		for (int i = 0; i <= (numberoffields - 1); i++) {
			gridbagcontraints.gridx = 0;
			gridbagcontraints.gridy = i;
			gridbagcontraints.gridwidth = 1;
			gridbagcontraints.gridheight = 1;
			gridbagcontraints.anchor = GridBagConstraints.WEST;
			gridbaglayout.setConstraints(fieldname[i], gridbagcontraints);
			primarytablevalues.add(fieldname[i]);
			gridbagcontraints.gridx = 1;
			gridbagcontraints.gridy = i;
			gridbagcontraints.gridwidth = 1;
			gridbagcontraints.gridheight = 1;
			gridbagcontraints.anchor = GridBagConstraints.WEST;
			gridbaglayout.setConstraints(tablefield[i], gridbagcontraints);
			primarytablevalues.add(tablefield[i]);
			if (fieldname[i].getText().toLowerCase().indexOf("id") > -1) {
				if (fieldname[i].getText().toLowerCase()
						.substring(0, fieldname[i].getText().toLowerCase().indexOf("id")).length()
						+ 2 == fieldname[i].getText().toLowerCase().length()) {
					tablefield[i].setEnabled(false);
					tablefield[i].setBackground(new Color(204, 204, 204));
					if ((this.actualstatus == status[1])
							&& (primary_foreign_key[i].toLowerCase().indexOf("pri") > -1)) {
						query = "select max(";
						querybuffer = new StringBuffer(query);
						querybuffer.append(fieldname[i].getText().toLowerCase());
						querybuffer.append(") + 1 from ");
						querybuffer.append(TableName);
						query = querybuffer.toString();
						try {
							odata_new = cc.jdbcconnection.returnData(query);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						newvalue = (String) odata_new[0][0];
						tablefield[i].setText(newvalue);
					}
				}
			}
			if ((type[i].toLowerCase().indexOf("date") > -1) || (type[i].toLowerCase().indexOf("time") > -1)) {
				tablefield[i].setEnabled(false);
				tablefield[i].setBackground(new Color(204, 204, 204));
			}
			if ((fieldname[i].getText().toLowerCase().indexOf("id") > -1)
					&& (!(primary_foreign_key[i].toLowerCase().indexOf("pri") > -1))) {
				zoek[i] = new JButton("Search");
				zoek[i].addActionListener(this);
				clear[i] = new JButton("Clear");
				clear[i].addActionListener(this);
				zoektable[i] = fieldname[i].getText().toLowerCase().substring(0,
						fieldname[i].getText().toLowerCase().indexOf("id"));
				gridbagcontraints.gridx = 2;
				gridbagcontraints.gridy = i;
				gridbagcontraints.gridwidth = 1;
				gridbagcontraints.gridheight = 1;
				gridbagcontraints.anchor = GridBagConstraints.WEST;
				gridbaglayout.setConstraints(zoek[i], gridbagcontraints);
				primarytablevalues.add(zoek[i]);
				gridbagcontraints.gridx = 3;
				gridbagcontraints.gridy = i;
				gridbagcontraints.gridwidth = 1;
				gridbagcontraints.gridheight = 1;
				gridbagcontraints.anchor = GridBagConstraints.WEST;
				gridbaglayout.setConstraints(clear[i], gridbagcontraints);
				primarytablevalues.add(clear[i]);
			}

			if (type[i].toLowerCase().indexOf("date") > -1) {
				btncalendar[i] = new JButton("Calendar");
				btncalendar[i].addActionListener(this);
				gridbagcontraints.gridx = 2;
				gridbagcontraints.gridy = i;
				gridbagcontraints.gridwidth = 1;
				gridbagcontraints.gridheight = 1;
				gridbagcontraints.anchor = GridBagConstraints.WEST;
				gridbaglayout.setConstraints(btncalendar[i], gridbagcontraints);
				primarytablevalues.add(btncalendar[i]);
			}
		}
		return primarytablevalues;
	}

	public static String findNum(String str) {
		StringBuffer number = new StringBuffer("0");
		boolean numberfound = false;
		for (int i = 0; i < str.length(); i++) {
			if (num.indexOf(str.charAt(i)) != -1) {
				number.append(str.charAt(i));
				numberfound = true;
			} else {
				if (numberfound == true) {
					return number.toString();
				}
			}
		}
		return number.toString();
	}

	private void createslavePanes(String tab, String primarykey, String primarykeyvalue) {
		slavepanels[slavepanelcount] = new JPanel();
		slavepane[slavepanelcount] = new SlavePane(tab, primarykey, primarykeyvalue, slavepanels[slavepanelcount],
				tabbedPane, cc);
		slavepanelcount++;
	}

	private void deleterecord() {
		if (TableName.trim().equalsIgnoreCase("result")) {
			query = " select type, file, experimentid from result where " + primarykey + " = " + primarykeyvalue;
			odata_delete = null;
			try {
				odata_delete = cc.jdbcconnection.returnData(query);
			} catch (SQLException e) {
				if (debugmode) {
					e.printStackTrace();
				} else {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			if (odata_delete != null) {
				if ((String.valueOf(odata_delete[0][0]).trim().equalsIgnoreCase("fid"))
						|| (String.valueOf(odata_delete[0][0]).trim().equalsIgnoreCase("zero(fid)"))) {
					fidname = String.valueOf(odata_delete[0][1]).trim();
					searchstring = "_fid";
					indexof_fid = fidname.indexOf(searchstring);
					if (indexof_fid + searchstring.length() == fidname.length()) {
						query = " delete from result where (file = '" + fidname.substring(0, indexof_fid)
								+ "_acqus' or file = '" + fidname.substring(0, indexof_fid) + "_acqu') and"
								+ " experimentid = " + String.valueOf(odata_delete[0][2]).trim();
						try {
							rows = cc.jdbcconnection.update_table(query);
						} catch (SQLException e) {
							if (debugmode) {
								e.printStackTrace();
							} else {
								JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}
						}

					}
				}
			}
		}
		query = "delete from  " + TableName + " where ";
		querybuffer = new StringBuffer(query);
		querybuffer.append(primarykey);
		querybuffer.append(" = ");
		querybuffer.append(primarykeyvalue);
		try {
			rows = cc.jdbcconnection.update_table(querybuffer.toString());
		} catch (SQLException e) {
			if (debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		this.actualstatus = status[0];
		btnPrevious.setEnabled(true);
		btnPrevious.doClick();
	}

	private void insertrecord() {
		query = "insert into " + TableName + " (";
		querybuffer = new StringBuffer(query);
		recordtoupdate = false;
		for (int j = 0; j <= (fieldname.length - 1); j++) {
			if (!(fieldname[j].getText().trim().toLowerCase().equals(oldprimarykey.trim().toLowerCase()))) {
				if (recordtoupdate == true) {
					querybuffer.append(",");
				}
				querybuffer.append(fieldname[j].getText().trim());
				recordtoupdate = true;
			}
		}
		querybuffer.append(") values (");
		recordtoupdate = false;
		for (int j = 0; j <= (fieldname.length - 1); j++) {
			if (!(fieldname[j].getText().trim().toLowerCase().equals(oldprimarykey.trim().toLowerCase()))) {
				if (recordtoupdate == true) {
					querybuffer.append(",");
				}
				if (tablefield[j].getText().trim().equals("")) {
					querybuffer.append("null");
				} else {
					if ((type[j].indexOf("char") > -1) || (type[j].indexOf("date") > -1)
							|| (type[j].indexOf("time") > -1)) {
						querybuffer.append("'");
					}
					querybuffer.append(tablefield[j].getText().trim());
					if ((type[j].indexOf("char") > -1) || (type[j].indexOf("date") > -1)
							|| (type[j].indexOf("time") > -1)) {
						querybuffer.append("'");
					}
				}
				recordtoupdate = true;
			}
		}
		querybuffer.append(")");
		if (recordtoupdate == true) {
			try {
				rows = cc.jdbcconnection.update_table(querybuffer.toString());
			} catch (SQLException e) {
				if (debugmode) {
					e.printStackTrace();
				} else {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		query = "select max(";
		querybuffer = new StringBuffer(query);
		querybuffer.append(oldprimarykey.trim().toLowerCase());
		querybuffer.append(") from ");
		querybuffer.append(TableName);
		query = querybuffer.toString();
		try {
			odata_new = cc.jdbcconnection.returnData(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		oldprimarykeyvalue = odata_new[0][0].toString();
	}

	private void updaterecord() {
		if ((TableName != null) && (primarykey != null) && (primarykeyvalue != null)) {
			query = "update " + TableName + " set ";
			querybuffer = new StringBuffer(query);
			recordtoupdate = false;
			for (int j = 0; j <= (fieldname.length - 1); j++) {
				if (!fieldname[j].getText().trim().equalsIgnoreCase(primarykey.trim())) {
					if (fieldname[j].getText().toLowerCase().indexOf("id") > -1) {
						if (type[j].toLowerCase().indexOf("int") > -1) {
							if (tablefield[j].getText().trim().equals("")) {
								if (recordtoupdate == true) {
									querybuffer.append(" , ");
								}
								querybuffer.append(fieldname[j].getText().trim());
								querybuffer.append(" = null ");
								recordtoupdate = true;
							}
						}
					}
				}
				if (!fieldname[j].getText().trim().equalsIgnoreCase(primarykey.trim())
						&& (!(tablefield[j].getText().trim().equalsIgnoreCase("")))) {
					if (recordtoupdate == true) {
						querybuffer.append(" , ");
					}
					querybuffer.append(fieldname[j].getText().trim());
					querybuffer.append(" = ");
					if ((type[j].indexOf("char") > -1) || (type[j].indexOf("date") > -1)
							|| (type[j].indexOf("time") > -1)) {
						querybuffer.append("'");
					}
					querybuffer.append(tablefield[j].getText().trim());
					if ((type[j].indexOf("char") > -1) || (type[j].indexOf("date") > -1)
							|| (type[j].indexOf("time") > -1)) {
						querybuffer.append("'");
					}
					recordtoupdate = true;
				}
			}
			querybuffer.append(" where ");
			querybuffer.append(primarykey);
			querybuffer.append(" = ");
			querybuffer.append(primarykeyvalue);
			if (recordtoupdate == true) {
				try {
					rows = cc.jdbcconnection.update_table(querybuffer.toString());
				} catch (SQLException e) {
					if (debugmode) {
						e.printStackTrace();
					} else {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnallocation) {
			if (primarykeyvalue != null) {
				cc.selectexperimentAllocation(primarykeyvalue);
			}
		}
		if (evt.getSource() == btncreatematrix) {
			Creatematrix creatematrix = new Creatematrix(cc);
			cc.selectexperiment(primarykeyvalue);
		}
		if (evt.getSource() == btnOK) {
			message = "";
			if ((this.actualstatus == status[2]) || (this.actualstatus == status[1])) {
				message = "Are you sure to save data";
			}
			if (this.actualstatus == status[3]) {
				message = "Are you sure to delete this record";
			}
			int answer = JOptionPane.showConfirmDialog(cc.frame, message, "Select an Option",
					JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION) {
				if (this.actualstatus == status[2]) {
					updaterecord();
					this.actualstatus = status[0];// DISPLAY
					viewtablerecord(oldTableName, oldprimarykey, oldprimarykeyvalue);
				}
				if (this.actualstatus == status[1]) {
					insertrecord();
					this.actualstatus = status[0];// DISPLAY
					viewtablerecord(oldTableName, oldprimarykey, oldprimarykeyvalue);
				}
				if (this.actualstatus == status[3]) {
					if (!firstrecord) {
						deleterecord();
					} else {
						this.actualstatus = status[0];// DISPLAY
						viewtablerecord(oldTableName, oldprimarykey, oldprimarykeyvalue);
					}
				}
			} else {
				this.actualstatus = status[0];// DISPLAY
				viewtablerecord(oldTableName, oldprimarykey, oldprimarykeyvalue);
			}
		}

		if (evt.getSource() == btnCancel) {
			this.actualstatus = status[0];// DISPLAY
			viewtablerecord(oldTableName, oldprimarykey, oldprimarykeyvalue);
		}
		if ((evt.getSource() == btnNew) && (!TableName.equalsIgnoreCase("Result"))) {
			this.actualstatus = status[1];// NEW";
			viewtablerecord(TableName, "0", primarykeyvalue);
		}
		if ((evt.getSource() == btnUpdate) && (!TableName.equalsIgnoreCase("Result"))) {
			this.actualstatus = status[2];// UPDATE";
			viewtablerecord(TableName, primarykey, primarykeyvalue);
		}
		if (evt.getSource() == btnDelete) {
			this.actualstatus = status[3];// DELETE";
			viewtablerecord(TableName, primarykey, primarykeyvalue);
		}
		if (evt.getSource() == btnrefresh) {
			viewtablerecord(TableName, primarykey, primarykeyvalue);
		}
		if (evt.getSource() == btnNext) {
			query = "select ";
			querybuffer = new StringBuffer(query);
			querybuffer.append(primarykey);
			querybuffer.append(" from ");
			querybuffer.append(TableName);
			querybuffer.append(" where ");
			querybuffer.append(primarykey);
			querybuffer.append(" > ");
			querybuffer.append(primarykeyvalue);
			querybuffer.append(" order by ");
			querybuffer.append(primarykey);
			querybuffer.append(" limit 0,1 ");
			query = querybuffer.toString();
			try {
				query = querybuffer.toString();
				odata = cc.jdbcconnection.returnData(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (odata.length > 0) {
				primarykeyvalue = (String) odata[0][0];
				viewtablerecord(TableName, primarykey, primarykeyvalue);
			}
		}
		if (evt.getSource() == btnFirst) {
			query = "select min(";
			querybuffer = new StringBuffer(query);
			querybuffer.append(primarykey);
			querybuffer.append(") from ");
			querybuffer.append(TableName);
			query = querybuffer.toString();
			try {
				query = querybuffer.toString();
				odata = cc.jdbcconnection.returnData(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (odata.length > 0) {
				primarykeyvalue = (String) odata[0][0];
				viewtablerecord(TableName, primarykey, primarykeyvalue);
			}
		}
		if (evt.getSource() == btnPrevious) {
			query = "select ";
			querybuffer = new StringBuffer(query);
			querybuffer.append(primarykey);
			querybuffer.append(" from ");
			querybuffer.append(TableName);
			querybuffer.append(" where ");
			querybuffer.append(primarykey);
			querybuffer.append(" < ");
			querybuffer.append(primarykeyvalue);
			querybuffer.append(" order by ");
			querybuffer.append(primarykey);
			querybuffer.append(" desc limit 0,1 ");
			query = querybuffer.toString();
			try {
				query = querybuffer.toString();
				odata = cc.jdbcconnection.returnData(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (odata.length > 0) {
				primarykeyvalue = (String) odata[0][0];
				viewtablerecord(TableName, primarykey, primarykeyvalue);
			}
		}
		if (evt.getSource() == btnLast) {
			query = "select max(";
			querybuffer = new StringBuffer(query);
			querybuffer.append(primarykey);
			querybuffer.append(") from ");
			querybuffer.append(TableName);
			query = querybuffer.toString();
			try {
				query = querybuffer.toString();
				odata = cc.jdbcconnection.returnData(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (odata.length > 0) {
				primarykeyvalue = (String) odata[0][0];
				viewtablerecord(TableName, primarykey, primarykeyvalue);
			}
		}
		for (int i = 0; i < open.length; i++) {
			if (evt.getSource() == open[i]) {
				if (!primarykeymastertable[i].trim().equals("")) {
					viewtablerecord(open[i].getText(), primarykeynamemastertable[i], primarykeymastertable[i]);
				}
			}
		}
		for (int i = 0; i < zoek.length; i++) {
			if (evt.getSource() == zoek[i]) {
				cc.updatefield = tablefield[i];
				dialogtitle = "Search " + zoektable[i];
				cc.searchtablerecorddialog = new JDialog(cc.frame, dialogtitle);
				searchtablerecordpanel = new JPanel();
				cc.searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
				searchselection = new SearchSelection(this.cc, cc.searchtablerecorddialog, searchtablerecordpanel);
				wherestring = "";
				wherestring = returnwheresringsearch(TableName, zoektable[i]);
				searchselection.selectbrowser(zoektable[i], wherestring);
				cc.searchtablerecorddialog.setSize(900, 700);
				cc.searchtablerecorddialog.setVisible(true);
			}
			if (evt.getSource() == clear[i]) {
				tablefield[i].setText("");
			}
		}

		for (int i = 0; i < btncalendar.length; i++) {
			if (evt.getSource() == btncalendar[i]) {
				calendarframe = new JFrame("Calendar");
				calendar = new JCalendar(calendarframe, tablefield[i]);
				calendarframe.getContentPane().add(calendar);
				calendarframe.pack();
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				calendarframe.setLocation((d.width - calendarframe.getSize().width) / 2,
						(d.height - calendarframe.getSize().height) / 2);
				calendarframe.setVisible(true);
			}
		}
	}

	private String returnwheresringsearch(String targettable, String searchtble) {
		wherestringsearch = "";
		if (searchtble.equals("type")) {
			wherestringsearch = " where TableName = '" + targettable.trim() + "' ";
		}
		return wherestringsearch;
	}

}
