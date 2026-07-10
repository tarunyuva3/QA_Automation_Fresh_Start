package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.SortablePage;

public class SortableTest extends BaseTest {

    @Test(priority = 1, description = "Verify drag-and-drop sorting in a List view")
    public void testSortableList() {
        SortablePage sortablePage = new SortablePage(getDriver());
        
        sortablePage.navigateToSortablePage();
        sortablePage.clickListTab();
        
        // The page object handles the wait, click-and-hold, drag, and release logic internally
        sortablePage.sortRandomListElements();
    }

    @Test(priority = 2, description = "Verify drag-and-drop sorting in a Grid view")
    public void testSortableGrid() {
        SortablePage sortablePage = new SortablePage(getDriver());
        
        sortablePage.navigateToSortablePage();
        sortablePage.clickGridTab();
        
        // Randomly sorts items and ensures the JS UI catches the DOM changes
        sortablePage.sortRandomGridElements();
    }
}