package com.infamous.fdsa.mysticker.common.model.note;

/**
 * Created by apple on 5/21/17.
 */

public class ItemCheckList {

    private String id;
    private String content;
    private boolean isDone;

    public ItemCheckList() {

    }

    public ItemCheckList(String id, String content, boolean isDone) {
        this.id = id;
        this.content = content;
        this.isDone = isDone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
