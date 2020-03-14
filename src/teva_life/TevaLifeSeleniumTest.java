package teva_life;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import mailru.pages.AuthPage;
import mailru.pages.NonAuthPage;
import teva_life.model.CardWebElementData;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TevaLifeSeleniumTest {

    private boolean clickLoadMoreBtn(List <WebElement> categoryBodyChilds, WebDriver driver ) throws InterruptedException {
        for (WebElement webElement:categoryBodyChilds){
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
    @Test
    public void checkCountStories() throws InterruptedException {
        int countStoriesFromTitle=-1;
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://lifeeffects.teva/eu/mental-health");


        List<WebElement> regionOptionList = driver.findElements(By.className("region-option__control"));
        for (WebElement webElement:  regionOptionList) {
            String value = webElement.getAttribute("value");
            if (value.equals("en-EU") ){
                webElement.click();
            }
        }
        List <WebElement> footerChilds = driver.findElement(By.className("default-modal__footer")).findElements(By.cssSelector("*"));
        for (WebElement webElement: footerChilds){
            String className = webElement.getAttribute("class");
            if (className.equals("button button--green button--fullwidth-on-mobile default-modal__footer-button region-selector__next-button is-visible")){
                webElement.click();
                break;
            }
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_lt_ctl02_CookieLawAndTrackingConsent_TLE_btnAllowAll")));
        WebElement btnAllowAllCookies = driver.findElement(By.id("p_lt_ctl02_CookieLawAndTrackingConsent_TLE_btnAllowAll"));
        btnAllowAllCookies.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("category__panel")));
        List <WebElement> categoryPanelChilds = driver.findElement(By.className("category__panel")).findElements(By.cssSelector("*"));
        for (WebElement webElement: categoryPanelChilds){
            String className = webElement.getAttribute("class");
            if (className.equals("category__title title title--m title--blue")) {
                countStoriesFromTitle = Integer.parseInt(webElement.getText().split(" ")[0]);
                break;
            }
        }

        List <WebElement> categoryBodyChilds = driver.findElement(By.className("category__body")).findElements(By.cssSelector("*"));
        int countCards = 0;
        List<CardWebElementData> cards= new ArrayList<>();

        boolean isLoadMoreBtnClick=true;
        while (isLoadMoreBtnClick){
            for (WebElement webElement:categoryBodyChilds){
                String className =webElement.getAttribute("class");
                if (className != null) {
                    if (className.equals("card card--grid margin-bottom-20-tablet") ) {
                        String cardTitle ="";
                        boolean hasTitleWebElement=false;
                        for (WebElement cardChildren: webElement.findElements(By.cssSelector("*"))){
                            String classNameCardChildren = cardChildren.getAttribute("class");
                            if ((classNameCardChildren != null)&&(classNameCardChildren.equals("card__title title title--xs title--green-emerald"))){
                                cardTitle = cardChildren.getText();
                                hasTitleWebElement = true;
                                break;
                            }
                        }
                        if (hasTitleWebElement){
                            CardWebElementData cardWebElementData = new CardWebElementData(webElement,cardTitle);
                            boolean hasItem=false;
                            for (CardWebElementData listItem:cards){
                                if (listItem.getCardTitle().equals(cardTitle)){
                                    hasItem=true;
                                }
                            }
                            if (!hasItem){
                                cards.add(cardWebElementData);
                            }
                        }

                    }
                }
            }
            isLoadMoreBtnClick = clickLoadMoreBtn(categoryBodyChilds,driver);
            categoryBodyChilds = driver.findElement(By.className("category__body")).findElements(By.cssSelector("*"));
        }



        assertEquals(countStoriesFromTitle, cards.size() );
    }
}