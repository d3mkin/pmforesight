package pages.managementobjects.gprogram;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.GProgram;
import pages.BasePage;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class GosProgramPage extends BasePage {

    private SelenideElement tabCommonInfo = $(By.linkText("Общая информация"));
    private SelenideElement gosProgramTypeId = $("#control-group-GProgramTypeId");
    private SelenideElement nameValue = $("#Name");
    private SelenideElement textDateStart = $("#StartDate");
    private SelenideElement textDateEnd = $("#FinishDate");
    private SelenideElement ObjectGosprogram = $("#control-group-ParentId");
    private SelenideElement numberObjectGosprogram = $("#Number");
    private SelenideElement description = $("#Reason");
    private SelenideElement descriptionGoals = $("#Goal");
    private SelenideElement goals = $("#control-group-ActivityGoal");
    private SelenideElement quest = $("#Tasks");
    private SelenideElement expectedResult = $("#control-group-Results");
    private SelenideElement consequencesUnrealization = $("#control-group-Effect");
    private SelenideElement responsibleExecutive = $("#control-group-Leader");
    private SelenideElement admins = $("#control-group-Administrator");
    private SelenideElement executors = $("#control-group-WorkGroup");
    //Роли
    private SelenideElement tabRoles = $(By.linkText("Роли"));

    @Step("В модальном окне создания госпрограммы заполнить обязательные поля на вкладке \"Общая информация\"")
    public void fillCommonInfo(GProgram gProgram) {
        tabCommonInfo.click();
        searchAndSelectFirstFromSelect(gosProgramTypeId, gProgram.getTypeObjectGosprogram());
        typeText(nameValue, gProgram.getNameValue());
        textDateStart.waitUntil(visible, 10000);
        typeText(textDateStart, gProgram.getTextDateStart());
        textDateEnd.waitUntil(visible, 10000);
        typeText(textDateEnd, gProgram.getTextDateEnd());


    }

    @Step("В модальном окне создания госпрограммы перейти на вкладку \"Роли\" и заполнить обязательные поля")
    public void fillRoleInfo(GProgram gProgram) {
        tabRoles.click();
        searchAndSelectFirstFromSelect(responsibleExecutive, gProgram.getExecutiveOfficer());

    }
}