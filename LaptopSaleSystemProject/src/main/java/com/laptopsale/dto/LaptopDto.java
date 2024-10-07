package com.laptopsale.dto;
import lombok.Data;

@Data
public class LaptopDto {
	private Integer id;
	private String modelNo;
	private String serialNo;	
	private String imageName;
//	private String description;
	private Integer price;
	private Integer stock;
	private String specification;
	
	private String laptopName;
	private Integer brandId;
}
