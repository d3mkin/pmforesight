package pages.managementobjects.initiativeprojects;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import model.ProjectsInitiative;
import pages.elements.BasePage;

import static com.codeborne.selenide.Selenide.$;

public class ProjectsInitiativePage extends BasePage {
    private SelenideElement tabCommonInfo = $(By.linkText("Общая информация"));
    private SelenideElement tabRoles = $(By.linkText("Роли"));

    private SelenideElement name = $("#Name");
    private SelenideElement nameOfDirection = $("#control-group-StrategicDirectionId");
    private SelenideElement typeOfProject = $("#control-group-ProjectTypeId");
    private SelenideElement portfolio_program = $("#control-group-ParentId");
    private SelenideElement commentOnPortfolio_program = $("#ParentComment");
    private SelenideElement projectBackground = $("#Explanation");
    private SelenideElement formalReasonForInitiationLocation = $("#Location");
    private SelenideElement projectStartDate = $("#StartDate");
    private SelenideElement projectDateEnd = $("#FinishDate");

    private SelenideElement restrictions = $("#Limitations");
    private SelenideElement assumptionsAndProposals = $("#Allowance");
    private SelenideElement proposalOnTheFormOfImplementation = $("#control-group-ObjectTypeId");
    private SelenideElement additionalInformation = $("#MoreInformation");

    private SelenideElement initiator = $("#control-group-Initiator");
    private SelenideElement functionalCustomer = $("#control-group-FunctionalCustomer");
    private SelenideElement curatorOfTheProject = $("#control-group-Owner");
    private SelenideElement projectManager = $("#control-group-Leader");
    private SelenideElement mainExecutorsAndCoExecutorOfTheProject = $("#control-group-Executor");
    private SelenideElement projectPlanningGroup = $("#control-group-PlaningGroup");
    private SelenideElement CPO = $("#control-group-CPO");
    private SelenideElement secretaryPC = $("#control-group-CPOSecretary");

    @Step("В модальном окне создания Предложения по проекту заполнить обязательные поля на вкладке \"Общая информация\"")
    public void fillGeneralInfo(ProjectsInitiative projectsInitiative) {
        tabCommonInfo.click();
        typeOrSkip(name, projectsInitiative.getName());
        searchInSelectAndClickToFirst(nameOfDirection, projectsInitiative.getNameOfDirection());
        searchInSelectAndClickToFirst(typeOfProject, projectsInitiative.getTypeOfProject());
        searchInSelectAndClickToFirst(portfolio_program, projectsInitiative.getPortfolio_program());
        typeOrSkip(commentOnPortfolio_program, projectsInitiative.getCommentOnPortfolio_program());
        typeOrSkip(projectBackground, projectsInitiative.getProjectBackground());
        typeOrSkip(formalReasonForInitiationLocation, projectsInitiative.getFormalReasonForInitiationLocation());
        typeDate(projectStartDate, projectsInitiative.getProjectStartDate());
        typeDate(projectDateEnd, projectsInitiative.getProjectDateEnd());
    }

    @Step("В модальном окне создания Предложения по проекту перейти на вкладку \"Дополнительно\" и заполнить обязательные поля")
    public void fillAdditionallyInfo(ProjectsInitiative projectsInitiative) {
        typeOrSkip(restrictions, projectsInitiative.getRestrictions());
        typeOrSkip(assumptionsAndProposals, projectsInitiative.getAssumptionsAndProposals());
        searchInSelectAndClickToFirst(proposalOnTheFormOfImplementation, projectsInitiative.getProposalOnTheFormOfImplementation());
        typeOrSkip(additionalInformation, projectsInitiative.getAdditionalInformation());
    }

    @Step("В модальном окне создания Предложения по проекту перейти на вкладку \"Роли\" и заполнить обязательные поля")
    public void fillRoleInfo(ProjectsInitiative projectsInitiative) {
        tabRoles.click();
        searchInSelectAndClickToFirst(initiator,projectsInitiative.getInitiator());
        searchInSelectAndClickToFirst(functionalCustomer,projectsInitiative.getFunctionalCustomer());
        searchInSelectAndClickToFirst(curatorOfTheProject,projectsInitiative.getCuratorOfTheProject());
        searchInSelectAndClickToFirst(projectManager,projectsInitiative.getProjectManager());
        searchInSelectAndClickToFirst(mainExecutorsAndCoExecutorOfTheProject,projectsInitiative.getMainExecutorsAndCoExecutorOfTheProject());
        searchInSelectAndClickToFirst(projectPlanningGroup,projectsInitiative.getProjectPlanningGroup());
        searchInSelectAndClickToFirst(CPO,projectsInitiative.getCPO());
        searchInSelectAndClickToFirst(secretaryPC,projectsInitiative.getSecretaryPC());
    }
}
