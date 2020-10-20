package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.personnel_management.OrganizationalStructureRegistry;
import pages.personnel_management.TrainingRegistry;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class PersonnelManagement extends AbstractMenuItem {

    private SelenideElement personnelManagement = menu.$("li[f-menu-slide-menulistid=\"18\"]");
    private SelenideElement orgStructure = $("li[f-menu-slide-menulistid=\"10027\"]");
    private SelenideElement courseWork = $("li[f-menu-slide-menulistid=\"10026\"]");

    @Step("Открыть 'Организационная структура'")
    public OrganizationalStructureRegistry openOrgStructure() {
        clickItem(personnelManagement, orgStructure);
        return new OrganizationalStructureRegistry();
    }

    @Step("Открыть 'Обучение'")
    public TrainingRegistry openCourseWork() {
        clickItem(personnelManagement, courseWork);
        return new TrainingRegistry();
    }

    @Override
    public void expand() {
        personnelManagement.click();
        Selenide.sleep(1000);
    }

    @Override
    public PersonnelManagement visible() {
        personnelManagement.shouldBe(visible);
        return this;
    }

    @Override
    public PersonnelManagement hasCorrectText() {
        personnelManagement.shouldHave(text("Управление персоналом"));
        return this;
    }
}
