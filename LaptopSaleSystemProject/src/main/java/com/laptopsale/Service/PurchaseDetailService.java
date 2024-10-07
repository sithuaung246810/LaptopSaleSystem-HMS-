package com.laptopsale.Service;

import java.util.Date;
import java.util.List;

import com.laptopsale.dto.HistoryDTO;
import com.laptopsale.entity.PurchaseDetail;

public interface PurchaseDetailService {
    PurchaseDetail savePurchaseDetail(PurchaseDetail purchaseDetail);
    PurchaseDetail getPurchaseDetailById(Long purchaseDetailId);
    void purchaseHistory(HistoryDTO historyDTO);
	List<PurchaseDetail> getPurchaseDetailsByDate(Date purchaseDate);
	Integer sumAllPurchase();
}