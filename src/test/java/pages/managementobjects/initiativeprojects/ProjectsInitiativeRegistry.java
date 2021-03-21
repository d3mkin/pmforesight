package pages.managementobjects.initiativeprojects;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Предложения по проекту
 */
public class ProjectsInitiativeRegistry implements Registry {
    private Header header;
    private MainMenu mainMenu;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=initiative";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = mainContainer.$("div.f-grid__grid");
    private SelenideElement firstFoundRow = $("div.slick-cell:not(.slick-group).l3.r3");
    private SelenideElement firstFoundCheckBox = $(".slick-cell-checkboxsel input");
    private ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row:not(.slick-group)");

    public ProjectsInitiativeRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Предложения по проекту' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Предложения по проекту' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openProjectsInitiative();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }
    public ControlPanel controlPanel() {
        return controlPanel;
    }
    @Override
    @Step("Проверка открытия реестра 'Предложения по проекту'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }
    @Step("Осуществить поиск контракта")
    public void searchGProgram(String value) {
        controlPanel.typeSearchValue(value);
    }

    @Step("Проверка корректности ссылки {this.url}")
    public ProjectsInitiativeRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public ProjectsInitiativeRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public ProjectsInitiativeRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
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
    @Step("Проверка отображения Госпрограммы после создания записи")
    public void shouldHaveCreatedRecord() {
        firstFoundRow.shouldBe(visible);
    }
    @Step("Проверка наличия найденной записи")
    public void shouldHaveCreatedRecord(String name) {
        allFoundRow.shouldHaveSize(1);
        firstFoundRow.shouldHave(text(name));
    }
}
