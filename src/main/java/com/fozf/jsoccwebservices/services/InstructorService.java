package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Student;

import java.util.List;

    public interface InstructorService {
    Instructor findInstructorById(Long id);
    Instructor findByUserName(String username);
    List<Instructor> findAllInstructor();


    Instructor saveInstructor(Instructor instructor);

}
