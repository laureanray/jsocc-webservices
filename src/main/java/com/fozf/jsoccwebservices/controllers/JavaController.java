package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.CompileTask;
import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.exceptions.ResourceNotFoundException;
import com.fozf.jsoccwebservices.javac.JavaCompiler;
import com.fozf.jsoccwebservices.services.CourseService;
import com.fozf.jsoccwebservices.services.InstructorService;
import com.fozf.jsoccwebservices.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(JavaController.BASE_URL)
public class JavaController {

    public static final String BASE_URL = "api/v1/javac";
    private JavaCompiler javaCompiler;

    public JavaController()
    {
        javaCompiler = new JavaCompiler();
    }


    // This methods
    @CrossOrigin(origins = "http://localhost:5500")
    @PostMapping("/compile")
    public DeferredResult<CompileTask> compile(@RequestBody CompileTask compileTask){

        final DeferredResult<CompileTask> deferredResult = new DeferredResult<>();

        javaCompiler.Compile(compileTask)
            .registerOnCompileListener(res -> {
                System.out.println(res);
                deferredResult.setResult(res);
            });


        return deferredResult;
    }

//    @CrossOrigin(origins = "http://localhost:5500")
//    @PostMapping("/compileAndRun")
//    public DeferredResult<CompileTask> compileAndRun(@RequestBody CompileTask compileTask){
//        final DeferredResult<CompileTask> deferredResult = new DeferredResult<>();
//
//        javaCompiler.CompileAndRun(compileTask)
//                .registerOnCompileListener(res -> {
//
//                });
//    }

//    @GetMapping("/findByInstructorId/{id}")
//    public List<Course> findByInstructorId(@PathVariable long id){
//        return courseService.findByInstructorId(id);
//    }
//
//    @PutMapping("/updateById/{id}")
//    public Course updateById(@PathVariable long id, @Valid @RequestBody Course newCourse){
//        return courseService.findById(id).map(course -> {
//
//            if (!newCourse.getCourseCode().isEmpty()) {
//                course.setCourseCode(newCourse.getCourseCode());
//            }
//
//            if(!newCourse.getCourseDescription().isEmpty()) {
//                course.setCourseDescription(newCourse.getCourseDescription());
//            }
//
//            if(!newCourse.getCourseTitle().isEmpty()){
//                course.setCourseTitle(newCourse.getCourseTitle());
//            }
//
//            course.setDateModified(new Date());
//            return courseService.saveCourse(course);
//        }).orElseThrow(() -> new ResourceNotFoundException("course_id: " + id + " not found"));
//    }



    }
