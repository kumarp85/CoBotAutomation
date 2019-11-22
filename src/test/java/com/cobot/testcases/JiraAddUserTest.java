/********************************************************************************************************************************
 * Change History:
 * 
 * Revision     Date            Updated by                  Comments
 * ---------------------------------------------------------------------------------------------------------------------------
 * 1.0          10-Mar-2018     Punnam Santosh Kumar        Creating the new TestCase "ComposeTest"
 * 
 * 
 * Copyright (c) Rock Interview. All Rights Reserved.
 ********************************************************************************************************************************/

package com.cobot.testcases;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.cobot.drivers.DriverBase;
import com.cobot.pages.GoogleAccounts;
import com.cobot.pages.JiraHomePage;
import com.cobot.pages.JiraLoginPage;
import com.cobot.pages.JiraPeoplePage;
import com.cobot.reports.Reports;
import com.cobot.utils.AppData;

/**
 * The Class ComposeTest.
 */
public class JiraAddUserTest {

    /** The driver. */
    public static WebDriver driver;

    /** The tc name. */
    public String tcName = this.getClass().getSimpleName();

    /** The tc description. */
    public String tcDescription;

    /** The iteration. */
    public int iteration = 0;

    /** The test count. */
    public int testCount = 0;

    /**
     * User defined test case.
     * @param input the input
     * @throws IOException
     */
    public void test(String userName, String password, String newUser) throws IOException {
        try {
            Reports.startTest(tcName, "Add new User in Jira");
            Reports.info("Running... UserDefinedTestCase(String... input)");
            driver = DriverBase.getDriver();
            driver.get(AppData.properties.getProperty("url"));

            JiraLoginPage jiraLogin = new JiraLoginPage(driver);
            jiraLogin.clickOnSignWithGoogle();

            GoogleAccounts googleAccount = new GoogleAccounts(driver);
            googleAccount.signIn(userName, password);

            JiraHomePage jiraHome = new JiraHomePage(driver);
            jiraHome.clickOnPeople();

            JiraPeoplePage jiraPeople = new JiraPeoplePage(driver);
            jiraPeople.addNewUser(newUser);

            finishTest();
        } catch (Exception e) {
            Reports.finishWithfail("TestCase case has been stopped due to exception");
            finishTest();
        }
    }

    /**
     * Finish test.
     */
    public void finishTest() {
        try {
            Reports.finishTest();
            if (AppData.properties.getProperty("Suite_Execution") == null
                    || AppData.properties.getProperty("Suite_Execution").equals("NO")) {
                Reports.openReport();
            }
        } catch (Exception e) {
            Reports.info("finishTest for " + tcName + " with Exception: " + e.getMessage());
        }
    }
}
