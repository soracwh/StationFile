package com.example.stationfile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stationfile.entity.Defect;
import com.example.stationfile.entity.Device;
import com.example.stationfile.entity.Measure;
import com.example.stationfile.entity.RepairRecord;

public class AddDeviceActivity extends AppCompatActivity implements View.OnClickListener {
    Device device;
    MyDBHelper dbHelper;
    TextView title;
    ImageView back;
    Button delete,post;
    Intent intent;
    int measureId;
    boolean updateFlag;
    EditText person,content,time,target;
    int complete = 2;

    RadioGroup groupComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
        dbHelper = new MyDBHelper(this);
        dbHelper.openDataBase();
        intent = this.getIntent();
        int deviceId = Integer.parseInt(intent.getStringExtra("deviceId"));
        device = dbHelper.queryDeviceById(deviceId);

        title = findViewById(R.id.d_title);
        content = findViewById(R.id.m_content);
        person = findViewById(R.id.d_person);
        time = findViewById(R.id.d_time);
        target = findViewById(R.id.m_measure);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        post = findViewById(R.id.post);

        if(intent.getStringExtra("measureId") != null){
            //Log.e("Id", String.valueOf(repairId));
            measureId = Integer.parseInt(intent.getStringExtra("measureId"));
            Measure measure = dbHelper.queryMeasureById(measureId);
            title.setText(device.getName()+"修改反措记录");
            content.setText(measure.getContent());
            person.setText(measure.getPerson());
            time.setText(measure.getDate());
            target.setText(measure.getTarget());
            complete = measure.getFlag();
            RadioButton com1 = findViewById(R.id.radio_1);
            RadioButton com2 = findViewById(R.id.radio_2);
            if(complete == 1){
                com1.setChecked(true);
            }else {
                com2.setChecked(true);
            }
            updateFlag = true;
        }else{
            title.setText("增加反措记录");
        }

        groupComplete = findViewById(R.id.group1);
        groupComplete.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.radio_1){
                complete = 1;
            }else{
                complete =0;
            }
        });

        back.setOnClickListener(this);
        delete.setOnClickListener(this);
        post.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.delete:
                if(updateFlag){
                    Intent intent2 = new Intent();
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(this);
                    deleteDialog.setTitle("确认删除该记录").setPositiveButton("确认",(dialog, id) -> {
                        intent2.putExtra("data_return","成功删除该条数据");
                        dbHelper.deleteMeasure(measureId);
                        setResult(RESULT_OK,intent2);
                        finish();
                    }).setNegativeButton("取消", (dialog, id) -> {});
                    deleteDialog.show();
                }else{
                    Toast.makeText(AddDeviceActivity.this,"你不能删除一个正在新增的项目，请选择返回",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.post:
                Intent intent1 = new Intent();
                intent1.putExtra("data_return","已更新数据库");
                if(content.getText().toString().length() == 0){
                    Toast.makeText(this,"请输入反措内容",Toast.LENGTH_SHORT).show();
                } else if (target.getText().toString().length() == 0) {
                    Toast.makeText(this,"请输入措施",Toast.LENGTH_SHORT).show();
                } else if (complete == 2) {
                    Toast.makeText(this,"请选择是否完成",Toast.LENGTH_SHORT).show();
                } else if(complete == 1 && person.getText().toString().length() == 0){
                    Toast.makeText(this,"请输入负责人",Toast.LENGTH_SHORT).show();
                }else if(complete == 1 && time.getText().toString().length() == 0){
                    Toast.makeText(this,"请输入检修时间",Toast.LENGTH_SHORT).show();
                } else if (complete == 0 && (time.getText().toString().length() != 0||time.getText().toString().length() != 0)) {
                    Toast.makeText(this,"请选择已完成",Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
                    confirmDialog.setTitle("确认提交").setPositiveButton("确认",(dialog, id) -> {
                        Measure measure = new Measure();
                        measure.setContent(content.getText().toString());
                        measure.setPerson(person.getText().toString());
                        measure.setDate(time.getText().toString());
                        measure.setFlag(complete);
                        measure.setDeviceId(device.getId());
                        measure.setTarget(target.getText().toString());
                        Log.e("record",measure.getContent());
                        if(updateFlag){
                            measure.setId(measureId);
                            dbHelper.updateMeasure(measure);
                        }else{
                            dbHelper.insertMeasure(measure);
                        }
                        setResult(RESULT_OK,intent1);
                        finish();
                    }).setNegativeButton("取消", (dialog, id) -> {});
                    confirmDialog.show();
                }
                break;
        }
    }
}