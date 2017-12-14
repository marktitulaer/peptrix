package pharmaceuticals.nl.peptrix.creatematrix;

import java.io.BufferedReader;

import java.io.FileReader;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class CombineNoisePeaks2 {

	BufferedReader in;

	Controller cc;

	String[] noisemass;

	String line;

	String noiseline = "";

	double absolutedifference;

	double max_retention_time;

	double min_retention_time;

	double doubletime;

	double doublemass;

	double doubleintensity;

	double deltatimelocalcombine;

	double deltatimecombine;

	int timeclusteringtechnique;

	int j_save2;

	boolean fixfirst;

	boolean withintimewindow;

	boolean addmasstolist = true;

	public CombineNoisePeaks2(Controller cc) {
		this.cc = cc;
	}

	public void combinenoisepeaks2(String file, Experiment experiment, double[][] doublearraycombinedpeaks,
			Matrix matrix) {
		int clusteringtechnique = experiment.getclusteringtechnique();
		timeclusteringtechnique = experiment.gettime_clustering_absolute_or_percentage();
		deltatimecombine = experiment.gettime_window_combining_peptide_masses();
		double deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
		try {
			in.close();
		} catch (Exception e) {
		}
		j_save2 = 0;
		fixfirst = true;
		try {
			in = new BufferedReader(new FileReader(cc.userhome + cc.fileSeparator + file));
			while ((line = in.readLine()) != null) {
				noiseline = line.toLowerCase();
				noisemass = noiseline.split(" ");
				if (noisemass[0].trim().equalsIgnoreCase("")) {
					doublemass = -1.0;
				} else {
					doublemass = Double.valueOf(noisemass[0].trim()).doubleValue();
				}
				if (noisemass[1].trim().equalsIgnoreCase("")) {
					doubleintensity = -1.0;
				} else {
					doubleintensity = Double.valueOf(noisemass[1].trim()).doubleValue();
				}
				if (noisemass[2].trim().equalsIgnoreCase("")) {
					doubletime = -1.0;
				} else {
					doubletime = Double.valueOf(noisemass[2].trim()).doubleValue();
				}
				if (noisemass[3].trim().equalsIgnoreCase("")) {
					min_retention_time = -1.0;
				} else {
					min_retention_time = Double.valueOf(noisemass[3].trim()).doubleValue();
				}
				if (noisemass[4].trim().equalsIgnoreCase("")) {
					max_retention_time = -1.0;
				} else {
					max_retention_time = Double.valueOf(noisemass[4].trim()).doubleValue();
				}
				fixfirst = true;
				for (int j = j_save2; j < doublearraycombinedpeaks[0].length; j++) {
					if (matrix.boolean_addnoisepeaks[j] == 1) {
						absolutedifference = Math.abs(doublemass - doublearraycombinedpeaks[0][j]);
						if (clusteringtechnique == 1) {
							// ppm
							deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine())
									* doublearraycombinedpeaks[0][j] / 1000000;
						}
						if (absolutedifference < deltamzcombinelocal) {
							if (fixfirst) {
								j_save2 = j;
								fixfirst = false;
							}
							addmasstolist = true;
							deltatimelocalcombine = deltatimecombine;
							if (timeclusteringtechnique == 1) {
								deltatimelocalcombine = deltatimecombine * doubletime / 100;
							}
							withintimewindow = false;
							if (Math.abs(doublearraycombinedpeaks[1][j] - doubletime) < deltatimelocalcombine) {
								withintimewindow = true;
							} else {
								if ((doublearraycombinedpeaks[2][j] > -1) && (doublearraycombinedpeaks[3][j] > -1)) {
									if ((min_retention_time < doublearraycombinedpeaks[2][j])
											&& (max_retention_time > doublearraycombinedpeaks[2][j])) {
										withintimewindow = true;
									}
									if ((min_retention_time < doublearraycombinedpeaks[3][j])
											&& (max_retention_time > doublearraycombinedpeaks[3][j])) {
										withintimewindow = true;
									}
									if ((min_retention_time > doublearraycombinedpeaks[2][j])
											&& (max_retention_time < doublearraycombinedpeaks[3][j])) {
										withintimewindow = true;
									}
								}
							}
							if (withintimewindow) {
								addmasstolist = true;
							} else {
								addmasstolist = false;
							}
							if (addmasstolist) {
								matrix.samplenoisecount[j] = matrix.samplenoisecount[j] + 1;
								matrix.samplenoiseintensity[j] = matrix.samplenoiseintensity[j] + doubleintensity;
							}
						}
					}
				}
			}
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
