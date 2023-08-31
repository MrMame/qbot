package utils;

import de.mme.qbot.model.domain.Dare;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class DareImportFileFactory {


    // ImportFile Syntax --------------------------------------------------------------
    private static final String FILENAME_PREFIX_EXPORT_DARES = "qbot-dares-export";

    private static final String FILE_DARES_FIRSTROW_TEXT = "qBot-Dares Exportfile\r\n";
    private static final String CSV_HEADERNAME_DARE = "dare";
    private static final String CSV_HEADERNAME_ID = "id";
    private static final String EXPORT_DATETIMEROW_TEXT_PREFIX = "Export DateTime - ";
    private static final String COMMENT_CHARACTER = "#";
    private static final String SEPERATOR = ";";
    private static final String NEWLINE_CHARACTERS = "\r\n";

    // ImportFile TESTDATA Syntax --------------------------------------------------------------

//
//    public static void deleteTestfile(String filename){
//        Path retFile = Paths.get(filename);
//        retFile.toFile().setReadable(true);
//        retFile.toFile().setWritable(true);
//        retFile.toFile().delete();
//    }

//    public static BufferedReader createBufferedReaderFromNewEmptyTestfile(String filename){
//        Path emptyTestFile = createNewEmptyTestfile(filename);
//        BufferedReader br;
//        try {
//            br = new BufferedReader(Files.newBufferedReader(emptyTestFile));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return br;
//    }


//    public static Path createNewEmptyTestfile(String filename){
//
//        deleteTestfile(filename);
//        Path retFile = Paths.get(filename);
//
//        Set<PosixFilePermission> permissions = PosixFilePermissions
//                .fromString("rw-rw-rw-");
//        FileAttribute<Set<PosixFilePermission>> attribs = PosixFilePermissions
//                .asFileAttribute(permissions);
//
//        try {
//            Files.createFile(retFile,attribs);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return retFile;
//    }

    public static Path createNewOKTestfile(List<Dare> dares,String fileName){



        Path retFile = ImportFiles.createNewEmptyTestfile(fileName);


        // Build the File Content ___________________________________________________________
        StringBuilder fileContent = new StringBuilder();
        // -> Export Date
        fileContent.append(COMMENT_CHARACTER + FILE_DARES_FIRSTROW_TEXT);
        fileContent.append(COMMENT_CHARACTER + EXPORT_DATETIMEROW_TEXT_PREFIX);
        fileContent.append(LocalDateTime.now());
        fileContent.append(NEWLINE_CHARACTERS);

        // -> Header Row - Apending # marks Comment
        fileContent.append(COMMENT_CHARACTER);
        fileContent.append(CSV_HEADERNAME_ID + SEPERATOR);
        fileContent.append(CSV_HEADERNAME_DARE + NEWLINE_CHARACTERS);

        // -> Dares
        for(Dare d:  dares){

            String dField = "\"" + d.getId() + "\"";
            String dText = (d.getText()!=null) ? "\""+d.getText()+"\"" : "null";

            fileContent.append(dField + SEPERATOR);
            fileContent.append(dText + NEWLINE_CHARACTERS);
        }

        // Write the File ___________________________________________________________
        try {
            Files.writeString(retFile,fileContent.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return retFile;
    }


//    private static Path createNewNotAccessibleTestfile(String filename){
//        deleteTestfile(filename);
//        Path retFile = Paths.get(filename);
//
//        Set<PosixFilePermission> permissions = PosixFilePermissions
//                .fromString("rw-rw-rw-");
//        FileAttribute<Set<PosixFilePermission>> attribs = PosixFilePermissions
//                .asFileAttribute(permissions);
//
//        try {
//            Files.createFile(retFile,attribs);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        return retFile;
//    }

}
