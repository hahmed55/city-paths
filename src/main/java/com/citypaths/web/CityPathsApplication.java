package com.citypaths.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.citypaths.web","com.citypaths.service","com.citypaths.util"})
public class CityPathsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CityPathsApplication.class , args);
    }
}
