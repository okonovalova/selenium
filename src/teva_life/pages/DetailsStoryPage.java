package teva_life.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DetailsStoryPage {
    private RemoteWebDriver driver;
    private WebDriverWait wait;

    public DetailsStoryPage(RemoteWebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    private void waitByAppearHeaderStory() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("article__header")));
    }

    public String getHeaderStory() {
        waitByAppearHeaderStory();
        WebElement arcticleHeaderElement = driver.findElement(By.className("article__header"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", arcticleHeaderElement);
        return driver.findElement(By.className("title")).getText();
    }
}
