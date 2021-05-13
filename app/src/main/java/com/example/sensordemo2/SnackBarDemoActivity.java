package com.example.sensordemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class SnackBarDemoActivity extends AppCompatActivity {

    Button btnDefaultSanckbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar_demo);
        btnDefaultSanckbar = findViewById(R.id.btnDefaultSanckbar);
        btnDefaultSanckbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar sb = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    sb = Snackbar.make(v, Html.fromHtml("<font color=\"#ff00ff\">Tap to open</font>"),
                            Snackbar.LENGTH_LONG);
                }
                sb.setAction("Yes", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"Yes clicked",Toast.LENGTH_LONG).show();
                    }
                });

                sb.setActionTextColor(Color.RED);
                View v1 = sb.getView();
                v1.setBackgroundColor(Color.YELLOW);
                sb.show();
            }
        });

    }
}