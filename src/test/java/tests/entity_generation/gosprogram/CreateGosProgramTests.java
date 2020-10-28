package tests.entity_generation.gosprogram;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.GProgram;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.elements.ControlPanel;
import pages.managementobjects.gprogram.GProgramRegistry;
import pages.managementobjects.gprogram.GosProgramPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.ENTITY_CREATION)
@Tag("entityGeneration")
@Tag("Regression")
public class CreateGosProgramTests extends BaseTest {
    private SingInPage singIn;
    private GProgramRegistry gProgramRegistry;
    private ControlPanel controlPanel;
    private GProgram gProgram;
    private GosProgramPage gProgramPage;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        gProgramRegistry = new GProgramRegistry();
        controlPanel = new ControlPanel();
        gProgram = new GProgram();
        gProgramPage = new GosProgramPage();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание сущности Госпрограмма из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-136")
    @TmsLink("344")
    public void CreateGosProgramWithSaveAndCloseTest(User user) {
        long currentTime = System.currentTimeMillis();
        gProgram.setTypeObjectGosprogram("Государственная программа")
                .setNameValue("Тест_344_" + currentTime)
                .setTextDateStart("12.12.2018")
                .setTextDateEnd("12.12.2019")
                .setExecutiveOfficer(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        gProgramRegistry.open();
        gProgramRegistry.shouldBeRegistry();
        gProgramRegistry.controlPanel().clickAddButton();
        gProgramPage.shouldHaveTitle("Объект госпрограммы");
        gProgramPage.shouldBeOpened();
        gProgramPage.fillCommonInfo(gProgram);
        gProgramPage.fillRoleInfo(gProgram);
        gProgramPage.clickSaveAndClose();
        gProgramPage.shouldBeClosed();
        gProgramRegistry.shouldBeRegistry();
        gProgramRegistry.searchGprogram(gProgram.getNameValue());
        gProgramRegistry.shouldHaveCreatedRecord(gProgram.getNameValue());
        gProgramRegistry.selectFirstRow();
        gProgramRegistry.clickDelete();
        gProgramRegistry.acceptDelete();
        gProgramRegistry.searchGprogram(gProgram.getNameValue());
        gProgramRegistry.shouldNotHaveResults();
    }

    @ParameterizedTest(name = "Создание сущности Госпрограмма из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#UsersFA")
    @Tag("ATEST-137")
    @TmsLink("348")
    public void checkRequiredMessageTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        gProgramRegistry.open();
        gProgramRegistry.controlPanel().clickAddButton();
        gProgramPage.shouldBeOpened();
        gProgramRegistry.shouldBeRegistry();
        gProgramPage.shouldHaveTitle("Объект госпрограммы");
        gProgramPage.clickSave();
        gProgramPage.shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Вид объекта госпрограммы\"",
                        "Необходимо заполнить поле \"Наименование\"",
                        "Необходимо заполнить поле \"Дата начала реализации\"",
                        "Необходимо заполнить поле \"Дата окончания реализации\"",
                        "Необходимо заполнить поле \"Ответственный исполнитель\""
                );
        gProgramPage.closeDialog();
        gProgramPage.clickClose();
        gProgramPage.shouldBeClosed();
        gProgramRegistry.shouldBeRegistry();
    }
}
