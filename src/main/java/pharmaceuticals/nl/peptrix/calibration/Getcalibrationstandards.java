package pharmaceuticals.nl.peptrix.calibration;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.SystemCodeItemService;
import pharmaceuticals.nl.peptrix.serviceimpl.SystemCodeItemServiceImpl;

public class Getcalibrationstandards {
    Controller cc;
    Object[][] odatacalibrationmasses;
    String[] strcalibrationstandards;
    SystemCodeItemService systemCodeItemService;

    public Getcalibrationstandards(Controller cc) {
        this.cc = cc;
        systemCodeItemService = new SystemCodeItemServiceImpl(cc);
    }

    public String[] collectcalibrationstandards() {
        odatacalibrationmasses = systemCodeItemService.getclibrationstandards();
        if (odatacalibrationmasses.length > 0) {
            strcalibrationstandards = new String[odatacalibrationmasses.length];
            for (int i = 0; i < odatacalibrationmasses.length; i++) {
                strcalibrationstandards[i] = (String) odatacalibrationmasses[i][1];
            }
        } else {
            strcalibrationstandards = new String[1];
            strcalibrationstandards[0] = "";
        }
        return strcalibrationstandards;
    }

    public String getcodeitemid(int i) {
        return (String) odatacalibrationmasses[i][0];
    }
}
