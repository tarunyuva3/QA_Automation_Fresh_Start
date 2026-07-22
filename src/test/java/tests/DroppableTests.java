package tests;

import base.BaseTest;
import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DroppablePage;
import testUtils.Log;

public class DroppableTests extends BaseTest {

    // Helper to wait for CSS animations (like the revert slide) to finish
    private void waitForDOMToSettle() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(priority = 1, description = "Verify Simple Drag and Drop functionality")
    public void testSimpleDragAndDrop() {
        Log.info("Starting testSimpleDragAndDrop...");
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        
        Log.info("Dragging simple box to target...");
        page.dragSimpleBox();
        waitForDOMToSettle();

        String targetText = page.getSimpleTargetText();
        Log.info("Target box text after drop: " + targetText);
        
        Assert.assertEquals(targetText, "Dropped!", "The simple box was not dropped successfully.");
        Log.info("testSimpleDragAndDrop completed successfully.");
    }

//    @Test(priority = 2, description = "Verify Acceptable and Not-Acceptable Drag and Drop behavior")
//    public void testAcceptableDragAndDrop() {
//        Log.info("Starting testAcceptableDragAndDrop...");
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.clickAcceptTab();
//
//        Log.info("Testing Not-Acceptable box...");
//        page.dragNotAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Drop here", "The Not Acceptable box incorrectly triggered a drop event.");
//
//        Log.info("Testing Acceptable box...");
//        page.dragAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Dropped!", "The Acceptable box was not dropped successfully.");
//        Log.info("testAcceptableDragAndDrop completed successfully.");
//    }

    @Test(priority = 3, description = "Verify Prevent Propagation behavior on nested drop boxes")
    public void testPreventPropagationDroppable() {
        Log.info("Starting testPreventPropagationDroppable...");
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        page.clickPreventTab();
        waitForDOMToSettle();

        // 1. Test Not Greedy Drop
        Log.info("Executing Scenario 1: Not Greedy Drop...");
        page.dragToNotGreedyInner();
        waitForDOMToSettle();
        
        Assert.assertTrue(page.getNotGreedyInnerClass().contains("ui-state-highlight"), "Inner box (not greedy) did not highlight.");
        Assert.assertTrue(page.getNotGreedyOuterClass().contains("ui-state-highlight"), "Outer box (not greedy) failed to highlight.");

        // 2. REFRESH to reset the DOM (Prevents the single source box from bugging out)
        Log.info("Refreshing page to reset DOM state for Greedy test...");
        page.navigateToDroppablePage();
        page.clickPreventTab();
        waitForDOMToSettle();

        // 3. Test Greedy Drop
        Log.info("Executing Scenario 2: Greedy Drop...");
        page.dragToGreedyInner();
        waitForDOMToSettle();
        
        Assert.assertTrue(page.getGreedyInnerClass().contains("ui-state-highlight"), "Inner box (greedy) did not highlight.");
        Assert.assertFalse(page.getGreedyOuterClass().contains("ui-state-highlight"), "Outer box (greedy) incorrectly highlighted (propagation was not prevented)!");
        
        Log.info("testPreventPropagationDroppable completed successfully.");
    }

    @Test(priority = 4, description = "Verify Revertible Drag and Drop behavior for both reverting and non-reverting boxes")
    public void testRevertDraggable() {
        Log.info("Starting testRevertDraggable...");
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        page.clickRevertTab();
        waitForDOMToSettle();

        // ==========================================
        // PART 1: Test "Will Revert" Box
        // ==========================================
        Log.info("Testing 'Will Revert' Box...");
        Point startRevert = page.getRevertSourceLocation();
        Log.info("Starting location: " + startRevert);
        
        page.dragRevertBox(true); 
        
        // CRITICAL: Must wait for the box to physically slide back to the origin
        Log.info("Waiting for revert animation to complete...");
        waitForDOMToSettle(); 
        
        Point endRevert = page.getRevertSourceLocation();
        Log.info("Ending location: " + endRevert);

        Assert.assertEquals(endRevert, startRevert, "The 'Will Revert' box did not return to its original starting position.");

        // ==========================================
        // PART 2: Test "Not Revert" Box
        // ==========================================
        Log.info("Testing 'Not Revert' Box...");
        Point startNotRevert = page.getNotRevertSourceLocation();
        Log.info("Starting location: " + startNotRevert);
        
        page.dragRevertBox(false); 
        waitForDOMToSettle();
        
        Point endNotRevert = page.getNotRevertSourceLocation();
        Log.info("Ending location: " + endNotRevert);

        Assert.assertNotEquals(endNotRevert, startNotRevert, "The 'Not Revert' box incorrectly returned to its starting position!");
        Log.info("testRevertDraggable completed successfully.");
    }
}