package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;

import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.creatematrix.Creatematrixcenter;
import pharmaceuticals.nl.peptrix.gui.creatematrix.Creatematrixnorth;
import pharmaceuticals.nl.peptrix.gui.creatematrix.PanelCreateMatrixSouth;

public class Creatematrix {

    Controller cc;

    Creatematrixnorth northcreatematrix;

    public Creatematrixcenter creatematrix_center;

    PanelCreateMatrixSouth panelcreatematrixsouth;

    public Creatematrix(Controller cc) {
        this.cc = cc;
        northcreatematrix = new Creatematrixnorth(cc);
        creatematrix_center = new Creatematrixcenter(cc);
        panelcreatematrixsouth = new PanelCreateMatrixSouth(cc);
    }

    public void displayframes() {
        cc.pBody.setLayout(new BorderLayout());
        cc.pBody.removeAll();
        cc.pBody.add(northcreatematrix.getpanel(), BorderLayout.PAGE_START);
        cc.pBody.add(creatematrix_center.returnscrollPane(), BorderLayout.CENTER);
        cc.pBody.add(panelcreatematrixsouth.getpanel(), BorderLayout.PAGE_END);
    }

    public void setbackground(Color color) {
        creatematrix_center.setbackground(color);
        panelcreatematrixsouth.setbackground(color);
    }

    public void fill_panels_with_fields() {
        creatematrix_center.fill_panels_with_fields_();
    }

    public JPanel getcreatematrixcenterpanelsouth() {
        return creatematrix_center.getcreatematrixcenterpanelsouth();
    }

}
