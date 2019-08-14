package com.fozf.jsoccwebservices.bootstrap;

import com.fozf.jsoccwebservices.domain.Course;
import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.repositories.CourseRepository;
import com.fozf.jsoccwebservices.repositories.InstructorRepository;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {

    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;


    public BootStrapData(StudentRepository studentRepository, InstructorRepository instructorRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
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
        instructor1.setFirstName("Arvin");
        instructor1.setLastName("Dela Cruz");
        instructor1.setUsername("doca");
        instructor1.setPassword(BCrypt.hashpw("P@$$w0rd", BCrypt.gensalt(10)));
        instructor1.setEmail("docarvin@gmail.com");

        Course course = new Course();
        course.setCourseTitle("Object Oriented Programming");
        course.setCourseDescription("Description blab bla");
        course.setCourseCode("COEN3331");
        course.setEnrollmentKey("123456");
        course.setInstructorId(1);

        if(studentRepository.count() == 0 ){
            studentRepository.save(student1);
        }

        if(instructorRepository.count() == 0){
            instructorRepository.save(instructor1);
        }
        if(courseRepository.count() == 0){
            courseRepository.save(course);
        }

        System.out.println("Current # of student in database:  " + studentRepository.count());
        System.out.println("Current # of instructor in database: " + instructorRepository.count());
        System.out.println("Current # of course in database: " + courseRepository.count());

    }
}
