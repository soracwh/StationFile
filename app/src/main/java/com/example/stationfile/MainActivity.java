package com.example.stationfile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.stationfile.adapter.StationAdapter;
import com.example.stationfile.entity.Simplified;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView stationList = null;
    Button search, scan, add;
    EditText text;
    List<Simplified> data = null;

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
        try {
            myDbHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        myDbHelper.openDataBase();

        data = myDbHelper.queryAllStation();
        init();

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

        });

/*        ddd = findViewById(R.id.ddd);
          ddd.setOnClickListener(v -> {
          MyDBHelper myDBHelper = new MyDBHelper(MainActivity.this,"electric.db",null,1);
          Log.e("ShadyPi", "getView: "+myDBHelper.queryAllStation().get(0).getName());
        });*/

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
/*
    if(data!=null||data.size()!=0){
            data.clear();
        }
        //数据库初始化
*/
        //渲染页面
        StationAdapter myAdapter = new StationAdapter(data,MainActivity.this);
        stationList.setAdapter(myAdapter);
    }

    /*private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

*//*        private final class  ViewHolder{
            Button button;
        }*//*

*//*        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null){
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.activity_main, viewGroup, false);
                viewHolder.button = view.findViewById(R.id.name);
                view.setTag(viewHolder);
            }
            else {
                viewHolder = (ViewHolder) view.getTag();
            }
            //TextView textview = view.findViewById(R.id.text);
            //textview.setText(data.get(i).getName());
            viewHolder.button.setText(name[position]);

            Log.e("ShadyPi", "getView: "+position);

            return view;
        }*//*


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this,R.layout.stationlayout,null);

            singleName = view.findViewById(R.id.name);
            singleName.setText(name[position]);
           *//* Intent intent = new Intent(MainActivity.this, StationActivity.class);
            singleName.setOnClickListener(v -> {
                s1 = singleName.getText().toString();
                intent.putExtra("station",s1);
                startActivity(intent);
            });*//*
            return view;
        }
    }*/

}