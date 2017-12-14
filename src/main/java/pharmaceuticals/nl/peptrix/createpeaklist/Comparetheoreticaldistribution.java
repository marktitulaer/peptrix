package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class Comparetheoreticaldistribution {

	Experiment experiment;

	Theoreticaldistribution theoreticaldistribution;

	StringBuffer list_monotopic_id;

	StringBuffer bufferoverviewofisotopes2;

	String[] isotopes;

	String overviewofisotopes;

	double[] ratio;

	double inputerror;

	double potentialisotope;

	double sum_error_nosulphur;

	double error_nosulphur;

	double sum_error_onesulphur;

	double error_onesulphur;

	double sum_error_twosulphur;

	double error_twosulphur;

	double save_error;

	double return_error;

	double istopehigh;

	double istopelow;

	int counterror;

	int l;

	int chargestate;

	int numberoflines2;

	boolean calculate;

	boolean is_isotopic_cluster;

	public Comparetheoreticaldistribution(Experiment experiment) {
		this.experiment = experiment;
		theoreticaldistribution = new Theoreticaldistribution();
	}

	public StringBuffer compare_with_theoretical_distribution(double[][] peaks, Clusters clusters) {
		String linefeed = "\n";
		chargestate = clusters.getchargestate();
		numberoflines2 = clusters.getnumberoflines();
		bufferoverviewofisotopes2 = clusters.getbufferoverviewofisotopes();
		inputerror = experiment.getdeviation_from_expected_intensity_ratio();
		potentialisotope = 0;
		sum_error_nosulphur = 0;
		error_nosulphur = 0;
		sum_error_onesulphur = 0;
		error_onesulphur = 0;
		sum_error_twosulphur = 0;
		error_twosulphur = 0;
		istopehigh = (experiment.getisotopic_distance_c13_c12() / chargestate)
				+ ((experiment.getisotopic_distance_c13_c12())
						* experiment.getpercent_deviation_from_isotopic_distance() / 100);
		istopelow = (experiment.getisotopic_distance_c13_c12() / chargestate)
				- ((experiment.getisotopic_distance_c13_c12())
						* experiment.getpercent_deviation_from_isotopic_distance() / 100);
		save_error = -1;
		return_error = 0;
		clusters.init_ids_todelete();
		list_monotopic_id = new StringBuffer();
		while (numberoflines2 > 0) {
			numberoflines2--;
			isotopes = bufferoverviewofisotopes2.substring(0, bufferoverviewofisotopes2.indexOf(linefeed)).toString()
					.split(",");
			sum_error_nosulphur = 0;
			sum_error_onesulphur = 0;
			sum_error_twosulphur = 0;
			error_nosulphur = 0;
			calculate = true;
			counterror = 0;
			ratio = new double[Integer.parseInt(isotopes[1]) - 1];
			for (int i = 0; i <= Integer.parseInt(isotopes[1]) - 1; i++) {
				if ((i <= Integer.parseInt(isotopes[1]) - 2) && (peaks[Integer.parseInt(isotopes[i + 2])][1] > 0)
						&& (i < theoreticaldistribution.returnnumberofcoefficients())) {
					ratio[i] = peaks[Integer.parseInt(isotopes[i + 3])][1]
							/ peaks[Integer.parseInt(isotopes[i + 2])][1];
					if (ratio[i] < 0) {
						calculate = false;
					}
					try {
						error_nosulphur = Math.abs(Math
								.log(theoreticaldistribution.theoretical_distributions_no_sulphur(
										peaks[Integer.parseInt(isotopes[i + 2])][0], i, chargestate))
								- Math.log(ratio[i]));
					} catch (Exception e) {
						calculate = false;
					}
					try {
						error_onesulphur = Math.abs(Math
								.log(theoreticaldistribution.theoretical_distributions_one_sulphur(
										peaks[Integer.parseInt(isotopes[i + 2])][0], i, chargestate))
								- Math.log(ratio[i]));
					} catch (Exception e) {
						calculate = false;
					}
					try {
						error_twosulphur = Math.abs(Math
								.log(theoreticaldistribution.theoretical_distributions_two_sulphur(
										peaks[Integer.parseInt(isotopes[i + 2])][0], i, chargestate))
								- Math.log(ratio[i]));
					} catch (Exception e) {
						calculate = false;
					}
					counterror++;
					sum_error_nosulphur = sum_error_nosulphur + error_nosulphur;
					sum_error_onesulphur = sum_error_onesulphur + error_onesulphur;
					sum_error_twosulphur = sum_error_twosulphur + error_twosulphur;
					if (((sum_error_nosulphur / counterror) <= inputerror)
							|| ((sum_error_onesulphur / counterror) <= inputerror)
							|| ((sum_error_twosulphur / counterror) <= inputerror)) {
					} else {
						if (chargestate > 1) {
							if (counterror == 1) {
								save_error = sum_error_nosulphur / counterror;
								if ((sum_error_onesulphur / counterror) < save_error) {
									save_error = sum_error_onesulphur / counterror;
								}
								if ((sum_error_twosulphur / counterror) < save_error) {
									save_error = sum_error_twosulphur / counterror;
								}
							}
						}
						i = Integer.parseInt(isotopes[1]);
						counterror--;
						sum_error_nosulphur = sum_error_nosulphur - error_nosulphur;
						sum_error_onesulphur = sum_error_onesulphur - error_onesulphur;
						sum_error_twosulphur = sum_error_twosulphur - error_twosulphur;
					}
				}
			}
			if (calculate) {
				error_nosulphur = sum_error_nosulphur / counterror;
				error_onesulphur = sum_error_onesulphur / counterror;
				error_twosulphur = sum_error_twosulphur / counterror;
				is_isotopic_cluster = false;
				if (counterror > 0) {
					if ((error_nosulphur <= inputerror) || (error_onesulphur <= inputerror)
							|| (error_twosulphur <= inputerror)) {
						is_isotopic_cluster = true;
					}
				}
				if ((counterror == 0) && (isotopes.length == 4)) {
					if (chargestate > 1) {
						if (save_error > -1) {
							if (save_error <= (3 * inputerror)) {
								potentialisotope = peaks[Integer.parseInt(isotopes[3])][0];
								l = Integer.parseInt(isotopes[3]);
								while (l < (peaks.length - 1)) {
									l++;
									if ((Math.abs(peaks[l][0] - potentialisotope) < istopehigh)
											&& (Math.abs(peaks[l][0] - potentialisotope) > istopelow)) {
										is_isotopic_cluster = true;
									}
									if (Math.abs(peaks[l][0] - potentialisotope) > 2 * istopehigh) {
										l = peaks.length;
									}
								}
							}
						}
					}
				}
				if (is_isotopic_cluster) {
					return_error = error_nosulphur;
					if (error_onesulphur < return_error) {
						return_error = error_onesulphur;
					}
					if (error_twosulphur < return_error) {
						return_error = error_twosulphur;
					}
					if (counterror > 5) {
						counterror = Integer.parseInt(isotopes[1]) - 1;
					}
					for (int i = 0; i <= counterror; i++) {
						if (i > 0) {
							clusters.append_ids_todelete(Integer.parseInt(isotopes[i + 2]) + ",");
						} else {
							list_monotopic_id.append(Integer.parseInt(isotopes[i + 2]) + ","
									+ String.valueOf(counterror) + "," + String.valueOf(return_error) + linefeed);
						}
					}
				}
			}
			overviewofisotopes = bufferoverviewofisotopes2.substring(bufferoverviewofisotopes2.indexOf(linefeed) + 1);
			bufferoverviewofisotopes2 = new StringBuffer(overviewofisotopes);
		}
		return list_monotopic_id;
	}
}
