package helper;

import constants.FrameworkConstants;

public class PageURLs {

	// Load raw property values (default fallback)
	private static final String ENV_PROPERTY = System.getProperty("app.environment", "qa").toLowerCase();
	private static final String PROJECT_PROPERTY = System.getProperty("app.project", "orangehrm").toLowerCase();

	// Get base URL dynamically based on project + environment
	public static String getBaseURL() {
		if (PROJECT_PROPERTY.equals("orangehrm")) {
			switch (ENV_PROPERTY) {
			case "qa":
				return "https://opensource-demo.orangehrmlive.com"; // QA/Staging Environment URL
			case "prod":
				return "https://opensource-demo.orangehrmlive.com"; // Live/Production Environment URL
			default:
				throw new IllegalArgumentException("Unknown environment: " + ENV_PROPERTY);
			}
		} else if (PROJECT_PROPERTY.equals("orangehrm2")) {
			switch (ENV_PROPERTY) {
			case "qa":
				return "https://opensource-demo.orangehrmlive.com"; // QA/Staging Environment URL
			case "prod":
				return "https://opensource-demo.orangehrmlive.com"; // Live/Production Environment URL
			default:
				throw new IllegalArgumentException("Unknown environment: " + ENV_PROPERTY);
			}
		} else {
			throw new IllegalArgumentException("Unknown project: " + PROJECT_PROPERTY);
		}
	}

	// Page paths
	public static final String Home_PAGE_PATH = "/";
	public static final String Login_PAGE_PATH = "web/index.php/auth/login";

	// Full URLs
	public static final String Login_PAGE = buildURL(Login_PAGE_PATH);
	public static final String HOME_PAGE = getBaseURL();

	// URL builder
	public static String buildURL(String path) {
		String base = getBaseURL();
		if (path == null || path.isEmpty())
			return base;
		return base + "/" + path;
	}
}
