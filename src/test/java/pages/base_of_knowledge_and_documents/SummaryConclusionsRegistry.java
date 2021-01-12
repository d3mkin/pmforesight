package pages.base_of_knowledge_and_documents;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Итоговый вывод
 */
public class SummaryConclusionsRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=Summary";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");
    private ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row");
    private final SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public SummaryConclusionsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
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

    @Step("Осуществить поиск извлеченных уроков")
    public void searchSummaryConclusion(String value) {
        sleep(3000);
        controlPanel.typeSearchValue(value);
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
    }

    @Step ("Проверить что Итоговый вывод не отображается в реестре")
    public void checkSummaryConclusionNotExist(String summaryConclusionName){
        checkRegistryIsLoaded();
        searchSummaryConclusion(summaryConclusionName);
        shouldNotHaveResults();
    }

    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible);
        tableWithEntities.shouldBe(visible);
    }
}
