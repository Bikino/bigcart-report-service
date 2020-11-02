package com.bigcart.bigcartreportservice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigcart.bigcartreportservice.service.EmployeeReportService;
import com.bigcart.bigcartreportservice.service.OrderReportService;

import net.sf.jasperreports.engine.JRException;



@RestController
@RequestMapping("/order")
public class OrderReportController {

	@Autowired
	private OrderReportService OrderReportService;

	@GetMapping("/report")
	public void OrderReport(HttpServletResponse response) {

		try {
			 OrderReportService.generateReport(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@GetMapping("/receipt/{id}")
	public void OrderReceipt(HttpServletResponse response,@PathVariable Long id) {

		try {
			 OrderReportService.generateReceipt(response, id);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@GetMapping("/vendor/{vendorId}")
	public void OrderByVendorReport(HttpServletResponse response,@PathVariable Long vendorId) {

		try {
			 OrderReportService.generateReportByVendor(response,vendorId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GetMapping("/category/{categoryId}")
	public void OrderByCategoryReport(HttpServletResponse response,@PathVariable int categoryId) {

		try {
			 OrderReportService.generateReportByCategory(response,categoryId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
