package tests.snapshot;

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
import pages.managementobjects.project.ProjectPage;
import pages.snapshot.SnapshotPage;
import tests.BaseTest;

import java.io.File;

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

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        currentTime = System.currentTimeMillis();
        projectPage = new ProjectPage();
        snapshot = new Snapshot();
        snapshotPage = new SnapshotPage();
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        fileToUpload = new File("src/test/resources/test.txt");
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
        projectPage.checkSnapshotNameAndStatusInTable(snapshot.getName(), "Новый");
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
        projectPage.checkSnapshotNameAndStatusInTable(snapshot.getName(), "Новый");
        projectPage.openSnapshotCard(snapshot.getName());
        projectPage.getBrowserTabs();
        projectPage.switchToNextBrowserTab();
        String snapshotComment = "Комментарий" + currentTime;
        snapshotPage.checkNameAndStatus(snapshot.getName(), "Новый");
        snapshotPage.sendToApprove(snapshotComment, fileToUpload);
        snapshotPage.checkNameAndStatus(snapshot.getName(), "На согласовании");
        snapshotPage.shouldHaveRecordInTable(user.getName(), "Отправлен на согласование", snapshotComment, fileToUpload.getName());
    }
}