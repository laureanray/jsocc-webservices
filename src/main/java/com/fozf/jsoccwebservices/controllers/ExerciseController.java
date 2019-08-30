package com.fozf.jsoccwebservices.controllers;

import com.fozf.jsoccwebservices.domain.Exercise;
import com.fozf.jsoccwebservices.repositories.ExerciseRepository;
import com.fozf.jsoccwebservices.services.ExerciseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.xml.ws.Response;
import java.net.URI;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(ExerciseController.BASE_URL)
public class ExerciseController {

    public static final String BASE_URL = "api/v1/exercise";

    private final ExerciseService exerciseService;
    private final ExerciseRepository exerciseRepository;

    public ExerciseController(ExerciseService exerciseService, ExerciseRepository exerciseRepository)
    {
        this.exerciseService = exerciseService;
        this.exerciseRepository = exerciseRepository;
    }

    @GetMapping()
    List<Exercise> getAllExercise(){
        return exerciseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getByExerciseId(@PathVariable long id){
        Exercise exercise = exerciseService.findById(id);
        if(exercise != null){
            return ResponseEntity.ok(exercise);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Exercise> saveExercise(@RequestBody Exercise exercise){
        exercise.setDateAdded(new Date());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(exercise.getId())
                .toUri();
        return ResponseEntity.created(uri).body(exerciseService.saveExercise(exercise));
    }


    @GetMapping("/findUsingCourseId/{courseId}")
    public ResponseEntity<List<Exercise>> findUsingCourseId(@PathVariable long courseId){
        List<Exercise> exercises = exerciseService.findByCourseId(courseId);

        if(exercises != null){
            return ResponseEntity.ok(exercises);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Exercise> updateExercise(@RequestBody Exercise newExercise, @PathVariable long id){

        Exercise updated = exerciseRepository.findById(id).map(exercise -> {
            if(newExercise.getExerciseTitle() != null) exercise.setExerciseTitle(newExercise.getExerciseTitle());
            if(newExercise.getExerciseDescription() != null) exercise.setExerciseDescription(newExercise.getExerciseDescription());
            if(newExercise.getExerciseDeadline() != null) exercise.setExerciseDeadline(newExercise.getExerciseDeadline());

            exercise.setDateModified(new Date());
            return exerciseRepository.save(exercise);
        }).orElseGet(() -> {
            newExercise.setId(id);
            return exerciseRepository.save(newExercise);
        });

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable long id){
        exerciseRepository.deleteById(id);
    }

}
