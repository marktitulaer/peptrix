package pharmaceuticals.nl.peptrix.createpeaklist.ms2sequencing;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

import pharmaceuticals.nl.peptrix.Controller;
import pharmaceuticals.nl.peptrix.export.ExportFileToDisk;

public class Taxonomy {
    String program_directory;
    String taxonomy_file_name;
    String linefeed = "\n";
    String taxonomycontent;
    FileDescriptor fd;
    String taxonomy_label_for_file;
    public boolean taxonomy_file_created;
    TaxonomyFasta taxonomyfasta;

    public Taxonomy(String found_ms2_database, String string_Taxonomy, Controller cc,
                    ExportFileToDisk exportfiletodisk) {
        taxonomy_label_for_file = "human";
        taxonomyfasta = new TaxonomyFasta(cc, exportfiletodisk);
        String taxonomyFastaFileName = "";
        if (string_Taxonomy.trim().equalsIgnoreCase("homo sapiens")) {
            taxonomy_label_for_file = "human";
            taxonomyFastaFileName = taxonomyfasta.generateTaxonomyFastaFile(found_ms2_database, string_Taxonomy);
        }
        taxonomy_file_created = false;
        taxonomy_file_name = "";
        program_directory = "";
        taxonomycontent = "";
        try {
            taxonomy_file_name = cc.userhome + cc.fileSeparator + "taxonomy.xml";
            if ((!string_Taxonomy.trim().equalsIgnoreCase("")) && (!found_ms2_database.trim().equalsIgnoreCase(""))) {
                taxonomycontent = "<?xml version=\"1.0\"?>" + linefeed
                        + "<bioml label=\"x! taxon-to-file matching list\">" + linefeed + "<taxon label=\""
                        + taxonomy_label_for_file.trim() + "\">" + linefeed + "<file format=\"peptide\" URL=\""
                        + taxonomyFastaFileName.trim() + "\" />" + linefeed + "</taxon>" + linefeed + "</bioml>";
            }
            if (!taxonomycontent.trim().equalsIgnoreCase("")) {
                try {
                    FileOutputStream os = new FileOutputStream(taxonomy_file_name);
                    fd = os.getFD();
                    byte[] data = taxonomycontent.getBytes();
                    os.write(data);
                    os.flush();
                    fd.sync();
                    os.close();
                    taxonomy_file_created = true;
                } catch (IOException e) {
                    taxonomy_file_created = false;
                }
            }
        } catch (Exception ex) {
        }
    }

    public String get_taxonomy_file_name() {
        return taxonomy_file_name;
    }
}
