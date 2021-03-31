package pages.managementobjects.portfolio;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.DeleteEntityDialog;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Портфель
 */
public class PortfolioRegistry implements Registry {
    private MainMenu mainMenu;
    private Header header;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=portfolio";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = mainContainer.$("div.f-grid__grid");
    private SelenideElement firstFoundRow = $("div.slick-cell.l3.r3");
    private SelenideElement firstFoundCheckBox = $(".slick-cell-checkboxsel input");
    private SelenideElement firstPortfolioRow = $x(".//div[@class = 'ui-widget-content slick-row even']");
    private ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row");
    private final SelenideElement foundCheckBox = $(By.cssSelector("div[class=\"slick-cell l0 r0 slick-cell-checkboxsel\"]"));
    private final SelenideElement loadingImage = $("div .k-loading-mask");

    public PortfolioRegistry() {
        this.mainMenu = new MainMenu();
        this.header = new Header();
        this.controlPanel=new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Портфель' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Портфель' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openPortfolio();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Портфель'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public PortfolioRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public PortfolioRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Портфель"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public PortfolioRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
    @Step("Осуществить поиск Портфеля")
    public void searchPortfolio(String value) {
        sleep(1000);
        controlPanel.typeSearchValue(value);
        checkRegistryIsLoaded();
    }

    @Step("Проверка отображения Портфеля после создания записи")
    public void shouldHaveCreatedRecord() {
        firstFoundRow.shouldBe(visible);
    }

    @Step("Проверка наличия найденной записи")
    public void shouldHaveCreatedRecord(String entityName) {
        $x("//div[contains(text(),'"+entityName+"')]").waitUntil(visible, Configuration.timeout);
        //firstFoundRow.shouldBe(visible);
        sleep(1000);
    }

    @Step("Выбрать первую запись")
    public void selectFirstRow() {
        firstFoundCheckBox.click();
    }

    @Step("Нажать кнопку удалить")
    public void clickDelete() {
        controlPanel.clickDelete();
    }

    @Step("Подтвердить удаление")
    public void acceptDelete() {
        $x("//div[@class='k-widget k-window k-dialog']").waitUntil(visible, Configuration.timeout);
        $x("//label[@for='dialog-check-all']").click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }
    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Выбрать найденную запись")
    public void selectRow() {
        sleep(2000);
        foundCheckBox.click();
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
        $x("//span[contains(text(),'"+viewName+"')]").shouldBe(visible);
        $("#f-grid-viewlist .k-input").shouldHave(text(""+viewName+""));
        loadingImage.waitUntil(not(visible), 1200000);
    }

    @Step ("Удалить Портфель из реестра")
    public void deleteEntity(String entityName){
        shouldBeRegistry();
        searchPortfolio(entityName);
        shouldHaveCreatedRecord(entityName);
        selectRow();
        sleep(3000);
        controlPanel.clickDelete();
        acceptDelete();
        loadingImage.waitUntil(not(visible), 1200000);
        searchPortfolio(entityName);
        shouldNotHaveResults();
    }

    @Step ("Проверить что страница загрузилась")
    public void checkRegistryIsLoaded () {
        //loadImage.shouldNotBe(visible, Duration.ofMinutes(1));
        $$(".k-loading-image").shouldBe(size(0), ofMillis(timeout));
        $(".k-loading-mask").shouldNotBe(exist, ofMillis(timeout));
    }

    @Step("Найти Портфель {portfolioName} в реестре и открыть его карточку")
    public void searchAndOpenPortfolio(String portfolioName) {
        searchPortfolio(portfolioName);
        shouldHaveCreatedRecord(portfolioName);
        firstPortfolioRow.shouldBe(visible).click();
    }
}
