package com.laptopsale.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.laptopsale.Repository.BrandRepository;
import com.laptopsale.entity.Brand;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	BrandRepository brandRepo;

	@Override
	public void addBrand(Brand brand) {
		// TODO Auto-generated method stub
	
		if(brandRepo.findByBrandName(brand.getBrandName()) !=null) {
			throw new IllegalArgumentException("Brand Name is already exist");
		}
		brandRepo.save(brand);
		
	}
	

	@Override
	public void updateBrand(Brand brand) {
		// TODO Auto-generated method stub
		brandRepo.save(brand);
	}


	@Override
	public List<Brand> getAllBrands(String keyword) {
		// TODO Auto-generated method stub
		if(keyword != null) {
			return brandRepo.findAll(keyword);
		}
		return brandRepo.findAll(); //Ascending..
//		return brandRepo.findAll().reversed();
	}

	@Override
	public void deleteBrandById(Integer id) {
		// TODO Auto-generated method stub
		brandRepo.deleteById(id);
		
	}

	@Override
	public Optional<Brand> getBrandById(Integer id) {
		// TODO Auto-generated method stub
		return brandRepo.findById(id);
	}

	@Override
	public Page<Brand> getBrandBySorting(String sortField, String sortDirection) {
		// TODO Auto-generated method stub
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():Sort.by(sortField).descending();
		
	
		return (Page<Brand>) brandRepo.findAll(sort);
	

	}
	
	 public List<Brand> getAllBrandsSorted(String sortField, String sortDir) {
	        // Determine sorting direction
	        Sort.Direction direction = sortDir.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	        
	        // Create Sort object based on the field and direction
	        Sort sort = Sort.by(direction, sortField);
	        
	        // Fetch sorted list from repository
	        return brandRepo.findAll(sort);
	    }

	@Override
	public Page<Brand> findAllActiveBrands(Integer pageNo, Integer pageSize,String sortField,String sortDirection) {
		// TODO Auto-generated method stub
		/*
		 * Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?
		 * Sort.by(sortField).ascending():Sort.by(sortField).descending();
		 * 
		 * Pageable pageable = PageRequest.of(pageNo -1 , pageSize);
		 *///not show 0 page
		
		 Pageable pageable = PageRequest.of(pageNo - 1, pageSize, 
				 sortDirection.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
		
		return brandRepo.findByDeletedFalse(pageable);
	}

	@Override
	public void softDeletBrands(Integer id) {
		// TODO Auto-generated method stub
		
		Optional<Brand> brandsoft = brandRepo.findById(id);
		if(brandsoft.isPresent()) {
			Brand brand = brandsoft.get();
			brand.setDeleted(true);
			brandRepo.save(brand);
		}
		
		
	}

	@Override
	public Integer countActiveBrand() {
		// TODO Auto-generated method stub
		return brandRepo.countActiveBrand();
	}

	







	



}
