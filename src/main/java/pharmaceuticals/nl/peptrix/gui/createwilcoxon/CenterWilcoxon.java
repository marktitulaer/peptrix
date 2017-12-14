package pharmaceuticals.nl.peptrix.gui.createwilcoxon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.ScrollPane;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.gui.createwilcoxon.center.Inputpanelwilcoxon;
import pharmaceuticals.nl.peptrix.gui.createwilcoxon.center.Panelpvalues;

public class CenterWilcoxon {

	ScrollPane wilcoxoncenter;

	JPanel wilcoxoncenterpanelsouth;

	JPanel wilcoxoncenterpanelnorth;

	JPanel dummypanel;

	JPanel wilcoxoninputpanel;

	public Inputpanelwilcoxon inputpanelwilcoxon;

	public Panelpvalues panelpvalues;

	public CenterWilcoxon(Controller cc) {
		GridBagLayout gridbagpanelnorth = new GridBagLayout();
		GridBagConstraints constraintspanelnorth = new GridBagConstraints();
		constraintspanelnorth.anchor = GridBagConstraints.FIRST_LINE_START;
		constraintspanelnorth.insets = new Insets(2, 2, 2, 2);
		constraintspanelnorth.gridx = 1;
		constraintspanelnorth.gridy = 1;
		constraintspanelnorth.gridwidth = 1;
		constraintspanelnorth.gridheight = 1;
		wilcoxoncenterpanelnorth = new JPanel();
		inputpanelwilcoxon = new Inputpanelwilcoxon(cc);
		wilcoxoninputpanel = inputpanelwilcoxon.getpanel();
		gridbagpanelnorth.setConstraints(wilcoxoninputpanel, constraintspanelnorth);
		wilcoxoncenterpanelnorth.add(wilcoxoninputpanel);
		constraintspanelnorth.gridx = 1;
		constraintspanelnorth.gridy = 2;
		constraintspanelnorth.gridwidth = 1;
		constraintspanelnorth.gridheight = 1;
		panelpvalues = new Panelpvalues(cc);
		JPanel pvaluespanel = panelpvalues.getpanel();
		gridbagpanelnorth.setConstraints(pvaluespanel, constraintspanelnorth);
		wilcoxoncenterpanelnorth.add(pvaluespanel);
		wilcoxoncenterpanelnorth.setLayout(gridbagpanelnorth);
		JPanel centerpanelnorthtotal = new JPanel();
		centerpanelnorthtotal.setLayout(new BorderLayout());
		centerpanelnorthtotal.add(wilcoxoncenterpanelnorth, BorderLayout.WEST);
		dummypanel = new JPanel();
		centerpanelnorthtotal.add(dummypanel, BorderLayout.CENTER);
		JPanel wilcoxoncenterpanel = new JPanel();
		wilcoxoncenterpanel.setLayout(new BorderLayout());
		wilcoxoncenterpanel.add(centerpanelnorthtotal, BorderLayout.NORTH);
		wilcoxoncenterpanelsouth = new JPanel();
		wilcoxoncenterpanel.add(wilcoxoncenterpanelsouth, BorderLayout.CENTER);
		wilcoxoncenter = new ScrollPane();
		wilcoxoncenter.add(wilcoxoncenterpanel);
	}

	public JPanel getwilcoxoninputpanel() {
		return wilcoxoninputpanel;
	}

	public ScrollPane returnScrollPane() {
		return wilcoxoncenter;
	}

	public void setBackground(Color color) {
		panelpvalues.setBackground(color);
		inputpanelwilcoxon.setBackground(color);
		wilcoxoncenterpanelsouth.setBackground(color);
		dummypanel.setBackground(color);
		wilcoxoncenterpanelnorth.setBackground(color);
	}

	public void removeAll_from_p_plot_panel() {
		panelpvalues.removeAll_from_p_plot_panel();
	}

	public void removeAll_from_p_values_panel() {
		panelpvalues.removeAll_from_p_values_panel();
	}

	public void p_plot_panel_add_image(JLabel icon_label) {
		panelpvalues.p_plot_panel.add(icon_label);
	}

	public void displayPvalues(JTable Pvaluestable) {
		panelpvalues.displayPvalues(Pvaluestable);
	}
}
