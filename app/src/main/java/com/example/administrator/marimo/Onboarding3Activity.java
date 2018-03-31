package com.example.administrator.marimo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Onboarding3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding3);

        Button btn = (Button)findViewById(R.id.start);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"asdasd",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Onboarding3Activity.this,UserEnroll.class);
                startActivity(intent);
            }
        });
    }
}
