package pharmaceuticals.nl.peptrix.fileimport;

import java.io.File;
import java.util.Vector;

import pharmaceuticals.nl.peptrix.gui.Progress;

public class ScanFilesandDirs {
    Vector<File[]> filevector;
    AddOnlyFiles addfiles;
    Directories directories;

    public ScanFilesandDirs(Progress progress) {
        filevector = new Vector<File[]>();
        addfiles = new AddOnlyFiles(progress);
        directories = new Directories();
    }

    public Vector<File[]> search_files_in_directories(File[] name) {
        boolean show_progress = true;
        filevector = addfiles.add_only_files_to_filevector(filevector, name, show_progress);
        filevector = directories.search_for_sub_directories(this, addfiles, name, filevector);
        return filevector;
    }
}
