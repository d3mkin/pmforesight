package pages.directories;

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
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Сотрудники
 */
public class EmployeesRegistry implements Registry {

    private final Header header;
    private final MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private final String url = Configuration.baseUrl + "/page/register?view=User";
    private final SelenideElement mainContainer = $("#mainBodyContainer");
    private final SelenideElement registryName = $("#f-grid-title span");
    private final SelenideElement table = $("div.f-grid__grid");
    private final ElementsCollection allFoundRow = table.$$(".grid-canvas div.slick-row");
    private final SelenideElement firstFoundRow = table.$(".grid-canvas div.slick-row:first-child");
    private final SelenideElement firstFoundCheckBox = $(".slick-cell-checkboxsel input");
    private final SelenideElement userIsTop = $(By.xpath("//div[@class='slick-cell l12 r12']"));
    private final SelenideElement userGroupsMember = $(By.xpath("//div[@class='slick-cell l17 r17']"));


    public EmployeesRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    @Override
    @Step("Открыть реестр 'Сотрудники' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Сотрудники' через меню")
    public void openFromMenu() {
        mainMenu.catalogs().openEmployees();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Сотрудники'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public EmployeesRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public EmployeesRegistry shouldHaveName() {
        registryName.shouldHave(text("Сотрудники"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public EmployeesRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    public Header header() {
        return header;
    }

    public MainMenu mainMenu() {
        return mainMenu;
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    public void searchEmployees(String name) {
        sleep(1000);
        controlPanel.typeSearchValue(name);
    }

    @Step("Проверка наличия найденной записи")
    public void shouldHaveCreatedRecord(String name) {
        allFoundRow.shouldHaveSize(1);
        firstFoundRow.$(".l1.r1").shouldHave(text(name));
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
        $("a[href='#tab-main']").shouldBe(visible).click();
        $(By.xpath("//label[@for='dialog-check-all']")).click();
        new DeleteEntityDialog().clickDeleteYes();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allFoundRow.shouldHaveSize(0);
    }

    @Step ("Добавить пользователя из реестра")
    public void addUser() {
        open();
        shouldBeRegistry();
        controlPanel().clickAddButton();
    }

    @Step ("Проверить что пользователь входит в Топ руководство")
    public void checkIsTop (String statusTop) {
        userIsTop.shouldHave(text(statusTop));
    }

    @Step ("Проверить что пользователь входит в группу")
    public void checkUserIsMemberOfGroup (String groupName) {
        userGroupsMember.shouldHave(text(groupName));
    }

    @Step ("Проверить что пользователь НЕ входит в группу")
    public void checkUserIsNotMemberOfGroup (String groupName) {
        userGroupsMember.shouldNotHave(text(groupName));
    }

    @Step ("Открыть карточку сотрудника")
    public void openUserCard () {
        firstFoundRow.click();
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
    }

    @Step ("Удалить пользователя из реестра")
    public void deleteUser(String userName){
        shouldBeRegistry();
        searchEmployees(userName);
        shouldHaveCreatedRecord(userName);
        selectFirstRow();
        clickDelete();
        acceptDelete();
        searchEmployees(userName);
        shouldNotHaveResults();
    }
}
