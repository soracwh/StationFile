package com.example.stationfile;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stationfile.adapter.DefectAdapter;
import com.example.stationfile.adapter.MeasureAdapter;
import com.example.stationfile.adapter.RepairImformationAdapter;
import com.example.stationfile.entity.Defect;
import com.example.stationfile.entity.Device;
import com.example.stationfile.entity.Measure;
import com.example.stationfile.entity.RepairRecord;


import java.util.ArrayList;
import java.util.List;

public class DeviceActivity extends AppCompatActivity {

    Button back;
    List<RepairRecord> data = new ArrayList<>();

    List<Defect> defects = new ArrayList<>();

    List<Measure> measures =new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent intent = this.getIntent();

        View view = findViewById(R.id.backcolor);
        String deviceId = intent.getStringExtra("id");
        MyDBHelper myDbHelper = new MyDBHelper(this);
        myDbHelper.openDataBase();
        Device device = myDbHelper.queryDeviceById(Integer.parseInt(deviceId));
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
                view.setBackgroundColor(Color.parseColor("#03A9F4"));
                break;
        }

        TextView textView = findViewById(R.id.deviceName);
        textView.setText(s1);
        TextView stationName = findViewById(R.id.station);
        stationName.setText(station);
        TextView intervalName = findViewById(R.id.interval);
        intervalName.setText(interval);



        /*extracted(device);*/

        data = myDbHelper.queryRepairByDeviceId(Integer.parseInt(deviceId));
        Log.e("ShadyPi", "getRecord: "+data.size());

        /*缺陷*/
        defects = myDbHelper.queryDefectByDeviceId(Integer.parseInt(deviceId));
        ListView defectList = findViewById(R.id.imformationList2);
        DefectAdapter defectAdapter = new DefectAdapter(defects,DeviceActivity.this);
        defectList.setAdapter(defectAdapter);

        ListView imformationList1 = findViewById(R.id.imformationList1);
        RepairImformationAdapter adapter = new RepairImformationAdapter(data,DeviceActivity.this);
        imformationList1.setAdapter(adapter);
        /*反措*/
        measures = myDbHelper.queryMeasureByDeviceId(Integer.parseInt(deviceId));
        if(measures==null||measures.size()==0){
            View measureArea = findViewById(R.id.measure_area);
            measureArea.setVisibility(View.GONE);
        }else{
            ListView measureList = findViewById(R.id.imformationList3);
            MeasureAdapter measureAdapter = new MeasureAdapter(measures,DeviceActivity.this);
            measureList.setAdapter(measureAdapter);
        }


        TextView measureNum = findViewById(R.id.measure_num);
        measureNum.setText(""+measures.size());

        int count = 0;
        for (int i = 0; i < defects.size(); i++) {
            if(defects.get(i).getFlag()==0){
                count++;
            }
        }
        TextView defectNum = findViewById(R.id.defect_num);
        defectNum.setText(""+count);

        //返回
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void extracted(Device device) {
/*        TextView supplier = findViewById(R.id.supplier);
        supplier.setText(device.getSupplier());
        TextView basicInfo = findViewById(R.id.basicInfo);
        basicInfo.setText(device.getBasicInfo());*/
/*        TextView measureNum = findViewById(R.id.measure_num);
        measureNum.setText(device.getTime_start());
        TextView defectNum = findViewById(R.id.defect_num);
        defectNum.setText(device.getTimeRepair());*/

        /*v0.0.1*/
/*        TextView time_last = findViewById(R.id.time_last);
        time_last.setText(device.getTime_last());
        TextView blackSpot = findViewById(R.id.blackSpot);
        blackSpot.setText(device.getBlackSpot());
        TextView defect = findViewById(R.id.defect);
        defect.setText(device.getDefect());
        TextView imDefect = findViewById(R.id.imDefect);
        imDefect.setText(String.valueOf(device.getImDefect()));
        TextView noDefect = findViewById(R.id.noDefect);
        noDefect.setText(String.valueOf(device.getNoDefect()));
        TextView greater = findViewById(R.id.greater);
        greater.setText(String.valueOf(device.getGreater()));
        TextView repair = findViewById(R.id.repair);
        repair.setText(device.getRepair());
        TextView otherRepair = findViewById(R.id.otherRepair);
        otherRepair.setText(device.getOtherRepair());
        TextView swTime = findViewById(R.id.swTime);
        swTime.setText(String.valueOf(device.getSwTime()));
        TextView transfer = findViewById(R.id.transfer);
        transfer.setText(device.getTransfer());
        TextView transToEarth = findViewById(R.id.transToEarth);
        transToEarth.setText(device.getTransToEarth());
        TextView oil = findViewById(R.id.oil);
        oil.setText(device.getOil());
        TextView transTrip = findViewById(R.id.transTrip);
        transTrip.setText(device.getTransTrip());
        TextView timeRepair = findViewById(R.id.timeRepair);
        timeRepair.setText(device.getTimeRepair());
        TextView contentRepair = findViewById(R.id.contentRepair);
        contentRepair.setText(device.getContentRepair());
        TextView person = findViewById(R.id.person);
        person.setText(device.getPerson());
        TextView file1 = findViewById(R.id.file1);
        file1.setText(device.getFile1());
        TextView file2 = findViewById(R.id.file2);
        file2.setText(device.getFile2());
        TextView file3 = findViewById(R.id.file3);
        file3.setText(device.getFile3());
        TextView file4 = findViewById(R.id.file4);
        file4.setText(device.getFile4());
        TextView checkAccept = findViewById(R.id.checkAccept);
        checkAccept.setText(device.getCheckAccept());
        TextView exPerson = findViewById(R.id.ex_person);
        exPerson.setText(device.getExPerson());
        TextView startPerson = findViewById(R.id.start_person);
        startPerson.setText(device.getStartPerson());*/
    }

}