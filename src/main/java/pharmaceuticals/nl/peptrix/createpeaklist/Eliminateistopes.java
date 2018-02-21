package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class Eliminateistopes {

    Experiment experiment;

    StringBuffer bufferoverviewofisotopes;

    String[] isotopes;

    String linefeed = "\n";

    String overviewofisotopes;

    double stdev;

    double mean1;

    double sum_of_squares2;

    double deltamzsearchcalibrantslocal;

    double mean2;

    int number_of_best_isotopic_clusters;

    int numberoflines2;

    boolean is_an_isotope;

    public Eliminateistopes(Experiment experiment) {
        this.experiment = experiment;
    }

    public void delete_masses_within_acceptance_window(double[][] peaks, double[] newcalibrationmassesraw,
                                                       Clusters clusters) {
        sum_of_squares2 = clusters.getsum_of_squares2();
        mean2 = clusters.getmean2();
        number_of_best_isotopic_clusters = clusters.getnumber_of_best_isotopic_clusters();
        numberoflines2 = clusters.getnumberoflines();
        bufferoverviewofisotopes = clusters.getbufferoverviewofisotopes();
        mean1 = 0;
        mean2 = mean2 / number_of_best_isotopic_clusters;
        stdev = Math.sqrt((sum_of_squares2 - (number_of_best_isotopic_clusters * mean2 * mean2))
                / (number_of_best_isotopic_clusters - 1));
        while (numberoflines2 > 0) {
            numberoflines2--;
            isotopes = bufferoverviewofisotopes.substring(0, bufferoverviewofisotopes.indexOf(linefeed)).toString()
                    .split(",");
            while ((Integer.parseInt(isotopes[1]) > 1) && ((Double.parseDouble(isotopes[0]) < (mean2 - stdev))
                    || (Double.parseDouble(isotopes[0]) > (mean2 + stdev)))) {
                mean1 = 0;
                for (int i = 0; i <= Integer.parseInt(isotopes[1]) - 3; i++) {
                    mean1 = mean1 + (peaks[Integer.parseInt(isotopes[3 + i])][0]
                            - peaks[Integer.parseInt(isotopes[2 + i])][0]);
                }
                mean1 = mean1 / (Integer.parseInt(isotopes[1]) - 2);
                isotopes[0] = String.valueOf(mean1);
                isotopes[1] = String.valueOf(Integer.parseInt(isotopes[1]) - 1);
            }
            if ((Double.parseDouble(isotopes[0]) >= (mean2 - stdev))
                    && (Double.parseDouble(isotopes[0]) <= (mean2 + stdev))) {
                for (int i = 0; i <= Integer.parseInt(isotopes[1]) - 2; i++) {
                    is_an_isotope = true;
                    if (experiment.getInternalcalibration()) {
                        deltamzsearchcalibrantslocal = Double.parseDouble(experiment.getdelta_mz_search_calibrants());
                        if (experiment.getclusteringtechnique() == 1) {
                            deltamzsearchcalibrantslocal = peaks[Integer.parseInt(isotopes[3 + i])][0]
                                    * Double.parseDouble(experiment.getdelta_mz_search_calibrants()) / 1000000;
                        }
                        for (int j = 0; j <= newcalibrationmassesraw.length - 1; j++) {
                            if ((peaks[Integer.parseInt(isotopes[3 + i])][0] > (newcalibrationmassesraw[j]
                                    - deltamzsearchcalibrantslocal))
                                    && (peaks[Integer.parseInt(isotopes[3 + i])][0] < (newcalibrationmassesraw[j]
                                    + deltamzsearchcalibrantslocal))) {
                                is_an_isotope = false;
                            }
                        }
                    }
                    if (is_an_isotope) {
                        peaks[Integer.parseInt(isotopes[3 + i])][0] = 0;
                    }
                }
            }
            overviewofisotopes = bufferoverviewofisotopes.substring(bufferoverviewofisotopes.indexOf(linefeed) + 1);
            bufferoverviewofisotopes = new StringBuffer(overviewofisotopes);
        }
    }
}
