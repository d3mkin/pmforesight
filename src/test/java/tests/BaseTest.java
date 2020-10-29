package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import helpers.ConfigManager;
import org.junit.jupiter.api.TestInstance;
import webdriver.CustomWebDriver;
import static helpers.AttachmentsHelper.*;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BaseTest {

    static protected ConfigManager configManager = new ConfigManager();

    @BeforeAll
    @Step("Tests setup")
    public static void setup() {
        //addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
        //Configuration.headless = true;
        String selenoid = System.getProperty("selenoid_url");
        String browser = System.getProperty("browser", "chrome");
        if (selenoid == null) {
            Configuration.browser = browser;
        } else {
            Configuration.browser = CustomWebDriver.class.getName();
        }
        if (Configuration.baseUrl.equals("http://localhost:8080")) {
            Configuration.baseUrl = configManager.getDefaultBaseUrl();
        }
        Configuration.timeout = 15000;
        Configuration.browserSize = "1920x1080";
        Configuration.startMaximized = configManager.getStartMaximized();
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