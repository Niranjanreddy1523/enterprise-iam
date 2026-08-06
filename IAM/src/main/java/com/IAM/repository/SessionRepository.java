package com.IAM.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IAM.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long>{

}
