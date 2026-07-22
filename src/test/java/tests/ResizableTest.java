package tests;

import base.BaseTest;
import org.openqa.selenium.Dimension;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ResizablePage;
import testUtils.Log;

public class ResizableTest extends BaseTest {

    // Added synchronization helper for resize animations in CI
    private void waitForDOMToSettle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1, description = "Verify resizing of a box with max/min constraints")
    public void testResizableBoxWithRestriction() {
        Log.info("Starting testResizableBoxWithRestriction...");
        ResizablePage resizablePage = new ResizablePage(getDriver());
        
        resizablePage.navigateToResizablePage();
        
        Dimension initialSize = resizablePage.getRestrictedBoxSize();
        Log.info("Initial size: " + initialSize);
        
        resizablePage.resizeRestrictedBox(100, 50);
        
        waitForDOMToSettle();
        Dimension newSize = resizablePage.getRestrictedBoxSize();
        Log.info("New size: " + newSize);
        
        Assert.assertTrue(newSize.getWidth() > initialSize.getWidth(), "The restricted box width did not increase as expected.");
        Assert.assertTrue(newSize.getHeight() > initialSize.getHeight(), "The restricted box height did not increase as expected.");
        Log.info("testResizableBoxWithRestriction completed successfully.");
    }

    @Test(priority = 2, description = "Verify free resizing of an unrestricted box")
    public void testResizableBoxWithoutRestriction() {
        Log.info("Starting testResizableBoxWithoutRestriction...");
        ResizablePage resizablePage = new ResizablePage(getDriver());
        
        resizablePage.navigateToResizablePage();
        
        Dimension initialSize = resizablePage.getUnrestrictedBoxSize();
        Log.info("Initial size: " + initialSize);
        
        resizablePage.resizeUnrestrictedBox(150, 150);
        
        waitForDOMToSettle(); 
        Dimension newSize = resizablePage.getUnrestrictedBoxSize();
        Log.info("New size: " + newSize);
        
        Assert.assertTrue(newSize.getWidth() > initialSize.getWidth(), "The unrestricted box width did not increase.");
        Assert.assertTrue(newSize.getHeight() > initialSize.getHeight(), "The unrestricted box height did not increase.");
        Log.info("testResizableBoxWithoutRestriction completed successfully.");
    }
}