package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import pharmaceuticals.nl.peptrix.Controller;

public class PanelFTICR {

    JPanel ftmspanel;

    Controller cc;

    public PanelFTICR(Controller cc) {
        this.cc = cc;
        ftmspanel = new JPanel();
    }

    public void fill_panels_with_fields() {
        ftmspanel.removeAll();
        ftmspanel.setBorder(BorderFactory.createTitledBorder("FTMS"));
        GridBagLayout ftmspanellayout = new GridBagLayout();
        ftmspanel.setLayout(ftmspanellayout);
        GridBagConstraints gridbagcontraintsftmspanel = new GridBagConstraints();
        gridbagcontraintsftmspanel.anchor = GridBagConstraints.FIRST_LINE_START;
        gridbagcontraintsftmspanel.insets = new Insets(2, 2, 2, 2);
        cc.labelApodizationformula = new JLabel();
        cc.labelApodizationformula.setForeground(Color.YELLOW);
        cc.labelApodizationformula.setText("exp{(-pi*LorentzfactorA*i/N) + (-pi*GaussfactorB*(i/N)2)}");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 1;
        gridbagcontraintsftmspanel.gridwidth = 5;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.labelApodizationformula, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.labelApodizationformula);
        cc.labelLorentzianfactor = new JLabel("LorentzfactorA");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 2;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.labelLorentzianfactor, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.labelLorentzianfactor);
        cc.inputLorentzfactorA = new TextField(8);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 2;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.inputLorentzfactorA, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.inputLorentzfactorA);
        cc.labelGaussianfaktor = new JLabel("GaussfactorB");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 3;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.labelGaussianfaktor, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.labelGaussianfaktor);
        cc.inputGaussianfaktor = new TextField(8);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 3;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.inputGaussianfaktor, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.inputGaussianfaktor);
        JLabel labelzerofillingfactor = new JLabel("zerofillingfactor");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 4;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(labelzerofillingfactor, gridbagcontraintsftmspanel);
        ftmspanel.add(labelzerofillingfactor);
        cc.inputzerofillingfaktor = new TextField(8);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 4;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.inputzerofillingfaktor, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.inputzerofillingfaktor);
        JLabel labeldeisotoping = new JLabel("De-isotoping");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 5;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(labeldeisotoping, gridbagcontraintsftmspanel);
        ftmspanel.add(labeldeisotoping);
        cc.checkdeisotoping = new Checkbox();
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 5;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.checkdeisotoping, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.checkdeisotoping);
        JLabel labelisotopicdistance = new JLabel("Isotopic distance");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 6;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(labelisotopicdistance, gridbagcontraintsftmspanel);
        ftmspanel.add(labelisotopicdistance);
        cc.isotopicdistance = new TextField(8);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 6;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.isotopicdistance, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.isotopicdistance);
        JLabel plus_minus = new JLabel("+/-");
        gridbagcontraintsftmspanel.gridx = 3;
        gridbagcontraintsftmspanel.gridy = 6;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(plus_minus, gridbagcontraintsftmspanel);
        ftmspanel.add(plus_minus);
        cc.deviation_isotopicdistance = new TextField(2);
        gridbagcontraintsftmspanel.gridx = 4;
        gridbagcontraintsftmspanel.gridy = 6;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.deviation_isotopicdistance, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.deviation_isotopicdistance);
        JLabel percent = new JLabel("%");
        gridbagcontraintsftmspanel.gridx = 5;
        gridbagcontraintsftmspanel.gridy = 6;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(percent, gridbagcontraintsftmspanel);
        ftmspanel.add(percent);
        JLabel labelvarianceisotopicdistance = new JLabel("Variance isotopic distance");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 7;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(labelvarianceisotopicdistance, gridbagcontraintsftmspanel);
        ftmspanel.add(labelvarianceisotopicdistance);
        cc.inputvarianceisotopicdistance = new TextField(8);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 7;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.inputvarianceisotopicdistance, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.inputvarianceisotopicdistance);
        JLabel percent2 = new JLabel();
        percent2.setText("%");
        gridbagcontraintsftmspanel.gridx = 3;
        gridbagcontraintsftmspanel.gridy = 7;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(percent2, gridbagcontraintsftmspanel);
        ftmspanel.add(percent2);
        JLabel labelinputerror = new JLabel();
        String inputerrorstring = "i" + (char) 931 + "M " + " { Log(Re) - Log(Ro) } /M";
        labelinputerror.setText(inputerrorstring);
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 8;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(labelinputerror, gridbagcontraintsftmspanel);
        ftmspanel.add(labelinputerror);
        cc.texfieldinputerror = new TextField(8);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 8;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.texfieldinputerror, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.texfieldinputerror);
        JLabel labelmaxchargestate = new JLabel("maximum charge state (+)");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 9;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(labelmaxchargestate, gridbagcontraintsftmspanel);
        ftmspanel.add(labelmaxchargestate);
        cc.textfieldmaxchargestate = new TextField(2);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 9;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.textfieldmaxchargestate, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.textfieldmaxchargestate);
        cc.apodization_method = new JLabel("Apodization method");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 10;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.apodization_method, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.apodization_method);
        cc.radiosetapodization = new ButtonGroup();
        cc.btn_Gaussian_Lorentzian = new JRadioButton("Gaussian");
        cc.btn_Gaussian_Lorentzian.setMnemonic(KeyEvent.VK_0);
        cc.btn_Gaussian_Lorentzian.addActionListener(cc);
        cc.radiosetapodization.add(cc.btn_Gaussian_Lorentzian);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 10;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.btn_Gaussian_Lorentzian, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.btn_Gaussian_Lorentzian);
        cc.btn_sine_bell = new JRadioButton("Sine");
        cc.btn_sine_bell.setMnemonic(KeyEvent.VK_0);
        cc.btn_sine_bell.addActionListener(cc);
        cc.radiosetapodization.add(cc.btn_sine_bell);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 11;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.btn_sine_bell, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.btn_sine_bell);
        cc.btn_Squared_Sine_bell = new JRadioButton("Sq Sine");
        cc.btn_Squared_Sine_bell.setMnemonic(KeyEvent.VK_0);
        cc.radiosetapodization.add(cc.btn_Squared_Sine_bell);
        cc.btn_Squared_Sine_bell.addActionListener(cc);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 12;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.btn_Squared_Sine_bell, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.btn_Squared_Sine_bell);
        cc.btn_Hanning = new JRadioButton("Hanning");
        cc.btn_Hanning.setMnemonic(KeyEvent.VK_0);
        cc.radiosetapodization.add(cc.btn_Hanning);
        cc.btn_Hanning.addActionListener(cc);
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 11;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.btn_Hanning, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.btn_Hanning);
        cc.btn_Hamming = new JRadioButton("Hamming");
        cc.btn_Hamming.setMnemonic(KeyEvent.VK_0);
        cc.radiosetapodization.add(cc.btn_Hamming);
        cc.btn_Hamming.addActionListener(cc);
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 12;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.btn_Hamming, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.btn_Hamming);
        cc.btn_Blackmann_Harris = new JRadioButton("Blackmann_Harris");
        cc.btn_Blackmann_Harris.setMnemonic(KeyEvent.VK_0);
        cc.radiosetapodization.add(cc.btn_Blackmann_Harris);
        cc.btn_Blackmann_Harris.addActionListener(cc);
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 13;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.btn_Blackmann_Harris, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.btn_Blackmann_Harris);
        cc.btn_no_apodization = new JRadioButton("None");
        cc.btn_no_apodization.setMnemonic(KeyEvent.VK_0);
        cc.radiosetapodization.add(cc.btn_no_apodization);
        cc.btn_no_apodization.addActionListener(cc);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 13;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.btn_no_apodization, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.btn_no_apodization);
        cc.Raw_to_mzXML = new JLabel("Raw to mzXML conversion");
        gridbagcontraintsftmspanel.gridx = 1;
        gridbagcontraintsftmspanel.gridy = 14;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.Raw_to_mzXML, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.Raw_to_mzXML);
        cc.raw_to_mzXML_programms = new ButtonGroup();


        cc.msconvert_programm_64 = new JRadioButton("msconvert 64");
        cc.msconvert_programm_64.setMnemonic(KeyEvent.VK_1);
        cc.raw_to_mzXML_programms.add(cc.msconvert_programm_64);
        cc.msconvert_programm_64.addActionListener(cc);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 14;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.msconvert_programm_64, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.msconvert_programm_64);
        cc.msconvert_programm_32 = new JRadioButton("msconvert 32");
        cc.msconvert_programm_32.setMnemonic(KeyEvent.VK_1);
        cc.raw_to_mzXML_programms.add(cc.msconvert_programm_32);
        cc.msconvert_programm_32.addActionListener(cc);
        gridbagcontraintsftmspanel.gridx = 2;
        gridbagcontraintsftmspanel.gridy = 15;
        gridbagcontraintsftmspanel.gridwidth = 1;
        gridbagcontraintsftmspanel.gridheight = 1;
        ftmspanellayout.setConstraints(cc.msconvert_programm_32, gridbagcontraintsftmspanel);
        ftmspanel.add(cc.msconvert_programm_32);
    }

    public JPanel getpanel() {
        return ftmspanel;
    }
}
