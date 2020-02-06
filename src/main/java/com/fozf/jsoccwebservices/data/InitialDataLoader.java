package com.fozf.jsoccwebservices.data;

import com.fozf.jsoccwebservices.domain.*;
import com.fozf.jsoccwebservices.repositories.*;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private AdminRepository adminRepository;


    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_STUDENT", Arrays.asList(readPrivilege));
        createRoleIfNotFound("ROLE_INSTRUCTOR", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role studentRole = roleRepository.findByName("ROLE_STUDENT");
        Role instructorRole = roleRepository.findByName("ROLE_INSTRUCTOR");


        Admin admin = new Admin();
        admin.setFirstName("Test");
        admin.setLastName("Test");
        admin.setPassword(passwordEncoder.encode("P@$$w0rd"));
        admin.setEmail("test@test.com");
        admin.setRoles(Arrays.asList(adminRole));
        admin.setUsername("admin");
        admin.setEnabled(true);


        Student student1 = new Student();
        student1.setFirstName("Laurean Ray");
        student1.setLastName("Bahala");
        student1.setPassword(BCrypt.hashpw("P@$$w0rd", BCrypt.gensalt(10)));
        student1.setEmail("laureanraybahala@gmail.com");
        student1.setUsername("laureanray");
        student1.setRoles(Arrays.asList(studentRole));

        Instructor instructor1 = new Instructor();
        instructor1.setFirstName("Juan");
        instructor1.setLastName("Dela Cruz");
        instructor1.setUsername("juan");
        instructor1.setPassword(BCrypt.hashpw("P@$$w0rd", BCrypt.gensalt(10)));
        instructor1.setEmail("juan@gmail.com");
        instructor1.setRoles(Arrays.asList(instructorRole));

        Course course1 = new Course();
        course1.setCourseTitle("Basic Java Programming");
        course1.setCourseDescription("This course is an introductory to the Java programming language.");
        course1.setCourseCode("COEN3400");
        course1.setEnrollmentKey("basicjava");
        course1.setInstructor(instructor1);
        course1.setDateAdded(new Date());
        course1.setCourseProgrammingLanguage("Java");

        if(adminRepository.findAll().isEmpty()){
            adminRepository.save(admin);
        }

        if(studentRepository.findAll().isEmpty()){
            studentRepository.save(student1);
        }

        if(instructorRepository.findAll().isEmpty()){
            instructorRepository.save(instructor1);
        }

        alreadySetup = true;
    }

    @Transactional
    private Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, List<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}