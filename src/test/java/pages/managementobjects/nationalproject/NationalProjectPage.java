package pages.managementobjects.nationalproject;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.NationalProject;
import pages.BasePage;

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
        typeText(nameValue, nationalProject.getNameValue());
        searchAndSelectFirstFromSelect(target, nationalProject.getTarget());
        tabRoles.click();
        searchAndSelectFirstFromSelect(curator, nationalProject.getCurator());
        searchAndSelectFirstFromSelect(supervisor, nationalProject.getSupervisor());
        searchAndSelectFirstFromSelect(administrator, nationalProject.getAdministrator());
    }
}
