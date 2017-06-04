package com.infamous.fdsa.mysticker.common.ba;

import android.content.Context;

import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.model.note.WidgetNote;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apple on 5/21/17.
 */

public class NoteManager {

    protected static NoteManager instance;
    private NoteService noteService;
    private Context context;

    private NoteManager(Context context) {
        this.context = context;
        noteService = new NoteServiceImp(context);
    }

    public synchronized static NoteManager getInstance(Context context) {
        if (instance == null) {
            return new NoteManager(context);
        }
        return instance;
    }

    public long createNewNote(Note note) {
        return noteService.createNewNote(note);
    }

    public long updateNote(Note note) {
        return noteService.updateNote(note);
    }

    public long deleteNote(Note note) {
        return noteService.deleteNote(note);
    }

    public ArrayList<Note> findAllNote() {
        return noteService.findAllNote();
    }

    public Note findNoteById(String noteId) {
        return noteService.findNoteById(noteId);
    }

    public ArrayList<Note> findNoteByColor(String color) {
        return noteService.findNoteByColor(color);
    }

    public ArrayList<Note> findNoteByDateCreate() {
        return noteService.findNoteByDateCreate();
    }

    public ArrayList<Note> findNoteByLastModify() {
        return noteService.findNoteByLastModify();
    }

    public ArrayList<WidgetNote> getWidgetNote() {
        return noteService.getWidgetNote();
    }

    public HashMap<String, WidgetNote> getHashMapWidgetNote() {
        HashMap<String, WidgetNote> hashMap = new HashMap<String, WidgetNote>();
        for (WidgetNote widgetNote : this.getWidgetNote()) {
            hashMap.put(widgetNote.getWidgetID(), widgetNote);
        }
        return hashMap;
    }

    public long insertWidgetNote(WidgetNote widgetNote) {
        return noteService.insertWidgetNote(widgetNote);
    }

    public long deleteWidgetNote(WidgetNote widgetNote) {
        return noteService.deleteWidgetNote(widgetNote);
    }

    public ArrayList<WidgetNote> findWidgetNoteByNoteId(String id) {
        return noteService.findWidgetNoteByNoteId(id);
    }

    public WidgetNote findWidgetNoteByWidgetId(String id) {
        return noteService.findWidgetNoteByWidgetId(id);
    }
}
