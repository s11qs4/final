package com.example.user.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 2018/1/1.
 */

public class o1db extends SQLiteOpenHelper
{
    private static final String database="o1.db";
    private static final int version=1;
    public o1db(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }

    public o1db(Context context){
        this(context,database,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE main01 (_id integer primary key autoincrement ,"+" title text no null ,"+" address text no null ,"+" star text no null) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS main01");
        onCreate(db);
    }
}
