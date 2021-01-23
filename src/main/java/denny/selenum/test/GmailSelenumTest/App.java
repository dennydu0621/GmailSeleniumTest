package denny.selenum.test.GmailSelenumTest;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import denny.selenum.test.GmailSelenumTest.testcase.GmailSendRecvCase;
import denny.selenum.test.GmailSelenumTest.testcase.TestCase;
import denny.selenum.test.GmailSelenumTest.testcase.TestResult;
/**
 * Hello world!
 *
 */
public class App 
{
	private static String confPath = "";
    public static void main( String[] args ) throws Exception
    {	
    	getConfPath(args);
    	
    	new ConfigHandler(confPath);
    	
    	ArrayList<TestCase> testCaseList = new ArrayList<>();
    	for(String testCase:ConfigHandler.conf.getRunCase()) {
        	testCaseList.add((TestCase)Class.forName(ConfigHandler.conf.getTestCasePrefix()+"."+testCase).newInstance());
    	}
    	FileWriter file = new FileWriter(ConfigHandler.conf.getReportPath());
    	file.write("CaseName,Pass,runTime,startTime,endTime,message\n");
    	for(TestCase testCase:testCaseList) {
    		testCase.runTest();
    		TestResult testResult =testCase.getResult();
    		file.write(
    				testResult.getCaseName()+","+
    				testResult.getPass()+","+
    				testResult.getRunTime()+","+
    				testResult.getStartTime()+","+
    				testResult.getEndTime()+","+
    				testResult.getMessage()+"\n");
    		System.out.println(testResult.toString());
    		file.flush();
    	}
    	file.close();
    	
    
    }
    public static void getConfPath(String[] args) {
    	if(args.length==0) {
    		confPath="./TestConfig.json";
    	}else {
    		confPath = args[0];
    	}    	
    }
   
 
}
