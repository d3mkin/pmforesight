package tests.entity_generation.goals_and_indicators;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.Goal;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.goals_and_indicators.GoalRegistry;
import pages.goals_and_indicators.goal.GoalPage;
import tests.BaseTest;
import helpers.ActionsViaAPI;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.ENTITY_CREATION)
@Tag("entityGeneration")
@Tag("Regression")
public class CreateGoalTests extends BaseTest {
    private SingInPage singIn;
    private GoalPage createModal;
    private GoalRegistry registry;
    private Goal departmentalGoal;
    private Goal municipalGoal;
    private Goal federalGoal;
    private Goal regionalGoal;
    private Goal nationalGoal;
    private long currentTime;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        createModal = new GoalPage();
        registry = new GoalRegistry();
        departmentalGoal = new Goal();
        municipalGoal = new Goal();
        regionalGoal = new Goal();
        federalGoal = new Goal();
        nationalGoal = new Goal();
        currentTime = System.currentTimeMillis();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest (name = "Создание сущности Цель с разными уровнями управления: Муниципальный, Федеральный, Региональный, Национальный")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-115")
    @TmsLink("1235")
    public void createAllTypesGoalTest(User user){
        parameter("Пользователь", user.getName());
        //Ведомственная цель
        departmentalGoal
                .setName("Тест_C1235_" + currentTime)
                .setEditors(user.getName());
        singIn.asUser(user);
        registry.addGoal();
        createModal.fillFields(departmentalGoal);
        createModal.clickSaveAndClose();
        registry.findAndDeleteGoal(departmentalGoal);
        //Муниципальная цель
        municipalGoal
            .setName("Тест_C1235_" + currentTime)
            .setManagementLevel("Муниципальный")
            .setEditors(user.getName());
        registry.addGoal();
        createModal.fillFields(municipalGoal);
        createModal.clickSaveAndClose();
        registry.changeView("Все цели нацпроектов");
        registry.findAndDeleteGoal(municipalGoal);
        //Федеральная цель
        federalGoal
                .setName("Тест_C1235_" + currentTime)
                .setManagementLevel("Федеральный")
                .setEditors(user.getName());
        registry.addGoal();
        createModal.fillFields(federalGoal);
        createModal.clickSaveAndClose();
        registry.changeView("Все цели нацпроектов");
        registry.findAndDeleteGoal(federalGoal);
        //Региональная цель
        regionalGoal
                .setName("Тест_C1235_" + currentTime)
                .setManagementLevel("Региональный")
                .setEditors(user.getName());
        registry.addGoal();
        createModal.fillFields(regionalGoal);
        createModal.clickSaveAndClose();
        registry.changeView("Все цели нацпроектов");
        registry.findAndDeleteGoal(regionalGoal);
        //Национальная цель
        nationalGoal
                .setName("Тест_C1235_" + currentTime)
                .setManagementLevel("Национальный")
                .setEditors(user.getName());
        registry.addGoal();
        createModal.fillFields(nationalGoal);
        createModal.clickSaveAndClose();
        registry.changeView("Все цели нацпроектов");
        registry.findAndDeleteGoal(nationalGoal);
    }

    @ParameterizedTest (name = "Создание сущности Цель (API)")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-114")
    public void createGoalFromAPITest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        ActionsViaAPI.createGoalViaAPI("Ведомственный");
        ActionsViaAPI.openGoalCreatedFromAPI();
        ActionsViaAPI.deleteGoalCreatedFromAPI();
    }
}
