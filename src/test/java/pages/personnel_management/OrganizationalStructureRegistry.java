package pages.personnel_management;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
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
 * Организационная структура
 */
public class OrganizationalStructureRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/OrgStruct";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $(".f-page__name");
    private SelenideElement table = $(By.xpath("//div[contains(@id,\"orgchart__content-chart\")]"));

    public OrganizationalStructureRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Step("Открыть реестр 'Организационная структура' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Организационная структура' через меню")
    public void openFromMenu() {
        mainMenu.personnelManagement().openOrgStructure();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Организационная структура'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public OrganizationalStructureRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public OrganizationalStructureRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public OrganizationalStructureRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
