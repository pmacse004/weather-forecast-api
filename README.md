# Weather Forecast API 🌤️

A production-ready Spring Boot REST API that provides live weather information using the OpenWeatherMap API.

## 📋 Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing the API](#testing-the-api)
- [Project Structure](#project-structure)
- [Error Handling](#error-handling)
- [Troubleshooting](#troubleshooting)

## ✨ Features

- ✅ Fetch live weather data for any city
- ✅ Returns temperature, humidity, weather description, and wind speed
- ✅ Clean and beginner-friendly code with detailed comments
- ✅ Proper exception handling
- ✅ RESTful API design
- ✅ Production-ready architecture
- ✅ Uses modern WebClient for HTTP calls
- ✅ Java 21 and Spring Boot 3.2.5

## 🛠️ Technologies Used

- **Java**: 21
- **Spring Boot**: 3.2.5
- **Maven**: Build tool
- **WebClient**: For HTTP API calls
- **Lombok**: To reduce boilerplate code
- **OpenWeatherMap API**: For live weather data
- **SLF4J**: For logging

## 📦 Prerequisites

Before running this application, ensure you have:

1. **Java 21** installed
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version`

2. **Maven** installed (or use Maven Wrapper included in project)
   - Download from: https://maven.apache.org/download.cgi
   - Verify installation: `mvn -version`

3. **OpenWeatherMap API Key** (Free)
   - Sign up at: https://openweathermap.org/api
   - Get your free API key from the dashboard

## 🚀 Getting Started

### Step 1: Clone or Download the Project

```bash
cd WeatherForecast
```

### Step 2: Get Your OpenWeatherMap API Key

1. Go to https://openweathermap.org/api
2. Click on "Sign Up" (top right)
3. Create a free account
4. After login, go to "API keys" section
5. Copy your API key (it may take a few minutes to activate)

### Step 3: Configure the API Key

Open the file: `src/main/resources/application.properties`

Replace `YOUR_API_KEY_HERE` with your actual API key:

```properties
openweathermap.api.key=your_actual_api_key_here
```

**Example:**
```properties
openweathermap.api.key=your_actual_api_key_here
```

## ⚙️ Configuration

The application uses the following configuration in `application.properties`:

```properties
# Server Configuration
server.port=8080

# OpenWeatherMap API Configuration
openweathermap.api.key=YOUR_API_KEY_HERE
openweathermap.api.url=https://api.openweathermap.org/data/2.5/weather

# Logging Configuration
logging.level.com.weather.forecast=INFO
```

## ▶️ Running the Application

### Option 1: Using Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Option 2: Using Maven Wrapper (Windows)

```bash
.\mvnw.cmd clean install
.\mvnw.cmd spring-boot:run
```

### Option 3: Using Maven Wrapper (Linux/Mac)

```bash
./mvnw clean install
./mvnw spring-boot:run
```

### Option 4: Run as JAR

```bash
mvn clean package
java -jar target/weather-forecast-1.0.0.jar
```

### Successful Startup

When the application starts successfully, you'll see:

```
=======================================================
   Weather Forecast API Started Successfully!         
=======================================================
   Server running on: http://localhost:8080           
   API Endpoint: GET /weather/{city}                  
   Health Check: GET /weather/health                  
   API Info: GET /weather/info                        
=======================================================
```

## 📡 API Endpoints

### 1. Get Weather by City

**Endpoint:** `GET /weather/{city}`

**Description:** Fetches live weather data for the specified city.

**Example Request:**
```
GET http://localhost:8080/weather/London
```

**Example Response:**
```json
{
  "city": "London",
  "temperature": 15.5,
  "humidity": 72,
  "weatherDescription": "light rain",
  "windSpeed": 4.5
}
```

### 2. Health Check

**Endpoint:** `GET /weather/health`

**Description:** Checks if the API is running.

**Example Request:**
```
GET http://localhost:8080/weather/health
```

**Example Response:**
```
Weather Forecast API is running successfully!
```

### 3. API Information

**Endpoint:** `GET /weather/info`

**Description:** Returns API information and usage details.

**Example Request:**
```
GET http://localhost:8080/weather/info
```

## 🧪 Testing the API

### Method 1: Using Web Browser

Simply open your browser and navigate to:
```
http://localhost:8080/weather/London
```

Replace "London" with any city name.

### Method 2: Using Postman

1. Download and install Postman: https://www.postman.com/downloads/
2. Create a new GET request
3. Enter URL: `http://localhost:8080/weather/London`
4. Click "Send"
5. View the JSON response

### Method 3: Using cURL (Command Line)

```bash
curl http://localhost:8080/weather/London
```

### Method 4: Using PowerShell (Windows)

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/weather/London" -Method Get
```

## 📁 Project Structure

```
WeatherForecast/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── weather/
│       │           └── forecast/
│       │               ├── WeatherForecastApplication.java  # Main application class
│       │               ├── controller/
│       │               │   └── WeatherController.java       # REST endpoints
│       │               ├── service/
│       │               │   └── WeatherService.java          # Business logic
│       │               ├── model/
│       │               │   ├── WeatherResponse.java         # Response model
│       │               │   └── OpenWeatherMapResponse.java  # API response model
│       │               ├── config/
│       │               │   └── WebClientConfig.java         # HTTP client config
│       │               └── exception/
│       │                   └── GlobalExceptionHandler.java  # Error handling
│       └── resources/
│           └── application.properties                       # Configuration
├── pom.xml                                                  # Maven dependencies
└── README.md                                                # This file
```

## 🔧 Error Handling

The application includes comprehensive error handling:

### Common Errors and Solutions

1. **City Not Found**
   ```json
   {
     "timestamp": "2024-05-17T10:30:00",
     "status": 500,
     "error": "Internal Server Error",
     "message": "City not found: XYZ. Please check the city name and try again."
   }
   ```
   **Solution:** Check the city name spelling.

2. **Invalid API Key**
   ```json
   {
     "timestamp": "2024-05-17T10:30:00",
     "status": 500,
     "error": "Internal Server Error",
     "message": "Invalid API key. Please check your OpenWeatherMap API key configuration."
   }
   ```
   **Solution:** Verify your API key in `application.properties`.

3. **API Key Not Activated**
   - OpenWeatherMap API keys can take 10-15 minutes to activate after creation.
   - Wait a few minutes and try again.

## 🐛 Troubleshooting

### Issue: Port 8080 Already in Use

**Error Message:**
```
Web server failed to start. Port 8080 was already in use.
```

**Solution 1:** Change the port in `application.properties`:
```properties
server.port=8081
```

**Solution 2:** Stop the application using port 8080:

Windows:
```bash
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

Linux/Mac:
```bash
lsof -i :8080
kill -9 <PID>
```

### Issue: Maven Build Fails

**Solution:**
```bash
mvn clean install -U
```

### Issue: Java Version Mismatch

**Error Message:**
```
Unsupported class file major version
```

**Solution:** Ensure Java 21 is installed and set as default:
```bash
java -version
```

Should show: `java version "21.x.x"`

## 📝 Sample API Requests

### Test Different Cities

```bash
# London, UK
curl http://localhost:8080/weather/London

# New York, USA
curl http://localhost:8080/weather/New%20York

# Tokyo, Japan
curl http://localhost:8080/weather/Tokyo

# Mumbai, India
curl http://localhost:8080/weather/Mumbai

# Paris, France
curl http://localhost:8080/weather/Paris
```

### Expected Response Format

```json
{
  "city": "Mumbai",
  "temperature": 32.5,
  "humidity": 65,
  "weatherDescription": "clear sky",
  "windSpeed": 3.2
}
```

**Note:** 
- Temperature is in Celsius
- Wind speed is in meters per second
- Humidity is in percentage

## 🎓 Learning Resources

### Understanding the Code

1. **Controller Layer** (`WeatherController.java`)
   - Handles HTTP requests
   - Maps URLs to methods
   - Returns JSON responses

2. **Service Layer** (`WeatherService.java`)
   - Contains business logic
   - Calls external APIs
   - Processes data

3. **Model Layer** (`WeatherResponse.java`, `OpenWeatherMapResponse.java`)
   - Defines data structures
   - Maps JSON to Java objects

4. **Configuration** (`WebClientConfig.java`)
   - Configures HTTP client
   - Sets timeouts

5. **Exception Handling** (`GlobalExceptionHandler.java`)
   - Catches errors
   - Returns user-friendly messages

## 🤝 Contributing

This is a beginner-friendly project. Feel free to:
- Report bugs
- Suggest improvements
- Add new features
- Improve documentation

## 📄 License

This project is open-source and available for educational purposes.

## 📞 Support

If you encounter any issues:
1. Check the [Troubleshooting](#troubleshooting) section
2. Verify your API key is correct and activated
3. Ensure Java 21 and Maven are properly installed
4. Check application logs for detailed error messages

## 🎉 Success!

If you see weather data when you access `http://localhost:8080/weather/London`, congratulations! Your Weather Forecast API is working perfectly! 🎊

---

**Happy Coding! 💻**