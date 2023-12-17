package com.project1.A1.StudentDatabaseRepository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project1.A1.entity.StudentDatabase;

public interface StudentRepository extends JpaRepository<StudentDatabase, Integer> {

	List<StudentDatabase> findByStudentname(String thestudentname);
	
	List<StudentDatabase> findStudentnameOrderByStudentbalancefees(String thestudentname);
	
	List<StudentDatabase> findByStudentbalancefeesBetween(double balancefees1, double balancefees2);

	List<StudentDatabase> findByDateofinquiryBetween(Date date1, Date date2);
	
	List<StudentDatabase> findByStudentnameContains(String thestudentname);

	List<StudentDatabase> findByInquiery(String theinquiery);

	List<StudentDatabase> findByStudentemail(String thestudentemails);

	List<StudentDatabase> findByInquieryAndDateofinquiryBetween(String theinquiery,Date date1, Date date2);

}
