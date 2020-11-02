package com.bigcart.bigcartreportservice.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bigcart.bigcartreportservice.domain.Employee;
import com.bigcart.bigcartreportservice.domain.EmployeeDTO;
import com.bigcart.bigcartreportservice.domain.Orders;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class EmployeeReportService  {
	private RestTemplate restTemplate;
	//@Autowired
   // RestTemplate restTemplate;
	 

	public HttpServletResponse generateReport(HttpServletResponse response)throws IOException, JRException {
		restTemplate = new RestTemplate();
		ResponseEntity<List<EmployeeDTO>>  resp = restTemplate.exchange("http://localhost:9988/employee/",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeDTO>>() {});
List<EmployeeDTO> data = resp.getBody();
List<Employee> empList  = new ArrayList<Employee>();

for(int i = 0;i<data.size();i++) {
	EmployeeDTO d = data.get(i);
	Employee n = new Employee(d.getId(),d.getFirstName(),d.getLastName(), d.getEmail(), d.getSalary());
	empList .add(n);
}

	//  prodList = Arrays.asList(
			//new Product(1, "Youssoupha",1, "Mar", "Front-end Developer", true));

       // String resp = restTemplate.exchange("http://student-service/getStudentDetailsForSchool/{schoolname}",
                              //  HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}, schoolname).getBody();

			String reportPath = System.getProperty("user.dir")+"/src/main/resources/";

			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(reportPath+"employee-rpt.jrxml");

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


	
	public HttpServletResponse generateReportByVendor(HttpServletResponse response)throws IOException, JRException {
		restTemplate = new RestTemplate();
		ResponseEntity<List<EmployeeDTO>>  resp = restTemplate.exchange("http://localhost:9988/employee/",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<EmployeeDTO>>() {});
List<EmployeeDTO> data = resp.getBody();
List<Employee> empList  = new ArrayList<Employee>();

for(int i = 0;i<data.size();i++) {
	EmployeeDTO d = data.get(i);
	Employee n = new Employee(d.getId(),d.getFirstName(),d.getLastName(), d.getEmail(), d.getSalary());
	empList .add(n);
}
			String reportPath = System.getProperty("user.dir")+"/src/main/resources/";

			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport(reportPath+"employee-rpt.jrxml");

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(empList);

			// Add parameters
			Map<String, Object> parameters = new HashMap<>();

			parameters.put("createdBy", "team4.org");
			

			// Fill the report
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters,
					jrBeanCollectionDataSource);
			JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
			
			
			response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");

			System.out.println("Done");

			//return "Report successfully generated @path= " + reportPath;
            return response;
		
		
	}
	

}