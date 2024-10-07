package com.laptopsale.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.laptopsale.entity.Brand;
import com.laptopsale.entity.Laptop;
@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Integer> {
	
	
	List<Laptop> findAllLaptopsByBrandId(Integer id);
	
	
	   List<Laptop> findByidIn(List<Integer> ids);

	    
	    @Query("SELECT l FROM Laptop l JOIN l.brand b WHERE (:brand = 'ALL' OR LOWER(b.brandName) = LOWER(:brand))")
	    List<Laptop> findByBrandOrAll(@Param("brand") String brand);
	    
	    @Query("SELECT l FROM Laptop l WHERE l.deleted= false")
	    Page<Laptop> findAllActiveLaptop(Pageable pageable);
	    
	    @Query("SELECT l FROM Laptop l WHERE l.deleted = false")
		 List<Laptop> findAllActiveLaptopList();
	    
	    Page<Laptop> findByDeletedFalse(Pageable pageable);
	    
	    @Query("SELECT sum(l.stock) FROM Laptop l WHERE l.deleted = false")
	    Integer sumActiveLaptops();
	    
	   
		    
		    Laptop findByLaptopName(String laptopName);
	    

}
