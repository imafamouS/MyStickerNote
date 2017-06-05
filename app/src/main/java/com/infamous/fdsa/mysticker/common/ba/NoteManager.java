package com.infamous.fdsa.mysticker.common.ba;

import android.content.Context;

import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.model.note.WidgetNote;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by apple on 5/21/17.
 */


/**Các phương phức giao tiếp với CSDL**/
public class NoteManager {

    protected static NoteManager instance;
    private NoteService noteService;
    private Context context;


    //Hàm khởi tạo
    private NoteManager(Context context) {
        this.context = context;
        noteService = new NoteServiceImp(context);
    }
    //Hàm khởi
    public synchronized static NoteManager getInstance(Context context) {
        if (instance == null) {
            instance = new NoteManager(context);
        }
        return instance;
    }
    //Tạo mới note
    public long createNewNote(Note note) {
        return noteService.createNewNote(note);
    }
    //Cập nhật note
    public long updateNote(Note note) {
        return noteService.updateNote(note);
    }
    //Xóa note
    public long deleteNote(Note note) {
        return noteService.deleteNote(note);
    }
    //Lấy danh sách note
    public ArrayList<Note> findAllNote() {
        return noteService.findAllNote();
    }
    //Lấy note theo ID
    public Note findNoteById(String noteId) {
        return noteService.findNoteById(noteId);
    }
    //Lấy note theo màu
    public ArrayList<Note> findNoteByColor(String color) {
        return noteService.findNoteByColor(color);
    }
    //Lấy note theo ngày lập
    public ArrayList<Note> findNoteByDateCreate() {
        return noteService.findNoteByDateCreate();
    }
    //Lấy note theo lần chỉnh sửa mới nhất
    public ArrayList<Note> findNoteByLastModify() {
        return noteService.findNoteByLastModify();
    }
    //Lấy danh sách note của widget (note được hiện thông qua widget)
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
    //Them notewidget
    public long insertWidgetNote(WidgetNote widgetNote) {
        return noteService.insertWidgetNote(widgetNote);
    }
    //Xóa notewidget
    public long deleteWidgetNote(WidgetNote widgetNote) {
        return noteService.deleteWidgetNote(widgetNote);
    }
    //Lấy danh sách widgetnote theo Note ID
    public ArrayList<WidgetNote> findWidgetNoteByNoteId(String id) {
        return noteService.findWidgetNoteByNoteId(id);
    }
    //Lấy danhs sách widgetnote theo widget ID
    public WidgetNote findWidgetNoteByWidgetId(String id) {
        return noteService.findWidgetNoteByWidgetId(id);
    }
}
