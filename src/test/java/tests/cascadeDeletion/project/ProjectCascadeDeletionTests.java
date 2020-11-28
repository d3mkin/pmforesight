package tests.cascadeDeletion.project;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.*;
import model.Order;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.base_of_knowledge_and_documents.LessonsLearnedRegistry;
import pages.base_of_knowledge_and_documents.lessons_learned.LessonLearnedPage;
import pages.elements.SearchForm;
import pages.goals_and_indicators.IndicatorsRegistry;
import pages.goals_and_indicators.indicator.IndicatorPage;
import pages.managementobjects.contracts.ContractsRegistry;
import pages.managementobjects.project.ProjectRegistry;
import pages.managementobjects.ResultsRegistry;
import pages.managementobjects.StagesWorksAndPointsRegistry;
import pages.managementobjects.contracts.ContractPage;
import pages.managementobjects.project.ProjectPage;
import pages.managementobjects.result.ResultPage;
import pages.risk_management.RisksAndOpportunitiesRegistry;
import pages.risk_management.risks_and_opportunities.RisksAndOpportunitiesPage;
import pages.сommunications.meeting.MeetingsRegistry;
import pages.сommunications.meeting.MeetingPage;
import pages.сommunications.open_questions.OpenQuestionPage;
import pages.сommunications.open_questions.OpenQuestionsRegistry;
import pages.сommunications.orders.OrderPage;
import pages.сommunications.orders.OrderRegistry;
import tests.BaseTest;
import helpers.ActionsViaAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.CASCADE_DELETION)
@Story("Каскадное удаление Проекта и дочерних сущностей")
@Tag("cascadeDeletion")
@Tag("Regression")

public class ProjectCascadeDeletionTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
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


    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        projectPage = new ProjectPage();
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
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Каскадное удаление: КТ в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-119")
    @TmsLink("1237")
    public void cascadeDeletionPointInProject (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openActivityTab();
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectPage.clickEditGantt();
        String newPointName = "Тестовая КТ_C1237_" + currentTime;
        String newWorkName = "Тестовая Работа_C1237_" + currentTime;
        String newStageName = "Тестовый Этап_C1237_" + currentTime;
        projectPage.addNewPointInGantt(newPointName, "Рабочий план");
        projectPage.clickEditGantt();
        projectPage.addNewWorkInGantt(newWorkName , "Рабочий план");
        projectPage.clickEditGantt();
        projectPage.addNewStageInGantt(newStageName, "Рабочий план");
        projectPage.clickToMaximizeOrMinimizeGantt();
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        swpRegistry.open();
        swpRegistry.changeView("Все");
        swpRegistry.checkEntityNotExist(newPointName);
        swpRegistry.checkEntityNotExist(newWorkName);
        swpRegistry.checkEntityNotExist(newStageName);
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(newPointName);
        searchForm.checkEntityNotFoundInGlobalSearch(newWorkName);
        searchForm.checkEntityNotFoundInGlobalSearch(newStageName);
    }

    @ParameterizedTest(name = "Каскадное удаление: Результаты в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-120")
    @TmsLink("1238")
    public void cascadeDeletionResultInProject (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openResultsTab();
        projectPage.clickAddResult("Ведомственный");
        result
                .setName("Тест_C1238_" + currentTime)
                .setUnit("Единица")
                .setValue("1")
                .setResponsible(user.getName());
        resultPage.fillFields(result);
        resultPage.clickSaveAndClose();
        projectPage.shouldHaveDefaultResultsTable();
        projectPage.shouldHaveDepartmentalResult(result.getName());
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        resultsRegistry.open();
        resultsRegistry.changeView("Все результаты");
        resultsRegistry.checkEntityNotExist(result.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(result.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Контракт в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-121")
    @TmsLink("1239")
    public void cascadeDeletionContractInProject (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openContractsTab();
        projectPage.clickAddContact();
        contact
                .setContractName("Контракт_С1239_" + currentTime)
                .setExceptedPrice("12345");
        contractPage.fillFields(contact);
        contractPage.clickSaveAndClose();
        projectPage.checkContractPresentInTable(contact.getName());
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        contractsRegistry.open();
        contractsRegistry.changeView("Все контракты");
        contractsRegistry.checkContractNotExist(contact.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(contact.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Показатель в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-122")
    @TmsLink("1240")
    public void cascadeDeletionIndicatorInProject (User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        indicator
                .setName("Тест_C1240_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(indicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(indicator.getName());
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        indicatorsRegistry.open();
        indicatorsRegistry.changeView("Все показатели");
        indicatorsRegistry.checkIndicatorNotExist(indicator.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(indicator.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Риски/Возможности проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-123")
    //TODO: Линк на ТМС
    public void cascadeDeletionRiskAndOpportunitiesInProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openRisksOpportunitiesTab();
        projectPage.clickAddRisk();
        risk
                .setRisksAndOpportunitiesName("ATEST-123_" + currentTime)
                .setCategory("Финансовый риск");
        risksAndOpportunitiesPage.fillFields(risk);
        risksAndOpportunitiesPage.clickSaveAndClose();
        projectPage.checkRiskPresentInTable(risk.getRisksAndOpportunitiesName());
        projectPage.clickAddOpportunity();
        opportunity
                .setRisksAndOpportunitiesName("ATEST-123_" + currentTime)
                .setCategory("Финансовая возможность");
        risksAndOpportunitiesPage.fillFields(opportunity);
        risksAndOpportunitiesPage.clickSaveAndClose();
        projectPage.checkOpportunityPresentInTable(opportunity.getRisksAndOpportunitiesName());
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        risksAndOpportunitiesRegistry.open();
        risksAndOpportunitiesRegistry.changeView("Все");
        risksAndOpportunitiesRegistry.checkRiskOrOpportunityNotExist(risk.getRisksAndOpportunitiesName());
        risksAndOpportunitiesRegistry.checkRiskOrOpportunityNotExist(opportunity.getRisksAndOpportunitiesName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(risk.getRisksAndOpportunitiesName());
        searchForm.checkEntityNotFoundInGlobalSearch(opportunity.getRisksAndOpportunitiesName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Поручение в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-124")
    //TODO: Линк на ТМС
    public void cascadeDeletionOrderInProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openOrdersTab();
        projectPage.clickAddOrder();
        order
                .setName("ATEST-124_" + currentTime)
                .setPriority("Высокий")
                .setPlanDate(currentDate)
                .setExecutor(user.getName());
        orderPage.fillFields(order);
        orderPage.clickSaveAndClose();
        projectPage.checkOrderPresentInTable(order.getName());
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        orderRegistry.open();
        orderRegistry.changeView("Все поручения");
        orderRegistry.checkOrderNotExist(order.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(order.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Совещание в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-125")
    //TODO: Линк на ТМС
    public void cascadeDeletionMeetingInProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openMeetingTab();
        projectPage.clickAddMeeting();
        meeting
                .setName("ATEST-125_" + currentTime)
                .setLocation("Переговорка 'Север'")
                .setSecretary(user.getName());
        meetingPage.fillFields(meeting);
        meetingPage.clickSaveAndClose();
        projectPage.checkMeetingPresentInTable(meeting.getName());
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        meetingsRegistry.open();
        meetingsRegistry.changeView("Все совещания");
        meetingsRegistry.checkOrderNotExist(meeting.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(meeting.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Открытые вопросы в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-126")
    //TODO: Линк на ТМС
    public void cascadeDeletionOpenQuestionsInProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openOpenQuestionsTab();
        projectPage.clickAddOpenQuestion();
        openQuestion
                .setName("ATEST-126_" + currentTime)
                .setExecutor(user.getName())
                .setInitDate(currentDate);
        openQuestionPage.fillFields(openQuestion);
        openQuestionPage.clickSaveAndClose();
        projectPage.checkOpenQuestionPresentInTable(openQuestion.getName());
        projectRegistry.open();
        projectRegistry.shouldBeRegistry();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        openQuestionsRegistry.open();
        openQuestionsRegistry.changeView("Все открытые вопросы");
        openQuestionsRegistry.checkOpenQuestionNotExist(openQuestion.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(openQuestion.getName());
    }

    @ParameterizedTest(name = "Каскадное удаление: Извлеченные уроки в проекте")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-127")
    //TODO: Линк на ТМС
    public void cascadeDeletionLessonsInProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.checkCurrentProjectStage("Инициирование");
        projectPage.openLessonsTab();
        projectPage.clickAddNegativeLesson();
        negativeLesson
                .setName("Негативный урок_ATEST-127_" + currentTime);
        lessonPage.fillFields(negativeLesson);
        lessonPage.clickSaveAndClose();
        projectPage.checkLessonPresentInTable(negativeLesson.getName());
        projectPage.clickAddPositiveLesson();
        positiveLesson
                .setName("Позитивный урок_ATEST-127_" + currentTime);
        lessonPage.fillFields(positiveLesson);
        lessonPage.clickSaveAndClose();
        projectPage.checkLessonPresentInTable(positiveLesson.getName());
        projectRegistry.open();
        String projectName = ActionsViaAPI.getProjectNameFromAPI();
        projectRegistry.changeView("Все проекты");
        projectRegistry.deleteProject(projectName);
        lessonsRegistry.open();
        lessonsRegistry.changeView("Все уроки");
        lessonsRegistry.checkLessonNotExist(negativeLesson.getName());
        lessonsRegistry.checkLessonNotExist(positiveLesson.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(projectName);
        searchForm.checkEntityNotFoundInGlobalSearch(negativeLesson.getName());
        searchForm.checkEntityNotFoundInGlobalSearch(positiveLesson.getName());
    }
}
