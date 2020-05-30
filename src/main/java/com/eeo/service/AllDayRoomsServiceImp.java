package com.eeo.service;

import com.eeo.entity.AllDayRooms;
import com.eeo.repository.AllDayRoomsRepository;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AllDayRoomsServiceImp implements AllDayRoomsService {

    @Autowired
    AllDayRoomsRepository roomsRepository;

    @Override
    public String findRooms() {
        int count = (int)roomsRepository.count();
        AllDayRooms allDayRooms = roomsRepository.findById(count).get();
        return allDayRooms.toString();
    }
}
