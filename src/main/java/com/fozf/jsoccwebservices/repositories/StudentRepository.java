package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s from Student s where s.username = ?1")
    Student findByUserName(String username);
}
