package helper;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pageObjects.pages.accounts.RegistrationPage;

public class RandomStringGenerator {

	// Valid data generators
	public String randomValidUsername() {
		String randomUserName = RandomStringUtils.randomAlphabetic(5);
		return randomUserName;
	}

	public String randomValidEmailID() {
		String generatedstring = RandomStringUtils.randomAlphabetic(5);
		String generatednumber = RandomStringUtils.randomNumeric(2);
		return (generatedstring + "@gmail.com");
	}

	public String randomValidPhoneNo() {
		String generatednumber = RandomStringUtils.randomNumeric(10);
		return generatednumber;
	}

	// Random dropdown option selector
	public void randomOption(WebElement dropdown) {
		Select select = new Select(dropdown);
		List<WebElement> options = select.getOptions();
		Random random = new Random();
		int size = options.size();
		if (size > 1) {
			// Select a random index from 1 to size-1 (excluding index 0)
			select.selectByIndex(random.nextInt(size - 1) + 1);
		}
	}

	// Invalid data generators
	public String randomInvalidUserName() {
		String randomUserName = RandomStringUtils.randomAlphabetic(3) + "@!";
		return randomUserName; // Too short and special chars
	}

	public String randomInvalidEmailID() {
		String generatedstring = RandomStringUtils.randomAlphabetic(7);
		return (generatedstring + "gmailcom");// Missing '@' and domain
	}

	public String randomInvalidPhoneNo() {
		String generatednumber = RandomStringUtils.randomNumeric(5) + "@#";
		return generatednumber;
	}

}
