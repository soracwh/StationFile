package com.example.stationfile;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.stationfile.entity.Defect;
import com.example.stationfile.entity.Device;
import com.example.stationfile.entity.Interval;
import com.example.stationfile.entity.Measure;
import com.example.stationfile.entity.RepairRecord;
import com.example.stationfile.entity.Simplified;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDBHelper  extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private final Context myContext;

    private final String DB_PATH;

    private static final String PACKAGE_NAME = "com.example.myapplication";
    private static final String DB_NAME = "electric.db";
    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        this.DB_PATH = myContext.getFilesDir().getPath();
    }

    public void createDataBase()throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {
       //do nothing - database already exist
        } else {
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            //this.getReadableDatabase();
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            //checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
           //database does't exist yet.
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ;
    }

    private void copyDataBase()throws IOException {
        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the inputfile to the outputfile
        byte[]buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }



/* private void initDataBase() throws IOException {

        try {
            String databaseFilename = DB_PATH + "/" + DB_NAME;

            File file = new File(DB_PATH);
            if (!file.exists()) {
                File dir = new File(databaseFilename);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                InputStream is = myContext.getAssets().open(DB_NAME);
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[8192];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
            db = SQLiteDatabase.openOrCreateDatabase(databaseFilename, null);
        } catch (Exception e) {
            Log.e("DBManager", "创建数据库文件异常", e);
        }

    }*/


    //该方法内部要用自己创建的db
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        /*sqLiteDatabase.execSQL("create table station(id integer primary key autoincrement, name text, level text)");*/
/*        db = sqLiteDatabase;
        try {
            this.initDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }

    public void openDataBase()throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        /*String myPath = myContext.getApplicationInfo().dataDir+DB_NAME;*/
        //db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
/*        Log.e("ShadyPi", "getView: ");
        Log.e("ShadyPi", "getView: "+myContext.getFilesDir().getPath());*/
    }

    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }



    //db.execSQL("insert into person (name, phone, money) values (?, ?, ?);", new Object[]{"张三", 15987461, 75000});
    public void insertStation(String name){
        String level = "";
        if(name.length()>6){
            level = name.substring(0,5);
        }
        db.execSQL("insert into station (name,level) values (?,?)",new Object[] {name,level});
    }

    public void deleteStation(int id) {
        db.execSQL("delete from station where id = ?", new Object[]{id});
    }

    public void updateStation(Simplified s, String newName){
        db.execSQL("update station set name = ? where id  = ?", new Object[] {newName,s.getId()});
    }

    public Interval queryAllByIntervalId(int id){
        Interval interval =new Interval();
        String sql_sel = "SELECT * FROM interval WHERE id = "+id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            interval.setIntervalId(cursor.getInt(0));
            interval.setStationId(cursor.getInt(1));
            interval.setName(cursor.getString(2));
        }
        return interval;
    }
    public void insertInterval(Interval interval){
        db.execSQL("insert into interval (station_id,name) values (?,?)",new Object[] {interval.getStationId(),interval.getName()});
    }
    public void deleteInterval(int id){
        db.execSQL("delete from interval where id = ?", new Object[] {id});
    }
    public void deleteIntervalByStationId(int id){
        db.execSQL("delete from interval where station_id = ?", new Object[] {id});
    }
    public void updateInterval(Interval interval, String newName){
        db.execSQL("update interval set name = ? where id  = ?", new Object[] {newName,interval.getIntervalId()});

    }

    public List<Simplified> queryAllType(){
        List<Simplified> res = new ArrayList<>();
        String sql_sel = "select  t.id, t.name from type as t";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(1));
            res.add(simplified);
        }
        return res;
    }

    public void deleteDevice(int id){
        db.execSQL("delete from device where id = ?", new Object[] {id});
    }
    public void deleteDeviceByStation(int id){
        db.execSQL("delete from device where station_id = ?", new Object[] {id});
    }
    public void deleteDeviceByInterval(int id){
        db.execSQL("delete from device where interval_id = ?", new Object[] {id});
    }
    public void insertDevice(Device device){
        db.execSQL("insert into device (station_id,interval_id,type_id,name) values (?,?,?,?)",
                new Object[] {device.getStationId(),device.getIntervalId(),device.getTypeId(),device.getName()});
    }

    public void updateDevice(Device device){
        db.execSQL("update device set name=? where id = ?",
                new Object[] {device.getName(),device.getId()});
    }


    public List<Simplified> queryAllStation(){
        List<Simplified> res = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.query("station",null,null,null,null,null,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(1));
            res.add(simplified);
        }
        return res;
    }

    public List<Simplified> queryAllIntervalByStationId(int stationId){
        List<Simplified> res = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from interval where station_id = " + stationId,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(2));
            res.add(simplified);
        }
        return res;
    }

    public List<Simplified> queryAllTypeByIntervalId(int intervalId){
        List<Simplified> res = new ArrayList<>();
        String sql_sel = "select distinct t.id, t.name from type as t inner join device as d on d.type_id = t.id where d.interval_id = " + intervalId;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(1));
            res.add(simplified);
        }
        return res;
    }

    public List<Simplified> queryDeviceByTypeId(int typeId, int intervalId){
        List<Simplified> res = new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from device where type_id = " + typeId + " and interval_id = "+intervalId+" order by name",null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(4));
            res.add(simplified);
        }
        return res;
    }

    public List<Simplified> queryByStationName(String s){
        List<Simplified> res = new ArrayList<>();
        String sql_sel = "SELECT * FROM station WHERE name LIKE '%"+s+"%'";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(1));
            res.add(simplified);
        }
        return res;
    }

    public Simplified queryByStationId(int id){
        Simplified res = new Simplified();
        String sql_sel = "SELECT * FROM station WHERE id =" + id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            res.setId(cursor.getInt(0));
            res.setName(cursor.getString(1));
        }
        return res;
    }

    public List<Simplified> queryByIntervalName(String s){
        List<Simplified> res = new ArrayList<>();
        String sql_sel = "SELECT * FROM interval WHERE name LIKE '%"+s+"%'";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(2));
            res.add(simplified);
        }
        return res;
    }

    public Simplified queryByIntervalId(int id){
        Simplified simplified = new Simplified();
        String sql_sel = "SELECT * FROM interval WHERE id = "+id;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(2));
        }
        return simplified;
    }

    public List<Simplified> queryByTypeName(String s){
        List<Simplified> res = new ArrayList<>();
        String sql_sel = "SELECT * FROM type WHERE name LIKE '%"+s+"%'";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(3));
            res.add(simplified);
        }
        return res;
    }

    public List<Simplified> queryByDeviceNameAndTypeId(String s, int typeId){
        List<Simplified> res = new ArrayList<>();
        String sql_sel = "SELECT * FROM device WHERE name LIKE '%"+s+"%'" + "AND type_id =" + typeId;
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Simplified simplified = new Simplified();
            simplified.setId(cursor.getInt(0));
            simplified.setName(cursor.getString(4));
            res.add(simplified);
        }
        return res;
    }

    /*查询设备*/
    public Device queryDeviceById(int id){
        Device device = new Device();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from device where id = " + id,null);
        while(cursor.moveToNext()){
            device.setId(cursor.getInt(0));
            device.setStationId(cursor.getInt(1));
            device.setIntervalId(cursor.getInt(2));
            device.setName(cursor.getString(4));
            device.setState(cursor.getInt(13));
        }
        return device;
    }

    /*bysd_id*/
    public Device queryDeviceBySDId(int SD_id){
        Device device = new Device();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("select * from device where SD_id = " + SD_id,null);
        while(cursor.moveToNext()){
            device.setId(cursor.getInt(0));
            device.setStationId(cursor.getInt(1));
            device.setIntervalId(cursor.getInt(2));
            device.setName(cursor.getString(4));
            device.setState(cursor.getInt(13));
        }
        return device;
    }

    /*查询检修记录*/
    public List<RepairRecord> queryRepairByDeviceId(int id){
        List<RepairRecord> res = new ArrayList<>();
        String sql_sel = "SELECT * FROM repair AS r INNER JOIN repair_device AS rd ON r.id = rd.repair_id where rd.device_id =" + id + " order by r.time desc";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            RepairRecord record =new RepairRecord();
            record.setId(cursor.getInt(0));
            record.setContent(cursor.getString(1));
            record.setTime(cursor.getString(2));
            record.setPerson(cursor.getString(3));
            res.add(record);
        }
        return res;
    }

    /*缺陷*/
    public List<Defect> queryDefectByDeviceId(int id){
        List<Defect> res = new ArrayList<>();
        String sql_sel = "SELECT * FROM defect where device_id = " + id + " order by flag,level desc,time desc";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Defect defect =new Defect();
            defect.setId(cursor.getInt(0));
            defect.setContent(cursor.getString(1));
            defect.setTime(cursor.getString(2));
            defect.setLevel(cursor.getInt(3));
            defect.setPerson(cursor.getString(4));
            defect.setFlag(cursor.getInt(5));
            res.add(defect);
        }
        return res;
    }

    /*反措*/
    public List<Measure> queryMeasureByDeviceId(int id){
        List<Measure> res =new ArrayList<>();
        String sql_sel = "SELECT * FROM measure where device_id = " + id + " order by flag";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(sql_sel,null);
        while (cursor.moveToNext()){
            Measure measure =new Measure();
            measure.setId(cursor.getInt(0));
            measure.setContent(cursor.getString(1));
            measure.setFlag(cursor.getInt(3));
            measure.setTarget(cursor.getString(4));
            measure.setPerson(cursor.getString(5));
            measure.setDate(cursor.getString(6));
            res.add(measure);
        }
        return res;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
