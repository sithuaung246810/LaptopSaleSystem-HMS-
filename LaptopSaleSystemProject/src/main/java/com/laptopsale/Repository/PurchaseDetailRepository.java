package com.laptopsale.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptopsale.entity.PurchaseDetail;

import java.util.Date;
import java.util.List;

@Repository
public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Long> {
	@Query("SELECT pd FROM PurchaseDetail pd JOIN pd.purchase p WHERE p.purchaseDate = :purchaseDate")
    Page<PurchaseDetail> findByPurchaseDate(@Param("purchaseDate") Date purchaseDate, Pageable pageable);
//    List<PurchaseDetail> findByPurchaseDate(@Param("purchaseDate") Date purchaseDate);
	
	@Query("SELECT SUM(p.quantity) FROM PurchaseDetail p")
	Integer sumAllPurchase();
	


	@Query("SELECT pd FROM PurchaseDetail pd JOIN pd.purchase p WHERE p.purchaseDate = :purchaseDate")
	List<PurchaseDetail> findByPurchaseDate( @Param("purchaseDate")Date purchaseDate);

	
}