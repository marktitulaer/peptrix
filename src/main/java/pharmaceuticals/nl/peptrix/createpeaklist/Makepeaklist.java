package pharmaceuticals.nl.peptrix.createpeaklist;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.sql.SQLException;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.utils.SortMatrix;

import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class Makepeaklist {

	Controller cc;

	Experiment experiment;

	FileInputStream readfile;

	SortMatrix sortmatrix;

	Object[][] odata;

	String[] arraycombinedpeaks;

	String[] arraymaxtimecobinedpeaks;

	String[] arraymintimecobinedpeaks;

	String[] arraycombinedcounts;

	String[] mass;

	String[] arraytimecobinedpeaks;

	StringBuffer linebuffer;

	StringBuffer buffermasscombinedpeaks;

	StringBuffer buffertimecombinedpeaks;

	StringBuffer bufferstringsamplecount;

	StringBuffer buffermintimecombinedpeaks;

	StringBuffer buffermaxtimecombinedpeaks;

	String fileSeparator;

	String line;

	String stringsamplecount;

	double[][] doublearraycombinedpeaks;

	double doublemaxtimecombinedpeaks;

	double doublemasscombinedpeaks;

	double doublemintimecombinedpeaks;

	double valueprevious;

	double min_time_window;

	double max_time_window;

	double deltatimelocalcombine;

	double tempmass;

	double temptime;

	double doublemass;

	double doubletime;

	double doubletimecombined;

	double absolutedifference;

	double max_retention_time;

	double min_retention_time;

	int index1 = 0;

	int index2 = 0;

	int ch;

	int change_j;

	int increase_k;

	int j_bewaar;

	int number;

	int deletemasseswithcount = 0;

	int timeclusteringtechnique;

	boolean withintimewindow;

	boolean first = true;

	boolean startcombinedpeaks = true;

	boolean addmasstolist = true;

	ResultService resultService;

	public Makepeaklist(Controller cc, Experiment experiment) {
		this.cc = cc;
		this.experiment = experiment;
		resultService = new ResultServiceImpl(cc);
	}

	public void makepeaklist(Progress progress, FTPClient ftp) {
		timeclusteringtechnique = experiment.gettime_clustering_absolute_or_percentage();
		progress.setnumberandtext(0, "Make peaklist .... ");
		deletemasseswithcount = experiment.getminimum_number_of_masses_that_should_be_present();
		odata = resultService.select_peak_lists(experiment.getExperimentid(), experiment.getquantilethreshold());
		if (odata != null) {
			first = true;
			startcombinedpeaks = true;
			buffermasscombinedpeaks = new StringBuffer("");
			buffertimecombinedpeaks = new StringBuffer("");
			buffermintimecombinedpeaks = new StringBuffer("");
			buffermaxtimecombinedpeaks = new StringBuffer("");
			bufferstringsamplecount = new StringBuffer("");
			progress.setmaximum(odata.length);
			stringsamplecount = "";
			for (int i = 0; i < odata.length; i++) {
				addpeaks(odata[i][0].toString(), ftp);
				first = false;
				progress.setnumberandtext((i + 1), "Make peaklist .." + odata[i][0].toString());
			}
			stringsamplecount = bufferstringsamplecount.toString();
		}
		if (deletemasseswithcount > 0) {
			arraycombinedpeaks = buffermasscombinedpeaks.toString().split(",");
			arraytimecobinedpeaks = buffertimecombinedpeaks.toString().split(",");
			arraymintimecobinedpeaks = buffermintimecombinedpeaks.toString().split(",");
			arraymaxtimecobinedpeaks = buffermaxtimecombinedpeaks.toString().split(",");
			buffermasscombinedpeaks = new StringBuffer("");
			buffertimecombinedpeaks = new StringBuffer("");
			buffermintimecombinedpeaks = new StringBuffer("");
			buffermaxtimecombinedpeaks = new StringBuffer("");
			startcombinedpeaks = true;
			index1 = 0;
			index2 = 0;
			for (int i = 0; i < arraycombinedpeaks.length; i++) {
				index2 = bufferstringsamplecount.indexOf(",", index1 + 1);
				if (index1 > 0) {
					index1 = index1 + 1;
				}
				if (index2 < 0) {
					index2 = bufferstringsamplecount.length();
				}
				if (!bufferstringsamplecount.substring(index1, index2).trim().equalsIgnoreCase("") && (Integer
						.parseInt(bufferstringsamplecount.substring(index1, index2)) > deletemasseswithcount)) {
					if (startcombinedpeaks == true) {
						buffermasscombinedpeaks.append(arraycombinedpeaks[i].trim());
						if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
								|| (experiment.getequipmentid() == 5)) {
							buffertimecombinedpeaks.append(arraytimecobinedpeaks[i].trim());
							buffermintimecombinedpeaks.append(arraymintimecobinedpeaks[i].trim());
							buffermaxtimecombinedpeaks.append(arraymaxtimecobinedpeaks[i].trim());
						}
						startcombinedpeaks = false;
					} else {
						buffermasscombinedpeaks.append("," + arraycombinedpeaks[i].trim());
						if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
								|| (experiment.getequipmentid() == 5)) {
							buffertimecombinedpeaks.append("," + arraytimecobinedpeaks[i].trim());
							buffermintimecombinedpeaks.append("," + arraymintimecobinedpeaks[i].trim());
							buffermaxtimecombinedpeaks.append("," + arraymaxtimecobinedpeaks[i].trim());
						}
					}
				}
				index1 = bufferstringsamplecount.indexOf(",", index1 + 1);
			}
		}
		arraycombinedpeaks = buffermasscombinedpeaks.toString().split(",");
		arraytimecobinedpeaks = buffertimecombinedpeaks.toString().split(",");
		arraymintimecobinedpeaks = buffermintimecombinedpeaks.toString().split(",");
		arraymaxtimecobinedpeaks = buffermaxtimecombinedpeaks.toString().split(",");
		arraycombinedcounts = bufferstringsamplecount.toString().split(",");
		doublearraycombinedpeaks = new double[5][arraycombinedpeaks.length];
		experiment.setcombinedpeaks(doublearraycombinedpeaks);
		for (int i = 0; i < arraycombinedpeaks.length; i++) {
			if (!arraycombinedpeaks[i].trim().equalsIgnoreCase("")) {
				doublearraycombinedpeaks[0][i] = Double.valueOf(arraycombinedpeaks[i]).doubleValue();
			} else {
				doublearraycombinedpeaks[0][i] = -1;
			}
			if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
					|| (experiment.getequipmentid() == 5)) {
				if (!arraytimecobinedpeaks[i].trim().equalsIgnoreCase("")) {
					doublearraycombinedpeaks[1][i] = Double.valueOf(arraytimecobinedpeaks[i]).doubleValue();
				} else {
					doublearraycombinedpeaks[1][i] = -1;
				}
				if (!arraymintimecobinedpeaks[i].trim().equalsIgnoreCase("")) {
					doublearraycombinedpeaks[2][i] = Double.valueOf(arraymintimecobinedpeaks[i]).doubleValue();
				} else {
					doublearraycombinedpeaks[2][i] = -1;
				}
				if (!arraymaxtimecobinedpeaks[i].trim().equalsIgnoreCase("")) {
					doublearraycombinedpeaks[3][i] = Double.valueOf(arraymaxtimecobinedpeaks[i]).doubleValue();
				} else {
					doublearraycombinedpeaks[3][i] = -1;
				}
				if (!arraycombinedcounts[i].trim().equalsIgnoreCase("")) {
					doublearraycombinedpeaks[4][i] = Double.valueOf(arraycombinedcounts[i]).doubleValue();
				} else {
					doublearraycombinedpeaks[4][i] = -1;
				}
			}
		}
		sortmatrix = new SortMatrix(doublearraycombinedpeaks, 0);
		if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
				|| (experiment.getequipmentid() == 5)) {
			double deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
			deltatimelocalcombine = experiment.gettime_window_combining_peptide_masses();
			for (int i = 0; i < doublearraycombinedpeaks[0].length; i++) {
				if (doublearraycombinedpeaks[0][i] > 0) {
					increase_k = i;
					if (experiment.getclusteringtechnique() == 1) {
						// ppm
						deltamzcombinelocal = doublearraycombinedpeaks[0][i]
								* Double.parseDouble(experiment.getdelta_mz_combine()) / 1000000;
					}
					while (increase_k < doublearraycombinedpeaks[0].length - 1) {
						increase_k++;
						if (doublearraycombinedpeaks[0][increase_k] > 0) {
							if (Math.abs(doublearraycombinedpeaks[0][i]
									- doublearraycombinedpeaks[0][increase_k]) < deltamzcombinelocal) {
								if (timeclusteringtechnique == 1) {
									// %
									deltatimelocalcombine = experiment.gettime_window_combining_peptide_masses()
											* doublearraycombinedpeaks[1][i] / 100;
								}
								withintimewindow = false;
								min_time_window = -1;
								max_time_window = -1;
								if (doublearraycombinedpeaks[2][i] > 0) {
									min_time_window = doublearraycombinedpeaks[2][i];
								}
								if (doublearraycombinedpeaks[2][increase_k] > 0) {
									if (doublearraycombinedpeaks[2][increase_k] < min_time_window) {
										min_time_window = doublearraycombinedpeaks[2][increase_k];
									}
								}
								if (doublearraycombinedpeaks[3][i] > 0) {
									max_time_window = doublearraycombinedpeaks[3][i];
								}
								if (doublearraycombinedpeaks[3][increase_k] > 0) {
									if (doublearraycombinedpeaks[3][increase_k] > max_time_window) {
										max_time_window = doublearraycombinedpeaks[3][increase_k];
									}
								}
								if (Math.abs(doublearraycombinedpeaks[1][i]
										- doublearraycombinedpeaks[1][increase_k]) < deltatimelocalcombine) {
									withintimewindow = true;
								}
								if (doublearraycombinedpeaks[2][increase_k] > 0) {
									if (Math.abs(doublearraycombinedpeaks[2][increase_k]
											- doublearraycombinedpeaks[1][i]) < deltatimelocalcombine) {
										withintimewindow = true;
									}
								}
								if (doublearraycombinedpeaks[3][increase_k] > 0) {
									if (Math.abs(doublearraycombinedpeaks[3][increase_k]
											- doublearraycombinedpeaks[1][i]) < deltatimelocalcombine) {
										withintimewindow = true;
									}
								}
								if (doublearraycombinedpeaks[2][i] > 0) {
									if (Math.abs(doublearraycombinedpeaks[2][i]
											- doublearraycombinedpeaks[1][increase_k]) < deltatimelocalcombine) {
										withintimewindow = true;
									}
								}
								if (doublearraycombinedpeaks[3][i] > 0) {
									if (Math.abs(doublearraycombinedpeaks[3][i]
											- doublearraycombinedpeaks[1][increase_k]) < deltatimelocalcombine) {
										withintimewindow = true;
									}
								}
								if ((doublearraycombinedpeaks[2][increase_k] > 0)
										&& (doublearraycombinedpeaks[3][increase_k] > 0)) {
									if ((doublearraycombinedpeaks[2][increase_k] < doublearraycombinedpeaks[1][i])
											&& (doublearraycombinedpeaks[3][increase_k] > doublearraycombinedpeaks[1][i])) {
										withintimewindow = true;
									}
								}
								if ((doublearraycombinedpeaks[2][increase_k] > 0)
										&& (doublearraycombinedpeaks[3][increase_k] > 0)
										&& (doublearraycombinedpeaks[2][i] > 0)) {
									if ((doublearraycombinedpeaks[2][increase_k] < doublearraycombinedpeaks[2][i])
											&& (doublearraycombinedpeaks[3][increase_k] > doublearraycombinedpeaks[2][i])) {
										withintimewindow = true;
									}
								}
								if ((doublearraycombinedpeaks[2][increase_k] > 0)
										&& (doublearraycombinedpeaks[3][increase_k] > 0)
										&& (doublearraycombinedpeaks[3][i] > 0)) {
									if ((doublearraycombinedpeaks[2][increase_k] < doublearraycombinedpeaks[3][i])
											&& (doublearraycombinedpeaks[3][increase_k] > doublearraycombinedpeaks[3][i])) {
										withintimewindow = true;
									}
								}
								if ((doublearraycombinedpeaks[2][increase_k] > 0)
										&& (doublearraycombinedpeaks[3][increase_k] > 0)
										&& (doublearraycombinedpeaks[2][i] > 0)
										&& (doublearraycombinedpeaks[3][i] > 0)) {
									if ((doublearraycombinedpeaks[2][increase_k] > doublearraycombinedpeaks[2][i])
											&& (doublearraycombinedpeaks[3][increase_k] < doublearraycombinedpeaks[3][i])) {
										withintimewindow = true;
									}
									if ((doublearraycombinedpeaks[2][i] > doublearraycombinedpeaks[2][increase_k])
											&& (doublearraycombinedpeaks[3][i] < doublearraycombinedpeaks[3][increase_k])) {
										withintimewindow = true;
									}
								}
								if (withintimewindow) {
									if (min_time_window > -1) {
										doublearraycombinedpeaks[2][i] = min_time_window;
									}
									if (max_time_window > -1) {
										doublearraycombinedpeaks[3][i] = max_time_window;
									}
									if ((doublearraycombinedpeaks[4][i] > 0)
											&& (doublearraycombinedpeaks[4][increase_k] > 0)) {
										doublearraycombinedpeaks[1][i] = ((doublearraycombinedpeaks[4][i]
												* doublearraycombinedpeaks[1][i])
												+ (doublearraycombinedpeaks[4][increase_k]
														* doublearraycombinedpeaks[1][increase_k]))
												/ (doublearraycombinedpeaks[4][i]
														+ doublearraycombinedpeaks[4][increase_k]);
										doublearraycombinedpeaks[0][i] = ((doublearraycombinedpeaks[4][i]
												* doublearraycombinedpeaks[0][i])
												+ (doublearraycombinedpeaks[4][increase_k]
														* doublearraycombinedpeaks[0][increase_k]))
												/ (doublearraycombinedpeaks[4][i]
														+ doublearraycombinedpeaks[4][increase_k]);
										doublearraycombinedpeaks[4][i] = doublearraycombinedpeaks[4][i]
												+ doublearraycombinedpeaks[4][increase_k];
									}
									doublearraycombinedpeaks[0][increase_k] = 9999;
									doublearraycombinedpeaks[1][increase_k] = 0.1;
								}
							} else {
								increase_k = doublearraycombinedpeaks[0].length;
							}
						}
					}
				}
			}
		}
		experiment.setnumberofmasses(arraycombinedpeaks.length);
		sortmatrix = new SortMatrix(doublearraycombinedpeaks, 0);
	}

	private void addpeaks(String filename, FTPClient ftp) {
		double deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
		doublemaxtimecombinedpeaks = -1;
		doublemintimecombinedpeaks = -1;
		try {
			ftp.quit();
		} catch (Exception e) {
		}
		try {
			ftp.setRemoteHost(cc.ftpremotehost);
			ftp.connect();
			ftp.login(cc.ftpuser, cc.ftppassword);
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setTimeout(cc.ftp_longtime);
			ftp.setType(FTPTransferType.BINARY);
			ftp.chdir(File.separator + experiment.getExperimentyear() + File.separator + experiment.getExperimentid());
			try {
				if (readfile != null) {
					readfile.close();
				}
			} catch (IOException e) {
			}
			readfile = null;
			fileSeparator = System.getProperty("file.separator");
			ftp.get(cc.userhome + fileSeparator + "temp_list.txt", filename);
			try {
				readfile = new FileInputStream(cc.userhome + fileSeparator + "temp_list.txt");
			} catch (IOException e) {
				if (cc.debugmode) {
					e.printStackTrace();
				} else {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		try {
			if ((readfile != null) && (readfile.available() > 0)) {
				line = "";
				linebuffer = new StringBuffer(line);
				while ((ch = readfile.read()) != -1) {
					if ((ch != 10) && (ch != 13)) {
						linebuffer.append((char) ch);
					}
					if (ch == 10) {
						line = linebuffer.toString();
						mass = line.split(" ");
						min_retention_time = -1;
						max_retention_time = -1;
						if ((experiment.getequipmentid() == 4) && (mass.length > 4)) {
							if (mass[3].trim().equalsIgnoreCase("")) {
								min_retention_time = -1.0;
							} else {
								min_retention_time = Double.valueOf(mass[3]).doubleValue();
							}
							if (mass[4].trim().equalsIgnoreCase("")) {
								max_retention_time = -1.0;
							} else {
								max_retention_time = Double.valueOf(mass[4]).doubleValue();
							}
						}
						if (first == true) {
							addmasstolist = true;
							if ((experiment.getequipmentid() == 4) && (mass.length > 4)) {
								arraycombinedpeaks = buffermasscombinedpeaks.toString().split(",");
								arraycombinedcounts = bufferstringsamplecount.toString().split(",");
								arraytimecobinedpeaks = buffertimecombinedpeaks.toString().split(",");
								arraymintimecobinedpeaks = buffermintimecombinedpeaks.toString().split(",");
								arraymaxtimecobinedpeaks = buffermaxtimecombinedpeaks.toString().split(",");
								try {
									doublemass = Double.valueOf(mass[0].trim()).doubleValue();
								} catch (Exception ex) {
									doublemass = -1;
								}
								if (doublemass > 0) {
									deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
									if (experiment.getclusteringtechnique() == 1) {
										// ppm
										deltamzcombinelocal = doublemass
												* Double.parseDouble(experiment.getdelta_mz_combine()) / 1000000;
									}
									change_j = arraycombinedpeaks.length - 1;
									while (change_j >= 0) {
										if (arraycombinedpeaks[change_j].trim().equalsIgnoreCase("")) {
											doublemasscombinedpeaks = -1.0;
										} else {
											doublemasscombinedpeaks = Double.valueOf(arraycombinedpeaks[change_j])
													.doubleValue();
										}
										deltatimelocalcombine = experiment.gettime_window_combining_peptide_masses();
										if (timeclusteringtechnique == 1) {
											// %
											deltatimelocalcombine = experiment.gettime_window_combining_peptide_masses()
													* Double.valueOf(arraytimecobinedpeaks[change_j]).doubleValue()
													/ 100;
										}
										if (doublemasscombinedpeaks > -1) {
											if (Math.abs(doublemass - doublemasscombinedpeaks) < deltamzcombinelocal) {
												withintimewindow = false;
												if (mass[2].trim().equalsIgnoreCase("")) {
													doubletime = -1.0;
												} else {
													doubletime = Double.valueOf(mass[2]).doubleValue();
												}
												if (arraytimecobinedpeaks[change_j].trim().equalsIgnoreCase("")) {
													doubletimecombined = -1.0;
												} else {
													doubletimecombined = Double.valueOf(arraytimecobinedpeaks[change_j])
															.doubleValue();
												}
												if (Math.abs(doubletime - doubletimecombined) < deltatimelocalcombine) {
													withintimewindow = true;
												}
												if (min_retention_time > -1) {
													if (Math.abs(min_retention_time
															- doubletimecombined) < deltatimelocalcombine) {
														withintimewindow = true;
													}
												}
												if (max_retention_time > -1) {
													if (Math.abs(max_retention_time
															- doubletimecombined) < deltatimelocalcombine) {
														withintimewindow = true;
													}
												}
												if (doublemintimecombinedpeaks > -1) {
													if (Math.abs(doublemintimecombinedpeaks
															- doubletime) < deltatimelocalcombine) {
														withintimewindow = true;
													}
												}
												if (doublemaxtimecombinedpeaks > -1) {
													if (Math.abs(doublemaxtimecombinedpeaks
															- doubletime) < deltatimelocalcombine) {
														withintimewindow = true;
													}
												}
												if ((min_retention_time > -1) && (max_retention_time > -1)) {
													if ((min_retention_time < doubletimecombined)
															&& (max_retention_time > doubletimecombined)) {
														withintimewindow = true;
													}
												}
												if ((min_retention_time > -1) && (max_retention_time > -1)
														&& (doublemintimecombinedpeaks > -1)) {
													if ((min_retention_time < doublemintimecombinedpeaks)
															&& (max_retention_time > doublemintimecombinedpeaks)) {
														withintimewindow = true;
													}
												}
												if ((min_retention_time > -1) && (max_retention_time > -1)
														&& (doublemaxtimecombinedpeaks > -1)) {
													if ((min_retention_time < doublemaxtimecombinedpeaks)
															&& (max_retention_time > doublemaxtimecombinedpeaks)) {
														withintimewindow = true;
													}
												}
												if ((min_retention_time > -1) && (max_retention_time > -1)
														&& (doublemaxtimecombinedpeaks > -1)
														&& (doublemintimecombinedpeaks > -1)) {
													if ((min_retention_time > doublemintimecombinedpeaks)
															&& (max_retention_time < doublemaxtimecombinedpeaks)) {
														withintimewindow = true;
													}
													if ((doublemintimecombinedpeaks > min_retention_time)
															&& (doublemaxtimecombinedpeaks < max_retention_time)) {
														withintimewindow = true;
													}
												}
												if (withintimewindow) {
													addmasstolist = false;
												}
											} else {
												change_j = 0;
											}
										}
										change_j--;
									}
								}
							}
							if (experiment.getperform_ms2_sequencing() && experiment.getonly_ms2_sequencedmasses()) {
								if (experiment.getequipmentid() == 4) {
									if (mass.length < 6) {
										addmasstolist = false;
									}
								}
							}
							if (addmasstolist) {
								if (startcombinedpeaks == true) {
									if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
											|| (experiment.getequipmentid() == 5)) {
										buffermasscombinedpeaks.append(mass[0].trim());
										bufferstringsamplecount.append("1");
										buffertimecombinedpeaks.append(mass[2].trim());
										buffermintimecombinedpeaks.append(String.valueOf(min_retention_time));
										buffermaxtimecombinedpeaks.append(String.valueOf(max_retention_time));
									} else {
										buffermasscombinedpeaks.append(mass[0].trim());
										bufferstringsamplecount.append("1");
									}
									startcombinedpeaks = false;
								} else {
									buffermasscombinedpeaks.append("," + mass[0].trim());
									bufferstringsamplecount.append(",1");
									if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
											|| (experiment.getequipmentid() == 5)) {
										buffertimecombinedpeaks.append("," + mass[2].trim());
										buffermintimecombinedpeaks.append("," + String.valueOf(min_retention_time));
										buffermaxtimecombinedpeaks.append("," + String.valueOf(max_retention_time));
									}
								}
							}
						} else {
							if (!buffermasscombinedpeaks.toString().trim().equalsIgnoreCase("")) {
								arraycombinedpeaks = buffermasscombinedpeaks.toString().split(",");
								arraycombinedcounts = bufferstringsamplecount.toString().split(",");
								arraytimecobinedpeaks = buffertimecombinedpeaks.toString().split(",");
								arraymintimecobinedpeaks = buffermintimecombinedpeaks.toString().split(",");
								arraymaxtimecobinedpeaks = buffermaxtimecombinedpeaks.toString().split(",");
								doublemass = Double.valueOf(mass[0]).doubleValue();
								addmasstolist = true;
								valueprevious = -1.0;
								j_bewaar = 0;
								for (int j = 0; j < arraycombinedpeaks.length; j++) {
									if (arraycombinedpeaks[j].trim().equalsIgnoreCase("")) {
										doublemasscombinedpeaks = -1.0;
									} else {
										doublemasscombinedpeaks = Double.valueOf(arraycombinedpeaks[j]).doubleValue();
									}
									absolutedifference = Math.abs(doublemass - doublemasscombinedpeaks);
									if (experiment.getclusteringtechnique() == 1) {
										// ppm
										deltamzcombinelocal = doublemasscombinedpeaks
												* Double.parseDouble(experiment.getdelta_mz_combine()) / 1000000;
									}
									withintimewindow = true;
									if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
											|| (experiment.getequipmentid() == 5)) {
										if (mass.length > 4) {
											if (arraycombinedpeaks[j].trim().equalsIgnoreCase("")) {
												doublemintimecombinedpeaks = -1.0;
											} else {
												doublemintimecombinedpeaks = Double.valueOf(arraymintimecobinedpeaks[j])
														.doubleValue();
											}
											if (arraycombinedpeaks[j].trim().equalsIgnoreCase("")) {
												doublemaxtimecombinedpeaks = -1.0;
											} else {
												doublemaxtimecombinedpeaks = Double.valueOf(arraymaxtimecobinedpeaks[j])
														.doubleValue();
											}
										}
										withintimewindow = false;
										deltatimelocalcombine = experiment.gettime_window_combining_peptide_masses();
										if (timeclusteringtechnique == 1) {
											// %
											deltatimelocalcombine = experiment.gettime_window_combining_peptide_masses()
													* Double.valueOf(arraytimecobinedpeaks[j]).doubleValue() / 100;
										}
										if (mass[2].trim().equalsIgnoreCase("")) {
											doubletime = -1.0;
										} else {
											doubletime = Double.valueOf(mass[2]).doubleValue();
										}
										if (arraytimecobinedpeaks[j].trim().equalsIgnoreCase("")) {
											doubletimecombined = -1.0;
										} else {
											doubletimecombined = Double.valueOf(arraytimecobinedpeaks[j]).doubleValue();
										}
										if (Math.abs(doubletime - doubletimecombined) < deltatimelocalcombine) {
											withintimewindow = true;
										}
										if (min_retention_time > -1) {
											if (Math.abs(
													min_retention_time - doubletimecombined) < deltatimelocalcombine) {
												withintimewindow = true;
											}
										}
										if (max_retention_time > -1) {
											if (Math.abs(
													max_retention_time - doubletimecombined) < deltatimelocalcombine) {
												withintimewindow = true;
											}
										}
										if (doublemintimecombinedpeaks > -1) {
											if (Math.abs(
													doublemintimecombinedpeaks - doubletime) < deltatimelocalcombine) {
												withintimewindow = true;
											}
										}
										if (doublemaxtimecombinedpeaks > -1) {
											if (Math.abs(
													doublemaxtimecombinedpeaks - doubletime) < deltatimelocalcombine) {
												withintimewindow = true;
											}
										}
										if ((min_retention_time > -1) && (max_retention_time > -1)) {
											if ((min_retention_time < doubletimecombined)
													&& (max_retention_time > doubletimecombined)) {
												withintimewindow = true;
											}
										}
										if ((min_retention_time > -1) && (max_retention_time > -1)
												&& (doublemintimecombinedpeaks > -1)) {
											if ((min_retention_time < doublemintimecombinedpeaks)
													&& (max_retention_time > doublemintimecombinedpeaks)) {
												withintimewindow = true;
											}
										}
										if ((min_retention_time > -1) && (max_retention_time > -1)
												&& (doublemaxtimecombinedpeaks > -1)) {
											if ((min_retention_time < doublemaxtimecombinedpeaks)
													&& (max_retention_time > doublemaxtimecombinedpeaks)) {
												withintimewindow = true;
											}
										}
										if ((min_retention_time > -1) && (max_retention_time > -1)
												&& (doublemaxtimecombinedpeaks > -1)
												&& (doublemintimecombinedpeaks > -1)) {
											if ((min_retention_time > doublemintimecombinedpeaks)
													&& (max_retention_time < doublemaxtimecombinedpeaks)) {
												withintimewindow = true;
											}
											if ((doublemintimecombinedpeaks > min_retention_time)
													&& (doublemaxtimecombinedpeaks < max_retention_time)) {
												withintimewindow = true;
											}
										}
									}
									if ((absolutedifference < deltamzcombinelocal) && (withintimewindow)) {
										addmasstolist = false;
										if (valueprevious == -1.0) {
											valueprevious = absolutedifference;
											j_bewaar = j;
										} else {
											if (absolutedifference < valueprevious) {
												valueprevious = absolutedifference;
												j_bewaar = j;
											}
										}
									}
								}
								if (experiment.getperform_ms2_sequencing()
										&& experiment.getonly_ms2_sequencedmasses()) {
									if (experiment.getequipmentid() == 4) {
										if (mass.length < 6) {
											addmasstolist = false;
										}
									}
								}
								if (addmasstolist) {
									if (startcombinedpeaks == true) {
										buffermasscombinedpeaks.append(mass[0].trim());
										bufferstringsamplecount.append("1");
										if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
												|| (experiment.getequipmentid() == 5)) {
											buffertimecombinedpeaks.append(mass[2].trim());
											buffermintimecombinedpeaks.append(String.valueOf(min_retention_time));
											buffermaxtimecombinedpeaks.append(String.valueOf(max_retention_time));
										}
										startcombinedpeaks = false;
									} else {
										buffermasscombinedpeaks.append("," + mass[0].trim());
										bufferstringsamplecount.append(",1");
										if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
												|| (experiment.getequipmentid() == 5)) {
											buffertimecombinedpeaks.append("," + mass[2].trim());
											buffermintimecombinedpeaks.append("," + String.valueOf(min_retention_time));
											buffermaxtimecombinedpeaks.append("," + String.valueOf(max_retention_time));
										}
									}
								} else {
									number = Integer.parseInt(arraycombinedcounts[j_bewaar]);
									if (number > 0) {
										tempmass = Double.parseDouble(arraycombinedpeaks[j_bewaar]);
										tempmass = ((tempmass * number) + doublemass) / (number + 1);
										tempmass = tempmass * 100000;
										tempmass = Math.round(tempmass);
										tempmass = tempmass / 100000;
										temptime = -1;
										if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
												|| (experiment.getequipmentid() == 5)) {
											try {
												temptime = Double.parseDouble(arraytimecobinedpeaks[j_bewaar]);
											} catch (Exception ex) {
											}
											if ((temptime > 0) && (doubletime > 0) && (number > 0)) {
												temptime = ((temptime * number) + doubletime) / (number + 1);
												temptime = temptime * 100;
												temptime = Math.round(temptime);
												temptime = temptime / 100;
											}
										}
										number++;
										index1 = 0;
										index2 = 0;
										for (int p = 0; p < j_bewaar; p++) {
											index1 = bufferstringsamplecount.indexOf(",", index1 + 1);
										}
										index2 = bufferstringsamplecount.indexOf(",", index1 + 1);
										if (index1 > 0) {
											index1 = index1 + 1;
										}
										if (index2 < 0) {
											index2 = bufferstringsamplecount.length();
										}
										bufferstringsamplecount.replace(index1, index2, String.valueOf(number).trim());
										index1 = 0;
										index2 = 0;
										for (int p = 0; p < j_bewaar; p++) {
											index1 = buffermasscombinedpeaks.indexOf(",", index1 + 1);
										}
										index2 = buffermasscombinedpeaks.indexOf(",", index1 + 1);
										if (index1 > 0) {
											index1 = index1 + 1;
										}
										if (index2 < 0) {
											index2 = buffermasscombinedpeaks.length();
										}
										buffermasscombinedpeaks.replace(index1, index2,
												String.valueOf(tempmass).trim());
										if ((experiment.getequipmentid() == 3) || (experiment.getequipmentid() == 4)
												|| (experiment.getequipmentid() == 5)) {
											if (temptime > 0) {
												index1 = 0;
												index2 = 0;
												for (int p = 0; p < j_bewaar; p++) {
													index1 = buffertimecombinedpeaks.indexOf(",", index1 + 1);
												}
												index2 = buffertimecombinedpeaks.indexOf(",", index1 + 1);
												if (index1 > 0) {
													index1 = index1 + 1;
												}
												if (index2 < 0) {
													index2 = buffertimecombinedpeaks.length();
												}
												buffertimecombinedpeaks.replace(index1, index2,
														String.valueOf(temptime).trim());
											}
										}
									}
								}
							}
						}
						line = "";
						linebuffer = new StringBuffer(line);
					}
				}
			}
		} catch (IOException e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
