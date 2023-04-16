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
import com.example.stationfile.entity.Simplified;

import java.util.ArrayList;
import java.util.List;

public class IntervalActivity extends AppCompatActivity {

    private final String[] type= {"闸刀","开关","电流互感器","避雷器"};
    List<Simplified> data = new ArrayList<>();
    TextView singleName = null;

    EditText text;
    Button back,search = null;

    ListView TypeList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval);
        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("interval");
        String intervalId = intent.getStringExtra("id");

/*        for (int i = 0; i < type.length; i++) {
            Simplified simplified = new Simplified();
            simplified.setId(i);
            simplified.setName(type[i]);
            data.add(simplified);
        }*/

        MyDBHelper myDbHelper = new MyDBHelper(this);
        myDbHelper.openDataBase();
        data = myDbHelper.queryAllTypeByIntervalId(Integer.parseInt(intervalId));
        Simplified simplified = myDbHelper.queryByIntervalId(Integer.parseInt(intervalId));

        if (s1==null|| s1.equals("")){
            s1 = simplified.getName();
        }

        TextView textView = findViewById(R.id.intervalName);
        textView.setText(s1);

        TypeList = findViewById(R.id.TypeList);
        init();

        Intent intent2 = new Intent(this, TypeActivity.class);
        TypeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s1 = data.get(i).getName();
                int s2 = data.get(i).getId();
                intent2.putExtra("type",s1);
                intent2.putExtra("interval",intervalId);
                intent2.putExtra("id", String.valueOf(s2));
                startActivity(intent2);
            }
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
        StationAdapter myAdapter = new StationAdapter(data,IntervalActivity.this);
        TypeList.setAdapter(myAdapter);
    }


   /* private class deviceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return device.length;
        }

        @Override
        public Object getItem(int position) {
            return device[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(IntervalActivity.this,R.layout.stationlayout,null);
            singleName = view.findViewById(R.id.name);
            singleName.setText(device[position]);
*//*            Intent intent = new Intent(IntervalActivity.this, StationActivity.class);
            singleName.setOnClickListener(v -> {
                String s1 = singleName.getText().toString();
                intent.putExtra("station",s1);
                startActivity(intent);
            });*//*
            return view;
        }
    }*/
}