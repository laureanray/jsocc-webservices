package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.CourseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseCategoryRepository extends JpaRepository<CourseCategory, Long> {
}
