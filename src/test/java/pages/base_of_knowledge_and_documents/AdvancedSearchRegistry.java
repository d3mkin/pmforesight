package pages.base_of_knowledge_and_documents;

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
 * Расширенный поиск
 */
public class AdvancedSearchRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/DocSearch";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $(".f-page__name");
    private SelenideElement table = $(" div.k-grid-header");

    public AdvancedSearchRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Override
    @Step("Открыть реестр 'Расширенный поиск' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Расширенный поиск ' через меню")
    public void openFromMenu() {
        mainMenu.knowledgeBaseAndDocuments().openExtendsSearch();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Расширенный поиск'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public AdvancedSearchRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public AdvancedSearchRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public AdvancedSearchRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
