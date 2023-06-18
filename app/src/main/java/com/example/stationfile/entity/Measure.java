package com.example.stationfile.entity;


public class Measure {
    int id;
    int flag;
    String content;
    String person;
    String date;
    String target;
    int deviceId;

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public int getFlag() {
        return flag;
    }

    public String getContent() {
        return content;
    }

    public String getPerson() {
        return person;
    }

    public String getDate() {
        return date;
    }

    public String getTarget() {
        return target;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
