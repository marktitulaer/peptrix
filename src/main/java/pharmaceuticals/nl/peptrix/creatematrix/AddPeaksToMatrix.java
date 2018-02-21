package pharmaceuticals.nl.peptrix.creatematrix;

import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.createpeaklist.MassSpectrometryFile;

public class AddPeaksToMatrix {

    CheckMassandTimeWindow checkMassandTimeWindow;

    Checkbettermatchingothermass checkbettermatchingothermass;

    Experiment experiment;

    double deltatimecombine;

    double deltamzcombinelocal;

    double absolutedifference;

    double deltatimelocalcombine;

    double retentiontime_peaklist;

    double min_retention_time;

    double max_retention_time;

    double doublemass;

    double doubleintensity;

    boolean matchingtest;

    boolean addmasstolist;

    public AddPeaksToMatrix(Experiment experiment) {
        this.experiment = experiment;
        checkbettermatchingothermass = new Checkbettermatchingothermass(experiment);
        checkMassandTimeWindow = new CheckMassandTimeWindow(experiment);
    }

    public void combinepeaks(double[][] reference_list, boolean extra_retention_time_and_ms2_sequencing_data,
                             MassSpectrometryFile masspectrometryfile, Matrix matrix) {
        String[] massesofmasspectrometryfile = masspectrometryfile.getarraymassesoffile();
        String[] intenistiesofmasspectrometryfile = masspectrometryfile.getarrayintensitiesoffile();
        String[] arraymintimesoffile = masspectrometryfile.getarraymintimesoffile();
        String[] arraymaxtimesoffile = masspectrometryfile.getarraymaxtimesoffile();
        String[] arraytimesoffile = masspectrometryfile.getarraytimesoffile();
        deltatimecombine = experiment.gettime_window_combining_peptide_masses();
        deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine());
        for (int j = 0; j < reference_list[0].length; j++) {
            for (int k = 0; k < masspectrometryfile.getlength(); k++) {
                doublemass = -1.0;
                doubleintensity = -1.0;
                retentiontime_peaklist = -1.0;
                min_retention_time = -1;
                max_retention_time = -1;
                matchingtest = false;
                if (!massesofmasspectrometryfile[k].trim().equalsIgnoreCase("")) {
                    doublemass = Double.valueOf(massesofmasspectrometryfile[k]).doubleValue();
                }
                if (!intenistiesofmasspectrometryfile[k].trim().equalsIgnoreCase("")) {
                    doubleintensity = Double.valueOf(intenistiesofmasspectrometryfile[k]).doubleValue();
                }
                if (experiment.getHas_retentiontime()) {
                    if (!arraytimesoffile[k].trim().equalsIgnoreCase("")) {
                        retentiontime_peaklist = Double.valueOf(arraytimesoffile[k]).doubleValue();
                    }
                }
                if ((experiment.getequipmentid() == 4) && (extra_retention_time_and_ms2_sequencing_data)) {
                    if (!arraymintimesoffile[k].trim().equalsIgnoreCase("")) {
                        min_retention_time = Double.valueOf(arraymintimesoffile[k]).doubleValue();
                    }
                    if (!arraymaxtimesoffile[k].trim().equalsIgnoreCase("")) {
                        max_retention_time = Double.valueOf(arraymaxtimesoffile[k]).doubleValue();
                    }
                }
                if (experiment.getclusteringtechnique() == 1) {
                    deltamzcombinelocal = Double.parseDouble(experiment.getdelta_mz_combine()) * reference_list[0][j]
                            / 1000000;
                }
                if (experiment.getHas_retentiontime()) {
                    deltatimelocalcombine = deltatimecombine;
                    if (experiment.gettime_clustering_absolute_or_percentage() == 1) {
                        deltatimelocalcombine = deltatimecombine * reference_list[1][j] / 100;
                    }
                } else {
                    min_retention_time = -1;
                    max_retention_time = -1;
                    retentiontime_peaklist = -1;
                    reference_list[1][j] = -1;
                    reference_list[2][j] = -1;
                    reference_list[3][j] = -1;
                }


                matchingtest = checkMassandTimeWindow.checkMassandTimeWindow(doublemass, reference_list[0][j],
                        extra_retention_time_and_ms2_sequencing_data, min_retention_time, max_retention_time,
                        retentiontime_peaklist, reference_list[1][j], reference_list[2][j], reference_list[3][j]);
                if (matchingtest) {


                    absolutedifference = Math.abs(doublemass - reference_list[0][j]);
                    addmasstolist = checkbettermatchingothermass.check_better_matching_other_mass(j, reference_list,
                            deltamzcombinelocal, massesofmasspectrometryfile, k, arraytimesoffile, doublemass,
                            retentiontime_peaklist, deltatimelocalcombine, absolutedifference);
                    if (addmasstolist) {
                        if (experiment.getHas_retentiontime()) {
                            massesofmasspectrometryfile[k] = "";
                        }
                        matrix.addtotalcount(j, 1);
                        matrix.addsamplecount(j, 1);
                        matrix.addaccumulatemass(j, doublemass);
                        matrix.addsampleintensity(j, doubleintensity);
                        matrix.setboolean_addnoisepeaks(j, 0);
                        if ((j == reference_list[0].length - 1) && (k < massesofmasspectrometryfile.length - 1)) {
                            k = massesofmasspectrometryfile.length;
                        }
                        if (experiment.getHas_retentiontime()) {
                            if (j < reference_list[0].length - 1) {
                                k = -1;
                            }
                        }
                        j++;
                    }
                }
            }
        }
    }
}
