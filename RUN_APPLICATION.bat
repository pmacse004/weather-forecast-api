@echo off
echo ========================================
echo Weather Forecast API - Quick Start
echo ========================================
echo.

REM Check if API key is configured
findstr /C:"YOUR_API_KEY_HERE" src\main\resources\application.properties >nul
if %errorlevel% equ 0 (
    echo [ERROR] API Key not configured!
    echo.
    echo Please follow these steps:
    echo 1. Open: src\main\resources\application.properties
    echo 2. Replace YOUR_API_KEY_HERE with your actual OpenWeatherMap API key
    echo 3. Save the file
    echo 4. Run this script again
    echo.
    echo Don't have an API key? Get one free at: https://openweathermap.org/api
    echo.
    pause
    exit /b 1
)

echo [INFO] API Key configured - Starting application...
echo.
echo Please wait while the application starts...
echo This may take 30-60 seconds on first run...
echo.

REM Run the application
call mvnw.cmd spring-boot:run

pause

@REM Made with Bob
