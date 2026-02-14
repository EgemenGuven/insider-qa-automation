package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;

public class InsiderTest extends BaseTest {

    @Test
    public void testFlow(){

        HomePage home = new HomePage(driver);
        home.go();
        home.acceptCookies();
        home.goToCareers();

        CareersPage careers = new CareersPage(driver);
        careers.openJobs();

        JobsPage jobs = new JobsPage(driver);
        jobs.filterLocationTurkey();
        jobs.filterTeamQualityAssurance();

        jobs.verifyFilteredPostings();
        jobs.openFirstJobAndApplyVerifyForm();
    }

    @Test
    public void shouldRedirectToLeverJobsPageFromCareers(){

        HomePage home = new HomePage(driver);
        home.go();
        home.acceptCookies();
        home.goToCareers();

        CareersPage careers = new CareersPage(driver);
        careers.openJobs();

        // (jobs.lever.co + insiderone)
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("jobs.lever.co"),
                "Expected to be redirected to Lever jobs page. Current URL: " + url);
        Assert.assertTrue(url.contains("insiderone"),
                "Expected InsiderOne Lever page. Current URL: " + url);
    }
}