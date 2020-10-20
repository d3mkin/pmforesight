package pages.managementobjects.program;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.Program;
import pages.elements.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProgramPage extends BasePage {
    //Создание программы
    private SelenideElement nameProgram = $(By.xpath("//input[contains(@data-binding-title,\"Наименование\") and contains(@name,\"Name\")]"));
    private SelenideElement portfolioProgramList = $(By.cssSelector("#control-group-ParentId span.k-widget.k-dropdown"));
    private SelenideElement tabRolesList = $(By.xpath("//li[contains(@id,\"tab-roles\")]"));
    private SelenideElement functionalCustomerProgramList = $(By.cssSelector("#control-group-Customer span.k-widget.k-dropdown"));
    private SelenideElement curatorProgramList = $(By.cssSelector("#control-group-Owner span.k-widget.k-dropdown"));
    private SelenideElement leaderProgramList = $(By.cssSelector("#control-group-Leader span.k-widget.k-dropdown"));
    //создание "извлеченного урока" в карточке программы
    private SelenideElement nameProjectAfterOpen = $("#card-Name");
    private SelenideElement learnedLesson = $(By.xpath("//span[text()='Извлечённые уроки']"));
    private SelenideElement positiveLesson = $(By.xpath("//a[contains(@class,\"itv-custom-buttons btn btn-small btn-success k-button\") and contains(@data-tooltip,\"Положительный урок\")]"));
    private SelenideElement searchCreateLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//input[contains(@placeholder,\"Поиск...\")]"));
    private SelenideElement nameLesson = $(By.xpath("//div[contains(@id,\"LessonInlineTable\")]/..//div[contains(@class,\"k-grid-content k-auto-scrollable\")]/..//a[contains(@target,\"_blank\")]"));

    public void fillFields(Program program) {
        typeOrSkip(nameProgram, program.getName());
        searchInSelectAndClickToFirstWithCheckDropDown(portfolioProgramList, program.getPortfolio());
        tabRolesList.click();
        searchInSelectAndClickToFirstWithCheckDropDown(functionalCustomerProgramList, program.getCustomer());
        searchInSelectAndClickToFirstWithCheckDropDown(curatorProgramList, program.getCurator());
        searchInSelectAndClickToFirstWithCheckDropDown(leaderProgramList, program.getSupervisor());
    }

    @Step("Проверка открытия и корректного названия программы")
    public void openAndCheckProgramName(String nproject) {
        assertTrue(nameProjectAfterOpen.getText().contains(nproject));
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
}
