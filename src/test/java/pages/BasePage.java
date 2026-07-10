package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

// Importing from your custom JAR
import com.automation.utils.ui.WebActions;
import com.automation.utils.js.JSExecutorUtils;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Uses WebActions from qa-utils to wait and click
     */
    protected void clickElement(WebElement element) {
        WebActions.click(driver, element, 15);
    }

    /**
     * Uses WebActions from qa-utils to wait for visibility
     */
    protected WebElement waitForElement(WebElement element) {
        return WebActions.waitForVisibility(driver, element, 15);
    }

    /**
     * Uses WebActions from qa-utils to perform drag and drop
     */
    protected void performDragAndDrop(WebElement source, WebElement target) {
        WebActions.dragAndDrop(driver, source, target, 15);
    }

    /**
     * Uses JSExecutorUtils from qa-utils
     */
    protected void clickUsingJS(WebElement element) {
        JSExecutorUtils.clickByJS(driver, element);
    }

    /**
     * Uses JSExecutorUtils from qa-utils
     */
    protected void disableTextSelection() {
        JSExecutorUtils.disableTextSelection(driver);
    }
}