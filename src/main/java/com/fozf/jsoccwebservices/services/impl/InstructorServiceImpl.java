package com.fozf.jsoccwebservices.services.impl;

import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.repositories.InstructorRepository;
import com.fozf.jsoccwebservices.repositories.RoleRepository;
import com.fozf.jsoccwebservices.services.InstructorService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepository;
    private final RoleRepository roleRepository;

    public InstructorServiceImpl(InstructorRepository instructorRepository, RoleRepository roleRepository) {
        this.instructorRepository = instructorRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Instructor findInstructorById(Long id) {
        return instructorRepository.findById(id).get();
    }

    @Override
    public Instructor findByUserName(String username) {
        return instructorRepository.findByUsername(username);
    }

    @Override
    public List<Instructor> findAllInstructor() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor saveInstructor(Instructor instructor) {
        String hashed = BCrypt.hashpw(instructor.getPassword(), BCrypt.gensalt(10));
        instructor.setPassword(hashed);
        instructor.setRoles(Arrays.asList(roleRepository.findByName("ROLE_INSTRUCTOR")));
        return instructorRepository.save(instructor);
    }
}
