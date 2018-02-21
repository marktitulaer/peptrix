package pharmaceuticals.nl.peptrix.statistics.wilcoxon;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.service.ResultService;
import pharmaceuticals.nl.peptrix.serviceimpl.ResultServiceImpl;

public class Getmatrixfileswilcoxon {
    Controller cc;
    int odatamatrixfileswicoxonlength;
    String[] matrixfiles;
    String experimentyear;
    ResultService resultService;

    public Getmatrixfileswilcoxon(Controller cc) {
        this.cc = cc;
        resultService = new ResultServiceImpl(cc);
    }

    public String getyear() {
        return experimentyear;
    }

    public int getdatalength() {
        return odatamatrixfileswicoxonlength;
    }

    public String[] collectmatrixfileswilcoxon(String experimentid) {
        Object[][] odatamatrixfileswicoxon;
        experimentyear = "";
        odatamatrixfileswicoxonlength = 0;
        if (!experimentid.trim().equals("")) {
            odatamatrixfileswicoxon = resultService.collectmatrixfileswilcoxon2(experimentid);
            if (odatamatrixfileswicoxon.length > 0) {
                odatamatrixfileswicoxonlength = odatamatrixfileswicoxon.length;
                matrixfiles = new String[odatamatrixfileswicoxon.length];
                for (int i = 0; i < odatamatrixfileswicoxon.length; i++) {
                    matrixfiles[i] = (String) odatamatrixfileswicoxon[i][0];
                    experimentyear = odatamatrixfileswicoxon[i][2].toString().trim();
                }
            }
        }
        return matrixfiles;
    }
}
