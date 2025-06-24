package helper;

import constants.ConstVariables;

public class PageURLs {

    // Load raw property values (default fallback)
    private static final String ENV_PROPERTY = System.getProperty("app.environment", "qa").toLowerCase();
    private static final String PROJECT_PROPERTY = System.getProperty("app.project", "hoco").toLowerCase();

    // Get base URL dynamically based on project + environment
    public static String getBaseURL() {
        if (PROJECT_PROPERTY.equals("hoco")) {
            switch (ENV_PROPERTY) {
                case "qa":
                    return "http://qa.hocoverse.com";
                case "prod":
                    return "https://demo.hocosoftware.com";
                default:
                    throw new IllegalArgumentException("Unknown environment: " + ENV_PROPERTY);
            }
        } else if (PROJECT_PROPERTY.equals("xlarge")) {
            switch (ENV_PROPERTY) {
                case "qa":
                    return "http://qa.xlargeverse.com";
                case "prod":
                    return "https://demo.xlargesoftware.com";
                default:
                    throw new IllegalArgumentException("Unknown environment: " + ENV_PROPERTY);
            }
        } else {
            throw new IllegalArgumentException("Unknown project: " + PROJECT_PROPERTY);
        }
    }

    // Page paths
    public static final String Home_PAGE_PATH = "#/";
    public static final String SPORTS_PAGE_PATH = "#/sport/pregame?v=1";
    public static final String CASINO_PAGE_PATH = "#/game/casino?pg=0";

    // Full URLs
    public static final String SPORTS_PAGE = buildURL(SPORTS_PAGE_PATH);
    public static final String CASINO_PAGE = buildURL(CASINO_PAGE_PATH);
    public static final String HOME_PAGE = getBaseURL();

    // URL builder
    public static String buildURL(String path) {
        String base = getBaseURL();
        if (path == null || path.isEmpty()) return base;
        return base + "/" + path;
    }
}


