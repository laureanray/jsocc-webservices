package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c from Course c where c.courseCode = ?1")
    Course findByCode(String code);
    @Query("SELECT c from Course c where c.instructorId = ?1")
    List<Course> findByInstructorId(long instructorId);
}
