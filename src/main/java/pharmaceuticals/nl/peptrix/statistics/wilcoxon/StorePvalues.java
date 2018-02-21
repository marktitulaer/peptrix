package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.io.File;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class StorePvalues {

    Controller cc;

    CreateexportstringWilcoxon createexportstringWilcoxon;

    public StorePvalues(Controller cc) {
        this.cc = cc;
    }

    public void store_p_values(String filenamematrix) {
        createexportstringWilcoxon = new CreateexportstringWilcoxon();
        cc.data_wilcoxon = createexportstringWilcoxon.create_exportstring(cc.cleanedPalues);
        String exportnamelocal = cc.p_values_prefix + cc.groupcode1wilcoxon + "_" + cc.groupcode2wilcoxon + "_"
                + filenamematrix;
        cc.exportname = cc.p_values_prefix + cc.groupid1wilcoxon + "_" + cc.groupid2wilcoxon + "_" + filenamematrix;
        store_file_local(exportnamelocal);
        store_file_server();
    }

    public void store_file_server() {
        cc.strtype = "p_values";
        cc.actualtime.resettime();
        cc.filegrootte_kbytes_wilcoxon = cc.data_wilcoxon.length / 1024.000;
        cc.tempBD = new BigDecimal(cc.filegrootte_kbytes_wilcoxon);
        cc.filetransportedwilcoxon = false;
        try {
            cc.ftp.setRemoteHost(cc.ftpremotehost);
            cc.ftp.connect();
            cc.ftp.login(cc.ftpuser, cc.ftppassword);
            cc.ftp.setConnectMode(FTPConnectMode.PASV);
            cc.ftp.setType(FTPTransferType.BINARY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cc.ftp.chdir(File.separator + cc.dataexperiment[4] + File.separator + cc.strexperimentid2);
            cc.ftp.put(cc.data_wilcoxon, cc.exportname);
            cc.filetransportedwilcoxon = true;
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            cc.ftp.quit();
        } catch (Exception e) {
            ;
        }
        if (cc.filetransportedwilcoxon) {
            cc.insertresultrecordWilcoxon.insertresultrecordwilcoxon(cc.strexperimentid2, cc.strtype, cc.exportname);
        }
    }

    public void store_file_local(String exportnamelocal) {
        boolean exporttodisksucceeded = cc.exportfiletodisk.exportfile_and_directory(cc.userhome, exportnamelocal,
                cc.data_wilcoxon);
    }
}
