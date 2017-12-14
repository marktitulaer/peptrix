package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Centerpanelnorthtotalcreatematrix {

	JPanel centerpanelnorthtotal;

	public CenterpanelnorthCreatematrix centerpanelnorthcreatematrix;

	JPanel creatematrixcenterpanelnorth;

	public JPanel dummypanel;

	public Centerpanelnorthtotalcreatematrix(Controller cc) {
		centerpanelnorthcreatematrix = new CenterpanelnorthCreatematrix(cc);
		creatematrixcenterpanelnorth = centerpanelnorthcreatematrix.getpanel();
		dummypanel = new JPanel();
		centerpanelnorthtotal = new JPanel();
		centerpanelnorthtotal.setLayout(new BorderLayout());
		centerpanelnorthtotal.add(creatematrixcenterpanelnorth, BorderLayout.WEST);
		centerpanelnorthtotal.add(dummypanel, BorderLayout.CENTER);
	}

	public void setBackground(Color color) {
		centerpanelnorthcreatematrix.setBackground(color);
		dummypanel.setBackground(color);
		creatematrixcenterpanelnorth.setBackground(color);
	}

	public void fill_panels_with_fields_() {
		centerpanelnorthcreatematrix.fill_panels_with_fields_();
	}

	public JPanel getpanel() {
		return centerpanelnorthtotal;
	}

}
