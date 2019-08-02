package com.fozf.jsoccwebservices.repositories;

import com.fozf.jsoccwebservices.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
