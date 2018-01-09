package pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class TaxonomyFasta {

	Controller cc;
	
	ExportFileToDisk exportfiletodisk;

	public TaxonomyFasta(Controller cc, ExportFileToDisk exportfiletodisk) {
		this.cc = cc;
		this.exportfiletodisk = exportfiletodisk;
	}

	public String generateTaxonomyFastaFile(String found_ms2_database, String string_Taxonomy) {

		String taxonomyFastaFileName = cc.userhome + cc.fileSeparator + string_Taxonomy.replaceAll(" ", "_") + ".fasta";

		boolean taxonomyFastaFileCreated = exportfiletodisk
				.exportcompletefilename(taxonomyFastaFileName, "".getBytes());
		
		System.out.println("taxonomyFastaFileCreated " + taxonomyFastaFileCreated);
	
		
		return taxonomyFastaFileName;

	}

}
