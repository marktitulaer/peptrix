package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import pharmaceuticals.nl.peptrix.Controller;

public class CenterpanelnorthCreatematrix {

    PanelPeakFind panelpeakfind;

    public PanelCombine panelcombine;

    CreateMatrixStartPanel creatematrixstartpanel;

    JPanel creatematrixcenterpanelnorth;

    public JPanel peakfindinputpanel;

    JPanel combinepanel;

    public JPanel startcreatematrixpanel;

    public CenterpanelnorthCreatematrix(Controller cc) {
        panelpeakfind = new PanelPeakFind(cc);
        peakfindinputpanel = panelpeakfind.getpanel();
        panelcombine = new PanelCombine(cc);
        combinepanel = panelcombine.returnpanel();
        creatematrixstartpanel = new CreateMatrixStartPanel(cc);
        startcreatematrixpanel = creatematrixstartpanel.getpanel();
        creatematrixcenterpanelnorth = new JPanel();
        GridBagLayout gridbagpanelnorth = new GridBagLayout();
        GridBagConstraints constraintspanelnorth = new GridBagConstraints();
        constraintspanelnorth.anchor = GridBagConstraints.FIRST_LINE_START;
        constraintspanelnorth.insets = new Insets(2, 2, 2, 2);
        creatematrixcenterpanelnorth.setLayout(gridbagpanelnorth);
        constraintspanelnorth.gridx = 1;
        constraintspanelnorth.gridy = 1;
        constraintspanelnorth.gridwidth = 1;
        constraintspanelnorth.gridheight = 1;
        gridbagpanelnorth.setConstraints(peakfindinputpanel, constraintspanelnorth);
        creatematrixcenterpanelnorth.add(peakfindinputpanel);
        constraintspanelnorth.gridx = 2;
        constraintspanelnorth.gridy = 1;
        constraintspanelnorth.gridwidth = 1;
        constraintspanelnorth.gridheight = 1;
        gridbagpanelnorth.setConstraints(combinepanel, constraintspanelnorth);
        creatematrixcenterpanelnorth.add(combinepanel);
        constraintspanelnorth.gridx = 1;
        constraintspanelnorth.gridy = 2;
        constraintspanelnorth.gridwidth = 2;
        constraintspanelnorth.gridheight = 1;
        gridbagpanelnorth.setConstraints(startcreatematrixpanel, constraintspanelnorth);
        creatematrixcenterpanelnorth.add(startcreatematrixpanel);
    }

    public JPanel getpanel() {
        return creatematrixcenterpanelnorth;
    }

    public void setBackground(Color color) {
        combinepanel.setBackground(color);
        panelcombine.setBackground(color);
        peakfindinputpanel.setBackground(color);
        startcreatematrixpanel.setBackground(color);
    }

    public void fill_panels_with_fields_() {
        panelpeakfind.fill_panels_with_fields();
        panelcombine.fill_panels_with_fields_();
        creatematrixstartpanel.fill_panels_with_fields();
    }

}
