package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

public class InsertresultrecordWilcoxon {

    Controller cc;

    int updatesample;

    ResultService resultService;

    public InsertresultrecordWilcoxon(Controller cc) {
        this.cc = cc;
        resultService = new ResultServiceImpl(cc);
    }

    public void insertresultrecordwilcoxon(String strexperimentid, String strtype, String exportname) {
        updatesample = -1;
        updatesample = resultService.deleteresultrecordwilcoxon(strexperimentid, strtype, exportname);
        updatesample = resultService.insertresultrecordwilcoxon(cc.tempBD, strexperimentid, strtype, exportname);
    }
}
