package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.awt.Dimension;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import pharmaceuticals.nl.peptrix.Controller;

public class DisplayPvalues {
    Controller cc;
    FTPClient ftp;
    int numberofrows;
    byte[] data_wilcoxon;

    public DisplayPvalues(Controller cc) {
        this.cc = cc;
    }

    public JTable displaypvalues(String p_values_name, String stryear, String strexperiment) {
        JTable pvaluestable = null;
        if (ftp == null) {
            try {
                ftp = new FTPClient();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            ftp.setRemoteHost(cc.ftpremotehost);
            ftp.connect();
            ftp.login(cc.ftpuser, cc.ftppassword);
            ftp.setConnectMode(FTPConnectMode.PASV);
            ftp.setType(FTPTransferType.BINARY);
            ftp.chdir(File.separator + stryear + File.separator + strexperiment);
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        String[] stringtableheader = {"Mass", "Wilcoxon P-value"};
        String[] temp;
        String line = "";
        String[] listdir = null;
        String[][] stringPvalues = null;
        try {
            numberofrows = 0;
            boolean filepresent = false;
            listdir = ftp.dir();
            if (listdir != null) {
                for (int i = 0; i < listdir.length; i++) {
                    if (listdir[i].trim().equalsIgnoreCase(p_values_name.trim())) {
                        filepresent = true;
                    }
                }
            }
            if (filepresent) {
                data_wilcoxon = ftp.get(p_values_name);
                for (int i = 0; i < data_wilcoxon.length; i++) {
                    if (data_wilcoxon[i] == 10) {
                        numberofrows++;
                    }
                }
                if (numberofrows > 0) {
                    stringPvalues = new String[numberofrows][];
                    numberofrows = 0;
                    line = "";
                    StringBuffer linebuffer = new StringBuffer(line);
                    for (int i = 0; i < data_wilcoxon.length; i++) {
                        if ((data_wilcoxon[i] != 10) && (data_wilcoxon[i] != 13)) {
                            linebuffer.append((char) data_wilcoxon[i]);
                        }
                        if (data_wilcoxon[i] == 10) {
                            line = linebuffer.toString();
                            temp = null;
                            temp = line.split(",");
                            stringPvalues[numberofrows] = temp;
                            numberofrows++;
                            line = "";
                            linebuffer = new StringBuffer(line);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (numberofrows > 0) {
            DefaultTableModel defaulttablemodel = new DefaultTableModel(stringPvalues, stringtableheader);
            pvaluestable = new JTable(defaulttablemodel);
            pvaluestable.setPreferredScrollableViewportSize(new Dimension(400, 220));
        }
        try {
            ftp.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pvaluestable;
    }
}
