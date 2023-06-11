package com.example.stationfile.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.stationfile.MainActivity;
import com.example.stationfile.R;

public class StationDialog extends DialogFragment {

    private String title;

    public StationDialog(String title){
        this.title = title;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setView(R.layout.dialog_station)
                // Add action buttons
                .setPositiveButton("确认", (dialog, id) -> {
                    listener.onDialogPositiveClick(StationDialog.this);
                })
                .setNegativeButton("取消", (dialog, id) -> StationDialog.this.getDialog().cancel());
        return builder.create();
    }

    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
    }
    NoticeDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (NoticeDialogListener) context;

        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(MainActivity.class
                    + " must implement NoticeDialogListener");
        }
    }
}
