package com.project1.A1.creatingpdf;

import java.io.FileOutputStream;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.project1.A1.entity.StudentDatabase;

import lombok.Data;

@Data
public class PDFGenerating {

	// List to hold all Students
	private List<StudentDatabase> studentList;

	public void generate(HttpServletResponse response) throws DocumentException, IOException {

		Random r = new Random();
		int a = 1 + r.nextInt(99999);
				
		String path = "D:\\NewJava\\" +a+".pdf";
				
		// Creating the Object of Document
		Document document = new Document(PageSize.A4);

		// Getting instance of PdfWriter
		PdfWriter.getInstance(document, new FileOutputStream(path));

		// Opening the created document to modify it
		document.open();

		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);

		// Creating paragraph
		Paragraph paragraph = new Paragraph("List Of Students", fontTiltle);
//		Paragraph paragraph1 = new Paragraph("Contact us: 00120012, 8765421359", fontTiltle);
//		Paragraph paragraph2 = new Paragraph("Add: Arc Tachnologies, near KDK college, Nandanvan, Nagpur", fontTiltle);
//		
		// Aligning the paragraph in document
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
//		paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
//		paragraph2.setAlignment(Paragraph.ALIGN_CENTER);
		
		
		// Adding the created paragraph in document
		document.add(paragraph);
//		document.add(paragraph1);
//		document.add(paragraph2);

		// Creating a table of 3 columns
		PdfPTable table = new PdfPTable(7);

		// Setting width of table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 1, 4, 7, 3 ,2 , 2, 2});
		table.setSpacingBefore(15);

		// Create Table Cells for table header
		PdfPCell cell = new PdfPCell();

		// Setting the background color and padding
		cell.setBackgroundColor(CMYKColor.MAGENTA);
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.WHITE);

		// Adding headings in the created table cell/ header
		// Adding Cell to table
		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Name", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Email", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Phone", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Total  Fees", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Paid  Fees", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("BalanceFees", font));
		table.addCell(cell);

		// Iterating over the list of students
		for (StudentDatabase student : studentList) {
			// Adding student id
			table.addCell(String.valueOf(student.getStudentid()));
			// Adding student name
			table.addCell(student.getStudentname());
			// Adding student section
			table.addCell(student.getStudentemail());
					
			table.addCell(student.getStudentphonenumber());
					
			table.addCell(String.valueOf(student.getStudentfees()));
					
			table.addCell(String.valueOf(student.getStudentpaidfees()));
					
			table.addCell(String.valueOf(student.getStudentbalancefees()));
					
		}
		// Adding the created table to document
		document.add(table);

		// Closing the document
		document.close();

	}
	
	String name;
	
	String inquiery;
	
	double totalfees;
	
	double balancefees;
	
	double paying;
	
	String Receiptid;
	
	public void Receipt(HttpServletResponse response) throws DocumentException, IOException {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");  
		 
        LocalDate date = LocalDate.now();
         
        Random rnd = new Random();
        this.Receiptid = "ARC"+rnd.nextInt(999999);
				
		String path = "D:\\NewJava\\" +Receiptid+".pdf";
		
		// Creating the Object of Document
		Document document = new Document(PageSize.A4);

		// Getting instance of PdfWriter
		PdfWriter.getInstance(document, new FileOutputStream(path));

		// Opening the created document to modify it
		document.open();

		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(14);

		// Creating paragraph
		Paragraph paragraph = new Paragraph("Nishu Technologies and Institute", fontTiltle);

		// Aligning the paragraph in document
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);

		// Adding the created paragraph in document
		document.add(paragraph);

		// Creating a table of 2 columns
		PdfPTable table = new PdfPTable(2);

		// Setting width of table, its columns and spacing
		table.setWidthPercentage(100f);
		table.setWidths(new int[] { 5, 5});
		table.setSpacingBefore(15);

		// Create Table Cells for table header
		PdfPCell cell = new PdfPCell();

		// Setting the background color and padding
		cell.setPadding(5);

		// Creating font
		// Setting font style and size
		Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		font.setColor(CMYKColor.BLACK);

		// Adding headings in the created table cell/ header
		// Adding Cell to table
		cell.setPhrase(new Phrase("Receipt No: " + Receiptid, font));
		cell.setBorderColor(CMYKColor.WHITE);
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Date: " + date, font));
		cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cell.setBorderColor(CMYKColor.WHITE);
		table.addCell(cell);
		cell.setExtraParagraphSpace(40);
		
		// Creating a table of 2 columns
		PdfPTable table1 = new PdfPTable(2);

		// Setting width of table, its columns and spacing
		table1.setWidthPercentage(100f);
		table1.setWidths(new int[] {2, 8});
		table1.setSpacingBefore(15);

		// Create Table Cells for table header
		PdfPCell cell1 = new PdfPCell();

		// Setting the background color and padding
		cell1.setPadding(5);

		cell1.setPhrase(new Phrase("Name: ", font));
		table1.addCell(cell1);
		
		cell1.setPhrase(new Phrase(name));
		table1.addCell(cell1);
		
		
		// Creating a table of 2 columns
		PdfPTable table2 = new PdfPTable(2);

		// Setting width of table, its columns and spacing
		table2.setWidthPercentage(100f);
		table2.setWidths(new int[] {2, 8});
		table2.setSpacingBefore(15);

		// Create Table Cells for table header
		PdfPCell cell2 = new PdfPCell();

		// Setting the background color and padding
		cell2.setPadding(5);

		cell2.setPhrase(new Phrase("Course: ", font));
		table2.addCell(cell2);
		
		
		cell2.setPhrase(new Phrase(inquiery));
		table2.addCell(cell2);
		
		
		// Creating a table of 2 columns
		PdfPTable table3 = new PdfPTable(2);

		// Setting width of table, its columns and spacing
		table3.setWidthPercentage(100f);
		table3.setWidths(new int[] {3, 8});
		table3.setSpacingBefore(15);

		// Create Table Cells for table header
		PdfPCell cell3 = new PdfPCell();

		// Setting the background color and padding
		cell3.setPadding(5);

		cell3.setPhrase(new Phrase("Method of payment: ", font));
		table3.addCell(cell3);
		
		
		cell3.setPhrase(new Phrase("cash/online", font));
		table3.addCell(cell3);
		
		
		// Creating a table of 2 columns
		PdfPTable table4 = new PdfPTable(4);

		// Setting width of table, its columns and spacing
		table4.setWidthPercentage(100f);
		table4.setWidths(new int[] {2, 4 , 2 , 4});
		table4.setSpacingBefore(15);

		// Create Table Cells for table header
		PdfPCell cell4 = new PdfPCell();

		// Setting the background color and padding
		cell4.setPadding(5);

		cell4.setPhrase(new Phrase("Total Amount: ", font));
		table4.addCell(cell4);
		
		cell4.setPhrase(new Phrase(String.valueOf(totalfees)));
		table4.addCell(cell4);
		
		cell4.setPhrase(new Phrase("Balance: ", font));
		table4.addCell(cell4);
		
		cell4.setPhrase(new Phrase(String.valueOf(balancefees)));
		table4.addCell(cell4);
		
		// Creating a table of 2 columns
		PdfPTable table5 = new PdfPTable(3);

		// Setting width of table, its columns and spacing
		table5.setWidthPercentage(100f);
		table5.setWidths(new int[] {5, 3, 3});
		table5.setSpacingBefore(15);		
		
		// Create Table Cells for table header
		PdfPCell cell5 = new PdfPCell();

		// Setting the background color and padding
		cell5.setPadding(5);

		cell5.setPhrase(new Phrase("Signature: ", font));
		cell5.setVerticalAlignment(Element.ALIGN_BOTTOM);
		cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell5.setBorderColor(CMYKColor.WHITE);
		cell5.setPaddingTop(150);
		table5.addCell(cell5);
		
		cell5.setPhrase(new Phrase("Fees: ", font));
		cell5.setPaddingTop(0);
		cell5.setVerticalAlignment(Element.ALIGN_TOP);
		cell5.setBorderColor(CMYKColor.WHITE);
		cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table5.addCell(cell5);
		
		cell5.setPhrase(new Phrase(String.valueOf(paying)));
		cell5.setPaddingTop(0);
		cell5.setVerticalAlignment(Element.ALIGN_TOP);
		cell5.setBorderColor(CMYKColor.WHITE);
		cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table5.addCell(cell5);
		
		// Adding the created table to document
		document.add(table);
		document.add(table1);
		document.add(table2);
		document.add(table3);
		document.add(table4);
		document.add(table5);
		
		// Closing the document
		document.close();

	}

}
