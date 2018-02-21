package pharmaceuticals.nl.peptrix.gui.allocation.center;

import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Allocationcenterpanelsouth {
    Controller cc;
    JPanel jpanel;

    public Allocationcenterpanelsouth(Controller cc) {
        this.cc = cc;
        jpanel = new JPanel();
        jpanel.setBackground(cc.getcolor_empty_frames());
    }

    public JPanel getpanel() {
        return jpanel;
    }
}
