package com.fozf.jsoccwebservices.data;

import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.domain.Privilege;
import com.fozf.jsoccwebservices.domain.Role;
import com.fozf.jsoccwebservices.domain.User;
import com.fozf.jsoccwebservices.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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
        Admin admin = new Admin();
        admin.setFirstName("Test");
        admin.setLastName("Test");
        admin.setPassword(passwordEncoder.encode("P@$$w0rd"));
        admin.setEmail("test@test.com");
        admin.setRoles(Arrays.asList(adminRole));
        admin.setUsername("admin");
        admin.setEnabled(true);

        if(adminRepository.findAll().isEmpty()){
            adminRepository.save(admin);
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