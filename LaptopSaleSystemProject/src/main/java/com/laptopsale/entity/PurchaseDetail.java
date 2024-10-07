package com.laptopsale.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PurchaseDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pDetailId;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

    private int quantity;
    
//    public PurchaseDetail(Laptop laptop, int quantity, User user) {
//        this.laptop = laptop;
//        this.quantity = quantity;
//        
//    }

	public Long getpDetailId() {
		return pDetailId;
	}

	public void setpDetailId(Long pDetailId) {
		this.pDetailId = pDetailId;
	}

	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

	public Laptop getLaptop() {
		return laptop;
	}

	public void setLaptop(Laptop laptop) {
		this.laptop = laptop;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
}