package com.project1.A1.StudentDatabaseService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;
import com.project1.A1.StudentDatabaseRepository.StudentRepository;
import com.project1.A1.creatingpdf.PDFGenerating;
import com.project1.A1.entity.StudentDatabase;
import com.project1.A1.utility.StudentDatabaseUtility;


@EnableScheduling
@Service
public class studentService {

	@Autowired
	private StudentRepository repo;
	
	@Autowired
	private StudentDatabaseUtility send;
	
	
	
	public StudentDatabase savedata(StudentDatabase thestudentdatabase) {
		
		return repo.save(thestudentdatabase);
	}
	
	public List<StudentDatabase> savemultipledata(List<StudentDatabase> thestudentdatabase) {
		
		return repo.saveAll(thestudentdatabase);
	}
	
	public List<StudentDatabase> findmultipledata(){
				
		return repo.findAll();
	}

	public List<StudentDatabase> findByStudentname(String thestudentname) {
		
		return repo.findByStudentname(thestudentname);
	}
	
	public List<StudentDatabase> findByDateofinquiryBetween(Date date1 , Date date2){
		
		return repo.findByDateofinquiryBetween(date1, date2);
	}
	
	
	public List<StudentDatabase> findbystudentnamecontain(String thestudentname) {

		return repo.findByStudentnameContains(thestudentname);
	}

	public List<StudentDatabase> findbyinquiery(String theinquiery) {

		return repo.findByInquiery(theinquiery);
	}
	
	public List<StudentDatabase> getAllEmails(){
		
		return repo.findAll();
	}
	
	public StudentDatabase findbyid(int id) {
		
		return repo.findById(id).get();
	}
	
	public List<StudentDatabase> findbyinquieryanddates(String theinquiery,Date date1, Date date2){
		
		return repo.findByInquieryAndDateofinquiryBetween(theinquiery, date1, date2);
	}

	public void deletestudentdata(int thestudentid) {

		repo.deleteById(thestudentid);
	}

	
	public void payingfees(int thestudentid, double thestudentpaidfees,HttpServletResponse response) throws DocumentException, IOException {
		
		for(StudentDatabase studentdb : repo.findAll()) {
			
			if (studentdb.getStudentid() == thestudentid) {
				
				if(studentdb.getStudentbalancefees() == 0) {
					throw new NullPointerException("Your have no balance left. Your fees is paid.");
					
				}else {
					double feespaid = thestudentpaidfees + studentdb.getStudentpaidfees();
					
					double balance = studentdb.getStudentfees() - feespaid;
					
					studentdb.setStudentbalancefees(balance);
					
					studentdb.setStudentpaidfees(feespaid);
				}
			}
			
		 repo.save(studentdb);
				
				if(studentdb.getStudentid() == thestudentid) {
					response.setContentType("application/pdf");
					DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
					String currentDateTime = dateFormat.format(new Date());
					String headerkey = "Content-Disposition";
					String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
					response.setHeader(headerkey, headervalue);
				 
					PDFGenerating generator = new PDFGenerating();
					generator.setPaying(thestudentpaidfees);
					generator.setInquiery(studentdb.getInquiery());
					generator.setName(studentdb.getStudentname());
					generator.setTotalfees(studentdb.getStudentfees());
					generator.setBalancefees(studentdb.getStudentbalancefees());
					generator.Receipt(response);

					String attach = "D:\\NewJava\\" +generator.getReceiptid()+".pdf";
					
					String thesubject = "Your Payment Receipt.";
					String thebody = "Thank You!";
					send.sendEmailwithattach(studentdb.getStudentemail() , thesubject, thebody, attach);
					break;
					
				}
			}
	}
	
	
	//This method is used to schedule email on last day of every month
	//@Scheduled(initialDelay = 10000, fixedDelayString = "30*24*60*60*1000")
	@Scheduled(cron = "0 15 10 L * ?")
	public void feesremainder() {
		
		for(StudentDatabase studentdb : repo.findAll()) {
			
			if (studentdb.getStudentbalancefees() > 0 && studentdb.getStudentbalancefees() != 0) {
				String thesubject = "Dear " + studentdb.getStudentname() +" sir";
				
				String thebody = "You have balance of " + studentdb.getStudentbalancefees() + ". Kindly pay it patkan nahi to tera access jayega gachkan.";
				
				send.sendEmail(studentdb.getStudentemail() , thesubject, thebody);
				
			}
		}
	}
	
	
	public void Pdfofalldata(HttpServletResponse response) throws DocumentException, IOException {
		
		response.setContentType("application/pdf");
		DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
		String currentDateTime = dateFormat.format(new Date());
		String headerkey = "Content-Disposition";
		String headervalue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
		response.setHeader(headerkey, headervalue);
			
		List<StudentDatabase> studentList = repo.findAll();
			
		PDFGenerating generator = new PDFGenerating();
		generator.setStudentList(studentList);
		generator.generate(response);
		
	}
	
}
