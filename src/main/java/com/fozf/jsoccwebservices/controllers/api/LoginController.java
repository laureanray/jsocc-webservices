package com.fozf.jsoccwebservices.controllers.api;

import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Login;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.domain.User;
import com.fozf.jsoccwebservices.services.InstructorService;
import com.fozf.jsoccwebservices.services.StudentService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(LoginController.BASE_URL)
public class LoginController {

    public static final String BASE_URL = "api/v1/auth";
    private final StudentService studentService;
    private final InstructorService instructorService;

    public LoginController(StudentService studentService, InstructorService instructorService)
    {
        this.studentService = studentService;
        this.instructorService = instructorService;
    }

    @PostMapping("/signin")
    public ResponseEntity<User> signin(@RequestBody Login login){
        Student student = studentService.findByUserName(login.getUsername());
        Instructor instructor = instructorService.findByUserName(login.getUsername());
        if(student != null){

            if(BCrypt.checkpw(login.getPassword(), student.getPassword())){
                return ResponseEntity.ok(student);
            }else {
                return ResponseEntity.badRequest().build();
            }
        }else if(instructor != null){

            if(BCrypt.checkpw(login.getPassword(),  instructor.getPassword())){
                return ResponseEntity.ok(instructor);
            }else {
                return ResponseEntity.badRequest().build();
            }
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
