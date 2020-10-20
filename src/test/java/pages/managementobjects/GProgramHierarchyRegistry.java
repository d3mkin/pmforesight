package pages.managementobjects;

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
 * Объект госпрограммы
 */
public class GProgramHierarchyRegistry implements Registry {

    private MainMenu mainMenu;
    private Header header;

    private String url = Configuration.baseUrl + "/page/registerGProgramHierarhy";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $(".InlineTableViewHierarchyTable");

    public GProgramHierarchyRegistry() {
        this.mainMenu = new MainMenu();
        this.header = new Header();
    }
    @Step("Открыть реестр 'Объект госпрограммы' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Объект госпрограммы' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openGProgramHierarchy();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Объект госпрограммы'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public GProgramHierarchyRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public GProgramHierarchyRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public GProgramHierarchyRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
