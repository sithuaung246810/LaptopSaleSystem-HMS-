package com.laptopsale.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
	private int laptopId;
    private String modelNo;
    private String brandName;
    private double price;
    private int quantity;
    private String image;
}
