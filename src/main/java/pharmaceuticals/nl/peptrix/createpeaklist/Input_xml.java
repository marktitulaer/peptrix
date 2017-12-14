package pharmaceuticals.nl.peptrix.createpeaklist;

import java.io.FileDescriptor;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class Input_xml {

	FileDescriptor fd;

	ExportFileToDisk exportfiletodisk;

	String linefeed = "\n";

	String input_xmlcontent;

	boolean input_xml_file_created;

	public Input_xml(Controller cc) {
		exportfiletodisk = new ExportFileToDisk(cc);
	}

	public void create_input_xml(String default_input_xml_file_name, String mgf_file_name,
			String ms2_export_xml_filename, String input_xml_file_name, Taxonomy taxonomy) {
		input_xmlcontent = "";
		input_xml_file_created = false;
		if ((!taxonomy.get_taxonomy_file_name().trim().equalsIgnoreCase(""))
				&& (!default_input_xml_file_name.trim().equalsIgnoreCase(""))
				&& (!mgf_file_name.trim().equalsIgnoreCase(""))) {
			input_xmlcontent = "<?xml version=\"1.0\"?> " + linefeed + "	<bioml>" + linefeed + "			<note>"
					+ linefeed
					+ "			Each one of the parameters for x! tandem is entered as a labeled note node. " + linefeed
					+ "			Any of the entries in the default_input.xml file can be over-ridden by" + linefeed
					+ "			adding a corresponding entry to this file. This file represents a minimum" + linefeed
					+ "			input file, with only entries for the default settings, the output file" + linefeed
					+ "			and the input spectra file name. " + linefeed
					+ "			See the taxonomy.xml file for a description of how FASTA sequence list " + linefeed
					+ "			files are linked to a taxon name." + linefeed + "			</note>" + linefeed
					+ linefeed + "			<note type=\"input\" label=\"list path, default parameters\">"
					+ default_input_xml_file_name + "</note>" + linefeed
					+ "			<note type=\"input\" label=\"list path, taxonomy information\">"
					+ taxonomy.get_taxonomy_file_name() + "</note>" + linefeed + linefeed
					+ "			<note type=\"input\" label=\"protein, taxon\">human</note>" + linefeed + linefeed
					+ "			<note type=\"input\" label=\"spectrum, path\">" + mgf_file_name + "</note>" + linefeed
					+ linefeed + "			<note type=\"input\" label=\"output, path\">" + ms2_export_xml_filename
					+ "</note>" + linefeed + "			<note type=\"input\" label=\"output, results\">valid</note>"
					+ linefeed + "		</bioml>";
		}
		if (!input_xmlcontent.trim().equalsIgnoreCase("")) {
			input_xml_file_created = exportfiletodisk.exportcompletefilename(input_xml_file_name,
					input_xmlcontent.getBytes());
		}
	}
}
