package pages.monitoring_and_control;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Отчёты
 */
public class StatusReporRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/register?view=repReport";
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");
    private SelenideElement windowReports = $(By.xpath("//div[contains(@class,\"f-application__middle\")]"));

    public StatusReporRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }


    @Step("Открыть реестр 'Отчёты' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Отчёты' через меню")
    public void openFromMenu() {
        mainMenu.monitoringAndControl().openStatusReports();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Отчёты'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public StatusReporRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public StatusReporRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public StatusReporRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
