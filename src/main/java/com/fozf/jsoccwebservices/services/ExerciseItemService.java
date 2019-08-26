package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Exercise;
import com.fozf.jsoccwebservices.domain.ExerciseItem;

import java.util.List;

public interface ExerciseItemService {
    ExerciseItem findById(long id);
    List<ExerciseItem> findByExerciseId(long exerciseId);
    List<ExerciseItem> findAll();
    ExerciseItem saveExercise(ExerciseItem exerciseItem);
}
