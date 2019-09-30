package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity

public class CourseCategory {
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
    private Date dateAdded;
    private Date dateModified;
    private Date enrollmentStartDate;
    private Date enrollmentEndDate;
}
