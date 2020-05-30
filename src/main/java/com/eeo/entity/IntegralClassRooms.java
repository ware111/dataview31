package com.eeo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class IntegralClassRooms {

    @Id
    private int id;
    private String currentDateTime;
    private int size;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
