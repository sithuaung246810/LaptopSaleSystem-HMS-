package com.laptopsale.util;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	private String Email_Body = "Thank you for shopping with HMS online laptop sale. Please find your Itinerary attached.";
	private String Email_Subject = "Itinerary for your purchase history.";
			
	@Autowired
	private JavaMailSender sender;
	public void sendItineary(String toAddress, String filePath) {
		MimeMessage message= sender.createMimeMessage();
		try {
			MimeMessageHelper messagehelper = new MimeMessageHelper(message, true);
			messagehelper.setTo(toAddress);
			messagehelper.setSubject(Email_Body);
			messagehelper.setText(Email_Subject);

			messagehelper.addAttachment("Itinerary", new File(filePath));
			sender.send(message);
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
}