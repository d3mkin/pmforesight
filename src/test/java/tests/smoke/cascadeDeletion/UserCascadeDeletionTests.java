package tests.smoke.cascadeDeletion;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import helpers.UserProvider;
import model.Employee;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.directories.EmployeesRegistry;
import pages.directories.emloyees.EmployeePage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.CASCADE_DELETION)
@Tag("cascadeDeletion")
@Tag("Regression")
public class UserCascadeDeletionTests extends BaseTest {
    private SingInPage singIn;
    private EmployeesRegistry employeesRegistry;
    private EmployeePage employeePage;
    private Employee employee;
    private long currentTime;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        employeesRegistry = new EmployeesRegistry();
        employeePage = new EmployeePage();
        employee = new Employee();
        currentTime = System.currentTimeMillis();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Удаление из системы пользователя, не назначенного на роль")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-144")
    @TmsLink("1243")
    public void cascadeDeletionUser(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        employeesRegistry.open();
        employeesRegistry.addUser();
        employee.setName("TestUser" + currentTime)
                .setFirstName("FirstName" + currentTime)
                .setLastName("LastName" + currentTime);
        employeePage.fillFields(employee);
        employeePage.clickSaveAndClose();
        employeesRegistry.changeView("Все сотрудники");
        employeesRegistry.deleteUser(employee.getName());
    }
}
