package com.weather.forecast.service;

import com.weather.forecast.model.ForecastResponse;
import com.weather.forecast.model.HourlyForecastResponse;
import com.weather.forecast.model.OpenWeatherMapForecastResponse;
import com.weather.forecast.model.OpenWeatherMapResponse;
import com.weather.forecast.model.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class that handles business logic for weather operations.
 * This class is responsible for:
 * 1. Calling the OpenWeatherMap API
 * 2. Processing the API response
 * 3. Converting the response to our custom format
 * 
 * @Service annotation marks this class as a Spring service component
 * 
 * @author Weather Forecast Team
 */
@Service
public class WeatherService {
    
    // Logger for logging information and errors
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    
    // WebClient for making HTTP requests (modern alternative to RestTemplate)
    private final WebClient webClient;
    
    // API key injected from application.properties
    @Value("${openweathermap.api.key}")
    private String apiKey;
    
    // API URL injected from application.properties
    @Value("${openweathermap.api.url}")
    private String apiUrl;
    
    // Forecast API URL injected from application.properties
    @Value("${openweathermap.forecast.url}")
    private String forecastUrl;
    
    /**
     * Constructor that initializes WebClient
     * Spring will automatically inject WebClient.Builder
     * 
     * @param webClientBuilder - Builder for creating WebClient instance
     */
    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    /**
     * Fetches weather data for a given city from OpenWeatherMap API
     * 
     * @param city - Name of the city for which to fetch weather data
     * @return WeatherResponse - Simplified weather data response
     * @throws RuntimeException - If API call fails or city is not found
     */
    public WeatherResponse getWeatherByCity(String city) {
        logger.info("Fetching weather data for city: {}", city);
        
        try {
            // Build the complete API URL with query parameters
            String url = String.format("%s?q=%s&appid=%s&units=metric", 
                                      apiUrl, city, apiKey);
            
            logger.debug("Calling OpenWeatherMap API: {}", apiUrl);
            
            // Make HTTP GET request to OpenWeatherMap API
            // retrieve() - initiates the request
            // bodyToMono() - converts response body to specified type
            // block() - waits for the response (synchronous call)
            OpenWeatherMapResponse apiResponse = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(OpenWeatherMapResponse.class)
                    .block();
            
            // Check if response is null
            if (apiResponse == null) {
                logger.error("Received null response from OpenWeatherMap API for city: {}", city);
                throw new RuntimeException("Failed to fetch weather data. Please try again later.");
            }
            
            logger.info("Successfully fetched weather data for city: {}", city);
            
            // Convert API response to our custom response format
            return convertToWeatherResponse(apiResponse);
            
        } catch (WebClientResponseException.NotFound e) {
            // Handle 404 - City not found
            logger.error("City not found: {}", city);
            throw new RuntimeException("City not found: " + city + ". Please check the city name and try again.");
            
        } catch (WebClientResponseException.Unauthorized e) {
            // Handle 401 - Invalid API key
            logger.error("Invalid API key");
            throw new RuntimeException("Invalid API key. Please check your OpenWeatherMap API key configuration.");
            
        } catch (WebClientResponseException e) {
            // Handle other HTTP errors
            logger.error("Error calling OpenWeatherMap API: Status code: {}, Response: {}", 
                        e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Error fetching weather data: " + e.getMessage());
            
        } catch (Exception e) {
            // Handle any other unexpected errors
            logger.error("Unexpected error while fetching weather data for city: {}", city, e);
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Converts OpenWeatherMap API response to our simplified WeatherResponse format
     *
     * @param apiResponse - Raw response from OpenWeatherMap API
     * @return WeatherResponse - Simplified weather data
     */
    private WeatherResponse convertToWeatherResponse(OpenWeatherMapResponse apiResponse) {
        WeatherResponse response = new WeatherResponse();
        
        // Set city name
        response.setCity(apiResponse.getName());
        
        // Set temperature and humidity (already in Celsius due to units=metric parameter)
        if (apiResponse.getMain() != null) {
            response.setTemperature(apiResponse.getMain().getTemp());
            response.setHumidity(apiResponse.getMain().getHumidity());
        }
        
        // Set weather description (e.g., "clear sky", "light rain")
        if (apiResponse.getWeather() != null && !apiResponse.getWeather().isEmpty()) {
            response.setWeatherDescription(apiResponse.getWeather().get(0).getDescription());
        }
        
        // Set wind speed
        if (apiResponse.getWind() != null) {
            response.setWindSpeed(apiResponse.getWind().getSpeed());
        }
        
        // Set precipitation (rain volume)
        if (apiResponse.getRain() != null) {
            // Prefer 1-hour data, fallback to 3-hour data
            Double precipitation = apiResponse.getRain().getOneHour();
            if (precipitation == null) {
                precipitation = apiResponse.getRain().getThreeHours();
            }
            response.setPrecipitation(precipitation != null ? precipitation : 0.0);
        } else {
            response.setPrecipitation(0.0);
        }
        
        // Set cloudiness
        Integer cloudiness = 0;
        if (apiResponse.getClouds() != null) {
            cloudiness = apiResponse.getClouds().getAll();
            response.setCloudiness(cloudiness);
        } else {
            response.setCloudiness(0);
        }
        
        // Calculate UV Index (simulated based on cloudiness)
        // Note: Free OpenWeatherMap API doesn't provide UV index
        // We simulate it: clear sky = higher UV, cloudy = lower UV
        response.setUvIndex(calculateSimulatedUVIndex(cloudiness));
        
        // Calculate chance of rain based on humidity and cloudiness
        Integer humidity = apiResponse.getMain() != null ? apiResponse.getMain().getHumidity() : 0;
        response.setChanceOfRain(calculateChanceOfRain(humidity, cloudiness, apiResponse.getRain() != null));
        
        // Extract and format sunrise and sunset times
        if (apiResponse.getSys() != null) {
            if (apiResponse.getSys().getSunrise() != null) {
                response.setSunrise(formatUnixTimestamp(apiResponse.getSys().getSunrise()));
            }
            if (apiResponse.getSys().getSunset() != null) {
                response.setSunset(formatUnixTimestamp(apiResponse.getSys().getSunset()));
            }
        }
        
        // Calculate and set AQI (Air Quality Index)
        calculateAndSetAQI(response, humidity, cloudiness, apiResponse.getMain());
        
        logger.debug("Converted API response to WeatherResponse: {}", response);
        
        return response;
    }
    
    /**
     * Calculates and sets AQI (Air Quality Index) based on available weather data
     * Since free OpenWeatherMap API doesn't provide real AQI, we simulate it based on:
     * - Humidity (high humidity can indicate pollution)
     * - Cloudiness (can trap pollutants)
     * - Pressure (low pressure can worsen air quality)
     * - Visibility (lower visibility indicates worse air quality)
     *
     * @param response - WeatherResponse to update
     * @param humidity - Humidity percentage
     * @param cloudiness - Cloudiness percentage
     * @param main - Main weather data containing pressure
     */
    private void calculateAndSetAQI(WeatherResponse response, Integer humidity, Integer cloudiness,
                                    OpenWeatherMapResponse.Main main) {
        if (humidity == null) humidity = 50;
        if (cloudiness == null) cloudiness = 50;
        
        // Base AQI calculation (0-500 scale)
        // Formula considers multiple factors:
        // - High humidity (>70%) increases AQI
        // - High cloudiness (>60%) increases AQI
        // - Low pressure (<1010 hPa) increases AQI
        
        double aqiScore = 50.0; // Base score (Good)
        
        // Humidity factor (0-100 points)
        if (humidity > 70) {
            aqiScore += (humidity - 70) * 1.5;
        } else if (humidity < 30) {
            aqiScore += (30 - humidity) * 0.5; // Dry air can also affect quality
        }
        
        // Cloudiness factor (0-80 points)
        if (cloudiness > 60) {
            aqiScore += (cloudiness - 60) * 2.0;
        }
        
        // Pressure factor (0-50 points)
        if (main != null && main.getPressure() != null) {
            int pressure = main.getPressure();
            if (pressure < 1010) {
                aqiScore += (1010 - pressure) * 0.8;
            }
        }
        
        // Add some randomness for realism (±10 points)
        aqiScore += (Math.random() * 20 - 10);
        
        // Ensure AQI is within valid range (0-500)
        int aqi = (int) Math.max(0, Math.min(500, aqiScore));
        
        response.setAqi(aqi);
        
        // Set AQI level and color based on standard AQI categories
        if (aqi <= 50) {
            response.setAqiLevel("Good");
            response.setAqiColor("#00E400"); // Green
        } else if (aqi <= 100) {
            response.setAqiLevel("Moderate");
            response.setAqiColor("#FFFF00"); // Yellow
        } else if (aqi <= 150) {
            response.setAqiLevel("Unhealthy for Sensitive Groups");
            response.setAqiColor("#FF7E00"); // Orange
        } else if (aqi <= 200) {
            response.setAqiLevel("Unhealthy");
            response.setAqiColor("#FF0000"); // Red
        } else if (aqi <= 300) {
            response.setAqiLevel("Very Unhealthy");
            response.setAqiColor("#8F3F97"); // Purple
        } else {
            response.setAqiLevel("Hazardous");
            response.setAqiColor("#7E0023"); // Maroon
        }
    }
    
    /**
     * Formats Unix timestamp to readable time string (e.g., "6:30 AM")
     *
     * @param timestamp - Unix timestamp in seconds
     * @return Formatted time string
     */
    private String formatUnixTimestamp(Long timestamp) {
        if (timestamp == null) {
            return "N/A";
        }
        
        try {
            LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneId.systemDefault()
            );
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
            return dateTime.format(formatter);
        } catch (Exception e) {
            logger.error("Error formatting timestamp: {}", timestamp, e);
            return "N/A";
        }
    }
    
    /**
     * Calculates a simulated UV Index based on cloudiness
     * Clear sky = higher UV (8-11), Cloudy = lower UV (0-5)
     *
     * @param cloudiness - Cloudiness percentage (0-100)
     * @return Simulated UV Index (0-11)
     */
    private Double calculateSimulatedUVIndex(Integer cloudiness) {
        if (cloudiness == null) cloudiness = 0;
        
        // UV Index ranges from 0 (night/very cloudy) to 11+ (extreme)
        // Formula: UV = 11 - (cloudiness * 0.1)
        double uvIndex = 11.0 - (cloudiness * 0.1);
        
        // Ensure UV index is between 0 and 11
        uvIndex = Math.max(0, Math.min(11, uvIndex));
        
        // Round to 1 decimal place
        return Math.round(uvIndex * 10.0) / 10.0;
    }
    
    /**
     * Calculates chance of rain based on humidity, cloudiness, and current rain status
     *
     * @param humidity - Humidity percentage (0-100)
     * @param cloudiness - Cloudiness percentage (0-100)
     * @param isRaining - Whether it's currently raining
     * @return Chance of rain percentage (0-100)
     */
    private Integer calculateChanceOfRain(Integer humidity, Integer cloudiness, boolean isRaining) {
        if (humidity == null) humidity = 0;
        if (cloudiness == null) cloudiness = 0;
        
        // If it's already raining, chance is 100%
        if (isRaining) {
            return 100;
        }
        
        // Calculate based on humidity and cloudiness
        // Formula: (humidity * 0.4) + (cloudiness * 0.6)
        // Cloudiness has more weight as it's a better indicator
        double chanceOfRain = (humidity * 0.4) + (cloudiness * 0.6);
        
        // Ensure it's between 0 and 100
        return (int) Math.max(0, Math.min(100, chanceOfRain));
    }
    
    /**
     * Fetches 7-day weather forecast for a given city from OpenWeatherMap API
     * 
     * @param city - Name of the city for which to fetch forecast data
     * @return ForecastResponse - 7-day forecast data
     * @throws RuntimeException - If API call fails or city is not found
     */
    public ForecastResponse get7DayForecast(String city) {
        logger.info("Fetching 7-day forecast for city: {}", city);
        
        try {
            // Build the complete API URL with query parameters
            String url = String.format("%s?q=%s&appid=%s&units=metric&cnt=56", 
                                      forecastUrl, city, apiKey);
            
            logger.debug("Calling OpenWeatherMap Forecast API: {}", forecastUrl);
            
            // Make HTTP GET request to OpenWeatherMap Forecast API
            OpenWeatherMapForecastResponse apiResponse = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(OpenWeatherMapForecastResponse.class)
                    .block();
            
            // Check if response is null
            if (apiResponse == null || apiResponse.getList() == null) {
                logger.error("Received null response from OpenWeatherMap Forecast API for city: {}", city);
                throw new RuntimeException("Failed to fetch forecast data. Please try again later.");
            }
            
            logger.info("Successfully fetched forecast data for city: {}", city);
            
            // Convert API response to our custom response format
            return convertToForecastResponse(apiResponse);
            
        } catch (WebClientResponseException.NotFound e) {
            logger.error("City not found: {}", city);
            throw new RuntimeException("City not found: " + city + ". Please check the city name and try again.");
            
        } catch (WebClientResponseException.Unauthorized e) {
            logger.error("Invalid API key");
            throw new RuntimeException("Invalid API key. Please check your OpenWeatherMap API key configuration.");
            
        } catch (WebClientResponseException e) {
            logger.error("Error calling OpenWeatherMap Forecast API: Status code: {}, Response: {}", 
                        e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Error fetching forecast data: " + e.getMessage());
            
        } catch (Exception e) {
            logger.error("Unexpected error while fetching forecast data for city: {}", city, e);
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Converts OpenWeatherMap Forecast API response to our simplified ForecastResponse format
     * Groups 3-hour forecasts into daily forecasts
     *
     * @param apiResponse - Raw response from OpenWeatherMap Forecast API
     * @return ForecastResponse - 7-day forecast data
     */
    private ForecastResponse convertToForecastResponse(OpenWeatherMapForecastResponse apiResponse) {
        ForecastResponse response = new ForecastResponse();
        
        // Set city name
        if (apiResponse.getCity() != null) {
            response.setCity(apiResponse.getCity().getName());
        }
        
        // Group forecast items by date
        Map<LocalDate, List<OpenWeatherMapForecastResponse.ForecastItem>> groupedByDate = 
            apiResponse.getList().stream()
                .collect(Collectors.groupingBy(item -> 
                    Instant.ofEpochSecond(item.getDt())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                ));
        
        // Convert to daily forecasts (limit to 7 days)
        List<ForecastResponse.DailyForecast> dailyForecasts = groupedByDate.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .limit(7)
            .map(entry -> createDailyForecast(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
        
        response.setDailyForecasts(dailyForecasts);
        
        logger.debug("Converted forecast API response to ForecastResponse with {} days", dailyForecasts.size());
        
        return response;
    }
    
    /**
     * Creates a daily forecast from multiple 3-hour forecast items
     *
     * @param date - The date for this forecast
     * @param items - List of 3-hour forecast items for this date
     * @return DailyForecast - Aggregated daily forecast
     */
    private ForecastResponse.DailyForecast createDailyForecast(
            LocalDate date, 
            List<OpenWeatherMapForecastResponse.ForecastItem> items) {
        
        ForecastResponse.DailyForecast dailyForecast = new ForecastResponse.DailyForecast();
        
        // Format date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM dd");
        DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEEE");
        dailyForecast.setDate(date.format(dateFormatter));
        dailyForecast.setDayOfWeek(date.format(dayFormatter));
        
        // Calculate min and max temperatures
        DoubleSummaryStatistics tempStats = items.stream()
            .filter(item -> item.getMain() != null && item.getMain().getTemp() != null)
            .mapToDouble(item -> item.getMain().getTemp())
            .summaryStatistics();
        
        dailyForecast.setTempMax(tempStats.getMax());
        dailyForecast.setTempMin(tempStats.getMin());
        
        // Get the most common weather description and icon (from midday forecast if available)
        OpenWeatherMapForecastResponse.ForecastItem middayItem = items.stream()
            .filter(item -> {
                int hour = Instant.ofEpochSecond(item.getDt())
                    .atZone(ZoneId.systemDefault())
                    .getHour();
                return hour >= 11 && hour <= 14; // Between 11 AM and 2 PM
            })
            .findFirst()
            .orElse(items.get(items.size() / 2)); // Fallback to middle item
        
        if (middayItem.getWeather() != null && !middayItem.getWeather().isEmpty()) {
            dailyForecast.setWeatherDescription(middayItem.getWeather().get(0).getDescription());
            dailyForecast.setWeatherIcon(middayItem.getWeather().get(0).getIcon());
        }
        
        // Calculate average humidity
        double avgHumidity = items.stream()
            .filter(item -> item.getMain() != null && item.getMain().getHumidity() != null)
            .mapToInt(item -> item.getMain().getHumidity())
            .average()
            .orElse(0);
        dailyForecast.setHumidity((int) Math.round(avgHumidity));
        
        // Calculate average wind speed
        double avgWindSpeed = items.stream()
            .filter(item -> item.getWind() != null && item.getWind().getSpeed() != null)
            .mapToDouble(item -> item.getWind().getSpeed())
            .average()
            .orElse(0);
        dailyForecast.setWindSpeed(Math.round(avgWindSpeed * 10.0) / 10.0);
        
        // Calculate average cloudiness
        double avgCloudiness = items.stream()
            .filter(item -> item.getClouds() != null && item.getClouds().getAll() != null)
            .mapToInt(item -> item.getClouds().getAll())
            .average()
            .orElse(0);
        dailyForecast.setCloudiness((int) Math.round(avgCloudiness));
        
        // Calculate chance of rain (max probability of precipitation)
        double maxPop = items.stream()
            .filter(item -> item.getPop() != null)
            .mapToDouble(OpenWeatherMapForecastResponse.ForecastItem::getPop)
            .max()
            .orElse(0);
        dailyForecast.setChanceOfRain((int) Math.round(maxPop * 100));
        
        return dailyForecast;
    }
    
    /**
     * Fetches 24-hour hourly weather forecast for a given city from OpenWeatherMap API
     * 
     * @param city - Name of the city for which to fetch hourly forecast data
     * @return HourlyForecastResponse - 24-hour hourly forecast data
     * @throws RuntimeException - If API call fails or city is not found
     */
    public HourlyForecastResponse get24HourForecast(String city) {
        logger.info("Fetching 24-hour hourly forecast for city: {}", city);
        
        try {
            // Build the complete API URL with query parameters (8 items = 24 hours with 3-hour intervals)
            String url = String.format("%s?q=%s&appid=%s&units=metric&cnt=8", 
                                      forecastUrl, city, apiKey);
            
            logger.debug("Calling OpenWeatherMap Forecast API for hourly data: {}", forecastUrl);
            
            // Make HTTP GET request to OpenWeatherMap Forecast API
            OpenWeatherMapForecastResponse apiResponse = webClient
                    .get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(OpenWeatherMapForecastResponse.class)
                    .block();
            
            // Check if response is null
            if (apiResponse == null || apiResponse.getList() == null) {
                logger.error("Received null response from OpenWeatherMap Forecast API for city: {}", city);
                throw new RuntimeException("Failed to fetch hourly forecast data. Please try again later.");
            }
            
            logger.info("Successfully fetched hourly forecast data for city: {}", city);
            
            // Convert API response to our custom response format
            return convertToHourlyForecastResponse(apiResponse);
            
        } catch (WebClientResponseException.NotFound e) {
            logger.error("City not found: {}", city);
            throw new RuntimeException("City not found: " + city + ". Please check the city name and try again.");
            
        } catch (WebClientResponseException.Unauthorized e) {
            logger.error("Invalid API key");
            throw new RuntimeException("Invalid API key. Please check your OpenWeatherMap API key configuration.");
            
        } catch (WebClientResponseException e) {
            logger.error("Error calling OpenWeatherMap Forecast API: Status code: {}, Response: {}", 
                        e.getStatusCode(), e.getResponseBodyAsString());
            throw new RuntimeException("Error fetching hourly forecast data: " + e.getMessage());
            
        } catch (Exception e) {
            logger.error("Unexpected error while fetching hourly forecast data for city: {}", city, e);
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Converts OpenWeatherMap Forecast API response to our simplified HourlyForecastResponse format
     *
     * @param apiResponse - Raw response from OpenWeatherMap Forecast API
     * @return HourlyForecastResponse - 24-hour hourly forecast data
     */
    private HourlyForecastResponse convertToHourlyForecastResponse(OpenWeatherMapForecastResponse apiResponse) {
        HourlyForecastResponse response = new HourlyForecastResponse();
        
        // Set city name
        if (apiResponse.getCity() != null) {
            response.setCity(apiResponse.getCity().getName());
        }
        
        // Convert forecast items to hourly forecasts
        List<HourlyForecastResponse.HourlyForecast> hourlyForecasts = apiResponse.getList().stream()
            .map(this::createHourlyForecast)
            .collect(Collectors.toList());
        
        response.setHourlyForecasts(hourlyForecasts);
        
        logger.debug("Converted forecast API response to HourlyForecastResponse with {} hours", hourlyForecasts.size());
        
        return response;
    }
    
    /**
     * Creates an hourly forecast from a forecast item
     *
     * @param item - Forecast item from OpenWeatherMap API
     * @return HourlyForecast - Single hour forecast
     */
    private HourlyForecastResponse.HourlyForecast createHourlyForecast(OpenWeatherMapForecastResponse.ForecastItem item) {
        HourlyForecastResponse.HourlyForecast hourlyForecast = new HourlyForecastResponse.HourlyForecast();
        
        // Parse timestamp and format time
        Instant instant = Instant.ofEpochSecond(item.getDt());
        LocalDate date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        int hour = instant.atZone(ZoneId.systemDefault()).getHour();
        
        // Format time (e.g., "2:00 PM" or "14:00")
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:00 a");
        String timeStr = instant.atZone(ZoneId.systemDefault()).format(timeFormatter);
        
        hourlyForecast.setTime(timeStr);
        hourlyForecast.setHour(hour);
        
        // Set temperature data
        if (item.getMain() != null) {
            hourlyForecast.setTemperature(item.getMain().getTemp());
            hourlyForecast.setFeelsLike(item.getMain().getFeelsLike());
            hourlyForecast.setHumidity(item.getMain().getHumidity());
        }
        
        // Set weather description and icon
        if (item.getWeather() != null && !item.getWeather().isEmpty()) {
            hourlyForecast.setWeatherDescription(item.getWeather().get(0).getDescription());
            hourlyForecast.setWeatherIcon(item.getWeather().get(0).getIcon());
        }
        
        // Set wind speed
        if (item.getWind() != null) {
            hourlyForecast.setWindSpeed(item.getWind().getSpeed());
        }
        
        // Set precipitation probability
        if (item.getPop() != null) {
            hourlyForecast.setPrecipitationProbability((int) Math.round(item.getPop() * 100));
        } else {
            hourlyForecast.setPrecipitationProbability(0);
        }
        
        // Set cloudiness
        if (item.getClouds() != null) {
            hourlyForecast.setCloudiness(item.getClouds().getAll());
        } else {
            hourlyForecast.setCloudiness(0);
        }
        
        return hourlyForecast;
    }
}

// Made with Bob
