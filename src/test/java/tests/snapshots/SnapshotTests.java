package tests.snapshots;

import com.codeborne.selenide.Selenide;
import helpers.ActionsViaAPI;
import helpers.TestSuiteName;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import model.Snapshot;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.point.PointPage;
import pages.managementobjects.project.ProjectPage;
import pages.snapshot.SnapshotPage;
import tests.BaseTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static io.qameta.allure.Allure.parameter;


@Epic(TestSuiteName.SNAPSHOTS)
@Tag("Snapshots")
@Tag("Regression")
public class SnapshotTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
    private long currentTime;
    private Snapshot snapshot;
    private SnapshotPage snapshotPage;
    private File fileToUpload;
    private PointPage pointPage;
    private String currentDate;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        currentTime = System.currentTimeMillis();
        projectPage = new ProjectPage();
        snapshot = new Snapshot();
        snapshotPage = new SnapshotPage();
        pointPage = new PointPage();
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        fileToUpload = new File("src/test/resources/test.txt");
        currentDate = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteProjectCreatedFromAPI();
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание слепка в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-209")
    @TmsLink("1503")
    @Severity(SeverityLevel.CRITICAL)
    public void createSnapshotInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-209" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
    }

    @ParameterizedTest(name = "Отправка на согласование слепка в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-210")
    @TmsLink("1504")
    @Severity(SeverityLevel.CRITICAL)
    public void sendToApproveSnapshotInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-210" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.sendToApprove(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "На согласовании");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Отправлен на согласование", snapshotComment, fileToUpload.getName());
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "На согласовании");
    }

    @ParameterizedTest(name = "Согласование слепка в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-211")
    @TmsLink("1505")
    @Severity(SeverityLevel.CRITICAL)
    public void approveSnapshotInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-211" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.sendToApprove(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "На согласовании");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Отправлен на согласование", snapshotComment, fileToUpload.getName());
        snapshotPage.approveSnapshot(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Согласован");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Слепок согласован", snapshotComment, fileToUpload.getName());
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Согласован");
    }

    @ParameterizedTest(name = "Отзыв слепка с согласования")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-212")
    @TmsLink("1506")
    @Severity(SeverityLevel.CRITICAL)
    public void recallSnapshotInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-212" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.sendToApprove(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "На согласовании");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Отправлен на согласование", snapshotComment, fileToUpload.getName());
        snapshotPage.recallSnapshot(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Согласование отозвано", snapshotComment, fileToUpload.getName());
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
    }

    @ParameterizedTest(name = "Отклонение согласования слепка")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-213")
    @TmsLink("1507")
    @Severity(SeverityLevel.CRITICAL)
    public void rejectSnapshotInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-213" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.sendToApprove(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "На согласовании");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Отправлен на согласование", snapshotComment, fileToUpload.getName());
        snapshotPage.rejectSnapshot(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Отклонён");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Слепок отклонён", snapshotComment, fileToUpload.getName());
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Отклонён");
    }

    @ParameterizedTest(name = "Удаление нового слепка из карточки слепка")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-214")
    @TmsLink("1508")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteNewSnapshotFromSnapshotPage(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-214" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.deleteSnapshot(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Удалён");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Слепок удален", snapshotComment, fileToUpload.getName());
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.checkSnapshotNotExistInTable(snapshot.getName());
    }

    @ParameterizedTest(name = "Удаление отклонённого слепка из карточки слепка")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-214")
    @TmsLink("1508")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteRejectedSnapshotFromSnapshotPage(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-214" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.sendToApprove(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "На согласовании");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Отправлен на согласование", snapshotComment, fileToUpload.getName());
        snapshotPage.rejectSnapshot(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Отклонён");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Слепок отклонён", snapshotComment, fileToUpload.getName());
        snapshotPage.deleteSnapshot(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Удалён");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Слепок удален", snapshotComment, fileToUpload.getName());
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.checkSnapshotNotExistInTable(snapshot.getName());
    }

    @ParameterizedTest(name = "Удаление нового слепка из карточки проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-215")
    @TmsLink("1509")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteNewSnapshotFromProjectPage(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-215" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.deleteSnapshot(snapshot.getName());
        projectPage.checkSnapshotNotExistInTable(snapshot.getName());
    }

    @ParameterizedTest(name = "Удаление отклоннёного слепка из карточки проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-215")
    @TmsLink("1509")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteRejectedSnapshotFromProjectPage(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-215" + currentTime);
        snapshotPage.fillFields(snapshot);
        snapshotPage.clickSaveAndClose();
        projectPage.searchSnapshotInTable(snapshot.getName());
        projectPage.checkSnapshotExistInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.sendToApprove(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "На согласовании");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Отправлен на согласование", snapshotComment, fileToUpload.getName());
        snapshotPage.rejectSnapshot(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Отклонён");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Слепок отклонён", snapshotComment, fileToUpload.getName());
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToPreviousBrowserTab();
        Selenide.refresh();
        projectPage.openSnapshotTab();
        projectPage.deleteSnapshot(snapshot.getName());
        projectPage.checkSnapshotNotExistInTable(snapshot.getName());
    }

    @ParameterizedTest(name = "Создание авто-слепка")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-216")
    @TmsLink("1510")
    public void creatingAutoSnapshotsTest(User user){
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        ActionsViaAPI.openProjectCreatedFromAPI();
        String snapshotName = "Авто-слепок "+ currentDate;
        String snapshotDate = currentDate;
        projectPage.checkCurrentProjectStage("Инициирование");
        //Выполняем условия для перевода на стадию Подготовка
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
        projectPage.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Подготовка");
        projectPage.closeCreatedSnapshotNotification();
        //Проверяем, что Автослепок создан
        projectPage.openSnapshotTab();
        projectPage.checkPageIsLoaded();
        projectPage.searchSnapshotInTable(snapshotName);
        projectPage.checkSnapshotExistInTable(snapshotName, snapshotDate, "Создан автоматически при переходе на стадию ЖЦ \"Подготовка\"", "Новый");
        //Выполняем условия для перевода на стадию Реализация
        projectPage.openActivityTab();
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
        projectPage.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Реализация");
        projectPage.closeCreatedSnapshotNotification();
        //Проверяем, что Автослепок создан
        projectPage.openSnapshotTab();
        projectPage.checkPageIsLoaded();
        projectPage.searchSnapshotInTable(snapshotName);
        projectPage.checkSnapshotExistInTable(snapshotName, snapshotDate, "Создан автоматически при переходе на стадию ЖЦ \"Реализация\"", "Согласован");
        //Выполняем условия для перевода на стадию Завершение
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
        projectPage.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Завершение");
        projectPage.closeCreatedSnapshotNotification();
        //Проверяем, что Автослепок создан
        projectPage.openSnapshotTab();
        projectPage.checkPageIsLoaded();
        projectPage.searchSnapshotInTable(snapshotName);
        projectPage.checkSnapshotExistInTable(snapshotName, snapshotDate, "Создан автоматически при переходе на стадию ЖЦ \"Завершение\"", "Новый");
        //Выполняем условия для перевода на стадию Постпроектный мониторинг
        projectPage.openActivityTab();
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
        projectPage.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Постпроектный мониторинг");
        projectPage.closeCreatedSnapshotNotification();
        //Проверяем, что Автослепок создан
        projectPage.openSnapshotTab();
        projectPage.checkPageIsLoaded();
        projectPage.searchSnapshotInTable(snapshotName);
        projectPage.checkSnapshotExistInTable(snapshotName, snapshotDate, "Создан автоматически при переходе на стадию ЖЦ \"Постпроектный мониторинг\"", "Новый");
        //Выполняем условия для перевода на стадию Архив
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
        projectPage.clickAcceptNextStageTransition();
        projectPage.checkCurrentProjectStage("Архив");
        //Проверяем, что Автослепок создан
        projectPage.openSnapshotTab();
        projectPage.checkPageIsLoaded();
        projectPage.searchSnapshotInTable(snapshotName);
        projectPage.checkSnapshotExistInTable(snapshotName, snapshotDate, "Создан автоматически при переходе на стадию ЖЦ \"Архив\"", "Новый");
    }
}