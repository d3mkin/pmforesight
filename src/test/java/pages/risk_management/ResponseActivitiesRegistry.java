package pages.risk_management;

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
 * Мероприятия реагирования
 */
public class ResponseActivitiesRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/register?view=RiskResponding";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");

    public ResponseActivitiesRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Step("Открыть реестр 'Мероприятия реагирования' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Мероприятия реагирования' через меню")
    public void openFromMenu() {
        mainMenu.managementOfRisks().openRiskResponding();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Мероприятия реагирования'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }
    @Step("Проверка корректности ссылки {this.url}")
    public ResponseActivitiesRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public ResponseActivitiesRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public ResponseActivitiesRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
