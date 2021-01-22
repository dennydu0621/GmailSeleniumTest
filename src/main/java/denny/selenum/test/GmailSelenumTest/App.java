package denny.selenum.test.GmailSelenumTest;

import java.io.FileNotFoundException;
import java.io.InputStream;
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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	GmailSendRecvCase testCase = new GmailSendRecvCase();
    	long currentTime = System.currentTimeMillis();
    	testCase.step();
    	Thread.sleep(10000);
    	testCase.vaild( currentTime);
    	testCase.result();
    }
   
    public void checkMail() {
    	
    }
}
