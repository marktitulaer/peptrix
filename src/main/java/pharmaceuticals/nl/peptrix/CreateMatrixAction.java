package pharmaceuticals.nl.peptrix;

import java.awt.*;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import pharmaceuticals.nl.peptrix.gui.Creatematrix;

public class CreateMatrixAction implements TextListener {

    Controller cc;

    public TextField experimentid2;

    Creatematrix creatematrix;


    public CreateMatrixAction(Controller cc)

    {
        this.cc = cc;


    }

    public void create_matrix_screen() {
        creatematrix = new Creatematrix(cc);
    }

    public void textValueChanged(TextEvent evt) {
        cc.PerformAction(evt.getSource());
        if (evt.getSource() == experimentid2) {

            creatematrix.creatematrix_center.centerpanelsouthcreatematrix.removeAll2();
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                    .setVisible(false);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                    .setVisible(false);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.startcreatematrixpanel
                    .setVisible(false);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.peakfindinputpanel
                    .setVisible(false);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                    .setVisible(false);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                    .setVisible(false);
            creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.dummypanel.setVisible(false);
            cc.dataexperiment[3] = "";
            cc.peakfindmethod = 1;
            cc.centroidingmethod = -1;
            cc.inputerror = -1;
            cc.matrixfiles = cc.collectmatrixfiles.collectmatrixfiles(experimentid2.getText());
            cc.dataexperiment = cc.getexperimentdata.getexperimentdata(experimentid2.getText());
            cc.experimentname2.setText(cc.dataexperiment[0]);
            cc.equipmentname2.setText(cc.dataexperiment[1]);
            cc.dateexperiment2.setText(cc.dataexperiment[2]);
            boolean equipmentid_ok = false;
            try {
                int testint = Integer.parseInt(cc.dataexperiment[3]);
                if (testint > 0) {
                    equipmentid_ok = true;
                }
            } catch (Exception ex) {
            }
            if (equipmentid_ok) {
                creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.preprocessingpanel
                        .setVisible(true);
                creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.panelcalibrationinput
                        .setVisible(true);
                creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.startcreatematrixpanel
                        .setVisible(true);
                creatematrix.creatematrix_center.centerpanelsouthcreatematrix.setvisible();
                creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.peakfindinputpanel
                        .setVisible(true);
                creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ftmspanel
                        .setVisible(true);
                creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.centerpanelnorthcreatematrix.panelcombine.ms2_sequencing_panel
                        .setVisible(true);
                creatematrix.creatematrix_center.centerpanelnorthtotalcreatematrix.dummypanel.setVisible(true);
                creatematrix.fill_panels_with_fields();
                cc.setlabelsanddefaults();
                cc.setbackground(cc.colorgrey);
                cc.frame.setVisible(true);
                if (cc.combomatrixtodisplay.getItemCount() > 0) {
                    cc.combomatrixtodisplay.setSelectedIndex(0);
                }
            }
        } else {
            cc.textValueChangedZ(evt);
        }
    }
}
