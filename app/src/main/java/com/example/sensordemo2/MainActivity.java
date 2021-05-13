package com.example.sensordemo2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    Button btnAccelerometer, btnProximity, btnLight,btnGyroscope, btnAmbientTempSensor, btnStepCounter;
    TextView tvData;
    Sensor as; //accelerometer sensor
    Sensor ps; //proximity sensor
    Sensor ls;//light sensor
    Sensor gs; //gyroscope sensor
    Sensor ats; //Ambient Temp. Sensor
    Sensor scs; //Step counter sensor
    SensorManager sm;

    private int cs; // current sensor
    private long lut = 0;
    boolean flag = false;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAccelerometer = findViewById(R.id.btnAccelerometer);
        btnProximity = findViewById(R.id.btnProximity);
        btnLight = findViewById(R.id.btnLight);
        btnGyroscope = findViewById(R.id.btnGyroscope);
        btnAmbientTempSensor = findViewById(R.id.btnAmbientTempSensor);
        btnStepCounter = findViewById(R.id.btnStepCounterSensor);
        tvData = findViewById(R.id.tvData);

/*        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){
            //ask for permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 123);
            }
        }*/



        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        as = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        ps = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        gs = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        ats = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        scs = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        ls = sm.getDefaultSensor(Sensor.TYPE_LIGHT);

        lut = System.currentTimeMillis();
        sm.registerListener(this,(Sensor)sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,(Sensor)sm.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,(Sensor)sm.getDefaultSensor(Sensor.TYPE_LIGHT),
                SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,(Sensor)sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,(Sensor)sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE),
                SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(this,(Sensor)sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR),
                SensorManager.SENSOR_DELAY_NORMAL);


        btnAccelerometer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    cs = Sensor.TYPE_ACCELEROMETER;
            }
        });
        btnProximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cs = Sensor.TYPE_PROXIMITY;
            }
        });
        btnLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cs = Sensor.TYPE_LIGHT;
            }
        });
        btnGyroscope.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSensorAvailability(Sensor.TYPE_GYROSCOPE)) {
                    cs = Sensor.TYPE_GYROSCOPE;
                }
                else
                {
                    tvData.setText("Gyroscope is not available");
                }
            }
        });
        btnAmbientTempSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSensorAvailability(Sensor.TYPE_AMBIENT_TEMPERATURE))
                {
                    cs = Sensor.TYPE_AMBIENT_TEMPERATURE;
                }
                else
                {
                    tvData.setText("Ambient Temp. is not available");
                }

            }
        });
        btnStepCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSensorAvailability(Sensor.TYPE_STEP_DETECTOR))
                {
                    cs = Sensor.TYPE_STEP_DETECTOR;
                }
                else
                {
                    tvData.setText("Step counter is not available");
                }
            }
        });





    }

    public boolean checkSensorAvailability(int sensortype)
    {
        boolean flag=false;
        if (sm.getDefaultSensor(sensortype)!=null)
        {
            flag=true;
        }
        return flag;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == cs)
        {
            if (cs == Sensor.TYPE_ACCELEROMETER)
            {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];

                float asr = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
                long at = System.currentTimeMillis();
                if (asr >= 2) {
                    if (at - lut < 200) {
                        return;
                    }
                    lut = at;
                    if (flag)
                        tvData.setBackgroundColor(Color.YELLOW);
                    else
                        tvData.setBackgroundColor(Color.RED);
                    flag = !flag;
                }
            }
            else if (cs == Sensor.TYPE_PROXIMITY) {
                float distance = event.values[0];
                if (distance>4.0)
                    tvData.setBackgroundColor(Color.GREEN);
                else
                    tvData.setBackgroundColor(Color.MAGENTA);
                tvData.setText("Proximity : " + distance);
            }
            else if (cs==Sensor.TYPE_LIGHT)
            {
                float value = event.values[0];
                tvData.setText("Brightness : "+value);
            }
            else if (cs==Sensor.TYPE_GYROSCOPE)
            {
                float value = event.values[2];
                if (value>0.5f)
                {
                    tvData.setText("Anti clock");
                }
                else if (value<-0.5f)
                {
                    tvData.setText("Clock");
                }
            }
            else if (cs==Sensor.TYPE_AMBIENT_TEMPERATURE)
            {
                float value = event.values[0];
                tvData.setText("Ambient Temp in celsius : " + value);
            }
            else if (cs==Sensor.TYPE_STEP_DETECTOR)
            {
                float value = event.values[0];
                tvData.setText("Steps : "+ value);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}