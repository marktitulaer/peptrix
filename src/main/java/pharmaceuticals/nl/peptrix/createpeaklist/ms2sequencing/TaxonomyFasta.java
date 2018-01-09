package pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class TaxonomyFasta {

	Controller cc;

	ExportFileToDisk exportfiletodisk;

	String line;

	String testline;

	public TaxonomyFasta(Controller cc, ExportFileToDisk exportfiletodisk) {
		this.cc = cc;
		this.exportfiletodisk = exportfiletodisk;
	}

	public String generateTaxonomyFastaFile(String found_ms2_database, String string_Taxonomy) {

		String taxonomyFastaFileName = cc.userhome + cc.fileSeparator + string_Taxonomy.replaceAll(" ", "_") + ".fasta";

		boolean taxonomyFastaFileCreated = exportfiletodisk.exportcompletefilename(taxonomyFastaFileName,
				"".getBytes());

		System.out.println("taxonomyFastaFileCreated " + taxonomyFastaFileCreated);

		try {
			BufferedReader in = new BufferedReader(new FileReader(found_ms2_database));
			while ((line = in.readLine()) != null) {
				System.out.println("twee");
				testline = line.toLowerCase();
				System.out.println("  testline " + testline);
			}
			System.out.println("file geopend ");
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
