package me.jorlowski;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SensorDialogFragment extends DialogFragment {
    private Sensor sensor;
    public SensorDialogFragment(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(sensor.getName());
        builder.setMessage(getString(R.string.sensor_info, sensor.getVendor(), sensor.getMaximumRange()));
        builder.setPositiveButton(R.string.close, (dialog, which) -> {});

        return builder.create();
    }
}
