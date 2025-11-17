package com.mayank.carrental.repository;

import com.mayank.carrental.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
}
