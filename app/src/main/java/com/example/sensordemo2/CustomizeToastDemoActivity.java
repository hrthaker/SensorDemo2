package com.example.sensordemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class CustomizeToastDemoActivity extends AppCompatActivity {

    Button btnCustomToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_toast_demo);
        btnCustomToast = findViewById(R.id.btnCustomToast);
        LayoutInflater li = getLayoutInflater();
        View layout = li.inflate(R.layout.custom_toast_layout,(ViewGroup) findViewById(R.id.llcustomlayout));
        btnCustomToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast t = new Toast(getApplicationContext());
                t.setDuration(Toast.LENGTH_LONG);
                t.setGravity(Gravity.CENTER,0,0);
                t.setView(layout);
                t.show();
            }
        });
    }
}