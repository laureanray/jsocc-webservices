package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.CourseCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    private final CourseCategoryService courseCategoryService;

    public CourseCategoryServiceImpl(CourseCategoryService courseCategoryService) {
        this.courseCategoryService = courseCategoryService;
    }

    @Override
    public CourseCategory findById(long id) {
        return courseCategoryService.findById(id);
    }

    @Override
    public List<CourseCategory> findAllCourseCategory() {
        return courseCategoryService.findAllCourseCategory();
    }

    @Override
    public CourseCategory saveCourseCategory(CourseCategory courseCategory) {
        return courseCategoryService.saveCourseCategory(courseCategory);
    }

    @Override
    public void removeCourseCategory(long courseCategoryId) {
        courseCategoryService.removeCourseCategory(courseCategoryId);
    }
}
