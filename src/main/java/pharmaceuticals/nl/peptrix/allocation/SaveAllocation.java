package pharmaceuticals.nl.peptrix.allocation;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.gui.ResultSetTableModel;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

public class SaveAllocation {
    Object[][] odatadetails;
    Object[] oheaders;
    Controller cc;
    ResultService resultService;
    StringBuffer printallocationbuffer;
    String linefeed = "\n";
    String exportname;
    String strquery;
    String printallocation;

    public SaveAllocation(Controller cc) {
        this.cc = cc;
        resultService = new ResultServiceImpl(cc);
    }

    public void save_allocation(ResultSetTableModel resultsettablemodel, ExportFileToDisk exportfiletodisk,
                                ResultServiceImpl queryresults) {
        if (resultsettablemodel.getRowCount() > 0) {
            printallocation = "";
            printallocationbuffer = new StringBuffer(printallocation);
            for (int i = 0; i <= (resultsettablemodel.getColumnCount() - 1); i++) {
                if (i == 0) {
                    printallocationbuffer.append(resultsettablemodel.getColumnName(i).trim());
                } else {
                    printallocationbuffer.append("," + resultsettablemodel.getColumnName(i).trim());
                }
                if (i == resultsettablemodel.getColumnCount() - 1) {
                    printallocationbuffer.append(linefeed);
                }
            }
            for (int j = 0; j <= (resultsettablemodel.getRowCount() - 1); j++) {
                for (int i = 0; i <= (resultsettablemodel.getColumnCount() - 1); i++) {
                    if (i == 0) {
                        printallocationbuffer.append(resultsettablemodel.getValueAt(j, i).toString().trim());
                    } else {
                        printallocationbuffer.append("," + resultsettablemodel.getValueAt(j, i).toString().trim());
                    }
                }
                printallocationbuffer.append(linefeed);
            }
            printallocation = printallocationbuffer.toString();
            exportname = "experiment_" + cc.experimentid.getText().trim() + "_allocationreport.txt";
            boolean exporttodisksucceeded = exportfiletodisk.exportfile_and_directory(cc.userhome, exportname,
                    printallocation.getBytes());
            odatadetails = resultService.selectresultrecords(cc.experimentid.getText().trim(),
                    queryresults.getfiltertype());
            oheaders = resultService.selectheader();
            printallocation = "";
            printallocationbuffer = new StringBuffer(printallocation);
            for (int i = 0; i <= (oheaders.length - 1); i++) {
                if (i == 0) {
                    printallocationbuffer.append(oheaders[i].toString().trim());
                } else {
                    printallocationbuffer.append("," + oheaders[i].toString().trim());
                }
                if (i == oheaders.length - 1) {
                    printallocationbuffer.append(linefeed);
                }
            }
            for (int i = 0; i <= (odatadetails.length - 1); i++) {
                for (int j = 0; j <= (odatadetails[i].length - 1); j++) {
                    if (j == 0) {
                        printallocationbuffer.append(odatadetails[i][j].toString().trim());
                    } else {
                        printallocationbuffer.append("," + odatadetails[i][j].toString().trim());
                    }
                    if (j == odatadetails[i].length - 1) {
                        printallocationbuffer.append(linefeed);
                    }
                }
            }
            printallocation = printallocationbuffer.toString();
            exportname = "experiment_" + cc.experimentid.getText().trim() + "_allocationdetails.txt";
            boolean exporttodisksucceeded2 = exportfiletodisk.exportfile_and_directory(cc.userhome, exportname,
                    printallocation.getBytes());
        }
    }
}
