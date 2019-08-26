package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.TestCase;
import com.fozf.jsoccwebservices.repositories.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    final TestCaseRepository testCaseRepository;

    public TestCaseServiceImpl(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    @Override
    public TestCase findById(long id) {
        return testCaseRepository.findById(id).get();
    }

    @Override
    public List<TestCase> findAll() {
        return testCaseRepository.findAll();
    }

    @Override
    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }
}
