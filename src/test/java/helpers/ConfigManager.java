package helpers;

public class ConfigManager {
    private static final String PROPERTIES_FILE = "selenide.properties";
    private final PropertiesResourceManager prm;
    private final String baseUrl;
    private final Long defaultTimeout;
    private final Boolean startMaximized;

    public ConfigManager() {
        this.prm = new PropertiesResourceManager(PROPERTIES_FILE);
        this.baseUrl = prm.getProperty("defaultBaseUrl");
        this.defaultTimeout = Long.parseLong(prm.getProperty("defaultTimeout"));
        this.startMaximized = Boolean.parseBoolean(prm.getProperty("startMaximized"));
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Long getDefaultTimeout() {
        return defaultTimeout;
    }

    public Boolean getStartMaximized() {
        return startMaximized;
    }
}
