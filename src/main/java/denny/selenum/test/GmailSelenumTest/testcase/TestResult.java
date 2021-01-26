package denny.selenum.test.GmailSelenumTest.testcase;

public class TestResult {
	private String caseName;
	private boolean pass=false;
	private String message="";
	private long startTime;
	private long endTime;
	private long runTime;
	
	
	public TestResult(String caseName) {
		this.caseName = caseName;
	}
	public String getCaseName() {
		return caseName;
	}
	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}
	public boolean getPass() {
		return pass;
	}
	public void setPass(boolean pass) {
		this.pass = pass;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public long getRunTime() {
		return runTime;
	}
	public void setRunTime(long runTime) {
		this.runTime = runTime;
	}
	
	@Override
	public String toString() {
		return "TestResult [caseName=" + caseName + ", pass=" + pass + ", message=" + message 
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", runTime=" + runTime + "]";
	}

	
}
