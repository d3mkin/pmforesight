package tests.access_permission;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import helpers.TestSuiteName;
import model.Employee;
import model.User;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.administration.AdministrationPage;
import pages.administration.GroupsPage;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.directories.EmployeesRegistry;
import pages.directories.emloyees.EmployeePage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.PERMISSION)
@Tag("accessPermission")
@Tag("Regression")
public class MatchExternalObserverToTopManagementTest extends BaseTest {

    private SingInPage singIn;
    private EmployeesRegistry employeeRegistry;
    private EmployeePage employeePage;
    private long currentTime;
    private Employee employee;
    private AdministrationPage adminPage;
    private GroupsPage groups;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        employeeRegistry = new EmployeesRegistry();
        employeePage = new EmployeePage();
        currentTime = System.currentTimeMillis();
        employee = new Employee();
        adminPage = new AdministrationPage();
        groups = new GroupsPage();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest (name = "Соответствие группы 'Внешний наблюдатель' признаку 'Топ-руководство' при добавлении группы в карточку пользователя")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-79")
    @TmsLink("1229")
    public void checkTopManagementMatchingExternalObserverInUserCard(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        employee
                .setName("Тест C1229" + currentTime)
                .setLastName("Фамилия " + currentTime)
                .setFirstName("Имя " + currentTime)
                .setSecondName("Отчество " + currentTime)
                .setGroups("Команда управления проектом");
        employeeRegistry.addUser();
        employeePage.shouldBeOpened();
        employeePage.shouldHaveTitle("Пользователь");
        employeePage.fillFields(employee);
        employeePage.clickSaveAndOpenCard();
        employeePage.userCarsShouldBeOpened(employee);
        employeePage.checkUserStatusIsTop("Нет");
        employeePage.checkUserIsMemberOfGroup(employee.getGroups());
        employeePage.checkUserIsNotMemberOfGroup("Внешний наблюдатель");
        employeePage.addUserGroup("Внешний наблюдатель");
        employeePage.checkUserStatusIsTop("Да");
        employeePage.checkUserIsMemberOfGroup("Внешний наблюдатель");
        employeePage.closeForm();
        employeeRegistry.shouldBeRegistry();
        employeeRegistry.searchEmployees(employee.getName());
        employeeRegistry.shouldHaveCreatedRecord(employee.getName());
        employeeRegistry.checkIsTop("Да");
        employeeRegistry.checkUserIsMemberOfGroup("Внешний наблюдатель");
        employeeRegistry.selectFirstRow();
        employeeRegistry.clickDelete();
        employeeRegistry.acceptDelete();
        employeeRegistry.searchEmployees(employee.getName());
        employeeRegistry.shouldNotHaveResults();
    }

    @ParameterizedTest (name = "Соответствие группы 'Внешний наблюдатель' признаку 'Топ-руководство' при добавлении пользователя в группу" )
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-111")
    @TmsLink("1230")
    public void checkTopManagementMatchingExternalObserverInGroupCard(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        adminPage.open();
        adminPage.clickExpandedItem("Доступ");
        adminPage.clickItemInExpandedMenu("Группы");
        groups.addEmployeeToGroup("Внешний наблюдатель", user);
        employeeRegistry.open();
        employeeRegistry.searchEmployees(user.getName());
        employeeRegistry.shouldHaveCreatedRecord(user.getName());
        employeeRegistry.checkIsTop("Да");
        employeeRegistry.checkUserIsMemberOfGroup("Внешний наблюдатель");
        employeeRegistry.openUserCard();
        employeePage.checkUserStatusIsTop("Да");
        employeePage.checkUserIsMemberOfGroup("Внешний наблюдатель");
        employeePage.removeUserGroup("Внешний наблюдатель");
        employeePage.checkUserStatusIsTop("Нет");
        employeePage.checkUserIsNotMemberOfGroup("Внешний наблюдатель");
    }
}
