package com.example.stationfile.entity;

public class Device {
    int id;
    int stationId;
    int intervalId;
    String name;
    int SD_id;
    int typeId;
    int state;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
