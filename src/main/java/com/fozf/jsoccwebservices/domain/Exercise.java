package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Course course;

    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<ExerciseItem> exerciseItems = new HashSet<>();

    private String exerciseTitle;
    private String exerciseDescription;
    private Date exerciseDeadline;
    private Date dateAdded;
    private Date dateModified;
    private boolean isCompleted = false;
}
