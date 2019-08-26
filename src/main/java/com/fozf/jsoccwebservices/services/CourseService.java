package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Optional<Course> findById(long id);
    List<Course> findAllCourse();
    Course saveCourse(Course course);
    List<Course> findByInstructorId(long instructorId);
    List<Course> findByCourseCode(String courseCode);
}
