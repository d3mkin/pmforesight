package tests.entity_generation.projects;

import com.codeborne.selenide.Configuration;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.Project;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.project.ProjectRegistry;
import pages.managementobjects.project.ProjectPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.ENTITY_CREATION)
@Story("Проект")
@Tag("entityGeneration")
@Tag("Regression")
public class CreateProjectTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
    private ProjectRegistry projectRegistry;
    private Project project;
    private long currentTime;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        projectPage = new ProjectPage();
        projectRegistry = new ProjectRegistry();
        project = new Project();
        currentTime = System.currentTimeMillis();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание сущности Проект из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("C379")
    @Tag("ATEST-105")
    @TmsLink("379")
    public void projectSaveAndClose(User user) {
        project
                .setName("Тест_C379_" + currentTime)
                .setType("Информационные технологии")
                .setProgram("Портфель национальных проектов")
                .setCurator(user.getName())
                .setSupervisor(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        projectRegistry.addProject();
        projectPage.fillFields(project);
        projectPage.clickSaveAndClose();
        projectRegistry.deleteProject(project);
    }

    @ParameterizedTest(name = "Создание сущности Проект из реестра. Сообщение о несохранённых изменениях для кнопки закрыть")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-106")
    @Tag("C384")
    @TmsLink("384")
    public void shouldHaveMessageAboutUnsavedData(User user) {
        project
                .setName("Тест_C384_" + currentTime)
                .setType("Информационные технологии")
                .setProgram("Портфель национальных проектов")
                .setCurator(user.getName())
                .setSupervisor(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        projectRegistry.addProject();
        projectPage.fillFields(project);
        long L = Configuration.timeout;
        projectPage.clickClose();
        projectPage.dialogWindowShouldHaveTitle();
        projectPage.shouldHaveMessageAboutUnsaved();
        projectPage.clickDialogCancel();
        projectPage.clickClose();
        projectPage.clickDialogSave();
        projectRegistry.deleteProject(project);
    }

    @ParameterizedTest(name = "Создание сущности Проект из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки Сохранить")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("C383")
    @Tag("ATEST-107")
    @TmsLink("383")
    public void shouldHaveMessageAboutRequiredFieldsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        projectRegistry.addProject();
        projectPage.modalWindowShouldBeOpened();
        projectPage.modalWindowShouldHaveTitle("Проект");
        projectPage.clickSave();
        projectPage
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Наименование\"",
                        "Необходимо заполнить поле \"Тип проекта\"",
                        "Необходимо заполнить поле \"Портфель или программа\"",
                        "Необходимо заполнить поле \"Куратор\"",
                        "Необходимо заполнить поле \"Руководитель\""
                );
        projectPage.closeDialog();
        projectPage.clickClose();
        projectRegistry.shouldBeRegistry();
    }
}

