package pharmaceuticals.nl.peptrix.gui.application;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.application.menu.Buttonsleftpanel;

public class FramePanel {
    Contentrightpanel contentrightpanel;
    BorderLayout borderlayout = new BorderLayout();
    JPanel panel = new JPanel();

    public FramePanel(Controller cc) {
        Buttonsleftpanel buttonsleftpanel = new Buttonsleftpanel(cc);
        cc.pLeft = buttonsleftpanel.getPanel();
        Contentrightpanel contentrightpanel = new Contentrightpanel(cc);
        JPanel jpanel = contentrightpanel.getPanel();
        panel.setLayout(borderlayout);
        panel.add("West", cc.pLeft);
        panel.add("Center", jpanel);
    }

    public JPanel getPanel() {
        return panel;
    }
}
