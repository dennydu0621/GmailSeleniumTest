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

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException
    {
    	String dirvePath = "D://chromedriver_win32/chromedriver.exe";
    	System.setProperty("webdriver.chrome.driver",dirvePath);
    	WebDriver driver = new ChromeDriver();
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	driver.get("https://www.google.com/");    
        //System.out.println( "Hello World!" );
        
        driver.findElement(By.linkText("Gmail")).click();
        driver.findElement(By.linkText("Sign in")).click();
        //driver.switchTo().
        String home = driver.getWindowHandle();
        for (String handles : driver.getWindowHandles()) {
        	System.out.println("handles:"+handles);
			if (handles.equals(home))
				continue;
			driver.switchTo().window(handles);
        }
        System.out.println("Driver title "+driver.getTitle()+" url:"+driver.getCurrentUrl());
        driver.getTitle();
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement accoutInput = driver.findElement(By.id("identifierId"));
        accoutInput.sendKeys("dennyduTestA@gmail.com");
        System.out.println( accoutInput.getTagName());
        driver.findElement(By.id("identifierNext")).click(); 
        //driver.findElement(By.className(""));
        
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("testtest0621");
        driver.findElement(By.id("passwordNext")).click(); 
        
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        
    	Thread.sleep(5000);
    	/*String search= "abcdefghijklmnopqrstuvwxyz";
    	for(char c : search.toCharArray()) {
    		try {
    			if( driver.findElement(By.xpath("//div[@id=':3"+c+"']/div/div"))!=null) {
        			driver.findElement(By.xpath("//div[@id=':3"+c+"']/div/div")).click();
        	
        			break;
        		}
    		}catch(Exception e){
    			
    		}
    		
    	}*/
    	 /*JavascriptExecutor j = (JavascriptExecutor) driver;
         j.executeScript("dlrqf()");*/
    	//driver.findElement(By.className(".T-I-KE")).click();
    	driver.findElement(By.xpath("//div[@class='T-I T-I-KE L3']")).click();
    	driver.findElement(By.name("to")).sendKeys("dennyduTestB@gmail.com");
    	driver.findElement(By.name("subjectbox")).sendKeys("title");
    	driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf tS-tW']")).sendKeys("contents");
    	driver.findElement(By.xpath("//div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();
    }
   
    public void checkMail() {
    	
    }
}
