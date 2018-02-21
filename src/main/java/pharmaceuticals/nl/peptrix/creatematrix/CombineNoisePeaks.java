package pharmaceuticals.nl.peptrix.creatematrix;

import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.createpeaklist.MassSpectrometryFile;

public class CombineNoisePeaks {
    double valueprevious;
    double valuenext;
    double min_retention_time;
    double max_retention_time;
    double doublemass;
    double doubleintensity;
    double absolutedifference;
    double doubletime;
    double deltatimelocalcombine;
    double deltamzcombinelocal;
    double deltatimecombine;
    int increase_k;
    int increase_j;
    int timeclusteringtechnique;
    boolean withintimewindow;
    boolean checksmallerdistance;
    boolean addmasstolist;

    public CombineNoisePeaks() {
    }

    public void combinenoisepeaks(double[][] doublearraycombinedpeaks, Experiment experiment, Matrix matrix,
                                  MassSpectrometryFile masspectrometryfile, boolean min_and_max_retentiontime_present) {
        String[] arraymintimesoffile = masspectrometryfile.getarraymintimesoffile();
        String[] arraymassesoffile = masspectrometryfile.getarraymassesoffile();
        String[] arrayintenistiesoffile = masspectrometryfile.getarrayintensitiesoffile();
        String[] arraytimesoffile = masspectrometryfile.getarraytimesoffile();
        String[] arraymaxtimesoffile = masspectrometryfile.getarraymaxtimesoffile();
        double[] boolean_addnoisepeaks = matrix.getboolean_addnoisepeaks2();
        timeclusteringtechnique = experiment.gettime_clustering_absolute_or_percentage();
        deltatimecombine = experiment.gettime_window_combining_peptide_masses();
        deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
        for (int j = 0; j < doublearraycombinedpeaks[0].length; j++) {
            if (boolean_addnoisepeaks[j] == 1) {
                for (int k = 0; k < arraymassesoffile.length; k++) {
                    if ((experiment.getequipmentid() == 4) && min_and_max_retentiontime_present) {
                        if (arraymintimesoffile[k].trim().equalsIgnoreCase("")) {
                            min_retention_time = -1.0;
                        } else {
                            min_retention_time = Double.valueOf(arraymintimesoffile[k]).doubleValue();
                        }
                        if (arraymaxtimesoffile[k].trim().equalsIgnoreCase("")) {
                            max_retention_time = -1.0;
                        } else {
                            max_retention_time = Double.valueOf(arraymaxtimesoffile[k]).doubleValue();
                        }
                    }
                    if (arraymassesoffile[k].trim().equalsIgnoreCase("")) {
                        doublemass = -1.0;
                    } else {
                        doublemass = Double.valueOf(arraymassesoffile[k]).doubleValue();
                    }
                    if (arrayintenistiesoffile[k].trim().equalsIgnoreCase("")) {
                        doubleintensity = -1.0;
                    } else {
                        doubleintensity = Double.valueOf(arrayintenistiesoffile[k]).doubleValue();
                    }
                    absolutedifference = Math.abs(doublemass - doublearraycombinedpeaks[0][j]);
                    if (experiment.getclusteringtechnique() == 1) {
                        deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine())
                                * doublearraycombinedpeaks[0][j] / 1000000;
                    }
                    if (absolutedifference < deltamzcombinelocal) {
                        addmasstolist = true;
                        if (experiment.getequipmentid() == 4) {
                            if (arraytimesoffile[k].trim().equalsIgnoreCase("")) {
                                doubletime = -1.0;
                            } else {
                                doubletime = Double.valueOf(arraytimesoffile[k]).doubleValue();
                            }
                            deltatimelocalcombine = deltatimecombine;
                            if (timeclusteringtechnique == 1) {
                                deltatimelocalcombine = deltatimecombine * doubletime / 100;
                            }
                            withintimewindow = false;
                            if (Math.abs(doublearraycombinedpeaks[1][j] - doubletime) < deltatimelocalcombine) {
                                withintimewindow = true;
                            } else {
                                if ((experiment.getequipmentid() == 4) && min_and_max_retentiontime_present
                                        && (doublearraycombinedpeaks[2][j] > -1)
                                        && (doublearraycombinedpeaks[3][j] > -1)) {
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
                                    if (min_retention_time > -1) {
                                        if (Math.abs(min_retention_time
                                                - doublearraycombinedpeaks[1][j]) < deltatimelocalcombine) {
                                            withintimewindow = true;
                                        }
                                    }
                                    if (max_retention_time > -1) {
                                        if (Math.abs(max_retention_time
                                                - doublearraycombinedpeaks[1][j]) < deltatimelocalcombine) {
                                            withintimewindow = true;
                                        }
                                    }
                                    if (doublearraycombinedpeaks[2][j] > -1) {
                                        if (Math.abs(
                                                doublearraycombinedpeaks[2][j] - doubletime) < deltatimelocalcombine) {
                                            withintimewindow = true;
                                        }
                                    }
                                    if (doublearraycombinedpeaks[3][j] > -1) {
                                        if (Math.abs(
                                                doublearraycombinedpeaks[3][j] - doubletime) < deltatimelocalcombine) {
                                            withintimewindow = true;
                                        }
                                    }
                                }
                            }
                            if (withintimewindow) {
                                addmasstolist = true;
                                checksmallerdistance = true;
                                increase_k = 0;
                                while (checksmallerdistance) {
                                    checksmallerdistance = false;
                                    increase_k++;
                                    if ((k + increase_k) < arraymassesoffile.length - 1) {
                                        valuenext = -1;
                                        try {
                                            valuenext = Double.valueOf(arraymassesoffile[k + increase_k]).doubleValue();
                                        } catch (Exception e) {
                                            checksmallerdistance = true;
                                        }
                                        if (valuenext > 0) {
                                            if (Math.abs(
                                                    valuenext - doublearraycombinedpeaks[0][j]) < deltamzcombinelocal) {
                                                checksmallerdistance = true;
                                                valuenext = -1;
                                                try {
                                                    valuenext = Double.valueOf(arraytimesoffile[k + increase_k])
                                                            .doubleValue();
                                                } catch (Exception e) {
                                                    ;
                                                }
                                                if (valuenext > 0) {
                                                    if ((Math.abs(doublearraycombinedpeaks[1][j] - valuenext) < Math
                                                            .abs(doublearraycombinedpeaks[1][j] - doubletime))
                                                            && (Math.abs(doublearraycombinedpeaks[1][j]
                                                            - valuenext) < deltatimelocalcombine)) {
                                                        addmasstolist = false;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (addmasstolist) {
                                    checksmallerdistance = true;
                                    increase_j = 0;
                                    while (checksmallerdistance) {
                                        checksmallerdistance = false;
                                        increase_j++;
                                        if ((j + increase_j) < doublearraycombinedpeaks[0].length - 1) {
                                            valuenext = -1;
                                            try {
                                                valuenext = doublearraycombinedpeaks[0][j + increase_j];
                                            } catch (Exception e) {
                                                checksmallerdistance = true;
                                            }
                                            if (valuenext > 0) {
                                                if (Math.abs(valuenext - doublemass) < deltamzcombinelocal) {
                                                    checksmallerdistance = true;
                                                    valuenext = -1;
                                                    try {
                                                        valuenext = doublearraycombinedpeaks[1][j + increase_j];
                                                    } catch (Exception e) {
                                                        ;
                                                    }
                                                    if (valuenext > 0) {
                                                        if ((Math.abs(valuenext - doubletime) < Math
                                                                .abs(doublearraycombinedpeaks[1][j] - doubletime))
                                                                && (Math.abs(valuenext
                                                                - doubletime) < deltatimelocalcombine)) {
                                                            addmasstolist = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (addmasstolist) {
                                    checksmallerdistance = true;
                                    increase_k = 0;
                                    while (checksmallerdistance) {
                                        checksmallerdistance = false;
                                        increase_k--;
                                        if ((k + increase_k) > 0) {
                                            valueprevious = -1;
                                            try {
                                                valueprevious = Double.valueOf(arraymassesoffile[k + increase_k])
                                                        .doubleValue();
                                            } catch (Exception e) {
                                                checksmallerdistance = true;
                                            }
                                            if (valueprevious > 0) {
                                                if (Math.abs(valueprevious
                                                        - doublearraycombinedpeaks[0][j]) < deltamzcombinelocal) {
                                                    checksmallerdistance = true;
                                                    valueprevious = -1;
                                                    try {
                                                        valueprevious = Double.valueOf(arraytimesoffile[k + increase_k])
                                                                .doubleValue();
                                                    } catch (Exception e) {
                                                        ;
                                                    }
                                                    if (valueprevious > 0) {
                                                        if ((Math.abs(doublearraycombinedpeaks[1][j]
                                                                - valueprevious) < Math.abs(
                                                                doublearraycombinedpeaks[1][j] - doubletime))
                                                                && (Math.abs(doublearraycombinedpeaks[1][j]
                                                                - valueprevious) < deltatimelocalcombine)) {
                                                            addmasstolist = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                if (addmasstolist) {
                                    checksmallerdistance = true;
                                    increase_j = 0;
                                    while (checksmallerdistance) {
                                        checksmallerdistance = false;
                                        increase_j--;
                                        if ((j + increase_j) > 0) {
                                            valueprevious = -1;
                                            try {
                                                valueprevious = doublearraycombinedpeaks[0][j + increase_j];
                                            } catch (Exception e) {
                                                checksmallerdistance = true;
                                            }
                                            if (valueprevious > 0) {
                                                if (Math.abs(valueprevious - doublemass) < deltamzcombinelocal) {
                                                    checksmallerdistance = true;
                                                    valueprevious = -1;
                                                    try {
                                                        valueprevious = doublearraycombinedpeaks[1][j + increase_j];
                                                    } catch (Exception e) {
                                                        ;
                                                    }
                                                    if (valueprevious > 0) {
                                                        if ((Math.abs(valueprevious - doubletime) < Math
                                                                .abs(doublearraycombinedpeaks[1][j] - doubletime))
                                                                && (Math.abs(valueprevious
                                                                - doubletime) < deltatimelocalcombine)) {
                                                            addmasstolist = false;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                addmasstolist = false;
                            }
                        } else {
                            if (j > 0) {
                                valueprevious = doublearraycombinedpeaks[0][j - 1];
                                if (Math.abs(doublemass - valueprevious) < absolutedifference) {
                                    addmasstolist = false;
                                }
                            }
                            if (j < doublearraycombinedpeaks[0].length - 1) {
                                valuenext = doublearraycombinedpeaks[0][j + 1];
                                if (Math.abs(doublemass - valuenext) < absolutedifference) {
                                    addmasstolist = false;
                                }
                            }
                        }
                        if (addmasstolist) {
                            if (boolean_addnoisepeaks[j] == 1) {
                                matrix.samplenoisecount[j] = matrix.samplenoisecount[j] + 1;
                                matrix.samplenoiseintensity[j] = matrix.samplenoiseintensity[j] + doubleintensity;
                                matrix.addtotalcount(j, 1);
                                matrix.addaccumulatemass(j, doublemass);
                            }
                            if ((j == doublearraycombinedpeaks[0].length - 1) && (k < arraymassesoffile.length - 1)) {
                                k = arraymassesoffile.length;
                            }
                            boolean_addnoisepeaks[j] = 0;
                            j++;
                        }
                    }
                }
            }
        }
    }
}
