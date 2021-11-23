package pages.managementobjects.portfolio;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Portfolio;
import pages.BasePage;

import java.util.ArrayList;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class PortfolioPage extends BasePage {
    private SelenideElement tabCommonInfo = $(By.linkText("Общая информация"));
    private SelenideElement tabRoles = $(By.linkText("Роли"));
    private SelenideElement name=$("#Name");
    private SelenideElement description=$("#Description");
    private SelenideElement target=$("#control-group-ActivityGoal");
    private SelenideElement supervisor=$("#control-group-Leader .k-input");
    private SelenideElement administrator=$("#control-group-Administrator");
    private SelenideElement workingGroup=$("#control-group-WorkGroup");

    //Основные вкладки
    private final SelenideElement tabMeetings = $("a[href='#tab-meeting']");
    private final SelenideElement tabResults = $("a[href='#tab-result']");
    private final SelenideElement tabIndicators = $("a[href='#tab-kpi']");
    private final SelenideElement tabComponents = $("a[href='#tab-component']");
    private final SelenideElement tabGantt = $("a[href='#tab-gantt']");
    private final SelenideElement tabRisksOpportunities = $("a[href='#tab-risk']");
    private final SelenideElement tabLessons = $("a[href='#tab-gleaning']");
    private final SelenideElement tabOrders = $("a[href='#tab-order']");
    private final SelenideElement tabOpenQuestions = $("a[href='#tab-lov']");
    private final SelenideElement tabMain = $("a[href='#tab-main']");

    //Показатели
    private final SelenideElement indicatorAddButton = $("#KPIs a[data-tooltip='Добавить']");

    //Таблицы показателей
    private final SelenideElement objectIndicatorsTable = $("#KPIs .k-grid-content");
    private final SelenideElement objectIndicatorsTableHeader = $x("//span[contains(text(),'Показатели объекта')]");
    private final SelenideElement indicatorTableSearchInput = $("#KPIs input[placeholder='Поиск...']");
    private final SelenideElement firstFoundIndicator = $x("//div[@id='KPIs']//tbody/tr[1]/td[2]");

    //Поручения
    private final SelenideElement addOrderButton = $("#Orders a[data-tooltip='Добавить']");

    //Совещания
    private final SelenideElement addMeetingButton = $("#tab-meeting a[data-tooltip='Добавить']");

    //Открытые вопросы
    private final SelenideElement addOpenQuestionButton = $("#tab-lov a[data-tooltip='Добавить']");

    @Step("В модальном окне создания портфеля заполнить обязательные поля на вкладке 'Общая информация'")
    public void fillFields(Portfolio portfolio){
        tabCommonInfo.click();
        typeText(name,portfolio.getNameValue());
        typeText(description,portfolio.getDescription());
        searchAndSelectFirstFromSelect(target, portfolio.getPurpose());
    }

    @Step("В модальном окне создания портфеля перейти на вкладку \"Роли\" и заполнить обязательные поля")
    public void fillRoles(Portfolio portfolio){
        tabRoles.click();
        searchAndSelectFirstFromSelect(supervisor, portfolio.getSupervisor());
        searchAndSelectFirstFromSelect(administrator, portfolio.getAdministrator());
        searchAndSelectFirstFromSelect(workingGroup, portfolio.getWorkingGroup());
    }

    @Step ("Открыть вкладку Показатели")
    public void openIndicatorsTab(){
        tabIndicators.click();
        checkPageIsLoaded();
        sleep(1000);
    }

    @Step ("Нажать кнопку Добавить показатель")
    public void clickAddIndicator(){
        checkPageIsLoaded();
        indicatorAddButton.click();
    }

    @Step ("Проверить наличие таблицы 'Показатели объекта'")
    public void shouldHaveIndicatorsTable() {
        objectIndicatorsTable.shouldBe(visible);
        objectIndicatorsTableHeader.shouldHave(text("Показатели объекта"));
    }

    @Step ("Проверить наличие показателя в таблице 'Показатели объекта'")
    public void shouldHaveIndicator(String indicatorName) {
        indicatorTableSearchInput.click();
        indicatorTableSearchInput.sendKeys(indicatorName);
        firstFoundIndicator.shouldBe(visible);
        firstFoundIndicator.shouldHave(text(indicatorName));
    }

    @Step ("Открыть вкладку Поручения")
    public void openOrdersTab(){
        checkPageIsLoaded();
        tabOrders.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку 'Добавить Поручение'")
    public void clickAddOrder () {
        addOrderButton.shouldBe(visible).click();
    }

    @Step("Проверить наличие Поручения в таблице Поручений")
    public void checkOrderPresentInTable(String orderName){
        $x("//div[@id='Orders']//td//a[contains(text(),'"+ orderName +"')]").shouldBe(visible);
    }

    @Step("Открыть вкладку Совещания")
    public void openMeetingTab() {
        checkPageIsLoaded();
        tabMeetings.click();
    }

    @Step("Добавить новое Совещание из карточки Программы")
    public void clickAddMeeting() {
        addMeetingButton.click();
    }

    @Step("Проверить наличие Совещания в таблице Совещаний")
    public void checkMeetingPresentInTable(String meetingName){
        $x("//div[@id='tab-meeting']//td//a[contains(text(),'"+ meetingName +"')]").shouldBe(visible);
    }

    @Step ("Открыть вкладку Открытые вопросы")
    public void openOpenQuestionsTab(){
        checkPageIsLoaded();
        tabOpenQuestions.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку 'Добавить Открытый вопрос'")
    public void clickAddOpenQuestion () {
        addOpenQuestionButton.shouldBe(visible).click();
    }

    @Step("Проверить наличие Открытого вопроса в таблице Открытых вопросов")
    public void checkOpenQuestionPresentInTable(String questionName){
        $(By.xpath("//div[@id='tab-lov']//td//a[contains(text(),'"+ questionName +"')]")).shouldBe(visible);
    }

    @Step("Добавить в Портфель Цели {goalsNames}")
    public void addGoals(ArrayList<String> goalsNames){
        for (String goal:goalsNames) {
            checkPageIsLoaded();
            searchAndSelectFirstFromMultiSelect($("#control-group-ActivityGoal .k-widget"), goal);
            checkPageIsLoaded();
        }
    }
}
