package com.week11.zomato.entrypoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.week11.zomato.data.repository")
@EntityScan("com.week11.zomato.data.model")
@ComponentScan(basePackages = { "com.week11.zomato.controller", "com.week11.zomato.service",
		"com.week11.zomato.filter" })
@SpringBootApplication
public class ZomatoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZomatoApplication.class, args);
	}

}
//*******************************************************main server************************************** */