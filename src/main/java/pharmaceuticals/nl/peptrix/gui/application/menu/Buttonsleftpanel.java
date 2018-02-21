package pharmaceuticals.nl.peptrix.gui.application.menu;

import java.awt.*;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Buttonsleftpanel {

    JPanel jpanel;

    public Buttonsleftpanel(Controller cc) {
        JPanel leftcenter = new JPanel();
        leftcenter.setBackground(cc.menubackgroundcolor);
        Buttonsleftpaneltop buttonsleftpaneltop = new Buttonsleftpaneltop(cc);
        JPanel lefttop = buttonsleftpaneltop.getPanel();
        jpanel = new JPanel();
        jpanel.setLayout(new BorderLayout());
        jpanel.add(leftcenter, BorderLayout.CENTER);
        jpanel.add(lefttop, BorderLayout.NORTH);
    }

    public JPanel getPanel() {
        return jpanel;
    }
}
