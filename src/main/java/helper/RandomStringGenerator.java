package helper;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

/**
 * Utility class to generate valid and invalid random test data.
 * Helps create realistic and unique inputs for positive and negative test scenarios.
 */
public class RandomStringGenerator {

    private static final Random RANDOM = new Random();

    // ========================
    // VALID DATA GENERATORS
    // ========================

    /**
     * Generates a valid first name (alphabetic, length 3–10).
     */
    public String generateValidName() {
        return capitalize(RandomStringUtils.randomAlphabetic(randomLength(3, 10)));
    }

    /**
     * Generates a valid surname (alphabetic, length 3–12).
     */
    public String generateValidSurname() {
        return capitalize(RandomStringUtils.randomAlphabetic(randomLength(3, 12)));
    }

    /**
     * Generates a valid username (alphanumeric, starts with a letter, length 6–12).
     */
    public String generateValidUsername() {
        String prefix = RandomStringUtils.randomAlphabetic(1);
        String rest = RandomStringUtils.randomAlphanumeric(randomLength(5, 11));
        return prefix + rest;
    }

    /**
     * Generates a valid password with uppercase, lowercase, digits, and special characters.
     * Length between 8–12.
     */
    public String generateValidPassword() {
        String upper = RandomStringUtils.randomAlphabetic(1).toUpperCase();
        String lower = RandomStringUtils.randomAlphabetic(3).toLowerCase();
        String digits = RandomStringUtils.randomNumeric(2);
        String special = RandomStringUtils.random(2, "!@#$%&*");
        return upper + lower + digits + special;
    }

    /**
     * Generates a valid email address.
     */
    public String generateValidEmailID() {
        String localPart = RandomStringUtils.randomAlphanumeric(randomLength(5, 10)).toLowerCase();
        return localPart + "@testmail.com";
    }

    /**
     * Generates a valid 10-digit phone number starting with a non-zero digit.
     */
    public String generateValidPhoneNo() {
        String firstDigit = String.valueOf(RANDOM.nextInt(9) + 1);
        String rest = RandomStringUtils.randomNumeric(9);
        return firstDigit + rest;
    }

    // ========================
    // INVALID DATA GENERATORS
    // ========================

    /**
     * Generates an invalid name with too short length or special characters.
     */
    public String generateInvalidName() {
        return RandomStringUtils.randomAlphabetic(1) + "@!";
    }

    /**
     * Generates an invalid surname with too short length or special characters.
     */
    public String generateInvalidSurname() {
        return RandomStringUtils.randomAlphabetic(2) + "#$";
    }

    /**
     * Generates an invalid username with special characters and too short length.
     */
    public String generateInvalidUserName() {
        return "@!" + RandomStringUtils.randomAlphanumeric(2);
    }

    /**
     * Generates an invalid password that doesn't meet complexity rules.
     */
    public String generateInvalidPassword() {
        return RandomStringUtils.randomAlphabetic(3); // too short, no digits/special chars
    }

    /**
     * Generates a different invalid password (for confirm password mismatch testing).
     */
    public String generateInvalidPasswordAgain() {
        return RandomStringUtils.randomAlphabetic(4);
    }

    /**
     * Generates an invalid email missing '@' or domain.
     */
    public String generateInvalidEmailID() {
        return RandomStringUtils.randomAlphabetic(6).toLowerCase() + "gmail.com"; // Missing '@'
    }

    /**
     * Generates an invalid phone number with letters or too short length.
     */
    public String generateInvalidPhoneNo() {
        return RandomStringUtils.randomNumeric(5) + "abc";
    }

    // ========================
    // HELPER METHODS
    // ========================

    /**
     * Capitalizes the first letter of the given string.
     */
    private String capitalize(String input) {
        if (input == null || input.isEmpty()) return input;
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    /**
     * Generates a random length between min and max (inclusive).
     */
    private int randomLength(int min, int max) {
        return RANDOM.nextInt((max - min) + 1) + min;
    }
}
