package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Exercise;
import com.fozf.jsoccwebservices.domain.ExerciseItem;
import com.fozf.jsoccwebservices.repositories.ExerciseItemRepository;
import com.fozf.jsoccwebservices.repositories.ExerciseRepository;
import com.fozf.jsoccwebservices.services.ExerciseItemService;
import com.fozf.jsoccwebservices.services.ExerciseService;
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

    private final ExerciseItemRepository exerciseItemRepository;

    public ExerciseItemController(ExerciseItemRepository exerciseItemRepository, ExerciseItemService exerciseItemService) {
        this.exerciseItemRepository = exerciseItemRepository;
        this.exerciseItemService = exerciseItemService;
    }

    private final ExerciseItemService exerciseItemService;

    @GetMapping()
    List<ExerciseItem> getAllExerciseItems(){
        return exerciseItemService.findAll();
    }
//
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseItem> getByExerciseItemId(@PathVariable long id){
        ExerciseItem exerciseItem = exerciseItemService.findById(id);
        if(exerciseItem != null){
            return ResponseEntity.ok(exerciseItem);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<ExerciseItem> saveExercise(@RequestBody ExerciseItem exerciseItem){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(exerciseItem.getId())
                .toUri();
        return ResponseEntity.created(uri).body(exerciseItemService.saveExercise(exerciseItem));
    }

    @GetMapping("/findUsingExerciseId/{exerciseId}")
    public ResponseEntity<List<ExerciseItem>> findUsingCourseId(@PathVariable long exerciseId){
        List<ExerciseItem> exerciseItems = exerciseItemService.findByExerciseId(exerciseId);

        if(exerciseItems != null){
            return ResponseEntity.ok(exerciseItems);
        }

        return ResponseEntity.notFound().build();
    }

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
    @DeleteMapping("/{exerciseItemId}")
    public void deleteExerciseItem(@PathVariable long exerciseItemId){
        exerciseItemRepository.deleteById(exerciseItemId);
    }

}
