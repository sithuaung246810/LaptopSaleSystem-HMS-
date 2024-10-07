package com.laptopsale.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import com.laptopsale.dto.HistoryDTO;
import com.laptopsale.entity.Brand;
import com.laptopsale.entity.Laptop;
import com.laptopsale.entity.PurchaseDetail;

import jakarta.transaction.Transactional;

public interface LaptopService {
	void addLaptop(Laptop laptop);
	List<Laptop> getAllLaptops(String keyword);
//	List<Laptop> getReverseAllLaptops();
	void deleteLaptopById(Integer id);
	Optional<Laptop> getAdminLaptopById(Integer id);
	List<Laptop> getAllLaptopsByBrandId(Integer id);
//	@Query("SELECT lap from Laptop lap WHERE lap.stock LIKE %?1%") 
	Page<Laptop> findPaginated(Integer pageNo, Integer pageSize,String sortField,String sortDirection);
//	Page<Laptop> findPaginated(String keyword,Integer pageNo, Integer pageSize);
//	Page<Laptop> listAll();
	 public List<Laptop> getFeaturedLaptops();
	 public List<Laptop> getLatestLaptops();
	 
	 public List<Laptop> getAllLaptops();

	 public Laptop getLaptopById(int id) ;
	 public List<Laptop> getLaptopsByBrand(String brand) ;
	 @Transactional
	 public void updateStock(int laptopId, int quantityPurchased);
	 
	 
//	 public Page<Laptop> getPaginatedActiveLaptops(Page<Laptop> page);
	 
	Page<Laptop> getPaginatedActiveLaptops(Pageable pageable);
	
	public List<Laptop> findAllActiveLaptopList();
	 
	 public void softDeletLaptops(Integer id);
	 
	 public Integer sumActiveLaptops();
	void updateLaptop(Laptop laptop);
	 
	 
}
