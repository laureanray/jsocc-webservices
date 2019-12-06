package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.CourseTemplate;

import java.util.List;
import java.util.Optional;

public interface CourseTemplateService {
    Optional<CourseTemplate> findById(long id);
    List<CourseTemplate> findAllCourseTemplate();
    CourseTemplate saveCourseTemplate(CourseTemplate courseTemplate);
    List<CourseTemplate> findByCourseTemplateCode(String courseTemplateCode);
    List<CourseTemplate> findByCourseProgrammingLanguage(String courseProgrammingLanguage);
    void removeCourseTemplate(long courseTemplateId);
}
