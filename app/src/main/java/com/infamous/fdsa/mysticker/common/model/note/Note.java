package com.infamous.fdsa.mysticker.common.model.note;

import java.io.Serializable;

/**
 * Created by apple on 5/21/17.
 */

public abstract class Note implements Serializable {


    public static final int NORMAL = 0;
    public static final int CHECKLIST = 1;

    protected String id;
    protected String title;
    protected String dateCreate;
    protected String lastMotify;
    protected String color;
    protected int type;
    protected boolean isComplete;

    public static Note buildNote(int type) {
        switch (type) {
            case NORMAL:
                return new NormalNote();
            case CHECKLIST:
                return new CheckListNote();
            default:
                return null;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getLastMotify() {
        return lastMotify;
    }

    public void setLastMotify(String lastMotify) {
        this.lastMotify = lastMotify;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", dateCreate='" + dateCreate + '\'' +
                ", lastMotify='" + lastMotify + '\'' +
                ", color='" + color + '\'' +
                ", type=" + type +
                '}';
    }
}
