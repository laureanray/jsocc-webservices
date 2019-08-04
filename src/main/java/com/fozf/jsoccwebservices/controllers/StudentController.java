package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Login;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.services.StudentService;
import org.mindrot.jbcrypt.BCrypt;
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
        return studentService.findAllStudent();
    }

    @GetMapping("/{id}")
    public Student getCustomerById(@PathVariable Long id){
        return studentService.findCustomerById(id);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Student saveStudent(@RequestBody Student student){
        return studentService.saveStudent(student);
    }

    @PostMapping("/login")
    public Student login(@RequestBody Login login){
//        System.out.println(login.getUsername());
        Student student = studentService.findByUserName(login.getUsername());
        if(student == null){
            throw new RuntimeException("Incorrect");
        }
;
        if(BCrypt.checkpw(login.getPassword(), student.getPassword())){
            return student;
        }else{
            throw new RuntimeException("Incorrect password");
        }
    }
//    @PostMapping
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Student loginStudent(@RequestBody Login login){
//        return studentService.loginStudent(login);
//    }
}
