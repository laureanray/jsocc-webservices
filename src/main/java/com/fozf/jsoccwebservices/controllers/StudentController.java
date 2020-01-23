package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Login;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import com.fozf.jsoccwebservices.services.CourseService;
import com.fozf.jsoccwebservices.services.InstructorService;
import com.fozf.jsoccwebservices.services.StudentService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CourseService courseService;
    private final StudentRepository studentRepository;

    public StudentController(StudentService studentService, InstructorService instructorService, CourseService courseService, StudentRepository studentRepository)
    {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.courseService = courseService;
        this.studentRepository = studentRepository;
    }

    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<Student> getAllStudents(){
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

    @PostMapping("/enroll/{username}/{courseId}")
    public ResponseEntity<Student> enrollStudentToCourse(@PathVariable String username, @PathVariable int courseId){
        Student studentToEnroll = studentService.findByUserName(username);
        Course courseToEnrollTo = courseService.findById(courseId).get();

        if(studentToEnroll != null && courseToEnrollTo != null) {
            System.out.println(studentToEnroll.getUsername());
            System.out.println(courseToEnrollTo.getCourseCode());

            studentToEnroll.getCourses().add(courseToEnrollTo);
            courseToEnrollTo.getStudents().add(studentToEnroll);

            studentService.updateStudent(studentToEnroll);
            courseService.saveCourse(courseToEnrollTo);

            return ResponseEntity.ok(studentToEnroll);
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
