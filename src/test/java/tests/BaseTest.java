package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import webdriver.CustomWebDriver;

import java.util.logging.Level;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BaseTest {
//    static protected ConfigManager configManager = new ConfigManager();
    @BeforeAll
    @Step("Tests setup")
    public static void setup() {
        addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false)
                .includeSelenideSteps(true)
                .enableLogs(LogType.BROWSER, Level.SEVERE));
        Configuration.timeout = 60000;
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = System.getProperty("baseUrl","http://tgr.hera.test.local");
        String selenoid = System.getProperty("selenoid_url");
        String browser = System.getProperty("browser", "chrome");
        if (selenoid == null) {
            Configuration.browser = browser;
//            Configuration.headless = true;
        } else {
            Configuration.browser = CustomWebDriver.class.getName();
        }
    }

    @AfterAll
    @Step("Tests teardown")
    public static void closeBrowser() {
        WebDriverRunner.clearBrowserCache();
        WebDriverRunner.getWebDriver().manage().deleteAllCookies();
        WebDriverRunner.getWebDriver().close();
    }
}