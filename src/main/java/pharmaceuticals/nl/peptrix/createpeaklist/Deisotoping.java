package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

class Deisotoping {

	Eliminateistopes eliminateistopes;

	Clusters clusters;

	Potentialisotopicclusters potentialisotopicclusters;

	Comparetheoreticaldistribution comparetheoreticaldistribution;

	public Deisotoping(Experiment experiment) {
		clusters = new Clusters();
		eliminateistopes = new Eliminateistopes(experiment);
		potentialisotopicclusters = new Potentialisotopicclusters(experiment);
		comparetheoreticaldistribution = new Comparetheoreticaldistribution(experiment);
	}

	public Clusters determine_potential_isotopic_clusters(double[][] peaks, int chargestate,
			boolean checkdoublecharge) {
		clusters = potentialisotopicclusters.determine_potential_isotopic_clusters(peaks, chargestate,
				checkdoublecharge, clusters);
		return clusters;
	}

	public String compare_with_theoretical_distribution(double[][] peaks) {
		StringBuffer list_monotopic_id = comparetheoreticaldistribution.compare_with_theoretical_distribution(peaks,
				clusters);
		return list_monotopic_id.toString();
	}
}
