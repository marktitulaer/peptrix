package pharmaceuticals.nl.peptrix.gui.creatematrix;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.creatematrix.top.ExperimentLeftPanelCreateMatrix;

public class Creatematrixnorth {

    JPanel creatematrixnorth;

    public Creatematrixnorth(Controller cc) {
        ExperimentLeftPanelCreateMatrix experimentleftpanelcreatematrix = new ExperimentLeftPanelCreateMatrix(cc);
        JPanel panelexperimentleft = experimentleftpanelcreatematrix.getpanel();
        JPanel dummypanel = new JPanel();
        creatematrixnorth = new JPanel();
        creatematrixnorth.setLayout(new BorderLayout());
        creatematrixnorth.add(panelexperimentleft, BorderLayout.WEST);
        creatematrixnorth.add(dummypanel, BorderLayout.CENTER);
    }

    public JPanel getpanel() {
        return creatematrixnorth;
    }

}
