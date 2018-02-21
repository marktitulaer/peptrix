package pharmaceuticals.nl.peptrix.creatematrix;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class Checkbettermatchingothermass {

    Experiment experiment;

    double valueprevious;

    double valuenext;

    int change_j;

    int increase_k;

    boolean addmasstolist;

    boolean checksmallerdistance;

    public Checkbettermatchingothermass(Experiment experiment) {
        this.experiment = experiment;
    }

    public boolean check_better_matching_other_mass(int j, double[][] doublearraycombinedpeaks,
                                                    double deltamzcombinelocal, String[] arraymassesoffile, int k, String[] arraytimesoffile, double doublemass,
                                                    double doubletime, double deltatimelocalcombine, double absolutedifference) {
        addmasstolist = true;
        if (experiment.getHas_retentiontime()) {
            checksmallerdistance = true;
            change_j = 0;
            while (checksmallerdistance) {
                checksmallerdistance = false;
                change_j++;
                if ((j + change_j) < doublearraycombinedpeaks[0].length - 1) {
                    valuenext = doublearraycombinedpeaks[0][j + change_j];
                    if (Math.abs(doublemass - valuenext) < deltamzcombinelocal) {
                        checksmallerdistance = true;
                        valuenext = doublearraycombinedpeaks[1][j + change_j];
                        if ((Math.abs(valuenext - doubletime) < Math.abs(doublearraycombinedpeaks[1][j] - doubletime))
                                && (Math.abs(valuenext - doubletime) < deltatimelocalcombine)) {
                            addmasstolist = false;
                        }
                    }
                }
            }
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
                        if (Math.abs(valuenext - doublearraycombinedpeaks[0][j]) < deltamzcombinelocal) {
                            checksmallerdistance = true;
                            valuenext = -1;
                            try {
                                valuenext = Double.valueOf(arraytimesoffile[k + increase_k]).doubleValue();
                            } catch (Exception e) {
                                ;
                            }
                            if (valuenext > 0) {
                                if ((Math.abs(doublearraycombinedpeaks[1][j] - valuenext) < Math
                                        .abs(doublearraycombinedpeaks[1][j] - doubletime))
                                        && (Math.abs(
                                        doublearraycombinedpeaks[1][j] - valuenext) < deltatimelocalcombine)) {
                                    addmasstolist = false;
                                }
                            }
                        }
                    }
                }
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
        return addmasstolist;
    }
}
