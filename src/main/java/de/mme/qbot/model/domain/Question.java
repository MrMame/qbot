package de.mme.qbot.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.DataAmount;

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
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                '}';
    }
}
