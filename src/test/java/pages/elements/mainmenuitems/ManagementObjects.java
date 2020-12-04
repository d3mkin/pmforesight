package pages.elements.mainmenuitems;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pages.managementobjects.*;
import pages.managementobjects.contracts.ContractsRegistry;
import pages.managementobjects.gprogram.GProgramRegistry;
import pages.managementobjects.nationalproject.NationalProjectRegistry;
import pages.managementobjects.portfolio.PortfolioRegistry;
import pages.managementobjects.program.ProgramRegistry;
import pages.managementobjects.project.ProjectRegistry;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class ManagementObjects extends AbstractMenuItem {
    private SelenideElement managementObjects = menu.$("li[f-menu-slide-menulistid='1']");
    private SelenideElement gProgramAndObjects = managementObjects.$("li[f-menu-slide-menulistid=\"22\"]");
    private SelenideElement gProgramHierarchy = managementObjects.$("li[f-menu-slide-menulistid=\"20043\"]");
    private SelenideElement nationalProject = managementObjects.$("li[f-menu-slide-menulistid=\"20051\"]");
    private SelenideElement portfolio = managementObjects.$("li[f-menu-slide-menulistid=\"2\"]");
    private SelenideElement programs = managementObjects.$("li[f-menu-slide-menulistid=\"10040\"]");
    private SelenideElement projects = managementObjects.$("li[f-menu-slide-menulistid=\"3\"]");
    private SelenideElement projectsInitiative = managementObjects.$("li[f-menu-slide-menulistid=\"10024\"]");
    private SelenideElement notProjectEvent = managementObjects.$("li[f-menu-slide-menulistid=\"20040\"]");
    private SelenideElement contracts = managementObjects.$("li[f-menu-slide-menulistid=\"10034\"]");
    private SelenideElement results = managementObjects.$("li[f-menu-slide-menulistid=\"20044\"]");
    private SelenideElement stagesWorksKT = managementObjects.$("li[f-menu-slide-menulistid=\"4\"]");


    @Step("Развернуть 'Объекты управления'")
    public void expand() {
            managementObjects.click();
            Selenide.sleep(1000);

    }

    @Step("Открыть 'Госпрограммы и объекты госпрограм'")
    public GProgramRegistry openGProgramAndObjects() {
        clickItem(managementObjects, gProgramAndObjects);
        return new GProgramRegistry();
    }

    @Step("Открыть 'Иерархия госпрограмм'")
    public GProgramHierarchyRegistry openGProgramHierarchy() {
        clickItem(managementObjects, gProgramHierarchy);
        return new GProgramHierarchyRegistry();
    }

    @Step("Открыть 'Национальные проекты'")
    public NationalProjectRegistry openNationalProject() {
        clickItem(managementObjects, nationalProject);
        return new NationalProjectRegistry();
    }

    @Step("Открыть 'Портфели'")
    public PortfolioRegistry openPortfolio() {
        clickItem(managementObjects, portfolio);
        return new PortfolioRegistry();
    }

    @Step("Открыть 'Программы'")
    public ProgramRegistry openPrograms() {
        clickItem(managementObjects, programs);
        return new ProgramRegistry();
    }

    @Step("Открыть 'Проекты'")
    public ProjectRegistry openProjects() {
        clickItem(managementObjects, projects);
        return new ProjectRegistry();
    }

    @Step("Открыть 'Предложения по проекту'")
    public ProjectsInitiativeRegistry openProjectsInitiative() {
        clickItem(managementObjects, projectsInitiative);
        return new ProjectsInitiativeRegistry();
    }

    @Step("Открыть 'Непроектные мероприятия'")
    public NonProjectEventRegistry openNotProjectEvent() {
        clickItem(managementObjects, notProjectEvent);
        return new NonProjectEventRegistry();
    }

    @Step("Открыть 'Контракты'")
    public ContractsRegistry openContracts() {
        clickItem(managementObjects, contracts);
        return new ContractsRegistry();
    }

    @Step("Открыть 'Результаты'")
    public ResultsRegistry openResults() {
        clickItem(managementObjects, results);
        return new ResultsRegistry();
    }

    @Step("Открыть 'Этапы/Работы/КТ'")
    public StagesWorksAndPointsRegistry openStagesWorksKT() {
        clickItem(managementObjects, stagesWorksKT);
        return new StagesWorksAndPointsRegistry();
    }

    public ManagementObjects visible() {
        managementObjects.shouldBe(visible);
        return this;
    }

    public ManagementObjects hasCorrectText() {
        managementObjects.shouldHave(text("Объекты управления"));
        return this;
    }
}
