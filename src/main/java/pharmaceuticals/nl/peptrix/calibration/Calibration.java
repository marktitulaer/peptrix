package pharmaceuticals.nl.peptrix.calibration;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class Calibration {

    GenerateinputfilesRscript generateinputfilesRscript;

    Controller cc;

    ExportFileToDisk exportfiletodisk;

    CalibrationRScript calibrationRScript;

    Readoutputfilescalibration readoutputfilescalibration;

    double[] calibration_coefficients;

    int count;

    public Calibration(Controller cc) {
        this.cc = cc;
        exportfiletodisk = new ExportFileToDisk(cc);
        calibrationRScript = new CalibrationRScript(cc);
        generateinputfilesRscript = new GenerateinputfilesRscript(cc, exportfiletodisk);
        readoutputfilescalibration = new Readoutputfilescalibration(cc);
    }

    public double[] performcalibration(double[] y_data, double[] x_data, String Rscript,
                                       int number_of_calibration_coefficients, String inputRscriptname) {
        calibration_coefficients = new double[number_of_calibration_coefficients];
        String Rscriptname = inputRscriptname + ".R";
        calibrationRScript.generateRscript(Rscript, Rscriptname);
        generateinputfilesRscript.generateinputfiles(y_data, x_data);
        cc.rterm.createRbatchfile(inputRscriptname);
        int procesexitvalue = cc.rterm.execRscript();
        if (procesexitvalue == 0) {
            readoutputfilescalibration.readoutputfiles(calibration_coefficients);
        }
        return calibration_coefficients;
    }

}
