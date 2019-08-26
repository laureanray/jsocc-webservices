package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Exercise;
import com.fozf.jsoccwebservices.domain.ExerciseItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExerciseItemRepository extends JpaRepository<ExerciseItem, Long> {
    @Query("SELECT et FROM ExerciseItem et INNER JOIN et.exercise e WHERE e.id = ?1")
    List<ExerciseItem> findByExerciseId(long exerciseId);
}
