package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.ExerciseItem;
import com.fozf.jsoccwebservices.domain.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
