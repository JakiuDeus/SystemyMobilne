package me.jorlowski;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentActivity.*;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;

import org.w3c.dom.Text;

import java.util.List;

public class SensorActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        recyclerView = findViewById(R.id.sensor_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        if (adapter == null) {
            adapter = new SensorAdapter(sensorList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private class SensorHolder extends RecyclerView.ViewHolder {
        private Sensor sensor;
        private TextView sensorNameTextView;
        private ImageView sensorIconImageView;

        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));
            sensorNameTextView = itemView.findViewById(R.id.sensor_item_name);
            sensorIconImageView = itemView.findViewById(R.id.sensor_item_icon);
        }

        public void bind(Sensor sensor) {
            this.sensor = sensor;
            sensorNameTextView.setText(sensor.getName());
            sensorIconImageView.setImageResource(R.drawable.ic_launcher_foreground);
        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder> {

        public SensorAdapter(List<Sensor> sensorList) {

        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}