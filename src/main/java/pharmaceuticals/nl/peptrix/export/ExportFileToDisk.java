package pharmaceuticals.nl.peptrix.export;

import java.io.*;

import javax.swing.JOptionPane;

import pharmaceuticals.nl.peptrix.Controller;

public class ExportFileToDisk {

    Controller cc;

    FileOutputStream os;

    FileDescriptor fd;

    boolean exportsucceeded;

    public ExportFileToDisk(Controller cc) {
        this.cc = cc;
    }

    public boolean exportfile_and_directory(String directory, String filename, byte[] data) {
        String completefilename = directory + cc.fileSeparator + filename;
        return exportcompletefilename(completefilename, data);
    }

    public boolean exportcompletefilename(String completefilename, byte[] data) {
        exportsucceeded = true;
        try {
            open_complete_file(completefilename);
            append_data_to_file(data);
            close_file();
            exportsucceeded = true;
        } catch (IOException e) {
            exportsucceeded = false;
            if (cc.debugmode) {
                e.printStackTrace();
            } else {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        try {
            close_file();
        } catch (Exception e) {
        }
        return exportsucceeded;
    }

    public void open_file(String directory, String filename) throws IOException {
        String completefilename = directory + cc.fileSeparator + filename;
        open_complete_file(completefilename);
    }

    public void open_complete_file(String completefilename) throws IOException {
        os = new FileOutputStream(completefilename);
        fd = os.getFD();
    }

    public void append_data_to_file(byte[] data) throws IOException {
        os.write(data);
        os.flush();
    }

    public void close_file() throws IOException {
        fd.sync();
        os.close();
    }

}
