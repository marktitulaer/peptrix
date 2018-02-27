package pharmaceuticals.nl.peptrix.fileimport;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ExperimentService;
import pharmaceuticals.nl.peptrix.serviceimpl.ExperimentServiceImpl;

public class Createnewexperiment {
    Controller cc;
    Object[][] odataexperiment;
    String latestexperimentid;
    String sqlstring;
    String strquery;
    int numberofrows;
    ExperimentService experimentService;

    public Createnewexperiment(Controller cc) {
        this.cc = cc;
        experimentService = new ExperimentServiceImpl(cc);
    }

    public String createnewexperiment(String newexperimentname, String equipmentid) {
        cc.actualtime.resettime();
        numberofrows = experimentService.create_new_experiment(equipmentid, newexperimentname);
        latestexperimentid = experimentService.getlatestexperimentid();
        return latestexperimentid;
    }
}
