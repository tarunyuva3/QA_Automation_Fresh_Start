package tests;

import base.BaseTest;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SelectablePage;

import java.util.Set;

public class SelectableTest extends BaseTest {

    @Test(priority = 1, description = "Verify random selection of elements in a vertical List format")
    public void testSelectableList() {
        SelectablePage selectablePage = new SelectablePage(getDriver());
        
        selectablePage.navigateToSelectablePage();
        selectablePage.clickListTab();
        
        // Select 2 random items
        Set<WebElement> selectedItems = selectablePage.selectRandomItemsFromList(2);
        
        // Verify all selected items received the 'active' class styling
        for(WebElement item : selectedItems) {
            Assert.assertTrue(item.getAttribute("class").contains("active"), "List Item '" + item.getText() + "' was not properly highlighted as active.");
        }
    }

    @Test(priority = 2, description = "Verify random selection of elements in a Grid format")
    public void testSelectableGrid() {
        SelectablePage selectablePage = new SelectablePage(getDriver());
        
        selectablePage.navigateToSelectablePage();
        selectablePage.clickGridTab();
        
        // Select 3 random items from the grid
        Set<WebElement> selectedItems = selectablePage.selectRandomItemsFromGrid(3);
        
        // Verify all selected items received the 'active' class styling
        for(WebElement item : selectedItems) {
            Assert.assertTrue(item.getAttribute("class").contains("active"), "Grid Item '" + item.getText() + "' was not properly highlighted as active.");
        }
    }
}