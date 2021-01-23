package denny.selenum.test.GmailSelenumTest.testcase;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import com.google.gson.JsonSyntaxException;
import denny.selenum.test.GmailSelenumTest.ConfigHandler;

public class GmailSendRecvCase implements TestCase{
	private Config conf;
	private long currentTime=0;
	private TestResult result;
	private WebDriver driver;
	private String host = "pop.gmail.com";
	private Properties properties = new Properties();
	private Store store;
	public GmailSendRecvCase() throws JsonSyntaxException, FileNotFoundException, ClassNotFoundException{		
		Map<String,String> map = ConfigHandler.conf.getCaseConf();
		String confPath = map.get(this.getClass().getSimpleName());
		conf = (Config)ConfigHandler.load(confPath,new Config());
		result = new TestResult(this.getClass().getSimpleName());
		System.setProperty("webdriver.chrome.driver",ConfigHandler.conf.getChromedriver());
    	driver = new ChromeDriver();   
    	properties.put("mail.pop3.host", host);
	    properties.put("mail.pop3.port", "995");
	    properties.put("mail.pop3.starttls.enable", "true");
	}
		
	private  JSONObject parseMultipart(Multipart multipart,JSONObject bodyJson) throws MessagingException, IOException {    
		int count = multipart.getCount();    
        System.out.println("count =  "+count);    
        for (int idx=0;idx<count;idx++) {    
            BodyPart bodyPart = multipart.getBodyPart(idx);  
            bodyJson.put(bodyPart.getContentType(), bodyPart.getContent());
            System.out.println(bodyPart.getContentType());    
            if (bodyPart.isMimeType("text/plain")) {    
            	bodyJson.put("plain", bodyPart.getContent().toString().replace("\r\n",""));
                System.out.println("plain................."+bodyPart.getContent());    
            } else if(bodyPart.isMimeType("text/html")) {    
            	bodyJson.put("html", bodyPart.getContent());   
            } else if(bodyPart.isMimeType("")) {    
                Multipart mpart = (Multipart)bodyPart.getContent();    
                bodyJson = parseMultipart(mpart,bodyJson);                       
            } 
        }
		return bodyJson;    
    }    
	private boolean switchToGmail() {
		boolean pass = false;
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("https://www.google.com/");    
			driver.findElement(By.linkText("Gmail")).click();
			pass= true;
		}catch(Exception e) {
        	System.out.println("switchToGmail error:"+e.getMessage());
        	result.setMessage(result.getMessage()+" switchToGmail error:"+e.getMessage());
        }
	    return pass;
	}
	private boolean signIn() {
		boolean pass = false;
	    try {
	    	driver.findElement(By.linkText("Sign in")).click();
	    	String home = driver.getWindowHandle();
	    	for (String handles : driver.getWindowHandles()) {
	    		System.out.println("handles:"+handles);
	    		if (handles.equals(home))continue;
	    		driver.switchTo().window(handles);
	    	}
	    	System.out.println("Driver title "+driver.getTitle()+" url:"+driver.getCurrentUrl());
	    	driver.getTitle();
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	    	WebElement accoutInput = driver.findElement(By.id("identifierId"));
	    	accoutInput.sendKeys(conf.getSenderAccount());
	    	System.out.println( accoutInput.getTagName());
	    	driver.findElement(By.id("identifierNext")).click(); 
        
	    	WebElement passwordInput = driver.findElement(By.name("password"));
	    	passwordInput.sendKeys(conf.getSenderPassword());
	    	driver.findElement(By.id("passwordNext")).click(); 
	    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        	Thread.sleep(5000);
        	pass =true;
        }catch(Exception e) {
        	System.out.println("signIn error:"+e.getMessage());
        	result.setMessage(result.getMessage()+" signIn error:"+e.getMessage());
        }
	    return pass;
	}
	private boolean sendMail() {
		boolean pass = false;
		 try {
			 currentTime = System.currentTimeMillis();
			 driver.findElement(By.xpath("//div[@class='T-I T-I-KE L3']")).click();
			 driver.findElement(By.name("to")).sendKeys(conf.getReceiverAccount());
			 driver.findElement(By.name("subjectbox")).sendKeys(conf.getTitle());
			 driver.findElement(By.xpath("//div[@class='Am Al editable LW-avf tS-tW']")).sendKeys(conf.getContent());
			 Thread.sleep(1000);
			 driver.findElement(By.xpath("//div[@class='T-I J-J5-Ji aoO v7 T-I-atl L3']")).click();
			 pass =true;
	         Thread.sleep(5000);
	    }catch(Exception e) {
	        System.out.println("sendMail error:"+e.getMessage());
	       	result.setMessage(result.getMessage()+" sendMail error:"+e.getMessage());
	     }
		return pass;
	}
	private boolean closeWeb() {
		boolean pass = false;
		try {  
			for (String handles : driver.getWindowHandles()) {
				driver.switchTo().window(handles).close();
			}    
			System.out.println("Wait for 10 sec...");
    	    Thread.sleep(10000);
    	    pass =true;
    	}catch(Exception e) {
    	     System.out.println("closeWeb error:"+e.getMessage());
    	  	result.setMessage(result.getMessage()+" closeWeb error:"+e.getMessage());
    	}
		return pass;
	}
	@Override
	public void runTest()  {
		// TODO Auto-generated method stub
		try {
			result.setStartTime(System.currentTimeMillis());
			if(!switchToGmail())return;
			if(!signIn())return;
			if(!sendMail())return;
			if(!closeWeb())return;  
			if(!verify()) {
				while(conf.getRetry()!=0) {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Retry["+conf.getRetry()+"]");
					if(verify()) {
						result.setPass(true);
						
					    
					}
					conf.setRetry(conf.getRetry()-1);
				}
				return;
			}
			result.setPass(true);
		}finally {
			result.setEndTime(System.currentTimeMillis());
	    	result.setRunTime((result.getEndTime()-result.getStartTime()));
		}
    	
	}
	private boolean connectGmail() {
		boolean pass = false;
		try {
			 Session emailSession = Session.getDefaultInstance(properties);
			 store = emailSession.getStore("pop3s");
			 store.connect(host, conf.getReceiverAccount(), conf.getReceiverPassword());
			 pass = true;
		} catch (Exception e) {
			 System.out.println("connectGmail error:"+e.getMessage());
	    	 result.setMessage(result.getMessage()+" connectGmail error:"+e.getMessage());
		}
		return pass;
		 
	}
	private boolean checkMail() {
		 boolean pass =false;
		 try {			
			   
			 Folder emailFolder = store.getFolder("INBOX");
			 emailFolder.open(Folder.READ_WRITE);		      		   
			 Message[] messages = emailFolder.getMessages();			   
			 System.out.println("messages.length---" + messages.length);
			 for (int i = 0, n = messages.length; i < n; i++) {
				 Message message = messages[i];
			     System.out.println("currentTime: " + currentTime+" "+message.getSentDate().getTime());			
			     System.out.println("--------------Email Number"+(i + 1)+"-------------------");
			     System.out.println("Subject: " + message.getSubject());
			     System.out.println("From: " + message.getFrom()[0]);			       
			     System.out.println("Text: " + message.getContent());
			     System.out.println("SentDate: " + message.getSentDate().getTime());
			     message.setFlag(Flags.Flag.DELETED, true);
			     if(currentTime>message.getSentDate().getTime())continue;
			     if(!message.getSubject().equals(conf.getTitle()))continue;
			     JSONObject bodyJson = new JSONObject();
			     bodyJson.put("plain","");
			     bodyJson.put("html","");
			     Object content = message.getContent();    
		         if (content instanceof MimeMultipart) {    
		             MimeMultipart multipart = (MimeMultipart) content;    
		             bodyJson=parseMultipart(multipart,bodyJson); 
		                    
		         }    
		         System.out.println(bodyJson);
		         System.out.println(bodyJson.get("plain").toString());
		         if(bodyJson.get("plain").toString().equals(conf.getContent())) {
	                  pass =true;
	             }
			    

			  }			      			     
			 emailFolder.close(false);
			 store.close();
		 }catch(Exception e) {
			 System.out.println("checkMail error:"+e.getMessage());
	    	 result.setMessage(result.getMessage()+" checkMail error:"+e.getMessage());
	    	 pass=false;
		 }
		 return pass;
	}

	public boolean verify() {		
		if(!connectGmail())return false;				   
		return checkMail();
	}

	@Override
	public TestResult getResult() {
		return result;
		
	}
	class Config {
		private String senderAccount;
		private String senderPassword;
		private String receiverAccount;
		private String receiverPassword;
		private String title;
		private int retry;
		
		private String content;
		public String getSenderAccount() {
			return senderAccount;
		}
		public void setSenderAccount(String senderAccount) {
			this.senderAccount = senderAccount;
		}
		public String getSenderPassword() {
			return senderPassword;
		}
		public void setSenderPassword(String senderPassword) {
			this.senderPassword = senderPassword;
		}
		public String getReceiverAccount() {
			return receiverAccount;
		}
		public void setReceiverAccount(String receiverAccount) {
			this.receiverAccount = receiverAccount;
		}
		public String getReceiverPassword() {
			return receiverPassword;
		}
		public void setReceiverPassword(String receiverPassword) {
			this.receiverPassword = receiverPassword;
		}
		public int getRetry() {
			return retry;
		}
		public void setRetry(int retry) {
			this.retry = retry;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}
}
