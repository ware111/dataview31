package com.eeo.repository;

import com.eeo.entity.IntegralClassRooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface IntegralClassRoomsRepository extends JpaRepository<IntegralClassRooms,Integer> {
}
