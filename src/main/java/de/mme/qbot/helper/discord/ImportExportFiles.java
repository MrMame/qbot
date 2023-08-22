package de.mme.qbot.helper.discord;

import de.mme.qbot.controllers.discord.ErrorReadingImportFileException;
import de.mme.qbot.model.domain.Question;

import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ImportExportFiles {

    public static final String FILE_FIRSTROW_TEXT = "qBot-Questions Exportfile\r\n";
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
        List<Question> retList = new ArrayList<>();

        br.lines()
                .map(line->line.trim())                     // Remove Blanks from beginning and end of line
                .filter(line->!line.startsWith(COMMENT_CHARACTER))        // Skip Comment rows
                .forEach((line)->{                          // Each line to question
                    String[] parts = line.split(SEPERATOR);
                    Question newQuestion = new Question(parts[1],parts[2],parts[3],parts[4],parts[5],parts[6]);
                    retList.add(newQuestion);
                });

        return retList;
    }


    public static String createExportFileContent(Iterable<Question> questions){

        // Export all questions from repository

        StringBuilder exportFileContent = new StringBuilder();
        // -> Export Date
        exportFileContent.append(COMMENT_CHARACTER + FILE_FIRSTROW_TEXT);
        exportFileContent.append(COMMENT_CHARACTER + EXPORT_DATETIMEROW_TEXT_PREFIX);
        exportFileContent.append(LocalDateTime.now());
        exportFileContent.append(NEWLINE_CHARACTERS);

        // -> Header Row - Apending # marks Comment
        exportFileContent.append(COMMENT_CHARACTER);
        exportFileContent.append("\"" + CSV_HEADERNAME_ID + "\"" + SEPERATOR);
        exportFileContent.append("\"" + CSV_HEADERNAME_QUESTION + "\"" + SEPERATOR);
        exportFileContent.append("\"" + CSV_HEADERNAME_ANSWER_A + "\"" + SEPERATOR);
        exportFileContent.append("\"" + CSV_HEADERNAME_ANSWER_B + "\"" + SEPERATOR);
        exportFileContent.append("\"" + CSV_HEADERNAME_ANSWER_C + "\"" + SEPERATOR);
        exportFileContent.append("\"" + CSV_HEADERNAME_ANSWER_D + "\"" + SEPERATOR);
        exportFileContent.append("\"" + CSV_HEADERNAME_ANSWER_E + "\"" + NEWLINE_CHARACTERS);

        // -> Questions
        for(Question q:  questions){
            exportFileContent.append("\"" + q.getId() + "\"" + SEPERATOR);
            exportFileContent.append("\"" + q.getQuestionText() + "\"" + SEPERATOR);
            exportFileContent.append("\"" + q.getAnswerA() + "\"" + SEPERATOR);
            exportFileContent.append("\"" + q.getAnswerB() + "\"" + SEPERATOR);
            exportFileContent.append("\"" + q.getAnswerC() + "\"" + SEPERATOR);
            exportFileContent.append("\"" + q.getAnswerD() + "\"" + SEPERATOR);
            exportFileContent.append("\"" + q.getAnswerE() + "\"" + NEWLINE_CHARACTERS);
        }

        return exportFileContent.toString();
    }

}
