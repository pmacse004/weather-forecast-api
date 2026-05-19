package com.weather.forecast.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Model class for OpenWeatherMap One Call API 3.0 response
 * Provides 8-day daily forecast
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OneCallResponse {
    
    private Double lat;
    private Double lon;
    private String timezone;
    
    @JsonProperty("timezone_offset")
    private Integer timezoneOffset;
    
    private List<DailyForecast> daily;
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DailyForecast {
        private Long dt;
        private Long sunrise;
        private Long sunset;
        private Long moonrise;
        private Long moonset;
        
        @JsonProperty("moon_phase")
        private Double moonPhase;
        
        private String summary;
        private Temp temp;
        
        @JsonProperty("feels_like")
        private FeelsLike feelsLike;
        
        private Integer pressure;
        private Integer humidity;
        
        @JsonProperty("dew_point")
        private Double dewPoint;
        
        @JsonProperty("wind_speed")
        private Double windSpeed;
        
        @JsonProperty("wind_deg")
        private Integer windDeg;
        
        @JsonProperty("wind_gust")
        private Double windGust;
        
        private List<Weather> weather;
        private Integer clouds;
        private Double pop; // Probability of precipitation
        private Double rain;
        private Double snow;
        private Double uvi;
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Temp {
        private Double day;
        private Double min;
        private Double max;
        private Double night;
        private Double eve;
        private Double morn;
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeelsLike {
        private Double day;
        private Double night;
        private Double eve;
        private Double morn;
    }
    
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }
}

// Made with Bob
