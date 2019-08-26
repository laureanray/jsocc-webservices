package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Instructor;

import java.util.List;

public interface CourseService {
    Course findById(long id);
//    Course findByCode(String code);
    List<Course> findAllCourse();
//    List<Course> findByInstructorId(long instructorId);
    Course saveCourse(Course course);
}
