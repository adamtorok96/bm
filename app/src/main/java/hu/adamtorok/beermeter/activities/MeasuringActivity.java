package hu.adamtorok.beermeter.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import hu.adamtorok.beermeter.R;
import hu.adamtorok.beermeter.Utils;
import hu.adamtorok.beermeter.db.models.Measurement;

public class MeasuringActivity extends AppCompatActivity
        implements View.OnClickListener, SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private Thread mThread;

    private boolean mStarted = false;

    private double mSensitivity;

    private long
            mStartTime = 0,
            mEndTime;

    private TextView tvTime;

    private EditText etName;

    private Button btnClearName;

    private Runnable mThreadRunnable = new Runnable() {
        @Override
        public void run() {
            while ( true ) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if( mStarted )
                    setTimeText(getElapsedMillisecondsFromStart());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measuring);

        tvTime = findViewById(R.id.tvTime);
        etName = findViewById(R.id.etName);
        btnClearName = findViewById(R.id.btnClearName);

        btnClearName.setOnClickListener(this);

        init();
    }

    private void init() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        mSensitivity = Double.valueOf(sharedPreferences.getString("pref_sensitivity", "12"));

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if( mSensorManager == null ) {
            Toast
                    .makeText(this, R.string.failed_to_open_sensor_manager, Toast.LENGTH_LONG)
                    .show()
            ;

            finish();

            return;
        }

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    private void handleSensorChanged(float[] values) {
        double length = Utils.vectorLength(values);

        if( length < mSensitivity )
            return;

        if( mStarted ) {
            if( getElapsedSecondsFromStart() > 0 ) {
                mEndTime = System.nanoTime();
                mStarted = false;

                setTimeText(getTime());

                createMeasurement();
            }
        } else {
            if( getElapsedSecondsFromEnd() > 0 ) {
                mStartTime = System.nanoTime();
                mStarted = true;
            }
        }
    }

    private void createMeasurement() {
        Measurement measurement = null;

        if( getName() != null ) {
            measurement = Measurement
                    .find(Measurement.class, "name = ?", getName())
                    .get(0)
            ;
        }

        if( measurement == null ) {
            measurement = new Measurement(getTime(), getName());
        }

        measurement.save();
    }

    private float getTime() {
        return Utils.nanoTimeToMilliSeconds(mEndTime - mStartTime);
    }

    private long getElapsedSecondsFromStart() {
        return Utils.nanoTimeToSeconds(System.nanoTime() - mStartTime);
    }

    private long getElapsedSecondsFromEnd() {
        return Utils.nanoTimeToSeconds(System.nanoTime() - mEndTime);
    }

    private float getElapsedMillisecondsFromStart() {
        return Utils.nanoTimeToMilliSeconds(System.nanoTime() - mStartTime);
    }

    private void setTimeText(float value) {
        runOnUiThread(new UpdateTimeTextRunnable(value));
    }

    @Override
    public void onClick(View v) {
        if( v.equals(btnClearName) ) {
            etName.getText().clear();
        }
    }

    @Nullable
    private String getName() {
        String name = etName.getText().toString();

        return name.isEmpty() ? null : name;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mThread = new Thread(mThreadRunnable);
        mThread.start();

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();

        mThread.interrupt();

        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        handleSensorChanged(event.values);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class UpdateTimeTextRunnable implements Runnable {

        private DecimalFormat sDecimalFormat = new DecimalFormat("#.00");

        private float time;

        public UpdateTimeTextRunnable(float time) {
            this.time = time;
        }

        @Override
        public void run() {
            tvTime.setText(sDecimalFormat.format(time));
        }
    }
}
