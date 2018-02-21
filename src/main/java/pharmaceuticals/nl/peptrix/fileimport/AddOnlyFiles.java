package pharmaceuticals.nl.peptrix.fileimport;

import java.io.File;
import java.util.Vector;

import pharmaceuticals.nl.peptrix.gui.Progress;

public class AddOnlyFiles {
    Progress progress;

    public AddOnlyFiles(Progress progress) {
        this.progress = progress;
    }

    public Vector<File[]> add_only_files_to_filevector(Vector<File[]> filevector, File[] files_and_dirs,
                                                       boolean show_progress) {
        filevector = fill_filevector(show_progress, files_and_dirs, filevector);
        return filevector;
    }

    private Vector<File[]> fill_filevector(boolean show_progress, File[] files_and_dirs, Vector<File[]> filevector) {
        int countnumberoffiles = determine_number_of_files_in_directory(show_progress, files_and_dirs);
        File[] onlyfiles = new File[countnumberoffiles];
        int index = 0;
        for (int i = 0; i <= (files_and_dirs.length - 1); i++) {
            if (show_progress) {
                progress.setnumberandtext(i, "Collecting files... " + files_and_dirs[i].getAbsolutePath());
            }
            if (files_and_dirs[i].isFile()) {
                onlyfiles[index] = files_and_dirs[i];
                index++;
            }
        }
        filevector.add(onlyfiles);
        return filevector;
    }

    private int determine_number_of_files_in_directory(boolean show_progress, File[] files_and_dirs) {
        initialyze_progress(show_progress, files_and_dirs);
        int countnumberoffiles = 0;
        for (int i = 0; i <= (files_and_dirs.length - 1); i++) {
            if (files_and_dirs[i].isFile()) {
                countnumberoffiles++;
            }
        }
        return countnumberoffiles;
    }

    private void initialyze_progress(boolean show_progress, File[] files_and_dirs) {
        if (show_progress) {
            progress.init("Collecting files... ", 70);
            progress.setmaximum(files_and_dirs.length - 1);
        }
    }
}
