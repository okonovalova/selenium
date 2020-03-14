package mailru.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NonAuthPage {
    public NonAuthPage(RemoteWebDriver driver, WebDriverWait wait ){
        this.driver = driver;
        this.wait = wait;

    }
    private RemoteWebDriver driver;
    private WebDriverWait wait;

    public void openPage(){
        driver.get("https://mail.ru/");
    }

    private WebElement getLoginField(){
        return driver.findElement(By.id("mailbox:login"));
    }

    public void setValueLoginField(String value){
        getLoginField().sendKeys(value);
    }

    private WebElement getCheckBoxSaveAuth(){
        return driver.findElement(By.id("mailbox:saveauth"));
    }

    public void disabledCheckboxSaveAuth(){
        getCheckBoxSaveAuth().click();
    }

    private WebElement getBtnSubmit(){
        return driver.findElement(By.id("mailbox:submit"));
    }

    public void clickBtnSubmit(){
        getBtnSubmit().click();
    }

    private WebElement getPasswordField(){
        return driver.findElement(By.id("mailbox:password"));
    }

    public void setValuePasswordField(String value){
        getPasswordField().sendKeys(value);
    }

    public void switchToFrameErrorPage(){
        driver.switchTo().frame(driver.findElement(By.className("ag-popup__frame__layout__iframe")));
    }
    public void waitByAppearDescriptionField(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Description")));
    }

    private WebElement getDescriptionElement(){
        return driver.findElement(By.className("Description"));
    }

    public String getTextFromDecriptionElement(){
        return  getDescriptionElement().getText();
    }

}
