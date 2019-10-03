package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Exercise;
import com.fozf.jsoccwebservices.domain.ExerciseItem;
import com.fozf.jsoccwebservices.repositories.ExerciseItemRepository;
import com.fozf.jsoccwebservices.services.ExerciseItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(ExerciseItemController.BASE_URL)
public class ExerciseItemController {

    public static final String BASE_URL = "api/v1/exerciseItem";

    private final ExerciseItemService exerciseItemService;
    private final ExerciseItemRepository exerciseItemRepository;

    public ExerciseItemController(ExerciseItemService exerciseItemService, ExerciseItemRepository exerciseItemRepository)
    {

        this.exerciseItemService = exerciseItemService;
        this.exerciseItemRepository = exerciseItemRepository;
    }

    @GetMapping()
    List<ExerciseItem> getAllExerciseItems(){
        return exerciseItemService.findAll();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Exercise> getByExerciseId(@PathVariable long id){
//        Exercise exercise = exerciseService.findById(id);
//        if(exercise != null){
//            return ResponseEntity.ok(exercise);
//        }
//
//        return ResponseEntity.notFound().build();
//    }

    @PostMapping("/create")
    public ResponseEntity<ExerciseItem> saveExerciseItem(@RequestBody ExerciseItem exerciseItem){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(exerciseItem.getId())
                .toUri();
        return ResponseEntity.created(uri).body(exerciseItemService.saveExerciseItem(exerciseItem));
    }

//
//    @GetMapping("/findUsingCourseId/{courseId}")
//    public ResponseEntity<List<Exercise>> findUsingCourseId(@PathVariable long courseId){
//        List<Exercise> exercises = exerciseService.findByCourseId(courseId);
//
//        if(exercises != null){
//            return ResponseEntity.ok(exercises);
//        }
//
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("/update/{id}")
//    public ResponseEntity<Exercise> updateExercise(@RequestBody Exercise newExercise, @PathVariable long id){
//
//        Exercise updated = exerciseRepository.findById(id).map(exercise -> {
//            if(newExercise.getExerciseTitle() != null) exercise.setExerciseTitle(newExercise.getExerciseTitle());
//            if(newExercise.getExerciseDescription() != null) exercise.setExerciseDescription(newExercise.getExerciseDescription());
//            if(newExercise.getExerciseDeadline() != null) exercise.setExerciseDeadline(newExercise.getExerciseDeadline());
//
//            exercise.setDateModified(new Date());
//            return exerciseRepository.save(exercise);
//        }).orElseGet(() -> {
//            newExercise.setId(id);
//            return exerciseRepository.save(newExercise);
//        });
//
//        return ResponseEntity.ok(updated);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteExercise(@PathVariable long id){
//        exerciseRepository.deleteById(id);
//    }

}
