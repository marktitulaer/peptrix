package pharmaceuticals.nl.peptrix.gui.filechooser;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class PanelTop {
    JPanel toppanel;
    FileChooserEquipment filechooserequipment;

    public PanelTop(Controller cc) {
        filechooserequipment = new FileChooserEquipment(cc);
        FileChooserExperiment filechooserexperiment = new FileChooserExperiment(cc);
        JPanel panelequipment = filechooserequipment.getpanelequipment();
        JPanel panelexperiment = filechooserexperiment.getpanelexperiment();
        JPanel buttonpanel = new JPanel();
        buttonpanel.setLayout(new BorderLayout());
        buttonpanel.add(panelequipment, BorderLayout.NORTH);
        buttonpanel.add(panelexperiment, BorderLayout.CENTER);
        ScrollPane buttonscrollpane = new ScrollPane();
        buttonscrollpane.setSize(new Dimension(320, 225));
        buttonscrollpane.add(buttonpanel);
        toppanel = new JPanel();
        toppanel.add(buttonscrollpane);
    }

    public JPanel gettoppanel() {
        return toppanel;
    }
}
