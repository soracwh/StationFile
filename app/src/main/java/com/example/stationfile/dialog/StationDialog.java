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

import com.example.stationfile.MainActivity;
import com.example.stationfile.R;

import java.util.Objects;

public class StationDialog extends DialogFragment {

    private String title;
    private Context context;

    public StationDialog(String title,Context context){
        this.title = title;
        this.context = context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setView(R.layout.dialog_station)
                // Add action buttons
                .setPositiveButton("确认", (dialog, id) -> {
                    EditText res = Objects.requireNonNull(this.getDialog()).findViewById(R.id.update_name);
                    if(res.getText().toString().length() == 0){
                        Toast.makeText(this.context,"请输入数据",Toast.LENGTH_SHORT).show();
                    }else{
                        listener.onDialogPositiveClick(StationDialog.this);
                    }
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
