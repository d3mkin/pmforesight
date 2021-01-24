package tests.cascade_deletion.portfolio;

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
import pages.base_of_knowledge_and_documents.SummaryConclusionsRegistry;
import pages.base_of_knowledge_and_documents.lessons_learned.LessonLearnedPage;
import pages.elements.SearchForm;
import pages.goals_and_indicators.IndicatorsRegistry;
import pages.goals_and_indicators.indicator.IndicatorPage;
import pages.managementobjects.NonProjectEventRegistry;
import pages.managementobjects.ResultsRegistry;
import pages.managementobjects.StagesWorksAndPointsRegistry;
import pages.managementobjects.nonprojectevent.NonProjectEventPage;
import pages.managementobjects.point.PointPage;
import pages.managementobjects.portfolio.PortfolioPage;
import pages.managementobjects.portfolio.PortfolioRegistry;
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
@Story("Каскадное удаление Портфеля и дочерних сущностей")
@Tag("cascadeDeletion")
@Tag("Regression")
public class PortfolioCascadeDeletionTests extends BaseTest {

    private SingInPage singIn;
    private long currentTime;
    private StagesWorksAndPointsRegistry swpRegistry;
    private String currentDate;
    private ProjectRegistry projectRegistry;
    private Result result;
    private ResultPage resultPage;
    private ResultsRegistry resultsRegistry;
    private IndicatorPage indicatorPage;
    private Indicator indicator;
    private IndicatorsRegistry indicatorsRegistry;
    private SearchForm searchForm;
    private OrderRegistry orderRegistry;
    private Order order;
    private OrderPage orderPage;
    private Meeting meeting;
    private MeetingsRegistry meetingsRegistry;
    private MeetingPage meetingPage;
    private OpenQuestion openQuestion;
    private OpenQuestionPage openQuestionPage;
    private OpenQuestionsRegistry openQuestionsRegistry;
    private ProgramPage programPage;
    private ProgramRegistry programRegistry;
    private ProjectPage projectPage;
    private Project project;
    private PortfolioRegistry portfolioRegistry;
    private Program program;
    private PortfolioPage portfolioPage;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        projectRegistry = new ProjectRegistry();
        currentTime = System.currentTimeMillis();
        currentDate = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
        result = new Result();
        resultPage = new ResultPage();
        resultsRegistry = new ResultsRegistry();
        indicatorPage = new IndicatorPage();
        indicator = new Indicator();
        indicatorsRegistry = new IndicatorsRegistry();
        searchForm = new SearchForm();
        orderRegistry = new OrderRegistry();
        order = new Order();
        orderPage = new OrderPage();
        meeting = new Meeting();
        meetingPage = new MeetingPage();
        meetingsRegistry = new MeetingsRegistry();
        openQuestion = new OpenQuestion();
        openQuestionPage = new OpenQuestionPage();
        openQuestionsRegistry = new OpenQuestionsRegistry();
        programPage = new ProgramPage();
        programRegistry = new ProgramRegistry();
        projectPage = new ProjectPage();
        project = new Project();
        swpRegistry = new StagesWorksAndPointsRegistry();
        portfolioRegistry = new PortfolioRegistry();
        program = new Program();
        portfolioPage = new PortfolioPage();

        ActionsViaAPI.createPortfolioViaAPI();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Каскадное удаление: Проект(КТ,Этап,Работа + Результат) Портфеля")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-146")
    @TmsLink("1245")
    public void cascadeDeletionProjectInPortfolio (User user) {
        parameter("Пользователь", user.getName());
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.clickEditGantt();
        String newPointName = "КТ_ATEST-146_" + currentTime;
        String newWorkName = "Работа_ATEST-146_" + currentTime;
        String newStageName = "Этап_ATEST-146_" + currentTime;
        String portfolioName = ActionsViaAPI.getPortfolioNameFromAPI();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectPage.addNewPointInGantt(newPointName, "Рабочий план");
        projectPage.clickEditGantt();
        projectPage.addNewWorkInGantt(newWorkName , "Рабочий план");
        projectPage.clickEditGantt();
        projectPage.addNewStageInGantt(newStageName, "Рабочий план");
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.openResultsTab();
        projectPage.clickAddResult("Ведомственный");
        result
                .setName("ATEST-146_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        String resultName = result.getName();
        projectPage.shouldHaveDefaultResultsTable();
        projectPage.shouldHaveDepartmentalResult(result.getName());
        projectPage.clickEditForm();
        project.setProgram(portfolioName);
        projectPage.fillFields(project);
        projectPage.clickSaveAndClose();
        projectPage.openMainTab();
        projectPage.checkProjectPortfolioName(portfolioName);
        portfolioRegistry.open();
        portfolioRegistry.changeView("Все портфели");
        portfolioRegistry.deleteEntity(portfolioName);
        projectRegistry.open();
        projectRegistry.changeView("Все проекты");
        projectRegistry.checkProjectNotExist(projectName);
        swpRegistry.open();
        swpRegistry.changeView("Все");
        swpRegistry.checkEntityNotExist(newPointName);
        swpRegistry.checkEntityNotExist(newWorkName);
        swpRegistry.checkEntityNotExist(newStageName);
        searchForm.checkEntityNotFoundInGlobalSearch(portfolioName);
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(newPointName);
        searchForm.checkEntityNotFoundInGlobalSearch(newWorkName);
        searchForm.checkEntityNotFoundInGlobalSearch(newStageName);
        searchForm.checkEntityNotFoundInGlobalSearch(resultName);
    }

    @ParameterizedTest(name = "Каскадное удаление: Программа(Показатель + Результат) Портфеля")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-146")
    @TmsLink("1245")
    public void cascadeDeletionProgramInPortfolio (User user) {
        parameter("Пользователь", user.getName());
        ActionsViaAPI.createProgramViaAPI();
        singIn.asUser(user);
        ActionsViaAPI.openProgramCreatedFromAPI();
        String programName = ActionsViaAPI.getProgramNameFromAPI();
        programPage.checkCurrentProgramStage("Инициирование");
        programPage.openIndicatorsTab();
        programPage.clickAddIndicator();
        indicator
                .setName("ATEST-146_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(indicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProgramNameFromAPI());
        indicatorPage.clickSaveAndClose();
        programPage.shouldHaveIndicatorsTable();
        String indicatorName = indicator.getName();
        programPage.shouldHaveIndicator(indicatorName);
        programPage.openResultsTab();
        programPage.clickAddResult();
        result
                .setName("ATEST-146_" + currentTime)
                .setUnit("Единица")
                .setDate(currentDate)
                .setValue("1");
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        programPage.shouldHaveResultsTable();
        String resultName = result.getName();
        programPage.shouldHaveResult(resultName);
        programPage.clickEditForm();
        String portfolioName = ActionsViaAPI.getPortfolioNameFromAPI();
        program.setPortfolio(portfolioName);
        programPage.fillFields(program);
        programPage.clickSaveAndClose();
        programPage.openMainTab();
        programPage.checkProjectPortfolioName(portfolioName);
        portfolioRegistry.open();
        portfolioRegistry.changeView("Все портфели");
        portfolioRegistry.deleteEntity(portfolioName);
        programRegistry.open();
        programRegistry.changeView("Все программы");
        programRegistry.checkProgramNotExist(programName);
        indicatorsRegistry.open();
        indicatorsRegistry.changeView("Все показатели");
        indicatorsRegistry.checkIndicatorNotExist(indicatorName);
        resultsRegistry.open();
        resultsRegistry.changeView("Все результаты");
        resultsRegistry.checkEntityNotExist(resultName);
        searchForm.checkEntityNotFoundInGlobalSearch(portfolioName);
        searchForm.checkEntityNotFoundInGlobalSearch(programName);
        searchForm.checkEntityNotFoundInGlobalSearch(indicatorName);
        searchForm.checkEntityNotFoundInGlobalSearch(resultName);
    }

    @ParameterizedTest(name = "Каскадное удаление: Показатели Портфеля")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-146")
    @TmsLink("1245")
    public void cascadeDeletionIndicatorInPortfolio (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openPortfolioCreatedFromAPI();
        portfolioPage.openIndicatorsTab();
        portfolioPage.clickAddIndicator();
        indicator
                .setName("ATEST-146_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(indicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getPortfolioNameFromAPI());
        indicatorPage.clickSaveAndClose();
        portfolioPage.shouldHaveIndicatorsTable();
        portfolioPage.shouldHaveIndicator(indicator.getName());
        portfolioRegistry.open();
        portfolioRegistry.changeView("Все портфели");
        String portfolioName = ActionsViaAPI.getPortfolioNameFromAPI();
        portfolioRegistry.deleteEntity(portfolioName);
        indicatorsRegistry.open();
        indicatorsRegistry.changeView("Все показатели");
        indicatorsRegistry.checkIndicatorNotExist(indicator.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(portfolioName);
        searchForm.checkEntityNotFoundInGlobalSearch(indicator.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Поручение в Портфеле")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-146")
    @TmsLink("1245")
    public void cascadeDeletionOrderInPortfolio(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openPortfolioCreatedFromAPI();
        portfolioPage.openOrdersTab();
        portfolioPage.clickAddOrder();
        order
                .setName("ATEST-146_" + currentTime)
                .setPriority("Высокий")
                .setPlanDate(currentDate)
                .setExecutor(user.getName());
        orderPage.fillFields(order);
        orderPage.clickSaveAndClose();
        portfolioPage.checkOrderPresentInTable(order.getName());
        portfolioRegistry.open();
        portfolioRegistry.changeView("Все портфели");
        String portfolioName = ActionsViaAPI.getPortfolioNameFromAPI();
        portfolioRegistry.deleteEntity(portfolioName);
        orderRegistry.open();
        orderRegistry.changeView("Все поручения");
        orderRegistry.checkOrderNotExist(order.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(portfolioName);
        searchForm.checkEntityNotFoundInGlobalSearch(order.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Совещание в Портфеле")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-146")
    @TmsLink("1245")
    public void cascadeDeletionMeetingInPortfolio(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openPortfolioCreatedFromAPI();
        portfolioPage.openMeetingTab();
        portfolioPage.clickAddMeeting();
        meeting
                .setName("ATEST-146_" + currentTime)
                .setLocation("Переговорка 'Север'")
                .setSecretary(user.getName());
        meetingPage.fillFields(meeting);
        meetingPage.clickSaveAndClose();
        portfolioPage.checkMeetingPresentInTable(meeting.getName());
        portfolioRegistry.open();
        portfolioRegistry.changeView("Все портфели");
        String portfolioName = ActionsViaAPI.getPortfolioNameFromAPI();
        portfolioRegistry.deleteEntity(portfolioName);
        meetingsRegistry.open();
        meetingsRegistry.changeView("Все совещания");
        meetingsRegistry.checkOrderNotExist(meeting.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(portfolioName);
        searchForm.checkEntityNotFoundInGlobalSearch(meeting.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Открытые вопросы в Портфеле")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-146")
    @TmsLink("1245")
    public void cascadeDeletionOpenQuestionsInPortfolio(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openPortfolioCreatedFromAPI();
        portfolioPage.openOpenQuestionsTab();
        portfolioPage.clickAddOpenQuestion();
        openQuestion
                .setName("ATEST-146_" + currentTime)
                .setExecutor(user.getName())
                .setInitDate(currentDate)
                .setPlanDate(currentDate);
        openQuestionPage.fillFields(openQuestion);
        openQuestionPage.clickSaveAndClose();
        portfolioPage.checkOpenQuestionPresentInTable(openQuestion.getName());
        portfolioRegistry.open();
        portfolioRegistry.changeView("Все портфели");
        String portfolioName = ActionsViaAPI.getPortfolioNameFromAPI();
        portfolioRegistry.deleteEntity(portfolioName);
        openQuestionsRegistry.open();
        openQuestionsRegistry.changeView("Все открытые вопросы");
        openQuestionsRegistry.checkOpenQuestionNotExist(openQuestion.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(portfolioName);
        searchForm.checkEntityNotFoundInGlobalSearch(openQuestion.getName());
    }
}
