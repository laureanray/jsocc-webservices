package com.fozf.jsoccwebservices.domain;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Student> students;

    @ManyToMany(mappedBy = "roles")
    private Collection<Instructor> instructors;

    @ManyToMany(mappedBy = "roles")
    private Collection<Admin> admins;


    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn (
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn (
                    name = "privilege_id", referencedColumnName = "id"))

    private Collection<Privilege> privileges;


    public Role() {
        super();
    }

    public Role(final String name) {
        super();
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Student> getStudents() {
        return students;
    }

    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    public Collection<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(Collection<Instructor> instructors) {
        this.instructors = instructors;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
