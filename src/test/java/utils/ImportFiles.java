package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

public class ImportFiles {


    public static void deleteTestfile(String filename){
        Path retFile = Paths.get(filename);
        retFile.toFile().setReadable(true);
        retFile.toFile().setWritable(true);
        retFile.toFile().delete();
    }

    public static BufferedReader createBufferedReaderFromNewEmptyTestfile(String filename){
        Path emptyTestFile = createNewEmptyTestfile(filename);
        BufferedReader br;
        try {
            br = new BufferedReader(Files.newBufferedReader(emptyTestFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return br;
    }

    public static Path createNewEmptyTestfile(String filename){

        deleteTestfile(filename);
        Path retFile = Paths.get(filename);

        Set<PosixFilePermission> permissions = PosixFilePermissions
                .fromString("rw-rw-rw-");
        FileAttribute<Set<PosixFilePermission>> attribs = PosixFilePermissions
                .asFileAttribute(permissions);

        try {
            Files.createFile(retFile,attribs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return retFile;
    }

    private static Path createNewNotAccessibleTestfile(String filename){
        deleteTestfile(filename);
        Path retFile = Paths.get(filename);

        Set<PosixFilePermission> permissions = PosixFilePermissions
                .fromString("rw-rw-rw-");
        FileAttribute<Set<PosixFilePermission>> attribs = PosixFilePermissions
                .asFileAttribute(permissions);

        try {
            Files.createFile(retFile,attribs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return retFile;
    }

}
