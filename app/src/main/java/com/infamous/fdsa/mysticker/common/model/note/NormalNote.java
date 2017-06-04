package com.infamous.fdsa.mysticker.common.model.note;

/**
 * Created by apple on 5/21/17.
 */

public class NormalNote extends Note {
    String content;

    public NormalNote() {
        this.type = Note.NORMAL;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
