package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(unique = true, nullable = false)
    private String courseCode;
    @Column(nullable = false)
    private String enrollmentKey;

    @ManyToOne(fetch = FetchType.LAZY)
    private Instructor instructor;
    private Date dateAdded;
    private Date dateModified;

    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL,
            mappedBy = "course")
    private Grade grade;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Exercise> exercises = new HashSet<>();
}
