package com.example.stationfile.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stationfile.MainActivity;
import com.example.stationfile.R;
import com.example.stationfile.RefulshStateListenter;
import com.example.stationfile.entity.Simplified;

import java.util.Objects;

public class UpdateDeviceDialog extends DialogFragment {
    private final String title;

    private RefulshStateListenter refulshLister;

    private Simplified simplified;

    public UpdateDeviceDialog(String title, RefulshStateListenter refulshLister, Simplified simplified){
        this.title = title;
        this.refulshLister = refulshLister;
        this.simplified = simplified;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title+simplified.getName())
                .setView(R.layout.device_dialog)
                // Add action buttons
                .setPositiveButton("确认", (dialog, id) -> {
                    EditText res = Objects.requireNonNull(this.getDialog()).findViewById(R.id.update_name);
                    EditText sd = Objects.requireNonNull(this.getDialog()).findViewById(R.id.SD_id);
                    //res.setText(simplified.getName());
                    simplified.setName(sd.getText().toString());
                    refulshLister.updateCallback(simplified,res.getText().toString());
                })
                .setNegativeButton("取消", (dialog, id) -> UpdateDeviceDialog.this.getDialog().cancel());
        return builder.create();
    }

}
