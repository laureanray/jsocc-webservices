package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Instructor extends User{
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Course> courses = new HashSet<>();

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(name = "instructor_roles", joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

}
