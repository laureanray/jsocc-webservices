package com.fozf.jsoccwebservices.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class PasswordResetToken {
    private static final int EXPIRATION  = 60 * 24;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String token;

    @OneToOne(targetEntity = Student.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Student student;

    private Date expiryDate;
}
