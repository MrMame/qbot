package de.mme.qbot.model.domain;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Dare {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(length=1000)
    private String text ="";

    public Long getId() {
        return id;
    }


    public String getText() {
        return text;
    }


    protected Dare(){}

    public Dare(Long id, String questionText, String answerA, String answerB, String answerC, String answerD, String answerE) {
        this.id = id;
        this.text = questionText != null ? questionText.replace("\r", "").replace("\n", "") : null;
    }

    @Override
    public String toString() {
        return "Dare{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dare dare = (Dare) o;
        return Objects.equals(id, dare.id) && Objects.equals(text, dare.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text);
    }
}
