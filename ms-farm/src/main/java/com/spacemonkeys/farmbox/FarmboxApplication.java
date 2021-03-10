package com.spacemonkeys.farmbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class FarmboxApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmboxApplication.class, args);
	}

}
