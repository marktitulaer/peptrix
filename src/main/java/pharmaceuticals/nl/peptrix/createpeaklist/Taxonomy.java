package pharmaceuticals.nl.peptrix.createpeaklist;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import pharmaceuticals.nl.peptrix.Controller;

public class Taxonomy {

	String program_directory;

	String taxonomy_file_name;

	String linefeed = "\n";

	String taxonomycontent;

	FileDescriptor fd;

	boolean taxonomy_file_created;

	public Taxonomy(String found_ms2_database, String string_Taxonomy, String found_xtandem_program, String program,
			Controller cc) {
		taxonomy_file_created = false;
		taxonomy_file_name = "";
		program_directory = "";
		taxonomycontent = "";
		try {
			try {
				program_directory = found_xtandem_program.substring(0, found_xtandem_program.indexOf(program));
			} catch (Exception ex2) {
			}
			if (!program_directory.trim().equalsIgnoreCase("")) {

				taxonomy_file_name = cc.userhome + cc.fileSeparator + "taxonomy.xml";

				if ((!string_Taxonomy.trim().equalsIgnoreCase(""))
						&& (!found_ms2_database.trim().equalsIgnoreCase(""))) {
					taxonomycontent = "<?xml version=\"1.0\"?>" + linefeed
							+ "<bioml label=\"x! taxon-to-file matching list\">" + linefeed + "<taxon label=\""
							+ string_Taxonomy.trim() + "\">" + linefeed + "<file format=\"peptide\" URL=\""
							+ found_ms2_database.trim() + "\" />" + linefeed + "</taxon>" + linefeed + "</bioml>";
				}
			}
			if (!taxonomycontent.trim().equalsIgnoreCase("")) {
				try {
					FileOutputStream os = new FileOutputStream(taxonomy_file_name);
					fd = os.getFD();
					byte[] data = taxonomycontent.getBytes();
					os.write(data);
					os.flush();
					fd.sync();
					os.close();
					taxonomy_file_created = true;
				} catch (IOException e) {
					taxonomy_file_created = false;
				}
			}
		} catch (Exception ex) {
		}
	}

	public String get_taxonomy_file_name() {
		return taxonomy_file_name;
	}
}
