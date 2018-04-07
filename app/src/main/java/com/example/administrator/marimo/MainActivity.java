package com.example.administrator.marimo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.marimo.adapter.HorizontalListView;
import com.example.administrator.marimo.database.AllHabitDBHelper;
import com.example.administrator.marimo.database.DataManager;
import com.example.administrator.marimo.database.HabitDBHelper;
import com.example.administrator.marimo.database.StatusHabitDBHelper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
   //마리모 움직이기 ***********
//    BallView ballView;

    private SensorManager mSensorMgr;
    private Sensor accelerometer;
    private Sensor magnetometer;

//********************************


    DataManager dbmamager;
    AllHabitDBHelper allhelper;
    HabitDBHelper helper;
    public StatusHabitDBHelper sthelper;
    private SQLiteDatabase db;
    private SQLiteDatabase db2;
    private SQLiteDatabase db3;
    private String str_date;
    ArrayList<Integer> dataObjects;
    ArrayList<String> habit_title;
    ArrayList<String> habit_startDate;
    ArrayList<Integer> habit_no;
    TextView  text;
    TextView text1;
    private int doing_date;

    ImageButton to_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//마리모 움직이기 ***********
//        ballView = new BallView(this);
        Window win = getWindow();

        win.setContentView(R.layout.activity_main);


        mSensorMgr = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = mSensorMgr.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
 //****************************


//        LayoutInflater inflater=getLayoutInflater();
//        View layout=(View) inflater.inflate(R.layout.activity_main,null);
//        addContentView(layout, new LinearLayout.LayoutParams(320,480));

//        LayoutInflater inflater = (LayoutInflater)getSystemService(
//
//                Context.LAYOUT_INFLATER_SERVICE);
//
//        RelativeLayout linear = (RelativeLayout)inflater.inflate(ballView);
//


        //파라미터를 세팅해줌
//
//        LinearLayout.LayoutParams paramlinear = new LinearLayout.LayoutParams(
//
//                LinearLayout.LayoutParams.MATCH_PARENT,
//
//                LinearLayout.LayoutParams.MATCH_PARENT
//
//        );
//
//
//
//        //윈도우에 추가시킴
//
//        win.addContentView(linear, paramlinear);



        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        str_date = df.format(new Date());


        text=(TextView)findViewById(R.id.textView4);
        text1=(TextView)findViewById(R.id.textView5);
        to_history =(ImageButton)findViewById(R.id.image_button);
       to_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HistoryListActivity.class);
                startActivity(intent);
            }
        });

        dbmamager = new DataManager((MainActivity.this));
        //  dbmamager.enrollMarimo("jwheffdaw");

        allhelper = new AllHabitDBHelper(getApplicationContext());
        helper = new HabitDBHelper(getApplicationContext());
        sthelper = new StatusHabitDBHelper(getApplicationContext());
        habit_title = new ArrayList<String>();
        habit_startDate = new ArrayList<String>();
        habit_no = new ArrayList<Integer>();
        dataObjects = new ArrayList<Integer>();
        // db = helper.getReadableDatabase();


        select();
        select2();

        HorizontalListView listview = (HorizontalListView) findViewById(R.id.listview);
        listview.setAdapter(new HAdapter());

        listview.setOnItemClickListener(mItemClickListener);
        text.setText(String.valueOf(doing_date));
// 두날짜의 차이 구하기
        text1.setText(String.valueOf(66-doing_date)+"일만 지나면 마리모가 성장해요!");

        //   Toast.makeText(getApplicationContext(), getTest(), Toast.LENGTH_SHORT).show();
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long l_position) {
            // parent는 AdapterView의 속성의 모두 사용 할 수 있다.
            String tv = (String) parent.getAdapter().getItem(position);
            //   Toast.makeText(getApplicationContext(), tv, Toast.LENGTH_SHORT).show();

            // Position 은 클릭한 Row의 position 을 반환해 준다.
            // Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
            // l_Position 은 클릭한 Row의 long type의 position 을 반환해 준다.
            //     Toast.makeText(getApplicationContext(), "l = " + l_position, Toast.LENGTH_SHORT).show();
        }
    };


    public void select() {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("select * from habit", null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                int _id = c.getInt(c.getColumnIndex("habit_no"));
                String start_date = c.getString(c.getColumnIndex("start_date"));
                String end_date = c.getString(c.getColumnIndex("end_date"));
                String push = c.getString(c.getColumnIndex("push"));

               String category = c.getString(c.getColumnIndex("category"));
                Log.i("dbdb", "id:" + _id + " start_date : " + start_date + " end_date: " + end_date+" category: ");
                dataObjects.add(_id);
                habit_startDate.add(start_date);
                doing_date=doDiffOfDate(habit_startDate.get(0),str_date);
                c.moveToNext();
            }
        }
        c.close();
    }

    public void select2() {
        db = allhelper.getReadableDatabase();
        Cursor c2 = db.rawQuery("select * from all_habit where all_no in(" + dataObjects.get(0) + "," + dataObjects.get(1) + "," + dataObjects.get(2) + ")", null);


        if (c2.moveToFirst()) {
            while (!c2.isAfterLast()) {
                int no = c2.getInt(c2.getColumnIndex("all_no"));
                String name = c2.getString(c2.getColumnIndex("title"));
                String name2 = c2.getString(c2.getColumnIndex("content"));
                String category =c2.getString(c2.getColumnIndex("category"));
                Log.i("dbdb", " name : " + name + " name2: " + name2+"category:");

                habit_no.add(no);
                habit_title.add(name);

                c2.moveToNext();
            }
        }
    }

    public void finding(int a, int str_date) {

        db = sthelper.getWritableDatabase();
        //날짜 계산해서 자동으로 넣기

        try {
            //날짜 계산해서 이미 오늘 날짜에 데이터 삽입이 이루어졌으면 update나 혹은 insert불가하게
            String query = ("Insert into status_habit (status_no, doing_date,status)  values(" + a + "," + str_date + "," + "1)");
            db.execSQL(query);        //Log.i("zonnnn","성공");
            Toast.makeText(getApplicationContext(), "습관 실천 성공!", Toast.LENGTH_SHORT).show();
            db = sthelper.getReadableDatabase();
            Cursor c2 = db.rawQuery("select * from status_habit", null);
            while (c2.moveToNext()) {


                Log.i("a", "성공");
                int no = c2.getInt(c2.getColumnIndex("status_no"));


                Log.i("b", String.valueOf(no));

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "이미 입력하셨습니다!", Toast.LENGTH_SHORT).show();
        }

    }

    public void finding2(int a, int str_date) {

        try {
            db = sthelper.getWritableDatabase();
            //날짜 계산해서 자동으로 넣기


            String query = ("Insert into status_habit (status_no, doing_date,status) values(" + a + "," + str_date + "," + "0)");
            db.execSQL(query);        //Log.i("zonnnn","성공");
            Toast.makeText(getApplicationContext(), "습관 실천 실패!ㅠㅠ", Toast.LENGTH_SHORT).show();
            db = sthelper.getReadableDatabase();
            Cursor c2 = db.rawQuery("select * from status_habit", null);

            while (c2.moveToNext()) {
                Log.i("a", "성공");
                int no = c2.getInt(c2.getColumnIndex("status_no"));

                Log.i("b", String.valueOf(no));
            }
        } catch (Exception e) {    //날짜 계산해서 이미 오늘 날짜에 데이터 삽입이 이루어졌으면 update나 혹은 insert불가하게

            Toast.makeText(getApplicationContext(), "이미 입력하셨습니다!", Toast.LENGTH_SHORT).show();
        }

    }

    private class HAdapter extends BaseAdapter {
        String[] dataObjects2 = new String[]{
                habit_title.get(0), habit_title.get(1), habit_title.get(2)
        };

        public HAdapter() {
            super();
        }

        private OnClickListener mOnButtonClicked = new OnClickListener() {

            public void onClick(View v) {
                Button bt = (Button) v;
                Toast.makeText(getApplicationContext(), "Hola desde " + bt.getText(), Toast.LENGTH_SHORT).show();
            }
        };

        public int getCount() {
            return dataObjects2.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            View retval = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewitem, null);
            TextView title = (TextView) retval.findViewById(R.id.title);
            Button button1 = (Button) retval.findViewById(R.id.btn1);
            Button button2 = (Button) retval.findViewById(R.id.btn2);
            ImageView img = (ImageView) retval.findViewById(R.id.image);
            if (position == 1) {
                img.setImageResource(R.mipmap.vitamin_icon);
            }



            doing_date=doDiffOfDate(habit_startDate.get(position),str_date);

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = habit_no.get(position);
                    Log.i("c", String.valueOf(a));

                //    Toast.makeText(getApplicationContext(),"날짜차이:"+doing_date, Toast.LENGTH_SHORT).show();

                    finding(a, doing_date);

                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int a = habit_no.get(position);
                    // doing_date=doDiffOfDate(habit_startDate.get(position),str_date);
                  //  Toast.makeText(getApplicationContext(),"날짜차이:"+doing_date, Toast.LENGTH_SHORT).show();

                    finding2(a, doing_date);
                }
            });
            title.setText(dataObjects2[position]);
            return retval;
        }
    }

    ;


    public int doDiffOfDate(String now_date, String start_date) {
        int diffDays = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date beginDate = formatter.parse(start_date);
            Date endDate = formatter.parse(now_date);

            // 시간차이를 시간,분,초를 곱한 값으로 나누면 하루 단위가 나옴
            long diff = beginDate.getTime()-endDate.getTime();
            diffDays = (int) diff / (24 * 60 * 60 * 1000);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffDays;
    }


//
//
//
////마리모 움직이기*******************
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mSensorMgr.unregisterListener(ballView);
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        mSensorMgr.registerListener(ballView, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
//        mSensorMgr.registerListener(ballView, magnetometer, SensorManager.SENSOR_DELAY_FASTEST);
//    }
//
//
//    class BallView extends View implements SensorEventListener {
//
//        float[] mGravity = null;
//        float[] mGeomagnetic= null;
//
//        float pitch;
//        float roll;
//
//        Paint paint;
//
//        int width;
//        int height;
//
//        int x;
//        int y;
//        int r;
//
//        boolean isStart;
//
//        public BallView(Context context) {
//            super(context);
//            paint = new Paint();
//            paint.setColor(Color.RED);
//            paint.setAntiAlias(true);
//            isStart = true;
//            r = 100;
//        }
//
//        public void onDraw(Canvas canvas) {
//            if(isStart) {
//                width = canvas.getWidth();
//                height = canvas.getHeight();
//                x =  width / 2;
//                y =  height / 2;
//                isStart = false;
//            }
//
//            canvas.drawCircle(x, y, r, paint);
//        }
//
//
//        @Override
//        public void onAccuracyChanged(Sensor arg0, int arg1) {
//
//        }
//
//        @Override
//        public void onSensorChanged(SensorEvent event) {
////			중력과 자기장 값 획득, 각각 획득되므로 모두 수집할 때까지 멤버변수에 저장
//            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                mGravity = event.values.clone();
//            }
//            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
//                mGeomagnetic = event.values.clone();
//
////		    두 센서 값이 모두 수집되었을 경우
//            if (mGravity != null && mGeomagnetic != null) {
//                float rotationMatrix[] = new float[9];
////		    	기기 측정 중력 및 자기장 값을 토대로 회전 정보 획득, rotationMatrix에 해당 값 저장
//                boolean success = SensorManager.getRotationMatrix(rotationMatrix, null, mGravity, mGeomagnetic);
//
//                if (success) {
//                    float values[] = new float[3];
////		    		회전 정보 매트릭스를 통해 기기의 orientation 획득, values 에 저장
//                    SensorManager.getOrientation(rotationMatrix, values);
//
////		    		from rad. to degree
//                    for (int i=0; i < values.length; i++) {
//                        Double degrees = Math.toDegrees(values[i]);
//                        values[i] = degrees.floatValue();
//                    }
//
//                    float pitch = values[1];
//                    float roll = values[2];
//
//                    if (pitch > 0) {
//                        if (y > r) y -= 1;
//                    } else if (pitch < 0) {
//                        if (y < (height - r)) y += 1;
//                    }
//
//                    if (roll > 0) {
//                        if (x <
//                                (width - r)) x += 1;
//                    } else if (roll < 0) {
//                        if (x > r) x -= 1;
//                    }
//
//                    invalidate();
//                }
//            }
//        }
//
//    }

//*****************************************
}