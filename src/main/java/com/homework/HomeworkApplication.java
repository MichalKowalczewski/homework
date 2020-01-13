package com.homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;

@SpringBootApplication
public class HomeworkApplication extends SpringBootServletInitializer {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(HomeworkApplication.class, args);
	}
}
