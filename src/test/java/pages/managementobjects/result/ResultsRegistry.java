package pages.managementobjects.result;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
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
 * Результаты
 */
public class ResultsRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=ActivityResult";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = mainContainer.$("div.f-grid__grid");
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public ResultsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }
    @Override
    @Step("Открыть реестр 'Результаты' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Результаты' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openResults();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Результаты'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public ResultsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public ResultsRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public ResultsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Осуществить поиск сущности в реестре")
    public void searchEntity(String entityName) {
        sleep(1000);
        controlPanel.typeSearchValue(entityName);
    }

    @Step("Проверка отображения совещания после создания записи")
    public void shouldHaveCreatedEntity() {
        firstFoundRow.shouldBe(visible);
        sleep(1000);
    }

    @Step ("Проверка что нет данных в таблице реестра")
    public void shouldNotHaveResults() {
        allRows.shouldNot(visible);
    }

    @Step ("Проверка что сущность не отображается в реестре")
    public void checkEntityNotExist(String entityName){
        checkRegistryIsLoaded();
        searchEntity(entityName);
        shouldNotHaveResults();
    }

    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible);
        tableWithEntities.shouldBe(visible);
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
    }
}
