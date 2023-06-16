package com.example.stationfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.stationfile.adapter.StationAdapter;
import com.example.stationfile.dialog.AddDeviceDialog;
import com.example.stationfile.dialog.DeleteDialog;
import com.example.stationfile.dialog.StationDialog;
import com.example.stationfile.dialog.UpdateDeviceDialog;
import com.example.stationfile.dialog.UpdateDialog;
import com.example.stationfile.entity.Device;
import com.example.stationfile.entity.Interval;
import com.example.stationfile.entity.Simplified;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TypeActivity extends AppCompatActivity implements AddDeviceDialog.NoticeDialogListener{

    Button back,search = null;
    List<Simplified> data = new ArrayList<>();
    ListView deviceList = null;
    EditText text;
    FloatingActionButton add;
    private RefulshStateListenter refulshLister;
    MyDBHelper dbHelper;
    int typeId, intervalId;
    Device deviceInfo  = new Device();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("type");
        intervalId = Integer.parseInt(intent.getStringExtra("intervalId"));
        typeId = Integer.parseInt(intent.getStringExtra("typeId"));
        TextView textView = findViewById(R.id.intervalName);
        textView.setText(s1);
        add = findViewById(R.id.add_device);

        MyDBHelper myDbHelper = new MyDBHelper(this);
        myDbHelper.openDataBase();
        dbHelper = myDbHelper;
        //data = myDbHelper.queryDeviceByTypeId(Integer.parseInt(typeId),Integer.parseInt(intervalId));
        deviceList = findViewById(R.id.deviceList);
        //获取该间隔下类型的站点和间隔信息，因为类型不和站点绑定
        Interval interval = myDbHelper.queryAllByIntervalId(intervalId);
        deviceInfo.setTypeId(typeId);
        deviceInfo.setIntervalId(intervalId);
        deviceInfo.setStationId(interval.getStationId());

        refulshLister = new RefulshStateListenter() {
            @Override
            public void refush(Simplified s) {
                DeleteDialog deleteDialog = new DeleteDialog(refulshLister,s);
                deleteDialog.show(getSupportFragmentManager(),"delete");
            }
            @Override
            public void dialogCallback(Simplified s) {
                dbHelper.deleteDevice(s.getId());
                init();
            }

            @Override
            public void update(Simplified s) {
                UpdateDeviceDialog updateDialog = new UpdateDeviceDialog("修改",refulshLister,s,TypeActivity.this);
                updateDialog.show(getSupportFragmentManager(),"update");
            }

            @Override
            public void updateCallback(Simplified s, String newName) {
                deviceInfo.setName(newName);
                deviceInfo.setId(s.getId());
                deviceInfo.setSD_id(Integer.parseInt(s.getName()));
                System.out.println(deviceInfo.getName());
                System.out.println(deviceInfo.getId());
                System.out.println(deviceInfo.getSD_id());
                dbHelper.updateDevice(deviceInfo);
                init();
            }
        };

        Intent intent2 = new Intent(this, DeviceActivity.class);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s1 = data.get(i).getName();
                int s2 = data.get(i).getId();
                intent2.putExtra("device",s1);
                intent2.putExtra("id", String.valueOf(s2));
                startActivity(intent2);
            }
        });

        //返回
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });

        search = findViewById(R.id.search);
        text = findViewById(R.id.edit_text);
        search.setOnClickListener(v -> {
            String s = String.valueOf(text.getText());
            data = myDbHelper.queryByDeviceNameAndTypeId(s,typeId);
            init();
        });
        add.setOnClickListener(v -> {
            AddDeviceDialog stationDialog = new AddDeviceDialog("增加设备",this);
            stationDialog.show(this.getSupportFragmentManager(),null);
        });

        init();
    }

    private void init(){
        data = dbHelper.queryDeviceByTypeId(typeId,intervalId);
        StationAdapter myAdapter = new StationAdapter(data,TypeActivity.this,refulshLister);
        deviceList.setAdapter(myAdapter);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText res = Objects.requireNonNull(dialog.getDialog()).findViewById(R.id.update_name);
        EditText Sd = dialog.getDialog().findViewById(R.id.SD_id);
        deviceInfo.setName(res.getText().toString());
        deviceInfo.setSD_id(Integer.parseInt(Sd.getText().toString()));
        dbHelper.insertDevice(deviceInfo);
        init();
    }
}