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
 * Итоговый вывод
 */
public class SummaryConclusionsRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;

    private String url = Configuration.baseUrl + "/page/register?view=Summary";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");

    public SummaryConclusionsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
    }

    @Override
    @Step("Открыт реестр 'Итоговый вывод' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Итоговый вывод' через меню")
    public void openFromMenu() {
        mainMenu.knowledgeBaseAndDocuments().openSummary();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Итоговый вывод'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public SummaryConclusionsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public SummaryConclusionsRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public SummaryConclusionsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
