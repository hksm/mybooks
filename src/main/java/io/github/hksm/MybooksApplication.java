package io.github.hksm;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan
public class MybooksApplication {
	
	@Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        Set<String> patterns = new HashSet<>();
        patterns.add("/api/authors/*");
        patterns.add("/api/books/*");
        patterns.add("/api/countries/*");
        patterns.add("/api/genres/*");
        patterns.add("/api/languages/*");
        patterns.add("/api/auth/role/*");
        registrationBean.setUrlPatterns(patterns);

        return registrationBean;
    }

	public static void main(String[] args) {
		SpringApplication.run(MybooksApplication.class, args);
	}
}