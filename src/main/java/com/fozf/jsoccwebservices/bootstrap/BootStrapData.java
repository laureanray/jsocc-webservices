package com.fozf.jsoccwebservices.bootstrap;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Exercise;
import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.CourseRepository;
import com.fozf.jsoccwebservices.repositories.ExerciseRepository;
import com.fozf.jsoccwebservices.repositories.InstructorRepository;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BootStrapData implements CommandLineRunner {

    private StudentRepository studentRepository;

    private InstructorRepository instructorRepository;

    private CourseRepository courseRepository;

    private ExerciseRepository exerciseRepository;

    public BootStrapData(StudentRepository studentRepository, InstructorRepository instructorRepository, CourseRepository courseRepository, ExerciseRepository exerciseRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.exerciseRepository = exerciseRepository;
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

        Course course = new Course();
        course.setCourseTitle("Object Oriented Programming");
        course.setCourseDescription("Description blab bla");
        course.setCourseCode("COEN3331");
        course.setEnrollmentKey("123456");
        course.setInstructorId(1);

        Exercise exercise = new Exercise();
        exercise.setCourse(course);
        exercise.setExerciseTitle("Basic Java");
        exercise.setExerciseDescription("Test");

        if(studentRepository.count() == 0 ){
            studentRepository.save(student1);
        }

        if(instructorRepository.count() == 0){
            instructorRepository.save(instructor1);
        }
        if(courseRepository.count() == 0){
            course.setDateAdded(new Date());
            courseRepository.save(course);

            if(exerciseRepository.count() == 0){
                exercise.setDateAdded(new Date());
                exercise.setExerciseDeadline(new Date("01/02/2020"));
                exerciseRepository.save(exercise);
            }
        }



        System.out.println("Current # of student in database:  " + studentRepository.count());
        System.out.println("Current # of instructor in database: " + instructorRepository.count());
        System.out.println("Current # of course in database: " + courseRepository.count());
        System.out.println("Current # of exercises in database: " + exerciseRepository.count());


    }
}
