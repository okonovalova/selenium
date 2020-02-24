package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AuthPage {
    public AuthPage(RemoteWebDriver driver, WebDriverWait wait ){
        this.driver = driver;
        this.wait = wait;
    }
    private RemoteWebDriver driver;
    private WebDriverWait wait;


    private WebElement getUserEmailAuthElement(){
        return driver.findElement(By.id("PH_user-email"));
    }
    public String getActualUserEmailAuth(){
        return getUserEmailAuthElement().getText();
    }
}
