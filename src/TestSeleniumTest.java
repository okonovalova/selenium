import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.AuthPage;
import pages.NonAuthPage;

import java.time.Duration;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TestSeleniumTest {

    @Test
    public void openPageMailRuFailedAuthTest() {
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        NonAuthPage nonAuthPage = new NonAuthPage(driver, wait);
        nonAuthPage.openPage();
        nonAuthPage.setValueLoginField("abc");
        nonAuthPage.disabledCheckboxSaveAuth();
        nonAuthPage.clickBtnSubmit();
        nonAuthPage.setValuePasswordField("abc@gmail.com");
        nonAuthPage.clickBtnSubmit();
        nonAuthPage.switchToFrameErrorPage();
        nonAuthPage.waitByAppearDescriptionField();
        String actualDescriptionText = nonAuthPage.getTextFromDecriptionElement();
        String expectedDescriptionText = "There have been suspicious attempts to sign-in to your account.\n" +
                "You may have entered your password incorrectly or perhaps you are signing in from an unusual location. To convince us that it really is you, please enter your password again and the code from the picture.\n" +
                "abc@mail.ru\n" +
                "Change account";

        assertEquals(expectedDescriptionText, actualDescriptionText);
    }

    @Test
    public void openPageMailRuAuthTest() throws InterruptedException {
        //ChromeDriver driver = new ChromeDriver();
        FirefoxDriver driver = new FirefoxDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        NonAuthPage nonAuthPage = new NonAuthPage(driver,wait);
        AuthPage authPage = new AuthPage(driver, wait);
        nonAuthPage.openPage();
        nonAuthPage.setValueLoginField("***");
        nonAuthPage.disabledCheckboxSaveAuth();
        nonAuthPage.clickBtnSubmit();
        nonAuthPage.setValuePasswordField("***");
        nonAuthPage.clickBtnSubmit();
        String expectedUserEmailAuth = "****";

        String actualActualUserEmailAuth = authPage.getActualUserEmailAuth();
        assertEquals(expectedUserEmailAuth, actualActualUserEmailAuth);
    }
}