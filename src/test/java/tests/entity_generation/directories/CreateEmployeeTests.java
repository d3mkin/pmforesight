package tests.entity_generation.directories;

import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.runner.RunWith;
import helpers.TestSuiteName;
import model.Employee;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.directories.EmployeesRegistry;
import pages.directories.emloyees.EmployeePage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.ENTITY_CREATION)
@Tag("entityGeneration")
@Tag("Regression")
@RunWith(DataProviderRunner.class)
public class CreateEmployeeTests extends BaseTest {

    SingInPage singIn;
    EmployeesRegistry registry;
    EmployeePage createEmployee;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        registry = new EmployeesRegistry();
        createEmployee = new EmployeePage();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание сущности Сотрудника из реестра. Кнопка 'Сохранить'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-135")
    @TmsLink("562")
    public void createEmployeeWithSaveAndCloseTest(User user) {
        parameter("Пользователь", user.getName());
        long currentTime = System.currentTimeMillis();
        Employee employee = new Employee();
        employee
                .setName("Тест_C562_" + currentTime)
                .setLastName("Фамилия_" + currentTime)
                .setFirstName("Имя_" + currentTime)
                .setSecondName("Отчество_" + currentTime);
        registry.open();
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        registry.controlPanel().clickAddButton();
        createEmployee.shouldBeOpened();
        createEmployee.shouldHaveTitle("Пользователь");
        createEmployee.fillFields(employee);
        createEmployee.clickSaveAndClose();
        createEmployee.shouldBeClosed();
        registry.searchEmployees(employee.getName());
        registry.shouldHaveCreatedRecord(employee.getName());
        registry.selectFirstRow();
        registry.clickDelete();
        registry.acceptDelete();
        registry.searchEmployees(employee.getName());
        registry.shouldNotHaveResults();
    }

    //TODO: Написать тест на проверку обязательных полей
}
