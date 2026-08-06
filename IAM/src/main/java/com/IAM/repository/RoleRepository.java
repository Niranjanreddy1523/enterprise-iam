package com.IAM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IAM.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> 
{
	

}
