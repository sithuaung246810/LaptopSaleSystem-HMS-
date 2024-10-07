package com.laptopsale.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptopsale.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
	Admin findByEmail(String email);
}
