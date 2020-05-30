package com.eeo.job;

import com.alibaba.fastjson.JSON;
import com.eeo.entity.Hour24Data;
import com.eeo.entity.IntegralClassRooms;
import com.eeo.entity.IntegralStudents;
import com.eeo.entity.IntegralTeachers;
import com.eeo.repository.IntegralClassRoomsRepository;
import com.eeo.repository.IntegralStudentsRepository;
import com.eeo.repository.IntegralTeachersRepository;
import com.eeo.service.AllDayRoomsService;
import com.eeo.service.StudentService;
import com.eeo.service.TeacherService;
import com.eeo.socket.*;
import com.eeo.util.CurrentHourTime;
import com.eeo.util.CurrentTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.websocket.Session;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;


@EnableScheduling
@Component
public class WebSocketJob {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    StudentService studentService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    AllDayRoomsService roomsService;

    @Autowired
    IntegralClassRoomsRepository integralRepository;

    @Autowired
    IntegralStudentsRepository integralStudentsRepository;

    @Autowired
    IntegralTeachersRepository integralTeachersRepository;

    @Async(value = "taskPoolExecutor")
    @Scheduled(cron = "*/5 * * * * ?")
    public void dayTeacherJob() throws InterruptedException, IOException, ParseException {


        CopyOnWriteArraySet<Session> nowClassRoomSession = NowClassRoomSocketServer.getWebSessionSet();
        Query nativeQuery = entityManager.createNativeQuery("select count(*) from (select sum(flag) s from in_out_class_data group by classid) t where t.s > 0");
        boolean isIntegralTime = CurrentTimeUtil.IsIntegralTime();
        Object roomCount = nativeQuery.getResultList().get(0);


        //实时在线教室数
        nowClassRoomSession.forEach(sessionRoom -> {
            synchronized (sessionRoom) {
                try {
                    sessionRoom.getBasicRemote().sendText(roomCount + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        //当天在线教室数
        CopyOnWriteArraySet<Session> dayClassRoomSeesions = DayClassRoomSocketServer.getWebSessionSet();
        long times = CurrentHourTime.getZeroTime();
        int size = entityManager.createNativeQuery("select count(classid) from in_out_class_data where time_stamp >" + times+ " and flag=1 group by classid").getResultList().size();
        dayClassRoomSeesions.forEach(x -> {
            try {
                synchronized (x) {
                    x.getBasicRemote().sendText("" + size);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        //实时在线学生数
        CopyOnWriteArraySet<Session> nowStudentSessions = NowStudentSocketServer.getWebSessionSet();
        int nowStudentCount = studentService.findNowStudent();

        nowStudentSessions.forEach(x -> {
            try {
                synchronized (x) {
                    x.getBasicRemote().sendText(nowStudentCount + "");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //当天在线学生数
        CopyOnWriteArraySet<Session> dayStudentSessions = DayStudentSocketServer.getWebSessionSet();
        int dayStudentCount = studentService.findDayStudent();
        dayStudentSessions.forEach(x -> {
            try {
                synchronized (x) {
                    x.getBasicRemote().sendText(dayStudentCount + "");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        //实时在线教师数
        CopyOnWriteArraySet<Session> nowTeacherSessions = NowTeacherSocketServer.getWebSessionSet();
        int nowTeacherCount = teacherService.findNowTeacher();
        nowTeacherSessions.forEach(x -> {
            try {
                synchronized (x) {
                    x.getBasicRemote().sendText(nowTeacherCount + "");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //当天在线学生数
        CopyOnWriteArraySet<Session> dayTeacherSessions = DayTeacherSocketServer.getWebSessionSet();
        int dayTeacherCount = teacherService.findDayTeacher();
        dayTeacherSessions.forEach(x -> {
            try {
                synchronized (x) {
                    x.getBasicRemote().sendText(dayTeacherCount + "");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //过往24小时数据
        if (isIntegralTime) {
            String currentTime = CurrentTimeUtil.getCurrentTime();
            IntegralClassRooms integralClassRooms = new IntegralClassRooms();
            integralClassRooms.setCurrentDateTime(currentTime);
            integralClassRooms.setSize(Integer.valueOf(roomCount + ""));
            integralRepository.save(integralClassRooms);
        }
        int count = (int) integralRepository.count();
        String sql = "";
        if (count > 24) {
            sql = "select * from integral_class_rooms where id <= " + count + "&>=" + (count - 24) + "order by id asc";
        } else {
            sql = "select * from integral_class_rooms order by id asc";
        }
        Query rooms = entityManager.createNativeQuery(sql, IntegralClassRooms.class);


        if (isIntegralTime) {
            String currentTime = CurrentTimeUtil.getCurrentTime();
            IntegralTeachers integralStudents = new IntegralTeachers();
            integralStudents.setCurrentDateTime(currentTime);
            integralStudents.setSize(Integer.valueOf(dayTeacherCount + ""));
            integralTeachersRepository.save(integralStudents);
        }
        count = (int) integralTeachersRepository.count();
        if (count > 24) {
            sql = "select * from integral_teachers where id <= " + count + "&>=" + (count - 24) + "order by id asc";
        } else {
            sql = "select * from integral_teachers order by id asc";
        }
        Query teachers = entityManager.createNativeQuery(sql, IntegralTeachers.class);

        if (isIntegralTime) {
            String currentTime = CurrentTimeUtil.getCurrentTime();
            IntegralStudents integralStudents = new IntegralStudents();
            integralStudents.setCurrentDateTime(currentTime);
            integralStudents.setSize(Integer.valueOf(nowStudentCount + ""));
            integralStudentsRepository.save(integralStudents);
        }
        count = (int) integralStudentsRepository.count();
        if (count > 24) {
            sql = "select * from integral_students where id <= " + count + "&>=" + (count - 24) + "order by id asc";
        } else {
            sql = "select * from integral_students order by id asc";
        }
        Query students = entityManager.createNativeQuery(sql, IntegralStudents.class);


        List<IntegralClassRooms> roomList = rooms.getResultList();
        List<IntegralStudents> studentList = students.getResultList();
        List<IntegralTeachers> teacherList = teachers.getResultList();
        int roomSize = roomList.size();
        int studentSize = studentList.size();
        int teacherSize = teacherList.size();
        int[] sizes = {roomSize, studentSize, teacherSize};
        Arrays.sort(sizes);



        CopyOnWriteArraySet<Session> AllDayRoomSession = Hour10ClassRoomSocketServer.getWebSessionSet();
        AllDayRoomSession.forEach(session24 -> {
            String dateTime = "";
            String tmpSize = "";
            Hour24Data hour24Data = new Hour24Data();
            if ((roomSize + studentSize + teacherSize) != 0) {
                for (int i = 0; i < sizes[2]; i++) {
                    if (roomList.size() != 0) {
                        if (i < roomSize) {
                            if (roomList.get(i) != null) {
                                dateTime = "roomTime:"+roomList.get(i).getCurrentDateTime() + "\t";
                                tmpSize = "roomSize:"+roomList.get(i).getSize() + "\t";
                                hour24Data.setRoomSize(roomList.get(i).getSize());
                                hour24Data.setRoomTime(roomList.get(i).getCurrentDateTime());
                            }
                        }
                    }

                    if (studentList.size() != 0) {
                        if (i < studentSize) {
                            if (studentList.get(i) != null) {
                                dateTime += "studentTime:"+studentList.get(i).getCurrentDateTime() + "\t";
                                tmpSize += "studentSize:"+studentList.get(i).getSize() + "\t";
                                hour24Data.setStudentSize(studentList.get(i).getSize());
                                hour24Data.setStudentTime(studentList.get(i).getCurrentDateTime());
                            }
                        }
                    }

                    if (teacherList.size() != 0) {
                        if (i < teacherSize) {
                            if (teacherList.get(i) != null) {
                                dateTime += "teacherTime:"+teacherList.get(i).getCurrentDateTime() + "\t";
                                tmpSize += "teacherSize:"+teacherList.get(i).getSize() + "\t";
                                hour24Data.setTeacherSize(teacherList.get(i).getSize());
                                hour24Data.setTeacherTime(teacherList.get(i).getCurrentDateTime());
                            }
                        }
                    }


                    try {
                        synchronized (session24) {
                            session24.getBasicRemote().sendText(JSON.toJSON(hour24Data)+"");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
//        String currentTime = simpleDateFormat.format(new Date());
//        String substring = currentTime.substring(3);
//        boolean isIntegralTime = CurrentTimeUtil.IsIntegralTime();
//        if (isIntegralTime){
//        }
//        if (substring.equals("00:00")){
//            String rooms = roomsService.findRooms();
//            String[] split = rooms.split("\t");
//            CopyOnWriteArraySet<Session> AllDayRoomSession = Hour10ClassRoomSocketServer.getWebSessionSet();
//            AllDayRoomSession.forEach(x -> {
//                try {
//                    synchronized (x) {
//                        x.getBasicRemote().sendText(currentTime + "\t" + split[1]);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
    }
}
