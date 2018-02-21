package pharmaceuticals.nl.peptrix.fileimport;

import java.io.File;
import java.util.Vector;

public class Directories {
    Vector<File[]> directoryvector;
    File[] filesindir;
    File[] onlyfiles;
    File[] allsubdirectories;
    File[] filesanddirs_in_dir;
    File[] directories_in_dir;
    int newindexdirectories;
    int countnumberoffiles;
    int newindexsubdirectories;
    int count_of_dirs_in_dir;
    int total_number_of_subdirectories;

    public Directories() {
    }

    public Vector<File[]> search_for_sub_directories(ScanFilesandDirs addfilestofilevector, AddOnlyFiles addfiles,
                                                     File[] filesanddirs, Vector<File[]> filevector) {
        filesanddirs_in_dir = null;
        directories_in_dir = null;
        directoryvector = new Vector<File[]>();
        for (int i = 0; i <= (filesanddirs.length - 1); i++) {
            if (filesanddirs[i].isDirectory()) {
                boolean show_progress = false;
                filevector = addfiles.add_only_files_to_filevector(filevector, filesanddirs[i].listFiles(),
                        show_progress);
                filesanddirs_in_dir = filesanddirs[i].listFiles();
                count_of_dirs_in_dir = 0;
                for (int k = 0; k < filesanddirs_in_dir.length; k++) {
                    if (filesanddirs_in_dir[k].isDirectory()) {
                        count_of_dirs_in_dir++;
                    }
                }
                directories_in_dir = new File[count_of_dirs_in_dir];
                newindexdirectories = 0;
                for (int k = 0; k < filesanddirs_in_dir.length; k++) {
                    if (filesanddirs_in_dir[k].isDirectory()) {
                        directories_in_dir[newindexdirectories] = filesanddirs_in_dir[k];
                        newindexdirectories++;
                    }
                }
                directoryvector.add(directories_in_dir);
            }
        }
        allsubdirectories = null;
        total_number_of_subdirectories = 0;
        for (int k = 0; k <= (directoryvector.size() - 1); k++) {
            File testfile[];
            testfile = directoryvector.get(k);
            total_number_of_subdirectories = (total_number_of_subdirectories + testfile.length);
        }
        allsubdirectories = new File[total_number_of_subdirectories];
        newindexsubdirectories = 0;
        for (int k = 0; k <= (directoryvector.size() - 1); k++) {
            File testfile[];
            testfile = directoryvector.get(k);
            for (int l = 0; l <= (testfile.length - 1); l++) {
                allsubdirectories[newindexsubdirectories] = testfile[l];
                newindexsubdirectories++;
            }
        }
        if (allsubdirectories.length > 0) {
            addfilestofilevector.search_files_in_directories(allsubdirectories);
        }
        return filevector;
    }
}
