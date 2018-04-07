package com.example.administrator.marimo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.marimo.database.AllHabitDBHelper;
import com.example.administrator.marimo.database.HabitDBHelper;
import com.example.administrator.marimo.database.StatusHabitDBHelper;

import java.util.HashMap;

public class HistoryListActivity extends AppCompatActivity {
    private final static String TAG = "HistoryListActivity";
    int flag = 0;   //진행 중인 습관 달성률 확인 변수
    AllHabitDBHelper allHabitDBHelper;
    HabitDBHelper habitDBHelper;
    StatusHabitDBHelper statusHabitDBHelper;
    HashMap<Integer, String> titleMap;  //습관명 가져오기
    HashMap<Integer, String> catMap;   //습관에 관한 카테고리 가져오기
    int[] habit;    //진행 중인 습관(3개 이상 넘지 않음)

    TextView h_per1;
    TextView h_per2;
    TextView h_per3;
    TextView h_msg1;
    TextView h_msg2;
    TextView h_msg3;
    TextView habit1;
    TextView habit2;
    TextView habit3;
    TextView tv_percent1;
    TextView tv_percent2;
    TextView tv_percent3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_list);

        viewHolder();

        allHabitDBHelper = new AllHabitDBHelper(this);
        habitDBHelper = new HabitDBHelper(this);
        statusHabitDBHelper = new StatusHabitDBHelper(this);
        habit = new int[3];
        titleMap = new HashMap<Integer, String>();
        catMap = new HashMap<Integer, String>();

        doing();    //완료 퍼센트 보여주기
    }

    public void viewHolder(){
        h_per1 = (TextView)findViewById(R.id.h_per1);
        h_per2 = (TextView)findViewById(R.id.h_per2);
        h_per3 = (TextView)findViewById(R.id.h_per3);

        h_msg1 = (TextView)findViewById(R.id.h_msg1);
        h_msg2 = (TextView)findViewById(R.id.h_msg2);
        h_msg3 = (TextView)findViewById(R.id.h_msg3);

        habit1 = (TextView)findViewById(R.id.habit1);
        habit2 = (TextView)findViewById(R.id.habit2);
        habit3 = (TextView)findViewById(R.id.habit3);

        tv_percent1 = (TextView)findViewById(R.id.percent1);
        tv_percent2 = (TextView)findViewById(R.id.percent2);
        tv_percent3 = (TextView)findViewById(R.id.percent3);

        Shader shader = new LinearGradient(0, 0, 0, h_per1.getTextSize(),
                Color.parseColor("#82307f"), Color.parseColor("#f8584c"),
                Shader.TileMode.CLAMP);
        h_per1.getPaint().setShader(shader);
        h_per2.getPaint().setShader(shader);
        h_per3.getPaint().setShader(shader);

        h_msg1.getPaint().setShader(shader);
        h_msg2.getPaint().setShader(shader);
        h_msg3.getPaint().setShader(shader);

        tv_percent1.getPaint().setShader(shader);
        tv_percent2.getPaint().setShader(shader);
        tv_percent3.getPaint().setShader(shader);
    }

    public void doing(){
        SQLiteDatabase habit_db = habitDBHelper.getReadableDatabase();
        SQLiteDatabase status_db = statusHabitDBHelper.getReadableDatabase();
        Cursor cursor = habit_db.rawQuery("SELECT habit_no, category FROM habit WHERE history = 0;", null);

//        int[] habit = new int[3];
        String[] habit_cat = new String[3]; //진행 중인 습관 카테고리명(카테고리별로 격려의 메세지)
        int k = 0;
        while(cursor.moveToNext()){
            habit[k] = cursor.getInt(0);
            habit_cat[k] = cursor.getString(cursor.getColumnIndex("category"));
            catMap.put(habit[k], habit_cat[k]);
            k++;
        }
        cursor.close();

        int cnt = 1; //나중에 수정
        Cursor c = null;
        for(int i = 0; i < habit.length; i++){
            int num = habit[i];
            String[] columns = { "status_no", "status"};
            String selection = "status_no=?";
            String[] selectionArgs = new String[]{ String.valueOf(num) };
//            Log.i(TAG, String.valueOf(num));

            c = status_db.query("status_habit", columns, selection, selectionArgs,
                    null, null, null, null);

            calculate(c, cnt);
            cnt++;
        }
        c.close();

        findTitle();    //습관명 찾기

    }

    public void calculate(Cursor c, int cnt){
        int total = 0;
        int status_no = 0;
        while(c.moveToNext()){
            status_no = c.getInt(c.getColumnIndex("status_no"));
            total += c.getInt(c.getColumnIndex("status"));
        }

        String category = catMap.get(status_no);
        int percent = (int)(((double)total / 66.0) * 100);

        switch (cnt){
            case 1:
                h_per1.setText(String.valueOf(percent));
                h_msg2.setVisibility(View.GONE);
                h_msg3.setVisibility(View.GONE);
                habit2.setVisibility(View.GONE);
                habit3.setVisibility(View.GONE);
                tv_percent2.setVisibility(View.GONE);
                tv_percent3.setVisibility(View.GONE);
                h_per2.setVisibility(View.GONE);
                h_per3.setVisibility(View.GONE);
                break;
            case 2:
                h_per2.setVisibility(View.VISIBLE);
                h_msg2.setVisibility(View.VISIBLE);
                tv_percent2.setVisibility(View.VISIBLE);
                habit2.setVisibility(View.VISIBLE);
                h_per2.setText(String.valueOf(percent));
                break;
            case 3:
                h_per3.setVisibility(View.VISIBLE);
                h_msg3.setVisibility(View.VISIBLE);
                tv_percent3.setVisibility(View.VISIBLE);
                habit3.setVisibility(View.VISIBLE);
                h_per3.setText(String.valueOf(percent));
                break;
        }

        Log.i(TAG, category + " , " + percent);

    }

    public void findTitle(){
        SQLiteDatabase allHabit_db = allHabitDBHelper.getReadableDatabase();

        for(int i = 0; i < habit.length; i++) {
            String[] columns = { "all_no, title" };
            String selection = "all_no=?";
            String[] selectionArgs = new String[]{ String.valueOf(habit[i]) };

            Cursor cursor = allHabit_db.query("all_habit", columns, selection, selectionArgs,
                    null, null, null, null);

            while(cursor.moveToNext()){
                int no = cursor.getInt(0);
                String str = cursor.getString(1);
                Log.i(TAG, no + " , " + str);
                titleMap.put(no, str);
            }

            //나중에 수정
            habit1.setText(titleMap.get(30) + " ");
            habit2.setText(titleMap.get(31) + " ");
            habit3.setText(titleMap.get(32) + " ");
        }
    }
}