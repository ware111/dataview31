package com.eeo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AllDayRooms {

    @Id
    private int id;
    private long timeStamp;
    private int size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return timeStamp+"\t"+size;
    }
}
