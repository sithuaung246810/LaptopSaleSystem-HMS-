package com.laptopsale.Service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptopsale.Repository.PurchaseDetailRepository;
import com.laptopsale.dto.HistoryDTO;
import com.laptopsale.entity.PurchaseDetail;
import com.laptopsale.util.EmailUtil;
import com.laptopsale.util.PDFGenerator;



@Service
public class PurchaseDetailServiceImpl implements PurchaseDetailService {

	@Autowired
	PDFGenerator pdfGenerator;
	
	@Autowired
	EmailUtil emailUtil;
    @Autowired
    private PurchaseDetailRepository purchaseDetailRepository;

    @Override
    public PurchaseDetail savePurchaseDetail(PurchaseDetail purchaseDetail) {
        return purchaseDetailRepository.save(purchaseDetail);
    }
    @Override
    public PurchaseDetail getPurchaseDetailById(Long purchaseDetailId) {
    	return purchaseDetailRepository.findById(purchaseDetailId)
                .orElseThrow();
    }
    
	
	@Override
	public void purchaseHistory(HistoryDTO historyDTO) {
		// TODO Auto-generated method stub
		String filePath= "C:/Users/PC173 MM/Downloads/pdfPurchase/purchaseId " +historyDTO.getPurchaseId() +".pdf";
        pdfGenerator.generateItinerary(historyDTO, filePath);
        emailUtil.sendItineary (historyDTO.getEmail(), filePath);
		
	}
	@Override
	public List<PurchaseDetail> getPurchaseDetailsByDate(Date purchaseDate) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer sumAllPurchase() {
		// TODO Auto-generated method stub
		return purchaseDetailRepository.sumAllPurchase();
	}
}