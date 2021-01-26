package denny.selenum.test.GmailSelenumTest.testcase;

public interface TestCase {
	void runTest() throws InterruptedException;
	boolean verify();
	TestResult getResult();
}
