package com.xlq.hospital;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xlq.hospital.dao")
public class QfHospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(QfHospitalApplication.class, args);
	}
}
