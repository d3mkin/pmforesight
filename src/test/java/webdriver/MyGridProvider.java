package webdriver;

import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.firefox.FirefoxDriver.PROFILE;


public class MyGridProvider implements WebDriverProvider {


    private RemoteWebDriver remoteWebDriver;

    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {
        String browserName = System.getProperty("browserName");
        switch (browserName) {
            case WebDriverRunner.FIREFOX:
                desiredCapabilities = DesiredCapabilities.firefox();
                FirefoxProfile firefoxProfile = new FirefoxProfile();
                firefoxProfile.setPreference("browser.fullscreen.autohide", true);
                firefoxProfile.setPreference("browser.fullscreen.animateUp", 0);
                desiredCapabilities.setCapability("marionette", true);
                desiredCapabilities.setCapability(PROFILE, firefoxProfile);
                break;
            case WebDriverRunner.CHROME:
                desiredCapabilities = DesiredCapabilities.chrome();
                ChromeOptions options = new ChromeOptions();
                desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
                break;
        }

        try {
            String grid = System.getProperty("grid");
            remoteWebDriver = new RemoteWebDriver(new URL(grid), desiredCapabilities);
            remoteWebDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
            remoteWebDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
            return remoteWebDriver;

        } catch (MalformedURLException e) {
            System.out.println("Error create browser");
        }
        return null;
    }
}
