package com.bigcart.bigcartreportservice.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bigcart.bigcartreportservice.domain.Employee;
import com.bigcart.bigcartreportservice.domain.Product;
import com.google.inject.Inject;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Service
public class ProductReportService  {

	private RestTemplate restTemplate;
	 

	

	public HttpServletResponse generateReport(HttpServletResponse response)throws IOException, JRException {

	 restTemplate = new RestTemplate();
	ResponseEntity<List<Product>>  resp = restTemplate.exchange("http://localhost:8001/product/",
                             HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {});
		List<Product> prodList;
	
		
				//  prodList = Arrays.asList(
						//new Product(1, "Youssoupha",1, "Mar", "Front-end Developer", true));
		prodList =resp.getBody();
		String reportPath = "src/main/resources/";
			// Compile the Jasper report from .jrxml to .japser
			JasperReport jasperReport = JasperCompileManager.compileReport("src/main/resources/product-rpt.jrxml");

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(prodList);

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