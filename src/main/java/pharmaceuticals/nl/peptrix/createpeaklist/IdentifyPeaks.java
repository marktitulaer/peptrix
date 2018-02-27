package pharmaceuticals.nl.peptrix.createpeaklist;

import java.io.*;
import java.math.BigDecimal;
import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class IdentifyPeaks {
    FileInputStream fstream;
    BufferedReader in;
    Controller cc;
    IdentifypeaksRscript identifypeaksrscript;
    BigDecimal tempBD;
    FileOutputStream os;
    StringBuffer databuffer;
    String stringdatabuffer;
    String Rscriptname;
    double[][] peaks;
    double[][] noisepeaks;
    double minimummass;
    double maximummass;
    int count;
    int procesexitvalue;
    byte[] data;
    Experiment experiment;

    public IdentifyPeaks(Controller cc, Experiment experiment) {
        this.cc = cc;
        this.experiment = experiment;
        identifypeaksrscript = new IdentifypeaksRscript(cc);
    }

    public double[][] returnpeaks(FTMSdata ftmsdata, Spectrum spectrum) {
        Rscriptname = "Identifypeaks";
        boolean scriptgenerated = identifypeaksrscript.generateRscript(experiment, Rscriptname + ".R");
        if (experiment.getequipmentid() == 2) {
            generateinputfilesFTMS(ftmsdata);
        } else {
            generateinputfiles(spectrum);
        }
        executescript();
        return peaks;
    }

    private void generateinputfilesFTMS(FTMSdata ftmsdata) {
        minimummass = Double.parseDouble(experiment.getminimum_mass());
        maximummass = Double.parseDouble(experiment.getmaximum_mass());
        databuffer = new StringBuffer();
        count = 0;
        try {
            os = new FileOutputStream(cc.rterm.RinputBasePath + "input0.txt");
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (ftmsdata.data[ftmsdata.data.length / 2] < ftmsdata.data[ftmsdata.data.length - 1]) {
            for (int i = 0; i <= (ftmsdata.data.length / 2) - 1; i++) {
                if ((ftmsdata.data[ftmsdata.data.length / 2 + i] >= minimummass)
                        && (ftmsdata.data[ftmsdata.data.length / 2 + i] <= maximummass)) {
                    count++;
                    tempBD = new BigDecimal(ftmsdata.data[ftmsdata.data.length / 2 + i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(6, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    tempBD = new BigDecimal(ftmsdata.data[i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(5, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    databuffer.append(String.valueOf(i) + " ");
                    if (count >= 1000) {
                        stringdatabuffer = databuffer.toString();
                        try {
                            data = stringdatabuffer.getBytes();
                            os.write(data);
                            os.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        databuffer = new StringBuffer();
                        count = 0;
                    }
                }
            }
        } else {
            for (int i = (ftmsdata.data.length / 2) - 1; i >= 0; i--) {
                if ((ftmsdata.data[ftmsdata.data.length / 2 + i] >= minimummass)
                        && (ftmsdata.data[ftmsdata.data.length / 2 + i] <= maximummass)) {
                    count++;
                    tempBD = new BigDecimal(ftmsdata.data[ftmsdata.data.length / 2 + i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(6, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    tempBD = new BigDecimal(ftmsdata.data[i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(5, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    databuffer.append(String.valueOf(i) + " ");
                    if (count >= 1000) {
                        stringdatabuffer = databuffer.toString();
                        try {
                            data = stringdatabuffer.getBytes();
                            os.write(data);
                            os.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        databuffer = new StringBuffer();
                        count = 0;
                    }
                }
            }
        }
        stringdatabuffer = databuffer.toString();
        try {
            data = stringdatabuffer.getBytes();
            os.write(data);
            os.flush();
            os.close();
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateinputfiles(Spectrum spectrum) {
        minimummass = Double.parseDouble(experiment.getminimum_mass());
        maximummass = Double.parseDouble(experiment.getmaximum_mass());
        databuffer = new StringBuffer();
        count = 0;
        try {
            os = new FileOutputStream(cc.rterm.RinputBasePath + "input0.txt");
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (spectrum.mass[0] < spectrum.mass[spectrum.intensity.length - 1]) {
            for (int i = 0; i <= spectrum.intensity.length - 1; i++) {
                if ((spectrum.mass[i] >= minimummass) && (spectrum.mass[i] <= maximummass)) {
                    count++;
                    tempBD = new BigDecimal(spectrum.mass[i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(5, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    tempBD = new BigDecimal(spectrum.intensity[i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(5, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    databuffer.append(String.valueOf(i) + " ");
                    if (count >= 1000) {
                        stringdatabuffer = databuffer.toString();
                        try {
                            data = stringdatabuffer.getBytes();
                            os.write(data);
                            os.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        databuffer = new StringBuffer();
                        count = 0;
                    }
                }
            }
        } else {
            for (int i = spectrum.intensity.length - 1; i >= 0; i--) {
                if ((spectrum.mass[i] >= minimummass) && (spectrum.mass[i] <= maximummass)) {
                    count++;
                    tempBD = new BigDecimal(spectrum.mass[i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(5, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    tempBD = new BigDecimal(spectrum.intensity[i]);
                    databuffer
                            .append(String.valueOf(tempBD.setScale(5, BigDecimal.ROUND_HALF_EVEN).doubleValue()) + " ");
                    databuffer.append(String.valueOf(i) + " ");
                    if (count >= 1000) {
                        stringdatabuffer = databuffer.toString();
                        try {
                            data = stringdatabuffer.getBytes();
                            os.write(data);
                            os.flush();
                        } catch (Exception e) {
                            if (cc.debugmode) {
                                e.printStackTrace();
                            } else {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        databuffer = new StringBuffer();
                        count = 0;
                    }
                }
            }
        }
        stringdatabuffer = databuffer.toString();
        try {
            data = stringdatabuffer.getBytes();
            os.write(data);
            os.flush();
            os.close();
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void executescript() {
        cc.rterm.createRbatchfile(Rscriptname);
        procesexitvalue = cc.rterm.execRscript();
        if (procesexitvalue == 0) {
            readoutputfiles();
        }
        if (procesexitvalue == 1) {
            peaks = null;
        }
    }

    private void readoutputfiles() {
        try {
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output0.txt");
            in = new BufferedReader(new InputStreamReader(fstream));
            count = 0;
            while (in.ready()) {
                in.readLine();
                count++;
            }
            peaks = new double[count][3];
            in.close();
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output0.txt");
            in = new BufferedReader(new InputStreamReader(fstream));
            count = 0;
            while (in.ready()) {
                peaks[count][0] = Double.parseDouble(in.readLine());
                count++;
            }
            in.close();
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output1.txt");
            in = new BufferedReader(new InputStreamReader(fstream));
            count = 0;
            while (in.ready()) {
                peaks[count][1] = Double.parseDouble(in.readLine());
                count++;
            }
            in.close();
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output2.txt");
            in = new BufferedReader(new InputStreamReader(fstream));
            count = 0;
            while (in.ready()) {
                peaks[count][2] = Double.parseDouble(in.readLine());
                count++;
            }
            in.close();
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output3.txt");
            in = new BufferedReader(new InputStreamReader(fstream));
            count = 0;
            while (in.ready()) {
                in.readLine();
                count++;
            }
            noisepeaks = new double[count][2];
            in.close();
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output3.txt");
            in = new BufferedReader(new InputStreamReader(fstream));
            count = 0;
            while (in.ready()) {
                noisepeaks[count][0] = Double.parseDouble(in.readLine());
                count++;
            }
            in.close();
            fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output4.txt");
            in = new BufferedReader(new InputStreamReader(fstream));
            count = 0;
            while (in.ready()) {
                noisepeaks[count][1] = Double.parseDouble(in.readLine());
                count++;
            }
            in.close();
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public double[][] returnnoisepeaks() {
        return noisepeaks;
    }
}
