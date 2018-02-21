package pharmaceuticals.nl.peptrix.createpeaklist;

public class Theoreticaldistribution {
    double[][] coefficients_of_ratios_no_sulphur_atoms = {
            {-0.00142320578040, 0.06258138406507, 0.03092092306220, -0.02490747037406, -0.19423148776489,
                    0.04574408690798},
            {0.53158267080224, 0.24252967352808, 0.22353930450345, 0.26363266501679, 0.45952477474223,
                    -0.05092121193598},
            {0.00572776591574, 0.01729736525102, -0.02630395501009, -0.07330346656184, -0.18163820209523,
                    0.13874539944789},
            {-0.00040226083326, -0.00427641490976, 0.00728183023772, 0.01876886839392, 0.04173579115885,
                    -0.04344815868749},
            {-0.00007968737684, 0.00038011211412, -0.00073155573939, -0.00176688757979, -0.00355426505742,
                    0.00449747222180}};
    double[][] coefficients_of_ratios_one_sulphur_atom = {
            {-0.01040584267474, 0.37339166598255, 0.06969331604484, 0.04462649178239, -0.20727547407753,
                    0.27169670700251},
            {0.53121149663696, -0.15814640001919, 0.28154425636993, 0.23204790123388, 0.53536509500863,
                    -0.37192045082925},
            {0.00576913817747, 0.24085046064819, -0.08121643989151, -0.06083969521863, -0.22521649838170,
                    0.31939855191976},
            {-0.00039325152252, -0.06068695741919, 0.02372741957255, 0.01564282892512, 0.05180965157326,
                    -0.08668833166842},
            {-0.00007954180489, 0.00563606634601, -0.00238998426027, -0.00145145206815, -0.00439750995163,
                    0.00822975581940}};
    double[][] coefficients_of_ratios_two_sulphur_atoms = {
            {-0.01937823810470, 0.68496829280011, 0.04215807391059, 0.14015578207913, -0.02549241716294,
                    -0.14490868030324},
            {0.53084210514216, -0.54558176102022, 0.40434195078925, 0.14407679007180, 0.32153542852101,
                    0.33629928307361},
            {0.00580573751882, 0.44926662609767, -0.15884974959493, -0.01310480312503, -0.11409513283836,
                    -0.08223564735018},
            {-0.00038281138203, -0.11154849560657, 0.04319968814535, 0.00362292256563, 0.02617210469576,
                    0.01023410734015},
            {-0.00007958217070, 0.01023294598884, -0.00413693825139, -0.00034189078786, -0.00221816103608,
                    -0.00027717589598}};

    public Theoreticaldistribution() {
    }

    public int returnnumberofcoefficients() {
        return coefficients_of_ratios_no_sulphur_atoms[0].length;
    }

    public double theoretical_distributions_no_sulphur(double mass, int rationumber, int chargestate) {
        double[] ratio = new double[coefficients_of_ratios_no_sulphur_atoms[0].length];
        mass = (chargestate * mass) - ((chargestate - 1) * 1.00728);
        mass = mass / 1000;
        for (int k = 0; k < coefficients_of_ratios_no_sulphur_atoms[0].length; k++) {
            ratio[k] = 0;
        }
        for (int k = 0; k < coefficients_of_ratios_no_sulphur_atoms[0].length; k++) {
            for (int j = 0; j < coefficients_of_ratios_no_sulphur_atoms.length; j++) {
                Math.pow(mass, k);
                ratio[k] = ratio[k] + (coefficients_of_ratios_no_sulphur_atoms[j][k] * Math.pow(mass, j));
            }
        }
        return ratio[rationumber];
    }

    public double theoretical_distributions_one_sulphur(double mass, int rationumber, int chargestate) {
        double[] ratio = new double[coefficients_of_ratios_one_sulphur_atom[0].length];
        mass = (chargestate * mass) - ((chargestate - 1) * 1.00728);
        mass = mass / 1000;
        for (int k = 0; k < coefficients_of_ratios_one_sulphur_atom[0].length; k++) {
            ratio[k] = 0;
        }
        for (int k = 0; k < coefficients_of_ratios_one_sulphur_atom[0].length; k++) {
            for (int j = 0; j < coefficients_of_ratios_one_sulphur_atom.length; j++) {
                Math.pow(mass, k);
                ratio[k] = ratio[k] + (coefficients_of_ratios_one_sulphur_atom[j][k] * Math.pow(mass, j));
            }
        }
        return ratio[rationumber];
    }

    public double theoretical_distributions_two_sulphur(double mass, int rationumber, int chargestate) {
        double[] ratio = new double[coefficients_of_ratios_two_sulphur_atoms[0].length];
        mass = (chargestate * mass) - ((chargestate - 1) * 1.00728);
        mass = mass / 1000;
        for (int k = 0; k < coefficients_of_ratios_two_sulphur_atoms[0].length; k++) {
            ratio[k] = 0;
        }
        for (int k = 0; k < coefficients_of_ratios_two_sulphur_atoms[0].length; k++) {
            for (int j = 0; j < coefficients_of_ratios_two_sulphur_atoms.length; j++) {
                Math.pow(mass, k);
                ratio[k] = ratio[k] + (coefficients_of_ratios_two_sulphur_atoms[j][k] * Math.pow(mass, j));
            }
        }
        return ratio[rationumber];
    }
}
