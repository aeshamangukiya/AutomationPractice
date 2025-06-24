package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	private int retryCount = 0;
	private final int maxRetryCount = 1;

	public boolean retry(ITestResult result) {

		System.out.println("Success Result: " + result.toString());
		if (!result.isSuccess() && retryCount < maxRetryCount) {
			System.out.println("Success Result in if condition: " + result.isSuccess());
			retryCount++;

			return true; // Retry the test
		}

		System.out.println("Retry Count: " + retryCount);
		return false; // Do not try if max count is reached
	}
}

