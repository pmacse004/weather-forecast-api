package com.weather.forecast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Model class representing the raw forecast response from OpenWeatherMap API.
 * This class maps to the JSON structure returned by the 5-day/3-hour forecast API.
 * 
 * @author Weather Forecast Team
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapForecastResponse {
    
    /**
     * Response code
     */
    private String cod;
    
    /**
     * Number of forecast entries
     */
    private Integer cnt;
    
    /**
     * List of forecast data points (every 3 hours)
     */
    private List<ForecastItem> list;
    
    /**
     * City information
     */
    private City city;
    
    /**
     * Nested class for each forecast item
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ForecastItem {
        /**
         * Time of data forecasted (Unix timestamp)
         */
        private Long dt;
        
        /**
         * Main weather parameters
         */
        private Main main;
        
        /**
         * Weather conditions
         */
        private List<Weather> weather;
        
        /**
         * Cloudiness data
         */
        private Clouds clouds;
        
        /**
         * Wind information
         */
        private Wind wind;
        
        /**
         * Visibility in meters
         */
        private Integer visibility;
        
        /**
         * Probability of precipitation (0-1)
         */
        private Double pop;
        
        /**
         * Rain data
         */
        private Rain rain;
        
        /**
         * Part of the day (n - night, d - day)
         */
        private Sys sys;
        
        /**
         * Date/time text (e.g., "2023-01-15 12:00:00")
         */
        @JsonProperty("dt_txt")
        private String dtTxt;
    }
    
    /**
     * Nested class for main weather parameters
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private Double temp;
        
        @JsonProperty("feels_like")
        private Double feelsLike;
        
        @JsonProperty("temp_min")
        private Double tempMin;
        
        @JsonProperty("temp_max")
        private Double tempMax;
        
        private Integer pressure;
        private Integer humidity;
        
        @JsonProperty("sea_level")
        private Integer seaLevel;
        
        @JsonProperty("grnd_level")
        private Integer grndLevel;
        
        @JsonProperty("temp_kf")
        private Double tempKf;
    }
    
    /**
     * Nested class for weather conditions
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }
    
    /**
     * Nested class for cloudiness
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Clouds {
        private Integer all;
    }
    
    /**
     * Nested class for wind information
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        private Double speed;
        private Integer deg;
        private Double gust;
    }
    
    /**
     * Nested class for rain data
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rain {
        @JsonProperty("3h")
        private Double threeHours;
    }
    
    /**
     * Nested class for system data
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        private String pod; // Part of day (n - night, d - day)
    }
    
    /**
     * Nested class for city information
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class City {
        private Long id;
        private String name;
        private Coord coord;
        private String country;
        private Integer population;
        private Integer timezone;
        private Long sunrise;
        private Long sunset;
    }
    
    /**
     * Nested class for coordinates
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {
        private Double lat;
        private Double lon;
    }
}

// Made with Bob