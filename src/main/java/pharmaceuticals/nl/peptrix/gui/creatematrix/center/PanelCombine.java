package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class PanelCombine {

	Controller cc;

	PanelPreprocessing panelpreprocessing;

	PanelFTICR panelfticr;

	Panel_ms2_sequencing panel_ms2_sequencing;

	JPanel combinepanel;

	JPanel calibrationinputpanel;

	public JPanel preprocessingpanel;

	public JPanel ftmspanel;

	public JPanel ms2_sequencing_panel;

	public Calibrationinputpanel panelcalibrationinput;

	public PanelCombine(Controller cc) {
		this.cc = cc;
		panelcalibrationinput = new Calibrationinputpanel(cc);
		calibrationinputpanel = panelcalibrationinput.getPanel();
		panelpreprocessing = new PanelPreprocessing(cc);
		preprocessingpanel = panelpreprocessing.getpanel();
		panelfticr = new PanelFTICR(cc);
		ftmspanel = panelfticr.getpanel();
		panel_ms2_sequencing = new Panel_ms2_sequencing(cc);
		ms2_sequencing_panel = panel_ms2_sequencing.getpanel();
		combinepanel = new JPanel();
		GridBagLayout gridbagcombine = new GridBagLayout();
		GridBagConstraints constraintscombinepanel = new GridBagConstraints();
		constraintscombinepanel.anchor = GridBagConstraints.FIRST_LINE_START;
		constraintscombinepanel.insets = new Insets(2, 2, 2, 2);
		combinepanel.setLayout(gridbagcombine);
		constraintscombinepanel.gridx = 1;
		constraintscombinepanel.gridy = 1;
		constraintscombinepanel.gridwidth = 1;
		constraintscombinepanel.gridheight = 1;
		gridbagcombine.setConstraints(calibrationinputpanel, constraintscombinepanel);
		combinepanel.add(calibrationinputpanel);
		constraintscombinepanel.gridx = 1;
		constraintscombinepanel.gridy = 2;
		constraintscombinepanel.gridwidth = 1;
		constraintscombinepanel.gridheight = 1;
		gridbagcombine.setConstraints(preprocessingpanel, constraintscombinepanel);
		combinepanel.add(preprocessingpanel);
		constraintscombinepanel.gridx = 2;
		constraintscombinepanel.gridy = 1;
		constraintscombinepanel.gridwidth = 1;
		constraintscombinepanel.gridheight = 2;
		gridbagcombine.setConstraints(ftmspanel, constraintscombinepanel);
		combinepanel.add(ftmspanel);
		constraintscombinepanel.gridx = 3;
		constraintscombinepanel.gridy = 1;
		constraintscombinepanel.gridwidth = 1;
		constraintscombinepanel.gridheight = 2;
		gridbagcombine.setConstraints(ms2_sequencing_panel, constraintscombinepanel);
		combinepanel.add(ms2_sequencing_panel);
	}

	public void fill_panels_with_fields_() {
		panelpreprocessing.fill_panels_with_fields();
		panelfticr.fill_panels_with_fields();
		panel_ms2_sequencing.fill_panels_with_fields_();
	}

	public void setBackground(Color color) {
		preprocessingpanel.setBackground(color);
		cc.msconvert_programm_32.setBackground(color);
		cc.msconvert_programm_64.setBackground(color);
		// cc.Readw_programm.setBackground(color);
		ftmspanel.setBackground(color);
		ms2_sequencing_panel.setBackground(color);
		calibrationinputpanel.setBackground(color);
		cc.ppm.setBackground(color);
		cc.Da.setBackground(color);
		cc.none.setBackground(color);
		cc.btn_Gaussian_Lorentzian.setBackground(color);
		cc.btn_sine_bell.setBackground(color);
		cc.btn_Squared_Sine_bell.setBackground(color);
		cc.btn_Hanning.setBackground(color);
		cc.btn_Hamming.setBackground(color);
		cc.btn_Blackmann_Harris.setBackground(preprocessingpanel.getBackground());
		cc.btn_no_apodization.setBackground(color);
		cc.weightedmean.setBackground(color);
	}

	public JPanel returnpanel() {
		return combinepanel;
	}

}
