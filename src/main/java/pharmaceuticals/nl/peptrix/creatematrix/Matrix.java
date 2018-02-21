package pharmaceuticals.nl.peptrix.creatematrix;

public class Matrix {
    double[] totalcount;
    double[] samplecount;
    double[] accumulatemass;
    double[] sampleintensity;
    double[] boolean_addnoisepeaks;
    double[] samplenoisecount;
    double[] samplenoiseintensity;
    String[] detailinformation;

    public Matrix(int matrixlenght) {
        boolean_addnoisepeaks = new double[matrixlenght];
        totalcount = new double[matrixlenght];
        samplenoisecount = new double[matrixlenght];
        samplecount = new double[matrixlenght];
        sampleintensity = new double[matrixlenght];
        samplenoiseintensity = new double[matrixlenght];
        accumulatemass = new double[matrixlenght];
    }

    public void setboolean_addnoisepeaks(int i, double value) {
        boolean_addnoisepeaks[i] = value;
    }

    public double getboolean_addnoisepeaks(int i) {
        return boolean_addnoisepeaks[i];
    }

    public double[] getboolean_addnoisepeaks2() {
        return boolean_addnoisepeaks;
    }

    public void settotalcount(int i, double value) {
        totalcount[i] = value;
    }

    public void addtotalcount(int i, double value) {
        totalcount[i] = totalcount[i] + value;
    }

    public double gettotalcount(int i) {
        return totalcount[i];
    }

    public void setsamplenoisecount(int i, double value) {
        samplenoisecount[i] = value;
    }

    public double getsamplenoisecount(int i) {
        return samplenoisecount[i];
    }

    public void setsamplecount(int i, double value) {
        samplecount[i] = value;
    }

    public void addsamplecount(int i, double value) {
        samplecount[i] = samplecount[i] + value;
    }

    public double getsamplecount(int i) {
        return samplecount[i];
    }

    public int getsamplecountlength() {
        return samplecount.length;
    }

    public void setsampleintensity(int i, double value) {
        sampleintensity[i] = value;
    }

    public void addsampleintensity(int i, double value) {
        sampleintensity[i] = sampleintensity[i] + value;
    }

    public double getsampleintensity(int i) {
        return sampleintensity[i];
    }

    public void setsamplenoiseintensity(int i, double value) {
        samplenoiseintensity[i] = value;
    }

    public double getsamplenoiseintensity(int i) {
        return samplenoiseintensity[i];
    }

    public void addaccumulatemass(int i, double value) {
        accumulatemass[i] = accumulatemass[i] + value;
    }

    public double getaccumulatemass(int i) {
        return accumulatemass[i];
    }

    public void setdetailinformation(int i, String text) {
        detailinformation[i] = text;
    }

    public String getdetailinformation(int i) {
        return detailinformation[i];
    }
}
