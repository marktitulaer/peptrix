package pharmaceuticals.nl.peptrix.service;

public interface ExperimentService {

	public String[] getexperimentdata(String strexperimentid);

	public String getnewexperimentid();

	public int getequipmentid(String experimentnumber);

	public int create_new_experiment(String equipmentid, String newexperimentname);

	public String getlatestexperimentid();

	public Object[][] getexperimentdata2(String experimentid);

}
