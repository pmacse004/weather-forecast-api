package com.weather.forecast.controller;

import com.weather.forecast.model.ForecastResponse;
import com.weather.forecast.model.WeatherResponse;
import com.weather.forecast.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for WeatherController
 * Tests the REST API endpoints
 */
@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherController weatherController;

    private WeatherResponse mockWeatherResponse;
    private ForecastResponse mockForecastResponse;

    @BeforeEach
    void setUp() {
        // Create mock weather response
        mockWeatherResponse = new WeatherResponse();
        mockWeatherResponse.setCity("London");
        mockWeatherResponse.setTemperature(20.5);
        mockWeatherResponse.setHumidity(65);
        mockWeatherResponse.setWeatherDescription("clear sky");
        mockWeatherResponse.setWindSpeed(5.5);
        mockWeatherResponse.setPrecipitation(0.0);
        mockWeatherResponse.setUvIndex(8.5);
        mockWeatherResponse.setChanceOfRain(20);
        mockWeatherResponse.setCloudiness(15);

        // Create mock forecast response
        mockForecastResponse = new ForecastResponse();
        mockForecastResponse.setCity("London");
        List<ForecastResponse.DailyForecast> dailyForecasts = new ArrayList<>();
        
        ForecastResponse.DailyForecast dailyForecast = new ForecastResponse.DailyForecast();
        dailyForecast.setDate("Mon, May 18");
        dailyForecast.setDayOfWeek("Monday");
        dailyForecast.setTempMax(25.0);
        dailyForecast.setTempMin(15.0);
        dailyForecast.setWeatherDescription("clear sky");
        dailyForecast.setWeatherIcon("01d");
        dailyForecast.setHumidity(60);
        dailyForecast.setWindSpeed(5.0);
        dailyForecast.setCloudiness(10);
        dailyForecast.setChanceOfRain(15);
        
        dailyForecasts.add(dailyForecast);
        mockForecastResponse.setDailyForecasts(dailyForecasts);
    }

    @Test
    void testGetWeather_Success() {
        // Arrange
        String city = "London";
        when(weatherService.getWeatherByCity(city)).thenReturn(mockWeatherResponse);

        // Act
        ResponseEntity<WeatherResponse> response = weatherController.getWeather(city);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("London", response.getBody().getCity());
        assertEquals(20.5, response.getBody().getTemperature());
        
        // Verify service was called once
        verify(weatherService, times(1)).getWeatherByCity(city);
    }

    @Test
    void testGetWeather_EmptyCity() {
        // Act
        ResponseEntity<WeatherResponse> response = weatherController.getWeather("");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        // Verify service was never called
        verify(weatherService, never()).getWeatherByCity(anyString());
    }

    @Test
    void testGetWeather_NullCity() {
        // Act
        ResponseEntity<WeatherResponse> response = weatherController.getWeather(null);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        // Verify service was never called
        verify(weatherService, never()).getWeatherByCity(anyString());
    }

    @Test
    void testGetWeather_WhitespaceCity() {
        // Act
        ResponseEntity<WeatherResponse> response = weatherController.getWeather("   ");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        // Verify service was never called
        verify(weatherService, never()).getWeatherByCity(anyString());
    }

    @Test
    void testGetWeather_ServiceThrowsException() {
        // Arrange
        String city = "InvalidCity";
        when(weatherService.getWeatherByCity(city))
                .thenThrow(new RuntimeException("City not found"));

        // Act
        ResponseEntity<WeatherResponse> response = weatherController.getWeather(city);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        
        // Verify service was called
        verify(weatherService, times(1)).getWeatherByCity(city);
    }

    @Test
    void testGetWeather_TrimsCityName() {
        // Arrange
        String cityWithSpaces = "  London  ";
        String trimmedCity = "London";
        when(weatherService.getWeatherByCity(trimmedCity)).thenReturn(mockWeatherResponse);

        // Act
        ResponseEntity<WeatherResponse> response = weatherController.getWeather(cityWithSpaces);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        // Verify service was called with trimmed city name
        verify(weatherService, times(1)).getWeatherByCity(trimmedCity);
    }

    @Test
    void testGet7DayForecast_Success() {
        // Arrange
        String city = "London";
        when(weatherService.get7DayForecast(city)).thenReturn(mockForecastResponse);

        // Act
        ResponseEntity<ForecastResponse> response = weatherController.get7DayForecast(city);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("London", response.getBody().getCity());
        assertNotNull(response.getBody().getDailyForecasts());
        assertFalse(response.getBody().getDailyForecasts().isEmpty());
        
        // Verify service was called once
        verify(weatherService, times(1)).get7DayForecast(city);
    }

    @Test
    void testGet7DayForecast_EmptyCity() {
        // Act
        ResponseEntity<ForecastResponse> response = weatherController.get7DayForecast("");

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
        
        // Verify service was never called
        verify(weatherService, never()).get7DayForecast(anyString());
    }

    @Test
    void testGet7DayForecast_ServiceThrowsException() {
        // Arrange
        String city = "InvalidCity";
        when(weatherService.get7DayForecast(city))
                .thenThrow(new RuntimeException("City not found"));

        // Act
        ResponseEntity<ForecastResponse> response = weatherController.get7DayForecast(city);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
        
        // Verify service was called
        verify(weatherService, times(1)).get7DayForecast(city);
    }

    @Test
    void testHealthCheck() {
        // Act
        ResponseEntity<String> response = weatherController.healthCheck();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Weather Forecast API is running"));
    }

    @Test
    void testGetApiInfo() {
        // Act
        ResponseEntity<String> response = weatherController.getApiInfo();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("Weather Forecast API"));
        assertTrue(response.getBody().contains("version"));
        assertTrue(response.getBody().contains("endpoints"));
    }
}

// Made with Bob
