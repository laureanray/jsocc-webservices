package com.fozf.jsoccwebservices.services.impl;

import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.repositories.AdminRepository;
import com.fozf.jsoccwebservices.services.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
