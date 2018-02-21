package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class Potentialisotopicclusters {
    Experiment experiment;
    String[] isotopes;
    double istopehigh;
    double istopelow;
    StringBuffer listofpotentialistopes;
    StringBuffer listofpotentialmonoistopes;
    StringBuffer listofindecespotentialistopes;
    double potentialisotope;
    boolean is_an_isotope;
    double mean;
    double istopehigh_doublecharge;
    double istopelow_doublecharge;
    String linefeed = "\n";
    boolean first;
    int numberoflines;
    double mean2;
    int number_of_best_isotopic_clusters;
    double sum_of_squares2;
    String overviewofisotopes;
    StringBuffer bufferoverviewofisotopes;
    int j;
    int countnumberofpotenialisotopes;
    int countnumberofisotopes;
    double intensitypreviousisotope;
    boolean countmoreisotopes;
    boolean is_isotopic_series;
    boolean intensity_second_smaller_than_first;
    boolean intensity_declining;
    int l;
    double maxratio;
    double minratio;
    double sum_of_squares;
    double stdev;

    public Potentialisotopicclusters(Experiment experiment) {
        this.experiment = experiment;
    }

    public Clusters determine_potential_isotopic_clusters(double[][] peaks, int chargestate, boolean checkdoublecharge,
                                                          Clusters clusters) {
        clusters.setchargestate(chargestate);
        potentialisotope = 0;
        mean = 0;
        istopehigh_doublecharge = 0;
        istopelow_doublecharge = 0;
        maxratio = 6;
        minratio = 0.4;
        if (experiment.getequipmentid() == 4) {
            minratio = 0.05;
        }
        first = true;
        listofpotentialmonoistopes = new StringBuffer("");
        clusters.init_listidsofpotentialmonoistopes();
        numberoflines = 0;
        mean2 = 0;
        number_of_best_isotopic_clusters = 0;
        sum_of_squares2 = 0;
        overviewofisotopes = "";
        bufferoverviewofisotopes = new StringBuffer(overviewofisotopes);
        j = 0;
        countnumberofpotenialisotopes = 0;
        countnumberofisotopes = 0;
        intensitypreviousisotope = 0;
        istopehigh = (experiment.getisotopic_distance_c13_c12() / chargestate)
                + ((experiment.getisotopic_distance_c13_c12())
                * experiment.getpercent_deviation_from_isotopic_distance() / 100);
        istopelow = (experiment.getisotopic_distance_c13_c12() / chargestate)
                - ((experiment.getisotopic_distance_c13_c12())
                * experiment.getpercent_deviation_from_isotopic_distance() / 100);
        if (checkdoublecharge) {
            istopehigh_doublecharge = (experiment.getisotopic_distance_c13_c12() / (2 * chargestate))
                    + ((experiment.getisotopic_distance_c13_c12())
                    * experiment.getpercent_deviation_from_isotopic_distance() / 100);
            istopelow_doublecharge = (experiment.getisotopic_distance_c13_c12() / (2 * chargestate))
                    - ((experiment.getisotopic_distance_c13_c12())
                    * experiment.getpercent_deviation_from_isotopic_distance() / 100);
        }
        countmoreisotopes = true;
        for (int i = 0; i <= peaks.length - 1; i++) {
            if (peaks[i][0] > 0) {
                countnumberofpotenialisotopes = 1;
                countnumberofisotopes = 1;
                is_isotopic_series = true;
                intensity_second_smaller_than_first = false;
                intensity_declining = false;
                countmoreisotopes = true;
                listofpotentialistopes = new StringBuffer(String.valueOf(peaks[i][0]));
                listofindecespotentialistopes = new StringBuffer(String.valueOf(i));
                potentialisotope = peaks[i][0];
                is_an_isotope = false;
                l = i;
                intensitypreviousisotope = peaks[i][1];
                while (l > 0) {
                    l--;
                    if ((Math.abs(peaks[l][0] - potentialisotope) < istopehigh)
                            && (Math.abs(peaks[l][0] - potentialisotope) > istopelow)) {
                        if ((Math.abs(intensitypreviousisotope / peaks[l][1]) >= minratio)
                                && (Math.abs(intensitypreviousisotope / peaks[l][1]) <= maxratio)) {
                            is_an_isotope = true;
                        }
                    }
                    if (Math.abs(peaks[l][0] - potentialisotope) > 2 * istopehigh) {
                        l = 0;
                    }
                }
                if (checkdoublecharge) {
                    intensitypreviousisotope = peaks[i][1];
                    l = i;
                    while (l > 0) {
                        l--;
                        if ((Math.abs(peaks[l][0] - potentialisotope) < istopehigh_doublecharge)
                                && (Math.abs(peaks[l][0] - potentialisotope) > istopelow_doublecharge)) {
                            if ((Math.abs(intensitypreviousisotope / peaks[l][1]) >= minratio)
                                    && (Math.abs(intensitypreviousisotope / peaks[l][1]) <= maxratio)) {
                                is_an_isotope = true;
                            }
                        }
                        if (Math.abs(peaks[l][0] - potentialisotope) > 2 * istopehigh_doublecharge) {
                            l = 0;
                        }
                    }
                    l = i;
                    while (l < (peaks.length - 1)) {
                        l++;
                        if ((Math.abs(peaks[l][0] - potentialisotope) < istopehigh_doublecharge)
                                && (Math.abs(peaks[l][0] - potentialisotope) > istopelow_doublecharge)) {
                            is_an_isotope = true;
                        }
                        if (Math.abs(peaks[l][0] - potentialisotope) > 2 * istopehigh_doublecharge) {
                            l = peaks.length;
                        }
                    }
                }
                if (!is_an_isotope) {
                    if (i <= peaks.length - 2) {
                        j = i + 1;
                    }
                    while (j <= peaks.length - 2) {
                        if ((Math.abs(peaks[j][0] - potentialisotope) < istopehigh)
                                && (Math.abs(peaks[j][0] - potentialisotope) > istopelow)) {
                            potentialisotope = peaks[j][0];
                            countnumberofpotenialisotopes++;
                            boolean add_potential_isotope = false;
                            if (countmoreisotopes) {
                                add_potential_isotope = true;
                            }
                            if ((Math.abs(intensitypreviousisotope / peaks[j][1]) < minratio)
                                    || (Math.abs(intensitypreviousisotope / peaks[j][1]) > maxratio)) {
                                countmoreisotopes = false;
                                add_potential_isotope = false;
                            }
                            if (countnumberofpotenialisotopes == 2) {
                                if (peaks[j][1] < intensitypreviousisotope) {
                                    intensity_second_smaller_than_first = true;
                                } else {
                                    intensity_second_smaller_than_first = false;
                                }
                            }
                            if ((countnumberofpotenialisotopes > 2) && (intensity_second_smaller_than_first)) {
                                if (peaks[j][1] > intensitypreviousisotope) {
                                    countmoreisotopes = false;
                                    add_potential_isotope = false;
                                }
                            }
                            if ((countnumberofpotenialisotopes > 2) && (intensity_declining)) {
                                if (peaks[j][1] > intensitypreviousisotope) {
                                    countmoreisotopes = false;
                                    add_potential_isotope = false;
                                }
                            }
                            if ((countnumberofpotenialisotopes > 2) && (!intensity_second_smaller_than_first)) {
                                if (peaks[j][1] < intensitypreviousisotope) {
                                    intensity_declining = true;
                                }
                            }
                            if (add_potential_isotope) {
                                countnumberofisotopes++;
                                if (countnumberofisotopes == 2) {
                                    if (first) {
                                        first = false;
                                        listofpotentialmonoistopes.append(listofpotentialistopes.toString());
                                        clusters.append_listidsofpotentialmonoistopes(
                                                listofindecespotentialistopes.toString());
                                    } else {
                                        listofpotentialmonoistopes.append("," + listofpotentialistopes.toString());
                                        clusters.append_listidsofpotentialmonoistopes(
                                                "," + listofindecespotentialistopes.toString());
                                    }
                                }
                                listofpotentialistopes.append("," + String.valueOf(peaks[j][0]));
                                listofindecespotentialistopes.append("," + String.valueOf(j));
                            }
                            intensitypreviousisotope = peaks[j][1];
                        }
                        j = j + 1;
                    }
                    if (countnumberofisotopes < 2) {
                        is_isotopic_series = false;
                    }
                    if (is_isotopic_series) {
                        sum_of_squares = 0;
                        mean = 0;
                        isotopes = listofpotentialistopes.toString().split(",");
                        for (int k = 0; k < isotopes.length - 1; k++) {
                            mean = mean + Double.parseDouble(isotopes[k + 1]) - Double.parseDouble(isotopes[k]);
                            sum_of_squares = sum_of_squares
                                    + (Double.parseDouble(isotopes[k + 1]) - Double.parseDouble(isotopes[k]))
                                    * (Double.parseDouble(isotopes[k + 1]) - Double.parseDouble(isotopes[k]));
                        }
                        int N = isotopes.length - 1;
                        mean = mean / N;
                        stdev = 0;
                        numberoflines++;
                        bufferoverviewofisotopes
                                .append(String.valueOf(mean) + "," + String.valueOf(countnumberofisotopes) + ","
                                        + listofindecespotentialistopes.toString() + linefeed);
                        if (N > 2) {
                            stdev = Math.sqrt((sum_of_squares - (N * mean * mean)) / (N - 1)) / mean;
                            if (stdev < (experiment.getvariance_isotopic_distance() / 100)) {
                                mean2 = mean2 + mean;
                                sum_of_squares2 = sum_of_squares2 + (mean * mean);
                                number_of_best_isotopic_clusters++;
                            }
                        }
                    }
                }
            }
        }
        clusters.setbufferoverviewofisotopes(bufferoverviewofisotopes);
        clusters.setnumberoflines(numberoflines);
        clusters.setnumber_of_best_isotopic_clusters(number_of_best_isotopic_clusters);
        clusters.setmean2(mean2);
        clusters.setsum_of_squares2(sum_of_squares2);
        return clusters;
    }
}
