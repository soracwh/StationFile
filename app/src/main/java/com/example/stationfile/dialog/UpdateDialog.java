package com.example.stationfile.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stationfile.R;
import com.example.stationfile.RefulshStateListenter;
import com.example.stationfile.entity.Simplified;

import java.util.Objects;

public class UpdateDialog extends DialogFragment {

    private final String title;

    private RefulshStateListenter refulshLister;

    private Simplified simplified;
    private Context context;

    public UpdateDialog(String title, RefulshStateListenter refulshLister, Simplified simplified, Context context){
        this.title = title;
        this.refulshLister = refulshLister;
        this.simplified = simplified;
        this.context = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title+simplified.getName())
               .setView(R.layout.dialog_station)
                // Add action buttons
               .setPositiveButton("确认", (dialog, id) -> {
                    EditText res = Objects.requireNonNull(this.getDialog()).findViewById(R.id.update_name);
                    //res.setHint(simplified.getName());
                   if(res.getText().toString().length() == 0){
                       Toast.makeText(this.context,"请输入数据",Toast.LENGTH_SHORT).show();
                   }else {
                       refulshLister.updateCallback(simplified, res.getText().toString());
                   }
                })
                .setNegativeButton("取消", (dialog, id) -> UpdateDialog.this.getDialog().cancel());
        return builder.create();
    }
}
