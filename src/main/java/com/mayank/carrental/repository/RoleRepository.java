package com.mayank.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mayank.carrental.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}