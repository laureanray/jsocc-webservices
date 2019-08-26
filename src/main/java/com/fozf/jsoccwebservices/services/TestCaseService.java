package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.ExerciseItem;
import com.fozf.jsoccwebservices.domain.TestCase;

import java.util.List;

public interface TestCaseService {
    TestCase findById(long id);
    List<TestCase> findAll();
    TestCase saveTestCase(TestCase testCase);
}
