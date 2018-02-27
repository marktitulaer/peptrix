package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.Progress;
import com.enterprisedt.net.ftp.FTPClient;
import com.enterprisedt.net.ftp.FTPConnectMode;
import com.enterprisedt.net.ftp.FTPTransferType;

public class StoredetailsWilcoxon {

	int testint;

	String linetime;

	StringBuffer linetimebuffer;

	StringBuffer linebuffer2;

	String line;

	StringBuffer linebuffer;

	StringBuffer bufferheaderdetailsgroup1;

	StringBuffer bufferheaderdetailsgroup2;

	StringBuffer buffermatrixdata;

	StringBuffer buffermatrixdatagroup1;

	StringBuffer buffermatrixdatagroup2;

	Controller cc;

	boolean ms_data_present;

	boolean first;

	String[] teststring1;

	String[] teststring2;

	String line2;

	double meangroup1;

	double meangroup2;

	boolean createbufferheaderdetailsgroup1;

	boolean createbufferheaderdetailsgroup2;

	double totalsgroup1;

	double countsgroup1;

	double countsgroup2;

	double totalsgroup2;

	String exportname;

	int index;

	public StoredetailsWilcoxon(Controller cc) {
		this.cc = cc;
	}

	public void create_exportstring_details(String filenamematrix, Progress progress) {
		ms_data_present = false;
		if (cc.ms2_proteins != null) {
			if (cc.ms2_proteins.length > 0) {
				ms_data_present = true;
			}
		}
		exportname = cc.details_prefix + cc.groupid1wilcoxon + "_" + cc.groupid2wilcoxon + "_" + filenamematrix;
		try {
			cc.exportfiletodisk.open_file(cc.userhome, "tmp_" + exportname);
			if (cc.dataexperiment[3].equalsIgnoreCase("3") || cc.dataexperiment[3].equalsIgnoreCase("4")
					|| cc.dataexperiment[3].equalsIgnoreCase("5")) {
				cc.exportstringwilcoxon = "Mass,Time,Wilcoxon P_value,+/-,difference mean (" + cc.groupcode1wilcoxon
						+ " - " + cc.groupcode2wilcoxon + "),mean " + cc.groupcode1wilcoxon + ",mean "
						+ cc.groupcode2wilcoxon + ",cummulative " + cc.groupcode1wilcoxon + ",No samples "
						+ cc.groupcode1wilcoxon + ",cummulative " + cc.groupcode2wilcoxon + ",No samples "
						+ cc.groupcode2wilcoxon;
			} else {
				cc.exportstringwilcoxon = "Mass,Wilcoxon P_value,+/-,difference mean (" + cc.groupcode1wilcoxon + " - "
						+ cc.groupcode2wilcoxon + "),mean " + cc.groupcode1wilcoxon + ",mean " + cc.groupcode2wilcoxon
						+ ",cummulative " + cc.groupcode1wilcoxon + ",No samples " + cc.groupcode1wilcoxon
						+ ",cummulative " + cc.groupcode2wilcoxon + ",No samples " + cc.groupcode2wilcoxon;
			}
			cc.exportbuffer = new StringBuffer(cc.exportstringwilcoxon);
			bufferheaderdetailsgroup1 = new StringBuffer(",group1");
			bufferheaderdetailsgroup2 = new StringBuffer(",group2");
			buffermatrixdata = new StringBuffer();
			buffermatrixdatagroup1 = new StringBuffer("," + cc.groupcode1wilcoxon);
			buffermatrixdatagroup2 = new StringBuffer("," + cc.groupcode2wilcoxon);
			line = "";
			linebuffer = new StringBuffer(line);
			for (int k = 0; k < cc.data.length; k++) {
				if ((cc.data[k] != 10) && (cc.data[k] != 13)) {
					linebuffer.append((char) cc.data[k]);
				}
				if (cc.data[k] == 10) {
					line = linebuffer.toString();
					break;
				}
			}
			if (cc.dataexperiment[3].equalsIgnoreCase("3") || cc.dataexperiment[3].equalsIgnoreCase("4")
					|| cc.dataexperiment[3].equalsIgnoreCase("5")) {
				linetime = "";
				linetimebuffer = new StringBuffer(linetime);
				first = true;
				for (int k = 0; k < cc.data.length; k++) {
					if ((cc.data[k] != 10) && (cc.data[k] != 13)) {
						linetimebuffer.append((char) cc.data[k]);
					}
					if (cc.data[k] == 10) {
						linetime = linetimebuffer.toString();
						if (first) {
							linetime = "";
							linetimebuffer = new StringBuffer(linetime);
							first = false;
						} else {
							break;
						}
					}
				}
				first = true;
			}
			createbufferheaderdetailsgroup1 = true;
			createbufferheaderdetailsgroup2 = true;
			progress.setmaximum(cc.cleanedPalues[0].length - 1);
			for (int i = 0; i < cc.cleanedPalues[0].length; i++) {
				totalsgroup1 = 0;
				totalsgroup2 = 0;
				countsgroup1 = 0;
				countsgroup2 = 0;
				index = line.substring(0, line.indexOf(String.valueOf(cc.cleanedPalues[0][i]).trim()))
						.split(",").length;
				if (cc.dataexperiment[3].equalsIgnoreCase("3") || cc.dataexperiment[3].equalsIgnoreCase("4")
						|| cc.dataexperiment[3].equalsIgnoreCase("5")) {
					teststring1 = line.split(",");
					teststring2 = linetime.split(",");
					if (teststring1.length == teststring2.length) {
						for (int k = 0; k < teststring1.length; k++) {
							if ((teststring1[k].equalsIgnoreCase(String.valueOf(cc.cleanedPalues[0][i]).trim()))
									&& (teststring2[k]
											.equalsIgnoreCase(String.valueOf(cc.cleanedPalues[2][i]).trim()))) {
								index = k;
							}
						}
					}
				}
				line2 = "";
				linebuffer2 = new StringBuffer(line);
				first = true;
				for (int k = 0; k < cc.data.length; k++) {
					if ((cc.data[k] != 10) && (cc.data[k] != 13)) {
						linebuffer2.append((char) cc.data[k]);
					}
					if (cc.data[k] == 10) {
						line2 = linebuffer2.toString();
						cc.temp2 = line2.split(",");
						if (first) {
							first = false;
						} else {
							if (createbufferheaderdetailsgroup1) {
								if (cc.groupid1wilcoxon.trim().equalsIgnoreCase(cc.temp2[2].trim())) {
									bufferheaderdetailsgroup1.append("," + cc.temp2[1].trim());
								}
							}
							if (createbufferheaderdetailsgroup2) {
								if (cc.groupid2wilcoxon.trim().equalsIgnoreCase(cc.temp2[2].trim())) {
									bufferheaderdetailsgroup2.append("," + cc.temp2[1].trim());
								}
							}
							if (cc.groupid1wilcoxon.trim().equalsIgnoreCase(cc.temp2[2].trim())) {
								buffermatrixdatagroup1.append("," + cc.temp2[index].trim());
								totalsgroup1 = totalsgroup1 + Double.valueOf(cc.temp2[index].trim()).doubleValue();
								countsgroup1 = countsgroup1 + 1;
							}
							if (cc.groupid2wilcoxon.trim().equalsIgnoreCase(cc.temp2[2].trim())) {
								buffermatrixdatagroup2.append("," + cc.temp2[index].trim());
								totalsgroup2 = totalsgroup2 + Double.valueOf(cc.temp2[index].trim()).doubleValue();
								countsgroup2 = countsgroup2 + 1;
							}

						}
						line2 = "";
						linebuffer2 = new StringBuffer(line2);
					}
				}
				createbufferheaderdetailsgroup1 = false;
				createbufferheaderdetailsgroup2 = false;
				if (countsgroup1 > 0) {
					meangroup1 = totalsgroup1 / countsgroup1;
				} else {
					meangroup1 = 0;
				}
				if (countsgroup2 > 0) {
					meangroup2 = totalsgroup2 / countsgroup2;
				} else {
					meangroup2 = 0;
				}
				buffermatrixdata.append(String.valueOf(cc.cleanedPalues[0][i]).trim());
				if (cc.dataexperiment[3].equalsIgnoreCase("3") || cc.dataexperiment[3].equalsIgnoreCase("4")
						|| cc.dataexperiment[3].equalsIgnoreCase("5")) {
					buffermatrixdata.append("," + String.valueOf(cc.cleanedPalues[2][i]).trim());
				}
				buffermatrixdata.append("," + String.valueOf(cc.cleanedPalues[1][i]).trim());
				if (meangroup1 - meangroup2 > 0) {
					buffermatrixdata.append(",+");
				} else if (meangroup1 - meangroup2 < 0) {
					buffermatrixdata.append(",-");
				} else if ((meangroup1 == meangroup2) && (meangroup1 > 0)) {
					buffermatrixdata.append(",=");
				} else {
					buffermatrixdata.append(",!");
				}
				buffermatrixdata.append("," + String.valueOf(meangroup1 - meangroup2) + "," + String.valueOf(meangroup1)
						+ "," + String.valueOf(meangroup2) + "," + String.valueOf(totalsgroup1) + ","
						+ String.valueOf(countsgroup1) + "," + String.valueOf(totalsgroup2) + ","
						+ String.valueOf(countsgroup2));
				if (ms_data_present) {
					testint = (int) cc.cleanedPalues[3][i];
					if (!cc.ms2_proteins[testint].trim().equalsIgnoreCase("")) {
						buffermatrixdatagroup2.append("," + cc.ms2_proteins[testint]);
					} else {
						buffermatrixdatagroup2.append(", ");
					}
				}
				buffermatrixdatagroup2.append(cc.linefeed);
				buffermatrixdata.append(buffermatrixdatagroup1.toString());
				buffermatrixdatagroup1 = new StringBuffer("," + cc.groupcode1wilcoxon);
				buffermatrixdata.append(buffermatrixdatagroup2.toString());
				buffermatrixdatagroup2 = new StringBuffer("," + cc.groupcode2wilcoxon);
				cc.exportfiletodisk.append_data_to_file(buffermatrixdata.toString().getBytes());
				buffermatrixdata = new StringBuffer("");
				progress.setnumberandtext(i, "");
			}
			cc.exportfiletodisk.close_file();
			cc.exportbuffer.append(bufferheaderdetailsgroup1.toString());
			cc.exportbuffer.append(bufferheaderdetailsgroup2.toString());
			if (ms_data_present) {
				cc.exportbuffer.append(",sequence#missed_cleavage#protein");
			}
			cc.exportbuffer.append(cc.linefeed);
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		store_detailsserver();
		save_detailslocal();
	}

	private void store_detailsserver() {
		cc.actualtime.resettime();
		cc.filetransportedwilcoxon = false;
		try {
			cc.ftp.setRemoteHost(cc.ftpremotehost);
			cc.ftp.connect();
			cc.ftp.login(cc.ftpuser, cc.ftppassword);
			cc.ftp.setConnectMode(FTPConnectMode.PASV);
			cc.ftp.setType(FTPTransferType.BINARY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			cc.fis = new FileInputStream(cc.userhome + cc.fileSeparator + "tmp_" + exportname);
			cc.filegrootte_kbytes_wilcoxon = cc.fis.available() / 1024.000;
			cc.ftp.chdir(File.separator + cc.dataexperiment[4] + File.separator + cc.strexperimentid2);
			cc.data_wilcoxon = cc.exportbuffer.toString().getBytes();
			cc.filegrootte_kbytes_wilcoxon = cc.filegrootte_kbytes_wilcoxon + (cc.data_wilcoxon.length / 1024.000);
			cc.tempBD = new BigDecimal(cc.filegrootte_kbytes_wilcoxon);
			cc.ftp.put(cc.data_wilcoxon, exportname, false);
			cc.ftp.put(cc.fis, exportname, true);
			cc.fis.close();
			cc.filetransportedwilcoxon = true;
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		try {
			cc.ftp.quit();
		} catch (Exception e) {
			;
		}
		if (cc.filetransportedwilcoxon) {
			cc.insertresultrecordWilcoxon.insertresultrecordwilcoxon(cc.strexperimentid2, cc.strtype, cc.exportname);
		}
	}

	public void save_detailslocal() {
		if (cc.ftp == null) {
			try {
				cc.ftp = new FTPClient();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Object[][] odatagroupnumbers = cc.resultService.getodatagroupnumbers();
		exportname = cc.details_prefix + odatagroupnumbers[cc.combogroupnumber1.getSelectedIndex()][1].toString().trim()
				+ "_" + odatagroupnumbers[cc.combogroupnumber2.getSelectedIndex()][1].toString().trim() + "_"
				+ cc.combomatrixtodisplaywilcoxon.getSelectedItem().toString();
		try {
			cc.ftp.setRemoteHost(cc.ftpremotehost);
			cc.ftp.connect();
			cc.ftp.login(cc.ftpuser, cc.ftppassword);
			cc.ftp.setConnectMode(FTPConnectMode.PASV);
			cc.ftp.setType(FTPTransferType.BINARY);
			cc.ftp.chdir(File.separator + cc.dataexperiment[4] + File.separator + cc.strexperimentid2);
			cc.data_wilcoxon = cc.ftp.get(exportname);
			boolean exporttodisksucceeded = cc.exportfiletodisk.exportfile_and_directory(cc.userhome, exportname,
					cc.data_wilcoxon);
		} catch (Exception e) {
			if (cc.debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		try {
			cc.ftp.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
