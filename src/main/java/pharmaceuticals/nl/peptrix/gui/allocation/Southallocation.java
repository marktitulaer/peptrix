package pharmaceuticals.nl.peptrix.gui.allocation;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class Southallocation {

	JPanel allocationsouth;

	Box.Filler vFill;

	Dimension fill;

	BoxLayout boxlayout;

	public Southallocation(Controller cc) {
		allocationsouth = new JPanel();
		fill = new Dimension(25, 30);
		vFill = new Box.Filler(fill, fill, fill);
		allocationsouth.add(vFill);
		allocationsouth.setBackground(cc.color_empty_frames);
		boxlayout = new BoxLayout(allocationsouth, BoxLayout.X_AXIS);
	}

	public JPanel getpanel() {
		return allocationsouth;
	}

}
