package com.fozf.jsoccwebservices.services;

import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.domain.Instructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AdminService {
    Admin findByUsername(String username);
}
