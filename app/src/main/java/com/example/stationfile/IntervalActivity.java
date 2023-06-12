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

import com.example.stationfile.adapter.StationAdapter;
import com.example.stationfile.adapter.TypeAdapter;
import com.example.stationfile.entity.Simplified;

import java.util.ArrayList;
import java.util.List;

public class IntervalActivity extends AppCompatActivity {

    List<Simplified> data = new ArrayList<>();
    TextView singleName = null;

    EditText text;
    Button back,search = null;

    ListView TypeList = null;

    private RefulshStateListenter refulshLister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);
        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("interval");
        String intervalId = intent.getStringExtra("id");


        MyDBHelper myDbHelper = new MyDBHelper(this);
        myDbHelper.openDataBase();
        data = myDbHelper.queryAllType();
        Simplified simplified = myDbHelper.queryByIntervalId(Integer.parseInt(intervalId));

        if (s1==null|| s1.equals("")){
            s1 = simplified.getName();
        }

        TextView textView = findViewById(R.id.intervalName);
        textView.setText(s1);

        TypeList = findViewById(R.id.TypeList);
        init();

        Intent intent2 = new Intent(this, TypeActivity.class);

        TypeList.setOnItemClickListener((adapterView, view, i, l) -> {
            String s11 = data.get(i).getName();
            int s12 = data.get(i).getId();
            intent2.putExtra("type", s11);
            intent2.putExtra("intervalId",intervalId);
            intent2.putExtra("typeId", String.valueOf(s12));
            startActivity(intent2);
        });

        //返回
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });

        //条件查询
        search = findViewById(R.id.search);
        text = findViewById(R.id.edit_text);
        search.setOnClickListener(v -> {
            String s = String.valueOf(text.getText());
            data = myDbHelper.queryByTypeName(s);
            init();
        });
    }

    private void init(){

        //渲染页面
        TypeAdapter myAdapter = new TypeAdapter(data,IntervalActivity.this);
        TypeList.setAdapter(myAdapter);
    }

}