package tests.search;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.User;
import pages.administration.AdministrationPage;
import pages.administration.EntityPage;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.SEARCH)
@Tag("search")
@Tag("Regression")
public class SearchEntityInAdminTest extends BaseTest {

    SingInPage singIn;
    AdministrationPage admin;
    EntityPage entity;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        admin = new AdministrationPage();
        entity = new EntityPage();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest (name = "Работоспособность системы поиска для раздела 'Сущности' меню Администрирования")
    @MethodSource("helpers.UserProvider#mainFA")
    //TODO: Завести задачу в JIRA
    @TmsLink("178")
    public void searchEntityInAdminTest(User user) {
        admin.open();
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        admin.openEntity();
        entity.clickFilterName();
        entity.typeName("Acc");
        entity.selectItemByName("Account");
        entity.shouldHaveResultByName("Account");
        entity.clearNameFilter();
        entity.clickFilterCaption();
        entity.typeCaption("Учет");
        entity.selectItemByName("Учетная запись");
        entity.shouldHaveResultByCaption("Учетная запись");
        entity.clearCaptionFilter();
        entity.shouldHaveResult();
    }

}
