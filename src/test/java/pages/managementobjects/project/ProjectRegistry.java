package pages.managementobjects.project;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Project;
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
    private final SelenideElement firstProjectRow = table.$x(".//div[@class = 'ui-widget-content slick-row even']");
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement foundCheckBox = $("div[class='slick-cell l0 r0 slick-cell-checkboxsel']");
    private final SelenideElement importFromEBudgetButton = $(".k-toolbar a[data-tooltip='Загрузить из Электронного Бюджета']");
    private final SelenideElement eBudgetDialogHeader = $("span.k-window-title");
    private final SelenideElement eBudgetProjectNameInput = $("input[aria-label='Наименование']");
    private final SelenideElement loadingImage = $("div .k-loading-mask");
    private final SelenideElement reloadButton = $("#f-reload");

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

    @Step("Найти проект {projectName} в реестре и открыть его карточку")
    public void searchAndOpenProject(String projectName) {
        searchProject(projectName);
        shouldHaveCreatedRecord(projectName);
        firstProjectRow.shouldBe(visible).click();
    }

    @Step ("Обновить данные в реестре")
    public void clickReloadButton() {
        reloadButton.click();
        loadingImage.waitUntil(not(visible), Configuration.timeout);
    }

    //TODO: Перенести метод в BaseRegistry
    @Step("Проверка отображения {projectName} в реестре")
    public void shouldHaveCreatedRecord(String projectName) {
        $x("//div[contains(text(),'"+projectName+"')]").waitUntil(visible, Configuration.timeout);
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
        $(By.xpath("//div[@class='k-widget k-window k-dialog']")).waitUntil(visible,Configuration.timeout);
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
        loadingImage.waitUntil(not(visible), 1200000);
        searchProject(project.getName());
        shouldNotHaveResults();
    }

    //TODO: Перенести в универсальный класс
    @Step ("Удалить проект из реестра")
    public void deleteProject(String projectName){
        shouldBeRegistry();
        searchProject(projectName);
        shouldHaveCreatedRecord(projectName);
        selectRow();
        clickDelete();
        acceptDelete();
        loadingImage.waitUntil(not(visible), 1200000);
        searchProject(projectName);
        shouldNotHaveResults();
    }

    @Step("Проверить что ничего не найдено")
    public void shouldNotHaveResults() {
        allRows.waitUntil(not(visible), Configuration.timeout);
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

    @Step ("Импортировать из Электронного Бюджета проект {projectName}")
    public void importProjectFromEBudget(String projectName) {
        importFromEBudgetButton.shouldBe(visible).click();
        eBudgetDialogHeader.waitUntil(visible, Configuration.timeout).shouldHave(text("Доступные для импорта проекты"));
        eBudgetProjectNameInput.shouldBe(visible).setValue(projectName);
        $x("//li[contains(text(),'"+projectName+"')]").waitUntil(visible, Configuration.timeout).click();
        $x("//td[contains(text(),'" + projectName + "')]").waitUntil(visible, Configuration.timeout).click();
        $x("//button[contains(text(),'Импорт')]").click();
        eBudgetDialogHeader.waitUntil(text("Загруженные проекты"), 1200000);
        $("div.k-grid-content tr td div").shouldBe(visible).shouldHave(text("Проект успешно загружен"));
        $x("//button[contains(text(),'Закрыть')]").shouldBe(visible).click();
    }
}
