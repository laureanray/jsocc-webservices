package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Course> courses = new HashSet<>();
}
