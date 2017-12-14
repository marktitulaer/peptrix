package pharmaceuticals.nl.peptrix.createpeaklist;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileDescriptor;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.xml.sax.helpers.DefaultHandler;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.SearchFile;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class Thermo_Scientific extends DefaultHandler {

	ExportFileToDisk exportfiletodisk;

	Process jproc;

	FileDescriptor fd;

	Controller cc;

	FTPClient ftp;

	ReadmzXML readmzxml;

	File file_thermo_scientific;

	JFileChooser filechooser;

	String Batchfilename2;

	String clearstatusfile;

	String cmd2;

	String message = "";

	String text;

	String Thermo_Scientific_dir;

	String emptystring = "";

	String filename = "";

	String experimentyear = "";

	String experimentnumber = "";

	String thermoscientific_filename;

	String raw_to_mzXML_executable;

	String mslevel;

	String found_raw_to_mzXML_executable_name;

	String finished_text = "finished";

	String status_file = "status.txt";

	String content;

	String found_raw_to_mzXML_executable_name2;

	int procesexitvalue;

	boolean errormessageonce = false;

	boolean isWindowsFlag = false;

	// boolean readw_directory_found = false;

	boolean notfinished;

	SearchFile objectsearchfile;

	BufferedReader reader;

	String exportname;

	int number_of_masses;

	int number_of_noise_peaks = 0;

	long readw_byteSize;

	Vector<String[]> sequensing_results_vector;

	Experiment experiment;

	public Thermo_Scientific(Controller cc, Experiment experiment, ExportFileToDisk exportfiletodisk) {
		raw_to_mzXML_executable = "ReAdW.exe";
		// if (experiment.getraw_to_mzXML().equalsIgnoreCase("readw")) {
		// raw_to_mzXML_executable = "ReAdW.exe";
		// }
		if (experiment.getraw_to_mzXML().equalsIgnoreCase("msconvert32")) {
			raw_to_mzXML_executable = "msconvert.exe";
		}
		if (experiment.getraw_to_mzXML().equalsIgnoreCase("msconvert64")) {
			raw_to_mzXML_executable = "msconvert.exe";
		}
		this.exportfiletodisk = exportfiletodisk;
		objectsearchfile = new SearchFile(cc);
		thermoscientific_filename = "thermo_scientific";
		emptystring = "";
		this.cc = cc;
		this.experiment = experiment;
		isWindowsFlag = cc.osName.startsWith("Windows");
		makedirs();
	}

	public void setobjects_null() {
		if (readmzxml != null) {
			readmzxml.setobjects_null();
			readmzxml = null;
		}
	}

	public int createpeaklist(MassSpectrometryFile massspectrometryfile) {
		String filename = massspectrometryfile.getFilename();
		String exportname = massspectrometryfile.getTmp_exportname();
		sequensing_results_vector = null;
		this.filename = filename;
		this.experimentyear = experiment.getExperimentyear();
		this.experimentnumber = String.valueOf(experiment.getExperimentid());
		this.exportname = exportname;
		if (readmzxml == null) {
			readmzxml = new ReadmzXML(cc, experiment, exportfiletodisk);
		}
		number_of_noise_peaks = 0;
		if (ftp == null) {
			try {
				ftp = new FTPClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		found_raw_to_mzXML_executable_name = objectsearchfile.return_file(raw_to_mzXML_executable);
		readw_byteSize = objectsearchfile.byteSize;
		remove_old_files();
		store_file_local();
		createbatchfile();
		execscript();
		mslevel = "1";
		number_of_masses = 0;
		number_of_masses = readmzxml.store_peak_lists(Thermo_Scientific_dir + thermoscientific_filename + ".mzxml",
				mslevel, exportname, massspectrometryfile);
		number_of_noise_peaks = readmzxml.return_number_of_noisemasses();
		if (experiment.getperform_ms2_sequencing()) {
			if (readmzxml.sequensing_results_vector != null) {
				this.sequensing_results_vector = readmzxml.sequensing_results_vector;
			}
		}
		return number_of_masses;
	}

	private void makedirs() {
		Thermo_Scientific_dir = cc.userhome + cc.fileSeparator + "thermo_scientific" + cc.fileSeparator;
		file_thermo_scientific = null;
		try {
			file_thermo_scientific = new File(Thermo_Scientific_dir);
		} catch (Exception e) {
			;
		}
		if (!file_thermo_scientific.isDirectory()) {
			file_thermo_scientific.mkdirs();
		}
	}

	private void remove_old_files() {
		boolean exporttodisksucceeded = exportfiletodisk.exportcompletefilename(
				(Thermo_Scientific_dir + thermoscientific_filename + ".raw"), emptystring.getBytes());
		exporttodisksucceeded = exportfiletodisk.exportcompletefilename(
				(Thermo_Scientific_dir + thermoscientific_filename + ".mzxml"), emptystring.getBytes());
	}

	private void store_file_local() {
		try {
			ftp.quit();
		} catch (Exception e) {
		}
		try {
			ftp.setRemoteHost(cc.ftpremotehost);
			ftp.connect();
			ftp.login(cc.ftpuser, cc.ftppassword);
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setType(FTPTransferType.BINARY);
			ftp.chdir(File.separator + experimentyear + File.separator + experimentnumber);
			ftp.get(Thermo_Scientific_dir + thermoscientific_filename + ".raw", filename);
		} catch (Exception e) {
		}
		try {
			ftp.quit();
		} catch (Exception e) {
		}
	}

	private int execscript() {
		procesexitvalue = -1;
		try {
			jproc.destroy();
		} catch (Exception e) {
		}
		try {
			if (Batchfilename2.indexOf(" ") > 0) {
				Batchfilename2 = "\"" + Batchfilename2 + "\"";
			}
			jproc = Runtime.getRuntime().exec(Batchfilename2);
			try {
				jproc.waitFor();
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
			procesexitvalue = jproc.exitValue();
			if (procesexitvalue == 1) {
				if (errormessageonce == false) {
					errormessageonce = true;
					message = raw_to_mzXML_executable + " is not executed correctly ! ";
					JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			jproc.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (procesexitvalue);
	}

	private void createbatchfile() {
		cmd2 = "";
		Batchfilename2 = Thermo_Scientific_dir + thermoscientific_filename + "2.bat";
		found_raw_to_mzXML_executable_name2 = found_raw_to_mzXML_executable_name.trim();
		if (found_raw_to_mzXML_executable_name2.indexOf(" ") > 0) {
			found_raw_to_mzXML_executable_name2 = "\"" + found_raw_to_mzXML_executable_name2 + "\"";
		}
		if (experiment.getraw_to_mzXML().equalsIgnoreCase("readw")) {
			if (Thermo_Scientific_dir.trim().indexOf(" ") > 0) {
				if (!found_raw_to_mzXML_executable_name2.equalsIgnoreCase("")) {
					cmd2 = found_raw_to_mzXML_executable_name2 + " --mzXML " + "\"" + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw" + "\"" + " 1> " + "\"" + Thermo_Scientific_dir
							+ "output.txt" + "\"" + " 2> " + "\"" + Thermo_Scientific_dir + "error.txt" + "\" ";
				} else {
					cmd2 = "C:/" + raw_to_mzXML_executable.toLowerCase() + "  --mzXML  " + "\"" + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw" + "\"" + " 1> " + Thermo_Scientific_dir + "output.txt"
							+ "\"" + " 2> " + "\"" + Thermo_Scientific_dir + "error.txt" + "\" ";
				}
			} else {
				if (!found_raw_to_mzXML_executable_name2.equalsIgnoreCase("")) {
					cmd2 = found_raw_to_mzXML_executable_name2 + "  --mzXML   " + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw 1> " + Thermo_Scientific_dir + "output.txt" + " 2> "
							+ Thermo_Scientific_dir + "error.txt ";
				} else {
					cmd2 = "C:/" + raw_to_mzXML_executable.toLowerCase() + "   --mzXML    " + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw 1> " + Thermo_Scientific_dir + "output.txt" + " 2> "
							+ Thermo_Scientific_dir + "error.txt ";
				}
			}
		}
		if (experiment.getraw_to_mzXML().equalsIgnoreCase("msconvert32")) {
			if (Thermo_Scientific_dir.trim().indexOf(" ") > 0) {
				if (!found_raw_to_mzXML_executable_name2.equalsIgnoreCase("")) {
					cmd2 = found_raw_to_mzXML_executable_name2 + "  " + "\"" + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw" + "\"" + " --32  --mzXML -o " + "\""
							+ Thermo_Scientific_dir + "\"" + " 1> " + "\"" + Thermo_Scientific_dir + "output.txt" + "\""
							+ " 2> " + "\"" + Thermo_Scientific_dir + "error.txt" + "\" ";
				} else {
					cmd2 = "C:/" + raw_to_mzXML_executable.toLowerCase() + " " + "\"" + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw" + "\"" + " --32  --mzXML -o " + "\""
							+ Thermo_Scientific_dir + "\"" + "  1> " + Thermo_Scientific_dir + "output.txt" + "\""
							+ " 2> " + "\"" + Thermo_Scientific_dir + "error.txt" + "\" ";
				}
			} else {
				if (!found_raw_to_mzXML_executable_name2.equalsIgnoreCase("")) {
					cmd2 = found_raw_to_mzXML_executable_name2 + "     " + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw --32  --mzXML -o " + Thermo_Scientific_dir + "  1> "
							+ Thermo_Scientific_dir + "output.txt" + " 2> " + Thermo_Scientific_dir + "error.txt ";
				} else {
					cmd2 = "C:/" + raw_to_mzXML_executable.toLowerCase() + " " + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw --32  --mzXML -o " + Thermo_Scientific_dir + "  1> "
							+ Thermo_Scientific_dir + "output.txt" + " 2> " + Thermo_Scientific_dir + "error.txt ";
				}
			}
		}
		if (experiment.getraw_to_mzXML().equalsIgnoreCase("msconvert64")) {
			if (Thermo_Scientific_dir.trim().indexOf(" ") > 0) {
				if (!found_raw_to_mzXML_executable_name2.equalsIgnoreCase("")) {
					cmd2 = found_raw_to_mzXML_executable_name2 + "  " + "\"" + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw" + "\"" + " --mzXML -o " + "\"" + Thermo_Scientific_dir
							+ "\"" + " 1> " + "\"" + Thermo_Scientific_dir + "output.txt" + "\"" + " 2> " + "\""
							+ Thermo_Scientific_dir + "error.txt" + "\" ";
				} else {
					cmd2 = "C:/" + raw_to_mzXML_executable.toLowerCase() + " " + "\"" + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw" + "\"" + " --mzXML -o " + "\"" + Thermo_Scientific_dir
							+ "\"" + "  1> " + Thermo_Scientific_dir + "output.txt" + "\"" + " 2> " + "\""
							+ Thermo_Scientific_dir + "error.txt" + "\" ";
				}
			} else {
				if (!found_raw_to_mzXML_executable_name2.equalsIgnoreCase("")) {
					cmd2 = found_raw_to_mzXML_executable_name2 + "     " + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw --mzXML -o " + Thermo_Scientific_dir + "  1> "
							+ Thermo_Scientific_dir + "output.txt" + " 2> " + Thermo_Scientific_dir + "error.txt ";
				} else {
					cmd2 = "C:/" + raw_to_mzXML_executable.toLowerCase() + " " + Thermo_Scientific_dir
							+ thermoscientific_filename + ".raw  --mzXML -o " + Thermo_Scientific_dir + "  1> "
							+ Thermo_Scientific_dir + "output.txt" + " 2> " + Thermo_Scientific_dir + "error.txt ";
				}
			}
		}
		boolean exporttodisksucceeded = exportfiletodisk.exportcompletefilename(Batchfilename2, cmd2.getBytes());
	}
}
