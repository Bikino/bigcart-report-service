package com.bigcart.bigcartreportservice.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigcart.bigcartreportservice.service.ProductReportService;

import net.sf.jasperreports.engine.JRException;



@RestController
@RequestMapping("/product")
public class ProdReportController {

	@Autowired
	private ProductReportService productReportService;

	@GetMapping("/report")
	public void prodReport(HttpServletResponse response) {

		try {
			 productReportService.generateReport(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
