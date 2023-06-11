package com.example.stationfile;

import com.example.stationfile.entity.Simplified;

public interface RefulshStateListenter {
    void refush(Simplified s);
    void dialogCallback(Simplified s);

    void update(Simplified s);

    void updateCallback(Simplified s,String newName);
}
