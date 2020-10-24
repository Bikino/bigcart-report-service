package com.bigcart.bigcartreportservice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bigcart.bigcartreportservice.service.EmployeeReportService;

import net.sf.jasperreports.engine.JRException;



@RestController
@RequestMapping("/employee")
public class EmpReportController {

	@Autowired
	private EmployeeReportService employeeReportService;

	@GetMapping("/report")
	public void empReport(HttpServletResponse response) {

		try {
			 employeeReportService.generateReport(response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
