package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;

import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;

import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class StorePplot {

    Controller cc;

    public StorePplot(Controller cc) {
        this.cc = cc;
    }

    public void store_p_plot(String p_plot_prefix, String filenamematrix, String groupid1, String groupid2,
                             String groupcode1, String groupcode2) {
        File file = null;
        boolean filetransportedwilcoxon = false;
        String extension = "";
        String searchstringwilcoxon = ".bmp";
        String exportname = "";
        String exportnamelocal = "";
        int indexofsearchstring = cc.P_plot_file.trim().indexOf(searchstringwilcoxon);
        if ((indexofsearchstring > -1)
                && (searchstringwilcoxon.length() + indexofsearchstring == cc.P_plot_file.trim().length())) {
            extension = searchstringwilcoxon;
        }
        searchstringwilcoxon = ".jpeg";
        indexofsearchstring = cc.P_plot_file.trim().indexOf(searchstringwilcoxon);
        if ((indexofsearchstring > -1)
                && (searchstringwilcoxon.length() + indexofsearchstring == cc.P_plot_file.trim().length())) {
            extension = searchstringwilcoxon;
        }
        searchstringwilcoxon = ".txt";
        indexofsearchstring = filenamematrix.trim().indexOf(searchstringwilcoxon);
        if ((indexofsearchstring > -1)
                && (searchstringwilcoxon.length() + indexofsearchstring == filenamematrix.trim().length())) {
            exportname = p_plot_prefix + groupid1 + "_" + groupid2 + "_"
                    + filenamematrix.trim().trim().substring(0, indexofsearchstring) + extension;
            exportnamelocal = p_plot_prefix + groupcode1 + "_" + groupcode2 + "_"
                    + filenamematrix.trim().trim().substring(0, indexofsearchstring) + extension;
        }
        cc.strtype = "wilcoxon_plot";
        cc.actualtime.resettime();
        cc.filetransportedwilcoxon = false;
        cc.data_wilcoxon = null;
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
            file = new File(cc.P_plot_file);
            FileInputStream fis = new FileInputStream(file);
            cc.data_wilcoxon = new byte[(int) file.length()];
            fis.read(cc.data_wilcoxon);
            cc.ftp.chdir(File.separator + cc.dataexperiment[4] + File.separator + cc.strexperimentid2);
            cc.ftp.put(cc.data_wilcoxon, exportname);
            filetransportedwilcoxon = true;
            fis.close();

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
        boolean exporttodisksucceeded = cc.exportfiletodisk.exportfile_and_directory(cc.userhome, exportnamelocal,
                cc.data_wilcoxon);
        cc.filegrootte_kbytes_wilcoxon = file.length() / 1024.000;
        cc.tempBD = new BigDecimal(cc.filegrootte_kbytes_wilcoxon);
        if (filetransportedwilcoxon) {
            cc.insertresultrecordWilcoxon.insertresultrecordwilcoxon(cc.strexperimentid2, cc.strtype, cc.exportname);
        }
    }
}
