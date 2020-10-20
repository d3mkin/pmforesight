package pages.managementobjects.contracts;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Contract;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.DeleteEntityDialog;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Контракты
 */
public class ContractsRegistry implements Registry {
    private final Header header;
    private final MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private final String url = Configuration.baseUrl + "/page/register?view=contract";
    private final SelenideElement registryName = $("#f-grid-title span");
    private final SelenideElement mainContainer = $("#mainBodyContainer");
    private final SelenideElement table = $("div.f-grid__grid");
    private final SelenideElement tableHeader = table.$(".slick-header");
    private final SelenideElement tableRows = table.$(".slick-viewport");
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement firstFoundCheckBox = firstFoundRow.$(".slick-cell-checkboxsel input");
    private final ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row");
    private final SelenideElement foundCheckBox = $(By.cssSelector("div[class='slick-cell l0 r0 slick-cell-checkboxsel']"));
    private final SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));
    private final SelenideElement expandButton = $ (By.xpath("//a[@id='f-expand']"));



    public ContractsRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Step("Открыть реестр 'Контракты' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Контракты' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openContracts();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Контракты'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public ContractsRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public ContractsRegistry shouldHaveName() {
        registryName.shouldHave(text("Контракты"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public ContractsRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Осуществить поиск Контракта")
    public void searchContract(String contractName) {
        sleep(1000);
        controlPanel.typeSearchValue(contractName);
    }

    @Step("Проверка отображения контракта после создания записи")
    public void shouldHaveCreatedRecord() {
        firstFoundRow.shouldBe(visible);
    }

    @Step("Выбрать первую найденную запись")
    public void selectFirstRow() {
        firstFoundCheckBox.click();
    }

    public void clickDelete() {
        controlPanel.clickDelete();
    }

    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }

    public DeleteEntityDialog deleteDialog() {
        return new DeleteEntityDialog();
    }

    @Step("Подтвердить удаление")
    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Выбрать найденную запись")
    public void selectRow() {
        foundCheckBox.click();
        sleep(1000);
    }

    @Step ("Удалить контракт из реестра")
    public void deleteContract(Contract contract){
        shouldBeRegistry();
        searchContract(contract.getName());
        shouldHaveCreatedRecord();
        selectRow();
        clickDelete();
        acceptDelete();
        searchContract(contract.getName());
        shouldNotHaveResults();
    }

    @Step ("Проверка что Контракт не отображается в реестре")
    public void checkContractNotExist(String contractName){
        checkRegistryIsLoaded();
        searchContract(contractName);
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

    @Step ("Развернуть все группы в реестре")
    public void clickExpandAll () {
        expandButton.click();
    }

}
