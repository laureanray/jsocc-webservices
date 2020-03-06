package com.fozf.jsoccwebservices.services.impl;

import com.fozf.jsoccwebservices.domain.CourseCategory;
import com.fozf.jsoccwebservices.repositories.CourseCategoryRepository;
import com.fozf.jsoccwebservices.services.CourseCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class    CourseCategoryServiceImpl implements CourseCategoryService {

    private final CourseCategoryRepository courseCategoryRepository;

    public CourseCategoryServiceImpl(CourseCategoryRepository courseCategoryRepository) {
        this.courseCategoryRepository = courseCategoryRepository;
    }

    @Override
    public CourseCategory findById(long id) {
        return courseCategoryRepository.findById(id);
    }

    @Override
    public List<CourseCategory> findAllCourseCategory() {
        return courseCategoryRepository.findAll();
    }

    @Override
    public CourseCategory saveCourseCategory(CourseCategory courseCategory) {
        return courseCategoryRepository.save(courseCategory);
    }

    @Override
    public void removeCourseCategory(long courseCategoryId) {
        courseCategoryRepository.deleteById(courseCategoryId);
    }
}
