package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Login;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
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
    public Student findByUserName(String username) {
            return studentRepository.findByUserName(username);
    }

    @Override
    public List<Student> findAllCustomer() {
        return studentRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public Student saveStudent(Student student) {
        String hashed = BCrypt.hashpw(student.getPassword(), BCrypt.gensalt(10));
        student.setPassword(hashed);
        return studentRepository.save(student);
    }

//    @Override
//    public Student loginStudent(Login login) {
//        List<Student> students = studentRepository.findFirstNameByUsername(login.getUsername());
//
//        System.out.println(students.get(0).getFirstName());
//        return students.get(0);
//    }


}
