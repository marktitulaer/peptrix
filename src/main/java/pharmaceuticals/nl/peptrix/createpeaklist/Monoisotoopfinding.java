package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class Monoisotoopfinding {
    Deisotoping deisotoping;
    Experiment experiment;
    String[] string_of_ids_todelete;
    String dummy;
    double deltamzsearchcalibrantslocal;
    boolean is_an_isotope;
    boolean checkdoublecharge2;

    public Monoisotoopfinding(double[] newcalibrationmassesraw, Experiment experiment) {
        this.experiment = experiment;
        deisotoping = new Deisotoping(experiment);
    }

    public void mono_isotoopfinding(double[][] peaks, int chargestate, double[] newcalibrationmassesraw) {
        checkdoublecharge2 = false;
        deisotoping.determine_potential_isotopic_clusters(peaks, chargestate, checkdoublecharge2);
        dummy = deisotoping.compare_with_theoretical_distribution(peaks);
        if (deisotoping.clusters.getnumber_of_best_isotopic_clusters() > 5) {
            deisotoping.eliminateistopes.delete_masses_within_acceptance_window(peaks, newcalibrationmassesraw,
                    deisotoping.clusters);
        }
        string_of_ids_todelete = deisotoping.clusters.get_ids_todelete().toString().split(",");
        for (int i = 0; i < string_of_ids_todelete.length; i++) {
            if ((!string_of_ids_todelete[i].trim().equals("")) && (Integer.parseInt(string_of_ids_todelete[i]) > 0)) {
                is_an_isotope = true;
                if (experiment.getInternalcalibration()) {
                    deltamzsearchcalibrantslocal = Double.parseDouble(experiment.getdelta_mz_search_calibrants());
                    if (experiment.getclusteringtechnique() == 1) {
                        deltamzsearchcalibrantslocal = peaks[Integer.parseInt(string_of_ids_todelete[i])][0]
                                * Double.parseDouble(experiment.getdelta_mz_search_calibrants()) / 1000000;
                    }
                    for (int j = 0; j <= newcalibrationmassesraw.length - 1; j++) {
                        if ((peaks[Integer.parseInt(string_of_ids_todelete[i])][0] > (newcalibrationmassesraw[j]
                                - deltamzsearchcalibrantslocal))
                                && (peaks[Integer.parseInt(string_of_ids_todelete[i])][0] < (newcalibrationmassesraw[j]
                                + deltamzsearchcalibrantslocal))) {
                            is_an_isotope = false;
                        }
                    }
                }
                if (is_an_isotope) {
                    peaks[Integer.parseInt(string_of_ids_todelete[i])][0] = 0;
                }
            }
        }
    }
}
