package com.bigcart.bigcartreportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@EnableEurekaClient
@SpringBootApplication
public class BigcartReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigcartReportServiceApplication.class, args);
    }

}
