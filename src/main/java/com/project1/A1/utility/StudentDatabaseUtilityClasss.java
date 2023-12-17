package com.project1.A1.utility;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
public class StudentDatabaseUtilityClasss implements StudentDatabaseUtility {

	@Autowired
	private JavaMailSender sender;
	
	@Override
	public void sendEmail(String toAddress, String toSubject, String tobody) {
		
		MimeMessage message = sender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try {
			
			helper.setTo(toAddress);
			helper.setSubject(toSubject);
			helper.setText(tobody);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sender.send(message);

	}

	@Override
	public void sendEmailwithattach(String toAddress, String toSubject, String tobody, String attach) {
		
		MimeMessage message = sender.createMimeMessage();
		
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			
			helper.setTo(toAddress);
			helper.setSubject(toSubject);
			helper.setText(tobody);
			
			FileSystemResource file = new FileSystemResource(attach);
			helper.addAttachment(file.getFilename(), file);
			
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sender.send(message);
		
	}

	

}
