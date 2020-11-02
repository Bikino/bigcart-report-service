package com.bigcart.bigcartreportservice.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bigcart.bigcartreportservice.domain.BuyerDTO;
import com.bigcart.bigcartreportservice.domain.Order;
import com.bigcart.bigcartreportservice.domain.OrderDetail;
import com.bigcart.bigcartreportservice.domain.OrderDetails;
import com.bigcart.bigcartreportservice.domain.Orders;
import com.bigcart.bigcartreportservice.domain.Product;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

@Service
public class OrderReportService {
	private RestTemplate restTemplate;
	
	 public static JasperPrint jasperPrint;
	    public static JasperReport mainReport;

	    final static String reportsDirName = "src/main/resources/";
	  
	    final static String [] jrxmlFiles = {"order_report",
	                                         "orderDetail_subreport"};

 public static boolean compileJRXML()throws IOException, JRException{
	
     /**
      *  Compile all jrxml from reports to jasper and pur it to getJasperDir result.
      */
     boolean result = false;
     String jdn = "src/main/resources/";
     String s,o;
     File outf;
     
         for (String rep: jrxmlFiles){
        	 System.out.println("MMMMX");
             s = reportsDirName+"/"+rep+".jrxml";
             o = jdn+"/"+rep+".jasper";
             System.out.println("XXXXXX");
             JasperCompileManager.compileReportToFile(s, o);
             System.out.println("TTTTTT");
             outf = new File(o);
             System.out.println("GGGG");
             if (!outf.exists()) return false;
         }
     
     result = true;
    
     return result;
 }
 
	public HttpServletResponse generateReport(HttpServletResponse response)throws IOException, JRException {
		restTemplate = new RestTemplate();
		
		
		
		
		System.out.println("Begining");
		ResponseEntity<List<Orders>>  resp = restTemplate.exchange("http://localhost:8000/order/allOrders/",
	                             HttpMethod.GET, null, new ParameterizedTypeReference<List<Orders>>() {});
			List<Orders> dataSource ;
		
			
					//  prodList = Arrays.asList(
							//new Product(1, "Youssoupha",1, "Mar", "Front-end Developer", true));
			dataSource  = resp.getBody();
			List<Order> datadest = new ArrayList<Order>();
	
			for(int i= 0;i< dataSource.size();i++) {
				Orders o = dataSource.get(i);
				Set<OrderDetail> setdest = new HashSet<OrderDetail>();
				Set<OrderDetails> setofod = o.getOrderDetails();
				Iterator<OrderDetails> it = setofod.iterator();
				while(it.hasNext()) {
					OrderDetails od = it.next();
					long id = od.getProductId();
					ResponseEntity<Product>  res = restTemplate.exchange("http://localhost:8001/product/"+id,
                            HttpMethod.GET, null, new ParameterizedTypeReference<Product>() {});
		            Product prod=res.getBody();
	
		            String name = prod.getName();
		            System.out.println("ProductName "+name);
		            OrderDetail ord= new OrderDetail(od.getId(), name, od.getPrice(), od.getQuantity());
				setdest.add(ord);
		            //  prodList = Arrays.asList(
						//new Product(1, "Youssoupha",1, "Mar", "Front-end Developer", true));
		
				}
				System.out.println("Before");
				ResponseEntity<BuyerDTO>  residUser;
				try {
				residUser = restTemplate.exchange("http://localhost:9988/buyer/"+o.getUserId(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<BuyerDTO>() {});
				}
				catch(HttpClientErrorException.NotFound ex){
					residUser = null;
				}
				System.out.println("After");
	            Order orderdest;
				if(residUser!=null) {
					System.out.println("No error");
					BuyerDTO buyer=residUser.getBody();
				orderdest = new Order(o.getId(), buyer.getFirstName()+" "+buyer.getLastName(),o.getTotalAmount(), o.getAddressId(),o.getPaymentId(),o.getCreationDate(), setdest);
				}
				else {
					 orderdest = new Order(o.getId(),"",o.getTotalAmount(), o.getAddressId(),o.getPaymentId(),o.getCreationDate(), setdest);
					 System.out.println("Error");
				}
				datadest.add(orderdest);
			}
			
         //Parameters for pass to report
         HashMap<String,Object> parameters = new HashMap<String,Object>();
         //Organize datasource
     	
 
         JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(datadest);
         //Compile jrxml to jasper-files.
         FileInputStream mainReportFile = null;
      
         boolean result = false;
         String jdn = "src/main/resources/";
         String s,o;
         File outf;
         
             for (String rep: jrxmlFiles){
            	 System.out.println("MMMMX");
                 s = reportsDirName+"/"+rep+".jrxml";
                 o = jdn+"/"+rep+".jasper";
                 System.out.println("XXXXXX");
                 JasperCompileManager.compileReportToFile(s, o);
                 System.out.println("TTTTTT");
                 outf = new File(o);
                 System.out.println("GGGG");
                 
             }
         
        	
         //If you haven't plans to compile jrxml scip this call. Alternate
         //way to create a mainReportFile
         //import net.sf.jasperreports.engine.design.JasperDesign mainDesign = JRXmlLoader.load("/path/to/jrxml");
         //JasperReport mainReportFile = JasperCompileManager.compileReport(mainDesign);
             System.out.println("MAAAAAAAAAR");
                 String ss = "src/main/resources/"+"/"+jrxmlFiles[0]+".jasper";
                 mainReportFile = new FileInputStream(ss);
                 //pass directory with jasper-files as parameters
                 parameters.put("SUBREPORT_DIR", "src/main/resources/"+"/");
                 //Fill report and view report.
                 jasperPrint = JasperFillManager.fillReport(mainReportFile, parameters, beanDataSource);
                 JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
                 JasperViewer.viewReport(jasperPrint);
                 response.setContentType("application/pdf");
     			response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
     			 
             
         System.out.println("End");
         return response;
         }
	public HttpServletResponse generateReportByVendor(HttpServletResponse response,long vendorId)throws IOException, JRException {
		restTemplate = new RestTemplate();
		
		
		
		
		System.out.println("Begining");
		ResponseEntity<List<Orders>>  resp = restTemplate.exchange("http://localhost:8000/order/vendor/"+vendorId,
	                             HttpMethod.GET, null, new ParameterizedTypeReference<List<Orders>>() {});
			List<Orders> dataSource ;
		
			
					//  prodList = Arrays.asList(
							//new Product(1, "Youssoupha",1, "Mar", "Front-end Developer", true));
			dataSource  = resp.getBody();
			List<Order> datadest = new ArrayList<Order>();
	
			for(int i= 0;i< dataSource.size();i++) {
				Orders o = dataSource.get(i);
				Set<OrderDetail> setdest = new HashSet<OrderDetail>();
				Set<OrderDetails> setofod = o.getOrderDetails();
				Iterator<OrderDetails> it = setofod.iterator();
				while(it.hasNext()) {
					OrderDetails od = it.next();
					long id = od.getProductId();
					ResponseEntity<Product>  res = restTemplate.exchange("http://localhost:8001/product/"+id,
                            HttpMethod.GET, null, new ParameterizedTypeReference<Product>() {});
		            Product prod=res.getBody();
	
		            String name = prod.getName();
		            System.out.println("ProductName "+name);
		            OrderDetail ord= new OrderDetail(od.getId(), name, od.getPrice(), od.getQuantity());
				setdest.add(ord);
		            //  prodList = Arrays.asList(
						//new Product(1, "Youssoupha",1, "Mar", "Front-end Developer", true));
		
				}
				ResponseEntity<BuyerDTO>  residUser;
				try {
				residUser = restTemplate.exchange("http://localhost:9988/buyer/"+o.getUserId(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<BuyerDTO>() {});
				}
				catch(HttpClientErrorException.NotFound ex){
					residUser = null;
				}
				System.out.println("After");
	            Order orderdest;
				if(residUser!=null) {
					System.out.println("No error");
					BuyerDTO buyer=residUser.getBody();
				orderdest = new Order(o.getId(), buyer.getFirstName()+" "+buyer.getLastName(),o.getTotalAmount(), o.getAddressId(),o.getPaymentId(),o.getCreationDate(), setdest);
				}
				else {
					 orderdest = new Order(o.getId(),"",o.getTotalAmount(), o.getAddressId(),o.getPaymentId(),o.getCreationDate(), setdest);
					 System.out.println("Error");
				}
				datadest.add(orderdest);
			}
			
         //Parameters for pass to report
         HashMap<String,Object> parameters = new HashMap<String,Object>();
         //Organize datasource
     	
 
         JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(datadest);
         //Compile jrxml to jasper-files.
         FileInputStream mainReportFile = null;
      
         boolean result = false;
         String jdn = "src/main/resources/";
         String s,o;
         File outf;
         
             for (String rep: jrxmlFiles){
            	 System.out.println("MMMMX");
                 s = reportsDirName+"/"+rep+".jrxml";
                 o = jdn+"/"+rep+".jasper";
                 System.out.println("XXXXXX");
                 JasperCompileManager.compileReportToFile(s, o);
                 System.out.println("TTTTTT");
                 outf = new File(o);
                 System.out.println("GGGG");
                 
             }
         
        	
         //If you haven't plans to compile jrxml scip this call. Alternate
         //way to create a mainReportFile
         //import net.sf.jasperreports.engine.design.JasperDesign mainDesign = JRXmlLoader.load("/path/to/jrxml");
         //JasperReport mainReportFile = JasperCompileManager.compileReport(mainDesign);
             System.out.println("MAAAAAAAAAR");
                 String ss = "src/main/resources/"+"/"+jrxmlFiles[0]+".jasper";
                 mainReportFile = new FileInputStream(ss);
                 //pass directory with jasper-files as parameters
                 parameters.put("SUBREPORT_DIR", "src/main/resources/"+"/");
                 //Fill report and view report.
                 jasperPrint = JasperFillManager.fillReport(mainReportFile, parameters, beanDataSource);
                 JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
                 JasperViewer.viewReport(jasperPrint);
                 response.setContentType("application/pdf");
     			response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
     			 
             
         System.out.println("End");
         return response;
         }
	
	
	public HttpServletResponse generateReportByCategory(HttpServletResponse response,int categoryId)throws IOException, JRException {
		restTemplate = new RestTemplate();
		
		
		
		
		System.out.println("Begining");
		ResponseEntity<List<Orders>>  resp = restTemplate.exchange("http://localhost:8000/order/allOrders/",
	                             HttpMethod.GET, null, new ParameterizedTypeReference<List<Orders>>() {});
			List<Orders> dataSource ;
			dataSource  = resp.getBody();
			List<Orders> data = new ArrayList<Orders>();
			//////Filtering
			
			for(int i= 0;i< dataSource.size();i++) {
				Orders o = dataSource.get(i);
				Set<OrderDetail> setdest = new HashSet<OrderDetail>();
				Set<OrderDetails> setofod = o.getOrderDetails();
				Iterator<OrderDetails> it = setofod.iterator();
				while(it.hasNext()) {
					OrderDetails od = it.next();
					long id = od.getProductId();
					ResponseEntity<Product>  res = restTemplate.exchange("http://localhost:8001/product/"+id,
                            HttpMethod.GET, null, new ParameterizedTypeReference<Product>() {});
		            Product prod=res.getBody();
	
		            int idcat = prod.getCategoryId();
		            System.out.println(idcat);
		            System.out.print(prod.getName());
		           
		            if(idcat==categoryId) {
		            	if(!data.contains(o)) {
		            		
		            	data.add(o);
		            	}
		            }
		
				}
				
			}
			dataSource = data;
			
			
			
			/////Filtering
			
			
			
			
			
			
			
			
			
			List<Order> datadest = new ArrayList<Order>();
	
			for(int i= 0;i< dataSource.size();i++) {
				Orders o = dataSource.get(i);
				Set<OrderDetail> setdest = new HashSet<OrderDetail>();
				Set<OrderDetails> setofod = o.getOrderDetails();
				Iterator<OrderDetails> it = setofod.iterator();
				while(it.hasNext()) {
					OrderDetails od = it.next();
					long id = od.getProductId();
					ResponseEntity<Product>  res = restTemplate.exchange("http://localhost:8001/product/"+id,
                            HttpMethod.GET, null, new ParameterizedTypeReference<Product>() {});
		            Product prod=res.getBody();
	
		            String name = prod.getName();
		            System.out.println("ProductName "+name);
		            OrderDetail ord= new OrderDetail(od.getId(), name, od.getPrice(), od.getQuantity());
				setdest.add(ord);
		            //  prodList = Arrays.asList(
						//new Product(1, "Youssoupha",1, "Mar", "Front-end Developer", true));
		
				}
				ResponseEntity<BuyerDTO>  residUser;
				try {
				residUser = restTemplate.exchange("http://localhost:9988/buyer/"+o.getUserId(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<BuyerDTO>() {});
				}
				catch(HttpClientErrorException.NotFound ex){
					residUser = null;
				}
				System.out.println("After");
	            Order orderdest;
				if(residUser!=null) {
					System.out.println("No error");
					BuyerDTO buyer=residUser.getBody();
				orderdest = new Order(o.getId(), buyer.getFirstName()+" "+buyer.getLastName(),o.getTotalAmount(), o.getAddressId(),o.getPaymentId(),o.getCreationDate(), setdest);
				}
				else {
					 orderdest = new Order(o.getId(),"",o.getTotalAmount(), o.getAddressId(),o.getPaymentId(),o.getCreationDate(), setdest);
					 System.out.println("Error");
				}
				datadest.add(orderdest);
			}
			
         //Parameters for pass to report
         HashMap<String,Object> parameters = new HashMap<String,Object>();
         //Organize datasource
     	
 
         JRBeanCollectionDataSource beanDataSource = new JRBeanCollectionDataSource(datadest);
         //Compile jrxml to jasper-files.
         FileInputStream mainReportFile = null;
      
         boolean result = false;
         String jdn = "src/main/resources/";
         String s,o;
         File outf;
         
             for (String rep: jrxmlFiles){
            	 System.out.println("MMMMX");
                 s = reportsDirName+"/"+rep+".jrxml";
                 o = jdn+"/"+rep+".jasper";
                 System.out.println("XXXXXX");
                 JasperCompileManager.compileReportToFile(s, o);
                 System.out.println("TTTTTT");
                 outf = new File(o);
                 System.out.println("GGGG");
                 
             }
         
        	
         //If you haven't plans to compile jrxml scip this call. Alternate
         //way to create a mainReportFile
         //import net.sf.jasperreports.engine.design.JasperDesign mainDesign = JRXmlLoader.load("/path/to/jrxml");
         //JasperReport mainReportFile = JasperCompileManager.compileReport(mainDesign);
             System.out.println("MAAAAAAAAAR");
                 String ss = "src/main/resources/"+"/"+jrxmlFiles[0]+".jasper";
                 mainReportFile = new FileInputStream(ss);
                 //pass directory with jasper-files as parameters
                 parameters.put("SUBREPORT_DIR", "src/main/resources/"+"/");
                 //Fill report and view report.
                 jasperPrint = JasperFillManager.fillReport(mainReportFile, parameters, beanDataSource);
                 JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
                 JasperViewer.viewReport(jasperPrint);
                 response.setContentType("application/pdf");
     			response.addHeader("Content-Disposition", "inline; filename=jasper.pdf;");
     			 
             
         System.out.println("End");
         return response;
         }
     

}
