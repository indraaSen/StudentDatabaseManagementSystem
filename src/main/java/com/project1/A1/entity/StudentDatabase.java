package com.project1.A1.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
public class StudentDatabase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int studentid;
	
	private String studentname;
	
	private String studentemail;
	
	private String studentphonenumber;
	
	private String inquiery;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date studentdob;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date dateofinquiry;
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
	private Date dateofadmission;
	
	private double studentfees;
	
	private double studentpaidfees;
	
	private double studentbalancefees;
	
}
