package pharmaceuticals.nl.peptrix.gui.creatematrix;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class PanelCreateMatrixSouth {

	JPanel creatematrixsouth;

	public PanelCreateMatrixSouth(Controller cc) {
		creatematrixsouth = new JPanel();
		creatematrixsouth.setBackground(cc.color_empty_frames);
		BoxLayout boxlayout = new BoxLayout(creatematrixsouth, BoxLayout.X_AXIS);
		creatematrixsouth.setLayout(boxlayout);
		Dimension fill = new Dimension(10, 30);
		Box.Filler vFill = new Box.Filler(fill, fill, fill);
		creatematrixsouth.add(vFill);
	}

	public JPanel getpanel() {
		return creatematrixsouth;
	}

	public void setbackground(Color color) {
		creatematrixsouth.setBackground(color);
	}

}
