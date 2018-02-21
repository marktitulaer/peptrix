package pharmaceuticals.nl.peptrix.gui.filechooser.filters;

import java.io.File;

public class RawFilter extends SuffixAwareFilter {
    @Override
    public boolean accept(File f) {
        String suffix = getSuffix(f);
        if (suffix != null) {
            return super.accept(f) || suffix.equals("raw");
        } else {
            return super.accept(f);
        }
    }

    @Override
    public String getDescription() {
        return "Thermo Raw Files(*.raw)";
    }
}
