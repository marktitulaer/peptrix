package pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing;

import pharmaceuticals.nl.peptrix.Controller;

public class TaxonomyFasta {

	Controller cc;

	public TaxonomyFasta(Controller cc) {
		this.cc = cc;
	}

	public String generateTaxonomyFastaFile(String found_ms2_database, String string_Taxonomy) {

		String taxonomyFastaFileName = cc.userhome + cc.fileSeparator + string_Taxonomy.replaceAll(" ", "_") + ".fasta";

		return taxonomyFastaFileName;

	}

}
