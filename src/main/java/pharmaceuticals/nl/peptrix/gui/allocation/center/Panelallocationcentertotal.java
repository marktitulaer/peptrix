package pharmaceuticals.nl.peptrix.gui.allocation.center;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Panelallocationcentertotal {
    ScrollPane scrollallocationcenter;
    Controller cc;
    JPanel allocationcenterpaneltotal;
    JPanel dummypanel;
    public JPanel allocationcenterpanelsouth;
    Allocationcenterpanelsouth centerpanelsouthallocation;
    Object[][] odatasample;
    Object[][] odatagroup;
    String TableName;
    String primarykey;
    String primarykeyvalue;

    public Panelallocationcentertotal(Controller cc) {
        this.cc = cc;
        scrollallocationcenter = new ScrollPane();
        dummypanel = new JPanel();
        dummypanel.setBackground(cc.color_empty_frames);
        JPanel dummypanel2 = new JPanel(new BorderLayout());
        JPanel dummypanel3 = new JPanel();
        dummypanel3.setBackground(cc.colorgrey);
        centerpanelsouthallocation = new Allocationcenterpanelsouth(cc);
        allocationcenterpanelsouth = centerpanelsouthallocation.getpanel();
        dummypanel2.add(allocationcenterpanelsouth, BorderLayout.WEST);
        dummypanel2.add(dummypanel3, BorderLayout.CENTER);
        allocationcenterpaneltotal = new JPanel();
        allocationcenterpaneltotal.setLayout(new BorderLayout());
        allocationcenterpaneltotal.add(dummypanel2, BorderLayout.NORTH);
        allocationcenterpaneltotal.add(dummypanel, BorderLayout.CENTER);
        scrollallocationcenter.add(allocationcenterpaneltotal);
    }

    public void resetscrollposition() {
        scrollallocationcenter.setScrollPosition(0, 0);
    }

    public JPanel getpanel() {
        return allocationcenterpaneltotal;
    }

    public ScrollPane getscrollPane() {
        return scrollallocationcenter;
    }

    public void fill_panels_with_fields() {
        dummypanel.setBackground(cc.colorgrey);
        allocationcenterpanelsouth.setBackground(cc.colorgrey);
        allocationcenterpanelsouth.removeAll();
        fill_panels_with_fields2();
    }

    public void fill_panels_with_fields2() {
        JPanel jpanel = centerpanelsouthallocation.getpanel();
        int datalength = cc.odata_allocation.length;
        jpanel.setBackground(cc.colorgrey);
        jpanel.removeAll();
        TextField[] Filename;
        JLabel samplelabel;
        JLabel grouplabel;
        JLabel groupnamelabel;
        JLabel Offset_LC_MS_label;
        if (datalength > 0) {
            GridBagLayout gridbagresults = new GridBagLayout();
            jpanel.setLayout(gridbagresults);
            GridBagConstraints constraintsresults = new GridBagConstraints();
            constraintsresults.anchor = GridBagConstraints.FIRST_LINE_START;
            constraintsresults.insets = new Insets(2, 2, 2, 2);
            Filename = new TextField[datalength];
            cc.Sampleid = new TextField[datalength];
            cc.Groupid = new TextField[datalength];
            cc.Group_code = new TextField[datalength];
            cc.Sample_code = new TextField[datalength];
            cc.Sample_name = new TextField[datalength];
            cc.Group_name = new TextField[datalength];
            cc.Offset_LC_MS = new TextField[datalength];
            cc.checkbox = new Checkbox[datalength];
            cc.searchsample = new JButton[datalength];
            cc.clearsample = new JButton[datalength];
            cc.searchgroup = new JButton[datalength];
            cc.cleargroup = new JButton[datalength];
            cc.newsampleid = new String[datalength];
            cc.newgroupid = new String[datalength];
            cc.resultid = new String[datalength];
            cc.oldsamplecode = new String[datalength];
            cc.oldgroupcode = new String[datalength];
            cc.oldsampleid = new String[datalength];
            cc.oldgroupid = new String[datalength];
            cc.old_offset_LC_MS = new double[datalength];
            Object[] clmHeaders = null;
            try {
                clmHeaders = cc.jdbcconnection.returnHeaders();
            } catch (SQLException e) {
                if (cc.debugmode) {
                    e.printStackTrace();
                } else {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            JLabel[] Tableheader = new JLabel[clmHeaders.length];
            String displayedsamplecode = "";
            for (int y = 0; y <= (clmHeaders.length - 1); y++) {
                if (y == 0) {
                    cc.checkboxall = new Checkbox("All");
                    cc.checkboxall.addItemListener(cc);
                    Font checkFont = new Font("Dialog", Font.BOLD, 12);
                    cc.checkboxall.setFont(checkFont);
                    constraintsresults.gridx = 0;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(cc.checkboxall, constraintsresults);
                    jpanel.add(cc.checkboxall);
                    for (int i = 0; i <= datalength - 1; i++) {
                        cc.checkbox[i] = new Checkbox();
                        constraintsresults.gridx = 0;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.checkbox[i], constraintsresults);
                        jpanel.add(cc.checkbox[i]);
                    }
                    Tableheader[y] = new JLabel(clmHeaders[y].toString());
                    constraintsresults.gridx = y + 1;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(Tableheader[y], constraintsresults);
                    jpanel.add(Tableheader[y]);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        Filename[i] = new TextField(30);
                        Filename[i].setText(cc.odata_allocation[i][y].toString());
                        constraintsresults.gridx = y + 1;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(Filename[i], constraintsresults);
                        jpanel.add(Filename[i]);
                    }
                }
                if (y == 1) {
                    Tableheader[y] = new JLabel(clmHeaders[y].toString());
                    constraintsresults.gridx = y + 1;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(Tableheader[y], constraintsresults);
                    jpanel.add(Tableheader[y]);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.Sampleid[i] = new TextField(10);
                        cc.Sampleid[i].setEditable(false);
                        cc.Sampleid[i].addMouseListener(cc);
                        cc.Sampleid[i].setBackground(cc.colorgrey);
                        if (cc.odata_allocation[i][y] != null) {
                            cc.Sampleid[i].setText(cc.odata_allocation[i][y].toString());
                            cc.oldsampleid[i] = cc.odata_allocation[i][y].toString();
                        }
                        constraintsresults.gridx = y + 1;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.Sampleid[i], constraintsresults);
                        jpanel.add(cc.Sampleid[i]);
                    }
                }
                if (y == 2) {
                    samplelabel = new JLabel("Sample");
                    constraintsresults.gridx = y + 2;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(samplelabel, constraintsresults);
                    jpanel.add(samplelabel);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.searchsample[i] = new JButton("Search");
                        cc.searchsample[i].addActionListener(cc);
                        constraintsresults.gridx = y + 2;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.searchsample[i], constraintsresults);
                        jpanel.add(cc.searchsample[i]);
                    }
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.clearsample[i] = new JButton("Clear");
                        cc.clearsample[i].addActionListener(cc);
                        constraintsresults.gridx = y + 3;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.clearsample[i], constraintsresults);
                        jpanel.add(cc.clearsample[i]);
                    }
                    Tableheader[y] = new JLabel(clmHeaders[y].toString());
                    constraintsresults.gridx = y + 4;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(Tableheader[y], constraintsresults);
                    jpanel.add(Tableheader[y]);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.Sample_code[i] = new TextField(8);
                        cc.Sample_code[i].setBackground(Color.YELLOW);
                        if (cc.odata_allocation[i][y] != null) {
                            displayedsamplecode = cc.odata_allocation[i][y].toString();
                            cc.Sample_code[i].setText(cc.odata_allocation[i][y].toString());
                            cc.oldsamplecode[i] = cc.odata_allocation[i][y].toString();
                        }
                        constraintsresults.gridx = y + 4;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.Sample_code[i], constraintsresults);
                        jpanel.add(cc.Sample_code[i]);
                    }
                    cc.displayedsamplecode = displayedsamplecode;
                }
                if (y == 3) {
                    samplelabel = new JLabel("Sample_Name");
                    constraintsresults.gridx = y + 4;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(samplelabel, constraintsresults);
                    jpanel.add(samplelabel);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.Sample_name[i] = new TextField(15);
                        if (cc.odata_allocation[i][y] != null) {
                            cc.Sample_name[i].setText(cc.odata_allocation[i][y].toString());
                        }
                        if (!cc.Sampleid[i].getText().trim().equalsIgnoreCase("")) {
                            cc.Sample_name[i].setBackground(cc.colorgrey);
                        }
                        constraintsresults.gridx = y + 4;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.Sample_name[i], constraintsresults);
                        jpanel.add(cc.Sample_name[i]);
                    }
                }
                if (y == 4) {
                    Tableheader[y] = new JLabel(clmHeaders[y].toString());
                    constraintsresults.gridx = y + 4;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(Tableheader[y], constraintsresults);
                    jpanel.add(Tableheader[y]);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.Groupid[i] = new TextField(10);
                        cc.Groupid[i].setEditable(false);
                        cc.Groupid[i].addMouseListener(cc);
                        cc.Groupid[i].setBackground(cc.colorgrey);
                        if (cc.odata_allocation[i][y] != null) {
                            cc.Groupid[i].setText(cc.odata_allocation[i][y].toString());
                            cc.oldgroupid[i] = cc.odata_allocation[i][y].toString();
                        }
                        constraintsresults.gridx = y + 4;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.Groupid[i], constraintsresults);
                        jpanel.add(cc.Groupid[i]);
                    }
                }
                if (y == 5) {
                    grouplabel = new JLabel("Group");
                    constraintsresults.gridx = y + 4;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(grouplabel, constraintsresults);
                    jpanel.add(grouplabel);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.searchgroup[i] = new JButton("Search");
                        cc.searchgroup[i].addActionListener(cc);
                        constraintsresults.gridx = y + 4;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.searchgroup[i], constraintsresults);
                        jpanel.add(cc.searchgroup[i]);
                    }
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.cleargroup[i] = new JButton("Clear");
                        cc.cleargroup[i].addActionListener(cc);
                        constraintsresults.gridx = y + 5;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.cleargroup[i], constraintsresults);
                        jpanel.add(cc.cleargroup[i]);
                    }
                    Tableheader[y] = new JLabel(clmHeaders[y].toString());
                    constraintsresults.gridx = y + 6;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(Tableheader[y], constraintsresults);
                    jpanel.add(Tableheader[y]);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.Group_code[i] = new TextField(8);
                        cc.Group_code[i].setBackground(Color.YELLOW);
                        if (cc.odata_allocation[i][y] != null) {
                            cc.Group_code[i].setText(cc.odata_allocation[i][y].toString());
                            cc.oldgroupcode[i] = cc.odata_allocation[i][y].toString();
                        }
                        constraintsresults.gridx = y + 6;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.Group_code[i], constraintsresults);
                        jpanel.add(cc.Group_code[i]);
                    }
                }
                if (y == 6) {
                    groupnamelabel = new JLabel("Group_Name");
                    constraintsresults.gridx = y + 6;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(groupnamelabel, constraintsresults);
                    jpanel.add(groupnamelabel);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.Group_name[i] = new TextField(15);
                        if (cc.odata_allocation[i][y] != null) {
                            cc.Group_name[i].setText(cc.odata_allocation[i][y].toString());
                        }
                        if (!cc.Groupid[i].getText().trim().equalsIgnoreCase("")) {
                            cc.Group_name[i].setBackground(cc.colorgrey);
                        }
                        constraintsresults.gridx = y + 6;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.Group_name[i], constraintsresults);
                        jpanel.add(cc.Group_name[i]);
                    }
                }
                if (y == 7) {
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.resultid[i] = cc.odata_allocation[i][y].toString();
                    }
                }
                if (y == 8) {
                    Offset_LC_MS_label = new JLabel("Offset LC MS (+ or -)");
                    constraintsresults.gridx = y + 6;
                    constraintsresults.gridy = 0;
                    constraintsresults.gridwidth = 1;
                    constraintsresults.gridheight = 1;
                    gridbagresults.setConstraints(Offset_LC_MS_label, constraintsresults);
                    jpanel.add(Offset_LC_MS_label);
                    for (int i = 0; i <= (datalength - 1); i++) {
                        cc.Offset_LC_MS[i] = new TextField(15);
                        if (cc.odata_allocation[i][y] != null) {
                            cc.Offset_LC_MS[i].setText("" + cc.odata_allocation[i][y].toString());
                            try {
                                cc.old_offset_LC_MS[i] = Double.parseDouble(cc.odata_allocation[i][y].toString());
                            } catch (Exception ex) {
                            }
                        }
                        constraintsresults.gridx = y + 6;
                        constraintsresults.gridy = i + 1;
                        constraintsresults.gridwidth = 1;
                        constraintsresults.gridheight = 1;
                        gridbagresults.setConstraints(cc.Offset_LC_MS[i], constraintsresults);
                        jpanel.add(cc.Offset_LC_MS[i]);
                    }
                }
            }
        }
    }

    public void update_lc_ms_offset(int datalength) {
        for (int i = 0; i <= (datalength - 1); i++) {
            cc.double_offset_LC_MS = 0;
            try {
                cc.double_offset_LC_MS = Double.parseDouble(cc.Offset_LC_MS[i].getText().trim());
            } catch (Exception e) {
                ;
            }
            if (cc.old_offset_LC_MS[i] != cc.double_offset_LC_MS) {
                cc.strquery = "update result set Offset_LC_MS = " + String.valueOf(0 + cc.double_offset_LC_MS)
                        + " where resultid = " + cc.resultid[i].trim();
                try {
                    cc.updategroup = cc.jdbcconnection.update_table(cc.strquery);
                } catch (SQLException e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    public void ChangeditemState(ItemEvent event, int datalength) {
        if (event.getSource() == cc.checkboxall) {
            for (int i = 0; i <= (datalength - 1); i++) {
                cc.checkbox[i].setState(cc.checkboxall.getState());
            }
        }
    }

    public void Clickedmouse(MouseEvent evt, int datalength) {
        if (evt.getClickCount() == 2) {
            for (int i = 0; i <= (datalength - 1); i++) {
                if (evt.getSource() == cc.Sampleid[i]) {
                    if (!(cc.Sampleid[i].getText().trim().equals(""))) {
                        TableName = "Sample";
                        primarykey = "Sampleid";
                        primarykeyvalue = cc.Sampleid[i].getText().trim();
                        cc.PerformActionObjectAllocation();
                    }
                }
                if (evt.getSource() == cc.Groupid[i]) {
                    if (!(cc.Groupid[i].getText().trim().equals(""))) {
                        TableName = "Group_";
                        primarykey = "Group_id";
                        primarykeyvalue = cc.Groupid[i].getText().trim();
                        cc.PerformActionObjectAllocation();
                    }
                }
            }
        }
    }
}
