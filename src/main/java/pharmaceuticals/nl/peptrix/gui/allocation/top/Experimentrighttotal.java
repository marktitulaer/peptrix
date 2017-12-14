package pharmaceuticals.nl.peptrix.gui.allocation.top;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Experimentrighttotal {

	JPanel panelexperimentright;

	Experimentrightpanel experimentrightpanel;

	JPanel panelexperimentrighttotal;

	JPanel dummy;

	public Experimentrighttotal(Controller cc) {
		Dimension fill2 = new Dimension(5, 135);
		Box.Filler vFill2 = new Box.Filler(fill2, fill2, fill2);
		dummy = new JPanel();
		dummy.add(vFill2);
		experimentrightpanel = new Experimentrightpanel(cc);
		panelexperimentright = experimentrightpanel.getpanel();
		panelexperimentrighttotal = new JPanel();
		panelexperimentrighttotal.setLayout(new BorderLayout());
		panelexperimentrighttotal.add(panelexperimentright, BorderLayout.CENTER);
		panelexperimentrighttotal.add(dummy, BorderLayout.SOUTH);
	}

	public JPanel getpanel() {
		return panelexperimentrighttotal;
	}

}
