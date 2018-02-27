package pharmaceuticals.nl.peptrix;

import java.io.File;
import javax.swing.JFileChooser;

import pharmaceuticals.nl.peptrix.gui.filechooser.filters.*;
import pharmaceuticals.nl.peptrix.service.SystemCodeItemService;
import pharmaceuticals.nl.peptrix.serviceimpl.SystemCodeItemServiceImpl;

public class SearchFile {

	SystemCodeItemService systemcodeservice;

	Controller cc;

	File testfile;

	File file_on_disk;

	JFileChooser filechooser;

	ReadwFilter readwfilter;

	MsconvertFilter msconvertfilter;

	RtermFilter rtermfilter;

	FastaFilter fastafilter;

	TandemFilter tandemfilter;

	Object[][] odata;

	String[] list_of_readw_directories;

	String query = "";

	String systemcode_id;

	String file_to_search;

	String found_name;

	public long byteSize;

	int result;

	boolean program_directory_found = false;

	boolean cancel = false;

	public SearchFile(Controller cc) {
		this.cc = cc;
	}

	public String return_file(String file_to_search) {
		this.file_to_search = file_to_search;
		cancel = false;
		create_system_tables();
		search_file();
		return found_name;
	}

	public String return_file_on_wildcard(String wildcard) {
		this.file_to_search = wildcard;
		cancel = false;
		create_system_tables();
		search_file_wildcard();
		// System.out.println(" found_name " + found_name);
		return found_name;
	}

	private void create_system_tables() {
		cc.jdbcconnection.resetconnection();
		systemcodeservice = new SystemCodeItemServiceImpl(cc);
		odata = systemcodeservice.getdirectories();
		if (odata != null) {
			if (Integer.parseInt(odata[0][0].toString().trim()) == 0) {
				systemcodeservice.insertsystemdirectories();
			}
		}
		cc.jdbcconnection.resetconnection();
		odata = systemcodeservice.getdirectories2();
		if (odata != null) {
			systemcode_id = odata[0][0].toString().trim();
		}
		cc.jdbcconnection.resetconnection();
		odata = systemcodeservice.filecount(file_to_search);
		if (odata != null) {
			if (Integer.parseInt(odata[0][0].toString().trim()) == 0) {
				systemcodeservice.insertfile(systemcode_id, file_to_search);
			}
		}
		cc.jdbcconnection.resetconnection();
		odata = systemcodeservice.searchfile(file_to_search);
		if (odata != null) {
			if (odata.length > 0) {
				list_of_readw_directories = new String[odata.length];
				for (int i = 0; i < odata.length; i++) {
					list_of_readw_directories[i] = odata[i][0].toString();
				}
			}
		}
	}

	private void search_file() {
		program_directory_found = false;
		found_name = "";
		while ((!program_directory_found) && (!cancel)) {
			if (list_of_readw_directories != null) {
				search_file_with_system_tables();
			}
			if (!program_directory_found) {
				search_file_with_filechooser();
				if (program_directory_found) {
					create_system_tables();
					search_file_with_system_tables();
				}
			}
		}
		if (testfile != null) {
			byteSize = testfile.length();
		}
	}

	private void search_file_wildcard() {
		program_directory_found = false;
		found_name = "";
		while ((!program_directory_found) && (!cancel)) {
			if (list_of_readw_directories != null) {
				// search_file_with_system_tables();
				search_file_with_system_tables_wildcard();
			}
			if (!program_directory_found) {
				search_file_with_filechooser_wildcard();
				if (program_directory_found) {
					create_system_tables();
					// search_file_with_system_tables();
					search_file_with_system_tables_wildcard();
				}
			}
		}
		if (testfile != null) {
			byteSize = testfile.length();
		}
	}

	private void search_file_with_system_tables() {
		if (list_of_readw_directories != null) {
			for (int i = 0; i < list_of_readw_directories.length; i++) {
				testfile = new File(list_of_readw_directories[i]);
				if (testfile.exists()) {
					if (testfile.getName().trim().equalsIgnoreCase(file_to_search.trim())) {
						program_directory_found = true;
						found_name = backlashReplace(testfile.getAbsolutePath().trim());
						i = list_of_readw_directories.length;
					}
				}
			}
		}
	}

	private void search_file_with_system_tables_wildcard() {
		if (list_of_readw_directories != null) {
			for (int i = 0; i < list_of_readw_directories.length; i++) {
				testfile = new File(list_of_readw_directories[i]);
				if (testfile.exists()) {
					// if (testfile.getName().trim().equalsIgnoreCase(file_to_search.trim())) {
					if (testfile.getName().trim().indexOf(file_to_search.trim()) > -1) {
						program_directory_found = true;
						found_name = backlashReplace(testfile.getAbsolutePath().trim());
						i = list_of_readw_directories.length;
					}
				}
			}
		}
	}

	private void search_file_with_filechooser() {
		if (filechooser == null) {
			filechooser = new JFileChooser();
			filechooser.setDialogTitle("Search " + file_to_search);
		}
		if (file_to_search.toLowerCase().indexOf("readw") > -1) {
			if (readwfilter == null) {
				readwfilter = new ReadwFilter();
			}
			filechooser.addChoosableFileFilter(readwfilter);
		} else if (file_to_search.toLowerCase().indexOf("msconvert") > -1) {
			if (rtermfilter == null) {
				msconvertfilter = new MsconvertFilter();
			}
			filechooser.addChoosableFileFilter(msconvertfilter);
		} else if (file_to_search.toLowerCase().indexOf("r") > -1) {
			if (rtermfilter == null) {
				rtermfilter = new RtermFilter();
			}
			filechooser.addChoosableFileFilter(rtermfilter);
		} else if (file_to_search.toLowerCase().indexOf("tandem") > -1) {
			if (tandemfilter == null) {
				tandemfilter = new TandemFilter();
			}
			filechooser.addChoosableFileFilter(tandemfilter);
		} else if (file_to_search.toLowerCase().indexOf(".fasta") > -1) {
			if (fastafilter == null) {
				fastafilter = new FastaFilter();
			}
			filechooser.addChoosableFileFilter(fastafilter);
		} else {
			if (readwfilter == null) {
				readwfilter = new ReadwFilter();
			}
			filechooser.addChoosableFileFilter(readwfilter);
		}
		result = filechooser.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION) {
			program_directory_found = false;
			cancel = true;
		}
		if (result == JFileChooser.APPROVE_OPTION) {
			file_on_disk = filechooser.getSelectedFile();
			if (file_on_disk != null) {
				if (true) {
					if (file_on_disk.getName().trim().equalsIgnoreCase(file_to_search)) {
						store_filename_and_path(file_on_disk.getAbsolutePath());
						program_directory_found = true;
					}
				}
			}
		}
	}

	private void search_file_with_filechooser_wildcard() {
		if (filechooser == null) {
			filechooser = new JFileChooser();
			filechooser.setDialogTitle("Search " + file_to_search);
		}
		if (file_to_search.toLowerCase().indexOf("readw") > -1) {
			if (readwfilter == null) {
				readwfilter = new ReadwFilter();
			}
			filechooser.addChoosableFileFilter(readwfilter);
		} else if (file_to_search.toLowerCase().indexOf("msconvert") > -1) {
			if (rtermfilter == null) {
				msconvertfilter = new MsconvertFilter();
			}
			filechooser.addChoosableFileFilter(msconvertfilter);
		} else if (file_to_search.toLowerCase().indexOf("r") > -1) {
			if (rtermfilter == null) {
				rtermfilter = new RtermFilter();
			}
			filechooser.addChoosableFileFilter(rtermfilter);
		} else if (file_to_search.toLowerCase().indexOf("tandem") > -1) {
			if (tandemfilter == null) {
				tandemfilter = new TandemFilter();
			}
			filechooser.addChoosableFileFilter(tandemfilter);

		} else if (file_to_search.toLowerCase().indexOf(".fasta") > -1) {
			if (fastafilter == null) {
				fastafilter = new FastaFilter();
			}
			filechooser.addChoosableFileFilter(fastafilter);
		} else {
			if (readwfilter == null) {
				readwfilter = new ReadwFilter();
			}
			filechooser.addChoosableFileFilter(readwfilter);
		}
		result = filechooser.showOpenDialog(null);
		if (result == JFileChooser.CANCEL_OPTION) {
			program_directory_found = false;
			cancel = true;
		}
		if (result == JFileChooser.APPROVE_OPTION) {
			file_on_disk = filechooser.getSelectedFile();
			if (file_on_disk != null) {
				if (true) {
					// if (file_on_disk.getName().trim().equalsIgnoreCase(file_to_search)) {
					if (file_on_disk.getName().trim().indexOf(file_to_search) > -1) {
						store_filename_and_path(file_on_disk.getAbsolutePath());
						// System.out.println(" file_on_disk.getAbsolutePath() " +
						// file_on_disk.getAbsolutePath());
						program_directory_found = true;
					}
				}
			}
		}
	}

	private void store_filename_and_path(String filename_and_path) {
		filename_and_path = backlashReplace(filename_and_path);
		cc.jdbcconnection.resetconnection();
		systemcodeservice = new SystemCodeItemServiceImpl(cc);
		odata = systemcodeservice.searchdirectories(file_to_search);
		if (odata != null) {
			systemcodeservice.insertitemvalue(odata[0][0].toString(), filename_and_path);
		}
	}

	private String backlashReplace(String myStr) {
		StringBuilder result = new StringBuilder();
		char character;
		for (int i = 0; i < myStr.length(); i++) {
			character = myStr.charAt(i);
			if (character == '\\') {
				result.append("/");
			} else {
				result.append(character);
			}
		}
		return result.toString();
	}

}
