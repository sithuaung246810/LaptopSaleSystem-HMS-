package com.laptopsale.entity;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Laptop {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "laptop_id")
	private Integer id;
	
	private String modelNo;
	private String serialNo;
	
	private String imageName;
	private String imageName2;
	
	private String laptopName;
	private Integer price;
	private Integer stock;
	private String Specification;
	
	 @Column(name = "is_deleted")
	 private boolean deleted;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "brand_id", referencedColumnName = "brand_id")
	private Brand brand;
	
	public List<String> getFormattedSpecifications() {
        return Arrays.asList(Specification.split(", "));
    }

}
