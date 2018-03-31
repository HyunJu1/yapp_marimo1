package com.example.administrator.marimo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jeongyeoeun on 24/02/2018.
 * description : database fo habit status
 */

public class StatusHabitDBHelper extends SQLiteOpenHelper {
    public StatusHabitDBHelper(Context context) {
        super(context, "status_habit.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table status_habit(" +
                "  status_no Integer ," +
                "  doing_date TEXT not null," +
                "  status Integer not null," +
                "primary key(status_no,doing_date),"+  //이 부분 변경했음
                "  constraint fk_status foreign key(status_no) references habit(habit_no)" +
                ");");

// sample data
        db.execSQL("insert into status_habit values (30, '1', 1);");
        db.execSQL("insert into status_habit values (31, '1', 0);");
        db.execSQL("insert into status_habit values (32, '1', 1);");
        db.execSQL("insert into status_habit values (30, '2', 1);");
        db.execSQL("insert into status_habit values (31, '2', 1);");
        db.execSQL("insert into status_habit values (32, '2', 1);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
