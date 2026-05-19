# ⚡ Quick Start Guide - Run in 3 Steps!

## 🎯 Prerequisites
- Java 21 installed
- OpenWeatherMap API key (free from https://openweathermap.org/api)

---

## 📝 Step 1: Add Your API Key

1. Open file: `src/main/resources/application.properties`

2. Find this line:
   ```properties
   openweathermap.api.key=YOUR_API_KEY_HERE
   ```

3. Replace `YOUR_API_KEY_HERE` with your actual API key:
   ```properties
   openweathermap.api.key=your_actual_api_key_here
   ```

4. Save the file

**Don't have an API key?**
- Go to: https://openweathermap.org/api
- Sign up (free)
- Get your API key
- Wait 10-15 minutes for activation

---

## 🚀 Step 2: Run the Application

### Option A: Double-click the batch file (Easiest!)
Simply double-click: **`RUN_APPLICATION.bat`**

### Option B: Use Command Prompt
```cmd
cd C:\Users\BabluPatel\Desktop\WeatherForecast
mvnw.cmd spring-boot:run
```

### Option C: Use PowerShell
```powershell
cd C:\Users\BabluPatel\Desktop\WeatherForecast
.\mvnw.cmd spring-boot:run
```

**Wait for this message:**
```
=======================================================
   Weather Forecast API Started Successfully!         
=======================================================
   Server running on: http://localhost:8080           
```

---

## 🧪 Step 3: Test the API

### Method 1: Use Browser (Simplest!)
Open your browser and go to:
```
http://localhost:8080/weather/London
```

You should see JSON response like:
```json
{
  "city": "London",
  "temperature": 15.5,
  "humidity": 72,
  "weatherDescription": "light rain",
  "windSpeed": 4.5
}
```

### Method 2: Use Test Script
Double-click: **`TEST_API.bat`**

This will automatically test multiple cities!

### Method 3: Use PowerShell
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/weather/London" -Method Get
```

### Method 4: Use Command Prompt
```cmd
curl http://localhost:8080/weather/London
```

---

## 🌍 Try Different Cities

```
http://localhost:8080/weather/London
http://localhost:8080/weather/Paris
http://localhost:8080/weather/Tokyo
http://localhost:8080/weather/Mumbai
http://localhost:8080/weather/New%20York
```

**Note:** For cities with spaces, use `%20` (e.g., `New%20York`)

---

## 🎉 Success Indicators

✅ **Application Started:** You see the success banner in console
✅ **API Working:** Browser shows JSON weather data
✅ **No Errors:** No red error messages in console

---

## ❌ Common Issues

### Issue 1: "Port 8080 already in use"
**Solution:** Change port in `application.properties`:
```properties
server.port=8081
```
Then use: `http://localhost:8081/weather/London`

### Issue 2: "Invalid API key"
**Solutions:**
- Wait 10-15 minutes (new keys need activation)
- Check for typos in your API key
- Make sure no spaces before/after the key

### Issue 3: "City not found"
**Solutions:**
- Check spelling
- Try major cities first (London, Paris, Tokyo)
- Use `%20` for spaces in city names

### Issue 4: "Java not found"
**Solution:** Install Java 21 from:
https://www.oracle.com/java/technologies/downloads/#java21

---

## 🛑 Stop the Application

Press `Ctrl + C` in the terminal/command prompt

---

## 📚 Need More Help?

- **Detailed Setup:** See `SETUP_GUIDE.md`
- **API Documentation:** See `API_DOCUMENTATION.md`
- **Project Overview:** See `README.md`

---

## 🎯 Quick Test Checklist

- [ ] Java 21 installed (`java -version`)
- [ ] API key added to `application.properties`
- [ ] Application started (see success banner)
- [ ] Browser test works (`http://localhost:8080/weather/London`)
- [ ] JSON response received

**All checked? Congratulations! Your API is working! 🎊**

---

## 💡 Pro Tips

1. **Keep terminal open** - Don't close it while testing
2. **Wait for startup** - First run takes 30-60 seconds
3. **Check logs** - Console shows helpful error messages
4. **Test health first** - `http://localhost:8080/weather/health`
5. **Use Postman** - Better for API testing than browser

---

## 🔥 Ready to Code?

Now that it's working, explore the code:
- `WeatherController.java` - REST endpoints
- `WeatherService.java` - Business logic
- `WeatherResponse.java` - Response model

**Happy Coding! 🚀**