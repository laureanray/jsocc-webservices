package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    @Query("SELECT i from Instructor i where i.username = ?1")
    Instructor findByUserName(String username);
}
