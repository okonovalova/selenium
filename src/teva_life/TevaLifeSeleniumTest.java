package teva_life;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import teva_life.pages.CategoryPage;
import teva_life.pages.DetailsStoryPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TevaLifeSeleniumTest {
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

    @Test
    public void CheckCategoryTitleTest() {
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        CategoryPage categoryPage = new CategoryPage(driver, wait);
        categoryPage.maximizeWindow();
        categoryPage.openPage();
        categoryPage.clickRegionOptionControl("en-EU");
        categoryPage.clickBtnSubmitRegionOptionControl();
        categoryPage.clickBtnAllowCookies();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("category__panel")));
        WebElement categoryPanel = driver.findElement(By.className("category__panel"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", categoryPanel);
        WebElement firstStoryCard = driver.findElement(By.className("card__title title title--xs title--green-emerald"));
        String firstStoryCardTitle = firstStoryCard.getText();
        firstStoryCard.click();

        DetailsStoryPage detailsStoryPage = new DetailsStoryPage(driver, wait);
        String actualStoryTitle = detailsStoryPage.getHeaderStory();
        assertEquals(firstStoryCardTitle, actualStoryTitle);

    }
}