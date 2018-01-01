package pharmaceuticals.nl.peptrix.createpeaklist;

import java.io.*;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.SearchFile;
import pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing.Default_input_xml;
import pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing.Input_xml;
import pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing.Taxonomy;
import pharmaceuticals.nl.peptrix.utils.SortMatrix;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class ReadmzXML {

	ResultService resultService;

	StringBuffer listidsofpotentialmonoistopes;

	Process jproc;

	String searchstring;

	String protein;

	String scannumber_retentiontime;

	String MS2_scannumber;

	int integer_MS2_scannumber;

	int integer_MS2_scannumber_old;

	String MS2_retentiontime;

	double double_MS2_retentiontime;

	double double_MS2_retentiontime_old;

	String[] array_scannumber_retentiontime;

	String group_tag;

	FileOutputStream os;

	FileDescriptor fd;

	String input_xml_file_name;

	boolean taxonomy_file_created;

	boolean default_input_xml_file_created;

	boolean ms2dataOK;

	GregorianCalendar gc;

	SortMatrix sortmatrix;

	Controller cc;

	Base64Coder base64coder;

	Spectrum spectrum;

	Simplepeakfind simplepeakfind;

	FTPClient ftp = null;

	Deisotoping deisotoping;

	BigDecimal tempBD;

	BufferedReader in;

	BufferedReader in_ms2;

	StringBuffer databuffer;

	StringBuffer databuffer2;

	StringBuffer outputstringbuffer;

	StringBuffer combined_raw_peaks;

	StringBuffer buffer_count_raw_peaks;

	StringBuffer buffer_last_lc_fraction;

	StringBuffer retentiontime_last_lc_fraction;

	StringBuffer buffer_maximum_intensity;

	StringBuffer buffer_sum_mass;

	StringBuffer buffer_sum_quadratic_mass;

	StringBuffer buffer_sum_intensity;

	StringBuffer buffer_retentiontime_maximum_intensity;

	StringBuffer buffer_retentiontime_start_peak;

	StringBuffer export_peak_buffer;

	StringBuffer export_noise_buffer;

	String[] details_list_of_monoisotopic_ids;

	String[] tmp_string;

	String[] machedpeak;

	String[] directoryfiles;

	String[] potential_monoisotopes;

	String mzxmlfraction;

	String mzxml_filename;

	String inputmslevel;

	String precision;

	String line;

	String line_ms2;

	String mslevel;

	String pairorder;

	String byteorder;

	String mzxml_peak_data;

	String testline;

	String testline2;

	String testline3;

	String peakscount;

	String retentiontime;

	String precursormassovercharge;

	String sampleid;

	String Group_id;

	String strdatum;

	String strtime;

	String outputstring;

	String teststring = "0123456789.";

	String query;

	String exportname;

	String exportnamexmlfraction;

	String linefeed = "\n";

	String strjaar = "";

	StringBuffer exportbuffer;

	String exportstring;

	String list_of_monoisotopic_ids;

	String directoryfile;

	String precursorCharge;

	String mgf_file_name;

	String mgfstring;

	String savetextpart;

	String group_id;

	byte[] data;

	double[][] matrix_combined_raw_peaks;

	double[][] peaks;

	double[][] ms2_peaks;

	double[][] peakstransposed;

	double[][] storepeaks;

	double[] peaks_also_found_in_previous_fractions;

	double[][] potentialpeaks;

	double[] newcalibrationmassesraw;

	double[] count_peaks;

	double[] sum_mass_peaks;

	double filegrootte_kbytes;

	double offset_lc_ms = 0;

	double percent_stdevisotopingdistance;

	double deltamzsearchcalibrants;

	double deltamzcombinelocal;

	double absolutedifference;

	double averagemass;

	double standarddeviation;

	double mass_distance_factor;

	int missingfractions;

	int index;

	int index_end;

	int index_end2;

	int filenumber;

	int followingnumber;

	int updatesample;

	double testdouble;

	int chargestate = 1;

	int sortrow;

	int testint;

	int count_number_of_masses = 0;

	int count_number_of_noise_masses = 0;

	int r;

	boolean filetransported;

	boolean filetransported__list;

	boolean filetransported__noiselist;

	boolean filetransported__rejectedlist;

	boolean firsttime;

	boolean firsttime2;

	boolean internalcalibration;

	boolean peaks_data;

	boolean run_once = false;

	boolean do_once;

	boolean makedir = true;

	boolean lipodomics = false;

	boolean no_deisotoping_lipodomics = false;

	boolean first_istope_found;

	double istopehigh;

	double istopelow;

	double intensity_first_isotope;

	int countdown;

	boolean checkdoublecharge = true;

	String fixed_mod;

	String variabel_mod;

	FileOutputStream mgf_file;

	byte[] mgfdata;

	SearchFile objectsearchfile;

	String found_ms2_database;

	String found_xtandem_program;

	String program;

	String batchfile;

	Taxonomy taxonomy;

	Default_input_xml default_input_xml;

	Input_xml input_xml;

	boolean input_xml_file_created;

	String ms2_export_xml_filename;

	String xtandembatchfile;

	String xtandembatchoutputfile;

	String xtandemcmd;

	String mass_MH;

	String ms2_sequence;

	String missed_cleavages;

	boolean tandembatchfilecreated;

	boolean tandembatchfileexecuted;

	boolean errormessageonce;

	String time_stamp;

	String recognision_pattern;

	File directory;

	Vector<String[]> sequensing_results_vector;

	String[] sequensing_results;

	String[] compare_sequensing_results;

	int save_i;

	double number_sequenced_masses;

	double double_value_sequenced_mass;

	int number_missed_cleavages;

	double testdouble1;

	double testdouble2;

	Experiment experiment;

	int clusteringtechnique;

	Clusters clusters;

	ExportFileToDisk exportfiletodisk;

	public ReadmzXML(Controller cc, Experiment experiment, ExportFileToDisk exportfiletodisk) {

		resultService = new ResultServiceImpl(cc);
		this.exportfiletodisk = exportfiletodisk;
		// String selected_database = experiment.getname_taxonomy_file();
		this.experiment = experiment;
		clusteringtechnique = experiment.getclusteringtechnique();
		default_input_xml = new Default_input_xml(cc, experiment, exportfiletodisk);
		input_xml = new Input_xml(cc);
		taxonomy_file_created = false;
		default_input_xml_file_created = false;
		this.cc = cc;
		simplepeakfind = new Simplepeakfind();
		found_ms2_database = "";
		found_xtandem_program = "";
		batchfile = "";
		if ((experiment.getperform_ms2_sequencing() == true)
				&& (experiment.getname_search_engine().trim().equalsIgnoreCase("x!tandem"))) {
			// if ((selected_database != null) &&
			// (!selected_database.trim().equalsIgnoreCase(""))) {
			objectsearchfile = new SearchFile(cc);

			// System.out.println("selected_database " + selected_database);

			// found_ms2_database = objectsearchfile.return_file(".fasta");

			found_ms2_database = objectsearchfile.return_file_on_wildcard(".fasta");

			System.out.println("readmzxml 1 found_ms2_database  " + found_ms2_database);

			System.out.println("experiment.gettaxonomy_name()  " + experiment.gettaxonomy_name());

			objectsearchfile = new SearchFile(cc);
			program = "tandem.exe";
			batchfile = "tandem.bat";
			found_xtandem_program = objectsearchfile.return_file(program);
			xtandembatchfile = found_xtandem_program.replace(program, batchfile);
			// }
		}
		errormessageonce = false;
	}

	public int store_peak_lists(String mzxml_filename, String inputmslevel, String exportname,
			MassSpectrometryFile massspectrometryfile) {
		int filenumber = massspectrometryfile.getFilenumber();
		String[] array_variabel_modifications = experiment.getvariabel_peptide_modifications();
		String[] array_fixed_modifications = experiment.getfixed_peptide_modifications();
		double offset_lc_ms = massspectrometryfile.getOffset_lc_ms();
		String Group_id = massspectrometryfile.getGroupid();
		String sampleid = massspectrometryfile.getSampleid();
		String string_Taxonomy = experiment.gettaxonomy_name();
		sequensing_results_vector = null;
		gc = new GregorianCalendar();
		time_stamp = String.valueOf(gc.get(Calendar.YEAR) + 1000) + String.valueOf(gc.get(Calendar.MONTH) + 10)
				+ String.valueOf(gc.get(Calendar.DAY_OF_MONTH) + 10) + String.valueOf(gc.get(Calendar.HOUR_OF_DAY) + 10)
				+ String.valueOf(gc.get(Calendar.MINUTE) + 10) + String.valueOf(gc.get(Calendar.SECOND) + 10);
		count_number_of_masses = 0;
		count_number_of_noise_masses = 0;
		this.offset_lc_ms = offset_lc_ms;
		this.inputmslevel = inputmslevel;
		this.mzxml_filename = mzxml_filename;
		this.sampleid = sampleid;
		this.Group_id = Group_id;
		this.filenumber = filenumber;
		this.exportname = exportname;
		this.strjaar = experiment.getExperimentyear();
		followingnumber = 0;
		input_xml_file_created = false;
		ms2_export_xml_filename = "";
		if (experiment.getperform_ms2_sequencing()) {
			mgf_file_name = cc.userhome + cc.fileSeparator + Group_id.trim() + "_" + sampleid.trim() + "_"
					+ String.valueOf(filenumber).trim() + ".mgf";
		}
		if ((experiment.getperform_ms2_sequencing() == true)
				&& (experiment.getname_search_engine().trim().equalsIgnoreCase("x!tandem"))) {
			recognision_pattern = Group_id.trim() + "_" + sampleid.trim() + "_" + String.valueOf(filenumber).trim()
					+ "_" + time_stamp + "_output";
			ms2_export_xml_filename = cc.userhome + cc.fileSeparator + recognision_pattern + ".xml";
			xtandembatchoutputfile = cc.userhome + cc.fileSeparator + Group_id.trim() + "_" + sampleid.trim() + "_"
					+ String.valueOf(filenumber).trim() + "_output.txt";

			input_xml_file_name = cc.userhome + cc.fileSeparator + Group_id.trim() + "_" + sampleid.trim() + "_"
					+ String.valueOf(filenumber).trim() + "_input.xml";
			if (!taxonomy_file_created) {
				if ((!string_Taxonomy.trim().equalsIgnoreCase("")) && (!found_ms2_database.trim().equalsIgnoreCase(""))
						&& (!found_xtandem_program.trim().equalsIgnoreCase(""))) {
					
					System.out.println("readmzxml 2 found_ms2_database  " + found_ms2_database);
					System.out.println("readmzxml 2 string_Taxonomy  " + string_Taxonomy);

					
					taxonomy = new Taxonomy(found_ms2_database, string_Taxonomy, found_xtandem_program, program, cc);
					taxonomy_file_created = taxonomy.taxonomy_file_created;
				}
			}
			if (taxonomy_file_created) {
				if (!default_input_xml_file_created) {
					
					

					
					default_input_xml.create_default_input_xml(found_xtandem_program, program, taxonomy);
					default_input_xml_file_created = default_input_xml.default_input_xml_file_created;
				}
			}
			if (taxonomy_file_created && default_input_xml_file_created) {
				input_xml.create_input_xml(default_input_xml.default_input_xml_file_name, mgf_file_name,
						ms2_export_xml_filename, input_xml_file_name, taxonomy);
				input_xml_file_created = input_xml.input_xml_file_created;
			}
		}
		parse_xml_file(array_fixed_modifications, array_variabel_modifications);
		return count_number_of_masses;
	}

	public void setobjects_null() {
		peaks = null;
		peakstransposed = null;
		storepeaks = null;
		peaks_also_found_in_previous_fractions = null;
		potentialpeaks = null;
		details_list_of_monoisotopic_ids = null;
		list_of_monoisotopic_ids = null;
	}

	public void parse_xml_file(String[] array_fixed_modifications, String[] array_variabel_modifications) {
		double deltamzcombine = Double.parseDouble(experiment.getdelta_mz_combine());
		double inputerror = experiment.getdeviation_from_expected_intensity_ratio();
		int minimumnumberoffractions = experiment.getpeptide_present_in_minimumnumberoffractions();
		String TOL = experiment.getTOL();
		String TOLU = experiment.getTOLU();
		String ITOL = experiment.getITOL();
		String ITOLU = experiment.getITOLU();
		String string_search_engine = experiment.getname_search_engine();
		String selected_enzyme = "Trypsin";
		String[] selected_enzymes = experiment.getSelected_enzymes();
		if (selected_enzymes != null) {
			if ((selected_enzymes.length == 1) && (selected_enzymes[0].trim().equalsIgnoreCase("trypsin"))) {
				selected_enzyme = "Trypsin";
			}
			if ((selected_enzymes.length == 1) && (selected_enzymes[0].trim().equalsIgnoreCase("chymotrypsin"))) {
				selected_enzyme = "Chymotrypsin";
			}
			if (selected_enzymes.length == 2) {
				if ((selected_enzymes[0].trim().equalsIgnoreCase("trypsin"))
						&& (selected_enzymes[1].trim().equalsIgnoreCase("chymotrypsin"))) {
					selected_enzyme = "TrypChymo";
				}
				if ((selected_enzymes[0].trim().equalsIgnoreCase("chymotrypsin"))
						&& (selected_enzymes[1].trim().equalsIgnoreCase("trypsin"))) {
					selected_enzyme = "TrypChymo";
				}
			}
		}
		int max_charge_state = experiment.getmax_charge_state_peptide();
		boolean perform_ms2_sequencing = experiment.getperform_ms2_sequencing();
		mass_distance_factor = 1;
		export_peak_buffer = new StringBuffer("");
		export_noise_buffer = new StringBuffer("");
		filetransported__list = false;
		filetransported__noiselist = false;
		filetransported__rejectedlist = false;
		if (ftp == null) {
			try {
				ftp = new FTPClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				ftp.quit();
			} catch (Exception e) {
				;
			}
		}
		try {
			ftp.setRemoteHost(cc.ftpremotehost);
			ftp.connect();
			ftp.login(cc.ftpuser, cc.ftppassword);
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setTimeout(cc.ftp_longtime);
			ftp.setType(FTPTransferType.BINARY);
		} catch (Exception e) {
			;
		}
		try {
			ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
					+ String.valueOf(experiment.getExperimentid()));
			ftp.put(export_peak_buffer.toString().getBytes(), exportname, false);
			ftp.put(export_noise_buffer.toString().getBytes(), "noise_" + exportname, false);
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		try {
			ftp.quit();
		} catch (Exception e) {
			;
		}
		firsttime = true;
		count_number_of_masses = 0;
		count_number_of_noise_masses = 0;
		try {
			in.close();
		} catch (Exception e) {
		}
		try {
			if (perform_ms2_sequencing) {
				mgfstring = "";
				try {
					mgf_file = new FileOutputStream(mgf_file_name);
				} catch (Exception e) {
					if (cc.debugmode) {
						e.printStackTrace();
					} else {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				if (string_search_engine.trim().equalsIgnoreCase("mascot")) {
					mgfstring = mgfstring + "SEARCH=MIS" + linefeed;
					mgfstring = mgfstring + "REPTYPE=Peptide" + linefeed;
					if (exportname != null) {
						if (!exportname.trim().equalsIgnoreCase("")) {
							mgfstring = mgfstring + "COM=" + exportname.trim() + linefeed;
						}
					}
					if (selected_enzyme != null) {
						if (!selected_enzyme.trim().equalsIgnoreCase("")) {
							mgfstring = mgfstring + "CLE=" + selected_enzyme.trim() + linefeed;
						}
					}
					if (TOL != null) {
						if (!TOL.trim().equalsIgnoreCase("")) {
							mgfstring = mgfstring + "TOL=" + TOL.trim() + linefeed;
						}
					}
					if (TOLU != null) {
						if (!TOLU.trim().equalsIgnoreCase("")) {
							mgfstring = mgfstring + "TOLU=" + TOLU.trim() + linefeed;
						}
					}
					if (ITOL != null) {
						if (!ITOL.trim().equalsIgnoreCase("")) {
							mgfstring = mgfstring + "ITOL=" + ITOL.trim() + linefeed;
						}
					}
					if (ITOLU != null) {
						if (!ITOLU.trim().equalsIgnoreCase("")) {
							mgfstring = mgfstring + "ITOLU=" + ITOLU.trim() + linefeed;
						}
					}
					fixed_mod = "";
					if (array_fixed_modifications != null) {
						if (array_fixed_modifications.length >= 1) {
							for (int i = 0; i < array_fixed_modifications.length; i++) {
								if (!array_fixed_modifications[i].trim().equalsIgnoreCase("")) {
									if (fixed_mod.trim().equalsIgnoreCase("")) {
										fixed_mod = "MODS=" + array_fixed_modifications[i].trim();
									} else {
										fixed_mod = fixed_mod + "," + array_fixed_modifications[i].trim();
									}
								}
							}
						}
					}
					if (!fixed_mod.trim().equalsIgnoreCase("")) {
						mgfstring = mgfstring + fixed_mod + linefeed;
					}
					variabel_mod = "";
					if (array_variabel_modifications != null) {
						if (array_variabel_modifications.length >= 1) {
							for (int i = 0; i < array_variabel_modifications.length; i++) {
								if (!array_variabel_modifications[i].trim().equalsIgnoreCase("")) {
									if (variabel_mod.trim().equalsIgnoreCase("")) {
										variabel_mod = "IT_MODS=" + array_variabel_modifications[i].trim();
									} else {
										variabel_mod = variabel_mod + "," + array_variabel_modifications[i].trim();
									}
								}
							}
						}
					}
					if (!variabel_mod.trim().equalsIgnoreCase("")) {
						mgfstring = mgfstring + variabel_mod + linefeed;
					}
					mgfdata = mgfstring.getBytes();
					try {
						mgf_file.write(mgfdata);
						mgf_file.flush();
					} catch (Exception e) {
						if (cc.debugmode) {
							e.printStackTrace();
						} else {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				if (string_search_engine.toLowerCase().indexOf("tandem") > -1) {
					mgfstring = mgfstring + "COM=";
					if (exportname != null) {
						if (!exportname.trim().equalsIgnoreCase("")) {
							mgfstring = mgfstring + exportname.trim();

						}
					}
					mgfstring = mgfstring + " ms2" + linefeed;
					mgfdata = mgfstring.getBytes();
					try {
						mgf_file.write(mgfdata);
						mgf_file.flush();
					} catch (Exception e) {
						if (cc.debugmode) {
							e.printStackTrace();
						} else {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
			in = new BufferedReader(new FileReader(mzxml_filename));
			peaks_data = false;
			while ((line = in.readLine()) != null) {
				testline = line.toLowerCase();
				if (testline.indexOf("<scan") > -1) {
					peaks_data = true;
					databuffer = new StringBuffer();
				}
				if (peaks_data) {
					databuffer.append(line + " ");
				}
				if (testline.indexOf("</peaks") > 0) {
					if (peaks_data) {
						peaks_data = false;
						get_peaks_from_spectrum(inputerror, max_charge_state, minimumnumberoffractions,
								string_search_engine);
					}
				}
			}
			get_remaining_peaks_from_last_spectra(minimumnumberoffractions);
			try {
				ftp.quit();
			} catch (Exception e) {
				;
			}
			exportnamexmlfraction = exportname;
			filegrootte_kbytes = 1;
			followingnumber = 0;
			mzxmlfraction = "0";
			retentiontime = "0";
			precursormassovercharge = "0";
			cc.actualtime.resettime();
			strdatum = cc.actualtime.getdatestring();
			strjaar = cc.actualtime.getyear();
			strtime = cc.actualtime.gettimestring();
			if ((filetransported__list) && (!sampleid.trim().equals("")) && (!Group_id.trim().equals(""))) {
				updatesample = resultService.insertresultrecord("tmp", filegrootte_kbytes, sampleid, Group_id,
						experiment, strdatum, strtime, exportnamexmlfraction, strjaar, retentiontime, mzxmlfraction,
						filenumber, offset_lc_ms);
			}
			exportnamexmlfraction = "noise_" + exportname;
			cc.actualtime.resettime();
			strdatum = cc.actualtime.getdatestring();
			strjaar = cc.actualtime.getyear();
			strtime = cc.actualtime.gettimestring();
			if ((filetransported__noiselist) && (!sampleid.trim().equals("")) && (!Group_id.trim().equals(""))) {
				updatesample = resultService.insertresultrecord("tmp2", filegrootte_kbytes, sampleid, Group_id,
						experiment, strdatum, strtime, exportnamexmlfraction, strjaar, retentiontime, mzxmlfraction,
						filenumber, offset_lc_ms);

			}
			exportnamexmlfraction = "rejected_" + exportname;
			if ((filetransported__rejectedlist) && (!sampleid.trim().equals("")) && (!Group_id.trim().equals(""))) {
				updatesample = resultService.insertresultrecord("rejected", filegrootte_kbytes, sampleid, Group_id,
						experiment, strdatum, strtime, exportnamexmlfraction, strjaar, retentiontime, mzxmlfraction,
						filenumber, offset_lc_ms);
			}
			if (perform_ms2_sequencing) {
				sequensing_results_vector = new Vector();
				try {
					mgf_file.close();
				} catch (Exception e) {
					if (cc.debugmode) {
						e.printStackTrace();
					} else {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				if (string_search_engine.trim().equalsIgnoreCase("x!tandem")) {
					if (input_xml_file_created) {
						if (!xtandembatchfile.trim().equalsIgnoreCase("")) {
							tandembatchfilecreated = true;
							tandembatchfileexecuted = false;
							xtandemcmd = "\"" + found_xtandem_program + "\" \"" + input_xml_file_name + "\"  >  \""
									+ xtandembatchoutputfile + "\"";
							try {
								os = new FileOutputStream(xtandembatchfile);
								fd = os.getFD();
								byte[] data = xtandemcmd.getBytes();
								os.write(data);
								os.flush();
								fd.sync();
								os.close();
							} catch (IOException e) {
								tandembatchfilecreated = false;
							}
							if (tandembatchfilecreated) {
								int procesexitvalue = -1;
								try {
									if (xtandembatchfile.indexOf(" ") > 0) {
										xtandembatchfile = "\"" + xtandembatchfile + "\"";
									}
									jproc = Runtime.getRuntime().exec(xtandembatchfile);
									try {

										jproc.waitFor();
									} catch (InterruptedException ie) {
										ie.printStackTrace();
									}
									procesexitvalue = jproc.exitValue();
									if (procesexitvalue == 1) {
										if (!errormessageonce) {
											errormessageonce = true;
											System.out.println("X!tandem was not executed correctly");
										}
									} else {
										tandembatchfileexecuted = true;
									}
									jproc.destroy();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							if (tandembatchfileexecuted) {
								directory = new File(cc.userhome);
								directoryfiles = directory.list();
								if (directoryfiles != null) {
									if (directoryfiles.length > 0) {
										for (int i = 0; i < directoryfiles.length; i++) {
											if (directoryfiles[i].trim().toLowerCase()
													.indexOf(recognision_pattern.toLowerCase()) > -1) {
												get_ms2_identifications(cc.userhome, cc.fileSeparator,
														directoryfiles[i].trim(), deltamzcombine, clusteringtechnique);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void get_ms2_identifications(String directory, String fileSeparator, String filename, double deltamzcombine,
			int clusteringtechnique) {
		String selected_enzyme = "Trypsin";
		String[] selected_enzymes = experiment.getSelected_enzymes();
		if (selected_enzymes != null) {
			if ((selected_enzymes.length == 1) && (selected_enzymes[0].trim().equalsIgnoreCase("trypsin"))) {
				selected_enzyme = "Trypsin";
			}
			if ((selected_enzymes.length == 1) && (selected_enzymes[0].trim().equalsIgnoreCase("chymotrypsin"))) {
				selected_enzyme = "Chymotrypsin";
			}
			if (selected_enzymes.length == 2) {
				if ((selected_enzymes[0].trim().equalsIgnoreCase("trypsin"))
						&& (selected_enzymes[1].trim().equalsIgnoreCase("chymotrypsin"))) {
					selected_enzyme = "TrypChymo";
				}
				if ((selected_enzymes[0].trim().equalsIgnoreCase("chymotrypsin"))
						&& (selected_enzymes[1].trim().equalsIgnoreCase("trypsin"))) {
					selected_enzyme = "TrypChymo";
				}
			}
		}
		databuffer2 = new StringBuffer();
		savetextpart = "";
		group_tag = "</group";
		try {
			in_ms2 = new BufferedReader(new FileReader(directory + fileSeparator + filename));
			group_id = "";
			protein = "";
			mass_MH = "";
			ms2_sequence = "";
			missed_cleavages = "";
			while ((line_ms2 = in_ms2.readLine()) != null) {
				databuffer2.append(line_ms2);
				if (databuffer2.toString().toLowerCase().indexOf(group_tag) > -1) {
					testline2 = databuffer2.toString().toLowerCase();
					savetextpart = testline2.substring((testline2.indexOf(group_tag) + (group_tag.length() + 1)),
							testline2.length());
					scannumber_retentiontime = "";
					MS2_scannumber = "";
					MS2_retentiontime = "";
					testline3 = "";
					if (testline2.indexOf("<group id=\"") > -1) {
						index = -1;
						searchstring = "<group id=\"";
						try {
							index = testline2.indexOf(searchstring) + searchstring.length();
						} catch (Exception ex) {
							group_id = "";
						}
						if (index > -1) {
							group_id = testline2.substring(index, testline2.length() - 1).substring(0,
									testline2.substring(index, testline2.length() - 1).indexOf("\""));
							try {
								testline3 = testline2.substring(index, testline2.length() - 1);
								testline3 = testline3.substring(0, testline3.indexOf(">"));
							} catch (Exception ex) {
								testline3 = "";
							}
						} else {
							group_id = "";
						}
						if (!testline3.trim().equalsIgnoreCase("")) {
							searchstring = " mh=\"";
							try {
								index = testline3.indexOf(searchstring) + searchstring.length();
							} catch (Exception ex) {
								mass_MH = "";
							}
							if (index > -1) {
								mass_MH = testline3.substring(index, testline3.length() - 1).substring(0,
										testline3.substring(index, testline3.length() - 1).indexOf("\""));
							} else {
								mass_MH = "";
							}
						} else {
							mass_MH = "";
						}
						index = -1;
						searchstring = "seq=\"";
						try {
							index = testline2.indexOf(searchstring) + searchstring.length();
						} catch (Exception ex) {
							ms2_sequence = "";
						}
						if (index > -1) {
							ms2_sequence = testline2.substring(index, testline2.length() - 1)
									.substring(0, testline2.substring(index, testline2.length() - 1).indexOf("\""))
									.toUpperCase();
						} else {
							ms2_sequence = "";
						}
						index = -1;
						searchstring = "missed_cleavages=\"";
						try {
							index = testline2.indexOf(searchstring) + searchstring.length();
						} catch (Exception ex) {
							missed_cleavages = "";
						}
						if (index > -1) {
							missed_cleavages = testline2.substring(index, testline2.length() - 1).substring(0,
									testline2.substring(index, testline2.length() - 1).indexOf("\""));
						} else {
							missed_cleavages = "";
						}
						index = -1;
						searchstring = "<protein expect=\"";
						index = testline2.indexOf(searchstring) + searchstring.length();
						testline3 = "";
						if (index > -1) {
							index_end2 = -1;
							try {
								index_end2 = testline2.substring(index, testline2.length()).indexOf(">");
							} catch (Exception ex) {
								protein = "";
							}
							if (index_end2 > -1) {
								testline3 = testline2.substring(index, testline2.length()).substring(0, index_end2);
								index = -1;
								searchstring = "label=\"";
								try {
									index = testline3.indexOf(searchstring) + searchstring.length();
								} catch (Exception ex) {
									protein = "";
								}
								if (index > -1) {
									try {
										protein = testline3.substring(index, testline3.length());
									} catch (Exception ex) {
										protein = "";
									}
									try {
										index = -1;
										index = protein.indexOf("\"");
									} catch (Exception ex) {
										protein = "";
									}
									if (index > -1) {
										protein = protein.substring(0, index).replace(",", " ");
									} else {
										protein = "";
									}
								}
							} else {
								protein = "";
							}
						}
					}
					if (testline2.indexOf("<group type=\"support\"") > -1) {
						index = -1;
						searchstring = "<note label=\"description\"";
						index = testline2.indexOf(searchstring) + searchstring.length();
						if (index > -1) {
							index_end2 = -1;
							try {
								index_end2 = testline2.substring(index, testline2.length()).indexOf("</note>");
							} catch (Exception ex) {

							}
							if (index_end2 > -1) {
								testline3 = testline2.substring(index, testline2.length()).substring(0, index_end2);
								index = -1;
								searchstring = ">";
								try {
									index = testline3.indexOf(searchstring) + searchstring.length();
								} catch (Exception ex) {
								}
								if (index > -1) {
									try {
										scannumber_retentiontime = testline3.substring(index, testline3.length());
									} catch (Exception ex) {
									}
									if (!scannumber_retentiontime.trim().equalsIgnoreCase("")) {
										if (scannumber_retentiontime.indexOf(",") > -1) {
											array_scannumber_retentiontime = scannumber_retentiontime.split(",");
										}
										if (array_scannumber_retentiontime.length == 2) {
											if (array_scannumber_retentiontime[0].indexOf(":") > -1) {
												MS2_scannumber = array_scannumber_retentiontime[0]
														.substring((array_scannumber_retentiontime[0].indexOf(":") + 1),
																array_scannumber_retentiontime[0].length())
														.trim();
											}
											if (array_scannumber_retentiontime[1].indexOf(":") > -1) {
												MS2_retentiontime = array_scannumber_retentiontime[1]
														.substring((array_scannumber_retentiontime[1].indexOf(":") + 1),
																array_scannumber_retentiontime[1].length())
														.trim();
											}
										}
									}
								}
							}
						}
						if (testline2.indexOf(group_id) > -1) {
							if ((!group_id.trim().equalsIgnoreCase("")) && (!protein.trim().equalsIgnoreCase(""))
									&& (!mass_MH.trim().equalsIgnoreCase(""))
									&& (!ms2_sequence.trim().equalsIgnoreCase(""))
									&& (!MS2_scannumber.trim().equalsIgnoreCase(""))
									&& (!MS2_retentiontime.trim().equalsIgnoreCase(""))) {
								boolean already_sequenced = false;
								for (int i = (sequensing_results_vector.size() - 1); i > -1; i--) {
									compare_sequensing_results = sequensing_results_vector.elementAt(i);
									if (compare_sequensing_results != null) {
										if (ms2_sequence.trim().equalsIgnoreCase(compare_sequensing_results[0])) {
											testdouble1 = -1;
											try {
												testdouble1 = Double.parseDouble(mass_MH.trim());
											} catch (Exception ex1) {
											}
											testdouble2 = -1;
											try {
												testdouble2 = Double.parseDouble(compare_sequensing_results[1]);
											} catch (Exception ex2) {
											}
											if ((testdouble1 > -1) && (testdouble2 > -1)) {
												deltamzcombinelocal = deltamzcombine;
												if (clusteringtechnique == 1) {
													deltamzcombinelocal = testdouble1 * deltamzcombine / 1000000;
												}
												if (Math.abs(testdouble1 - testdouble2) < deltamzcombinelocal) {
													already_sequenced = true;
													save_i = i;
													i = -1;
												}
											}
										}
									}
								}
								if (!already_sequenced) {
									sequensing_results = new String[9];
									sequensing_results[0] = ms2_sequence.trim();
									sequensing_results[1] = mass_MH.trim();
									sequensing_results[2] = "1";
									sequensing_results[3] = protein.replaceAll(" ", "_").trim();
									integer_MS2_scannumber = -1;
									try {
										integer_MS2_scannumber = Integer.parseInt(MS2_scannumber);
									} catch (Exception ex) {
										integer_MS2_scannumber = -1;
									}
									sequensing_results[4] = String.valueOf(integer_MS2_scannumber);
									sequensing_results[5] = String.valueOf(integer_MS2_scannumber);
									double_MS2_retentiontime = -1;
									try {
										double_MS2_retentiontime = Double.parseDouble(MS2_retentiontime);
									} catch (Exception ex) {
										double_MS2_retentiontime = -1;
									}
									sequensing_results[6] = String.valueOf(double_MS2_retentiontime);
									sequensing_results[7] = String.valueOf(double_MS2_retentiontime);
									number_missed_cleavages = 0;
									if (selected_enzyme.trim().equalsIgnoreCase("trypsin")) {
										for (int i = 0; i < ms2_sequence.length() - 1; i++) {
											if ((ms2_sequence.charAt(i) == 'k') || (ms2_sequence.charAt(i) == 'K')
													|| (ms2_sequence.charAt(i) == 'R')
													|| (ms2_sequence.charAt(i) == 'r')) {
												number_missed_cleavages++;
											}
										}
									}
									if (selected_enzyme.trim().equalsIgnoreCase("chymotrypsin")) {
										for (int i = 0; i < ms2_sequence.length() - 1; i++) {
											if ((ms2_sequence.charAt(i) == 'F') || (ms2_sequence.charAt(i) == 'f')
													|| (ms2_sequence.charAt(i) == 'Y')
													|| (ms2_sequence.charAt(i) == 'y')
													|| (ms2_sequence.charAt(i) == 'W')
													|| (ms2_sequence.charAt(i) == 'w')
													|| (ms2_sequence.charAt(i) == 'L')
													|| (ms2_sequence.charAt(i) == 'l')) {
												number_missed_cleavages++;
											}
										}
									}
									if (selected_enzyme.trim().equalsIgnoreCase("trypchymo")) {
										for (int i = 0; i < ms2_sequence.length() - 1; i++) {
											if ((ms2_sequence.charAt(i) == 'k') || (ms2_sequence.charAt(i) == 'K')
													|| (ms2_sequence.charAt(i) == 'R')
													|| (ms2_sequence.charAt(i) == 'r')
													|| (ms2_sequence.charAt(i) == 'F')
													|| (ms2_sequence.charAt(i) == 'f')
													|| (ms2_sequence.charAt(i) == 'Y')
													|| (ms2_sequence.charAt(i) == 'y')
													|| (ms2_sequence.charAt(i) == 'W')
													|| (ms2_sequence.charAt(i) == 'w')
													|| (ms2_sequence.charAt(i) == 'L')
													|| (ms2_sequence.charAt(i) == 'l')) {
												number_missed_cleavages++;
											}
										}
									}
									sequensing_results[8] = String.valueOf(number_missed_cleavages);
									sequensing_results_vector.add(sequensing_results);
								} else {
									compare_sequensing_results = sequensing_results_vector.elementAt(save_i);
									number_sequenced_masses = 0;
									try {
										number_sequenced_masses = Double.parseDouble(compare_sequensing_results[2]);
									} catch (Exception ex) {

									}
									try {
										double_value_sequenced_mass = (number_sequenced_masses
												* Double.parseDouble(compare_sequensing_results[1]));
										mass_MH = String
												.valueOf((double_value_sequenced_mass + Double.parseDouble(mass_MH))
														/ (number_sequenced_masses + 1));
									} catch (Exception ex) {
									}
									number_sequenced_masses = number_sequenced_masses + 1;
									sequensing_results_vector.removeElementAt(save_i);
									sequensing_results = new String[9];
									sequensing_results[0] = ms2_sequence.trim();
									sequensing_results[1] = mass_MH.trim();
									sequensing_results[2] = String.valueOf(number_sequenced_masses);
									sequensing_results[3] = protein.replaceAll(" ", "_").trim();
									sequensing_results[4] = compare_sequensing_results[4];
									sequensing_results[5] = compare_sequensing_results[5];
									sequensing_results[6] = compare_sequensing_results[6];
									sequensing_results[7] = compare_sequensing_results[7];
									sequensing_results[8] = compare_sequensing_results[8];
									integer_MS2_scannumber = -1;
									try {
										integer_MS2_scannumber = Integer.parseInt(MS2_scannumber);
									} catch (Exception ex) {
										integer_MS2_scannumber = -1;
									}
									if (integer_MS2_scannumber > -1) {
										try {
											integer_MS2_scannumber_old = Integer
													.parseInt(compare_sequensing_results[4]);
										} catch (Exception ex) {
											integer_MS2_scannumber_old = -1;
										}
										if (integer_MS2_scannumber_old > -1) {
											if (integer_MS2_scannumber < integer_MS2_scannumber_old) {
												sequensing_results[4] = String.valueOf(integer_MS2_scannumber);
											}
										}
										try {
											integer_MS2_scannumber_old = Integer
													.parseInt(compare_sequensing_results[5]);
										} catch (Exception ex) {
											integer_MS2_scannumber_old = -1;
										}
										if (integer_MS2_scannumber_old > -1) {
											if (integer_MS2_scannumber > integer_MS2_scannumber_old) {
												sequensing_results[5] = String.valueOf(integer_MS2_scannumber);
											}
										}
									}
									double_MS2_retentiontime = -1;
									try {
										double_MS2_retentiontime = Double.parseDouble(MS2_retentiontime);
									} catch (Exception ex) {
										double_MS2_retentiontime = -1;
									}
									if (double_MS2_retentiontime > -1) {
										try {
											double_MS2_retentiontime_old = Double
													.parseDouble(compare_sequensing_results[6]);
										} catch (Exception ex) {
											double_MS2_retentiontime_old = -1;
										}
										if (double_MS2_retentiontime_old > -1) {
											if (double_MS2_retentiontime < double_MS2_retentiontime_old) {
												sequensing_results[6] = String.valueOf(double_MS2_retentiontime);
											}

										}
										try {
											double_MS2_retentiontime_old = Double
													.parseDouble(compare_sequensing_results[7]);
										} catch (Exception ex) {
											double_MS2_retentiontime_old = -1;
										}
										if (double_MS2_retentiontime_old > -1) {
											if (double_MS2_retentiontime > double_MS2_retentiontime_old) {
												sequensing_results[7] = String.valueOf(double_MS2_retentiontime);
											}
										}
									}
									sequensing_results_vector.add(sequensing_results);
								}
							}
						}
					}
					databuffer2 = new StringBuffer(savetextpart);
				}
			}
		} catch (Exception e) {
			;
		}
	}

	private void get_peaks_from_spectrum(double inputerror, int max_charge_state, int minimumnumberoffractions,
			String string_search_engine) {
		boolean perform_ms2_sequencing = experiment.getperform_ms2_sequencing();
		int missingfractionsallowed = experiment.getmissing_number_ms_scans_allowed();
		double deltamzcombine = Double.parseDouble(experiment.getdelta_mz_combine());
		double deltamzsearchmaximum = Double.parseDouble(experiment.getdelta_mz_searchmaximum());
		double percent_deviation_isotopic_distance = experiment.getpercent_deviation_from_isotopic_distance();
		double minimummass = Double.parseDouble(experiment.getminimum_mass());
		double maximummass = Double.parseDouble(experiment.getmaximum_mass());
		double c13_c12 = experiment.getisotopic_distance_c13_c12();
		String strquantilethreshold = experiment.getquantilethreshold();
		if (base64coder == null) {
			base64coder = new Base64Coder();
		}
		testline = databuffer.toString().toLowerCase();
		precursormassovercharge = "";
		if (testline.indexOf("/precursormz") > -1) {
			index_end = testline.indexOf("/precursormz");
			countdown = testline.indexOf("/precursormz");
			while (countdown > 0) {
				countdown--;
				if (String.valueOf(testline.charAt(countdown)).equalsIgnoreCase(">")) {
					index = countdown;
					countdown = 0;
				}
			}
			precursormassovercharge = testline.substring(index, index_end);
			precursormassovercharge.replace("<", "");
			precursormassovercharge.replace(">", "");
			precursormassovercharge = convert_to_number(precursormassovercharge);
		}
		precursorCharge = "";
		index = testline.indexOf("precursorcharge");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				precursorCharge = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				precursorCharge = "";
			}
			precursorCharge = convert_to_number(precursorCharge);
		}
		retentiontime = "";
		index = testline.indexOf("retentiontime");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				retentiontime = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				retentiontime = "";
			}
			retentiontime = convert_to_number(retentiontime);
		}
		mzxmlfraction = "";
		index = testline.indexOf("num");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				mzxmlfraction = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				mzxmlfraction = "";
			}
		}
		precision = "";
		index = testline.indexOf("precision");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				precision = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				precision = "";
			}
		}
		peakscount = "";
		index = testline.indexOf("peakscount");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				peakscount = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				peakscount = "";
			}

		}
		mslevel = "";
		index = testline.indexOf("mslevel");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				mslevel = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				mslevel = "";
			}
		}
		pairorder = "";
		index = testline.indexOf("pairorder");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				pairorder = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				pairorder = "";
			}
		}
		byteorder = "";
		index = testline.indexOf(" byteorder");
		if (index >= 0) {
			try {
				index = testline.indexOf("\"", index + 1);
				index_end = testline.indexOf("\"", index + 1);
				byteorder = databuffer.toString().substring(index + 1, index_end);
			} catch (Exception e) {
				byteorder = "";
			}
		}
		index = 0;
		while ((index >= 0) && (index < testline.indexOf("<peaks"))) {
			index = testline.indexOf(">", index + 1);
		}
		try {
			mzxml_peak_data = databuffer.toString().substring((index + 1), testline.indexOf("</peaks"));
		} catch (Exception e) {
			mzxml_peak_data = "";
		}
		if (inputmslevel.equalsIgnoreCase(mslevel)) {
			spectrum = base64coder.decode(mzxml_peak_data, precision, byteorder);
		}
		if (spectrum != null) {
			if (mslevel.trim().equalsIgnoreCase("1")) {
				followingnumber++;
				peaks = simplepeakfind.findlocalmaxima(deltamzsearchmaximum, Double.parseDouble(strquantilethreshold),
						minimummass, maximummass, clusteringtechnique, spectrum);
				if (peaks != null) {
					storepeaks = new double[peaks.length][3];
					peaks_also_found_in_previous_fractions = new double[peaks.length];
					potentialpeaks = new double[peaks.length][2];
					retentiontime = String.valueOf(Double.parseDouble(retentiontime) + offset_lc_ms);
					exportnamexmlfraction = "lcfraction_" + mzxmlfraction + "_follownr_"
							+ String.valueOf(followingnumber) + "_" + exportname;
					if (deisotoping == null) {
						deisotoping = new Deisotoping(experiment);
					}
					lipodomics = false;
					no_deisotoping_lipodomics = false;
					if (lipodomics) {
						chargestate = 1;
						sort_on_mass();
						istopehigh = (c13_c12 / chargestate) + ((c13_c12) * percent_deviation_isotopic_distance / 100);
						istopelow = (c13_c12 / chargestate) - ((c13_c12) * percent_deviation_isotopic_distance / 100);
						first_istope_found = false;
						intensity_first_isotope = 0;
						for (int i = 0; i < peaks.length; i++) {
							if (peaks[i][0] > 0) {
								peaks[i][2] = chargestate;
								r = i;
								first_istope_found = false;
								while (r < peaks.length) {
									if ((Math.abs(peaks[r][0] - peaks[i][0]) < istopehigh)
											&& (Math.abs(peaks[r][0] - peaks[i][0]) > istopelow)) {
										if (peaks[r][1] > 0) {
											if ((Math.abs(peaks[r][1] / peaks[i][1]) > 0.2)
													&& (Math.abs(peaks[r][1] / peaks[i][1]) < 0.5)) {
												first_istope_found = true;
											}
											if (!no_deisotoping_lipodomics) {
												peaks[r][1] = -1;
											}
										}
									}
									if (peaks[r][0] > (peaks[i][0] + (2 * c13_c12))) {
										r = peaks.length;
									}
									r++;
								}
								if (!first_istope_found) {
									if (!no_deisotoping_lipodomics) {
										peaks[i][0] = -1;
									}
								}
							}
						}
					} else {
						checkdoublecharge = true;
						for (int i = 0; i < potentialpeaks.length; i++) {
							potentialpeaks[i][0] = -1;
						}
						for (int chargestate = 2; chargestate <= 6; chargestate++) {
							clusters = deisotoping.determine_potential_isotopic_clusters(peaks, chargestate,
									checkdoublecharge);
							listidsofpotentialmonoistopes = clusters.get_listidsofpotentialmonoistopes();
							if ((chargestate > 0) && (chargestate <= max_charge_state)) {
								potential_monoisotopes = listidsofpotentialmonoistopes.toString().split(",");
								if (potential_monoisotopes != null) {
									for (int i = 0; i < potential_monoisotopes.length; i++) {
										if (!potential_monoisotopes[i].toString().equalsIgnoreCase("")) {
											potentialpeaks[Integer
													.parseInt(potential_monoisotopes[i])][0] = (chargestate
															* peaks[Integer.parseInt(potential_monoisotopes[i])][0])
															- ((chargestate - 1) * 1.00728);
											potentialpeaks[Integer
													.parseInt(potential_monoisotopes[i])][1] = peaks[Integer
															.parseInt(potential_monoisotopes[i])][1];
											if (matrix_combined_raw_peaks != null) {
												for (int o = 0; o < matrix_combined_raw_peaks[0].length; o++) {
													deltamzcombinelocal = deltamzcombine;
													if (clusteringtechnique == 1) {
														deltamzcombinelocal = matrix_combined_raw_peaks[0][o]
																* deltamzcombine / 1000000;
													}
													if (potentialpeaks[Integer
															.parseInt(potential_monoisotopes[i])][0] > 0) {
														if (Math.abs(potentialpeaks[Integer
																.parseInt(potential_monoisotopes[i])][0]
																- matrix_combined_raw_peaks[0][o]) < deltamzcombinelocal) {
															peaks_also_found_in_previous_fractions[Integer
																	.parseInt(potential_monoisotopes[i])] = chargestate;

														}
													}
												}
											}
										}
									}
								}
							}
							list_of_monoisotopic_ids = deisotoping.compare_with_theoretical_distribution(peaks);
							index = 0;
							while ((index >= 0) && (index < list_of_monoisotopic_ids.indexOf("\n"))) {
								details_list_of_monoisotopic_ids = list_of_monoisotopic_ids
										.substring(index, list_of_monoisotopic_ids.indexOf("\n")).split(",");
								list_of_monoisotopic_ids = list_of_monoisotopic_ids.substring(
										list_of_monoisotopic_ids.indexOf("\n") + 1, list_of_monoisotopic_ids.length());
								if (storepeaks[Integer.parseInt(details_list_of_monoisotopic_ids[0])][0] > 0) {
									if (Double.parseDouble(details_list_of_monoisotopic_ids[1]) > storepeaks[Integer
											.parseInt(details_list_of_monoisotopic_ids[0])][1]) {
										storepeaks[Integer
												.parseInt(details_list_of_monoisotopic_ids[0])][0] = chargestate;
										storepeaks[Integer.parseInt(details_list_of_monoisotopic_ids[0])][1] = Double
												.parseDouble(details_list_of_monoisotopic_ids[1]);
										storepeaks[Integer.parseInt(details_list_of_monoisotopic_ids[0])][2] = Double
												.parseDouble(details_list_of_monoisotopic_ids[2]);
									} else if (Double
											.parseDouble(details_list_of_monoisotopic_ids[1]) < storepeaks[Integer
													.parseInt(details_list_of_monoisotopic_ids[0])][1]) {
									} else {
										if (Double.parseDouble(details_list_of_monoisotopic_ids[2]) < storepeaks[Integer
												.parseInt(details_list_of_monoisotopic_ids[0])][2]) {
											storepeaks[Integer
													.parseInt(details_list_of_monoisotopic_ids[0])][0] = chargestate;
											storepeaks[Integer
													.parseInt(details_list_of_monoisotopic_ids[0])][1] = Double
															.parseDouble(details_list_of_monoisotopic_ids[1]);
											storepeaks[Integer
													.parseInt(details_list_of_monoisotopic_ids[0])][2] = Double
															.parseDouble(details_list_of_monoisotopic_ids[2]);
										}
									}
								} else {
									storepeaks[Integer.parseInt(details_list_of_monoisotopic_ids[0])][0] = chargestate;
									storepeaks[Integer.parseInt(details_list_of_monoisotopic_ids[0])][1] = Double
											.parseDouble(details_list_of_monoisotopic_ids[1]);
									storepeaks[Integer.parseInt(details_list_of_monoisotopic_ids[0])][2] = Double
											.parseDouble(details_list_of_monoisotopic_ids[2]);
								}
							}
						}
						for (int i = 0; i < storepeaks.length; i++) {
							if ((storepeaks[i][0] > 0) && (storepeaks[i][0] <= max_charge_state)) {
								peaks[i][0] = (storepeaks[i][0] * peaks[i][0]) - ((storepeaks[i][0] - 1) * 1.00728);
								peaks[i][2] = storepeaks[i][0];
								if (peaks[i][0] == potentialpeaks[i][0]) {
									potentialpeaks[i][0] = -1;
								}
							} else {
								if (peaks_also_found_in_previous_fractions[i] > 0) {
									peaks[i][0] = (peaks_also_found_in_previous_fractions[i] * peaks[i][0])
											- ((peaks_also_found_in_previous_fractions[i] - 1) * 1.00728);
									peaks[i][2] = peaks_also_found_in_previous_fractions[i];
									if (peaks[i][0] == potentialpeaks[i][0]) {
										potentialpeaks[i][0] = -1;
									}
								} else {
									peaks[i][0] = -1;
								}
							}
						}
					}
					sort_on_mass();
					export_lcms_fraction();
					average_chargeStates(clusteringtechnique, deltamzcombine);
					create_peak_list(clusteringtechnique, deltamzcombine, minimumnumberoffractions,
							missingfractionsallowed);
				}
			}
			if ((mslevel.trim().equalsIgnoreCase("2")) && (perform_ms2_sequencing)) {
				ms2dataOK = true;
				try {
					testint = Integer.parseInt(precursorCharge);
					if (testint > 0) {
					} else {
						ms2dataOK = false;
					}
				} catch (Exception notok) {
					ms2dataOK = false;
				}
				try {
					testdouble = Double.parseDouble(precursormassovercharge);
					if (testdouble > 0) {
					} else {
						ms2dataOK = false;
					}
				} catch (Exception notok) {
					ms2dataOK = false;
				}
				if (ms2dataOK) {
					mgfstring = "";
					spectrum = base64coder.decode(mzxml_peak_data, precision, byteorder);
					ms2_peaks = simplepeakfind.findlocalmaxima(deltamzsearchmaximum,
							Double.parseDouble(strquantilethreshold), 0, 10000, clusteringtechnique, spectrum);
					if (ms2_peaks != null) {
						if (ms2_peaks.length > 0) {
							if (string_search_engine.trim().equalsIgnoreCase("mascot")) {
								mgfstring = mgfstring + linefeed;
							}
							mgfstring = mgfstring + "BEGIN IONS" + linefeed;
							mgfstring = mgfstring + "PEPMASS=" + precursormassovercharge + linefeed;
							if (string_search_engine.trim().equalsIgnoreCase("mascot")) {
								mgfstring = mgfstring + "SCANS=" + mzxmlfraction + linefeed;
								if (retentiontime.indexOf(".") > -1) {
									mgfstring = mgfstring + "RTINSECONDS="
											+ retentiontime.substring(0, retentiontime.indexOf(".")) + linefeed;
								} else {
									mgfstring = mgfstring + "RTINSECONDS=" + retentiontime + linefeed;
								}
							}
							mgfstring = mgfstring + "CHARGE=" + precursorCharge + "+" + linefeed;
							mgfstring = mgfstring + "TITLE=ms2 scan : " + mzxmlfraction + ", retentiontime : "
									+ retentiontime + linefeed;
							for (int i = 0; i < ms2_peaks.length; i++) {
								mgfstring = mgfstring + ms2_peaks[i][0] + " " + ms2_peaks[i][1] + linefeed;
							}
							mgfstring = mgfstring + "END IONS" + linefeed;
							mgfdata = mgfstring.getBytes();
							try {
								mgf_file.write(mgfdata);
								mgf_file.flush();
							} catch (Exception e) {
								if (cc.debugmode) {
									e.printStackTrace();
								} else {
									JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					}
				}
			}
		}
	}

	private void sort_on_mass() {
		peakstransposed = new double[peaks[0].length][peaks.length];
		for (int i = 0; i < peaks.length; i++) {
			for (int j = 0; j < peaks[0].length; j++) {
				peakstransposed[j][i] = peaks[i][j];
			}
		}
		sortrow = 0;
		sortmatrix = new SortMatrix(peakstransposed, sortrow);
		for (int i = 0; i < peaks.length; i++) {
			for (int j = 0; j < peaks[0].length; j++) {
				peaks[i][j] = peakstransposed[j][i];
			}
		}
	}

	private void average_chargeStates(int clusteringtechnique, double deltamzcombine) {
		deltamzcombinelocal = mass_distance_factor * deltamzcombine;
		count_peaks = new double[peaks.length];
		sum_mass_peaks = new double[peaks.length];
		for (int i = 0; i < peaks.length; i++) {
			count_peaks[i] = 1;
			sum_mass_peaks[i] = peaks[i][0];
			if (peaks[i][0] > 0) {
				for (int j = i + 1; j < peaks.length; j++) {
					if (peaks[j][0] > 0) {
						absolutedifference = Math.abs(peaks[i][0] - peaks[j][0]);
						if (clusteringtechnique == 1) {
							// ppm
							deltamzcombinelocal = mass_distance_factor * peaks[i][0] * deltamzcombine / 1000000;
						}
						if (absolutedifference <= deltamzcombinelocal) {
							if (peaks[i][2] != peaks[j][2]) {
								count_peaks[i] = count_peaks[i] + 1;
								sum_mass_peaks[i] = sum_mass_peaks[i] + peaks[j][0];
								peaks[i][1] = peaks[i][1] + peaks[j][1];
								peaks[j][0] = -1;
							}
						} else {
							j = peaks.length;
						}
					}
				}
			}
		}
		for (int i = 0; i < peaks.length; i++) {
			if (peaks[i][0] > 0) {
				if (count_peaks[i] > 1) {
					peaks[i][0] = sum_mass_peaks[i] / count_peaks[i];
				}
			}
		}
	}

	private void get_remaining_peaks_from_last_spectra(int minimumnumberoffractions) {
		if (!combined_raw_peaks.toString().trim().equalsIgnoreCase("")) {
			tmp_string = combined_raw_peaks.toString().split(",");
			matrix_combined_raw_peaks = new double[10][tmp_string.length];
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[0][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_count_raw_peaks.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[1][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_last_lc_fraction.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[2][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = retentiontime_last_lc_fraction.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[7][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_maximum_intensity.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[3][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_retentiontime_maximum_intensity.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[4][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_retentiontime_start_peak.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[6][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_sum_mass.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[5][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_sum_quadratic_mass.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[8][j] = Double.parseDouble(tmp_string[j]);
			}
			tmp_string = buffer_sum_intensity.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				matrix_combined_raw_peaks[9][j] = Double.parseDouble(tmp_string[j]);
			}
			for (int i = 0; i < matrix_combined_raw_peaks[0].length; i++) {
				missingfractions = followingnumber - (int) matrix_combined_raw_peaks[2][i];
				if (matrix_combined_raw_peaks[1][i] >= minimumnumberoffractions) {
					averagemass = (matrix_combined_raw_peaks[5][i] / matrix_combined_raw_peaks[9][i]);
					standarddeviation = 0;
					if (matrix_combined_raw_peaks[1][i] > 1) {
						standarddeviation = Math.sqrt(((1000000 * matrix_combined_raw_peaks[8][i])
								- (matrix_combined_raw_peaks[1][i] * averagemass * averagemass))
								/ (matrix_combined_raw_peaks[1][i] - 1));
					}
					try {
						export_peak_buffer.append(String.valueOf(averagemass) + "#" + matrix_combined_raw_peaks[3][i]
								+ "#" + matrix_combined_raw_peaks[4][i] + "#" + matrix_combined_raw_peaks[2][i] + "#"
								+ matrix_combined_raw_peaks[1][i] + "#" + matrix_combined_raw_peaks[6][i] + "#"
								+ matrix_combined_raw_peaks[7][i] + "#" + String.valueOf(standarddeviation) + "#"
								+ linefeed);
					} catch (Exception e) {
						if (cc.debugmode) {
							e.printStackTrace();
						} else {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					count_number_of_masses++;
				} else {
					averagemass = (matrix_combined_raw_peaks[5][i] / matrix_combined_raw_peaks[9][i]);
					standarddeviation = 0;
					if (matrix_combined_raw_peaks[1][i] > 1) {
						standarddeviation = Math.sqrt(((1000000 * matrix_combined_raw_peaks[8][i])
								- (matrix_combined_raw_peaks[1][i] * averagemass * averagemass))
								/ (matrix_combined_raw_peaks[1][i] - 1));
					}
					try {
						export_noise_buffer.append(String.valueOf(averagemass) + "#" + matrix_combined_raw_peaks[3][i]
								+ "#" + matrix_combined_raw_peaks[4][i] + "#" + matrix_combined_raw_peaks[2][i] + "#"
								+ matrix_combined_raw_peaks[1][i] + "#" + matrix_combined_raw_peaks[6][i] + "#"
								+ matrix_combined_raw_peaks[7][i] + "#" + String.valueOf(standarddeviation) + "#"
								+ linefeed);
					} catch (Exception e) {
						if (cc.debugmode) {
							e.printStackTrace();
						} else {
							JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					count_number_of_noise_masses++;
				}
			}
		}
		export_lcms_fraction();
	}

	public int return_number_of_noisemasses() {
		return count_number_of_noise_masses;
	}

	private void create_peak_list(int clusteringtechnique, double deltamzcombine, int minimumnumberoffractions,
			int missingfractionsallowed) {
		if (firsttime) {
			combined_raw_peaks = new StringBuffer("");
			buffer_count_raw_peaks = new StringBuffer("");
			buffer_last_lc_fraction = new StringBuffer("");
			retentiontime_last_lc_fraction = new StringBuffer("");
			buffer_maximum_intensity = new StringBuffer("");
			buffer_retentiontime_maximum_intensity = new StringBuffer("");
			buffer_sum_mass = new StringBuffer("");
			buffer_sum_quadratic_mass = new StringBuffer("");
			buffer_sum_intensity = new StringBuffer("");
			buffer_retentiontime_start_peak = new StringBuffer("");
			for (int i = 0; i < peaks.length; i++) {
				if (peaks[i][0] > 0) {
					if (firsttime) {
						combined_raw_peaks.append(String.valueOf(peaks[i][0]));
						buffer_count_raw_peaks.append("1");
						buffer_last_lc_fraction.append(String.valueOf(followingnumber));
						retentiontime_last_lc_fraction.append(String.valueOf(retentiontime));
						buffer_maximum_intensity.append(String.valueOf(peaks[i][1]));
						buffer_retentiontime_maximum_intensity.append(retentiontime);
						buffer_retentiontime_start_peak.append(retentiontime);
						buffer_sum_mass.append(String.valueOf(peaks[i][0] * (peaks[i][1] / 1000)));
						buffer_sum_quadratic_mass.append(String.valueOf(peaks[i][0] * peaks[i][0] / 1000000));
						buffer_sum_intensity.append(peaks[i][1] / 1000);
						firsttime = false;
					} else {
						combined_raw_peaks.append("," + String.valueOf(peaks[i][0]));
						buffer_count_raw_peaks.append(",1");
						buffer_last_lc_fraction.append(String.valueOf("," + String.valueOf(followingnumber)));
						retentiontime_last_lc_fraction.append("," + String.valueOf(retentiontime));
						buffer_maximum_intensity.append("," + String.valueOf(peaks[i][1]));
						buffer_retentiontime_maximum_intensity.append("," + retentiontime);
						buffer_sum_mass.append("," + String.valueOf(peaks[i][0] * (peaks[i][1] / 1000)));
						buffer_sum_quadratic_mass.append("," + String.valueOf(peaks[i][0] * peaks[i][0] / 1000000));
						buffer_retentiontime_start_peak.append("," + retentiontime);
						buffer_sum_intensity.append("," + (peaks[i][1] / 1000));
					}
				}
			}
		} else {
			tmp_string = combined_raw_peaks.toString().split(",");
			matrix_combined_raw_peaks = new double[10][tmp_string.length];
			for (int j = 0; j < tmp_string.length; j++) {
				if (!tmp_string[j].trim().equalsIgnoreCase("")) {
					matrix_combined_raw_peaks[0][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[0][j] = -1;
				}
			}
			tmp_string = buffer_count_raw_peaks.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[1][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[1][j] = 0;
				}
			}
			tmp_string = buffer_last_lc_fraction.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[2][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[2][j] = 0;
				}
			}
			tmp_string = retentiontime_last_lc_fraction.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[7][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[7][j] = 0;
				}
			}
			tmp_string = buffer_maximum_intensity.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[3][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[3][j] = 0;
				}
			}
			tmp_string = buffer_retentiontime_maximum_intensity.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[4][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[4][j] = 0;
				}
			}
			tmp_string = buffer_retentiontime_start_peak.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[6][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[6][j] = 0;
				}
			}
			tmp_string = buffer_sum_mass.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[5][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[5][j] = 0;
				}
			}
			tmp_string = buffer_sum_quadratic_mass.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[8][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[8][j] = 0;
				}
			}
			tmp_string = buffer_sum_intensity.toString().split(",");
			for (int j = 0; j < tmp_string.length; j++) {
				if (matrix_combined_raw_peaks[0][j] > -1) {
					matrix_combined_raw_peaks[9][j] = Double.parseDouble(tmp_string[j]);
				} else {
					matrix_combined_raw_peaks[9][j] = 0;
				}
			}
			machedpeak = new String[peaks.length];
			for (int j = 0; j < peaks.length; j++) {
				machedpeak[j] = "no";
			}
			sortrow = 0;
			sortmatrix = new SortMatrix(matrix_combined_raw_peaks, sortrow);
			sortmatrix = null;
			int p = 0;
			for (int i = 0; i < matrix_combined_raw_peaks[0].length; i++) {
				do_once = true;
				for (int j = p; j < peaks.length; j++) {
					deltamzcombinelocal = mass_distance_factor * deltamzcombine;
					if (clusteringtechnique == 1) {
						deltamzcombinelocal = mass_distance_factor * matrix_combined_raw_peaks[0][i] * deltamzcombine
								/ 1000000;
					}
					if (peaks[j][0] > 0) {
						if (Math.abs(peaks[j][0] - matrix_combined_raw_peaks[0][i]) < deltamzcombinelocal) {
							if (do_once) {
								if (machedpeak[j].trim().equalsIgnoreCase("no")) {
									do_once = false;
									matrix_combined_raw_peaks[5][i] = matrix_combined_raw_peaks[5][i]
											+ (peaks[j][0] * (peaks[j][1] / 1000));
									matrix_combined_raw_peaks[9][i] = matrix_combined_raw_peaks[9][i]
											+ (peaks[j][1] / 1000);
									matrix_combined_raw_peaks[8][i] = matrix_combined_raw_peaks[8][i]
											+ (peaks[j][0] * peaks[j][0] / 1000000);
									matrix_combined_raw_peaks[2][i] = followingnumber;
									matrix_combined_raw_peaks[7][i] = Double.valueOf(retentiontime);
									matrix_combined_raw_peaks[1][i] = matrix_combined_raw_peaks[1][i] + 1;
									if (peaks[j][1] > matrix_combined_raw_peaks[3][i]) {
										matrix_combined_raw_peaks[3][i] = peaks[j][1];
										matrix_combined_raw_peaks[4][i] = Double.parseDouble(retentiontime);
										matrix_combined_raw_peaks[0][i] = peaks[j][0];
									}
								}
							}
							machedpeak[j] = "yes";
							p = j + 1;
							j = peaks.length;
						} else if (peaks[j][0] - matrix_combined_raw_peaks[0][i] > deltamzcombinelocal) {
							j = peaks.length;
						}
					}
				}
			}
			combined_raw_peaks = new StringBuffer("");
			buffer_count_raw_peaks = new StringBuffer("");
			buffer_last_lc_fraction = new StringBuffer("");
			retentiontime_last_lc_fraction = new StringBuffer("");
			buffer_maximum_intensity = new StringBuffer("");
			buffer_retentiontime_maximum_intensity = new StringBuffer("");
			buffer_sum_mass = new StringBuffer("");
			buffer_sum_quadratic_mass = new StringBuffer("");
			buffer_sum_intensity = new StringBuffer("");
			buffer_retentiontime_start_peak = new StringBuffer("");
			firsttime2 = true;
			for (int i = 0; i < matrix_combined_raw_peaks[0].length; i++) {
				missingfractions = followingnumber - (int) matrix_combined_raw_peaks[2][i];
				if (missingfractions > missingfractionsallowed) {
					if (matrix_combined_raw_peaks[1][i] >= minimumnumberoffractions) {
						averagemass = (matrix_combined_raw_peaks[5][i] / matrix_combined_raw_peaks[9][i]);
						standarddeviation = 0;
						if (matrix_combined_raw_peaks[1][i] > 1) {
							standarddeviation = Math.sqrt(((1000000 * matrix_combined_raw_peaks[8][i])
									- (matrix_combined_raw_peaks[1][i] * averagemass * averagemass))
									/ (matrix_combined_raw_peaks[1][i] - 1));
						}
						try {
							export_peak_buffer.append(String.valueOf(averagemass) + "#"
									+ matrix_combined_raw_peaks[3][i] + "#" + matrix_combined_raw_peaks[4][i] + "#"
									+ matrix_combined_raw_peaks[2][i] + "#" + matrix_combined_raw_peaks[1][i] + "#"
									+ matrix_combined_raw_peaks[6][i] + "#" + matrix_combined_raw_peaks[7][i] + "#"
									+ String.valueOf(standarddeviation) + "#" + linefeed);
						} catch (Exception e) {
							if (cc.debugmode) {
								e.printStackTrace();
							} else {
								JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						count_number_of_masses++;
					} else {
						averagemass = (matrix_combined_raw_peaks[5][i] / matrix_combined_raw_peaks[9][i]);
						standarddeviation = 0;
						if (matrix_combined_raw_peaks[1][i] > 1) {
							standarddeviation = Math.sqrt(((1000000 * matrix_combined_raw_peaks[8][i])
									- (matrix_combined_raw_peaks[1][i] * averagemass * averagemass))
									/ (matrix_combined_raw_peaks[1][i] - 1));
						}
						try {
							export_noise_buffer.append(String.valueOf(averagemass) + "#"
									+ matrix_combined_raw_peaks[3][i] + "#" + matrix_combined_raw_peaks[4][i] + "#"
									+ matrix_combined_raw_peaks[2][i] + "#" + matrix_combined_raw_peaks[1][i] + "#"
									+ matrix_combined_raw_peaks[6][i] + "#" + matrix_combined_raw_peaks[7][i] + "#"
									+ String.valueOf(standarddeviation) + "#" + linefeed);
						} catch (Exception e) {
							if (cc.debugmode) {
								e.printStackTrace();
							} else {
								JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
						count_number_of_noise_masses++;
					}
				} else {
					if (firsttime2) {
						combined_raw_peaks.append(matrix_combined_raw_peaks[0][i]);
						buffer_count_raw_peaks.append(matrix_combined_raw_peaks[1][i]);
						buffer_last_lc_fraction.append(matrix_combined_raw_peaks[2][i]);
						retentiontime_last_lc_fraction.append(matrix_combined_raw_peaks[7][i]);
						buffer_maximum_intensity.append(matrix_combined_raw_peaks[3][i]);
						buffer_retentiontime_maximum_intensity.append(matrix_combined_raw_peaks[4][i]);
						buffer_sum_mass.append(matrix_combined_raw_peaks[5][i]);
						buffer_sum_quadratic_mass.append(matrix_combined_raw_peaks[8][i]);
						buffer_sum_intensity.append(matrix_combined_raw_peaks[9][i]);
						buffer_retentiontime_start_peak.append(matrix_combined_raw_peaks[6][i]);
						firsttime2 = false;
					} else {
						combined_raw_peaks.append("," + matrix_combined_raw_peaks[0][i]);
						buffer_count_raw_peaks.append("," + matrix_combined_raw_peaks[1][i]);
						buffer_last_lc_fraction.append("," + matrix_combined_raw_peaks[2][i]);
						retentiontime_last_lc_fraction.append("," + matrix_combined_raw_peaks[7][i]);
						buffer_maximum_intensity.append("," + matrix_combined_raw_peaks[3][i]);
						buffer_retentiontime_maximum_intensity.append("," + matrix_combined_raw_peaks[4][i]);
						buffer_sum_mass.append("," + matrix_combined_raw_peaks[5][i]);
						buffer_sum_quadratic_mass.append("," + matrix_combined_raw_peaks[8][i]);
						buffer_sum_intensity.append("," + matrix_combined_raw_peaks[9][i]);
						buffer_retentiontime_start_peak.append("," + matrix_combined_raw_peaks[6][i]);
					}
				}
			}
			for (int j = 0; j < peaks.length; j++) {
				if ((peaks[j][0] > 0) && (machedpeak[j].trim().equalsIgnoreCase("no"))) {
					if (firsttime2) {
						combined_raw_peaks.append(String.valueOf(peaks[j][0]));
						buffer_count_raw_peaks.append("1");
						buffer_last_lc_fraction.append(String.valueOf(followingnumber));
						retentiontime_last_lc_fraction.append(String.valueOf(retentiontime));
						buffer_maximum_intensity.append(String.valueOf(peaks[j][1]));
						buffer_retentiontime_maximum_intensity.append(retentiontime);
						buffer_retentiontime_start_peak.append(retentiontime);
						buffer_sum_mass.append(String.valueOf(peaks[j][0] * (peaks[j][1] / 1000)));
						buffer_sum_quadratic_mass.append(String.valueOf(peaks[j][0] * peaks[j][0] / 1000000));
						buffer_sum_intensity.append(String.valueOf(peaks[j][1] / 1000));
						firsttime2 = false;
					} else {
						combined_raw_peaks.append("," + String.valueOf(peaks[j][0]));
						buffer_count_raw_peaks.append("," + "1");
						buffer_last_lc_fraction.append("," + String.valueOf(followingnumber));
						retentiontime_last_lc_fraction.append("," + String.valueOf(retentiontime));
						buffer_maximum_intensity.append("," + String.valueOf(peaks[j][1]));
						buffer_retentiontime_maximum_intensity.append("," + retentiontime);
						buffer_retentiontime_start_peak.append("," + retentiontime);
						buffer_sum_mass.append("," + String.valueOf(peaks[j][0] * (peaks[j][1] / 1000)));
						buffer_sum_quadratic_mass.append("," + String.valueOf(peaks[j][0] * peaks[j][0] / 1000000));
						buffer_sum_intensity.append(String.valueOf("," + (peaks[j][1]) / 1000));
					}
				}
			}
		}
	}

	private void export_lcms_fraction() {
		makedir = true;
		exportbuffer = new StringBuffer();
		for (int i = 0; i <= peaks.length - 1; i++) {
			tempBD = new BigDecimal(peaks[i][0]);
			if (peaks[i][0] > 0) {
				exportbuffer
						.append(tempBD.setScale(6, BigDecimal.ROUND_HALF_EVEN) + " " + peaks[i][1] + " " + peaks[i][2]);
				exportbuffer.append(linefeed);
			}
		}
		exportstring = exportbuffer.toString();
		data = exportstring.getBytes();
		filegrootte_kbytes = (data.length / 1024.000);
		filetransported = false;
		if (ftp == null) {
			try {
				ftp = new FTPClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			ftp.setRemoteHost(cc.ftpremotehost);
			ftp.connect();
			ftp.login(cc.ftpuser, cc.ftppassword);
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setType(FTPTransferType.BINARY);
		} catch (Exception e) {
			;
		}
		try {
			ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
					+ String.valueOf(experiment.getExperimentid()));
			directoryfiles = ftp.dir(".");
			for (int j = 0; j < directoryfiles.length; j++) {
				directoryfile = directoryfiles[j].replaceAll("\r", "");
				if (directoryfile.equals(String.valueOf(filenumber))) {
					makedir = false;
				}
			}
			if (makedir == true) {
				ftp.mkdir(File.separator + experiment.getExperimentyear() + File.separator
						+ String.valueOf(experiment.getExperimentid()) + File.separator + String.valueOf(filenumber));
			}
			ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
					+ String.valueOf(experiment.getExperimentid()) + File.separator + String.valueOf(filenumber));
			if (data != null) {
				ftp.put(data, exportnamexmlfraction);
				filetransported = true;
			}
			ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator
					+ String.valueOf(experiment.getExperimentid()));
			if (!export_peak_buffer.toString().equalsIgnoreCase("")) {
				ftp.put(export_peak_buffer.toString().getBytes(), exportname, true);
				filetransported__list = true;
			}
			export_peak_buffer = new StringBuffer("");
			if (!export_noise_buffer.toString().equalsIgnoreCase("")) {
				ftp.put(export_noise_buffer.toString().getBytes(), "noise_" + exportname, true);
				filetransported__noiselist = true;
			}
			export_noise_buffer = new StringBuffer("");
			ftp.quit();
		} catch (Exception e) {
			if (cc.debugmode) {
			} else {
			}
		}
		try {
			testint = Integer.parseInt(mzxmlfraction);
		} catch (Exception ex) {
			mzxmlfraction = String.valueOf(followingnumber);
		}

		cc.actualtime.resettime();
		strdatum = cc.actualtime.getdatestring();
		strjaar = cc.actualtime.getyear();
		strtime = cc.actualtime.gettimestring();
		if ((filetransported) && (!sampleid.trim().equals("")) && (!Group_id.trim().equals(""))) {
			updatesample = resultService.insertresultrecord("lc_fraction", filegrootte_kbytes, sampleid, Group_id,
					experiment, strdatum, strtime, exportnamexmlfraction, strjaar, retentiontime, mzxmlfraction,
					filenumber, offset_lc_ms);
		}
		try {
			ftp.quit();
		} catch (Exception e) {
		}
	}

	private String convert_to_number(String inputstring) {
		outputstring = "";
		outputstringbuffer = new StringBuffer(outputstring);
		for (int i = 0; i < inputstring.length(); i++) {
			if (teststring.indexOf(inputstring.charAt(i)) >= 0) {
				outputstringbuffer.append(inputstring.charAt(i));
			}
		}
		outputstring = outputstringbuffer.toString();
		return outputstring;
	}

}
