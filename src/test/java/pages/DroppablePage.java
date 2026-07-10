//package pages;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Point;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//
//// Importing from the reusable JAR
//import com.automation.utils.js.JSExecutorUtils;
//
//public class DroppablePage extends BasePage {
//
//    @FindBy(css = "#simpleDropContainer #draggable")
//    private WebElement simpleSource;
//
//    @FindBy(css = "#simpleDropContainer #droppable")
//    private WebElement simpleTarget;
//
//    @FindBy(id = "droppableExample-tab-accept")
//    private WebElement acceptTab;
//
//    @FindBy(id = "acceptable")
//    private WebElement acceptableSource;
//
//    @FindBy(id = "notAcceptable")   
//    private WebElement notAcceptableSource;
//
//    @FindBy(id = "droppableExample-tab-preventPropogation")
//    private WebElement preventTab;
//
//    @FindBy(id = "dragBox")
//    private WebElement preventSource;
//
//    @FindBy(id = "notGreedyInnerDropBox")
//    private WebElement notGreedyInner;
//
//    @FindBy(id = "notGreedyDropBox")
//    private WebElement notGreedyOuter;
//
//    @FindBy(id = "greedyDropBoxInner")
//    private WebElement greedyInner;
//
//    @FindBy(id = "greedyDropBox")
//    private WebElement greedyOuter;
//
//    @FindBy(id = "droppableExample-tab-revertable")
//    private WebElement revertTab;
//
//    @FindBy(id = "revertable")
//    private WebElement willRevertSource;
//
//    @FindBy(id = "notRevertable")
//    private WebElement notRevertSource;
//
//    public DroppablePage(WebDriver driver) {
//        super(driver);
//    }
//
//    public void navigateToDroppablePage() {
//        driver.get("https://demoqa.com/droppable");
//        driver.navigate().refresh();
//        disableTextSelection();
//    }
//
//    public void dragSimpleBox() {
//        try {
//            performDragAndDrop(simpleSource, simpleTarget);
//        } catch (Exception e) {
//            JSExecutorUtils.dragAndDropJS(driver, simpleSource, simpleTarget); 
//        }
//    }
//
//    public String getSimpleTargetText() {
//        return waitForElement(simpleTarget).getText();
//    }
//
//    public void clickAcceptTab() {
//        clickElement(acceptTab);
//        waitForElement(driver.findElement(By.id("acceptDropContainer")));
//        try { Thread.sleep(500); } catch (Exception e) {} // React UI render buffer
//    }
//
//    private WebElement getAcceptTarget() {
//        return waitForElement(driver.findElement(By.cssSelector("#acceptDropContainer #droppable")));
//    }
//
//    public void dragNotAcceptableBox() {
//        try {
//            performDragAndDrop(notAcceptableSource, getAcceptTarget());
//        } catch (Exception e) {
//            JSExecutorUtils.dragAndDropJS(driver, notAcceptableSource, getAcceptTarget());
//        }
//    }
//
//    public void dragAcceptableBox() {
//        try {
//            performDragAndDrop(acceptableSource, getAcceptTarget());
//        } catch (Exception e) {
//            JSExecutorUtils.dragAndDropJS(driver, acceptableSource, getAcceptTarget());
//        }
//    }
//
//    public String getAcceptTargetText() {
//        return getAcceptTarget().getText();
//    }
//
//    public void clickPreventTab() {
//        clickElement(preventTab);
//        waitForElement(preventSource);
//    }
//
//    public void dragToNotGreedyInner() {
//        performDragAndDrop(preventSource, notGreedyInner);
//    }
//
//    public void dragToGreedyInner() {
//        performDragAndDrop(preventSource, greedyInner);
//    }
//
//    public String getNotGreedyInnerClass() {
//        return notGreedyInner.getAttribute("class");
//    }
//
//    public String getNotGreedyOuterClass() {
//        return notGreedyOuter.getAttribute("class");
//    }
//
//    public String getGreedyInnerClass() {
//        return greedyInner.getAttribute("class");
//    }
//
//    public String getGreedyOuterClass() {
//        return greedyOuter.getAttribute("class");
//    }
//
//    public void clickRevertTab() {
//        clickElement(revertTab);
//        waitForElement(willRevertSource);
//    }
//
//    private WebElement getRevertTarget() {
//        return waitForElement(driver.findElement(By.cssSelector("#revertableDropContainer #droppable")));
//    }
//
//    public void dragRevertBox(boolean shouldRevert) {
//        WebElement source = shouldRevert ? willRevertSource : notRevertSource;
//        performDragAndDrop(source, getRevertTarget());
//
//        if (shouldRevert) {
//            try { Thread.sleep(2000); } catch (InterruptedException e) {}
//        }
//    }
//
//    public Point getRevertSourceLocation() {
//        return willRevertSource.getLocation();
//    }
//
//    public Point getNotRevertSourceLocation() {
//        return notRevertSource.getLocation();
//    }
//}
package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import java.time.Duration;

public class DroppablePage extends BasePage {

    @FindBy(css = "#simpleDropContainer #draggable")
    private WebElement simpleSource;

    @FindBy(css = "#simpleDropContainer #droppable")
    private WebElement simpleTarget;

    @FindBy(id = "droppableExample-tab-accept")
    private WebElement acceptTab;

    @FindBy(id = "acceptable")
    private WebElement acceptableSource;

    @FindBy(id = "notAcceptable")   
    private WebElement notAcceptableSource;

    // Fixed dynamic locator for the accept target
    @FindBy(css = "#acceptDropContainer #droppable")
    private WebElement acceptTarget;

    @FindBy(id = "droppableExample-tab-preventPropogation")
    private WebElement preventTab;

    @FindBy(id = "dragBox")
    private WebElement preventSource;

    @FindBy(id = "notGreedyInnerDropBox")
    private WebElement notGreedyInner;

    @FindBy(id = "notGreedyDropBox")
    private WebElement notGreedyOuter;

    @FindBy(id = "greedyDropBoxInner")
    private WebElement greedyInner;

    @FindBy(id = "greedyDropBox")
    private WebElement greedyOuter;

    @FindBy(id = "droppableExample-tab-revertable")
    private WebElement revertTab;

    @FindBy(id = "revertable")
    private WebElement willRevertSource;

    @FindBy(id = "notRevertable")
    private WebElement notRevertSource;

    // Fixed dynamic locator for the revert target
    @FindBy(css = "#revertableDropContainer #droppable")
    private WebElement revertTarget;

    public DroppablePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToDroppablePage() {
        driver.get("https://demoqa.com/droppable");
        disableTextSelection();
    }

    /**
     * Custom, bulletproof Drag and Drop specifically tuned for DemoQA
     */
    private void executeReliableDragAndDrop(WebElement source, WebElement target) {
        // 1. Force the target to the dead-center of the screen. 
        // This guarantees it is NOT hidden under the sticky footer ad or top header.
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", target);
        
        try { Thread.sleep(500); } catch (Exception e) {} // Wait for scroll to settle

        // 2. Perform slow, deliberate actions so React/jQuery registers the event listeners
        Actions actions = new Actions(driver);
        actions.moveToElement(source)
               .pause(Duration.ofMillis(200))
               .clickAndHold(source)
               .pause(Duration.ofMillis(200))
               .moveToElement(target)
               .pause(Duration.ofMillis(200))
               .release(target)
               .build()
               .perform();
    }

    public void dragSimpleBox() {
        executeReliableDragAndDrop(simpleSource, simpleTarget);
    }

    public String getSimpleTargetText() {
        return waitForElement(simpleTarget).getText();
    }

    public void clickAcceptTab() {
        clickElement(acceptTab);
        waitForElement(acceptTarget);
    }

    public void dragNotAcceptableBox() {
        executeReliableDragAndDrop(notAcceptableSource, acceptTarget);
    }

    public void dragAcceptableBox() {
        executeReliableDragAndDrop(acceptableSource, acceptTarget);
    }

    public String getAcceptTargetText() {
        return waitForElement(acceptTarget).getText();
    }

    public void clickPreventTab() {
        clickElement(preventTab);
        waitForElement(preventSource);
    }

    public void dragToNotGreedyInner() {
        executeReliableDragAndDrop(preventSource, notGreedyInner);
    }

    public void dragToGreedyInner() {
        executeReliableDragAndDrop(preventSource, greedyInner);
    }

    public String getNotGreedyInnerClass() {
        return notGreedyInner.getAttribute("class");
    }

    public String getNotGreedyOuterClass() {
        return notGreedyOuter.getAttribute("class");
    }

    public String getGreedyInnerClass() {
        return greedyInner.getAttribute("class");
    }

    public String getGreedyOuterClass() {
        return greedyOuter.getAttribute("class");
    }

    public void clickRevertTab() {
        clickElement(revertTab);
        waitForElement(willRevertSource);
    }

    public void dragRevertBox(boolean shouldRevert) {
        WebElement source = shouldRevert ? willRevertSource : notRevertSource;
        executeReliableDragAndDrop(source, revertTarget);

        if (shouldRevert) {
            // Wait for the DemoQA visual animation to finish snapping back
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
        }
    }

    public Point getRevertSourceLocation() {
        return waitForElement(willRevertSource).getLocation();
    }
    public Point getNotRevertSourceLocation() {
        return waitForElement(notRevertSource).getLocation();
    }
}