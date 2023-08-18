package de.mme.qbot.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.DataAmount;

import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String questionText;


    public Long getId() {
        return id;
    }


    public String getQuestionText() {
        return questionText;
    }

    protected Question(){}

    public Question(String questionText){
        this.questionText = questionText;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(questionText, question.questionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionText);
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}
