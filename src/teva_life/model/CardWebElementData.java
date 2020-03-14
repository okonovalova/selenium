package teva_life.model;

import org.openqa.selenium.WebElement;

public class CardWebElementData {
    private WebElement element;
    private String cardTitle;

    public CardWebElementData(WebElement element, String cardTitle){
        this.element = element;
        this.cardTitle = cardTitle;
    }
    public void setWebElement(WebElement webElement){
        element=webElement;
    }
    public WebElement getWebElement(){
        return element;
    }

    public void setCardTitle(String cardTitle){
        this.cardTitle = cardTitle;
    }

    public String getCardTitle(){
        return cardTitle;
    }
}
