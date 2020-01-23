package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Admin;
import com.fozf.jsoccwebservices.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
    @Override
    void delete(Admin admin);
}
