package teva_life;


import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import teva_life.pages.CategoryPage;
import teva_life.pages.DetailsStoryPage;

import java.time.Duration;


public class TevaLifeSeleniumTest extends Assert {

    @DataProvider
    public Object[][] categoryUrls() {
        return new Object[][]{
                {"https://lifeeffects.teva/eu/mental-health"},
                {"https://lifeeffects.teva/eu/respiratory"},
                {"https://lifeeffects.teva/eu/multiple-sclerosis"},
                {"https://lifeeffects.teva/eu/cancer"},
                {"https://lifeeffects.teva/eu/cardiovascular-disease"},
                {"https://lifeeffects.teva/eu/caregivers"}
        };
    }


    @Test
    public void checkCountStories() throws InterruptedException {

        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        CategoryPage categoryPage = new CategoryPage(driver, wait);

        categoryPage.maximizeWindow();
        categoryPage.openPage("https://lifeeffects.teva/eu/mental-health");
        categoryPage.clickRegionOptionControl("en-EU");
        categoryPage.clickBtnSubmitRegionOptionControl();
        categoryPage.clickBtnAllowCookies();

        int countStoriesFromTitle = categoryPage.getCountStoriesFromTitle();
        int actualCountStories = categoryPage.getActualCountStories();
        assertEquals(countStoriesFromTitle, actualCountStories);
    }


    @Test(dataProvider = "categoryUrls")
    public void CheckCategoryTitleTest(String url) {
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        CategoryPage categoryPage = new CategoryPage(driver, wait);
        categoryPage.maximizeWindow();
        categoryPage.openPage(url);
        categoryPage.clickRegionOptionControl("en-EU");
        categoryPage.clickBtnSubmitRegionOptionControl();
        categoryPage.clickBtnAllowCookies();
        String firstStoryCardTitle = categoryPage.getTitleFirsCard();
        categoryPage.clickFirstStoryCard();

        DetailsStoryPage detailsStoryPage = new DetailsStoryPage(driver, wait);
        String actualStoryTitle = detailsStoryPage.getHeaderStory();

        assertEquals(firstStoryCardTitle, actualStoryTitle);
    }

}