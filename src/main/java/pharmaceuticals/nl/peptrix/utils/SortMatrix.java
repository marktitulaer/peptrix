package pharmaceuticals.nl.peptrix.utils;

public class SortMatrix {
    private double[] A;
    int row;
    double[][] matrix;

    public SortMatrix(double[][] a, int row) {
        this.row = row;
        matrix = a;
        A = a[row];
        sort(A);
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
        for (int k = 0; k <= matrix.length - 1; k++) {
            T = matrix[k][i];
            matrix[k][i] = matrix[k][j];
            matrix[k][j] = T;
        }
    }
}
