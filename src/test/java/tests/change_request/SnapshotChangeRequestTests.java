package tests.change_request;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import helpers.ActionsViaAPI;
import helpers.TestSuiteName;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import model.SnapshotChangeRequests;
import model.Indicator;
import model.Snapshot;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.monitoring_and_control.change_requests.SnapshotChangeRequestsPage;
import pages.goals_and_indicators.indicator.IndicatorPage;
import pages.managementobjects.point.PointPage;
import pages.managementobjects.project.ProjectPage;
import pages.monitoring_and_control.change_requests.SnapshotChangeRequestsRegistry;
import pages.snapshot.SnapshotPage;
import tests.BaseTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.CHANGE_REQUEST)
@Tag("changeRequest")
@Tag("Regression")
public class SnapshotChangeRequestTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
    private long currentTime;
    private Snapshot snapshot;
    private SnapshotPage snapshotPage;
    private PointPage pointPage;
    private String currentDate;
    private File fileToUpload;
    private IndicatorPage indicatorPage;
    private Indicator testIndicator;
    private SnapshotChangeRequests snapshotChangeRequests;
    private SnapshotChangeRequestsPage snapshotChangeRequestsPage;
    private SnapshotChangeRequestsRegistry snapshotChangeRequestsRegistry;


    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        currentTime = System.currentTimeMillis();
        projectPage = new ProjectPage();
        snapshot = new Snapshot();
        snapshotPage = new SnapshotPage();
        pointPage = new PointPage();
        indicatorPage = new IndicatorPage();
        testIndicator = new Indicator();
        snapshotChangeRequests = new SnapshotChangeRequests();
        snapshotChangeRequestsPage = new SnapshotChangeRequestsPage();
        snapshotChangeRequestsRegistry = new SnapshotChangeRequestsRegistry();
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        fileToUpload = new File("src/test/resources/test.txt");
        currentDate = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteProjectCreatedFromAPI();
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание ЗИ в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-217")
    @TmsLink("1608")
    @Severity(SeverityLevel.CRITICAL)
    public void createChangeRequestsInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-217_" + currentTime);
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
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        testIndicator
                .setName("ATEST-217_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openChangeRequestSnapshotTab();
        projectPage.clickAddChangeRequest();
        String changeRequestName = "ATEST-217_" + currentTime;
        String changeRequestComment = "ATEST-217_" + currentTime;
        snapshotChangeRequests
                .setName(changeRequestName)
                .setComment(changeRequestComment);
        snapshotChangeRequestsPage.fillRequiredFields(snapshotChangeRequests);
        snapshotChangeRequestsPage.clickSaveAndClose();
        projectPage.modalWindowShouldBeClosed();
        projectPage.checkPageIsLoaded();
        projectPage.shouldHaveChangeRequest(changeRequestName);
        projectPage.checkChangeRequestData(changeRequestName,changeRequestComment, "Новый", snapshot.getName());
        snapshotChangeRequestsRegistry.open();
        snapshotChangeRequestsRegistry.changeView("Все запросы на изменение");
        snapshotChangeRequestsRegistry.searchChangeRequests(changeRequestName);
        snapshotChangeRequestsRegistry.checkChangeRequestData(ActionsViaAPI.getProjectNameFromAPI(), changeRequestName, "Новый");
    }

    @ParameterizedTest(name = "Создание ЗИ в Проекте (проверка карточки)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-218")
    @TmsLink("1609")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangeRequestsCardTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-218_" + currentTime);
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
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        testIndicator
                .setName("ATEST-218_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openChangeRequestSnapshotTab();
        projectPage.clickAddChangeRequest();
        String changeRequestName = "ATEST-218_" + currentTime;
        String changeRequestComment = "ATEST-218_" + currentTime;
        String changeRequestProjectReasons = "ATEST-218_" + currentTime;
        snapshotChangeRequests
                .setName(changeRequestName)
                .setComment(changeRequestComment)
                .setProjectReasons(changeRequestProjectReasons);
        snapshotChangeRequestsPage.fillRequiredFields(snapshotChangeRequests);
        snapshotChangeRequestsPage.clickSaveAndClose();
        projectPage.modalWindowShouldBeClosed();
        projectPage.checkPageIsLoaded();
        projectPage.shouldHaveChangeRequest(changeRequestName);
        projectPage.checkChangeRequestData(changeRequestName,changeRequestComment, "Новый", snapshot.getName());
        projectPage.findAndOpenChangeRequestViewForm(changeRequestName);
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotChangeRequestsPage.checkSnapshotData(ActionsViaAPI.getProjectNameFromAPI(), changeRequestName, changeRequestComment);
        snapshotChangeRequestsPage.checkChangeReasons(changeRequestProjectReasons);
        snapshotChangeRequestsPage.checkApproveChangesKPI(testIndicator.getName());
    }

    @ParameterizedTest(name = "Создание ЗИ проекте (до внесения изменений)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-220")
    @TmsLink("1610")
    @Severity(SeverityLevel.CRITICAL)
    public void checkProjectCardInSnapshotModeTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-220_" + currentTime);
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
        projectPage.openChangeRequestSnapshotTab();
        projectPage.clickAddChangeRequest();
        String changeRequestName = "ATEST-220_" + currentTime;
        String changeRequestComment = "ATEST-220_" + currentTime;
        String changeRequestProjectReasons = "ATEST-220_" + currentTime;
        snapshotChangeRequests
                .setName(changeRequestName)
                .setComment(changeRequestComment)
                .setProjectReasons(changeRequestProjectReasons);
        snapshotChangeRequestsPage.fillRequiredFields(snapshotChangeRequests);
        snapshotChangeRequestsPage.clickSaveAndClose();
        projectPage.modalWindowShouldBeClosed();
        projectPage.checkPageIsLoaded();
        projectPage.shouldHaveChangeRequest(changeRequestName);
        projectPage.checkChangeRequestData(changeRequestName,changeRequestComment, "Новый", snapshot.getName());
        projectPage.findAndOpenChangeRequestViewForm(changeRequestName);
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotChangeRequestsPage.checkSnapshotData(ActionsViaAPI.getProjectNameFromAPI(), changeRequestName, changeRequestComment);
        snapshotChangeRequestsPage.clickAddChangesButton();
        snapshotChangeRequestsPage.checkProjectCardIsOnSnapshotMode(changeRequestName);
    }
}
