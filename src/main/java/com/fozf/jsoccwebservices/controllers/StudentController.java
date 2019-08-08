package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Login;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.services.InstructorService;
import com.fozf.jsoccwebservices.services.StudentService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(StudentController.BASE_URL)
public class StudentController {

    public static final String BASE_URL = "api/v1/students";

    private final StudentService studentService;
    private final InstructorService instructorService;

    public StudentController(StudentService studentService, InstructorService instructorService)
    {
        this.studentService = studentService;
        this.instructorService = instructorService;
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
    public ResponseEntity<Student> saveStudent(@RequestBody Student student){
        // Check first if username is taken in both tables

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();

        Instructor instructor = instructorService.findByUserName(student.getUsername() );
        if(instructor == null){
            return ResponseEntity.created(uri).body(studentService.saveStudent(student));
        }
        return ResponseEntity.badRequest().build();

    }

    @PostMapping("/login")
    public ResponseEntity<Student> login(@RequestBody Login login){
//        System.out.println(login.getUsername());
        Student student = studentService.findByUserName(login.getUsername());
        if(student == null){
            return ResponseEntity.notFound().build();
        }
;
        if(BCrypt.checkpw(login.getPassword(), student.getPassword())){
            return ResponseEntity.ok(student);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Student> findStudentByUsername(@PathVariable String username){
        Student student = studentService.findByUserName(username);
        if(student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

//    @PostMapping(value = "/find/{username}")
//    public Student findByUsername(@)
//    @PostMapping
//    @ResponseStatus(HttpStatus.ACCEPTED)
//    public Student loginStudent(@RequestBody Login login){
//        return studentService.loginStudent(login);
//    }
}
