package tests.entity_generation.risk_management;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import helpers.TestSuiteName;
import model.RisksAndOpportunities;
import model.User;
import pages.auth.SingInPage;
import pages.index.IndexPage;
import pages.risk_management.RisksAndOpportunitiesRegistry;
import pages.risk_management.risks_and_opportunities.RisksAndOpportunitiesPage;
import tests.BaseTest;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.ENTITY_CREATION)
@Tag("entityGeneration")
@Tag("Regression")
public class CreateRiskEntityTests extends BaseTest {
    private SingInPage singIn;
    private RisksAndOpportunitiesRegistry riskRegistry;
    private RisksAndOpportunitiesPage riskPage;
    private IndexPage index;
    private RisksAndOpportunities risksAndOpportunities;

    @BeforeEach
    public void loginAndSetup() {
        singIn = new SingInPage();
        singIn.open();
        riskRegistry = new RisksAndOpportunitiesRegistry();
        index = new IndexPage();
        riskPage = new RisksAndOpportunitiesPage();
        risksAndOpportunities = new RisksAndOpportunities();
    }

    @AfterEach
    public void logout() {
        index.header().logout();
    }


    @ParameterizedTest(name = "Создание сущности Риски и возможности из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-172")
    @TmsLink("467")
    public void riskEntitySaveHaveMessageAboutRequiredFieldsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        riskRegistry.open();
        riskRegistry.controlPanel().clickAddButton();
        riskPage.shouldBeOpened();
        riskPage.shouldHaveTitle("Риски и возможности");
        riskRegistry.shouldBeRegistry();
        riskPage.clickSave();
        riskPage
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Наименование\"",
                        "Необходимо заполнить поле \"Объект управления\"",
                        "Необходимо заполнить поле \"Тип риска/возможности\"",
                        "Необходимо заполнить поле \"Категория\"",
                        "Необходимо заполнить поле \"Инициатор\"",
                        "Необходимо заполнить поле \"Ответственный\""
                );
        riskPage.closeDialog();
        riskPage.clickClose();
        riskRegistry.shouldBeRegistry();
    }

    @ParameterizedTest(name = "Создание сущности Риски и возможности из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-171")
    @TmsLink("463")
    public void createRiskWithSaveButton(User user) {
        long currentTime = System.currentTimeMillis();
        risksAndOpportunities
                .setRisksAndOpportunitiesName("Тест_C463_" + currentTime)
                .setControlObject("Популяризация предпринимательства")
                .setTypeRisksOpportunities("Риск")
                .setCategory("Финансовый риск")
                .setState("Закрыто")
                .setriskProbability("Средняя")
                .setlevelOfInfluence("Среднее")
                .setApprovingDocument("Паспорт")
                .setInitiator(user.getName())
                .setResponsible(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        riskRegistry.open();
        riskRegistry.controlPanel().clickAddButton();
        riskPage.shouldBeOpened();
        riskPage.fillFields(risksAndOpportunities);
        riskPage.clickSaveAndClose();
        riskRegistry.chooseTypeOfSearchAllMy();
        riskRegistry.searchRisksAndOpportunities(risksAndOpportunities.getRisksAndOpportunitiesName());
        riskRegistry.selectFirstRow();
        riskRegistry.clickDelete();
        riskRegistry.acceptDelete();
        riskRegistry.searchRisk(risksAndOpportunities.getRisksAndOpportunitiesName());
        riskRegistry.shouldNotHaveResults();
    }
}
