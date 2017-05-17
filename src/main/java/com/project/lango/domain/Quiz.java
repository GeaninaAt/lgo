package com.project.lango.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gatomulesei on 5/16/2017.
 */
@Entity
public class Quiz extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;

    public Type type;

    private int score = 10;

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public enum Type{
        GERMAN, SPANISH, DUTCH;

        public static List<String> getAllTypes(){
            List<String> types = new ArrayList<>();
            types.add(GERMAN.name());
            types.add(SPANISH.name());
            types.add(DUTCH.name());
            return types;
        }
    }


}
