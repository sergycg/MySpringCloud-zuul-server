package com.springboot.app.zuul;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
@EnableScheduling
public class SpringbootServicioZuulServerApplication {

	@Value("${herokuNotIdle.url}")
	private String herokuNotIdle_url;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootServicioZuulServerApplication.class, args);
	}

	
  @Scheduled(cron = "${herokuNotIdle.cronExpression}")
  public void herokuNotIdle() {
	System.out.println(" INIT - Heroku not idle execution ");
	RestTemplate restTemplate = new RestTemplate();
	try {
		ResponseEntity<String> response = restTemplate.exchange(herokuNotIdle_url, HttpMethod.GET, null, String.class);
		System.out.println(response);
	}catch (Exception e) {
		System.out.println(e.getMessage());
	}
	System.out.println(" FINISH - Heroku not idle execution");
  }
}
