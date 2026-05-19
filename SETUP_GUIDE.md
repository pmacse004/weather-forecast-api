# 🚀 Complete Setup Guide - Weather Forecast API

This guide will walk you through setting up and running the Weather Forecast API step-by-step, even if you're a complete beginner.

## 📋 Table of Contents
1. [Prerequisites Installation](#prerequisites-installation)
2. [Getting OpenWeatherMap API Key](#getting-openweathermap-api-key)
3. [Project Setup](#project-setup)
4. [Running the Application](#running-the-application)
5. [Testing the API](#testing-the-api)
6. [Troubleshooting](#troubleshooting)

---

## 1. Prerequisites Installation

### Step 1.1: Install Java 21

#### Windows:
1. Go to https://www.oracle.com/java/technologies/downloads/#java21
2. Download "Windows x64 Installer"
3. Run the installer and follow the wizard
4. After installation, open Command Prompt and verify:
   ```cmd
   java -version
   ```
   You should see: `java version "21.x.x"`

#### Mac:
1. Download Java 21 from the Oracle website
2. Or use Homebrew:
   ```bash
   brew install openjdk@21
   ```
3. Verify installation:
   ```bash
   java -version
   ```

#### Linux:
```bash
sudo apt update
sudo apt install openjdk-21-jdk
java -version
```

### Step 1.2: Install Maven (Optional - Project includes Maven Wrapper)

The project includes Maven Wrapper, so you don't need to install Maven separately. However, if you want to install it:

#### Windows:
1. Download from https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Apache\maven`
3. Add to PATH environment variable
4. Verify: `mvn -version`

#### Mac:
```bash
brew install maven
mvn -version
```

#### Linux:
```bash
sudo apt install maven
mvn -version
```

---

## 2. Getting OpenWeatherMap API Key

### Step 2.1: Create Account
1. Go to https://openweathermap.org/
2. Click "Sign Up" in the top right corner
3. Fill in your details:
   - Username
   - Email
   - Password
4. Verify your email address

### Step 2.2: Get API Key
1. After login, click on your username (top right)
2. Select "My API keys"
3. You'll see a default API key already created
4. Copy this API key (it looks like: `abc123def456ghi789jkl012mno345pq`)
5. **IMPORTANT**: New API keys take 10-15 minutes to activate

### Step 2.3: Test Your API Key
Wait 10-15 minutes after creating your account, then test your API key:

Open this URL in your browser (replace YOUR_API_KEY):
```
https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY
```

If you see JSON data, your API key is working! ✅

---

## 3. Project Setup

### Step 3.1: Navigate to Project Directory

Open Command Prompt or Terminal and navigate to the project:

```bash
cd C:\Users\BabluPatel\Desktop\WeatherForecast
```

### Step 3.2: Configure API Key

1. Open the file: `src/main/resources/application.properties`
2. Find this line:
   ```properties
   openweathermap.api.key=YOUR_API_KEY_HERE
   ```
3. Replace `YOUR_API_KEY_HERE` with your actual API key:
   ```properties
   openweathermap.api.key=your_actual_api_key_here
   ```
4. Save the file

### Step 3.3: Verify Project Structure

Your project should look like this:
```
WeatherForecast/
├── .mvn/
│   └── wrapper/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/weather/forecast/
│       └── resources/
│           └── application.properties
├── pom.xml
├── README.md
└── SETUP_GUIDE.md (this file)
```

---

## 4. Running the Application

### Method 1: Using Maven Wrapper (Recommended - No Maven Installation Required)

#### Windows:
```cmd
mvnw.cmd clean install
mvnw.cmd spring-boot:run
```

#### Mac/Linux:
```bash
./mvnw clean install
./mvnw spring-boot:run
```

### Method 2: Using Installed Maven

```bash
mvn clean install
mvn spring-boot:run
```

### Method 3: Using Java Directly

```bash
# First, build the project
mvnw.cmd clean package

# Then run the JAR file
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

**The application is now running!** 🎉

---

## 5. Testing the API

### Test 1: Health Check (Simplest Test)

Open your web browser and go to:
```
http://localhost:8080/weather/health
```

You should see:
```
Weather Forecast API is running successfully!
```

### Test 2: Get Weather Data

#### Using Web Browser:
```
http://localhost:8080/weather/London
```

#### Using Command Prompt (Windows):
```cmd
curl http://localhost:8080/weather/London
```

#### Using PowerShell (Windows):
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/weather/London" -Method Get
```

#### Using Terminal (Mac/Linux):
```bash
curl http://localhost:8080/weather/London
```

### Expected Response:
```json
{
  "city": "London",
  "temperature": 15.5,
  "humidity": 72,
  "weatherDescription": "light rain",
  "windSpeed": 4.5
}
```

### Test 3: Try Different Cities

```
http://localhost:8080/weather/Paris
http://localhost:8080/weather/Tokyo
http://localhost:8080/weather/Mumbai
http://localhost:8080/weather/New%20York
```

**Note**: For cities with spaces, use `%20` instead of space (e.g., `New%20York`)

---

## 6. Testing with Postman

### Step 6.1: Install Postman
1. Download from https://www.postman.com/downloads/
2. Install and open Postman

### Step 6.2: Create a Request
1. Click "New" → "HTTP Request"
2. Set method to "GET"
3. Enter URL: `http://localhost:8080/weather/London`
4. Click "Send"
5. View the JSON response in the bottom panel

### Step 6.3: Save Your Request
1. Click "Save"
2. Create a new collection: "Weather API"
3. Save the request as "Get Weather by City"

---

## 7. Troubleshooting

### Problem 1: "Port 8080 already in use"

**Solution 1**: Change the port in `application.properties`:
```properties
server.port=8081
```

**Solution 2**: Stop the application using port 8080:

Windows:
```cmd
netstat -ano | findstr :8080
taskkill /PID <PID_NUMBER> /F
```

Mac/Linux:
```bash
lsof -i :8080
kill -9 <PID_NUMBER>
```

### Problem 2: "Invalid API key"

**Causes**:
- API key not activated yet (wait 10-15 minutes)
- Wrong API key copied
- Extra spaces in the API key

**Solution**:
1. Double-check your API key in `application.properties`
2. Make sure there are no spaces before or after the key
3. Wait 15 minutes if you just created the account
4. Test your API key directly in browser:
   ```
   https://api.openweathermap.org/data/2.5/weather?q=London&appid=YOUR_API_KEY
   ```

### Problem 3: "City not found"

**Solution**:
- Check spelling of city name
- Try major cities first (London, Paris, Tokyo)
- For cities with spaces, use `%20` (e.g., `New%20York`)

### Problem 4: "Java version mismatch"

**Error**: `Unsupported class file major version`

**Solution**:
1. Verify Java version: `java -version`
2. Should show version 21.x.x
3. If not, install Java 21 and set JAVA_HOME

### Problem 5: Maven build fails

**Solution**:
```bash
# Clean everything and rebuild
mvnw.cmd clean install -U

# If still fails, delete target folder and try again
rmdir /s target
mvnw.cmd clean install
```

### Problem 6: Application won't start

**Check**:
1. Is Java 21 installed? `java -version`
2. Is port 8080 free?
3. Is the API key configured correctly?
4. Are there any error messages in the console?

**Common Error Messages**:

| Error | Solution |
|-------|----------|
| "Cannot find or load main class" | Run `mvnw.cmd clean install` |
| "Failed to bind to port 8080" | Change port or kill process using 8080 |
| "Connection refused" | Check if application is running |
| "401 Unauthorized" | Check API key configuration |

---

## 8. Stopping the Application

### In Command Prompt/Terminal:
Press `Ctrl + C`

### If Running in Background:
Windows:
```cmd
taskkill /F /IM java.exe
```

Mac/Linux:
```bash
pkill -f "weather-forecast"
```

---

## 9. Next Steps

Once your API is running successfully:

1. ✅ Test all endpoints
2. ✅ Try different cities
3. ✅ Understand the code structure
4. ✅ Modify and experiment
5. ✅ Add new features

### Suggested Enhancements:
- Add more weather parameters (pressure, visibility)
- Add weather forecast for multiple days
- Add caching to reduce API calls
- Add database to store weather history
- Create a simple web UI

---

## 10. Quick Reference

### Start Application:
```bash
mvnw.cmd spring-boot:run
```

### Test Endpoints:
```
Health: http://localhost:8080/weather/health
Weather: http://localhost:8080/weather/London
Info: http://localhost:8080/weather/info
```

### Stop Application:
```
Ctrl + C
```

---

## 11. Getting Help

If you're still stuck:

1. Check the error message carefully
2. Read the logs in the console
3. Verify all prerequisites are installed
4. Make sure API key is activated
5. Try the troubleshooting steps above

---

## 🎉 Congratulations!

If you've made it this far and your API is working, you've successfully:
- ✅ Set up a Spring Boot application
- ✅ Integrated with an external API
- ✅ Created RESTful endpoints
- ✅ Tested your API

**You're now ready to build more complex applications!** 🚀

---

**Happy Coding!** 💻