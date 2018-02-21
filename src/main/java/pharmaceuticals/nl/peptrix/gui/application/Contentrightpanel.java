package pharmaceuticals.nl.peptrix.gui.application;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Contentrightpanel {
    JPanel jpanel;

    public Contentrightpanel(Controller cc) {
        BorderLayout borderlayout = new BorderLayout();
        jpanel = new JPanel();
        jpanel.setLayout(borderlayout);
        jpanel.add("North", cc.pTop);
        jpanel.add("Center", cc.pBody);
    }

    public JPanel getPanel() {
        return jpanel;
    }
}
