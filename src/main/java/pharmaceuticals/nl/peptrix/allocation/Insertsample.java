package pharmaceuticals.nl.peptrix.allocation;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.service.SampleService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;
import pharmaceuticals.nl.peptrix.serviceimpl.SampleServiceImpl;

public class Insertsample {

    Controller cc;

    Object[][] odatasample;

    String newsamplename;

    ResultService resultService;

    ResultServiceImpl resultServiceImpl;

    SampleService sampleService;

    public Insertsample(Controller cc) {
        this.cc = cc;
        resultService = new ResultServiceImpl(cc);
        sampleService = new SampleServiceImpl(cc);
    }

    public String insertsample(String strSample_code, String strSample_name, String strexperimentid) {
        String newsampleid = "";
        odatasample = resultService.get_sampleid_from_samplecode_in_experiment(strSample_code, strexperimentid);
        if (odatasample.length > 0) {
            newsampleid = odatasample[0][0].toString().trim();
        } else {
            newsamplename = strSample_name.trim();
            if (newsamplename.equalsIgnoreCase("")) {
                newsamplename = "sample " + strSample_code.trim();
            }
            cc.updatesample = sampleService.insertsample(strSample_code, newsamplename);
            if (cc.updatesample > 0) {
                odatasample = sampleService.getmaxsampleidfromsamplecode(strSample_code);
                if (odatasample.length > 0) {
                    newsampleid = odatasample[0][0].toString().trim();
                }
            }
        }
        return newsampleid;
    }
}
