package pharmaceuticals.nl.peptrix.gui.allocation.center;

import java.awt.FlowLayout;

import javax.swing.JPanel;

public class Allocationbrowsepanel {

    JPanel jpanel;

    public Allocationbrowsepanel() {
        jpanel = new JPanel();
        FlowLayout layoutMgr = new FlowLayout(FlowLayout.LEFT, 2, 5);
        jpanel.setLayout(layoutMgr);
    }

    public JPanel getpanel() {
        return jpanel;
    }

}
