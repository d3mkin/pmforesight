package tests.smoke.project;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import helpers.UserProvider;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.point.PointPage;
import pages.managementobjects.project.ProjectPage;
import tests.BaseTest;
import helpers.ActionsViaAPI;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.CHANGE_STATUS)
public class PointStageTransitionTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
    private ProjectPage modalDialog;
    private PointPage pointPage;
    private File fileToUpload;
    private String currentDate;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        projectPage = new ProjectPage();
        modalDialog = new ProjectPage();
        pointPage = new PointPage();
        fileToUpload = new File("src/test/resources/test.txt");
        currentDate = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        ActionsViaAPI.createProjectViaAPI("Инициирование","Ведомственный");
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteProjectCreatedFromAPI();
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Перевод КТ из состояния 'В работе' в состояние 'Выполнено'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-88")
    @TmsLink("1220")
    public void checkStageTransitionInProgressToComplete(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.clickEditGantt();
        projectPage.addNewPointInGantt("Тестовая КТ", "Рабочий план");
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.findInGanttAndOpenEntityPage("Тестовая КТ");
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.checkPointStatus("Выполнено");
    }

    @ParameterizedTest(name = "Перевод КТ из состояния 'Выполнено' в состояние 'В работе'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-89")
    @TmsLink("1221")
    public void checkStageTransitionFromCompleteToInProgress(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.clickEditGantt();
        projectPage.addNewPointInGantt("Тестовая КТ", "Рабочий план");
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.findInGanttAndOpenEntityPage("Тестовая КТ");
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        pointPage.backInProgressPointAndUploadFile("31.12.2020", fileToUpload);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.checkPointStatus("Прогноз срыва сроков");
    }

    @ParameterizedTest(name = "Перевод КТ из состояния 'В работе' в состояние 'Отменена'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-90")
    @TmsLink("1223")
    public void checkStageTransitionFromInProgressToCanceled (User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.clickEditGantt();
        projectPage.addNewPointInGantt("Тестовая КТ", "Рабочий план");
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.findInGanttAndOpenEntityPage("Тестовая КТ");
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        pointPage.cancelPointAndUploadFile(currentDate, fileToUpload);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.checkPointStatus("Отменена");
    }

    @ParameterizedTest(name = "Перевод КТ из состояния 'Отменена' в состояние 'В работе'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-91")
    @TmsLink("1224")
    public void checkStageTransitionFromCanceledToInProgress(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.clickEditGantt();
        projectPage.addNewPointInGantt("Тестовая КТ", "Рабочий план");
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.findInGanttAndOpenEntityPage("Тестовая КТ");
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        pointPage.cancelPointAndUploadFile(currentDate, fileToUpload);
        pointPage.backInProgressPointAndUploadFile("31.12.2020", fileToUpload);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.checkPointStatus("Прогноз срыва сроков");
    }

    @ParameterizedTest(name = "Перевод КТ из состояния 'Выполнено' в состояние 'Подтверждена'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-92")
    @TmsLink("1225")
    public void checkStageTransitionFromCompletedToApproved(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.clickEditGantt();
        projectPage.addNewPointInGantt("Тестовая КТ", "Рабочий план");
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.findInGanttAndOpenEntityPage("Тестовая КТ");
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        pointPage.approvePointAndUploadFile(fileToUpload);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.checkPointStatus("Подтверждена");
    }
}

