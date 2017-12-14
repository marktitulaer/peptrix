package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;

import pharmaceuticals.nl.peptrix.Controller;

public class CreateMenu extends MenuBar {

	public CreateMenu(Controller cc) {
		Menu mFile = new Menu("File");
		this.add(mFile);
		mFile.add(cc.Import);
		cc.Import.addActionListener(cc);
		Menu mSystem = new Menu("System");
		this.add(mSystem);
		mSystem.add(cc.Code);
		cc.Code.addActionListener(cc);
	}

}
