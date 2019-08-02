package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.exceptions.NotFoundExeption;
import com.fozf.jsoccwebservices.exceptions.ValidationExeption;
import com.fozf.jsoccwebservices.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StudentController.BASE_URL)
public class StudentController {

    public static final String BASE_URL = "api/v1/students";

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping()
    List<Student> getAllCustomers(){
        return studentService.findAllCustomer();
    }

    @GetMapping("/{id}")
    public Student getCustomerById(@PathVariable Long id){
        return studentService.findCustomerById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student saveCustomer(@RequestBody Student student){
        return studentService.saveCustomer(student);
    }
}
