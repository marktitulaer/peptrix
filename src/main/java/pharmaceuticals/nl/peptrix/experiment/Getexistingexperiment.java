package pharmaceuticals.nl.peptrix.experiment;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.serviceimpl.ExperimentServiceImpl;

public class Getexistingexperiment {
    Controller cc;
    Experiment existingexperiment;
    ExperimentServiceImpl getexperimentdata;
    String[] dataexperiment;

    public Getexistingexperiment(Controller cc) {
        this.cc = cc;
        existingexperiment = new Experiment();
        getexperimentdata = new ExperimentServiceImpl(cc);
    }

    public Experiment getexperimentname(String str_experimentid) {
        dataexperiment = getexperimentdata.getexperimentdata(str_experimentid);
        existingexperiment.setstr_experimentname(dataexperiment[0]);
        existingexperiment.setstr_equipmentname(dataexperiment[1]);
        existingexperiment.setExperimentyear(dataexperiment[4]);
        return existingexperiment;
    }
}
