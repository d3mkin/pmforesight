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
    private final SelenideElement tabMeetings = $(By.cssSelector("a[href='#tab-meeting']"));
    private final SelenideElement tabResults = $(By.cssSelector("a[href='#tab-result']"));
    private final SelenideElement tabIndicators = $(By.cssSelector("a[href='#tab-kpi']"));
    private final SelenideElement tabActivity = $(By.cssSelector("a[href='#tab-activity']"));
    private final SelenideElement tabDocuments = $(By.cssSelector("a[href='#tab-documents']"));
    private final SelenideElement tabContracts = $(By.cssSelector("a[href='#tab-contract']"));
    private final SelenideElement tabRisksOpportunities = $(By.cssSelector("a[href='#tab-risk']"));
    private final SelenideElement tabLessons = $(By.cssSelector("a[href='#tab-gleaning']"));
    private final SelenideElement tabOrders = $(By.cssSelector("a[href='#tab-order']"));
    private final SelenideElement tabOpenQuestions = $(By.cssSelector("a[href='#tab-lov']"));

    //создание "извлеченного урока" в карточке программы
    private SelenideElement nameProjectAfterOpen = $("#card-Name");
    private SelenideElement learnedLesson = $(By.xpath("//span[text()='Извлечённые уроки']"));
    private SelenideElement positiveLesson = $(By.xpath("//a[contains(@class,\"itv-custom-buttons btn btn-small btn-success k-button\") and contains(@data-tooltip,\"Положительный урок\")]"));
    private SelenideElement searchCreateLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//input[contains(@placeholder,\"Поиск...\")]"));
    private SelenideElement nameLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//div[contains(@class,\"k-grid-content k-auto-scrollable\")]/..//a[contains(@target,\"_blank\")]"));
    //Показатели
    private final SelenideElement indicatorAddButton = $("#KPIDivContent .itv-add-button[data-tooltip=\"Показатель\"]");
    //Таблицы показателей
    private final SelenideElement objectIndicatorsTable = $("#KPIDivContent .k-grid-content");
    private final SelenideElement objectIndicatorsHeader = $x("//span[contains(text(),'Показатели объекта')]");
    private final SelenideElement indicatorTableSearchInput = $("#KPIDivContent input[placeholder='Поиск...']");
    private final SelenideElement firstFoundIndicator = $x("//div[@id='KPIDivContent']//tbody/tr[1]/td[2]");

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
        objectIndicatorsHeader.shouldHave(text("Показатели объекта"));
    }

    @Step ("Проверить наличие показателя в таблице 'Персональные показатели проекта'")
    public void shouldHaveIndicator(String indicatorName) {
        indicatorTableSearchInput.click();
        indicatorTableSearchInput.sendKeys(indicatorName);
        firstFoundIndicator.shouldBe(visible);
        firstFoundIndicator.shouldHave(text(indicatorName));
    }
}
