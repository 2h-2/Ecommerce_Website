package com.ecomerce.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.EntityManager;

@EnableJpaRepositories(basePackages = "com.ecomerce.backend.repository")
@EntityScan(basePackages = "com.ecomerce.backend.model.entity")
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);

	}

    @Bean
public CommandLineRunner checkEntities(EntityManager entityManager) {
    return args -> {
        Metamodel metamodel = entityManager.getMetamodel();
        metamodel.getEntities().forEach(entityType ->
            System.out.println("Detected Entity: " + entityType.getName())
        );
    };
}

	@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:4200")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true);
            }
        };
    }
}
