package com.example.stationfile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stationfile.dialog.DeleteDialog;
import com.example.stationfile.entity.Device;
import com.example.stationfile.entity.RepairRecord;

public class AddRepairActivity extends AppCompatActivity implements View.OnClickListener {
    Device device;
    MyDBHelper dbHelper;
    TextView title;
    EditText person,content,time;
    ImageView back;
    Button delete,post;
    Intent intent;
    int repairId;

    boolean updateFlag = false;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_repair);
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

        if(intent.getStringExtra("repairId") != null){
            //Log.e("Id", String.valueOf(repairId));
            repairId = Integer.parseInt(intent.getStringExtra("repairId"));
            RepairRecord record = dbHelper.queryRepairById(repairId);
            title.setText(device.getName()+"修改检修记录");
            content.setText(record.getContent());
            person.setText(record.getPerson());
            time.setText(record.getTime());
            updateFlag = true;
        }else{
            title.setText("增加检修记录");
        }

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
                        dbHelper.deleteRepair(repairId);
                        setResult(RESULT_OK,intent2);
                        finish();
                    }).setNegativeButton("取消", (dialog, id) -> {});
                    deleteDialog.show();
                }else{
                    Toast.makeText(AddRepairActivity.this,"你不能删除一个正在新增的项目，请选择返回",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.post:
                Intent intent1 = new Intent();
                intent1.putExtra("data_return","已更新数据库");
                if(content.getText().toString().length() == 0){
                    Toast.makeText(this,"请输入检修内容",Toast.LENGTH_SHORT).show();
                }else if(person.getText().toString().length()== 0){
                    Toast.makeText(this,"请输入负责人",Toast.LENGTH_SHORT).show();
                }else if(time.getText().toString().length() ==0){
                    Toast.makeText(this,"请输入检修时间",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder confirmDialog = new AlertDialog.Builder(this);
                    confirmDialog.setTitle("确认提交").setPositiveButton("确认",(dialog, id) -> {
                        RepairRecord record = new RepairRecord();
                        record.setContent(content.getText().toString());
                        record.setPerson(person.getText().toString());
                        record.setTime(time.getText().toString());
                        Log.e("record",record.getContent());
                        if(updateFlag){
                            record.setId(repairId);
                            dbHelper.updateRepair(record);
                        }else{
                            dbHelper.insertRepair(record,device.getId());
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