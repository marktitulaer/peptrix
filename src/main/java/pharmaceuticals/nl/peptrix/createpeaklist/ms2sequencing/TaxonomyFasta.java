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
		String testline = "";
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
				testline = line.toLowerCase();
				if (testline.indexOf(">") > -1) {
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
					block = testline + "\n";
				} else {
					block = block + testline + "\n";
				}
			}
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
		return taxonomyFastaFileName;
	}

}
