package com.example.stationfile.entity;

public class Defect {
    int id;
    int flag;
    int level;
    String time;
    String person;
    String content;

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

    public void setLevel(int level) {
        this.level = level;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPerson(String person) {
        this.person = person;
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

    public int getLevel() {
        return level;
    }

    public String getTime() {
        return time;
    }

    public String getPerson() {
        return person;
    }

    public String getContent() {
        return content;
    }
}
