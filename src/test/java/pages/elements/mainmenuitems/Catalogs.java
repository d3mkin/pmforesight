package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.directories.*;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class Catalogs extends AbstractMenuItem {

    private final SelenideElement catalogs = menu.$("li[f-menu-slide-menulistid=\"10032\"]");
    private final SelenideElement employees = catalogs.$("li[f-menu-slide-menulistid=\"19\"]");
    private final SelenideElement orgUnit = catalogs.$("li[f-menu-slide-menulistid=\"20\"]");
    private final SelenideElement contractor = catalogs.$("li[f-menu-slide-menulistid=\"10030\"]");
    private final SelenideElement externalOrganization = catalogs.$("li[f-menu-slide-menulistid=\"10031\"]");
    private final SelenideElement courses = catalogs.$("li[f-menu-slide-menulistid=\"10025\"]");


    @Override
    public void expand() {
        catalogs.click();
        Selenide.sleep(1000);
    }

    @Override
    public Catalogs visible() {
        catalogs.shouldBe(visible);
        return this;
    }

    @Override
    public Catalogs hasCorrectText() {
        catalogs.shouldHave(text("Справочники"));
        return this;
    }

    @Step("Открыть 'Сотрудники'")
    public EmployeesRegistry openEmployees() {
        clickItem(catalogs, employees);
        return new EmployeesRegistry();
    }

    @Step("Открыть 'Подразделения'")
    public DivisionsRegistry openOrgUnit() {
        clickItem(catalogs, orgUnit);
        return new DivisionsRegistry();
    }

    @Step("Открыть 'Подрядчики'")
    public ContractorsRegistry openContractor() {
        clickItem(catalogs, contractor);
        return new ContractorsRegistry();
    }

    @Step("Открыть 'Внешние организации'")
    public ExternalOrganizationsRegistry openExternalOrganization() {
        clickItem(catalogs, externalOrganization);
        return new ExternalOrganizationsRegistry();
    }

    @Step("Открыть 'Курсы'")
    public CoursesRegistry openCourses() {
        clickItem(catalogs, courses);
        return new CoursesRegistry();
    }
}
