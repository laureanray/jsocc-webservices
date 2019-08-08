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

import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(InstructorController.BASE_URL)
public class InstructorController {

   final static String BASE_URL = "api/v1/instructor";
   private final InstructorService instructorService;
   private final StudentService studentService;

    public InstructorController(InstructorService instructorService, StudentService studentService) {
        this.instructorService = instructorService;
        this.studentService = studentService;
    }

    @GetMapping
    List<Instructor> getAllInstructors() { return instructorService.findAllInstructor(); }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Instructor> saveInstructor(@RequestBody Instructor instructor) throws URISyntaxException {
        // Before saving check if the username is already taken

        Student student = studentService.findByUserName(instructor.getUsername());

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(instructor.getId())
                .toUri();


        if(student == null){
            return ResponseEntity.created(uri).body(instructorService.saveInstructor(instructor));
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Instructor> login(@RequestBody Login login){
//        System.out.println(login.getUsername());
        Instructor instructor = instructorService.findByUserName(login.getUsername());
        if(instructor == null){
            return ResponseEntity.badRequest().build();
        }
        ;
        if(BCrypt.checkpw(login.getPassword(), instructor.getPassword())){
            return ResponseEntity.ok(instructor);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/find/{username}")
    public ResponseEntity<Instructor> findByUsername(@PathVariable String username){
        Instructor instructor = instructorService.findByUserName(username);
        if(instructor != null){
            return ResponseEntity.ok(instructor);
        }

        return ResponseEntity.notFound().build();
    }

}
