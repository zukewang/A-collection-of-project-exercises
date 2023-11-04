package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class PersonSQLiteOpenHelper extends SQLiteOpenHelper {
    public PersonSQLiteOpenHelper(Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sql = new StringBuffer();
        sql.append("create table person(");
        sql.append("id integer primary key autoincrement,");
        sql.append("name varchar(20),");
        sql.append("psw varchar(20))");
        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
