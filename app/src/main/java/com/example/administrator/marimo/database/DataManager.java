package com.example.administrator.marimo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jeongyeoeun on 24/02/2018.
 * description : datamanager that can access (read, write, delete ..) to databases
 */

public class DataManager {

    Context context = null;
    public MarimoDBHelper marimoDBHelper;
    public HabitDBHelper habitDBHelper;
    public AllHabitDBHelper allHabitDBHelper;
    public StatusHabitDBHelper statusHabitDBHelper;

    public DataManager(Context aContext) {
        context = aContext;
        marimoDBHelper = new MarimoDBHelper(context);
        habitDBHelper = new HabitDBHelper(context);
        allHabitDBHelper = new AllHabitDBHelper(context);
        statusHabitDBHelper = new StatusHabitDBHelper(context);
    }

    // get marimo's name
    public String getMarimoName() {
        SQLiteDatabase marimoDB = marimoDBHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM marimo";
        Cursor cursor = marimoDB.rawQuery(selectQuery, null);
        String marimoName = null;
        while (cursor.moveToNext()) {
            marimoName = cursor.getString(1);
            break;
        }
        marimoDB.close();

        return marimoName;
    }

}
