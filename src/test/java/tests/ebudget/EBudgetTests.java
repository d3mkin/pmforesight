package tests.ebudget;

import com.codeborne.selenide.Selenide;
import helpers.TestSuiteName;
import io.qameta.allure.Epic;
import io.qameta.allure.TmsLink;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.project.ProjectPage;
import pages.managementobjects.project.ProjectRegistry;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.ELECTRONIC_BUDGET)
@Tag("EBudget")
@Tag("Regression")
public class EBudgetTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
    private ProjectRegistry projectRegistry;
    private String importProject;
    private String importProjectPortfolio;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        projectPage = new ProjectPage();
        projectRegistry = new ProjectRegistry();
        importProject = "Финансовая поддержка семей при рождении детей";
        importProjectPortfolio = "Портфель национальных проектов";
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "ЭБ: создание проекта с выбором региона")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("C1322")
    @Tag("ATEST-168")
    @TmsLink("1322")
    public void CreateEBudgetFromRegistryTest(User user) {
        parameter("Пользователь", user.getName());
        parameter("Проект для импорта", importProject);
        parameter("Связь с портфелем", importProjectPortfolio);
        singIn.asUser(user);
        projectRegistry.open();
        projectRegistry.shouldBeRegistry();
        projectRegistry.changeView("Все проекты");
        projectRegistry.importProjectFromEBudget(importProject);
        projectRegistry.searchAndOpenProject(importProject);
        Selenide.sleep(3000);
        projectPage.checkProjectName(importProject);
        projectPage.checkProjectPortfolioName(importProjectPortfolio);
        projectRegistry.open();
        projectRegistry.shouldBeRegistry();
        projectRegistry.deleteProject(importProject);
    }
}
