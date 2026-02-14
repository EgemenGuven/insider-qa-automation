package base;

import org.openqa.selenium.*;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.DriverFactory;

import java.io.File;
import java.nio.file.Files;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setup(){
        driver = DriverFactory.initDriver();
    }

    @AfterMethod
    public void teardown(ITestResult result){
        try{
            if(result.getStatus() == ITestResult.FAILURE && driver != null){
                takeScreenshot(result.getName());
            }
        } catch(Exception ignored){}
        finally{
            DriverFactory.quitDriver();
        }
    }

    private void takeScreenshot(String testName) throws Exception {
        File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        Files.createDirectories(new File("screenshots").toPath());
        File dest = new File("screenshots/" + testName + "_" + System.currentTimeMillis() + ".png");

        Files.copy(src.toPath(), dest.toPath());
        System.out.println("Screenshot saved: " + dest.getAbsolutePath());
    }
}