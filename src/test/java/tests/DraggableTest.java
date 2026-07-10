package tests;

import base.BaseTest;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DraggablePage;

public class DraggableTest extends BaseTest {

    @Test(priority = 1)
    public void testSimpleDraggable() {
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        draggablePage.clickSimpleTab();
        
        Point initialLoc = draggablePage.getSimpleBoxLocation();
        draggablePage.dragSimpleBox(100, 100);
        Point newLoc = draggablePage.getSimpleBoxLocation();
        
        Assert.assertTrue(newLoc.getX() > initialLoc.getX(), "The simple box did not move right!");
        Assert.assertTrue(newLoc.getY() > initialLoc.getY(), "The simple box did not move down!");
    }

    @Test(priority = 2)
    public void testAxisRestrictedDraggable() {
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        draggablePage.clickAxisTab();

        // --- Test X-Axis Box ---
        Point startLocationX = draggablePage.getRestrictedXBoxLocation();
        draggablePage.dragRestrictedXBox(100, 100); 
        Point endLocationX = draggablePage.getRestrictedXBoxLocation();
        
        Assert.assertTrue(endLocationX.getX() > startLocationX.getX(), 
            "Only X box did not move horizontally!");
            
        // FIXED: Apply 2-pixel tolerance to the restricted Y axis
        Assert.assertTrue(Math.abs(endLocationX.getY() - startLocationX.getY()) <= 2, 
            "Only X box illegally moved vertically!");

        // --- Test Y-Axis Box ---
        Point startLocationY = draggablePage.getRestrictedYBoxLocation();
        draggablePage.dragRestrictedYBox(100, 100); 
        Point endLocationY = draggablePage.getRestrictedYBoxLocation();
        
        Assert.assertTrue(endLocationY.getY() > startLocationY.getY(), 
            "Only Y box did not move vertically!");
            
        // FIXED: Apply 2-pixel tolerance to the restricted X axis
        Assert.assertTrue(Math.abs(endLocationY.getX() - startLocationY.getX()) <= 2, 
            "Only Y box illegally moved horizontally!");
    }
    @Test(priority = 3)
    public void testCursorStyleDraggable() {
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        draggablePage.clickCursorTab();

        // Box 1: Center
        Point centerInitial = draggablePage.getCursorCenterLocation();
        draggablePage.dragCursorCenter(100, 100);
        Point centerFinal = draggablePage.getCursorCenterLocation();
        Assert.assertNotEquals(centerFinal, centerInitial, "Cursor Center box failed to move!");

        // Box 2: Top Left
        Point topLeftInitial = draggablePage.getCursorTopLeftLocation();
        draggablePage.dragCursorTopLeft(100, 100);
        Point topLeftFinal = draggablePage.getCursorTopLeftLocation();
        Assert.assertNotEquals(topLeftFinal, topLeftInitial, "Cursor Top Left box failed to move!");

        // Box 3: Bottom
        Point bottomInitial = draggablePage.getCursorBottomLocation();
        draggablePage.dragCursorBottom(100, -100); // Drag UP to avoid screen edge
        Point bottomFinal = draggablePage.getCursorBottomLocation();
        Assert.assertNotEquals(bottomFinal, bottomInitial, "Cursor Bottom box failed to move!");
    }

    @Test(priority = 4)
    public void testContainerRestrictedElegant() {
        DraggablePage draggablePage = new DraggablePage(getDriver());
        
        draggablePage.navigateToDraggablePage();
        draggablePage.clickContainerTab();

        // ==========================================
        // SCENARIO 1: Box Constraint (Top-Left Smash)
        // ==========================================
        Point startBox = draggablePage.getBoxDraggableLocation();

        // 1. Move safely to center
        draggablePage.dragBoxInsideContainer(100, 100);
        Point midBox = draggablePage.getBoxDraggableLocation();
        Assert.assertTrue(midBox.getX() > startBox.getX(), "Scenario 1: Failed to move away from wall!");

        // 2. Smash into Top-Left boundary
        draggablePage.dragBoxInsideContainer(-200, -200);
        Point finalBox = draggablePage.getBoxDraggableLocation();

        // 3. Assert it clamped back perfectly (with a 2-pixel safety tolerance for sub-pixel rendering)
        Assert.assertTrue(Math.abs(finalBox.getX() - startBox.getX()) <= 2, 
            "Scenario 1: Box breached the LEFT boundary! Expected ~" + startBox.getX() + " but got " + finalBox.getX());
        Assert.assertTrue(Math.abs(finalBox.getY() - startBox.getY()) <= 2, 
            "Scenario 1: Box breached the TOP boundary! Expected ~" + startBox.getY() + " but got " + finalBox.getY());


        // ==========================================
        // SCENARIO 2: Parent Constraint (Top-Left Smash)
        // ==========================================
        Point startParent = draggablePage.getParentDraggableLocation();

        // 1. Move safely to center
        draggablePage.dragSpanInsideParent(50, 50);
        Point midParent = draggablePage.getParentDraggableLocation();
        Assert.assertTrue(midParent.getY() > startParent.getY(), "Scenario 2: Failed to move away from wall!");

        // 2. Smash into Top-Left boundary
        draggablePage.dragSpanInsideParent(-200, -200);
        Point finalParent = draggablePage.getParentDraggableLocation();

        // 3. Assert it clamped back perfectly (with a 2-pixel safety tolerance)
        Assert.assertTrue(Math.abs(finalParent.getX() - startParent.getX()) <= 2, 
            "Scenario 2: Span breached the LEFT boundary! Expected ~" + startParent.getX() + " but got " + finalParent.getX());
        Assert.assertTrue(Math.abs(finalParent.getY() - startParent.getY()) <= 2, 
            "Scenario 2: Span breached the TOP boundary! Expected ~" + startParent.getY() + " but got " + finalParent.getY());
    }
}