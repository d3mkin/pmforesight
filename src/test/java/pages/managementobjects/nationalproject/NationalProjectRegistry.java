package pages.managementobjects.nationalproject;

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
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Мои национальные проекты
 */
public class NationalProjectRegistry implements Registry {

    private MainMenu mainMenu;
    private Header header;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=nationalproject";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName =  $("#f-grid-title span");
    private SelenideElement table = mainContainer.$("div.f-grid__grid");
    private SelenideElement firstFoundRow = $(".slick-row.even");
    private SelenideElement firstFoundCheckBox = $(".slick-cell-checkboxsel input");
    private ElementsCollection allFoundRow = $$(".grid-canvas div.slick-row");

    public NationalProjectRegistry() {
        this.mainMenu = new MainMenu();
        this.header = new Header();
        this.controlPanel = new ControlPanel();
    }

    @Step("Осуществить поиск контракта")
    public void searchGProgram(String value) {
        controlPanel.typeSearchValue(value);
    }

    @Step("Выбрать первую запись")
    public void selectFirstRow() {
        firstFoundCheckBox.click();
    }
    @Step("Проверка наличия найденной записи")
    public void shouldHaveCreatedRecord(String name) {
        allFoundRow.shouldHaveSize(1);
        firstFoundRow.$(".l2.r2").shouldHave(text(name));
    }

    @Step("Нажать кнопку удалить")
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

    @Override
    @Step("Открыть реестр 'Мои национальные проекты' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Мои национальные проекты' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openNationalProject();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Мои национальные проекты'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public NationalProjectRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public NationalProjectRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Мои национальные проекты"));
        return this;
    }
    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Проверка отображения табличной части")
    public NationalProjectRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
}
