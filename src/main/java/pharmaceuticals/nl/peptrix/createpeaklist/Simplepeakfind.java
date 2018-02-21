package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.utils.SortArray;

public class Simplepeakfind {
    double threshold;
    boolean valid;
    boolean lookleft;
    int left;
    int right;
    int j;
    int count;
    double[][] peaks;
    double[] sort_intensities;
    SortArray sortarray;
    int indexquantile;
    double double_indexquantile;

    public Simplepeakfind() {
    }

    public double[][] findlocalmaxima(double deltamz, double quantilethreshold, double minimummass, double maximummass,
                                      int clusteringtechnique, Spectrum spectrum) {
        double localdeltamz = deltamz;
        threshold = 0;
        if (spectrum.intensity.length > 0) {
            sort_intensities = new double[spectrum.intensity.length];
            for (int i = 0; i <= spectrum.intensity.length - 1; i++) {
                sort_intensities[i] = spectrum.intensity[i];
            }
            sortarray = new SortArray(sort_intensities);
            double_indexquantile = (quantilethreshold * sort_intensities.length) - 1;
            indexquantile = (int) double_indexquantile;
            if (indexquantile > sort_intensities.length - 1) {
                indexquantile = sort_intensities.length - 1;
            }
            if (indexquantile < 0) {
                indexquantile = 0;
            }
            threshold = sort_intensities[indexquantile];
        }
        sort_intensities = null;
        sortarray = null;
        count = 0;
        lookleft = true;
        for (int i = 0; i <= spectrum.mass.length - 1; i++) {
            if ((spectrum.mass[i] >= minimummass) && (spectrum.mass[i] <= maximummass)
                    && (spectrum.intensity[i] > threshold)) {
                valid = true;
                if (clusteringtechnique == 1) {
                    localdeltamz = spectrum.mass[i] * deltamz / 1000000;
                }
                if (lookleft) {
                    j = 1;
                    left = i - j;
                    while ((left >= 0) && (Math.abs(spectrum.mass[i] - spectrum.mass[left])) < localdeltamz) {
                        if (spectrum.intensity[left] > spectrum.intensity[i]) {
                            valid = false;
                            break;
                        }
                        j = j + 1;
                        left = i - j;
                    }
                }
                j = 1;
                right = i + j;
                if (!lookleft) {
                    lookleft = true;
                }
                if (valid) {
                    while ((right <= (spectrum.mass.length - 1))
                            && (Math.abs(spectrum.mass[right] - spectrum.mass[i])) < localdeltamz) {
                        if (spectrum.intensity[right] > spectrum.intensity[i]) {
                            valid = false;
                            lookleft = false;
                            break;
                        }
                        j = j + 1;
                        right = i + j;
                    }
                }
                if (valid == true) {
                    count = count + 1;
                    i = right - 1;
                }
            }
        }
        if (count > 0) {
            peaks = new double[count][3];
        }
        count = 0;
        lookleft = true;
        for (int i = 0; i <= spectrum.mass.length - 1; i++) {
            if ((spectrum.mass[i] >= minimummass) && (spectrum.mass[i] <= maximummass)
                    && (spectrum.intensity[i] > threshold)) {
                valid = true;
                if (clusteringtechnique == 1) {
                    localdeltamz = spectrum.mass[i] * deltamz / 1000000;
                }
                if (lookleft) {
                    j = 1;
                    left = i - j;
                    while ((left >= 0) && (Math.abs(spectrum.mass[i] - spectrum.mass[left])) < localdeltamz) {
                        if (spectrum.intensity[left] > spectrum.intensity[i]) {
                            valid = false;
                            break;
                        }
                        j = j + 1;
                        left = i - j;
                    }
                }
                j = 1;
                right = i + j;
                if (!lookleft) {
                    lookleft = true;
                }
                if (valid) {
                    while ((right <= (spectrum.mass.length - 1))
                            && (Math.abs(spectrum.mass[right] - spectrum.mass[i])) < localdeltamz) {
                        if (spectrum.intensity[right] > spectrum.intensity[i]) {
                            valid = false;
                            lookleft = false;
                            break;
                        }
                        j = j + 1;
                        right = i + j;
                    }
                }
                if (valid == true) {
                    peaks[count][0] = spectrum.mass[i];
                    peaks[count][1] = spectrum.intensity[i];
                    peaks[count][2] = i;
                    count = count + 1;
                    i = right - 1;
                }
            }
        }
        return peaks;
    }

    public double[][] findlocalmaximaFTMS(double deltamz, double quantilethreshold, double minimummass,
                                          double maximummass, int clusteringtechnique, FTMSdata ftmsdata) {
        double localdeltamz = deltamz;
        sort_intensities = new double[ftmsdata.data.length / 2];
        for (int i = 0; i <= (ftmsdata.data.length / 2) - 1; i++) {
            sort_intensities[i] = ftmsdata.data[i];
        }
        sortarray = new SortArray(sort_intensities);
        double_indexquantile = (quantilethreshold * sort_intensities.length) - 1;
        indexquantile = (int) double_indexquantile;
        if (indexquantile > sort_intensities.length - 1) {
            indexquantile = sort_intensities.length - 1;
        }
        if (indexquantile < 0) {
            indexquantile = 0;
        }
        threshold = sort_intensities[indexquantile];
        sort_intensities = null;
        sortarray = null;
        count = 0;
        if (threshold > 0) {
            for (int i = 0; i <= (ftmsdata.data.length / 2) - 1; i++) {
                if ((ftmsdata.data[ftmsdata.data.length / 2 + i] >= minimummass)
                        && (ftmsdata.data[ftmsdata.data.length / 2 + i] <= maximummass)
                        && (ftmsdata.data[i] > threshold)) {
                    valid = true;
                    if (clusteringtechnique == 1) {
                        localdeltamz = ftmsdata.data[ftmsdata.data.length / 2 + i] * deltamz / 1000000;
                    }
                    j = 1;
                    left = i - j;
                    while ((left >= 0) && (Math.abs(ftmsdata.data[ftmsdata.data.length / 2 + i]
                            - ftmsdata.data[ftmsdata.data.length / 2 + left])) < localdeltamz) {
                        if (ftmsdata.data[left] > ftmsdata.data[i]) {
                            valid = false;
                        }
                        j = j + 1;
                        left = i - j;
                    }
                    j = 1;
                    right = i + j;
                    while ((right <= ((ftmsdata.data.length / 2) - 1))
                            && (Math.abs(ftmsdata.data[ftmsdata.data.length / 2 + right]
                            - ftmsdata.data[ftmsdata.data.length / 2 + i])) < localdeltamz) {
                        if (ftmsdata.data[right] > ftmsdata.data[i]) {
                            valid = false;
                        }
                        j = j + 1;
                        right = i + j;
                    }
                    if (valid == true) {
                        count = count + 1;
                    }
                }
            }
            if (count > 0) {
                peaks = new double[count][3];
            }
        }
        count = 0;
        if (threshold > 0) {
            for (int i = 0; i <= (ftmsdata.data.length / 2) - 1; i++) {
                if ((ftmsdata.data[ftmsdata.data.length / 2 + i] >= minimummass)
                        && (ftmsdata.data[ftmsdata.data.length / 2 + i] <= maximummass)
                        && (ftmsdata.data[i] > threshold)) {
                    valid = true;
                    if (clusteringtechnique == 1) {
                        localdeltamz = ftmsdata.data[ftmsdata.data.length / 2 + i] * deltamz / 1000000;
                    }
                    j = 1;
                    left = i - j;
                    while ((left >= 0) && (Math.abs(ftmsdata.data[ftmsdata.data.length / 2 + i]
                            - ftmsdata.data[ftmsdata.data.length / 2 + left])) < localdeltamz) {
                        if (ftmsdata.data[left] > ftmsdata.data[i]) {
                            valid = false;
                        }
                        j = j + 1;
                        left = i - j;
                    }
                    j = 1;
                    right = i + j;
                    while ((right <= ((ftmsdata.data.length / 2) - 1))
                            && (Math.abs(ftmsdata.data[ftmsdata.data.length / 2 + right]
                            - ftmsdata.data[ftmsdata.data.length / 2 + i])) < localdeltamz) {
                        if (ftmsdata.data[right] > ftmsdata.data[i]) {
                            valid = false;
                        }
                        j = j + 1;
                        right = i + j;
                    }
                    if (valid == true) {
                        peaks[count][0] = ftmsdata.data[ftmsdata.data.length / 2 + i];
                        peaks[count][1] = ftmsdata.data[i];
                        peaks[count][2] = i;
                        count = count + 1;
                    }
                }
            }
        }
        return peaks;
    }
}
