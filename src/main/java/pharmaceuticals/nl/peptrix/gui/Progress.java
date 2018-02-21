package pharmaceuticals.nl.peptrix.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextField;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class Progress {

    JProgressBar progressbar;
    JPanel progressPanel;
    JPanel tekstprogressbarpanel;
    TextField tekstprogessbar;
    JDialog jd;

    public Progress() {
        progressbar = new JProgressBar(0, 100);
        progressbar.setStringPainted(true);
        progressPanel = new JPanel();
        progressPanel.add(progressbar);
        tekstprogessbar = new TextField();
        tekstprogessbar.setBackground(Color.YELLOW);
        tekstprogessbar.setEnabled(false);
        tekstprogressbarpanel = new JPanel();
        tekstprogressbarpanel.add(tekstprogessbar);
        jd = new JDialog();
        jd.setTitle("Progress");
        jd.setLocation(200, 200);
        jd.getContentPane().add(progressPanel, BorderLayout.NORTH);
        jd.getContentPane().add(tekstprogressbarpanel, BorderLayout.CENTER);
        jd.setSize(600, 150);
    }

    public void setdialogsize(Dimension dimension) {
        jd.setSize(dimension);
    }

    public void init(String text, int columns) {
        tekstprogessbar.setColumns(columns);
        progressbar.setValue(0);
        progressbar.setMaximum(1);
        tekstprogessbar.setText(text);
        jd.setVisible(true);
        progressbar.paintImmediately(progressbar.getVisibleRect());

    }

    public void setmaximum(int maximum) {
        progressbar.setMaximum(maximum);
    }

    public void setnumberandtext(int number, String text) {
        progressbar.setValue(number);
        if (!text.trim().equalsIgnoreCase("")) {
            tekstprogessbar.setText(text);
        }
        progressbar.paintImmediately(progressbar.getVisibleRect());

    }

    public void settext(String text) {
        if (!text.trim().equalsIgnoreCase("")) {
            tekstprogessbar.setText(text);
        }
        progressbar.paintImmediately(progressbar.getVisibleRect());
    }

    public void close() {
        jd.setVisible(false);
        jd.dispose();
    }

}
