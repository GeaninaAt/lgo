package com.project.lango.ws.rest.Create;

/**
 * Created by alexandra on 5/10/2017.
 */
public class CreateAnswer {

    String text;
    Long questionId;
    boolean correct;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
