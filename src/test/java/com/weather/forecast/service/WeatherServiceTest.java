package com.weather.forecast.service;

import com.weather.forecast.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for WeatherService
 * Tests the business logic for weather operations
 */
@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private WeatherService weatherService;

    private static final String TEST_API_KEY = "test-api-key";
    private static final String TEST_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String TEST_FORECAST_URL = "https://api.openweathermap.org/data/2.5/forecast";

    @BeforeEach
    void setUp() {
        // Mock WebClient.Builder to return our mocked WebClient
        when(webClientBuilder.build()).thenReturn(webClient);
        
        // Create WeatherService with mocked WebClient
        weatherService = new WeatherService(webClientBuilder);
        
        // Set private fields using ReflectionTestUtils
        ReflectionTestUtils.setField(weatherService, "apiKey", TEST_API_KEY);
        ReflectionTestUtils.setField(weatherService, "apiUrl", TEST_API_URL);
        ReflectionTestUtils.setField(weatherService, "forecastUrl", TEST_FORECAST_URL);
    }

    @Test
    void testGetWeatherByCity_Success() {
        // Arrange
        String city = "London";
        OpenWeatherMapResponse mockResponse = createMockWeatherResponse();

        // Mock WebClient chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(OpenWeatherMapResponse.class)).thenReturn(Mono.just(mockResponse));

        // Act
        WeatherResponse result = weatherService.getWeatherByCity(city);

        // Assert
        assertNotNull(result);
        assertEquals("London", result.getCity());
        assertEquals(20.5, result.getTemperature());
        assertEquals(65, result.getHumidity());
        assertEquals("clear sky", result.getWeatherDescription());
        assertEquals(5.5, result.getWindSpeed());
        assertEquals(0.0, result.getPrecipitation());
        
        // Verify WebClient was called
        verify(webClient, times(1)).get();
    }

    @Test
    void testGetWeatherByCity_NullResponse() {
        // Arrange
        String city = "London";

        // Mock WebClient to return null
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(OpenWeatherMapResponse.class)).thenReturn(Mono.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            weatherService.getWeatherByCity(city);
        });

        assertTrue(exception.getMessage().contains("Failed to fetch weather data"));
    }

    @Test
    void testGetWeatherByCity_CityNotFound() {
        // Arrange
        String city = "InvalidCity";

        // Mock WebClient to throw NotFound exception
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(OpenWeatherMapResponse.class))
                .thenThrow(WebClientResponseException.NotFound.create(404, "Not Found", null, null, null));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            weatherService.getWeatherByCity(city);
        });

        assertTrue(exception.getMessage().contains("City not found"));
    }

    @Test
    void testGetWeatherByCity_UnauthorizedApiKey() {
        // Arrange
        String city = "London";

        // Mock WebClient to throw Unauthorized exception
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(OpenWeatherMapResponse.class))
                .thenThrow(WebClientResponseException.Unauthorized.create(401, "Unauthorized", null, null, null));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            weatherService.getWeatherByCity(city);
        });

        assertTrue(exception.getMessage().contains("Invalid API key"));
    }

    @Test
    void testGet7DayForecast_Success() {
        // Arrange
        String city = "London";
        OpenWeatherMapForecastResponse mockResponse = createMockForecastResponse();

        // Mock WebClient chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(OpenWeatherMapForecastResponse.class)).thenReturn(Mono.just(mockResponse));

        // Act
        ForecastResponse result = weatherService.get7DayForecast(city);

        // Assert
        assertNotNull(result);
        assertEquals("London", result.getCity());
        assertNotNull(result.getDailyForecasts());
        assertFalse(result.getDailyForecasts().isEmpty());
        
        // Verify WebClient was called
        verify(webClient, times(1)).get();
    }

    @Test
    void testGet7DayForecast_NullResponse() {
        // Arrange
        String city = "London";

        // Mock WebClient to return null
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(OpenWeatherMapForecastResponse.class)).thenReturn(Mono.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            weatherService.get7DayForecast(city);
        });

        assertTrue(exception.getMessage().contains("Failed to fetch forecast data"));
    }

    @Test
    void testGet24HourForecast_Success() {
        // Arrange
        String city = "London";
        OpenWeatherMapForecastResponse mockResponse = createMockForecastResponse();

        // Mock WebClient chain
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(OpenWeatherMapForecastResponse.class)).thenReturn(Mono.just(mockResponse));

        // Act
        HourlyForecastResponse result = weatherService.get24HourForecast(city);

        // Assert
        assertNotNull(result);
        assertEquals("London", result.getCity());
        assertNotNull(result.getHourlyForecasts());
        assertFalse(result.getHourlyForecasts().isEmpty());
        
        // Verify WebClient was called
        verify(webClient, times(1)).get();
    }

    // Helper method to create mock weather response
    private OpenWeatherMapResponse createMockWeatherResponse() {
        OpenWeatherMapResponse response = new OpenWeatherMapResponse();
        response.setName("London");
        
        OpenWeatherMapResponse.Main main = new OpenWeatherMapResponse.Main();
        main.setTemp(20.5);
        main.setHumidity(65);
        response.setMain(main);
        
        OpenWeatherMapResponse.Weather weather = new OpenWeatherMapResponse.Weather();
        weather.setDescription("clear sky");
        weather.setIcon("01d");
        List<OpenWeatherMapResponse.Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        response.setWeather(weatherList);
        
        OpenWeatherMapResponse.Wind wind = new OpenWeatherMapResponse.Wind();
        wind.setSpeed(5.5);
        response.setWind(wind);
        
        OpenWeatherMapResponse.Clouds clouds = new OpenWeatherMapResponse.Clouds();
        clouds.setAll(20);
        response.setClouds(clouds);
        
        return response;
    }

    // Helper method to create mock forecast response
    private OpenWeatherMapForecastResponse createMockForecastResponse() {
        OpenWeatherMapForecastResponse response = new OpenWeatherMapForecastResponse();
        
        OpenWeatherMapForecastResponse.City city = new OpenWeatherMapForecastResponse.City();
        city.setName("London");
        response.setCity(city);
        
        List<OpenWeatherMapForecastResponse.ForecastItem> forecastItems = new ArrayList<>();
        
        // Create sample forecast items
        for (int i = 0; i < 8; i++) {
            OpenWeatherMapForecastResponse.ForecastItem item = new OpenWeatherMapForecastResponse.ForecastItem();
            item.setDt(System.currentTimeMillis() / 1000 + (i * 10800)); // 3-hour intervals
            
            OpenWeatherMapForecastResponse.Main main = new OpenWeatherMapForecastResponse.Main();
            main.setTemp(20.0 + i);
            main.setFeelsLike(19.0 + i);
            main.setHumidity(60 + i);
            item.setMain(main);
            
            OpenWeatherMapForecastResponse.Weather weather = new OpenWeatherMapForecastResponse.Weather();
            weather.setDescription("clear sky");
            weather.setIcon("01d");
            List<OpenWeatherMapForecastResponse.Weather> weatherList = new ArrayList<>();
            weatherList.add(weather);
            item.setWeather(weatherList);
            
            OpenWeatherMapForecastResponse.Wind wind = new OpenWeatherMapForecastResponse.Wind();
            wind.setSpeed(5.0);
            item.setWind(wind);
            
            OpenWeatherMapForecastResponse.Clouds clouds = new OpenWeatherMapForecastResponse.Clouds();
            clouds.setAll(20);
            item.setClouds(clouds);
            
            item.setPop(0.3);
            
            forecastItems.add(item);
        }
        
        response.setList(forecastItems);
        
        return response;
    }
}

// Made with Bob
