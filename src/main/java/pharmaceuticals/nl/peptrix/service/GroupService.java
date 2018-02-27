package pharmaceuticals.nl.peptrix.service;

public interface GroupService {

	public Object[][] getgroupdata(String groupid);

	public Object[][] selectgroup(String strGroup_code, String strexperimentid);

	public int insertgroup(String strGroup_code, String strGroup_name);

	public String getmaxgroupid(String strGroup_code);

}
