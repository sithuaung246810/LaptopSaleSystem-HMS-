package com.laptopsale.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptopsale.Repository.PurchaseRepository;
import com.laptopsale.entity.Purchase;


@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;

    public Purchase savePurchase(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

 
}