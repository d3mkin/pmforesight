package tests.project;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.ActionsViaAPI;
import helpers.TestSuiteName;
import model.Indicator;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.goals_and_indicators.GoalRegistry;
import pages.goals_and_indicators.goal.GoalPage;
import pages.goals_and_indicators.indicator.IndicatorPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Epic(TestSuiteName.GOALS)
@Story("Расчет индикатора цели по показателям")
public class GoalCalculationTests extends BaseTest {
    private SingInPage singIn;
    private IndicatorPage indicatorPage;
    private Indicator achievedIndicator;
    private Indicator almostAchievedIndicator;
    private Indicator notAchievedIndicator;
    private Indicator noDataIndicator;
    private GoalPage goalPage;
    private GoalRegistry goalRegistry;
    private long currentTime;


    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        indicatorPage = new IndicatorPage();
        achievedIndicator = new Indicator();
        almostAchievedIndicator = new Indicator();
        notAchievedIndicator = new Indicator();
        noDataIndicator = new Indicator();
        goalPage = new GoalPage();
        goalRegistry = new GoalRegistry();
        currentTime = System.currentTimeMillis();
        ActionsViaAPI.createGoalViaAPI("Ведомственный");
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteGoalCreatedFromAPI();
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Расчет индикатора цели по показателям: 'Нет показателей'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-78")
    @TmsLink("1233")
    public void noIndicatorsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openGoalCreatedFromAPI();
        goalRegistry.open();
        goalRegistry.shouldBeRegistry();
        goalRegistry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Нет показателей");
    }

    @ParameterizedTest(name = "Расчет индикатора цели по показателям: 'Достигнута'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-78")
    @TmsLink("1233")
    public void achievedIndicatorTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openGoalCreatedFromAPI();
        achievedIndicator
                .setName("Достигнута" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1")
                .setApprovingDoc("Рабочий план")
                .setPeriod("2020")
                .setPlan("10")
                .setForecast("10")
                .setFact("11")
                .setResponsible(user.getName());
        goalPage.addIndicator();
        indicatorPage.fillRequiredFields(achievedIndicator);
        indicatorPage.clickSaveAndClose();
        goalPage.checkIndicatorIsDisplayed(achievedIndicator);
        goalRegistry.open();
        goalRegistry.shouldBeRegistry();
        goalRegistry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Нет данных");
        ActionsViaAPI.openGoalCreatedFromAPI();
        goalPage.searchAndOpenIndicator(achievedIndicator);
        goalPage.getBrowserTabs();
        goalPage.switchToNextBrowserTab();
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(achievedIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkValuesAreDisplayed(achievedIndicator);
        indicatorPage.closeCurrentBrowserTab();
        goalPage.switchToPreviousBrowserTab();
        goalRegistry.open();
        goalRegistry.shouldBeRegistry();
        goalRegistry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Достигнута");

    }

    @ParameterizedTest(name = "Расчет индикатора цели по показателям: 'Частично достигнута'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-78")
    @TmsLink("1233")
    public void almostAchievedIndicatorTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openGoalCreatedFromAPI();
        almostAchievedIndicator
                .setName("Частично достигнута" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1")
                .setApprovingDoc("Рабочий план")
                .setPeriod("2020")
                .setPlan("10")
                .setForecast("10")
                .setFact("9")
                .setResponsible(user.getName());
        goalPage.addIndicator();
        indicatorPage.fillRequiredFields(almostAchievedIndicator);
        indicatorPage.clickSaveAndClose();
        goalPage.checkIndicatorIsDisplayed(almostAchievedIndicator);
        goalPage.searchAndOpenIndicator(almostAchievedIndicator);
        goalPage.getBrowserTabs();
        goalPage.switchToNextBrowserTab();
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(almostAchievedIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkValuesAreDisplayed(almostAchievedIndicator);
        indicatorPage.closeCurrentBrowserTab();
        goalPage.switchToPreviousBrowserTab();
        goalRegistry.open();
        goalRegistry.shouldBeRegistry();
        goalRegistry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Частично достигнута");

    }

    @ParameterizedTest(name = "Расчет индикатора цели по показателям: 'Не достигнута'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-78")
    @TmsLink("1233")
    public void notAchievedIndicatorTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openGoalCreatedFromAPI();
        notAchievedIndicator
                .setName("Не достигнута" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1")
                .setApprovingDoc("Рабочий план")
                .setPeriod("2020")
                .setPlan("10")
                .setForecast("10")
                .setFact("5")
                .setResponsible(user.getName());
        goalPage.addIndicator();
        indicatorPage.fillRequiredFields(notAchievedIndicator);
        indicatorPage.clickSaveAndClose();
        goalPage.checkIndicatorIsDisplayed(notAchievedIndicator);
        goalPage.searchAndOpenIndicator(notAchievedIndicator);
        goalPage.getBrowserTabs();
        goalPage.switchToNextBrowserTab();
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(notAchievedIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkValuesAreDisplayed(notAchievedIndicator);
        indicatorPage.closeCurrentBrowserTab();
        goalPage.switchToPreviousBrowserTab();
        goalRegistry.open();
        goalRegistry.shouldBeRegistry();
        goalRegistry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Не достигнута");
    }

    @ParameterizedTest(name = "Расчет индикатора цели по показателям: 'Нет данных'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-78")
    @TmsLink("1233")
    public void noDataIndicatorTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openGoalCreatedFromAPI();
        noDataIndicator
                .setName("Нет данных" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1")
                .setApprovingDoc("Рабочий план")
                .setResponsible(user.getName());
        goalPage.addIndicator();
        indicatorPage.fillRequiredFields(noDataIndicator);
        indicatorPage.clickSaveAndClose();
        goalPage.checkIndicatorIsDisplayed(noDataIndicator);
        goalRegistry.open();
        goalRegistry.shouldBeRegistry();
        goalRegistry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Нет данных");
    }
}
