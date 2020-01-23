package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Student extends User {
    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    public Student() {
        super.setStudent(true);
    }
}
