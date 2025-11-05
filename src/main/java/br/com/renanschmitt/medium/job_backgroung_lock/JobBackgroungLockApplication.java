package br.com.renanschmitt.medium.job_backgroung_lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JobBackgroungLockApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobBackgroungLockApplication.class, args);
	}

}
