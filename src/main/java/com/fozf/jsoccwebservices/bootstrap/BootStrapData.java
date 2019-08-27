package com.fozf.jsoccwebservices.bootstrap;

import com.fozf.jsoccwebservices.domain.*;
import com.fozf.jsoccwebservices.repositories.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class BootStrapData implements CommandLineRunner {

    private StudentRepository studentRepository;
    private InstructorRepository instructorRepository;
    private CourseRepository courseRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseItemRepository exerciseItemRepository;

    public BootStrapData(StudentRepository studentRepository,
                         InstructorRepository instructorRepository,
                         CourseRepository courseRepository,
                         ExerciseRepository exerciseRepository,
                         ExerciseItemRepository exerciseItemRepository
        ) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseItemRepository = exerciseItemRepository;
    }


    @Override
    public void run(String... args) throws Exception {

        Student student1 = new Student();
        student1.setFirstName("Laurean Ray");
        student1.setLastName("Bahala");
        student1.setPassword(BCrypt.hashpw("P@$$w0rd", BCrypt.gensalt(10)));
        student1.setEmail("laureanraybahala@gmail.com");
        student1.setUsername("laureanray");


        Instructor instructor1 = new Instructor();
        instructor1.setFirstName("Juan");
        instructor1.setLastName("Dela Cruz");
        instructor1.setUsername("juan");
        instructor1.setPassword(BCrypt.hashpw("P@$$w0rd", BCrypt.gensalt(10)));
        instructor1.setEmail("juan@gmail.com");


        Course course1 = new Course();
        course1.setCourseTitle("Object Oriented Programming");
        course1.setCourseDescription("Description blab bla");
        course1.setCourseCode("COEN3331");
        course1.setEnrollmentKey("123456");
        course1.setInstructor(instructor1);
        course1.setDateAdded(new Date());

        Course course2 = new Course();
        course2.setCourseTitle("Object Oriented Programming 2");
        course2.setCourseDescription("Description blab bla");
        course2.setCourseCode("COEN3332");
        course2.setEnrollmentKey("123456");
        course2.setInstructor(instructor1);
        course2.setDateAdded(new Date());

        Exercise exercise = new Exercise();
        exercise.setExerciseTitle("Basic Java Programming");
        exercise.setDateAdded(new Date());
        exercise.setExerciseDescription("Create a basic java application");
        exercise.setCourse(course1);
        exercise.setExerciseDeadline(new Date());

        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.setItemTitle("Hello Java");
        exerciseItem.setItemDescription("Use stdout to print Hello, Java.");
        exerciseItem.setPoints(10);
        exerciseItem.setExercise(exercise);

        TestCase testCase1 = new TestCase();
        testCase1.setInput(null);
        testCase1.setOutput("Hello, Java.");
        testCase1.setExerciseItem(exerciseItem);



        exerciseItem.getTestCases().add(testCase1);
        exercise.getExerciseItems().add(exerciseItem);
        course1.getExercises().add(exercise);
        instructor1.getCourses().add(course1);
        instructor1.getCourses().add(course2);


        if(instructorRepository.count() == 0){
            instructorRepository.save(instructor1);
        }

        if(studentRepository.count() == 0){
            studentRepository.save(student1);
        }

        System.out.println("Current # of student in database:  " + studentRepository.count());
        System.out.println("Current # of instructor in database: " + instructorRepository.count());
        System.out.println("Current # of grade in database: " + courseRepository.count());
        System.out.println("Current # of course in database: " + courseRepository.count());
        System.out.println("Current # of exercises in database: " + exerciseRepository.count());
        System.out.println("Current # of exercises_items in database: " + exerciseItemRepository.count());
    }
}
