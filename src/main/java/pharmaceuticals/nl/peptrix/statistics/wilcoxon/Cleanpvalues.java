package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

public class Cleanpvalues {

	public Cleanpvalues() {
	}

	public double[][] clean_p_values(double[][] doublePvalues) {
		double[][] cleanedPalues = new double[doublePvalues.length][doublePvalues[0].length];
		int p = 0;
		for (int i = 0; i < doublePvalues[0].length; i++) {
			if (!(String.valueOf(doublePvalues[1][i]).trim().equalsIgnoreCase("nan"))) {
				cleanedPalues[0][p] = doublePvalues[0][i];
				cleanedPalues[1][p] = doublePvalues[1][i];
				cleanedPalues[2][p] = doublePvalues[2][i];
				cleanedPalues[3][p] = doublePvalues[3][i];
				p++;
			} else {
				cleanedPalues[0][p] = doublePvalues[0][i];
				cleanedPalues[1][p] = 10;
				cleanedPalues[2][p] = doublePvalues[2][i];
				cleanedPalues[3][p] = doublePvalues[3][i];
				p++;
			}
		}
		return cleanedPalues;
	}
}
