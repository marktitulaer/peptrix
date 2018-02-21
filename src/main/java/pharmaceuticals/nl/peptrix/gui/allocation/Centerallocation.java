package pharmaceuticals.nl.peptrix.gui.allocation;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.allocation.center.Centerpanelnorthallocation;
import pharmaceuticals.nl.peptrix.gui.allocation.center.Panelallocationcentertotal;

public class Centerallocation {

    public Panelallocationcentertotal panelallocationcentertotal;

    Centerpanelnorthallocation centerpanelnorthallocation;

    JPanel jpanel;

    public Centerallocation(Controller cc) {
        centerpanelnorthallocation = new Centerpanelnorthallocation(cc);
        JPanel allocationcenterpanelnorth = centerpanelnorthallocation.getpanel();
        panelallocationcentertotal = new Panelallocationcentertotal(cc);
        jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.add(allocationcenterpanelnorth, BorderLayout.NORTH);
        jpanel.add(panelallocationcentertotal.getscrollPane(), BorderLayout.CENTER);
    }

    public JPanel getpanel() {
        return jpanel;
    }

    public void fill_panels_with_fields() {
        panelallocationcentertotal.fill_panels_with_fields();
    }

    public void fillbrowsepanel() {
        centerpanelnorthallocation.fillbrowsepanel();
    }

    public void resetscrollposition() {
        panelallocationcentertotal.resetscrollposition();
    }
}
