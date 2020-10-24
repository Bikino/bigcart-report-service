package com.bigcart.bigcartreportservice.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.bigcart.bigcartreportservice.domain.Employee;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class EmployeeReportService  {

	private List<Employee> empList = Arrays.asList(
			new Employee(1, "Youssoupha", "Mar", "Front-end Developer", 20000),
			new Employee(2, "Omar", "Genpact", "Lead Developer", 40000),
			new Employee(3, "Matip", "Touch ", "Sr. Java Engineer", 47000),
			new Employee(4, "Mouhamad", "Ahmad", "Backend Developer", 700000));

	public HttpServletResponse generateReport(HttpServletResponse response)throws IOException, JRException {
		

			String reportPath = "src/main/resources/";

			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/employee-rpt.jrxml");

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(empList);

			// Add parameters
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("createdBy", "team4.org");
			

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);

			// Export the report to a PDF file
			//JasperExportManager.exportReportToPdfFile(jasperPrint,reportPath+"/Emp-Rpt.pdf");
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");

			System.out.println("Done");

			//return "Report successfully generated @path= " + reportPath;
            return response;
		
		
	}
}