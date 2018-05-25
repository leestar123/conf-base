package com.conf.template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.conf.template.db.mapper")
public class BootstrapAppliaction {

	public static void main(String[] args) {
		SpringApplication.run(BootstrapAppliaction.class, args);
	}
}
