package com.eeo.service;

import com.eeo.repository.DayStudenttRepository;
import com.eeo.util.CurrentHourTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class TeacherServiceImp implements TeacherService {
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    DayStudenttRepository dayStudenttRepository;

    @Override
    public int findNowTeacher() {
        Object studentCount = entityManager.createNativeQuery("select sum(flag) from now_teachers").getResultList().get(0);
        if (studentCount != null){
        return Integer.valueOf(studentCount+"");
        }else {
            return 0;
        }
    }

    @Override
    public int findDayTeacher() throws ParseException {
        long zeroTime = CurrentHourTime.getZeroTime();
        int dayTeacherCount = entityManager.createNativeQuery("select count(*) from (select * from day_teachers where time_stamp>" + zeroTime + ") t group by uid,classid").getResultList().size();
        return dayTeacherCount;
    }
}
