package pages.managementobjects.program;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import model.Point;
import org.openqa.selenium.By;
import model.Program;
import pages.BasePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramPage extends BasePage {
    //Создание программы
    private final SelenideElement nameProgram = $(By.xpath("//input[contains(@data-binding-title,\"Наименование\") and contains(@name,\"Name\")]"));
    private final SelenideElement portfolioProgramList = $(By.cssSelector("#control-group-ParentId span.k-widget.k-dropdown"));
    private final SelenideElement tabRolesList = $(By.xpath("//li[contains(@id,\"tab-roles\")]"));
    private final SelenideElement functionalCustomerProgramList = $(By.cssSelector("#control-group-Customer span.k-widget.k-dropdown"));
    private final SelenideElement curatorProgramList = $(By.cssSelector("#control-group-Owner span.k-widget.k-dropdown"));
    private final SelenideElement leaderProgramList = $(By.cssSelector("#control-group-Leader span.k-widget.k-dropdown"));
    //Общие
    private final SelenideElement currentProgramStageField = $(".f-card__info");

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

    //создание "извлеченного урока" в карточке программы
    private final SelenideElement nameProjectAfterOpen = $("#card-Name");
    private final SelenideElement learnedLesson = $(By.xpath("//span[text()='Извлечённые уроки']"));
    private final SelenideElement positiveLesson = $(".k-widget .btn-success.k-button");
    private final SelenideElement searchCreateLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//input[contains(@placeholder,\"Поиск...\")]"));
    private final SelenideElement nameLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//div[contains(@class,\"k-grid-content k-auto-scrollable\")]/..//a[contains(@target,\"_blank\")]"));
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
    //Компоненты программы
    private final SelenideElement addProjectButton = $("#tab-component a[data-tooltip='Добавить новый проект в программу']");
    private final SelenideElement addEventButton = $("#tab-component a[data-tooltip='Добавить новое непроектное меропритие в программу']");
    private final SelenideElement componentsTable = $("#ComponentTable .inlineTableView-grid");
    private final SelenideElement componentsTableHeader = $x("//div[@class='f-widget__header-name']//span[contains(text(),'Компоненты программы')]");
    private final SelenideElement componentsSearch = $("#ComponentTable input[placeholder='Поиск...']");
    private final SelenideElement firstFoundComponent = $x("//div[@id='ComponentTable']//tbody/tr[1]/td[2]");
    //Календарный план
    private final SelenideElement addPointButton = $("#btnCreatePoints");
    //Открытые вопросы
    private final SelenideElement addOpenQuestionButton = $("#tab-lov a[data-tooltip='Добавить']");
    //Таблица Риски и возможности
    private  final SelenideElement addRiskButton = $("#RiskDivContent a[data-tooltip='Добавить']");
    private  final SelenideElement addOpportunityButton = $("#ChanceDivContent a[data-tooltip='Добавить']");
    //Поручения
    private final SelenideElement addOrderButton = $("#OrderInlineTable a[data-tooltip='Добавить']");
    //Совещания
    private final SelenideElement addMeetingButton = $("#tab-meeting a[data-tooltip='Добавить']");
    //Извчлеченные уроки
    private final SelenideElement addNegativeLessonButton = $("#LessonInlineTable [data-tooltip='Отрицательный урок']");
    private final SelenideElement addPositiveLessonButton = $("#LessonInlineTable [data-tooltip='Положительный урок']");



    public void fillFields(Program program) {
        typeText(nameProgram, program.getName());
        searchAndSelectFirstFromSelect(portfolioProgramList, program.getPortfolio());
        tabRolesList.click();
        searchAndSelectFirstFromSelect(functionalCustomerProgramList, program.getCustomer());
        searchAndSelectFirstFromSelect(curatorProgramList, program.getCurator());
        searchAndSelectFirstFromSelect(leaderProgramList, program.getSupervisor());
    }

    @Step("Проверка открытия и корректного названия программы")
    public void openAndCheckProgramName(String programName) {
        nameProjectAfterOpen.shouldHave(text(programName));
    }

    @Step("Открытие формы редактирования Положительного урока")
    public void openPositiveLessonsLearnedEditForm() {
        clickOnMenuItem("Извлечённые уроки");
        positiveLesson.click();
    }

    @Step("Проверка создания урока")
    public void checkCreateLesson(String value) {
        searchCreateLesson.clear();
        searchCreateLesson.sendKeys(value);
        assertEquals(value, nameLesson.getText());
    }

    @Step ("Проверить текущую стадию программы")
    public void checkCurrentProgramStage(String stage) {
        currentProgramStageField.waitUntil(text(stage), 30000);
    }

    @Step ("Открыть вкладку Показатели")
    public void openIndicatorsTab(){
        tabIndicators.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку Добавить показатель программы")
    public void clickAddIndicator(){
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

    @Step("Открыть вкладку Результаты")
    public void openResultsTab() {
        tabResults.click();
        sleep(1000);
    }

    @Step("Открыть вкладку Календарный план")
    public void openGanttTab() {
        tabGantt.click();
        sleep(1000);
    }

    @Step("Открыть вкладку Компоненты программы")
    public void openComponentsTab() {
        tabComponents.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку Добавить результат")
    public void clickAddResult() {
        addResultButton.click();
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

    @Step("Нажать 'Добавить проект'")
    public void clickAddProject() {
        addProjectButton.click();
    }

    @Step("Нажать 'Добавить непроектное мероприятие'")
    public void clickAddEvent() {
        addEventButton.click();
    }

    @Step("Проверка наличия таблицы 'Компоненты программы'")
    public void shouldHaveComponentsTable() {
        componentsTable.shouldBe(visible);
        componentsTableHeader.shouldHave(text("Компоненты программы"));
    }

    @Step("Проверка наличия компонента в таблице")
    public void shouldHaveComponent(String componentName) {
        componentsSearch.click();
        componentsSearch.sendKeys(componentName);
        firstFoundComponent.shouldBe(visible).shouldHave(text(componentName));
    }

    @Step ("Нажать 'Создать нетиповую контрольную точку'")
    public void clickAddNonTypicalPoint() {
        addPointButton.click();
    }

    public void shouldHaveNonTypicalPoint(Point point) {
        $x("//span[contains(text(),'"+point.getName()+"')]").waitUntil(visible, Configuration.timeout).shouldBe(visible);
    }

    @Step ("Открыть вкладку Открытые вопросы")
    public void openOpenQuestionsTab(){
        tabOpenQuestions.click();
        sleep(1000);
    }

    @Step("Открыть вкладку 'Общая информация'")
    public void openMainTab() {
        tabMain.click();
    }

    @Step ("Нажать кнопку 'Добавить Открытый вопрос'")
    public void clickAddOpenQuestion () {
        addOpenQuestionButton.shouldBe(visible).click();
    }

    @Step("Проверить наличие Открытого вопроса в таблице Открытых вопросов")
    public void checkOpenQuestionPresentInTable(String questionName){
        $(By.xpath("//div[@id='tab-lov']//td//a[contains(text(),'"+ questionName +"')]")).shouldBe(visible);
    }

    @Step ("Открыть вкладку Риски и возможности")
    public void openRisksOpportunitiesTab(){
        tabRisksOpportunities.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку 'Добавить Риск'")
    public void clickAddRisk () {
        addRiskButton.shouldBe(visible).click();
    }

    @Step("Проверить наличие Риска в таблице Рисков")
    public void checkRiskPresentInTable(String riskName){
        $(By.xpath("//div[@id='RiskDivContent']//td//a[contains(text(),'"+ riskName +"')]")).shouldBe(visible);
    }

    @Step ("Нажать кнопку 'Добавить Возможность'")
    public void clickAddOpportunity () {
        addOpportunityButton.shouldBe(visible).click();
    }

    public void checkOpportunityPresentInTable(String opportunityName) {
        $(By.xpath("//div[@id='ChanceDivContent']//td//a[contains(text(),'"+ opportunityName +"')]")).shouldBe(visible);
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

    @Step ("Открыть вкладку Извлечённые уроки")
    public void openLessonsTab(){
        tabLessons.click();
        sleep(1000);
    }

    @Step ("Нажать кнопку 'Добавить Отрицательный урок'")
    public void clickAddNegativeLesson() {
        addNegativeLessonButton.shouldBe(visible).click();
    }

    @Step ("Нажать кнопку 'Добавить Положительный урок'")
    public void clickAddPositiveLesson() {
        addPositiveLessonButton.shouldBe(visible).click();
    }

    @Step("Проверить наличие Урока в таблице Извлеченных уроков")
    public void checkLessonPresentInTable(String lessonName){
        $(By.xpath("//div[@name='LessonTable']//td//a[contains(text(),'"+ lessonName +"')]")).shouldBe(visible);
    }

    @Step("Добавить Итоговый вывод с описанием {description}")
    public void addSummaryConclusion(String description) {
        $("#btnCreateSummary").click();
        $("#Description").setValue(description);
        clickSaveAndClose();
    }

    public void checkSummaryConclusionPresent(String FinalConclusionName) {
        $x("//a[contains(text(),'"+FinalConclusionName+"')]").shouldBe(visible);
    }

    @Step ("Проверить что наименование Портфеля соответствует названию {projectPortfolioName}")
    public void checkProjectPortfolioName (String projectPortfolioName) {
        $x("//div[@id='ParentId']//a[contains(text(),'"+projectPortfolioName+"')]").shouldHave(text(projectPortfolioName));
    }
}
