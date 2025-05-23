package com.trade.repo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.trade.repo")
public class TradeRepositoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradeRepositoryApplication.class, args);
	}

}
