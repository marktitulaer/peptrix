package pharmaceuticals.nl.peptrix.gui.filechooser.filters;

import java.io.File;

public class MsconvertFilter extends SuffixAwareFilter {

	String suffix;

	String searchname = "msconvert.exe";

	boolean readw_in_name = false;

	@Override
	public boolean accept(File f) {
		suffix = getSuffix(f);
		readw_in_name = false;
		if (f.getName().trim().toLowerCase().indexOf(searchname.toLowerCase()) == 0) {
			readw_in_name = true;
		}
		if (suffix != null) {
			return super.accept(f) || readw_in_name;
		} else {
			return super.accept(f);
		}
	}

	@Override
	public String getDescription() {
		return searchname;
	}
}
