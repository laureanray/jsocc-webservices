package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.services.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(InstructorController.BASE_URL)
public class InstructorController {

   final static String BASE_URL = "api/v1/instructor";
   private final InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    List<Instructor> getAllInstructors() { return instructorService.findAllInstructor(); }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Instructor saveInstructor(@RequestBody Instructor instructor){
        return instructorService.saveInstructor(instructor);
    }

}
