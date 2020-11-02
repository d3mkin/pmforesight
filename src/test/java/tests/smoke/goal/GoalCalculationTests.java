package tests.smoke.goal;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

@Story(TestSuiteName.GOALS)
public class    GoalCalculationTests extends BaseTest {
    private SingInPage singIn;
    private IndicatorPage indicatorPage;
    private Indicator achievedIndicator;
    private Indicator almostAchievedIndicator;
    private Indicator notAchievedIndicator;
    private Indicator noDataIndicator;
    private GoalPage goalPage;
    private GoalRegistry registry;
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
        registry = new GoalRegistry();
        currentTime = System.currentTimeMillis();
        ActionsViaAPI.createGoalViaAPI("Ведомственный");
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteGoalCreatedFromAPI();
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Расчет индикатора цели по показателям: 'Нет показателей', 'Нет данных', 'Достигнута', 'Частично достигнута', 'Не достигнута' ")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-78")
    @TmsLink("1233")
    public void calculationByIndicators(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openGoalCreatedFromAPI();
        registry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Нет показателей");
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
        registry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Нет данных");
        ActionsViaAPI.openGoalCreatedFromAPI();
        goalPage.searchAndOpenIndicator(achievedIndicator);
        goalPage.getBrowserTabs();
        //TODO переделать метод переключения между вкладками
        goalPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(achievedIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkValuesAreDisplayed(achievedIndicator);
        registry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Достигнута");
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.getBrowserTabs();
        indicatorPage.switchToBrowserTab(0);

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
        goalPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(almostAchievedIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkValuesAreDisplayed(almostAchievedIndicator);
        registry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Частично достигнута");
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.getBrowserTabs();
        indicatorPage.switchToBrowserTab(0);

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
        goalPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(notAchievedIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkValuesAreDisplayed(notAchievedIndicator);
        registry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Не достигнута");
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.getBrowserTabs();
        indicatorPage.switchToBrowserTab(0);

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
        registry.checkGoalIndicatorByIndex(ActionsViaAPI.getGoalNameFromAPI(), "Нет данных");
    }
}
