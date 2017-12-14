package pharmaceuticals.nl.peptrix.calibration;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class CalibrationRScript {

	Controller cc;

	CalibrationRScript calibrationRScript;

	ExportFileToDisk exportfiletodisk;

	StringBuffer xdatabuffer;

	StringBuffer ydatabuffer;

	StringBuffer inputpathbuffer;

	StringBuffer outputpathbuffer;

	String searchstring;

	String inputpath;

	String teststring;

	String outputpath;

	char backslash = (char) 92;

	double[] calibration_coefficients;

	int count;

	public CalibrationRScript(Controller cc) {
		this.cc = cc;
		exportfiletodisk = new ExportFileToDisk(cc);
	}

	public boolean generateRscript(String Rscript, String Rscriptname) {
		teststring = String.valueOf(cc.rterm.RinputBasePath);
		inputpathbuffer = new StringBuffer();
		while (teststring.indexOf(backslash) > -1) {
			inputpathbuffer.append(teststring.substring(0, teststring.indexOf(backslash)));
			inputpathbuffer.append(backslash);
			inputpathbuffer.append(backslash);
			inputpathbuffer.append(backslash);
			inputpathbuffer.append(backslash);
			teststring = teststring.substring(teststring.indexOf(backslash) + 1);
		}
		inputpath = inputpathbuffer.toString();
		teststring = String.valueOf(cc.rterm.ReportsBasePath);
		outputpathbuffer = new StringBuffer();
		while (teststring.indexOf(backslash) > -1) {
			outputpathbuffer.append(teststring.substring(0, teststring.indexOf(backslash)));
			outputpathbuffer.append(backslash);
			outputpathbuffer.append(backslash);
			outputpathbuffer.append(backslash);
			outputpathbuffer.append(backslash);
			teststring = teststring.substring(teststring.indexOf(backslash) + 1);
		}
		outputpath = outputpathbuffer.toString();
		for (int i = 0; i <= 20; i++) {
			searchstring = "input" + String.valueOf(i);
			if (Rscript.indexOf(searchstring) >= 0) {
				Rscript = Rscript.replaceAll(searchstring, inputpath + searchstring + ".txt");
			}
			searchstring = "output" + String.valueOf(i);
			if (Rscript.indexOf(searchstring) >= 0) {
				Rscript = Rscript.replaceAll(searchstring, outputpath + searchstring + ".txt");
			}
		}
		boolean exporttodisksucceeded = exportfiletodisk
				.exportcompletefilename((cc.rterm.RsourceBasePath + Rscriptname), Rscript.getBytes());
		return exporttodisksucceeded;
	}
}
