//package base;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.edge.EdgeDriver;
//import org.openqa.selenium.edge.EdgeOptions;
//import org.openqa.selenium.firefox.FirefoxDriver;
//import org.openqa.selenium.firefox.FirefoxOptions;
//import testUtils.ConfigReader;
//
//import java.time.Duration;
//
//public class DriverFactory {
//
//    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
//
//    public static WebDriver initDriver() {
//
//        // 1. Check System Properties first (for Jenkins/Maven CLI), fallback to config.properties
//        String sysBrowser = System.getProperty("browser");
//        String browser = (sysBrowser != null && !sysBrowser.isEmpty()) ? sysBrowser.toLowerCase() : ConfigReader.getProperty("browser").toLowerCase();
//
//        String sysHeadless = System.getProperty("headless");
//        boolean isHeadless = (sysHeadless != null && !sysHeadless.isEmpty()) ? Boolean.parseBoolean(sysHeadless) : Boolean.parseBoolean(ConfigReader.getProperty("headless"));
//        
//        switch (browser) {
//
//            case "chrome":
//                ChromeOptions chromeOptions = new ChromeOptions();
//
//                // Standard CI/CD stability flags
//                chromeOptions.addArguments("--no-sandbox");
//                chromeOptions.addArguments("--disable-dev-shm-usage");
//                chromeOptions.addArguments("--remote-allow-origins=*");
//                chromeOptions.addArguments("--remote-debugging-pipe"); // Bypass file system
//                chromeOptions.addArguments("--disable-extensions"); 
//
//                if (isHeadless) {
//                    chromeOptions.addArguments("--headless=new"); 
//                    chromeOptions.addArguments("--disable-gpu"); 
//                    chromeOptions.addArguments("--window-size=1920,1080");
//                } else {
//                    chromeOptions.addArguments("--start-maximized");
//                }
//
//                tlDriver.set(new ChromeDriver(chromeOptions));
//                break;
//                
//            case "edge":
//                EdgeOptions edgeOptions = new EdgeOptions();
//
//                edgeOptions.addArguments("--disable-dev-shm-usage");
//                edgeOptions.addArguments("--no-sandbox");
//                edgeOptions.addArguments("--remote-allow-origins=*");
//                edgeOptions.addArguments("--remote-debugging-pipe");
//                edgeOptions.addArguments("--disable-extensions");
//
//                if (isHeadless) {
//                    // ✅ FIXED: Using classic headless to stop white ghost windows in Edge
//                    edgeOptions.addArguments("--headless"); 
//                    edgeOptions.addArguments("--disable-gpu"); 
//                    edgeOptions.addArguments("--window-size=1920,1080");
//                } else {
//                    edgeOptions.addArguments("--start-maximized");
//                }
//
//                tlDriver.set(new EdgeDriver(edgeOptions));
//                break;
//
//            case "firefox":
//                FirefoxOptions firefoxOptions = new FirefoxOptions();
//
//                if (isHeadless) {
//                    firefoxOptions.addArguments("--headless");
//                    firefoxOptions.addArguments("--width=1920");
//                    firefoxOptions.addArguments("--height=1080");
//                }
//
//                tlDriver.set(new FirefoxDriver(firefoxOptions));
//                
//                if (!isHeadless) {
//                    tlDriver.get().manage().window().maximize();
//                }
//                break;
//
//            default:
//                throw new RuntimeException("Invalid browser specified: " + browser + ". Please check your config.properties or Maven arguments.");
//        }
//
//        // Common setup
//        getDriver().manage().deleteAllCookies();
//        
//        // Fetch implicit wait securely 
//        String waitTime = ConfigReader.getProperty("implicitWait");
//        int timeout = (waitTime != null && !waitTime.isEmpty() && !waitTime.equals("Property not found")) ? Integer.parseInt(waitTime) : 10;
//        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
//
//        return getDriver();
//    }
//
//    public static WebDriver getDriver() {
//        return tlDriver.get();
//    }
//
//    public static void quitDriver() {
//        if (getDriver() != null) {
//            getDriver().quit();
//            tlDriver.remove(); // CRITICAL: Removes the thread ID to prevent memory leaks during parallel execution
//        }
//    }
//}
package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import testUtils.ConfigReader;

import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initDriver() {

        String sysBrowser = System.getProperty("browser");
        String browser = (sysBrowser != null && !sysBrowser.isEmpty()) ? sysBrowser.toLowerCase() : ConfigReader.getProperty("browser").toLowerCase();

        String sysHeadless = System.getProperty("headless");
        boolean isHeadless = (sysHeadless != null && !sysHeadless.isEmpty()) ? Boolean.parseBoolean(sysHeadless) : Boolean.parseBoolean(ConfigReader.getProperty("headless"));
        
        System.out.println("========== Starting " + browser + " | Headless Mode: " + isHeadless + " ==========");

        switch (browser) {

            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                chromeOptions.addArguments("--remote-debugging-pipe"); 
                chromeOptions.addArguments("--disable-extensions"); 

                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new"); 
                    chromeOptions.addArguments("--disable-gpu"); 
                    chromeOptions.addArguments("--window-size=1920,1080");
                } else {
                    chromeOptions.addArguments("--start-maximized");
                }
                tlDriver.set(new ChromeDriver(chromeOptions));
                break;
                
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-dev-shm-usage");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--remote-allow-origins=*");
                edgeOptions.addArguments("--remote-debugging-pipe");
                edgeOptions.addArguments("--disable-extensions");

                if (isHeadless) {
                    // Classic headless to stop white ghost windows in Edge
                    edgeOptions.addArguments("--headless"); 
                    edgeOptions.addArguments("--disable-gpu"); 
                    edgeOptions.addArguments("--window-size=1920,1080");
                    edgeOptions.addArguments("--disable-software-rasterizer"); 
                    edgeOptions.addArguments("--window-position=-3200,-3200"); 
                } else {
                    edgeOptions.addArguments("--start-maximized");
                }
                tlDriver.set(new EdgeDriver(edgeOptions));
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("--headless");
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                }
                tlDriver.set(new FirefoxDriver(firefoxOptions));
                if (!isHeadless) {
                    tlDriver.get().manage().window().maximize();
                }
                break;

            default:
                throw new RuntimeException("Invalid browser specified: " + browser);
        }

        getDriver().manage().deleteAllCookies();
        String waitTime = ConfigReader.getProperty("implicitWait");
        int timeout = (waitTime != null && !waitTime.isEmpty() && !waitTime.equals("Property not found")) ? Integer.parseInt(waitTime) : 10;
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));

        return getDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove(); 
        }
    }
}