package pharmaceuticals.nl.peptrix.gui.filechooser.filters;

import java.io.File;

public class TandemFilter extends SuffixAwareFilter {

    String suffix;

    String searchname = "tandem.exe";

    boolean tandem_in_name = false;

    @Override
    public boolean accept(File f) {
        suffix = getSuffix(f);
        tandem_in_name = false;
        if (f.getName().trim().toLowerCase().indexOf(searchname.toLowerCase()) == 0) {
            tandem_in_name = true;
        }
        if (suffix != null) {
            return super.accept(f) || tandem_in_name;
        } else {
            return super.accept(f);
        }
    }

    @Override
    public String getDescription() {
        return searchname;
    }
}
