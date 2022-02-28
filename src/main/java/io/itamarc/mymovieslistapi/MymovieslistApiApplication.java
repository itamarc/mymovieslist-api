package io.itamarc.mymovieslistapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import io.itamarc.mymovieslistapi.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class MymovieslistApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MymovieslistApiApplication.class, args);
	}
}
