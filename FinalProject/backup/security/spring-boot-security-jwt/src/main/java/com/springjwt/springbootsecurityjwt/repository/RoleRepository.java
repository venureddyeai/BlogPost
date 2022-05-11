package com.springjwt.springbootsecurityjwt.repository;

import com.springjwt.springbootsecurityjwt.models.ERole;
import com.springjwt.springbootsecurityjwt.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
