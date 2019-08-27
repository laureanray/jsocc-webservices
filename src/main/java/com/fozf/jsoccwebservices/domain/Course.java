package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
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
    @NotNull
    private String courseCode;
    @Column(nullable = false)
    private String enrollmentKey;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Instructor instructor;
    private Date dateAdded;
    private Date dateModified;
    private Date enrollmentStartDate;
    private Date enrollmentEndDate;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Exercise> exercises = new HashSet<>();
}
