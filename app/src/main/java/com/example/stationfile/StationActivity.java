package com.example.stationfile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stationfile.adapter.StationAdapter;
import com.example.stationfile.entity.Simplified;

import java.util.ArrayList;
import java.util.List;

public class StationActivity extends AppCompatActivity {

    private String[] interval= {"#1主变","#2主变","xxxx线路","220kV母联"};
    List<Simplified> data = new ArrayList<>();

    private TextView singleName = null;
    EditText text;
    private Button back, search = null;

    ListView intervalList = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station);
        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("station");
        String stationId = intent.getStringExtra("id");
        TextView textView = findViewById(R.id.stationName);
        textView.setText(s1);

/*        for (int i = 0; i < interval.length; i++) {
            Simplified simplified = new Simplified();
            simplified.setId(i);
            simplified.setName(interval[i]);
            data.add(simplified);
        }*/

        MyDBHelper myDbHelper = new MyDBHelper(this);
        myDbHelper.openDataBase();
        data = myDbHelper.queryAllIntervalByStationId(Integer.parseInt(stationId));

        //列表
        intervalList = findViewById(R.id.intervalList);
        init();

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
    }

    private void init(){

        //渲染页面
        StationAdapter myAdapter = new StationAdapter(data,StationActivity.this);
        intervalList.setAdapter(myAdapter);
    }

/*    private class intervalAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return interval.length;
        }

        @Override
        public Object getItem(int position) {
            return interval[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(StationActivity.this,R.layout.stationlayout,null);
            singleName = view.findViewById(R.id.name);
            singleName.setText(interval[position]);
            *//*Intent intent = new Intent(StationActivity.this, IntervalActivity.class);
            singleName.setOnClickListener(v -> {
                String s1 = singleName.getText().toString();
                intent.putExtra("Interval",s1);
                startActivity(intent);
            });*//*
            return view;
        }
    }*/
}