package com.example.stationfile.entity;

public class Interval {
    int stationId;
    int intervalId;
    String name;
    String level;

    public Integer getStationId() {
        return stationId;
    }

    public Integer getIntervalId() {
        return intervalId;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public void setIntervalId(Integer intervalId) {
        this.intervalId = intervalId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
