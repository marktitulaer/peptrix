package pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.experiment.Experiment;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class Default_input_xml {

    Controller cc;

    Experiment experiment;

    ExportFileToDisk exportfiletodisk;

    public boolean default_input_xml_file_created;

    public String default_input_xml_file_name;

    public Default_input_xml(Controller cc, Experiment experiment, ExportFileToDisk exportfiletodisk) {
        this.experiment = experiment;
        this.cc = cc;
        this.exportfiletodisk = exportfiletodisk;
    }

    public void create_default_input_xml(String found_xtandem_program, String program, Taxonomy taxonomy) {
        String local_TOLU = "";
        String local_ITOLU = "";
        String[] array_variabel_modifications = experiment.getvariabel_peptide_modifications();
        String[] array_fixed_modifications = experiment.getfixed_peptide_modifications();
        String TOL = experiment.getTOL();
        String TOLU = experiment.getTOLU();
        String ITOL = experiment.getITOL();
        String ITOLU = experiment.getITOLU();
        String fixed_modifications = "";
        if (array_fixed_modifications != null) {
            if (array_fixed_modifications.length >= 1) {
                for (int i = 0; i < array_fixed_modifications.length; i++) {
                    if (!array_fixed_modifications[i].trim().equalsIgnoreCase("")) {
                        if (!fixed_modifications.trim().equalsIgnoreCase("")) {
                            fixed_modifications = fixed_modifications + ",";
                        }
                        if (array_fixed_modifications[i].trim().equalsIgnoreCase("Carbamidomethyl (C)")) {
                            fixed_modifications = fixed_modifications + "+57.022@C";
                        }
                        if (array_fixed_modifications[i].trim().equalsIgnoreCase("Oxidation (M)")) {
                            fixed_modifications = fixed_modifications + "+15.9994@M";
                        }
                        if (array_fixed_modifications[i].trim().equalsIgnoreCase("Oxidation (M)")) {
                            fixed_modifications = fixed_modifications + "+15.9994@M";
                        }
                        if (array_fixed_modifications[i].trim().equalsIgnoreCase("Phosphorylation (S)")) {
                            fixed_modifications = fixed_modifications + "+79.9663@S,+97.9769@S";
                        }
                        if (array_fixed_modifications[i].trim().equalsIgnoreCase("Phosphorylation (T)")) {
                            fixed_modifications = fixed_modifications + "+79.9663@T,+97.9769@T";
                        }
                        if (array_fixed_modifications[i].trim().equalsIgnoreCase("Phosphorylation (Y)")) {
                            fixed_modifications = fixed_modifications + "+79.9663@Y,+97.9769@Y";
                        }
                    }
                }
            }
        }
        String variabel_modifications = "";
        if (array_variabel_modifications != null) {
            if (array_variabel_modifications.length >= 1) {
                for (int i = 0; i < array_variabel_modifications.length; i++) {
                    if (!array_variabel_modifications[i].trim().equalsIgnoreCase("")) {
                        if (!variabel_modifications.trim().equalsIgnoreCase("")) {
                            variabel_modifications = variabel_modifications + ",";
                        }
                        if (array_variabel_modifications[i].trim().equalsIgnoreCase("Carbamidomethyl (C)")) {
                            variabel_modifications = variabel_modifications + "+57.022@C";
                        }
                        if (array_variabel_modifications[i].trim().equalsIgnoreCase("Oxidation (M)")) {
                            variabel_modifications = variabel_modifications + "+15.9994@M";
                        }
                        if (array_variabel_modifications[i].trim().equalsIgnoreCase("Phosphorylation (S)")) {
                            variabel_modifications = variabel_modifications + "+79.9663@S,+97.9769@S";
                        }
                        if (array_variabel_modifications[i].trim().equalsIgnoreCase("Phosphorylation (T)")) {
                            variabel_modifications = variabel_modifications + "+79.9663@T,+97.9769@T";
                        }
                        if (array_variabel_modifications[i].trim().equalsIgnoreCase("Phosphorylation (Y)")) {
                            variabel_modifications = variabel_modifications + "+79.9663@Y,+97.9769@Y";
                        }
                    }
                }
            }
        }
        default_input_xml_file_created = false;
        default_input_xml_file_name = "";
        String program_directory = "";
        String default_input_xmlcontent = "";
        if (TOLU.trim().equalsIgnoreCase("ppm")) {
            local_TOLU = "ppm";
        }
        if (TOLU.trim().equalsIgnoreCase("Da")) {
            local_TOLU = "Daltons";
        }
        if (ITOLU.trim().equalsIgnoreCase("ppm")) {
            local_ITOLU = "ppm";
        }
        if (ITOLU.trim().equalsIgnoreCase("Da")) {
            local_ITOLU = "Daltons";
        }
        try {
            try {
                program_directory = found_xtandem_program.substring(0, found_xtandem_program.indexOf(program));
            } catch (Exception ex2) {
            }
            if (!program_directory.trim().equalsIgnoreCase("")) {
                default_input_xml_file_name = cc.userhome + cc.fileSeparator + "default_input.xml";
                if ((!TOL.equalsIgnoreCase("")) && (!TOLU.equalsIgnoreCase("")) && (!ITOL.equalsIgnoreCase(""))
                        && (!ITOLU.equalsIgnoreCase(""))) {
                    default_input_xmlcontent = "<?xml version=\"1.0\"?>" + cc.linefeed
                            + "<?xml-stylesheet type=\"text/xsl\" href=\"tandem-input-style.xsl\"?>" + cc.linefeed
                            + "<bioml>" + cc.linefeed + "<note>list path parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"list path, default parameters\">"
                            + default_input_xml_file_name.trim() + "</note>" + cc.linefeed
                            + "		<note>This value is ignored when it is present in the default parameter"
                            + cc.linefeed + "		list path.</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"list path, taxonomy information\">"
                            + taxonomy.get_taxonomy_file_name().trim() + "</note>" + cc.linefeed + "" + cc.linefeed
                            + "<note>spectrum parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, fragment monoisotopic mass error\">" + ITOL
                            + "</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, parent monoisotopic mass error plus\">" + TOL
                            + "</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, parent monoisotopic mass error minus\">" + TOL
                            + "</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, parent monoisotopic mass isotope error\">yes</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, fragment monoisotopic mass error units\">"
                            + local_ITOLU + "</note>" + cc.linefeed
                            + "	<note>The value for this parameter may be 'Daltons' or 'ppm': all other values are ignored</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, parent monoisotopic mass error units\">"
                            + local_TOLU + "</note>" + cc.linefeed
                            + "		<note>The value for this parameter may be 'Daltons' or 'ppm': all other values are ignored</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, fragment mass type\">monoisotopic</note>"
                            + cc.linefeed + "		<note>values are monoisotopic|average </note>" + cc.linefeed + ""
                            + cc.linefeed + "<note>spectrum conditioning parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, dynamic range\">100.0</note>" + cc.linefeed
                            + "		<note>The peaks read in are normalized so that the most intense peak" + cc.linefeed
                            + "		is set to the dynamic range value. All peaks with values of less that" + cc.linefeed
                            + "		1, using this normalization, are not used. This normalization has the" + cc.linefeed
                            + "		overall effect of setting a threshold value for peak intensities.</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"spectrum, total peaks\">50</note> "
                            + cc.linefeed
                            + "		<note>If this value is 0, it is ignored. If it is greater than zero (lets say 50),"
                            + cc.linefeed
                            + "		then the number of peaks in the spectrum with be limited to the 50 most intense"
                            + cc.linefeed
                            + "		peaks in the spectrum. X! tandem does not do any peak finding: it only"
                            + cc.linefeed
                            + "		limits the peaks used by this parameter, and the dynamic range parameter.</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"spectrum, maximum parent charge\">4</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, use noise suppression\">yes</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, minimum parent m+h\">500.0</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, minimum fragment mz\">150.0</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, minimum peaks\">15</note> " + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, threads\">1</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"spectrum, sequence batch size\">1000</note>" + cc.linefeed
                            + "	" + cc.linefeed + "<note>residue modification parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"residue, modification mass\">" + fixed_modifications
                            + "</note>" + cc.linefeed
                            + "		<note>The format of this parameter is m@X, where m is the modfication" + cc.linefeed
                            + "		mass in Daltons and X is the appropriate residue to modify. Lists of" + cc.linefeed
                            + "		modifications are separated by commas. For example, to modify M and C" + cc.linefeed
                            + "		with the addition of 16.0 Daltons, the parameter line would be" + cc.linefeed
                            + "		+16.0@M,+16.0@C" + cc.linefeed + "		Positive and negative values are allowed."
                            + cc.linefeed + "		</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"residue, potential modification mass\">"
                            + variabel_modifications + "</note>" + cc.linefeed
                            + "		<note>The format of this parameter is the same as the format" + cc.linefeed
                            + "		for residue, modification mass (see above).</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"residue, potential modification motif\"></note>"
                            + cc.linefeed
                            + "		<note>The format of this parameter is similar to residue, modification mass,"
                            + cc.linefeed
                            + "		with the addition of a modified PROSITE notation sequence motif specification."
                            + cc.linefeed + "		For example, a value of 80@[ST!]PX[KR] indicates a modification"
                            + cc.linefeed
                            + "		of either S or T when followed by P, and residue and the a K or an R." + cc.linefeed
                            + "		A value of 204@N!{P}[ST]{P} indicates a modification of N by 204, if it"
                            + cc.linefeed
                            + "		is NOT followed by a P, then either an S or a T, NOT followed by a P." + cc.linefeed
                            + "		Positive and negative values are allowed." + cc.linefeed + "		</note>"
                            + cc.linefeed + "" + cc.linefeed + "<note>protein parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"protein, taxon\">other mammals</note>" + cc.linefeed
                            + "		<note>This value is interpreted using the information in taxonomy.xml.</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"protein, cleavage site\">[RK]|{P}</note>"
                            + cc.linefeed
                            + "		<note>this setting corresponds to the enzyme trypsin. The first characters"
                            + cc.linefeed
                            + "		in brackets represent residues N-terminal to the bond - the '|' pipe -"
                            + cc.linefeed
                            + "		and the second set of characters represent residues C-terminal to the" + cc.linefeed
                            + "		bond. The characters must be in square brackets (denoting that only" + cc.linefeed
                            + "		these residues are allowed for a cleavage) or french brackets (denoting"
                            + cc.linefeed
                            + "		that these residues cannot be in that position). Use UPPERCASE characters."
                            + cc.linefeed + "		To denote cleavage at any residue, use [X]|[X] and reset the "
                            + cc.linefeed
                            + "		scoring, maximum missed cleavage site parameter (see below) to something like 50."
                            + cc.linefeed + "		</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"protein, modified residue mass file\"></note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"protein, cleavage C-terminal mass change\">+17.002735</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"protein, cleavage N-terminal mass change\">+1.007825</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"protein, N-terminal residue modification mass\">0.0</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"protein, C-terminal residue modification mass\">0.0</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"protein, homolog management\">no</note>"
                            + cc.linefeed
                            + "		<note>if yes, an upper limit is set on the number of homologues kept for a particular spectrum</note>"
                            + cc.linefeed + "" + cc.linefeed + "<note>model refinement parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"refine\">yes</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, modification mass\"></note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, sequence path\"></note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, tic percent\">20</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, spectrum synthesis\">yes</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, maximum valid expectation value\">0.1</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, potential N-terminus modifications\">+42.010565@[</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, potential C-terminus modifications\"></note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, unanticipated cleavage\">yes</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, potential modification mass\"></note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"refine, point mutations\">no</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, use potential modifications for full refinement\">no</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"refine, point mutations\">no</note>"
                            + cc.linefeed
                            + "	<note type=\"input\" label=\"refine, potential modification motif\"></note>"
                            + cc.linefeed
                            + "	<note>The format of this parameter is similar to residue, modification mass,"
                            + cc.linefeed
                            + "		with the addition of a modified PROSITE notation sequence motif specification."
                            + cc.linefeed + "		For example, a value of 80@[ST!]PX[KR] indicates a modification"
                            + cc.linefeed
                            + "		of either S or T when followed by P, and residue and the a K or an R." + cc.linefeed
                            + "		A value of 204@N!{P}[ST]{P} indicates a modification of N by 204, if it"
                            + cc.linefeed
                            + "		is NOT followed by a P, then either an S or a T, NOT followed by a P." + cc.linefeed
                            + "		Positive and negative values are allowed." + cc.linefeed + "		</note>"
                            + cc.linefeed + "" + cc.linefeed + "<note>scoring parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, minimum ion count\">4</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, maximum missed cleavage sites\">1</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"scoring, x ions\">no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, y ions\">yes</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, z ions\">no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, a ions\">no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, b ions\">yes</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, c ions\">no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"scoring, cyclic permutation\">no</note>" + cc.linefeed
                            + "		<note>if yes, cyclic peptide sequence permutation is used to pad the scoring histograms</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"scoring, include reverse\">no</note>"
                            + cc.linefeed
                            + "		<note>if yes, then reversed sequences are searched at the same time as forward sequences</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"scoring, cyclic permutation\">no</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"scoring, include reverse\">no</note>"
                            + cc.linefeed + "" + cc.linefeed + "<note>output parameters</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, log path\"></note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, message\">testing 1 2 3</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, one sequence copy\">no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, sequence path\"></note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, path\">output.xml</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, sort results by\">protein</note>" + cc.linefeed
                            + "		<note>values = protein|spectrum (spectrum is the default)</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, path hashing\">yes</note>" + cc.linefeed
                            + "		<note>values = yes|no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, xsl path\">tandem-style.xsl</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, parameters\">yes</note>" + cc.linefeed
                            + "		<note>values = yes|no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, performance\">yes</note>" + cc.linefeed
                            + "		<note>values = yes|no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, spectra\">yes</note>" + cc.linefeed
                            + "		<note>values = yes|no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, histograms\">yes</note>" + cc.linefeed
                            + "		<note>values = yes|no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, proteins\">yes</note>" + cc.linefeed
                            + "		<note>values = yes|no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, sequences\">yes</note>" + cc.linefeed
                            + "		<note>values = yes|no</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, one sequence copy\">no</note>" + cc.linefeed
                            + "		<note>values = yes|no, set to yes to produce only one copy of each protein sequence in the output xml</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"output, results\">valid</note>"
                            + cc.linefeed + "		<note>values = all|valid|stochastic</note>" + cc.linefeed
                            + "	<note type=\"input\" label=\"output, maximum valid expectation value\">0.1</note>"
                            + cc.linefeed
                            + "		<note>value is used in the valid|stochastic setting of output, results</note>"
                            + cc.linefeed + "	<note type=\"input\" label=\"output, histogram column width\">30</note>"
                            + cc.linefeed
                            + "		<note>values any integer greater than 0. Setting this to '1' makes cutting and pasting histograms"
                            + cc.linefeed + "		into spread sheet programs easier.</note>" + cc.linefeed
                            + "<note type=\"description\">ADDITIONAL EXPLANATIONS</note>" + cc.linefeed
                            + "	<note type=\"description\">Each one of the parameters for X! tandem is entered as a labeled note"
                            + cc.linefeed + "			node. In the current version of X!, keep those note nodes"
                            + cc.linefeed + "			on a single line." + cc.linefeed + "	</note>" + cc.linefeed
                            + "	<note type=\"description\">The presence of the type 'input' is necessary if a note is to be considered"
                            + cc.linefeed + "			an input parameter." + cc.linefeed + "	</note>" + cc.linefeed
                            + "	<note type=\"description\">Any of the parameters that are paths to files may require alteration for a "
                            + cc.linefeed
                            + "			particular installation. Full path names usually cause the least trouble,"
                            + cc.linefeed
                            + "			but there is no reason not to use relative path names, if that is the"
                            + cc.linefeed + "			most convenient." + cc.linefeed + "	</note>" + cc.linefeed
                            + "	<note type=\"description\">Any parameter values set in the 'list path, default parameters' file are"
                            + cc.linefeed
                            + "			reset by entries in the normal input file, if they are present. Otherwise,"
                            + cc.linefeed + "			the default set is used." + cc.linefeed + "	</note>"
                            + cc.linefeed
                            + "	<note type=\"description\">The 'list path, taxonomy information' file must exist."
                            + cc.linefeed + "		</note>" + cc.linefeed
                            + "	<note type=\"description\">The directory containing the 'output, path' file must exist: it will not be created."
                            + cc.linefeed + "		</note>" + cc.linefeed
                            + "	<note type=\"description\">The 'output, xsl path' is optional: it is only of use if a good XSLT style sheet exists."
                            + cc.linefeed + "		</note>" + cc.linefeed + "" + cc.linefeed + "</bioml>";
                }
            }
            if (!default_input_xmlcontent.trim().equalsIgnoreCase("")) {
                default_input_xml_file_created = exportfiletodisk.exportcompletefilename(default_input_xml_file_name,
                        default_input_xmlcontent.getBytes());
            }
        } catch (Exception ex) {
        }
    }
}
