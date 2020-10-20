package tests.menu;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import helpers.TestSuiteName;
import model.User;
import pages.administration.*;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import tests.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.MENU_AND_REGISTRY)
@Tag("menu")
@Tag("Regression")
public class UIAdminCheckTest extends BaseTest {

    SingInPage singIn;
    AdministrationPage adminPage;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        adminPage = new AdministrationPage();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @Ignore
    @ParameterizedTest
    @DisplayName("Отображение и работоспособность функций страницы Администрирование")
    @MethodSource("helpers.UserProvider#UsersFA")
    //TODO: Завести задачу в JIRA
    @TmsLink("342")
    public void checkAdminUI(User user) {
        adminPage.open();
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        adminPage.checkEntity(new EntityPage());
        adminPage.checkLifeCycleEntity(
                new StagesPage(),
                new SettingTransitionStatusAndStagePage(),
                new HistoryTransitionStatusAndStagePage(),
                new IndicatorsPage()
        );
        adminPage.checkKT(
                new TypeKTPage(),
                new TypicalKTPage(),
                new TemplatesCalendarPage(),
                new TypicalPointCheckListPage()
        );
        adminPage.checkDocuments(new DocumentsPage());
        adminPage.checkAccess(
                new GroupsPage(),
                new EmployeesPage(),
                new CardRolesPage(),
                new UnitsPage(),
                new SubstitutionPage(),
                new AccessMatrixPage(),
                new SetsAccessMatrixPage(),
                new RulesAccessMatrixPage(),
                new ImpersonateRolePage(),
                new AccessGroupPage());
        adminPage.checkReview(
                new ReviewCyclePage(),
                new ReviewersPage(),
                new ReviewCycleCardPage()
        );
        adminPage.checkStatusReports(
                new ReportEventsPage(),
                new ReportEventsCalendarPage(),
                new ReportCollectionFormPage()
        );
        adminPage.checkSystemSetting(
                new EntitySettingPage(),
                new EntitySchemaPage(),
                new ViewPage(),
                new NotificationsPage(),
                new NotificationsQueuePage(),
                new ScheduledTaskPage(),
                new ScheduledHistoryPage(),
                new SystemLogsPage(),
                new UpdateSystemPage(),
                new SystemInformationPage()

        );
        adminPage.checkView(
                new MenuSettingPage(),
                new PagesPage(),
                new PageElementsPage(),
                new ReportsPage(),
                new ReportsCategoriesPages(),
                new SystemTourPage()
        );

    }

}
