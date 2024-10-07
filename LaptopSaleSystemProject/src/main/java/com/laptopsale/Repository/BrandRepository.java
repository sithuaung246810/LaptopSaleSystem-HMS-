package com.laptopsale.Repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.laptopsale.entity.Brand;
@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{
	@Query("SELECT br FROM Brand br WHERE CONCAT(br.id,br.brandName) LIKE %?1%")
	List<Brand> findAll(String keyword);
	
	@Query("SELECT br FROM Brand br WHERE br.deleted = false")
	 Page<Brand> findAllActiveBrands( Pageable pageable);
	
	 Page<Brand> findByDeletedFalse(Pageable pageable);
	
	Brand findByBrandName(String brandName);
	
	@Query("SELECT COUNT(b) FROM Brand b WHERE b.deleted = false")
	Integer countActiveBrand();
	
//	
//	List<Brand> findAllByBrands(String brandName);

//	 Brand findByName(String brandName);
	
}
