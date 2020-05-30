package com.eeo.repository;

import com.eeo.entity.AllDayRooms;
import com.eeo.entity.NowStudents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AllDayRoomsRepository extends JpaRepository<AllDayRooms,Integer> {
}
