package com.laptopsale.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.laptopsale.entity.Brand;
import com.laptopsale.entity.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);

	User findById(Integer id);

	List<User> findByRole(String role);
	
	@Query("SELECT DISTINCT u.role FROM User u")
	List<String> findDistinctRoles();
	
	@Query("SELECT COUNT(role) FROM User WHERE role='ROLE_USER'")
	Long countUser();
	
	@Query("SELECT COUNT(role) FROM User WHERE role='ROLE_ADMIN'")
	Long countAdmin();
	
	@Query("SELECT COUNT(role) FROM User WHERE role='ROLE_BAN'")
	Long countBanUser();

	Page<User> findByRole(String role, Pageable pageable);

	
}
