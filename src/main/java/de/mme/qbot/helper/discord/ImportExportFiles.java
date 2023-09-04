package de.mme.qbot.helper.discord;

import de.mme.qbot.exceptions.ErrorReadingImportFileException;
import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.Question;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportExportFiles {


    public static final String FILENAME_PREFIX_EXPORT_QUESTIONS = "qbot-questions-export";
    public static final String FILENAME_PREFIX_EXPORT_DARES = "qbot-dares-export";

    public static final String FILE_QUESTIONS_FIRSTROW_TEXT = "qBot-Questions Exportfile\r\n";
    public static final String FILE_DARES_FIRSTROW_TEXT = "qBot-Dares Exportfile\r\n";
    public static final String CSV_HEADERNAME_DARES = "dare";
    public static final String CSV_HEADERNAME_QUESTION = "question";
    public static final String CSV_HEADERNAME_ANSWER_A = "answer-a";
    public static final String CSV_HEADERNAME_ANSWER_B = "answer-b";
    public static final String CSV_HEADERNAME_ANSWER_C = "answer-c";
    public static final String CSV_HEADERNAME_ANSWER_D = "answer-d";
    public static final String CSV_HEADERNAME_ANSWER_E = "answer-e";
    public static final String CSV_HEADERNAME_ID = "id";
    public static final String EXPORT_DATETIMEROW_TEXT_PREFIX = "Export DateTime - ";
    public static final String COMMENT_CHARACTER = "#";
    public static final String SEPERATOR = ";";
    public static final String NEWLINE_CHARACTERS = "\r\n";


    public static List<Question> ReadQuestionsFromImportfile(BufferedReader br) throws ErrorReadingImportFileException {

        if(br==null)throw new IllegalArgumentException("BufferedReader Argument may not be null");

        List<Question> retList = new ArrayList<>();
        try {
            br.lines()
                    .map(line -> line.trim())                     // Remove Blanks from beginning and end of line
                    .filter(line -> !line.startsWith(COMMENT_CHARACTER))        // Skip Comment rows
                    .forEach((line) -> {                          // Each line to question
                            String[] parts = line.split(SEPERATOR);
                            if(parts.length!=7) throw new RuntimeException("Question Import Format Error. Number of columns is not 6");
                            Long id =  Long.valueOf(parts[0].replace("\"", ""));
                            parts[1] = (parts[1].equals("null")) ? parts[1] = null : parts[1].substring(1, parts[1].length() - 1);
                            parts[2] = (parts[2].equals("null")) ? parts[2] = null : parts[2].substring(1, parts[2].length() - 1);
                            parts[3] = (parts[3].equals("null")) ? parts[3] = null : parts[3].substring(1, parts[3].length() - 1);
                            parts[4] = (parts[4].equals("null")) ? parts[4] = null : parts[4].substring(1, parts[4].length() - 1);
                            parts[5] = (parts[5].equals("null")) ? parts[5] = null : parts[5].substring(1, parts[5].length() - 1);
                            parts[6] = (parts[6].equals("null")) ? parts[6] = null : parts[6].substring(1, parts[6].length() - 1);

                            Question newQuestion = new Question(id, parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                            retList.add(newQuestion);
                    });
        }catch(RuntimeException ex){
            throw new ErrorReadingImportFileException("Problems with parsing questions-importfile. " + ex.getMessage(),ex);
        }
        return retList;
    }

    public static List<Dare> ReadDaresFromImportfile(BufferedReader br) throws ErrorReadingImportFileException {

        if(br==null)throw new IllegalArgumentException("BufferedReader Argument may not be null");


        List<Dare> retList = new ArrayList<>();
        try {
            br.lines()
                    .map(line->line.trim())                     // Remove Blanks from beginning and end of line
                    .filter(line->!line.startsWith(COMMENT_CHARACTER))        // Skip Comment rows
                    .forEach((line)->{                          // Each line to question
                        String[] parts = line.split(SEPERATOR);
                        if(parts.length!=2) throw new RuntimeException("Dare Import Format Error. Number of columns is not 6");
                            Long id = Long.valueOf(parts[0].replace("\"", ""));
                            parts[1] = (parts[1].equals("null")) ? parts[1] = null : parts[1].substring(1, parts[1].length() - 1);

                            Dare newDare = new Dare(id, parts[1]);
                            retList.add(newDare);
                    });

            }catch(RuntimeException ex){
                throw new ErrorReadingImportFileException("Problems with parsing dares-importfile. " + ex.getMessage(),ex);
            }

            return retList;
    }


    public static String createQuestionExportFileContent(Iterable<Question> questions){

        // Export all questions from repository

        StringBuilder exportFileContent = new StringBuilder();
        // -> Export Date
        exportFileContent.append(COMMENT_CHARACTER + FILE_QUESTIONS_FIRSTROW_TEXT);
        exportFileContent.append(COMMENT_CHARACTER + EXPORT_DATETIMEROW_TEXT_PREFIX);
        exportFileContent.append(LocalDateTime.now());
        exportFileContent.append(NEWLINE_CHARACTERS);

        // -> Header Row - Apending # marks Comment
        exportFileContent.append(COMMENT_CHARACTER);
        exportFileContent.append(CSV_HEADERNAME_ID + SEPERATOR);
        exportFileContent.append(CSV_HEADERNAME_QUESTION + SEPERATOR);
        exportFileContent.append(CSV_HEADERNAME_ANSWER_A + SEPERATOR);
        exportFileContent.append(CSV_HEADERNAME_ANSWER_B + SEPERATOR);
        exportFileContent.append(CSV_HEADERNAME_ANSWER_C + SEPERATOR);
        exportFileContent.append(CSV_HEADERNAME_ANSWER_D + SEPERATOR);
        exportFileContent.append(CSV_HEADERNAME_ANSWER_E + NEWLINE_CHARACTERS);

        // -> Questions
        for(Question q:  questions){

            String qField = "\"" + q.getId() + "\"";
            String qText = (q.getQuestionText()!=null) ? "\""+q.getQuestionText()+"\"" : "null";
            String qAnswerA = (q.getAnswerA()!=null) ? "\""+q.getAnswerA()+"\"" : "null";
            String qAnswerB = (q.getAnswerB()!=null) ? "\""+q.getAnswerB()+"\"" : "null";
            String qAnswerC = (q.getAnswerC()!=null) ? "\""+q.getAnswerC()+"\"" : "null";
            String qAnswerD = (q.getAnswerD()!=null) ? "\""+q.getAnswerD()+"\"" : "null";
            String qAnswerE = (q.getAnswerE()!=null) ? "\""+q.getAnswerE()+"\"" : "null";


            exportFileContent.append(qField + SEPERATOR);
            exportFileContent.append(qText + SEPERATOR);
            exportFileContent.append(qAnswerA + SEPERATOR);
            exportFileContent.append(qAnswerB + SEPERATOR);
            exportFileContent.append(qAnswerC + SEPERATOR);
            exportFileContent.append(qAnswerD + SEPERATOR);
            exportFileContent.append(qAnswerE + NEWLINE_CHARACTERS);
        }

        return exportFileContent.toString();
    }

    public static String createDareExportFileContent(Iterable<Dare> dares){

        // Export all questions from repository

        StringBuilder exportFileContent = new StringBuilder();
        // -> Export Date
        exportFileContent.append(COMMENT_CHARACTER + FILE_DARES_FIRSTROW_TEXT);
        exportFileContent.append(COMMENT_CHARACTER + EXPORT_DATETIMEROW_TEXT_PREFIX);
        exportFileContent.append(LocalDateTime.now());
        exportFileContent.append(NEWLINE_CHARACTERS);

        // -> Header Row - Apending # marks Comment
        exportFileContent.append(COMMENT_CHARACTER);
        exportFileContent.append(CSV_HEADERNAME_ID + SEPERATOR );
        exportFileContent.append(CSV_HEADERNAME_DARES + NEWLINE_CHARACTERS);

        // -> Questions
        for(Dare d:  dares){

            String qField = "\"" + d.getId() + "\"";
            String qText = (d.getText()!=null) ? "\""+d.getText()+"\"" : "null";

            exportFileContent.append(qField + SEPERATOR);
            exportFileContent.append(qText + NEWLINE_CHARACTERS);

        }

        return exportFileContent.toString();
    }

}
