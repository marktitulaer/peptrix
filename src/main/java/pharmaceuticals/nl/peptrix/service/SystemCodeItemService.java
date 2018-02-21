package pharmaceuticals.nl.peptrix.service;

public interface SystemCodeItemService {
    public Object[][] getsystemcodeitem();

    public Object[][] selectenzymes();

    public Object[][] getdirectories();

    public void insertsystemdirectories();

    public Object[][] getdirectories2();

    public Object[][] filecount(String file_to_search);

    public Object[][] searchfile(String file_to_search);

    public void insertfile(String systemcode_id, String file_to_search);

    public Object[][] searchdirectories(String file_to_search);

    public void insertitemvalue(String systemcodeitemid, String itemvalue);

    public Object[][] getclibrationstandards();

    public Object[][] getclibrationmasses(String systemcodeitemid);
}
