package pharmaceuticals.nl.peptrix.gui.creatematrix.center;

import java.awt.Checkbox;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.SortedListModel;

public class Panel_ms2_sequencing {

	Controller cc;

	JPanel ms2_sequencing_panel;

	public Panel_ms2_sequencing(Controller cc) {
		this.cc = cc;
		ms2_sequencing_panel = new JPanel();
	}

	public void fill_panels_with_fields_() {
		ms2_sequencing_panel.removeAll();
		ms2_sequencing_panel.setBorder(BorderFactory.createTitledBorder("MS2 Sequencing"));
		GridBagLayout ms2_sequencing_panellayout = new GridBagLayout();
		ms2_sequencing_panel.setLayout(ms2_sequencing_panellayout);
		GridBagConstraints gridbagcontraintsmodifications = new GridBagConstraints();
		
		//-----line 1------------------------------------------------------------
		gridbagcontraintsmodifications.anchor = GridBagConstraints.FIRST_LINE_START;
		gridbagcontraintsmodifications.insets = new Insets(2, 2, 2, 2);
		gridbagcontraintsmodifications.gridx = 1;
		gridbagcontraintsmodifications.gridy = 1;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel question_ms2_sequencing = new JLabel("MS2 sequencing (Yes/No) ?");
		ms2_sequencing_panel.add(question_ms2_sequencing, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 2;
		gridbagcontraintsmodifications.gridy = 1;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.checkbox_ms2sequencing = new Checkbox();
		ms2_sequencing_panel.add(cc.checkbox_ms2sequencing, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 6;
		gridbagcontraintsmodifications.gridy = 1;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel label_search_engine = new JLabel("Search engine");
		ms2_sequencing_panel.add(label_search_engine, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 7;
		gridbagcontraintsmodifications.gridy = 1;
		gridbagcontraintsmodifications.gridwidth = 2;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		String[] array_searchengine = { "X!tandem" };
		cc.combo_searchengine = new JComboBox(array_searchengine);
		cc.combo_searchengine.setLightWeightPopupEnabled(false);
		ms2_sequencing_panel.add(cc.combo_searchengine, gridbagcontraintsmodifications);
		
		//-----line 2------------------------------------------------------------	
		
		gridbagcontraintsmodifications.gridx = 6;
		gridbagcontraintsmodifications.gridy = 2;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel label_fixed_mod = new JLabel("Fixed modifications");
		ms2_sequencing_panel.add(label_fixed_mod, gridbagcontraintsmodifications);
		
		gridbagcontraintsmodifications.gridx = 10;
		gridbagcontraintsmodifications.gridy = 2;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel enzymelabel = new JLabel("Enzyme");
		ms2_sequencing_panel.add(enzymelabel, gridbagcontraintsmodifications);		
		
		
		//--------line 3---------------------------------------------------------				
		gridbagcontraintsmodifications.gridx = 1;
		gridbagcontraintsmodifications.gridy = 3;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel label_tol = new JLabel("Peptide precursor tolerance");
		ms2_sequencing_panel.add(label_tol, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 2;
		gridbagcontraintsmodifications.gridy = 3;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.input_tol = new TextField(6);
		ms2_sequencing_panel.add(cc.input_tol, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 3;
		gridbagcontraintsmodifications.gridy = 3;
		gridbagcontraintsmodifications.gridwidth = 2;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		String[] array_TOLU = new String[4];
		array_TOLU[0] = "ppm";
		array_TOLU[1] = "Da";
		array_TOLU[2] = "%";
		array_TOLU[3] = "mmu";
		cc.combo_TOLU = new JComboBox(array_TOLU);
		cc.combo_TOLU.setLightWeightPopupEnabled(false);
		ms2_sequencing_panel.add(cc.combo_TOLU, gridbagcontraintsmodifications);		
		gridbagcontraintsmodifications.gridx = 6;
		gridbagcontraintsmodifications.gridy = 3;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 5;
		gridbagcontraintsmodifications.fill = GridBagConstraints.BOTH;
		cc.model_fixed_mod = new SortedListModel();
		cc.list_fixed_mod = new JList(cc.model_fixed_mod);
		JScrollPane pane_fixed_mod = new JScrollPane(cc.list_fixed_mod);
		ms2_sequencing_panel.add(pane_fixed_mod, gridbagcontraintsmodifications);		
		gridbagcontraintsmodifications.gridx = 7;
		gridbagcontraintsmodifications.gridy = 3;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.button_remove_fixed = new JButton(">");
		cc.button_remove_fixed.addActionListener(cc);
		ms2_sequencing_panel.add(cc.button_remove_fixed, gridbagcontraintsmodifications);	
		gridbagcontraintsmodifications.gridx = 8;
		gridbagcontraintsmodifications.gridy = 3;
		gridbagcontraintsmodifications.gridwidth = 2;
		gridbagcontraintsmodifications.gridheight = 11;
		gridbagcontraintsmodifications.fill = GridBagConstraints.BOTH;
		cc.model_all_modifications = new SortedListModel();
		cc.list_all_modifications = new JList(cc.model_all_modifications);
		JScrollPane pane_all_mods = new JScrollPane(cc.list_all_modifications);
		ms2_sequencing_panel.add(pane_all_mods, gridbagcontraintsmodifications);
		
		gridbagcontraintsmodifications.gridx = 10;
		gridbagcontraintsmodifications.gridy = 3;
		gridbagcontraintsmodifications.gridwidth = 2;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.comboenzymes = new JComboBox();
		cc.comboenzymes.setLightWeightPopupEnabled(false);
		ms2_sequencing_panel.add(cc.comboenzymes, gridbagcontraintsmodifications);
				
		//--------line 4---------------------------------------------------------		
		gridbagcontraintsmodifications.gridx = 1;
		gridbagcontraintsmodifications.gridy = 4;
		gridbagcontraintsmodifications.gridwidth = 2;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel label_itol = new JLabel("MS/MS fragment tolerance");
		ms2_sequencing_panel.add(label_itol, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 2;
		gridbagcontraintsmodifications.gridy = 4;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.input_itol = new TextField(6);
		ms2_sequencing_panel.add(cc.input_itol, gridbagcontraintsmodifications);
		String[] array_ITOLU = new String[4];
		array_ITOLU[0] = "ppm";
		array_ITOLU[1] = "Da";
		array_ITOLU[2] = "%";
		array_ITOLU[3] = "mmu";
		gridbagcontraintsmodifications.gridx = 3;
		gridbagcontraintsmodifications.gridy = 4;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.combo_ITOLU = new JComboBox(array_TOLU);
		cc.combo_ITOLU.setLightWeightPopupEnabled(false);
		ms2_sequencing_panel.add(cc.combo_ITOLU, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 7;
		gridbagcontraintsmodifications.gridy = 4;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.button_add_fixed = new JButton("<");
		cc.button_add_fixed.addActionListener(cc);
		ms2_sequencing_panel.add(cc.button_add_fixed, gridbagcontraintsmodifications);
			
		//--------line 5---------------------------------------------------------		
		gridbagcontraintsmodifications.gridx = 1;
		gridbagcontraintsmodifications.gridy = 5;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel taxonometrylabel = new JLabel("Taxonomy");
		ms2_sequencing_panel.add(taxonometrylabel, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 2;
		gridbagcontraintsmodifications.gridy = 5;
		gridbagcontraintsmodifications.gridwidth = 2;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		String[] array_Taxonomy = { "human" };
		cc.combo_Taxonomy = new JComboBox(array_Taxonomy);
		cc.combo_Taxonomy.setLightWeightPopupEnabled(false);
		ms2_sequencing_panel.add(cc.combo_Taxonomy, gridbagcontraintsmodifications);
		
		//--------line 6---------------------------------------------------------		
		gridbagcontraintsmodifications.gridx = 1;
		gridbagcontraintsmodifications.gridy = 6;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel databaseslabel = new JLabel("Database(s)");
		ms2_sequencing_panel.add(databaseslabel, gridbagcontraintsmodifications);
		gridbagcontraintsmodifications.gridx = 2;
		gridbagcontraintsmodifications.gridy = 6;
		gridbagcontraintsmodifications.gridwidth = 2;
		gridbagcontraintsmodifications.gridheight = 1;
		String[] array_databases = { "HUMAN.fasta" };
		cc.combo_proteindatabases = new JComboBox(array_databases);
		cc.combo_proteindatabases.setLightWeightPopupEnabled(false);
		ms2_sequencing_panel.add(cc.combo_proteindatabases, gridbagcontraintsmodifications);
					
		//--------line 7---------------------------------------------------------		
		gridbagcontraintsmodifications.gridx = 1;
		gridbagcontraintsmodifications.gridy = 7;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel only_ms2_sequenced_masses = new JLabel("Only MS2 sequenced masses in matrix");
		ms2_sequencing_panel.add(only_ms2_sequenced_masses, gridbagcontraintsmodifications);		
		gridbagcontraintsmodifications.gridx = 2;
		gridbagcontraintsmodifications.gridy = 7;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.only_ms2sequenced_masses = new Checkbox();
		ms2_sequencing_panel.add(cc.only_ms2sequenced_masses, gridbagcontraintsmodifications);
				
		//--------line 8---------------------------------------------------------				
		gridbagcontraintsmodifications.gridx = 6;
		gridbagcontraintsmodifications.gridy = 8;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		JLabel label_variable_mod = new JLabel("Variable modifications");
		ms2_sequencing_panel.add(label_variable_mod, gridbagcontraintsmodifications);
		
		//--------line 9---------------------------------------------------------						
		gridbagcontraintsmodifications.gridx = 6;
		gridbagcontraintsmodifications.gridy = 9;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 4;
		gridbagcontraintsmodifications.fill = GridBagConstraints.BOTH;
		cc.model_variabel_mod = new SortedListModel();
		cc.list_variabel_mod = new JList(cc.model_variabel_mod);
		JScrollPane pane_variabel_mod = new JScrollPane(cc.list_variabel_mod);
		ms2_sequencing_panel.add(pane_variabel_mod, gridbagcontraintsmodifications);		
		gridbagcontraintsmodifications.gridx = 7;
		gridbagcontraintsmodifications.gridy = 9;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.button_remove_variabel = new JButton(">");
		cc.button_remove_variabel.addActionListener(cc);
		ms2_sequencing_panel.add(cc.button_remove_variabel, gridbagcontraintsmodifications);
		
		//--------line 10---------------------------------------------------------								
		gridbagcontraintsmodifications.gridx = 7;
		gridbagcontraintsmodifications.gridy = 10;
		gridbagcontraintsmodifications.gridwidth = 1;
		gridbagcontraintsmodifications.gridheight = 1;
		gridbagcontraintsmodifications.fill = GridBagConstraints.NONE;
		cc.button_add_variabel = new JButton("<");
		cc.button_add_variabel.addActionListener(cc);
		ms2_sequencing_panel.add(cc.button_add_variabel, gridbagcontraintsmodifications);
		
		
		
		
	}

	public JPanel getpanel() {
		return ms2_sequencing_panel;
	}
}
