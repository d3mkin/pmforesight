package tests.cascade_deletion.nonproject_event;

import helpers.ActionsViaAPI;
import helpers.TestSuiteName;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
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
import pages.managementobjects.program.ProgramPage;
import pages.managementobjects.program.ProgramRegistry;
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
@Story("Каскадное удаление Непроектного мероприятия и дочерних сущностей")
@Tag("cascadeDeletion")
@Tag("Regression")
public class NonProjectEventCascadeDeletionTests extends BaseTest {
    private SingInPage singIn;
    private long currentTime;
    private String currentDate;
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
    private ProgramRegistry programRegistry;
    private NonProjectEventPage nonProjectEventPage;
    private NonProjectEventRegistry nonProjectEventRegistry;
    private StagesWorksAndPointsRegistry swpRegistry;


    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
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
        programRegistry = new ProgramRegistry();
        nonProjectEventPage = new NonProjectEventPage();
        nonProjectEventRegistry = new NonProjectEventRegistry();
        swpRegistry = new StagesWorksAndPointsRegistry();

        ActionsViaAPI.createNonProjectEventViaAPI();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Каскадное удаление: Показатели Непроектного мероприятия")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-202")
    public void cascadeDeletionIndicatorInNonProjectEvent (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openNonProjectEventFromAPI();
        nonProjectEventPage.checkCurrentStage("В работе");
        nonProjectEventPage.openIndicatorsTab();
        nonProjectEventPage.clickAddIndicator();
        indicator
                .setName("Тест_ATEST_202_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(indicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getNonProjectEventNameFromAPI());
        indicatorPage.clickSaveAndClose();
        nonProjectEventPage.shouldHaveIndicatorsTable();
        nonProjectEventPage.shouldHaveIndicator(indicator.getName());
        nonProjectEventRegistry.open();
        String nonProjectEventName = ActionsViaAPI.getNonProjectEventNameFromAPI();
        nonProjectEventRegistry.changeView("Все мероприятия");
        nonProjectEventRegistry.deleteEntity(nonProjectEventName);
        indicatorsRegistry.open();
        indicatorsRegistry.changeView("Все показатели");
        indicatorsRegistry.checkIndicatorNotExist(indicator.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(nonProjectEventName);
        searchForm.checkEntityNotFoundInGlobalSearch(indicator.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Результаты Непроектного мероприятия")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-202")
    public void cascadeDeletionResultInNonProjectEvent  (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openNonProjectEventFromAPI();
        nonProjectEventPage.checkCurrentStage("В работе");
        nonProjectEventPage.openResultsTab();
        nonProjectEventPage.clickAddResult();
        result
                .setName("Тест_ATEST_202_" + currentTime)
                .setUnit("Единица")
                .setDate(currentDate)
                .setValue("1");
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        nonProjectEventPage.shouldHaveResultsTable();
        nonProjectEventPage.shouldHaveResult(result.getName());
        nonProjectEventRegistry.open();
        String nonProjectEventName = ActionsViaAPI.getNonProjectEventNameFromAPI();
        nonProjectEventRegistry.changeView("Все мероприятия");
        nonProjectEventRegistry.deleteEntity(nonProjectEventName);
        resultsRegistry.open();
        resultsRegistry.changeView("Все результаты");
        resultsRegistry.checkEntityNotExist(result.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(nonProjectEventName);
        searchForm.checkEntityNotFoundInGlobalSearch(result.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: КТ/Этап/Работа в Непроектном мероприятии")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-202")
    public void cascadeDeletionPointInNonProjectEvent (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openNonProjectEventFromAPI();
        nonProjectEventPage.checkCurrentStage("В работе");
        nonProjectEventPage.openActivityTab();
        nonProjectEventPage.clickToMaximizeOrMinimizeGantt();
        nonProjectEventPage.clickEditGantt();
        String newPointName = "Тестовая КТ_ATEST_202_" + currentTime;
        String newWorkName = "Тестовая Работа_ATEST_202_" + currentTime;
        String newStageName = "Тестовый Этап_ATEST_202_" + currentTime;
        nonProjectEventPage.addNewPointInGantt(newPointName, "Рабочий план");
        nonProjectEventPage.clickEditGantt();
        nonProjectEventPage.addNewWorkInGantt(newWorkName , "Рабочий план");
        nonProjectEventPage.clickEditGantt();
        nonProjectEventPage.addNewStageInGantt(newStageName, "Рабочий план");
        nonProjectEventPage.clickToMaximizeOrMinimizeGantt();
        nonProjectEventRegistry.open();
        String nonProjectEventName = ActionsViaAPI.getNonProjectEventNameFromAPI();
        nonProjectEventRegistry.changeView("Все мероприятия");
        nonProjectEventRegistry.deleteEntity(nonProjectEventName);
        swpRegistry.open();
        swpRegistry.changeView("Все");
        swpRegistry.checkEntityNotExist(newPointName);
        swpRegistry.checkEntityNotExist(newWorkName);
        swpRegistry.checkEntityNotExist(newStageName);
        searchForm.checkEntityNotFoundInGlobalSearch(nonProjectEventName);
        searchForm.checkEntityNotFoundInGlobalSearch(newPointName);
        searchForm.checkEntityNotFoundInGlobalSearch(newWorkName);
        searchForm.checkEntityNotFoundInGlobalSearch(newStageName);
    }

    @ParameterizedTest(name = "Каскадное удаление: Поручение в Непроектном мероприятии")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-202")
    public void cascadeDeletionOrderInInNonProjectEvent(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openNonProjectEventFromAPI();
        nonProjectEventPage.checkCurrentStage("В работе");
        nonProjectEventPage.openOrdersTab();
        nonProjectEventPage.clickAddOrder();
        order
                .setName("ATEST-202_" + currentTime)
                .setPriority("Высокий")
                .setPlanDate(currentDate)
                .setExecutor(user.getName());
        orderPage.fillFields(order);
        orderPage.clickSaveAndClose();
        nonProjectEventPage.checkOrderPresentInTable(order.getName());
        nonProjectEventRegistry.open();
        String nonProjectEventName = ActionsViaAPI.getNonProjectEventNameFromAPI();
        nonProjectEventRegistry.changeView("Все мероприятия");
        nonProjectEventRegistry.deleteEntity(nonProjectEventName);
        orderRegistry.open();
        orderRegistry.changeView("Все поручения");
        orderRegistry.checkOrderNotExist(order.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(nonProjectEventName);
        searchForm.checkEntityNotFoundInGlobalSearch(order.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Совещание в Непроектном мероприятии")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-202")
    public void cascadeDeletionMeetingInNonProjectEvent(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openNonProjectEventFromAPI();
        nonProjectEventPage.checkCurrentStage("В работе");
        nonProjectEventPage.openMeetingTab();
        nonProjectEventPage.clickAddMeeting();
        meeting
                .setName("ATEST-202_" + currentTime)
                .setLocation("Переговорка 'Север'")
                .setSecretary(user.getName());
        meetingPage.fillFields(meeting);
        meetingPage.clickSaveAndClose();
        nonProjectEventPage.checkMeetingPresentInTable(meeting.getName());
        nonProjectEventRegistry.open();
        String nonProjectEventName = ActionsViaAPI.getNonProjectEventNameFromAPI();
        nonProjectEventRegistry.changeView("Все мероприятия");
        nonProjectEventRegistry.deleteEntity(nonProjectEventName);
        meetingsRegistry.open();
        meetingsRegistry.changeView("Все совещания");
        meetingsRegistry.checkOrderNotExist(meeting.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(nonProjectEventName);
        searchForm.checkEntityNotFoundInGlobalSearch(meeting.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Открытые вопросы в Непроектном мероприятии")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-202")
    public void cascadeDeletionOpenQuestionsInNonProjectEvent(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openNonProjectEventFromAPI();
        nonProjectEventPage.checkCurrentStage("В работе");
        nonProjectEventPage.openOpenQuestionsTab();
        nonProjectEventPage.clickAddOpenQuestion();
        openQuestion
                .setName("ATEST-202_" + currentTime)
                .setExecutor(user.getName())
                .setInitDate(currentDate)
                .setPlanDate(currentDate);
        openQuestionPage.fillFields(openQuestion);
        openQuestionPage.clickSaveAndClose();
        nonProjectEventPage.checkOpenQuestionPresentInTable(openQuestion.getName());
        nonProjectEventRegistry.open();
        String nonProjectEventName = ActionsViaAPI.getNonProjectEventNameFromAPI();
        nonProjectEventRegistry.changeView("Все мероприятия");
        nonProjectEventRegistry.deleteEntity(nonProjectEventName);
        openQuestionsRegistry.open();
        openQuestionsRegistry.changeView("Все открытые вопросы");
        openQuestionsRegistry.checkOpenQuestionNotExist(openQuestion.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(nonProjectEventName);
        searchForm.checkEntityNotFoundInGlobalSearch(openQuestion.getName());
    }
}
