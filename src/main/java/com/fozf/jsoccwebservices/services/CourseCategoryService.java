package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.CourseCategory;

import java.util.List;
import java.util.Optional;

public interface CourseCategoryService {
    CourseCategory findById(long id);
    List<CourseCategory> findAllCourseCategory();
    CourseCategory saveCourseCategory(CourseCategory courseCategory);
    void removeCourseCategory(long courseCategoryId);
}
