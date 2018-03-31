package com.example.administrator.marimo;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.marimo.database.DataManager;

public class HabitEnroll extends AppCompatActivity {
    TextView bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitenroll);

        //
        showMarimoName();

        TabHost tabHost1 = (TabHost) findViewById(R.id.tabHost1);
        tabHost1.setup();

        TabHost.TabSpec ts1 = tabHost1.newTabSpec("Tab Spec 1");
        ts1.setContent(R.id.content1);
        ts1.setIndicator("운동");
        tabHost1.addTab(ts1);

        TabHost.TabSpec ts2 = tabHost1.newTabSpec("Tab Spec 2");
        ts2.setContent(R.id.content2);
        ts2.setIndicator("공부");
        tabHost1.addTab(ts2);

        TabHost.TabSpec ts3 = tabHost1.newTabSpec("Tab Spec 3");
        ts3.setContent(R.id.content3);
        ts3.setIndicator("생활패턴");
        tabHost1.addTab(ts3);

        TabHost.TabSpec ts4 = tabHost1.newTabSpec("Tab Spec 4");
        ts4.setContent(R.id.content4);
        ts4.setIndicator("정신건강");
        tabHost1.addTab(ts4);

        TabHost.TabSpec ts5 = tabHost1.newTabSpec("Tab Spec 5");
        ts5.setContent(R.id.content5);
        ts5.setIndicator("건강");
        tabHost1.addTab(ts5);

        TabHost.TabSpec ts6 = tabHost1.newTabSpec("Tab Spec 6");
        ts6.setContent(R.id.content6);
        ts6.setIndicator("사회관계");
        tabHost1.addTab(ts6);

        Button btn = (Button) findViewById(R.id.plus);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show();
            }
        });
        bt=(TextView)findViewById(R.id.next2);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(HabitEnroll.this, MainActivity.class);
                    startActivity(intent);

            }
        });

    }

    void show() {
        final EditText edittext = new EditText(this);
        edittext.setHint("15자 이내로 작성");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("습관을 입력해주세요.");

        builder.setView(edittext);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), edittext.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    void showMarimoName() {
        DataManager dataManager = new DataManager(this);
        TextView tv = (TextView) findViewById(R.id.habitenroll_tv_marimoname);
        tv.setText(dataManager.getMarimoName());
    }
}
