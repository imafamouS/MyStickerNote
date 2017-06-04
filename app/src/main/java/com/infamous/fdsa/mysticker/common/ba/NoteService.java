package com.infamous.fdsa.mysticker.common.ba;

import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.model.note.WidgetNote;

import java.util.ArrayList;

/**
 * Created by apple on 5/21/17.
 */

public interface NoteService {
    long createNewNote(Note note);

    long updateNote(Note note);

    long deleteNote(Note noteId);

    ArrayList<Note> findAllNote();

    Note findNoteById(String noteId);

    ArrayList<Note> findNoteByColor(String color);

    ArrayList<Note> findNoteByDateCreate();

    ArrayList<Note> findNoteByLastModify();

    ArrayList<WidgetNote> getWidgetNote();

    ArrayList<WidgetNote> findWidgetNoteByNoteId(String id);

    WidgetNote findWidgetNoteByWidgetId(String id);

    long insertWidgetNote(WidgetNote widgetNote);

    long deleteWidgetNote(WidgetNote widgetNote);
}
