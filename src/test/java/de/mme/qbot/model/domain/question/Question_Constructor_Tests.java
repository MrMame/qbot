package de.mme.qbot.model.domain.question;

import de.mme.qbot.model.domain.Question;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Question_Constructor_Tests {

    @Test
    void AddingNormalValues_isOk() {

        // ARRANGE
        Question question;
        String lbText = "This is normal Text.";

        // ACT
        question = new Question(0000L,lbText,lbText,lbText,lbText,lbText,lbText);

        // ASSERT
        assertEquals(lbText,question.getQuestionText());
        assertEquals(lbText,question.getAnswerA());
        assertEquals(lbText,question.getAnswerB());
        assertEquals(lbText,question.getAnswerC());
        assertEquals(lbText,question.getAnswerD());
        assertEquals(lbText,question.getAnswerE());

    }


    @Test
    void AddingLineBreaksIntoValues_LbAreGettingRemoved() {

        // ARRANGE
        Question question;
        String lbText = "This has \r\n linebreak. Also with \r single and with \n.";
        String cleanedText = "This has  linebreak. Also with  single and with .";

        // ACT
        question = new Question(0000L,lbText,lbText,lbText,lbText,lbText,lbText);

        // ASSERT
        assertEquals(cleanedText,question.getQuestionText());
        assertEquals(cleanedText,question.getAnswerA());
        assertEquals(cleanedText,question.getAnswerB());
        assertEquals(cleanedText,question.getAnswerC());
        assertEquals(cleanedText,question.getAnswerD());
        assertEquals(cleanedText,question.getAnswerE());

    }

    @Test
    void UsingNullValues_noErrorsAndNullReturned() {

        // ARRANGE
        Question question;

        // ACT
        question = new Question(0000L,null,null,null,null,null,null);

        // ASSERT
        assertEquals(null,question.getQuestionText());
        assertEquals(null,question.getAnswerA());
        assertEquals(null,question.getAnswerB());
        assertEquals(null,question.getAnswerC());
        assertEquals(null,question.getAnswerD());
        assertEquals(null,question.getAnswerE());

    }

    @Test
    void UsingEmptyStringValues_noErrorsAndEmptyStringReturned() {

        // ARRANGE
        Question question;

        // ACT
        question = new Question(0000L,"","","","","","");

        // ASSERT
        assertEquals("",question.getQuestionText());
        assertEquals("",question.getAnswerA());
        assertEquals("",question.getAnswerB());
        assertEquals("",question.getAnswerC());
        assertEquals("",question.getAnswerD());
        assertEquals("",question.getAnswerE());

    }
}
