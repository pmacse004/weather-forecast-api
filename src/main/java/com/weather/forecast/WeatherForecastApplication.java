package com.weather.forecast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application class for Weather Forecast API.
 * This is the entry point of the Spring Boot application.
 * 
 * @SpringBootApplication - Combines three annotations:
 *   1. @Configuration - Marks this class as a source of bean definitions
 *   2. @EnableAutoConfiguration - Enables Spring Boot's auto-configuration
 *   3. @ComponentScan - Enables component scanning in this package and sub-packages
 * 
 * @author Weather Forecast Team
 */
@SpringBootApplication
public class WeatherForecastApplication {
    
    /**
     * Main method - Entry point of the application
     * 
     * @param args - Command line arguments
     */
    public static void main(String[] args) {
        // Start the Spring Boot application
        SpringApplication.run(WeatherForecastApplication.class, args);
        
        // Print startup message
        System.out.println("\n" +
                "=======================================================\n" +
                "   Weather Forecast API Started Successfully!         \n" +
                "=======================================================\n" +
                "   Server running on: http://localhost:8080           \n" +
                "   API Endpoint: GET /weather/{city}                  \n" +
                "   Health Check: GET /weather/health                  \n" +
                "   API Info: GET /weather/info                        \n" +
                "=======================================================\n");
    }
}

// Made with Bob
