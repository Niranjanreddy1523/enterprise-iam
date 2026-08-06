package com.IAM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IAM.entity.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
