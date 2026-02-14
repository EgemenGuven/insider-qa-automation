package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected WebElement find(By locator){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement findPresent(By locator){
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void click(By locator){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scroll(element);

        try{
            element.click();
        }
        catch(Exception e){
            jsClick(element);
        }
    }

    protected void forceClick(By locator){
        WebElement element = findPresent(locator);
        scroll(element);
        jsClick(element);
    }

    protected void type(By locator,String text){
        WebElement element = find(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String text(By locator){
        return find(locator).getText();
    }

    protected void scroll(WebElement element){
        ((JavascriptExecutor)driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
    }

    protected void jsClick(WebElement element){
        ((JavascriptExecutor)driver)
                .executeScript("arguments[0].click();", element);
    }
}