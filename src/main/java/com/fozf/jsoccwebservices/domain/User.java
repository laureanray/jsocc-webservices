package com.fozf.jsoccwebservices.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Date;


@MappedSuperclass
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    @NotEmpty(message = "Please provide email")
    private String email;
    private String password;
    @NotEmpty(message = "Please provide username")
    @Column(unique = true, nullable = false)
    private String username;
    private boolean enabled;
    private boolean tokenExpired;
    private String profileImageURL;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}
