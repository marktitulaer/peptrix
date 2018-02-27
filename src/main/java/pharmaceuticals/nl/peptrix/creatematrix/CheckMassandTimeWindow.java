package pharmaceuticals.nl.peptrix.creatematrix;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class CheckMassandTimeWindow {
    Experiment experiment;
    double deltamzcombinelocal;
    double absolutedifference;
    double deltatimecombine;
    double deltatimelocalcombine;
    boolean massandtimewindowOK;
    boolean withintimewindowtest;
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
            deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine()) * reference_mass / 1000000;
        }
        absolutedifference = Math.abs(mass_peaklist - reference_mass);
        if (absolutedifference < deltamzcombinelocal) {
            withinmasswindow = true;
            withintimewindow = true;
            if (experiment.getHas_retentiontime()) {
                withintimewindow = false;
                withintimewindowtest = false;
                deltatimelocalcombine = deltatimecombine;
                if (timeclusteringtechnique == 1) {
                    deltatimelocalcombine = deltatimecombine * reference_retentiontime / 100;
                }
                if (Math.abs(reference_retentiontime - retentiontime_peaklist) < deltatimelocalcombine) {
                    withintimewindow = true;
                } else {
                    if ((experiment.getequipmentid() == 4) && (extra_retention_time_and_ms2_sequencing_data)
                            && (reference_min_retentiontime > -1) && (reference_max_retentiontime > -1)) {
                        if ((minimum_retentiontime_peaklist <= reference_min_retentiontime)
                                && (maximum_retentiontime_peaklist >= reference_min_retentiontime)) {
                            withintimewindow = true;
                        }
                        if ((minimum_retentiontime_peaklist <= reference_max_retentiontime)
                                && (maximum_retentiontime_peaklist >= reference_max_retentiontime)) {
                            withintimewindow = true;
                        }
                        if ((minimum_retentiontime_peaklist >= reference_min_retentiontime)
                                && (maximum_retentiontime_peaklist <= reference_max_retentiontime)) {
                            withintimewindow = true;
                        }
                        if ((maximum_retentiontime_peaklist >= reference_min_retentiontime)
                                && (reference_max_retentiontime >= minimum_retentiontime_peaklist)) {
                            withintimewindowtest = true;
                        }
                        if (withintimewindowtest != withintimewindow) {
                            System.out.println(" verschil withintimewindowtest " + withintimewindowtest
                                    + " withintimewindow  " + withintimewindow);
                            System.out.println("   reference_min_retentiontime " + reference_min_retentiontime
                                    + " reference_max_retentiontime  " + reference_max_retentiontime);
                            System.out.println("   minimum_retentiontime_peaklist " + minimum_retentiontime_peaklist
                                    + " maximum_retentiontime_peaklist  " + maximum_retentiontime_peaklist);
                        }
                        if (minimum_retentiontime_peaklist > -1) {
                            if (Math.abs(
                                    minimum_retentiontime_peaklist - reference_retentiontime) < deltatimelocalcombine) {
                                withintimewindow = true;
                            }
                        }
                        if (maximum_retentiontime_peaklist > -1) {
                            if (Math.abs(
                                    maximum_retentiontime_peaklist - reference_retentiontime) < deltatimelocalcombine) {
                                withintimewindow = true;
                            }
                        }
                        if (reference_min_retentiontime > -1) {
                            if (Math.abs(
                                    reference_min_retentiontime - retentiontime_peaklist) < deltatimelocalcombine) {
                                withintimewindow = true;
                            }
                        }
                        if (reference_max_retentiontime > -1) {
                            if (Math.abs(
                                    reference_max_retentiontime - retentiontime_peaklist) < deltatimelocalcombine) {
                                withintimewindow = true;
                            }
                        }
                    }
                }
            }
        }
        massandtimewindowOK = (withinmasswindow && withintimewindow);
        return massandtimewindowOK;
    }
}
