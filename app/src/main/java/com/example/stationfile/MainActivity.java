package com.example.stationfile;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.example.stationfile.adapter.StationAdapter;
import com.example.stationfile.dialog.DeleteDialog;
import com.example.stationfile.dialog.StationDialog;
import com.example.stationfile.dialog.UpdateDialog;
import com.example.stationfile.entity.Simplified;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements StationDialog.NoticeDialogListener{

    ListView stationList = null;
    Button search, scan;
    FloatingActionButton add;
    EditText text;
    List<Simplified> data = null;
    MyDBHelper dbHelper;
    RefulshStateListenter  refulshLister;

    public static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void verifyStoragePermissions() {
        /*******below android 6.0*******/
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stationList = findViewById(R.id.stationList);
        search = findViewById(R.id.search);
        scan = findViewById(R.id.scan);
        text = findViewById(R.id.edit_text);
        add = findViewById(R.id.fab);

        verifyStoragePermissions();
        /*SQLiteStudioService.instance().start(this);*/
/*        MyDBHelper myDBHelper = new MyDBHelper(MainActivity.this);
        data = myDBHelper.queryAllStation();
        Log.e("ShadyPi", "getView: "+myDBHelper.queryAllStation().size());*/

        MyDBHelper myDbHelper = new MyDBHelper(this);
        dbHelper = myDbHelper;
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbHelper.openDataBase();

        /*
        * adpter、dialog、activity通过接口回调函数通讯*/
        refulshLister = new RefulshStateListenter() {
            @Override
            public void refush(Simplified s) {
                DeleteDialog deleteDialog = new DeleteDialog(refulshLister,s);
                deleteDialog.show(getSupportFragmentManager(),"delete");
            }
            @Override
            public void dialogCallback(Simplified s) {
                dbHelper.deleteDeviceByStation(s.getId());
                dbHelper.deleteIntervalByStationId(s.getId());
                dbHelper.deleteStation(s.getId());
                init();
            }
            @Override
            public void update(Simplified s) {
                UpdateDialog updateDialog = new UpdateDialog("修改",refulshLister,s);
                updateDialog.show(getSupportFragmentManager(),"update");
            }

            @Override
            public void updateCallback(Simplified s,String newName) {
                dbHelper.updateStation(s,newName);
                init();
            }
        };

        Intent intent = new Intent(this, StationActivity.class);
        stationList.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.e("1", "click:"+i);
            String s1 = data.get(i).getName();
            int s2 = data.get(i).getId();
            intent.putExtra("station",s1);
            intent.putExtra("id", String.valueOf(s2));
            startActivity(intent);
        });

        //条件查询
        search.setOnClickListener(v -> {
            String s = String.valueOf(text.getText());
            data = myDbHelper.queryByStationName(s);
            init();
        });

        //二维码查询
        scan.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
            intentIntegrator.initiateScan();
        });

        add.setOnClickListener(v -> {
            StationDialog stationDialog = new StationDialog("增加变电站");
            stationDialog.show(this.getSupportFragmentManager(),null);
        });

        init();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 获取解析结果
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "取消扫描", Toast.LENGTH_LONG).show();
            } else {
                /*Toast.makeText(this, "扫描内容:" + result.getContents(), Toast.LENGTH_LONG).show();*/
                String s = result.getContents();
                String r = s.substring(s.length()-6);
                Log.e("ShadyPi", "getView: "+r);
                Intent intent1 = new Intent(MainActivity.this, DeviceActivity.class);
                intent1.putExtra("SD_id",r);
                startActivity(intent1);
                /*String[] s1 = s.split(":");
                switch(s1[0]){
                    case "interval":
                        Intent intent = new Intent(MainActivity.this, IntervalActivity.class);
                        intent.putExtra("id",s1[1]);
                        startActivity(intent);
                        break;
                    case "device":
                        Intent intent1 = new Intent(MainActivity.this, DeviceActivity.class);
                        intent1.putExtra("id",s1[1]);
                        startActivity(intent1);
                        break;
                }*/
/*                Intent intent = new Intent(MainActivity.this, DeviceActivity.class);
                intent.putExtra("id",result.getContents());
                startActivity(intent);*/
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void init(){
        //渲染页面
        data = dbHelper.queryAllStation();
        StationAdapter myAdapter = new StationAdapter(data, MainActivity.this, refulshLister);
        stationList.setAdapter(myAdapter);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        /*回调函数更新数据库*/
        EditText res = Objects.requireNonNull(dialog.getDialog()).findViewById(R.id.update_name);
        dbHelper.insertStation(res.getText().toString());
        init();
    }

}