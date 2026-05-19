package com.weather.forecast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Model class representing the forecast response returned to the client.
 * Contains a list of daily forecasts for the next 7 days.
 * 
 * @author Weather Forecast Team
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForecastResponse {
    
    /**
     * Name of the city for which forecast data is fetched
     */
    private String city;
    
    /**
     * List of daily forecasts
     */
    private List<DailyForecast> dailyForecasts;
    
    /**
     * Nested class representing a single day's forecast
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyForecast {
        /**
         * Date of the forecast (e.g., "Mon, Jan 15")
         */
        private String date;
        
        /**
         * Day of week (e.g., "Monday")
         */
        private String dayOfWeek;
        
        /**
         * Maximum temperature in Celsius
         */
        private Double tempMax;
        
        /**
         * Minimum temperature in Celsius
         */
        private Double tempMin;
        
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
         * Chance of rain percentage
         */
        private Integer chanceOfRain;
        
        /**
         * Cloudiness percentage
         */
        private Integer cloudiness;
    }
}

// Made with Bob