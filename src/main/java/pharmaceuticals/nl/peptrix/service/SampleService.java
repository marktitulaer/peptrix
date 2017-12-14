package pharmaceuticals.nl.peptrix.service;

public interface SampleService {

	public Object[][] getsampledata(String sampleid);

	public Object[][] determine_number_of_samples(String strexperimentid);

	public Object[][] getnumberofsamplesinmatrix(String strexperimentid, String strquantilethreshold);

	public Object[][] getsampleswithtoolownumberofcalibrantmatches(String strexperimentid, String strquantilethreshold);

	public int insertsample(String strSample_code, String newsamplename);

	public Object[][] getmaxsampleidfromsamplecode(String strSample_code);

	public void insertsample(String filename);

}
