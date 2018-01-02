package com.example.user.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2017/12/24.
 */

public class MaincDB extends SQLiteOpenHelper {
    private static final String database="mainC.db";
    private static final int version=1;
    public MaincDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public MaincDB(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE main01 (_id integer primary key autoincrement ,"+" title text no null ,"+" object text no null ,"+" month text no null ,"+" price text no null) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS main01");
        onCreate(db);
    }
}
