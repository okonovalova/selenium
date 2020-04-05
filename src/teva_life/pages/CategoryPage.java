package teva_life.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import teva_life.model.CardWebElementData;

import java.util.ArrayList;
import java.util.List;

public class CategoryPage {
    private RemoteWebDriver driver;
    private WebDriverWait wait;
    private int countStoriesFromTitle = -1;

    public CategoryPage(RemoteWebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void maximizeWindow() {
        driver.manage().window().maximize();
    }

    public void openPage(String url) {
        driver.get(url);
    }

    private List<WebElement> getRegionOptionControl() {
        return driver.findElements(By.className("region-option__control"));
    }

    public void clickRegionOptionControl(String region) {
        List<WebElement> regionOptionList = getRegionOptionControl();
        for (WebElement webElement : regionOptionList) {
            String value = webElement.getAttribute("value");
            if (value.equals(region)) {
                webElement.click();
            }
        }
    }

    private List<WebElement> getFooterChilds() {
        return driver.findElement(By.className("default-modal__footer")).findElements(By.cssSelector("*"));
    }

    public void clickBtnSubmitRegionOptionControl() {
        List<WebElement> footerChilds = getFooterChilds();
        for (WebElement webElement : footerChilds) {
            String className = webElement.getAttribute("class");
            if (className.equals("button button--green button--fullwidth-on-mobile default-modal__footer-button region-selector__next-button is-visible")) {
                webElement.click();
                break;
            }
        }
    }

    private void waitByAppearBtnAllowAllCookies() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_lt_ctl02_CookieLawAndTrackingConsent_TLE_btnAllowAll")));
    }

    private WebElement getBtnAllowAllCookies() {
        return driver.findElement(By.id("p_lt_ctl02_CookieLawAndTrackingConsent_TLE_btnAllowAll"));
    }

    public void clickBtnAllowCookies() {
        waitByAppearBtnAllowAllCookies();
        WebElement btnAllowAllCookies = getBtnAllowAllCookies();
        btnAllowAllCookies.click();
    }

    private void waitByAppearCategoryPanel() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("category__panel")));
    }

    private List<WebElement> getcategoryPanelChilds() {
        return driver.findElement(By.className("category__panel")).findElements(By.cssSelector("*"));
    }

    public int getCountStoriesFromTitle() {
        waitByAppearCategoryPanel();
        List<WebElement> categoryPanelChilds = getcategoryPanelChilds();
        for (WebElement webElement : categoryPanelChilds) {
            String className = webElement.getAttribute("class");
            if (className.equals("category__title title title--m title--blue")) {
                countStoriesFromTitle = Integer.parseInt(webElement.getText().split(" ")[0]);
                break;
            }
        }
        return countStoriesFromTitle;
    }

    private List<WebElement> getCategoryBodyChilds() {
        return driver.findElement(By.className("category__body")).findElements(By.cssSelector("*"));
    }

    private boolean clickLoadMoreBtn(List<WebElement> categoryBodyChilds, WebDriver driver) throws InterruptedException {
        for (WebElement webElement : categoryBodyChilds) {
            String className = webElement.getAttribute("class");
            if (className != null) {
                if (className.equals("category__load-more button button--blue")) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", webElement);
                    webElement.click();
                    Thread.sleep(2000);
                    return true;
                }
            }
        }
        return false;
    }

    private String getCardTitle(WebElement cardElement) {

        for (WebElement cardChildren : cardElement.findElements(By.cssSelector("*"))) {
            String classNameCardChildren = cardChildren.getAttribute("class");
            if ((classNameCardChildren != null) && (classNameCardChildren.equals("card__title title title--xs title--green-emerald"))) {
                return cardChildren.getText();
            }
        }
        return null;
    }

    private boolean checkDublicateCardElement(ArrayList<CardWebElementData> cards, String cardTitle) {
        for (CardWebElementData listItem : cards) {
            if (listItem.getCardTitle().equals(cardTitle)) {
                return true;
            }
        }
        return false;
    }

    public int getActualCountStories() throws InterruptedException {
        List<WebElement> categoryBodyChilds = getCategoryBodyChilds();
        int countCards = 0;
        ArrayList<CardWebElementData> cards = new ArrayList<>();

        boolean isLoadMoreBtnClick = true;
        while (isLoadMoreBtnClick) {
            for (WebElement webElement : categoryBodyChilds) {
                String className = webElement.getAttribute("class");
                if ((className != null) && (className.equals("card card--grid margin-bottom-20-tablet"))) {
                    String cardTitle = getCardTitle(webElement);
                    if ((cardTitle != null) && (!checkDublicateCardElement(cards, cardTitle))) {
                        cards.add(new CardWebElementData(webElement, cardTitle));
                    }
                }
            }
            isLoadMoreBtnClick = clickLoadMoreBtn(categoryBodyChilds, driver);
            categoryBodyChilds = getCategoryBodyChilds();
        }
        return cards.size();
    }

    private WebElement getFirstStoryCard() {
        WebElement firstStoryCard = driver.findElement(By.className("card__title"));
        return firstStoryCard;
    }


    public String getTitleFirsCard() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("category__panel")));
        WebElement categoryPanel = driver.findElement(By.className("category__panel"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", categoryPanel);
        WebElement firstStoryCard = getFirstStoryCard();
        return firstStoryCard.getText();
    }

    public void clickFirstStoryCard() {
        getFirstStoryCard().click();
    }

}
