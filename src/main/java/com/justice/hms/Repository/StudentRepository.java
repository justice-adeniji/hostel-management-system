package com.justice.hms.Repository;

import com.justice.hms.Model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByEmail(String email);

    Optional<Student> findByMatricNumber(String matricNumber);

    boolean existsByEmail(String email);

    boolean existsByMatricNumber(String matricNumber);

}