package io.github.hksm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan
public class MybooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MybooksApplication.class, args);
	}
}