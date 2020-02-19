package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<Student> students;

    @ManyToMany(mappedBy = "roles")
    private Set<Instructor> instructors;

    @ManyToMany(mappedBy = "roles")
    private Set<Admin> admins;


    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn (
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn (
                    name = "privilege_id", referencedColumnName = "id"))
    @JsonIgnoreProperties("roles")
    private List<Privilege> privileges;


    public Role() {
        super();
    }

    public Role(final String name) {
        super();
        this.name = name;
    }



}
