package pharmaceuticals.nl.peptrix.gui.creatematrix;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ScrollPane;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.creatematrix.center.Centerpanelnorthtotalcreatematrix;
import pharmaceuticals.nl.peptrix.gui.creatematrix.center.CenterpanelsouthCreatematrix;

public class Creatematrixcenter {

    public Centerpanelnorthtotalcreatematrix centerpanelnorthtotalcreatematrix;

    public CenterpanelsouthCreatematrix centerpanelsouthcreatematrix;

    public ScrollPane creatematrixcenter;

    public Creatematrixcenter(Controller cc) {
        centerpanelnorthtotalcreatematrix = new Centerpanelnorthtotalcreatematrix(cc);
        JPanel centerpanelnorthtotal = centerpanelnorthtotalcreatematrix.getpanel();
        centerpanelsouthcreatematrix = new CenterpanelsouthCreatematrix(cc);
        JPanel creatematrixcenterpanelsouthnew = centerpanelsouthcreatematrix.getpanel();
        JPanel creatematrixcenterpanel = new JPanel();
        creatematrixcenterpanel.setLayout(new BorderLayout());
        creatematrixcenterpanel.add(centerpanelnorthtotal, BorderLayout.NORTH);
        creatematrixcenterpanel.add(creatematrixcenterpanelsouthnew, BorderLayout.CENTER);
        creatematrixcenter = new ScrollPane();
        creatematrixcenter.add(creatematrixcenterpanel);
    }

    public ScrollPane returnscrollPane() {
        return creatematrixcenter;
    }

    public void setbackground(Color color) {
        centerpanelnorthtotalcreatematrix.setBackground(color);
        centerpanelsouthcreatematrix.setbackground(color);
    }

    public void fill_panels_with_fields_() {
        centerpanelnorthtotalcreatematrix.fill_panels_with_fields_();
    }

    public JPanel getcreatematrixcenterpanelsouth() {
        return centerpanelsouthcreatematrix.getcreatematrixcenterpanelsouth();
    }

}
