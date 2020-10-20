package tests.entity_generation.initiativeprogects;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.ProjectsInitiative;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.ProjectsInitiativeRegistry;
import pages.managementobjects.initiativeprojects.ProjectsInitiativePage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.DATA_GENERATION)
@Tag("entityGeneration")
@Tag("Regression")
public class CreateProjectInitiativeTests extends BaseTest {
    private ProjectsInitiativeRegistry registry;
    private ProjectsInitiativePage projectInitiativePage;
    private ProjectsInitiative projectsInitiative;
    private SingInPage singIn;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        registry = new ProjectsInitiativeRegistry();
        projectInitiativePage = new ProjectsInitiativePage();
        projectsInitiative=new ProjectsInitiative();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest
    @DisplayName("Создание сущности Предложение по проекту из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-138")
    @TmsLink("372")
    public void CreateProjectInitiativeSaveAndCloseTest(User user) {
        long currentTime = System.currentTimeMillis();
        projectsInitiative.setName("Тест_C372_"+currentTime)
                .setTypeOfProject("Закупка")
                .setProjectStartDate("12.11.2019")
                .setProjectDateEnd("20.11.2019")
                .setInitiator(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        registry.open();
        registry.controlPanel().clickAddButton();
        projectInitiativePage.shouldBeOpened();
        projectInitiativePage.fillGeneralInfo(projectsInitiative);
        projectInitiativePage.fillRoleInfo(projectsInitiative);
        projectInitiativePage.clickSaveAndClose();
        registry.searchGProgram(projectsInitiative.getName());
        registry.shouldHaveCreatedRecord(projectsInitiative.getName());
        registry.selectFirstRow();
        registry.clickDelete();
        registry.acceptDelete();
        registry.searchGProgram(projectsInitiative.getName());
        registry.shouldNotHaveResults();
    }

    @ParameterizedTest
    @DisplayName("Создание сущности Предложение по проекту из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-139")
    @TmsLink("376")
    public void checkRequiredMessageTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        registry.open();
        registry.controlPanel().clickAddButton();
        projectInitiativePage.shouldBeOpened();
        projectInitiativePage.shouldHaveTitle("Предложения по проекту");
        projectInitiativePage.clickSave();
        projectInitiativePage
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Наименование\"",
                        "Необходимо заполнить поле \"Тип проекта\"",
                        "Необходимо заполнить поле \"Дата начала проекта\"",
                        "Необходимо заполнить поле \"Дата окончания проекта\"",
                        "Необходимо заполнить поле \"Инициатор \" "
                );
        projectInitiativePage.closeDialog();
        projectInitiativePage.clickClose();
        projectInitiativePage.shouldBeClosed();
        registry.shouldBeRegistry();
    }

}
