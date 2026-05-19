package com.weather.forecast.controller;

import com.weather.forecast.model.ForecastResponse;
import com.weather.forecast.model.WeatherResponse;
import com.weather.forecast.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller that handles HTTP requests for weather data.
 * This controller exposes endpoints for clients to fetch weather information.
 * 
 * @RestController - Combines @Controller and @ResponseBody
 *                   Automatically converts return values to JSON
 * @RequestMapping - Base path for all endpoints in this controller
 * 
 * @author Weather Forecast Team
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {
    
    // Logger for logging information and errors
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    
    // Service layer dependency - handles business logic
    private final WeatherService weatherService;
    
    /**
     * Constructor-based dependency injection
     * Spring automatically injects WeatherService instance
     * 
     * @param weatherService - Service that handles weather operations
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    /**
     * GET endpoint to fetch weather data for a specific city
     * 
     * Endpoint: GET /weather/{city}
     * Example: GET http://localhost:8080/weather/London
     * 
     * @GetMapping - Maps HTTP GET requests to this method
     * @PathVariable - Extracts {city} from URL path
     * 
     * @param city - Name of the city (extracted from URL path)
     * @return ResponseEntity<WeatherResponse> - HTTP response with weather data
     */
    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeather(@PathVariable String city) {
        logger.info("Received request to fetch weather for city: {}", city);
        
        try {
            // Validate city parameter
            if (city == null || city.trim().isEmpty()) {
                logger.warn("Empty or null city name provided");
                return ResponseEntity
                        .badRequest()
                        .build();
            }
            
            // Call service layer to fetch weather data
            WeatherResponse weatherResponse = weatherService.getWeatherByCity(city.trim());
            
            logger.info("Successfully retrieved weather data for city: {}", city);
            
            // Return successful response with weather data
            return ResponseEntity
                    .ok()
                    .body(weatherResponse);
            
        } catch (RuntimeException e) {
            // Log the error
            logger.error("Error fetching weather for city: {}", city, e);
            
            // Return error response
            // In production, you might want to return a custom error response object
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
    
    /**
     * GET endpoint to fetch 7-day weather forecast for a specific city
     * 
     * Endpoint: GET /weather/forecast/{city}
     * Example: GET http://localhost:8080/weather/forecast/London
     * 
     * @param city - Name of the city (extracted from URL path)
     * @return ResponseEntity<ForecastResponse> - HTTP response with 7-day forecast data
     */
    @GetMapping("/forecast/{city}")
    public ResponseEntity<ForecastResponse> get7DayForecast(@PathVariable String city) {
        logger.info("Received request to fetch 7-day forecast for city: {}", city);
        
        try {
            // Validate city parameter
            if (city == null || city.trim().isEmpty()) {
                logger.warn("Empty or null city name provided");
                return ResponseEntity
                        .badRequest()
                        .build();
            }
            
            // Call service layer to fetch forecast data
            ForecastResponse forecastResponse = weatherService.get7DayForecast(city.trim());
            
            logger.info("Successfully retrieved 7-day forecast for city: {}", city);
            
            // Return successful response with forecast data
            return ResponseEntity
                    .ok()
                    .body(forecastResponse);
            
        } catch (RuntimeException e) {
            // Log the error
            logger.error("Error fetching 7-day forecast for city: {}", city, e);
            
            // Return error response
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
    
    /**
     * GET endpoint to fetch 24-hour hourly weather forecast for a specific city
     *
     * Endpoint: GET /weather/hourly/{city}
     * Example: GET http://localhost:8080/weather/hourly/London
     *
     * @param city - Name of the city (extracted from URL path)
     * @return ResponseEntity<HourlyForecastResponse> - HTTP response with 24-hour hourly forecast data
     */
    @GetMapping("/hourly/{city}")
    public ResponseEntity<?> get24HourForecast(@PathVariable String city) {
        logger.info("Received request to fetch 24-hour hourly forecast for city: {}", city);
        
        try {
            // Validate city parameter
            if (city == null || city.trim().isEmpty()) {
                logger.warn("Empty or null city name provided");
                return ResponseEntity
                        .badRequest()
                        .build();
            }
            
            // Call service layer to fetch hourly forecast data
            var hourlyForecastResponse = weatherService.get24HourForecast(city.trim());
            
            logger.info("Successfully retrieved 24-hour hourly forecast for city: {}", city);
            
            // Return successful response with hourly forecast data
            return ResponseEntity
                    .ok()
                    .body(hourlyForecastResponse);
            
        } catch (RuntimeException e) {
            // Log the error
            logger.error("Error fetching 24-hour hourly forecast for city: {}", city, e);
            
            // Return error response
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
    
    /**
     * GET endpoint to check if the API is running
     * 
     * Endpoint: GET /weather/health
     * Example: GET http://localhost:8080/weather/health
     * 
     * @return ResponseEntity<String> - Simple health check response
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        logger.info("Health check endpoint called");
        return ResponseEntity
                .ok()
                .body("Weather Forecast API is running successfully!");
    }
    
    /**
     * GET endpoint to get API information
     * 
     * Endpoint: GET /weather/info
     * Example: GET http://localhost:8080/weather/info
     * 
     * @return ResponseEntity<String> - API usage information
     */
    @GetMapping("/info")
    public ResponseEntity<String> getApiInfo() {
        logger.info("API info endpoint called");
        
        String info = """
                {
                    "name": "Weather Forecast API",
                    "version": "1.0.0",
                    "description": "REST API to fetch live weather data from OpenWeatherMap",
                    "endpoints": {
                        "getWeather": "GET /weather/{city}",
                        "health": "GET /weather/health",
                        "info": "GET /weather/info"
                    },
                    "example": "GET http://localhost:8080/weather/London"
                }
                """;
        
        return ResponseEntity
                .ok()
                .body(info);
    }
}

// Made with Bob
