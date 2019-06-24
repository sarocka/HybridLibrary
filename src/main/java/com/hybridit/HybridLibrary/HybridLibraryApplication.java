package com.hybridit.HybridLibrary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class HybridLibraryApplication {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(HybridLibraryApplication.class);
		logger.info("test");
		SpringApplication.run(HybridLibraryApplication.class, args);

	}

}
