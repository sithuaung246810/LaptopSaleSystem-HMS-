package com.laptopsale.Service;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.laptopsale.Repository.LaptopRepository;
import com.laptopsale.Repository.PurchaseDetailRepository;
import com.laptopsale.dto.HistoryDTO;
import com.laptopsale.entity.Brand;
import com.laptopsale.entity.Laptop;
import com.laptopsale.entity.PurchaseDetail;
import com.laptopsale.util.PDFGenerator;

import jakarta.transaction.Transactional;
@Service
public class LaptopServiceImpl  implements LaptopService{

	@Autowired
	LaptopRepository laptopRepo;
	
	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	PurchaseDetailRepository purchaseDetailRepository;
	
	
	
	
	//Admin......
//	@Override
//	public void addLaptop(Laptop laptop) {
//		laptopRepo.save(laptop);
//		
//	}

	
	@Override
	public void addLaptop(Laptop laptop) {
		if(laptopRepo.findByLaptopName(laptop.getLaptopName()) !=null) {
			throw new IllegalArgumentException(" Model Name is already exist");
		}
		laptopRepo.save(laptop);
		
		
	}
	
	@Override
	public void updateLaptop(Laptop laptop) {
		// TODO Auto-generated method stub
		laptopRepo.save(laptop);
	}
	
	@Override
	public List<Laptop> getAllLaptops(String keyword) {
		// TODO Auto-generated method stub
		if(keyword != null) {
			return laptopRepo.findAll();
		}
		
		return laptopRepo.findAll();
	}
//	@Override
//	public List<Laptop> getReverseAllLaptops() {
//		// TODO Auto-generated method stub
//		return laptopRepo.findAll().reversed();
//	}

	@Override
	public void deleteLaptopById(Integer id) {
		// TODO Auto-generated method stub
		laptopRepo.deleteById(id);
		
	}

	@Override
	public Optional<Laptop> getAdminLaptopById(Integer id) {
		// TODO Auto-generated method stub
		return laptopRepo.findById(id);
	}

	@Override
	public List<Laptop> getAllLaptopsByBrandId(Integer id) {
		// TODO Auto-generated method stub
		return laptopRepo.findAllLaptopsByBrandId(id);
	}



	@Override
	public Page<Laptop> findPaginated(Integer pageNo, Integer pageSize,String sortField,String sortDirection) {
		// TODO Auto-generated method stub
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo -1 , pageSize,sort); //not show 0 page

		return this.laptopRepo.findByDeletedFalse(pageable);
	
	}

	//User......
	
	public List<Laptop> getFeaturedLaptops() {
        List<Integer> featuredLaptopIds = Arrays.asList(4,16,20,25,32,3,2,1,7,9);
        return laptopRepo.findByidIn(featuredLaptopIds);
    }
	    
	   public List<Laptop> getLatestLaptops() {
	        List<Integer> latestLaptopIds = Arrays.asList(4,16,20,25,32);
	        return laptopRepo.findByidIn(latestLaptopIds);
	    }
	   
	    public List<Laptop> getAllLaptops() {
	        return laptopRepo.findAll();
	    }

	    public Laptop getLaptopById(int id) {
	        return laptopRepo.findById(id).orElse(null);
	    }
	    public List<Laptop> getLaptopsByBrand(String brand) {
	        return laptopRepo.findByBrandOrAll(brand);
	    }
	    
	    public void updateStock(int laptopId, int quantityPurchased) {
	        Laptop laptop = laptopRepo.findById(laptopId)
	            .orElseThrow();
	        
	        int newStock = laptop.getStock() - quantityPurchased;
	        
	        
	        laptop.setStock(newStock);
	        laptopRepo.save(laptop);
	    }

		@Override
		public Page<Laptop> getPaginatedActiveLaptops(Pageable pageable) {
			// TODO Auto-generated method stub
			return laptopRepo.findAllActiveLaptop(pageable);
		}

		@Override
		public List<Laptop> findAllActiveLaptopList() {
			// TODO Auto-generated method stub
			return laptopRepo.findAllActiveLaptopList();
		}

		@Override
		public void softDeletLaptops(Integer id) {
			// TODO Auto-generated method stub
			Optional<Laptop> laptopsoft = laptopRepo.findById(id);
			if(laptopsoft.isPresent()) {
				Laptop laptop = laptopsoft.get();
				laptop.setDeleted(true);
				laptopRepo.save(laptop);
			
		}

//		@Override
//		public Page<Laptop> getPaginatedActiveLaptops(Page<Laptop> page) {
//			// TODO Auto-generated method stub
//			return laptopRepo.findAllActiveLaptop(page);
//		}

	
		
		}

		@Override
		public Integer sumActiveLaptops() {
			// TODO Auto-generated method stub
			return laptopRepo.sumActiveLaptops();
		}

}
