package com.example.magnetometer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView textView9, textView12, textView13, textView14;
    private static SensorManager sensorManager;
    private Sensor sensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView9 = (TextView) findViewById(R.id.textView9);
        textView12 = (TextView) findViewById(R.id.textView12);
        textView13 = (TextView) findViewById(R.id.textView13);
        textView14 = (TextView) findViewById(R.id.textView14);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(sensor!=null){
           sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else{
            Toast.makeText(this,"Not Supported", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    String B_net,B_x,B_y,B_z;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float Mag_x =Math.round(sensorEvent.values[0]);
        float Mag_y =Math.round(sensorEvent.values[1]);
        float Mag_z =Math.round(sensorEvent.values[2]);

        double Mag_norm = Math.sqrt((Mag_x*Mag_x)+(Mag_y*Mag_y)+(Mag_z*Mag_z));

         B_net = String.format("%.0f",Mag_norm);
         B_x =String.format("%.0f",Mag_x);
         B_y =String.format("%.0f",Mag_y);
         B_z =String.format("%.0f",Mag_z);

        textView9.setText(B_net);
        textView12.setText("X: "+B_x);
        textView13.setText("Y: "+B_y);
        textView14.setText("Z: "+B_z);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}

