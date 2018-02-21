package pharmaceuticals.nl.peptrix.gui.allocation.top;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Experimentrightpanel {
    JPanel panelexperimentright;

    public Experimentrightpanel(Controller cc) {
        panelexperimentright = new JPanel();
        cc.labelrecordsnotallocated = new JLabel("not allocated :");
        panelexperimentright.setBorder(BorderFactory.createTitledBorder("Info"));
        panelexperimentright.add(cc.labelrecordsnotallocated);
    }

    public JPanel getpanel() {
        return panelexperimentright;
    }
}
