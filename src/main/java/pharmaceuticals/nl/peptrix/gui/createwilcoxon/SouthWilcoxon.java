package pharmaceuticals.nl.peptrix.gui.createwilcoxon;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class SouthWilcoxon {

	JPanel creatematrixsouth;

	BoxLayout boxlayout;

	Box.Filler vFill;

	Dimension fill;

	Controller cc;

	public SouthWilcoxon(Controller cc) {
		this.cc = cc;
		creatematrixsouth = new JPanel();
		creatematrixsouth = new JPanel();
		creatematrixsouth.setBackground(cc.color_empty_frames);
		boxlayout = new BoxLayout(creatematrixsouth, BoxLayout.X_AXIS);
		creatematrixsouth.setLayout(boxlayout);
		fill = new Dimension(10, 30);
		vFill = new Box.Filler(fill, fill, fill);
		creatematrixsouth.add(vFill);
	}

	public JPanel getpanel() {
		return creatematrixsouth;
	}

}
