package com.eeo.entity;

import java.io.Serializable;

public class Hour24Data implements Serializable {
    private String roomTime="1";
    private String studentTime="1";
    private String teacherTime="1";

    private int roomSize;
    private int studentSize;
    private int teacherSize;

    public String getRoomTime() {
        return roomTime;
    }

    public void setRoomTime(String roomTime) {
        this.roomTime = roomTime;
    }

    public String getStudentTime() {
        return studentTime;
    }

    public void setStudentTime(String studentTime) {
        this.studentTime = studentTime;
    }

    public String getTeacherTime() {
        return teacherTime;
    }

    public void setTeacherTime(String teacherTime) {
        this.teacherTime = teacherTime;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public int getStudentSize() {
        return studentSize;
    }

    public void setStudentSize(int studentSize) {
        this.studentSize = studentSize;
    }

    public int getTeacherSize() {
        return teacherSize;
    }

    public void setTeacherSize(int teacherSize) {
        this.teacherSize = teacherSize;
    }
}
