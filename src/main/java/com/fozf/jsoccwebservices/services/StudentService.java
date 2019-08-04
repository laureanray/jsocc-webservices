package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Login;
import com.fozf.jsoccwebservices.domain.Student;

import java.util.List;

public interface StudentService {
    Student findCustomerById(Long id);
    Student findByUserName(String username);
    List<Student> findAllStudent();


    Student saveStudent(Student student);

}
