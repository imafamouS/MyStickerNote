package com.infamous.fdsa.mysticker.common.ba;

import android.content.Context;

import com.infamous.fdsa.mysticker.common.dao.NoteDataAccess;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.model.note.WidgetNote;

import java.util.ArrayList;

/**
 * Created by apple on 5/21/17.
 */

public class NoteServiceImp implements NoteService {

    private NoteDataAccess dataAccess;

    public NoteServiceImp(Context context) {
        dataAccess = new NoteDataAccess(context);
    }

    @Override
    public long createNewNote(Note note) {

        return dataAccess.createNewNote(note);
    }

    @Override
    public long updateNote(Note note) {

        return dataAccess.updateNote(note);
    }

    @Override
    public long deleteNote(Note note) {

        return dataAccess.deleteNote(note);
    }

    @Override
    public ArrayList<Note> findAllNote() {
        return dataAccess.findAllNote();
    }

    @Override
    public Note findNoteById(String noteId) {
        return dataAccess.findNoteById(noteId);
    }

    @Override
    public ArrayList<Note> findNoteByColor(String color) {

        return dataAccess.findNoteByColor(color);
    }

    @Override
    public ArrayList<Note> findNoteByDateCreate() {

        return dataAccess.findNoteByDateCreate();
    }

    @Override
    public ArrayList<Note> findNoteByLastModify() {

        return dataAccess.findNoteByLastModify();
    }

    @Override
    public ArrayList<WidgetNote> getWidgetNote() {
        return dataAccess.getWidgetNote();
    }

    @Override
    public long insertWidgetNote(WidgetNote widgetNote) {
        return dataAccess.insertWidgetNote(widgetNote);
    }

    @Override
    public long deleteWidgetNote(WidgetNote widgetNote) {
        return dataAccess.deleteWidgetNote(widgetNote);
    }

    @Override
    public ArrayList<WidgetNote> findWidgetNoteByNoteId(String id) {
        return dataAccess.findWidgetNoteByNoteId(id);
    }

    @Override
    public WidgetNote findWidgetNoteByWidgetId(String id) {
        return null;
    }
}
