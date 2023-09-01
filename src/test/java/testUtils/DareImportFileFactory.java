package testUtils;

import de.mme.qbot.model.domain.Dare;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

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

}
