package testUtils;

import de.mme.qbot.model.domain.Question;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;

public class QuestionImportFileFactory {


    // ImportFile Syntax --------------------------------------------------------------
    private static final String FILENAME_PREFIX_EXPORT_QUESTIONS = "qbot-questions-export";

    private static final String FILE_QUESTIONS_FIRSTROW_TEXT = "qBot-Questions Exportfile\r\n";
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

    public static Path createNewOKTestfile(List<Question> questions,String fileName){

        Path retFile = ImportFiles.createNewEmptyTestfile(fileName);

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

}
