package com.project1.A1.utility;

public interface StudentDatabaseUtility {

	void sendEmail(String toAddress, String toSubject, String tobody);
	
	void sendEmailwithattach(String toAddress, String toSubject, String tobody, String attach);
}
