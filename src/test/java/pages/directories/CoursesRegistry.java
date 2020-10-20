package pages.directories;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
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
 * Курсы
 */
public class CoursesRegistry implements Registry {

    private Header header;
    private MainMenu mainMenu;
    private ControlPanel controlPanel;

    private String url = Configuration.baseUrl + "/page/register?view=Course";
    private SelenideElement mainContainer = $("#mainBodyContainer");
    private SelenideElement registryName = $("#f-grid-title span");
    private SelenideElement table = $("div.f-grid__grid");
    private ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row");
    private SelenideElement firstFoundRow = table.$(".grid-canvas div.slick-row:first-child");
    private SelenideElement firstFoundCheckBox = firstFoundRow.$(".slick-cell-checkboxsel input");

    public CoursesRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Курсы' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Курсы' через меню")
    public void openFromMenu() {
        mainMenu.catalogs().openCourses();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Курсы'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
        sleep(2000);
    }

    @Step("Проверка корректности ссылки {this.url}")
    public CoursesRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public CoursesRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        return this;
    }

    @Step("Проверка отображения табличной части")
    public CoursesRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }
    public ControlPanel controlPanel() {
        return controlPanel;
    }

    public void searchContractors(String name) {
        controlPanel.typeSearchValue(name);
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
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }

    @Step("Осуществить поиск риска и возможности")
    public void searchCourse(String value) {
        sleep(3000);
        controlPanel.typeSearchValue(value);
    }

}
