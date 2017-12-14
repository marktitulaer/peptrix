package pharmaceuticals.nl.peptrix.createpeaklist;

public class Spectrum {

	double[] mass;

	double[] intensity;

	public Spectrum(int intnumberofmeasurements) {
		mass = new double[intnumberofmeasurements];
		intensity = new double[intnumberofmeasurements];
	}
}
