package me.jorlowski;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SensorActivity extends AppCompatActivity {

    private static final String KEY_SUBTITLE_SET = "keySubtitleSet";
    public static final String KEY_SENSOR = "keySensor";
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private RecyclerView recyclerView;
    private SensorAdapter adapter;
    private boolean countVisible = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            countVisible = savedInstanceState.getBoolean(KEY_SUBTITLE_SET);
        }
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
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SUBTITLE_SET, countVisible);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sensor_menu, menu);
        MenuItem sensorsCount = menu.findItem(R.id.sensors_count_show);
        if (countVisible) {
            sensorsCount.setTitle(R.string.hide_subtitle);
        } else {
            sensorsCount.setTitle(R.string.show_subtitle);
        }
        updateSubtitle();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.sensors_count_show) {
            countVisible = !countVisible;
            invalidateOptionsMenu();
            updateSubtitle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void updateSubtitle() {
        String subtitle = getString(R.string.sensors_count, sensorList.size());
        if (!countVisible) {
            subtitle = null;
        }
        getSupportActionBar().setSubtitle(subtitle);
    }

    private class SensorHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private Sensor sensor;
        private TextView sensorNameTextView;
        private ImageView sensorIconImageView;
        public SensorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sensor_list_item, parent, false));
            itemView.setOnLongClickListener(this);
            sensorNameTextView = itemView.findViewById(R.id.sensor_item_name);
            sensorIconImageView = itemView.findViewById(R.id.sensor_item_icon);
        }


        public void bind(Sensor sensor) {
            this.sensor = sensor;
            if (sensor.getType() == Sensor.TYPE_GRAVITY || sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR || sensor.getType() == Sensor.TYPE_LIGHT) {
                itemView.setBackgroundResource(R.drawable.premium_border_layout);
                itemView.setOnClickListener(this);
            } else if (sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
                itemView.setBackgroundResource(R.drawable.premium_border_layout);
                itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(SensorActivity.this, LocationActivity.class);
                    intent.putExtra(KEY_SENSOR, sensor.getType());
                    startActivity(intent);
                });
            } else {
                itemView.setBackgroundResource(R.drawable.border_layout);
                itemView.setOnClickListener(null);
            }
            sensorNameTextView.setText(sensor.getName());
            sensorIconImageView.setImageResource(R.drawable.ic_launcher_foreground);
        }

        @Override
        public boolean onLongClick(View v) {
            DialogFragment dialogFragment = new SensorDialogFragment(sensor);
            dialogFragment.show(getSupportFragmentManager(), null);
            return true;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(SensorActivity.this, SensorDetailsActivity.class);
            intent.putExtra(KEY_SENSOR, sensor.getType());
            startActivity(intent);
        }
    }

    private class SensorAdapter extends RecyclerView.Adapter<SensorHolder> {

        private List<Sensor> sensorList;
        public SensorAdapter(List<Sensor> sensorList) {
            this.sensorList = sensorList;
        }

        @NonNull
        @Override
        public SensorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(SensorActivity.this);
            return new SensorHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull SensorHolder holder, int position) {
            Sensor sensor = sensorList.get(position);
            holder.bind(sensor);
        }

        @Override
        public int getItemCount() {
            return sensorList.size();
        }
    }
}