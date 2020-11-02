package tests.smoke.project;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.ActionsViaAPI;
import helpers.TestSuiteName;
import model.Indicator;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.goals_and_indicators.indicator.IndicatorPage;
import pages.managementobjects.project.ProjectPage;
import tests.BaseTest;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.INDICATORS)
public class IndicatorTests extends BaseTest {
    private SingInPage singIn;
    private ProjectPage projectPage;
    private IndicatorPage indicatorPage;
    private Indicator testIndicator;
    private long currentTime;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        projectPage = new ProjectPage();
        indicatorPage = new IndicatorPage();
        testIndicator = new Indicator();
        currentTime = System.currentTimeMillis();
        ActionsViaAPI.createProjectViaAPI("Инициирование", "Ведомственный");
    }

    @AfterEach
    public void logout() {
        ActionsViaAPI.deleteProjectCreatedFromAPI();
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание сущности Показатель из карточки Проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-61")
    @TmsLink("1047")
    public void createIndicatorFromProject(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        testIndicator
                .setName("Тест_1047_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1");
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
    }

    @ParameterizedTest(name = "Добавление данных за период в Показателе Ведомственного проекта")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-63")
    @TmsLink("1155")
    public void checkPercentageOfAchievement(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1")
                .setPeriod("2020")
                .setPlan("10")
                .setForecast("10")
                .setFact("9");
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        //Переключение на другую вкладку
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkValuesAreDisplayed(testIndicator);
        indicatorPage.getBrowserTabs();
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.switchToBrowserTab(0);
    }

    @ParameterizedTest(name = "Общий процент достижения плана %: Возрастающего показателя")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-80")
    @TmsLink("1228")
    public void checkIncreasedIndicatorPercentageOfAchievement(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("1")
                .setPeriod("2020")
                .setPlan("10")
                .setForecast("10")
                .setFact("9");
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        //Переключение на другую вкладку
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        //Проверяем для Возрастающего и Плановое значение > Базового
        indicatorPage.checkPercentageOfAchievement(testIndicator);
        //Проверяем для Возрастающего и Плановое значение <= Базового
        indicatorPage.changeBasicValue(testIndicator, "11");
        indicatorPage.checkPercentageOfAchievement(testIndicator);
        indicatorPage.getBrowserTabs();
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.switchToBrowserTab(0);
    }

    @ParameterizedTest(name =  "Общий процент достижения плана %: Убывающего показателя")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-81")
    @TmsLink("1228")
    public void checkDecreasedIndicatorPercentageOfAchievement(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Убывающий")
                .setUnit("Единица")
                .setBasicValue("11")
                .setPeriod("2020")
                .setPlan("10")
                .setForecast("10")
                .setFact("9");
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        //Переключение на другую вкладку
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        //Проверяем для Убывающего и Плановое значение < Базового
        indicatorPage.checkPercentageOfAchievement(testIndicator);
        //Проверяем для Убывающего и Плановое значение >= Базового
        indicatorPage.changeBasicValue(testIndicator, "7");
        indicatorPage.checkPercentageOfAchievement(testIndicator);
        indicatorPage.getBrowserTabs();
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.switchToBrowserTab(0);
    }

    @ParameterizedTest(name = "Общий процент достижения плана %: Фиксированного показателя")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-82")
    @TmsLink("1228")
    public void checkFixedIndicatorPercentageOfAchievement(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        projectPage.clickAddIndicator();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Фиксированный")
                .setUnit("Единица")
                .setBasicValue("17")
                .setPeriod("2020")
                .setPlan("10")
                .setForecast("10")
                .setFact("9");
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.checkDefaultControlObject(ActionsViaAPI.getProjectNameFromAPI());
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        //Переключение на другую вкладку
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        //Проверяем для Фиксированного и Плановое значение != Базового
        indicatorPage.checkPercentageOfAchievement(testIndicator);
        //Проверяем для Фиксированного и Плановое значение == Базового
        indicatorPage.changeBasicValue(testIndicator, "10");
        indicatorPage.checkPercentageOfAchievement(testIndicator);
        indicatorPage.getBrowserTabs();
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.switchToBrowserTab(0);
    }

    @ParameterizedTest(name = "Расчет индикатора периода: Возрастающий показатель")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-42")
    @Tag("ATEST-43")
    @Tag("ATEST-44")
    @Tag("ATEST-45")
    @TmsLink("1217")
    public void checkIncreasingIndicatorValue(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("100")
                .setPeriod("2020")
                .setFact("100")
                .setPlan("100");
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        //Проверка кейса 'Нет данных'
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        indicatorPage.changePlanValue(testIndicator, "90");
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Достигнут'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2021")
                .setFact("150")
                .setPlan("150");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        indicatorPage.changeFactValue(testIndicator, "160");
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Срыв достижения'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2022")
                .setFact("140")
                .setPlan("150");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Не достигнут'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2023")
                .setFact("110")
                .setPlan("150");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        indicatorPage.getBrowserTabs();
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.switchToBrowserTab(0);
    }

    @ParameterizedTest(name = "Расчет индикатора периода: Убывающий показатель")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-46")
    @Tag("ATEST-47")
    @Tag("ATEST-48")
    @Tag("ATEST-49")
    @TmsLink("1218")
    public void checkDecreasingIndicatorValue(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Убывающий")
                .setUnit("Единица")
                .setBasicValue("100")
                .setPeriod("2020")
                .setFact("100")
                .setPlan("100");
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        //Проверка кейса 'Нет данных'
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        indicatorPage.changePlanValue(testIndicator, "110");
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Достигнут'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2021")
                .setFact("90")
                .setPlan("90");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        indicatorPage.changeFactValue(testIndicator, "50");
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Срыв достижения'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2022")
                .setFact("92")
                .setPlan("90");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Не достигнут'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2023")
                .setFact("110")
                .setPlan("90");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        indicatorPage.getBrowserTabs();
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.switchToBrowserTab(0);
    }

    @ParameterizedTest(name = "Расчет индикатора периода: Фиксированный показатель")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-50")
    @Tag("ATEST-51")
    @Tag("ATEST-52")
    @Tag("ATEST-53")
    @TmsLink("1219")
    public void checkFixedIndicatorValue(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Фиксированный")
                .setUnit("Единица")
                .setBasicValue("100")
                .setPeriod("2020")
                .setFact("100")
                .setPlan("100");
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        //Проверка кейса 'Нет данных'
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Достигнут'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2021")
                .setFact("110")
                .setPlan("110");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Срыв достижения'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2022")
                .setFact("88")
                .setPlan("90");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //Проверка кейса 'Не достигнут'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2023")
                .setFact("150")
                .setPlan("90");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        indicatorPage.getBrowserTabs();
        indicatorPage.closeCurrentBrowserTab();
        indicatorPage.switchToBrowserTab(0);
    }

    @ParameterizedTest(name = "Расчёт общего индикатора Показателя: Год, ВКЛЮЧАЯ текущий")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-54")
    @TmsLink("1226")
    public void checkCurrentYearGeneralIndicator(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setBasicValue("100")
                .setPeriod("2019")
                .setFact("140")
                .setPlan("150");
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        //'Срыв срока'
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //'Не достигнут'
        testIndicator
                .setBasicValue("100")
                .setPeriod("2020")
                .setFact("110")
                .setPlan("150");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.openIndicatorsTab();
        $(By.xpath("//div[@name='KPIs']//td/span")).shouldBe(visible).shouldHave(attribute("data-tooltip", "Не достигнут на 2020 г."),
                cssValue("color", "rgba(255, 89, 64, 1)"));
    }

    @ParameterizedTest(name = "Расчёт общего индикатора Показателя: Полугодие, ВКЛЮЧАЯ текущее")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-55")
    @TmsLink("1226")
    public void checkCurrentHalfYearGeneralIndicator(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setKPI("Полугодие")
                .setBasicValue("100")
                .setPeriod("П 1 2019")
                .setFact("110")
                .setPlan("110");
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        //'Достигнут' 1ое полугодие
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //'Достигнут' 2ое полугодие
        testIndicator
                .setBasicValue("100")
                .setPeriod("П 2 2019")
                .setFact("110")
                .setPlan("110");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //'Нет данных' 1ое полугодие текущего года
        testIndicator
                .setBasicValue("100")
                .setPeriod("П 1 2020")
                .setFact("100")
                .setPlan("100");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.openIndicatorsTab();
        $(By.xpath("//div[@name='KPIs']//td/span")).shouldBe(visible).shouldHave(attribute("data-tooltip", "Нет данных на П 1 2020 г."),
                cssValue("color", "rgba(102, 102, 102, 1)"));
    }

    @ParameterizedTest(name = "Расчёт общего индикатора Показателя: Квартал, ВКЛЮЧАЯ текущий")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-56")
    @TmsLink("1226")
    public void checkCurrentQuarterGeneralIndicator(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setKPI("Квартал")
                .setBasicValue("100")
                .setPeriod("Кв 1 2020")
                .setFact("110")
                .setPlan("110");
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        //'Достигнут' 1ый квартал 2020
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //'Срыв срока' 2ой квартал 2020
        testIndicator
                .setBasicValue("100")
                .setPeriod("Кв 2 2020")
                .setFact("140")
                .setPlan("150");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //'Нет данных' 3ий квартал 2020
        testIndicator
                .setBasicValue("100")
                .setPeriod("Кв 3 2020")
                .setFact("100")
                .setPlan("100");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.openIndicatorsTab();
        $(By.xpath("//div[@name='KPIs']//td/span")).shouldBe(visible).shouldHave(attribute("data-tooltip", "Срыв достижения на Кв 2 2020 г."),
                cssValue("color", "rgba(255, 210, 70, 1)"));
    }

    @ParameterizedTest(name = "Расчёт общего индикатора Показателя: Месяц, ВКЛЮЧАЯ текущий")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-57")
    @TmsLink("1226")
    public void checkCurrentMonthGeneralIndicator(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.openProjectCreatedFromAPI();
        projectPage.openIndicatorsTab();
        testIndicator
                .setName("Тест_1155_" + currentTime)
                .setEstimationType("Возрастающий")
                .setUnit("Единица")
                .setKPI("Месяц")
                .setBasicValue("100")
                .setPeriod("Март 2020")
                .setFact("140")
                .setPlan("150");
        projectPage.clickAddIndicator();
        indicatorPage.fillRequiredFields(testIndicator);
        indicatorPage.clickSaveAndClose();
        projectPage.shouldHaveIndicatorsTable();
        projectPage.shouldHaveIndicator(testIndicator.getName());
        projectPage.openIndicatorCard();
        projectPage.getBrowserTabs();
        projectPage.switchToBrowserTab(1);
        indicatorPage.clickAddValueButton();
        //'Срыв срока' Март 2020
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //'Нет Данных' Апрель 2020
        testIndicator
                .setBasicValue("100")
                .setPeriod("Апрель 2020")
                .setFact("100")
                .setPlan("100");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        //'Достигнут' Май 2020
        testIndicator
                .setBasicValue("100")
                .setPeriod("Май 2020")
                .setFact("110")
                .setPlan("110");
        indicatorPage.clickAddValueButton();
        indicatorPage.fillIndicatorsValues(testIndicator);
        indicatorPage.clickSaveAndClose();
        indicatorPage.checkPeriodIndicatorStatus(testIndicator);
        projectPage.closeCurrentBrowserTab();
        projectPage.switchToBrowserTab(0);
        Selenide.refresh();
        projectPage.openIndicatorsTab();
        $(By.xpath("//div[@name='KPIs']//td/span")).shouldBe(visible).shouldHave(attribute("data-tooltip", "Достигнут на Май 2020 г."),
                cssValue("color", "rgba(95, 175, 97, 1)"));
    }
}
