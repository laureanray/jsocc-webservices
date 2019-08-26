package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Login;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.services.CourseService;
import com.fozf.jsoccwebservices.services.InstructorService;
import com.fozf.jsoccwebservices.services.StudentService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(CourseController.BASE_URL)
public class CourseController {

    public static final String BASE_URL = "api/v1/course";

    private final StudentService studentService;
    private final InstructorService instructorService;
    private final CourseService courseService;

    public CourseController(StudentService studentService, InstructorService instructorService, CourseService courseService)
    {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @GetMapping()
    List<Course> getAllCourse(){
        return courseService.findAllCourse();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable long id){
        Course course = courseService.findById(id);
        if(course != null){
            return ResponseEntity.ok(course);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course){
        course.setDateAdded(new Date());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUri();
        return ResponseEntity.created(uri).body(courseService.saveCourse(course));
    }


//    @GetMapping("/find/{code}")
//    public ResponseEntity<Course> findByCode(@PathVariable String code){
//        Course course = courseService.findByCode(code);
//        if(course != null) {
//            return ResponseEntity.ok(course);
//        }
//        return ResponseEntity.notFound().build();
//    }
//
//    @GetMapping("/findUsingInstructorId/{instructorId}")
//    public ResponseEntity<List<Course>> findUsingInstructorId(@PathVariable long instructorId){
//        List<Course> courses = courseService.findByInstructorId(instructorId);
//
//        if(courses != null){
//            return ResponseEntity.ok(courses);
//        }
//
//        return ResponseEntity.notFound().build();
//    }

}
