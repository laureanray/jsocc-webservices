package com.fozf.jsoccwebservices.bootstrap;

import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final StudentRepository studentRepository;

    public BootStrapData(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Current count: " + studentRepository.count());

    }
}
