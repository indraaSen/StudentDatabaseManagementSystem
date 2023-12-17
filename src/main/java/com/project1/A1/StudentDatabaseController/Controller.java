package com.project1.A1.StudentDatabaseController;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.project1.A1.StudentDatabaseService.studentService;
import com.project1.A1.entity.StudentDatabase;
import com.project1.A1.utility.StudentDatabaseUtility;

@RestController
public class Controller {

	@Autowired
	private studentService serv;
	
	@Autowired
	private StudentDatabaseUtility send;
	
	//below method is to save single data in database and mail them also.
	@PostMapping("/saveonedata")
	public StudentDatabase saveonedata(@RequestParam("studentname") String thestudentname, 
			@RequestParam("studentemail") String thestudentemail, 
			@RequestParam("studentphonenumber") String thestudentphonenumber,
			@RequestParam("inquiery") String theinquiery, 
			@RequestParam("dateofinquiry") @DateTimeFormat(pattern = "dd-MM-yyyy") Date thedateofinquiry,
			@RequestParam("subject") String thesubject, @RequestParam("body") String thebody) {
		
		StudentDatabase student = new StudentDatabase();
		student.setStudentname(thestudentname); 
		student.setStudentemail(thestudentemail);
		student.setStudentphonenumber(thestudentphonenumber);
		student.setInquiery(theinquiery);
		student.setDateofinquiry(thedateofinquiry);
		
		for(StudentDatabase email : serv.getAllEmails()) {
			send.sendEmail(email.getStudentemail() , thesubject, thebody);
			break;
		}
		
		return serv.savedata(student);
	}
	
	
	//below method is to update particular field without changing the old data..
	@PatchMapping("/updatedata/{studentid}/{dateofadmission}/{studentbalancefees}/{studentpaidfees}/{studentfees}")
	public ResponseEntity<StudentDatabase> updatedata(@PathVariable("studentid") int id,@PathVariable("dateofadmission") @DateTimeFormat(pattern = "dd-MM-yyyy") Date thedate1,
		@PathVariable("studentbalancefees") double thestudentbalancefees,
		@PathVariable("studentpaidfees") double thestudentpaidfees,
		@PathVariable("studentfees") double thestudentfees){
				
		try {
			StudentDatabase updatestudent = serv.findbyid(id);
			updatestudent.setStudentfees(thestudentfees);
			updatestudent.setDateofadmission(thedate1);
			updatestudent.setStudentbalancefees(thestudentbalancefees);
			updatestudent.setStudentpaidfees(thestudentpaidfees);
				
			return new ResponseEntity<StudentDatabase>(serv.savedata(updatestudent), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	//below method is use for sending mails to students who came for inquiry in last couple of months.
	@PostMapping("sendemailtoinquiry") 
	public void sendemailtoinquiry(@RequestParam("inquiery") String theinquiery,
			@RequestParam("date1") @DateTimeFormat(pattern = "dd-MM-yyyy") Date thedate1,
			@RequestParam("date2") @DateTimeFormat(pattern = "dd-MM-yyyy") Date thedate2) {

		List<StudentDatabase> inqdatebetween = serv.findbyinquieryanddates(theinquiery, thedate1, thedate2);
		
		for(StudentDatabase date : inqdatebetween) {
			
			String thesubject = "New batch for fullstack Starting soon.";
			
			String thebody = "Hello "+ date.getStudentname() +", our new Batch is Starting soon, If you are intrested kindly contact us.";
			send.sendEmail(date.getStudentemail() , thesubject, thebody);
		}

	}
	
	
	//below method is to save multiple data in the form of json.
	@PostMapping("/savemultipledata")
	public List<StudentDatabase> savemultipledata(@RequestBody List<StudentDatabase> thestudentdatabase) {
		
		return serv.savemultipledata(thestudentdatabase);
	}
	
	
	//below method is to find data by only studentname in the form of key and value.
	@GetMapping("/find")
	public List<StudentDatabase> findstudentbyname(@RequestParam("studentname") String thestudentname) {
		
		return serv.findByStudentname(thestudentname);
	}
	
	
	//below method is to find data from date1 to date2 in the form of pathvariable.
	@GetMapping("/findbydatebetween/{date1}/{date2}")
	public List<StudentDatabase> findbydatebetween(@PathVariable("date1") @DateTimeFormat(pattern = "dd-MM-yyyy") Date thedate1,
			@PathVariable("date2") @DateTimeFormat(pattern = "dd-MM-yyyy") Date thedate2){
		
		return serv.findByDateofinquiryBetween(thedate1, thedate2);
	}
	
	
	//below method is to find data by only studentname.
	@GetMapping("findbynamecontain")
	public List<StudentDatabase> findbynamecontain(@RequestParam("studentname") String thestudentname) {
			
		return serv.findbystudentnamecontain(thestudentname);
	}
	
	//below code is to find all the data from database
	@GetMapping("/findalldata")
	public List<StudentDatabase> findall(){
		
		return serv.findmultipledata();
	}
	
	
	//below method is use to get all emails of the students.
	@GetMapping("/emails")
	public void getallemails() {
		
		List<StudentDatabase> emails = serv.getAllEmails();
		
		for(StudentDatabase email : emails) {
			System.out.println(email.getStudentemail());
		}
	}
	
	
	//below method is use to send mail to all the people from the database.
	@PostMapping("/sendemailtoall")
	public void Mail(@RequestParam("subject") String thesubject,
			@RequestParam("body") String thebody) {
		
		for(StudentDatabase email : serv.getAllEmails()) {
			
			send.sendEmail(email.getStudentemail() , thesubject, thebody);
		}
	}
	
	//below method is use to get all inquirys from database
	@GetMapping("/inquiery")
	public List<StudentDatabase> findbyinquiery(@RequestParam("inquiery") String theinquiery){
		
		return serv.findbyinquiery(theinquiery);
	}
	
	@DeleteMapping("/deletestudentdata/{studentid}")
	public void deletestudentdata(@PathVariable("studentid") int thestudentid) {
		
		serv.deletestudentdata(thestudentid);
	}
	
	//below code is to find the student, paying his fees and updating in database.
	@PutMapping("/payfees")
	public void payfees(@RequestParam("studentid") int thestudentid,HttpServletResponse response,
			@RequestParam("studentpaidfees") double thestudentpaidfees) throws DocumentException, IOException {
		
			 serv.payingfees(thestudentid, thestudentpaidfees, response);;
	}
	
	//below code is to find all the student's name, phonenumber, email and fees and convert it into pdf.
	@GetMapping("/pdfofallstudents")
	public void generatePdf(HttpServletResponse response) throws DocumentException, IOException {
			
		serv.Pdfofalldata(response);
	}
	
	//This method is use to send remainders to students who's fees is greater than 0.
	@PostMapping("/remainder")
	public void remainder() {
		
		serv.feesremainder();
	}
	
}
