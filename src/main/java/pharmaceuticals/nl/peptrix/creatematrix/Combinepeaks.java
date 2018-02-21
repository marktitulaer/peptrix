package pharmaceuticals.nl.peptrix.creatematrix;

import javax.swing.*;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.createpeaklist.Makepeaklist;
import pharmaceuticals.nl.peptrix.gui.Progress;
import com.enterprisedt.net.ftp.*;

public class Combinepeaks {
    Controller cc;
    Experiment experiment;
    FTPClient ftp;
    Makematrix makematrix;
    Makepeaklist makepeaklist;
    Eliminatesamples eliminatesamples;

    public Combinepeaks(Controller cc, Experiment experiment) {
        this.cc = cc;
        this.experiment = experiment;
        eliminatesamples = new Eliminatesamples(cc, experiment);
        makepeaklist = new Makepeaklist(cc, experiment);
        makematrix = new Makematrix(cc, experiment);
    }

    public void combinepeaks(String calibtext, String str_aligned, Progress progress) {
        int recordsaffected = eliminatesamples.exclude_samples_with_to_low_numbers_of_replicates();
        ftp = new FTPClient();
        makepeaklist.makepeaklist(progress, ftp);
        makematrix.makematrix(str_aligned, calibtext, progress, ftp);
        try {
            ftp.quit();
        } catch (Exception e) {
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
