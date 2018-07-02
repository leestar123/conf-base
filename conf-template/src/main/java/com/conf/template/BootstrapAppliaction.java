package com.conf.template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:urule-console-context.xml","classpath:conf-base-context.xml"})
@ComponentScan(basePackages="com.conf.client")
@MapperScan("com.conf.template.db.mapper")
public class BootstrapAppliaction {

	public static void main(String[] args) {
		SpringApplication.run(BootstrapAppliaction.class, args);
	}
}
