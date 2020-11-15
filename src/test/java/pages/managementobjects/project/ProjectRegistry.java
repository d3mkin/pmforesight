package pages.managementobjects.project;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Project;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.DeleteEntityDialog;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Проекты
 */
public class ProjectRegistry implements Registry {
    private final MainMenu mainMenu;
    private final Header header;
    private final ControlPanel controlPanel;

    private final String url = Configuration.baseUrl + "/page/register?view=project";
    private final SelenideElement mainContainer = $("#mainBodyContainer");
    private final SelenideElement registryName = $("#f-grid-title span");
    private final SelenideElement table = mainContainer.$("div.f-grid__grid");
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement firstProjectRow = table.$(By.xpath(".//div[@class = 'ui-widget-content slick-row even']"));
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement foundCheckBox = $(By.cssSelector("div[class='slick-cell l0 r0 slick-cell-checkboxsel']"));

    public ProjectRegistry() {
        this.mainMenu = new MainMenu();
        this.header = new Header();
        this.controlPanel = new ControlPanel();
    }
    @Override
    @Step("Открыть реестр 'Проекты' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Step("Поиск проекта {projectName} в реестре")
    public void searchProject(String projectName) {
        sleep(1000);
        controlPanel.typeSearchValue(projectName);
        //controlPanel.clickSearch();
    }

    //TODO: Перенести метод в BaseRegistry
    @Step("Проверка отображения {projectName} в реестре")
    public void shouldHaveCreatedRecord(String projectName) {
        $x("//div[contains(text(),'"+projectName+"')]");
        //firstFoundRow.shouldBe(visible);
        sleep(1000);
    }

    public void clickFirstRow() {
        firstProjectRow.click();
    }

    @Override
    @Step("Открыть реестр 'Проекты' через меню")
    public void openFromMenu() {
        mainMenu.managementObjects().openProjects();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Проекты'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public ProjectRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public ProjectRegistry shouldHaveName() {
        registryName.shouldBe(visible).shouldHave(text("Проекты"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public ProjectRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Step("Выбрать найденную запись")
    public void selectRow() {
        foundCheckBox.click();
        sleep(1000);
    }

    @Step ("Удалить выбранную запись")
    public void clickDelete() {
        controlPanel.clickDelete();
    }

    @Step("Подтвердить удаление")
    public void acceptDelete() {
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).shouldBe(visible);
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step ("Удалить проект из реестра")
    public void deleteProject(Project project){
        shouldBeRegistry();
        searchProject(project.getName());
        shouldHaveCreatedRecord(project.getName());
        selectRow();
        clickDelete();
        acceptDelete();
        searchProject(project.getName());
        shouldNotHaveResults();
    }

    @Step ("Удалить проект из реестра")
    public void deleteProject(String projectName){
        shouldBeRegistry();
        searchProject(projectName);
        shouldHaveCreatedRecord(projectName);
        selectRow();
        clickDelete();
        acceptDelete();
        searchProject(projectName);
        shouldNotHaveResults();
    }


    public void shouldNotHaveResults() {
        allRows.shouldNot(visible);
    }

    @Step ("Добавить проект из реестра")
    public void addProject() {
        open();
        shouldBeRegistry();
        controlPanel().clickAddButton();
    }

    //TODO: Перенести метод в BaseRegistry
    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
        $x("//span[contains(text(),'"+viewName+"')]").shouldBe(visible);
        $("#f-grid-viewlist .k-input").shouldHave(text(""+viewName+""));
    }
}
