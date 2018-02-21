package pharmaceuticals.nl.peptrix.gui.filechooser.filters;

import java.io.File;

public class SuffixAwareFilter extends javax.swing.filechooser.FileFilter {

    String s;

    String suffix;

    int i;

    public String getSuffix(File f) {
        s = f.getPath();
        suffix = null;
        i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            suffix = s.substring(i + 1).toLowerCase();
        }
        if (i <= 0 && (f.isFile() == true) && ((f.getName().trim().equalsIgnoreCase("fid") == false))) {
            suffix = "leeg";
        }
        if (f.isFile() && ((f.getName().trim().equalsIgnoreCase("fid")))) {
            suffix = "fid";
        }
        if (f.isFile() && ((f.getName().trim().equalsIgnoreCase("acqu")))) {
            suffix = "acqu";
        }
        if (f.isFile() && ((f.getName().trim().equalsIgnoreCase("acqus")))) {
            suffix = "acqus";
        }
        return suffix;
    }

    @Override
    public boolean accept(File f) {
        return f.isDirectory();
    }

    @Override
    public String getDescription() {

        return null;
    }
}
