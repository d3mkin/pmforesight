package tests.change_requests;

import com.codeborne.selenide.Selenide;
import helpers.TestSuiteName;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.TmsLink;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.managementobjects.portfolio.PortfolioPage;
import pages.managementobjects.portfolio.PortfolioRegistry;
import pages.monitoring_and_control.change_requests.SnapshotChangeRequestsPage;
import pages.goals_and_indicators.indicator.IndicatorPage;
import pages.managementobjects.point.PointPage;
import pages.managementobjects.project.ProjectPage;
import pages.monitoring_and_control.change_requests.SnapshotChangeRequestsRegistry;
import pages.snapshot.SnapshotPage;
import tests.BaseTest;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static helpers.ActionsViaAPI.*;
import static helpers.ActionsViaAPI.openProjectCreatedFromAPI;
import static io.qameta.allure.Allure.parameter;
import static java.util.Calendar.getInstance;

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
    private Project project;
    private PortfolioPage portfolioPage;
    private PortfolioRegistry portfolioRegistry;


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
        project = new Project();
        portfolioPage = new PortfolioPage();
        portfolioRegistry = new PortfolioRegistry();
        createProjectViaAPI("Инициирование", "Ведомственный");
        fileToUpload = new File("src/test/resources/test.txt");
        currentDate = new SimpleDateFormat("dd.MM.yyyy").format(getInstance().getTime());
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
        deleteProjectCreatedFromAPI();
    }

    @ParameterizedTest(name = "Создание ЗИ в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-217")
    @TmsLink("1608")
    @Severity(SeverityLevel.CRITICAL)
    public void createChangeRequestsInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
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
        indicatorPage.checkDefaultControlObject(getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.clickOnMenuItem("Запросы на изменение (Слепки)");
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
        projectPage.checkChangeRequestData(changeRequestName, changeRequestComment, "Новый", snapshot.getName());
        snapshotChangeRequestsRegistry.open();
        snapshotChangeRequestsRegistry.changeView("Все запросы на изменение");
        snapshotChangeRequestsRegistry.searchChangeRequests(changeRequestName);
        snapshotChangeRequestsRegistry.checkChangeRequestData(getProjectNameFromAPI(), changeRequestName, "Новый");
    }

    @ParameterizedTest(name = "Создание ЗИ в Проекте (проверка карточки)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-218")
    @TmsLink("1609")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangeRequestsCardTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
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
        indicatorPage.checkDefaultControlObject(getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.clickOnMenuItem("Запросы на изменение (Слепки)");
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
        projectPage.checkChangeRequestData(changeRequestName, changeRequestComment, "Новый", snapshot.getName());
        projectPage.findAndOpenChangeRequestViewForm(changeRequestName);
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotChangeRequestsPage.checkSnapshotData(getProjectNameFromAPI(), changeRequestName, changeRequestComment);
//        snapshotChangeRequestsPage.checkChangeReasons(changeRequestProjectReasons);
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
        openProjectCreatedFromAPI();
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
        projectPage.clickOnMenuItem("Запросы на изменение (Слепки)");
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
        projectPage.checkChangeRequestData(changeRequestName, changeRequestComment, "Новый", snapshot.getName());
        projectPage.findAndOpenChangeRequestViewForm(changeRequestName);
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotChangeRequestsPage.checkSnapshotData(getProjectNameFromAPI(), changeRequestName, changeRequestComment);
        snapshotChangeRequestsPage.clickAddChanges();
        projectPage.checkPageIsOnSnapshotMode(changeRequestName);
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в основные параметры Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-221")
    @TmsLink("1611")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInMainProjectTab(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot.setName("ATEST-221_" + currentTime);
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
        projectPage.clickOnMenuItem("Запросы на изменение (Слепки)");
        projectPage.clickAddChangeRequest();
        String changeRequestName = "ATEST-221_" + currentTime;
        String changeRequestComment = "ATEST-221_" + currentTime;
        String changeRequestProjectReasons = "ATEST-221_" + currentTime;
        snapshotChangeRequests
                .setName(changeRequestName)
                .setComment(changeRequestComment)
                .setProjectReasons(changeRequestProjectReasons);
        snapshotChangeRequestsPage.fillRequiredFields(snapshotChangeRequests);
        snapshotChangeRequestsPage.clickSaveAndClose();
        projectPage.modalWindowShouldBeClosed();
        projectPage.checkPageIsLoaded();
        projectPage.shouldHaveChangeRequest(changeRequestName);
        projectPage.checkChangeRequestData(changeRequestName, changeRequestComment, "Новый", snapshot.getName());
        projectPage.findAndOpenChangeRequestViewForm(changeRequestName);
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotChangeRequestsPage.checkSnapshotData(getProjectNameFromAPI(), changeRequestName, changeRequestComment);
        snapshotChangeRequestsPage.clickAddChanges();
        projectPage.checkPageIsOnSnapshotMode(changeRequestName);
        project
                .setName(getProjectNameFromAPI() + "ChangeRequests")
                .setPriority("Приоритетный")
                .setProjectLevel("Региональный")
                .setType("Другие проекты");
        projectPage.clickEditForm();
        projectPage.fillFields(project);
        projectPage.clickSaveAndClose();
        projectPage.checkPageIsLoaded();
        projectPage.checkCountOfMainChanges(4);
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в Цели Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-222")
    @TmsLink("1612")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInGoalsProjectTab(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        createGoalViaAPI("Ведомственный");
        String goalNameForDelete = getGoalNameFromAPI();
        int goalForDeleteId = getGoalId();
        createGoalViaAPI("Ведомственный");
        String goalNameForAdd = getGoalNameFromAPI();
        int goalForAddId = getGoalId();
        ArrayList<String> projectGoalsNames = new ArrayList<String>();
        projectGoalsNames.add(goalNameForDelete);
        projectGoalsNames.add(goalNameForAdd);
        portfolioRegistry.open();
        portfolioRegistry.changeView("Все портфели");
        portfolioRegistry.searchAndOpenPortfolio("Портфель национальных проектов");
        portfolioPage.checkPageIsLoaded();
        portfolioPage.clickEditForm();
        portfolioPage.addGoals(projectGoalsNames);
        portfolioPage.clickSaveAndClose();
        openProjectCreatedFromAPI();
        project
                .setGoal(goalNameForDelete);
        projectPage.clickEditForm();
        projectPage.fillFields(project);
        projectPage.clickSaveAndClose();
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot
                .setName("ATEST-222_" + currentTime);
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
        projectPage.clickOnMenuItem("Запросы на изменение (Слепки)");
        projectPage.clickAddChangeRequest();
        String changeRequestName = "ATEST-222_" + currentTime;
        String changeRequestComment = "ATEST-222_" + currentTime;
        String changeRequestProjectReasons = "ATEST-222_" + currentTime;
        snapshotChangeRequests
                .setName(changeRequestName)
                .setComment(changeRequestComment)
                .setProjectReasons(changeRequestProjectReasons);
        snapshotChangeRequestsPage.fillRequiredFields(snapshotChangeRequests);
        snapshotChangeRequestsPage.clickSaveAndClose();
        projectPage.modalWindowShouldBeClosed();
        projectPage.checkPageIsLoaded();
        projectPage.shouldHaveChangeRequest(changeRequestName);
        projectPage.checkChangeRequestData(changeRequestName, changeRequestComment, "Новый", snapshot.getName());
        projectPage.findAndOpenChangeRequestViewForm(changeRequestName);
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotChangeRequestsPage.checkSnapshotData(getProjectNameFromAPI(), changeRequestName, changeRequestComment);
        snapshotChangeRequestsPage.clickAddChanges();
        projectPage.checkPageIsOnSnapshotMode(changeRequestName);
        projectPage.clickEditForm();
        projectPage.addGoals(projectGoalsNames);
        projectPage.clickSaveAndClose();
        projectPage.checkPageIsLoaded();
        projectPage.openGoalsTab();
//        projectPage.checkCountOfGoalChanges(2);
        projectPage.checkGoalStatusInTable(goalNameForAdd, "Добавлено");
        projectPage.checkGoalStatusInTable(goalNameForDelete, "Удалено");
        deleteGoalCreatedFromAPI(goalForDeleteId);
        deleteGoalCreatedFromAPI(goalForAddId);
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в Показатели Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-223")
    @TmsLink("1613")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInIndicatorProjectTab(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        Indicator indicatorToEdit = new Indicator();
        indicatorToEdit
                .setName("indicatorToEdit_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("100")
                .setPeriod("2020")
                .setFact("100")
                .setPlan("100");
        Indicator indicatorToDelete = new Indicator();
        indicatorToDelete
                .setName("indicatorToDelete_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("100")
                .setPeriod("2020")
                .setFact("100")
                .setPlan("100");
        Indicator indicatorToAdd = new Indicator();
        indicatorToAdd
                .setName("indicatorToAdd_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("100")
                .setPeriod("2020")
                .setFact("100")
                .setPlan("100");
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(indicatorToEdit);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicator(indicatorToEdit.getName());
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(indicatorToDelete);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicator(indicatorToDelete.getName());
        projectPage.openSnapshotTab();
        projectPage.clickAddSnapshot();
        snapshot
                .setName("ATEST-223_" + currentTime);
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
        projectPage.clickOnMenuItem("Запросы на изменение (Слепки)");
        projectPage.clickAddChangeRequest();
        String changeRequestName = "ATEST-223_" + currentTime;
        String changeRequestComment = "ATEST-223_" + currentTime;
        String changeRequestProjectReasons = "ATEST-223_" + currentTime;
        snapshotChangeRequests
                .setName(changeRequestName)
                .setComment(changeRequestComment)
                .setProjectReasons(changeRequestProjectReasons);
        snapshotChangeRequestsPage.fillRequiredFields(snapshotChangeRequests);
        snapshotChangeRequestsPage.clickSaveAndClose();
        projectPage.modalWindowShouldBeClosed();
        projectPage.checkPageIsLoaded();
        projectPage.shouldHaveChangeRequest(changeRequestName);
        projectPage.checkChangeRequestData(changeRequestName, changeRequestComment, "Новый", snapshot.getName());
        projectPage.findAndOpenChangeRequestViewForm(changeRequestName);
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        snapshotChangeRequestsPage.checkSnapshotData(getProjectNameFromAPI(), changeRequestName, changeRequestComment);
        snapshotChangeRequestsPage.clickAddChanges();
        projectPage.checkPageIsOnSnapshotMode(changeRequestName);
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(indicatorToAdd);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicator(indicatorToAdd.getName());
        projectPage.checkIndicatorStatus("Добавлено");
        projectPage.indicatorTableSearchInput.clear();
        projectPage.shouldHaveIndicator(indicatorToEdit.getName());
        projectPage.clickEditIndicator();
        indicatorPage.changeBasicValue("1");
        projectPage.shouldHaveIndicator(indicatorToEdit.getName());
        projectPage.checkIndicatorStatus("Изменено");
        projectPage.indicatorTableSearchInput.clear();
        projectPage.shouldHaveIndicator(indicatorToDelete.getName());
        projectPage.clickDeleteIndicator();
        projectPage.acceptDelete();
        projectPage.checkPageIsLoaded();
        projectPage.openIndicatorsTab();
        projectPage.checkPageIsLoaded();
        projectPage.indicatorTableSearchInput.sendKeys(indicatorToDelete.getName());
        projectPage.checkIndicatorStatus("Удалено");
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в Результаты Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-224")
    @TmsLink("1614")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInResultsProjectTab(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в Роли Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-225")
    @TmsLink("1615")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInRolesProjectTab(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в Ресурсы Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-226")
    @TmsLink("1616")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInResourcesProjectTab(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "ЗИ: согласование внесенных изменений по одному функционалу")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-228")
    @TmsLink("1627")
    @Severity(SeverityLevel.CRITICAL)
    public void approvalChangesInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в Бюджет паспорта Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-229")
    @TmsLink("1628")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInBudgetProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "ЗИ: внесение изменений в Детализированный бюджет проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-230")
    @TmsLink("1630")
    @Severity(SeverityLevel.CRITICAL)
    public void checkChangesInDetailBudgetProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @Disabled
    @ParameterizedTest(name = "ЗИ: согласование внесенных изменений по комбинациям функционала (рандом)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-231")
    @TmsLink("1629")
    @Severity(SeverityLevel.CRITICAL)
    public void approveRandomChangesInProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "Удаление ЗИ (из таблицы ЗИ)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-232")
    @TmsLink("1631")
    @Severity(SeverityLevel.CRITICAL)
    public void deleteChangeRequestTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "ЗИ: перевод ЗИ в статус \"На согласовании\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-235")
    @TmsLink("1636")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestStageTransitionToApprovingTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: попытка перевода второго ЗИ в статус \"На согласовании\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-236")
    @TmsLink("1637")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestStageTransitionToApproveSecondCRSTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: Отзыв ЗИ, находящегося на согласовании")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-237")
    @TmsLink("1639")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestRecallFromApprovingTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: внесение изменений во взаимосвязи Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-238")
    @TmsLink("1614")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestInConnectionsProjectTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: возврат на доработку ЗИ из статуса \"На согласовании\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-239")
    @TmsLink("1656")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestReturnToRevisionFromApprovingTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: отклонение внесенных изменений по одному функционалу")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-240")
    @TmsLink("1657")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestRejectionChangesOneFunctionalTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @Disabled
    @ParameterizedTest(name = "ЗИ: отклонение внесенных изменений по комбинациям функционала (рандом)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-241")
    @TmsLink("1658")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestRejectionRandomChangesTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: соответствие изменений вкладке \"Согласование изменений\", блок \"Основные параметры\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-242")
    @TmsLink("1669")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestCheckChangesOnMainParamTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: соответствие изменений вкладке \"Согласование изменений\", блок \"Показатели\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-243")
    @TmsLink("1670")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestCheckChangesOnKPITest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: соответствие изменений вкладке \"Согласование изменений\", блок \"Результаты\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-244")
    @TmsLink("1671")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestCheckChangesOnResultsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();
    }

    @ParameterizedTest(name = "ЗИ: соответствие изменений вкладке \"Согласование изменений\", блок \"Календарный план\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-245")
    @TmsLink("1673")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestCheckChangesOnCalendarPlanTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: соответствие изменений вкладке \"Согласование изменений\", блок \"Бюджет\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-246")
    @TmsLink("1674")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestCheckChangesOnBudgetTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: соответствие изменений вкладке \"Согласование изменений\", блок \"Участники\"")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-247")
    @TmsLink("1675")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestCheckChangesOnRolesTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }

    @ParameterizedTest(name = "ЗИ: В Проекте не применяются изменения из ЗИ его после согласования")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-248")
    @Severity(SeverityLevel.CRITICAL)
    public void changeRequestCheckChangesAfterApprove(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        openProjectCreatedFromAPI();
        projectPage.openSnapshotTab();

    }
}
