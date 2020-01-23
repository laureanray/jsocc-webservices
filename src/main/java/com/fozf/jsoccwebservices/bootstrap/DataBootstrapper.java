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
public class DataBootstrapper implements CommandLineRunner {


    // Static student list for testing

    public static List<Student> students = new ArrayList<>();
    public static List<Instructor> instructors = new ArrayList<>();

    private StudentRepository studentRepository;
    private InstructorRepository instructorRepository;
    private CourseRepository courseRepository;
    private ExerciseRepository exerciseRepository;
    private ExerciseItemRepository exerciseItemRepository;
    private CourseTemplateRepository courseTemplateRepository;

    public DataBootstrapper(StudentRepository studentRepository,
                            InstructorRepository instructorRepository,
                            CourseRepository courseRepository,
                            ExerciseRepository exerciseRepository,
                            ExerciseItemRepository exerciseItemRepository,
                            CourseTemplateRepository courseTemplateRepository
        ) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.exerciseRepository = exerciseRepository;
        this.exerciseItemRepository = exerciseItemRepository;
        this.courseTemplateRepository = courseTemplateRepository;
    }


    @Override
    public void run(String... args) throws Exception {


        Student student1 = new Student();
        student1.setFirstName("Laurean Ray");
        student1.setLastName("Bahala");
        student1.setPassword(BCrypt.hashpw("P@$$w0rd", BCrypt.gensalt(10)));
        student1.setEmail("laureanraybahala@gmail.com");
        student1.setUsername("laureanray");


        students.add(student1);

        Instructor instructor1 = new Instructor();
        instructor1.setFirstName("Juan");
        instructor1.setLastName("Dela Cruz");
        instructor1.setUsername("juan");
        instructor1.setPassword(BCrypt.hashpw("P@$$w0rd", BCrypt.gensalt(10)));
        instructor1.setEmail("juan@gmail.com");

        instructors.add(instructor1);

        Course course1 = new Course();
        course1.setCourseTitle("Basic Java Programming");
        course1.setCourseDescription("This course is an introductory to the Java programming language.");
        course1.setCourseCode("COEN3400");
        course1.setEnrollmentKey("basicjava");
        course1.setInstructor(instructor1);
        course1.setDateAdded(new Date());
        course1.setCourseProgrammingLanguage("Java");

//        Course course2 = new Course();
//        course2.setCourseTitle("Advanced Java Programming");
//        course2.setCourseDescription("This course in for advanced Java programming.");
//        course2.setCourseCode("COEN3401");
//        course2.setEnrollmentKey("advancedjava");
//        course2.setInstructor(instructor1);
//        course2.setDateAdded(new Date());
//        course2.setCourseProgrammingLanguage("Java");
//
//        Course course3 = new Course();
//        course3.setCourseTitle("Basic Python Programming");
//        course3.setCourseDescription("This course is an introductory to the Python programming language.");
//        course3.setCourseCode("COEN3500");
//        course3.setEnrollmentKey("basicpython");
//        course3.setInstructor(instructor1);
//        course3.setDateAdded(new Date());
//        course3.setCourseProgrammingLanguage("Python");
//
//        Course course4 = new Course();
//        course4.setCourseTitle("Advanced Python Programming");
//        course4.setCourseDescription("This course in for advanced Python programming.");
//        course4.setCourseCode("COEN3501");
//        course4.setEnrollmentKey("advancedpython");
//        course4.setInstructor(instructor1);
//        course4.setDateAdded(new Date());
//        course4.setCourseProgrammingLanguage("Python");

        // Create course templates
        CourseTemplate template1  = new CourseTemplate();
        template1.setCourseProgrammingLanguage("Java");
        template1.setCourseTemplateCode("COEN3400");
        template1.setCourseTitle("Basic Java Programming");

        CourseTemplate template2 = new CourseTemplate();
        template2.setCourseProgrammingLanguage("Java");
        template2.setCourseTemplateCode("COEN3401");
        template2.setCourseTitle("Advanced Java Programming");

        CourseTemplate template3  = new CourseTemplate();
        template3.setCourseProgrammingLanguage("Python");
        template3.setCourseTemplateCode("COEN3500");
        template3.setCourseTitle("Basic Python Programming");

        CourseTemplate template4 = new CourseTemplate();
        template4.setCourseProgrammingLanguage("Python");
        template4.setCourseTemplateCode("COEN3501");
        template4.setCourseTitle("Advanced Python Programming");

        Exercise exercise = new Exercise();
        exercise.setExerciseTitle("Hello, World. ");
        exercise.setDateAdded(new Date());
        exercise.setExerciseDescription("Hello World!");
        exercise.setCourse(course1);
        exercise.setExerciseDeadline(new Date());

        ExerciseItem exerciseItem = new ExerciseItem();
        exerciseItem.setItemTitle("Printing to the STDOUT");
        exerciseItem.setItemDescription("Use stdout to print Hello, World.");
        exerciseItem.setPoints(10);
        exerciseItem.setExercise(exercise);
        TestCase testCase1 = new TestCase();
        testCase1.setInput(null);
        testCase1.setOutput("Hello, World.");
        testCase1.setExerciseItem(exerciseItem);

        Exercise exercise1 = new Exercise();
        exercise1.setExerciseTitle("Variables and Types");
        exercise1.setDateAdded(new Date());
        exercise1.setExerciseDescription("This exercise test your understanding about Java variables and types. ");
        exercise1.setCourse(course1);
        exercise1.setExerciseDeadline(new Date());

        ExerciseItem exerciseItem1 = new ExerciseItem();
        exerciseItem1.setItemTitle("Integers");
        exerciseItem1.setItemDescription("All about integers");
        exerciseItem1.setPoints(10);
        exerciseItem1.setExercise(exercise1);
        TestCase testCase11 = new TestCase();
        testCase11.setInput(null);
        testCase11.setOutput("Hello, World.");
        testCase11.setExerciseItem(exerciseItem1);

        exerciseItem.getTestCases().add(testCase1);
        exerciseItem1.getTestCases().add(testCase11);
        exercise.getExerciseItems().add(exerciseItem);
        exercise1.getExerciseItems().add(exerciseItem1);
        course1.getExercises().add(exercise);
        course1.getExercises().add(exercise1);
        instructor1.getCourses().add(course1);
//        instructor1.getCourses().add(course2);

        if(instructorRepository.count() == 0){
            instructorRepository.save(instructor1);
        }



        if(studentRepository.count() == 0){

            for(Student student : students) {
                studentRepository.save(student);
            }

        }

        if(courseTemplateRepository.count() == 0){
            courseTemplateRepository.save(template1);
            courseTemplateRepository.save(template2);
            courseTemplateRepository.save(template3);
            courseTemplateRepository.save(template4);
        }

        System.out.println("Current # of student in database:  " + studentRepository.count());
        System.out.println("Current # of instructor in database: " + instructorRepository.count());
        System.out.println("Current # of grade in database: " + courseRepository.count());
        System.out.println("Current # of course in database: " + courseRepository.count());
        System.out.println("Current # of course template in database: " + courseTemplateRepository.count());
        System.out.println("Current # of exercises in database: " + exerciseRepository.count());
        System.out.println("Current # of exercises_items in database: " + exerciseItemRepository.count());
    }
}
