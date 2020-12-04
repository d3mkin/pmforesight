package pages.managementobjects.program;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Program;
import pages.BasePage;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramPage extends BasePage {
    //Создание программы
    private SelenideElement nameProgram = $(By.xpath("//input[contains(@data-binding-title,\"Наименование\") and contains(@name,\"Name\")]"));
    private SelenideElement portfolioProgramList = $(By.cssSelector("#control-group-ParentId span.k-widget.k-dropdown"));
    private SelenideElement tabRolesList = $(By.xpath("//li[contains(@id,\"tab-roles\")]"));
    private SelenideElement functionalCustomerProgramList = $(By.cssSelector("#control-group-Customer span.k-widget.k-dropdown"));
    private SelenideElement curatorProgramList = $(By.cssSelector("#control-group-Owner span.k-widget.k-dropdown"));
    private SelenideElement leaderProgramList = $(By.cssSelector("#control-group-Leader span.k-widget.k-dropdown"));
    //Общие
    private final SelenideElement currentProgramStageField = $(".f-card__info");

    //Основные вкладки
    private final SelenideElement tabMeetings = $("a[href='#tab-meeting']");
    private final SelenideElement tabResults = $("a[href='#tab-result']");
    private final SelenideElement tabIndicators = $("a[href='#tab-kpi']");
    private final SelenideElement tabComponents = $("a[href='#tab-component']");
    private final SelenideElement tabActivity = $("a[href='#tab-activity']");
    private final SelenideElement tabDocuments = $("a[href='#tab-documents']");
    private final SelenideElement tabContracts = $("a[href='#tab-contract']");
    private final SelenideElement tabRisksOpportunities = $("a[href='#tab-risk']");
    private final SelenideElement tabLessons = $("a[href='#tab-gleaning']");
    private final SelenideElement tabOrders = $("a[href='#tab-order']");
    private final SelenideElement tabOpenQuestions = $("a[href='#tab-lov']");

    //создание "извлеченного урока" в карточке программы
    private SelenideElement nameProjectAfterOpen = $("#card-Name");
    private SelenideElement learnedLesson = $(By.xpath("//span[text()='Извлечённые уроки']"));
    private SelenideElement positiveLesson = $(By.xpath("//a[contains(@class,\"itv-custom-buttons btn btn-small btn-success k-button\") and contains(@data-tooltip,\"Положительный урок\")]"));
    private SelenideElement searchCreateLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//input[contains(@placeholder,\"Поиск...\")]"));
    private SelenideElement nameLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//div[contains(@class,\"k-grid-content k-auto-scrollable\")]/..//a[contains(@target,\"_blank\")]"));
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
    private final SelenideElement componentsTable = $("#ComponentTable .inlineTableView-grid");
    private final SelenideElement componentsTableHeader = $x("//div[@class='f-widget__header-name']//span[contains(text(),'Компоненты программы')]");
    private final SelenideElement componentsSearch = $("#ComponentTable input[placeholder='Поиск...']");
    private final SelenideElement firstFoundComponent = $x("//div[@id='ComponentTable']//tbody/tr[1]/td[2]");



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

    @Step("Открытие положительного урока")
    public void positiveLessonsLearned() {
        learnedLesson.click();
        positiveLesson.shouldBe(visible);
        positiveLesson.click();
    }

    @Step("Проверка создания урока")
    public void checkCreateLesson(String value) {
        searchCreateLesson.clear();
        searchCreateLesson.sendKeys(value);
        assertTrue(nameLesson.getText().equals(value));
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

    @Step("Проверка наличия таблицы 'Компоненты программы'")
    public void shouldHaveComponentsTable() {
        componentsTable.shouldBe(visible);
        componentsTableHeader.shouldHave(text("Компоненты программы"));
    }

    public void shouldHaveComponent(String componentName) {
        componentsSearch.click();
        componentsSearch.sendKeys(componentName);
        firstFoundComponent.shouldBe(visible).shouldHave(text(componentName));
    }
}
