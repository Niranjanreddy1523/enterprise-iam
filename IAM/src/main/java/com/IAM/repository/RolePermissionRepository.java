package com.IAM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IAM.entity.RolePermission;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {

}
