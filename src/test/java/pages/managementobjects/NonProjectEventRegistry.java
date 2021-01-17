package pages.managementobjects;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.DeleteEntityDialog;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Непроектные мероприятия
 */
public class NonProjectEventRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=event";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = mainContainer.$("div.f-grid__grid");
    private SelenideElement loadImage = mainContainer.$(".k-loading-image");
    private final SelenideElement tableWithEntities = $ (By.xpath("//div[@class='slick-viewport']"));
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement foundCheckBox = $(By.cssSelector("div[class=\"slick-cell l0 r0 slick-cell-checkboxsel\"]"));
    private final SelenideElement loadingImage = $("div .k-loading-mask");

    public NonProjectEventRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Непроектные мероприятия' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Непроектные мероприятия' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openNotProjectEvent();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Непроектные мероприятия'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public NonProjectEventRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Поиск мероприятия {eventName} в реестре")
    public void searchEvent(String eventName) {
        sleep(1000);
        controlPanel.typeSearchValue(eventName);
        //controlPanel.clickSearch();
    }

    @Step("Проверка наличия имени реестра")
    public NonProjectEventRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отсутствия результатов поиска")
    private void shouldNotHaveResults() {
        allRows.waitUntil(not(visible), Configuration.timeout);
    }

    @Step("Проверка отображения табличной части")
    public NonProjectEventRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
        $x("//span[contains(text(),'"+viewName+"')]").shouldBe(visible);
        $("#f-grid-viewlist .k-input").shouldHave(text(""+viewName+""));
    }

    @Step ("Проверить что реестр загрузился")
    public void checkRegistryIsLoaded () {
        loadImage.shouldNotBe(visible);
        tableWithEntities.shouldBe(visible);
    }

    @Step ("Проверка что Мероприятие не отображается в реестре")
    public void checkEventNotExist(String entityName){
        checkRegistryIsLoaded();
        searchEvent(entityName);
        shouldNotHaveResults();
    }

    @Step("Проверка отображения сущности")
    public void shouldHaveCreatedRecord(String programName) {
        $x("//div[contains(text(),'"+programName+"')]").waitUntil(visible, Configuration.timeout);
        //firstFoundRow.shouldBe(visible);
        sleep(1000);
    }

    @Step("Выбрать найденную запись")
    public void selectRow() {
        sleep(1000);
        foundCheckBox.click();
    }

    @Step ("Нажать удалить")
    public void clickDelete() {
        controlPanel.clickDelete();
    }

    @Step ("Подтвердить удаление")
    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step ("Удалить Непроектное мероприятие из реестра")
    public void deleteEntity(String entityName){
        shouldBeRegistry();
        searchEvent(entityName);
        shouldHaveCreatedRecord(entityName);
        selectRow();
        clickDelete();
        acceptDelete();
        loadingImage.waitUntil(not(visible), 1200000);
        searchEvent(entityName);
        shouldNotHaveResults();
    }

}
