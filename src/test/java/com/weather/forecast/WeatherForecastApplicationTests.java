package com.weather.forecast;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * Integration tests for the Weather Forecast Application
 * Tests that the Spring Boot application context loads successfully
 */
@SpringBootTest
@TestPropertySource(properties = {
    "openweathermap.api.key=test-key",
    "openweathermap.api.url=https://api.openweathermap.org/data/2.5/weather",
    "openweathermap.forecast.url=https://api.openweathermap.org/data/2.5/forecast"
})
class WeatherForecastApplicationTests {

    /**
     * Test that the Spring application context loads successfully
     * This verifies that all beans are properly configured and can be instantiated
     */
    @Test
    void contextLoads() {
        // If the context loads successfully, this test passes
        // This is a smoke test to ensure the application starts without errors
    }
}

// Made with Bob
