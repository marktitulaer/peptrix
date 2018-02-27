package pharmaceuticals.nl.peptrix.creatematrix;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

public class Eliminatesamples {
    Experiment experiment;
    Controller cc;
    ResultService resultService;

    public Eliminatesamples(Controller cc, Experiment experiment) {
        this.cc = cc;
        this.experiment = experiment;
        resultService = new ResultServiceImpl(cc);
    }

    public int exclude_samples_with_to_low_numbers_of_replicates() {
        int recordsaffected = 0;
        int required_number_of_replicates_for_each_sample = Integer.parseInt(experiment.getNumberofreplicatessample());
        Object[][] number_of_replicates_for_each_sample = resultService
                .determinenumberofreplicates(experiment.getExperimentid(), experiment.getquantilethreshold());
        if (number_of_replicates_for_each_sample != null) {
            if (number_of_replicates_for_each_sample.length > 0) {
                for (int i = 0; i < number_of_replicates_for_each_sample.length; i++) {
                    if (Integer.parseInt(number_of_replicates_for_each_sample[i][0]
                            .toString()) < required_number_of_replicates_for_each_sample) {
                        recordsaffected = resultService.too_low_numbers_of_replicates(experiment.getExperimentid(),
                                String.valueOf(required_number_of_replicates_for_each_sample).trim(),
                                number_of_replicates_for_each_sample[i][1].toString(),
                                experiment.getquantilethreshold());
                    }
                }
            }
        }
        return recordsaffected;
    }
}
