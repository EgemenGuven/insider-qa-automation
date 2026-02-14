package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class JobsPage extends BasePage {

    public JobsPage(WebDriver driver){
        super(driver);
    }

    /* =======================
       FILTERS
       ======================= */

    private final By locationFilterButton = By.cssSelector("div.filter-bar > div:nth-child(2) .filter-button");
    private final By locationPopup       = By.cssSelector("div.filter-bar > div:nth-child(2) .filter-popup");
    private final By turkeyOption        = By.cssSelector("div.filter-bar > div:nth-child(2) .filter-popup a[href*='location=Turkey']");

    private final By teamFilterButton = By.cssSelector("div.filter-bar > div:nth-child(3) .filter-button");
    private final By teamPopup       = By.cssSelector("div.filter-bar > div:nth-child(3) .filter-popup");
    private final By qaTeamOption    = By.cssSelector("div.filter-bar > div:nth-child(3) .filter-popup a[href*='team=Quality']");

    /* =======================
       POSTINGS
       ======================= */

    private final By postingsWrapper   = By.cssSelector("div.postings-wrapper");
    private final By postings          = By.cssSelector("div.postings-wrapper .posting");

    private final By postingTitle      = By.cssSelector(".posting-title");
    private final By postingCategories = By.cssSelector(".posting-categories");

    // Listede ilan detayına götüren link (genelde posting’in içindeki <a>)
    private final By postingLinkInside = By.cssSelector("a.posting-title, a.posting-title-link, a");

    /* =======================
       JOB DETAIL -> APPLY
       ======================= */

    // Job detail sayfasında Apply butonu/Linki (Lever farklı varyasyonlar)
    private final By applyOnDetailByHref = By.cssSelector("a[href*='/apply']");
    private final By applyOnDetailByText = By.xpath("//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'apply')]");
    private final By leverForm           = By.cssSelector("form");

    /* =======================
       FILTER ACTIONS
       ======================= */

    public void filterLocationTurkey(){
        click(locationFilterButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locationPopup));
        forceClick(turkeyOption);
        wait.until(d -> d.getCurrentUrl().contains("location=Turkey"));
    }

    public void filterTeamQualityAssurance(){
        click(teamFilterButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(teamPopup));
        forceClick(qaTeamOption);
        wait.until(d -> d.getCurrentUrl().contains("team=Quality"));
    }

    public void verifyFilteredPostings(){

        String url = driver.getCurrentUrl();
        if(url == null || !url.contains("team=Quality")){
            throw new RuntimeException("Team filter not applied. Current URL: " + url);
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(postingsWrapper));
        List<WebElement> list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(postings));

        if(list.isEmpty())
            throw new RuntimeException("No postings found after applying filters.");

        for(WebElement posting : list){

            String title = "";
            try { title = posting.findElement(postingTitle).getText(); }
            catch(Exception ignored){}

            String categories = "";
            try { categories = posting.findElement(postingCategories).getText(); }
            catch(Exception ignored){}

            if(title == null) title = "";
            if(categories == null) categories = "";

            String titleLower = title.toLowerCase();
            String catLower   = categories.toLowerCase();

            boolean titleOk = titleLower.contains("qa") || titleLower.contains("quality");
            if(!titleOk){
                throw new RuntimeException("Wrong job title (QA/Quality not found). Title: " + title);
            }

            boolean locOk =
                    catLower.contains("turkey") ||
                            catLower.contains("turkiye") ||
                            catLower.contains("türkiye");

            if(!locOk){
                throw new RuntimeException("Wrong location (Turkey not found). Categories: " + categories);
            }
        }
    }


    public void openFirstJobAndApplyVerifyForm(){

        wait.until(ExpectedConditions.presenceOfElementLocated(postingsWrapper));
        List<WebElement> list = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(postings));

        if(list.isEmpty())
            throw new RuntimeException("No postings found to open.");

        WebElement first = list.get(0);


        WebElement link = null;
        try {

            link = first.findElement(By.cssSelector("a.posting-title"));
        } catch(Exception ignored1){
            try {
                link = first.findElement(By.cssSelector("a"));
            } catch(Exception ignored2){}
        }

        if(link == null)
            throw new RuntimeException("Could not find a clickable link inside first posting.");

        String href = link.getAttribute("href");
        if(href != null && !href.isBlank()){
            driver.get(href);
        } else {
            scroll(link);
            jsClick(link);
        }


        wait.until(d -> !d.getCurrentUrl().contains("jobs.lever.co/insiderone?"));


        WebElement applyBtn = null;
        try {
            applyBtn = wait.until(ExpectedConditions.presenceOfElementLocated(applyOnDetailByHref));
        } catch(Exception ignored){
            applyBtn = wait.until(ExpectedConditions.presenceOfElementLocated(applyOnDetailByText));
        }

        String applyHref = applyBtn.getAttribute("href");
        if(applyHref != null && !applyHref.isBlank()){
            driver.get(applyHref);
        } else {
            scroll(applyBtn);
            jsClick(applyBtn);
        }

        wait.until(d -> d.getCurrentUrl().contains("/apply"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(leverForm));
    }
}