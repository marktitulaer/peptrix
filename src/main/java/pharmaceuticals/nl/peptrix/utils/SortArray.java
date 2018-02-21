package pharmaceuticals.nl.peptrix.utils;

public class SortArray {

    public SortArray(double[] a) {
        sort(a);
    }

    private void sort(double[] a) {
        QuickSort(a, 0, a.length - 1);
    }

    private void QuickSort(double a[], int lo0, int hi0) {
        int lo = lo0;
        int hi = hi0;
        double mid;
        if (hi0 > lo0) {
            mid = a[(lo0 + hi0) / 2];
            while (lo <= hi) {
                while ((lo < hi0) && (a[lo] < mid)) {
                    ++lo;
                }
                while ((hi > lo0) && (a[hi] > mid)) {
                    --hi;
                }
                if (lo <= hi) {
                    swap(a, lo, hi);
                    ++lo;
                    --hi;
                }
            }
            if (lo0 < hi) {
                QuickSort(a, lo0, hi);
            }
            if (lo < hi0) {
                QuickSort(a, lo, hi0);
            }
        }
    }

    private void swap(double a[], int i, int j) {
        double T;
        T = a[i];
        a[i] = a[j];
        a[j] = T;
    }
}
