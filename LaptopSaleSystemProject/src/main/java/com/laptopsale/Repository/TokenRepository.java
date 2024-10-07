package com.laptopsale.Repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptopsale.entity.PasswordResetToken;



public interface TokenRepository extends JpaRepository<PasswordResetToken, Integer>{

	PasswordResetToken findByToken(String token);

	List<PasswordResetToken> findByExpiryDateTimeBefore(LocalDateTime now);


}