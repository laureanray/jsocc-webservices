package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student findCustomerById(Long id) {
           return studentRepository.findById(id).get();

    }

    @Override
    public List<Student> findAllCustomer() {
        return studentRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Student saveCustomer(Student student) {
        String hashed = BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(10));
        student.setPassword(hashed);
        return studentRepository.save(student);
    }
}
