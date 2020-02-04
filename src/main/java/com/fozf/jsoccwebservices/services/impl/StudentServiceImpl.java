package com.fozf.jsoccwebservices.services.impl;

import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.RoleRepository;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import com.fozf.jsoccwebservices.services.StudentService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;

    public StudentServiceImpl(StudentRepository studentRepository, RoleRepository roleRepository) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Student findById(Long id) {
           return studentRepository.findById(id).get();
    }

    @Override
    public Student findByUserName(String username) {
            return studentRepository.findByUsername(username);
    }

    @Override
    public List<Student> findAllStudent() {
        return studentRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Student saveStudent(Student student) {
        String hashed = BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(10));
        student.setPassword(hashed);
        student.setRoles(Arrays.asList(roleRepository.findByName("ROLE_STUDENT")));
        return studentRepository.save(student);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }
}
