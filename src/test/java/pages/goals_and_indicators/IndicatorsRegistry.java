package pages.goals_and_indicators;

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
 * Показатель
 */
public class IndicatorsRegistry implements Registry {
    private final Header header;
    private final MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private final String url = Configuration.baseUrl + "/page/register?view=kpi";
    private final SelenideElement mainContainer = $("#mainBodyContainer");
    private final SelenideElement registryName = $("#f-grid-title span");
    private final SelenideElement table = $(By.xpath("//div[contains(@class,\"f-grid__grid\")]"));
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public IndicatorsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Показатель' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Показатель' через меню")
    public void openFromMenu() {
        mainMenu.goalsAndIndicators().openIndicatorsRegistry();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Показатель'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }
    @Step("Проверка корректности ссылки {this.url}")
    public IndicatorsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public IndicatorsRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public IndicatorsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Осуществить поиск Показателя в реестре")
    public void searchIndicator(String indicatorName) {
        sleep(1000);
        controlPanel.typeSearchValue(indicatorName);
    }

    @Step("Проверка отображения Показателя после создания записи")
    public void shouldHaveCreatedEntity() {
        firstFoundRow.shouldBe(visible);
        sleep(1000);
    }

    public void shouldNotHaveResults() {
        allRows.shouldNot(visible);
    }

    @Step ("Проверка что Индикатор не отображается в реестре")
    public void checkIndicatorNotExist(String indicatorName){
        checkRegistryIsLoaded();
        searchIndicator(indicatorName);
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
