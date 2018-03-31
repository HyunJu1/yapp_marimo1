package com.example.administrator.marimo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.marimo.database.DataManager;

public class UserEnroll extends AppCompatActivity {
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userenroll);

        TextView tv = (TextView)findViewById(R.id.next);
        et = (EditText)findViewById(R.id.name);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String marimoName = et.getText().toString();
                if(marimoName.equals("")){
                    Toast.makeText(getApplicationContext(),"이름을 입력해주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    //마리모 등록
                    DataManager dm = new DataManager(UserEnroll.this);
                    dm.enrollMarimo(marimoName);

                    Intent intent = new Intent(UserEnroll.this, HabitEnroll.class);
                    startActivity(intent);
                }
            }
        });
    }
}
