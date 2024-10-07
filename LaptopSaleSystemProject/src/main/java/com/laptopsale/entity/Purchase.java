package com.laptopsale.entity;

import java.sql.Date;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Purchase {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long purchaseId;

	    @ManyToOne
	    @JoinColumn(name = "id")
	    private User user;

	    private Date purchaseDate;
	    
	    public List<PurchaseDetail> getPurchaseDetails() {
			return purchaseDetails;
		}

		public void setPurchaseDetails(List<PurchaseDetail> purchaseDetails) {
			this.purchaseDetails = purchaseDetails;
		}

		@OneToMany(mappedBy = "purchase")
	    private List<PurchaseDetail> purchaseDetails;

	 

		public Long getPurchaseId() {
			return purchaseId;
		}

		public void setPurchaseId(Long purchaseId) {
			this.purchaseId = purchaseId;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public Date getPurchaseDate() {
			return purchaseDate;
		}

		public void setPurchaseDate(Date purchaseDate) {
			this.purchaseDate = purchaseDate;
		}

		
	    
}