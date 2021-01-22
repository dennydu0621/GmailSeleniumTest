package denny.selenum.test.GmailSelenumTest.testcase;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

public class GmailSendRecvCase {

	public void step() {
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
        try {
        	Thread.sleep(5000);
        }catch(Exception e) {
        	System.out.println("sleep error:"+e.getMessage());
        }
    	

    	driver.findElement(By.xpath("//div[@class='T-I T-I-KE L3']")).click();
    	driver.findElement(By.name("to")).sendKeys("dennyduTestB@gmail.com");
    	driver.findElement(By.name("subjectbox")).sendKeys("title");
    	driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf tS-tW']")).sendKeys("contents");
    	driver.findElement(By.xpath("//div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();
        try {
        	Thread.sleep(5000);
        }catch(Exception e) {
        	System.out.println("sleep error:"+e.getMessage());
        }
    	for (String handles : driver.getWindowHandles()) {


			driver.switchTo().window(handles).close();
        }
	}
	public void vaildByPoP3(long currentTime) {
		 try {
			  String host = "pop.gmail.com";// change accordingly
		      String mailStoreType = "pop3";
		      String username = "dennyduTestB@gmail.com";// change accordingly
		      String password = "testtest0621";// change accordingly
		      //create properties field
		      Properties properties = new Properties();

		      properties.put("mail.pop3.host", host);
		      properties.put("mail.pop3.port", "995");
		      properties.put("mail.pop3.starttls.enable", "true");
		      Session emailSession = Session.getDefaultInstance(properties);
		  
		      //create the POP3 store object and connect with the pop server
		      Store store = emailSession.getStore("pop3s");

		      store.connect(host, username, password);

		      //create the folder object and open it
		      Folder emailFolder = store.getFolder("INBOX");
		      emailFolder.open(Folder.READ_WRITE);
		      
		      // retrieve the messages from the folder in an array and print it
		      Message[] messages = emailFolder.getMessages();
		   
		      System.out.println("messages.length---" + messages.length);

		      for (int i = 0, n = messages.length; i < n; i++) {
		         Message message = messages[i];
		         System.out.println("currentTime: " + currentTime);
		         System.out.println("message.getSentDate().getTime()): "+message.getSentDate().getTime());
		         if(currentTime>message.getSentDate().getTime())continue;
		         //if( message.isSet(Flags.Flag.RECENT))continue;
		         System.out.println("---------------------------------");
		         System.out.println("Email Number " + (i + 1));
		         System.out.println("Subject: " + message.getSubject());
		         System.out.println("From: " + message.getFrom()[0]);
		       
		         System.out.println("Text: " + message.getContent());
		         System.out.println("SentDate: " + message.getSentDate().getTime());
		         Object content = message.getContent();    
	                if (content instanceof MimeMultipart) {    
	                    MimeMultipart multipart = (MimeMultipart) content;    
	                    parseMultipart(multipart);    
	                }    
		         message.setFlag(Flags.Flag.DELETED, true);
		         //message.saveChanges();
		         
		      }
		      
		      //close the store and folder objects
		      emailFolder.close(false);
		      store.close();

		      } catch (NoSuchProviderException e) {
		         e.printStackTrace();
		      } catch (MessagingException e) {
		         e.printStackTrace();
		      } catch (Exception e) {
		    	  e.printStackTrace();
		      }
	}
	public  void parseMultipart(Multipart multipart) throws MessagingException, IOException {    
        int count = multipart.getCount();    
        System.out.println("couont =  "+count);    
        for (int idx=0;idx<count;idx++) {    
            BodyPart bodyPart = multipart.getBodyPart(idx);    
            System.out.println(bodyPart.getContentType());    
            if (bodyPart.isMimeType("text/plain")) {    
                System.out.println("plain................."+bodyPart.getContent());    
            } else if(bodyPart.isMimeType("text/html")) {    
                System.out.println("html..................."+bodyPart.getContent());    
            } else if(bodyPart.isMimeType("multipart/*")) {    
                Multipart mpart = (Multipart)bodyPart.getContent();    
                parseMultipart(mpart);    
                    
            } 
        }    
    }    
	public void vaildByIMAP() throws MessagingException, IOException {
		 String username = "dennyduTestB@gmail.com";// change accordingly
	      String password = "testtest0621";// change accordingly
		  Properties prop = System.getProperties();    
         prop.put("mail.store.protocol", "imap");    
         prop.put("mail.imap.host", "imap.gmail.com"); 
         prop.put("mail.imap.port", "993");
         Session session = Session.getInstance(prop);   
         int total = 0;    
         IMAPStore store = (IMAPStore) session.getStore("imap"); // 使用imap會話機制，連線伺服器    
         store.connect(username, password);    
         IMAPFolder folder = (IMAPFolder) store.getFolder("INBOX"); // 收件箱    
         folder.open(Folder.READ_WRITE);    
         // 獲取總郵件數    
         total = folder.getMessageCount();    
         System.out.println("-----------------共有郵件：" + total    
                 + " 封--------------");    
         // 得到收件箱資料夾資訊，獲取郵件列表    
         System.out.println("未讀郵件數：" + folder.getUnreadMessageCount());    
         Message[] messages = folder.getMessages();    
         int messageNumber = 0;    
         for (Message message : messages) {    
             System.out.println("傳送時間：" + message.getSentDate());    
             System.out.println("主題：" + message.getSubject());    
             System.out.println("內容：" + message.getContent());    
             Flags flags = message.getFlags();    
             if (flags.contains(Flags.Flag.SEEN))    
                 System.out.println("這是一封已讀郵件");    
             else {    
                 System.out.println("未讀郵件");    
             }    
             System.out    
                     .println("========================================================");    
             System.out    
                     .println("========================================================");    
             //每封郵件都有一個MessageNumber，可以通過郵件的MessageNumber在收件箱裡面取得該郵件    
             messageNumber = message.getMessageNumber();    
         }    
         Message message = folder.getMessage(messageNumber);    
         System.out.println(message.getContent()+message.getContentType());    
         // 釋放資源    
         if (folder != null)    
             folder.close(true);     
         if (store != null)    
             store.close();  
	}
	public void vaild(long currentTime) throws Exception {
		vaildByPoP3(currentTime);
		//vaildByIMAP();
	}
	public void result() {
		System.out.println("Successful");
	}
}
