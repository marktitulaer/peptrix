package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.*;

import pharmaceuticals.nl.peptrix.Controller;

public class SearchSelection implements ActionListener, MouseListener, PropertyChangeListener {
    TableColumn col;
    Dimension fill;
    Box.Filler vFill;
    Controller cc;
    DefaultTableModel model = new DefaultTableModel();
    BorderLayout borderlayout = new BorderLayout();
    GridBagLayout gridbaglayout1 = new GridBagLayout();
    GridBagConstraints gbc1 = new GridBagConstraints();
    GridBagLayout gridbaglayout2 = new GridBagLayout();
    GridBagConstraints gbc2 = new GridBagConstraints();
    JFrame frame;
    JPanel pLeft;
    JPanel pTop;
    JPanel pBody;
    JPanel browsepanel;
    JPanel panelnorth;
    JPanel panelsouth;
    JPanel bodybottempanel = new JPanel();
    JPanel searchtablerecordpanel;
    JPanel browseinfo;
    JPanel toppanel;
    ScrollPane panelcenter;
    JScrollPane browserpane;
    JButton btnOKsearch = null;
    JButton btnClearsearch = null;
    JButton btnNext;
    JButton btnFirst;
    JButton btnrefresh;
    JButton btnPrevious;
    JButton btnLast;
    JButton[] searchforeignkey;
    JButton[] searchforeignkey2;
    JTable table = null;
    TableSorter sorter = null;
    JDialog searchtablerecorddialog = null;
    TextField[] dummy1;
    TextField[] dummy2;
    ResultSetTableModel resultsettablemodel;
    FlowLayout layoutMgr;
    SearchSelection searchselection;
    JOptionPane optionsearch;
    Object object = null;
    Object[] clmHeaders;
    Object[][] odata;
    Object[][] odata2;
    Object[][] maxnumberofrecords;
    JLabel labelnumberofrecords;
    Label[] fieldname;
    public String TableName;
    String TableNameold;
    public String primarykey = "";
    public String primarykeyvalue;
    String id;
    String dialogtitle;
    String wherestring;
    String prop;
    String stroffset;
    String strlimit;
    String query;
    String querymaxnumberrecords;
    String[] dummy1old;
    String[] dummy2old;
    String[] strSearch = {"=", ">=", ">", "<=", "<", "<>", "pattern"};
    String[] and_or = {"and", "or"};
    StringBuffer querybuffermaxnumberrecords;
    StringBuffer querybuffer;
    JComboBox sqlSearch0 = null;
    JComboBox sqlSearch1 = null;
    JComboBox sqlSearch2 = null;
    JComboBox sqlSearch3 = null;
    JComboBox sqlSearch4 = null;
    JComboBox sqlSearch5 = null;
    JComboBox sqlSearch6 = null;
    JComboBox sqlSearch7 = null;
    JComboBox sqlSearch8 = null;
    JComboBox sqlSearch9 = null;
    JComboBox sqlSearch10 = null;
    JComboBox sqlSearch11 = null;
    JComboBox sqlSearch12 = null;
    JComboBox sqlSearch13 = null;
    JComboBox sqlSearch14 = null;
    JComboBox sqlSearch15 = null;
    JComboBox sqlSearch16 = null;
    JComboBox sqlSearch17 = null;
    JComboBox sqlSearch18 = null;
    JComboBox sqlSearch19 = null;
    JComboBox sqlSearch20 = null;
    JComboBox sqlSearch21 = null;
    JComboBox sqlSearch22 = null;
    JComboBox sqlSearch23 = null;
    JComboBox sqlSearch24 = null;
    JComboBox sqlSearch25 = null;
    JComboBox sqlSearch26 = null;
    JComboBox sqlSearch27 = null;
    JComboBox sqlSearch28 = null;
    JComboBox sqlSearch29 = null;
    JComboBox sqlSearch30 = null;
    JComboBox and_or0 = null;
    JComboBox and_or1 = null;
    JComboBox and_or2 = null;
    JComboBox and_or3 = null;
    JComboBox and_or4 = null;
    JComboBox and_or5 = null;
    JComboBox and_or6 = null;
    JComboBox and_or7 = null;
    JComboBox and_or8 = null;
    JComboBox and_or9 = null;
    JComboBox and_or10 = null;
    JComboBox and_or11 = null;
    JComboBox and_or12 = null;
    JComboBox and_or13 = null;
    JComboBox and_or14 = null;
    JComboBox and_or15 = null;
    JComboBox and_or16 = null;
    JComboBox and_or17 = null;
    JComboBox and_or18 = null;
    JComboBox and_or19 = null;
    JComboBox and_or20 = null;
    JComboBox and_or21 = null;
    JComboBox and_or22 = null;
    JComboBox and_or23 = null;
    JComboBox and_or24 = null;
    JComboBox and_or25 = null;
    JComboBox and_or26 = null;
    JComboBox and_or27 = null;
    JComboBox and_or28 = null;
    JComboBox and_or29 = null;
    JComboBox and_or30 = null;
    JComboBox secondSearch0 = null;
    JComboBox secondSearch1 = null;
    JComboBox secondSearch2 = null;
    JComboBox secondSearch3 = null;
    JComboBox secondSearch4 = null;
    JComboBox secondSearch5 = null;
    JComboBox secondSearch6 = null;
    JComboBox secondSearch7 = null;
    JComboBox secondSearch8 = null;
    JComboBox secondSearch9 = null;
    JComboBox secondSearch10 = null;
    JComboBox secondSearch11 = null;
    JComboBox secondSearch12 = null;
    JComboBox secondSearch13 = null;
    JComboBox secondSearch14 = null;
    JComboBox secondSearch15 = null;
    JComboBox secondSearch16 = null;
    JComboBox secondSearch17 = null;
    JComboBox secondSearch18 = null;
    JComboBox secondSearch19 = null;
    JComboBox secondSearch20 = null;
    JComboBox secondSearch21 = null;
    JComboBox secondSearch22 = null;
    JComboBox secondSearch23 = null;
    JComboBox secondSearch24 = null;
    JComboBox secondSearch25 = null;
    JComboBox secondSearch26 = null;
    JComboBox secondSearch27 = null;
    JComboBox secondSearch28 = null;
    JComboBox secondSearch29 = null;
    JComboBox secondSearch30 = null;
    boolean firstwindow;
    boolean lastwindow;
    boolean dialog = false;
    int offset = 0;
    int limit = 5000;
    int numbersofrows = 0;
    int indexofid;
    int intmaxnumberofrecords;
    int j;
    int k;
    int l;
    int m;
    int n;
    int o;
    int p;
    int q;
    int columnwidth = 200;
    Integer testint;

    public SearchSelection(Controller cc, Object object, JPanel body) {
        frame = new JFrame();
        pLeft = new JPanel();
        pTop = new JPanel();
        pBody = new JPanel();
        panelcenter = new ScrollPane();
        this.cc = cc;
        if (object != null) {
            this.object = object;
            if (object.getClass().isInstance(this.frame)) {
                this.frame = (JFrame) object;
            }
            if (object.getClass().toString().indexOf("JDialog") > -1) {
                this.dialog = true;
            }
        }
        this.pBody = body;
        panelsouth = new JPanel();
        panelsouth.setBackground(pBody.getBackground());
        BoxLayout boxlayout = new BoxLayout(panelsouth, BoxLayout.X_AXIS);
        panelsouth.setLayout(boxlayout);
        fill = new Dimension(50, 50);
        vFill = new Box.Filler(fill, fill, fill);
        panelsouth.add(vFill);
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
        browsepanel = new JPanel();
        layoutMgr = new FlowLayout(FlowLayout.LEFT, 2, 5);
        browsepanel.setLayout(layoutMgr);
        browsepanel.add(btnFirst);
        browsepanel.add(btnPrevious);
        browsepanel.add(btnrefresh);
        browsepanel.add(btnNext);
        browsepanel.add(btnLast);
        panelnorth = new JPanel();
        panelnorth.setLayout(new BorderLayout());
        labelnumberofrecords = new JLabel();
        browseinfo = new JPanel();
        toppanel = new JPanel();
        toppanel.setLayout(new BorderLayout());
    }

    public void selectbrowser(String tablename, String wherestring) {
        this.TableName = tablename;
        if (TableNameold != null) {
            if (!(TableName.trim().equalsIgnoreCase(TableNameold.trim()))) {
                offset = 0;
                dummy1old = null;
                dummy2old = null;
            }
        }
        TableNameold = this.TableName;
        browserpane = null;
        try {
            stroffset = String.valueOf(offset);
            strlimit = String.valueOf(limit);
            querymaxnumberrecords = "select count(*) from ";
            querybuffermaxnumberrecords = new StringBuffer(querymaxnumberrecords);
            querybuffermaxnumberrecords.append(tablename);
            if (!wherestring.trim().equals("")) {
                querybuffermaxnumberrecords.append(wherestring);
            }
            querymaxnumberrecords = querybuffermaxnumberrecords.toString();
            maxnumberofrecords = cc.jdbcconnection.returnData(querymaxnumberrecords);
            intmaxnumberofrecords = 0;
            if (maxnumberofrecords != null) {
                intmaxnumberofrecords = Integer.parseInt(maxnumberofrecords[0][0].toString());
            }
            query = "select * from ";
            querybuffer = new StringBuffer(query);
            querybuffer.append(tablename);
            if (!wherestring.trim().equals("")) {
                querybuffer.append(wherestring);
            }
            querybuffer.append(" order by ");
            querybuffer.append(tablename);
            querybuffer.append("id desc ");
            querybuffer.append(" limit ");
            querybuffer.append(strlimit);
            querybuffer.append(" offset ");
            querybuffer.append(stroffset);
            query = querybuffer.toString();
            cc.jdbcconnection.selectdata(query);
            if (resultsettablemodel == null) {
                resultsettablemodel = new ResultSetTableModel(cc.jdbcconnection);
            } else {
                resultsettablemodel.displayresults(cc.jdbcconnection);
            }
            if (sorter == null) {
                sorter = new TableSorter(resultsettablemodel);
            } else {
                sorter.setTableModel(resultsettablemodel);
            }
            if (table == null) {
                table = new JTable(sorter);
                table.addMouseListener(this);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.setPreferredScrollableViewportSize(new Dimension(100, 200));
            } else {
                table.setModel(sorter);
            }
            sorter.setTableHeader(table.getTableHeader());
            for (int i = 0; i <= (table.getColumnCount() - 1); i++) {
                col = table.getColumnModel().getColumn(i);
                col.setPreferredWidth(columnwidth);
            }
            browserpane = new JScrollPane(table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Selectfields(panelcenter, tablename);
        pBody.setLayout(borderlayout);
        pBody.removeAll();
        if (dialog == true) {
            searchtablerecorddialog = (JDialog) object;
            searchtablerecorddialog.repaint();
        } else if (frame != null) {
            frame.repaint();
        }
        disablebrowsebuttons();
        panelnorth.removeAll();
        browseinfo.removeAll();
        browseinfo.add(labelnumberofrecords);
        toppanel.removeAll();
        toppanel.add(browsepanel, BorderLayout.WEST);
        toppanel.add(browseinfo, BorderLayout.EAST);
        panelnorth.add(toppanel, BorderLayout.NORTH);
        panelnorth.add(browserpane, BorderLayout.SOUTH);
        pBody.add(panelnorth, BorderLayout.NORTH);
        pBody.add(panelcenter, BorderLayout.CENTER);
        if (!dialog) {
            pBody.add(panelsouth, BorderLayout.SOUTH);
        }
        if (dialog == true) {
            searchtablerecorddialog = (JDialog) object;
            searchtablerecorddialog.setVisible(true);
        } else if (frame != null) {
            labelnumberofrecords.setText("Number of selected records : " + String.valueOf(intmaxnumberofrecords));
            frame.setVisible(true);
        }
    }

    private void disablebrowsebuttons() {
        if (offset == 0) {
            btnFirst.setEnabled(false);
            btnPrevious.setEnabled(false);
        } else {
            btnFirst.setEnabled(true);
            btnPrevious.setEnabled(true);
        }
        if ((offset + limit) >= intmaxnumberofrecords) {
            btnNext.setEnabled(false);
            btnLast.setEnabled(false);
        } else {
            btnNext.setEnabled(true);
            btnLast.setEnabled(true);
        }
    }

    private void Selectfields(ScrollPane panelcenter, String tablename) {
        odata = null;
        btnOKsearch = new JButton("Search");
        btnClearsearch = new JButton("Clear");
        try {
            query = "describe ";
            querybuffer = new StringBuffer(query);
            querybuffer.append(tablename);
            query = querybuffer.toString();
            odata = cc.jdbcconnection.returnData(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fieldname = new Label[odata.length];
        dummy1 = new TextField[odata.length];
        dummy2 = new TextField[odata.length];
        searchforeignkey = new JButton[odata.length];
        searchforeignkey2 = new JButton[odata.length];
        gbc2.insets = new Insets(2, 2, 2, 2);
        bodybottempanel.removeAll();
        bodybottempanel.setLayout(gridbaglayout2);
        numbersofrows = odata.length;
        for (int i = 0; i <= (odata.length - 1); i++) {
            fieldname[i] = new Label((String) odata[i][0]);
            gbc2.gridx = 0;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            gridbaglayout2.setConstraints(fieldname[i], gbc2);
            bodybottempanel.add(fieldname[i]);
            gbc2.gridx = 1;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            switch (i) {
                case 0:
                    sqlSearch0 = new JComboBox(strSearch);
                    sqlSearch0.setSelectedIndex(0);
                    sqlSearch0.setName("sqlSearch");
                    sqlSearch0.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch0, gbc2);
                    break;
                case 1:
                    sqlSearch1 = new JComboBox(strSearch);
                    sqlSearch1.setSelectedIndex(0);
                    sqlSearch1.setName("sqlSearch");
                    sqlSearch1.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch1, gbc2);
                    break;
                case 2:
                    sqlSearch2 = new JComboBox(strSearch);
                    sqlSearch2.setSelectedIndex(0);
                    sqlSearch2.setName("sqlSearch");
                    sqlSearch2.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch2, gbc2);
                    break;
                case 3:
                    sqlSearch3 = new JComboBox(strSearch);
                    sqlSearch3.setSelectedIndex(0);
                    sqlSearch3.setName("sqlSearch");
                    sqlSearch3.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch3, gbc2);
                    break;
                case 4:
                    sqlSearch4 = new JComboBox(strSearch);
                    sqlSearch4.setSelectedIndex(0);
                    sqlSearch4.setName("sqlSearch");
                    sqlSearch4.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch4, gbc2);
                    break;
                case 5:
                    sqlSearch5 = new JComboBox(strSearch);
                    sqlSearch5.setSelectedIndex(0);
                    sqlSearch5.setName("sqlSearch");
                    sqlSearch5.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch5, gbc2);
                    break;
                case 6:
                    sqlSearch6 = new JComboBox(strSearch);
                    sqlSearch6.setSelectedIndex(0);
                    sqlSearch6.setName("sqlSearch");
                    sqlSearch6.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch6, gbc2);
                    break;
                case 7:
                    sqlSearch7 = new JComboBox(strSearch);
                    sqlSearch7.setSelectedIndex(0);
                    sqlSearch7.setName("sqlSearch");
                    sqlSearch7.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch7, gbc2);
                    break;
                case 8:
                    sqlSearch8 = new JComboBox(strSearch);
                    sqlSearch8.setSelectedIndex(0);
                    sqlSearch8.setName("sqlSearch");
                    sqlSearch8.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch8, gbc2);
                    break;
                case 9:
                    sqlSearch9 = new JComboBox(strSearch);
                    sqlSearch9.setSelectedIndex(0);
                    sqlSearch9.setName("sqlSearch");
                    sqlSearch9.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch9, gbc2);
                    break;
                case 10:
                    sqlSearch10 = new JComboBox(strSearch);
                    sqlSearch10.setSelectedIndex(0);
                    sqlSearch10.setName("sqlSearch");
                    sqlSearch10.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch10, gbc2);
                    break;
                case 11:
                    sqlSearch11 = new JComboBox(strSearch);
                    sqlSearch11.setSelectedIndex(0);
                    sqlSearch11.setName("sqlSearch");
                    sqlSearch11.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch11, gbc2);
                    break;
                case 12:
                    sqlSearch12 = new JComboBox(strSearch);
                    sqlSearch12.setSelectedIndex(0);
                    sqlSearch12.setName("sqlSearch");
                    sqlSearch12.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch12, gbc2);
                    break;
                case 13:
                    sqlSearch13 = new JComboBox(strSearch);
                    sqlSearch13.setSelectedIndex(0);
                    sqlSearch13.setName("sqlSearch");
                    sqlSearch13.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch13, gbc2);
                    break;
                case 14:
                    sqlSearch14 = new JComboBox(strSearch);
                    sqlSearch14.setSelectedIndex(0);
                    sqlSearch14.setName("sqlSearch");
                    sqlSearch14.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch14, gbc2);
                    break;
                case 15:
                    sqlSearch15 = new JComboBox(strSearch);
                    sqlSearch15.setSelectedIndex(0);
                    sqlSearch15.setName("sqlSearch");
                    sqlSearch15.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch15, gbc2);
                    break;
                case 16:
                    sqlSearch16 = new JComboBox(strSearch);
                    sqlSearch16.setSelectedIndex(0);
                    sqlSearch16.setName("sqlSearch");
                    sqlSearch16.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch16, gbc2);
                    break;
                case 17:
                    sqlSearch17 = new JComboBox(strSearch);
                    sqlSearch17.setSelectedIndex(0);
                    sqlSearch17.setName("sqlSearch");
                    sqlSearch17.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch17, gbc2);
                    break;
                case 18:
                    sqlSearch18 = new JComboBox(strSearch);
                    sqlSearch18.setSelectedIndex(0);
                    sqlSearch18.setName("sqlSearch");
                    sqlSearch18.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch18, gbc2);
                    break;
                case 19:
                    sqlSearch19 = new JComboBox(strSearch);
                    sqlSearch19.setSelectedIndex(0);
                    sqlSearch19.setName("sqlSearch");
                    sqlSearch19.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch19, gbc2);
                    break;
                case 20:
                    sqlSearch20 = new JComboBox(strSearch);
                    sqlSearch20.setSelectedIndex(0);
                    sqlSearch20.setName("sqlSearch");
                    sqlSearch20.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch20, gbc2);
                    break;
                case 21:
                    sqlSearch21 = new JComboBox(strSearch);
                    sqlSearch21.setSelectedIndex(0);
                    sqlSearch21.setName("sqlSearch");
                    sqlSearch21.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch21, gbc2);
                    break;
                case 22:
                    sqlSearch22 = new JComboBox(strSearch);
                    sqlSearch22.setSelectedIndex(0);
                    sqlSearch22.setName("sqlSearch");
                    sqlSearch22.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch22, gbc2);
                    break;
                case 23:
                    sqlSearch23 = new JComboBox(strSearch);
                    sqlSearch23.setSelectedIndex(0);
                    sqlSearch23.setName("sqlSearch");
                    sqlSearch23.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch23, gbc2);
                    break;
                case 24:
                    sqlSearch24 = new JComboBox(strSearch);
                    sqlSearch24.setSelectedIndex(0);
                    sqlSearch24.setName("sqlSearch");
                    sqlSearch24.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch24, gbc2);
                    break;
                case 25:
                    sqlSearch25 = new JComboBox(strSearch);
                    sqlSearch25.setSelectedIndex(0);
                    sqlSearch25.setName("sqlSearch");
                    sqlSearch25.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch25, gbc2);
                    break;
                case 26:
                    sqlSearch26 = new JComboBox(strSearch);
                    sqlSearch26.setSelectedIndex(0);
                    sqlSearch26.setName("sqlSearch");
                    sqlSearch26.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch26, gbc2);
                    break;
                case 27:
                    sqlSearch27 = new JComboBox(strSearch);
                    sqlSearch27.setSelectedIndex(0);
                    sqlSearch27.setName("sqlSearch");
                    sqlSearch27.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch27, gbc2);
                    break;
                case 28:
                    sqlSearch28 = new JComboBox(strSearch);
                    sqlSearch28.setSelectedIndex(0);
                    sqlSearch28.setName("sqlSearch");
                    sqlSearch28.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch28, gbc2);
                    break;
                case 29:
                    sqlSearch29 = new JComboBox(strSearch);
                    sqlSearch29.setSelectedIndex(0);
                    sqlSearch29.setName("sqlSearch");
                    sqlSearch29.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch29, gbc2);
                    break;
                case 30:
                    sqlSearch30 = new JComboBox(strSearch);
                    sqlSearch30.setSelectedIndex(0);
                    sqlSearch30.setName("sqlSearch");
                    sqlSearch30.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(sqlSearch30, gbc2);
                    break;
            }
            dummy1[i] = new TextField("", 20);
            dummy1[i].addActionListener(this);
            if (dummy1old != null) {
                if (!(dummy1old[i].trim().equals(""))) {
                    dummy1[i].setText(dummy1old[i].trim());
                }
            }
            gbc2.gridx = 3;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            gridbaglayout2.setConstraints(dummy1[i], gbc2);
            bodybottempanel.add(dummy1[i]);
            searchforeignkey[i] = new JButton("...");
            searchforeignkey[i].addActionListener(this);
            gbc2.gridx = 4;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            gridbaglayout2.setConstraints(searchforeignkey[i], gbc2);
            id = "id";
            indexofid = fieldname[i].getText().trim().indexOf(id);
            if ((indexofid > -1) && (id.length() + indexofid == fieldname[i].getText().trim().length())) {
                if (!(fieldname[i].getText().trim().substring(0, indexofid).equalsIgnoreCase(TableName.trim()))) {
                    bodybottempanel.add(searchforeignkey[i]);
                }
            }
            gbc2.gridx = 5;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            switch (i) {
                case 0:
                    and_or0 = new JComboBox(and_or);
                    and_or0.setSelectedIndex(0);
                    and_or0.setName("and_or");
                    and_or0.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or0, gbc2);
                    break;
                case 1:
                    and_or1 = new JComboBox(and_or);
                    and_or1.setSelectedIndex(0);
                    and_or1.setName("and_or");
                    and_or1.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or1, gbc2);
                    break;
                case 2:
                    and_or2 = new JComboBox(and_or);
                    and_or2.setSelectedIndex(0);
                    and_or2.setName("and_or");
                    and_or2.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or2, gbc2);
                    break;
                case 3:
                    and_or3 = new JComboBox(and_or);
                    and_or3.setSelectedIndex(0);
                    and_or3.setName("and_or");
                    and_or3.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or3, gbc2);
                    break;
                case 4:
                    and_or4 = new JComboBox(and_or);
                    and_or4.setSelectedIndex(0);
                    and_or4.setName("and_or");
                    and_or4.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or4, gbc2);
                    break;
                case 5:
                    and_or5 = new JComboBox(and_or);
                    and_or5.setSelectedIndex(0);
                    and_or5.setName("and_or");
                    and_or5.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or5, gbc2);
                    break;
                case 6:
                    and_or6 = new JComboBox(and_or);
                    and_or6.setSelectedIndex(0);
                    and_or6.setName("and_or");
                    and_or6.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or6, gbc2);
                    break;
                case 7:
                    and_or7 = new JComboBox(and_or);
                    and_or7.setSelectedIndex(0);
                    and_or7.setName("and_or");
                    and_or7.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or7, gbc2);
                    break;
                case 8:
                    and_or8 = new JComboBox(and_or);
                    and_or8.setSelectedIndex(0);
                    and_or8.setName("and_or");
                    and_or8.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or8, gbc2);
                    break;
                case 9:
                    and_or9 = new JComboBox(and_or);
                    and_or9.setSelectedIndex(0);
                    and_or9.setName("and_or");
                    and_or9.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or9, gbc2);
                    break;
                case 10:
                    and_or10 = new JComboBox(and_or);
                    and_or10.setSelectedIndex(0);
                    and_or10.setName("and_or");
                    and_or10.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or10, gbc2);
                    break;
                case 11:
                    and_or11 = new JComboBox(and_or);
                    and_or11.setSelectedIndex(0);
                    and_or11.setName("and_or");
                    and_or11.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or11, gbc2);
                    break;
                case 12:
                    and_or12 = new JComboBox(and_or);
                    and_or12.setSelectedIndex(0);
                    and_or12.setName("and_or");
                    and_or12.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or12, gbc2);
                    break;
                case 13:
                    and_or13 = new JComboBox(and_or);
                    and_or13.setSelectedIndex(0);
                    and_or13.setName("and_or");
                    and_or13.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or13, gbc2);
                    break;
                case 14:
                    and_or14 = new JComboBox(and_or);
                    and_or14.setSelectedIndex(0);
                    and_or14.setName("and_or");
                    and_or14.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or14, gbc2);
                    break;
                case 15:
                    and_or15 = new JComboBox(and_or);
                    and_or15.setSelectedIndex(0);
                    and_or15.setName("and_or");
                    and_or15.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or15, gbc2);
                    break;
                case 16:
                    and_or16 = new JComboBox(and_or);
                    and_or16.setSelectedIndex(0);
                    and_or16.setName("and_or");
                    and_or16.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or16, gbc2);
                    break;
                case 17:
                    and_or17 = new JComboBox(and_or);
                    and_or17.setSelectedIndex(0);
                    and_or17.setName("and_or");
                    and_or17.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or17, gbc2);
                    break;
                case 18:
                    and_or18 = new JComboBox(and_or);
                    and_or18.setSelectedIndex(0);
                    and_or18.setName("and_or");
                    and_or18.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or18, gbc2);
                    break;
                case 19:
                    and_or19 = new JComboBox(and_or);
                    and_or19.setSelectedIndex(0);
                    and_or19.setName("and_or");
                    and_or19.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or19, gbc2);
                    break;
                case 20:
                    and_or20 = new JComboBox(and_or);
                    and_or20.setSelectedIndex(0);
                    and_or20.setName("and_or");
                    and_or20.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or20, gbc2);
                    break;
                case 21:
                    and_or21 = new JComboBox(and_or);
                    and_or21.setSelectedIndex(0);
                    and_or21.setName("and_or");
                    and_or21.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or21, gbc2);
                    break;
                case 22:
                    and_or22 = new JComboBox(and_or);
                    and_or22.setSelectedIndex(0);
                    and_or22.setName("and_or");
                    and_or22.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or22, gbc2);
                    break;
                case 23:
                    and_or23 = new JComboBox(and_or);
                    and_or23.setSelectedIndex(0);
                    and_or23.setName("and_or");
                    and_or23.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or23, gbc2);
                    break;
                case 24:
                    and_or24 = new JComboBox(and_or);
                    and_or24.setSelectedIndex(0);
                    and_or24.setName("and_or");
                    and_or24.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or24, gbc2);
                    break;
                case 25:
                    and_or25 = new JComboBox(and_or);
                    and_or25.setSelectedIndex(0);
                    and_or25.setName("and_or");
                    and_or25.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or25, gbc2);
                    break;
                case 26:
                    and_or26 = new JComboBox(and_or);
                    and_or26.setSelectedIndex(0);
                    and_or26.setName("and_or");
                    and_or26.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or26, gbc2);
                    break;
                case 27:
                    and_or27 = new JComboBox(and_or);
                    and_or27.setSelectedIndex(0);
                    and_or27.setName("and_or");
                    and_or27.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or27, gbc2);
                    break;
                case 28:
                    and_or28 = new JComboBox(and_or);
                    and_or28.setSelectedIndex(0);
                    and_or28.setName("and_or");
                    and_or28.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or28, gbc2);
                    break;
                case 29:
                    and_or29 = new JComboBox(and_or);
                    and_or29.setSelectedIndex(0);
                    and_or29.setName("and_or");
                    and_or29.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or29, gbc2);
                    break;
                case 30:
                    and_or30 = new JComboBox(and_or);
                    and_or30.setSelectedIndex(0);
                    and_or30.setName("and_or");
                    and_or30.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(and_or30, gbc2);
                    break;
            }
            gbc2.gridx = 6;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            switch (i) {
                case 0:
                    secondSearch0 = new JComboBox(strSearch);
                    secondSearch0.setSelectedIndex(0);
                    secondSearch0.setName("secondSearch");
                    secondSearch0.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch0, gbc2);
                    break;
                case 1:
                    secondSearch1 = new JComboBox(strSearch);
                    secondSearch1.setSelectedIndex(0);
                    secondSearch1.setName("secondSearch");
                    secondSearch1.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch1, gbc2);
                    break;
                case 2:
                    secondSearch2 = new JComboBox(strSearch);
                    secondSearch2.setSelectedIndex(0);
                    secondSearch2.setName("secondSearch");
                    secondSearch2.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch2, gbc2);
                    break;
                case 3:
                    secondSearch3 = new JComboBox(strSearch);
                    secondSearch3.setSelectedIndex(0);
                    secondSearch3.setName("secondSearch");
                    secondSearch3.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch3, gbc2);
                    break;
                case 4:
                    secondSearch4 = new JComboBox(strSearch);
                    secondSearch4.setSelectedIndex(0);
                    secondSearch4.setName("secondSearch");
                    secondSearch4.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch4, gbc2);
                    break;
                case 5:
                    secondSearch5 = new JComboBox(strSearch);
                    secondSearch5.setSelectedIndex(0);
                    secondSearch5.setName("secondSearch");
                    secondSearch5.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch5, gbc2);
                    break;
                case 6:
                    secondSearch6 = new JComboBox(strSearch);
                    secondSearch6.setSelectedIndex(0);
                    secondSearch6.setName("secondSearch");
                    secondSearch6.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch6, gbc2);
                    break;
                case 7:
                    secondSearch7 = new JComboBox(strSearch);
                    secondSearch7.setSelectedIndex(0);
                    secondSearch7.setName("secondSearch");
                    secondSearch7.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch7, gbc2);
                    break;
                case 8:
                    secondSearch8 = new JComboBox(strSearch);
                    secondSearch8.setSelectedIndex(0);
                    secondSearch8.setName("secondSearch");
                    secondSearch8.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch8, gbc2);
                    break;
                case 9:
                    secondSearch9 = new JComboBox(strSearch);
                    secondSearch9.setSelectedIndex(0);
                    secondSearch9.setName("secondSearch");
                    secondSearch9.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch9, gbc2);
                    break;
                case 10:
                    secondSearch10 = new JComboBox(strSearch);
                    secondSearch10.setSelectedIndex(0);
                    secondSearch10.setName("secondSearch");
                    secondSearch10.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch10, gbc2);
                    break;
                case 11:
                    secondSearch11 = new JComboBox(strSearch);
                    secondSearch11.setSelectedIndex(0);
                    secondSearch11.setName("secondSearch");
                    secondSearch11.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch11, gbc2);
                    break;
                case 12:
                    secondSearch12 = new JComboBox(strSearch);
                    secondSearch12.setSelectedIndex(0);
                    secondSearch12.setName("secondSearch");
                    secondSearch12.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch12, gbc2);
                    break;
                case 13:
                    secondSearch13 = new JComboBox(strSearch);
                    secondSearch13.setSelectedIndex(0);
                    secondSearch13.setName("secondSearch");
                    secondSearch13.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch13, gbc2);
                    break;
                case 14:
                    secondSearch14 = new JComboBox(strSearch);
                    secondSearch14.setSelectedIndex(0);
                    secondSearch14.setName("secondSearch");
                    secondSearch14.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch14, gbc2);
                    break;
                case 15:
                    secondSearch15 = new JComboBox(strSearch);
                    secondSearch15.setSelectedIndex(0);
                    secondSearch15.setName("secondSearch");
                    secondSearch15.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch15, gbc2);
                    break;
                case 16:
                    secondSearch16 = new JComboBox(strSearch);
                    secondSearch16.setSelectedIndex(0);
                    secondSearch16.setName("secondSearch");
                    secondSearch16.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch16, gbc2);
                    break;
                case 17:
                    secondSearch17 = new JComboBox(strSearch);
                    secondSearch17.setSelectedIndex(0);
                    secondSearch17.setName("secondSearch");
                    secondSearch17.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch17, gbc2);
                    break;
                case 18:
                    secondSearch18 = new JComboBox(strSearch);
                    secondSearch18.setSelectedIndex(0);
                    secondSearch18.setName("secondSearch");
                    secondSearch18.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch18, gbc2);
                    break;
                case 19:
                    secondSearch19 = new JComboBox(strSearch);
                    secondSearch19.setSelectedIndex(0);
                    secondSearch19.setName("secondSearch");
                    secondSearch19.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch19, gbc2);
                    break;
                case 20:
                    secondSearch20 = new JComboBox(strSearch);
                    secondSearch20.setSelectedIndex(0);
                    secondSearch20.setName("secondSearch");
                    secondSearch20.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch20, gbc2);
                    break;
                case 21:
                    secondSearch21 = new JComboBox(strSearch);
                    secondSearch21.setSelectedIndex(0);
                    secondSearch21.setName("secondSearch");
                    secondSearch21.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch21, gbc2);
                    break;
                case 22:
                    secondSearch22 = new JComboBox(strSearch);
                    secondSearch22.setSelectedIndex(0);
                    secondSearch22.setName("secondSearch");
                    secondSearch22.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch22, gbc2);
                    break;
                case 23:
                    secondSearch23 = new JComboBox(strSearch);
                    secondSearch23.setSelectedIndex(0);
                    secondSearch23.setName("secondSearch");
                    secondSearch23.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch23, gbc2);
                    break;
                case 24:
                    secondSearch24 = new JComboBox(strSearch);
                    secondSearch24.setSelectedIndex(0);
                    secondSearch24.setName("secondSearch");
                    secondSearch24.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch24, gbc2);
                    break;
                case 25:
                    secondSearch25 = new JComboBox(strSearch);
                    secondSearch25.setSelectedIndex(0);
                    secondSearch25.setName("secondSearch");
                    secondSearch25.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch25, gbc2);
                    break;
                case 26:
                    secondSearch26 = new JComboBox(strSearch);
                    secondSearch26.setSelectedIndex(0);
                    secondSearch26.setName("secondSearch");
                    secondSearch26.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch26, gbc2);
                    break;
                case 27:
                    secondSearch27 = new JComboBox(strSearch);
                    secondSearch27.setSelectedIndex(0);
                    secondSearch27.setName("secondSearch");
                    secondSearch27.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch27, gbc2);
                    break;
                case 28:
                    secondSearch28 = new JComboBox(strSearch);
                    secondSearch28.setSelectedIndex(0);
                    secondSearch28.setName("secondSearch");
                    secondSearch28.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch28, gbc2);
                    break;
                case 29:
                    secondSearch29 = new JComboBox(strSearch);
                    secondSearch29.setSelectedIndex(0);
                    secondSearch29.setName("secondSearch");
                    secondSearch29.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch29, gbc2);
                    break;
                case 30:
                    secondSearch30 = new JComboBox(strSearch);
                    secondSearch30.setSelectedIndex(0);
                    secondSearch30.setName("secondSearch");
                    secondSearch30.setLightWeightPopupEnabled(false);
                    gridbaglayout2.setConstraints(secondSearch30, gbc2);
                    break;
            }
            switch (i) {
                case 0:
                    bodybottempanel.add(sqlSearch0);
                    bodybottempanel.add(and_or0);
                    bodybottempanel.add(secondSearch0);
                    break;
                case 1:
                    bodybottempanel.add(sqlSearch1);
                    bodybottempanel.add(and_or1);
                    bodybottempanel.add(secondSearch1);
                    break;
                case 2:
                    bodybottempanel.add(sqlSearch2);
                    bodybottempanel.add(and_or2);
                    bodybottempanel.add(secondSearch2);
                    break;
                case 3:
                    bodybottempanel.add(sqlSearch3);
                    bodybottempanel.add(and_or3);
                    bodybottempanel.add(secondSearch3);
                    break;
                case 4:
                    bodybottempanel.add(sqlSearch4);
                    bodybottempanel.add(and_or4);
                    bodybottempanel.add(secondSearch4);
                    break;
                case 5:
                    bodybottempanel.add(sqlSearch5);
                    bodybottempanel.add(and_or5);
                    bodybottempanel.add(secondSearch5);
                    break;
                case 6:
                    bodybottempanel.add(sqlSearch6);
                    bodybottempanel.add(and_or6);
                    bodybottempanel.add(secondSearch6);
                    break;
                case 7:
                    bodybottempanel.add(sqlSearch7);
                    bodybottempanel.add(and_or7);
                    bodybottempanel.add(secondSearch7);
                    break;
                case 8:
                    bodybottempanel.add(sqlSearch8);
                    bodybottempanel.add(and_or8);
                    bodybottempanel.add(secondSearch8);
                    break;
                case 9:
                    bodybottempanel.add(sqlSearch9);
                    bodybottempanel.add(and_or9);
                    bodybottempanel.add(secondSearch9);
                    break;
                case 10:
                    bodybottempanel.add(sqlSearch10);
                    bodybottempanel.add(and_or10);
                    bodybottempanel.add(secondSearch10);
                    break;
                case 11:
                    bodybottempanel.add(sqlSearch11);
                    bodybottempanel.add(and_or11);
                    bodybottempanel.add(secondSearch11);
                    break;
                case 12:
                    bodybottempanel.add(sqlSearch12);
                    bodybottempanel.add(and_or12);
                    bodybottempanel.add(secondSearch12);
                    break;
                case 13:
                    bodybottempanel.add(sqlSearch13);
                    bodybottempanel.add(and_or13);
                    bodybottempanel.add(secondSearch13);
                    break;
                case 14:
                    bodybottempanel.add(sqlSearch14);
                    bodybottempanel.add(and_or14);
                    bodybottempanel.add(secondSearch14);
                    break;
                case 15:
                    bodybottempanel.add(sqlSearch15);
                    bodybottempanel.add(and_or15);
                    bodybottempanel.add(secondSearch15);
                    break;
                case 16:
                    bodybottempanel.add(sqlSearch16);
                    bodybottempanel.add(and_or16);
                    bodybottempanel.add(secondSearch16);
                    break;
                case 17:
                    bodybottempanel.add(sqlSearch17);
                    bodybottempanel.add(and_or17);
                    bodybottempanel.add(secondSearch17);
                    break;
                case 18:
                    bodybottempanel.add(sqlSearch18);
                    bodybottempanel.add(and_or18);
                    bodybottempanel.add(secondSearch18);
                    break;
                case 19:
                    bodybottempanel.add(sqlSearch19);
                    bodybottempanel.add(and_or19);
                    bodybottempanel.add(secondSearch19);
                    break;
                case 20:
                    bodybottempanel.add(sqlSearch20);
                    bodybottempanel.add(and_or20);
                    bodybottempanel.add(secondSearch20);
                    break;
                case 21:
                    bodybottempanel.add(sqlSearch21);
                    bodybottempanel.add(and_or21);
                    bodybottempanel.add(secondSearch21);
                    break;
                case 22:
                    bodybottempanel.add(sqlSearch22);
                    bodybottempanel.add(and_or22);
                    bodybottempanel.add(secondSearch22);
                    break;
                case 23:
                    bodybottempanel.add(sqlSearch23);
                    bodybottempanel.add(and_or23);
                    bodybottempanel.add(secondSearch23);
                    break;
                case 24:
                    bodybottempanel.add(sqlSearch24);
                    bodybottempanel.add(and_or24);
                    bodybottempanel.add(secondSearch24);
                    break;
                case 25:
                    bodybottempanel.add(sqlSearch25);
                    bodybottempanel.add(and_or25);
                    bodybottempanel.add(secondSearch25);
                    break;
                case 26:
                    bodybottempanel.add(sqlSearch26);
                    bodybottempanel.add(and_or26);
                    bodybottempanel.add(secondSearch26);
                    break;
                case 27:
                    bodybottempanel.add(sqlSearch27);
                    bodybottempanel.add(and_or27);
                    bodybottempanel.add(secondSearch27);
                    break;
                case 28:
                    bodybottempanel.add(sqlSearch28);
                    bodybottempanel.add(and_or28);
                    bodybottempanel.add(secondSearch28);
                    break;
                case 29:
                    bodybottempanel.add(sqlSearch29);
                    bodybottempanel.add(and_or29);
                    bodybottempanel.add(secondSearch29);
                    break;
                case 30:
                    bodybottempanel.add(sqlSearch30);
                    bodybottempanel.add(and_or30);
                    bodybottempanel.add(secondSearch30);
                    break;
            }
            dummy2[i] = new TextField("", 20);
            dummy2[i].addActionListener(this);
            if (dummy2old != null) {
                if (!(dummy2old[i].trim().equals(""))) {
                    dummy2[i].setText(dummy2old[i].trim());
                }
            }
            gbc2.gridx = 7;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            gridbaglayout2.setConstraints(dummy2[i], gbc2);
            bodybottempanel.add(dummy2[i]);
            searchforeignkey2[i] = new JButton("...");
            searchforeignkey2[i].addActionListener(this);
            gbc2.gridx = 8;
            gbc2.gridy = i;
            gbc2.gridwidth = 1;
            gbc2.gridheight = 1;
            gridbaglayout2.setConstraints(searchforeignkey2[i], gbc2);
            id = "id";
            indexofid = fieldname[i].getText().trim().indexOf(id);
            if ((indexofid > -1) && (id.length() + indexofid == fieldname[i].getText().trim().length())) {
                if (!(fieldname[i].getText().trim().substring(0, indexofid).equalsIgnoreCase(TableName.trim()))) {
                    bodybottempanel.add(searchforeignkey2[i]);
                }
            }
            if (i == 0) {
                gbc2.gridx = 9;
                gbc2.gridy = i;
                gbc2.gridwidth = 1;
                gbc2.gridheight = 1;
                gridbaglayout2.setConstraints(btnOKsearch, gbc2);
                gbc2.gridx = 10;
                gbc2.gridy = i;
                gbc2.gridwidth = 1;
                gbc2.gridheight = 1;
                gridbaglayout2.setConstraints(btnClearsearch, gbc2);
            }
        }
        FlowLayout layoutMgr = new FlowLayout(FlowLayout.LEFT, 10, 5);
        JPanel dummypanel1 = new JPanel();
        JPanel dummypanel2 = new JPanel();
        dummypanel1.setLayout(layoutMgr);
        dummypanel1.add(bodybottempanel);
        dummypanel1.add(dummypanel2);
        btnOKsearch.addActionListener(this);
        bodybottempanel.add(btnOKsearch);
        btnClearsearch.addActionListener(this);
        bodybottempanel.add(btnClearsearch);
        bodybottempanel.setBorder(BorderFactory.createTitledBorder("Search"));
        panelcenter.add(dummypanel1);
    }

    private void CreateSelectiequery(String tablename) {
        odata = null;
        Component[] components = bodybottempanel.getComponents();
        TextField textfield;
        Label label;
        JComboBox combobox;
        String[] strtextfield = new String[components.length];
        String[] strtextfield2 = new String[components.length];
        String strlabel[] = new String[components.length];
        String strcombobox[] = new String[components.length];
        String strcombobox2[] = new String[components.length];
        String and_or[] = new String[components.length];
        String fieldtype[] = new String[components.length];
        String column = null;
        StringBuffer where = new StringBuffer();
        StringBuffer where2 = null;
        StringBuffer where3 = null;
        try {
            String query = "describe ";
            StringBuffer querybuffer = new StringBuffer(query);
            querybuffer.append(tablename);
            query = querybuffer.toString();
            odata = cc.jdbcconnection.returnData(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        j = k = l = n = o = p = q = 0;
        for (int i = 0; i <= (components.length - 1); i++) {
            if (components[i].getName() != null) {
                if (components[i].getName().indexOf("label") > -1) {
                    label = (Label) components[i];
                    strlabel[j] = label.getText();
                    for (int z = 0; z <= (odata.length - 1); z++) {
                        column = (String) odata[z][0];
                        if (column.trim().equals(strlabel[j].trim())) {
                            fieldtype[j] = (String) odata[z][1];
                        }
                    }
                    j++;
                }
                if (components[i].getName().indexOf("sqlSearch") > -1) {
                    combobox = (JComboBox) components[i];
                    strcombobox[k] = combobox.getSelectedItem().toString();
                    k++;
                }
                if (components[i].getName().indexOf("secondSearch") > -1) {
                    combobox = (JComboBox) components[i];
                    strcombobox2[p] = combobox.getSelectedItem().toString();
                    p++;
                }
                if (components[i].getName().indexOf("and_or") > -1) {
                    combobox = (JComboBox) components[i];
                    and_or[q] = combobox.getSelectedItem().toString();
                    q++;
                }
                if (components[i].getName().indexOf("textfield") > -1) {
                    textfield = (TextField) components[i];
                    if ((l % 2) == 0) {
                        strtextfield[n] = textfield.getText();
                        n++;
                    } else {
                        strtextfield2[o] = textfield.getText();
                        o++;
                    }
                    l++;
                }
            }
        }
        m = 0;
        for (int i = 0; i < j; i++) {
            where3 = new StringBuffer();
            where2 = new StringBuffer();
            if ((strcombobox[i].indexOf("pattern") > -1) && (fieldtype[i].indexOf("char") == -1)) {
                ;
            } else {
                if (!strtextfield[i].trim().equals("")) {
                    if (m == 0) {
                        where.append(" where ");
                    } else {
                        where.append(" and ");
                    }
                    where3.append(strlabel[i]);
                    where3.append(" ");
                    if ((fieldtype[i].indexOf("char") > -1) && (strcombobox[i].indexOf("pattern") > -1)) {
                        where3.append("like");
                    } else {
                        where3.append(strcombobox[i]);
                    }
                    where3.append(" ");
                    if (fieldtype[i].indexOf("char") > -1) {
                        if (strcombobox[i].indexOf("pattern") > -1) {
                            where3.append("'%");
                        } else {
                            where3.append("'");
                        }
                    }
                    where3.append(checkfield(strtextfield[i].trim(), fieldtype[i]));
                    if (fieldtype[i].indexOf("char") > -1) {
                        if (strcombobox[i].indexOf("pattern") > -1) {
                            where3.append("%'");
                        } else {
                            where3.append("'");
                        }
                    }
                    m++;
                }
                if (!strtextfield2[i].trim().equals("")) {
                    where2.append(" ");
                    if (!strtextfield[i].trim().equals("")) {
                        where2.append(and_or[i]);
                        where2.append(" ");
                        where2.append(strlabel[i]);
                        where2.append(" ");
                        if ((fieldtype[i].indexOf("char") > -1) && (strcombobox2[i].indexOf("pattern") > -1)) {
                            where2.append("like");
                        } else {
                            where2.append(strcombobox2[i]);
                        }
                        where2.append(" ");
                        if (fieldtype[i].indexOf("char") > -1) {
                            if (strcombobox2[i].indexOf("pattern") > -1) {
                                where2.append("'%");
                            } else {
                                where2.append("'");
                            }
                        }
                        where2.append(checkfield(strtextfield2[i].trim(), fieldtype[i]));
                        if (fieldtype[i].indexOf("char") > -1) {
                            if (strcombobox2[i].indexOf("pattern") > -1) {
                                where2.append("%'");
                            } else {
                                where2.append("'");
                            }
                        }
                    } else {
                        if (m == 0) {
                            where.append(" where ");
                        } else {
                            where.append(" and ");
                        }
                        where3.append(strlabel[i]);
                        where3.append(" ");
                        if ((fieldtype[i].indexOf("char") > -1) && (strcombobox2[i].indexOf("pattern") > -1)) {
                            where3.append("like");
                        } else {
                            where3.append(strcombobox2[i]);
                        }
                        where3.append(" ");
                        if (fieldtype[i].indexOf("char") > -1) {
                            if (strcombobox2[i].indexOf("pattern") > -1) {
                                where3.append("'%");
                            } else {
                                where3.append("'");
                            }
                        }
                        where3.append(checkfield(strtextfield2[i].trim(), fieldtype[i]));
                        if (fieldtype[i].indexOf("char") > -1) {
                            if (strcombobox2[i].indexOf("pattern") > -1) {
                                where3.append("%'");
                            } else {
                                where3.append("'");
                            }
                        }
                        m++;
                    }
                }
                if (!where2.toString().trim().equals("")) {
                    where.append("(");
                    where.append(where3.toString());
                    where.append(where2.toString());
                    where.append(")");
                } else {
                    where.append(where3.toString());
                    where.append(where2.toString());
                }
            }
        }
        wherestring = where.toString();
        selectbrowser(tablename, wherestring);
    }

    private String checkfield(String tekst, String fieldtype) {
        if (fieldtype.indexOf("char") > -1) {
            if (tekst.indexOf("'") > -1) {
                tekst = "";
            }
            if (tekst.indexOf("\"") > -1) {
                tekst = "";
            }
        }
        if (fieldtype.indexOf("int") > -1) {
            try {
                testint = Integer.valueOf(tekst);
            } catch (Exception ex) {
                tekst = "null";
            } finally {
            }
        }
        return tekst;
    }

    private void Clearfilledinvalues() {
        if (dummy1old != null) {
            for (int i = 0; i <= (dummy1old.length - 1); i++) {
                dummy1old[i] = "";
            }
        }
        if (dummy2old != null) {
            for (int i = 0; i <= (dummy2old.length - 1); i++) {
                dummy2old[i] = "";
            }
        }
    }

    private void Copyfilledinvalues() {
        if (dummy1 != null) {
            dummy1old = new String[dummy1.length];
            for (int i = 0; i <= (dummy1.length - 1); i++) {
                dummy1old[i] = dummy1[i].getText().trim();
            }
        }
        if (dummy2 != null) {
            dummy2old = new String[dummy2.length];
            for (int i = 0; i <= (dummy2.length - 1); i++) {
                dummy2old[i] = dummy2[i].getText().trim();
            }
        }
    }

    public void actionPerformed(ActionEvent evt) {
        Copyfilledinvalues();
        Object choice = evt.getSource();
        if (choice == btnFirst) {
            offset = 0;
            CreateSelectiequery(TableName);
        }
        if (choice == btnPrevious) {
            offset = offset - limit;
            if (offset < 0) {
                offset = 0;
            }
            CreateSelectiequery(TableName);
        }
        if (choice == btnrefresh) {
            offset = 0;
            btnClearsearch.doClick();
            CreateSelectiequery(TableName);
        }
        if (choice == btnNext) {
            offset = offset + limit;
            CreateSelectiequery(TableName);
        }
        if (choice == btnLast) {
            offset = intmaxnumberofrecords - (intmaxnumberofrecords % limit);
            CreateSelectiequery(TableName);
        }
        if (evt.getSource().toString().indexOf("TextField") > -1) {
            btnOKsearch.doClick();
        }
        if (choice == btnOKsearch) {
            offset = 0;
            CreateSelectiequery(TableName);
        }
        if (choice == btnClearsearch) {
            Clearfilledinvalues();
            Selectfields(panelcenter, TableName);
            pBody.remove(panelcenter);
            pBody.add(panelcenter, BorderLayout.CENTER);
            if (dialog == true) {
                searchtablerecorddialog = (JDialog) object;
                searchtablerecorddialog.setVisible(true);
            } else if (frame != null) {
                frame.setVisible(true);
            }
        }
        for (int i = 0; i <= (numbersofrows - 1); i++) {
            if (evt.getSource() == searchforeignkey[i]) {
                indexofid = fieldname[i].getText().trim().indexOf(id);
                cc.updatefield = dummy1[i];
                dialogtitle = "Search " + fieldname[i].getText().trim().substring(0, indexofid);
                optionsearch = new JOptionPane(dialogtitle, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                cc.searchtablerecorddialog = optionsearch.createDialog(this.frame, dialogtitle);
                optionsearch.addPropertyChangeListener(this);
                searchtablerecordpanel = new JPanel();
                cc.searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
                searchselection = new SearchSelection(this.cc, cc.searchtablerecorddialog, searchtablerecordpanel);
                wherestring = "";
                searchselection.selectbrowser(fieldname[i].getText().trim().substring(0, indexofid), wherestring);
                cc.searchtablerecorddialog.setSize(900, 700);
                cc.searchtablerecorddialog.setVisible(true);
            }
            if (evt.getSource() == searchforeignkey2[i]) {
                indexofid = fieldname[i].getText().trim().indexOf(id);
                cc.updatefield = dummy2[i];
                dialogtitle = "Search " + fieldname[i].getText().trim().substring(0, indexofid);
                optionsearch = new JOptionPane(dialogtitle, JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                cc.searchtablerecorddialog = optionsearch.createDialog(this.frame, dialogtitle);
                optionsearch.addPropertyChangeListener(this);
                searchtablerecordpanel = new JPanel();
                cc.searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
                searchselection = new SearchSelection(this.cc, cc.searchtablerecorddialog, searchtablerecordpanel);
                wherestring = "";
                searchselection.selectbrowser(fieldname[i].getText().trim().substring(0, indexofid), wherestring);
                cc.searchtablerecorddialog.setSize(900, 700);
                cc.searchtablerecorddialog.setVisible(true);
            }
        }
    }

    public void mouseClicked(MouseEvent evt2) {
        odata2 = null;
        int columnpositionprimairykey = -1;
        try {
            String query = "describe ";
            StringBuffer querybuffer = new StringBuffer(query);
            querybuffer.append(TableName);
            query = querybuffer.toString();
            odata2 = cc.jdbcconnection.returnData(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int j = 0; j <= (odata2.length - 1); j++) {
            if (((String) odata2[j][3]).equals("PRI")) {
                primarykey = (String) odata2[j][0];
                for (int i = 0; i < table.getColumnCount(); i++) {
                    if (table.getColumnName(i).equals(odata2[j][0])) {
                        columnpositionprimairykey = i;
                    }
                }
            }
        }
        primarykeyvalue = sorter.getValueAt(table.getSelectedRow(), columnpositionprimairykey).toString();
        if (dialog == true) {
            cc.updatefieldvalue = primarykeyvalue;
            cc.PerformAction(searchtablerecorddialog);
            searchtablerecorddialog.dispose();
        } else if (frame != null) {
            cc.PerformAction(this);
        }
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void propertyChange(PropertyChangeEvent evt) {
        prop = evt.getPropertyName();
        if (cc.searchtablerecorddialog != null) {
            if (cc.searchtablerecorddialog.isVisible() && (evt.getSource() == optionsearch)
                    && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
                if (evt.getOldValue().toString().trim().equalsIgnoreCase("0")) {
                    optionsearch.setVisible(false);
                } else if (evt.getOldValue().toString().trim().equalsIgnoreCase("1")) {
                    cc.searchtablerecorddialog.setVisible(false);
                } else {
                    cc.searchtablerecorddialog.setVisible(false);
                }
            }
        }
    }
}
