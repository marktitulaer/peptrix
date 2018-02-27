package pharmaceuticals.nl.peptrix.fileimport;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.gui.filechooser.filters.SuffixAwareFilter;

import com.enterprisedt.net.ftp.*;

public class FileTransfer {

	SuffixAwareFilter suffixawarefilter;

	String suffix;

	String[] files_and_dirs;

	String file_or_dir;

	File[] files;

	String filename;

	File file1;

	File parent1;

	File parent2;

	File parent3;

	File parent4;

	FileInputStream fis;

	FTPClient ftp = null;

	boolean fidfile;

	StringBuffer stringbufferfilename;

	Controller cc;

	boolean choose_testvector;

	public FileTransfer(Controller cc) {
		this.cc = cc;
	}

	public void putDataFiles(String year, String expdir, Vector<File[]> filevector, boolean transfer, String filtertype,
			Progress progress) {
		try {
			if (ftp != null) {
			} else {
				try {
					ftp = new FTPClient();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			ftp.setRemoteHost(cc.ftpremotehost);
			ftp.connect();
			ftp.login(cc.ftpuser, cc.ftppassword);
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setType(FTPTransferType.BINARY);
			ftp.setTimeout(cc.ftp_longtime);
			boolean makeyear = true;
			boolean makedir = true;
			files_and_dirs = ftp.dir(".");
			for (int j = 0; j < files_and_dirs.length; j++) {
				file_or_dir = files_and_dirs[j].replaceAll("\r", "");
				if (file_or_dir.equals(year.trim())) {
					makeyear = false;
				}
			}
			if (makeyear == true) {
				ftp.mkdir(File.separator + year);
			}
			ftp.chdir(File.separator + year);
			files_and_dirs = ftp.dir();
			for (int j = 0; j < files_and_dirs.length; j++) {
				file_or_dir = files_and_dirs[j].replaceAll("\r", "");
				if (file_or_dir.equals(expdir)) {
					makedir = false;
				}
			}
			if (transfer == true) {
				if (makedir == true) {
					ftp.mkdir(File.separator + year.trim() + File.separator + expdir);
				}
				ftp.chdir(File.separator + year.trim() + File.separator + expdir);
			}
			if (transfer == true) {
				progress.setmaximum(filevector.size());
				progress.setnumberandtext(0, "Filetransfer ...   ");
				choose_testvector = true;
				filename = "";
				for (int i = 0; i <= filevector.size() - 1; i++) {
					files = null;
					files = filevector.get(i);
					if (files.length > filevector.size()) {
						choose_testvector = false;
					}
					if (!choose_testvector) {
						progress.setmaximum(files.length);
						progress.setnumberandtext(0, "Filetransfer ...   ");
					}
					for (int j = 0; j <= files.length - 1; j++) {
						suffix = null;
						suffixawarefilter = new SuffixAwareFilter();
						suffix = suffixawarefilter.getSuffix(files[j]);
						fidfile = false;
						try {
							if (files[j].length() > "fid".length()) {
								if (files[j].getName().toString().trim()
										.substring(files[j].getName().toString().trim().length() - "fid".length(),
												files[j].getName().toString().trim().length())
										.equalsIgnoreCase("fid")) {
									fidfile = true;
								}
							}
						} catch (Exception ex) {
						}
						if ((suffix.equals("txt") && (filtertype.equals("txt"))) || (suffix.equals("leeg") && fidfile)
								|| (suffix.equals("fid") && (filtertype.equals("fid")))
								|| (suffix.equals("acqu") && (filtertype.equals("fid")))
								|| (suffix.equals("xml") && (filtertype.equals("xml")))
								|| (suffix.equals("mzxml") && (filtertype.equals("mzxml")))
								|| (suffix.equals("raw") && (filtertype.equals("raw")))
								|| (suffix.equals("acqus") && (filtertype.equals("fid")))) {
							filename = files[j].getName();
							if (suffix.equals("fid") || suffix.equals("acqu") || suffix.equals("xml")
									|| suffix.equals("mzxml") || suffix.equals("raw") || suffix.equals("acqus")) {
								parent1 = files[j].getParentFile();
								parent2 = parent1.getParentFile();
								parent3 = parent2.getParentFile();
								parent4 = parent3.getParentFile();
								stringbufferfilename = new StringBuffer();
								if (parent4 != null && !parent4.getName().trim().equalsIgnoreCase("")) {
									stringbufferfilename.append(parent4.getName().trim() + "_");
								}
								if (parent3 != null && !parent3.getName().trim().equalsIgnoreCase("")) {
									stringbufferfilename.append(parent3.getName().trim() + "_");
								} else {
									stringbufferfilename = new StringBuffer();
								}
								if (parent2 != null && !parent2.getName().trim().equalsIgnoreCase("")) {
									stringbufferfilename.append(parent2.getName().trim() + "_");
								} else {
									stringbufferfilename = new StringBuffer();
								}
								if (parent1 != null && !parent1.getName().trim().equalsIgnoreCase("")) {
									stringbufferfilename.append(parent1.getName().trim() + "_");
								} else {
									stringbufferfilename = new StringBuffer();
								}
								stringbufferfilename.append(files[j].getName().trim());
								filename = stringbufferfilename.toString();
							} else if (suffix.equals("txt")) {
								parent1 = files[j].getParentFile();
								if (parent1.getName().trim().equalsIgnoreCase("")) {
									filename = files[j].getName().trim();
								} else {
									filename = parent1.getName().trim() + "_" + files[j].getName().trim();
								}
							}
							file1 = new File(files[j].getPath());
							fis = new FileInputStream(file1);
							if (filename.length() > 100) {
								filename = filename.substring(filename.length() - 100, filename.length());
							}
							progress.settext("Filetransfer ...   " + filename);
							ftp.put(fis, filename);
							fis.close();
						}
						if (!choose_testvector) {
							progress.setnumberandtext((j + 1), "");
						}
					}
					if (choose_testvector) {
						progress.setnumberandtext((i + 1), "");
					}
				}
			}
			ftp.quit();
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
