package com.lifeistech.android.newapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements Runnable, SensorEventListener {
    SensorManager sm;
    TextView tv;
    Handler h;
    float gx, gy, gz;
    TextView tx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll = new LinearLayout(this);
        setContentView(ll);

        tx = new TextView(this);
        ll.addView(tx);

        tv = new TextView(this);
        ll.addView(tv);

        h = new Handler();
        h.postDelayed((Runnable) this, 500);


        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors =
                sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (0 < sensors.size()) {
            sm.registerListener(this, sensors.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void run() {
        tv.setText("X-axis : " + gx + "\n" +
                "Y-axis : " + gy + "\n" +
                "Z-axis : " + gz + "\n");
        h.postDelayed(this, 500);
        if (gx < -5 ){
            tx.setText("右");
        } else if (gx > 5) {
            tx.setText("左");

        } else if (gy < -3){
            tx.setText("上");
        }else if (gy > 5){
             tx.setText("下");
        }else{
            tx.setText("動かせ");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors =
                sm.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (0 < sensors.size()) {
            sm.registerListener(this, sensors.get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        h.removeCallbacks(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        gx = event.values[0];
        gy = event.values[1];
        gz = event.values[2];

    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

}
