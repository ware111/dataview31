package com.eeo.service;

import java.text.ParseException;

public interface TeacherService {
    int findNowTeacher();
    int findDayTeacher() throws ParseException;
}
