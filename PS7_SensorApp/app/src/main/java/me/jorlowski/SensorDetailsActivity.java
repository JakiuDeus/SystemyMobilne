package me.jorlowski;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class SensorDetailsActivity extends AppCompatActivity implements SensorEventListener {

    private Sensor sensor;
    private SensorManager sensorManager;
    private TextView sensorLabel;
    private TextView sensorType;
    private TextView sensorValues;
    private Button returnButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_details);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(getIntent().getIntExtra(SensorActivity.KEY_SENSOR, -1));
        sensorLabel = findViewById(R.id.sensor_label);
        sensorType = findViewById(R.id.sensor_type);
        sensorValues = findViewById(R.id.sensor_values);
        returnButton = findViewById(R.id.back_button);
        returnButton.setOnClickListener(l -> finish());
        if (sensor == null) {
            sensorLabel.setText(R.string.missing_sensor);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorTypeId = event.sensor.getType();
        float currentValue = event.values[0];
        sensorLabel.setText(event.sensor.getName());
        sensorType.setText(event.sensor.getStringType());
        sensorValues.setText(getResources().getString(R.string.sensor_value, currentValue));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}