package com.fozf.jsoccwebservices.services.impl;

import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.domain.Instructor;
import com.fozf.jsoccwebservices.domain.Student;
import com.fozf.jsoccwebservices.domain.User;
import com.fozf.jsoccwebservices.repositories.AdminRepository;
import com.fozf.jsoccwebservices.repositories.InstructorRepository;
import com.fozf.jsoccwebservices.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppUserDetailsService implements UserDetailsService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(s);
        Instructor instructor = instructorRepository.findByUsername(s);
        Admin admin = adminRepository.findByUsername(s);
        List<GrantedAuthority> authorities = new ArrayList<>();

        if(student != null) {
            student.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

            UserDetails userDetails = new org.springframework.security.core.userdetails.
                    User(student.getUsername(), student.getPassword(), authorities);

            return userDetails;
        } else if(instructor != null) {
            instructor.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

            UserDetails userDetails = new org.springframework.security.core.userdetails.
                    User(instructor.getUsername(), instructor.getPassword(), authorities);

            return userDetails;
        } else if(admin != null) {
            admin.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });

            UserDetails userDetails = new org.springframework.security.core.userdetails.
                    User(admin.getUsername(), admin.getPassword(), authorities);

            return userDetails;
        } else {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }


    }
}
