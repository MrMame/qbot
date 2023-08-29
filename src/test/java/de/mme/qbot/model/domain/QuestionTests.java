package de.mme.qbot.model.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class QuestionTests {
    @Test
    void AddingLineBreaksIntoValues_LbAreGettingRemoved() {

        // ARRANGE
        Question question;
        String lbText = "This has \r\n linebreak. Also with \r single and with \n.";
        String cleanedText = "This has  linebreak. Also with  single and with .";

        // ACT
        question = new Question(0001L,lbText,lbText,lbText,lbText,lbText,lbText);

        // ASSERT
        assertEquals(cleanedText,question.getQuestionText());
        assertEquals(cleanedText,question.getAnswerA());
        assertEquals(cleanedText,question.getAnswerB());
        assertEquals(cleanedText,question.getAnswerC());
        assertEquals(cleanedText,question.getAnswerD());
        assertEquals(cleanedText,question.getAnswerE());

    }
}
