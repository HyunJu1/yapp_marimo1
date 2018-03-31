package com.example.administrator.marimo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.pm10.library.CircleIndicator;

public class Onboarding extends AppCompatActivity {
    ViewPager viewPager = null;
    Handler handler = null;
    int p=0;
    int v=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        handler = new Handler(){
            public void handleMessage(android.os.Message msg) {

                if(p==0){

                    viewPager.setCurrentItem(1);

                    p++;

                    v=1;

                }if(p==1&&v==0){

                    viewPager.setCurrentItem(1);

                    p--;

                }if(p==1&&v==1){

                    viewPager.setCurrentItem(2);

                    p++;

                }if(p==2){

                    viewPager.setCurrentItem(1);

                    p--;

                    v=0;

                }

            }

        };
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);
        circleIndicator.setupWithViewPager(viewPager);

    }
}
