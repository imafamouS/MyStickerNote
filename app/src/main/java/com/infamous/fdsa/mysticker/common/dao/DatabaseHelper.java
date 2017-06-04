package com.infamous.fdsa.mysticker.common.dao;

/**
 * Created by apple on 5/21/17.
 */

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "mysticker.sqlite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        setForcedUpgrade();

    }

}