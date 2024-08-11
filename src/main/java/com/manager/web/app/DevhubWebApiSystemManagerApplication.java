package com.manager.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.manager.web.app")
public class DevhubWebApiSystemManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevhubWebApiSystemManagerApplication.class, args);
	}
}
