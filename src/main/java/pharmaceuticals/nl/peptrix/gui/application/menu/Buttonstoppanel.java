package pharmaceuticals.nl.peptrix.gui.application.menu;

import java.awt.*;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Buttonstoppanel {

    JPanel jpanel;

    public Buttonstoppanel(Controller cc) {
        FlowLayout layoutMgr = new FlowLayout(FlowLayout.LEFT, 10, 5);
        jpanel = new JPanel();
        jpanel.setLayout(layoutMgr);
        jpanel.setBackground(cc.topmenu);
        cc.btnGroup.addActionListener(cc);
        jpanel.add(cc.btnGroup);
        cc.btnEquipment.addActionListener(cc);
        jpanel.add(cc.btnEquipment);
        cc.btnExperiment.addActionListener(cc);
        jpanel.add(cc.btnExperiment);
        cc.btnSample_.addActionListener(cc);
        jpanel.add(cc.btnSample_);
        cc.btnResult.addActionListener(cc);
        jpanel.add(cc.btnResult);
    }

    public JPanel getPanel() {
        return jpanel;
    }

}
