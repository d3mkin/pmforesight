package tests.menu;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.elements.MainMenu;
import pages.index.IndexPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.MENU_AND_REGISTRY)
@Tag("menu")
@Tag("Regression")
public class CheckAllMenuRegistryTest extends BaseTest {
    private IndexPage index;
    private SingInPage singIn;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Проверка работоспособности главного выдвижного меню и реестров")
    @MethodSource("helpers.UserProvider#mainFA")
    //TODO: Завести задачу в JIRA и проверку на коды ошибок + сделать переключение представлений
    @TmsLink("303")
    public void userFACanSeeMenuAndOpenRegistry(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        index = new IndexPage();
        MainMenu mainMenu = index.mainMenu();
        mainMenu.clickExpand();
        mainMenu.managementObjects().expand();
        mainMenu.shouldHaveAndOpenManagementObjectsItemsForUserFA();
        mainMenu.managementObjects().expand();
        mainMenu.monitoringAndControl().expand();
        mainMenu.shouldHaveAndOpenMonitoringAndControlItemsForUserFA();
        mainMenu.monitoringAndControl().expand();
        mainMenu.communication().expand();
        mainMenu.shouldHaveAndOpenCommunicationForUserFA();
        mainMenu.communication().expand();
        mainMenu.managementOfRisks().expand();
        mainMenu.shouldHaveAndOpenManageOfRisksForUserFA();
        mainMenu.managementOfRisks().expand();
        mainMenu.personnelManagement().expand();
        mainMenu.shouldHaveAndOpenPersonalManagementForUserFA();
        mainMenu.personnelManagement().expand();
        mainMenu.goalsAndIndicators().expand();
        mainMenu.shouldHaveAndOpenGoalAndIndicatorsForUserFA();
        mainMenu.goalsAndIndicators().expand();
        mainMenu.knowledgeBaseAndDocuments().expand();
        mainMenu.shouldHaveAndOpenKnowledgeBaseAndDocumentsForUserFA();
        mainMenu.knowledgeBaseAndDocuments().expand();
        mainMenu.reportsr().expand();
        mainMenu.shouldHaveAndOpenReportsForUserFA();
        mainMenu.reportsr().expand();
        mainMenu.catalogs().expand();
        mainMenu.shouldHaveAndOpenCatalogsForUserFA();
        mainMenu.catalogs().expand();
        mainMenu.administartion().open();
        mainMenu.shouldHaveAndOpenAdministrarioForUserFA();
        mainMenu.supportt().open();
        mainMenu.shouldHaveAndOpenSuportForUserFA();
    }


}
