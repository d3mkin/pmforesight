package pages.managementobjects.nonprojectevent;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.NonProjectEvent;
import org.openqa.selenium.By;
import pages.BasePage;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.cssValue;
import static com.codeborne.selenide.Selenide.*;

public class NonProjectEventPage extends BasePage {
    private final SelenideElement eventNameInput = $("#Name");
    private final SelenideElement parentEntityInput = $("#control-group-ParentId span.k-widget.k-dropdown");
    private final SelenideElement tabRoles = $("#tab2");
    private final SelenideElement curatorRole = $("#control-group-Customer span.k-widget.k-dropdown");
    private final SelenideElement leaderRole = $("#control-group-Leader span.k-widget.k-dropdown");
    //Общие
    private final SelenideElement currentNonProjectEventStage = $(".f-card__info");
    //Календарный план
    private final SelenideElement editGanttButton = $x("//a[@id='startEditing']");
    private final SelenideElement levelDownButton = $x("//a[@id='tbBtnLevelDown']");
    private final SelenideElement saveGanttButton = $x("//a[@id='saveChanges']");
    private final SelenideElement maximizeOrMinimizeGanttButton = $x("//a[@id='tbBtnFullScreen']");
    private final SelenideElement newPointAddButton = $x("//a[@id='tbBtnCreatePoint']");
    private final SelenideElement newWorkOrStageAddButton = $x("//a[@id='tbBtnCreateTask']");
    private final SelenideElement newGanttActivityNameTR = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]"));
    private final SelenideElement newGanttActivityNameTD = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[7]"));
    private final SelenideElement newGanttActivityNameInput = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[7]//div"));
    private final SelenideElement newGanttActivityApproveDocTD = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[18]//div"));
    private final SelenideElement newGanttActivityCheckBox = $x("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[2]");
    private final SelenideElement newGanttActivityApproveDocSelect = $(".editor .k-select");
    private final SelenideElement newGanttActivityStatusTitle = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]//td[3]//img"));
    //Открытые вопросы
    private final SelenideElement addOpenQuestionButton = $("#tab-lov a[data-tooltip='Добавить']");
    //Основные вкладки
    private final SelenideElement tabMeetings = $("a[href='#tab-meeting']");
    private final SelenideElement tabResults = $("a[href='#tab-result']");
    private final SelenideElement tabIndicators = $("a[href='#tab-kpi']");
    private final SelenideElement tabActivity = $("a[href='#tab-activity']");
    private final SelenideElement tabOrders = $("a[href='#tab-order']");
    private final SelenideElement tabOpenQuestions = $("a[href='#tab-lov']");
    //Показатели
    private final SelenideElement indicatorAddButton = $("#KPIDivContent .itv-add-button[data-tooltip='Показатель']");
    //Таблицы показателей
    private final SelenideElement objectIndicatorsTable = $("#KPIDivContent .k-grid-content");
    private final SelenideElement objectIndicatorsTableHeader = $x("//span[contains(text(),'Показатели объекта')]");
    private final SelenideElement indicatorTableSearchInput = $("#KPIDivContent input[placeholder='Поиск...']");
    private final SelenideElement firstFoundIndicator = $x("//div[@id='KPIDivContent']//tbody/tr[1]/td[2]");
    //Результаты
    private final SelenideElement addResultButton = $("#ResultDivContent .itv-add-button[data-tooltip='Результат']");
    private final SelenideElement resultsTable = $("#ResultDivContent inlinetableview");
    private final SelenideElement resultsTableHeader = $x("//span[contains(text(),'Подчинённые результаты')]");
    private final SelenideElement resultsSearch = $("#ResultDivContent input[placeholder='Поиск...']");
    private final SelenideElement firstFoundResult = $x("//div[@id='ResultDivContent']//tbody/tr[1]/td[2]");
    //Поручения
    private final SelenideElement addOrderButton = $("#OrderInlineTable a[data-tooltip='Добавить']");
    //Совещания
    private final SelenideElement addMeetingButton = $("#tab-meeting a[data-tooltip='Добавить']");


    public void fillFields(NonProjectEvent event) {
        typeText(eventNameInput, event.getName());
        searchAndSelectFirstFromSelect(parentEntityInput, event.getParentEntity());
        tabRoles.click();
        curatorRole.waitUntil(visible, 1000);
        searchAndSelectFirstFromSelect(curatorRole, event.getCurator());
        sleep(500);
        searchAndSelectFirstFromSelect(leaderRole, event.getLeader());
    }

    //TODO: Перенести в BasePage и сделать рефактор кода
    @Step("Проверить текущую стадию Непроектного мероприятия")
    public void checkCurrentStage(String stage) {
        currentNonProjectEventStage.waitUntil(text(stage), 30000);
    }

    @Step ("Открыть вкладку Показатели")
    public void openIndicatorsTab(){
        tabIndicators.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку Добавить показатель")
    public void clickAddIndicator(){
        indicatorAddButton.click();
    }

    @Step ("Нажать кнопку Добавить результат")
    public void clickAddResult() {
        addResultButton.click();
    }

    @Step ("Проверить наличие таблицы 'Показатели объекта'")
    public void shouldHaveIndicatorsTable() {
        objectIndicatorsTable.shouldBe(visible);
        objectIndicatorsTableHeader.shouldHave(text("Показатели объекта"));
    }

    @Step ("Проверить наличие показателя в таблице 'Персональные показатели проекта'")
    public void shouldHaveIndicator(String indicatorName) {
        indicatorTableSearchInput.click();
        indicatorTableSearchInput.sendKeys(indicatorName);
        firstFoundIndicator.shouldBe(visible);
        firstFoundIndicator.shouldHave(text(indicatorName));
    }

    @Step("Открыть вкладку Результаты")
    public void openResultsTab() {
        tabResults.click();
        sleep(1000);
    }

    @Step ("Проверка наличия таблицы подчиненных результатов")
    public void shouldHaveResultsTable() {
        resultsTable.shouldBe(visible);
        resultsTableHeader.shouldHave(text("Подчинённые результаты"));
    }

    @Step ("Проверить наличие результата в таблице")
    public void shouldHaveResult(String resultName){
        resultsSearch.click();
        resultsSearch.sendKeys(resultName);
        firstFoundResult.shouldBe(visible).shouldHave(text(resultName));
    }

    @Step ("Открыть вкладку Календарный план")
    public void openActivityTab(){
        tabActivity.click();
        sleep(1000);
    }

    @Step("Перевести Гант в полноэкранный или оконный режим просмотра")
    public void clickToMaximizeOrMinimizeGantt(){
        switchTo().frame("ganttframe");
        maximizeOrMinimizeGanttButton.shouldBe(visible).click();
        switchTo().defaultContent();
    }

    @Step("Перевести Гант в режим редактирования")
    public void clickEditGantt(){
        switchTo().frame("ganttframe");
        editGanttButton.shouldBe(visible).click();
        switchTo().defaultContent();
    }

    @Step ("Понизить уровень сущности")
    public void clickToDownEntityLevel() {
        levelDownButton.click();
    }

    @Step("Добавить на Гант КТ с назаванием {pointName} и утверждающим документом {approvingDoc}")
    public void addNewPointInGantt(String pointName, String approvingDoc){
        switchTo().frame("ganttframe");
        newPointAddButton.shouldBe(visible).click();
        newGanttActivityNameTR.shouldBe(visible).click();
        newGanttActivityNameTD.shouldBe(visible).click();
        newGanttActivityNameInput.shouldBe(visible).click();
        $x("//div[@class='editor']/input").sendKeys(pointName);
        newGanttActivityNameTR.shouldBe(visible).click();
        sleep(3000);
        newGanttActivityApproveDocTD.shouldBe(visible).click();
        newGanttActivityApproveDocSelect.shouldBe(visible).click();
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").setValue(approvingDoc);
        sleep(3000);
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").pressEnter();
        saveGanttButton.click();
        newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "В работе по плану"));
        switchTo().defaultContent();
    }

    @Step ("Добавить на Гант Работу с названием {workName} и утверждающим документом {approvingDoc}")
    public void addNewWorkInGantt (String workName, String approvingDoc) {
        switchTo().frame("ganttframe");
        newWorkOrStageAddButton.shouldBe(visible).click();
        newGanttActivityNameTR.shouldBe(visible).click();
        newGanttActivityNameTD.shouldBe(visible).click();
        newGanttActivityNameInput.shouldBe(visible).click();
        $x("//div[@class='editor']/input").sendKeys(workName);
        newGanttActivityNameTR.shouldBe(visible).click();
        sleep(3000);
        newGanttActivityApproveDocTD.shouldBe(visible).click();
        newGanttActivityApproveDocSelect.shouldBe(visible).click();
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").setValue(approvingDoc);
        sleep(3000);
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").pressEnter();
        saveGanttButton.click();
        newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "В работе по плану"));
        switchTo().defaultContent();
    }

    @Step ("Добавить на Гант Этап с названием {stageName} и утверждающим документом {approvingDoc}")
    public void addNewStageInGantt(String stageName, String approvingDoc) {
        switchTo().frame("ganttframe");
        newWorkOrStageAddButton.shouldBe(visible).click();
        newGanttActivityNameTR.shouldBe(visible).click();
        newGanttActivityNameTD.shouldBe(visible).click();
        newGanttActivityNameInput.shouldBe(visible).click();
        $x("//div[@class='editor']/input").sendKeys(stageName);
        newGanttActivityNameTR.shouldBe(visible).click();
        sleep(3000);
        newGanttActivityApproveDocTD.shouldBe(visible).click();
        newGanttActivityApproveDocSelect.shouldBe(visible).click();
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").setValue(approvingDoc);
        sleep(3000);
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").pressEnter();
        newPointAddButton.shouldBe(visible).click();
        newGanttActivityNameTR.shouldBe(visible).click();
        newGanttActivityNameTD.shouldBe(visible).click();
        newGanttActivityNameInput.shouldBe(visible).click();
        $x("//div[@class='editor']/input").sendKeys("КТ для этапа");
        newGanttActivityNameTR.shouldBe(visible).click();
        sleep(3000);
        newGanttActivityApproveDocTD.shouldBe(visible).click();
        newGanttActivityApproveDocSelect.shouldBe(visible).click();
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").setValue(approvingDoc);
        sleep(3000);
        $("div.k-animation-container[aria-hidden='false'] .k-textbox").pressEnter();
        newGanttActivityCheckBox.click();
        clickToDownEntityLevel();
        saveGanttButton.click();
        newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "В работе по плану"));
        switchTo().defaultContent();
    }

    @Step ("Открыть вкладку Поручения")
    public void openOrdersTab(){
        tabOrders.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку 'Добавить Поручение'")
    public void clickAddOrder () {
        addOrderButton.shouldBe(visible).click();
    }

    @Step("Проверить наличие Поручения в таблице Поручений")
    public void checkOrderPresentInTable(String orderName){
        $x("//div[@id='OrderInlineTable']//td//a[contains(text(),'"+ orderName +"')]").shouldBe(visible);
    }

    @Step("Открыть вкладку Совещания")
    public void openMeetingTab() {
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
        tabOpenQuestions.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку 'Добавить Открытый вопрос'")
    public void clickAddOpenQuestion () {
        addOpenQuestionButton.shouldBe(visible).click();
    }

    @Step("Проверить наличие Открытого вопроса в таблице Открытых вопросов")
    public void checkOpenQuestionPresentInTable(String questionName){
        $x("//td[@data-column-field='Name']//a[contains(text(),'"+ questionName +"')]").shouldBe(visible);
    }
}
