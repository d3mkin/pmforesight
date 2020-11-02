package tests.entity_generation.portfolio;

import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import helpers.TestSuiteName;
import model.Portfolio;
import model.User;
import pages.auth.LogoutPage;
import pages.auth.SingInPage;
import pages.elements.ControlPanel;
import pages.managementobjects.portfolio.PortfolioRegistry;
import pages.managementobjects.portfolio.CreatePortfolioPage;
import tests.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.qameta.allure.Allure.parameter;

@Story(TestSuiteName.ENTITY_CREATION)
@Tag("entityGeneration")
@Tag("Regression")
public class CreatePortfolioTests extends BaseTest {
   private SingInPage singIn;
    private PortfolioRegistry portfolioRegistry;
    private CreatePortfolioPage portfolioPage;
    private Portfolio portfolio;
    private ControlPanel controlPanel;

    @BeforeEach
    public void setupPages() {
        singIn = new SingInPage();
        singIn.open();
        portfolioRegistry = new PortfolioRegistry();
        portfolioPage = new CreatePortfolioPage();
        portfolio = new Portfolio();
        controlPanel = new ControlPanel();
    }

    @AfterEach
    public void logout() {
        new LogoutPage().open();
    }

    @ParameterizedTest(name = "Создание сущности Портфеля из реестра. Сообщение о необходимости заполнить обязательные поля для кнопки 'Сохранить'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-176")
    @TmsLink("355")
    public void PortfolioSaveShouldHaveMessageAboutRequiredFieldsTest(User user) {
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        portfolioRegistry.open();
        portfolioRegistry.controlPanel().clickAddButton();
        portfolioPage.shouldBeOpened();
        portfolioPage.shouldHaveTitle("Портфель");
        portfolioPage.clickSave();
        portfolioPage
                .shouldHaveMessageAboutRequiredFields(
                        "Необходимо заполнить поле \"Наименование\"",
                        "Необходимо заполнить поле \"Руководитель\""
                );
        portfolioPage.closeDialog();
        portfolioPage.clickClose();
        portfolioRegistry.shouldBeRegistry();
    }

    @ParameterizedTest(name = "Создание сущности Портфель из реестра. Кнопка 'Сохранить и закрыть'")
    @MethodSource("helpers.UserProvider#mainFA")
    @Tag("ATEST-175")
    @TmsLink("355")
    public void createPortfolioButtonSave(User user) {
        long currentTime = System.currentTimeMillis();
        portfolio.setNameValue("Тест_C355_" + currentTime)
                .setSupervisor(user.getName());
        parameter("Пользователь", user.getName());
        singIn.asUser(user);
        portfolioRegistry.open();
        portfolioRegistry.shouldBeRegistry();
        portfolioRegistry.controlPanel().clickAddButton();
        portfolioPage.shouldBeOpened();
        portfolioPage.shouldHaveTitle("Портфель");
        portfolioPage.fillInConnonInfo(portfolio);
        portfolioPage.fillRoles(portfolio);
        portfolioPage.clickSaveAndClose();
        portfolioRegistry.shouldBeRegistry();
        portfolioRegistry.searchGprogram(portfolio.getNameValue());
        portfolioRegistry.shouldHaveCreatedRecord(portfolio.getNameValue());
        portfolioRegistry.selectFirstRow();
        portfolioRegistry.clickDelete();
        portfolioRegistry.acceptDelete();
        portfolioRegistry.searchGprogram(portfolio.getNameValue());
        portfolioRegistry.shouldNotHaveResults();
    }

}
