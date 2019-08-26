package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
      List<Course> findByInstructorId(long instructorId);
      List<Course> findByCourseCode(String courseCode);
}
