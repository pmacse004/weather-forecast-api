# Free Weather APIs with 7+ Day Forecast

## Recommended Free Alternatives

### 1. **Open-Meteo** ⭐ BEST OPTION
- **Website:** https://open-meteo.com/
- **Forecast Days:** Up to 16 days FREE
- **No API Key Required:** ✅ Yes
- **Rate Limit:** 10,000 requests/day (free)
- **Features:**
  - Temperature (min/max)
  - Weather codes
  - Precipitation probability
  - Wind speed
  - Humidity
  - UV Index
- **API Example:**
  ```
  https://api.open-meteo.com/v1/forecast?latitude=51.5074&longitude=-0.1278&daily=temperature_2m_max,temperature_2m_min,precipitation_probability_max,weathercode,windspeed_10m_max&timezone=auto
  ```
- **Pros:**
  - No API key needed
  - Very generous free tier
  - Excellent documentation
  - Fast and reliable
  - Open source

### 2. **WeatherAPI.com**
- **Website:** https://www.weatherapi.com/
- **Forecast Days:** 3 days (free), 14 days (paid)
- **API Key Required:** ✅ Yes (free signup)
- **Rate Limit:** 1 million calls/month (free)
- **Features:**
  - Current weather
  - 3-day forecast (free)
  - Astronomy data
  - Air quality
- **API Example:**
  ```
  http://api.weatherapi.com/v1/forecast.json?key=YOUR_KEY&q=London&days=3
  ```
- **Pros:**
  - Easy to use
  - Good free tier
  - Detailed data
- **Cons:**
  - Only 3 days free (not 7)

### 3. **Tomorrow.io (formerly ClimaCell)**
- **Website:** https://www.tomorrow.io/
- **Forecast Days:** 5 days (free)
- **API Key Required:** ✅ Yes (free signup)
- **Rate Limit:** 500 calls/day, 25 calls/hour (free)
- **Features:**
  - Minute-by-minute precipitation
  - Hourly and daily forecasts
  - Weather insights
- **Cons:**
  - Only 5 days free

### 4. **Visual Crossing Weather**
- **Website:** https://www.visualcrossing.com/
- **Forecast Days:** 15 days FREE
- **API Key Required:** ✅ Yes (free signup)
- **Rate Limit:** 1000 records/day (free)
- **Features:**
  - Historical weather
  - 15-day forecast
  - Weather alerts
- **API Example:**
  ```
  https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/London?key=YOUR_KEY
  ```
- **Pros:**
  - 15-day forecast
  - Historical data
  - Good free tier

### 5. **7Timer!**
- **Website:** http://www.7timer.info/
- **Forecast Days:** 7 days FREE
- **No API Key Required:** ✅ Yes
- **Rate Limit:** Reasonable (not specified)
- **Features:**
  - 7-day forecast
  - Multiple models
  - Simple JSON/XML output
- **API Example:**
  ```
  http://www.7timer.info/bin/api.pl?lon=-0.1278&lat=51.5074&product=civil&output=json
  ```
- **Pros:**
  - No API key
  - Free 7-day forecast
- **Cons:**
  - Less detailed than others
  - Simpler interface

## 🏆 RECOMMENDED SOLUTION: Open-Meteo

**Why Open-Meteo is the best choice:**

1. ✅ **16-day forecast** (more than you need)
2. ✅ **No API key required** (easier setup)
3. ✅ **10,000 requests/day** (very generous)
4. ✅ **Excellent documentation**
5. ✅ **Fast and reliable**
6. ✅ **Open source**
7. ✅ **All features you need:**
   - Temperature (min/max)
   - Weather conditions
   - Precipitation probability
   - Wind speed
   - Humidity
   - UV Index

## Implementation Plan for Open-Meteo

### Step 1: Update application.properties
```properties
# Open-Meteo API (no key required)
openmeteo.api.url=https://api.open-meteo.com/v1/forecast
openmeteo.geocoding.url=https://geocoding-api.open-meteo.com/v1/search
```

### Step 2: Create OpenMeteo Response Models
- `OpenMeteoResponse.java`
- `OpenMeteoGeocodingResponse.java`

### Step 3: Update WeatherService
- Add method to get coordinates from city name (geocoding)
- Update `get7DayForecast()` to use Open-Meteo API
- Parse Open-Meteo response format

### Step 4: Benefits
- Get full 7 days (or even 16 days if you want)
- No API key management
- Better rate limits
- More reliable

## Comparison Table

| API | Free Days | API Key | Rate Limit | Best For |
|-----|-----------|---------|------------|----------|
| **Open-Meteo** | **16** | **No** | **10k/day** | **Best overall** |
| Visual Crossing | 15 | Yes | 1k/day | Good alternative |
| 7Timer! | 7 | No | Reasonable | Simple needs |
| WeatherAPI.com | 3 | Yes | 1M/month | Current weather |
| Tomorrow.io | 5 | Yes | 500/day | Minute forecasts |
| OpenWeatherMap | 5 | Yes | 1k/day | Current choice |

## Next Steps

Would you like me to:
1. **Implement Open-Meteo API** (recommended) - Get 16-day forecast for free
2. **Implement Visual Crossing** - Get 15-day forecast
3. **Keep OpenWeatherMap** - Accept 5-day limitation

Let me know your preference and I'll implement it!