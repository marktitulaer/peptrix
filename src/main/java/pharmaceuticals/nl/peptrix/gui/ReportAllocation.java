package pharmaceuticals.nl.peptrix.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.allocation.SaveAllocation;
import pharmaceuticals.nl.peptrix.gui.Allocation;
import pharmaceuticals.nl.peptrix.gui.TableSorter;

public class ReportAllocation {
    String queryallocationreport;
    JScrollPane scrollpane;
    public SaveAllocation saveallocation;
    Controller cc;
    public ResultSetTableModel resultsettablemodel;
    Allocation allocation;
    ExportFileToDisk exportfiletodisk;

    public ReportAllocation(Controller cc) {
        this.cc = cc;
        exportfiletodisk = new ExportFileToDisk(cc);
    }

    public void reportallocation(String filtertype, TableSorter sorter, JTable allocationtable,
                                 JPanel allocationcenterpanelsouth) {
        GridBagLayout gridbagresults = new GridBagLayout();
        allocationcenterpanelsouth.setLayout(gridbagresults);
        GridBagConstraints constraintsresults = new GridBagConstraints();
        constraintsresults.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintsresults.insets = new Insets(2, 2, 2, 2);
        cc.savereportallocation = new JButton("save report");
        saveallocation = new SaveAllocation(cc);
        allocationcenterpanelsouth.removeAll();
        allocationcenterpanelsouth.repaint();
        queryallocationreport = "select sa.sampleid, sa.sample_code, count(sa.sampleid) replicates, gr.group_id, gr.group_code ,gr.name "
                + "from result rs inner join sample sa on rs.sampleid = sa.sampleid "
                + "inner join Group_ gr on rs.Group_id = gr.Group_id " + "where rs.experimentid = "
                + cc.experimentid.getText().trim() + " and rs.Type = " + filtertype
                + " group by gr.name, sa.sample_code";
        try {
            cc.jdbcconnection.selectdata(queryallocationreport);
            if (resultsettablemodel == null) {
                resultsettablemodel = new ResultSetTableModel(cc.jdbcconnection);
            } else {
                resultsettablemodel.displayresults(cc.jdbcconnection);
            }
        } catch (SQLException e) {
            if (cc.debugmode) {
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
        if (allocationtable == null) {
            allocationtable = new JTable(sorter);
        } else {
            allocationtable.setModel(sorter);
        }
        allocationtable.setPreferredScrollableViewportSize(new Dimension(850, 350));
        sorter.setTableHeader(allocationtable.getTableHeader());
        scrollpane = new JScrollPane(allocationtable);
        cc.savereportallocation.addActionListener(cc);
        constraintsresults.gridx = 1;
        constraintsresults.gridy = 1;
        constraintsresults.gridwidth = 1;
        constraintsresults.gridheight = 1;
        gridbagresults.setConstraints(cc.savereportallocation, constraintsresults);
        allocationcenterpanelsouth.add(cc.savereportallocation);
        constraintsresults.gridx = 1;
        constraintsresults.gridy = 2;
        constraintsresults.gridwidth = 1;
        constraintsresults.gridheight = 1;
        gridbagresults.setConstraints(scrollpane, constraintsresults);
        allocationcenterpanelsouth.add(scrollpane);
        cc.frame.setVisible(true);
    }
}
