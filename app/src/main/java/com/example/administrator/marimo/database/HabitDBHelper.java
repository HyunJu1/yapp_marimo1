package com.example.administrator.marimo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeongyeoeun on 24/02/2018.
 * description : database for habit in progress
 */

public class HabitDBHelper extends SQLiteOpenHelper {
    public HabitDBHelper(Context context) {
        super(context, "habit.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table habit(" +
                "  habit_no Integer primary key," +
                "  start_date TEXT not null," +
                "  end_date TEXT not null," +
                "  push TEXT," +
                "  constraint fk_habit foreign key (habit_no) references all_habit(all_no)" +
                " );");

        // sample data
//        db.execSQL("insert into habit values ();");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
