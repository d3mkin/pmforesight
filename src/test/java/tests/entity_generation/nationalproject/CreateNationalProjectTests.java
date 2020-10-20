package tests.entity_generation.nationalproject;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import helpers.TestSuiteName;
import model.NationalProject;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.nationalproject.NationalProjectRegistry;
import pages.managementobjects.nationalproject.NationalProjectPage;
import tests.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


@Story(TestSuiteName.DATA_GENERATION)
@Tag("entityGeneration")
@Tag("Regression")
public class CreateNationalProjectTests extends BaseTest {
    private NationalProjectRegistry nProjectRegistry;
    private NationalProjectPage nProjectPage;
    private NationalProject nationalProject;

    @BeforeEach
    public void setupPages() {
        nProjectRegistry = new NationalProjectRegistry();
        nProjectPage = new NationalProjectPage();
        nationalProject = new NationalProject();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest
    @DisplayName("Создание сущности Национальный проект из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-177")
    @TmsLink("358")
    public void createNProjectButtonSaveTest(User user) {
        long currentTime = System.currentTimeMillis();
        nationalProject.setNameValue("Тест_C358_" + currentTime)
                .setCurator(user.getName())
                .setSupervisor(user.getName());
        nProjectRegistry.open();
        new SingInPage().asUser(user);
        nProjectRegistry.controlPanel().clickAddButton();
        nProjectPage.shouldBeOpened();
        nProjectPage.shouldHaveTitle("Национальный проект");
        nProjectPage.fillInGeneralInformation(nationalProject);
        nProjectPage.clickSaveAndClose();
        nProjectRegistry.searchGProgram(nationalProject.getNameValue());
        nProjectRegistry.shouldHaveCreatedRecord(nationalProject.getNameValue());
        nProjectRegistry.selectFirstRow();
        nProjectRegistry.clickDelete();
        nProjectRegistry.acceptDelete();
        nProjectRegistry.searchGProgram(nationalProject.getNameValue());
        nProjectRegistry.shouldNotHaveResults();
    }

    @ParameterizedTest
    @DisplayName("Создание сущности Национальный проект из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-178")
    @TmsLink("362")
    public void checkRequiredMessage(User user) {
        nProjectRegistry.open();
        new SingInPage().asUser(user);
        nProjectRegistry.controlPanel().clickAddButton();
        nProjectPage.shouldBeOpened();
        nProjectPage.shouldHaveTitle("Национальный проект");
        nProjectPage.clickSave();
        nProjectPage
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Наименование\"",
                        "Необходимо заполнить поле \"Куратор\"",
                        "Необходимо заполнить поле \"Руководитель\""
                );
        nProjectPage.closeDialog();
        nProjectPage.clickClose();
        nProjectPage.shouldBeClosed();
        nProjectRegistry.shouldBeRegistry();
    }


}
