package tests.strageTransition;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
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

@Tag("Stage_transition")
@Story(TestSuiteName.CHANGE_STATUS)
public class ProjectStageTransitionTests extends BaseTest {
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
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteProjectCreatedFromAPI();
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Перевод Проекта на стадию 'Отменено' (positive)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-67")
    @TmsLink("1177")
    public void checkCanceledStagePositive(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.checkPossibilityProjectStaging();
        projectPage.moveStageTo("Отменить");
        modalDialog.shouldBeOpened();
        modalDialog.shouldHaveTitle("Отмена проекта");
        modalDialog.shouldHaveReasonField();
        modalDialog.fillReasonField("Веская причина для отмены");
        modalDialog.clickSaveAndClose();
        projectPage.checkCurrentProjectStage("Отменено");
    }

    @ParameterizedTest(name = "Перевод Проекта на стадию 'Отменено' (негативный сценарий)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-68")
    @TmsLink("1178")
    public void checkCanceledStageNegative(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.checkPossibilityProjectStaging();
        projectPage.moveStageTo("Отменить");
        modalDialog.shouldBeOpened();
        modalDialog.shouldHaveTitle("Отмена проекта");
        modalDialog.shouldHaveReasonField();
        modalDialog.clickSaveAndClose();
        modalDialog.shouldHaveMessage("Необходимо заполнить поле \"Причина отмены проекта\"");
        modalDialog.closeDialog();
        modalDialog.clickClose();
    }

    @ParameterizedTest(name = "Перевод Проекта на стадию 'до' первой и 'после' крайней стадии")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-73")
    @TmsLink("1213")
    public void checkTransitionBeforeFirstAndAfterLastStage(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.moveStageTo("На предыдущую стадию");
        modalDialog.clickAcceptPrevStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields("Это первая стадия");
        modalDialog.closeDialog();
        projectPage.moveStageTo("Отменить");
        modalDialog.shouldBeOpened();
        modalDialog.shouldHaveTitle("Отмена проекта");
        modalDialog.shouldHaveReasonField();
        modalDialog.fillReasonField("Веская причина для отмены");
        modalDialog.clickSaveAndClose();
        projectPage.checkCurrentProjectStage("Отменено");
        projectPage.moveStageTo("На предыдущую стадию");
        modalDialog.clickAcceptPrevStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields("Это первая стадия");
        modalDialog.closeDialog();
        ActionsViaAPI.changeProjectStageCreatedFromAPI("Архив");
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields(
                "Это последняя стадия");
        modalDialog.closeDialog();
    }

    @ParameterizedTest(name = "Перевод Проекта по стадиям, не требующим обязательных условий")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-74")
    @TmsLink("1214")
    public void checkNonRequireTransitions (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Архив", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Архив");
        projectPage.moveStageTo("На предыдущую стадию");
        modalDialog.clickAcceptPrevStageTransition();
        projectPage.checkCurrentProjectStage("Постпроектный мониторинг");
        projectPage.moveStageTo("На предыдущую стадию");
        modalDialog.clickAcceptPrevStageTransition();
        projectPage.checkCurrentProjectStage("Завершение");
        projectPage.moveStageTo("На предыдущую стадию");
        modalDialog.clickAcceptPrevStageTransition();
        projectPage.checkCurrentProjectStage("Реализация");
        projectPage.moveStageTo("На предыдущую стадию");
        modalDialog.clickAcceptPrevStageTransition();
        projectPage.checkCurrentProjectStage("Подготовка");
        projectPage.moveStageTo("На предыдущую стадию");
        modalDialog.clickAcceptPrevStageTransition();
        projectPage.checkCurrentProjectStage("Инициирование");
    }

    @ParameterizedTest(name = "Перевод Проекта по стадиям, требующим обязательных условий")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-75")
    @TmsLink("1215")
    public void checkFullStageTransitions(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        //Переводим на стадию Подготовка в случае, когда условия НЕ выполены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields(
                "Должна быть создана типовая контрольная точка 'Проект запущен'",
                "Должна быть создана и завершена типовая контрольная точка 'Проект запущен");
        modalDialog.closeDialog();
        //Выплняем условия для перевода на стадию Подготовка
        projectPage.openActivityTab();
        projectPage.expandRequiredPointsWidget();
        projectPage.createRequiredStagePoint("Инициирование", currentDate);
        projectPage.checkEntityIsDisplayedInGantt("Проект запущен");
        projectPage.findInGanttAndOpenEntityPage("Проект запущен");
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        //Закрываем вкладку браузера и переключаем фокус WebDriver на предыдущую вкладку
        pointPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        //Переводим на стадию Подготовка, когда все условия выполнены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Подготовка");
        projectPage.closeCreatedSnapshotNotification();

        //Переводим на стадию Реализация в случае, когда условия НЕ выполены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields(
                "Должна быть создана типовая контрольная точка 'Выполнение работ разрешено'",
                "Необходимо прикрепить документ 'Паспорт проекта'",
                "Необходимо прикрепить документ 'Сводный план проекта'",
                "Должна быть создана и завершена типовая контрольная точка 'Выполнение работ разрешено'");
        modalDialog.closeDialog();
        //Выполняем условия для перевода на стадию Реализация
        projectPage.createRequiredStagePoint("Подготовка",currentDate);
        projectPage.checkEntityIsDisplayedInGantt("Выполнение работ разрешено");
        projectPage.findInGanttAndOpenEntityPage("Выполнение работ разрешено");
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        pointPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        projectPage.openDocumentsTab();
        projectPage.expandDocuments();
        projectPage.clickToUploadProjectPassport(fileToUpload);
        projectPage.clickToUploadProjectConsolidatePlan(fileToUpload);
        //Переводим на стадию Реализация, когда все условия выполнены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Реализация");
        projectPage.closeCreatedSnapshotNotification();

        //Переводим на стадию Завершение в случае, когда условия НЕ выполены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields(
                "Должна быть создана типовая контрольная точка 'Результаты работ приняты'",
                "Должна быть создана и завершена типовая контрольная точка 'Результаты работ приняты'");
        modalDialog.closeDialog();
        //Выплняем условия для перевода на стадию Завершение
        projectPage.openActivityTab();
        projectPage.createRequiredStagePoint("Реализация", currentDate);
        projectPage.checkEntityIsDisplayedInGantt("Результаты работ приняты");
        projectPage.findInGanttAndOpenEntityPage("Результаты работ приняты");
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        pointPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        //Переводим на стадию Завершение, когда все условия выполнены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Завершение");
        projectPage.closeCreatedSnapshotNotification();


        //Переводим на стадию Постпроектный мониторинг в случае, когда условия НЕ выполены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields(
                "Должна быть создана типовая контрольная точка 'Проект закрыт'",
                "Необходимо прикрепить документ 'Итоговый отчет о реализации проекта'",
                "Должна быть создана и завершена типовая контрольная точка 'Проект закрыт'");
        modalDialog.closeDialog();
        //Выплняем условия для перевода на стадию Постпроектный мониторинг
        projectPage.createRequiredStagePoint("Завершение", currentDate);
        projectPage.checkEntityIsDisplayedInGantt("Проект закрыт");
        projectPage.findInGanttAndOpenEntityPage("Проект закрыт");
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        pointPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        projectPage.openDocumentsTab();
        projectPage.clickToUploadFinalReport(fileToUpload);
        //Переводим на стадию Постпроектный мониторинг, когда все условия выполнены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Постпроектный мониторинг");
        projectPage.closeCreatedSnapshotNotification();

        //Переводим на стадию Архив в случае, когда условия НЕ выполены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        modalDialog.shouldHaveMessageAboutRequiredFields(
                "Должна быть создана типовая контрольная точка 'Постпроектный мониторинг завершен'",
                "Должна быть создана и завершена типовая контрольная точка 'Постпроектный мониторинг завершен'");
        modalDialog.closeDialog();
        //Выплняем условия для перевода на стадию Архив
        projectPage.openActivityTab();
        projectPage.createRequiredStagePoint("Постпроектный мониторинг", currentDate);
        projectPage.checkEntityIsDisplayedInGantt("Постпроектный мониторинг завершен");
        projectPage.findInGanttAndOpenEntityPage("Постпроектный мониторинг завершен");
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        pointPage.completePointAndUploadFile(currentDate, fileToUpload);
        pointPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        //Переводим на стадию Архив, когда все условия выполнены
        projectPage.moveStageTo("На следующую стадию");
        modalDialog.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Архив");
    }
}
