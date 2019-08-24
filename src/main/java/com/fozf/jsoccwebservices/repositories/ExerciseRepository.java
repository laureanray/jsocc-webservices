package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    @Query("SELECT e FROM Exercise e INNER JOIN e.course c WHERE c.id = ?1")
    List<Exercise> findByCourseId(long courseId);

}
