package utils;

import de.mme.qbot.helper.discord.ImportExportFiles;
import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class QuestionImportFileFactory {


    // ImportFile Syntax --------------------------------------------------------------
    private static final String FILENAME_PREFIX_EXPORT_QUESTIONS = "qbot-questions-export";
    private static final String FILENAME_PREFIX_EXPORT_DARES = "qbot-dares-export";

    private static final String FILE_QUESTIONS_FIRSTROW_TEXT = "qBot-Questions Exportfile\r\n";
    private static final String FILE_DARES_FIRSTROW_TEXT = "qBot-Dares Exportfile\r\n";
    private static final String CSV_HEADERNAME_DARES = "dare";
    private static final String CSV_HEADERNAME_QUESTION = "question";
    private static final String CSV_HEADERNAME_ANSWER_A = "answer-a";
    private static final String CSV_HEADERNAME_ANSWER_B = "answer-b";
    private static final String CSV_HEADERNAME_ANSWER_C = "answer-c";
    private static final String CSV_HEADERNAME_ANSWER_D = "answer-d";
    private static final String CSV_HEADERNAME_ANSWER_E = "answer-e";
    private static final String CSV_HEADERNAME_ID = "id";
    private static final String EXPORT_DATETIMEROW_TEXT_PREFIX = "Export DateTime - ";
    private static final String COMMENT_CHARACTER = "#";
    private static final String SEPERATOR = ";";
    private static final String NEWLINE_CHARACTERS = "\r\n";

    // ImportFile TESTDATA Syntax --------------------------------------------------------------


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

    public static Path createNewOKTestfile(List<Question> questions,String fileName){



        Path retFile = createNewEmptyTestfile(fileName);


        // Build the File Content ___________________________________________________________
        StringBuilder fileContent = new StringBuilder();
        // -> Export Date
        fileContent.append(COMMENT_CHARACTER + FILE_QUESTIONS_FIRSTROW_TEXT);
        fileContent.append(COMMENT_CHARACTER + EXPORT_DATETIMEROW_TEXT_PREFIX);
        fileContent.append(LocalDateTime.now());
        fileContent.append(NEWLINE_CHARACTERS);

        // -> Header Row - Apending # marks Comment
        fileContent.append(COMMENT_CHARACTER);
        fileContent.append(CSV_HEADERNAME_ID + SEPERATOR);
        fileContent.append(CSV_HEADERNAME_QUESTION + SEPERATOR);
        fileContent.append(CSV_HEADERNAME_ANSWER_A + SEPERATOR);
        fileContent.append(CSV_HEADERNAME_ANSWER_B + SEPERATOR);
        fileContent.append(CSV_HEADERNAME_ANSWER_C + SEPERATOR);
        fileContent.append(CSV_HEADERNAME_ANSWER_D + SEPERATOR);
        fileContent.append(CSV_HEADERNAME_ANSWER_E + NEWLINE_CHARACTERS);

        // -> Questions
        for(Question q:  questions){

            String qField = "\"" + q.getId() + "\"";
            String qText = (q.getQuestionText()!=null) ? "\""+q.getQuestionText()+"\"" : "null";
            String qAnswerA = (q.getAnswerA()!=null) ? "\""+q.getAnswerA()+"\"" : "null";
            String qAnswerB = (q.getAnswerB()!=null) ? "\""+q.getAnswerB()+"\"" : "null";
            String qAnswerC = (q.getAnswerC()!=null) ? "\""+q.getAnswerC()+"\"" : "null";
            String qAnswerD = (q.getAnswerD()!=null) ? "\""+q.getAnswerD()+"\"" : "null";
            String qAnswerE = (q.getAnswerE()!=null) ? "\""+q.getAnswerE()+"\"" : "null";


            fileContent.append(qField + SEPERATOR);
            fileContent.append(qText + SEPERATOR);
            fileContent.append(qAnswerA + SEPERATOR);
            fileContent.append(qAnswerB + SEPERATOR);
            fileContent.append(qAnswerC + SEPERATOR);
            fileContent.append(qAnswerD + SEPERATOR);
            fileContent.append(qAnswerE + NEWLINE_CHARACTERS);
        }

        // Write the File ___________________________________________________________
        try {
            Files.writeString(retFile,fileContent.toString());
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
