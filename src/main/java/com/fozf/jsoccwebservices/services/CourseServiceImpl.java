package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Course findById(long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public Course findByCode(String code) {
        return courseRepository.findByCode(code);
    }

    @Override
    public List<Course> findAllCourse() {
        return courseRepository.findAll();
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> findByInstructorId(long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }
}
