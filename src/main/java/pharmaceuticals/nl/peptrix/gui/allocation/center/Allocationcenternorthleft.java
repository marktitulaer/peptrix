package pharmaceuticals.nl.peptrix.gui.allocation.center;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Allocationcenternorthleft {

    JPanel jpanel;

    public Allocationcenternorthleft(Controller cc) {
        jpanel = new JPanel();
        jpanel.setBackground(cc.lightgrey);
        FlowLayout flowlayout = new FlowLayout(FlowLayout.LEFT, 10, 5);
        jpanel.setLayout(flowlayout);
        cc.btnsave = new JButton("Save");
        cc.btnsave.addActionListener(cc);
        jpanel.add(cc.btnsave);
        cc.creatematrix2 = new JButton("Create matrix");
        cc.creatematrix2.addActionListener(cc);
        jpanel.add(cc.creatematrix2);
        cc.btnreport = new JButton("Report");
        cc.btnreport.addActionListener(cc);
        jpanel.add(cc.btnreport);
    }

    public JPanel getpanel() {
        return jpanel;
    }

}
