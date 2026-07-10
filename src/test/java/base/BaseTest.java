package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    
    // ❌ REMOVED: protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    // Reason: DriverFactory already manages the ThreadLocal securely. 
    // Duplicating it here causes memory leaks and parallel execution collisions.

    @BeforeMethod
    public void setUp() {
        // DriverFactory spins up the browser and stores it in its own ThreadLocal
        DriverFactory.initDriver();
    }

    public WebDriver getDriver() {
        // Always fetch the thread-safe instance directly from the Factory
        return DriverFactory.getDriver();
    }

    public String captureBase64Screenshot() {
        try {
            // Scroll to top to ensure a clean screenshot
            ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 0);");

            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    @AfterMethod
    public void tearDown() {
        // Delegate teardown to the Factory so it can safely remove the ThreadLocal ID
        DriverFactory.quitDriver();
    }
}