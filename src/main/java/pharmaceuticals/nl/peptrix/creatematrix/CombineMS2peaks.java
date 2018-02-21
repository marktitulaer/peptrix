package pharmaceuticals.nl.peptrix.creatematrix;

import java.util.Vector;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class CombineMS2peaks {

    Experiment experiment;

    String[] compare_sequensing_results;

    String stringsamplecount;

    String linefeed = "\n";

    String sequencing_protein_descrition;

    double doublemass;

    double absolutedifference;

    double max_retention_time;

    double min_retention_time;

    double doubletime;

    double deltatimelocalcombine;

    boolean withintimewindow;

    double deltamzcombinelocal;

    double deltatimecombine;

    int timeclusteringtechnique;

    int ms2_vector_index;

    int highest_index_ms2_peak;

    int highest_index_matching_ms2_peak;

    boolean withinmasswindow;

    boolean massandtimewindowOK;

    boolean first;

    public CombineMS2peaks(Experiment experiment) {
        this.experiment = experiment;
    }

    public String combine_ms2_peaks(double[][] doublearraycombinedpeaks, double[][] ms2_peaks,
                                    Vector<String[]> sequensing_results_vector) {
        stringsamplecount = linefeed + " , , ,MS2_proteins";
        timeclusteringtechnique = experiment.gettime_clustering_absolute_or_percentage();
        deltatimecombine = experiment.gettime_window_combining_peptide_masses();
        deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
        highest_index_ms2_peak = 0;
        highest_index_matching_ms2_peak = 0;
        sequencing_protein_descrition = "";
        for (int i = 0; i < doublearraycombinedpeaks[0].length; i++) {
            first = true;
            sequencing_protein_descrition = "";
            for (int j = highest_index_ms2_peak; j < ms2_peaks[0].length; j++) {
                doublemass = ms2_peaks[0][j];
                if (experiment.getclusteringtechnique() == 1) {

                    deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine())
                            * doublearraycombinedpeaks[0][i] / 1000000;
                }
                absolutedifference = Math.abs(doublemass - doublearraycombinedpeaks[0][i]);
                if (absolutedifference < deltamzcombinelocal) {
                    if (first) {
                        highest_index_matching_ms2_peak = j;
                        first = false;
                    }
                }
                boolean test = checkMassandTimeWindowMS2(ms2_peaks[0][j], doublearraycombinedpeaks[0][i],
                        ms2_peaks[2][j], ms2_peaks[3][j], ms2_peaks[1][j], doublearraycombinedpeaks[1][i],
                        doublearraycombinedpeaks[2][i], doublearraycombinedpeaks[3][i]);
                if (test) {
                    ms2_vector_index = (int) ms2_peaks[4][j];
                    compare_sequensing_results = sequensing_results_vector.elementAt(ms2_vector_index);
                    sequencing_protein_descrition = compare_sequensing_results[4].trim().replaceAll(",", "_");
                }
            }
            stringsamplecount = stringsamplecount + "," + sequencing_protein_descrition;
            if (highest_index_matching_ms2_peak > highest_index_ms2_peak) {
                highest_index_ms2_peak = highest_index_matching_ms2_peak;
            }
        }
        return stringsamplecount;
    }

    public boolean checkMassandTimeWindowMS2(double mass_peaklist, double reference_mass,
                                             double minimum_retentiontime_peaklist, double maximum_retentiontime_peaklist, double retentiontime_peaklist,
                                             double reference_retentiontime, double reference_min_retentiontime, double reference_max_retentiontime) {
        massandtimewindowOK = false;
        withinmasswindow = false;
        withintimewindow = false;
        if (experiment.getclusteringtechnique() == 1) {

            deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine()) * reference_mass / 1000000;
        }
        absolutedifference = Math.abs(mass_peaklist - reference_mass);
        if (absolutedifference < deltamzcombinelocal) {
            withinmasswindow = true;
        }
        if (withinmasswindow) {
            deltatimelocalcombine = deltatimecombine;
            if (timeclusteringtechnique == 1) {
                deltatimelocalcombine = deltatimecombine * reference_retentiontime / 100;
            }
            if (Math.abs(reference_retentiontime - retentiontime_peaklist) < deltatimelocalcombine) {
                withintimewindow = true;
            } else if ((minimum_retentiontime_peaklist < reference_min_retentiontime)
                    && (maximum_retentiontime_peaklist > reference_min_retentiontime)) {
                withintimewindow = true;
            } else if ((minimum_retentiontime_peaklist < reference_max_retentiontime)
                    && (maximum_retentiontime_peaklist > reference_max_retentiontime)) {
                withintimewindow = true;
            } else if ((minimum_retentiontime_peaklist > reference_min_retentiontime)
                    && (maximum_retentiontime_peaklist < reference_max_retentiontime)) {
                withintimewindow = true;
            }
        }
        massandtimewindowOK = (withinmasswindow && withintimewindow);
        return massandtimewindowOK;
    }
}
