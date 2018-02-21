package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.CreateWilcoxon;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class DisplayPplot {

    FTPClient ftp;

    Controller cc;

    public DisplayPplot(Controller cc) {
        this.cc = cc;
    }

    public void displayPplot(String input_p_file, CreateWilcoxon createwilcoxon, String stryear, String strexperiment) {

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
        createwilcoxon.removeAll_from_p_plot_panel();
        Image m_BitMap;
        ImageIcon p_plot_icon;
        byte[] data_wilcoxon = null;
        boolean filepresent = false;
        String[] listdir = null;
        try {
            cc.P_plot_file = input_p_file.replaceAll(".txt", ".bmp");
            filepresent = false;
            listdir = ftp.dir();
            if (listdir != null) {
                for (int i = 0; i < listdir.length; i++) {
                    if (listdir[i].trim().equalsIgnoreCase(cc.P_plot_file.trim())) {
                        filepresent = true;
                    }
                }
            }
            if (filepresent) {
                data_wilcoxon = ftp.get(cc.P_plot_file);
            }
        } catch (Exception e) {
            filepresent = false;
        }
        if (!filepresent) {
            try {
                cc.P_plot_file = input_p_file.replaceAll(".txt", ".jpeg");
                if (listdir != null) {
                    for (int i = 0; i < listdir.length; i++) {
                        if (listdir[i].trim().equalsIgnoreCase(cc.P_plot_file.trim())) {
                            filepresent = true;
                        }
                    }
                }
                if (filepresent) {
                    data_wilcoxon = ftp.get(cc.P_plot_file);
                }
            } catch (Exception e) {
                filepresent = false;
            }
        }
        m_BitMap = null;
        p_plot_icon = null;
        if (data_wilcoxon != null) {
            m_BitMap = BMPLoader.read(data_wilcoxon);
        }
        if (m_BitMap != null) {
            p_plot_icon = new ImageIcon(m_BitMap);
            JLabel icon_label = new JLabel(p_plot_icon);
            createwilcoxon.p_plot_panel_add_image(icon_label);
        }

        try {
            ftp.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
