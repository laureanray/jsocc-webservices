package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.*;


@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String courseTitle;
    @Column(nullable = false)
    private String courseDescription;
    @Column(nullable = false)
    @NotNull
    private String courseCode;
    @Column(nullable = false)
    private String enrollmentKey;
    @Column(nullable = false)
    private String courseProgrammingLanguage;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
//    @JsonIgnoreProperties("courses")
    private Instructor instructor;

    private Date dateAdded;
    private Date dateModified;
    private Date enrollmentStartDate;
    private Date enrollmentEndDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Exercise> exercises = new HashSet<>();

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Student> students = new HashSet<>();
}
