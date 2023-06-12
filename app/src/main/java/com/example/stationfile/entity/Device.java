package com.example.stationfile.entity;


/*create table device(id integer primary key autoincrement, station_id integer, interval_id integer, type_id integer, name text,
supplier text,
basic_info text,
time_start text,
time_last text,
blackspot text,
defect text,
im_defect integer,
no_defect integer,
greater integer,
repair text,
other_repair text,
sw_time integer,
transfer text,
trans_earth text,
oil text,
trans_trip text,
time_repair text,
content_repair text,
person text,
file1 text,
file2 text
file3 text,
file4 text);*/
public class Device {
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public void setTime_last(String time_last) {
        this.time_last = time_last;
    }

    public void setBlackSpot(String blackSpot) {
        this.blackSpot = blackSpot;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public void setImDefect(int imDefect) {
        this.imDefect = imDefect;
    }

    public void setNoDefect(int noDefect) {
        this.noDefect = noDefect;
    }

    public void setGreater(int greater) {
        this.greater = greater;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public void setOtherRepair(String otherRepair) {
        this.otherRepair = otherRepair;
    }

    public void setSwTime(int swTime) {
        this.swTime = swTime;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }

    public void setTransToEarth(String transToEarth) {
        this.transToEarth = transToEarth;
    }

    public void setOil(String oil) {
        this.oil = oil;
    }

    public void setTransTrip(String transTrip) {
        this.transTrip = transTrip;
    }

    public void setTimeRepair(String timeRepair) {
        this.timeRepair = timeRepair;
    }

    public void setContentRepair(String contentRepair) {
        this.contentRepair = contentRepair;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setFile1(String file1) {
        this.file1 = file1;
    }

    public void setFile2(String file2) {
        this.file2 = file2;
    }

    public void setFile3(String file3) {
        this.file3 = file3;
    }

    public void setFile4(String file4) {
        this.file4 = file4;
    }

    int id;
    int stationId;
    int intervalId;
    String name;

    int SD_id;

    public void setSD_id(int SD_id) {
        this.SD_id = SD_id;
    }

    public int getSD_id() {
        return SD_id;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    int typeId;
    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public void setIntervalId(int intervalId) {
        this.intervalId = intervalId;
    }

    public int getStationId() {
        return stationId;
    }

    public int getIntervalId() {
        return intervalId;
    }

    String supplier;
    String basicInfo;
    String time_start;
    String time_last;
    String blackSpot;
    String defect;
    int imDefect;
    int noDefect;
    int greater;
    String repair;
    String otherRepair;
    int swTime;
    String transfer;
    String transToEarth;
    String oil;
    String transTrip;
    String timeRepair;
    String contentRepair;
    String person;
    String file1;
    String file2;
    String file3;
    String file4;
    String checkAccept;
    String ExPerson;
    String StartPerson;
    int state;

    public void setExPerson(String exPerson) {
        ExPerson = exPerson;
    }

    public void setStartPerson(String startPerson) {
        StartPerson = startPerson;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getExPerson() {
        return ExPerson;
    }

    public String getStartPerson() {
        return StartPerson;
    }

    public int getState() {
        return state;
    }

    public void setCheckAccept(String checkAccept) {
        this.checkAccept = checkAccept;
    }

    public String getCheckAccept() {
        return checkAccept;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSupplier() {
        return supplier;
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public String getTime_start() {
        return time_start;
    }

    public String getTime_last() {
        return time_last;
    }

    public String getBlackSpot() {
        return blackSpot;
    }

    public String getDefect() {
        return defect;
    }

    public int getImDefect() {
        return imDefect;
    }

    public int getNoDefect() {
        return noDefect;
    }

    public int getGreater() {
        return greater;
    }

    public String getRepair() {
        return repair;
    }

    public String getOtherRepair() {
        return otherRepair;
    }

    public int getSwTime() {
        return swTime;
    }

    public String getTransfer() {
        return transfer;
    }

    public String getTransToEarth() {
        return transToEarth;
    }

    public String getOil() {
        return oil;
    }

    public String getTransTrip() {
        return transTrip;
    }

    public String getTimeRepair() {
        return timeRepair;
    }

    public String getContentRepair() {
        return contentRepair;
    }

    public String getPerson() {
        return person;
    }

    public String getFile1() {
        return file1;
    }

    public String getFile2() {
        return file2;
    }

    public String getFile3() {
        return file3;
    }

    public String getFile4() {
        return file4;
    }
}
