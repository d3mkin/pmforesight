package tests;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeAll;
import helpers.ConfigManager;
import org.junit.jupiter.api.TestInstance;
import webdriver.CustomWebDriver;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BaseTest {

  //  static protected ConfigManager configManager = new ConfigManager();

    @BeforeAll
    @Step("Tests setup")
    public static void setup() {
        //addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        //Configuration.headless = true;
        Configuration.timeout = 15000;
        Configuration.browserSize = "1920x1080";
        //Configuration.startMaximized = configManager.getStartMaximized();
        Configuration.startMaximized = true;
        Configuration.baseUrl = System.getProperty("baseUrl","http://tgr.hera.test.local");
        String selenoid = System.getProperty("selenoid_url");
        String browser = System.getProperty("browser", "chrome");
        if (selenoid == null) {
            Configuration.browser = browser;
        } else {
            Configuration.browser = CustomWebDriver.class.getName();
        }
//        if (Configuration.baseUrl.equals("http://localhost:8080")) {
//            Configuration.baseUrl = configManager.getBaseUrl();
//        }
    }

//    @AfterEach
//    @Step("Attachments")
//    void afterEach() {
//        attachScreenshot("Last screenshot");
//        attachPageSource();
//        attachBrowserConsoleLogs();
//    }

//    @AfterAll
//    @Step("Tests teardown")
//    public static void closeBrowser() {
//        WebDriverRunner.clearBrowserCache();
//        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
//        WebDriverRunner.getWebDriver().close();
//    }
}