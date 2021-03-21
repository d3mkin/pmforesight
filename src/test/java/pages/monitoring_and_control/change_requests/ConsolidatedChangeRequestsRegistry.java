package pages.monitoring_and_control.change_requests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Запросы на изменение
 */
public class ConsolidatedChangeRequestsRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/register?view=ConsolidatedChangeRequest";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");

    public ConsolidatedChangeRequestsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Step("Открыть реестр 'Запросы на изменение' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Запросы на изменение' через меню")
    public void openFromMenu() {
        mainMenu.monitoringAndControl().openRequestsForChange();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Запросы на изменение'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public ConsolidatedChangeRequestsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public ConsolidatedChangeRequestsRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public ConsolidatedChangeRequestsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

}
