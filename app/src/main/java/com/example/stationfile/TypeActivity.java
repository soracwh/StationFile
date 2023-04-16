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

public class TypeActivity extends AppCompatActivity {

    private final String[] device= {"220kV正母闸刀","220kV副母闸刀","220kV主变闸刀","220kV主变中性点地刀"};

    Button back,search = null;

    List<Simplified> data = new ArrayList<>();

    ListView deviceList = null;

    EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        Intent intent = this.getIntent();
        String s1 = intent.getStringExtra("type");
        String intervalId = intent.getStringExtra("interval");
        String typeId = intent.getStringExtra("id");
        TextView textView = findViewById(R.id.intervalName);
        textView.setText(s1);

/*        for (int i = 0; i < device.length; i++) {
            Simplified simplified = new Simplified();
            simplified.setId(i);
            simplified.setName(device[i]);
            data.add(simplified);
        }*/

        MyDBHelper myDbHelper = new MyDBHelper(this);
        myDbHelper.openDataBase();
        data = myDbHelper.queryDeviceByTypeId(Integer.parseInt(typeId),Integer.parseInt(intervalId));


        deviceList = findViewById(R.id.deviceList);
        init();

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
            data = myDbHelper.queryByDeviceNameAndTypeId(s,Integer.parseInt(typeId));
            init();
        });
    }

    private void init(){
        StationAdapter myAdapter = new StationAdapter(data,TypeActivity.this);
        deviceList.setAdapter(myAdapter);
    }
}