package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.RoleRepository;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import com.fozf.jsoccwebservices.services.CourseService;
import com.fozf.jsoccwebservices.services.InstructorService;
import com.fozf.jsoccwebservices.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(StudentController.BASE_URL)
public class StudentController {

    public static final String BASE_URL = "api/v1/students";

    private final StudentService studentService;
    private final InstructorService instructorService;
    private final CourseService courseService;
    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final String ALL = "hasAuthority('ROLE_STUDENT') or hasAuthority('ROLE_INSTRUCTOR') or hasAuthority('ROLE_ADMIN')";

    public StudentController(StudentService studentService, InstructorService instructorService, CourseService courseService, StudentRepository studentRepository, RoleRepository roleRepository)
    {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.courseService = courseService;
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping()
    @PreAuthorize(ALL)
    public List<Student> getAllStudents(){
        return studentService.findAllStudent();
    }

    @GetMapping("/{id}")
    @PreAuthorize(ALL)
    public Student getCustomerById(@PathVariable Long id){
        return studentService.findStudentById(id);
    }

    @PostMapping(value = "/register", consumes = "application/json;charset=UTF-8")
    public ResponseEntity<Student> saveStudent(@Valid @RequestBody Student student){
        // Check first if username is taken in both tables
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(student.getId())
                .toUri();

        Instructor instructor = instructorService.findByUserName(student.getUsername() );
        if(instructor == null){
            // this means this doesnt conflict to other user

            // set roles first
            student.setRoles(Arrays.asList(roleRepository.findByName("ROLE_STUDENT")));
            return ResponseEntity.created(uri).body(studentService.saveStudent(student));
        }


        return ResponseEntity.badRequest().build();

    }

    @PreAuthorize(ALL)
    @GetMapping("/find/{username}")
    public ResponseEntity<Student> findStudentByUsername(@PathVariable String username){
        Student student = studentService.findByUserName(username);
        if(student != null) {
            return ResponseEntity.ok(student);
        }
        return ResponseEntity.notFound().build();
    }

    @PreAuthorize(ALL)
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

}
