package tests.entity_generation.meeting;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import helpers.TestSuiteName;
import model.Meeting;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.сommunications.meeting.MeetingsRegistry;
import pages.сommunications.meeting.MeetingPage;
import tests.BaseTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.MethodSource;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.ENTITY_CREATION)
@Story("Совещание")
@Tag("entityGeneration")
@Tag("Regression")
public class CreateMeetingTests extends BaseTest {
    private SingInPage singIn;
    private MeetingPage meetingPage;
    private MeetingsRegistry meetingRegistry;
    private Meeting meeting;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        meetingPage = new MeetingPage();
        meetingRegistry = new MeetingsRegistry();
        meeting = new Meeting();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание сущности Совещания из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-180")
    @TmsLink("431")
    public void meetingsSaveShouldHaveMessageAboutRequiredFieldsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        meetingRegistry.open();
        meetingRegistry.shouldBeRegistry();
        meetingRegistry.controlPanel().clickAddButton();
        meetingPage.modalWindowShouldBeOpened();
        meetingPage.modalWindowShouldHaveTitle("Совещание");
        meetingPage.clickSave();
        meetingPage
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Тема\"",
                        "Необходимо заполнить поле \"Место проведения\"",
                        "Необходимо заполнить поле \"Дата и время\"",
                        "Необходимо заполнить поле \"Объект управления\"",
                        "Необходимо заполнить поле \"Председатель\"",
                        "Необходимо заполнить поле \"Секретарь\""
                );
        meetingPage.closeDialog();
        meetingPage.clickClose();
        meetingRegistry.shouldBeRegistry();
    }

    @ParameterizedTest(name = "Создание сущности Совещания из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-180")
    @TmsLink("427")
    public void meetingSaveAndCloseTest(User user) {
        long currentTime = System.currentTimeMillis();
        meeting
                .setName("Тест_С427_" + currentTime)
                .setLocation("Переговорная")
                .setControlObject("Портфель национальных проектов")
                .setSecretary(user.getName())
                .setChairman(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        meetingRegistry.open();
        meetingRegistry.shouldBeRegistry();
        meetingRegistry.controlPanel().clickAddButton();
        meetingPage.modalWindowShouldBeOpened();
        meetingPage.fillFields(meeting);
        meetingPage.clickSaveAndClose();
        meetingRegistry.shouldBeRegistry();
        meetingRegistry.searchMeeting(meeting.getName());
        meetingRegistry.shouldHaveCreatedRecord();
        meetingRegistry.selectRow();
        meetingRegistry.clickDelete();
        meetingRegistry.checkRegistryIsLoaded();
        meetingRegistry.acceptDelete();
        meetingRegistry.searchMeeting(meeting.getName());
        meetingRegistry.shouldNotHaveResults();
        meetingRegistry.shouldBeRegistry();
    }


}
