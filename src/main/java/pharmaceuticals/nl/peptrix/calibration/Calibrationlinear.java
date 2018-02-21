package pharmaceuticals.nl.peptrix.calibration;

import pharmaceuticals.nl.peptrix.Controller;

public class Calibrationlinear {
    Controller cc;
    Calibration calibration;

    public Calibrationlinear(Controller cc) {
        this.cc = cc;
        calibration = new Calibration(cc);
    }

    public double[] linear(double[] y_data, double[] x_data) {
        int number_of_calibration_coefficients = 3;
        String Rscript = "#input files" + cc.linefeed + "filexdata <- \"input0\"" + cc.linefeed
                + "fileydata <- \"input1\"" + cc.linefeed + "#output files" + cc.linefeed + "fileoutput <- \"output0\""
                + cc.linefeed + " options(digits=16) " + cc.linefeed + "if (file.exists(filexdata)){" + cc.linefeed
                + "    x <- scan(filexdata,sep=\",\")" + cc.linefeed + "    y <- scan(fileydata,sep=\",\")"
                + cc.linefeed + "    ft2 <- lm(y ~ I(x))   " + cc.linefeed + "    a <- coef(ft2)   " + cc.linefeed
                + "    write(a, file = fileoutput,ncolumns = 1,append = FALSE)" + cc.linefeed + "}";
        String Rscriptname = "CalibrationLinear";
        double[] calibration_coefficients = calibration.performcalibration(y_data, x_data, Rscript,
                number_of_calibration_coefficients, Rscriptname);
        return calibration_coefficients;
    }
}
