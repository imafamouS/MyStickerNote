package com.infamous.fdsa.mysticker.common.model.note;

/**
 * Created by apple on 6/2/17.
 */

public class WidgetNote {

    private String widgetID;
    private String noteID;

    public WidgetNote(String widgetID, String noteID) {
        this.widgetID = widgetID;
        this.noteID = noteID;
    }

    public WidgetNote() {
    }

    public String getWidgetID() {
        return widgetID;
    }

    public void setWidgetID(String widgetID) {
        this.widgetID = widgetID;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }

    @Override
    public String toString() {
        return "WidgetNote{" +
                "widgetID='" + widgetID + '\'' +
                ", noteID='" + noteID + '\'' +
                '}';
    }
}
