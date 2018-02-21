package pharmaceuticals.nl.peptrix.gui.allocation;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.allocation.top.Experimentleftpanelallocation;
import pharmaceuticals.nl.peptrix.gui.allocation.top.Experimentrighttotal;

public class Northallocation {

    JPanel allocationnorthpanel;

    public Northallocation(Controller cc) {
        Experimentleftpanelallocation experimentleftpanel = new Experimentleftpanelallocation(cc);
        JPanel panelexperimentleft = experimentleftpanel.getpanel();
        Experimentrighttotal experimentrighttotal = new Experimentrighttotal(cc);
        JPanel panelexperimentrighttotal = experimentrighttotal.getpanel();
        FlowLayout layoutMgr = new FlowLayout(FlowLayout.LEFT, 10, 5);
        allocationnorthpanel = new JPanel();
        allocationnorthpanel.setLayout(layoutMgr);
        allocationnorthpanel.add(panelexperimentleft);
        allocationnorthpanel.add(panelexperimentrighttotal);
    }

    public JPanel getpanel() {
        return allocationnorthpanel;
    }
}
