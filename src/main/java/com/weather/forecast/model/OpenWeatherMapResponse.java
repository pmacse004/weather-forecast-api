package com.weather.forecast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Model class representing the raw response from OpenWeatherMap API.
 * This class maps to the JSON structure returned by the API.
 * 
 * @JsonIgnoreProperties - ignores any unknown properties in the JSON response
 * 
 * @author Weather Forecast Team
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapResponse {
    
    /**
     * Coordinates of the location
     */
    private Coord coord;
    
    /**
     * List of weather conditions
     */
    private List<Weather> weather;
    
    /**
     * Internal parameter
     */
    private String base;
    
    /**
     * Main weather data (temperature, humidity, pressure, etc.)
     */
    private Main main;
    
    /**
     * Visibility in meters
     */
    private Integer visibility;
    
    /**
     * Wind information
     */
    private Wind wind;
    
    /**
     * Cloudiness data
     */
    private Clouds clouds;
    
    /**
     * Rain data (precipitation)
     */
    private Rain rain;
    
    /**
     * Time of data calculation (Unix timestamp)
     */
    private Long dt;
    
    /**
     * System data (country, sunrise, sunset)
     */
    private Sys sys;
    
    /**
     * Timezone offset in seconds
     */
    private Integer timezone;
    
    /**
     * City ID
     */
    private Long id;
    
    /**
     * City name
     */
    private String name;
    
    /**
     * HTTP response code
     */
    private Integer cod;
    
    /**
     * Nested class for coordinates
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Coord {
        private Double lon; // Longitude
        private Double lat; // Latitude
    }
    
    /**
     * Nested class for weather conditions
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private Integer id;          // Weather condition id
        private String main;         // Group of weather parameters (Rain, Snow, Extreme etc.)
        private String description;  // Weather condition description
        private String icon;         // Weather icon id
    }
    
    /**
     * Nested class for main weather parameters
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        private Double temp;        // Temperature (Kelvin by default)
        
        @JsonProperty("feels_like")
        private Double feelsLike;   // Temperature feels like
        
        @JsonProperty("temp_min")
        private Double tempMin;     // Minimum temperature
        
        @JsonProperty("temp_max")
        private Double tempMax;     // Maximum temperature
        
        private Integer pressure;   // Atmospheric pressure (hPa)
        private Integer humidity;   // Humidity percentage
        
        @JsonProperty("sea_level")
        private Integer seaLevel;   // Atmospheric pressure at sea level
        
        @JsonProperty("grnd_level")
        private Integer grndLevel;  // Atmospheric pressure at ground level
    }
    
    /**
     * Nested class for wind information
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Wind {
        private Double speed;  // Wind speed (meter/sec)
        private Integer deg;   // Wind direction (degrees)
        private Double gust;   // Wind gust (meter/sec)
    }
    
    /**
     * Nested class for cloudiness
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Clouds {
        private Integer all;  // Cloudiness percentage
    }
    
    /**
     * Nested class for rain data (precipitation)
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rain {
        @JsonProperty("1h")
        private Double oneHour;  // Rain volume for last 1 hour (mm)
        
        @JsonProperty("3h")
        private Double threeHours;  // Rain volume for last 3 hours (mm)
    }
    
    /**
     * Nested class for system data
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sys {
        private Integer type;    // Internal parameter
        private Long id;         // Internal parameter
        private String country;  // Country code (e.g., "US", "IN")
        private Long sunrise;    // Sunrise time (Unix timestamp)
        private Long sunset;     // Sunset time (Unix timestamp)
    }
}

// Made with Bob
