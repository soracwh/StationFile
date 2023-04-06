package com.example.stationfile.entity;

public class Measure {
    int id;
    int flag;
    String content;

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
}
