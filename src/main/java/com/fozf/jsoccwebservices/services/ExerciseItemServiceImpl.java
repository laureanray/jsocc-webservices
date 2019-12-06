package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.ExerciseItem;
import com.fozf.jsoccwebservices.repositories.ExerciseItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseItemServiceImpl implements ExerciseItemService {

    final ExerciseItemRepository exerciseItemRepository;

    public ExerciseItemServiceImpl(ExerciseItemRepository exerciseItemRepository) {
        this.exerciseItemRepository = exerciseItemRepository;
    }

    @Override
    public ExerciseItem findById(long id) {
        return exerciseItemRepository.findById(id).get();
    }

    @Override
    public List<ExerciseItem> findByExerciseId(long exerciseId) {
        return exerciseItemRepository.findByExerciseId(exerciseId);
    }

    @Override
    public List<ExerciseItem> findAll() {
        return exerciseItemRepository.findAll();
    }

    @Override
    public ExerciseItem saveExerciseItem(ExerciseItem exerciseItem) {
        return exerciseItemRepository.save(exerciseItem);
    }
}
