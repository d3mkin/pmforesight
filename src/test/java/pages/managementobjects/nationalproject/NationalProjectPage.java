package pages.managementobjects.nationalproject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.NationalProject;
import pages.elements.BasePage;

import static com.codeborne.selenide.Selenide.$;


public class NationalProjectPage extends BasePage {
    private final SelenideElement tabCommonInfo = $(By.linkText("Общая информация"));
    private final SelenideElement tabRoles = $(By.linkText("Роли"));
    private final SelenideElement nameValue = $("#Name");
    private final SelenideElement target = $("#control-group-ActivityGoal");

    private final SelenideElement curator = $("#control-group-Owner");
    private final SelenideElement supervisor = $("#control-group-Leader");
    private final SelenideElement administrator = $("#control-group-Administrator");

    @Step("В модальном окне создания Национального проекта заполнить обязательные поля на вкладке 'Общая информация' и 'Роли'")
    public void fillInGeneralInformation(NationalProject nationalProject) {
        tabCommonInfo.click();
        typeOrSkip(nameValue, nationalProject.getNameValue());
        searchInSelectAndClickToFirst(target, nationalProject.getTarget());
        tabRoles.click();
        searchInSelectAndClickToFirst(curator, nationalProject.getCurator());
        searchInSelectAndClickToFirst(supervisor, nationalProject.getSupervisor());
        searchInSelectAndClickToFirst(administrator, nationalProject.getAdministrator());
    }
}
