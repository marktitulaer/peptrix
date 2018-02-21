package pharmaceuticals.nl.peptrix.fileimport;

import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.createpeaklist.Filetypes;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.service.EquipmentService;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.EquipmentServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.gui.FileChooser;

public class GetFiles {


    ImportFiles importfiles;

    Controller cc;

    Progress progress;

    int resultfilechooser;

    Filetypes filetypesfilechooser;

    Vector<File[]> filevectorfilechooser = new Vector<File[]>();

    FileChooser filechooser;

    ResultService resultService;

    EquipmentService equipmentService;

    public GetFiles(Controller cc) {
        this.cc = cc;
        filetypesfilechooser = new Filetypes();

        resultService = new ResultServiceImpl(cc);
        equipmentService = new EquipmentServiceImpl(cc);
    }

    public void getFiles() {

        equipmentService.show_equipment();
        filechooser = new FileChooser(cc);
        String InvalidFile;
        String latestexperimentid = "-1";

        progress = new Progress();
        ScanFilesandDirs addfilestofilevector = new ScanFilesandDirs(progress);
        cc.newradio.setSelected(cc.isnewexperiment);
        cc.disablefieldsexperiment(cc.isnewexperiment);
        resultfilechooser = filechooser.showOpenDialog();
        if (resultfilechooser == JFileChooser.CANCEL_OPTION) {
            filechooser.setVisible(false);
            filechooser.dispose();
            return;
        }
        if (cc.equipmentid.getText().trim().equals("") || cc.inputexperimentname.getText().trim().equals("")
                || cc.inputexperimentid.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(filechooser, "Choose equipment and experiment");
            getFiles();
            return;
        }
        String filtertypefileChooser = null;
        FileFilter filefilterfileChooser = filechooser.getFileFilter();
        if (filefilterfileChooser.equals(filechooser.textfilter) == true) {
            filtertypefileChooser = "txt";
        }
        if (filefilterfileChooser.equals(filechooser.fidfilter) == true) {
            filtertypefileChooser = "fid";
        }
        if (filefilterfileChooser.equals(filechooser.xmlfilter) == true) {
            filtertypefileChooser = "xml";
        }
        if (filefilterfileChooser.equals(filechooser.mzxmlfilter) == true) {
            filtertypefileChooser = "mzxml";
        }
        if (filefilterfileChooser.equals(filechooser.rawfilter) == true) {
            filtertypefileChooser = "raw";
        }
        String chosen_filterfileChooser;
        if (filtertypefileChooser != null) {
            chosen_filterfileChooser = filtertypefileChooser;
        } else {
            chosen_filterfileChooser = "empty";
        }
        filetypesfilechooser.can_be_processed_filetypes(Integer.parseInt(cc.equipmentid.getText()),
                filtertypefileChooser);
        if (!filetypesfilechooser.can_be_processed_filetypes(Integer.parseInt(cc.equipmentid.getText()),
                filtertypefileChooser)) {
            JOptionPane.showMessageDialog(filechooser,
                    "Filetype " + chosen_filterfileChooser + " can not be processed for this equipment");
            getFiles();
            return;
        }
        File[] fileNamefileChooser = filechooser.jfileChooser.getSelectedFiles();
        boolean invalidfilefileChooser = false;
        for (int i = 0; i <= (fileNamefileChooser.length - 1); i++) {
            if ((!fileNamefileChooser[i].isDirectory()) && (!fileNamefileChooser[i].isFile())) {
                invalidfilefileChooser = true;
                InvalidFile = "Invalid File Name " + fileNamefileChooser[i].getAbsolutePath();
                JOptionPane.showMessageDialog(filechooser, InvalidFile, "Invalid File Name", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (invalidfilefileChooser) {
            filechooser.setVisible(false);
            filechooser.dispose();
            fileNamefileChooser = null;
        }
        if ((fileNamefileChooser[0] == null) || (fileNamefileChooser[0].getName().equals(""))) {
            JOptionPane.showMessageDialog(filechooser, "Invalid File Name", "Invalid File Name",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        if (fileNamefileChooser != null) {
            progress.init("Collecting files", 70);
            if (cc.isnewexperiment) {
                latestexperimentid = cc.createnewexperiment.createnewexperiment(cc.inputexperimentname.getText(),
                        cc.equipmentid.getText().trim());


                cc.inputexperimentidfilechooser = resultService.newexperimentid();
            } else {
                cc.inputexperimentidfilechooser = cc.inputexperimentid.getText().trim();
            }
            filevectorfilechooser = addfilestofilevector.search_files_in_directories(fileNamefileChooser);
            importfiles = new ImportFiles();
            importfiles.processfiles(cc, filevectorfilechooser, filtertypefileChooser, cc.inputexperimentidfilechooser,
                    cc.isnewexperiment, latestexperimentid, cc.existingexperiment.getExperimentyear(), progress,
                    cc.generatesamplecodes());
            progress.close();
        }
        filechooser.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void disableexperimentfields() {
        cc.isnewexperiment = false;
        cc.disablefieldsexperiment(cc.isnewexperiment);
    }

    public void enableexperimentfields() {
        cc.isnewexperiment = true;
        cc.disablefieldsexperiment(cc.isnewexperiment);
    }
}
