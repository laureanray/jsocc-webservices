package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.CourseTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseTemplateRepository extends JpaRepository<CourseTemplate, Long> {
      List<CourseTemplate> findByCourseTemplateCode(String courseTemplateCode);
      List<CourseTemplate> findByCourseProgrammingLanguage(String courseProgrammingLanguage);
}
