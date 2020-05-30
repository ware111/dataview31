package com.eeo.repository;

import com.eeo.entity.AllDayRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Day30RoomsRepository extends JpaRepository<AllDayRooms,Integer> {
}
