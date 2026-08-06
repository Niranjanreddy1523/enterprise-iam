package com.IAM.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.IAM.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>
{
	  Optional<RefreshToken> findByToken(String token);
	    void deleteByUserId(Long userId);

}
