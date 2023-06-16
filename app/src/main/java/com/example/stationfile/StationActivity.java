package com.example.stationfile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.stationfile.adapter.StationAdapter;
import com.example.stationfile.dialog.DeleteDialog;
import com.example.stationfile.dialog.StationDialog;
import com.example.stationfile.dialog.UpdateDialog;
import com.example.stationfile.entity.Interval;
import com.example.stationfile.entity.Simplified;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StationActivity extends AppCompatActivity implements StationDialog.NoticeDialogListener{

    private String[] interval= {"#1主变","#2主变","xxxx线路","220kV母联"};
    List<Simplified> data = new ArrayList<>();

    private TextView singleName = null;
    EditText text;
    private Button back, search = null;
    FloatingActionButton add;
    ListView intervalList = null;

    MyDBHelper dbHelper;
    String stationId;

    private RefulshStateListenter refulshLister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("station");
        stationId = intent.getStringExtra("id");
        TextView textView = findViewById(R.id.stationName);
        textView.setText(s1);

        MyDBHelper myDbHelper = new MyDBHelper(this);
        dbHelper = myDbHelper;
        myDbHelper.openDataBase();
        //data = myDbHelper.queryAllIntervalByStationId(Integer.parseInt(stationId));
        //列表
        intervalList = findViewById(R.id.intervalList);

        Intent intent1 = new Intent(this,IntervalActivity.class);
        intervalList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s1 = data.get(i).getName();
                int s2 = data.get(i).getId();
                intent1.putExtra("interval",s1);
                intent1.putExtra("id", String.valueOf(s2));
                Log.e("2", "click:"+s1);
                startActivity(intent1);
            }
        });

        refulshLister = new RefulshStateListenter() {
            @Override
            public void refush(Simplified s) {
                DeleteDialog deleteDialog = new DeleteDialog(refulshLister,s);
                deleteDialog.show(getSupportFragmentManager(),"delete");
            }
            @Override
            public void dialogCallback(Simplified s) {
                Interval interval1 = myDbHelper.queryAllByIntervalId(s.getId());
                myDbHelper.deleteDeviceByInterval(interval1.getIntervalId());
                myDbHelper.deleteInterval(interval1.getIntervalId());
                init();
            }
            @Override
            public void update(Simplified s) {
                UpdateDialog updateDialog = new UpdateDialog("修改",refulshLister,s,StationActivity.this);
                updateDialog.show(getSupportFragmentManager(),"update");
            }

            @Override
            public void updateCallback(Simplified s,String newName) {
                Interval interval1 = myDbHelper.queryAllByIntervalId(s.getId());
                System.out.println(newName);
                myDbHelper.updateInterval(interval1,newName);
                init();
            }
        };

        //返回
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });

        search = findViewById(R.id.search);
        text = findViewById(R.id.edit_text);
        search.setOnClickListener(v -> {
            String s = String.valueOf(text.getText());
            data = myDbHelper.queryByIntervalName(s);
            init();
        });

        add = findViewById(R.id.add_interval);
        add.setOnClickListener(v -> {
            StationDialog stationDialog = new StationDialog("增加间隔",this);
            stationDialog.show(this.getSupportFragmentManager(),null);
        });
        init();
    }

    private void init(){

        //渲染页面
        data = dbHelper.queryAllIntervalByStationId(Integer.parseInt(stationId));
        StationAdapter myAdapter = new StationAdapter(data,StationActivity.this,refulshLister);
        intervalList.setAdapter(myAdapter);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText res = Objects.requireNonNull(dialog.getDialog()).findViewById(R.id.update_name);
        Interval interval1 = new Interval();
        interval1.setStationId(Integer.parseInt(stationId));
        interval1.setName(res.getText().toString());
        dbHelper.insertInterval(interval1);
        init();
    }
}