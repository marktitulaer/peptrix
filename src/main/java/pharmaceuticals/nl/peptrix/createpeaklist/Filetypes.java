package pharmaceuticals.nl.peptrix.createpeaklist;

import pharmaceuticals.nl.peptrix.experiment.Experiment;

public class Filetypes {

    public Filetypes() {

    }

    public boolean can_be_processed(Experiment experiment) {
        return can_be_processed_filetypes(experiment.getequipmentid(), experiment.getfiletype().trim());
    }

    public boolean can_be_processed_filetypes(int equipmentid, String filetype) {
        boolean filetype_canbe_processed = false;
        if (filetype != null) {
            if ((equipmentid == 1) && filetype.equalsIgnoreCase("fid")) {

                filetype_canbe_processed = true;
            }
            if ((equipmentid == 2) && filetype.equalsIgnoreCase("fid")) {

                filetype_canbe_processed = true;
            }
            if ((equipmentid == 1) && filetype.equalsIgnoreCase("txt")) {
                filetype_canbe_processed = true;
            }
            if ((equipmentid == 2) && filetype.equalsIgnoreCase("txt")) {
                filetype_canbe_processed = true;
            }
            if ((equipmentid == 3) && filetype.equalsIgnoreCase("txt")) {
                filetype_canbe_processed = true;
            }
            if ((equipmentid == 3) && filetype.equalsIgnoreCase("xml")) {
                filetype_canbe_processed = true;
            }
            if ((equipmentid == 4) && filetype.equalsIgnoreCase("txt")) {
                filetype_canbe_processed = true;
            }
            if ((equipmentid == 4) && filetype.equalsIgnoreCase("raw")) {
                filetype_canbe_processed = true;
            }
            if ((equipmentid == 4) && filetype.equalsIgnoreCase("mzxml")) {
                filetype_canbe_processed = true;
            }
            if ((equipmentid == 5) && filetype.equalsIgnoreCase("mzxml")) {
                filetype_canbe_processed = true;
            }
        }
        return filetype_canbe_processed;
    }
}
