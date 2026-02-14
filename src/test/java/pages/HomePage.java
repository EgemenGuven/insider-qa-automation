package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver){
        super(driver);
    }

    By careersBtn = By.xpath("//a[contains(@href,'careers')]");
    By cookie = By.id("wt-cli-accept-all-btn");

    public void go(){
        driver.get("https://useinsider.com/");
    }

    public void acceptCookies(){
        try{ click(cookie); }
        catch(Exception ignored){}
    }

    public void goToCareers(){
        click(careersBtn);
    }
}