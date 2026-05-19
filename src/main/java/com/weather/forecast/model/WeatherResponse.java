package com.weather.forecast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Model class representing the weather response returned to the client.
 * This is a simplified version containing only the essential weather information.
 * 
 * @author Weather Forecast Team
 */
@Data // Lombok annotation - generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Lombok annotation - generates no-argument constructor
@AllArgsConstructor // Lombok annotation - generates constructor with all fields
public class WeatherResponse {
    
    /**
     * Name of the city for which weather data is fetched
     */
    private String city;
    
    /**
     * Current temperature in Celsius
     */
    private Double temperature;
    
    /**
     * Current humidity percentage
     */
    private Integer humidity;
    
    /**
     * Description of current weather conditions (e.g., "clear sky", "light rain")
     */
    private String weatherDescription;
    
    /**
     * Current wind speed in meters per second
     */
    private Double windSpeed;
    
    /**
     * Precipitation (rain) volume in mm for the last hour
     */
    private Double precipitation;
    
    /**
     * UV Index (Note: Basic OpenWeatherMap API doesn't provide UV index,
     * but we'll calculate a simulated value based on cloudiness)
     */
    private Double uvIndex;
    
    /**
     * Chance of rain percentage (calculated based on humidity and cloudiness)
     */
    private Integer chanceOfRain;
    
    /**
     * Cloudiness percentage
     */
    private Integer cloudiness;
    
    /**
     * Sunrise time in formatted string (e.g., "6:30 AM")
     */
    private String sunrise;
    
    /**
     * Sunset time in formatted string (e.g., "7:45 PM")
     */
    private String sunset;
    
    /**
     * Air Quality Index (AQI) value (0-500)
     * Simulated based on weather conditions
     */
    private Integer aqi;
    
    /**
     * AQI Level description (Good, Moderate, Unhealthy, etc.)
     */
    private String aqiLevel;
    
    /**
     * AQI color indicator for UI display
     */
    private String aqiColor;
}

// Made with Bob
