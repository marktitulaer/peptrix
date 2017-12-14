package pharmaceuticals.nl.peptrix.calibration;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class GenerateinputfilesRscript {

	Controller cc;

	ExportFileToDisk exportfiletodisk;

	StringBuffer xdatabuffer;

	StringBuffer ydatabuffer;

	char backslash = (char) 92;

	double[] calibration_coefficients;

	int count;

	public GenerateinputfilesRscript(Controller cc, ExportFileToDisk exportfiletodisk) {
		this.cc = cc;
		this.exportfiletodisk = exportfiletodisk;
	}

	public boolean generateinputfiles(double[] y, double[] x) {
		xdatabuffer = new StringBuffer();
		for (int i = 0; i <= x.length - 1; i++) {
			if (i > 0) {
				xdatabuffer.append(",");
			}
			xdatabuffer.append(String.valueOf(x[i]));
		}
		ydatabuffer = new StringBuffer();
		for (int i = 0; i <= y.length - 1; i++) {
			if (i > 0) {
				ydatabuffer.append(",");
			}
			ydatabuffer.append(String.valueOf(y[i]));
		}
		boolean exporttodisksucceeded_file1 = exportfiletodisk
				.exportcompletefilename((cc.rterm.RinputBasePath + "input0.txt"), xdatabuffer.toString().getBytes());
		boolean exporttodisksucceeded_file2 = exportfiletodisk
				.exportcompletefilename((cc.rterm.RinputBasePath + "input1.txt"), ydatabuffer.toString().getBytes());
		boolean OK = exporttodisksucceeded_file1 & exporttodisksucceeded_file2;
		return OK;
	}

}
