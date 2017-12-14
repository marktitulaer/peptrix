package pharmaceuticals.nl.peptrix.transpose;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.Progress;
import pharmaceuticals.nl.peptrix.gui.SearchSelection;
import pharmaceuticals.nl.peptrix.gui.TableSorter;
import pharmaceuticals.nl.peptrix.service.ExperimentService;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ExperimentServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

import com.enterprisedt.net.ftp.*;

public class TransposeMatrix implements TextListener, ActionListener {

	ResultService resultService;

	FTPClient ftp;

	GridBagLayout gridbglayouttransposematrixresultpanel;

	JScrollPane browserpane;

	DefaultTableModel defaulttablemodel;

	TableSorter sorter;

	BigDecimal tempBD;

	FileDescriptor fd;

	FileOutputStream os;

	BoxLayout boxlayout;

	Object[][] odataexperimentname;

	Object[][] odatamatrixfiles;

	Object[][] odatatransposedmatrixfiles;

	SearchSelection searchselection;

	Controller cc;

	GridBagLayout gridbagexperiment;

	GridBagLayout gridbagpanelnorth;

	GridBagLayout gridbagtransposematrixinoutpanel;

	GridBagConstraints transposematrixinputpanelconstraints;

	GridBagConstraints constraintsexperiment;

	GridBagConstraints constraintspanelnorth;

	Color colorgrey = new Color(204, 204, 204);

	Color erasmuslichtblauw = new Color(134, 210, 237);

	Box.Filler vFill;

	Dimension fill;

	ScrollPane transposematrixcenter;

	JPanel transposematrixcenterpanel;

	JPanel transposematrixcenterpanelnorth;

	JPanel createtransposematrixnorth;

	JPanel creatematrixsouth;

	JPanel panelexperimentleft;

	JPanel panelexperimentright;

	JPanel searchtablerecordpanel;

	JPanel transposematrixinputpanel;

	JPanel centerpanelnorthtotal;

	JPanel dummypanel;

	JPanel transposematrixresultpanel;

	JComboBox combomatrixtodisplay;

	JLabel labelexperimentname;

	JLabel experimentidlabel;

	JLabel equipmentlabel;

	JLabel datelabel;

	JLabel labelselectmatrix;

	TextField experimentname;

	TextField experimentid;

	TextField equipmentname;

	TextField dateexperiment;

	BufferedReader in;

	JButton search;

	JButton starttransposematrix;

	FileInputStream is;

	JTable matrixtable;

	GridBagConstraints gridbagconstraints;

	FileReader fr;

	Progress progress;

	StringBuffer linebuffer;

	String[][] tabledata_raw;

	String[][] tabledata;

	String[][] transposedmatrix;

	String[] arraystr;

	String[] temp;

	String[] matrixfiles;

	String[] tableheader;

	String dialogtitle;

	String wherestring;

	String strquery;

	String strexperimentname;

	String strequipmentname;

	String experimentdate;

	String strequipmentid;

	String fileSeparator;

	String strexperimentid;

	String userhome;

	String strtype;

	String experimentyear;

	String filename;

	String exportline;

	String str;

	String transposed_file_name;

	String line;

	String transposed_prefix = "t_";

	double double_columncount = 0;

	double filegrootte_kbytes;

	double double_rowcount = 0;

	int updatesample;

	int selected_index;

	int int_blocksize;

	int max_column;

	int min_column;

	int columncount = 0;

	int rowcount = 0;

	int rowcount_transposematrix = 0;

	int numberofrows;

	int maxcolumns;

	int maxcolums2;

	int int_file_size;

	byte[] exportdata;

	boolean firstline;

	boolean first = true;

	boolean filetransported;

	boolean debugmode = true;

	boolean filepresent;

	ExperimentService experimentService;

	public TransposeMatrix(Controller cc) {
		this.cc = cc;
		resultService = new ResultServiceImpl(cc);
		experimentService = new ExperimentServiceImpl(cc);
		tabledata_raw = new String[40][20];
		defaulttablemodel = new DefaultTableModel();
		progress = new Progress();
	}

	public void selectexperiment(String strinputexperimentid) {
		// 1.
		// -------------------------------------------------------------------
		// upper panel experiment selection
		panelexperimentleft = new JPanel();
		fillexperimentleftpanel();
		panelexperimentright = new JPanel();
		createtransposematrixnorth = new JPanel();
		createtransposematrixnorth.setLayout(new BorderLayout());
		createtransposematrixnorth.add(panelexperimentleft, BorderLayout.WEST);
		createtransposematrixnorth.add(panelexperimentright, BorderLayout.CENTER);
		// 2A.
		// -------------------------------------------------------------------
		// ####################
		transposematrixcenterpanelnorth = new JPanel(); // I
		transposematrixinputpanel = new JPanel();
		gridbagpanelnorth = new GridBagLayout();
		constraintspanelnorth = new GridBagConstraints();
		constraintspanelnorth.anchor = GridBagConstraints.FIRST_LINE_START;
		constraintspanelnorth.insets = new Insets(2, 2, 2, 2);
		transposematrixcenterpanelnorth.setLayout(gridbagpanelnorth);
		constraintspanelnorth.gridx = 1;
		constraintspanelnorth.gridy = 1;
		constraintspanelnorth.gridwidth = 1;
		constraintspanelnorth.gridheight = 1;
		gridbagpanelnorth.setConstraints(transposematrixinputpanel, constraintspanelnorth);
		transposematrixcenterpanelnorth.add(transposematrixinputpanel);
		// ####################
		// to shift transposematrixcenterpanelnorth to left
		dummypanel = new JPanel();
		centerpanelnorthtotal = new JPanel();
		centerpanelnorthtotal.setLayout(new BorderLayout());
		centerpanelnorthtotal.add(transposematrixcenterpanelnorth, BorderLayout.WEST); // I
		centerpanelnorthtotal.add(dummypanel, BorderLayout.CENTER);
		// ####################
		// to shift transposematrixcenterpanelnorth to top
		transposematrixcenterpanel = new JPanel();
		transposematrixcenterpanel.setLayout(new BorderLayout());
		transposematrixcenterpanel.add(centerpanelnorthtotal, BorderLayout.NORTH);
		// 2B
		// -----------------------------------------------------------------------------
		transposematrixresultpanel = new JPanel();
		transposematrixcenterpanel.add(transposematrixresultpanel, BorderLayout.CENTER);

		// 2A + 2B
		// -----------------------------------------------------------------------------
		transposematrixcenter = new ScrollPane();
		transposematrixcenter.add(transposematrixcenterpanel);
		// 4
		// -----------------------------------------------------------------------------
		creatematrixsouth = new JPanel();
		creatematrixsouth.setBackground(erasmuslichtblauw);
		boxlayout = new BoxLayout(creatematrixsouth, BoxLayout.X_AXIS);
		creatematrixsouth.setLayout(boxlayout);
		fill = new Dimension(10, 30);
		vFill = new Box.Filler(fill, fill, fill);
		creatematrixsouth.add(vFill);
		// ------------------------------------------------------------------------------
		cc.pBody.setLayout(new BorderLayout());
		cc.pBody.removeAll();
		cc.pBody.add(createtransposematrixnorth, BorderLayout.PAGE_START); // 1
		cc.pBody.add(transposematrixcenter, BorderLayout.CENTER); // 2
		cc.pBody.add(creatematrixsouth, BorderLayout.PAGE_END); // 3
		// ------------------------------------------------------------------------------
		colorframesblue();
		if (strinputexperimentid != null) {
			experimentid.setText(strinputexperimentid);
		}
		cc.frame.setVisible(true);
	}

	private void colorframesblue() {
		transposematrixinputpanel.setBackground(erasmuslichtblauw);
		transposematrixcenterpanelnorth.setBackground(erasmuslichtblauw);
		transposematrixcenterpanel.setBackground(erasmuslichtblauw);
		transposematrixresultpanel.setBackground(erasmuslichtblauw);
		dummypanel.setBackground(erasmuslichtblauw);
	}

	private void colorframesgrey() {
		transposematrixinputpanel.setBackground(colorgrey);
		dummypanel.setBackground(colorgrey);
		transposematrixcenterpanelnorth.setBackground(colorgrey);
		transposematrixresultpanel.setBackground(colorgrey);
		transposematrixcenterpanel.setBackground(colorgrey);
	}

	private void fillpanelstransposematrix() {
		filltransposematrixinputpanel();
		colorframesgrey();
	}

	private void collectmatrixfiles() {
		if (!experimentid.getText().trim().equals("")) {
			odatamatrixfiles = resultService.collectmatrixfiles2(experimentid.getText());
			if (odatamatrixfiles.length > 0) {
				matrixfiles = new String[odatamatrixfiles.length];
				for (int i = 0; i < odatamatrixfiles.length; i++) {
					matrixfiles[i] = (String) odatamatrixfiles[i][0];
					experimentyear = odatamatrixfiles[i][2].toString().trim();
				}
			}
		}
	}

	private void filltransposematrixinputpanel() {
		transposematrixinputpanel.setBorder(BorderFactory.createTitledBorder("Transpose matrix"));
		gridbagtransposematrixinoutpanel = new GridBagLayout();
		transposematrixinputpanel.setLayout(gridbagtransposematrixinoutpanel);
		transposematrixinputpanelconstraints = new GridBagConstraints();
		transposematrixinputpanelconstraints.anchor = GridBagConstraints.FIRST_LINE_START;
		transposematrixinputpanelconstraints.insets = new Insets(2, 2, 2, 2);
		labelselectmatrix = new JLabel("Matrix");
		transposematrixinputpanelconstraints.gridx = 1;
		transposematrixinputpanelconstraints.gridy = 1;
		transposematrixinputpanelconstraints.gridwidth = 1;
		transposematrixinputpanelconstraints.gridheight = 1;
		gridbagtransposematrixinoutpanel.setConstraints(labelselectmatrix, transposematrixinputpanelconstraints);
		transposematrixinputpanel.add(labelselectmatrix);
		combomatrixtodisplay = new JComboBox(matrixfiles);
		combomatrixtodisplay.addActionListener(this);
		combomatrixtodisplay.setLightWeightPopupEnabled(false);
		transposematrixinputpanelconstraints.gridx = 2;
		transposematrixinputpanelconstraints.gridy = 1;
		transposematrixinputpanelconstraints.gridwidth = 2;
		transposematrixinputpanelconstraints.gridheight = 1;
		gridbagtransposematrixinoutpanel.setConstraints(combomatrixtodisplay, transposematrixinputpanelconstraints);
		transposematrixinputpanel.add(combomatrixtodisplay);
		starttransposematrix = new JButton("Start");
		starttransposematrix.addActionListener(this);
		starttransposematrix.setBackground(Color.GREEN);
		transposematrixinputpanelconstraints.gridx = 1;
		transposematrixinputpanelconstraints.gridy = 2;
		transposematrixinputpanelconstraints.gridwidth = 1;
		transposematrixinputpanelconstraints.gridheight = 1;
		gridbagtransposematrixinoutpanel.setConstraints(starttransposematrix, transposematrixinputpanelconstraints);
		transposematrixinputpanel.add(starttransposematrix);
	}

	private void fillexperimentleftpanel() {
		panelexperimentleft.setBorder(BorderFactory.createTitledBorder("Experiment"));
		gridbagexperiment = new GridBagLayout();
		panelexperimentleft.setLayout(gridbagexperiment);
		constraintsexperiment = new GridBagConstraints();
		constraintsexperiment.anchor = GridBagConstraints.FIRST_LINE_START;
		constraintsexperiment.insets = new Insets(2, 2, 2, 2);
		experimentidlabel = new JLabel(" ID");
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(experimentidlabel, constraintsexperiment);
		panelexperimentleft.add(experimentidlabel);
		experimentid = new TextField(20);
		experimentid.addTextListener(this);
		experimentid.setEnabled(false);
		experimentid.setBackground(colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(experimentid, constraintsexperiment);
		panelexperimentleft.add(experimentid);
		constraintsexperiment.gridx = 3;
		constraintsexperiment.gridy = 1;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		search = new JButton("Search");
		search.addActionListener(this);
		gridbagexperiment.setConstraints(search, constraintsexperiment);
		panelexperimentleft.add(search);
		labelexperimentname = new JLabel("Name");
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 2;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(labelexperimentname, constraintsexperiment);
		panelexperimentleft.add(labelexperimentname);
		experimentname = new TextField(30);
		experimentname.setEnabled(false);
		experimentname.setBackground(colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 2;
		constraintsexperiment.gridwidth = 2;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(experimentname, constraintsexperiment);
		panelexperimentleft.add(experimentname);
		equipmentlabel = new JLabel("Equipment");
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 3;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(equipmentlabel, constraintsexperiment);
		panelexperimentleft.add(equipmentlabel);
		equipmentname = new TextField(50);
		equipmentname.setEnabled(false);
		equipmentname.setBackground(colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 3;
		constraintsexperiment.gridwidth = 2;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(equipmentname, constraintsexperiment);
		panelexperimentleft.add(equipmentname);
		datelabel = new JLabel("Date");
		constraintsexperiment.gridx = 1;
		constraintsexperiment.gridy = 4;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(datelabel, constraintsexperiment);
		panelexperimentleft.add(datelabel);
		dateexperiment = new TextField(10);
		dateexperiment.setEnabled(false);
		dateexperiment.setBackground(colorgrey);
		constraintsexperiment.gridx = 2;
		constraintsexperiment.gridy = 4;
		constraintsexperiment.gridwidth = 1;
		constraintsexperiment.gridheight = 1;
		gridbagexperiment.setConstraints(dateexperiment, constraintsexperiment);
		panelexperimentleft.add(dateexperiment);
	}

	private void getexperimentname() {
		odataexperimentname = experimentService.getexperimentdata2(experimentid.getText());
		if (odataexperimentname.length > 0) {
			strexperimentname = (String) odataexperimentname[0][0];
			strequipmentname = (String) odataexperimentname[0][1];
			experimentdate = (String) odataexperimentname[0][2];
			strequipmentid = (String) odataexperimentname[0][3];
		} else {
			strexperimentname = "";
		}
	}

	public void textValueChanged(TextEvent evt) {
		if (evt.getSource() == experimentid) {
			collectmatrixfiles();
			getexperimentname();
			experimentname.setText(strexperimentname);
			equipmentname.setText(strequipmentname);
			dateexperiment.setText(experimentdate);
			transposematrixinputpanel.removeAll();
			if ((strequipmentid != null) && (odatamatrixfiles.length > 0)) {
				fillpanelstransposematrix();
				if (combomatrixtodisplay != null) {
					if (combomatrixtodisplay.getComponentCount() > 0) {
						combomatrixtodisplay.setSelectedIndex(0);
					}
				}

			}
			cc.frame.setVisible(true);
		}
	}

	private void display_transposed_matrix() {
		transposematrixresultpanel.removeAll();
		transposed_file_name = "";
		selected_index = -1;
		try {
			selected_index = combomatrixtodisplay.getSelectedIndex();
		} catch (Exception ex) {

		}
		if (selected_index > -1) {
			odatatransposedmatrixfiles = null;
			filename = combomatrixtodisplay.getSelectedItem().toString();
			if (!filename.trim().equalsIgnoreCase("")) {
				transposed_file_name = transposed_prefix + filename;
				if (!experimentid.getText().trim().equals("")) {
					odatatransposedmatrixfiles = resultService.gettransposedmatrixfiles(transposed_file_name,
							experimentid.getText());

					if (odatatransposedmatrixfiles != null) {
						if (odatatransposedmatrixfiles.length > 0) {
							filepresent = false;
							byte[] data = null;
							if (ftp != null) {
							} else {
								ftp = new FTPClient();
							}
							try {
								ftp.setRemoteHost(cc.ftpremotehost);
								ftp.connect();
								ftp.login(cc.ftpuser, cc.ftppassword);
								ftp.setConnectMode(FTPConnectMode.PASV);
								ftp.setType(FTPTransferType.BINARY);
								ftp.chdir(File.separator + experimentyear + File.separator
										+ experimentid.getText().trim());

							} catch (Exception e) {
								if (cc.debugmode) {
									e.printStackTrace();
								} else {
									JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
											JOptionPane.ERROR_MESSAGE);
								}
							}
							try {
								data = ftp.get(transposed_file_name.trim());
								filepresent = true;
							} catch (Exception e) {
								filepresent = false;
							}
							try {
								ftp.quit();
							} catch (Exception e) {
								;
							}
							for (int j = 0; j < tabledata_raw[0].length; j++) {
								for (int k = 0; k < tabledata_raw.length; k++) {
									tabledata_raw[k][j] = "";
								}
							}
							if (filepresent) {
								line = "";
								linebuffer = new StringBuffer(line);
								temp = null;
								numberofrows = 0;
								maxcolumns = 0;
								for (int i = 0; i < data.length; i++) {
									if ((data[i] != 10) && (data[i] != 13)) {
										linebuffer.append((char) data[i]);
									}
									if (data[i] == 10) {
										line = linebuffer.toString();
										temp = line.split(",");
										if (temp.length > maxcolumns) {
											maxcolumns = temp.length;
										}
										numberofrows++;
										if (numberofrows >= tabledata_raw.length) {
											i = data.length;
										}
										for (int j = 0; ((j < temp.length) && (j < tabledata_raw[0].length)); j++) {
											tabledata_raw[numberofrows - 1][j] = temp[j];
										}
										line = "";
										linebuffer = new StringBuffer(line);
									}
								}
								maxcolums2 = 0;
								maxcolums2 = tabledata_raw[0].length;
								if (maxcolumns < maxcolums2) {
									maxcolums2 = maxcolumns;
								}
								tabledata = new String[numberofrows - 1][maxcolums2];
								tableheader = new String[maxcolums2];
								for (int i = 0; i < tabledata_raw.length; i++) {
									for (int j = 0; ((j < tabledata_raw[i].length) && (j < maxcolumns)); j++) {
										if (i == 0) {
											tableheader[j] = tabledata_raw[i][j];
										} else {
											tabledata[i - 1][j] = tabledata_raw[i][j];
										}
									}
								}
								defaulttablemodel.setDataVector(tabledata, tableheader);
								if (sorter == null) {
									sorter = new TableSorter(defaulttablemodel);
								} else {
									sorter.setTableModel(defaulttablemodel);
								}
								if (matrixtable == null) {
									matrixtable = new JTable(sorter);
									matrixtable
											.setPreferredScrollableViewportSize(transposematrixresultpanel.getSize());
								} else {
									matrixtable.setModel(sorter);
								}
								sorter.setTableHeader(matrixtable.getTableHeader());
								browserpane = new JScrollPane(matrixtable);
								gridbglayouttransposematrixresultpanel = new GridBagLayout();
								transposematrixresultpanel.setLayout(gridbglayouttransposematrixresultpanel);
								gridbagconstraints = new GridBagConstraints();
								gridbagconstraints.anchor = GridBagConstraints.NORTHWEST;
								gridbagconstraints.insets = new Insets(2, 2, 2, 2);
								gridbagconstraints.gridx = 1;
								gridbagconstraints.gridy = 3;
								gridbagconstraints.gridwidth = 1;
								gridbagconstraints.gridheight = 1;
								gridbglayouttransposematrixresultpanel.setConstraints(browserpane, gridbagconstraints);
								transposematrixresultpanel.add(browserpane);
							}
						}
					}
				}
			}
		}
		cc.frame.setVisible(true);
	}

	private void download_file_local() {
		userhome = System.getProperty("user.home");
		fileSeparator = System.getProperty("file.separator");
		filepresent = false;
		if (ftp != null) {
		} else {
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
			ftp.chdir(File.separator + experimentyear + File.separator + strexperimentid);
			filepresent = false;
			String[] listdir = ftp.dir();
			for (int i = 0; i < listdir.length; i++) {
				if (listdir[i].trim().equalsIgnoreCase(filename.trim())) {
					filepresent = true;
				}
			}
			try {
				ftp.quit();
			} catch (Exception e) {
			}
			if (filepresent) {
				filepresent = false;
				try {
					ftp.connect();
					ftp.login(cc.ftpuser, cc.ftppassword);
					ftp.setConnectMode(FTPConnectMode.PASV);
					ftp.setType(FTPTransferType.BINARY);
					ftp.setTimeout(cc.ftp_longtime);
					ftp.chdir(File.separator + experimentyear + File.separator + strexperimentid);
					ftp.get(userhome + fileSeparator + filename, filename);
					filepresent = true;
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
				}
			}
		} catch (Exception e) {
			if (debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void transpose_matrix() {
		transposed_file_name = "";
		double_rowcount = 0;
		try {
			fr.close();
			in.close();
		} catch (Exception e) {
			;
		}
		in = null;
		fr = null;
		first = true;
		try {
			fr = new FileReader(userhome + fileSeparator + filename);
			in = new BufferedReader(fr);
			while ((str = in.readLine()) != null) {
				double_rowcount++;
				if (first) {
					arraystr = str.split(",");
					double_columncount = arraystr.length;
					first = false;
				}
			}
			in.close();
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((double_columncount % 2) == 0) {
		} else {
			double_columncount++;
		}
		int_blocksize = (int) (double_columncount / double_rowcount);
		if ((int_blocksize % 2) == 0) {
		} else {
			int_blocksize++;
		}
		columncount = (int) double_columncount;
		rowcount = (int) double_rowcount;
		if ((columncount > 0) && (rowcount > 0)) {
			// progress = new Progress();
			progress.init("Transpose matrix  ......", 70);
			rowcount_transposematrix = rowcount;
			transposed_file_name = transposed_prefix + filename;
			// Open or create the output file
			try {
				os = new FileOutputStream(userhome + fileSeparator + transposed_file_name);
				fd = os.getFD();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				transposed_file_name = "";
			}
			int j = 0;
			progress.setmaximum(columncount);
			while (j < columncount) {
				progress.setnumberandtext(j, "");
				rowcount = 0;
				min_column = j;
				max_column = min_column + int_blocksize;
				if (max_column > columncount) {
					max_column = columncount - 1;
				}
				transposedmatrix = new String[max_column - min_column][rowcount_transposematrix];
				try {
					fr = new FileReader(userhome + fileSeparator + filename);
					in = new BufferedReader(fr);
					while ((str = in.readLine()) != null) {
						arraystr = str.split(",");
						int k = 0;
						for (int i = min_column; i < max_column; i++) {
							transposedmatrix[k][rowcount] = arraystr[i];
							k++;
						}
						rowcount++;
					}
					fr.close();
					in.close();
				} catch (Exception e) {
				}
				for (int k = 0; k < transposedmatrix.length; k++) {
					firstline = true;
					exportline = "";
					for (int i = 0; i < transposedmatrix[k].length; i++) {
						if (firstline) {
							if (transposedmatrix[k][i] == null) {
								exportline = exportline + "";
							} else {
								exportline = exportline + transposedmatrix[k][i].trim();
							}
							firstline = false;
						} else {
							if (transposedmatrix[k][i] == null) {
								exportline = exportline + "," + " ";
							} else {
								exportline = exportline + "," + transposedmatrix[k][i].trim();
							}
						}
					}
					exportline = exportline + "\n";
					exportdata = exportline.getBytes();
					try {
						os.write(exportdata);
						os.flush();

					} catch (Exception e) {
					}
				}
				j = j + int_blocksize;
			}
			progress.close();
		}
		try {
			fd.sync();
			os.close();
		} catch (Exception e) {
			;
		}
	}

	private void store_file_server() {
		strtype = "transposed";
		cc.actualtime.resettime();
		filegrootte_kbytes = 0;
		filetransported = false;
		try {
			ftp.setRemoteHost(cc.ftpremotehost);
			ftp.connect();
			ftp.login(cc.ftpuser, cc.ftppassword);
			ftp.setConnectMode(FTPConnectMode.PASV);
			ftp.setType(FTPTransferType.BINARY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ftp.chdir(File.separator + experimentyear + File.separator + strexperimentid);
			is = new FileInputStream(userhome + fileSeparator + transposed_file_name);
			int_file_size = is.available();
			filegrootte_kbytes = int_file_size / 1024.000;
			ftp.put(is, transposed_file_name, false);
			is.close();
			filetransported = true;
		} catch (Exception e) {
			if (debugmode) {
				e.printStackTrace();
			} else {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		tempBD = new BigDecimal(filegrootte_kbytes);
		try {
			ftp.quit();
		} catch (Exception e) {
		}
		if (filetransported) {
			insertresultrecord();
		}
	}

	private void insertresultrecord() {
		updatesample = resultService.deletetransposedfile(strexperimentid, strtype, transposed_file_name);
		updatesample = resultService.inserttransposedfile(tempBD, strexperimentid, strtype, transposed_file_name);
	}

	public void actionPerformed(ActionEvent evt) {
		strexperimentid = null;
		try {
			strexperimentid = experimentid.getText().trim();
		} catch (Exception ex) {
		}
		if (evt.getSource() == starttransposematrix) {
			selected_index = -1;
			try {
				selected_index = combomatrixtodisplay.getSelectedIndex();
			} catch (Exception ex) {

			}
			if (selected_index > -1) {
				filename = combomatrixtodisplay.getSelectedItem().toString();
				if ((strexperimentid != null) && (!strexperimentid.trim().equalsIgnoreCase(""))
						&& (!filename.trim().equalsIgnoreCase(""))) {
					download_file_local();
				}
				if (filepresent) {
					transpose_matrix();
					if (!transposed_file_name.trim().equalsIgnoreCase("")) {
						store_file_server();
					}
				}
			}
		}
		if (matrixfiles != null) {
			if ((combomatrixtodisplay.getComponentCount() > 0) && (!strexperimentid.trim().equalsIgnoreCase(""))
					&& (strexperimentid != null)) {
				display_transposed_matrix();
			}
		}
		if (evt.getSource() == search) {
			cc.updatefield = experimentid;
			dialogtitle = "Search experiment";
			cc.searchtablerecorddialog = new JDialog(this.cc.frame, dialogtitle);
			searchtablerecordpanel = new JPanel();
			cc.searchtablerecorddialog.getContentPane().add(searchtablerecordpanel);
			searchselection = new SearchSelection(this.cc, cc.searchtablerecorddialog, searchtablerecordpanel);
			wherestring = "";
			searchselection.selectbrowser("experiment", wherestring);
			cc.searchtablerecorddialog.setSize(900, 700);
			cc.searchtablerecorddialog.setVisible(true);
		}
	}
}
