package com.example.stationfile.entity;

public class RepairRecord {
    String content;
    String person;
    int id;
    String time;

    public RepairRecord() {
    }

    public String getContent() {
        return content;
    }

    public String getPerson() {
        return person;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
