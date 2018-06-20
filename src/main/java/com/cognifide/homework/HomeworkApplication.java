package com.cognifide.homework;

import com.cognifide.homework.util.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.io.IOException;
import java.text.ParseException;

@SpringBootApplication
public class HomeworkApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(HomeworkApplication.class, args);
	}
}
