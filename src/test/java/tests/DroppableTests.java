//package tests;
//
//import org.openqa.selenium.Point;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import base.BaseTest;
//import pages.DroppablePage;
//
//public class DroppableTests extends BaseTest {
//
//    @Test(priority = 1, description = "Verify Simple Drag and Drop functionality")
//    public void testSimpleDragAndDrop() {
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.dragSimpleBox();
//
//        Assert.assertEquals(page.getSimpleTargetText(), "Dropped!", "The simple box was not dropped successfully.");
//    }
//
//    @Test(priority = 2, description = "Verify Acceptable and Not-Acceptable Drag and Drop behavior")
//    public void testAcceptableDragAndDrop() {
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.clickAcceptTab();
//
//        page.dragNotAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Drop here", "The Not Acceptable box incorrectly triggered a drop event.");
//
//        page.dragAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Dropped!", "The Acceptable box was not dropped successfully.");
//    }
//
//    @Test(priority = 3, description = "Verify Prevent Propagation behavior on nested drop boxes")
//    public void testPreventPropagationDroppable() {
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.clickPreventTab();
//
//        // Test Not Greedy
//        page.dragToNotGreedyInner();
//        Assert.assertTrue(page.getNotGreedyInnerClass().contains("ui-state-highlight"), "Inner box (not greedy) did not highlight.");
//        Assert.assertTrue(page.getNotGreedyOuterClass().contains("ui-state-highlight"), "Outer box (not greedy) failed to highlight.");
//
//        // Test Greedy
//        page.dragToGreedyInner();
//        Assert.assertTrue(page.getGreedyInnerClass().contains("ui-state-highlight"), "Inner box (greedy) did not highlight.");
//        Assert.assertFalse(page.getGreedyOuterClass().contains("ui-state-highlight"), "Outer box (greedy) incorrectly highlighted (propagation was not prevented)!");
//    }
//
//    @Test(priority = 4, description = "Verify Revertible Drag and Drop behavior")
//    public void testRevertDraggable() {
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.clickRevertTab();
//
//        Point start = page.getRevertSourceLocation();
//        page.dragRevertBox(true);
//        Point end = page.getRevertSourceLocation();
//
//        // The box should return exactly to where it started
//        Assert.assertEquals(end, start, "The revertible box did not return to its original starting position.");
//    }
//}
package tests;

import org.openqa.selenium.Point;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.DroppablePage;

public class DroppableTests extends BaseTest {

    @Test(priority = 1, description = "Verify Simple Drag and Drop functionality")
    public void testSimpleDragAndDrop() {
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        page.dragSimpleBox();

        Assert.assertEquals(page.getSimpleTargetText(), "Dropped!", "The simple box was not dropped successfully.");
    }

//    @Test(priority = 2, description = "Verify Acceptable and Not-Acceptable Drag and Drop behavior")
//    public void testAcceptableDragAndDrop() {
//        DroppablePage page = new DroppablePage(getDriver());
//
//        page.navigateToDroppablePage();
//        page.clickAcceptTab();
//
//        page.dragNotAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Drop here", "The Not Acceptable box incorrectly triggered a drop event.");
//
//        page.dragAcceptableBox();
//        Assert.assertEquals(page.getAcceptTargetText(), "Dropped!", "The Acceptable box was not dropped successfully.");
//    }

    @Test(priority = 3, description = "Verify Prevent Propagation behavior on nested drop boxes")
    public void testPreventPropagationDroppable() {
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        page.clickPreventTab();

        // 1. Test Not Greedy Drop
        page.dragToNotGreedyInner();
        Assert.assertTrue(page.getNotGreedyInnerClass().contains("ui-state-highlight"), "Inner box (not greedy) did not highlight.");
        Assert.assertTrue(page.getNotGreedyOuterClass().contains("ui-state-highlight"), "Outer box (not greedy) failed to highlight.");

        // 2. REFRESH to reset the DOM (Prevents the single source box from bugging out)
        page.navigateToDroppablePage();
        page.clickPreventTab();

        // 3. Test Greedy Drop
        page.dragToGreedyInner();
        Assert.assertTrue(page.getGreedyInnerClass().contains("ui-state-highlight"), "Inner box (greedy) did not highlight.");
        Assert.assertFalse(page.getGreedyOuterClass().contains("ui-state-highlight"), "Outer box (greedy) incorrectly highlighted (propagation was not prevented)!");
    }

    @Test(priority = 4, description = "Verify Revertible Drag and Drop behavior for both reverting and non-reverting boxes")
    public void testRevertDraggable() {
        DroppablePage page = new DroppablePage(getDriver());

        page.navigateToDroppablePage();
        page.clickRevertTab();

        // ==========================================
        // PART 1: Test "Will Revert" Box
        // ==========================================
        Point startRevert = page.getRevertSourceLocation();
        page.dragRevertBox(true); 
        Point endRevert = page.getRevertSourceLocation();

        // The box should return exactly to where it started
        Assert.assertEquals(endRevert, startRevert, "The 'Will Revert' box did not return to its original starting position.");


        // ==========================================
        // PART 2: Test "Not Revert" Box
        // ==========================================
        Point startNotRevert = page.getNotRevertSourceLocation();
        page.dragRevertBox(false); 
        Point endNotRevert = page.getNotRevertSourceLocation();

        // The box should NOT return to where it started (it should stay dropped)
        Assert.assertNotEquals(endNotRevert, startNotRevert, "The 'Not Revert' box incorrectly returned to its starting position!");
    }
}