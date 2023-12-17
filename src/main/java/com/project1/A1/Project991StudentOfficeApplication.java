package com.project1.A1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project1.A1.StudentDatabaseService.studentService;
import com.project1.A1.entity.StudentDatabase;

@SpringBootApplication
public class Project991StudentOfficeApplication {

	@Autowired
	private studentService service;
	
	public static void main(String[] args) {
		SpringApplication.run(Project991StudentOfficeApplication.class, args);
	}

	public void run(String... args) throws Exception {
		for (int i = 0; i < 11; i++) {
			StudentDatabase student = new StudentDatabase();
			student.setStudentname("student" + i);
			student.setStudentemail("student" + i);
			student.setStudentphonenumber("student" + i);
			student.setStudentfees(i);
			student.setStudentpaidfees(i);
			student.setStudentbalancefees(i);
			service.savedata(student);
		}
	}
	
}
