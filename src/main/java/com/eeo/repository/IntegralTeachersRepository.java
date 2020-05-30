package com.eeo.repository;

import com.eeo.entity.IntegralStudents;
import com.eeo.entity.IntegralTeachers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IntegralTeachersRepository extends JpaRepository<IntegralTeachers,Integer> {
}
