package denny.selenum.test.GmailSelenumTest;

import java.util.Arrays;
import java.util.Map;

import org.json.JSONObject;

public class Config {

	private String senderAccount;
	private String receiverAccount;
	private String reportPath;
	private String testCasePrefix;
	private String chromedriver;
	private String[] runCase;
	public String getChromedriver() {
		return chromedriver;
	}
	public void setChromedriver(String chromedriver) {
		this.chromedriver = chromedriver;
	}
	private Map<String,String> caseConf;
	public Map<String,String> getCaseConf() {
		return caseConf;
	}
	public void setCaseConf(Map<String,String> caseConf) {
		this.caseConf = caseConf;
	}
	public String getTestCasePrefix() {
		return testCasePrefix;
	}
	public void setTestCasePrefix(String testCasePrefix) {
		this.testCasePrefix = testCasePrefix;
	}

	

	public String getSenderAccount() {
		return senderAccount;
	}
	public void setSenderAccount(String senderAccount) {
		this.senderAccount = senderAccount;
	}
	public String getReceiverAccount() {
		return receiverAccount;
	}
	public void setReceiverAccount(String receiverAccount) {
		this.receiverAccount = receiverAccount;
	}
	public String getReportPath() {
		return reportPath;
	}
	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	
	public String[] getRunCase() {
		return runCase;
	}
	public void setRunCase(String[] runCase) {
		this.runCase = runCase;
	}
	@Override
	public String toString() {
		return "Config [senderAccount=" + senderAccount + ", receiverAccount=" + receiverAccount + ", reportPath="
				+ reportPath + ", testCasePrefix=" + testCasePrefix + ", chromedriver=" + chromedriver + ", runCase="
				+ Arrays.toString(runCase) + ", caseConf=" + caseConf + "]";
	}
	

	
	
}
