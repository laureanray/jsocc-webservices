package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@MappedSuperclass
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    //@Column(unique = true, nullable = false)
    @NotEmpty(message = "Please provide asdasdaosm")
    private String username;
    private boolean enabled;
    private boolean tokenExpired;
    private String profileImageURL;
    private boolean isStudent;
}
