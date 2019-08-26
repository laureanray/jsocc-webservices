package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Exercise exercise;

    @OneToMany(mappedBy = "exerciseItem", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TestCase> testCases = new HashSet<>();
}
