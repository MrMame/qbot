package de.mme.qbot.services.questionRepoService;

import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.Question;
import de.mme.qbot.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionRepoService_SaveQuestion_Tests {

    @Autowired
    QuestionRepoService repo;

    @Test
    void addingQuestionToFullDb_ThrowsMaximumQuestionsStoredException() throws MaximumQuestionsStoredException, TextIsTooLongException {
        // ARRANGE

        // ACT
        MaximumQuestionsStoredException thrown = Assertions.assertThrows(MaximumQuestionsStoredException.class, () -> {
            for(int i = 1;i<=QuestionRepoService.MAXIMUM_NUMBERS_OF_QUESTION_ALLOWED+1;i++){
                repo.saveQuestion(new Question(Long.valueOf(i)
                        , "Question Text of " + i
                        , "AnswerA Text of " + i
                        , "AnswerB Text of " + i
                        , "AnswerC Text of " + i
                        , "AnswerD Text of " + i
                        , "AnswerE Text of " + i ));
            }
        });
        // ASSERT
        Assertions.assertEquals(MaximumQuestionsStoredException.class, thrown.getClass());
        // CleanUp
        repo.removeAll();

    }

    @Test
    void addingQuestionToEmptyDb_NoErrors() throws MaximumQuestionsStoredException, TextIsTooLongException {
        // CleanUp
        repo.removeAll();
        // ARRANGE
        Question newQuestion = new Question(000L,"Test Question",null,null,null,null,null);
        // ACT
        Question savedQuestion = repo.saveQuestion(newQuestion);
        // ASSERT
        assertEquals(savedQuestion.getQuestionText(),newQuestion.getQuestionText());

    }


}
