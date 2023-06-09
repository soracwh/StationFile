package com.example.stationfile;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stationfile.adapter.DefectAdapter;
import com.example.stationfile.adapter.MeasureAdapter;
import com.example.stationfile.adapter.RepairImformationAdapter;
import com.example.stationfile.entity.Defect;
import com.example.stationfile.entity.Device;
import com.example.stationfile.entity.Measure;
import com.example.stationfile.entity.RepairRecord;

import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends AppCompatActivity implements View.OnClickListener {

    Button back;
    List<RepairRecord> data = new ArrayList<>();

    List<Defect> defects = new ArrayList<>();

    List<Measure> measures =new ArrayList<>();

    MyDBHelper dbHelper;

    ImageView addRepair,addMeasure,addDefect;

    Device device;
    ActivityResultLauncher launcher;

    ListView repairList, measureList, defectList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent intent = this.getIntent();
        MyDBHelper myDbHelper = new MyDBHelper(this);
        myDbHelper.openDataBase();
        dbHelper = myDbHelper;
        addRepair = findViewById(R.id.repair_add);
        addMeasure = findViewById(R.id.measure_add);
        addDefect = findViewById(R.id.defect_add);
        addDefect.setOnClickListener(this);
        addMeasure.setOnClickListener(this);
        addRepair.setOnClickListener(this);
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        assert result.getData() != null;
                        Toast.makeText(DeviceActivity.this,result.getData().getStringExtra("data_return"),Toast.LENGTH_SHORT).show();
                        initRepair();
                        initDefect();
                        initMeasure();
                    }
                });
        measureList = findViewById(R.id.imformationList3);
        defectList = findViewById(R.id.imformationList2);
        repairList = findViewById(R.id.imformationList1);

        View view = findViewById(R.id.backcolor);
        device = new Device();
        int deviceId;

        if(intent.getStringExtra("SD_id")!=null){
            device = myDbHelper.queryDeviceBySDId(Integer.parseInt(intent.getStringExtra("SD_id")));
            deviceId = device.getId();
        }else{
             deviceId = Integer.parseInt(intent.getStringExtra("id"));
             device = myDbHelper.queryDeviceById(deviceId);
        }

        /*Device device = myDbHelper.queryDeviceById(deviceId);*/
        String s1 = device.getName();
        String station = myDbHelper.queryByStationId(device.getStationId()).getName();
        String interval = myDbHelper.queryByIntervalId(device.getIntervalId()).getName();

        switch (device.getState()){
            case 2:
                view.setBackgroundColor(Color.parseColor("#FFEB3B"));
                break;
            case 3:
                view.setBackgroundColor(Color.parseColor("#E31809"));
                break;
            default:
                view.setBackgroundColor(Color.parseColor("#70F176"));
                break;
        }

        TextView textView = findViewById(R.id.deviceName);
        textView.setText(s1);
        TextView stationName = findViewById(R.id.station);
        stationName.setText(station);
        TextView intervalName = findViewById(R.id.interval);
        intervalName.setText(interval);

        //Log.e("ShadyPi", "getRecord: "+data.size());
        //初始化
        initRepair();
        initDefect();
        initMeasure();

        repairList.setOnItemClickListener((adapterView, v, i, l) -> {
            Intent intent1 = new Intent(this,AddRepairActivity.class);
            intent1.putExtra("repairId",String.valueOf(data.get(i).getId()));
            intent1.putExtra("deviceId",String.valueOf(deviceId));
            launcher.launch(intent1);
        });


        defectList.setOnItemClickListener((adapterView, v, i, l) -> {
            Intent intent1 = new Intent(this,AddDefectActivity.class);
            intent1.putExtra("defectId",String.valueOf(defects.get(i).getId()));
            intent1.putExtra("deviceId",String.valueOf(deviceId));
            launcher.launch(intent1);
        });

        measureList.setOnItemClickListener((adapterView, v, i, l) -> {
            Intent intent1 = new Intent(this,AddDeviceActivity.class);
            intent1.putExtra("measureId",String.valueOf(measures.get(i).getId()));
            intent1.putExtra("deviceId",String.valueOf(deviceId));
            launcher.launch(intent1);
        });
        /*动态修改长度*/
/*        View repairArea = findViewById(R.id.repair_area);
        ViewGroup.LayoutParams params = imformationList1.getLayoutParams();
        *//*setListViewHeightBasedOnChildren(imformationList1);*//*
        ViewGroup.LayoutParams params1 = repairArea.getLayoutParams();
        if(dip2px(this,350) < setListViewHeightBasedOnChildren(imformationList1)){
            params.height = dip2px(this,350);
            params1.height = dip2px(this,400);
            imformationList1.setLayoutParams(params);
            repairArea.setLayoutParams(params1);
        }*/


        int count1 = 0;
        for (int i = 0; i < measures.size(); i++) {
            if(measures.get(i).getFlag()==0){
                count1++;
            }
        }
        TextView measureNum = findViewById(R.id.measure_num);
        measureNum.setText(""+count1);

        int count2 = 0;
        for (int i = 0; i < defects.size(); i++) {
            if(defects.get(i).getFlag()==0){
                count2++;
            }
        }
        TextView defectNum = findViewById(R.id.defect_num);
        defectNum.setText(""+count2);

        //返回
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });


    }

    private void initMeasure() {
        /*反措*/
        measures = dbHelper.queryMeasureByDeviceId(device.getId());
        View measureArea = findViewById(R.id.show3);
        if(measures==null||measures.size()==0){
            measureArea.setBackgroundColor(Color.parseColor("#e6e2e2"));
            measureArea.setVisibility(View.GONE);
        }else{
            measureArea.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            MeasureAdapter measureAdapter = new MeasureAdapter(measures,DeviceActivity.this);
            measureList.setAdapter(measureAdapter);
        }
    }

    private void initDefect() {
        /*缺陷*/
        defects = dbHelper.queryDefectByDeviceId(device.getId());
        View defectArea = findViewById(R.id.show2);
        if(defects==null||defects.size()==0){
            defectArea.setBackgroundColor(Color.parseColor("#e6e2e2"));
            defectArea.setVisibility(View.GONE);
        }else{
            defectArea.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            DefectAdapter defectAdapter = new DefectAdapter(defects,DeviceActivity.this);
            defectList.setAdapter(defectAdapter);
        }
    }

    private void initRepair() {
        View repairArea = findViewById(R.id.show1);
        data = dbHelper.queryRepairByDeviceId(device.getId());
        if(data==null||data.size()==0){
            repairArea.setBackgroundColor(Color.parseColor("#e6e2e2"));
            repairArea.setVisibility(View.GONE);
        }else {
            repairArea.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            RepairImformationAdapter adapter = new RepairImformationAdapter(data,DeviceActivity.this);
            repairList.setAdapter(adapter);
        }
    }

    private int dip2px(Context context, float dpValue) {
        // 获取当前手机的像素密度（1个dp对应几个px）

        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f); // 四舍五入取整

    }

    public int setListViewHeightBasedOnChildren(ListView listView1) {
        RepairImformationAdapter listAdapter = (RepairImformationAdapter) listView1.getAdapter();
        if (listAdapter == null) {
            return 0;
        }
        int totalHeight = 0;
        //获取listView的宽度
        ViewGroup.LayoutParams params = listView1.getLayoutParams();

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView1);
            //给item的measure设置参数是listView的宽度就可以获取到真正每一个item的高度
            totalHeight += listItem.getMeasuredHeight();
        }

/*        params.height = totalHeight
                + (listView1.getDividerHeight() * (listAdapter.getCount() + 1));
        listView1.setLayoutParams(params);*/
        return  totalHeight;
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.repair_add:
                Intent intent1 =new Intent(this,AddRepairActivity.class);
                intent1.putExtra("deviceId",String.valueOf(device.getId()));
                launcher.launch(intent1);
                break;
            case R.id.defect_add:
                Intent intent2 = new Intent(this,AddDefectActivity.class);
                intent2.putExtra("deviceId",String.valueOf(device.getId()));
                launcher.launch(intent2);
                break;
            case R.id.measure_add:
                Intent intent3 = new Intent(this,AddDeviceActivity.class);
                intent3.putExtra("deviceId",String.valueOf(device.getId()));
                launcher.launch(intent3);
                break;
        }
    }
}