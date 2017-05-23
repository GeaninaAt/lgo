package com.project.lango.ws.rest.Create;

import javax.persistence.Lob;

/**
 * Created by gatomulesei on 5/23/2017.
 */
public class CreateComment {

    @Lob
    private String text;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
