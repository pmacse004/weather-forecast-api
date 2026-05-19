package com.weather.forecast.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Configuration class for WebClient.
 * This class configures the HTTP client used to make API calls.
 * 
 * @Configuration - Indicates that this class contains Spring Bean definitions
 * 
 * @author Weather Forecast Team
 */
@Configuration
public class WebClientConfig {
    
    /**
     * Creates and configures a WebClient.Builder bean.
     * WebClient is a modern, non-blocking HTTP client for making API calls.
     * 
     * This configuration includes:
     * - Connection timeout: 10 seconds
     * - Read timeout: 10 seconds
     * - Write timeout: 10 seconds
     * - Response timeout: 10 seconds
     * 
     * @Bean - Indicates that this method produces a bean to be managed by Spring
     * 
     * @return WebClient.Builder - Configured WebClient builder
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        
        // Configure HTTP client with timeouts
        HttpClient httpClient = HttpClient.create()
                // Connection timeout - time to establish connection
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                // Response timeout - maximum time to wait for response
                .responseTimeout(Duration.ofSeconds(10))
                // Configure read and write timeouts
                .doOnConnected(conn -> conn
                        // Read timeout - time to wait for data
                        .addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS))
                        // Write timeout - time to wait for write operation
                        .addHandlerLast(new WriteTimeoutHandler(10, TimeUnit.SECONDS))
                );
        
        // Create and return WebClient.Builder with configured HTTP client
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient));
    }
}

// Made with Bob
