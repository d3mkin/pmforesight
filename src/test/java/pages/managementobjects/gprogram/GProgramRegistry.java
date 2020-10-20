package pages.managementobjects.gprogram;

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
 * Госпрограммы и объекты госпрограмм
 */
public class GProgramRegistry implements Registry {
    private final Header header;
    private MainMenu mainMenu;
    private ControlPanel controlPanel;
    private String url = Configuration.baseUrl + "/page/register?view=gprogram";
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $(By.xpath("//div[@class='slick-viewport']"));
    private SelenideElement firstFoundRow = $(".slick-row.even");
    private SelenideElement firstFoundCheckBox = $(".slick-cell-checkboxsel input");
    private ElementsCollection allFoundRow = $$(".grid-canvas div.slick-row");

    public GProgramRegistry() {
        this.mainMenu = new MainMenu();
        this.header = new Header();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Госпрограммы и объекты госпрограмм' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Госпрограммы и объекты госпрограмм' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openGProgramAndObjects();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Госпрограммы и объекты госпрограмм'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public GProgramRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Проверка наличия имени реестра")
    public GProgramRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public GProgramRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step("Осуществить поиск контракта")
    public void searchGprogram(String value) {
        controlPanel.typeSearchValue(value);
    }

    @Step("Проверка отображения Госпрограммы после создания записи")
    public void shouldHaveCreatedRecord() {
        firstFoundRow.shouldBe(visible);
    }

    @Step("Проверка наличия найденной записи")
    public void shouldHaveCreatedRecord(String name) {
        allFoundRow.shouldHaveSize(1);
        firstFoundRow.$(".l2.r2").shouldHave(text(name));
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
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }
}
