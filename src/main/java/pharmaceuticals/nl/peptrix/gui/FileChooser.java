package pharmaceuticals.nl.peptrix.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.FidFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.PanelTop;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.RawFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.TextFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.XMLFilter;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.mzXMLFilter;

public class FileChooser extends JFrame {
    Controller cc;
    PanelTop paneltop;
    public JFileChooser jfileChooser;
    public TextFilter textfilter;
    public mzXMLFilter mzxmlfilter;
    public XMLFilter xmlfilter;
    public RawFilter rawfilter;
    public FidFilter fidfilter;

    public FileChooser(Controller cc) {
        this.cc = cc;
        jfileChooser = new JFileChooser();
        paneltop = new PanelTop(cc);
        jfileChooser.setAccessory(paneltop.gettoppanel());
        rawfilter = new RawFilter();
        jfileChooser.addChoosableFileFilter(rawfilter);
        textfilter = new TextFilter();
        jfileChooser.addChoosableFileFilter(textfilter);
        xmlfilter = new XMLFilter();
        jfileChooser.addChoosableFileFilter(xmlfilter);
        mzxmlfilter = new mzXMLFilter();
        jfileChooser.addChoosableFileFilter(mzxmlfilter);
        fidfilter = new FidFilter();
        jfileChooser.addChoosableFileFilter(fidfilter);
        jfileChooser.isValidateRoot();
        jfileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        jfileChooser.setMultiSelectionEnabled(true);
        jfileChooser.addPropertyChangeListener(cc);
        JPanel filechooserpanel = new JPanel();
        filechooserpanel.setLayout(new BorderLayout());
        Component[] filchoosercomponents = jfileChooser.getComponents();
        jfileChooser.removeAll();
        filechooserpanel.add(filchoosercomponents[0], BorderLayout.NORTH);
        filechooserpanel.add(filchoosercomponents[2], BorderLayout.CENTER);
        filechooserpanel.add(filchoosercomponents[3], BorderLayout.SOUTH);
        jfileChooser.add(filchoosercomponents[1], BorderLayout.EAST);
        jfileChooser.add(filechooserpanel, BorderLayout.WEST);
    }

    public int showOpenDialog() {
        return jfileChooser.showOpenDialog(this);
    }

    public FileFilter getFileFilter() {
        return jfileChooser.getFileFilter();
    }
}
