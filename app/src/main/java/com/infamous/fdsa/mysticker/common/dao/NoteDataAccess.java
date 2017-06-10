package com.infamous.fdsa.mysticker.common.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.infamous.fdsa.mysticker.common.TextUtils;
import com.infamous.fdsa.mysticker.common.model.note.CheckListNote;
import com.infamous.fdsa.mysticker.common.model.note.ItemCheckList;
import com.infamous.fdsa.mysticker.common.model.note.NormalNote;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.model.note.WidgetNote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by apple on 5/21/17.
 */

public class NoteDataAccess extends BaseDataAccess {

    final String TABLE_NAME = "note";

    final String COLUMN_ID = "id";
    final String COLUMN_TITLE = "title";
    final String COLUMN_DATECREATE = "date_create";
    final String COLUMN_LASTMODIFY = "last_modify";
    final String COUMN_COLOR = "color_theme";
    final String COLUMN_TYPE = "type";
    final String COLUMN_COMPLETE = "complete";

    final String SORT_DATE_CREATE = COLUMN_DATECREATE;
    final String SORT_LAST_MODIFY = COLUMN_LASTMODIFY;

    final Context context;
    final TextUtils textUtils;

    public NoteDataAccess(Context context) {
        super(context);
        this.context = context;
        this.textUtils = TextUtils.getInstance(context);
    }

    @Override
    public void open() {
        super.open();
    }

    @Override
    public void close() {
        super.close();
    }

    //Tạo mới note
    public long createNewNote(Note note) {
        String query = "";
        query = "INSERT INTO " + TABLE_NAME + "(id,title,date_create,last_modify,color_theme,type,complete) VALUES(?,?,?,?,?,?,?)";
        this.open();
        SQLiteStatement statement = this.database.compileStatement(query);
        try {
            statement.bindString(1, note.getId());
            statement.bindString(2, note.getTitle());
            statement.bindString(3, note.getDateCreate());
            statement.bindString(4, note.getLastMotify());
            statement.bindString(5, note.getColor());
            statement.bindLong(6, note.getType());
            statement.bindString(7, note.isComplete() ? "true" : "false");

            long resultInsert = 0;
            if (note.getType() == Note.NORMAL) {
                NormalNoteDataAccess normalNoteDataAccess = new NormalNoteDataAccess(context);
                resultInsert = normalNoteDataAccess.insert((NormalNote) note);
            } else if (note.getType() == Note.CHECKLIST) {
                CheckListDataAccess checkListDataAccess = new CheckListDataAccess(context);
                resultInsert = checkListDataAccess.insert((CheckListNote) note);
            }
            if (resultInsert == 0) {
                return 0;
            }
        } catch (SQLiteException e) {
            return 0;
        } finally {

        }
        return statement.executeInsert();
    }
    //Cập nhật note
    public long updateNote(Note note) {
        String query = "";
        query = "UPDATE  " + TABLE_NAME + " SET title=?,last_modify=?,color_theme=?,complete=? WHERE id=?";
        this.open();
        SQLiteStatement statement = this.database.compileStatement(query);
        try {
            statement.bindString(1, note.getTitle());
            statement.bindString(2, note.getLastMotify());
            statement.bindString(3, note.getColor());
            statement.bindString(4, note.isComplete() ? "true" : "false");
            statement.bindString(5, note.getId());
            long resultUpdate = 0;
            if (note.getType() == Note.NORMAL) {
                NormalNoteDataAccess normalNoteDataAccess = new NormalNoteDataAccess(context);

                resultUpdate = normalNoteDataAccess.update((NormalNote) note);
            } else if (note.getType() == Note.CHECKLIST) {
                CheckListDataAccess checkListDataAccess = new CheckListDataAccess(context);
                if (((CheckListNote) note).getItemCheckLists().size() >= 0) {
                    resultUpdate = checkListDataAccess.update((CheckListNote) note);
                } else {
                    return statement.executeUpdateDelete();
                }
            }
            if (resultUpdate == 0) {
                return 0;
            }
        } catch (SQLiteException e) {
            return 0;
        } finally {

        }
        return statement.executeUpdateDelete();
    }
    //Xóa note
    public long deleteNote(Note note) {
        String query = "";
        query = "DELETE FROM  " + TABLE_NAME + " WHERE id=?";
        if (note.getType() == Note.NORMAL) {
            query += "; DELETE FROM normal WHERE id=?";
        } else if (note.getType() == Note.CHECKLIST) {
            query += "; DELETE FROM checklist WHERE id=?";
        }
        query += " ;DELETE FROM widget WHERE noteid=?";

        this.open();


        try {

            for (String q : query.split(";")) {
                SQLiteStatement statement = this.database.compileStatement(q);
                statement.bindString(1, note.getId());
                statement.executeUpdateDelete();
            }

        } catch (SQLiteException e) {
            return 0;
        } finally {

        }
        return 1;

    }
    //Lấy DS Note
    public ArrayList<Note> findAllNote() {
        ArrayList<Note> list = new ArrayList<>();

        this.open();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = this.database.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String date_create = cursor.getString(2);
                String last_modify = cursor.getString(3);
                String color = cursor.getString(4);
                int type = cursor.getInt(5);
                String isComlete = cursor.getString(6);

                Note note = Note.buildNote(type);
                note.setId(id);
                note.setTitle(title);
                note.setDateCreate(date_create);
                note.setLastMotify(last_modify);
                note.setColor(color);
                note.setComplete(isComlete.trim().equalsIgnoreCase("true") ? true : false);


                list.add(note);
            }
        } finally {
            cursor.close();
            this.close();
        }
        return list;
    }
    //Lấy note theo id
    public Note findNoteById(String id) {
        Note note = null;
        this.open();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id='" + id + "'";
        Cursor cursor = this.database.rawQuery(query, null);
        try {
            if (cursor.moveToNext()) {
                String note_id = cursor.getString(0);
                String title = cursor.getString(1);
                String date_create = cursor.getString(2);
                String last_modify = cursor.getString(3);
                String color = cursor.getString(4);
                int type = cursor.getInt(5);
                String isComlete = cursor.getString(6);

                note = Note.buildNote(type);

                note.setId(note_id);
                note.setTitle(title);
                note.setDateCreate(date_create);
                note.setLastMotify(last_modify);
                note.setColor(color);
                note.setComplete(isComlete.trim().equalsIgnoreCase("true") ? true : false);
                if (type == Note.NORMAL) {
                    NormalNoteDataAccess normalNoteDataAccess = new NormalNoteDataAccess(context);
                    ((NormalNote) note).setContent(normalNoteDataAccess.getContent(note_id));
                } else if (type == Note.CHECKLIST) {
                    CheckListDataAccess checkListDataAccess = new CheckListDataAccess(context);
                    Log.d("SIZE_ITEM", checkListDataAccess.getContent(note_id).size() + "");
                    ((CheckListNote) note).setItemCheckLists(checkListDataAccess.getContent(id));
                }
            }
        } finally {
            cursor.close();
            this.close();
        }
        return note;
    }
    //Lấy note theo màu
    public ArrayList<Note> findNoteByColor(String color) {
        ArrayList<Note> list = new ArrayList<>();

        this.open();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE color_theme='" + color + "'";
        Cursor cursor = this.database.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String date_create = cursor.getString(2);
                String last_modify = cursor.getString(3);
                String color_note = cursor.getString(4);
                int type = cursor.getInt(5);
                String isComlete = cursor.getString(6);

                Note note = Note.buildNote(type);

                note.setId(id);
                note.setTitle(title);
                note.setDateCreate(date_create);
                note.setLastMotify(last_modify);
                note.setColor(color_note);
                note.setComplete(isComlete.trim().equalsIgnoreCase("true") ? true : false);

                list.add(note);
            }
        } finally {
            cursor.close();
            this.close();
        }
        return list;
    }
    //Lấy note theo ngày lập
    public ArrayList<Note> findNoteByDateCreate() {
        return searchBy(findAllNote(), SORT_DATE_CREATE);
    }
    //Lấy note theo lần chỉnh sửa mới nhất
    public ArrayList<Note> findNoteByLastModify() {
        return searchBy(findAllNote(), SORT_LAST_MODIFY);
    }
    //Lấy note theo kiểm tìm kiếm
    private ArrayList<Note> searchBy(ArrayList<Note> list, String sortType) {
        ArrayList<Note> returnList = new ArrayList<>();
        if (sortType.equalsIgnoreCase(SORT_DATE_CREATE)) {

            Collections.sort(list, Collections.reverseOrder(new Comparator<Note>() {

                @Override
                public int compare(Note o1, Note o2) {

                    String io1 = o1.getDateCreate();
                    String io2 = o2.getDateCreate();
                    return textUtils.compare2DateFromString(io1, io2);
                }
            }));
            returnList = list;
        } else if (sortType.equalsIgnoreCase(SORT_LAST_MODIFY)) {
            Collections.sort(list, Collections.reverseOrder(new Comparator<Note>() {

                @Override
                public int compare(Note o1, Note o2) {

                    String io1 = o1.getLastMotify();
                    String io2 = o2.getLastMotify();
                    return textUtils.compare2DateFromString(io1, io2);
                }
            }));
            returnList = list;
        }
        return returnList;
    }
    //Lấy danh sách widget note
    public ArrayList<WidgetNote> getWidgetNote() {
        ArrayList<WidgetNote> list = new ArrayList<>();

        this.open();
        String query = "SELECT * FROM " + "widget";
        Cursor cursor = this.database.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                String widgetid = cursor.getString(0);
                String noteid = cursor.getString(1);

                WidgetNote widgetNote = new WidgetNote(widgetid, noteid);

                list.add(widgetNote);
            }
        } finally {
            cursor.close();
            this.close();
        }
        return list;
    }
    //Lấy ds widget note theo note id
    public ArrayList<WidgetNote> findWidgetNoteByNoteId(String id) {
        ArrayList<WidgetNote> list = new ArrayList<>();

        this.open();
        String query = "SELECT * FROM " + "widget" + " WHERE noteid='" + id + "'";
        Cursor cursor = this.database.rawQuery(query, null);
        try {
            while (cursor.moveToNext()) {
                String widgetid = cursor.getString(0);
                String noteid = cursor.getString(1);

                WidgetNote widgetNote = new WidgetNote(widgetid, noteid);

                list.add(widgetNote);
            }
        } finally {
            cursor.close();
            this.close();
        }
        return list;
    }
    //Lấy ds widget note theo widget id
    public WidgetNote findWidgetNoteByWidgetId(String id) {
        WidgetNote widgetNote = new WidgetNote();

        this.open();
        String query = "SELECT * FROM " + "widget" + " WHERE widgetid='" + id + "'";
        Cursor cursor = this.database.rawQuery(query, null);
        try {
            if (cursor.moveToNext()) {
                String widgetid = cursor.getString(0);
                String noteid = cursor.getString(1);

                widgetNote.setNoteID(noteid);
                widgetNote.setWidgetID(widgetid);


            }
        } finally {
            cursor.close();
            this.close();
        }
        return widgetNote;
    }
    //thêm mới widget note
    public long insertWidgetNote(WidgetNote widgetNote) {
        String query = "";
        query = "INSERT INTO " + "widget" + "(widgetid,noteid) VALUES(?,?)";
        this.open();
        SQLiteStatement statement = this.database.compileStatement(query);
        try {
            statement.bindString(1, widgetNote.getWidgetID());
            statement.bindString(2, widgetNote.getNoteID());
        } catch (SQLiteException e) {
            return 0;
        } finally {

        }
        return statement.executeInsert();
    }
    //Xóa widget note
    public long deleteWidgetNote(WidgetNote widgetNote) {
        String query = "";
        query = "DELETE FROM widget WHERE noteid=?";
        this.open();
        SQLiteStatement statement = this.database.compileStatement(query);
        try {
            Log.d(this.getClass().getSimpleName(),widgetNote.toString());
            statement.bindString(1, widgetNote.getNoteID());
        } catch (SQLiteException e) {
            return 0;
        } finally {

        }
        return statement.executeUpdateDelete();
    }

    class NormalNoteDataAccess extends BaseDataAccess {
        final String TABLE_NAME = "normal";

        final String COLUMN_ID = "id";
        final String COLUMN_CONTENT = "content";

        public NormalNoteDataAccess(Context context) {
            super(context);
        }

        @Override
        public void open() {
            super.open();
        }

        @Override
        public void close() {
            super.close();
        }
        //Thêm mới note bình thường
        public long insert(NormalNote note) {
            String query = "INSERT INTO " + TABLE_NAME + "(id,content)" + " VALUES(?,?)";
            this.open();
            SQLiteStatement statement = this.database.compileStatement(query);
            try {
                statement.bindString(1, note.getId());
                statement.bindString(2, note.getContent());
            } catch (SQLiteException e) {
                return 0;
            } finally {
            }
            return statement.executeInsert();
        }
        //Cập nhật note
        public long update(NormalNote note) {
            String query = "UPDATE " + TABLE_NAME + " SET content=? WHERE id=?";
            this.open();
            SQLiteStatement statement = this.database.compileStatement(query);
            try {
                statement.bindString(1, note.getContent());
                statement.bindString(2, note.getId());
            } catch (SQLiteException e) {
                return 0;
            } finally {
            }
            return statement.executeUpdateDelete();
        }
        //Lấy nội dung note theo id note
        public String getContent(String id) {
            String result = "";
            String query = "SELECT " + COLUMN_CONTENT + " from " + TABLE_NAME + " WHERE " + COLUMN_ID + " = '" + id + "'";
            this.open();
            Cursor cursor = this.database.rawQuery(query, null);
            try {
                if (cursor.moveToNext()) {
                    String content = cursor.getString(0);

                    result = content;
                }
            } finally {
                cursor.close();
                this.close();
            }
            return result;
        }
    }

    class CheckListDataAccess extends BaseDataAccess {
        final String TABLE_NAME = "checklist";

        final String COLUMN_ID = "id";
        final String COLUMN_CONTENT = "content";
        final String COLUMN_ISDONE = "done";

        public CheckListDataAccess(Context context) {
            super(context);
        }

        @Override
        public void open() {
            super.open();
        }

        @Override
        public void close() {
            super.close();
        }
        //Thêm nội dung checklist
        public long insert(CheckListNote note) {
            String query = "INSERT OR REPLACE INTO " + TABLE_NAME + "(id,content,done)" + " VALUES(?,?,?)";
            this.open();
            SQLiteStatement statement = this.database.compileStatement(query);
            try {
                for (int i = 0; i < note.getItemCheckLists().size(); i++) {
                    statement.clearBindings();
                    statement.bindString(1, note.getId());
                    statement.bindString(2, note.getItemCheckLists().get(i).getContent());
                    statement.bindString(3, note.getItemCheckLists().get(i).isDone() ? "true" : "false");
                    statement.executeInsert();
                }
                // this.database.setTransactionSuccessful();
            } catch (SQLiteException e) {
                return 0;
            } finally {
                this.close();
            }
            return 1;
        }
        //Cập nhật nội dung checklist
        public long update(CheckListNote note) {
            String query = " insert into checklist(id,content,done) values(?,?,?)";

            this.open();
            SQLiteStatement statement = this.database.compileStatement(query);

            if (delete(note) < 0) {
                return 0;
            }
            try {

                for (int i = 0; i < note.getItemCheckLists().size(); i++) {
                    statement.clearBindings();

                    statement.bindString(1, note.getId());
                    statement.bindString(2, note.getItemCheckLists().get(i).getContent().trim());
                    statement.bindString(3, note.getItemCheckLists().get(i).isDone() ? "true" : "false");

                    statement.executeInsert();
                    Log.d("INSERT", "OK");

                }
                //this.database.setTransactionSuccessful();
            } catch (SQLiteException e) {
                return 0;
            } finally {
            }
            return 1;
        }
        //Xóa nội dung checklist
        public long delete(CheckListNote note) {
            String delete = "delete from checklist where id=?";
            SQLiteStatement sqLiteStatementDelte = this.database.compileStatement(delete);
            this.open();
            try {
                sqLiteStatementDelte.bindString(1, note.getId());
            } catch (SQLiteException e) {

            }

            return sqLiteStatementDelte.executeUpdateDelete();
        }
        //Lấy nội dung checklist
        public ArrayList<ItemCheckList> getContent(String id) {
            ArrayList<ItemCheckList> list = new ArrayList<>();
            String result = "";

            String query = "select content,done from checklist where id='" + id + "'";
            this.open();
            Cursor cursor = this.database.rawQuery(query, null);
            try {
                while (cursor.moveToNext()) {
                    String content = cursor.getString(0);
                    String isdone = cursor.getString(1);

                    ItemCheckList itemCheckList = new ItemCheckList();
                    itemCheckList.setId(id);
                    itemCheckList.setContent(content);
                    itemCheckList.setDone(isdone.trim().equalsIgnoreCase("true") ? true : false);
                    list.add(itemCheckList);
                }
            } catch (SQLiteException e) {
                return new ArrayList<>();
            } finally {
                cursor.close();
                this.close();
            }
            return list;
        }
    }
}
