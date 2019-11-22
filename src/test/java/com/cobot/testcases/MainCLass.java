package com.cobot.testcases;

public class MainCLass {
    public static void main(String[] args) throws Exception {
        JiraAddUserTest jira = new JiraAddUserTest();
        jira.test("mana.santosh9991@gmail.com", "Forgot@12345", "punnamsatyasai1998@gmail.com");
    }
}
