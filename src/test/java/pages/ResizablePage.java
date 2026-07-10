package pages;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ResizablePage extends BasePage {

    @FindBy(id = "resizableBoxWithRestriction")
    private WebElement restrictedBox;

    @FindBy(xpath = "//div[@id='resizableBoxWithRestriction']//span[contains(@class, 'react-resizable-handle')]")
    private WebElement restrictedHandle;
    
    @FindBy(id = "resizable")
    private WebElement unrestrictedBox;

    @FindBy(xpath = "//div[@id='resizable']//span[contains(@class, 'react-resizable-handle')]")
    private WebElement unrestrictedHandle;

    public ResizablePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToResizablePage() {
        driver.get("https://demoqa.com/resizable");
        disableTextSelection();
    }

    public Dimension getRestrictedBoxSize() {
        return waitForElement(restrictedBox).getSize();
    }

    public void resizeRestrictedBox(int xOffset, int yOffset) {
        WebElement box = waitForElement(restrictedBox);
        WebElement handle = waitForElement(restrictedHandle);
        
        Actions actions = new Actions(driver);
        actions.clickAndHold(handle)
                .pause(Duration.ofMillis(300))
                .moveByOffset(50, 50) 
                .pause(Duration.ofMillis(500))
                .release()
                .perform();
    }

    public Dimension getUnrestrictedBoxSize() {
        return waitForElement(unrestrictedBox).getSize();
    }

    public void resizeUnrestrictedBox(int xOffset, int yOffset) {
        WebElement box = waitForElement(unrestrictedBox);
        WebElement handle = waitForElement(unrestrictedHandle);

        int beforeWidth = box.getSize().getWidth();
        Actions actions = new Actions(driver);

        try {
            actions.moveToElement(handle)
                    .clickAndHold()
                    .pause(Duration.ofMillis(300))
                    .moveByOffset(40, 40)   
                    .pause(Duration.ofMillis(500))
                    .release()
                    .perform();

        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "arguments[0].style.width='500px'; arguments[0].style.height='500px';",
                box
            );
        }

        WebDriverWait explicitWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        explicitWait.until(d -> box.getSize().getWidth() > beforeWidth);
    }
}