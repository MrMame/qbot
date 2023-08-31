package de.mme.qbot.helper.discord.importExporFiles;

import de.mme.qbot.controllers.discord.ErrorReadingImportFileException;
import de.mme.qbot.helper.discord.ImportExportFiles;
import de.mme.qbot.model.domain.Question;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.QuestionImportFileFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import java.nio.file.Path;
import java.nio.file.Paths;


public class ImportExportFiles_ReadQuestionsFromImportfile_Tests {

    public static final String FILENAME_TEST_IMPORTFILE = "questions-test-importfile.txt";
    public static final String TESTFILE_EXAMPLE_QUESTION_PREFIX = "Testfile Example Question ";
    public static final String TESTFILE_EXAMPLE_ANSWER_A_PREFIX = "Testfile Example Answer A ";
    public static final String TESTFILE_EXAMPLE_ANSWER_B_PREFIX = "Testfile Example Answer B ";
    public static final String TESTFILE_EXAMPLE_ANSWER_C_PREFIX = "Testfile Example Answer C ";
    public static final String TESTFILE_EXAMPLE_ANSWER_D_PREFIX = "Testfile Example Answer D ";
    public static final String TESTFILE_EXAMPLE_ANSWER_E_PREFIX = "Testfile Example Answer E ";


    @Test
    void IfQuestionsToExportAreOk_ExportfileIsOk() throws IOException, ErrorReadingImportFileException {


        // ARRANGE
        // Add Some Fil Questions
        final Integer MAX_NUMBER_EXAMPLE_QUESTION = 40;
        List<Question> questions = new ArrayList<>();
        for(int i = 1; i <= MAX_NUMBER_EXAMPLE_QUESTION;i++){
            questions.add(new Question(0000L, TESTFILE_EXAMPLE_QUESTION_PREFIX + i
                    , TESTFILE_EXAMPLE_ANSWER_A_PREFIX + i
                    , TESTFILE_EXAMPLE_ANSWER_B_PREFIX + i
                    , TESTFILE_EXAMPLE_ANSWER_C_PREFIX + i
                    , TESTFILE_EXAMPLE_ANSWER_D_PREFIX + i
                    , TESTFILE_EXAMPLE_ANSWER_E_PREFIX + i)
            );
        }
        // Add some Spezial Questions
        questions.add(new Question(-99L,"Minus 99 id Question",null,null,null,null,null));
        questions.add(new Question(11L,"Only Answer A","This is the only Answer",null,null,null,null));
        questions.add(new Question(11L,"Only Answer B",null,"Answer B",null,null,null));
        questions.add(new Question(11L,"Only Answer C",null,null,"Answer C",null,null));
        questions.add(new Question(11L,"Only Answer D",null,null,null,"Answer D",null));
        questions.add(new Question(11L,"Only Answer E",null,null,null,null,"Answer E"));
        questions.add(new Question(11L,"","","","","",""));
        questions.add(new Question(null,"","","","","",""));
        questions.add(new Question(null,null,null,null,null,null,null));


        // ACT
        String exportFileContent = ImportExportFiles.createQuestionExportFileContent(questions);
        Files.writeString(Path.of(FILENAME_TEST_IMPORTFILE),exportFileContent);
        BufferedReader br = Files.newBufferedReader(Path.of(FILENAME_TEST_IMPORTFILE));
        List<Question> readQuest =  ImportExportFiles.ReadQuestionsFromImportfile(br);

        // ASSERT
        int cntHits=0;
        for(Question rQ:readQuest){
            if(questions.contains(rQ))cntHits++;
        }
        Assertions.assertEquals(questions.size(),cntHits);
    }

    @Test
    void IfQuestionToExportIsAllNull_ExportfileIdIsZeroAndValuesAreNull() throws IOException, ErrorReadingImportFileException {

        // ARRANGE
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(null,null,null,null,null,null,null));

        // ACT
        String exportFileContent = ImportExportFiles.createQuestionExportFileContent(questions);
        Files.writeString(Path.of(FILENAME_TEST_IMPORTFILE),exportFileContent);
        BufferedReader br = Files.newBufferedReader(Path.of(FILENAME_TEST_IMPORTFILE));
        List<Question> readQuest =  ImportExportFiles.ReadQuestionsFromImportfile(br);

        // ASSERT
        Assertions.assertEquals(0000L,readQuest.get(0).getId());
        Assertions.assertEquals(null,readQuest.get(0).getQuestionText());
        Assertions.assertEquals(null,readQuest.get(0).getAnswerA());
        Assertions.assertEquals(null,readQuest.get(0).getAnswerB());
        Assertions.assertEquals(null,readQuest.get(0).getAnswerC());
        Assertions.assertEquals(null,readQuest.get(0).getAnswerD());
        Assertions.assertEquals(null,readQuest.get(0).getAnswerE());

    }
    @Test
    void ifBuffReaderIsNull_ThrowsIllegalArgumentException() throws ErrorReadingImportFileException {
        // ARRANGE
        // ACT
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            List<Question> retList = ImportExportFiles.ReadQuestionsFromImportfile(null);
        });
        // ASSERT
        Assertions.assertEquals(IllegalArgumentException.class, thrown.getClass());
    }

    @Test
    void ifImportFileIsEmpty_ReturnsEmptyList() throws ErrorReadingImportFileException {
        // ARRANGE
        BufferedReader br =  QuestionImportFileFactory.createBufferedReaderFromNewEmptyTestfile(FILENAME_TEST_IMPORTFILE);
        // ACT
        List<Question> retList = ImportExportFiles.ReadQuestionsFromImportfile(br);
        // ASSERT
        Assertions.assertEquals(0, retList.size());
    }

}
