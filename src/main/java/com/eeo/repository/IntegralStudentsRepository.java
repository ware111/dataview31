package com.eeo.repository;

import com.eeo.entity.IntegralClassRooms;
import com.eeo.entity.IntegralStudents;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IntegralStudentsRepository extends JpaRepository<IntegralStudents,Integer> {
}
