package tests.cascadeDeletion.program;

import helpers.ActionsViaAPI;
import helpers.TestSuiteName;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.base_of_knowledge_and_documents.LessonsLearnedRegistry;
import pages.base_of_knowledge_and_documents.lessons_learned.LessonLearnedPage;
import pages.elements.SearchForm;
import pages.goals_and_indicators.IndicatorsRegistry;
import pages.goals_and_indicators.indicator.IndicatorPage;
import pages.managementobjects.NonProjectEventRegistry;
import pages.managementobjects.ResultsRegistry;
import pages.managementobjects.StagesWorksAndPointsRegistry;
import pages.managementobjects.contracts.ContractPage;
import pages.managementobjects.contracts.ContractsRegistry;
import pages.managementobjects.nonprojectevent.NonProjectEventPage;
import pages.managementobjects.point.PointPage;
import pages.managementobjects.program.ProgramPage;
import pages.managementobjects.program.ProgramRegistry;
import pages.managementobjects.project.ProjectPage;
import pages.managementobjects.project.ProjectRegistry;
import pages.managementobjects.result.ResultPage;
import pages.risk_management.RisksAndOpportunitiesRegistry;
import pages.risk_management.risks_and_opportunities.RisksAndOpportunitiesPage;
import pages.сommunications.meeting.MeetingPage;
import pages.сommunications.meeting.MeetingsRegistry;
import pages.сommunications.open_questions.OpenQuestionPage;
import pages.сommunications.open_questions.OpenQuestionsRegistry;
import pages.сommunications.orders.OrderPage;
import pages.сommunications.orders.OrderRegistry;
import tests.BaseTest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.CASCADE_DELETION)
@Story("Каскадное удаление Программы и дочерних сущностей")
@Tag("cascadeDeletion")
@Tag("Regression")
public class ProgramCascadeDeletionTests extends BaseTest {
    private SingInPage singIn;
    private long currentTime;
    private String currentDate;
    private StagesWorksAndPointsRegistry swpRegistry;
    private ProjectRegistry projectRegistry;
    private Result result;
    private ResultPage resultPage;
    private ResultsRegistry resultsRegistry;
    private IndicatorPage indicatorPage;
    private Indicator indicator;
    private IndicatorsRegistry indicatorsRegistry;
    private ContractPage contractPage;
    private Contract contact;
    private ContractsRegistry contractsRegistry;
    private SearchForm searchForm;
    private RisksAndOpportunities risk;
    private RisksAndOpportunities opportunity;
    private RisksAndOpportunitiesPage risksAndOpportunitiesPage;
    private RisksAndOpportunitiesRegistry risksAndOpportunitiesRegistry;
    private OrderRegistry orderRegistry;
    private Order order;
    private OrderPage orderPage;
    private Meeting meeting;
    private MeetingsRegistry meetingsRegistry;
    private MeetingPage meetingPage;
    private OpenQuestion openQuestion;
    private OpenQuestionPage openQuestionPage;
    private OpenQuestionsRegistry openQuestionsRegistry;
    private LessonLearned negativeLesson;
    private LessonLearnedPage lessonPage;
    private LessonLearned positiveLesson;
    private LessonsLearnedRegistry lessonsRegistry;
    private ProgramPage programPage;
    private ProgramRegistry programRegistry;
    private ProjectPage projectPage;
    private Project project;
    private NonProjectEvent nonProjectEvent;
    private NonProjectEventPage nonProjectEventPage;
    private NonProjectEventRegistry nonProjectEventRegistry;
    private Point point;
    private PointPage pointPage;
    private StagesWorksAndPointsRegistry pointsRegistry;




    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        swpRegistry = new StagesWorksAndPointsRegistry();
        projectRegistry = new ProjectRegistry();
        currentTime = System.currentTimeMillis();
        currentDate = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        result = new Result();
        resultPage = new ResultPage();
        resultsRegistry = new ResultsRegistry();
        indicatorPage = new IndicatorPage();
        indicator = new Indicator();
        indicatorsRegistry = new IndicatorsRegistry();
        contact = new Contract();
        contractPage = new ContractPage();
        contractsRegistry = new ContractsRegistry();
        searchForm = new SearchForm();
        risk = new RisksAndOpportunities();
        opportunity = new RisksAndOpportunities();
        risksAndOpportunitiesPage = new RisksAndOpportunitiesPage();
        risksAndOpportunitiesRegistry = new RisksAndOpportunitiesRegistry();
        orderRegistry = new OrderRegistry();
        order = new Order();
        orderPage = new OrderPage();
        meeting = new Meeting();
        meetingPage = new MeetingPage();
        meetingsRegistry = new MeetingsRegistry();
        openQuestion = new OpenQuestion();
        openQuestionPage = new OpenQuestionPage();
        openQuestionsRegistry = new OpenQuestionsRegistry();
        positiveLesson = new LessonLearned();
        negativeLesson = new LessonLearned();
        lessonPage = new LessonLearnedPage();
        lessonsRegistry = new LessonsLearnedRegistry();
        programPage = new ProgramPage();
        programRegistry = new ProgramRegistry();
        projectPage = new ProjectPage();
        project = new Project();
        nonProjectEvent = new NonProjectEvent();
        nonProjectEventPage = new NonProjectEventPage();
        nonProjectEventRegistry = new NonProjectEventRegistry();
        point = new Point();
        pointPage = new PointPage();
        pointsRegistry = new StagesWorksAndPointsRegistry();

        ActionsViaAPI.createProgramViaAPI();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Каскадное удаление: Показатели Программы")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-147")
    @TmsLink("1246")
    public void cascadeDeletionIndicatorInProgram (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProgramCreatedFromAPI();
        programPage.checkCurrentProgramStage("Инициирование");
        programPage.openIndicatorsTab();
        programPage.clickAddIndicator();
        indicator
                .setName("Тест_C1246_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(indicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProgramNameFromAPI());
        indicatorPage.clickSaveAndClose();
        programPage.shouldHaveIndicatorsTable();
        programPage.shouldHaveIndicator(indicator.getName());
        programRegistry.open();
        String programName = ActionsViaAPI.getProgramNameFromAPI();
        programRegistry.changeView("Все программы");
        programRegistry.deleteEntity(programName);
        indicatorsRegistry.open();
        indicatorsRegistry.changeView("Все показатели");
        indicatorsRegistry.checkIndicatorNotExist(indicator.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(programName);
        searchForm.checkEntityNotFoundInGlobalSearch(indicator.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Результаты Программы")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-148")
    @TmsLink("1247")
    public void cascadeDeletionResultInProgram (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProgramCreatedFromAPI();
        programPage.checkCurrentProgramStage("Инициирование");
        programPage.openResultsTab();
        programPage.clickAddResult();
        result
                .setName("Тест_C1247_" + currentTime)
                .setUnit("Единица")
                .setDate(currentDate)
                .setValue("1");
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        programPage.shouldHaveResultsTable();
        programPage.shouldHaveResult(result.getName());
        programRegistry.open();
        String programName = ActionsViaAPI.getProgramNameFromAPI();
        programRegistry.changeView("Все программы");
        programRegistry.deleteEntity(programName);
        resultsRegistry.open();
        resultsRegistry.changeView("Все результаты");
        resultsRegistry.checkEntityNotExist(result.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(programName);
        searchForm.checkEntityNotFoundInGlobalSearch(result.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Проект Программы")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-149")
    @TmsLink("1248")
    public void cascadeDeletionProjectInProgram (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProgramCreatedFromAPI();
        programPage.checkCurrentProgramStage("Инициирование");
        programPage.openComponentsTab();
        programPage.clickAddProject();
        project
                .setName("Тест_C1248_" + currentTime)
                .setType("Информационные технологии")
                .setSupervisor(user.getName());
        projectPage.fillFields(project);
        projectPage.clickSaveAndClose();
        programPage.shouldHaveComponentsTable();
        programPage.shouldHaveComponent(project.getName());
        programRegistry.open();
        String programName = ActionsViaAPI.getProgramNameFromAPI();
        programRegistry.changeView("Все программы");
        programRegistry.deleteEntity(programName);
        projectRegistry.open();
        projectRegistry.changeView("Все проекты");
        projectRegistry.checkProjectNotExist(project.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(programName);
        searchForm.checkEntityNotFoundInGlobalSearch(project.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Непроектное мероприятие Программы")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-150")
    @TmsLink("1251")
    public void cascadeDeletionNonProjectEventInProgram (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProgramCreatedFromAPI();
        programPage.checkCurrentProgramStage("Инициирование");
        programPage.openComponentsTab();
        programPage.clickAddEvent();
        nonProjectEvent
                .setName("Тест_C1251_" + currentTime)
                .setCurator(user.getName())
                .setManager(user.getName());
        nonProjectEventPage.fillFields(nonProjectEvent);
        nonProjectEventPage.clickSaveAndClose();
        programPage.shouldHaveComponentsTable();
        programPage.shouldHaveComponent(nonProjectEvent.getName());
        programRegistry.open();
        String programName = ActionsViaAPI.getProgramNameFromAPI();
        programRegistry.changeView("Все программы");
        programRegistry.deleteEntity(programName);
        nonProjectEventRegistry.open();
        nonProjectEventRegistry.changeView("Все мероприятия");
        nonProjectEventRegistry.checkEventNotExist(nonProjectEvent.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(programName);
        searchForm.checkEntityNotFoundInGlobalSearch(nonProjectEvent.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Нетиповые КТ в Программе")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-151")
    @TmsLink("1249")
    public void cascadeDeletionNonTypicalPointsInProgram(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProgramCreatedFromAPI();
        programPage.checkCurrentProgramStage("Инициирование");
        programPage.openGanttTab();
        programPage.clickAddNonTypicalPoint();
        point
                .setName("Тест_C1249_" + currentTime)
                .setPlanDate(currentDate)
                .setForecastDate(currentDate);
        pointPage.fillFields(point);
        pointPage.clickSaveAndClose();
        programPage.shouldHaveNonTypicalPoint(point);
        programRegistry.open();
        String programName = ActionsViaAPI.getProgramNameFromAPI();
        programRegistry.changeView("Все программы");
        programRegistry.deleteEntity(programName);
        pointsRegistry.open();
        pointsRegistry.changeView("Все");
        pointsRegistry.checkEntityNotExist(point.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(programName);
        searchForm.checkEntityNotFoundInGlobalSearch(point.getName());
    }
}
