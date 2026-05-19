# 📚 Weather Forecast API Documentation

Complete API documentation for the Weather Forecast REST API.

## Base URL
```
http://localhost:8080
```

---

## 📡 Endpoints

### 1. Get Weather by City

Retrieves current weather information for a specified city.

**Endpoint:** `GET /weather/{city}`

**Parameters:**
| Parameter | Type | Location | Required | Description |
|-----------|------|----------|----------|-------------|
| city | String | Path | Yes | Name of the city |

**Example Requests:**

```bash
# Simple city name
GET http://localhost:8080/weather/London

# City with spaces (URL encoded)
GET http://localhost:8080/weather/New%20York

# Using cURL
curl http://localhost:8080/weather/London

# Using PowerShell
Invoke-RestMethod -Uri "http://localhost:8080/weather/London" -Method Get
```

**Success Response:**

**Code:** `200 OK`

**Content:**
```json
{
  "city": "London",
  "temperature": 15.5,
  "humidity": 72,
  "weatherDescription": "light rain",
  "windSpeed": 4.5
}
```

**Response Fields:**
| Field | Type | Unit | Description |
|-------|------|------|-------------|
| city | String | - | Name of the city |
| temperature | Double | Celsius | Current temperature |
| humidity | Integer | Percentage | Humidity level (0-100) |
| weatherDescription | String | - | Weather condition description |
| windSpeed | Double | m/s | Wind speed in meters per second |

**Error Responses:**

**City Not Found:**
```json
{
  "timestamp": "2024-05-17T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "City not found: XYZ. Please check the city name and try again.",
  "path": "/weather/XYZ"
}
```

**Invalid API Key:**
```json
{
  "timestamp": "2024-05-17T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Invalid API key. Please check your OpenWeatherMap API key configuration.",
  "path": "/weather/London"
}
```

---

### 2. Health Check

Checks if the API is running and accessible.

**Endpoint:** `GET /weather/health`

**Parameters:** None

**Example Request:**
```bash
GET http://localhost:8080/weather/health
```

**Success Response:**

**Code:** `200 OK`

**Content:**
```
Weather Forecast API is running successfully!
```

---

### 3. API Information

Returns information about the API, including version and available endpoints.

**Endpoint:** `GET /weather/info`

**Parameters:** None

**Example Request:**
```bash
GET http://localhost:8080/weather/info
```

**Success Response:**

**Code:** `200 OK`

**Content:**
```json
{
  "name": "Weather Forecast API",
  "version": "1.0.0",
  "description": "REST API to fetch live weather data from OpenWeatherMap",
  "endpoints": {
    "getWeather": "GET /weather/{city}",
    "health": "GET /weather/health",
    "info": "GET /weather/info"
  },
  "example": "GET http://localhost:8080/weather/London"
}
```

---

## 🌍 Supported Cities

The API supports any city name recognized by OpenWeatherMap. Here are some examples:

### Major Cities by Continent

**Europe:**
- London, Paris, Berlin, Rome, Madrid, Amsterdam, Vienna, Prague

**Asia:**
- Tokyo, Mumbai, Delhi, Shanghai, Beijing, Seoul, Bangkok, Singapore

**North America:**
- New York, Los Angeles, Chicago, Toronto, Mexico City, Vancouver

**South America:**
- São Paulo, Buenos Aires, Rio de Janeiro, Lima, Bogotá

**Africa:**
- Cairo, Lagos, Johannesburg, Nairobi, Casablanca

**Oceania:**
- Sydney, Melbourne, Auckland, Brisbane

### City Name Guidelines

1. **Simple names:** Use as-is (e.g., `London`, `Paris`)
2. **Names with spaces:** URL encode spaces as `%20` (e.g., `New%20York`, `Los%20Angeles`)
3. **Special characters:** URL encode special characters
4. **Case insensitive:** `london`, `London`, `LONDON` all work

---

## 📊 Response Data Details

### Temperature
- **Unit:** Celsius (°C)
- **Range:** Typically -50°C to 50°C
- **Precision:** One decimal place
- **Example:** 15.5

### Humidity
- **Unit:** Percentage (%)
- **Range:** 0 to 100
- **Type:** Integer
- **Example:** 72

### Weather Description
- **Type:** String
- **Examples:**
  - "clear sky"
  - "few clouds"
  - "scattered clouds"
  - "broken clouds"
  - "shower rain"
  - "light rain"
  - "thunderstorm"
  - "snow"
  - "mist"

### Wind Speed
- **Unit:** Meters per second (m/s)
- **Range:** 0 to 50+ m/s
- **Precision:** One decimal place
- **Example:** 4.5
- **Conversion:** 1 m/s ≈ 3.6 km/h ≈ 2.24 mph

---

## 🔧 Error Handling

### HTTP Status Codes

| Code | Meaning | Description |
|------|---------|-------------|
| 200 | OK | Request successful |
| 400 | Bad Request | Invalid request parameters |
| 401 | Unauthorized | Invalid API key |
| 404 | Not Found | City not found |
| 500 | Internal Server Error | Server error or API issue |

### Error Response Format

All errors return a JSON object with the following structure:

```json
{
  "timestamp": "2024-05-17T10:30:00",
  "status": 500,
  "error": "Error Type",
  "message": "Detailed error message",
  "path": "/weather/city"
}
```

### Common Error Messages

1. **"City not found"**
   - Cause: Invalid or misspelled city name
   - Solution: Check spelling, try major cities

2. **"Invalid API key"**
   - Cause: Wrong or inactive API key
   - Solution: Verify API key in application.properties

3. **"Failed to fetch weather data"**
   - Cause: Network issue or API timeout
   - Solution: Check internet connection, try again

---

## 🧪 Testing Examples

### Using cURL

```bash
# Basic request
curl http://localhost:8080/weather/London

# With headers
curl -H "Accept: application/json" http://localhost:8080/weather/Paris

# Save response to file
curl http://localhost:8080/weather/Tokyo -o weather.json

# Multiple cities
curl http://localhost:8080/weather/London
curl http://localhost:8080/weather/Paris
curl http://localhost:8080/weather/Tokyo
```

### Using PowerShell

```powershell
# Basic request
Invoke-RestMethod -Uri "http://localhost:8080/weather/London" -Method Get

# Store in variable
$weather = Invoke-RestMethod -Uri "http://localhost:8080/weather/Paris" -Method Get
$weather.temperature

# Multiple cities
$cities = @("London", "Paris", "Tokyo")
foreach ($city in $cities) {
    Invoke-RestMethod -Uri "http://localhost:8080/weather/$city" -Method Get
}
```

### Using JavaScript (Fetch API)

```javascript
// Basic request
fetch('http://localhost:8080/weather/London')
  .then(response => response.json())
  .then(data => console.log(data))
  .catch(error => console.error('Error:', error));

// Async/Await
async function getWeather(city) {
  try {
    const response = await fetch(`http://localhost:8080/weather/${city}`);
    const data = await response.json();
    console.log(data);
  } catch (error) {
    console.error('Error:', error);
  }
}

getWeather('London');
```

### Using Python (requests)

```python
import requests

# Basic request
response = requests.get('http://localhost:8080/weather/London')
weather = response.json()
print(weather)

# Multiple cities
cities = ['London', 'Paris', 'Tokyo']
for city in cities:
    response = requests.get(f'http://localhost:8080/weather/{city}')
    weather = response.json()
    print(f"{city}: {weather['temperature']}°C")
```

---

## 📈 Rate Limiting

Currently, there are no rate limits on the API itself. However, the OpenWeatherMap free tier has the following limits:

- **Calls per minute:** 60
- **Calls per day:** 1,000
- **Calls per month:** 1,000,000

**Recommendation:** Implement caching to reduce API calls.

---

## 🔒 Security Notes

1. **API Key:** Never commit your API key to version control
2. **HTTPS:** In production, use HTTPS instead of HTTP
3. **CORS:** Configure CORS if accessing from web browsers
4. **Input Validation:** City names are validated before API calls

---

## 🚀 Performance Tips

1. **Caching:** Cache responses for frequently requested cities
2. **Batch Requests:** If you need multiple cities, consider implementing batch endpoints
3. **Async Calls:** Use async/await for non-blocking requests
4. **Error Handling:** Always handle errors gracefully

---

## 📝 Sample Use Cases

### 1. Weather Dashboard
Display current weather for multiple cities on a dashboard.

### 2. Travel Planning
Check weather conditions before planning trips.

### 3. Mobile App
Integrate weather data into mobile applications.

### 4. IoT Devices
Use weather data for smart home automation.

### 5. Data Analysis
Collect and analyze weather patterns over time.

---

## 🔄 API Versioning

Current Version: **v1.0.0**

Future versions will be documented here.

---

## 📞 Support

For issues or questions:
1. Check the SETUP_GUIDE.md
2. Review the troubleshooting section
3. Verify your API key is active
4. Check application logs

---

## 📄 License

This API is provided for educational purposes.

---

**Last Updated:** May 2024