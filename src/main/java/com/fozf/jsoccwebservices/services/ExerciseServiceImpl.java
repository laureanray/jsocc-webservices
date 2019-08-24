package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Exercise;
import com.fozf.jsoccwebservices.repositories.ExerciseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    final ExerciseRepository exerciseRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }


    @Override
    public Exercise findById(long id) {
        return exerciseRepository.findById(id).get();
    }


    @Override
    public List<Exercise> findByCourseId(long courseId) {
        return exerciseRepository.findByCourseId(courseId);
    }

    @Override
    public Exercise saveExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }

    @Override
    public List<Exercise> findAll() {
        return exerciseRepository.findAll();
    }
}
