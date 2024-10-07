package com.laptopsale.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.laptopsale.dto.HistoryDTO;


@Component
public class PDFGenerator {
	
	public void generateItinerary(HistoryDTO historyDTO, String filePath) {
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(filePath));
			document.open();
			document.add(generateTable(historyDTO));
			document.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (DocumentException e) {

			e.printStackTrace();
		}

	}
	
	private Element generateTable(HistoryDTO historyDTO) {
	    PdfPTable table = new PdfPTable(5);
	    PdfPCell cell;
	    Font headerFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
	    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
	    Phrase header = (new Phrase("Purchase Summary by HMS Laptop Sales",headerFont));
	    cell = new PdfPCell(header);
	    cell.setColspan(5);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	    cell.setPadding(10);
	    table.addCell(cell);

	    table.addCell(createCell("Purchase ID: " + historyDTO.getPurchaseId(), 5));
	    table.addCell(createCell("Purchase Date: " + historyDTO.getDate(), 5));
	    table.addCell(createCell("Customer Name: " + historyDTO.getName(), 5));
	    table.addCell(createCell("Customer Email: " + historyDTO.getEmail(), 5));
	    table.addCell(createCell("Customer Phone: " + historyDTO.getPhone(), 5));

	    table.addCell(createHeaderCell("Model"));
	    table.addCell(createHeaderCell("Brand"));
	    table.addCell(createHeaderCell("Quantity"));
	    table.addCell(createHeaderCell("Unit Price"));
	    table.addCell(createHeaderCell("Total Price"));

	    List<String> models = historyDTO.getModel();
	    List<String> brands = historyDTO.getBrand();
	    List<Double> prices = historyDTO.getPrice();
	    List<Integer> quantities = historyDTO.getQuantity();

	    for (int i = 0; i < models.size(); i++) {
	        table.addCell(createCell(models.get(i),1,Element.ALIGN_CENTER));
	        table.addCell(createCell(brands.get(i),1,Element.ALIGN_CENTER));
	        table.addCell(createCell(quantities.get(i).toString(),1,Element.ALIGN_CENTER));
	        table.addCell(createCell("$" + numberFormat.format(prices.get(i)),1,Element.ALIGN_RIGHT));
	        table.addCell(createCell(numberFormat.format(prices.get(i) * quantities.get(i)),1,Element.ALIGN_RIGHT));
	    }

	    cell = new PdfPCell(new Phrase("Total: $" + numberFormat.format(historyDTO.getTotal()) ));
	    cell.setColspan(5);
	    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	    cell.setPadding(5);
	    table.addCell(cell);

	    return table;
	}

	private PdfPCell createCell(String text, int colspan) {
	    return createCell(text, colspan, Element.ALIGN_LEFT);
	}

	private PdfPCell createCell(String text, int colspan, int alignment) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setColspan(colspan);
	    cell.setHorizontalAlignment(alignment);
	    cell.setPadding(5);
	    return cell;
	}

	private PdfPCell createHeaderCell(String text) {
	    PdfPCell cell = new PdfPCell(new Phrase(text));
	    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
	    cell.setPadding(5);
	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    return cell;
	}

}