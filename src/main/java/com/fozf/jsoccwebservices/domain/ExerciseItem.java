package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class ExerciseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String itemTitle;
    private String itemDescription;
    private int points;
    private boolean isCompleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Exercise exercise;

    @OneToMany(mappedBy = "exerciseItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<TestCase> testCases = new HashSet<>();
}
