package com.eeo.entity;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DayTeachers {

    @Id
    private int id;
    private long classID;
    private int UID;
    private long timeStamp;

    public long getClassID() {
        return classID;
    }

    public void setClassID(long classID) {
        this.classID = classID;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
