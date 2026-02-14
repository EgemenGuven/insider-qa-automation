package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {

    public CareersPage(WebDriver driver){
        super(driver);
    }

    By seeAllJobs = By.xpath("//a[contains(@href,'jobs')]");

    public void openJobs(){
        click(seeAllJobs);
    }
}