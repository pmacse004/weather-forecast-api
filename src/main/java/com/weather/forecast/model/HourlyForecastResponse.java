package com.weather.forecast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Model class representing the hourly forecast response for the next 24 hours.
 * 
 * @author Weather Forecast Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HourlyForecastResponse {
    
    /**
     * Name of the city for which hourly forecast data is fetched
     */
    private String city;
    
    /**
     * List of hourly forecasts (up to 24 hours)
     */
    private List<HourlyForecast> hourlyForecasts;
    
    /**
     * Nested class representing a single hour's forecast
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HourlyForecast {
        /**
         * Time of the forecast (e.g., "2:00 PM", "15:00")
         */
        private String time;
        
        /**
         * Hour in 24-hour format (0-23)
         */
        private Integer hour;
        
        /**
         * Temperature in Celsius
         */
        private Double temperature;
        
        /**
         * Feels like temperature in Celsius
         */
        private Double feelsLike;
        
        /**
         * Weather description (e.g., "clear sky", "light rain")
         */
        private String weatherDescription;
        
        /**
         * Weather icon code from OpenWeatherMap
         */
        private String weatherIcon;
        
        /**
         * Humidity percentage
         */
        private Integer humidity;
        
        /**
         * Wind speed in meters per second
         */
        private Double windSpeed;
        
        /**
         * Probability of precipitation (0-100%)
         */
        private Integer precipitationProbability;
        
        /**
         * Cloudiness percentage
         */
        private Integer cloudiness;
    }
}

// Made with Bob