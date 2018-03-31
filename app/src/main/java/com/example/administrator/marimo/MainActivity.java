package com.example.administrator.marimo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.marimo.database.DataManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataManager dm = new DataManager(MainActivity.this);
        dm.enrollMarimo("jwheffdaw");
    }
}
