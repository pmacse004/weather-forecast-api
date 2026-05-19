@echo off
echo ========================================
echo Weather Forecast API - Test Script
echo ========================================
echo.
echo Make sure the application is running first!
echo.
echo Testing API endpoints...
echo.

echo [1] Testing Health Check...
curl -s http://localhost:8080/weather/health
echo.
echo.

echo [2] Testing API Info...
curl -s http://localhost:8080/weather/info
echo.
echo.

echo [3] Testing Weather for London...
curl -s http://localhost:8080/weather/London
echo.
echo.

echo [4] Testing Weather for Paris...
curl -s http://localhost:8080/weather/Paris
echo.
echo.

echo [5] Testing Weather for Tokyo...
curl -s http://localhost:8080/weather/Tokyo
echo.
echo.

echo ========================================
echo Testing Complete!
echo ========================================
echo.
echo You can also test in browser:
echo - http://localhost:8080/weather/London
echo - http://localhost:8080/weather/Paris
echo - http://localhost:8080/weather/Tokyo
echo.
pause

@REM Made with Bob
