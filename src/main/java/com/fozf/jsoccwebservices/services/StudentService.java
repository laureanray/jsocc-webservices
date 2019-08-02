package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Student;

import java.util.List;

public interface StudentService {
    Student findCustomerById(Long id);
    List<Student> findAllCustomer();

    Student saveCustomer(Student student);
}
