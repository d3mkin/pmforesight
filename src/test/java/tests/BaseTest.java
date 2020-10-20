package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import helpers.ConfigManager;
import helpers.CredentialsManager;
import webdriver.MyGridProvider;

public class BaseTest {

    static protected ConfigManager configManager = new ConfigManager();
    protected CredentialsManager credentialsManager = new CredentialsManager();

    @BeforeAll
    public static void setUp() {
        //Configuration.headless = true;
        String grid = System.getProperty("grid");
        String browser = System.getProperty("browser", "chrome");
        if (grid == null) {
            Configuration.browser = browser;
        } else {
            Configuration.browser = MyGridProvider.class.getName();
        }
        if (Configuration.baseUrl.equals("http://localhost:8080")) {
            Configuration.baseUrl = configManager.getDefaultBaseUrl();
        }
        Configuration.timeout = 15000;
        Configuration.browserSize = "1920x1080";
        Configuration.startMaximized = configManager.getStartMaximized();
    }

    @AfterAll
    public static void closeBrowser() {
        WebDriverRunner.clearBrowserCache();
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
        WebDriverRunner.getWebDriver().close();

    }
}