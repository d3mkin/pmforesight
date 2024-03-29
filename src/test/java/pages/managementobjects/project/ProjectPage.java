package pages.managementobjects.project;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Project;
import pages.BasePage;
import pages.managementobjects.result.ResultPage;
import helpers.ResultTypeArray;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectPage extends BasePage {
    //Создание проекта
    private final SelenideElement projectName = $("#Name");
    private final SelenideElement projectLevel = $("#control-group-ProjectLevelId span.k-widget.k-dropdown");
    private final SelenideElement projectPriority = $("#control-group-PriorityId span.k-widget.k-dropdown");
    private final SelenideElement projectType = $("#control-group-ProjectTypeId span.k-widget.k-dropdown");
    private final SelenideElement projectGoal = $("#control-group-ActivityGoal .k-multiselect");
    private final SelenideElement parentPortfolioOrProgram = $("#control-group-ParentId span.k-widget.k-dropdown");
    private final SelenideElement tabRoles = $("#tab-roles > a");
    private final SelenideElement curatorRole = $("#control-group-Owner span.k-widget.k-dropdown");
    private final SelenideElement managerRole = $("#control-group-Leader span.k-widget.k-dropdown");
    private final SelenideElement editForm = $(".f-card__header .m-i-pencil2");

    //Общая информация
    //TODO Рефакторинг локаторов
    private final SelenideElement currentProjectStageField = $(".f-card__info");
    private final SelenideElement projectViewName = $(".f-card__name");
    private final SelenideElement changeProjectStageButton = $x("//li[@id='MoveToNextPhase']");
    private final SelenideElement stageWorkflowWidget = $x("//span[.='Переход по стадиям']");
    private final SelenideElement nextStageButton = $(".EntityStateEdgeWorkflow .k-primary.k-button");
    private final SelenideElement prevStageButton = $(".EntityStateEdgeWorkflow .k-primary.k-button.k-toolbar-first-visible");
    private final SelenideElement cancelStageButton = $(".EntityStateEdgeWorkflow .btn-danger.k-button");

    //Модальное окно
    private final SelenideElement reasonOfCancelingField = $("#Comment");
    private final SelenideElement reasonOfCancelingIsRequired = $x("//span[@class='required-input']");

    //Основные вкладки
    private final SelenideElement tabMain = $("a[href='#tab-main']");
    private final SelenideElement tabMeetings = $("a[href='#tab-meeting']");
    private final SelenideElement tabResults = $("a[href='#tab-result']");
    private final SelenideElement tabIndicators = $("a[href='#tab-kpi']");
    private final SelenideElement tabActivity = $("a[href='#tab-activity']");
    private final SelenideElement tabDocuments = $("a[href='#tab-documents']");
    private final SelenideElement tabContracts = $("a[href='#tab-contract']");
    private final SelenideElement tabRisksOpportunities = $("a[href='#tab-risk']");
    private final SelenideElement tabLessons = $("a[href='#tab-gleaning']");
    private final SelenideElement tabOrders = $("a[href='#tab-order']");
    private final SelenideElement tabOpenQuestions = $("a[href='#tab-lov']");
    private final SelenideElement tabSnapshot = $(".f-popup__container [href='#tab-snapshot']");
    private final SelenideElement tabChangeRequestSnapshot = $("a[href='#tab-changerequest-snapshot']");
    private final SelenideElement tabGoals = $("a[href='#tab-goal']");


    //Календарный план
    private final SelenideElement initiationDateInput = $(By.xpath("//div[@class='stage stage1']//input"));
    private final SelenideElement preparationDateInput = $(By.xpath("//div[@class='stage stage2']//input"));
    private final SelenideElement realizationDateInput = $(By.xpath("//div[@class='stage stage3']//input"));
    private final SelenideElement completionDateInput = $(By.xpath("//div[@class='stage stage4']//input"));
    private final SelenideElement postProjectDateInput = $(By.xpath("//div[@class='stage stage5']//input"));
    private final SelenideElement pointSaveButton = $(".f-widget__header-button[data-tooltip='Сохранить']");
    private final SelenideElement searchInGanttTableInput = $(By.xpath("//input[@id='nameField']"));
    private final SelenideElement hideOrShowChartGanttButton = $(By.xpath("//input[@id='tbBtnShowChart']"));
    private final SelenideElement editGanttButton = $(By.xpath("//a[@id='startEditing']"));
    private final SelenideElement levelDownButton = $(By.xpath("//a[@id='tbBtnLevelDown']"));
    private final SelenideElement saveGanttButton = $(By.xpath("//a[@id='saveChanges']"));
    private final SelenideElement maximizeOrMinimizeGanttButton = $(By.xpath("//a[@id='tbBtnFullScreen']"));
    private final SelenideElement newPointAddButton = $(By.xpath("//a[@id='tbBtnCreatePoint']"));
    private final SelenideElement newWorkOrStageAddButton = $(By.xpath("//a[@id='tbBtnCreateTask']"));
    private final SelenideElement newGanttActivityNameTR = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]"));
    private final SelenideElement newGanttActivityNameTD = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[7]"));
    private final SelenideElement newGanttActivityNameInput = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[7]//div"));
    private final SelenideElement newGanttActivityApproveDocTD = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[18]//div"));
    private final SelenideElement newGanttActivityCheckBox = $x("//div[@class='ui-widget-content ui-grid-body']//tr[last()]/td[2]");
    private final SelenideElement newGanttActivityApproveDocSelect = $(".editor .k-select");
    private final SelenideElement newGanttActivityStatusTitle = $(By.xpath("//div[@class='ui-widget-content ui-grid-body']//tr[last()]//td[3]//img"));

    //Контракты
    private final SelenideElement addContractButton = $(By.xpath("//div//a[@data-tooltip = 'Контракт']"));

    //Документы
    private final SelenideElement expendDocumentsButton = $(By.xpath("//documents[@class='f-documents']//a[@id='f-document-toolbar-expand']"));
    private final SelenideElement projectPassportUploadButton = $(By.xpath("//tr[7]//div[@data-tooltip='Загрузить документ']"));
    private final SelenideElement projectConsolidatePlanUploadButton = $(By.xpath("//tr[10]//div[@data-tooltip='Загрузить документ']"));
    private final SelenideElement projectFinalReportUploadButton = $(By.xpath("//tr[18]//div[@data-tooltip='Загрузить документ']"));

    //Совещания
    private final SelenideElement addMeetingButton = $("#Meeting a[data-tooltip='Добавить']");

    //Показатели
    private final SelenideElement indicatorAddButton = $(By.xpath("//div[@name='KPIs']//a[@class='itv-add-button k-primary k-button k-button-icontext']"));

    //Проверка наличия совещания в карточке проекта
    private final SelenideElement checkMeeting = $(By.cssSelector("div.k-grid-content.k-auto-scrollable > table > tbody > tr:first-child"));

    //Создание "извлеченного урока" в карточке проекта
    private final SelenideElement nameProjectAfterOpen = $(".f-card__name");
    private final SelenideElement learnedLessons = $(By.xpath("//span[text()='Извлeчённые уроки']"));
    private final SelenideElement positiveLesson = $x("//a[contains(@class,'itv-custom-buttons btn btn-small btn-success k-button')]");
    private final SelenideElement searchCreateLesson = $(By.xpath("//div[contains(@id,'LessonInlineTable')]/..//input[contains(@placeholder,'Поиск...')]"));
    private final SelenideElement nameLesson = $(By.xpath("//div[contains(@id,'LessonInlineTable')]/..//div[contains(@class,'k-grid-content k-auto-scrollable')]/..//a[contains(@target,\"_blank\")]"));

    //Таблицы показателей
    private final SelenideElement personalIndicatorsTable = $("div[name='KPIs'] .k-grid-content");
    private final SelenideElement getPersonalIndicatorsHeader = $("div[name='KPIs'] .f-widget__header-text");
    public SelenideElement indicatorTableSearchInput = $x("//div[@name='KPIs']//input[@placeholder='Поиск...']");
    private final SelenideElement firstFoundIndicator = $("[data-column-field='Code'] a");

    //Таблицы результатов
    private final SelenideElement projectResultsTab =$(By.xpath("//span[contains(text(),'Ведение результатов проекта')]"));
    private final SelenideElement linkedKPAndResultsTableTab = $(By.xpath("//span[contains(text(),'Связь результатов и КП проекта')]"));
    private final SelenideElement departmentalResultsTable = $(By.xpath("//div[@name='Result']"));
    private final SelenideElement departmentalResultsHeader = $(By.xpath("//*[text() = 'Результаты проекта']"));
    private final SelenideElement departmentalResultsSearch = $(By.xpath("//div[@name='Results']//div//input[@placeholder='Поиск...']"));
//    private final SelenideElement departmentalFirstFoundResult = $(By.xpath("//div[@name='Result']//div[@class='inlineTableView-grid k-grid k-widget k-display-block']//a[@href]"));
    private final SelenideElement departmentalFirstFoundResult = $x("//div[@name='Result']//td[@data-column-field='FullName']//a");
    private final SelenideElement departmentalResultDelete = $(By.xpath("//div[@name='Results']//a[@class='itv-remove-button k-grid-trash']"));
    private final SelenideElement departmentalResultEdit = $(By.xpath("//div[@name='Results']//a[@class='itv-edit-button k-grid-edit']"));
    private final SelenideElement departmentalResultChild = $(By.xpath("//div[@name='Results']//span[@style='padding-left: 30px']"));
    private final SelenideElement federalResultsTable = $(By.xpath("//div[@name='FederalResult']"));
    private final SelenideElement federalResultsHeader = $(By.xpath("//*[text() = 'Результаты федерального проекта']"));
    private final SelenideElement federalResultsSearch = $(By.xpath("//div[@name='FederalResults']//input[@class='itv-inlineSearch k-textbox']"));
    private final SelenideElement federalFirstFoundResult = $x("//div[@name='FederalResults']//td[@data-column-field='FullName']//a");
    private final SelenideElement federalResultDelete = $(By.xpath("//div[@name='FederalResults']//a[@class='itv-remove-button k-grid-trash']"));
    private final SelenideElement federalResultEdit = $(By.xpath("//div[@name='FederalResults']//a[@class='itv-edit-button k-grid-edit']"));
    private final SelenideElement federalResultChild = $(By.xpath("//div[@name='FederalResults']//span[@style='padding-left: 30px']"));
    private final SelenideElement regionalResultsTable = $(By.xpath("//div[@name='FederalResult']"));
    private final SelenideElement regionalResultsHeader = $(By.xpath("//*[text() = 'Результаты регионального проекта']"));
    private final SelenideElement regionalResultsSearch = $(By.xpath("//div[@name='RegionalResults']//input[@class='itv-inlineSearch k-textbox']"));
//    private final SelenideElement regionalFirstFoundResult = $(By.xpath("//div[@name='RegionalResults']//div[@class='inlineTableView-grid k-grid k-widget k-display-block']//a[@href]"));
    private final SelenideElement regionalFirstFoundResult = $x("//div[@name='RegionalResults']//td[@data-column-field='FullName']//a");
    private final SelenideElement regionalResultDelete = $(By.xpath("//div[@id='RegionalResults']//a[@class='itv-remove-button k-grid-trash']"));
    private final SelenideElement regionalResultEdit = $(By.xpath("//div[@name='RegionalResults']//a[@class='itv-edit-button k-grid-edit']"));
    private final SelenideElement regionalResultChild = $(By.xpath("//div[@name='RegionalResults']//span[@style='padding-left: 30px']"));
    private final SelenideElement noneLinkedResultsTable = $(By.xpath("//div[@name='RegionalFederalLink']"));
    private final SelenideElement noneLinkedResultsHeader = $(By.xpath("//*[text() = 'Результаты регионального проекта, не привязанные к результатам федерального']"));
    private final SelenideElement noneLinkedResultsSearch = $(By.xpath("//div[@id='RegionalFederalLink']//input[@class='itv-inlineSearch k-textbox']"));
    private final SelenideElement regionalResultAddButton = $(By.xpath("//div[@name='RegionalResults']//a[@data-tooltip='Добавить']"));
    private final SelenideElement federalResultAddButton = $(By.xpath("//div[@name='FederalResults']//a[@data-tooltip='Добавить']"));
    private final SelenideElement departmentalResultAddButton = $(By.xpath("//div[@name='Results']//a[@data-tooltip='Добавить']"));
    private final SelenideElement linkedFederalResultsTD = $(By.xpath("//div[@name='FederalResult']//td[5]"));

    //Таблица Связь результатов и КП проекта
    private final SelenideElement editInKPAndResultsLinkedTableButton = $("#PointResultEditBtn");
    private final SelenideElement cancelInKPAndResultsLinkedTableChangesButton = $("#PointResultCancelBtn");

    //Таблица Риски и возможности
    private  final SelenideElement addRiskButton = $(By.xpath("//div[@name='Risks']//a[@data-tooltip='Добавить']"));
    private  final SelenideElement addOpportunityButton = $(By.xpath("//div[@name='Chances']//a[@data-tooltip='Добавить']"));

    //Поручения
    private final SelenideElement addOrderButton = $("#OrderInlineTable a[data-tooltip='Добавить']");

    //Открытые вопросы
    private final SelenideElement addOpenQuestionButton = $(By.xpath("//div[@name='LOVs']//a[@data-tooltip='Добавить']"));

    //Извчлеченные уроки
    private final SelenideElement addNegativeLessonButton = $("#LessonInlineTable [data-tooltip='Отрицательный урок']");
    private final SelenideElement addPositiveLessonButton = $("#LessonInlineTable [data-tooltip='Положительный урок']");

    //Слепки
    private final SelenideElement addSnapshotButton = $("#SnapshotInlineTableView a[data-tooltip='Создать новый слепок']");
    private final SelenideElement snapshotSearchInput = $("#SnapshotInlineTableView [placeholder='Поиск...']");
    private final SelenideElement snapshotTable = $("#tab-snapshot");
    private final SelenideElement firstFoundSnapshotName = $("#SnapshotInlineTableView [data-column-field='Name'] a");
    private final SelenideElement firstFoundCreationDate = $("#SnapshotInlineTableView [data-column-field='CreationDate']");
    private final SelenideElement firstFoundSnapshotComment = $("#SnapshotInlineTableView [data-column-field='Comment']");
    private final SelenideElement firstFoundSnapshotStatus = $("#SnapshotInlineTableView [data-column-field='ActivityPhaseName']");
    private final SelenideElement deleteSnapshotButton = $("#SnapshotInlineTableView .k-i-trash");

    //ЗИ
    private final SelenideElement createChangeRequestButton = $("[name='CreateCHLi']");
    private final SelenideElement changeRequestTableSearchInput = $("#SnapshotCRInlineTableView [placeholder='Поиск...']");
    private final SelenideElement foundChangeRequestName = $("#SnapshotCRInlineTableView [data-column-field='Name'] a");
    private final SelenideElement foundChangeRequestCreationDate = $("#SnapshotCRInlineTableView [data-column-field='CreationDate']");
    private final SelenideElement foundChangeRequestComment = $("#SnapshotCRInlineTableView [data-column-field='Comment']");
    private final SelenideElement foundChangeRequestCreationAuthorName = $("#SnapshotCRInlineTableView [data-column-field='CreationAuthorName']");
    private final SelenideElement foundChangeRequestStatus = $("#SnapshotCRInlineTableView [data-column-field='ActivityPhaseName']");
    private final SelenideElement foundChangeRequestApprovedSnapshotName = $("#SnapshotCRInlineTableView [data-column-field='ApprovedSnapshotName'] a");


    //TODO: сделать отдельный класс для верхней панели карточек
    private final SelenideElement buttonCloseForm = $(By.xpath("//div[@id='closeForm']"));

    @Step ("Проверить что наименование Проекта соответствует названию {projectName}")
    public void checkProjectName (String projectName) {
        projectViewName.shouldHave(text(projectName), ofMillis(timeout));
    }

    @Step ("Проверить что наименование Портфеля соответствует названию {projectPortfolioName}")
    public void checkProjectPortfolioName (String projectPortfolioName) {
        $x("//div[@id='Parent']//a[contains(text(),'"+projectPortfolioName+"')]").shouldHave(text(projectPortfolioName));
    }

    @Step("Заполнить поля проекта")
    public void fillFields(Project project) {
        checkPageIsLoaded();
        typeText(projectName, project.getName());
        searchAndSelectFirstFromSelect(projectPriority, project.getPriority());
        searchAndSelectFirstFromSelect(projectLevel, project.getProjectLevel());
        searchAndSelectFirstFromSelect(projectType, project.getType());
        searchAndSelectFirstFromSelect(parentPortfolioOrProgram, project.getProgram());
        searchAndSelectFirstFromMultiSelect(projectGoal, project.getGoal());

        tabRoles.click();
        curatorRole.shouldBe(visible, ofMillis(timeout));
        searchAndSelectFirstFromSelect(curatorRole, project.getCurator());
        sleep(500);
        searchAndSelectFirstFromSelect(managerRole, project.getSupervisor());
    }

    @Step("Проверить отображение совещания в созданном проете")
    public void shouldMeeting() {
        checkMeeting.shouldBe(visible);
    }

    @Step("Добавить новое Совещание из карточки Проекта")
    public void clickAddMeeting() {
        checkElementIsVisibleAndClick(addMeetingButton);
    }

    @Step("Проверить отображение кнопки 'Добавить совещание'  карточке проекта")
    public void shouldButtonAddMeeting() {
        tabMeetings.click();
        addMeetingButton.shouldBe(visible);
    }

    @Step("Открыть вкладку Совещания")
    public void openMeetingTab() {
        tabMeetings.click();
    }

    @Step("Открыть вкладку 'Общая информация'")
    public void openMainTab() {
        tabMain.click();
        checkPageIsLoaded();
    }

    @Step("Проверка открытия проекта")
    public void openProject(String projectName) {
        assertTrue(nameProjectAfterOpen.getText().contains(projectName));
    }

    @Step("Открытие положительного урока")
    public void positiveLessonsLearned() {
        clickOnMenuItem("Извлeчённые уроки");
        checkElementIsVisibleAndClick(positiveLesson);
    }

    @Step("Проверка создания урока")
    public void checkCreateLesson(String value) {
        searchCreateLesson.clear();
        searchCreateLesson.sendKeys(value);
        assertTrue(nameLesson.getText().equals(value));
    }

    @Step ("Закрыть форму просмотра карточки")
    public void clickCloseForm () {
        buttonCloseForm.click();
    }

    @Step("Открыть вкладку Результаты")
    public void openResultsTab() {
        tabResults.click();
        checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Показатели")
    public void openIndicatorsTab(){
       tabIndicators.click();
       checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Календарный план")
    public void openActivityTab(){
       tabActivity.click();
       checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Цели")
    public void openGoalsTab(){
       tabGoals.click();
       checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Документы")
    public void openDocumentsTab(){
        tabDocuments.click();
        checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Контракты")
    public void openContractsTab(){
        tabContracts.click();
        checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Риски и возможности")
    public void openRisksOpportunitiesTab() {
        tabRisksOpportunities.click();
        checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Поручения")
    public void openOrdersTab() {
        tabOrders.click();
        checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Открытые вопросы")
    public void openOpenQuestionsTab(){
        tabOpenQuestions.click();
        checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Извлеченные уроки")
    public void openLessonsTab(){
        checkPageIsLoaded();
        tabLessons.click();
    }

    @Step ("Открыть вкладку Слепки")
    public void openSnapshotTab() {
        checkPageIsLoaded();
        $(".f-tab__list_toolbar_more").click();
        tabSnapshot.click();
        checkPageIsLoaded();
    }

    @Step ("Открыть вкладку Запросы на изменения(Слепки)")
    public void openChangeRequestSnapshotTab() {
        tabChangeRequestSnapshot.click();
        checkPageIsLoaded();
    }

    @Step ("Проверка таблиц результатов ведомственного проекта(по умолчанию)")
    public void shouldHaveDefaultResultsTable() {
        checkPageIsLoaded();
        departmentalResultsTable.shouldBe(visible);
        departmentalResultsHeader.shouldHave(text("Результаты проекта"));
    }

    @Step ("Проверка таблиц результатов федерального проекта")
    public void shouldHaveFederalResultsTable() {
        checkPageIsLoaded();
        federalResultsTable.shouldBe(visible);
        federalResultsHeader.shouldHave(text("Результаты федерального проекта"));
    }

    @Step ("Проверка таблиц результатов регионального проекта")
    public void shouldHaveRegionalResultsTable() {
        checkPageIsLoaded();
        federalResultsTable.shouldBe(visible);
        federalResultsHeader.shouldHave(text("Результаты федерального проекта"));
        regionalResultsTable.shouldBe(visible);
        regionalResultsHeader.shouldHave(text("Результаты регионального проекта"));
        noneLinkedResultsTable.shouldBe(visible);
        noneLinkedResultsHeader.shouldHave(text("Результаты регионального проекта, не привязанные к результатам федерального"));
    }

    @Step ("Нажать кнопку Добавить показатель проекта")
    public void clickAddIndicator(){
        checkPageIsLoaded();
        checkElementIsVisibleAndClick(indicatorAddButton);
    }

    @Step ("Нажать кнопку Добавить результат")
    public void clickAddResult(String resultType) {
        switch (resultType){
            case ("Ведомственный"):
                checkElementIsVisibleAndClick(departmentalResultAddButton);
                break;
            case ("Федеральный"):
                checkElementIsVisibleAndClick(federalResultAddButton);
                break;
            case ("Региональный"):
                checkElementIsVisibleAndClick(regionalResultAddButton);
                break;
            default:
                break;
        }
    }

    @Step ("Открыть карточку просмотра Ведомственного результата из таблицы")
    public void openDepartmentalResultCard (){
        departmentalFirstFoundResult.click();
    }

    @Step ("Открыть карточку просмотра Федерального результата из таблицы")
    public void openFederalResultCard (){
        federalFirstFoundResult.click();
    }

    @Step ("Открыть карточку просмотра Регионального результата из таблицы")
    public void openRegionalResultCard (){
                regionalFirstFoundResult.click();
    }

    @Step ("Открыть карточку Регионального проекта на редактирование")
    public void editRegionalResultCard () {
        regionalResultEdit.click();
    }

    @Step ("Проверить созданный Ведомственный результат в таблице")
    public void shouldHaveDepartmentalResult(String result){
        departmentalResultsSearch.click();
        departmentalResultsSearch.sendKeys(result);
        checkPageIsLoaded();
        departmentalFirstFoundResult.shouldBe(visible);
        departmentalFirstFoundResult.shouldHave(text(result));
    }

    @Step ("Проверить созданный Федеральный результат в таблице")
    public void shouldHaveFederalResult(String result){
        federalResultsSearch.click();
        federalResultsSearch.sendKeys(result);
        checkPageIsLoaded();
        federalFirstFoundResult.shouldBe(visible);
        federalFirstFoundResult.shouldHave(text(result));
    }

    @Step ("Проверить созданный Региональный результат в таблице")
    public void shouldHaveRegionalResult(String result){
        regionalResultsSearch.click();
        regionalResultsSearch.sendKeys(result);
        checkPageIsLoaded();
        regionalFirstFoundResult.shouldBe(visible);
        regionalFirstFoundResult.shouldHave(text(result));
    }

    @Step ("Проверить связь рез-та Фед. проекта с созданным рез-том Рег. проекта")
    public void checkLinkBetweenFederalAndRegionalResults(String result){
        linkedFederalResultsTD.shouldHave(text(result));
    }

    @Step ("Удалить Ведомственный результат")
    public void clickDeleteDefaultResult() {
        departmentalResultDelete.shouldBe(visible);
        departmentalResultDelete.click();
        confirmDeleteEntity();
        departmentalResultDelete.shouldNot(visible);
    }

    @Step ("Удалить Федеральный результат")
    public void clickDeleteFederalResult() {
        federalResultDelete.shouldBe(visible);
        federalResultDelete.click();
        confirmDeleteEntity();
        federalResultDelete.shouldNot(visible);
    }

    @Step ("Удалить Федеральный результат")
    public void clickDeleteRegionalResult() {
        regionalResultDelete.shouldBe(visible);
        regionalResultDelete.click();
        confirmDeleteEntity();
        regionalResultDelete.shouldNot(visible);
    }

    @Step ("Проверить является ли результат дочерним")
    public void checkIsTheResultChild(String resultType, String resultName){
        switch (resultType) {
            case ("Ведомственный"):
                departmentalResultChild.shouldHave(text(resultName));
                break;
            case ("Федеральный"):
                federalResultChild.shouldHave(text(resultName));
                break;
            case ("Региональный"):
                regionalResultChild.shouldHave(text(resultName));
                break;
            default:
                break;
        }
    }

    @Step ("Открыть карточку редактирования результата")
    public void openResultEditForm(String resultType){
        switch (resultType) {
            case ("Ведомственный"):
                checkElementIsVisibleAndClick(departmentalResultEdit);

                break;
            case ("Федеральный"):
                checkElementIsVisibleAndClick(federalResultEdit);
                break;
            case ("Региональный"):
                checkElementIsVisibleAndClick(regionalResultEdit);
                break;
            default:
                break;
        }
    }

    @Step("Изменить уровень проекта")
    public void changeProjectLevel(String typeOfProjectLevel){
        searchAndSelectFirstFromSelect(projectLevel,typeOfProjectLevel);
    }

    @Step ("Перейти во вкладку 'Связь результатов и КП проекта'")
    public void switchToLinkedKPAndResultsTableTab(){
        linkedKPAndResultsTableTab.click();
    }

    @Step ("Перейти во вкладку 'Ведение результатов проекта'")
    public void switchToProjectResultsTab(){
        projectResultsTab.click();
    }

    @Step ("Проверить наличие наименования результата и его тип в таблице 'Связь результатов и КП проекта'")
    public void checkLinkedResultsInTable(String resultName, String resultType){
        $(By.xpath("//div[@class='wtHider']//td[contains(text(),'"+resultName+"')]")).shouldBe(visible);
        $(By.xpath("//div[@class='wtHider']//td[contains(text(),'"+resultType+"')]")).shouldBe(visible);
    }

    @Step ("Проверить соответствие типа результата с типом КТ/Мероприятием в таблице 'Связь результатов и КП проекта'")
    public void checkValidationInKPAndResultsLinkedTable(ResultPage result, String resultLevel) {
        for (String resultType : ResultTypeArray.resultTypeArray) {
            //Проверяется, что таблица результатов появилась
            switch (resultLevel) {
                case ("Ведомственный"):
                    shouldHaveDefaultResultsTable();
                    break;
                case ("Федеральный"):
                    shouldHaveFederalResultsTable();
                    break;
                case ("Региональный"):
                    shouldHaveRegionalResultsTable();
                    break;
                default:
                    break;
            }

            openResultEditForm(resultLevel);
            result.fillResultType(resultType);
            result.clickSaveAndClose();
            switchToLinkedKPAndResultsTableTab();
            sleep(2000);
            editInKPAndResultsLinkedTableButton.click();
            //Нажатие по полю Тип КТ/Меропрития*
            $(By.xpath("//div[@id='PointResultContainer']//td[3]")).doubleClick();
            sleep(1000);
            //Извлекаем все значения из выпадающего меню в список
            List dropDownValues = $$(By.xpath("//div[@class='k-animation-container']//li/span")).texts();
            //В зависимости от типа результата идёт сравненение значений в выпадаюшем списке со справочником (Используется HashSet для того чтоб не учитывать строгий порядок значений)
            switch (resultType) {
                case ("Строительство (реконструкция, техническое перевооружение, приобретение) объекта недвижимого имущества"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_1));
                    break;
                case ("Оказание услуг (выполнение работ)"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_2));
                    break;
                case ("Создание (реорганизация) организации (структурного подразделения)"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_3));
                    break;
                case ("Проведение образовательных мероприятий"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_4));
                    break;
                case ("Принятие нормативного правового (правового) акта"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_5));
                    break;
                case ("Утверждение документа"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_6));
                    break;
                case ("Проведение массовых мероприятий"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_7));
                    break;
                case ("Создание (развитие) информационно-телекоммуникационного сервиса (информационной системы)"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_8));
                    break;
                case ("Благоустройство территории, ремонт объектов недвижимого имущества"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_9));
                    break;
                case ("Проведение информационно-коммуникационной кампании"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_10));
                    break;
                case ("Обеспечение реализации федерального проекта (результата федерального проекта)"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_11));
                    break;
                case ("Проведение научно-исследовательских (опытно-конструкторских) работ, реализация проекта внедрения новой информационной технологии"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_12));
                    break;
                case ("Создание Российской промышленной зоны за рубежом"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_13));
                    break;
                case ("Приобретение товаров, работ, услуг"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_14));
                    break;
                case ("Обеспечено привлечение квалифицированных кадров"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_15));
                    break;
                case ("Закупка товаров, работ, услуг"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_16));
                    break;
                case ("Предоставление субсидий, иных межбюджетных трансфертов, имеющих целевое назначение, бюджетам бюджетной системы Российской Федерации"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_17));
                    break;
                case ("Предоставление субсидий на выполнение государственного задания бюджетным и автономным учреждениям"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_18));
                    break;
                case ("Предоставление субсидий юридическим (физическим) лицам, за исключением субсидий на выполнение государственного (муниципального) задания на оказание государственных (муниципальных) услуг (выполнение работ)"):
                    new HashSet(dropDownValues).equals(new HashSet(ResultTypeArray.dropDownValid_19));
                    break;
                default:
                    break;
            }
            cancelInKPAndResultsLinkedTableChangesButton.click();
            switchToProjectResultsTab();
        }
    }

    @Step ("Проверить наличие таблицы 'Персональные показатели проекта'")
    public void shouldHaveIndicatorsTable() {
        personalIndicatorsTable.shouldBe(visible);
        getPersonalIndicatorsHeader.shouldHave(text("Персональные показатели проекта"));
    }

    @Step ("Проверить наличие показателя в таблице 'Персональные показатели проекта'")
    public void shouldHaveIndicator(String indicatorName) {
        checkPageIsLoaded();
        indicatorTableSearchInput.click();
        checkPageIsLoaded();
        indicatorTableSearchInput.sendKeys(indicatorName);
        checkPageIsLoaded();
        firstFoundIndicator.shouldBe(visible);
        firstFoundIndicator.shouldHave(text(indicatorName));
    }

    @Step ("Открыть карточку показателя")
    public void openIndicatorCard(String indicatorName) {
        $x("//a[contains(text(),'"+indicatorName+"')]").shouldBe(visible).click();
//        firstFoundIndicator.click();
    }

    @Step ("Проверить текущую стадию проекта")
    public void checkCurrentProjectStage(String stage) {
        checkPageIsLoaded();
        currentProjectStageField.shouldHave(text(stage), ofMillis(timeout));
    }

    @Step ("Проверить возможность перевода проекта по стадиям")
    public void checkPossibilityProjectStaging(){
        checkPageIsLoaded();
        stageWorkflowWidget.shouldBe(visible).click();
        nextStageButton.shouldBe(visible);
        cancelStageButton.shouldBe(visible);
    }

    @Step ("Проверить невозможность перевода проекта по стадиям")
    public void checkImpossibilityProjectStaging(){
        checkPageIsLoaded();
        stageWorkflowWidget.shouldBe(visible);
        nextStageButton.shouldNotBe(visible);
        cancelStageButton.shouldNotBe(visible);
    }

    @Step ("Перевести проект на стадию {stage}")
    public void moveStageTo(String stage) {
        checkPageIsLoaded();
        if (!$(".EntityStateEdgeWorkflow").isDisplayed()) {
            checkPossibilityProjectStaging();
        }
        $x("//a[text()='"+stage+"']").click();
    }

    @Step ("Проверить что поле 'Причина отмена проекта' отображается и является обязательным")
    public void shouldHaveReasonField() {
        checkPageIsLoaded();
        reasonOfCancelingField.shouldBe(visible);
        reasonOfCancelingIsRequired.shouldBe(visible);
    }

    @Step ("Заполнить поле 'Причина отмена проекта'")
    public void fillReasonField(String reason) {
        reasonOfCancelingField.setValue(reason);
    }


    @Step ("Развернуть виджет 'Создание обязательных контрольных точек проекта'")
    public void expandOrCollapseRequiredPointsWidget(){
        $x("//span[contains(text(),'Создание обязательных контрольных точек проекта')]").waitUntil(visible, 10000).click();
    }

    @Step("Заполнить плановую дату обязательной КТ на стадии {stage}")
    public void createRequiredStagePoint(String stage, String date){
        checkPageIsLoaded();
        switch (stage) {
            case ("Инициирование"):
                typeDate(initiationDateInput, date );
                pointSaveButton.click();
                break;
            case ("Подготовка"):
                typeDate(preparationDateInput, date );
                pointSaveButton.click();
                break;
            case ("Реализация"):
                typeDate(realizationDateInput, date );
                pointSaveButton.click();
                break;
            case ("Завершение"):
                typeDate(completionDateInput, date );
                pointSaveButton.click();
                break;
            case ("Постпроектный мониторинг"):
                typeDate(postProjectDateInput, date );
                pointSaveButton.click();
                break;
            default:
                break;
        }
    }

    @Step("Проверить что сущность с названием {entityName} отображается в Ганте")
    public void checkEntityIsDisplayedInGantt(String entityName){
        checkPageIsLoaded();
        if (!$("#Activity2_container #ganttframe").isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
        }
        switchTo().frame("ganttframe");
        $(By.xpath("//div[@id='ganttplace']//*[text() = '"+ entityName +"']")).shouldBe(visible);
        switchTo().defaultContent();
    }

    @Step ("Понизить уровень сущности")
    public void clickToDownEntityLevel() {
        levelDownButton.click();
    }

    @Step ("Найти в Ганте сущность с названием {entityName} и открыть её карточку просмотра")
    public void findInGanttAndOpenEntityPage(String entityName){
        checkPageIsLoaded();
        if (!$("#Activity2_container #ganttframe").isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
        }
        switchTo().frame("ganttframe");
        typeText(searchInGanttTableInput, entityName);
        sleep(1000);
        //первая ссылка в таблице
        $(By.xpath("//div[@id='ganttplace']//a")).click();
        switchTo().defaultContent();
    }

    @Step("Перевести Гант в режим редактирования")
    public void clickEditGantt(){
        checkPageIsLoaded();
        if (!$("#Activity2_container #ganttframe").isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
        }
        switchTo().frame("ganttframe");
        editGanttButton.shouldBe(visible).click();
        switchTo().defaultContent();
    }

    @Step("Добавить на Гант КТ с назаванием {pointName} и утверждающим документом {approvingDoc}")
    public void addNewPointInGantt(String pointName, String approvingDoc){
        checkPageIsLoaded();
        if (!$("#Activity2_container #ganttframe").isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
        }
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
        checkPageIsLoaded();
        if (!$("#Activity2_container #ganttframe").isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
        }
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
        checkPageIsLoaded();
        if (!$("#Activity2_container #ganttframe").isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
        }
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

    @Step ("Проверить статус КТ")
    public void checkPointStatus (String status) {
        checkPageIsLoaded();
        if (!$("#Activity2_container #ganttframe").isDisplayed()) {
            Selenide.refresh();
            checkPageIsLoaded();
        }
        switchTo().frame("ganttframe");
        if (status.equals("В работе")) {
            newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "В работе по плану"));
        }
        if (status.equals("Выполнено")) {
            newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "Выполнено"));
        }
        if (status.equals("Подтверждено")) {
            newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "Подтверждено"));
        }
        if (status.equals("Просрочено")) {
            newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "Просрочено (дата планового окончания уже прошла)"));
        }
        if (status.equals("Отменено")) {
            newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "Отменено"));
        }
        if (status.equals("Прогноз срыва сроков")) {
            newGanttActivityStatusTitle.shouldHave(attribute("data-tooltip", "Прогноз срыва сроков"));
        }
        switchTo().defaultContent();
    }

    @Step("Нажать кнопку загрузить Паспорт проекта")
    public void clickToUploadProjectPassport (File file){
        checkPageIsLoaded();
        checkElementIsVisibleAndClick(projectPassportUploadButton);
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
    }
    @Step("Нажать кнопку загрузить Сводный план проекта")
    public void clickToUploadProjectConsolidatePlan (File file){
        checkPageIsLoaded();
        checkElementIsVisibleAndClick(projectConsolidatePlanUploadButton);
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
    }
    @Step("Нажать кнопку загрузить Сводный план проекта")
    public void clickToUploadFinalReport (File file){
        checkPageIsLoaded();
        checkElementIsVisibleAndClick(projectFinalReportUploadButton);
        uploadFile(file);
        checkFileIsUploaded(file);
        closeUploadWindow();
    }

    @Step ("Скрыть или показать графическую часть в Ганте")
    public void hideOrShowChartOnGantt(){
        hideOrShowChartGanttButton.click();
    }

    @Step ("Развернуть все группы в виджете Документы")
    public void expandDocuments() {
        expendDocumentsButton.click();
    }

    @Step ("Нажать кнопку 'Добавить контракт'")
    public void clickAddContact() {
        checkElementIsVisibleAndClick(addContractButton);
    }

    @Step ("Проверить наличие Контракта в таблице Контрактов")
    public void checkContractPresentInTable(String contractName){
        $(By.xpath("//div[@name='Contract']//td//span[contains(text(),'"+ contractName +"')]")).shouldBe(visible);
    }

    @Step("Проверить наличие Риска в таблице Рисков")
    public void checkRiskPresentInTable(String riskName){
        $(By.xpath("//div[@name='Risks']//td//a[contains(text(),'"+ riskName +"')]")).shouldBe(visible);
    }

    @Step("Проверить наличие Урока в таблице Извлеченных уроков")
    public void checkLessonPresentInTable(String lessonName){
        $(By.xpath("//div[@name='LessonTable']//td//a[contains(text(),'"+ lessonName +"')]")).shouldBe(visible);
    }

    @Step("Проверить наличие Возможности в таблице Возможностей")
    public void checkOpportunityPresentInTable(String opportunityName){
        $(By.xpath("//div[@name='Chances']//td//a[contains(text(),'"+ opportunityName +"')]")).shouldBe(visible);
    }

    @Step("Проверить наличие Поручения в таблице Поручений")
    public void checkOrderPresentInTable(String orderName){
        $(By.xpath("//div[@name='OrderInlineTable']//td//a[contains(text(),'"+ orderName +"')]")).shouldBe(visible);
    }

    @Step("Проверить наличие Совещания в таблице Совещаний")
    public void checkMeetingPresentInTable(String meetingName){
        $(By.xpath("//div[@name='Meeting']//td//a[contains(text(),'"+ meetingName +"')]")).shouldBe(visible);
    }

    @Step("Проверить наличие Открытого вопроса в таблице Открытых вопросов")
    public void checkOpenQuestionPresentInTable(String questionName){
        $(By.xpath("//div[@name='LOVs']//td//a[contains(text(),'"+ questionName +"')]")).shouldBe(visible);
    }

    @Step ("Нажать кнопку 'Добавить Риск'")
    public void clickAddRisk () {
        checkElementIsVisibleAndClick(addRiskButton);
    }

    @Step ("Нажать кнопку 'Добавить Отрицательный урок'")
    public void clickAddNegativeLesson() {
        checkElementIsVisibleAndClick(addNegativeLessonButton);
    }

    @Step ("Нажать кнопку 'Добавить Положительный урок'")
    public void clickAddPositiveLesson() {
        checkElementIsVisibleAndClick(addPositiveLessonButton);
    }

    @Step ("Нажать кнопку 'Добавить Возможность'")
    public void clickAddOpportunity () {
        checkElementIsVisibleAndClick(addOpportunityButton);
    }

    @Step ("Нажать кнопку 'Добавить Поручение'")
    public void clickAddOrder () {
        checkElementIsVisibleAndClick(addOrderButton);
    }

    @Step ("Нажать кнопку 'Добавить Открытый вопрос'")
    public void clickAddOpenQuestion () {
        checkElementIsVisibleAndClick(addOpenQuestionButton);
    }

    @Step("Закрыть всплывающее уведомление 'Внимание, создан слепок!'")
    public void closeCreatedSnapshotNotification() {
        checkPageIsLoaded();
        $(".k-notification-success").shouldBe(visible, ofMillis(timeout));
        $(".f-notify__title").shouldHave(text("Внимание, создан слепок!"));
        $(".f-notify__close").click();
        $(".k-notification-success").shouldNotBe(visible, ofMillis(timeout));
    }

    @Step("Нажать 'Создать новый слепок'")
    public void clickAddSnapshot() {
        checkPageIsLoaded();
        addSnapshotButton.click();
    }

    @Step("Найти в таблице Слепок с наименованием {snapshotName}")
    public void searchSnapshotInTable(String snapshotName) {
        snapshotTable.shouldBe(visible);
        snapshotSearchInput.shouldBe(visible).setValue(snapshotName).pressEnter();
    }

    @Step("Проверить имя слепка и его статус в таблице слепков")
    public void checkSnapshotExistInTable(String snapshotName, String snapshotStatus) {
        firstFoundSnapshotName.shouldBe(visible).shouldHave(text(snapshotName));
        firstFoundSnapshotStatus.shouldBe(visible).shouldHave(text(snapshotStatus));
    }

    @Step("Проверить имя слепка и его статус в таблице слепков")
    public void checkSnapshotExistInTable(String snapshotName, String snapshotDate, String snapshotComment, String snapshotStatus) {
        firstFoundSnapshotName.shouldBe(visible).shouldHave(text(snapshotName));
        firstFoundCreationDate.shouldBe(visible).shouldHave(text(snapshotDate));
        firstFoundSnapshotComment.shouldBe(visible).shouldHave(text(snapshotComment));
        firstFoundSnapshotStatus.shouldBe(visible).shouldHave(text(snapshotStatus));
    }

    @Step("Проверить что слепок не отображается в таблице слепков")
    public void checkSnapshotNotExistInTable(String snapshotName) {
        searchSnapshotInTable(snapshotName);
        firstFoundSnapshotName.shouldBe(not(visible));
    }

    @Step("Открыть карточку слепка с наименованием {snapshotName}")
    public void openSnapshotCard(String snapshotName) {
        searchSnapshotInTable(snapshotName);
        firstFoundSnapshotName.click();
    }

    @Step("Удалить слепок из таблицы Слепков в проекте")
    public void deleteSnapshot(String snapshotName) {
        searchSnapshotInTable(snapshotName);
        checkPageIsLoaded();
        $("#SnapshotInlineTableView .k-checkbox-label").click();
        deleteSnapshotButton.click();
        clickDialogSave();
        checkPageIsLoaded();
    }

    @Step ("Нажать Сформировать ЗИ")
    public void clickAddChangeRequest() {
        createChangeRequestButton.shouldBe(visible).click();
        modalWindowShouldBeOpened();
        modalWindowShouldHaveTitle("Запрос на изменение");
    }

    @Step ("Проверить наличие Запроса на изменение с наименованием {changeRequestName} в таблице 'Запросы на изменение'")
    public void shouldHaveChangeRequest(String changeRequestName) {
        changeRequestTableSearchInput.click();
        changeRequestTableSearchInput.sendKeys(changeRequestName);
        foundChangeRequestName.shouldBe(visible);
        foundChangeRequestName.shouldHave(text(changeRequestName));
    }

    @Step ("Найти Запрос на изменение с наименованием {changeRequestName} в таблице 'Запросы на изменение' и открыть его форму просмотра")
    public void findAndOpenChangeRequestViewForm(String changeRequestName) {
        changeRequestTableSearchInput.clear();
        changeRequestTableSearchInput.click();
        changeRequestTableSearchInput.sendKeys(changeRequestName);
        foundChangeRequestName.shouldBe(visible);
        foundChangeRequestName.shouldHave(text(changeRequestName)).click();
    }

    @Step ("Проверить что Запрос на изменение содержит наименование{changeRequestName}, комментарий{changeRequestComment}, состояние{changeRequestStatus}, утвержденный слепок{changeRequestSnapshot}")
    public void checkChangeRequestData(String changeRequestName, String changeRequestComment, String changeRequestStatus, String changeRequestSnapshot){
        foundChangeRequestName.shouldHave(text(changeRequestName));
        foundChangeRequestComment.shouldHave(text(changeRequestComment));
        foundChangeRequestStatus.shouldHave(text(changeRequestStatus));
        foundChangeRequestApprovedSnapshotName.shouldHave(text(changeRequestSnapshot));
    }

    @Step("Проверить что карточка Проекта открыта в режиме Слепка")
    public void checkPageIsOnSnapshotMode(String snapshotName){
        checkPageIsLoaded();
        $(".f-notify_inline").shouldBe(visible);
        $x("//span[contains(text(),'"+snapshotName+"')]").shouldBe(visible);
    }

    @Step("Проверить что кол-во изменений соотвестует цифре {containerChangesCount} на виджете Общая информация")
    public void checkCountOfMainChanges(int containerChangesCount) {
        checkPageIsLoaded();
//        $("a[href='#tab-main'] > .f-tag").shouldHave(text(""+tabChangesCount+""));
        $$("[data-name='SnapshotPrev']").shouldHave(CollectionCondition.size(containerChangesCount));
    }

    @Step("Проверить что кол-во изменений соотвестует цифре {tabChangesCount} на вкладке Цели и статусу {status} в таблице Цели")
    public void checkCountOfGoalChanges(int tabChangesCount) {
        checkPageIsLoaded();
        $("a[href='#tab-goal'] > .f-tag").shouldHave(text(""+tabChangesCount+""));
    }

    @Step("Добавить в Проект Цели {goalsNames}")
    public void addGoals(ArrayList<String> goalsNames){
        for (String goal:goalsNames) {
            checkPageIsLoaded();
            searchAndSelectFirstFromMultiSelect(projectGoal, goal);
        }
    }


    public void checkGoalStatusInTable(String goalName, String goalStatus) {
        $x("//div[@id='tab-goal']//input[@placeholder='Поиск...']").clear();
        $x("//div[@id='tab-goal']//input[@placeholder='Поиск...']").sendKeys(goalName);

        switch (goalStatus){
            case ("Добавлено"):
                $(".prevsnapshot-td-indicator").shouldHave(
                        attribute("data-tooltip", ""+goalStatus+""),
                        cssValue("background-color", "rgba(95, 175, 97, 1)"));
                break;
            case ("Удалено"):
                $(".prevsnapshot-td-indicator").shouldHave(
                        attribute("data-tooltip", ""+goalStatus+""),
                        cssValue("background-color", "rgba(255, 89, 64, 1)"));
                break;
            default:
                break;
        }
    }

    @Step("Нажать редактировать Показатель")
    public void clickEditIndicator() {
        $("div[name='KPIs'] .k-i-edit").click();
    }

    @Step("Нажать удалить Показатель")
    public void clickDeleteIndicator() {
        $("div[name='KPIs'] .k-i-trash").click();
    }

    @Step("Проверить индикацию Показателя")
    public void checkIndicatorStatus(String status) {
        checkPageIsLoaded();
        switch (status) {
            case ("Добавлено"):
                $(".prevsnapshot-td-indicator").shouldHave(attribute("data-tooltip", "Добавлено"),
                        cssValue("background-color", "rgba(95, 175, 97, 1)"));
                break;
            case ("Изменено"):
                $(".prevsnapshot-td-indicator").shouldHave(attribute("data-tooltip", "Изменено"),
                        cssValue("background-color", "rgba(255, 210, 70, 1)"));
                break;
            case ("Удалено"):
                $(".prevsnapshot-td-indicator").shouldHave(attribute("data-tooltip", "Удалено"),
                        cssValue("background-color", "rgba(255, 89, 64, 1)"));
                break;
            default:
                break;
        }
    }
}