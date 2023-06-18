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
import com.example.stationfile.entity.RepairRecord;

public class AddDefectActivity extends AppCompatActivity implements View.OnClickListener {

    Device device;
    MyDBHelper dbHelper;
    TextView title;
    ImageView back;
    Button delete,post;
    Intent intent;
    int defectId;
    boolean updateFlag;
    EditText person,content,time;
    RadioGroup groupLevel,groupComplete;
    int complete = 2, level = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_defect);
        dbHelper = new MyDBHelper(this);
        dbHelper.openDataBase();
        intent = this.getIntent();
        int deviceId = Integer.parseInt(intent.getStringExtra("deviceId"));
        device = dbHelper.queryDeviceById(deviceId);

        title = findViewById(R.id.d_title);
        content = findViewById(R.id.m_content);
        person = findViewById(R.id.d_person);
        time = findViewById(R.id.d_time);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);
        post = findViewById(R.id.post);

        if(intent.getStringExtra("defectId") != null){
            //Log.e("Id", String.valueOf(repairId));
            defectId = Integer.parseInt(intent.getStringExtra("defectId"));
            Defect defect = dbHelper.queryDefectById(defectId);
            title.setText(device.getName()+"修改缺陷记录");
            content.setText(defect.getContent());
            person.setText(defect.getPerson());
            time.setText(defect.getTime());
            complete = defect.getFlag();
            level = defect.getLevel();
            RadioButton com1 = findViewById(R.id.radio_1);
            RadioButton com2 = findViewById(R.id.radio_2);
            RadioButton level1 = findViewById(R.id.radio1_1);
            RadioButton level2 = findViewById(R.id.radio1_2);
            if(complete == 1){
                com1.setChecked(true);
            }else {
                com2.setChecked(true);
            }
            if(level == 1){
                level2.setChecked(true);
            }else{
                level1.setChecked(true);
            }
            updateFlag = true;
        }else{
            title.setText("增加缺陷记录");
        }
        groupComplete = findViewById(R.id.group1);
        groupLevel = findViewById(R.id.group2);
        groupComplete.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.radio_1){
                complete = 1;
            }else{
                complete =0;
            }
        });

        groupLevel.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.radio1_1){
                level = 0;
            }else{
                level =1;
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
                        dbHelper.deleteDefect(defectId);
                        setResult(RESULT_OK,intent2);
                        finish();
                    }).setNegativeButton("取消", (dialog, id) -> {});
                    deleteDialog.show();
                }else{
                    Toast.makeText(AddDefectActivity.this,"你不能删除一个正在新增的项目，请选择返回",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.post:
                Intent intent1 = new Intent();
                intent1.putExtra("data_return","已更新数据库");
                if(content.getText().toString().length() == 0){
                    Toast.makeText(this,"请输入缺陷内容",Toast.LENGTH_SHORT).show();
                } else if (complete == 2) {
                    Toast.makeText(this,"请选择是否完成",Toast.LENGTH_SHORT).show();
                } else if(complete == 1 && person.getText().toString().length() == 0){
                    Toast.makeText(this,"请输入负责人",Toast.LENGTH_SHORT).show();
                }else if(complete == 1 && time.getText().toString().length() == 0){
                    Toast.makeText(this,"请输入检修时间",Toast.LENGTH_SHORT).show();
                } else if (complete == 0 && (time.getText().toString().length() != 0||time.getText().toString().length() != 0)) {
                    Toast.makeText(this,"请选择已完成",Toast.LENGTH_SHORT).show();
                } else if (level == 2) {
                    Toast.makeText(this,"请选择缺陷等级",Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
                    confirmDialog.setTitle("确认提交").setPositiveButton("确认",(dialog, id) -> {
                        Defect defect = new Defect();
                        defect.setContent(content.getText().toString());
                        defect.setPerson(person.getText().toString());
                        defect.setTime(time.getText().toString());
                        defect.setFlag(complete);
                        defect.setLevel(level);
                        defect.setDeviceId(device.getId());
                        Log.e("record",defect.getContent());
                        if(updateFlag){
                            defect.setId(defectId);
                            dbHelper.updateDefect(defect);
                        }else{
                            dbHelper.insertDefect(defect);
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