package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.automation.utils.ui.WebActions;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SelectablePage extends BasePage {

    @FindBy(id = "demo-tab-list")
    private WebElement listTab;

    @FindBy(xpath = "//ul[@id='verticalListContainer']/li")
    private List<WebElement> listItems;
    
    @FindBy(id = "demo-tab-grid")
    private WebElement gridTab;

    @FindBy(xpath = "//div[@id='gridContainer']//li")
    private List<WebElement> gridItems;

    public SelectablePage(WebDriver driver) {
        super(driver);
    }

    public void navigateToSelectablePage() {
        driver.get("https://demoqa.com/selectable");
        disableTextSelection();
    }

    public void clickListTab() { clickElement(listTab); }
    public void clickGridTab() { clickElement(gridTab); }

    public Set<WebElement> selectRandomItemsFromList(int count) {
        return selectRandom(listItems, count);
    }

    public Set<WebElement> selectRandomItemsFromGrid(int count) {
        return selectRandom(gridItems, count);
    }

    private Set<WebElement> selectRandom(List<WebElement> elements, int count) {
        // Fetch visible list using JAR utility
        List<WebElement> visibleElements = WebActions.waitForAllVisible(driver, elements, 15);
        Random random = new Random();
        Set<Integer> randomIndices = new HashSet<>();
        Set<WebElement> selectedElements = new HashSet<>();
        
        while(randomIndices.size() < count) {
            randomIndices.add(random.nextInt(visibleElements.size()));
        }
        
        for(int index : randomIndices) {
            WebElement item = visibleElements.get(index);
            clickElement(item); // Uses the stable click wrapper
            selectedElements.add(item);
        }
        return selectedElements;
    }
}