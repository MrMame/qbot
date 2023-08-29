package de.mme.qbot.model.domain;

import de.mme.qbot.services.TextIsTooLongException;
import jakarta.persistence.*;
import jdk.jfr.DataAmount;

import java.util.Objects;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(length=1000)
    private String questionText="";
    @Column(length=1000)
    private String answerA="";
    @Column(length=1000)
    private String answerB="";
    @Column(length=1000)
    private String answerC="";
    @Column(length=1000)
    private String answerD="";
    @Column(length=1000)
    private String answerE="";


    public Long getId() {
        return id;
    }


    public String getQuestionText() {
        return questionText;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public String getAnswerE() {
        return answerE;
    }

    public boolean isAnswerAvailableA(){
        return !(answerA==null || answerA.isEmpty() || answerA.isBlank());
    }
    public boolean isAnswerAvailableB(){
        return !(answerB==null || answerB.isEmpty() || answerB.isBlank());
    }
    public boolean isAnswerAvailableC(){
        return !(answerC==null || answerC.isEmpty() || answerC.isBlank());
    }
    public boolean isAnswerAvailableD(){
        return !(answerD==null || answerD.isEmpty() || answerD.isBlank());
    }
    public boolean isAnswerAvailableE(){
        return !(answerE==null || answerE.isEmpty() || answerE.isBlank());
    }


    protected Question(){}

    public Question(Long id, String questionText,String answerA,String answerB,String answerC,String answerD,String answerE){
        this.id = id;
        this.questionText = questionText!=null?questionText.replace("\r","").replace("\n",""):null;
        this.answerA = answerA!=null? answerA.replace("\r","").replace("\n",""):null;
        this.answerB = answerB!=null? answerB.replace("\r","").replace("\n",""):null;
        this.answerC = answerC!=null? answerC.replace("\r","").replace("\n",""):null;
        this.answerD = answerD!=null? answerD.replace("\r","").replace("\n",""):null;
        this.answerE = answerE!=null? answerE.replace("\r","").replace("\n",""):null;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", questionText='" + questionText + '\'' +
                ", answerA='" + answerA + '\'' +
                ", answerB='" + answerB + '\'' +
                ", answerC='" + answerC + '\'' +
                ", answerD='" + answerD + '\'' +
                ", answerE='" + answerE + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id) && Objects.equals(questionText, question.questionText) && Objects.equals(answerA, question.answerA) && Objects.equals(answerB, question.answerB) && Objects.equals(answerC, question.answerC) && Objects.equals(answerD, question.answerD) && Objects.equals(answerE, question.answerE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, questionText, answerA, answerB, answerC, answerD, answerE);
    }
}
