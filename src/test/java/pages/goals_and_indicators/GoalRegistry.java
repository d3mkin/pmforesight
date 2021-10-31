package pages.goals_and_indicators;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Goal;
import pages.Registry;
import pages.auth.LogoutPage;
import pages.elements.ControlPanel;
import pages.elements.DeleteEntityDialog;
import pages.elements.Header;
import pages.elements.MainMenu;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoalRegistry implements Registry {
    private final Header header;
    private final MainMenu mainMenu;
    private final ControlPanel controlPanel;

    private final String url = Configuration.baseUrl + "/page/register?view=goal";
    private final SelenideElement mainContainer = $("#mainBodyContainer");
    private final SelenideElement registryName = $("#f-grid-title span");
    private final SelenideElement table = $x("//div[contains(@class,'f-grid__grid')]");
    private final SelenideElement firstFoundRow = table.$(".slick-row.odd");
    private final SelenideElement allRows = table.$(".grid-canvas .slick-row");
    private final SelenideElement viewList = $x("//span[@class='k-input']");
    private final SelenideElement foundCheckBox = $("div[class='slick-cell l0 r0 slick-cell-checkboxsel']");
    private final SelenideElement indicatorByIndex = $x("//div[@class='slick-cell l3 r3']//span");
    private final SelenideElement loadingImage = $("div .k-loading-mask");

    public GoalRegistry() {
        this.header = new Header();
        this.mainMenu = new MainMenu();
        this.controlPanel = new ControlPanel();
    }

    public ControlPanel controlPanel() {
        return controlPanel;
    }

    @Override
    @Step("Открыть реестр 'Цель' по прямой ссылке")
    public void open() {
        Selenide.open(url);
    }

    @Override
    @Step("Открыть реестр 'Цель' через меню")
    public void openFromMenu() {
        mainMenu.goalsAndIndicators().openGoalRegistry();
    }

    @Override
    public LogoutPage logout() {
        return header.logout();
    }

    @Override
    @Step("Проверка открытия реестра 'Цель'")
    public void shouldBeRegistry() {
        shouldHaveCorrectLink();
        shouldHaveName();
        shouldHaveContent();
    }

    @Step("Проверка корректности ссылки {this.url}")
    public GoalRegistry shouldHaveCorrectLink() {
        assertTrue(WebDriverRunner.url().startsWith(url), "Урл не соответствет " + url);
        return this;
    }

    @Step("Проверка наличия имени реестра")
    public GoalRegistry shouldHaveName() {
        registryName.shouldBe(visible);
        registryName.shouldHave(text("Цель"));
        return this;
    }

    @Step("Проверка отображения табличной части")
    public GoalRegistry shouldHaveContent() {
        table.shouldBe(visible);
        return this;
    }

    @Step ("Добавить Цель из реестра")
    public void addGoal() {
        open();
        shouldBeRegistry();
        controlPanel().clickAddButton();
    }

    @Step ("Сменить представление на {viewName}")
    public void changeView(String viewName){
        controlPanel.changeView(viewName);
        sleep(3000);
    }

    @Step("Осуществить поиск цели")
    public void searchGoal(String goal) {
        sleep(1000);
        controlPanel.typeSearchValue(goal);
    }

    @Step("Проверка отображения цели после создания записи")
    public void shouldHaveCreatedRecord() {
        firstFoundRow.shouldBe(visible);
        sleep(1000);
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

    @Step("Проверка отображения сущности после создания")
    public void shouldHaveCreatedRecord(String entityName) {
        $x("//div[contains(text(),'"+ entityName +"')]").waitUntil(visible, Configuration.timeout);
        //firstFoundRow.shouldBe(visible);
        sleep(1000);
    }

//    @Step ("Найти и удалить цель из реестра")
//    public void findAndDeleteGoal(Goal goal){
//        shouldBeRegistry();
//        searchGoal(goal.getName());
//        shouldHaveCreatedRecord();
//        selectRow();
//        clickDelete();
//        acceptDelete();
//        searchGoal(goal.getName());
//        shouldNotHaveResults();
//    }

    @Step ("Найти и удалить цель из реестра")
    public void findAndDeleteGoal(Goal goal){
        shouldBeRegistry();
        searchGoal(goal.getName());
        shouldHaveCreatedRecord(goal.getName());
        selectRow();
        clickDelete();
        acceptDelete();
        loadingImage.waitUntil(not(visible), 1200000);
        searchGoal(goal.getName());
        shouldNotHaveResults();
    }

    @Step("Проверка отсутствия результатов поиска")
    public void shouldNotHaveResults() {
        allRows.shouldNot(visible);
    }

    @Step ("Проверить корректность отображение индикатора в реестре по показателям")
    public void checkGoalIndicatorByIndex(String goalName, String indicatorStatus) {
        firstFoundRow.shouldBe(visible);
        searchGoal(goalName);
        switch (indicatorStatus) {
            case ("Нет показателей"):
                indicatorByIndex.shouldHave(attribute("data-tooltip", "Нет показателей"),
                        cssValue("color", "rgba(221, 221, 221, 1)"));
                break;
            case ("Достигнута"):
                indicatorByIndex.shouldHave(attribute("data-tooltip", "Достигнута"),
                        cssValue("color", "rgba(34, 167, 127, 1)"));
                break;
            case ("Частично достигнута"):
                indicatorByIndex.shouldHave(attribute("data-tooltip", "Частично достигнута"),
                        cssValue("color", "rgba(255, 210, 70, 1)"));
                break;
            case ("Не достигнута"):
                indicatorByIndex.shouldHave(attribute("data-tooltip", "Не достигнута"),
                        cssValue("color", "rgba(215, 79, 85, 1)"));
                break;
            case ("Нет данных"):
                indicatorByIndex.shouldHave(attribute("data-tooltip", "Нет данных"),
                        cssValue("color", "rgba(102, 102, 102, 1)"));
                break;
            default:
                break;
        }
    }
}
