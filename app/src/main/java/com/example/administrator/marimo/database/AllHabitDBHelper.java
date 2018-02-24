package com.example.administrator.marimo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeongyeoeun on 24/02/2018.
 * description : database for all habit
 */

public class AllHabitDBHelper extends SQLiteOpenHelper {
    public AllHabitDBHelper(Context context) {
        super(context, "all_hablit.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table all_habit(" +
                "  all_no Integer primary key," +
                "  title TEXT not null," +
                "  content TEXT not null" +
                ");");

        // sample data
//        db.execSQL("insert into all_habit values ();");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
