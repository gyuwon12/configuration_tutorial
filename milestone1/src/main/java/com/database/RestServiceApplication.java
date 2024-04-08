package com.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.database.mongodb.DataLoaderService;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.database.jpa")
@EnableMongoRepositories(basePackages = "com.database.mongodb")
public class RestServiceApplication {

    private final DataLoaderService dataLoaderService;

    @Autowired
    public RestServiceApplication(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    public static void main(String[] args) {
        SpringApplication.run(RestServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner loadCSVData() {
        return args -> {
            try {
                dataLoaderService.loadMovies();
                dataLoaderService.loadRatings();
                dataLoaderService.loadUsers();
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace(); 
            }
        };
    }
}
