package webdriver;

import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

import static org.openqa.selenium.firefox.FirefoxDriver.PROFILE;


public class CustomWebDriver implements WebDriverProvider {

    String browserName = System.getProperty("browser");
    String selenoid = System.getProperty("selenoid_url");

    @Override
    public WebDriver createDriver(DesiredCapabilities capabilities) {
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);

        switch (browserName) {
            case WebDriverRunner.FIREFOX:
                capabilities = DesiredCapabilities.firefox();
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.fullscreen.autohide", true);
                firefoxProfile.setPreference("browser.fullscreen.animateUp", 0);
                capabilities.setCapability("marionette", true);
                capabilities.setCapability(PROFILE, firefoxProfile);
                WebDriverManager.firefoxdriver().setup();
                break;
            case WebDriverRunner.CHROME:
                capabilities.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
                capabilities.setCapability("enableVNC", true);
                capabilities.setCapability("enableVideo", false);
                capabilities.setCapability("timeZone", "Europe/Moscow");
                capabilities.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                WebDriverManager.chromedriver().setup();
                break;
            case WebDriverRunner.IE:
                capabilities.setCapability("browserName", "internet explorer");
                capabilities.setCapability("browserVersion", "11");
                capabilities.setCapability("timeZone", "Europe/Moscow");
                WebDriverManager.iedriver().setup();
                break;
        }

        if (System.getProperty("selenoid_url") != null) {
          return getRemoteWebDriver(capabilities);
        } else {
          return getLocalChromeDriver(capabilities);
        }
    }

    private WebDriver getLocalChromeDriver(DesiredCapabilities capabilities) {
        return new ChromeDriver(capabilities);
    }

    private WebDriver getRemoteWebDriver(DesiredCapabilities capabilities) {
        RemoteWebDriver remoteWebDriver = new RemoteWebDriver(getRemoteWebDriverUrl(), capabilities);
        remoteWebDriver.setFileDetector(new LocalFileDetector());
        return remoteWebDriver;
    }

    private URL getRemoteWebDriverUrl() {
        try {
            return new URL(selenoid);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-infobars");
        chromeOptions.addArguments("--lang=ru");
        return chromeOptions;
    }
}
