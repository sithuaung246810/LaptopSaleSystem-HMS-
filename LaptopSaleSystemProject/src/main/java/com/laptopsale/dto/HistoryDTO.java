package com.laptopsale.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistoryDTO {
	private long purchaseId;
    private String name;
    private String email;
    private String phone;
    private Date date;

    private List<String> model;
    private List<String> brand;
    private List<Double> price;
    private List<Integer> quantity;
    
    private Double total;
    
}