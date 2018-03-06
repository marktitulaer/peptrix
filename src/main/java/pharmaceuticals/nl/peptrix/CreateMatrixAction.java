package pharmaceuticals.nl.peptrix;

import java.awt.*;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import pharmaceuticals.nl.peptrix.gui.Creatematrix;

import javax.swing.*;

public class CreateMatrixAction implements TextListener {
    Controller cc;
    public TextField experimentid2;
    Creatematrix creatematrix;

    public CreateMatrixAction(Controller cc) {
        this.cc = cc;
    }

    public void create_matrix_screen() {
        creatematrix = new Creatematrix(cc);
    }

    public void textValueChanged(TextEvent evt) {
        cc.PerformAction(evt.getSource());
        if (evt.getSource() == experimentid2) {
            String strexperimentid = experimentid2.getText();
            generateCreateMtrixScreen(strexperimentid);
        } else {
            cc.textValueChangedZ(evt);
        }
    }

    public void generateCreateMtrixScreen(String strexperimentid) {
        creatematrix.creatematrix_center.centerpanelsouthcreatematrix.removeAll2();
        creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                .setVisible(false);
        creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                .setVisible(false);
        creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.startcreatematrixpanel
                .setVisible(false);
        creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.peakfindinputpanel
                .setVisible(false);
        creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                .setVisible(false);
        creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                .setVisible(false);
        creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.dummypanel.setVisible(false);
        cc.dataexperiment[3] = "";
        cc.peakfindmethod = 1;
        cc.centroidingmethod = -1;
        cc.inputerror = -1;
        cc.matrixfiles = cc.collectmatrixfiles.collectmatrixfiles(strexperimentid);
        cc.dataexperiment = cc.getexperimentdata.getexperimentdata(strexperimentid);
        cc.experimentname2.setText(cc.dataexperiment[0]);
        cc.equipmentname2.setText(cc.dataexperiment[1]);
        cc.dateexperiment2.setText(cc.dataexperiment[2]);
        boolean equipmentid_ok = false;
        try {
            int testint = Integer.parseInt(cc.dataexperiment[3]);
            if (testint > 0) {
                equipmentid_ok = true;
            }
        } catch (Exception ex) {
        }
        if (equipmentid_ok) {
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                    .setVisible(true);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                    .setVisible(true);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.startcreatematrixpanel
                    .setVisible(true);
            creatematrix.creatematrix_center.centerpanelsouthcreatematrix.setvisible();
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.peakfindinputpanel
                    .setVisible(true);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(true);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(true);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.dummypanel.setVisible(true);
            creatematrix.fill_panels_with_fields();
            setlabelsanddefaults();
            cc.setbackground(cc.colorgrey);
            cc.frame.setVisible(true);
            if (cc.combomatrixtodisplay.getItemCount() > 0) {
                cc.combomatrixtodisplay.setSelectedIndex(0);
            }

        }



    }

    public void setlabelsanddefaults() {
        if (cc.inputgradientchange[0] > -1) {
            cc.input_changeingradient1.setText(String.valueOf(cc.inputgradientchange[0]));
        }
        if (cc.inputgradientchange[1] > -1) {
            cc.input_changeingradient2.setText(String.valueOf(cc.inputgradientchange[1]));
        }
        if (cc.inputgradientchange[2] > -1) {
            cc.input_changeingradient3.setText(String.valueOf(cc.inputgradientchange[2]));
        }
        cc.string_apodization_method = "gaussian";
        cc.btn_Gaussian_Lorentzian.setSelected(true);
        cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                .setVisible(true);
        cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                .setVisible(true);
        cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                .setVisible(true);
        if (cc.centroidingmethod == 0) {
            cc.none.setSelected(true);
        } else if (cc.centroidingmethod == 1) {
            cc.weightedmean.setSelected(true);
        }
        cc.labeltimewindow_lc_ms.setText("Time Window LC MS");
        cc.minimumcountmasses.setText("Mass counts in matrix    > ");
        cc.labeltimealignment.setText("Time Alignment");
        cc.labelpointstimealignment.setText("Minimum points timealignment");
        if (!cc.createMatrixAction.experimentid2.getText().trim().equalsIgnoreCase("")) {
            cc.count_allocated_records = cc.resultservice.countallocatedrecords(cc.createMatrixAction.experimentid2);
            if (cc.count_allocated_records.length > 0) {
                if (Integer.parseInt(cc.count_allocated_records[0][0].toString().trim()) >= 100) {
                    cc.inputminimumcountmasses.setText(String.valueOf((int) Math
                            .round(0.05 * Integer.parseInt(cc.count_allocated_records[0][0].toString().trim()))));
                } else if (Integer.parseInt(cc.count_allocated_records[0][0].toString().trim()) >= 40) {
                    cc.inputminimumcountmasses.setText("4");
                } else if (Integer.parseInt(cc.count_allocated_records[0][0].toString().trim()) >= 30) {
                    cc.inputminimumcountmasses.setText("3");
                } else if (Integer.parseInt(cc.count_allocated_records[0][0].toString().trim()) >= 20) {
                    cc.inputminimumcountmasses.setText("2");
                } else if (Integer.parseInt(cc.count_allocated_records[0][0].toString().trim()) >= 10) {
                    cc.inputminimumcountmasses.setText("1");
                } else {
                    cc.inputminimumcountmasses.setText("0");
                }
            }
            cc.odatafiletypes2 = cc.resultservice.resulttype(cc.createMatrixAction.experimentid2);
            if (cc.odatafiletypes2.length > 0) {
                for (int i = 0; i < cc.odatafiletypes2.length; i++) {
                    for (int j = 0; j < cc.filetypes.length; j++) {
                        if (cc.filetypes[j].trim().indexOf(cc.odatafiletypes2[i][0].toString().trim()) > 0) {
                            cc.filtercombobox2.addItem(cc.filetypes[j]);
                        }
                    }
                }
            } else {
                for (int j = 0; j < cc.filetypes.length; j++) {
                    cc.filtercombobox2.addItem(cc.filetypes[j]);
                }
            }
        }
        if (cc.dataexperiment[3].trim().equalsIgnoreCase("1")) {
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(false);
            cc.input_time_widow_lc_ms.setEditable(false);
            cc.input_time_widow_lc_ms.setBackground(cc.colorgrey);
            cc.performtimealgnment = false;
            cc.checkboxtimealignment.setState(cc.performtimealgnment);
            cc.checkboxtimealignment.setEnabled(false);
            cc.inputpointstimealignment.setEditable(false);
            cc.input_changeingradient1.setEditable(false);
            cc.input_changeingradient2.setEditable(false);
            cc.input_changeingradient3.setEditable(false);
            cc.inputpointstimealignment.setBackground(cc.colorgrey);
            cc.input_changeingradient1.setBackground(cc.colorgrey);
            cc.input_changeingradient2.setBackground(cc.colorgrey);
            cc.input_changeingradient3.setBackground(cc.colorgrey);
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(false);
            if (cc.centroidingmethod < 0) {
                cc.none.setSelected(true);
                cc.centroidingmethod = 0;
            }
            if (cc.clusteringtechnique < 1) {
                cc.clusteringtechnique = 2;
            }
            if (cc.clusteringtechnique == 1) {
                cc.ppm.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (ppm)");
                cc.labellimitcentroiding.setText("limit shift centroiding (ppm)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                cc.inputdeltamzsearch.setText("250");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                cc.inputdeltamzcombine.setText("250");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                cc.inputdeltamzsearchcalibrant.setText("250");
                cc.inputwindowcentroiding.setText("200");
                cc.inputlimitcentroiding.setText("50");
            } else if (cc.clusteringtechnique == 2) {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.50");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.50");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.50");
                cc.inputwindowcentroiding.setText("0.4");
                cc.inputlimitcentroiding.setText("0.1");
            } else {
                cc.ppm.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (ppm)");
                cc.labellimitcentroiding.setText("limit shift centroiding (ppm)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.50");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.50");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.50");
                cc.inputwindowcentroiding.setText("0.5");
                cc.inputlimitcentroiding.setText("2");
            }
            cc.labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            if (cc.peakfindmethod == 1) {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
                cc.peaksnoisyspectra.setText("1000");
            } else if (cc.peakfindmethod == 2) {
                cc.labelquantilepeakfinding.setText("Quantile threshold peak finding");
                cc.inputquantilepeakfinding.setText("0.98");
                cc.creatematrixmethodcombobox.setSelectedIndex(1);
                cc.peaksnoisyspectra.setText("1000");
            } else {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
                cc.peaksnoisyspectra.setText("1000");
            }
        } else if (cc.dataexperiment[3].trim().equalsIgnoreCase("2")) {
            cc.Raw_to_mzXML.setVisible(false);
            cc.msconvert_programm_64.setVisible(false);
            cc.msconvert_programm_32.setVisible(false);
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(false);
            cc.textfieldmaxchargestate.setText("1");
            cc.textfieldmaxchargestate.setEditable(false);
            cc.textfieldmaxchargestate.setBackground(cc.colorgrey);
            cc.input_time_widow_lc_ms.setEditable(false);
            cc.input_time_widow_lc_ms.setBackground(cc.colorgrey);
            cc.performtimealgnment = false;
            cc.checkboxtimealignment.setState(cc.performtimealgnment);
            cc.checkboxtimealignment.setEnabled(false);
            cc.inputpointstimealignment.setEditable(false);
            cc.inputpointstimealignment.setBackground(cc.colorgrey);
            cc.input_changeingradient1.setEditable(false);
            cc.input_changeingradient1.setBackground(cc.colorgrey);
            cc.input_changeingradient2.setEditable(false);
            cc.input_changeingradient2.setBackground(cc.colorgrey);
            cc.input_changeingradient3.setEditable(false);
            cc.input_changeingradient3.setBackground(cc.colorgrey);
            if (cc.percent_stdevisotopingdistance < 0) {
                cc.percent_stdevisotopingdistance = 0.1;
            }
            cc.inputvarianceisotopicdistance.setText(String.valueOf(cc.percent_stdevisotopingdistance));
            cc.percent_deviation_isotopic_distance = 1;
            cc.deviation_isotopicdistance.setText(String.valueOf(cc.percent_deviation_isotopic_distance));
            if (cc.inputerror < 0) {
                cc.inputerror = 0.15;
            }
            cc.texfieldinputerror.setText(String.valueOf(cc.inputerror));
            if (cc.c13_c12 < 0) {
                cc.c13_c12 = 1.0034;
            }
            cc.isotopicdistance.setText(String.valueOf(cc.c13_c12));
            cc.monoistopefinding = true;
            cc.checkdeisotoping.setState(cc.monoistopefinding);
            if (cc.zerofillingfactor < 0) {
                cc.zerofillingfactor = 4;
            }
            cc.inputzerofillingfaktor.setText(String.valueOf(cc.zerofillingfactor));
            if (cc.GaussfactorB < 0) {
                cc.GaussfactorB = 3.18;
            }
            cc.inputGaussianfaktor.setText(String.valueOf(cc.GaussfactorB));
            if (cc.LorentzfactorA < 0) {
                cc.LorentzfactorA = -3.18;
            }
            cc.inputLorentzfactorA.setText(String.valueOf(cc.LorentzfactorA));
            if (cc.clusteringtechnique < 1) {
                cc.clusteringtechnique = 1;
            }
            if (cc.centroidingmethod < 0) {
                cc.none.setSelected(true);
                cc.centroidingmethod = 0;
            }
            if (cc.clusteringtechnique == 1) {
                cc.ppm.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (ppm)");
                cc.labellimitcentroiding.setText("limit shift centroiding (ppm)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                cc.inputdeltamzsearch.setText("20");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                cc.inputdeltamzcombine.setText("3");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                cc.inputdeltamzsearchcalibrant.setText("30");
                cc.inputwindowcentroiding.setText("20");
                cc.inputlimitcentroiding.setText("0.5");
            } else if (cc.clusteringtechnique == 2) {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.040");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.006");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.060");
                cc.inputwindowcentroiding.setText("0.020");
                cc.inputlimitcentroiding.setText("0.002");
            } else {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.010");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.010");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.075");
                cc.inputwindowcentroiding.setText("0.010");
                cc.inputlimitcentroiding.setText("2");
            }
            cc.labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            cc.peaksnoisyspectra.setText("50000");
            if (cc.peakfindmethod == 1) {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (cc.peakfindmethod == 2) {
                cc.labelquantilepeakfinding.setText("Quantile threshold peak finding");
                cc.inputquantilepeakfinding.setText("0.92");
                cc.creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            }
        } else if (cc.dataexperiment[3].trim().equalsIgnoreCase("3")) {
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(false);
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                    .setVisible(false);
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(false);
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                    .setVisible(false);
            if (cc.clusteringtechnique < 1) {
                cc.clusteringtechnique = 2;
            }
            if (cc.centroidingmethod < 0) {
                cc.none.setSelected(true);
                cc.centroidingmethod = 0;
            }
            cc.input_time_widow_lc_ms.setText("120");
            cc.performtimealgnment = true;
            cc.checkboxtimealignment.setState(cc.performtimealgnment);
            cc.inputpointstimealignment.setText("100");
            if (cc.clusteringtechnique == 1) {
                cc.ppm.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (ppm)");
                cc.labellimitcentroiding.setText("limit shift centroiding (ppm)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                cc.inputdeltamzsearch.setText("3");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                cc.inputdeltamzcombine.setText("3");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                cc.inputdeltamzsearchcalibrant.setText("30");
                cc.inputwindowcentroiding.setText("0.5");
                cc.inputlimitcentroiding.setText("2");
            } else if (cc.clusteringtechnique == 2) {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.010");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.200");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.075");
                cc.inputwindowcentroiding.setText("0.5");
                cc.inputlimitcentroiding.setText("2");
            } else {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.010");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.010");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.075");
                cc.inputwindowcentroiding.setText("0.5");
                cc.inputlimitcentroiding.setText("2");
            }
            cc.labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            cc.peaksnoisyspectra.setText("30000");
            if (cc.peakfindmethod == 1) {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (cc.peakfindmethod == 2) {
                cc.labelquantilepeakfinding.setText("Quantile threshold peak finding");
                cc.inputquantilepeakfinding.setText("0.98");
                cc.creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            }
        } else if ((cc.dataexperiment[3].trim().equalsIgnoreCase("4"))
                || (cc.dataexperiment[3].trim().equalsIgnoreCase("5"))) {
            cc.msconvert_programm_32.setSelected(true);
            cc.raw_to_mzXML = "msconvert32";
            if (cc.filtercombobox2 != null) {
                if (cc.filtercombobox2.getSelectedItem().toString().toLowerCase().indexOf(".txt") > -1) {
                    cc.Raw_to_mzXML.setVisible(false);
                    cc.msconvert_programm_64.setVisible(false);
                    cc.msconvert_programm_32.setVisible(false);
                }
                if (cc.filtercombobox2.getSelectedItem().toString().toLowerCase().indexOf(".mzxml") > -1) {
                    cc.Raw_to_mzXML.setVisible(false);
                    cc.msconvert_programm_64.setVisible(false);
                    cc.msconvert_programm_32.setVisible(false);
                }
            }
            cc.perform_ms2_sequencing = true;
            cc.matrix_only_ms2sequenced_masses = false;
            cc.checkbox_ms2sequencing.setState(cc.perform_ms2_sequencing);
            cc.only_ms2sequenced_masses.setState(cc.matrix_only_ms2sequenced_masses);
            cc.input_tol.setText("10");
            if (cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
                cc.input_tol.setText("200");
            }
            for (int i = 0; i < cc.combo_TOLU.getItemCount(); i++) {
                if (cc.combo_TOLU.getItemAt(i).toString().trim().equalsIgnoreCase("ppm")) {
                    cc.combo_TOLU.setSelectedIndex(i);
                }
            }
            cc.input_itol.setText("0.6");
            for (int i = 0; i < cc.combo_ITOLU.getItemCount(); i++) {
                if (cc.combo_ITOLU.getItemAt(i).toString().trim().equalsIgnoreCase("da")) {
                    cc.combo_ITOLU.setSelectedIndex(i);
                }
            }
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(true);
            cc.btn_Gaussian_Lorentzian.setVisible(false);
            cc.btn_sine_bell.setVisible(false);
            cc.btn_Squared_Sine_bell.setVisible(false);
            cc.btn_Hanning.setVisible(false);
            cc.btn_Hamming.setVisible(false);
            cc.btn_Blackmann_Harris.setVisible(false);
            cc.btn_no_apodization.setVisible(false);
            cc.apodization_method.setVisible(false);
            if (cc.dataexperiment[3].trim().equalsIgnoreCase("4")) {
                cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                        .setBorder(BorderFactory.createTitledBorder("Orbitrap"));
            }
            if (cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
                cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                        .setBorder(BorderFactory.createTitledBorder("Bruker Daltonics Ion Trap"));
            }
            cc.inputvarianceisotopicdistance.setText("");
            cc.inputvarianceisotopicdistance.setEnabled(false);
            cc.inputvarianceisotopicdistance.setBackground(cc.colorgrey);
            if (cc.minimumnumberoffractions < 0) {
                cc.minimumnumberoffractions = 4;
            }
            cc.inputLorentzfactorA.setText(String.valueOf(cc.minimumnumberoffractions));
            if (cc.missingfractionsallowed < 0) {
                cc.missingfractionsallowed = 100;
            }
            cc.inputGaussianfaktor.setText(String.valueOf(cc.missingfractionsallowed));
            if (cc.max_charge_state < 0) {
                cc.max_charge_state = 3;
            }
            cc.textfieldmaxchargestate.setText(String.valueOf(cc.max_charge_state));
            if (cc.dataexperiment[3].trim().equalsIgnoreCase("4")) {
                cc.percent_deviation_isotopic_distance = 1;
            }
            if (cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
                cc.percent_deviation_isotopic_distance = 10;
            }
            cc.deviation_isotopicdistance.setText(String.valueOf(cc.percent_deviation_isotopic_distance));
            if (cc.inputerror < 0) {
                cc.inputerror = 0.40;
            }
            cc.texfieldinputerror.setText(String.valueOf(cc.inputerror));
            if (cc.c13_c12 < 0) {
                cc.c13_c12 = 1.0034;
            }
            cc.isotopicdistance.setText(String.valueOf(cc.c13_c12));
            cc.monoistopefinding = true;
            cc.checkdeisotoping.setState(cc.monoistopefinding);
            cc.checkdeisotoping.setEnabled(false);
            cc.labelApodizationformula.setText("");
            cc.labelLorentzianfactor.setText("Number of MS1 scans");
            cc.labelGaussianfaktor.setText("Missing number MS1 scans allowed");
            cc.inputzerofillingfaktor.setEnabled(false);
            cc.inputzerofillingfaktor.setBackground(cc.colorgrey);
            if (cc.clusteringtechnique < 1) {
                cc.clusteringtechnique = 1;
            }
            cc.peakfindmethod = 2;
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                    .setVisible(false);
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(true);
            cc.createMatrixAction.creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                    .setVisible(false);
            if (cc.clusteringtechnique < 1) {
                cc.clusteringtechnique = 2;
            }
            if (cc.centroidingmethod < 0) {
                cc.none.setSelected(true);
                cc.centroidingmethod = 0;
            }
            cc.input_time_widow_lc_ms.setText("300");
            cc.performtimealgnment = true;
            cc.checkboxtimealignment.setState(cc.performtimealgnment);
            cc.inputpointstimealignment.setText("30");
            if (cc.clusteringtechnique == 1) {
                cc.ppm.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (ppm)");
                cc.labellimitcentroiding.setText("limit shift centroiding (ppm)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                if (cc.dataexperiment[3].trim().equalsIgnoreCase("4")) {
                    cc.inputdeltamzsearch.setText("50");
                    cc.inputdeltamzcombine.setText("10");
                    cc.inputdeltamzsearchcalibrant.setText("30");
                    cc.inputwindowcentroiding.setText("0.5");
                    cc.inputlimitcentroiding.setText("2");
                }
                if (cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    cc.inputdeltamzsearch.setText("200");
                    cc.inputdeltamzcombine.setText("200");
                    cc.inputdeltamzsearchcalibrant.setText("500");
                    cc.inputwindowcentroiding.setText("0.5");
                    cc.inputlimitcentroiding.setText("2");
                }
            } else if (cc.clusteringtechnique == 2) {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                if (cc.dataexperiment[3].trim().equalsIgnoreCase("4")) {
                    cc.inputdeltamzsearch.setText("0.1");
                    cc.inputdeltamzcombine.setText("0.075");
                    cc.inputdeltamzsearchcalibrant.setText("0.075");
                    cc.inputwindowcentroiding.setText("0.5");
                    cc.inputlimitcentroiding.setText("2");
                }
                if (cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    cc.inputdeltamzsearch.setText("0.2");
                    cc.inputdeltamzcombine.setText("0.2");
                    cc.inputdeltamzsearchcalibrant.setText("0.5");
                    cc.inputwindowcentroiding.setText("0.5");
                    cc.inputlimitcentroiding.setText("2");
                }
            } else {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                if (cc.dataexperiment[3].trim().equalsIgnoreCase("4")) {
                    cc.inputdeltamzsearch.setText("0.1");
                    cc.inputdeltamzcombine.setText("0.02");
                    cc.inputdeltamzsearchcalibrant.setText("0.075");
                    cc.inputwindowcentroiding.setText("0.5");
                    cc.inputlimitcentroiding.setText("2");
                }
                if (cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    cc.inputdeltamzsearch.setText("0.5");
                    cc.inputdeltamzcombine.setText("0.5");
                    cc.inputdeltamzsearchcalibrant.setText("0.5");
                    cc.inputwindowcentroiding.setText("0.5");
                    cc.inputlimitcentroiding.setText("2");
                }
            }
            cc.labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            cc.peaksnoisyspectra.setText("200000");
            if (cc.peakfindmethod == 1) {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (cc.peakfindmethod == 2) {
                cc.labelquantilepeakfinding.setText("Quantile threshold peak finding");
                if (cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
                    cc.inputquantilepeakfinding.setText("0.90");
                } else {
                    cc.inputquantilepeakfinding.setText("0.75");
                }
                cc.creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            }
            cc.odatamodifications = null;
            try {
                cc.jdbcconnection.con.close();
            } catch (Exception e) {
                ;
            }
            try {
                cc.jdbcconnection.createcon();
            } catch (Exception e) {
                ;
            }
            cc.odatamodifications = cc.systemcodeservice.getsystemcodeitem();
            if (cc.odatamodifications != null) {
                if (cc.odatamodifications.length > 0) {
                    cc.allpossiblemodifications = new String[cc.odatamodifications.length];
                    for (int i = 0; i < cc.odatamodifications.length; i++) {
                        cc.allpossiblemodifications[i] = cc.odatamodifications[i][0].toString();
                    }
                    cc.addDestinationElements(cc.allpossiblemodifications);
                }
            }
            for (int i = 0; i < cc.model_all_modifications.getSize(); i++) {
                if (cc.model_all_modifications.getElementAt(i).toString().toLowerCase().trim()
                        .indexOf("carbamidomethyl (c)") == 0) {
                    cc.model_fixed_mod.add(cc.model_all_modifications.getElementAt(i));
                    cc.model_all_modifications.removeElement(cc.model_all_modifications.getElementAt(i));
                }
                if (cc.model_all_modifications.getElementAt(i).toString().toLowerCase().trim()
                        .indexOf("oxidation (m)") == 0) {
                    cc.model_variabel_mod.add(cc.model_all_modifications.getElementAt(i));
                    cc.model_all_modifications.removeElement(cc.model_all_modifications.getElementAt(i));
                }
            }
            try {
                cc.jdbcconnection.con.close();
            } catch (Exception e) {
                ;
            }
            try {
                cc.jdbcconnection.createcon();
            } catch (Exception e) {
                ;
            }
            cc.odataenzymes = null;
            cc.odataenzymes = cc.systemcodeservice.selectenzymes();
            if (cc.odataenzymes != null) {
                if (cc.odataenzymes.length > 0) {
                    cc.enzymes = new String[cc.odataenzymes.length];
                    for (int i = 0; i < cc.odataenzymes.length; i++) {
                        cc.enzymes[i] = cc.odataenzymes[i][0].toString();
                        if (cc.enzymes[i].trim().equalsIgnoreCase("trypsin")) {
                            cc.selected_enzyme_list.add(cc.enzymes[i].trim());
                        } else {
                            cc.potential_enzyme_list.add(cc.enzymes[i].trim());
                        }
                    }
                }
            }
        } else {
            if (cc.clusteringtechnique < 1) {
                cc.clusteringtechnique = 2;
            }
            if (cc.centroidingmethod < 0) {
                cc.none.setSelected(true);
                cc.centroidingmethod = 0;
            }
            if (cc.clusteringtechnique == 1) {
                cc.ppm.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (ppm)");
                cc.labellimitcentroiding.setText("limit shift centroiding (ppm)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (ppm) ");
                cc.inputdeltamzsearch.setText("3");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (ppm) ");
                cc.inputdeltamzcombine.setText("3");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (ppm) ");
                cc.inputdeltamzsearchcalibrant.setText("30");
                cc.inputwindowcentroiding.setText("0.5");
                cc.inputlimitcentroiding.setText("2");
            } else if (cc.clusteringtechnique == 2) {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.010");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.010");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.075");
                cc.inputwindowcentroiding.setText("0.5");
                cc.inputlimitcentroiding.setText("2");
            } else {
                cc.Da.setSelected(true);
                cc.labelwindowcentroiding.setText("Window centroiding (Da)");
                cc.labellimitcentroiding.setText("limit shift centroiding (Da)");
                cc.labeldeltamzsearch.setText("Find maxima within distance (Da) ");
                cc.inputdeltamzsearch.setText("0.010");
                cc.labeldeltamzcombine.setText("Combine peaks within distance (Da) ");
                cc.inputdeltamzcombine.setText("0.010");
                cc.labeldeltamzsearchcalibrants.setText("Search calibrants within distance (Da) ");
                cc.inputdeltamzsearchcalibrant.setText("0.075");
                cc.inputwindowcentroiding.setText("0.5");
                cc.inputlimitcentroiding.setText("2");
            }
            cc.labelnoisyspectra.setText("Noisy spectra contain peak numbers > ");
            cc.peaksnoisyspectra.setText("10000");
            if (cc.peakfindmethod == 1) {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            } else if (cc.peakfindmethod == 2) {
                cc.labelquantilepeakfinding.setText("Quantile threshold peak finding");
                cc.inputquantilepeakfinding.setText("0.98");
                cc.creatematrixmethodcombobox.setSelectedIndex(1);
            } else {
                cc.labelquantilepeakfinding.setText("Signal to noise peak finding");
                cc.inputquantilepeakfinding.setText("4");
                cc.creatematrixmethodcombobox.setSelectedIndex(0);
            }
        }
        //cc.creatematrixmethodcombobox.addActionListener(this);
        cc.creatematrixmethodcombobox.addActionListener(cc);

        cc.labelnumberofreplicatessample.setText("Maximum mass spectra per sample ");
        cc.inputnumberofreplicatessample.setText("1");
        cc.labelthresholdbinarytable.setText("Threshold binary table   0 <    1 >=");
        cc.inputthresholdbinarytable.setText("2");
        cc.labelminimummass.setText("Minimum mass (Da)");
        cc.inputminimummass.setText("790");
        if (cc.dataexperiment[3].trim().equalsIgnoreCase("4") || cc.dataexperiment[3].trim().equalsIgnoreCase("5")) {
            cc.inputminimummass.setText("0");
        }
        cc.labelmaximummass.setText("Maximum mass (Da)");
        cc.inputmaximummass.setText("4000");
        if (cc.dataexperiment[3].trim().equalsIgnoreCase("4")) {
            cc.inputmaximummass.setText("10000");
        }
    }


}
