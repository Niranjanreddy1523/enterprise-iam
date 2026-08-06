package com.IAM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IAM.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
