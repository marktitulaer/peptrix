package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.table.TableColumn;

import pharmaceuticals.nl.peptrix.Controller;

public class SlavePane implements MouseListener, ActionListener {
    Object[][] odata;
    Object[][] maxnumberofrecords;
    ResultSetTableModel resultsettablemodel;
    TableSorter sorter;
    FlowLayout layoutMgr;
    Controller cc;
    TableColumn col;
    public String table;
    public String primarykey;
    public String primarykeyvalue;
    String query;
    String stroffset;
    String strlimit;
    String querymaxnumberrecords;
    JPanel slavepanel;
    JPanel panelsouth;
    JPanel panelnorth;
    JPanel panelnorthwest;
    JPanel dummypanel;
    JPanel browsepanel;
    JPanel browseandinfopanel;
    JPanel infopanel;
    JTable slaveTable;
    JTabbedPane tabbedPane;
    StringBuffer tablabel;
    JScrollPane scrollpane;
    JButton btnNextslave;
    JButton btnFirstslave;
    JButton btnPreviousslave;
    JButton btnLastslave;
    JButton btnrefreshslave;
    JLabel numberrecordsinfolabel;
    int offset = 0;
    int limit = 10000;
    int numbersofrows = 0;
    int intmaxnumberofrecords;
    int columnwidth = 100;
    boolean debugmode = false;

    public SlavePane(String table, String primarykey, String primarykeyvalue, JPanel slavepanel, JTabbedPane tabbedPane,
                     Controller cc) {
        this.table = table;
        this.primarykey = primarykey;
        this.primarykeyvalue = primarykeyvalue;
        this.cc = cc;
        this.slavepanel = slavepanel;
        this.tabbedPane = tabbedPane;
        if (!primarykeyvalue.trim().equals("")) {
            createtable();
        }
        tablabel = new StringBuffer(table);
        tablabel.append("(s)");
        tabbedPane.addTab(tablabel.toString(), null, slavepanel);
    }

    private void createtable() {
        slavepanel.removeAll();
        stroffset = String.valueOf(offset);
        strlimit = String.valueOf(limit);
        panelsouth = new JPanel();
        panelnorth = new JPanel();
        panelnorthwest = new JPanel();
        dummypanel = new JPanel();
        btnNextslave = new JButton(">");
        btnNextslave.addActionListener(this);
        btnFirstslave = new JButton("|<");
        btnFirstslave.addActionListener(this);
        btnrefreshslave = new JButton("[]");
        btnrefreshslave.addActionListener(this);
        btnPreviousslave = new JButton("<");
        btnPreviousslave.addActionListener(this);
        btnLastslave = new JButton(">|");
        btnLastslave.addActionListener(this);
        browsepanel = new JPanel();
        layoutMgr = new FlowLayout(FlowLayout.LEFT, 2, 5);
        browsepanel.setLayout(layoutMgr);
        browsepanel.add(btnFirstslave);
        browsepanel.add(btnPreviousslave);
        browsepanel.add(btnrefreshslave);
        browsepanel.add(btnNextslave);
        browsepanel.add(btnLastslave);
        panelnorth.setLayout(new BorderLayout());
        slavepanel.setLayout(new BorderLayout());
        querymaxnumberrecords = "select count(*) from " + table + " where " + primarykey + " =  " + primarykeyvalue;
        try {
            maxnumberofrecords = cc.jdbcconnection.returnData(querymaxnumberrecords);
        } catch (SQLException e) {
            if (debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        intmaxnumberofrecords = 0;
        if (maxnumberofrecords != null) {
            intmaxnumberofrecords = Integer.parseInt(maxnumberofrecords[0][0].toString());
        }
        query = "select * from " + table + " where " + primarykey + " =  " + primarykeyvalue + " order by "
                + table.trim() + "id desc " + " limit " + strlimit + " offset " + stroffset;
        cc.jdbcconnection.selectdata(query);
        try {
            resultsettablemodel = new ResultSetTableModel(cc.jdbcconnection);
        } catch (SQLException e) {
            if (debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (sorter == null) {
            sorter = new TableSorter(resultsettablemodel);
        } else {
            sorter.setTableModel(resultsettablemodel);
        }
        if (slaveTable == null) {
            slaveTable = new JTable(sorter);
        } else {
            slaveTable.setModel(sorter);
        }
        sorter.setTableHeader(slaveTable.getTableHeader());
        slaveTable.addMouseListener(this);
        scrollpane = new JScrollPane(slaveTable);
        slaveTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i <= (slaveTable.getColumnCount() - 1); i++) {
            col = slaveTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(columnwidth);
        }
        panelnorthwest.add(scrollpane);
        browseandinfopanel = new JPanel();
        browseandinfopanel.setLayout(new BorderLayout());
        numberrecordsinfolabel = new JLabel("Number of records : " + String.valueOf(intmaxnumberofrecords));
        infopanel = new JPanel();
        infopanel.add(numberrecordsinfolabel);
        browseandinfopanel.add(browsepanel, BorderLayout.WEST);
        browseandinfopanel.add(infopanel, BorderLayout.EAST);
        panelnorth.add(browseandinfopanel, BorderLayout.NORTH);
        panelnorth.add(panelnorthwest, BorderLayout.WEST);
        panelnorth.add(dummypanel, BorderLayout.CENTER);
        slavepanel.add(panelnorth, BorderLayout.NORTH);
        slaveTable.setPreferredScrollableViewportSize(new Dimension(850, 500));
        disablebrowsebuttons();
        cc.frame.setVisible(true);
    }

    private void disablebrowsebuttons() {
        if (offset == 0) {
            btnFirstslave.setEnabled(false);
            btnPreviousslave.setEnabled(false);
        }
        if ((offset + limit) >= intmaxnumberofrecords) {
            btnNextslave.setEnabled(false);
            btnLastslave.setEnabled(false);
        }
    }

    public void mouseClicked(MouseEvent arg0) {
        odata = null;
        int columnpositionprimairykey = -1;
        try {
            String query = "describe ";
            StringBuffer querybuffer = new StringBuffer(query);
            querybuffer.append(table);
            query = querybuffer.toString();
            odata = cc.jdbcconnection.returnData(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int j = 0; j <= (odata.length - 1); j++) {
            if (((String) odata[j][3]).equals("PRI")) {
                primarykey = (String) odata[j][0];
                for (int i = 0; i < slaveTable.getColumnCount(); i++) {
                    if (slaveTable.getColumnName(i).equals(odata[j][0])) {
                        columnpositionprimairykey = i;
                    }
                }
            }
        }
        primarykeyvalue = slaveTable.getValueAt(slaveTable.getSelectedRow(), columnpositionprimairykey).toString();
        cc.PerformAction(this);
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == btnFirstslave) {
            offset = 0;
            createtable();
        }
        if (evt.getSource() == btnPreviousslave) {
            offset = offset - limit;
            if (offset < 0) {
                offset = 0;
            }
            createtable();
        }
        if (evt.getSource() == btnrefreshslave) {
            offset = 0;
            createtable();
        }
        if (evt.getSource() == btnNextslave) {
            offset = offset + limit;
            createtable();
        }
        if (evt.getSource() == btnLastslave) {
            offset = intmaxnumberofrecords - (intmaxnumberofrecords % limit);
            createtable();
        }
    }
}
