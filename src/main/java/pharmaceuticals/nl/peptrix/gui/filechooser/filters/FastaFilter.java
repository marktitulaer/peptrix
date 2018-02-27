package pharmaceuticals.nl.peptrix.gui.filechooser.filters;

import java.io.File;

public class FastaFilter extends SuffixAwareFilter {

	String suffix;

	String searchname = ".fasta";

	boolean fasta_in_name = false;

	@Override
	public boolean accept(File f) {
		suffix = getSuffix(f);
		fasta_in_name = false;
		if (f.getName().trim().toLowerCase().indexOf(searchname.toLowerCase()) > -1) {
			fasta_in_name = true;
		}
		if (suffix != null) {
			return super.accept(f) || fasta_in_name;
		} else {
			return super.accept(f);
		}
	}

	@Override
	public String getDescription() {
		return searchname;
	}
}
