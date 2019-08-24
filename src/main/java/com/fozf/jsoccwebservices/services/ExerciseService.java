package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Exercise;

import java.util.List;

public interface ExerciseService {
    Exercise findById(long id);
    List<Exercise> findByCourseId(long courseId);
    List<Exercise> findAll();
    Exercise saveExercise(Exercise exercise);
}
