package com.infamous.fdsa.mysticker.common.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 5/21/17.
 */

public abstract class BaseDataAccess {
    public SQLiteDatabase database;
    private SQLiteOpenHelper openHelper;

    //Hàm khởi tạo kết nối CSDL
    public BaseDataAccess(Context context) {
        this.openHelper = new DatabaseHelper(context);
    }

    //Hàm thực hiện mở kết nối
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    //Hàm thực hiện đóng kết nối
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

}