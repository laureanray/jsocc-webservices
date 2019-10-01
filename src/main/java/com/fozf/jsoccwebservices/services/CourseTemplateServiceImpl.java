package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.CourseTemplate;
import com.fozf.jsoccwebservices.repositories.CourseTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseTemplateServiceImpl implements CourseTemplateService {

    final CourseTemplateRepository courseTemplateRepository;

    public CourseTemplateServiceImpl(CourseTemplateRepository courseTemplateRepository) {
        this.courseTemplateRepository = courseTemplateRepository;
    }

    @Override
    public Optional<CourseTemplate> findById(long id) {
        return courseTemplateRepository.findById(id);
    }

    @Override
    public List<CourseTemplate> findAllCourseTemplate() {
        return courseTemplateRepository.findAll();
    }

    @Override
    public CourseTemplate saveCourseTemplate(CourseTemplate courseTemplate) {
        return courseTemplateRepository.save(courseTemplate);
    }

    @Override
    public List<CourseTemplate> findByCourseTemplateCode(String courseTemplateCode) {
        return courseTemplateRepository.findByCourseTemplateCode(courseTemplateCode);
    }

    @Override
    public List<CourseTemplate> findByCourseProgrammingLanguage(String courseProgrammingLanguage) {
        return courseTemplateRepository.findByCourseProgrammingLanguage(courseProgrammingLanguage);
    }

    @Override
    public void removeCourseTemplate(long courseTemplateId) {
        courseTemplateRepository.deleteById(courseTemplateId);
    }
}
