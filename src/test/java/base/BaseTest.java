package base;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    
    @BeforeMethod
    public void setUp() {
        DriverFactory.initDriver();
    }

    public WebDriver getDriver() {
        return DriverFactory.getDriver(); //getting the curr driver
    }

    public String captureBase64Screenshot() {
        try {
            ((JavascriptExecutor) getDriver()).executeScript("window.scrollTo(0, 0);");

            return ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BASE64);
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}