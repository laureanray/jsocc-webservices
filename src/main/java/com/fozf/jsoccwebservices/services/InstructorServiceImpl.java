package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.repositories.InstructorRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id).get();
    }

    @Override
    public Instructor findByUserName(String username) {
        return instructorRepository.findByUserName(username);
    }

    @Override
    public List<Instructor> findAllInstructor() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor saveInstructor(Instructor instructor) {
        String hashed = BCrypt.hashpw(instructor.getPassword(), BCrypt.gensalt(10));
        instructor.setPassword(hashed);
        return instructorRepository.save(instructor);
    }
}
