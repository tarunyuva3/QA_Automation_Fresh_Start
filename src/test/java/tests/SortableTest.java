package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.SortablePage;
import testUtils.Log;

public class SortableTest extends BaseTest {

    @Test(priority = 1, description = "Verify drag-and-drop sorting in a List view")
    public void testSortableList() {
        Log.info("Starting testSortableList...");
        SortablePage sortablePage = new SortablePage(getDriver());
        
        sortablePage.navigateToSortablePage();
        sortablePage.clickListTab();
        
        Log.info("Executing random sorting sequence on List elements...");
        // The page object handles the wait, click-and-hold, drag, and release logic internally
        sortablePage.sortRandomListElements();
        
        Log.info("testSortableList completed successfully.");
    }

    @Test(priority = 2, description = "Verify drag-and-drop sorting in a Grid view")
    public void testSortableGrid() {
        Log.info("Starting testSortableGrid...");
        SortablePage sortablePage = new SortablePage(getDriver());
        
        sortablePage.navigateToSortablePage();
        sortablePage.clickGridTab();
        
        Log.info("Executing random sorting sequence on Grid elements...");
        // Randomly sorts items and ensures the JS UI catches the DOM changes
        sortablePage.sortRandomGridElements();
        
        Log.info("testSortableGrid completed successfully.");
    }
}