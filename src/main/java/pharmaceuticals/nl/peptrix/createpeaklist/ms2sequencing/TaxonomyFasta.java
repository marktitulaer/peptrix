package pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class TaxonomyFasta {
    Controller cc;
    ExportFileToDisk exportfiletodisk;
    FileOutputStream taxonomyFastaFile;

    public TaxonomyFasta(Controller cc, ExportFileToDisk exportfiletodisk) {
        this.cc = cc;
        this.exportfiletodisk = exportfiletodisk;
    }

    public String generateTaxonomyFastaFile(String found_ms2_database, String string_Taxonomy) {
        String taxonomyFastaFileName = cc.userhome + cc.fileSeparator + string_Taxonomy.replaceAll(" ", "_") + ".fasta";
        boolean taxonomyFastaFileCreated = exportfiletodisk.exportcompletefilename(taxonomyFastaFileName,
                "".getBytes());
        String line = "";
        String block = "";
        try {
            taxonomyFastaFile = new FileOutputStream(taxonomyFastaFileName);
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            BufferedReader in = new BufferedReader(new FileReader(found_ms2_database));
            while ((line = in.readLine()) != null) {
                if (line.indexOf(">") > -1) {
                    if (block.toLowerCase().indexOf(string_Taxonomy.toLowerCase()) > -1) {
                        try {
                            taxonomyFastaFile.write(block.getBytes());
                            taxonomyFastaFile.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    block = line + "\n";
                } else {
                    block = block + line + "\n";
                }
            }
            if (block.toLowerCase().indexOf(string_Taxonomy.toLowerCase()) > -1) {
                try {
                    taxonomyFastaFile.write(block.getBytes());
                    taxonomyFastaFile.flush();
                } catch (Exception e) {
                    if (cc.debugmode) {
                        e.printStackTrace();
                    } else {
                        JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            taxonomyFastaFile.close();
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        taxonomyFastaFileName = taxonomyFastaFileName.replace("\\", "/");
        return taxonomyFastaFileName;
    }
}
