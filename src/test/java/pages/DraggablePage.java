package pages;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class DraggablePage extends BasePage {

	@FindBy(id = "draggableExample-tab-simple")
	private WebElement simpleTab;
	@FindBy(id = "dragBox")
	private WebElement dragBox;

	@FindBy(id = "draggableExample-tab-axisRestriction")
	private WebElement axisTab;
	@FindBy(id = "restrictedX")
	private WebElement restrictedXBox;
	@FindBy(id = "restrictedY")
	private WebElement restrictedYBox;

	@FindBy(id = "draggableExample-tab-cursorStyle")
	private WebElement cursorTab;
	@FindBy(id = "cursorCenter")
	private WebElement cursorCenter;
	@FindBy(id = "cursorTopLeft")
	private WebElement cursorTopLeft;
	@FindBy(id = "cursorBottom")
	private WebElement cursorBottom;

	@FindBy(id = "draggableExample-tab-containerRestriction")
	private WebElement containerTab;
	@FindBy(xpath = "//div[@id='containmentWrapper']//div[contains(@class, 'ui-widget-content')]")
	private WebElement boxDraggable;
	@FindBy(xpath = "//span[contains(text(), \"I'm contained within my parent\")]")
	private WebElement parentDraggable;

	public DraggablePage(WebDriver driver) {
		super(driver);
	}

	public void navigateToDraggablePage() {
		driver.get("https://demoqa.com/dragabble");
		disableTextSelection();
	}

	private void performDrag(WebElement element, int xOffset, int yOffset) {
		WebElement targetElement = waitForElement(element);
		Actions actions = new Actions(driver);
		actions.moveToElement(targetElement)
				.pause(Duration.ofMillis(200))
				.clickAndHold(targetElement)
				.pause(Duration.ofMillis(200))
				.moveByOffset(xOffset, yOffset)
				.pause(Duration.ofMillis(200))
				.release()
				.perform();
	}

	private void performSlowGrabDrag(WebElement element, int xOffset, int yOffset) {
		WebElement targetElement = waitForElement(element);
		Actions actions = new Actions(driver);
		actions.moveToElement(targetElement)
				.pause(Duration.ofMillis(500))
				.clickAndHold(targetElement)
				.pause(Duration.ofMillis(500))
				.moveByOffset(xOffset, yOffset)
				.pause(Duration.ofMillis(500))
				.release()
				.perform();
	}

	// Tab Methods
	public void clickSimpleTab() {
		clickElement(simpleTab);
	}

	public Point getSimpleBoxLocation() {
		return waitForElement(dragBox).getLocation();
	}

	public void dragSimpleBox(int x, int y) {
		performDrag(dragBox, x, y);
	}

	public void clickAxisTab() {
		clickElement(axisTab);
	}

	public Point getRestrictedXBoxLocation() {
		return waitForElement(restrictedXBox).getLocation();
	}

	public void dragRestrictedXBox(int x, int y) {
		performDrag(restrictedXBox, x, y);
	}

	public Point getRestrictedYBoxLocation() {
		return waitForElement(restrictedYBox).getLocation();
	}

	public void dragRestrictedYBox(int x, int y) {
		performDrag(restrictedYBox, x, y);
	}

	public void clickCursorTab() {
		clickElement(cursorTab);
	}

	public Point getCursorCenterLocation() {
		return waitForElement(cursorCenter).getLocation();
	}

	public void dragCursorCenter(int x, int y) {
		performSlowGrabDrag(cursorCenter, x, y);
	}

	public Point getCursorTopLeftLocation() {
		return waitForElement(cursorTopLeft).getLocation();
	}

	public void dragCursorTopLeft(int x, int y) {
		performSlowGrabDrag(cursorTopLeft, x, y);
	}

	public Point getCursorBottomLocation() {
		return waitForElement(cursorBottom).getLocation();
	}

	public void dragCursorBottom(int x, int y) {
		performSlowGrabDrag(cursorBottom, x, y);
	}

	public void clickContainerTab() {
		clickElement(containerTab);
	}

	public Point getBoxDraggableLocation() {
		return waitForElement(boxDraggable).getLocation();
	}

	public void dragBoxInsideContainer(int x, int y) {
		performSlowGrabDrag(boxDraggable, x, y);
	}

	public Point getParentDraggableLocation() {
		return waitForElement(parentDraggable).getLocation();
	}

	public void dragSpanInsideParent(int x, int y) {
		performSlowGrabDrag(parentDraggable, x, y);
	}
}