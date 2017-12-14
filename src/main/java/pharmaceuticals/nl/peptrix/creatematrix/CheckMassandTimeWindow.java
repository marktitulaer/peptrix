package pharmaceuticals.nl.peptrix.creatematrix;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class CheckMassandTimeWindow {

	Experiment experiment;

	double deltamzcombinelocal;

	double absolutedifference;

	double deltatimecombine;

	double deltatimelocalcombine;

	boolean massandtimewindowOK;

	boolean withintimewindow;

	boolean withinmasswindow;

	public CheckMassandTimeWindow(Experiment experiment) {
		this.experiment = experiment;
	}

	public boolean checkMassandTimeWindow(double mass_peaklist, double reference_mass,
			boolean extra_retention_time_and_ms2_sequencing_data, double minimum_retentiontime_peaklist,
			double maximum_retentiontime_peaklist, double retentiontime_peaklist, double reference_retentiontime,
			double reference_min_retentiontime, double reference_max_retentiontime) {
		int timeclusteringtechnique = experiment.gettime_clustering_absolute_or_percentage();
		massandtimewindowOK = false;
		withintimewindow = false;
		withinmasswindow = false;
		deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
		deltatimecombine = experiment.gettime_window_combining_peptide_masses();
		if (experiment.getclusteringtechnique() == 1) {
			// ppm
			deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine()) * reference_mass / 1000000;
		}
		absolutedifference = Math.abs(mass_peaklist - reference_mass);
		if (absolutedifference < deltamzcombinelocal) {
			withinmasswindow = true;
			withintimewindow = true;

			// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
			// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
			// System.out.println("checkMassandTimeWindow reference_mass " + reference_mass
			// + " mass_peaklist "
			// + mass_peaklist);
			// }
			// }

			if (experiment.getHas_retentiontime()) {
				withintimewindow = false;
				deltatimelocalcombine = deltatimecombine;

				// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
				// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
				// System.out.println("1a: withintimewindow" + withintimewindow + "
				// deltatimelocalcombine "
				// + deltatimelocalcombine);
				// }
				// }

				if (timeclusteringtechnique == 1) {
					deltatimelocalcombine = deltatimecombine * reference_retentiontime / 100;
				}

				// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
				// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
				// System.out.println("1b: withintimewindow" + withintimewindow + "
				// deltatimelocalcombine "
				// + deltatimelocalcombine);
				// }
				// }

				// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
				// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
				// System.out.println(" reference_retentiontime " + reference_retentiontime
				// + " retentiontime_peaklist " + retentiontime_peaklist
				// + " Math.abs(reference_retentiontime - retentiontime_peaklist) "
				// + Math.abs(reference_retentiontime - retentiontime_peaklist));
				//
				// }
				// }

				if (Math.abs(reference_retentiontime - retentiontime_peaklist) < deltatimelocalcombine) {
					withintimewindow = true;

					// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
					// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
					// System.out.println("2 : withintimewindow" + withintimewindow);
					// }
					// }

				} else {

					// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
					// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
					// System.out.println("3 : else" + withintimewindow);
					// System.out.println("3 : experiment.getequipmentid() " +
					// experiment.getequipmentid());
					// System.out.println("3 : extra_retention_time_and_ms2_sequencing_data "
					// + extra_retention_time_and_ms2_sequencing_data);
					// System.out.println("3 : reference_min_retentiontime " +
					// reference_min_retentiontime);
					// System.out.println("3 : reference_max_retentiontime " +
					// reference_max_retentiontime);
					// }
					// }

					if ((experiment.getequipmentid() == 4) && (extra_retention_time_and_ms2_sequencing_data)
							&& (reference_min_retentiontime > -1) && (reference_max_retentiontime > -1)) {
						if ((minimum_retentiontime_peaklist < reference_min_retentiontime)
								&& (maximum_retentiontime_peaklist > reference_min_retentiontime)) {
							withintimewindow = true;
						}

						// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
						// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
						// System.out.println("4 : else" + withintimewindow);
						// }
						// }

						if ((minimum_retentiontime_peaklist < reference_max_retentiontime)
								&& (maximum_retentiontime_peaklist > reference_max_retentiontime)) {
							withintimewindow = true;
						}

						// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
						// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
						// System.out.println("5 : else" + withintimewindow);
						/// }
						// }

						if ((minimum_retentiontime_peaklist > reference_min_retentiontime)
								&& (maximum_retentiontime_peaklist < reference_max_retentiontime)) {
							withintimewindow = true;
						}

						// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
						// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
						// System.out.println("6 : else" + withintimewindow);
						// }
						// }

						if (minimum_retentiontime_peaklist > -1) {
							if (Math.abs(
									minimum_retentiontime_peaklist - reference_retentiontime) < deltatimelocalcombine) {
								withintimewindow = true;
							}

							// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
							/// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
							// System.out.println("7 : else" + withintimewindow);
							// }
							// }

						}
						if (maximum_retentiontime_peaklist > -1) {
							if (Math.abs(
									maximum_retentiontime_peaklist - reference_retentiontime) < deltatimelocalcombine) {
								withintimewindow = true;
							}

							// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
							// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
							// System.out.println("8 : else" + withintimewindow);
							// }
							// }

						}
						if (reference_min_retentiontime > -1) {
							if (Math.abs(
									reference_min_retentiontime - retentiontime_peaklist) < deltatimelocalcombine) {
								withintimewindow = true;
							}

							/// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
							// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
							// System.out.println("9 : else" + withintimewindow);
							// }
							// }

						}
						if (reference_max_retentiontime > -1) {
							if (Math.abs(
									reference_max_retentiontime - retentiontime_peaklist) < deltatimelocalcombine) {
								withintimewindow = true;
							}

							// if ((reference_mass > 1910.930) && (reference_mass < 1910.932)) {
							// if ((mass_peaklist > 1910.930) && (mass_peaklist < 1910.932)) {
							// System.out.println("10 : else" + withintimewindow);
							// }
							// }

						}
					}
				}
			}
		}
		massandtimewindowOK = (withinmasswindow && withintimewindow);
		return massandtimewindowOK;
	}
}
