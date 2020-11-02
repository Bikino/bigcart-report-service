package com.bigcart.bigcartreportservice;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;



@EnableEurekaClient
@SpringBootApplication

public class BigcartReportServiceApplication {

    public static void main(String[] args) {
    	SpringApplicationBuilder builder = new SpringApplicationBuilder(BigcartReportServiceApplication.class);

    	builder.headless(false);

    	ConfigurableApplicationContext context = builder.run(args);
    }
 
}
