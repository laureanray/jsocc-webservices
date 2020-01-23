package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Privilege;
import com.fozf.jsoccwebservices.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Override
    void delete(Role role);
}
