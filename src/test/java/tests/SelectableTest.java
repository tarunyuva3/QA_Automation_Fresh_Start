package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SelectablePage;
import testUtils.Log;

import java.util.Set;

public class SelectableTest extends BaseTest {

    @Test(priority = 1, description = "Verify random selection of elements in a vertical List format")
    public void testSelectableList() {
        Log.info("Starting testSelectableList...");
        SelectablePage selectablePage = new SelectablePage(getDriver());
        
        selectablePage.navigateToSelectablePage();
        selectablePage.clickListTab();
        
        Log.info("Selecting 2 random items from the list...");
        Set<WebElement> selectedItems = selectablePage.selectRandomItemsFromList(2);
        
        Log.info("Verifying active classes on selected items...");
        for(WebElement item : selectedItems) {
            String itemText = item.getText();
            Log.info("Validating item: " + itemText);
            Assert.assertTrue(item.getAttribute("class").contains("active"), 
                "List Item '" + itemText + "' was not properly highlighted as active.");
        }
        Log.info("testSelectableList completed successfully.");
    }

    @Test(priority = 2, description = "Verify random selection of elements in a Grid format")
    public void testSelectableGrid() {
        Log.info("Starting testSelectableGrid...");
        SelectablePage selectablePage = new SelectablePage(getDriver());
        
        selectablePage.navigateToSelectablePage();
        selectablePage.clickGridTab();
        
        Log.info("Selecting 3 random items from the grid...");
        Set<WebElement> selectedItems = selectablePage.selectRandomItemsFromGrid(3);
        
        Log.info("Verifying active classes on selected grid items...");
        for(WebElement item : selectedItems) {
            String itemText = item.getText();
            Log.info("Validating grid item: " + itemText);
            Assert.assertTrue(item.getAttribute("class").contains("active"), 
                "Grid Item '" + itemText + "' was not properly highlighted as active.");
        }
        Log.info("testSelectableGrid completed successfully.");
    }
}