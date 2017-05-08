package com.project.lango.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ioana on 8/05/2017.
 */
@Entity
public class Question extends AbstractPersistable<Long> {

    private static final long serialVersionUID = 1L;

    @Lob
    private String text;

    @OneToMany
    private List<Answer> answers;

    private Category category;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    private enum Category {
        GERMAN, SPANISH, DUTCH;
    }
}
