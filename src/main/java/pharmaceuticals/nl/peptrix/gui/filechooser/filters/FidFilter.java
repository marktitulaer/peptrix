package pharmaceuticals.nl.peptrix.gui.filechooser.filters;

import java.io.File;

public class FidFilter extends SuffixAwareFilter {
    String suffix;
    boolean fid_in_name = false;

    @Override
    public boolean accept(File f) {
        suffix = getSuffix(f);
        fid_in_name = false;
        if (f.getName().trim().length() > 3) {
            if (f.getName().trim().substring(f.getName().trim().length() - 3, f.getName().trim().length())
                    .equalsIgnoreCase("fid")) {
                fid_in_name = true;
            }
        }
        if (suffix != null) {
            return super.accept(f) || suffix.equals("fid") || (fid_in_name && suffix.equals("leeg"));
        } else {
            return super.accept(f);
        }
    }

    @Override
    public String getDescription() {
        return "Fid Files (fid)";
    }
}
