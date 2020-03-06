package com.fozf.jsoccwebservices.services.impl;

import com.fozf.jsoccwebservices.domain.*;
import com.fozf.jsoccwebservices.exceptions.UserNotEnabledException;
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
import java.util.Collection;
import java.util.List;

@Component
public class _AppUserDetailsService implements UserDetailsService {
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
            return getUserDetails(authorities, student.isEnabled(), student.getRoles(), student.getUsername(), student.getPassword(), instructor);
        } else if(instructor != null) {
            return getUserDetails(authorities, instructor.isEnabled(), instructor.getRoles(), instructor.getUsername(), instructor.getPassword(), instructor);
        } else if(admin != null) {
            return getUserDetails(authorities, admin.isEnabled(), admin.getRoles(), admin.getUsername(), admin.getPassword(), instructor);
        } else {
            throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
        }
    }

    private UserDetails getUserDetails(List<GrantedAuthority> authorities, boolean enabled, Collection<Role> roles, String username, String password, Instructor instructor) {
        if (!enabled) {
            // TODO: 'Create custom exception for clearer error.
            throw new UsernameNotFoundException("Test");
        }

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        UserDetails userDetails = new org.springframework.security.core.userdetails.
                User(username, password, authorities);

        return userDetails;
    }
}
