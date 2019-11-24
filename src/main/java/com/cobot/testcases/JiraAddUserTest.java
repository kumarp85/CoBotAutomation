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
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.cobot.drivers.DriverBase;
import com.cobot.lib.APICalls;
import com.cobot.lib.BaseUtility;
import com.cobot.pages.GoogleAccounts;
import com.cobot.pages.JiraHomePage;
import com.cobot.pages.JiraLoginPage;
import com.cobot.pages.JiraPeoplePage;
import com.cobot.pages.ServerDown;
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

    public static int retryCount = 0;

    public static boolean isServerDown = false;

    /**
     * User defined test case.
     * @param input the input
     * @throws IOException
     * @throws Exception
     */
    public void test(Map<String, Object> inputData) throws Exception {
        try {
            Reports.startTest(tcName, "Add new User in Jira");
            Reports.info("Running... UserDefinedTestCase(String... input)");
            BaseUtility.jobId = inputData.get("jobId").toString();
            driver = DriverBase.getDriver();
            driver.get(inputData.get("url").toString());
            isServerDown = false;

            String adminUserName = inputData.get("adminUserName").toString();
            String adminPassword = inputData.get("adminPassword").toString();

            JiraLoginPage jiraLogin = new JiraLoginPage(driver);
            jiraLogin.clickOnSignWithGoogle();

            GoogleAccounts googleAccount = new GoogleAccounts(driver);
            googleAccount.signIn(adminUserName, adminPassword);

            JiraHomePage jiraHome = new JiraHomePage(driver);
            jiraHome.clickOnPeople();

            JiraPeoplePage jiraPeople = new JiraPeoplePage(driver);
            jiraPeople.addNewUser(inputData.get("newUser").toString());

            APICalls.Execute_postAPI(AppData.properties.getProperty("responseUrl"),
                    BaseUtility.getOutPut(BaseUtility.jobId, "Success", ""));
            finishTest();
        } catch (Exception e) {
            ServerDown serverDown = new ServerDown(driver);
            if (serverDown.isServerDown()) {
                if (JiraAddUserTest.retryCount < 5) {
                    isServerDown = true;
                    driver.close();
                    Thread.sleep(5000);
                    JiraAddUserTest.retryCount++;
                    JiraAddUserTest jiraTest = new JiraAddUserTest();
                    jiraTest.test(inputData);
                }
                if (JiraAddUserTest.retryCount >= 5 && isServerDown) {
                    APICalls.Execute_postAPI(AppData.properties.getProperty("responseUrl"),
                            BaseUtility.getOutPut(BaseUtility.jobId, "Failed", "Target System Unrechable"));
                }
            } else {
                APICalls.Execute_postAPI(AppData.properties.getProperty("responseUrl"),
                        BaseUtility.getOutPut(BaseUtility.jobId, "Failed", "Script Failure"));
            }
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
        } catch (Exception e) {
            Reports.info("finishTest for " + tcName + " with Exception: " + e.getMessage());
        }
    }
}
