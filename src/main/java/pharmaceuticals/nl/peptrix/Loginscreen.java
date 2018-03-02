package pharmaceuticals.nl.peptrix;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import pharmaceuticals.nl.peptrix.database.DataBase;
import pharmaceuticals.nl.peptrix.gui.CreateMenu;
import pharmaceuticals.nl.peptrix.gui.application.FramePanel;

public class Loginscreen implements WindowListener {
    static String screentitle = "PEPTRIX v3.46";
    Controller cc;
    DataBase database;

    public static void main(String[] args) throws SQLException {
        Loginscreen lg = new Loginscreen(screentitle);
        lg.dummy();
    }

    Loginscreen(String title) throws SQLException {
        String jdbc_user = "root";
        String jdbc_password = "jk3567";
        String databaseName = "proteomics";
        String ftpuser = "root";
        String ftppassword = "jk3567";
        String ftpremotehost = "127.0.0.1";
        database = new DataBase(jdbc_user, jdbc_password, databaseName);
        database.FillInitialData();
        JFrame frame = new JFrame(title);
        cc = new Controller(frame, jdbc_user, jdbc_password, databaseName, ftpuser, ftppassword, ftpremotehost);
        MenuBar menubar = new CreateMenu(cc);
        frame.setMenuBar(menubar);
        Dimension d = frame.getToolkit().getScreenSize();
        frame.setSize(d.width, d.height);
        frame.setLocation(0, 0);
        frame.addWindowListener(this);
        FramePanel framepanel = new FramePanel(cc);
        JPanel jpanel = framepanel.getPanel();
        frame.getContentPane().add(jpanel);
        frame.setVisible(true);
    }

    private void dummy() {
    }

    public void windowClosing(WindowEvent e) {
        try {
            if (cc.jdbcconnection.con != null) {
                cc.jdbcconnection.con.close();
            }
        } catch (SQLException sqlexception) {
        }
        System.exit(0);
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
}
