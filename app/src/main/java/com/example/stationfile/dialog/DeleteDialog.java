package com.example.stationfile.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stationfile.R;
import com.example.stationfile.RefulshStateListenter;
import com.example.stationfile.entity.Simplified;

import java.util.Objects;


public class DeleteDialog extends DialogFragment {

    private RefulshStateListenter refulshLister;

    private Simplified simplified;
    public DeleteDialog (RefulshStateListenter refulshLister, Simplified simplified){
        this.refulshLister = refulshLister;
        this.simplified = simplified;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示框")
                .setMessage("确认删除"+simplified.getName())
                .setPositiveButton("确认", (dialog, id) -> {
                    refulshLister.dialogCallback(simplified);
                })
                .setNegativeButton("取消", (dialog, id) -> DeleteDialog.this.getDialog().cancel());
        return builder.create();
    }


}
