package pharmaceuticals.nl.peptrix.gui.allocation.center;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import pharmaceuticals.nl.peptrix.Controller;

public class Centerpanelnorthallocation {
    JPanel jpanel;
    Allocationbrowsepanel allocationbrowsepanel;
    JPanel browsepanel;
    Controller cc;

    public Centerpanelnorthallocation(Controller cc) {
        this.cc = cc;
        Allocationcenternorthleft allocationcenternorthleft = new Allocationcenternorthleft(cc);
        JPanel allocationcenterpanelnorthleft = allocationcenternorthleft.getpanel();
        allocationbrowsepanel = new Allocationbrowsepanel();
        browsepanel = allocationbrowsepanel.getpanel();
        jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.add(allocationcenterpanelnorthleft, BorderLayout.WEST);
        jpanel.add(browsepanel, BorderLayout.EAST);
    }

    public JPanel getpanel() {
        return jpanel;
    }

    public JPanel getbrowsepanel() {
        return browsepanel;
    }

    public void fillbrowsepanel() {
        browsepanel.removeAll();
        cc.btnNext = new JButton(">");
        cc.btnNext.addActionListener(cc);
        cc.btnFirst = new JButton("|<");
        cc.btnFirst.addActionListener(cc);
        cc.btnrefresh = new JButton("[]");
        cc.btnrefresh.addActionListener(cc);
        cc.btnPrevious = new JButton("<");
        cc.btnPrevious.addActionListener(cc);
        cc.btnLast = new JButton(">|");
        cc.btnLast.addActionListener(cc);
        cc.labelzero = new JLabel("0");
        browsepanel.add(cc.labelzero);
        cc.slider = new JSlider(SwingConstants.HORIZONTAL);
        browsepanel.add(cc.slider);
        cc.numberofrecords = new JLabel();
        browsepanel.add(cc.numberofrecords);
        browsepanel.add(cc.btnFirst);
        browsepanel.add(cc.btnPrevious);
        browsepanel.add(cc.btnrefresh);
        browsepanel.add(cc.btnNext);
        browsepanel.add(cc.btnLast);
    }
}
