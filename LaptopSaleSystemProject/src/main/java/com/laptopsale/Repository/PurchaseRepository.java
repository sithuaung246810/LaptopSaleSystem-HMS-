package com.laptopsale.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.laptopsale.entity.Purchase;



public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
	
	
	
}
