package teva_life;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import teva_life.pages.CategoryPage;

import java.time.Duration;
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

        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        CategoryPage categoryPage = new CategoryPage(driver, wait);

        categoryPage.maximizeWindow();
        categoryPage.openPage();
        categoryPage.clickRegionOptionControl("en-EU");
        categoryPage.clickBtnSubmitRegionOptionControl();
        categoryPage.clickBtnAllowCookies();

        int countStoriesFromTitle = categoryPage.getCountStoriesFromTitle();
        int actualCountStories = categoryPage.getActualCountStories();
        assertEquals(countStoriesFromTitle, actualCountStories);
    }
}