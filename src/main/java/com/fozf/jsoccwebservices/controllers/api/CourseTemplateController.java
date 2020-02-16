package com.fozf.jsoccwebservices.controllers.api;

import com.fozf.jsoccwebservices.domain.CourseTemplate;
import com.fozf.jsoccwebservices.services.CourseTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(CourseTemplateController.BASE_URL)
public class CourseTemplateController {

    public static final String BASE_URL = "api/v1/courseTemplate";

    private final CourseTemplateService courseTemplateService;

    public CourseTemplateController(CourseTemplateService courseTemplateService)
    {
        this.courseTemplateService = courseTemplateService;
    }

    @CrossOrigin(origins = "http://localhost:5500")
    @GetMapping()
    List<CourseTemplate> findAllCourseTemplate(){
        return courseTemplateService.findAllCourseTemplate();
    }

    @GetMapping("/findByProgrammingLanguage/{pl}")
    public List<CourseTemplate> findById(@PathVariable String pl){
        return courseTemplateService.findByCourseProgrammingLanguage(pl);
    }
}

//    @GetMapping("/findById/{id}")
//    public ResponseEntity<Course> findById(@PathVariable long id){
//        Course course = courseService.findById(id).get();
//        if(course != null){
//            return ResponseEntity.ok(course);
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @CrossOrigin(origins = "http://localhost:5500")
//    @PostMapping("/create")
//    public ResponseEntity<Course> saveCourse(@RequestBody Course course){
//        course.setDateAdded(new Date());
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(course.getId())
//                .toUri();
//        return ResponseEntity.created(uri).body(courseService.saveCourse(course));
//    }
//
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
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable long id){
//        courseService.removeCourse(id);
//    }
//
//    }
