package de.mme.qbot.services.questionService;

import de.mme.qbot.model.domain.Dare;
import de.mme.qbot.model.domain.Question;
import de.mme.qbot.services.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class QuestionService_GetUniqueRandom_Tests {

    @Autowired
    QuestionService repo;

    // todo
    @Test
    void gettingQuestionIfNoQuestionWasStored_ReturnsEmptyOptional() {
        // ARRANGE
        repo.removeAll();
        // ACT
        Optional<Question> retQuestion = repo.getUniqueRandomQuestion();
        // ASSERT
        Assertions.assertEquals(true, retQuestion.isEmpty());

        repo.removeAll();
    }

    @Test
    void gettingQuestionIfOneQuestionWasStored_ReturnsStoredQuestion() throws MaximumQuestionsStoredException, TextIsTooLongException {
        // ARRANGE
        repo.removeAll();
        Question newQuestion = new Question(0001L,"The new Question "
                ,"The new Answer A"
                ,"The new Answer B"
                ,"The new Answer C"
                ,"The new Answer D"
                ,"The new Answer E");
        repo.saveQuestion(newQuestion);
        // ACT
        Optional<Question> storedQuestion = repo.getUniqueRandomQuestion();

        // ASSERT
        Assertions.assertEquals(newQuestion.getQuestionText(), storedQuestion.get().getQuestionText());
        Assertions.assertEquals(newQuestion.getAnswerA(), storedQuestion.get().getAnswerA());
        Assertions.assertEquals(newQuestion.getAnswerB(), storedQuestion.get().getAnswerB());
        Assertions.assertEquals(newQuestion.getAnswerC(), storedQuestion.get().getAnswerC());
        Assertions.assertEquals(newQuestion.getAnswerD(), storedQuestion.get().getAnswerD());
        Assertions.assertEquals(newQuestion.getAnswerE(), storedQuestion.get().getAnswerE());

        repo.removeAll();
    }

    @Test
    void gettingQuestions_ReturnsQuestionsUniqueWithoutDuplicates() throws MaximumQuestionsStoredException, TextIsTooLongException {

        final int NUMBER_OF_QUESTIONS_TO_INSERT = 5;

        // ARRANGE
        repo.removeAll();
        List<Question> questions = new ArrayList<>();
        for(int i = 1;i<=NUMBER_OF_QUESTIONS_TO_INSERT;i++){
            Question newQuestion = new Question(Long.valueOf(i)
                    ,"The new Question " + i
                    ,"The new Answer A " + i
                    ,"The new Answer B " + i
                    ,"The new Answer C " + i
                    ,"The new Answer D " + i
                    ,"The new Answer E " + i
            );
            questions.add(newQuestion);
            repo.saveQuestion(newQuestion);
        }

        // ACT
        List<Question> retQuestions = new ArrayList<>();
        for(int i = 1;i<=NUMBER_OF_QUESTIONS_TO_INSERT;i++){
            retQuestions.add(repo.getUniqueRandomQuestion().get());
        }

        // ASSERT
        // check for duplicates
        int cntSamePos = 0;
        int questionPos = 0;
        for(Question d:questions){
            questionPos++;
            int cntFound = 0;
            int retQuestionPos = 0;
            for(Question retQuestion:retQuestions){
                retQuestionPos++;
                if(d.getQuestionText().equals(retQuestion.getQuestionText())
                && d.getAnswerA().equals(retQuestion.getAnswerA())
                && d.getAnswerB().equals(retQuestion.getAnswerB())
                && d.getAnswerC().equals(retQuestion.getAnswerC())
                && d.getAnswerD().equals(retQuestion.getAnswerD())
                && d.getAnswerE().equals(retQuestion.getAnswerE())
                ){
                    cntFound++;
                    if(questionPos==retQuestionPos){cntSamePos++;}
                }
            }

            // the should be the question in the return list only one time, not lesse or more.
            Assertions.assertEquals(1,cntFound,"Question was Found more/less then 1 times!");
            Assertions.assertNotEquals(NUMBER_OF_QUESTIONS_TO_INSERT,cntSamePos,"Questions are not in random order!");   // if equal, all are at same position
        }
        repo.removeAll();
    }


}
