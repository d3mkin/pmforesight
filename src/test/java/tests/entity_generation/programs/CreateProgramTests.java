package tests.entity_generation.programs;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.Program;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.program.ProgramRegistry;
import pages.managementobjects.program.ProgramPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.ENTITY_CREATION)
@Story("Программа")
@Tag("entityGeneration")
@Tag("Regression")
public class CreateProgramTests extends BaseTest {
    private SingInPage singIn;
    private ProgramPage programPage;
    private ProgramRegistry programRegistry;
    private Program program;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        programPage = new ProgramPage();
        programRegistry = new ProgramRegistry();
        program = new Program();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание сущности Программа из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-174")
    @TmsLink("369")
    public void programSaveShouldHaveMessageAboutRequiredFieldsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        programRegistry.open();
        programRegistry.shouldBeRegistry();
        programRegistry.controlPanel().clickAddButton();
        programPage.modalWindowShouldBeOpened();
        programPage.modalWindowShouldHaveTitle("Программа");
        programPage.clickSave();
        programPage
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Наименование\"",
                        "Необходимо заполнить поле \"Портфель\"",
                        "Необходимо заполнить поле \"Функциональный заказчик\"",
                        "Необходимо заполнить поле \"Куратор\"",
                        "Необходимо заполнить поле \"Руководитель\""
                );
        programPage.closeDialog();
        programPage.clickClose();
        programRegistry.shouldBeRegistry();
    }

    @ParameterizedTest(name = "Создание сущности Программа из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-173")
    @TmsLink("365")
    public void programSaveAndClose(User user) {
        long currentTime = System.currentTimeMillis();
        program
                .setName("Тест_C365_" + currentTime)
                .setPortfolio("Портфель национальных проектов")
                .setCustomer(user.getName())
                .setCurator(user.getName())
                .setSupervisor(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        programRegistry.open();
        programRegistry.shouldBeRegistry();
        programRegistry.controlPanel().clickAddButton();
        programPage.modalWindowShouldBeOpened();
        programPage.fillFields(program);
        programPage.clickSaveAndClose();
        programRegistry.shouldBeRegistry();
        programRegistry.searchProgram(program.getName());
        programRegistry.shouldHaveCreatedRecord(program.getName());
        programRegistry.selectRow();
        programRegistry.clickDelete();
        programRegistry.acceptDelete();
        programRegistry.searchProgram(program.getName());
        programRegistry.shouldNotHaveResults();
    }
}
