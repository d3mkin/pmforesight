package pages.risk_management;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.DeleteEntityDialog;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.sleep;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Риски и возможности
 */
public class RisksAndOpportunitiesRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=risk";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");
    private ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row");
    private SelenideElement firstFoundRow = table.$(".grid-canvas div.slick-row:first-child");
    private SelenideElement firstFoundCheckBox = $(".slick-cell-checkboxsel input");
    protected SelenideElement ComboBoxTypeSearch = $(By.xpath("//span[contains(@aria-live,\"polite\")]"));
    protected SelenideElement TypeCSearchAll = $(By.xpath("//li[contains(@class,\"k-item\") and text()='Все']"));
    protected SelenideElement TypeCSearchAllMy = $(By.xpath("//li[contains(@class,\"k-item\") and text()='Все мои риски и возможности']"));
    private final SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));

    public RisksAndOpportunitiesRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Step("Открыть реестр 'Риски и возможности' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Риски и возможности' через меню")
    public void openFromMenu() {
        mainMenu.managementOfRisks().openRisks();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Риски и возможности'")
    public void shouldBeRegistry() {
        sleep(3000);
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();

    }
    @Step("Проверка корректности ссылки {this.url}")
    public RisksAndOpportunitiesRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public RisksAndOpportunitiesRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Риски и возможности"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public RisksAndOpportunitiesRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    public void searchRisk(String name) {
        controlPanel.typeSearchValue(name);
    }

    @Step("Осуществить поиск риска и возможности")
    public void searchRisksAndOpportunities(String value) {
        sleep(3000);
        controlPanel.typeSearchValue(value);
    }

    @Step("Выбрать первую запись")
    public void selectFirstRow() {
        firstFoundCheckBox.click();
    }

    @Step("Нажать кнопку удалить в реестре")
    public void clickDelete() {
        controlPanel.clickDelete();
    }

    @Step("Подтвердить удаление")
    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }

    @Step("Проверка наличия найденной записи")
    public void shouldHaveCreatedRecord(String name) {
        allFoundRow.shouldHaveSize(1);
        firstFoundRow.$(".l2.r2").shouldHave(text(name));
    }
    @Step("Выбор типа поиска из выподающего списка все")
    public void chooseTypeOfSearchAll() {
        ComboBoxTypeSearch.click();
        TypeCSearchAll.click();
    }
    @Step("Выбор типа поиска из выподающего списка все мои риски и возможности")
    public void chooseTypeOfSearchAllMy() {
        ComboBoxTypeSearch.click();
        TypeCSearchAllMy.click();
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
    }

    @Step ("Проверить что Риск/Возможность не отображается в реестре")
    public void checkRiskOrOpportunityNotExist(String riskOrOpportunityName){
        checkRegistryIsLoaded();
        searchRisksAndOpportunities(riskOrOpportunityName);
        shouldNotHaveResults();
    }

    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible);
        tableWithEntities.shouldBe(visible);
    }

}
