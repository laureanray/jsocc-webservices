package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.exceptions.ResourceNotFoundException;
import com.fozf.jsoccwebservices.services.CourseService;
import com.fozf.jsoccwebservices.services.InstructorService;
import com.fozf.jsoccwebservices.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(CourseCategoryController.BASE_URL)
public class CourseCategoryController {

    public static final String BASE_URL = "api/v1/course";

    private final StudentService studentService;
    private final InstructorService instructorService;
    private final CourseService courseService;

    public CourseCategoryController(StudentService studentService, InstructorService instructorService, CourseService courseService)
    {
        this.studentService = studentService;
        this.instructorService = instructorService;
        this.courseService = courseService;
    }

    @CrossOrigin(origins = "http://localhost:5500")
    @GetMapping()
    List<Course> findAllCourse(){
        return courseService.findAllCourse();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<Course> findById(@PathVariable long id){
        Course course = courseService.findById(id).get();
        if(course != null){
            return ResponseEntity.ok(course);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:5500")
    @PostMapping("/create")
    public ResponseEntity<Course> saveCourse(@RequestBody Course course){
        course.setDateAdded(new Date());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(course.getId())
                .toUri();
        return ResponseEntity.created(uri).body(courseService.saveCourse(course));
    }

    @GetMapping("/findByInstructorId/{id}")
    public List<Course> findByInstructorId(@PathVariable long id){
        return courseService.findByInstructorId(id);
    }

    @PutMapping("/updateById/{id}")
    public Course updateById(@PathVariable long id, @Valid @RequestBody Course newCourse){
        return courseService.findById(id).map(course -> {

            if (!newCourse.getCourseCode().isEmpty()) {
                course.setCourseCode(newCourse.getCourseCode());
            }

            if(!newCourse.getCourseDescription().isEmpty()) {
                course.setCourseDescription(newCourse.getCourseDescription());
            }

            if(!newCourse.getCourseTitle().isEmpty()){
                course.setCourseTitle(newCourse.getCourseTitle());
            }

            course.setDateModified(new Date());
            return courseService.saveCourse(course);
        }).orElseThrow(() -> new ResourceNotFoundException("course_id: " + id + " not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable long id){
        courseService.removeCourse(id);
    }

    }
