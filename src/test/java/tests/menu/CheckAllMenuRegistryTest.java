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
    @MethodSource("helpers.UserProvider#UsersFA")
    //TODO: Завести задачу в JIRA и проверку на коды ошибок + сделать переключение представлений
    @TmsLink("303")
    public void userFACanSeeMenuAndOpenRegistry(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        index = new IndexPage();
        MainMenu mm = index.mainMenu();
        mm.clickExpand();
        mm.managementObjects().expand();
        mm.shouldHaveAndOpenManagementObjectsItemsForUserFA();
        mm.managementObjects().expand();
        mm.monitoringAndControl().expand();
        mm.shouldHaveAndOpenMonitoringAndControlItemsForUserFA();
        mm.monitoringAndControl().expand();
        mm.communication().expand();
        mm.shouldHaveAndOpenCommunicationForUserFA();
        mm.communication().expand();
        mm.managementOfRisks().expand();
        mm.shouldHaveAndOpenManageOfRisksForUserFA();
        mm.managementOfRisks().expand();
        mm.personnelManagement().expand();
        mm.shouldHaveAndOpenPersonalManagementForUserFA();
        mm.personnelManagement().expand();
        mm.goalsAndIndicators().expand();
        mm.shouldHaveAndOpenGoalAndIndicatorsForUserFA();
        mm.goalsAndIndicators().expand();
        mm.knowledgeBaseAndDocuments().expand();
        mm.shouldHaveAndOpenKnowledgeBaseAndDocumentsForUserFA();
        mm.knowledgeBaseAndDocuments().expand();
        mm.reportsr().expand();
        mm.shouldHaveAndOpenReportsForUserFA();
        mm.reportsr().expand();
        mm.catalogs().expand();
        mm.shouldHaveAndOpenCatalogsForUserFA();
        mm.catalogs().expand();
        mm.administartion().open();
        mm.shouldHaveAndOpenAdministrarioForUserFA();
        mm.supportt().open();
        mm.shouldHaveAndOpenSuportForUserFA();
    }


}
