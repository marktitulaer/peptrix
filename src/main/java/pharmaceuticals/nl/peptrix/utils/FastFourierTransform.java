package pharmaceuticals.nl.peptrix.utils;

import pharmaceuticals.nl.peptrix.createpeaklist.FTMSdata;

public class FastFourierTransform {

    int n;

    int nu;

    int n2;

    int nu1;

    double tr;

    double ti;

    double p;

    double arg;

    double c;

    double s;

    private int bitrev(int j) {
        int j2;
        int j1 = j;
        int k = 0;
        for (int i = 1; i <= nu; i++) {
            j2 = j1 / 2;
            k = 2 * k + j1 - 2 * j2;
            j1 = j2;
        }
        return k;
    }

    public final void fftMag(FTMSdata xre, double[] xim) {
        n = xre.data.length;
        nu = (int) (Math.log(n) / Math.log(2));
        n2 = n / 2;
        nu1 = nu - 1;
        for (int i = 0; i < n; i++) {
            xim[i] = 0.0f;
        }
        int k = 0;
        for (int l = 1; l <= nu; l++) {
            while (k < n) {
                for (int i = 1; i <= n2; i++) {
                    p = bitrev(k >> nu1);
                    arg = 2 * (float) Math.PI * p / n;
                    c = Math.cos(arg);
                    s = Math.sin(arg);
                    tr = xre.data[k + n2] * c + xim[k + n2] * s;
                    ti = xim[k + n2] * c - xre.data[k + n2] * s;
                    xre.data[k + n2] = xre.data[k] - tr;
                    xim[k + n2] = xim[k] - ti;
                    xre.data[k] += tr;
                    xim[k] += ti;
                    k++;
                }
                k += n2;
            }
            k = 0;
            nu1--;
            n2 = n2 / 2;
        }
        k = 0;
        int r;
        while (k < n) {
            r = bitrev(k);
            if (r > k) {
                tr = xre.data[k];
                ti = xim[k];
                xre.data[k] = xre.data[r];
                xim[k] = xim[r];
                xre.data[r] = tr;
                xim[r] = ti;
            }
            k++;
        }
        xre.data[0] = (Math.sqrt(xre.data[0] * xre.data[0] + xim[0] * xim[0])) / n;
        for (int i = 1; i < n / 2; i++) {
            xre.data[i] = 2 * (float) (Math.sqrt(xre.data[i] * xre.data[i] + xim[i] * xim[i])) / n;
        }

    }
}
