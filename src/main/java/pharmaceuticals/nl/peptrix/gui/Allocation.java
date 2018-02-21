package pharmaceuticals.nl.peptrix.gui;

import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.allocation.Centerallocation;
import pharmaceuticals.nl.peptrix.gui.allocation.Northallocation;
import pharmaceuticals.nl.peptrix.gui.allocation.Southallocation;

public class Allocation {
    Northallocation northallocation;
    public Centerallocation centerallocation;
    Southallocation southallocation;

    public Allocation(Controller cc) {
        northallocation = new Northallocation(cc);
        centerallocation = new Centerallocation(cc);
        southallocation = new Southallocation(cc);
    }

    public void fill_panels_with_fields() {
        centerallocation.fill_panels_with_fields();
    }

    public JPanel getpanelnorthallocation() {
        return northallocation.getpanel();
    }

    public JPanel getpanelcenterallocation() {
        return centerallocation.getpanel();
    }

    public JPanel getpanelsouthallocation() {
        return southallocation.getpanel();
    }

    public void fillbrowsepanel() {
        centerallocation.fillbrowsepanel();
    }

    public void resetscrollposition() {
        centerallocation.resetscrollposition();
    }
}
