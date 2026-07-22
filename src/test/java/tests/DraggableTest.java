package tests;

import base.BaseTest;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DraggablePage;
import testUtils.Log;

public class DraggableTest extends BaseTest {

    // Helper method to fix CI/Headless flakiness. 
    // Gives the DOM's CSS engine time to update coordinates after an Action is performed.
    private void waitForDOMToSettle() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // NEW HELPER: Solves DemoQA specific overlapping ads and zoom issues
    private void prepareDemoQAEnvironment() {
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        // REMOVED: js.executeScript("document.body.style.zoom = '80%'"); - Zooming breaks Selenium's Actions class coordinate calculation on small elements.
        js.executeScript("var fixedBan = document.getElementById('fixedban'); if(fixedBan != null) fixedBan.style.display = 'none';");
        js.executeScript("var footers = document.getElementsByTagName('footer'); if(footers.length > 0) footers[0].style.display = 'none';");
        // NEW: Hide the top header to prevent MoveTargetOutOfBoundsException when dragging UP
        js.executeScript("var headers = document.getElementsByTagName('header'); if(headers.length > 0) headers[0].style.display = 'none';");
    }

    @Test(priority = 1)
    public void testSimpleDraggable() {
        Log.info("Starting testSimpleDraggable: Verifying basic drag functionality.");
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        prepareDemoQAEnvironment();
        
        draggablePage.clickSimpleTab();
        waitForDOMToSettle(); 
        
        Point initialLoc = draggablePage.getSimpleBoxLocation();
        Log.info("Initial Box Location: " + initialLoc);
        
        draggablePage.dragSimpleBox(100, 100);
        
        waitForDOMToSettle(); 
        Point newLoc = draggablePage.getSimpleBoxLocation();
        Log.info("New Box Location: " + newLoc);
        
        Assert.assertTrue(newLoc.getX() > initialLoc.getX(), "The simple box did not move right!");
        Assert.assertTrue(newLoc.getY() > initialLoc.getY(), "The simple box did not move down!");
        Log.info("testSimpleDraggable passed successfully.");
    }

    @Test(priority = 2)
    public void testAxisRestrictedDraggable() {
        Log.info("Starting testAxisRestrictedDraggable: Verifying X/Y axis constraints.");
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        prepareDemoQAEnvironment();
        
        draggablePage.clickAxisTab();
        waitForDOMToSettle(); 

        // --- Test X-Axis Box ---
        Log.info("Testing X-Axis restricted box.");
        Point startLocationX = draggablePage.getRestrictedXBoxLocation();
        draggablePage.dragRestrictedXBox(100, 100); 
        
        waitForDOMToSettle();
        Point endLocationX = draggablePage.getRestrictedXBoxLocation();
        
        Assert.assertTrue(endLocationX.getX() > startLocationX.getX(), 
            "Only X box did not move horizontally!");
            
        Assert.assertTrue(Math.abs(endLocationX.getY() - startLocationX.getY()) <= 5, 
            "Only X box illegally moved vertically!");

        // --- Test Y-Axis Box ---
        Log.info("Testing Y-Axis restricted box.");
        Point startLocationY = draggablePage.getRestrictedYBoxLocation();
        draggablePage.dragRestrictedYBox(100, 100); 
        
        waitForDOMToSettle();
        Point endLocationY = draggablePage.getRestrictedYBoxLocation();
        
        Assert.assertTrue(endLocationY.getY() > startLocationY.getY(), 
            "Only Y box did not move vertically!");
            
        Assert.assertTrue(Math.abs(endLocationY.getX() - startLocationY.getX()) <= 5, 
            "Only Y box illegally moved horizontally!");
            
        Log.info("testAxisRestrictedDraggable passed successfully.");
    }

    @Test(priority = 3)
    public void testCursorStyleDraggable() {
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        prepareDemoQAEnvironment();
        
        draggablePage.clickCursorTab();
        waitForDOMToSettle(); // CRITICAL: Wait for fade-in animation to finish

        // Box 1: Center
        Point centerInitial = draggablePage.getCursorCenterLocation();
        draggablePage.dragCursorCenter(100, 100);
        waitForDOMToSettle();
        Point centerFinal = draggablePage.getCursorCenterLocation();
        Assert.assertNotEquals(centerFinal, centerInitial, "Cursor Center box failed to move!");

        // Box 2: Top Left
        Point topLeftInitial = draggablePage.getCursorTopLeftLocation();
        draggablePage.dragCursorTopLeft(100, 100);
        waitForDOMToSettle();
        Point topLeftFinal = draggablePage.getCursorTopLeftLocation();
        Assert.assertNotEquals(topLeftFinal, topLeftInitial, "Cursor Top Left box failed to move!");

        // Box 3: Bottom
        Point bottomInitial = draggablePage.getCursorBottomLocation();
        draggablePage.dragCursorBottom(100, -100); // Drag UP to avoid screen edge
        waitForDOMToSettle();
        Point bottomFinal = draggablePage.getCursorBottomLocation();
        Assert.assertNotEquals(bottomFinal, bottomInitial, "Cursor Bottom box failed to move!");
    }

    @Test(priority = 4)
    public void testContainerRestrictedElegant() {
        Log.info("Starting testContainerRestrictedElegant: Verifying container boundaries.");
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        prepareDemoQAEnvironment();
        
        draggablePage.clickContainerTab();
        waitForDOMToSettle(); // CRITICAL: Wait for fade-in animation to finish

        // Scroll down gently to ensure the container is fully centered and no top-nav bars interfere
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        // INCREASED SCROLL: Push the element further down into the viewport so dragging UP doesn't throw the mouse out of the browser window.
        js.executeScript("window.scrollBy(0, 400);");
        waitForDOMToSettle();

        // ==========================================
        // SCENARIO 1: Box Constraint (Top-Left Smash)
        // ==========================================
        Log.info("Executing Scenario 1: Parent Container Boundary Smash.");
        Point startBox = draggablePage.getBoxDraggableLocation();

        // Use a much smaller move distance. The box is small; moving 100 might hit the bottom boundary prematurely!
        draggablePage.dragBoxInsideContainer(30, 30);
        waitForDOMToSettle();
        Point midBox = draggablePage.getBoxDraggableLocation();
        Assert.assertTrue(midBox.getX() > startBox.getX(), "Scenario 1: Failed to move away from wall!");

        // Smash back with a smaller offset. We only need to hit the CSS boundary, not the browser window edge!
        draggablePage.dragBoxInsideContainer(-50, -50);
        waitForDOMToSettle();
        Point finalBox = draggablePage.getBoxDraggableLocation();

        // INCREASED TOLERANCE to 5 for headless CI stability
        Assert.assertTrue(Math.abs(finalBox.getX() - startBox.getX()) <= 5, 
            "Scenario 1: Box breached the LEFT boundary! Expected ~" + startBox.getX() + " but got " + finalBox.getX());
        Assert.assertTrue(Math.abs(finalBox.getY() - startBox.getY()) <= 5, 
            "Scenario 1: Box breached the TOP boundary! Expected ~" + startBox.getY() + " but got " + finalBox.getY());


        // ==========================================
        // SCENARIO 2: Parent Constraint (Top-Left Smash)
        // ==========================================
        Log.info("Executing Scenario 2: Span Parent Boundary Smash.");
        Point startParent = draggablePage.getParentDraggableLocation();

        // The parent span is VERY small. Move it just a tiny bit (10, 10).
        draggablePage.dragSpanInsideParent(10, 10);
        waitForDOMToSettle();
        Point midParent = draggablePage.getParentDraggableLocation();
        Assert.assertTrue(midParent.getY() > startParent.getY(), "Scenario 2: Failed to move away from wall!");

        // Smash back with a small offset.
        draggablePage.dragSpanInsideParent(-30, -30);
        waitForDOMToSettle();
        Point finalParent = draggablePage.getParentDraggableLocation();

        Assert.assertTrue(Math.abs(finalParent.getX() - startParent.getX()) <= 5, 
            "Scenario 2: Span breached the LEFT boundary! Expected ~" + startParent.getX() + " but got " + finalParent.getX());
        Assert.assertTrue(Math.abs(finalParent.getY() - startParent.getY()) <= 5, 
            "Scenario 2: Span breached the TOP boundary! Expected ~" + startParent.getY() + " but got " + finalParent.getY());
            
        Log.info("testContainerRestrictedElegant passed successfully.");
    }
}