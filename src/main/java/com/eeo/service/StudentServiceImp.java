package com.eeo.service;

import com.eeo.entity.DayStudents;
import com.eeo.repository.DayStudenttRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class StudentServiceImp implements StudentService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    DayStudenttRepository dayStudenttRepository;

    @Override
    public int findNowStudent() {
        Object studentCount = entityManager.createNativeQuery("select sum(flag) from now_students").getResultList().get(0);
        if (studentCount != null){
            return Integer.valueOf(studentCount+"");
        } else{
            return 0;
        }

    }

    @Override
    public int findDayStudent() throws ParseException {
        SimpleDateFormat dateTime11 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat date11 = new SimpleDateFormat("yyyy-MM-dd");
        String format11 = date11.format(new Date());
        Date startTime = dateTime11.parse(format11 + " 00:00:00");
        long times = startTime.getTime();
//        long dayStudentCount = dayStudenttRepository.count();
        int dayStudentCount = entityManager.createNativeQuery("select count(*) from (select * from day_students where time_stamp>" + times/1000 + ") t group by uid,classid").getResultList().size();
        return dayStudentCount;
    }
}
