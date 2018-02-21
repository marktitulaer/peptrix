package pharmaceuticals.nl.peptrix.gui.filechooser.filters;

import java.io.File;

public class mzXMLFilter extends SuffixAwareFilter {
    @Override
    public boolean accept(File f) {
        String suffix = getSuffix(f);
        if (suffix != null) {
            return super.accept(f) || suffix.equals("mzxml");
        } else {
            return super.accept(f);
        }

    }

    @Override
    public String getDescription() {
        return "mzXML Files(*.mzxml)";
    }
}
