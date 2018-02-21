package pharmaceuticals.nl.peptrix.calibration;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import pharmaceuticals.nl.peptrix.Controller;

public class Readoutputfilescalibration {

    Controller cc;

    int count;

    public Readoutputfilescalibration(Controller cc) {
        this.cc = cc;
    }

    public void readoutputfiles(double[] calibration_coefficients) {
        try {
            count = 0;
            FileInputStream fstream = new FileInputStream(cc.rterm.ReportsBasePath + "output0.txt");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(in));
            String inputLine;
            while ((inputLine = bufferedreader.readLine()) != null) {
                try {
                    calibration_coefficients[count] = Double.parseDouble(inputLine.trim());
                } catch (Exception e2) {
                    calibration_coefficients[count] = 0;
                }
                count++;
            }
            bufferedreader.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
